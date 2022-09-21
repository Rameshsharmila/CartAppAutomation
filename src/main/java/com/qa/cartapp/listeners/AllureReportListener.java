package com.qa.cartapp.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.qa.cartapp.factory.DriverFactory.getDriver;

public class AllureReportListener implements ITestListener {
	
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName(); //getMethod() deprecated
	}

	//Image Attachments for Allure
	@Attachment (value="Page Screenshot", type="image/png")
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
	}
	
	//Text Attachments for Allure
	@Attachment(value="{0}", type="text/plain")
	public static String saveText(String message) {
		return message;
	}
	
	//HTML Attachments for Allure
		@Attachment(value="{0}", type="text/html")
		public static String saveHtml(String html) {
			return html;
		}
	
	
	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");		
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " success");
		
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
		
		Object testClass = iTestResult.getInstance();
		if(getDriver() instanceof WebDriver) {
			System.out.println("Screenshot captured for the test case " + getTestMethodName(iTestResult));
			saveScreenshotPNG(getDriver());
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
		
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		System.out.println("I am in onFinish method " + iTestContext.getName());
		
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("I am in onStart method " + iTestContext.getName());
		
	}

}
