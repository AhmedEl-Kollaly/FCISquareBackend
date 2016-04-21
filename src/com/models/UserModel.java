package com.models;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class UserModel {

	
	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	private String prem;
	
	public String getPass(){
		return pass;
	}
	
	public void setPass(String pass){
		this.pass = pass;
	}
	
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
	public static UserModel getUserInfo(int id){
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static UserModel addNewUser(String name, String email, String pass,
			             String question, String answer, String prem) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into users (`name`,`email`,`password`,`question`,`answer`,`prem`) VALUES  (?,?,?,?,?,?)";
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
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.lat = 0.0;
				user.lon = 0.0;
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static UserModel follow(int id1, int id2)
	{
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into follow (`id1`,`id2`) VALUES  (?,?)";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id1);
			stmt.setInt(2, id2);
			stmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static UserModel unfollow(int id1, int id2)
	{
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "DELETE FROM  `follow` WHERE `id1` =? AND `id2` =?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id1);
			stmt.setInt(2, id2);
			stmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static UserModel login(String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ? and `password` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				user.prem= rs.getString("prem");
				return user;
			}
			
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static UserModel forgetpassword(String question, String answer) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `question` = ? and `answer` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, question);
			stmt.setString(2, answer);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				user.prem= rs.getString("prem");
				return user;
			}
			
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static boolean updateUserPosition(Integer id, Double lat, Double lon) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Update users set `lat` = ? , `long` = ? where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
public static UserModel getLastPosition(String email) {
		
		// TODO Auto-generated method stub
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.email = rs.getString("email");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
public static ArrayList<UserModel> getFollowingList(int id){
	
	// TODO Auto-generated method stub
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from follow where `id1` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				ArrayList<UserModel> following = new  ArrayList<>();
				while(rs.next()) {
					
					following.add(getUserInfo(rs.getInt("id2")));
				}
				return following;
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

public static Integer getPlaces(int id,String lat, String lon) 
{
	double ladouble=Double.parseDouble(lat);
	double londouble=Double.parseDouble(lon);
	double mindist = 0;
	int minid = 0;
	double dist;
	// TODO Auto-generated method stub
				try {
					Connection conn = DBConnection.getActiveConnection();
					String sql = "Select * from places ";
					PreparedStatement stmt;
					stmt = conn.prepareStatement(sql);

					ResultSet rs = stmt.executeQuery();
					ArrayList<UserModel> following = new  ArrayList<>();
					int i=0;
					while(rs.next()) 
					{
						double lat2 = rs.getDouble("lat");
						double long2 = rs.getDouble("long");	
						int id =rs.getInt("id");
					
						
						 dist=DistanceCalculator.distance(ladouble,londouble,lat2,long2);
						
						if(dist<mindist||i==0)
						{
							mindist=dist;
							minid=id;
						}
						i++;
						
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*
				 *  yourID is 
				 * 
				 * 
				 */
		
	
	
	return minid;


}


}
