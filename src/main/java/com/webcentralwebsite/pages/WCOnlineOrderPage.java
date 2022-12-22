package com.webcentralwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class WCOnlineOrderPage extends TestBase{

	
	//Objects 
    @FindBy(how=How.XPATH, using = "//div[@class='domain']/input")
    WebElement newDomainSearchBox;
    
    @FindBy(how=How.XPATH, using = "//div[@class='search']/input")
    WebElement newDomainSearchButton;
    
	//Initializing Page Objects
    public WCOnlineOrderPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setDomainNameAndTld(String domainname, String tldname){
    	newDomainSearchBox.clear();
    	newDomainSearchBox.sendKeys(domainname);
    	tickTld(tldname);
    	
    }
    
    public void clearDefaultTldSelections(){
    	tickTld(".com");
    	tickTld(".com.au");
    	tickTld(".net.au");
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
    
    public WCDomainSearchPage clickNewDomainSearchButton() throws InterruptedException{
//    	try {
//    		Thread.sleep(2000);
//			driver.findElement(By.xpath("//*[@id='chatNow']/div/a[1]/img")).click();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.out.println("Chat window not present");
//		}
    	Thread.sleep(2000);
    	System.out.println("clicking new domain search button");
    	if(newDomainSearchButton.isDisplayed()||newDomainSearchButton.isEnabled()) {
    		newDomainSearchButton.click();
    	}
		else {
			System.out.println("element not found");
		}	
    	return new WCDomainSearchPage();
    }
}
