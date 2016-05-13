package com.services;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.models.UserModel;

public class UserFunctionsTest {
  UserModel user = new UserModel();
  @Test
  public void testGetUser() {
	  user.setName("ahmed");
	  user.setPass("123");
	  boolean f =user.getUser("ahmed","1234");
	  Assert.assertEquals(f ,false);
  }
  @Test
  public void testSearchUser() {
	  user.setName("ahmed");
	  user.setPass("123");
	  boolean found =user.searchUser("ahmed");
	  Assert.assertEquals(found ,true);
  }
  @Test
  public void testGetPass() {
	  user.setName("ahmed");
	  user.setPass("123");
	  String str = user.getPass();
	  Assert.assertEquals(str ,"123");
  }
  
}
