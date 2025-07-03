package com.fsa.te1.movok.admin.tests;

import com.fsa.te1.movok.admin.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;

import java.util.Arrays;

public class MovieTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MoviesPage moviesPage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/admin");

        loginPage = new LoginPage(driver);
        loginPage.login("admin@gmail.com", "Admin123!");

        dashboardPage = new DashboardPage(driver);
        moviesPage = new MoviesPage(driver);

        Assert.assertTrue(dashboardPage.isAtDashboard(), "Login failed - Not at dashboard.");
    }

//    @Test(priority = 1)
//    public void navigateToMoviesMenu() {
//        dashboardPage.openMovieMenu();
//        Assert.assertTrue(moviesPage.isMoviesListDisplayed(), "Movies list should be displayed");
//    }

    @Test(priority = 2)
    public void createNewMovie() throws InterruptedException {
        moviesPage.clickAddMovie();
        moviesPage.fillMovieForm(
                "Test Movie",                       // name
                "120",                              // duration
                "04-07-2025",                       // premiereDate
                "Vietnamese",                       // language
                "PG-13 - Parents Strongly Cautioned", // ageRating
                "Phim hành động gay cấn",          // description
                "https://i.ytimg.com/vi/xwDh0aZTs9I/maxresdefault.jpg", // imageUrl
                "http://youtube.com/watch?v=xwDh0aZTs9I",  // trailerUrl
                "Coming Soon",                      // status
                "Dean DeBlois",                     // director
                Arrays.asList("Thanh Long V2"), // actors
                Arrays.asList("Action", "Drama")      // genres
        );

        moviesPage.submitMovieForm();

        boolean found = false;
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            if (moviesPage.isMoviePresent("Test Movie")) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "❌ Movie should be created but not found in table.");

    }

    @Test(priority = 3)
    public void testAddMovie() throws InterruptedException {
        dashboardPage.openMovieMenu();
        moviesPage.clickAddMovie();
        moviesPage.fillMovieForm(
                "Interstellar",
                "169",
                "2025-07-01",
                "English",
                "PG-13 - Parents Strongly Cautioned",
                "A journey beyond space and time.",
                "https://i.ytimg.com/vi/xwDh0aZTs9I/maxresdefault.jpg",
                "http://youtube.com/watch?v=xwDh0aZTs9I",
                "Coming Soon",
                "Christopher Nolan",
                Arrays.asList("Matthew McConaughey", "Anne Hathaway"),
                Arrays.asList("Sci-fi", "Adventure")
        );
        moviesPage.saveMovie();
        Assert.assertTrue(moviesPage.isMoviePresent("Interstellar"), "Movie should be created");
    }


    @Test(priority = 4)
    public void testEditMovie() throws InterruptedException {
        moviesPage.clickEditMovie("Interstellar");
        moviesPage.fillMovieForm(
                "Interstellar",                       // name
                "120",                              // duration
                "2025-12-01",                       // premiereDate
                "Vietnamese",                       // language
                "", // ageRating
                "Phim hành động gay cấn",          // description
                "https://i.ytimg.com/vi/xwDh0aZTs9I/maxresdefault.jpg", // imageUrl
                "http://youtube.com/watch?v=xwDh0aZTs9I",  // trailerUrl
                "Coming Soon",                      // status
                "Nguyễn Văn A",                     // director
                Arrays.asList("Diễn viên A", "Diễn viên B"), // actors
                Arrays.asList("Hành Động", "Kịch Tính")      // genres
        );

        moviesPage.saveMovie();
        Assert.assertTrue(moviesPage.isMoviePresent("Interstellar"), "Movie should be edited");
    }

    @Test(priority = 5)
    public void testDeleteMovie() {
        moviesPage.clickDeleteMovie("Test Movie");

        // Có thể cần wait table refresh hoặc thực hiện search lại
        // moviesPage.clickSearch(); // nếu có nút search

        Assert.assertFalse(moviesPage.isMoviePresent("Test Movie"), "❌ Movie should be deleted from current tab");
    }

    // chưa undelete đc
    @Test(priority = 6)
    public void testUndeleteMovie() {
        moviesPage.openDeletedMoviesTab();
        moviesPage.restoreDeletedMovie("Test Movie");
        Assert.assertTrue(moviesPage.isMoviePresent("Test Movie"), "Movie should be restored");
    }


//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

}
