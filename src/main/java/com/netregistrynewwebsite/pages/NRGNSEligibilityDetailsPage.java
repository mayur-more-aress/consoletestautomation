package com.netregistrynewwebsite.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class NRGNSEligibilityDetailsPage extends TestBase 
{
	//Objects 
		@FindBy(how=How.XPATH, using = "//label[@for='busCert']")
		WebElement certifyDomainHasCloseAndSubstantialConnection;
		
		@FindBy(how=How.XPATH, using = "//*[contains(text(),'I agree to the terms outlined in the policy above and in the ')]")
		WebElement agreeTermsAndConditions;
		
		@FindBy(how=How.XPATH, using = "//div[@class='btn btnBg green']")
		WebElement continueButton;
		
		@FindBy(how=How.XPATH, using = "//input[@ng-model='form.element.busNoValue.value']")
		WebElement eligibilityIdNumber;

	   public NRGNSEligibilityDetailsPage()
	   {
	        PageFactory.initElements(driver, this);
	   }
	   
	//Methods     
	    public void setEligibilityDetails(String eligibilityidtype, String eligibilityidnumber, String eligibilitytype)
	    {
	    	driver.findElement(By.xpath("//select[@id='busNoType']/option[@label='" +eligibilityidtype + "']")).click();
	    	eligibilityIdNumber.sendKeys(eligibilityidnumber);
	    	driver.findElement(By.xpath("//select[@id='busType']/option[@label='" +eligibilitytype + "']")).click();
	    }
	    
	    public void tickCertifyDomainHasCloseAndSubstantialConnection()
	    {
	       	System.out.println("tick certify domain has close and substantial connection");
	       	if(certifyDomainHasCloseAndSubstantialConnection.isDisplayed()||certifyDomainHasCloseAndSubstantialConnection.isEnabled())
	       	{
	       		certifyDomainHasCloseAndSubstantialConnection.click();
	       	}
	    	else 
	    	{
	    		System.out.println("element not found");
	    	}
	    }
	    
	    public void tickTermsAndConditions() throws InterruptedException, AWTException
	    {
	       	System.out.println("tick agree to terms and conditions");
	       	Robot r = new Robot();
	       	Thread.sleep(2000);
	       	System.out.println("Tabbing");
	       	r.keyPress(KeyEvent.VK_TAB);
	       	Thread.sleep(2000);
	       	System.out.println("Clicking");
	       	WebElement ele;
	       	ele = driver.switchTo().activeElement();	
	       	Thread.sleep(2000);
	       	ele.sendKeys(Keys.SPACE);
	    }
	 
	    public NRGNSAddServicesToYourDomainPage clickContinueButton() throws InterruptedException
	    {
	    	System.out.println("clicking continue button");
	    	if(continueButton.isDisplayed()||continueButton.isEnabled())
	    	{
	    		Thread.sleep(2000);
	    		continueButton.click();
	    		continueButton.click();
	    	}
			else 
			{
				System.out.println("element not found");
			}    	
	    	return new NRGNSAddServicesToYourDomainPage();
	    }
}
