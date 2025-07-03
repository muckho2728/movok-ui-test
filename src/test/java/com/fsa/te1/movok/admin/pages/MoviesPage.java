package com.fsa.te1.movok.admin.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class MoviesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MoviesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private By openAddMoviePopupButton = By.xpath("//span[normalize-space()='Add New Movie']");
    private By movieNameInput = By.xpath("//input[@id='movieName']");
    private By durationInput = By.xpath("//input[@id='duration']");
    private By premiereDateInput = By.xpath("//input[@id='premiereDate']");
    private By languageInput = By.xpath("//input[@id='language']");
    private By ageRatingSelect = By.xpath("//span[@title='PG-13 - Parents Strongly Cautioned']");
    private By descriptionInput = By.xpath("//textarea[@id='description']");
    private By movieStatusTag = By.xpath("//span[contains(@class, 'ant-tag')]");
    private By saveButton = By.xpath("//button[normalize-space()='Add Movie']");
    private By moviesTableRows = By.xpath("//table//tbody/tr");
    private By deletedMoviesTab = By.xpath("//button[contains(text(), 'Deleted Movies')]");

    // Open Add Movie popup
    public void clickAddMovie() {
        wait.until(ExpectedConditions.elementToBeClickable(openAddMoviePopupButton)).click();
    }

    // Fill form
    public void fillMovieForm(String name, String duration, String date, String language,
                              String ageRating, String description, String imageUrl,
                              String trailerUrl, String status, String director,
                              List<String> actors, List<String> genres) throws InterruptedException {

        // Tên phim
        driver.findElement(By.id("movieName")).sendKeys(name);

        // Thời lượng
        WebElement durationInput = driver.findElement(By.id("duration"));
        durationInput.clear();
        durationInput.sendKeys(duration);

        // Ngày khởi chiếu
        driver.findElement(By.cssSelector("input[type='date']")).sendKeys(date);

        // Ngôn ngữ
        WebElement langInput = driver.findElement(By.id("language"));
        langInput.clear();
        langInput.sendKeys(language);

        // Age Rating
        WebElement ageDropdown = driver.findElement(By.cssSelector("span[title='" + ageRating + "']"));
        ageDropdown.click();
        WebElement ageOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@title='" + ageRating + "']")));
        ageOption.click();

        // Mô tả
        driver.findElement(By.xpath("//textarea[@placeholder='Enter movie description']")).sendKeys(description);

        // Image URL
        driver.findElement(By.xpath("//input[@placeholder='https://example.com/movie-poster.jpg']")).sendKeys(imageUrl);

        // Trailer URL
        driver.findElement(By.xpath("//input[@placeholder='https://youtube.com/watch?v=...']")).sendKeys(trailerUrl);

        // Movie Status
        WebElement statusDropdown = driver.findElement(By.cssSelector(
                "span[class='ant-select-selection-item'] span[class='flex items-center gap-2']"));
        statusDropdown.click();
        Thread.sleep(300);
        WebElement statusOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'ant-select-item-option-content') and normalize-space()='" + status + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", statusOption);

        // Đạo diễn
        WebElement directorDropdown = driver.findElement(By.id("directorId"));
        directorDropdown.click();
        Thread.sleep(300);
        WebElement directorOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='ant-select-item-option-content' and text()='" + director + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", directorOption);
        directorOption.click();

        // Diễn viên
        if (actors != null && !actors.isEmpty()) {
            for (String actor : actors) {
                if (actor == null || actor.trim().isEmpty()) continue;

                // Mở dropdown
                WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@id='movieActors']/ancestor::div[contains(@class,'ant-select')]")));
                dropdown.click();

                // Gửi tên diễn viên
                WebElement actorInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[text()='Actors']/following::input[1]")));
                actorInput.sendKeys(actor);

                // Chọn actor
                WebElement actorOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='ant-select-item-option-content' and text()='" + actor + "']")));
                actorOption.click();
            }
        }

        // Genres (Thể loại phim)
        if (genres != null && !genres.isEmpty()) {
            WebElement genreBox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[text()='Genres']/following::div[contains(@class,'ant-select')]")
            ));
            genreBox.click(); // mở dropdown

            for (String genre : genres) {
                if (genre == null || genre.trim().isEmpty()) continue;

                // Gửi genre vào input
                WebElement genreInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//label[text()='Genres']/following::input")
                ));
                genreInput.sendKeys(genre);
                Thread.sleep(300); // chờ dropdown hiển thị option

                // Chọn option đúng với text
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='ant-select-item-option-content' and text()='" + genre + "']")
                ));
                option.click();
            }

            // Nhấn ra ngoài để đóng dropdown nếu cần
            genreBox.click();
        }


    }



    // Submit
    public void submitMovieForm() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void saveMovie() {
        submitMovieForm();
    }

    // Check movie is in the list
    // Check movie is present and not inactive
    public boolean isMoviePresent(String title) {
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(moviesTableRows));
        return rows.stream().anyMatch(row -> {
            String movieTitle = row.findElement(By.xpath(".//td[1]")).getText();
            String status = row.findElement(By.xpath(".//td[3]")).getText();
            return movieTitle.equalsIgnoreCase(title) && !status.equalsIgnoreCase("Inactive");
        });
    }



    // Edit a movie
    public void clickEditMovie(String title) {
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(moviesTableRows));
        for (WebElement row : rows) {
            WebElement titleCell = row.findElement(By.xpath(".//td[2]"));
            if (titleCell.getText().contains(title)) {
                row.findElement(By.xpath(".//button[contains(@class,'edit')]")).click();
                break;
            }
        }
    }

    // Delete movie
    public void clickDeleteMovie(String title) {
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(moviesTableRows));

        for (WebElement row : rows) {
            if (row.getText().contains(title)) {
                WebElement deleteButton = row.findElement(By.cssSelector("button.ant-btn-dangerous")); // hoặc button có icon thùng rác
                deleteButton.click();

                // Xác nhận pop-up nếu có (tuỳ giao diện)
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Delete Movie']"))).click();
                return;
            }
        }
        throw new NoSuchElementException("❌ Không tìm thấy movie để xoá: " + title);
    }


    // Open deleted movies
    public void openDeletedMoviesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(deletedMoviesTab)).click();
    }

    // Restore deleted movie
    public void restoreDeletedMovie(String title) {
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(moviesTableRows));
        for (WebElement row : rows) {
            WebElement titleCell = row.findElement(By.xpath(".//td[2]"));
            if (titleCell.getText().contains(title)) {
                row.findElement(By.xpath(".//button[contains(@class,'restore')]")).click();
                break;
            }
        }
    }

    // Check if movie list displayed
    public boolean isMoviesListDisplayed() {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(moviesTableRows)).isDisplayed();
    }
}
