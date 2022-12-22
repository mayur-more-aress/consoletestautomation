package com.netregistryoldwebsite.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGOnlineOrderAccountDetailsPage extends TestBase {

	//Objects 
	
	@FindBy(how=How.XPATH, using = "//input[@name = 'upgradeLogin']")
    WebElement loginButton;
	
	@FindBy(how=How.XPATH, using = "//input[@name='username']")
    WebElement accountReference; 
	
	@FindBy(how=How.XPATH, using = "//input[@name='password']")
    WebElement password;
	
	//Initializing Page Objects
    public NRGOnlineOrderAccountDetailsPage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods
    
    public void setLoginDetails(String straccountreference, String strpassword) throws InterruptedException{
    	accountReference.sendKeys(straccountreference); 
    	Thread.sleep(2000); 
    	password.sendKeys(strpassword); 
    }
    
    public NRGHostingAndExtrasPage clickLoginButton() throws InterruptedException {

    	System.out.println("clicking submit button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
    	else {
    		System.out.println("element not found");
    	}
    	return new NRGHostingAndExtrasPage();
    }
}
