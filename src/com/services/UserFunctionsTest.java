package com.services;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.models.UserModel;

public class UserFunctionsTest {
  UserModel user = new UserModel();
  @BeforeTest
  public void set(){
	  user.setName("ahmed");
	  user.setPass("123");
  }
  @Test
  public void testGetUser() {
	 
	  boolean f =user.getUser("ahmed","1234");
	  Assert.assertEquals(f ,false);
  }
  @Test
  public void testSearchUser() {
	 
	  boolean found =user.searchUser("ahmed");
	  Assert.assertEquals(found ,true);
  }
  @Test(dependsOnMethods = { "testSearchUser" })
  public void testGetPass() {
	  String str = user.getPass();
	  Assert.assertEquals(str ,"123");
  }
  
}
