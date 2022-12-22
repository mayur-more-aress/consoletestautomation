package com.netregistryoldwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGDomainTransferPage extends TestBase
{
	//Objects 
    @FindBy(how=How.XPATH, using = "//*[@id='domainSearchBox']/div/div[1]/input")
    WebElement domainName;
    
    @FindBy(how=How.XPATH, using = "//*[@id='domainSearchBox']/div/div[3]/input")
    WebElement domainPassword;
    
    @FindBy(how=How.XPATH, using = "//*[@id='domainSearchBox']/div/div[4]/input")
    WebElement transferButton;
    
	//Initializing Page Objects
    public NRGDomainTransferPage(){
        PageFactory.initElements(driver, this);
    }
    
    public void setDomainNameAndTld(String domainname, String tldname)
    {
    	domainName.clear();
    	domainName.sendKeys(domainname); 	
    	selectTld(tldname);
    }
    
    public void setDomainPassword(String domainpassword)
    {
    	domainPassword.clear();
    	domainPassword.sendKeys(domainpassword); 	
    }
    
    public void selectTld(String tldname){
    
    	List<WebElement> tldList = driver.findElements(By.xpath("//*[@id='domainSearchBox']/div/div[2]/select/option"));
    	for (WebElement tld : tldList) {
    		if (tld.getText().equals(tldname)) 
    		{
    			tld.click();
    		}
    	}	
    }
    
    public NRGAddDomainToTransferPage clickTransferButton() throws InterruptedException
    {
    	transferButton.click();
    	Thread.sleep(10000);
    	return new NRGAddDomainToTransferPage();
    }
}
