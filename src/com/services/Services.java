package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.models.DBConnection;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */
	@POST
	@Path("/checkin")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkin(@FormParam("id") int  id,
			@FormParam("lat") String lat, @FormParam("long") String lon) throws SQLException {
		int id2 = UserModel.getPlaces(id, lat, lon);
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("palceid", id2);
		json.put("lat", lat);
		json.put("long",lon);

		return json.toJSONString();
	}
	@POST
	@Path("/like")
	@Produces(MediaType.TEXT_PLAIN)
	public String Like(@FormParam("id") int  idcheckin,
			@FormParam("lat") int myid) throws SQLException {
		int id2 = UserModel.Like(idcheckin,myid);
		JSONObject json = new JSONObject();
		json.put("id", myid);
		

		return json.toJSONString();
	}
	
	@POST
	@Path("/comment")
	@Produces(MediaType.TEXT_PLAIN)
	public String Comment(@FormParam("id") int  myid,
			@FormParam("lat") int checkinid, @FormParam("long") String comment) throws SQLException {
		int id2 = UserModel.comment(myid, checkinid, comment);
		JSONObject json = new JSONObject();
		json.put("id", myid);
		json.put("comment", comment);
		

		return json.toJSONString();
	}
	
	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass, 
			   @FormParam("question") String question, @FormParam("answer") String answer,
			      @FormParam("prem") String prem) {
		UserModel user = UserModel.addNewUser(name, email, pass, question, answer, prem);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		json.put("prem", user.getPrem());
		return json.toJSONString();
	}
	
	@POST
	@Path("/forgetpassword")
	@Produces(MediaType.TEXT_PLAIN)
	public String forgetpassword( @FormParam("question") String question
			,@FormParam("answer") String answer) {
		UserModel user = UserModel.forgetpassword(question, answer);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		json.put("prem", user.getPrem());
		return json.toJSONString();
	}
	
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String follow(@FormParam("id1") int id1,
			@FormParam("id2") int id2) {
		UserModel follow = UserModel.follow(id1, id2);
	   return null;
	}
	
	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollow(@FormParam("id1") int id1,
			@FormParam("id2") int id2) {
		UserModel unfollow = UserModel.unfollow(id1, id2);
	   return null;
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		if(user!=null)
		{
			
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		json.put("prem", user.getPrem());
		return json.toJSONString();
		}
		else
		{
			json.put("id", "-1");
			json.put("name", "-1");
			json.put("email", "-1");
			json.put("pass", "-1");
			json.put("lat", "-1");
			json.put("long", "-1");
			json.put("prem", "no");
			return json.toJSONString();
		}
	}
	
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	@POST
	@Path("/getLastPosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLastPosition(@FormParam("email") String email){
		UserModel user= UserModel.getLastPosition(email);
		JSONObject json = new JSONObject();
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
		}
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
   @POST
   @Path("/getFollowingList")
	@Produces(MediaType.TEXT_PLAIN)
   public String getFollowingList(@FormParam("id") int id){
	   ArrayList<UserModel> followingList = new ArrayList<UserModel>(UserModel.getFollowingList(id));
	  JSONObject json = new JSONObject();
	   for(int i= 0 ;i<followingList.size();++i){

		   json.put("id"+i+": ", followingList.get(i).getId());
			json.put("name"+i+": ", followingList.get(i).getName());
			json.put("email"+i+": ", followingList.get(i).getEmail());
			json.put("lat"+i+": ", followingList.get(i).getLat());
			json.put("long"+i+": ", followingList.get(i).getLon());
			
	   }
	  
	   return json.toJSONString();
   }
}
