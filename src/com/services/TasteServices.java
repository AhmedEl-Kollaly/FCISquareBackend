package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.TasteModel;
import com.models.UserModel;
import com.mysql.jdbc.Statement;

@Path("/")
public class TasteServices {
	String taste;
	
	@POST
	@Path("/addUserTaste")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean addUserTaste(@FormParam("name") String taste,
			@FormParam("id") int user_id) {
		
		boolean added = TasteModel.addTasteToUser(taste, user_id);
	
		return added;
	}
	
	
}
