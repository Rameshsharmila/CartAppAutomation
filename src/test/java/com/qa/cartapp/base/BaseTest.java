package com.qa.cartapp.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.cartapp.factory.DriverFactory;
import com.qa.cartapp.pages.AccountsPage;
import com.qa.cartapp.pages.LoginPage;
import com.qa.cartapp.pages.RegistrationPage;

public class BaseTest {
	
	public WebDriver driver;
	public Properties prop;
	public DriverFactory driverfactory;
	public SoftAssert softAssert;
	public LoginPage loginpage;
	public RegistrationPage regpage;
	public AccountsPage accountpage;
	
	@Parameters({"browser", "browserversion"})
	@BeforeTest
	public void setup(String browserName, String browserVersion, ITestContext testContext) {
		
		driverfactory = new DriverFactory();
		prop = driverfactory.initProp();
		
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
			prop.setProperty("browserversion", browserVersion);
			prop.setProperty("testname", testContext.getName());
		}
		
		driver=driverfactory.initDriver(prop);
		loginpage = new LoginPage(driver);
		softAssert = new SoftAssert();
		
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	

}
