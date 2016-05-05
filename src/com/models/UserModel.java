package com.models;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.mysql.jdbc.Statement;

import sun.net.www.content.text.plain;

public class UserModel {

	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	private String prem;

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
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

	public static UserModel getUserInfo(int id) {
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

	public static UserModel follow(int id1, int id2) {
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

	public static UserModel unfollow(int id1, int id2) {
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
				user.prem = rs.getString("prem");
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
				user.prem = rs.getString("prem");
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
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Update users set `lat` = ? , `long` = ? where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
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
	public static int getnumofusercheckins(int id)
	{
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select id from checkin where `user_id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Integer> numofcheckins=new ArrayList<Integer>();
			while (rs.next()) {
				numofcheckins.add(rs.getInt("id"));
			}
			
			return numofcheckins.size();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
	public static ArrayList<UserModel> getFollowingList(int id) {

		// TODO Auto-generated method stub
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from follow where `id1` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			ArrayList<UserModel> following = new ArrayList<>();
			while (rs.next()) {

				following.add(getUserInfo(rs.getInt("id2")));
			}
			return following;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	public static ArrayList<UserModel> getFollowersList(int id) {

		// TODO Auto-generated method stub
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from follow where `id2` = "+id+"";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			ArrayList<UserModel> followers = new ArrayList<>();
			while (rs.next()) {

				followers.add(getUserInfo(rs.getInt("id1")));
			}
			return followers;
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

	public static int getPlaces(int id1, String lat, String lon)
			throws SQLException {
		double ladouble = Double.parseDouble(lat);
		double londouble = Double.parseDouble(lon);
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
			ArrayList<UserModel> following = new ArrayList<>();
			int i = 0;
			while (rs.next()) {
				double lat2 = rs.getDouble("lat");
				double long2 = rs.getDouble("long");
				int id = rs.getInt("id");

				dist = DistanceCalculator.distance(ladouble, londouble, lat2,
						long2);

				if (dist < mindist || i == 0) {
					mindist = dist;
					minid = id;
				}
				i++;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updatenumofcheckins(minid);
		insertintocheckins(minid, id1);

		return minid;

	}

	public static ArrayList<ArrayList<PlaceModel>> showHomePage(int id) {

		ArrayList<ArrayList<PlaceModel>> homePage = new ArrayList<ArrayList<PlaceModel>>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		ArrayList<PlaceModel> places = new ArrayList<PlaceModel>();
		ArrayList<PlaceModel> followersPlaces = new ArrayList<PlaceModel>();
		ArrayList<Integer> followersLikes = new ArrayList<Integer>();
		ArrayList<PlaceModel> followersLikedPlaces = new ArrayList<PlaceModel>();
		ArrayList<Integer> followersComments = new ArrayList<Integer>();
		ArrayList<PlaceModel> followersCommentsPlaces = new ArrayList<PlaceModel>();

		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from checkin where `user_id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				temp.add(rs.getInt("place_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		while (i < temp.size()) {
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from places where `id` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, temp.get(i));
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					PlaceModel pl=new PlaceModel();
					places.add(pl);
					places.get(i).setName(rs.getString("name"));
					places.get(i).setLatitude(rs.getDouble("lat"));
					places.get(i).setLongitude(rs.getDouble("long"));
					places.get(i).setRate(rs.getInt("rate"));
					places.get(i).setNumberofcheckins(
							rs.getInt("numofcheckins"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		homePage.add(places);
		temp.clear();
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from follow where `id1` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				temp.add(rs.getInt("id2"));
				temp2.add(rs.getInt("id2"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i = 0;
		while (i < temp.size()) {
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from places where `id` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, temp.get(i));
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					followersPlaces.get(i).setName(rs.getString("name"));
					followersPlaces.get(i).setLatitude(rs.getDouble("lat"));
					followersPlaces.get(i).setLongitude(rs.getDouble("long"));
					followersPlaces.get(i).setRate(rs.getInt("rate"));
					followersPlaces.get(i).setNumberofcheckins(
							rs.getInt("numofcheckins"));

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		homePage.add(followersPlaces);
		i = 0;
		while (i < temp.size()) {
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from likes where `myid` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, temp.get(i));
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					followersLikes.add(rs.getInt("checkinid"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		temp.clear();
		i = 0;
		while (i < followersLikes.size()) {
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from checkin where `id` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, followersLikes.get(i));
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					temp.add(rs.getInt("place_id"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		i = 0;
		while (i < temp.size()) {
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from places where `id` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, temp.get(i));
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					followersLikedPlaces.get(i).setName(rs.getString("name"));
					followersLikedPlaces.get(i)
							.setLatitude(rs.getDouble("lat"));
					followersLikedPlaces.get(i).setLongitude(
							rs.getDouble("long"));
					followersLikedPlaces.get(i).setRate(rs.getInt("rate"));
					followersLikedPlaces.get(i).setNumberofcheckins(
							rs.getInt("numofcheckins"));

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		homePage.add(followersLikedPlaces);
		temp.clear();
		i = 0;
		while (i < temp2.size()) {
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from comments where `myid` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, temp2.get(i));
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					followersComments.add(rs.getInt("checkinid"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		i = 0;
		temp.clear();
		while (i < followersComments.size()) {
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from checkin where `id` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, followersComments.get(i));
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					temp.add(rs.getInt("place_id"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		i = 0;
		while (i < temp.size()) {
			try {
				Connection conn = DBConnection.getActiveConnection();
				String sql = "Select * from places where `id` = ?";
				PreparedStatement stmt;
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, temp.get(i));
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					followersCommentsPlaces.get(i)
							.setName(rs.getString("name"));
					followersCommentsPlaces.get(i).setLatitude(
							rs.getDouble("lat"));
					followersCommentsPlaces.get(i).setLongitude(
							rs.getDouble("long"));
					followersCommentsPlaces.get(i).setRate(rs.getInt("rate"));
					followersCommentsPlaces.get(i).setNumberofcheckins(
							rs.getInt("numofcheckins"));

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		homePage.add(followersCommentsPlaces);
		temp.clear();
		i = 0;
		return homePage;
	}

	public static ArrayList<ArrayList<PlaceModel>> sortHomePageByCheckin(int id) {
		ArrayList<ArrayList<PlaceModel>> homePage = new ArrayList<ArrayList<PlaceModel>>(
				showHomePage(id));
		PlaceModel temp = new PlaceModel();
		for (int i = 0; i < homePage.get(0).size(); i++) {
			for (int j = 1; j < homePage.get(0).size(); j++) {
				if (homePage.get(0).get(j).getNumberofcheckins() < homePage
						.get(0).get(i).getNumberofcheckins()) {
					temp.setLatitude(homePage.get(0).get(i).getLatitude());
					temp.setLongitude(homePage.get(0).get(i).getLongitude());
					temp.setName(homePage.get(0).get(i).getName());
					temp.setNumberofcheckins(homePage.get(0).get(i)
							.getNumberofcheckins());
					temp.setRate(homePage.get(0).get(i).getRate());
					temp.setTip(homePage.get(0).get(i).getTip());
					homePage.get(0).get(i)
							.setLatitude(homePage.get(0).get(j).getLatitude());
					homePage.get(0)
							.get(i)
							.setLongitude(homePage.get(0).get(j).getLongitude());
					homePage.get(0).get(i)
							.setName(homePage.get(0).get(j).getName());
					homePage.get(0)
							.get(i)
							.setNumberofcheckins(
									homePage.get(0).get(j)
											.getNumberofcheckins());
					homePage.get(0).get(i)
							.setRate(homePage.get(0).get(j).getRate());
					homePage.get(0).get(i)
							.setTip(homePage.get(0).get(j).getTip());
					homePage.get(0).get(j).setLatitude(temp.getLatitude());
					homePage.get(0).get(j).setLongitude(temp.getLongitude());
					homePage.get(0).get(j).setName(temp.getName());
					homePage.get(0).get(j)
							.setNumberofcheckins(temp.getNumberofcheckins());
					homePage.get(0).get(j).setRate(temp.getRate());
					homePage.get(0).get(j).setTip(temp.getTip());
				}
			}
		}
		for (int i = 0; i < homePage.get(1).size(); i++) {
			for (int j = 1; j < homePage.get(1).size(); j++) {
				if (homePage.get(1).get(j).getNumberofcheckins() < homePage
						.get(1).get(i).getNumberofcheckins()) {
					temp.setLatitude(homePage.get(1).get(i).getLatitude());
					temp.setLongitude(homePage.get(1).get(i).getLongitude());
					temp.setName(homePage.get(1).get(i).getName());
					temp.setNumberofcheckins(homePage.get(1).get(i)
							.getNumberofcheckins());
					temp.setRate(homePage.get(1).get(i).getRate());
					temp.setTip(homePage.get(1).get(i).getTip());
					homePage.get(1).get(i)
							.setLatitude(homePage.get(1).get(j).getLatitude());
					homePage.get(1)
							.get(i)
							.setLongitude(homePage.get(1).get(j).getLongitude());
					homePage.get(1).get(i)
							.setName(homePage.get(1).get(j).getName());
					homePage.get(1)
							.get(i)
							.setNumberofcheckins(
									homePage.get(2).get(j)
											.getNumberofcheckins());
					homePage.get(1).get(i)
							.setRate(homePage.get(1).get(j).getRate());
					homePage.get(1).get(i)
							.setTip(homePage.get(1).get(j).getTip());
					homePage.get(1).get(j).setLatitude(temp.getLatitude());
					homePage.get(1).get(j).setLongitude(temp.getLongitude());
					homePage.get(1).get(j).setName(temp.getName());
					homePage.get(1).get(j)
							.setNumberofcheckins(temp.getNumberofcheckins());
					homePage.get(1).get(j).setRate(temp.getRate());
					homePage.get(1).get(j).setTip(temp.getTip());
				}
			}
		}
		for (int i = 0; i < homePage.get(2).size(); i++) {
			for (int j = 1; j < homePage.get(2).size(); j++) {
				if (homePage.get(2).get(j).getNumberofcheckins() < homePage
						.get(2).get(i).getNumberofcheckins()) {
					temp.setLatitude(homePage.get(2).get(i).getLatitude());
					temp.setLongitude(homePage.get(2).get(i).getLongitude());
					temp.setName(homePage.get(2).get(i).getName());
					temp.setNumberofcheckins(homePage.get(2).get(i)
							.getNumberofcheckins());
					temp.setRate(homePage.get(2).get(i).getRate());
					temp.setTip(homePage.get(2).get(i).getTip());
					homePage.get(2).get(i)
							.setLatitude(homePage.get(2).get(j).getLatitude());
					homePage.get(2)
							.get(i)
							.setLongitude(homePage.get(2).get(j).getLongitude());
					homePage.get(2).get(i)
							.setName(homePage.get(2).get(j).getName());
					homePage.get(2)
							.get(i)
							.setNumberofcheckins(
									homePage.get(2).get(j)
											.getNumberofcheckins());
					homePage.get(2).get(i)
							.setRate(homePage.get(2).get(j).getRate());
					homePage.get(2).get(i)
							.setTip(homePage.get(2).get(j).getTip());
					homePage.get(2).get(j).setLatitude(temp.getLatitude());
					homePage.get(2).get(j).setLongitude(temp.getLongitude());
					homePage.get(2).get(j).setName(temp.getName());
					homePage.get(2).get(j)
							.setNumberofcheckins(temp.getNumberofcheckins());
					homePage.get(2).get(j).setRate(temp.getRate());
					homePage.get(2).get(j).setTip(temp.getTip());
				}
			}
		}
		for (int i = 0; i < homePage.get(3).size(); i++) {
			for (int j = 1; j < homePage.get(3).size(); j++) {
				if (homePage.get(3).get(j).getNumberofcheckins() < homePage
						.get(3).get(i).getNumberofcheckins()) {
					temp.setLatitude(homePage.get(3).get(i).getLatitude());
					temp.setLongitude(homePage.get(3).get(i).getLongitude());
					temp.setName(homePage.get(3).get(i).getName());
					temp.setNumberofcheckins(homePage.get(3).get(i)
							.getNumberofcheckins());
					temp.setRate(homePage.get(3).get(i).getRate());
					temp.setTip(homePage.get(3).get(i).getTip());
					homePage.get(3).get(i)
							.setLatitude(homePage.get(3).get(j).getLatitude());
					homePage.get(3)
							.get(i)
							.setLongitude(homePage.get(3).get(j).getLongitude());
					homePage.get(3).get(i)
							.setName(homePage.get(3).get(j).getName());
					homePage.get(3)
							.get(i)
							.setNumberofcheckins(
									homePage.get(3).get(j)
											.getNumberofcheckins());
					homePage.get(3).get(i)
							.setRate(homePage.get(3).get(j).getRate());
					homePage.get(3).get(i)
							.setTip(homePage.get(3).get(j).getTip());
					homePage.get(3).get(j).setLatitude(temp.getLatitude());
					homePage.get(3).get(j).setLongitude(temp.getLongitude());
					homePage.get(3).get(j).setName(temp.getName());
					homePage.get(3).get(j)
							.setNumberofcheckins(temp.getNumberofcheckins());
					homePage.get(3).get(j).setRate(temp.getRate());
					homePage.get(3).get(j).setTip(temp.getTip());
				}
			}
		}
		return homePage;
	}

	public static ArrayList<ArrayList<PlaceModel>> sortHomePageByRate(int id) {
		ArrayList<ArrayList<PlaceModel>> homePage = new ArrayList<ArrayList<PlaceModel>>(
				showHomePage(id));
		PlaceModel temp = new PlaceModel();
		for (int i = 0; i < homePage.get(0).size(); i++) {
			for (int j = 1; j < homePage.get(0).size(); j++) {
				if (homePage.get(0).get(j).getRate() < homePage.get(0).get(i)
						.getRate()) {
					temp.setLatitude(homePage.get(0).get(i).getLatitude());
					temp.setLongitude(homePage.get(0).get(i).getLongitude());
					temp.setName(homePage.get(0).get(i).getName());
					temp.setNumberofcheckins(homePage.get(0).get(i)
							.getNumberofcheckins());
					temp.setRate(homePage.get(0).get(i).getRate());
					temp.setTip(homePage.get(0).get(i).getTip());
					homePage.get(0).get(i)
							.setLatitude(homePage.get(0).get(j).getLatitude());
					homePage.get(0)
							.get(i)
							.setLongitude(homePage.get(0).get(j).getLongitude());
					homePage.get(0).get(i)
							.setName(homePage.get(0).get(j).getName());
					homePage.get(0)
							.get(i)
							.setNumberofcheckins(
									homePage.get(0).get(j)
											.getNumberofcheckins());
					homePage.get(0).get(i)
							.setRate(homePage.get(0).get(j).getRate());
					homePage.get(0).get(i)
							.setTip(homePage.get(0).get(j).getTip());
					homePage.get(0).get(j).setLatitude(temp.getLatitude());
					homePage.get(0).get(j).setLongitude(temp.getLongitude());
					homePage.get(0).get(j).setName(temp.getName());
					homePage.get(0).get(j)
							.setNumberofcheckins(temp.getNumberofcheckins());
					homePage.get(0).get(j).setRate(temp.getRate());
					homePage.get(0).get(j).setTip(temp.getTip());
				}
			}
		}
		for (int i = 0; i < homePage.get(1).size(); i++) {
			for (int j = 1; j < homePage.get(1).size(); j++) {
				if (homePage.get(1).get(j).getRate() < homePage.get(1).get(i)
						.getRate()) {
					temp.setLatitude(homePage.get(1).get(i).getLatitude());
					temp.setLongitude(homePage.get(1).get(i).getLongitude());
					temp.setName(homePage.get(1).get(i).getName());
					temp.setNumberofcheckins(homePage.get(1).get(i)
							.getNumberofcheckins());
					temp.setRate(homePage.get(1).get(i).getRate());
					temp.setTip(homePage.get(1).get(i).getTip());
					homePage.get(1).get(i)
							.setLatitude(homePage.get(1).get(j).getLatitude());
					homePage.get(1)
							.get(i)
							.setLongitude(homePage.get(1).get(j).getLongitude());
					homePage.get(1).get(i)
							.setName(homePage.get(1).get(j).getName());
					homePage.get(1)
							.get(i)
							.setNumberofcheckins(
									homePage.get(2).get(j)
											.getNumberofcheckins());
					homePage.get(1).get(i)
							.setRate(homePage.get(1).get(j).getRate());
					homePage.get(1).get(i)
							.setTip(homePage.get(1).get(j).getTip());
					homePage.get(1).get(j).setLatitude(temp.getLatitude());
					homePage.get(1).get(j).setLongitude(temp.getLongitude());
					homePage.get(1).get(j).setName(temp.getName());
					homePage.get(1).get(j)
							.setNumberofcheckins(temp.getNumberofcheckins());
					homePage.get(1).get(j).setRate(temp.getRate());
					homePage.get(1).get(j).setTip(temp.getTip());
				}
			}
		}
		for (int i = 0; i < homePage.get(2).size(); i++) {
			for (int j = 1; j < homePage.get(2).size(); j++) {
				if (homePage.get(2).get(j).getRate() < homePage.get(2).get(i)
						.getRate()) {
					temp.setLatitude(homePage.get(2).get(i).getLatitude());
					temp.setLongitude(homePage.get(2).get(i).getLongitude());
					temp.setName(homePage.get(2).get(i).getName());
					temp.setNumberofcheckins(homePage.get(2).get(i)
							.getNumberofcheckins());
					temp.setRate(homePage.get(2).get(i).getRate());
					temp.setTip(homePage.get(2).get(i).getTip());
					homePage.get(2).get(i)
							.setLatitude(homePage.get(2).get(j).getLatitude());
					homePage.get(2)
							.get(i)
							.setLongitude(homePage.get(2).get(j).getLongitude());
					homePage.get(2).get(i)
							.setName(homePage.get(2).get(j).getName());
					homePage.get(2)
							.get(i)
							.setNumberofcheckins(
									homePage.get(2).get(j)
											.getNumberofcheckins());
					homePage.get(2).get(i)
							.setRate(homePage.get(2).get(j).getRate());
					homePage.get(2).get(i)
							.setTip(homePage.get(2).get(j).getTip());
					homePage.get(2).get(j).setLatitude(temp.getLatitude());
					homePage.get(2).get(j).setLongitude(temp.getLongitude());
					homePage.get(2).get(j).setName(temp.getName());
					homePage.get(2).get(j)
							.setNumberofcheckins(temp.getNumberofcheckins());
					homePage.get(2).get(j).setRate(temp.getRate());
					homePage.get(2).get(j).setTip(temp.getTip());
				}
			}
		}
		for (int i = 0; i < homePage.get(3).size(); i++) {
			for (int j = 1; j < homePage.get(3).size(); j++) {
				if (homePage.get(3).get(j).getRate() < homePage.get(3).get(i)
						.getRate()) {
					temp.setLatitude(homePage.get(3).get(i).getLatitude());
					temp.setLongitude(homePage.get(3).get(i).getLongitude());
					temp.setName(homePage.get(3).get(i).getName());
					temp.setNumberofcheckins(homePage.get(3).get(i)
							.getNumberofcheckins());
					temp.setRate(homePage.get(3).get(i).getRate());
					temp.setTip(homePage.get(3).get(i).getTip());
					homePage.get(3).get(i)
							.setLatitude(homePage.get(3).get(j).getLatitude());
					homePage.get(3)
							.get(i)
							.setLongitude(homePage.get(3).get(j).getLongitude());
					homePage.get(3).get(i)
							.setName(homePage.get(3).get(j).getName());
					homePage.get(3)
							.get(i)
							.setNumberofcheckins(
									homePage.get(3).get(j)
											.getNumberofcheckins());
					homePage.get(3).get(i)
							.setRate(homePage.get(3).get(j).getRate());
					homePage.get(3).get(i)
							.setTip(homePage.get(3).get(j).getTip());
					homePage.get(3).get(j).setLatitude(temp.getLatitude());
					homePage.get(3).get(j).setLongitude(temp.getLongitude());
					homePage.get(3).get(j).setName(temp.getName());
					homePage.get(3).get(j)
							.setNumberofcheckins(temp.getNumberofcheckins());
					homePage.get(3).get(j).setRate(temp.getRate());
					homePage.get(3).get(j).setTip(temp.getTip());
				}
			}
		}
		return homePage;
	}

	public static ArrayList<Likes> activitylistlikes(int id) {
		ArrayList<Likes> likes = new ArrayList<Likes>();
		Likes l = new Likes();
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from likes where `myid` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			ArrayList<UserModel> following = new ArrayList<>();
			while (rs.next()) {
				l.setLikeID(rs.getInt("id"));
				l.setCheckinID(rs.getInt("checkinid"));
				likes.add(l);
			}
			return likes;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<comments> activitylistcomments(int id) {
		ArrayList<comments> comments = new ArrayList<comments>();
		comments c = new comments();
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from comments where `myid` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				c.setCommentID(rs.getInt("id"));
				c.setCheckinID(rs.getInt("checkinid"));
				comments.add(c);
			}
			return comments;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean deletelike(int likeid) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "DELETE FROM  `likes` WHERE `id` =?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, likeid);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deletecomment(int commentid) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "DELETE FROM  `comments` WHERE `id` =?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, commentid);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private static void insertintocheckins(int minid, int id1) {
		// TODO Auto-generated method stub
		try {
			Connection conn = DBConnection.getActiveConnection();
			// String sql =
			// "insert into checkin(place_id, user_id)"+" values (?,?)";
			String sql = "INSERT INTO `checkin`(`place_id`, `user_id`) VALUES ("
					+ minid + "," + id1 + ")";
			java.sql.Statement stmt = conn.createStatement();
			//

			stmt.executeUpdate(sql);

			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void updatenumofcheckins(int minid) throws SQLException {
		// TODO Auto-generated method stub
		int noofcheckins = 0;
		// try {
		// Connection conn = DBConnection.getActiveConnection();
		// String sql = "Select * from places  WHERE  id="+minid+"";
		// PreparedStatement stmt;
		// stmt = conn.prepareStatement(sql);
		//
		// ResultSet rs = stmt.executeQuery();
		// ArrayList<UserModel> following = new ArrayList<>();
		// int i=0;
		// while(rs.next())
		// {
		// noofcheckins = rs.getInt("numofcheckins");
		//
		// }
		// //System.out.println(noofcheckins);
		//
		// }
		// catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		noofcheckins++;
		try {

			Connection conn = DBConnection.getActiveConnection();

			// String sql = "UPDATE places SET numofcheckins=3  WHERE  id=2";
			String sql = "UPDATE `places` SET `numofcheckins`=`numofcheckins`+1 WHERE id="
					+ minid + "";

			java.sql.Statement stmt2 = conn.createStatement();
			//

			stmt2.executeUpdate(sql);

			stmt2.close();

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int Like(int idcheckin, int myid2) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			// String sql =
			// "insert into checkin(place_id, user_id)"+" values (?,?)";
			String sql = "INSERT INTO `likes`(`checkinid`, `myid`) VALUES ("
					+ idcheckin + "," + myid2 + ")";
			java.sql.Statement stmt = conn.createStatement();
			//

			stmt.executeUpdate(sql);

			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public static int comment(int myid, int checkinid, String comment) {
		// TODO Auto-generated method stub

		try {
			Connection conn = DBConnection.getActiveConnection();

			String sql = "INSERT INTO `comments`(`checkinid`, `myid`,`comment`) VALUES ("
					+ checkinid + "," + myid + ",'" + comment + "')";

			java.sql.Statement stmt = conn.createStatement();
			//

			stmt.executeUpdate(sql);

			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
	public static ArrayList<String> getLikesNotification(int user_id){
		// TODO Auto-generated method stub
				try {
					Connection conn = DBConnection.getActiveConnection();
					String sql = "Select id,place_id from checkin where `user_id` = ?";
					PreparedStatement stmt;
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, user_id);
					ResultSet rs = stmt.executeQuery();
					ArrayList<Integer> userCheckinIds = new ArrayList<>();
					ArrayList<Integer> placesIds = new ArrayList<Integer>();
					while (rs.next()) {
						userCheckinIds.add(rs.getInt("id"));
						placesIds.add(rs.getInt("place_id"));
					}
					ArrayList<String> notifications = new ArrayList<String>();
					PlaceModel obj = new PlaceModel();
					UserModel obj1 =  new UserModel();
					for(int i=0;i<userCheckinIds.size();++i){
						PlaceModel place = obj.getPlace(placesIds.get(i));
						ArrayList<Integer> users = getUsersIDsForLikes(userCheckinIds.get(i));
						for(int j = 0;j<users.size();++j){
							UserModel user = obj1.getUserInfo(users.get(j));
							String temp = user.getName().concat(" likes your checkin "+place.getName());
							notifications.add(temp);
						}
						ArrayList<Integer> usersComments = getUsersIDsForComments(userCheckinIds.get(i));
						for(int k=0;k<usersComments.size();++k){
							UserModel user = obj1.getUserInfo(users.get(k));
							
							ArrayList<String> comments = getCommentsForCheckins(userCheckinIds.get(i), usersComments.get(k));
							for(int r=0;r<comments.size();++r){
								String temp = user.getName().concat(" commented on your checkin "+place.getName()+" ");
								temp+='"';
								temp+=comments.get(r);
								temp+='"';
								notifications.add(temp);
							}
							
						}
					}
					return notifications;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
	}
	public static ArrayList<Integer> getUsersIDsForLikes(int checkin_id){
		// TODO Auto-generated method stub
				try {
					Connection conn = DBConnection.getActiveConnection();
					String sql = "Select myid from likes where `checkinid` = ?";
					PreparedStatement stmt;
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, checkin_id);
					ResultSet rs = stmt.executeQuery();
					ArrayList<Integer> userIds = new ArrayList<>();
					while (rs.next()) {

						userIds.add(rs.getInt("myid"));
					}
					return userIds;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
	}
	public static ArrayList<Integer> getUsersIDsForComments(int checkin_id){
		// TODO Auto-generated method stub
				try {
					Connection conn = DBConnection.getActiveConnection();
					String sql = "Select myid from comments where `checkinid` = ?";
					PreparedStatement stmt;
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, checkin_id);
					ResultSet rs = stmt.executeQuery();
					ArrayList<Integer> userIds = new ArrayList<>();
					while (rs.next()) {

						userIds.add(rs.getInt("myid"));
					}
					return userIds;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
	}
	public static ArrayList<String> getCommentsForCheckins(int checkin_id,int my_id){
		// TODO Auto-generated method stub
				try {
					Connection conn = DBConnection.getActiveConnection();
					String sql = "Select comment from comments where `checkinid` = ? and myid = ?";
					PreparedStatement stmt;
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, checkin_id);
					stmt.setInt(2,my_id);
					ResultSet rs = stmt.executeQuery();
					ArrayList<String> comments= new ArrayList<>();
					while (rs.next()) {

						comments.add(rs.getString("comment") );
					}
					return comments;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
	}
	public static String getPlaceName(int place_id){
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Select name from places where `id` = ?";
		PreparedStatement stmt;
		String placeName = "";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, place_id);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				placeName = rs.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return placeName;
	}
	public static int getPlaceID(int user_id,int checkin_id){
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Select place_id from checkin where `user_id` = ? and id = ?";
		PreparedStatement stmt;
		int place_id = -1;
		try {

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setInt(2,checkin_id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				place_id = rs.getInt("place_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return place_id;
	}
	public static String showPost(int user_id,int checkin_id){
		
		// TODO Auto-generated method stub
		
				
				UserModel obj1 = new UserModel();
				UserModel currentUser = obj1.getUserInfo(user_id);
				int placeID = getPlaceID(user_id, checkin_id);
				String placeName = getPlaceName(placeID);
				String temp = currentUser.getName()+" was in "+placeName+" $";
				ArrayList<Integer> users = getUsersIDsForLikes(checkin_id);
				temp += " number of likes: = "; 
				temp+=Integer.toString(users.size()); 
				temp+="  Likers: " ;
				for(int j = 0;j<users.size();++j){
					UserModel user = obj1.getUserInfo(users.get(j));
					 temp+= user.getName().concat("  ");
				}
				temp+="$";
				ArrayList<Integer> usersComments = getUsersIDsForComments(checkin_id);
				for(int k=0;k<usersComments.size();++k){
					UserModel user = obj1.getUserInfo(users.get(k));
					
					ArrayList<String> comments = getCommentsForCheckins(checkin_id, usersComments.get(k));
					for(int r=0;r<comments.size();++r){
						String temp1 = user.getName().concat(": ");
						temp+=temp1;
						temp+='"';
						temp+=comments.get(r);
						temp+='"';
						temp+="   ";
					}
					
				}
			return temp;
	}
}
