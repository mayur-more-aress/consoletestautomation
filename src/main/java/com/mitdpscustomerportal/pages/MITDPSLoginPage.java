package com.mitdpscustomerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.mitdpsresellerportal.pages.MITDPSTabPage;

import com.base.TestBase;

public class MITDPSLoginPage extends TestBase
{
	//Objects
    @FindBy(how=How.NAME, using = "login")
    WebElement accountReference;

    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "submit")
    WebElement loginButton;
    
    @FindBy(how=How.NAME, using = "button.login")
    WebElement loginbutton;

	//Initializing Page Objects
	public MITDPSLoginPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setLoginDetails(String straccountreference, String strpassword) throws InterruptedException {
    
    		accountReference.sendKeys(straccountreference);
	    	Thread.sleep(2000);
	    	password.sendKeys(strpassword);
			
    }
    
    public MITDPSHeaderPage clickLoginButton() throws InterruptedException {

    	System.out.println("clicking submit button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new MITDPSHeaderPage();
    	
    }
    
    public MITDPSTabPage clickOnLoginButton() throws InterruptedException {

    	System.out.println("clicking submit button");
    	if(loginbutton.isDisplayed()||loginbutton.isEnabled()) {
    		loginbutton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new MITDPSTabPage();
    	
    }
}
