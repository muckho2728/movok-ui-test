package com.fsa.te1.movok.admin.tests;

import com.fsa.te1.movok.admin.pages.DashboardPage;
import com.fsa.te1.movok.admin.pages.LoginPage;
import com.fsa.te1.movok.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DashboardTest extends BaseTest {

    @Test
    public void testNavigateToDashboardAndOpenMovieMenu() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin@gmail.com", "Admin123!");

        // KHÔNG cần gọi lại driver.get("/admin")

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isAtDashboard(), "Không ở trang dashboard");

        dashboardPage.openMovieMenu();
    }

}
