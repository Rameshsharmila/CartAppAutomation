package com.qa.cartapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.cartapp.constants.CartAppConstants;
import com.qa.cartapp.utils.ElementsUtility;

public class RegistrationPage {
	
		private WebDriver driver;
		private ElementsUtility eleUtil;
		
		private By firstName = By.id("input-firstname");
		private By lastName = By.id("input-lastname");
		private By email = By.id("input-email");
		private By telephone = By.id("input-telephone");
		private By password = By.id("input-password");
		private By confirmpassword = By.id("input-confirm");
		
		private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input");
		private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input");
		private By agreeCheckBox = By.name("agree");
		
		private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
		private By successMsg = By.cssSelector("div#content h1");

		private By logoutLink = By.linkText("Logout");
		private By registerLink = By.linkText("Register");
		
		public RegistrationPage(WebDriver driver) {
			this.driver = driver;
			eleUtil = new ElementsUtility(driver);
		}
		
		
		public boolean userRegistration(String firstName, String lastName, String email, String phone, String password,
				String subscribe) {
				eleUtil.doSendKeys(this.firstName,firstName);
				eleUtil.doSendKeys(this.lastName, lastName);
				eleUtil.doSendKeys(this.email, email);
				eleUtil.doSendKeys(this.telephone, phone);
				eleUtil.doSendKeys(this.password, password);
				eleUtil.doSendKeys(this.confirmpassword, password);
				
				if(subscribe.equals("yes")) {
					eleUtil.doClick(subscribeYes);
				} else {
					eleUtil.doClick(subscribeNo);
				}
				
				eleUtil.doClick(this.agreeCheckBox);
				eleUtil.doClick(this.continueButton);
				
				String message = eleUtil.waitForElementVisible(this.successMsg, CartAppConstants.MEDIUM_DEFAULT_TIME_OUT).getText();
				if(message.contains(CartAppConstants.REGISTER_SUCCESS_MSG)) {
					return true;
				} else {
					return false;
				}
			}

		
		public void goToRegisterPage() {
			eleUtil.doClick(this.logoutLink);
			eleUtil.doClick(this.registerLink);
		}

}
