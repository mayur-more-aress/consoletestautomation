package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZAddDomainPrivacyPage extends TestBase{

	//Objects         
    @FindBy(how=How.XPATH, using = "//div[@class='buttonRow']/input[@value='No thanks']")
    WebElement noThanksButton;
    
	//Initializing Page Objects
    public DMZAddDomainPrivacyPage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods
    public DMZHostingAndExtrasPage clickNoThanks(){
    	System.out.println("clicking No Thanks");
    	try {
			if(noThanksButton.isDisplayed()||noThanksButton.isEnabled()) {
				noThanksButton.click();
			}
			else {
				System.out.println("element not found");
			}
		} catch (Exception e) {
			System.out.println("No such element found");
		}
    	return new DMZHostingAndExtrasPage();
    }
	
}
