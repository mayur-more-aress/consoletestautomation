package com.braintree.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class BTLoginPage extends TestBase{

	
	static String stageUserName = "makeyanvesh";
	static String stagePassword = "ArqPayments505^";

	//Objects 
    @FindBy(how=How.ID, using = "login")
    WebElement userName;
    
    @FindBy(how=How.ID, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "commit")
    WebElement loginButton;
    
    //Initializing Page Objects
    public BTLoginPage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods
    public void setDefaultLoginDetails(String environment) throws InterruptedException {
	    
		if(environment.equalsIgnoreCase("stage")) {
	    	userName.sendKeys(stageUserName);
	    	password.sendKeys(stagePassword);
		}
		else if (environment.equalsIgnoreCase("production")) {
			//Param to be defined	
		}
    }
    
    public BTMainTabPage clickLoginButton() throws InterruptedException {

    	System.out.println("clicking login button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new BTMainTabPage();	
    }
	
}
