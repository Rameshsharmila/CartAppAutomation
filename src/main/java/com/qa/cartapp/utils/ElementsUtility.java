package com.qa.cartapp.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

/**
 * 
 * List all possible actions available for the elements found in UI.
 *
 */
public class ElementsUtility {
	
	private WebDriver driver;
	private Actions action;
	
	public static Logger log= Logger.getLogger(ElementsUtility.class);
	
	
	public ElementsUtility(WebDriver driver) {
		this.driver=driver;
		action = new Actions(driver);		
	}
	
	//Method to find the element based on locator
	public WebElement getElement(By locator) {	
		return driver.findElement(locator);	
	}
	
	//Method to find the list of elements based on the locator
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	//Generic method to cover all locatortypes
	public By getBy(String locatorType, String selector) {
	
		By locator = null;
		switch(locatorType.toLowerCase()) {
		
			case "id": 
					locator = By.id(selector);
					break;
			case "name":
					locator = By.name(selector);
					break;
			case "xpath":
					locator = By.xpath(selector);
					break;
			case "classname":
					locator = By.className(selector);
					break;
			case "cssselector":
				locator = By.cssSelector(selector);
				break;
			case "linkText":
				locator = By.linkText(selector);
				break;
			case "partiallinktext":
				locator = By.partialLinkText(selector);
				break;
			case "tagName":
				locator = By.tagName(selector);
				break;
			default:
				break;
		}
		
		return locator;
	}
	
	
	//Send Keys
	public void doSendKeys(String locatorType, String selector, String value) {
		By locator = getBy(locatorType, selector);
		getElement(locator).sendKeys(value);
	}
	
	
	public void doSendKeys(By locator, String value) {		
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}
	
	
	//Button Click
	public void doClick(String locatorType, String selector) {
		By locator = getBy(locatorType, selector);
		getElement(locator).click();
	}
	
	
	public void doClick(By locator) {		
		getElement(locator).click();
	}
	
	
	//Get text of the element selected
	public String getElementText(By locator) {
		String textValue = getElement(locator).getText();
		return textValue;
	}
	
	
	//Get the Attribute value for the element selected
	public String getElementAttributeValue(By locator, String attributeName) {
		String attributeValue = getElement(locator).getAttribute(attributeName);
		return attributeValue;
	}
	
	//Click - Mouse Actions
	public void doActionsClick(By locator) {
		action.click(getElement(locator)).perform();
	}
	
	
	public void doActionsSendKeys(By locator, String value) {
		action.sendKeys(getElement(locator),value).perform();
	}
	
	//Get no.of.links in the page
	public int getPageElementCount(By locator) {
		return getElements(locator).size();
	}
	
	
	//Verify if the element is displayed or not
	public boolean isElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	
	//Select Element based on Index
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	
	//Select Element based on visible text
		public void doSelectByVisibleText(By locator, String text) {
			Select select = new Select(getElement(locator));
			select.selectByVisibleText(text);
		}
		
		
	//Select Element based on visible text
		public void doSelectByValue(By locator, String value) {
			Select select = new Select(getElement(locator));
			select.selectByValue(value);
		}
		
		
		
	//Get the list of elements available in the dropdown
		public List<String> getDropDownValues(By locator) {
			Select select = new Select(getElement(locator));
			
			List<WebElement> elementList = select.getOptions();
			List<String> valueList = new ArrayList<>();
			for(WebElement e:elementList) {
				String text = e.getText();
				valueList.add(text);
			}
			
			return valueList;
		}
	
		
	//Get the count of elements available in the dropdown
		public int getDropDownValueCount(By locator) {
			Select select = new Select(getElement(locator));
			return select.getOptions().size();
		}
		
	//Select values based on the options	
		public void doSelectValueUsingOptions(By locator, String value) {
			Select select = new Select(getElement(locator));
			List<WebElement> optionsList = select.getOptions();

			for (WebElement e : optionsList) {
				String text = e.getText();
				System.out.println(text);
				if (text.equals(value)) {
					e.click();
					break;
				}
			}
		}

	
	//Select values from dropdown based on a value provided
		public void doSelectValueFromDropDown(By locator, String value) {
			List<WebElement> optionsList = getElements(locator);
			for (WebElement e : optionsList) {
				String text = e.getText();
				System.out.println(text);
				if (text.equals(value)) {
					e.click();
					break;
				}
			}
		}
		
