package com.webcentralwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class WCRegistrantContactPage extends TestBase {

	//Objects             
    @FindBy(how=How.NAME, using = "saveRegistrant")
    WebElement continueButton;

    //Initializing Page Objects
    public WCRegistrantContactPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods    
    public WCBillingPage clickContinueButton(){
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new WCBillingPage();
    }
	
  public void clickDomainInformation(String domaininformation) throws InterruptedException {
    	
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//div[@class='instore']/div/div/form//div[1]/input[@value='"+ domaininformation + "']")).click();
    }
}
