package com.netregistrynewwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class NRGNSDomainPrivacyPage extends TestBase{

	
	//Objects    
    @FindBy(how=How.CSS, using = ".privacy-domains .privacy-toggle-row")
    public static WebElement checkBox;
    
    @FindBy(how=How.CSS, using = "button.btn.green")
   public  static WebElement continueButton;
    
    
    //Initializing Page Objects
    public NRGNSDomainPrivacyPage(){
    	PageFactory.initElements(driver, this);
    }

 
    //Methods
	public void clickCheckBox() throws InterruptedException {

		Thread.sleep(5000);
    	List<WebElement> tlds = driver.findElements(By.cssSelector(".privacy-domains [ng-if]"));
    	for (WebElement tld : tlds) 
    	{  		
    		Thread.sleep(5000);
    		tld.click();
    	}
    	return;
    	
    }
    
    
    public NRGNSEmailAndOffice365PackagesPage clickContinueButton() throws InterruptedException {

    	Thread.sleep(3000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSEmailAndOffice365PackagesPage();
    	
    }
    
}


