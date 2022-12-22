package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;



public class DMZLoginPage extends TestBase {

	//Objects
    @FindBy(how=How.NAME, using = "login")
    WebElement accountReference;

    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "submit")
    WebElement loginButton;

	//Initializing Page Objects
	public DMZLoginPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setLoginDetails(String straccountreference, String strpassword) throws InterruptedException {
    
    		accountReference.sendKeys(straccountreference);
	    	Thread.sleep(2000);
	    	password.sendKeys(strpassword);
			
    }
    
    public DMZHeaderPage clickLoginButton() throws InterruptedException {

    	System.out.println("clicking submit button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new DMZHeaderPage();
    	
    }

}
