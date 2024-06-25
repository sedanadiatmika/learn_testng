package test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class Day1 {

    @AfterTest
    public void lastexe() {
//        WebDriverManager.chromedriver().setup();
//        WebDriver driver = new ChromeDriver();
        System.out.println("I will execute last");

    }

    @Test
    public void Demo() {
        System.out.println("Hello");
        Assert.fail();
    }

    @Test(groups = {"Smoke"})
    public void SecondTest() {
        System.out.println("Bye");
    }
}
