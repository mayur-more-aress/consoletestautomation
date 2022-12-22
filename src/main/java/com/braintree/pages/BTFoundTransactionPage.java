package com.braintree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class BTFoundTransactionPage extends TestBase{

	//Objects

	//Initializing Page Objects
	public BTFoundTransactionPage(){
		PageFactory.initElements(driver, this);
	}

	//Methods    
    public Boolean isTransactionIDFound() throws InterruptedException {
        
    	Boolean flag = false;
    	Thread.sleep(2000);

    	if (driver.findElement(By.xpath("//header[@class='header_group']/h2")).getText().contentEquals("Found 1 Transaction")) {
    		System.out.println("Transaction ID Found");
    		flag = true;
    	}
    	return flag;
    }
    
    public Boolean isCreditCardFound() throws InterruptedException {
        
    	Boolean flag = false;
    	Thread.sleep(2000);

    	if (!driver.findElement(By.xpath("//header[@class='header_group']/h2")).getText().contentEquals("Found 0 Transaction")) {
    		System.out.println("Credit Card Record Found");
    		flag = true;
    	}
    	return flag;
    }
    
    public BTTransactionDetailForIDPage clickTransactionIDInTable(String transactionid) throws InterruptedException {
        
    	Thread.sleep(1000);
    	WebElement transactionIDLink = driver.findElement(By.linkText(transactionid));
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", transactionIDLink);
    	
    	Thread.sleep(1000);
    	transactionIDLink.click();
    	
    	return new BTTransactionDetailForIDPage();
    }
    
    public String getLatestTransactionIDInTable() throws InterruptedException {
        
    	Thread.sleep(5000);
    	String latestTransactionID = driver.findElement(By.xpath("//table[@class='sep']/tbody/tr[1]/td[1]/a")).getText();
    	
    	Thread.sleep(1000);
    	WebElement latestTransactionIDLink = driver.findElement(By.xpath("//table[@class='sep']/tbody/tr[1]/td[1]/a"));
    	//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", latestTransactionIDLink);
    	
    	System.out.println("Latest Transaction ID: " + latestTransactionID);
    	return latestTransactionID;
    
    }
    
    public String getTransactionIDStatus(String transactionid) throws InterruptedException{
    	
    	String transactionidstatus = null;
    	Thread.sleep(1000);

    	transactionidstatus= driver.findElement(By.xpath("//a[text()='"+transactionid+"']/parent::td/parent::tr/td[4]")).getText();
    	System.out.println("Transaction ID Status: " + transactionidstatus);
    	return transactionidstatus;		
    }
    
    public String getTransactionIDAmount(String transactionid) throws InterruptedException{
    	
    	String transactionidamount = null;
    	Thread.sleep(1000);

    	transactionidamount= driver.findElement(By.xpath("//a[text()='"+transactionid+"']/parent::td/parent::tr/td[7]")).getText();
    	System.out.println("Transaction ID Amount: " + transactionidamount);
    	return transactionidamount;		
    }
    
    

}