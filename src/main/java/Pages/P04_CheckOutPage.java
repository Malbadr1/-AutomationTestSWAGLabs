package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static Utilities.Utility.generalWait;

public class P04_CheckOutPage {
    private final WebDriver driver;
    private final By firstNameLocator = By.id("first-name");
    private final By lastNameLocator = By.id("last-name");
    private final By zipCodeLocator = By.id("postal-code");
    private final By continueButtonLocator = By.xpath("//input[@type='submit' and @value='CONTINUE']");

    public P04_CheckOutPage(WebDriver driver) {

        this.driver = driver;
    }

    public P04_CheckOutPage filingInformationCheckOutForm(String fName, String lName, String zipCode) {
        Utility.sendData(driver, firstNameLocator, fName);
        Utility.sendData(driver, lastNameLocator, lName);
        Utility.sendData(driver, zipCodeLocator, zipCode);

        return this;

    }


    public P05_OverviewPage clickOnContinueButton() {
        Utility.clickingOnElement(driver, continueButtonLocator);
        return new P05_OverviewPage(driver);
    }

    public boolean verifyPageUrl(String expectUrl) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expectUrl));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
