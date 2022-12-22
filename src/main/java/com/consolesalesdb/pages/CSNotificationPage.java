package com.consolesalesdb.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSNotificationPage extends TestBase{

	//Objects  
    @FindBy(how=How.XPATH, using = "//button[contains(text(),'OK')]")
    WebElement okButton;
	
    //Initializing Page Objects
    public CSNotificationPage() throws InterruptedException{
        PageFactory.initElements(driver, this);
    }

    //Methods    
    public CSNrCRMPage clickOK() throws InterruptedException{
    	Thread.sleep(3000);
    	okButton.click();
    	return new CSNrCRMPage();
    }
	
}
