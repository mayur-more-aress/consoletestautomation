package com.netregistryoldwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;



public class NRGLoginPage extends TestBase {

	//Objects
    @FindBy(how=How.NAME, using = "login")
    WebElement accountReference;

    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "submit")
    WebElement loginButton;

    WebDriverWait wait = new WebDriverWait(driver,50);
	//Initializing Page Objects
	public NRGLoginPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setLoginDetails(String straccountreference, String strpassword) throws InterruptedException {
    
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("login")));
    	accountReference.sendKeys(straccountreference);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
    	password.sendKeys(strpassword);	
			
    }
    
    public NRGHeaderPage clickLoginButton() throws InterruptedException {

    	System.out.println("clicking submit button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new NRGHeaderPage();
    	
    }

}
