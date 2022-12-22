package com.tppcustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class TPPOrderPage extends TestBase{
	
	
	
	//Objects 
    @FindBy(how=How.XPATH, using = "//input[@id='domain']")
    WebElement newDomainSearchBox;
    
    @FindBy(how=How.XPATH, using = "//input[@name='domainSearch']")
    WebElement newDomainSearchButton;
    
    
		//Initializing Page Objects
			public TPPOrderPage(){
		        PageFactory.initElements(driver, this);
		    }
			
			//Methods
			 public void setDomainNameAndTld(String domainname , String tldname)throws Exception{
			    	newDomainSearchBox.clear();
			    	newDomainSearchBox.sendKeys(domainname);
			    	clickDomainExtension(tldname);
			 }
			 
			 public void clickDomainExtension(String tldname)throws Exception{
				 Thread.sleep(2000);
				 Select tld = new Select(driver.findElement(By.name("tlds")));
				 Thread.sleep(2000);
				 tld.selectByValue(tldname);
						    	
			 }
					
					 
			 public TPPDomainSearchPage clickNewDomainSearchButton(){
			    	System.out.println("clicking new domain search button");
			    	if(newDomainSearchButton.isDisplayed()||newDomainSearchButton.isEnabled()) {
			    		newDomainSearchButton.click();
			    	}
					else {
						System.out.println("element not found");
					}	
			    	return new TPPDomainSearchPage();
			    }
			 
			
}
