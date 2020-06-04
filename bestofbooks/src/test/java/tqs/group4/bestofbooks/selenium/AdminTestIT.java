package tqs.group4.bestofbooks.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tqs.group4.bestofbooks.BestofbooksApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BestofbooksApplication.class)
public class AdminTestIT {
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
  public void SeeCommissions() {
    driver.findElement(By.cssSelector("h1")).click();
    assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Your Comissions"));
    driver.findElement(By.id("commission_title_1")).click();
    assertThat(driver.findElement(By.id("commission_title_1")).getText(), containsString("Commission #1"));
    driver.findElement(By.id("commission_amount_1")).click();
    {
      WebElement element = driver.findElement(By.id("commission_amount_1"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    assertThat(driver.findElement(By.id("commission_amount_1")).getText(), containsString("Amount:"));
    driver.findElement(By.id("commission_order_1")).click();
    {
      WebElement element = driver.findElement(By.id("commission_order_1"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    assertThat(driver.findElement(By.id("commission_order_1")).getText(), containsString("Order ID:"));
  }

 /* @Test
  public void total() {
      List<WebElement> elements = driver.findElements(By.id("finalPrice"));
      assert(elements.size() > 0);
  }*/



  public void logIN () throws InterruptedException {
    driver.findElement(By.id("inputUsername")).click();
    driver.findElement(By.id("inputUsername")).sendKeys("admin");
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
