package com.melbourneitwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;


public class MITHostingAndExtrasPage extends TestBase{

	//Objects         
    @FindBy(how=How.XPATH, using = "//div[@class='next']/form/input[2]")
    WebElement continueButton;
    
    @FindBy(how=How.XPATH, using = "/html/body/div[1]/div[3]/div[1]/div/div[3]/div/div/div/div[1]/div[3]/div[2]/div/div[1]/label")
    WebElement webHostingRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[2]/input")
    WebElement websiteRadioButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='email-hosting']/div/div[1]/label")
    WebElement emailHostingRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[4]/input")
    WebElement domainManagerRadioButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='product-catalogue-wrapper']/div[3]/div/div[1]/label")
    WebElement webAndEmailForwarderButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='domainAdd']/form/input[2]")
    WebElement addHostingButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='CLOUD-BASIC']/div[2]/form/div/div[1]")
    WebElement basicCouldHosting;
    
    @FindBy(how=How.XPATH, using = "//*[@id='CLOUD-BASIC']/div[2]/form/div/div[2]/div[2]/span[1]")
    WebElement monthlyProduct;
    
    @FindBy(how=How.XPATH, using = "//*[@id=\"CLOUD-BASIC\"]/div[2]/form/div/div[2]/div[3]/span[2]")
    WebElement yearlyProduct;
    
    @FindBy(how=How.XPATH, using = "//*[@id='product-catalogue-wrapper']/div[2]/div/div[1]/label")
    WebElement websiteAndHostingLink; 
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-EESEN-QTY']/div[2]/form/div/div[1]")
    WebElement o365EmailEssentials;
           
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BESSEN-QTY']/div[2]/form/div/div[1]")
    WebElement o365BusinessEssentials;
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BPREM-QTY']/div[2]/form/div/div[1]")
    WebElement o365BusinessPremium;
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BUSINESS']/div[2]/form/div/div[1]")
    WebElement o365Business;
    
    @FindBy(how=How.XPATH, using = "//*[@id=\"TDM-FEE\"]/div[2]/form/div/div[1]")
    WebElement selectDomainManager;
    
    WebDriverWait wait = new WebDriverWait(driver,40);
    //Initializing Page Objects
    public MITHostingAndExtrasPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickWebHostingRadioButton() throws InterruptedException{
    	System.out.println("clicking web hosting radio button");
    	Thread.sleep(4000);
    	if(webHostingRadioButton.isDisplayed()||webHostingRadioButton.isEnabled()) {
    		Thread.sleep(2000);
    		webHostingRadioButton.click();
    		Thread.sleep(3000);
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickWebsiteRadioButton() throws InterruptedException{
    	System.out.println("clicking website radio button");
    	if(websiteRadioButton.isDisplayed()||websiteRadioButton.isEnabled()) {
    		Thread.sleep(2000);
    		websiteRadioButton.click();
    		Thread.sleep(2000);
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickEmailHostingRadioButton() throws InterruptedException{
    	System.out.println("clicking email hosting radio button");
    	if(emailHostingRadioButton.isDisplayed()||emailHostingRadioButton.isEnabled()) {
    		Thread.sleep(3000);
    		emailHostingRadioButton.click();
    		Thread.sleep(3000);
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
    public void clickWebAndEmailForwarderLink(){
    	System.out.println("clicking domain manager radio button");
    	if(webAndEmailForwarderButton.isDisplayed()||webAndEmailForwarderButton.isEnabled()) {
    		webAndEmailForwarderButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void addCPanelStarter (String subscription) throws InterruptedException
    {
    	System.out.println("adding cpanel starter in the cart");
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='CPANEL-STARTER']/div[2]/form/div/div[1]")));
    	driver.findElement(By.xpath("//*[@id='CPANEL-STARTER']/div[2]/form/div/div[1]")).click();
    	//Thread.sleep(4000);
    	//driver.findElement(By.xpath("//*[@id='CPANEL-STARTER']/div[2]/form/div/div[1]")).click();
    	Thread.sleep(4000);
    	//List<WebElement> subscriptionYears=driver.findElements(By.xpath("//*[@id='CPANEL-STARTER']/div[2]/form/div/div[2]/div/span[@class='period']"));
    	List<WebElement> subscriptionYears=driver.findElements(By.xpath("//*[@id='CPANEL-STARTER']/div[2]/form/div/div[2]/div/span[@class='period']"));
    	
    	for(WebElement year : subscriptionYears)
    	{
    		if(year.getText().contains(subscription))
    		{
    			Thread.sleep(5000);
    			year.click();
    			Thread.sleep(5000);
    			break;
    		}
    	}
    }
       
    public void addWesiteHosting(String hostingproduct) throws InterruptedException {
    	Select product = new Select(driver.findElement(By.xpath("//*[@id='CPANEL-STARTER']/div[2]/form/div/span")));
    	product.selectByVisibleText(hostingproduct);
    }
    
    public MITAccountContactPage clickContinueButton() {
    	
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new MITAccountContactPage();
    }

  //Methods
    public MITAddHostingPage clickAddHostingButton(){
    	System.out.println("clicking add hosting button");
    	if(addHostingButton.isDisplayed()||addHostingButton.isEnabled()) {
    		addHostingButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new MITAddHostingPage();
    }
    
    
    public MITAddHostingPage selectBasicCloudHosting() throws InterruptedException {
    	System.out.println("Selecting hosting product");
    	try {
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", basicCouldHosting);
			basicCouldHosting.click();
			Thread.sleep(2000);
			monthlyProduct.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
		}
    	
		return new MITAddHostingPage();
    	
    }
    
    public MITAddHostingPage selectBasicCloudHostingYearly() throws InterruptedException {
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
    	
		return new MITAddHostingPage();
    	
    }
    
    public void clickWebsiteAndHostingLink() throws InterruptedException {
    	
    	System.out.println("Click Website and Hosting Link");
    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", websiteAndHostingLink);
    	websiteAndHostingLink.click();
    }
    
    public void addCPanelStarter1Month (String subscription){
    	System.out.println("adding cpanel starter in the cart");
    	WebElement productDropdown = driver.findElement(By.xpath("//div[@id='CPANEL-STARTER']/div/form/div/span"));
		productDropdown.click();
    	driver.findElement(By.xpath("//*[@id=\"CPANEL-STARTER\"]/div[2]/form//*[contains(text(),'"+subscription+"')]")).click();
    	
    }
    
    public MITAddHostingPage selectOffice365(String strTypeOffice365, String strPeriod) throws InterruptedException {
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
		        		return new MITAddHostingPage();
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
		        		return new MITAddHostingPage();
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
		        		return new MITAddHostingPage();
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
		        		return new MITAddHostingPage();
		        		
		        	}
		        }
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
			Thread.sleep(3000);
		}
    	
      return new MITAddHostingPage();   	
    }	
    
    public void addEmailHosting(String emailproduct) throws InterruptedException {
    	Select product = new Select(driver.findElement(By.xpath("//*[@id='O365-EESEN']/div[2]/form/select")));
    	product.selectByVisibleText(emailproduct);
    }
    
    public void addDomainManager(String subscription) {
    	if(selectDomainManager.isDisplayed()||selectDomainManager.isEnabled()) {
    	selectDomainManager.click();
    	}else {
    		System.out.println("Element not found");
    	}
    	driver.findElement(By.xpath("//*[@id='TDM-FEE']/div[2]/form//*[contains(text(),'"+subscription+"')]")).click();
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
