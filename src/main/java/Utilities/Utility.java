package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {

    private static String screenShotsPath = "Test-outputs/ScreenShots/";

    //TODO: Send Data

    /**
     * function to send elements
     *
     * @param driver
     * @param locator
     * @param data
     */

    public static void sendData(WebDriver driver, By locator, String data) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(locator));

        driver.findElement(locator).sendKeys(data);

    }
//TODO: Get Data

    /**
     * @param driver
     * @param locator
     * @return
     */

    public static String getData(WebDriver driver, By locator) {

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    //TODO: Clicking in Element

    /**
     * function to click on
     *
     * @param driver
     * @param locator
     */


    public static void clickingOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }
    //TODO: General Wait

    /**
     * @param driver
     * @return
     */

    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //TODO: Scrolling

    /**
     * @param driver
     * @param locator
     */

    public static void scrolling(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", findWebElement(driver, locator));

    }

//TODO:FindWebElement

    /**
     * @param driver
     * @param locator
     * @return
     */


    public static WebElement findWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    //TODO:TimeStamp

    /**
     * @return
     */
    public static String getTimeStamp() {

        return new SimpleDateFormat("yyyy-MM-dd-ss-a").format(new Date());
    }

    //TODO: selectFromDropDown

    /**
     * @param driver
     * @param locator
     * @param option
     */
    public static void selectFromDropDown(WebDriver driver, By locator, String option) {
        new Select(findWebElement(driver, locator)).deselectByVisibleText(option);
    }


    //TODO: generate Random Number

    /**
     * @param upperBound
     * @return
     */
    public static int generateRandomNumber(int upperBound) {
        return new Random().nextInt(upperBound) + 1;
    }


    //TODO: generate Unique Number

    /**
     * @param numberOfProductsNeeded
     * @param totalNumberOfProducts
     * @return
     */
    public static Set<Integer> generateUniqueNumber(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberOfProductsNeeded) {
            int randomNumber = generateRandomNumber(totalNumberOfProducts);
            generatedNumbers.add(randomNumber);

        }
        return generatedNumbers;
    }

//TODO: Take ScreenShot

    /**
     * @param driver
     * @param screenShotName
     * @throws IOException
     */

    public static void takeScreenShot(WebDriver driver, String screenShotName) throws IOException {
        try {


            File screenShots = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenDest = new File(screenShotsPath + screenShotName + "-" + getTimeStamp() + ".png");
            FileUtils.copyFile(screenShots, screenDest);

            Allure.addAttachment(screenShotName, Files.newInputStream(Path.of(screenDest.getPath())));
        } catch (Exception e) {
            LogsUtils.getError(e.getMessage());
        }

    }
//TODO:fullScreenShot

    /**
     * @param driver
     * @param locator
     */
    public static void fullScreenShot(WebDriver driver, By locator) {
        try {

            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(findWebElement(driver, locator))
                    .save(screenShotsPath);

        } catch (Exception e) {
            LogsUtils.getError(e.getMessage());
        }
    }

    public static boolean verifyPageUrl(WebDriver driver, String expectUrl) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expectUrl));
        } catch (Exception e) {
            return false;
        }

        return true;
    }


    public static Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public static void restoreSession(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies
        ) {
            driver.manage().addCookie(cookie);
        }
    }

    public static File getLatesfile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0)
            return null;
        Arrays.sort(files, Comparator.comparing(File::lastModified).reversed());
        return files[0];
    }

}


