package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_FinishingOrderPage {

    private final WebDriver driver;

    private final By thanksMessage = By.xpath("//*[@id='checkout_complete_container']/h2");


    public P06_FinishingOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean checkVisibilityOfThankMassage() {
        return driver.findElement(thanksMessage).isDisplayed();
    }
}
