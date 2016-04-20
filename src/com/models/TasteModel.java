package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;



public class TasteModel {
	String taste;
	int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}
	public static boolean addTasteToUser(String taste,int id){
		
		TasteModel userTaste = addTaste(taste);
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into usertaste (`user_id`,`taste_id`) VALUES  (?,?)";
			// System.out.println(sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, userTaste.id);
			stmt.setInt(2, id);
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static TasteModel addTaste(String taste){
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into taste (`name`) VALUES  (?)";
			// System.out.println(sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, taste);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				TasteModel userTaste = new TasteModel();
				userTaste.id = rs.getInt(1);
				userTaste.taste= taste;
				return userTaste;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
