package com.consoleadmin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class CALoginPage extends TestBase{
		
	//Objects
    @FindBy(how=How.NAME, using = "login")
    WebElement userName;

    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "submit")
    WebElement submitButton;
	    
    //Initializing Page Objects
    public CALoginPage(){
    	PageFactory.initElements(driver, this);
    }
	    
    //Methods
    public boolean validateSubmitButtonExists(){
    	return submitButton.isDisplayed();
    }
    
    public CAHeaderPage setDefaultLoginDetails(String environment) throws InterruptedException {
    	
		if(environment.equalsIgnoreCase("dev5")||environment.equalsIgnoreCase("dev8")) {
			
	    	userName.sendKeys("erwin.sukarna");
	    	password.sendKeys("comein22");
		}
		else if(environment.equalsIgnoreCase("dev4")) {
			
			userName.sendKeys("agaudron");
	    	password.sendKeys("comein22");
		}
		else if(environment.equalsIgnoreCase("uat1")||environment.equalsIgnoreCase("uat2")) {
					
			userName.sendKeys("erwin.sukarna");
	    	password.sendKeys("comein22");
		}
		else if (environment.equalsIgnoreCase("ote")) {
			
			userName.sendKeys("roy.alcantara");
	    	password.sendKeys("");
		}
		else if (environment.equalsIgnoreCase("prod")) {
			
			userName.sendKeys("indira.uppuluri");
	    	password.sendKeys("Hanuman68@");
		}
		else if (environment.equalsIgnoreCase("citc") || environment.equalsIgnoreCase("aws")) {
			
			userName.sendKeys("tester");
	    	password.sendKeys("comein22");
		}
		
		submitButton.click();
		return new CAHeaderPage();
    }
    
    public CAHeaderPage login(String strusername, String strpassword){
    	
    	userName.sendKeys(strusername);
    	password.sendKeys(strpassword);
    	submitButton.click();
    	return new CAHeaderPage();
    }
}
