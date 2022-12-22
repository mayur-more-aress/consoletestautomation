package com.mitdpscustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITDPSAllServicesPage extends TestBase
{
	//Objects
    @FindBy(how=How.XPATH, using = "//*[@id='allservices-table']/tbody/tr[1]/td[5]/a")
    WebElement paymentLink;

    @FindBy(how=How.XPATH, using = "//*[@id='cc-table']/tbody/tr[1]/td[5]/input")
    WebElement existingcardRadioButton;
    
    @FindBy(how=How.XPATH, using = "//input[@value='Select card']")
    WebElement selectCardButton;

	//Initializing Page Objects
	public MITDPSAllServicesPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickOnPaymentLink() throws InterruptedException {
    	paymentLink.click();
	    Thread.sleep(2000);	
    }
    
    public void clickOnExistingCardRadioButton() throws InterruptedException {
    	existingcardRadioButton.click();
	    Thread.sleep(2000);	
    }
    
    public void clickOnSelectCardButton() throws InterruptedException {

    	System.out.println("clicking on cancel services link");
    	if(selectCardButton.isDisplayed()||selectCardButton.isEnabled()) {
    		selectCardButton.click();
    		Thread.sleep(2000);
    	}
		else {
			System.out.println("element not found");
		}
    }
    
    public String getSuccessMessage() {
		String getSuccessMessage = null;
		getSuccessMessage = driver.findElement(By.xpath("//*[@class='success-box']")).getText();
		return getSuccessMessage;

	}

  
}
