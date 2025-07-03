package com.fsa.te1.movok.admin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailField = By.cssSelector("input[placeholder='Email Address']");
    private By passwordField = By.cssSelector("input[placeholder='Password']");
    private By loginButton = By.xpath("//button[contains(text(), 'Sign In')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String email, String password) {
        if (!driver.getCurrentUrl().contains("/login")) {
            driver.get("http://localhost:5173/login"); // đảm bảo đúng đường dẫn login
        }

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        button.click();

        // Đợi redirect sang trang /admin (VD kiểm tra URL)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/admin"));
    }

}
