package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Testing {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("http://dev.luxeonarrival.com.au/");
        WebElement headerWeb = driver.findElement(By.tagName("h1"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headerWeb);
        Thread.sleep(3000);
        System.out.println(headerWeb.getText());
    }
}