	//ALERTS
		
	//Wait until Alert is present	
		public Alert waitForAlert(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
	
	//Get Text from the Alert
		public String getAlertText(int timeOut) {
			return waitForAlert(timeOut).getText();
		}
		
		
	//Accept alert
		public void acceptAlert(int timeOut) {
			waitForAlert(timeOut).accept();
		}
	
		
	//Dismiss alert
		public void dismissAlert(int timeOut) {
			waitForAlert(timeOut).dismiss();
		}
				
				
	//Send Keys to Alert
		public void sendKeysToAlert(int timeOut, String text) {
			waitForAlert(timeOut).sendKeys(text);
		}
				
	//Wait for title containing partial text	
		public String waitForTitleContains(int timeOut,String partialText) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			
			try {
				if(wait.until(ExpectedConditions.titleContains(partialText))) {
					return driver.getTitle();
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("No title found....");
			}
			
			return null;
		}
		

		//Wait for title containing text	
		public String waitForTitleToBe(int timeOut,String Text) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if(wait.until(ExpectedConditions.titleIs(Text))) {
					return driver.getTitle();
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("No title found....");
			}

			return null;
		}
		
				
		//Wait for title containing partial text	
		public String waitForURLContains(int timeOut,String partialUrl) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if(wait.until(ExpectedConditions.urlContains(partialUrl))) {
					return driver.getCurrentUrl();
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("No URL found....");
			}

			return null;
		}
		
		
		//Wait for title containing partial text	
		public String waitForURLToBe(int timeOut,String url) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

			try {
				if(wait.until(ExpectedConditions.urlToBe(url))) {
					return driver.getCurrentUrl();
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("No URL found....");
			}

			return null;
		}
		
		
		//Wait for Element to be present in the DOM (not necessarily visible)
		public WebElement waitForElementPresence(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}


		//Wait for Element to be visible in the DOM (displayed in UI and has height/width >0)
		public WebElement waitForElementVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}


		//Wait for Element to be present and send keys
		public void doSendKeysWithWait(By locator, int timeOut, String value) {
			WebElement ele = waitForElementVisible(locator, timeOut);
			ele.clear();
			ele.sendKeys(value);
		}


		//Click after a wait time
		public void doClickWithWait(By locator, int timeOut) {
			waitForElementVisible(locator, timeOut).click();
		}

		
		//Wait for element to be visible and then click
		public void clickElementWhenReady(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
		}
		
		
		//Wait for multiple elements to be visible
		public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		// Print all the elements
		public void printAllElementsText(By locator, int timeOut) {
			List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut);
			for (WebElement e : eleList) {
				String text = e.getText();
				System.out.println(text);
			}
		}

		// Get all the elements list
		public List<String> getAllElementsTextList(By locator, int timeOut) {
			List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut);
			List<String> eleTextList = new ArrayList<String>();
			for (WebElement e : eleList) {
				String text = e.getText();
				eleTextList.add(text);
			}
			return eleTextList;
		}
		
		
		//Wait for element with Fluent Wait
		public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(pollingTime))
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
					.withMessage("Element is not present on the page.....");

			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		
		//CUSTOM WAIT
		public static void shortWait() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

		public static void mediumWait() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

		public static void longWait() {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		public static void waitFor(int timeOut) {
			try {
				Thread.sleep(timeOut * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Wait for page to load
		public void waitForPageLoad(int timeOut) {
			long endTime = System.currentTimeMillis() + timeOut;
			while (System.currentTimeMillis() < endTime) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String pageState = js.executeScript("return document.readyState;").toString();
				System.out.println("page loading state: " + pageState);
				if (pageState.equals("complete")) {
					System.out.println("page is fully loaded with all scripts, images, css...");
					break;
				}
			}

		}
}
