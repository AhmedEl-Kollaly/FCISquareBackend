package com.models;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



//import java.beans.Statement;
import com.mysql.jdbc.Statement;

public class PlaceModel 
{
	private String name;
	private int rate;
	private double latitude;
	private double longitude;
	private int numberofcheckins;
	private int numrate;
	private int ratebefavg;
	private int id;
	private Tips [] tip;
	PlaceModel()
	{
		numberofcheckins=1;
		rate=0;
		ratebefavg=0;
		numrate=0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getNumberofcheckins() {
		return numberofcheckins;
	}
	public Tips[] getTip() {
		return tip;
	}
	public void setTip(Tips[] tip) {
		this.tip = tip;
	}
	public static boolean AddPlace(int userid,String name,int rate,double lon,double lat) throws SQLException
	{
		String premium="no";
		Connection connn = DBConnection.getActiveConnection();
		String sqll = "Select * from users where `id` = ?";
		PreparedStatement stmtt;
		stmtt = connn.prepareStatement(sqll);
		stmtt.setInt(1, userid);
		ResultSet rss = stmtt.executeQuery();
		if (rss.next()) 
		{	
			premium=rss.getString("prem");
		}
		if(premium.equals("yes"))
		{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into places (`name`,`rate`,`lat`,`long`,`numofcheckins`) VALUES  (?,?,?,?,?)";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setInt(2, rate);
			stmt.setDouble(3, lat);
			stmt.setDouble(4, lon);
			stmt.setDouble(5, 0);
			stmt.executeUpdate();	
			return true;
		}
		
		else
		{
			return false;
		}
	}
	public static boolean rateplace(int r,String placename) throws SQLException
	{
		int ratebefavg=0,numrate=0;
		try{
		Connection connn = DBConnection.getActiveConnection();
		String sqll = "Select * from places where `name` = ?";
		PreparedStatement stmtt;
		stmtt = connn.prepareStatement(sqll);
		stmtt.setString(1, placename);
		ResultSet rss = stmtt.executeQuery();
		if (rss.next()) 
		{
			ratebefavg = rss.getInt("ratebefavg");
			numrate = rss.getInt("numrate");
		}
		int rate,newratebef,newnumrate;
		newnumrate=numrate++;
		newratebef=ratebefavg+r;
		rate=newratebef/newnumrate;
		///////////////////////////////////////////////////////////////////////
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Update places set `rate` = ? `ratebefavg`=? `numrate`=? where `name` = ?";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rate);
		stmt.setInt(2, newratebef);		
		stmt.setInt(2, newnumrate);
		stmt.executeUpdate();
		return true;
		}catch(SQLException e)
		{
			return false;
		}
	}
	public static boolean saveplace(int userid,String placename) throws SQLException
	{
		try{
		int placeid =-1;
		Connection connn = DBConnection.getActiveConnection();
		String sqll = "Select * from places where `name` = ?";
		PreparedStatement stmtt;
		stmtt = connn.prepareStatement(sqll);
		stmtt.setString(1, placename);
		ResultSet rss = stmtt.executeQuery();
		if (rss.next()) 
		{
			placeid = rss.getInt("id");
		}
		/////////////////////////////////////////////////////////////
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into PlaceList (`userID`,`PlaceID`) VALUES  (?,?)";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, userid);
		stmt.setInt(2, placeid);
		stmt.executeUpdate();
		return true;
		}
		catch(SQLException e)
		{
			return false;
		}
	}
	public  void setNumberofcheckins(int num){
		numberofcheckins = num;
	}
	public static PlaceModel getPlace(int id){
		// TODO Auto-generated method stub
				try {
					Connection conn = DBConnection.getActiveConnection();
					String sql = "Select * from places where `id` = ?";
					PreparedStatement stmt;
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, id);
					ResultSet rs = stmt.executeQuery();
					
					if (rs.next()) {
						PlaceModel place = new PlaceModel();
						place.id = rs.getInt(1);
						place.latitude = rs.getDouble("lat");
						place.longitude = rs.getDouble("long");
						place.name = rs.getString("name");
						place.numberofcheckins = rs.getInt("numofcheckins");
						
						return place;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
	}
}
