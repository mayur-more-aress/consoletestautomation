package com.netregistryoldwebsite.pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class NRGHostingAndExtrasPage extends TestBase{

	//Objects         
	 @FindBy(how=How.XPATH, using = "//input[@value='Continue'][1]")
	    WebElement continueButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[1]/input")
    WebElement webHostingRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[2]/input")
    WebElement websiteRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[3]/input")
    WebElement emailHostingRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[4]/input")
    WebElement domainManagerRadioButton;

	@FindBy(how=How.XPATH, using = "//div[@class='domainAdd']/form/input[2]")
    WebElement addHostingButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='CLOUD-BASIC']/div[2]/form/div/div[1]")
    WebElement basicCouldHosting;
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-EESEN-QTY']/div[2]/form/div/div[1]")
    WebElement o365EmailEssentials;
           
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BESSEN-QTY']/div[2]/form/div/div[1]")
    WebElement o365BusinessEssentials;
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BPREM-QTY']/div[2]/form/div/div[1]")
    WebElement o365BusinessPremium;
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BUSINESS']/div[2]/form/div/div[1]")
    WebElement o365Business;
    
    @FindBy(how=How.XPATH, using = "//*[@id='CLOUD-BASIC']/div[2]/form/div/div[2]/div[2]/span[1]")
    WebElement monthlyProduct;
    
    @FindBy(how=How.XPATH, using = "//*[@id=\"CLOUD-BASIC\"]/div[2]/form/div/div[2]/div[3]/span[2]")
    WebElement yearlyProduct;
    
    @FindBy(how=How.XPATH, using = "//input[@id ='upgrade-warning-agreement']")
    WebElement upgradeWarningCheckBox;
    
    @FindBy(how=How.XPATH, using = "//input[@type='submit' and @name='save']")
    WebElement warningContinueButton;
    
    @FindBy(how=How.ID, using = "group-security")
    WebElement selectAddOnCheckBox;
    
    @FindBy(how=How.XPATH, using = "//*[@id=\"SITE-SECURITY\"]/div[2]/form/div/div[1]")
    WebElement selectSecurityAddOn;
  
    @FindBy(how=How.XPATH, using = "//*[@id=\"TDM-FEE\"]/div[2]/form/div/div[1]")
    WebElement selectDomainManager;
    
    @FindBy(how=How.XPATH, using = "//*[@id=\"WHOIS-PRIV\"]/div[2]/form/div/div[1]")
    WebElement selectDomainPrivacy;
  
  
    //Initializing Page Objects
    public NRGHostingAndExtrasPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickWebHostingRadioButton(){
    	System.out.println("clicking web hosting radio button");
    	if(webHostingRadioButton.isDisplayed()||webHostingRadioButton.isEnabled()) {
    		webHostingRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void addSeoAddOn() {
    	driver.findElement(By.xpath("//*[@id=\"TOPTIMISER\"]/div[2]/form/div/div[1]")).click();
    	driver.findElement(By.xpath("//*[@id=\"TOPTIMISER\"]/div[2]/form/div/div[2]/div[2]")).click();
    }
    
    public void clickWebsiteRadioButton(){
    	System.out.println("clicking website radio button");
    	if(websiteRadioButton.isDisplayed()||websiteRadioButton.isEnabled()) {
    		websiteRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickEmailHostingRadioButton(){
    	System.out.println("clicking email hosting radio button");
    	if(emailHostingRadioButton.isDisplayed()||emailHostingRadioButton.isEnabled()) {
    		emailHostingRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickDomainManagerRadioButton(){
    	System.out.println("clicking domain manager radio button");
    	if(domainManagerRadioButton.isDisplayed()||domainManagerRadioButton.isEnabled()) {
    		domainManagerRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void addCPanelStarter (String subscription) throws InterruptedException{
    	System.out.println("adding cpanel starter in the cart");
    	
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//*[@id='CPANEL-STARTER']/div[2]/form/div/div[1]")).click();
    	Thread.sleep(3000);
    	List<WebElement> subscriptionYears=driver.findElements(By.xpath("//*[@id='CPANEL-STARTER']/div[2]/form/div/div[2]/div/span[@class='period']"));
    	for(WebElement year : subscriptionYears)
    	{
    		if(year.getText().contains(subscription))
    		{
    			Thread.sleep(3000);
    			year.click();
    			Thread.sleep(3000);
    			break;
    		}
    	}
    }
       
    public NRGAccountContactPage clickContinueButton() throws InterruptedException {
    	
    	//Thread.sleep(8000);
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
    	jse.executeScript("window.scrollBy(0,-500)");
    	Thread.sleep(3000);
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new NRGAccountContactPage();
    }

    
	public NRGAddHostingPage clickAddHostingButton(){
    	System.out.println("clicking add hosting button");
    	if(addHostingButton.isDisplayed()||addHostingButton.isEnabled()) {
    		addHostingButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new NRGAddHostingPage();
    }
    
    
    public NRGAddHostingPage selectBasicCloudHosting() throws InterruptedException {
    	System.out.println("Selecting hosting product");
    	try {
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", basicCouldHosting);
			basicCouldHosting.click();
			monthlyProduct.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
		}
    	
		return new NRGAddHostingPage();  	
    }
    
    
	public NRGAddHostingPage selectBasicCloudHosting(String strPeriod) throws InterruptedException {
    	System.out.println("Selecting hosting product");
    	
    	try {
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", basicCouldHosting);
			basicCouldHosting.click();
	        List<WebElement> opt = driver.findElements(By.xpath("//div[@id='CLOUD-BASIC']/div[2]/form/div//div[@class='combo-list']//div//span[1][@class='period']"));
	       // System.out.println(opt.size());
	        
	        for(int i=0;i<=opt.size();i++)
	        {
	        	System.out.println(opt.get(i).getText());
	        	if(opt.get(i).getText().contains(strPeriod))
	        	{
	        		opt.get(i).click();
	        		System.out.println(strPeriod);
	        	}
	        }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
			Thread.sleep(3000);
		}
    	
      return new NRGAddHostingPage();   	
    }
	
	
	public NRGAddHostingPage selectOffice365(String strTypeOffice365, String strPeriod) throws InterruptedException {
    	System.out.println("Selecting email hosting");
    	    	  	
    	try {
			Thread.sleep(5000);
			if (strTypeOffice365 == "o365EmailEssentials")
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", o365EmailEssentials);
			      o365EmailEssentials.click();
		        List<WebElement> opt = driver.findElements(By.xpath("//div[@id='O365-EESEN-QTY']/div[2]/form/div//div[@class='combo-list']//div//span[1][@class='period']"));
		        System.out.println(opt.size());
		        
		        for(int i=0;i<=opt.size();i++)
		        {
		        	System.out.println(opt.get(i).getText());
		        	if(opt.get(i).getText().contains(strPeriod))
		        	{
		        		opt.get(i).click();
		        		System.out.println(strPeriod);
		        		return new NRGAddHostingPage();
		        	}
		        }			
			}
			if (strTypeOffice365 == "o365BusinessEssentials")
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", o365BusinessEssentials);
			      o365BusinessEssentials.click();
		        List<WebElement> opt = driver.findElements(By.xpath("//div[@id='O365-BESSEN-QTY']/div[2]/form/div//div[@class='combo-list']//div//span[1][@class='period']"));
		        System.out.println(opt.size());
		        
		        for(int i=0;i<=opt.size();i++)
		        {
		        	System.out.println(opt.get(i).getText());
		        	if(opt.get(i).getText().contains(strPeriod))
		        	{
		        		opt.get(i).click();
		        		System.out.println(strPeriod);
		        		return new NRGAddHostingPage();
		        	}
		        }				
			}
			
			if (strTypeOffice365 == "o365BusinessPremium")
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", o365BusinessPremium);
			      o365BusinessPremium.click();
		        List<WebElement> opt = driver.findElements(By.xpath("//div[@id='O365-BPREM-QTY']/div[2]/form/div//div[@class='combo-list']//div//span[1][@class='period']"));
		        System.out.println(opt.size());
		        
		        for(int i=0;i<=opt.size();i++)
		        {
		        	System.out.println(opt.get(i).getText());
		        	if(opt.get(i).getText().contains(strPeriod))
		        	{
		        		opt.get(i).click();
		        		System.out.println(strPeriod);
		        		return new NRGAddHostingPage();
		        	}
		        }
			}
		

			if (strTypeOffice365 == "o365Business")
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", o365Business);
			      o365Business.click();
		        List<WebElement> opt = driver.findElements(By.xpath("//div[@id='O365-BUSINESS']/div[2]/form/div//div[@class='combo-list']//div//span[1][@class='period']"));
		        System.out.println(opt.size());
		        
		        for(int i=0;i<=opt.size();i++)
		        {
		        	System.out.println(opt.get(i).getText());
		        	if(opt.get(i).getText().contains(strPeriod))
		        	{
		        		opt.get(i).click();
		        		System.out.println(strPeriod);
		        		return new NRGAddHostingPage();
		        		
		        	}
		        }
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
			Thread.sleep(3000);
		}
    	
      return new NRGAddHostingPage();   	
    }	
	
    
    public NRGAddHostingPage selectBasicCloudHostingYearly() throws InterruptedException {
    	System.out.println("Selecting hosting product");
    	try {
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", basicCouldHosting);
			basicCouldHosting.click();
			yearlyProduct.click();
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
		}
    	
		return new NRGAddHostingPage();  	
    }
    
    
    public NRGBillingPage clickContinueButtonToBillingPage() {
	     
		System.out.println("Clicking continue button to billing page");
		
		try {
	    	Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='chatNow']/div/a[1]/img")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Chat window not present");
		}
			
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) { 

    		continueButton.click();
    	} 
    	else {
    		System.out.println("Element not found"); 
    	}
    	
    	try {
		    Thread.sleep(2000);
		  	System.out.println("Clicking continue button ON warning to billing page");
        	if(upgradeWarningCheckBox.isDisplayed() || upgradeWarningCheckBox.isEnabled()) {
        		upgradeWarningCheckBox.click();
        		System.out.println("Warning button closed");
        	}
         	else { 
         		System.out.println("Element not found");
         	}
        	if (warningContinueButton.isDisplayed() || warningContinueButton.isEnabled()){
        		warningContinueButton.click(); 
        	}
         	else { 
         		System.out.println("Element not found");
         	}	    		
    	}
    	catch (Exception e) {
    		System.out.println("Warning Window Not Present");
    	} 
    	
    	return new NRGBillingPage();
    }
    public void addCPanelStarter1Month (String subscription) throws InterruptedException{
    	System.out.println("adding cpanel starter in the cart");
    	Thread.sleep(4000);
    	WebElement productDropdown = driver.findElement(By.xpath("//div[@id='CPANEL-STARTER']/div/form/div/span"));
		productDropdown.click();
    	driver.findElement(By.xpath("//*[@id=\"CPANEL-STARTER\"]/div[2]/form//*[contains(text(),'"+subscription+"')]")).click();
    	
    }
    
    public void selectAddOnCheckbox() {
    	if(selectAddOnCheckBox.isDisplayed()||selectAddOnCheckBox.isEnabled()) {
    	selectAddOnCheckBox.click();
    	}else {
    		System.out.println("Could not find element");
    	}
    }
    
    public void selectSecurityAddOn(String subscription) {
    	if(selectSecurityAddOn.isDisplayed()||selectSecurityAddOn.isEnabled()) {
    	selectSecurityAddOn.click();
    	}else {
    		System.out.println("Element not found");
    	}
    	driver.findElement(By.xpath("//*[@id='SITE-SECURITY']/div[2]/form//*[contains(text(),'"+subscription+"')]")).click();
    }
    
    public void addDomainManager(String subscription) {
    	if(selectDomainManager.isDisplayed()||selectDomainManager.isEnabled()) {
    	selectDomainManager.click();
    	}else {
    		System.out.println("Element not found");
    	}
    	driver.findElement(By.xpath("//*[@id='TDM-FEE']/div[2]/form//*[contains(text(),'"+subscription+"')]")).click();
    }
    
    public void addDomainPrivacy(String subscription) {
    	if(selectDomainPrivacy.isDisplayed()||selectDomainPrivacy.isEnabled()) {
    	selectDomainPrivacy.click();
    	}else {
    		System.out.println("Element not found");
    	}
    	driver.findElement(By.xpath("//*[@id='WHOIS-PRIV']/div[2]/form//*[contains(text(),'"+subscription+"')]")).click();
    }
    
    public void selectDomainName(String regDomainName) throws InterruptedException
    {
    	int index =1;
    	Thread.sleep(8000);
    	List<WebElement> lstDomainNames = driver.findElements(By.xpath("//*[@id='domain-list']/select/option"));
    	System.out.println("list of domains : " +lstDomainNames);
    	for(WebElement domainname : lstDomainNames)
    	{
    		int pos=index++;
    		System.out.println("Inside for loop");
    		driver.findElement(By.xpath("//*[@id='domain-list']/select")).click();
    		System.out.println("selecting domain from list :" +domainname.getText());
			System.out.println("Register domain Name :" +regDomainName);
    		if(domainname.getText().equalsIgnoreCase(regDomainName))
    		{
    			Thread.sleep(8000);
    			driver.findElement(By.xpath("//*[@id='domain-list']/select/option["+pos+"]")).click();
    			break;
    		}
    		 
    	}
    }
}
