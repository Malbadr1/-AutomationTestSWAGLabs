package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {

    static float totalPrice = 0;
    private final WebDriver driver;
    private final By pricesOfSelectedProductsLocator = By.xpath
            ("//button[.='REMOVE']/preceding-sibling::div[@class='inventory_item_price']");
    private final By checkout_button = By.xpath("//a[@class='btn_action checkout_button']");
    // private final By checkout_button = By.xpath(" //*[@id=\"cart_contents_container\"]/div/div[2]/a[2]");


    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrice() {
        try {


            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);

            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements =
                        By.xpath("(//button[.='REMOVE']/preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullText = Utility.getData(driver, elements);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtils.getInfo("Total Price : " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.getError(e.getMessage());
            return "not find SelectedProducts ";
        }
    }

    public boolean comperingPrices(String price) {
        return getTotalPrice().equals(price);
    }

    public P04_CheckOutPage clickOnCheckOutButton() {
        Utility.clickingOnElement(driver, checkout_button);
        return new P04_CheckOutPage(driver);
    }

}
