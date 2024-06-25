package test;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Day2 {
    @Parameters({"URL"})
    @Test(groups = {"Smoke"})
    public void Ploan(String urlName) {

        System.out.println(urlName);
    }

    @BeforeTest
    public void prereq() {
        System.out.println("I will execute first");
    }
}
