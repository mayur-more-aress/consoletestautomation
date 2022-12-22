package com.netregistryoldwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGAddDomainToTransferPage extends TestBase
{
	@FindBy(how=How.XPATH, using = "//*[@id='addToCart']")
	WebElement addDomainButton;
	    
	@FindBy(how=How.XPATH, using = "/html/body/div[2]/div[1]/div[3]/div/div/div/div/form/div[1]/div[1]/div/table/tbody/tr[2]/td[3]")
	WebElement domainTransferStatus;
	
	//Initializing Page Objects
    public NRGAddDomainToTransferPage(){
        PageFactory.initElements(driver, this);
    }
	    
    public void checkDomainStatus() throws Exception
    {
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
    
    public NRGAccountContactPage addDomainToCart() throws Exception
    {
    	//checkDomainStatus();
    	addDomainButton.click();
    	return new NRGAccountContactPage();
    }
}
