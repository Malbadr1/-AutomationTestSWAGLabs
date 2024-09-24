package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {
    private final WebDriver driver;
    private final By subTotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");
    private final By finishButton = By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[8]/a[2]");

    public P05_OverviewPage(WebDriver driver) {

        this.driver = driver;
    }

    public Float getSubTotalLabel() {
        return Float.parseFloat(Utility.getData(driver, subTotalLabel).replace("Item total: $", ""));
    }

    public Float getTaxLabel() {
        return Float.parseFloat(Utility.getData(driver, taxLabel).replace("Tax: $", ""));
    }

    public Float getTotalLabel() {
        LogsUtils.getInfo("Actual Total Label: " + (Utility.getData(driver, totalLabel).replace("Total: $", "")));
        return Float.parseFloat(Utility.getData(driver, totalLabel).replace("Total: $", ""));
    }

    public String calculateTotalPrice() {
        LogsUtils.getInfo(" Calculate Total Price : " + (getSubTotalLabel() + getTaxLabel()));
        return String.valueOf(getSubTotalLabel() + getTaxLabel());

    }

    public boolean comparingPrice() {
        return calculateTotalPrice().equals(String.valueOf(getTotalLabel()));
    }

    public P06_FinishingOrderPage clickOnFinishButton() {

        Utility.clickingOnElement(driver, finishButton);
        return new P06_FinishingOrderPage(driver);
    }

}
