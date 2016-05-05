package com.services;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;





import com.models.UserModel;

public class UserEntityTest {
  UserModel user = new UserModel();
  @Test(priority= 1)

  public void testLogin() throws ParseException {
	  
	   String email = "omar",pass = "123";
	  String serviceUrl = "http://firstapp-ilocate.rhcloud.com/FCISquare/rest/login";
	  String urlParameters = "email=" + email + "&pass=" + pass ;
	  String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		JSONObject obj;
			obj = (JSONObject)parser.parse(retJson);
	  
      Assert.assertEquals(obj.get("email"), "omar");
  }
  @Test(priority= 2)
  public void testgetLastPosition() throws ParseException{
	  
	  String email = "mhmdsamir@gmail.com";
	  
	  String serviceUrl = "http://firstapp-ilocate.rhcloud.com/FCISquare/rest/getLastPosition";
	  String urlParameters = "email=" + email;
	  String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
	  JSONParser parser = new JSONParser();
	 JSONObject obj;
		obj = (JSONObject)parser.parse(retJson);

	 Assert.assertEquals(obj.get("lat"), 30.12);
	  
  }
  @Test(priority= 3)
  public void testUpdatePosition() throws ParseException{
	  int id = 1;
	  double lat = 30.12, lon= 60.12;
	  String serviceUrl = "http://firstapp-ilocate.rhcloud.com/FCISquare/rest/updatePosition";
	  String urlParameters = "id=" + id + "&lat=" + lat + "&long="+ lon;
	  String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
	  JSONParser parser = new JSONParser();
	 JSONObject obj;
		obj = (JSONObject)parser.parse(retJson);

	 Assert.assertEquals(obj.get("status"), true);
	  
  }
 }
  