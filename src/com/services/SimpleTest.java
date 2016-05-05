package com.services;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class SimpleTest {
	Services  service = new Services();
  @Test
  public void f() {
	  service.follow(1, 2);
	  String str = "TestNG is working fine";
      assertEquals("TestNG is working fine", str);
  }
}
