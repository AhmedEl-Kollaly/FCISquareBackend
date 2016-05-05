package com.services;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class UserEntityTest {
  
  Services obj = new Services();
  @Test
  public void f() {
	  String str = "TestNG is working fine";
      assertEquals("TestNG is working fine", str);
  }
  
}
