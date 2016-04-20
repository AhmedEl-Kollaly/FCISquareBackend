package com.models;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;


public class BrandModel {
	
	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	private String prem;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	public static BrandModel addNewbrand(String name, String email, String pass,
            String question, String answer, String prem) {
try {
Connection conn = DBConnection.getActiveConnection();
String sql = "Insert into brand (`name`,`email`,`password`,`question`,`answer`,`prem`) VALUES  (?,?,?,?,?,?)";
// System.out.println(sql);

PreparedStatement stmt;
stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
stmt.setString(1, name);
stmt.setString(2, email);
stmt.setString(3, pass);
stmt.setString(4, question);
stmt.setString(5, answer);
stmt.setString(6, prem);
stmt.executeUpdate();
ResultSet rs = stmt.getGeneratedKeys();
if (rs.next()) {
	BrandModel brand = new BrandModel();
	brand.id = rs.getInt(1);
	brand.email = email;
	brand.pass = pass;
	brand.name = name;
	brand.lat = 0.0;
	brand.lon = 0.0;
	return brand;
}
return null;
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
return null;
}
	public String getPrem() {
		return prem;
	}
	public void setPrem(String prem) {
		this.prem = prem;
	}

	
	public static BrandModel getPremStatus(String email) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from brand where `email` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				BrandModel brand = new BrandModel();
				brand.id = rs.getInt(1);
				brand.email = rs.getString("email");
				brand.pass = rs.getString("password");
				brand.name = rs.getString("name");
				brand.lat = rs.getDouble("lat");
				brand.lon = rs.getDouble("long");
				brand.prem= rs.getString("prem");
				return brand;
			}
		
			
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
