package com.webcentralwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class WCAccountPage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = ".//*[@id='sidebar']/div/div[2]/ul/li[5]/a")
	WebElement cancelServicesLink;

	@FindBy(how = How.XPATH, using = ".//div[@id='cc-details']/a")
	WebElement editCreditCardsOnFileButton;

	// Initializing Page Objects
	public WCAccountPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
//    public ConsoleClientCancelServicesPage clickCancelServicesLink() throws InterruptedException {
//
//    	System.out.println("clicking cancel services");
//    	if(cancelServicesLink.isDisplayed()||cancelServicesLink.isEnabled()) {
//    		cancelServicesLink.click();
//    	}
//		else {
//			System.out.println("element not found");
//		}
//
//    	return new ConsoleClientCancelServicesPage(driver);
//    	
//    }

//	public ConsoleClientCancelServicesPage editCreditCardsOnFile() throws InterruptedException {
//
//
//  	
//  }

	// Methods
	public WCCreditCardsDetailsPage clickEditCreditCardsOnFile() throws InterruptedException {

		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editCreditCardsOnFileButton);
		System.out.println("clicking edit credit cards on file");
		if (editCreditCardsOnFileButton.isDisplayed() || editCreditCardsOnFileButton.isEnabled()) {
			editCreditCardsOnFileButton.click();
		} else {
			System.out.println("element not found");
		}

		return new WCCreditCardsDetailsPage();

	}

	public void makeCardDefault(String cardowner) throws InterruptedException {

		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//*[@id='cc-table']/tbody/tr[td//text()[contains(., '" + cardowner + "')]]/td[5]/*")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@name='removeOrMakeDefaultCreditCard']")).click();
		Thread.sleep(2000);
	}
}
