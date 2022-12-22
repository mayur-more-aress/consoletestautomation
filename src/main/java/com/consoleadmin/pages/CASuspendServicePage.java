package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class CASuspendServicePage extends TestBase{
		
	//Objects
    @FindBy(how=How.XPATH, using = "(//input[@name='notifyCustomer'])[2]")
    WebElement radioButtonNotifyCustomer;

    @FindBy(how=How.XPATH, using = "//textarea[@name='dontNotifyCustomerReason']")
    WebElement textdontNotifyCustomerReason;
    
    @FindBy(how=How.XPATH, using = "//input[@name='suspendWithoutNotification']")
    WebElement suspendAndDontSendNotificationButton;
    
    @FindBy(how=How.XPATH, using = "//textarea[@name='saInfo']")
    WebElement textTechnicalDetails;
    
    @FindBy(how=How.XPATH, using = "//input[@name='suspendWithNotification']")
    WebElement suspendAndSendNotificationButton;
  
	    
    //Initializing Page Objects
    public CASuspendServicePage(){
    	PageFactory.initElements(driver, this);
    }
	    
    //Methods
    public void clickOnDoNotNotifyCustomerButton()
    {
    	radioButtonNotifyCustomer.click();
    }
    
    public void enterdontNotifyCustomerReason(String reason)
    {
    	textdontNotifyCustomerReason.sendKeys(reason);
    }
   
    public void seletctTheSuspensionReason(String suspensionReason){
    	//WebElement suspensionReason = null;
    	if (suspensionReason.contentEquals("Website hacked")) {
    		driver.findElement(By.xpath("(//input[@name='suspensionReasonId'])[1]")).click();
    	}
    	else if (suspensionReason.contentEquals("Phishing")) {
    		driver.findElement(By.xpath("(//input[@name='suspensionReasonId'])[2]")).click();
    	}
    	else if (suspensionReason.contentEquals("Disk space abuse")) {
    		driver.findElement(By.xpath("(//input[@name='suspensionReasonId'])[3]")).click();
    	}
    	else if (suspensionReason.contentEquals("Resource abuse")) {
    		driver.findElement(By.xpath("(//input[@name='suspensionReasonId'])[4]")).click();
    	}
    	else if (suspensionReason.contentEquals("Spam")) {
    		driver.findElement(By.xpath("(//input[@name='suspensionReasonId'])[5]")).click();
    	}
    	else if (suspensionReason.contentEquals("Content complaint")) {
    		driver.findElement(By.xpath("(//input[@name='suspensionReasonId'])[6]")).click();
    	}
    	else if (suspensionReason.contentEquals("Spamvertising")) {
    		driver.findElement(By.xpath("(//input[@name='suspensionReasonId'])[7]")).click();
    	}
    	else {
    		System.out.println("Suspension reason not found");
    	}

    }

	public void clickOnSuspendAndDontSendNotificationButton(){
       	System.out.println("clicking on Suspend And Dont Send NotificationButton");
       	if(suspendAndDontSendNotificationButton.isDisplayed()||suspendAndDontSendNotificationButton.isEnabled()) {
       		suspendAndDontSendNotificationButton.click();
       	}
    	else {
    		System.out.println("element not found");
    	}       	
    }
	
	public void enterTechnicalDetails(String technicaldetails)
	{
		textTechnicalDetails.sendKeys(technicaldetails);
	}
	
	public CADomainLevelPage clickOnSuspendAndSendNotificationButton(){
       	System.out.println("clicking on Suspend and Send Notification Button");
       	if(suspendAndSendNotificationButton.isDisplayed()||suspendAndSendNotificationButton.isEnabled()) {
       		suspendAndSendNotificationButton.click();
       	}
    	else {
    		System.out.println("element not found");
    	}      
       	return new CADomainLevelPage();
    }
	
}
