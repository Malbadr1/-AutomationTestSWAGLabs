package Tests;

import Listeners.IInvokedMethodListenersClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactoryClass.*;

@Listeners({IInvokedMethodListenersClass.class, ITestResultListenerClass.class})

public class TC02_LandingPageTest {
    private String user_name = DataUtils.getGsonData("validLoginData", "userName");
    private String passWord = DataUtils.getGsonData("validLoginData", "password");

    private Set<Cookie> cookies;

    public TC02_LandingPageTest() throws FileNotFoundException {
    }

    @BeforeClass
    public void login() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") //if != null
                : DataUtils.getPropertiesData("environment", "Browser"); //else

        setupDriver(browser);
        LogsUtils.getInfo(browser + " Browser is opened");
        getDriver().get(DataUtils.getPropertiesData("environment", "LoginUrl"));
        LogsUtils.getInfo("Page is redirected to the URL ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        new P01_LoginPage(getDriver()).enterUserName(user_name)
                .enterPassword(passWord)
                .clickOnLoginButton();
        cookies = Utility.getAllCookies(getDriver());
        quitDriver();


    }

    @BeforeMethod
    public void setupBrowser() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") //if != null
                : DataUtils.getPropertiesData("environment", "Browser"); //else

        setupDriver(browser);
        LogsUtils.getInfo(browser + " Browser is opened");
        getDriver().get(DataUtils.getPropertiesData("environment", "LoginUrl"));
        LogsUtils.getInfo("Page is redirected to the URL ");
        Utility.restoreSession(getDriver(), cookies);
        getDriver().get(DataUtils.getPropertiesData("environment", "HomeUrl"));
        getDriver().navigate().refresh();


    }


    @Test
    public void checkNumbersOfSelectedProductsTc() throws InterruptedException {

      /*  new P01_LoginPage(getDriver()).enterUserName(user_name)
                .enterPassword(passWord)
                .clickOnLoginButton().addAllProductsToCart();*/

        new P02_LandingPage(getDriver()).addAllProductsToCart();

        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfCartWithSelecting());

    }

    @Test

    public void addingRandomProductsToCartTc() {
       /* new P01_LoginPage(getDriver())
                .enterUserName(user_name)
                .enterPassword(passWord)
                .clickOnLoginButton()
                .addRandomProducts(3, 6);*/

        new P02_LandingPage(getDriver()).addAllProductsToCart();

        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfCartWithSelecting());

    }

    @Test

    public void clickOnCartIconTC() throws IOException {
       /* new P01_LoginPage(getDriver())
                .enterUserName(user_name)
                .enterPassword(passWord)
                .clickOnLoginButton()
                .clickOnCartIcon();*/

        new P02_LandingPage(getDriver()).clickOnCartIcon();

        Assert.assertTrue(Utility.verifyPageUrl(getDriver(), DataUtils.getPropertiesData("environment", "CartUrl")));

    }

    @AfterMethod
    public void quit() {
        getDriver().quit();
    }

    @AfterClass

    public void deleteSession() {
        cookies.clear();
    }
}