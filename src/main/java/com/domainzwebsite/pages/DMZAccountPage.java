package com.domainzwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZAccountPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = ".//*[@id='sidebar']/div/div[2]/ul/li[5]/a")
    WebElement cancelServicesLink;
    
    //Credit Cards on file
    @FindBy(how=How.XPATH, using = ".//div[@id='cc-details']/a")
    WebElement editCreditCardsOnFileButton;

	//Initializing Page Objects
	public DMZAccountPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public DMZCreditCardsDetailsPage clickEditCreditCardsOnFile() throws InterruptedException {

    	Thread.sleep(2000);
     	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editCreditCardsOnFileButton);
    	System.out.println("clicking edit credit cards on file");
    	if(editCreditCardsOnFileButton.isDisplayed()||editCreditCardsOnFileButton.isEnabled()) {
    		editCreditCardsOnFileButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new DMZCreditCardsDetailsPage(); 	
    }
    
    public void makeCardDefault() throws InterruptedException {
    	
    	Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='cc-table']/tbody/tr[td//text()[contains(., 'Test Master')]]/td[5]/*")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@name='removeOrMakeDefaultCreditCard']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='cc-table']/tbody/tr[td//text()[contains(., 'Test Visa')]]/td[5]/*")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@name='removeOrMakeDefaultCreditCard']")).click();
		Thread.sleep(2000);
    }
	
}
