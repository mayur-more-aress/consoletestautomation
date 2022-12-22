package com.melbourneitwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITAddDomainToTransferPage extends TestBase
{
	@FindBy(how=How.XPATH, using = "//*[@id='addToCart']")
	WebElement addDomainButton;
	    
	//Initializing Page Objects
    public MITAddDomainToTransferPage(){
        PageFactory.initElements(driver, this);
    }
	    
    public void checkDomainStatus() throws Exception
    {
    	Thread.sleep(20000);
    	WebElement domainTransferStatus = driver.findElement(By.xpath("/html/body/div/div[3]/div[1]/div/div[3]/div/div/div/div/form/div[1]/div[1]/div/table/tbody/tr[2]/td[3]"));
    	String domainStatus = domainTransferStatus.getText();
    	if(domainStatus.equalsIgnoreCase("Registered"))
    	{
    		System.out.println("The Domain can be transffered");
    	}
    	else
    	{
    		System.out.println("The Domain cannot be transffered");
    		throw new Exception("Domain cannot be transffered");
    	}
    }
    
    public MITAccountContactPage addDomainToCart() throws Exception
    {
    	checkDomainStatus();
    	addDomainButton.click();
    	return new MITAccountContactPage();
    }
}
