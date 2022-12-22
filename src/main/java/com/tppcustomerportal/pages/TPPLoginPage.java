package com.tppcustomerportal.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class TPPLoginPage extends TestBase {

	//Objects
    @FindBy(how=How.NAME, using = "login")
    WebElement accountReference;

    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "submit")
    WebElement loginButton;

    
	//Initializing Page Objects
	public TPPLoginPage(){
        PageFactory.initElements(driver, this);
    }

	
    //Methods
    public void setLoginDetails(String straccountreference, String strpassword)  {
    		
    		accountReference.sendKeys(straccountreference);
	    	password.sendKeys(strpassword);
			
    }
    
    public TPPHeaderPage clickLoginButton() throws InterruptedException {

    	System.out.println("clicking login button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new TPPHeaderPage();
    	
    }

}

