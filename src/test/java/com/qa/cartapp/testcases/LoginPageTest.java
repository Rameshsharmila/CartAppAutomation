package com.qa.cartapp.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.cartapp.base.BaseTest;
import com.qa.cartapp.constants.CartAppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic ("Epic--Login Feature for Open Cart Application")
@Story ("Login Page Feature")
public class LoginPageTest extends BaseTest{
	
	@Test
	@Description("TestCaseL1: login Page Title Test")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageTitleTest() {
		String actTitle = loginpage.getLoginPageTitle();
		Assert.assertEquals(actTitle, CartAppConstants.LOGIN_PAGE_TITLE);
	}
	
	
	@Test
	@Description("TestCaseL2: login Page url Test")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageUrlTest() {
		String actUrl = loginpage.getLoginPageUrl();
		Assert.assertEquals(actUrl.contains(CartAppConstants.LOGIN_PAGE_URL_FRACTION), true);
	}
	
	@Test
	@Description("TestCaseL3: verify forgot pwd link exist on the login page")
	@Severity(SeverityLevel.CRITICAL)
	public void forgotPwdLinkExistTest() {
		Assert.assertEquals(loginpage.isForgotPwdLinkExist(), true);
	}
	
	@Test
	@Description("TestCaseL4: verify user is able to login with correct username and password")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		accountpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accountpage.getAccountsPageTitle(), CartAppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	@Description("TestCaseL5: verify user is getting valid error message with incorrect username and password")
	@Severity(SeverityLevel.NORMAL)
	public void invalidLoginTest() {
		Assert.assertEquals(loginpage.doInvalidLogin("invalid_id","invalidpwd"),true);
		
	}
	
	
	@Test(enabled = false)
	public void loginPageFooterTest() {
		System.out.println("loginPageFooterTest");
	}


}
