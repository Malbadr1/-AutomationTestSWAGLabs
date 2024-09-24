package Listeners;

import Utilities.LogsUtils;
import Utilities.Utility;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static DriverFactory.DriverFactoryClass.getDriver;

public class IInvokedMethodListenersClass implements IInvokedMethodListener {
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }


    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

        // التقاط لقطة شاشة كاملة لأي حالة
        //  Utility.fullScreenShot(getDriver(), new P02_LandingPage(getDriver()).getNumbersOfProductsOnCartIcon());

        File logFile = Utility.getLatesfile(LogsUtils.logsPath);
        try {
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // التحقق من حالة الفشل
        try {
            if (testResult.getStatus() == ITestResult.FAILURE) {

                LogsUtils.getInfo("Test Case : " + testResult.getTestName() + " failed");

                // التقاط لقطة شاشة للفشل
                Utility.takeScreenShot(getDriver(), testResult.getName());

            } else {
                LogsUtils.getInfo("Test Case : " + testResult.getTestName() + "passed");
                Utility.takeScreenShot(getDriver(), testResult.getName());
            }
        } catch (Exception e) {
            // رمي استثناء في حالة وجود مشكلة في أخذ لقطة الشاشة
            throw new RuntimeException(e);
        }


    }


}