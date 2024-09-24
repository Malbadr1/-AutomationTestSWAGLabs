package Listeners;

import Utilities.LogsUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestResultListenerClass implements ITestListener {
    public void onTestStart(ITestResult result) {
        LogsUtils.getInfo("Test Case : " + result.getTestName() + "  started");
    }

    public void onTestSuccess(ITestResult result) {
        LogsUtils.getInfo("Test Case : " + result.getTestName() + " passed");
    }

    public void onTestFailure(ITestResult result) {
        LogsUtils.getInfo("Test Case : " + result.getTestName() + " failure");
    }

    public void onTestS(ITestResult result) {
        LogsUtils.getInfo("Test Case : " + result.getTestName() + " failure");
    }

    public void onTestSkipped(ITestResult result) {
        LogsUtils.getInfo("Test Case : " + result.getTestName() + " skip");
    }
}
