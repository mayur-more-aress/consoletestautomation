package com.melbourneitwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITOnlineOrderPage extends TestBase{

	
	//Objects 
    @FindBy(how=How.XPATH, using = "//div[@class='domain']/input")
    WebElement newDomainSearchBox;
    
    @FindBy(how=How.XPATH, using = "//*[@id='multiDomainSearchBox']/table/tbody/tr[1]/td[2]/textarea")
    WebElement multiDomainSearchBox;
    
    @FindBy(how=How.XPATH, using = "//*[@id='multiDomainSearchBox']/table/tbody/tr[1]/td[3]/input")
    WebElement multiDomainSearchButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='search']/input")
    WebElement newDomainSearchButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='tldfilter']")
    WebElement nztlds; 
    
	//Initializing Page Objects
    public MITOnlineOrderPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    
    public void getMultipleDomainUrl() throws InterruptedException
    {
    	String url = driver.getCurrentUrl();
    
    	String url_new = url.replace("domain-search?start=", "multi-domain-search?start=");
    	driver.navigate().to(url_new);
    	Thread.sleep(30000);
    }
    
    public void getOrderSSLUrl() throws InterruptedException
    {
    	String url = driver.getCurrentUrl();
    
    	String url_new = url.replace("domain-search?start=", "ssl-order?start=");
    	driver.navigate().to(url_new);
    	Thread.sleep(20000);
    }
    
    public void getDomainTransferUrl() throws InterruptedException
    {
    	String url = driver.getCurrentUrl();
    
    	String url_new = url.replace("domain-search?start=", "multi-domain-transfer?start=");
    	driver.navigate().to(url_new);
    	Thread.sleep(30000);
    }
    
    public void setMultipleDomainNameandTld(String domainname, String tldname) throws InterruptedException
    {
    	Thread.sleep(2000);
    	multiDomainSearchBox.sendKeys(domainname+tldname+"\n");
    }
    
    public MITDomainSearchPage clickMultipleDomainSearchButton(){
    	System.out.println("clicking multi domain search button");
    	if(multiDomainSearchButton.isDisplayed()||multiDomainSearchButton.isEnabled()) {
    		multiDomainSearchButton.click();
    	}
		else {
			System.out.println("element not found");
		}	
    	return new MITDomainSearchPage();
    }
    
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
    
    public MITDomainSearchPage clickNewDomainSearchButton() throws InterruptedException{
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
    	return new MITDomainSearchPage();
    }
    
    public void selectShowAll() throws InterruptedException
    {
    	Thread.sleep(5000);
    	List<WebElement> optionsList = driver.findElements(By.xpath("//select[@id='tldfilter']/option"));
    	Thread.sleep(5000);
    	for (WebElement option : optionsList) {
    		if (option.getText().equalsIgnoreCase("Show All")) 
    		{
    			option.click();
    			clearDefaultTldSelections();
    		}
    	}	
    }
    
    public void selectNzTld() throws InterruptedException {
    	Thread.sleep(6000);
    	if(nztlds.isDisplayed() || nztlds.isEnabled()) {
    	nztlds.sendKeys("New Zealand");
    	}
    }
    
    public void getStoreUrl() throws InterruptedException
    {
    	String url = driver.getCurrentUrl();
    
    	String url_new = url.replace("execute2/account/newinterface/tabs/overview-tab", "store");
    	driver.navigate().to(url_new);
    	Thread.sleep(30000);
    }
}
