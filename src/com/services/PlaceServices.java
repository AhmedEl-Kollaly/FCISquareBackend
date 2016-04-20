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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.PlaceModel;
import com.models.UserModel;

@Path("/")
public class PlaceServices 
{
	@POST
	@Path("/addplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,@FormParam("name") String name,
			@FormParam("rate") String rate, @FormParam("long") String lon,@FormParam("lat") String lat ) throws NumberFormatException, SQLException {
		Boolean status = PlaceModel.AddPlace(Integer.parseInt(id),name,Integer.parseInt(rate), Double.parseDouble(lon), Double.parseDouble(lat));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	@POST
	@Path("/rateplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String rateplace(@FormParam("rate") String r,@FormParam("placename") String placename) throws NumberFormatException, SQLException 
	{
		Boolean status = PlaceModel.rateplace(Integer.parseInt(r),placename);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	@POST
	@Path("/saveplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String saveplace(@FormParam("userID") String userID,@FormParam("placename") String placename) throws NumberFormatException, SQLException 
	{
		Boolean status = PlaceModel.saveplace(Integer.parseInt(userID),placename);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
}
