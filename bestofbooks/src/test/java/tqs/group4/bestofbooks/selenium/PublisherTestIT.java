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
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BestofbooksApplication.class)
public class PublisherTestIT {
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

/* @Test
  public void seeStock() {
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("seeStock")).click();
   WebDriverWait wait = new WebDriverWait(driver, 10);
   wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("h1")));
   driver.findElement(By.cssSelector("h1")).click();
    assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Your Stock"));
  }
*/
  @Test
  public void revenues() {
    driver.findElement(By.cssSelector("h1")).click();
    assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Revenue"));

  }
  @Test
  public void total() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    List<WebElement> elements = driver.findElements(By.id("finalPrice"));
    assert(elements.size() > 0);

  }
  /* @Test
 public void addBookError() {
   driver.findElement(By.id("name")).click();
   driver.findElement(By.id("addBook")).click();
   driver.findElement(By.id("create_ISBN")).click();
   driver.findElement(By.id("create_ISBN")).sendKeys("1111111111114");
   driver.findElement(By.id("create_Title")).click();
   driver.findElement(By.id("create_Title")).sendKeys("Book3");
   driver.findElement(By.id("BookToAdd")).click();
   try {
     WebDriverWait wait = new WebDriverWait(driver, 10);
     wait.until(ExpectedConditions.alertIsPresent());
     Alert alert = driver.switchTo().alert();
     Assert.assertTrue(alert.getText().contains("Something is Missing! Did not Create"));
     alert.accept();
   } catch (Exception e) {
     System.err.println(e);
   }
 }

@Test
 public void addBookSuccess() {
   driver.findElement(By.id("name")).click();
   driver.findElement(By.id("addBook")).click();
   driver.findElement(By.id("create_ISBN")).click();
   driver.findElement(By.id("create_ISBN")).sendKeys("1111111111114");
   driver.findElement(By.id("create_Title")).click();
   driver.findElement(By.id("create_Title")).sendKeys("Book3");
   driver.findElement(By.id("create_Author")).click();
   driver.findElement(By.id("create_Author")).sendKeys("An Author");
   driver.findElement(By.id("create_Category")).click();
   driver.findElement(By.id("create_Category")).sendKeys("SciFi");
   driver.findElement(By.id("create_Description")).click();
   driver.findElement(By.id("create_Description")).sendKeys("A description");
   driver.findElement(By.id("create_Price")).click();
   driver.findElement(By.id("create_Price")).sendKeys("12.5");
   driver.findElement(By.id("create_Quantity")).click();
   driver.findElement(By.id("create_Quantity")).sendKeys("2");
   driver.findElement(By.id("BookToAdd")).click();
   try {
     WebDriverWait wait = new WebDriverWait(driver, 10);
     wait.until(ExpectedConditions.alertIsPresent());
     Alert alert = driver.switchTo().alert();
     Assert.assertTrue(alert.getText().contains("Book Created"));
     alert.accept();
   } catch (Exception e) {
     System.err.println(e);
   }
 }
  @Test
  public void badUpdate() {
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("updateStock")).click();
    driver.findElement(By.id("isbn")).click();
    driver.findElement(By.id("isbn")).sendKeys("1111");
    driver.findElement(By.id("quantity")).click();
    driver.findElement(By.id("quantity")).sendKeys("0");
    driver.findElement(By.id("updateBook")).click();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      Assert.assertTrue(alert.getText().contains("Quantity cannot not be less than 1"));
      alert.accept();
    } catch (Exception e) {
      System.err.println(e);
    }
  }
  @Test
  public void updateBookSucess() {
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("updateStock")).click();
    driver.findElement(By.id("isbn")).click();
    driver.findElement(By.id("isbn")).sendKeys("1111111111111");
    driver.findElement(By.id("quantity")).click();
    driver.findElement(By.id("quantity")).sendKeys("2");
    driver.findElement(By.id("updateBook")).click();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      Assert.assertTrue(alert.getText().contains("Updated"));
      alert.accept();
    } catch (Exception e) {
      System.err.println(e);
    }
  }*/

  public void logIN () throws InterruptedException {
    driver.findElement(By.id("inputUsername")).click();
    driver.findElement(By.id("inputUsername")).sendKeys("pub1");
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
}
