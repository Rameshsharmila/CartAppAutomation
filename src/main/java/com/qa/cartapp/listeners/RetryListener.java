package com.qa.cartapp.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Check if test failed -> Check if maxtry count has reached
 * 	If no, increase the maxtrycount, mark test as failed and have testng rerun the test
 * If yes, mark the test as failed
 * 
 * If test is passed, have testng mark as passed.
 */
public class RetryListener implements IRetryAnalyzer {
	
	private int count = 0;
	private static int maxTry = 3;
	
	
	@Override
	public boolean retry(ITestResult iTestResult) {
		if (!iTestResult.isSuccess()) { 
			if (count < maxTry) { 
				count++; 
				iTestResult.setStatus(ITestResult.FAILURE); 
				return true; 
			} else {
				iTestResult.setStatus(ITestResult.FAILURE); 
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS); 
		}
		return false;
		
	}
	
}
