package com.netregistrynewwebsite.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class NRGNSSearchFieldPage extends TestBase{
	
	
	//Objects
    @FindBy(how=How.ID, using = "initdomain")
    WebElement searchBox;

    @FindBy(how=How.CLASS_NAME, using = "btn")
    WebElement submitButton;
    
    @FindBy(how=How.LINK_TEXT, using = "Renew a domain")
    WebElement renewDomain;
    
    @FindBy(how=How.LINK_TEXT, using = "Transfer your domain to us")
    WebElement transferDomain;
    
    @FindBy(how=How.LINK_TEXT, using="Bulk domain search")
    WebElement bulkDomainSearch;
    
	
	//Initializing Page Objects
    public NRGNSSearchFieldPage(){
    	PageFactory.initElements(driver, this);
    }

    
    //Methods
    public void setDomain(String strDomainName) {
    	
    	searchBox.sendKeys(strDomainName);
    	
    }
    
    public void clickSubmitButton() {

    	submitButton.click();
    	
    }
    
    public NRGNSSearchAddDomainsPage searchDomain(String strDomainName) throws InterruptedException {

    	this.setDomain(strDomainName);
    	this.clickSubmitButton();
    	
    	return new NRGNSSearchAddDomainsPage();
 
    }
    
}

