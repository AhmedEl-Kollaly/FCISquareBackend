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

import com.models.BrandModel;
import com.models.DBConnection;
import com.models.UserModel;

@Path("/")
public class BrandServices {
  
	@POST
	@Path("/DefineBrand")
	@Produces(MediaType.TEXT_PLAIN)
	public String DefineBrand(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass, 
			  @FormParam("question") String question, @FormParam("answer") String answer, 
			    @FormParam("prem") String prem) {
		BrandModel brand = BrandModel.getPremStatus(email);
		JSONObject json = new JSONObject();
		if(brand!=null)
		{
			if(brand.getPrem()!="no")
			{
			
			brand= BrandModel.addNewbrand( name, email, pass,
		             question, answer, prem);
			
			json.put("id", brand.getId());
			json.put("name", brand.getName());
			json.put("email", brand.getEmail());
			json.put("pass", brand.getPass());
			json.put("lat", brand.getLat());
			json.put("long", brand.getLon());
			json.put("prem", brand.getPrem());
			return json.toJSONString();
			}
		}
		
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
