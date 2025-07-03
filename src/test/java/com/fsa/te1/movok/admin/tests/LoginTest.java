package com.fsa.te1.movok.admin.tests;

import com.fsa.te1.movok.base.BaseTest;
import com.fsa.te1.movok.admin.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // LoginTest.java
    @Test
    public void testValidAdminLogin() {

        driver.get("http://localhost:5173");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin@gmail.com", "Admin123!");

        Assert.assertTrue(driver.getCurrentUrl().contains("/admin")); // hoặc dashboard
    }

}
