package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

public class AutoTime {

    static WebDriver driver;

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(300));

        String homePage = "https://google.com/";

        driver.get("https://validator.w3.org/checklink");
        driver.findElement(By.id("uri_1")).sendKeys(homePage);
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[name='results1']")));

        List<WebElement> codes = driver.findElements(By.cssSelector("tbody td:nth-child(1)"));
        List<WebElement> occurrences = driver.findElements(By.cssSelector("tbody td:nth-child(2)"));
        List<WebElement> whatToDos = driver.findElements(By.cssSelector("tbody td:nth-child(3)"));

//        WebElement report = driver.findElement(By.xpath("(//dl[@class='report'])[1]"));

        List<WebElement> urls = driver.findElements(By.xpath("(//dl[@class='report'])[1]/dt"));
        List<WebElement> statuses = driver.findElements(By.xpath("(//dl[@class='report'])[1]/dd[@class='responsecode']"));
        List<WebElement> messages = driver.findElements(By.xpath("(//dl[@class='report'])[1]/dd[@class='message_explanation']"));

        System.out.println("List of broken links and other issues");
        for(int i = 0; i < statuses.size(); i++) {
            System.out.println("___________________________________________________");
            System.out.println("Link number: " + (i + 1));
            System.out.println(urls.get(i).getText());
            System.out.println(statuses.get(i).getText());
            System.out.println("Message: " + messages.get(i).getText());
        }
        System.out.println("___________________________________________________");

        System.out.println("\nVERIFY ALL LINKS SUMMARY");
        System.out.printf("%-13s%-13s%-13s%n", "Code", "Occurrences", "What to do");
        for(int i = 0; i < codes.size(); i++) {
            System.out.printf("%-13s%-13s%-13s%n", codes.get(i).getText(), occurrences.get(i).getText(), whatToDos.get(i).getText());
        }


        // PageSpeed

        while(true){
            driver.get("https://pagespeed.web.dev/");
            driver.findElement(By.id("i2")).sendKeys(homePage);
            WebElement error = null;
            w.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@jsname='O2CIGd']")));
            driver.findElement(By.xpath("//button[@jsname='O2CIGd']")).click();

            try {
                Thread.sleep(3000);
                error = driver.findElement(By.xpath("//span[contains(text(), 'Unknown Error')]"));
            } catch (NoSuchElementException ignored) {

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(error == null) {
                break;
            }
        }

        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='lh-gauge__percentage'])[9]")));

        String mobileScore = driver.findElement(By.xpath("(//div[@class='lh-gauge__percentage'])[9]")).getText();
        driver.findElement(By.id("desktop_tab")).click();

        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='lh-gauge__percentage'])[21]")));
        String desktopScore= driver.findElement(By.xpath("(//div[@class='lh-gauge__percentage'])[21]")).getText();

        System.out.println("================================================================");
        System.out.println("\nWEB PERFORMANCE");
        System.out.println("Mobile performance score: " + mobileScore);
        System.out.println("Desktop performance score: " + desktopScore);
        System.out.println("================================================================");

        System.out.println("\nTEST SUMMARY");
        System.out.println("Verify all links: check report above");

        if(Integer.parseInt(mobileScore) >= 55) {
            System.out.println("Mobile performance: passed");
        } else {
            System.out.println("Mobile performance: failed");
        }

        if(Integer.parseInt(desktopScore) >= 85) {
            System.out.println("Desktop performance: passed");
        } else {
            System.out.println("Desktop performance: failed");
        }

        driver.quit();

    }


}
