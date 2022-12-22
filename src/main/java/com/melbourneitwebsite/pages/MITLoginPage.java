package com.melbourneitwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class MITLoginPage extends TestBase {
	//Objects
    @FindBy(how=How.NAME, using = "login")
    WebElement accountReference;

    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "submit")
    WebElement loginButton;

    WebDriverWait wait = new WebDriverWait(driver,70);
	//Initializing Page Objects
	public MITLoginPage(){
        PageFactory.initElements(driver, this);
    }
	

    //Methods
    public void setLoginDetails(String straccountreference, String strpassword) throws InterruptedException {
    		/*Thread.sleep(5000);
    		if(accountReference.isDisplayed()||accountReference.isEnabled()) {
    		System.out.println("entering account reference");
    		accountReference.sendKeys(straccountreference);
    		}else {
    			System.out.println("element not found");
    		}
	    	Thread.sleep(2000);
	    	if(password.isDisplayed()||password.isEnabled()) {
	    		System.out.println("entering password");
	    	password.sendKeys(strpassword);
	    	}else {
				System.out.println("element not found");
			}*/
	    	
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("login")));
	    	accountReference.sendKeys(straccountreference);
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
	    	password.sendKeys(strpassword);	
    }
    
    public MITHeaderPage clickLoginButton() throws InterruptedException {
    	Thread.sleep(2000);
    	System.out.println("clicking submit button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    		Thread.sleep(3000);
    	}
		else {
			System.out.println("element not found");
		}

    	return new MITHeaderPage();
    	
    }

}
