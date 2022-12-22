package com.rrpproxypage.java;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class RRPProxyLoginPage extends TestBase {

	//Objects
	@FindBy(how = How.ID, using = "username")
	WebElement username;
	
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'password')]")
	WebElement password;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Sign in now')]")
	WebElement signInButton;
	
	//Initializing Page Objects
	public RRPProxyLoginPage(){
	       PageFactory.initElements(driver, this);
	}

	//Methods
	public void setLoginDetails(String rrpstrusername, String rrpstrpassword){
		username.sendKeys(rrpstrusername);
		password.sendKeys(rrpstrpassword);
	}
	
	public RRPTabPage clickLoginButton(){
		System.out.println("clicking sign in button");
		if(signInButton.isDisplayed()||signInButton.isEnabled()) {
			signInButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new RRPTabPage();
	}
}
