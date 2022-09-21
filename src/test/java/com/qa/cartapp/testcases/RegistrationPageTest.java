package com.qa.cartapp.testcases;

import java.util.Random;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.qa.cartapp.base.BaseTest;
import com.qa.cartapp.constants.CartAppConstants;
import com.qa.cartapp.utils.ExcelUtility;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;


@Epic ("Epic--Registration Feature for Open Cart Application")
@Story ("Registration Page Feature")
public class RegistrationPageTest extends BaseTest {

	@BeforeTest
	public void regSetUp() {
		regpage = loginpage.goToRegisterPage();
	}
	
	public String randomEmail() {
		Random random = new Random();
		String email = "automation" + random.nextInt(1000) + "@gmail.com";
		return email;
	}
	
	@DataProvider
	public Object[][] getRegExcelData() {
		Object regData[][] = ExcelUtility.getTestData(CartAppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	
	@Test(dataProvider="getRegExcelData")
	public void userRegTest(String firstName, String lastName, String phone, String password, String subscribe) {

		boolean successFlag = regpage.userRegistration(firstName, lastName, randomEmail(), phone, password, subscribe);
		regpage.goToRegisterPage();
		Assert.assertEquals(successFlag, true);

	}
}
