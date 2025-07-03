package com.fsa.te1.movok.admin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.TimeoutException;


public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Ví dụ: tiêu đề của dashboard hoặc 1 menu cụ thể
    //private By locator = By.xpath("//div[contains(@class,'ant-space-item')]//span[contains(text(),'Administrator')]");
    private By movieMenu = By.xpath("//span[contains(text(),'Movie')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAtDashboard() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement adminSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Administrator']")));
            return adminSpan.isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("URL after login: " + driver.getCurrentUrl());
            return false;
        }
    }


    public void openMovieMenu() {
        try {
            WebElement movieMenuElement = wait.until(ExpectedConditions.elementToBeClickable(movieMenu));
            movieMenuElement.click();
        } catch (TimeoutException e) {
            System.out.println("❌ Không tìm thấy menu 'Movies'. URL hiện tại: " + driver.getCurrentUrl());
            throw e;
        }
    }
}
