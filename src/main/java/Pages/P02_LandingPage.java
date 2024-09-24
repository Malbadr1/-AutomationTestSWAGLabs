package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class P02_LandingPage {

    static float totalPrice = 0;
    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;
    private final WebDriver driver;
    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");

    private final By numbersOfProductsOnCartIcon = By.className("shopping_cart_container");
    private final By numbersOfSelectedProducts = By.xpath("//button[.='REMOVE']");

    private final By cartIcon = By.className("shopping_cart_link");
    private final By pricesOfSelectedProductsLocator = By.xpath
            ("//button[.='REMOVE']/preceding-sibling::div[@class='inventory_item_price']");


    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }


    public By getNumbersOfProductsOnCartIcon() {
        return numbersOfProductsOnCartIcon;
    }

    public P02_LandingPage addAllProductsToCart() {

        allProducts = driver.findElements(addToCartButtonForAllProducts);
        LogsUtils.getInfo("number of all products To cart : " + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + i + "]");//dynamic locator
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);


        }

        return this;
    }


    public String getNumbersOfProductsInCart() {
        try {

            LogsUtils.getInfo("Numbers Of Products In Cart : " + Utility.getData(driver, numbersOfProductsOnCartIcon));
            return Utility.getData(driver, numbersOfProductsOnCartIcon);
        } catch (Exception e) {
            LogsUtils.getError(e.getMessage());
        }
        return " no products";
    }


    public String getNumbersOfSelectedProducts() {
        try {


            selectedProducts = driver.findElements(numbersOfSelectedProducts);
            LogsUtils.getInfo("Numbers Of Selected Products : " + (selectedProducts.size()));
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            LogsUtils.getError(e.getMessage());
        }
        return " no productsSelected";
    }


    public P02_LandingPage addRandomProducts(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> randomNumbers = Utility.generateUniqueNumber(numberOfProductsNeeded, totalNumberOfProducts);
        for (int random : randomNumbers) {
            LogsUtils.getInfo("randomNumber : " + random);
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]");//dynamic locator
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);

        }
        return this;
    }

    public boolean comparingNumberOfCartWithSelecting() {
        return getNumbersOfProductsInCart().equals(getNumbersOfSelectedProducts());
    }

    //TODO: click On Cart Icon Link

    /**
     * @return
     */
    public P03_CartPage clickOnCartIcon() {
        Utility.clickingOnElement(driver, cartIcon);
        return new P03_CartPage(driver);
    }


    public String getTotalPriceOfSelectedProducts() {
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
}
