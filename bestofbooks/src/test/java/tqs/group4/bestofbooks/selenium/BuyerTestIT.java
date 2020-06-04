package tqs.group4.bestofbooks.selenium;

import org.junit.After;
import org.junit.Assert;
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
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BestofbooksApplication.class)
public class BuyerTestIT {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;


  @Before
  public void setUp() throws InterruptedException {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless", "--disable-gpu");
    driver = new ChromeDriver(options);
    js = (JavascriptExecutor) driver;
    vars = new HashMap<>();
    driver.get("http://localhost:8080/login.html");
    logIN();

  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void bookDetails() {
    findBook1();
    driver.findElement(By.linkText("More Details")).click();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement find_title = wait.until(ExpectedConditions.elementToBeClickable(By.id("book_title")));
    find_title.click();
    assertThat(find_title.getText(), containsString("Book1")); // FIX THIS TEST
  }


  @Test
  public void buyWithoutAddress() {
    findBook1();
    driver.findElement(By.linkText("Add To Cart")).click();
    driver.findElement(By.cssSelector("#goToCart > .fa")).click();
    driver.findElement(By.cssSelector(".btn-success")).click();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 30);

      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      Assert.assertTrue(alert.getText().contains("Address cannot be null or whitespace"));
      alert.accept();
    } catch (Exception e) {
      System.err.println(e);
    }
  }
  @Test
  public void buySuccess()  {
    findBook1();
    driver.findElement(By.linkText("Add To Cart")).click();
    driver.findElement(By.cssSelector("#goToCart > .fa")).click();
    driver.findElement(By.id("address")).click();
    driver.findElement(By.id("address")).sendKeys("123 Main ST");
    driver.findElement(By.cssSelector(".btn-success")).click();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 30);

      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      Assert.assertTrue(alert.getText().contains("Thank You for Your Purchase"));
      alert.accept();
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  public void logIN () throws InterruptedException {
    driver.findElement(By.id("inputUsername")).click();
    driver.findElement(By.id("inputUsername")).sendKeys("buyer1");
    driver.findElement(By.id("inputPassword")).sendKeys("pw");
    driver.findElement(By.id("signIn")).click();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      alert.accept();
    } catch (Exception e) {
      System.err.println(e);
    }
    Thread.sleep(1000);
    driver.findElement(By.id("signIn")).click();
  }

  public void findBook1 (){
    driver.findElement(By.id("findBook")).click();
    driver.findElement(By.id("findBook")).sendKeys("Book1");
    driver.findElement(By.id("book_title")).click();
    driver.findElement(By.id("search_book")).click();
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("book_title_1")));
  }
}
