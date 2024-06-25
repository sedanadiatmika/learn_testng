package test;

import org.testng.annotations.*;

public class Day3 {

    @BeforeClass
    public void BfClass() {
        System.out.println("Before executing any class");
    }

    @BeforeSuite
    public void BfSuite() {
        System.out.println("I run first");
    }

    @AfterSuite
    public void AfSuite() {
        System.out.println("I'm last");
    }

    @BeforeMethod
    public void BfEvery() {
        System.out.println("Before every method");
    }

    @AfterMethod
    public void AfEvery() {
        System.out.println("After every method");
    }

    @AfterClass
    public void AfClass() {
        System.out.println("After class");
    }

    @Parameters({"URL"})
    @Test
    public void WebLoginCarLoan(String urlname) {
        System.out.println("WebLoginCarLoan");
        System.out.println(urlname);
    }

    @Test(groups = {"Smoke"}, dataProvider = "getData")
    public void MobileLoginCarLoan(String username, String password) {
        System.out.println("MobileLoginCarLoan");
        System.out.println(username);
        System.out.println(password);
    }

    @Test(groups = {"Smoke"})
    public void LoginAPICarLoan() {
        System.out.println("LoginAPICarLoan");
    }

    @DataProvider
    public Object[][] getData() {
        Object[][] data = new Object[3][2];
        data[0][0] = "username";
        data[0][1] = "password";

        return data;
    }
}
