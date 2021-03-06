package tqs.group4.bestofbooks.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tqs.group4.bestofbooks.BestofbooksApplication;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BestofbooksApplication.class)
public class RegisterTestIT {
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
    driver.get("http://localhost:8080/register.html");
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void registerWithoutInput() {
    driver.findElement(By.id("inputUsername")).click();
    driver.findElement(By.id("inputUsername")).sendKeys("l");
    driver.findElement(By.id("createAccount")).click();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      Assert.assertTrue(alert.getText().contains("Could not create, User either exists or missing an input!"));
      alert.accept();
    } catch (Exception e) {
      System.err.println(e);
    }
  }
  @Test
  public void registerAlreadyInUse() {
    driver.findElement(By.id("inputUsername")).click();
    driver.findElement(By.id("inputUsername")).sendKeys("buyer1");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("pw");
    driver.findElement(By.id("createAccount")).click();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      Assert.assertTrue(alert.getText().contains("Could not create, User either exists or missing an input!"));
      alert.accept();
    } catch (Exception e) {
      System.err.println(e);
    }
  }

 /* @Test
  public void registerSuccess() {
    driver.findElement(By.id("inputUsername")).click();
    driver.findElement(By.id("inputUsername")).sendKeys("newUser");
    driver.findElement(By.id("inputPassword")).click();
    driver.findElement(By.id("inputPassword")).sendKeys("pw");
    driver.findElement(By.id("createAccount")).click();
    try {
      WebDriverWait wait = new WebDriverWait(driver, 2);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      Assert.assertTrue(alert.getText().contains("Created!"));
      alert.accept();
    } catch (Exception e) {
      System.err.println(e);
    }

  }*/
}
