package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_LoginPage {

    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By clickLoginButton = By.id("login-button");
    private final WebDriver driver;

    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public P01_LoginPage enterUserName(String userNameText) {
        Utility.sendData(driver, username, userNameText);
        return this;
    }

    public P01_LoginPage enterPassword(String passwordText) {
        Utility.sendData(driver, password, passwordText);
        return this;
    }

    public P02_LandingPage clickOnLoginButton() {

        Utility.clickingOnElement(driver, clickLoginButton);
        return new P02_LandingPage(driver);

    }

    public boolean assertLoginTestCase(String expectValue) {

        return driver.getCurrentUrl().equals(expectValue);
    }
}
