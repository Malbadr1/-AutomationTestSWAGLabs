package Tests;


import Listeners.IInvokedMethodListenersClass;
import Listeners.ITestResultListenerClass;
import Pages.*;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
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
import static Utilities.DataUtils.getPropertiesData;

@Listeners({IInvokedMethodListenersClass.class, ITestResultListenerClass.class})
public class TC06_FinishingOrderPageTest {


    private final String user_name = DataUtils.getGsonData("validLoginData", "userName");
    private final String passWord = DataUtils.getGsonData("validLoginData", "password");
    private final String firstName = DataUtils.getGsonData("information", "fName") + "-" + Utility.getTimeStamp();
    private final String lastName = DataUtils.getGsonData("information", "lName") + "-" + Utility.getTimeStamp();

    private final String zipCode = new Faker().number().digits(5);


    public TC06_FinishingOrderPageTest() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setupBrowser() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") //if != null
                : DataUtils.getPropertiesData("environment", "Browser"); //else

        setupDriver(browser);
        LogsUtils.getInfo(browser + " Browser is opened");
        getDriver().get(getPropertiesData("environment", "LoginUrl"));
        LogsUtils.getInfo("Page is redirected to the URL ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void finishingOrderTc() throws IOException {
        //TODO:Login Steps
        new P01_LoginPage(getDriver()).enterUserName(user_name).enterPassword(passWord).clickOnLoginButton();
        //TODO: Adding product Steps
       /* new P02_LandingPage(getDriver())
                .addRandomProducts(2, 6)
                .clickOnCartIcon();*/
        new P02_LandingPage(getDriver()).addAllProductsToCart().clickOnCartIcon();
        //TODO: Go to checkOut page Steps
        new P03_CartPage(getDriver()).clickOnCheckOutButton();

        //TODO: Filling Steps
        new P04_CheckOutPage(getDriver()).filingInformationCheckOutForm(firstName, lastName, zipCode).clickOnContinueButton();
        LogsUtils.getInfo(firstName + " " + lastName + " " + zipCode);

        //TODO: ComparingPrice
        new P05_OverviewPage(getDriver()).clickOnFinishButton();

        Assert.assertTrue(new P06_FinishingOrderPage(getDriver()).checkVisibilityOfThankMassage());


    }

    @AfterMethod
    public void quit() {
        getDriver().quit();
    }
}

