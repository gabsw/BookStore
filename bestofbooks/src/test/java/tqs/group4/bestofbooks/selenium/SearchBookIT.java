package tqs.group4.bestofbooks.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tqs.group4.bestofbooks.BestofbooksApplication;


import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BestofbooksApplication.class)
public class SearchBookIT {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;

  @Before
  public void setUp() {
	ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless", "--disable-gpu");
    driver = new ChromeDriver(options);
    js = (JavascriptExecutor) driver;
    vars = new HashMap<>();
    driver.get("http://localhost:8080/");
  }
  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void searchBookTitle(){
    driver.findElement(By.id("findBook")).click();
    driver.findElement(By.id("findBook")).sendKeys("Book1");
    driver.findElement(By.id("book_title")).click();
    driver.findElement(By.id("search_book")).click();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement selectPollutant = wait.until(ExpectedConditions.elementToBeClickable(By.id("book_title_1")));
    selectPollutant.click();
    assertThat(driver.findElement(By.id("book_title_1")).getText(), is("Book1"));
  }


  @Test
  public void searchBookCategory() {
    driver.findElement(By.id("findBook")).click();
    driver.findElement(By.id("findBook")).sendKeys("SciFi");
    driver.findElement(By.id("book_category")).click();
    driver.findElement(By.id("search_book")).click();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement selectPollutant = wait.until(ExpectedConditions.elementToBeClickable(By.id("book_category_1")));
    selectPollutant.click();
    assertThat(driver.findElement(By.id("book_category_1")).getText(), containsString("SciFi"));
  }

  @Test
  public void searchBookAuthor(){
    driver.findElement(By.id("findBook")).click();
    driver.findElement(By.id("findBook")).sendKeys("someauthor");
    driver.findElement(By.id("book_author")).click();
    driver.findElement(By.id("search_book")).click();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement selectPollutant = wait.until(ExpectedConditions.elementToBeClickable(By.id("book_author_1")));
    selectPollutant.click();
    assertThat(driver.findElement(By.id("book_author_1")).getText(), containsString("someauthor"));
  }


  @Test
  public void searchInexistingBook() {
    driver.findElement(By.id("findBook")).click();
    driver.findElement(By.id("findBook")).sendKeys("nothing");
    driver.findElement(By.id("search_book")).click();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement selectPollutant = wait.until(ExpectedConditions.elementToBeClickable(By.id("undefined")));
    selectPollutant.click();
    assertThat(driver.findElement(By.id("undefined")).getText(), is("Nothing Found !"));
  }
}
