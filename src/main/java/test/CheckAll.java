package test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckAll {

    public static void main(String[] args) {
// TODO Auto-generated method stub

        String homePage = "http://dev.luxeonarrival.com.au/";
        String url = "";
        HttpURLConnection huc = null;
        int respCode = 200;

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(homePage);

        List<WebElement> links = driver.findElements(By.tagName("a"));

        Iterator<WebElement> it = links.iterator();

        ArrayList<String> validLinks = new ArrayList<>();
        ArrayList<String> brokenLinks = new ArrayList<>();
        ArrayList<String> skippedLinks = new ArrayList<>();

        while(it.hasNext()){

            url = it.next().getAttribute("href");

//            System.out.println(url);

            if(url == null || url.isEmpty()){
                System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }

            if(!url.startsWith(homePage)){
//                System.out.println("URL belongs to another domain, skipping it.");
                skippedLinks.add(url);
                continue;
            }

            try {
                huc = (HttpURLConnection)(new URL(url).openConnection());

                huc.setRequestMethod("HEAD");

                huc.connect();

                respCode = huc.getResponseCode();

                if(respCode >= 400){
                    brokenLinks.add(url);
                }
                else{
                    validLinks.add(url);
                }

            } catch (MalformedURLException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        validLinks = removeDuplicates(validLinks);
        brokenLinks = removeDuplicates(brokenLinks);
        skippedLinks = removeDuplicates(skippedLinks);

        System.out.println("Valid link:");
        for(String validLink:validLinks) {
            System.out.println(validLink);
        }

        System.out.println("Broken link:");
        for(String brokenLink:brokenLinks) {
            System.out.println(brokenLink);
        }

        System.out.println("Skipped link:");
        for(String skippedLink:skippedLinks) {
            System.out.println(skippedLink);
        }

//        checkMeta(validLinks);
        checkMobileFriendly(homePage);
//        checkPageSpeed(homePage);

        driver.quit();

    }

    public static void checkMeta(ArrayList<String> links) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));

        int i = 1;
        for(String link:links) {
            driver.get(link);

            String title = null;
            String description = null;
            String header = null;
            try {
                title = driver.getTitle();
                WebElement descriptionWeb = driver.findElement(By.xpath("//meta[@name='description']"));
                description = descriptionWeb.getAttribute("content");
                WebElement headerWeb = driver.findElement(By.tagName("h1"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headerWeb);
                w.until(ExpectedConditions.visibilityOf(headerWeb));
                Thread.sleep(1000);
                header = headerWeb.getText();
            }catch(NoSuchElementException | InterruptedException MSEE){
//                MSEE.printStackTrace();
            }

            if (title != null && !title.isEmpty() && description != null && !description.isEmpty() && header != null && !header.isEmpty()) {
                System.out.println("Link ke: " + i);
                System.out.println("URL: " + link);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("H1: " + header);
            } else {
                System.out.println("Link ke (invalid) : " + i);
                System.out.println("URL: " + link);
                if (title != null && !title.isEmpty()) {
                    System.out.println("Title: " + title);
                } else {
                    System.out.println("Title is empty");
                }

                if (description != null && !description.isEmpty()) {
                    System.out.println("Description: " + description);
                } else {
                    System.out.println("Description is empty");
                }

                if (header != null && !header.isEmpty()) {
                    System.out.println("H1: " + header);
                } else {
                    System.out.println("H1 is empty");
                }
            }

            i++;
        }

        driver.quit();
    }

    public static void checkMobileFriendly(String link) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://search.google.com/test/mobile-friendly");
        driver.findElement(By.xpath("//input[@type='url']")).sendKeys(link);
        driver.findElement(By.xpath("//div[@jsname='LZQqje']")).click();
    }

    public static void checkPageSpeed(String link) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://pagespeed.web.dev/");
        driver.findElement(By.id("i2")).sendKeys(link);
        driver.findElement(By.xpath("//button[@jsname='O2CIGd']")).click();

        String mobileScore = driver.findElement(By.xpath("(//div[@class='lh-gauge__percentage'])[9]")).getText();
        driver.findElement(By.id("desktop_tab")).click();
        String desktopScore= driver.findElement(By.xpath("(//div[@class='lh-gauge__percentage'])[21]")).getText();



    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}