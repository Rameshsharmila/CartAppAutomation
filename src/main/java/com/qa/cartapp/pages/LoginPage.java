package com.qa.cartapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.cartapp.constants.CartAppConstants;
import com.qa.cartapp.utils.ElementsUtility;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementsUtility eleUtil;
	
	
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By loginErrorMessage = By.xpath("//*[@id=\"account-login\"]/div[1]");
	
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementsUtility(driver);
	}
	
	@Step ("Get Login page Title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleToBe(CartAppConstants.SMALL_DEFAULT_TIME_OUT,CartAppConstants.LOGIN_PAGE_TITLE);
		System.out.println("login page title : " + title);
		return title;							
	}
	
	
	@Step ("Get Login page URL")
	public String getLoginPageUrl() {
		String url = eleUtil.waitForURLContains(CartAppConstants.SMALL_DEFAULT_TIME_OUT,CartAppConstants.LOGIN_PAGE_URL_FRACTION);
		System.out.println("login page url is : " + url);
		
		return url;						
	}
	
	
	@Step("Verify if Forgot Password Link Exist")
	public boolean isForgotPwdLinkExist() {
		System.out.println("checking forgot pwd link test");
		return eleUtil.waitForElementPresence(forgotPwdLink, CartAppConstants.SMALL_DEFAULT_TIME_OUT).isDisplayed();
	}
	
	@Step("login with username: {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		eleUtil.doSendKeysWithWait(emailId, CartAppConstants.MEDIUM_DEFAULT_TIME_OUT, username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);

		return new AccountsPage(driver);
	}
	
	@Step("login with invalid username: {0} and password: {1}")
	public boolean doInvalidLogin(String username, String pwd) {
		eleUtil.doSendKeysWithWait(emailId, CartAppConstants.MEDIUM_DEFAULT_TIME_OUT, username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
		//String errMsg="No match for E-Mail Address and/or Password";
		
		String errMsg=eleUtil.getElementText(loginErrorMessage);
		if(errMsg.contains(CartAppConstants.LOGIN_FAILURE_MSG)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	@Step("Navigating to Register page to register a new customer")
	public RegistrationPage goToRegisterPage() {
		System.out.println("Navigating to Registration page");
		eleUtil.doClick(registerLink);
		
		return new RegistrationPage(driver);
	}
	
	
}
