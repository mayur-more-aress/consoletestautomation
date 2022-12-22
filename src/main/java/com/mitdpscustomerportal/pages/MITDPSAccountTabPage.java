package com.mitdpscustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITDPSAccountTabPage extends TestBase
{
	//Objects
    @FindBy(how=How.XPATH, using = "(//span[contains(text(),'Account')])[1]")
    WebElement AccountTabLink;

    @FindBy(how=How.XPATH, using = "//a[contains(text(),'Cancel services')]")
    WebElement cancelServicesLink;
    
    @FindBy(how = How.XPATH, using = ".//div[@id='cc-details']/a")
   	WebElement editCreditCardsOnFileButton;


	//Initializing Page Objects
	public MITDPSAccountTabPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickAccountTabLink() throws InterruptedException {
    	AccountTabLink.click();
	    Thread.sleep(2000);	
    }
    
    public MITDPSCancelServicesPage clickOnCancelServicesLink() throws InterruptedException {

    	System.out.println("clicking on cancel services link");
    	if(cancelServicesLink.isDisplayed()||cancelServicesLink.isEnabled()) {
    		cancelServicesLink.click();
    		Thread.sleep(2000);
    	}
		else {
			System.out.println("element not found");
		}

    	return new MITDPSCancelServicesPage();
    	
    }
    
    public MITDPSCreditCardsDetailsPage clickEditCreditCardsOnFile() throws InterruptedException {

    	Thread.sleep(2000);
		if (editCreditCardsOnFileButton.isDisplayed() || editCreditCardsOnFileButton.isEnabled()) {
			editCreditCardsOnFileButton.click();
		} else {
			System.out.println("element not found");
		}

		return new MITDPSCreditCardsDetailsPage();
	}
    
    public void makeCardDefault(String cardowner) throws InterruptedException {
    	Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='cc-table']/tbody/tr[td//text()[contains(., '"+ cardowner +"')]]/td[5]/*")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@name='removeOrMakeDefaultCreditCard']")).click();
		Thread.sleep(2000);	
    }
    
    public void verifyDefaultCreditCard()
	{
		System.out.println("Default Credit card details:");
		String creditCardNumber=driver.findElement(By.xpath("//*[@id='sidebar']/div[1]/div[2]/p[1]")).getText();
		System.out.println(creditCardNumber);
		String cardExpiry=driver.findElement(By.xpath("//*[@id='sidebar']/div[1]/div[2]/p[2]")).getText();
		System.out.println(cardExpiry);
	}
	}

