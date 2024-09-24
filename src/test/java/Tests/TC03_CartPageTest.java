package Tests;

import Listeners.IInvokedMethodListenersClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
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
public class TC03_CartPageTest {  // تم تغيير اسم الفئة لتجنب التعارض مع فئة الصفحة

    private String user_name = DataUtils.getGsonData("validLoginData", "userName");
    private String passWord = DataUtils.getGsonData("validLoginData", "password");

    public TC03_CartPageTest() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setupBrowser() throws IOException {
        // setupDriver(DataUtils.getPropertiesData("environment", "Browser"));

        String browser = System.getProperty("browser") != null ? System.getProperty("browser") //if != null
                : DataUtils.getPropertiesData("environment", "Browser"); //else

        setupDriver(browser);
        LogsUtils.getInfo(browser + " Browser is opened");
        getDriver().get(DataUtils.getPropertiesData("environment", "LoginUrl"));
        LogsUtils.getInfo("Page is redirected to the URL ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void validLoginTestCase() {
        String totalPrice = new P01_LoginPage(getDriver())
                .enterUserName(user_name)
                .enterPassword(passWord)
                .clickOnLoginButton()
                .addRandomProducts(2, 6)
                .getTotalPriceOfSelectedProducts();

        new P02_LandingPage(getDriver()).clickOnCartIcon();

        Assert.assertTrue(new P03_CartPage(getDriver())
                .comperingPrices(totalPrice));  // تصحيح خطأ إملائي في comparingPrices
    }

    @AfterMethod
    public void quit() {
        getDriver().quit();
    }
}
