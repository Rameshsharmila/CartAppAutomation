package com.qa.cartapp.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static Logger log = Logger.getLogger(DriverFactory.class);
	
	/**
	 * Initialize the Properties reference with all config properties
	 * @return prop reference
	 */
	
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		
		String envName = System.getProperty("env");
		System.out.println("Running test cases on environment: " + envName);
		log.info("Running test cases on env: " + envName);
		
		if(envName==null) {
			System.out.println("No environment is provided. Running in QA by default..");
			try {
				ip=new FileInputStream("./src/test/resources/config/qa.config.properties");				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
				try {
					switch(envName.toLowerCase()) {
					case "qa":
							ip=new FileInputStream("./src/test/resources/config/qa.config.properties");		
							break;
					case "uat":
							ip=new FileInputStream("./src/test/resources/config/uat.config.properties");		
							break;
					case "prod":
							ip=new FileInputStream("./src/test/resources/config/prod.config.properties");		
							break;
					case "stage":
							ip=new FileInputStream("./src/test/resources/config/stage.config.properties");		
							break;
					default:
							System.out.println(envName+"  is not valid. Please pass the correct environment");
							break;
					}
				}	
				catch(FileNotFoundException e) {
					e.printStackTrace();
				}
			}
				
				try {
					prop.load(ip);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				return prop;
		
		
	}

	/**
	 * This method is used to initialize the webdriver based on the browsername.
	 * @param prop
	 * @return driver
	 */

	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser");
		System.out.println("Browser name is ....." + browserName);
		log.info("Browser name is ....." + browserName);
		
		optionsManager = new OptionsManager(prop);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			log.info("Running the tests on chrome...");
			
			if(Boolean.parseBoolean(System.getProperty("remote"))) {
				//Execute tests remotely
				init_remoteWebDriver("chrome");
			} else {
				log.info("Running the tests locally...");
				WebDriverManager.chromedriver().setup();
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			
			
		} else if(browserName.equalsIgnoreCase("firefox")) {
			log.info("Running the tests on Firefox...");
			
			if(Boolean.parseBoolean(System.getProperty("remote"))) {
				//Execute tests remotely
				init_remoteWebDriver("firefox");
			} else {
				log.info("Running the tests locally...");
				WebDriverManager.firefoxdriver().setup();
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			
			
		} else if(browserName.equalsIgnoreCase("edge")) {
			log.info("Running the tests on Microsoft Edge...");
			
			if(Boolean.parseBoolean(System.getProperty("remote"))) {
				//Execute tests remotely
				init_remoteWebDriver("edge");
			} else {
				log.info("Running the tests locally...");
				WebDriverManager.edgedriver().setup();
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}	
		
			
		} else {
			System.out.println("The Browser name entered is incorrect.."+browserName);
			log.error("Incorrect browser name "+browserName +"Please pass a valid browser name");
			}
		
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		
		return getDriver();
	}
	
	
	public static synchronized WebDriver getDriver() {
			return tlDriver.get();
		}
	
	public void init_remoteWebDriver(String browserName) {
		System.out.println("Running the tests remotely on Selenium GRID");
		
		if(browserName.equals("chrome")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getChromeOptions()));
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		else if(browserName.equals("firefox")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getFirefoxOptions()));
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		else if(browserName.equals("edge")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getEdgeOptions()));
			} catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * This method takes screenshot at any given time
	 */
	
	public static String takeScreenshot(String methodName) {
		
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+ "/screenshot/" +methodName + ".png";
		
		File dest=new File(path);
		try {
			FileUtils.copyFile(srcFile, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
	
}
