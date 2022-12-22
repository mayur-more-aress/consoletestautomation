package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSWorkflowNotificationPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = "//button[contains(text(),'OK')]")
    WebElement okButton;
    
    //Initializing Page Objects
    public CSWorkflowNotificationPage() throws InterruptedException {
        PageFactory.initElements(driver, this);

    }
    
    //Methods
    public String getWorkflowID() throws InterruptedException {
		//Thread.sleep(10000);
    	Thread.sleep(2000);
		String workflowId = driver.findElement(By.xpath("//*[@class='ext-mb-text']")).getText();
		String []workid = workflowId.split(": ",2);												 		 
		System.out.println("Workflow Id = "+workid[1]);
		return (workid[1]);
	}
    
    public String getNotificationMessage() throws InterruptedException {
  		//Thread.sleep(15000);
  		Thread.sleep(2000);
  		String strPopUpMessage = driver.findElement(By.xpath("//*[@class='ext-mb-text']")).getText();
		String strMessage = strPopUpMessage.substring(0, 35);
  		return strMessage;
  	}
    
    public void clickOKButton() throws InterruptedException {
    	//Thread.sleep(5000);
    	Thread.sleep(1000);
		okButton.click();
	}
}
