package com.netregistrynewwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.relevantcodes.extentreports.ExtentTest;


public class NRGNSSearchAddDomainsPage extends TestBase{

	public static ExtentTest logger;
	//Objects
    @FindBy(how=How.CSS, using = "button.search-btn.green")
    WebElement searchButton;
    
    @FindBy(how=How.CSS, using = "button.btn.green")
    WebElement continueButton;
    
    @FindBy(how=How.CSS, using = ".domain-search-form .search-box")
    WebElement newDomainSearchBox;
    
    @FindBy(how=How.XPATH, using = "//*[@id='ngdialog1']/div[2]/div[1]/div[2]/a")
    WebElement dialogbox;
	
    //Initializing Page Objects
    public NRGNSSearchAddDomainsPage(){
    	PageFactory.initElements(driver, this);
    	 driver.manage().deleteAllCookies();
    }

 
    //Methods
    public void clickSearchButton() throws InterruptedException {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchButton);
    	searchButton.click();
       	Thread.sleep(10000);
    	
    }
    
    public NRGNSDomainPrivacyPage clickContinueButton() throws InterruptedException {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSDomainPrivacyPage();
    	
    }
    
    public NRGNSEmailAndOffice365PackagesPage clickContinueButtonToEmailHosting() throws InterruptedException 
    {
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	return new NRGNSEmailAndOffice365PackagesPage();	
    }
    
    public void clickDomainExtension(String strDomainExtension) throws Exception 
   	{
       	//Get all the tld's
   		WebElement tlddropdown = driver.findElement(By.xpath("//select[@ng-model='tlds.selectedTldCategory']/option[@label='Show All']"));
       	tlddropdown.click();
       	Thread.sleep(10000);
       	Boolean isPopUpPresent = driver.findElements(By.xpath("//*[@id='ngdialog1']/div[2]/div[1]/div[2]/a")).size() > 0;
       	if(isPopUpPresent)
       	{
       		Thread.sleep(3000);
       		dialogbox.click();	
       	}
       	
   		List<WebElement> tlds = driver.findElements(By.cssSelector(".tld-select [for]"));
       	for (WebElement tld : tlds) {  	
       		
       		if (tld.getText().equals(strDomainExtension)) {
       			System.out.println("Clicking the tld " + tld); 			
       			tld.click();
       			return;
       		}
       	}
       	throw new Exception("tld is not available");
    }
    
    public void setDomainNameAndTld(String domainname, String tldname) throws Exception{
    	Thread.sleep(15000);
    	newDomainSearchBox.clear();
    	newDomainSearchBox.sendKeys(domainname);
    	this.clickDomainExtension(tldname);
    	
		
    }
    
    public void addDomainName(String domainname) {
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.partialLinkText(domainname)));
    	WebElement domainName = driver.findElement(By.partialLinkText(domainname));
    	domainName.click();

    }
    
    //Add the domain from search result that will match provided domain name and tld
    public void addDomainName(String domainName, String tldName) throws Exception {
    	
    	//Get all the domains in search result
    	List<WebElement> domains = driver.findElements(By.cssSelector(".search-results .result"));
    	
       	for(WebElement domain: domains) {
       		String resultDomainName = domain.findElement(By.cssSelector(".domain")).getText();
    		System.out.println("This the domain name with extension " + resultDomainName);
    		
    		if (resultDomainName.equalsIgnoreCase(domainName+tldName)){
    			domain.click();
       			return;
    		}
    	} 
   
       	throw new Exception("Domain name is not available");

    }
    
    public String checkDomainNameAvailabilty(String domainName, String tldName) throws Exception 
    {
    	//Get all the domains in search result
	    String domainavailable = null;
   		String resultDomainName = driver.findElement(By.xpath("//div[@ng-repeat='item in domainItems.items']/div/h4")).getText();
   		System.out.println("The domain status is " +resultDomainName);
		if(resultDomainName.equalsIgnoreCase("Domain Registration fee"))
		{
			domainavailable = "The domain is available";
			System.out.println("The domain " + domainName + tldName+ " is available");
			
		}
		else if (resultDomainName.equalsIgnoreCase("Domain Backorder"))
		{
			domainavailable = "The domain is not available";
			System.out.println("The domain " + domainName + tldName+ " is not available");
		}
		else
		{
			domainavailable = "Unknown error occured";
			System.out.println("Unknown error occured");
		}
		return domainavailable;
    }
    
    public void selectDropdown() throws InterruptedException {
    	Thread.sleep(2000);
    	driver.findElement(By.cssSelector("[ng-model*='tlds.selectedTldCategory']")).sendKeys("New Zealand");
    }
    
}
