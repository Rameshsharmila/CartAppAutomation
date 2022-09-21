package com.qa.cartapp.factory;

/**
 * This class is to define and set the various options available for Chrome/Firefox and Edge browsers.
 * 
 * Get the values from the properties file
 */

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	
	private Properties prop;
	private ChromeOptions chopt;
	private FirefoxOptions ffopt;
	private EdgeOptions edopt;
	
	public static Logger log = Logger.getLogger(OptionsManager.class);
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		log.info("adding the chrome options...");
		chopt = new ChromeOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			chopt.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			chopt.addArguments("--incognito");
		}
		
		chopt.addArguments("--disable-extensions"); 
		chopt.addArguments("--window-size=1280,800");
		
		return chopt;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		log.info("adding the Firefox options...");
		ffopt = new FirefoxOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			ffopt.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			ffopt.addArguments("--incognito");
		}
		
		return ffopt;
	}
	
	public EdgeOptions getEdgeOptions() {
		log.info("adding the Microsoft Edge options...");
		edopt = new EdgeOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			edopt.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			edopt.addArguments("--incognito");
		}
		
		return edopt;
	}

}
