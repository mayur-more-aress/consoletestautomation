package com.domainzwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class DMZOnlineOrderPage extends TestBase{

	//Objects 
    @FindBy(how=How.XPATH, using = "//div[@class='domain']/input")
    WebElement newDomainSearchBox;
    
    @FindBy(how=How.XPATH, using = "//div[@class='search']/input")
    WebElement newDomainSearchButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='tldfilter']")
    WebElement nztlds; 
    
	//Initializing Page Objects
    public DMZOnlineOrderPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setDomainNameAndTld(String domainname, String tldname){
    	newDomainSearchBox.clear();
    	newDomainSearchBox.sendKeys(domainname);
    	tickTld(tldname);
    }
    
    public void tickTld(String tldname){
    	WebElement tldUL = driver.findElement(By.xpath("//ul[@id='search-tlds']"));
    	List<WebElement> tldList = tldUL.findElements(By.tagName("li"));
    	for (WebElement li : tldList) {
    		if (li.getText().equals(tldname)) {
    			li.findElement(By.tagName("input")).click();
    		}
    	}	
    }
    
    public DMZDomainSearchPage clickNewDomainSearchButton(){
    	System.out.println("clicking new domain search button");
    	if(newDomainSearchButton.isDisplayed()||newDomainSearchButton.isEnabled()) {
    		newDomainSearchButton.click();
    	}
		else {
			System.out.println("element not found");
		}	
    	return new DMZDomainSearchPage();
    }
    
    public void clearDefaultTldSelections() {
	   	
    	new WebDriverWait(driver,30).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#search-tlds [checked]")));
    	List<WebElement> defaultTLDs = driver.findElements(By.cssSelector("#search-tlds [checked]"));
    	for (WebElement tld : defaultTLDs) {
    	     tld.click();
    	}	
    }
    
    public void selectNzTld() throws InterruptedException {
    	Thread.sleep(6000);
    	if(nztlds.isDisplayed() || nztlds.isEnabled()) {
    	nztlds.sendKeys("New Zealand");
    	}
    }
}

