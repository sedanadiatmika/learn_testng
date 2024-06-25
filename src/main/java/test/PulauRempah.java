package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.*;

import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;


public class PulauRempah {
    static WebDriver driver;
    static int noSheet = 5;
    static String brand = "bacardi";
    static ArrayList<String> category = new ArrayList<>();
    static ArrayList<String> title = new ArrayList<>();
    static ArrayList<String> description = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, IOException {

        readExcel("cat");
        readExcel("tit");
        readExcel("des");
        category.remove(0);
        title.remove(0);
        description.remove(0);


        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();

        String homePage = "https://staging.pulau-rempah.timedoor-host.web.id/login";

        String username = "pulaurempah";
        String password = "pr@2022!@#";

        driver.get(homePage);

        try {
            Runtime.getRuntime().exec("basic.exe");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        driver.findElement(By.id("username")).sendKeys("admin@admin.com");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.xpath("(//button[@type='submit'])[1]")).click();

        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='https://staging.pulau-rempah.timedoor-host.web.id/task']")));
        driver.findElement(By.xpath("//a[@href='https://staging.pulau-rempah.timedoor-host.web.id/task']")).click();
        driver.findElement(By.xpath("//a[@class='btn btn-primary mb-3']")).click();

        for(int i = 0; i < category.size(); i++) {
            w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@role='textbox'])[1]")));
            driver.findElement(By.xpath("(//span[@role='textbox'])[1]")).click();
            driver.findElement(By.xpath("//input[@role='searchbox']")).sendKeys(brand);
            driver.findElement(By.xpath("//li[@role='option']")).click();

            driver.findElement(By.xpath("(//span[@role='textbox'])[2]")).click();
            driver.findElement(By.xpath("//input[@role='searchbox']")).sendKeys(category.get(i));
            driver.findElement(By.xpath("//li[@role='option']")).click();

            driver.findElement(By.cssSelector("input[name='title']")).sendKeys(title.get(i));

            driver.findElement(By.cssSelector("input[name='description']")).sendKeys(description.get(i));

            driver.findElement(By.cssSelector("button[type='submit']")).click();

            w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Close']")));
            driver.findElement(By.cssSelector("button[aria-label='Close']")).click();
        }
    }

    static void readExcel(String variable) throws FileNotFoundException {
        try {
            File file = new File("Sales driver requirements.xlsx");   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            //creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(noSheet);     //creating a Sheet object to retrieve object
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            while (itr.hasNext()) {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column

                Cell cell = cellIterator.next();

                if (variable.equals("tit")) {
                    cell = cellIterator.next();
                } else if (variable.equals("des")) {
                    cell = cellIterator.next();
                    cell = cellIterator.next();
                }

                switch (cell.getCellType()) {
                    case STRING:    //field that represents string cell type

                        switch (variable) {
                            case "cat" -> category.add(cell.getStringCellValue());
                            case "tit" -> title.add(cell.getStringCellValue());
                            case "des" -> description.add(cell.getStringCellValue());
                        }

                        break;
                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


