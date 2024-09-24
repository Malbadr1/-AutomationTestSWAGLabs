package Tests;

import Listeners.IInvokedMethodListenersClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactoryClass.getDriver;
import static DriverFactory.DriverFactoryClass.setupDriver;

@Listeners({IInvokedMethodListenersClass.class, ITestResultListenerClass.class})

public class TC01_LoginTest {

    private String user_name = DataUtils.getGsonData("validLoginData", "userName");
    private String passWord = DataUtils.getGsonData("validLoginData", "password");

    public TC01_LoginTest() throws FileNotFoundException {
    }


    @BeforeMethod
    public void setupBrowser() throws IOException {


        String browser = System.getProperty("browser") != null ? System.getProperty("browser") //if != null
                : DataUtils.getPropertiesData("environment", "Browser"); //else

        setupDriver(browser);
        LogsUtils.getInfo(browser + " is opened");
        getDriver().get(DataUtils.getPropertiesData("environment", "LoginUrl"));
        LogsUtils.getInfo("Page is redirected to the URL ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


    }


    @Test
    public void validLoginTestCase() throws IOException, InterruptedException {
        new P01_LoginPage(getDriver()).enterUserName(user_name)
                .enterPassword(passWord)
                .clickOnLoginButton();


        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTestCase(DataUtils.getPropertiesData("environment", "HomeUrl")));

    }


    @AfterMethod
    public void quit() {
        getDriver().quit();
    }
}
