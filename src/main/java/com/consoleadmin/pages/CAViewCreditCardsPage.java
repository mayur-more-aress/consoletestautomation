package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


import com.base.TestBase;

public class CAViewCreditCardsPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = "//table/tbody/tr[2]/td/table/tbody/tr/td/h1")
    WebElement pageHeaderName;
		
	//Initializing Page Objects
	public CAViewCreditCardsPage(){
    	PageFactory.initElements(driver, this);
    }
	
    //Methods
    public Boolean isViewCreditcardsPageDisplayed() throws InterruptedException {
    	
    	Thread.sleep(3000);
    	
    	Boolean flag = false;
    	if (pageHeaderName.getText().contentEquals("View Creditcards")) {
    		System.out.println("View Creditcards Page");
    		flag = true;
    	}
    	return flag;
    }
    
    public void updateExpiryDate(String strexpirymonth, String strexpiryyear) throws InterruptedException {
    	
    	Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@class='card-expiry']")).click();
		Thread.sleep(2000);

		driver.findElement(By.name("expiryMonth")).click();
		driver.findElement(By.xpath("//*[@name='expiryMonth']/*[contains(text(),'"+strexpirymonth+"')]")).click();
		Thread.sleep(2000);

		driver.findElement(By.name("expiryYear")).click();
		driver.findElement(By.xpath("//*[@name='expiryYear']/*[contains(text(),'20"+strexpiryyear+"')]")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//*[@name='updateCardExpiry']")).click();
		Thread.sleep(2000);

	}
    
    public String getUpdateExpiryConfirmation() throws InterruptedException {
    	
    	Thread.sleep(3000);
    	String confirmationmessage = driver.findElement(By.xpath("//*[@class='warning-box']")).getText();
		System.out.println(confirmationmessage);
		
		return confirmationmessage;
 
    }
}
