package com.qa.cartapp.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.cartapp.constants.CartAppConstants;
import com.qa.cartapp.utils.ElementsUtility;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementsUtility eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By searchField = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	private By accPageHeaders = By.cssSelector("div#content h2");
	
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementsUtility(driver);
	}
	
	
	public String getAccountsPageTitle() {
		String title = eleUtil.waitForTitleToBe(CartAppConstants.SMALL_DEFAULT_TIME_OUT,CartAppConstants.ACCOUNTS_PAGE_TITLE);
		System.out.println("Accounts page title : " + title);
		return title;
	}
	
	
	public String getAccountsPageUrl() {
		String url = eleUtil.waitForURLContains(CartAppConstants.SMALL_DEFAULT_TIME_OUT, CartAppConstants.ACCOUNTS_PAGE_URL_FRACTION);
		System.out.println("Accounts page url : " + url);
		return url;
	}

	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, CartAppConstants.MEDIUM_DEFAULT_TIME_OUT).isDisplayed();
	}

	
	public boolean isSearchFieldExist() {
		return eleUtil.waitForElementVisible(searchField, CartAppConstants.MEDIUM_DEFAULT_TIME_OUT).isDisplayed();
	}


	public List<String> getAccountSectionsHeaderList() {
		return eleUtil.getAllElementsTextList(accPageHeaders, CartAppConstants.SMALL_DEFAULT_TIME_OUT);
	}
	
	

}
