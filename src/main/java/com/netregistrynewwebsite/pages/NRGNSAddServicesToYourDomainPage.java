package com.netregistrynewwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSAddServicesToYourDomainPage extends TestBase{

	
	//Objects        
    @FindBy(how=How.CSS, using = "button.btn.green")
    public static WebElement continueButton;
	
    
	//Initializing Page Objects
    public NRGNSAddServicesToYourDomainPage(){
    	PageFactory.initElements(driver, this);
    }

    
    //Methods    
    
    public void selectDomainName(String regDomainName) throws InterruptedException
    {
    	int index =1;
    	Thread.sleep(8000);
    	List<WebElement> lstDomainNames = driver.findElements(By.xpath("//*[@id='body']/div[1]/div[1]/div/div/div/div[2]/div[1]/div[1]/div[2]/select/option"));
    	System.out.println("list of domains : " +lstDomainNames);
    	for(WebElement domainname : lstDomainNames)
    	{
    		int pos = index++;
    		if(domainname.getText().equalsIgnoreCase(regDomainName))
    		{
    			Thread.sleep(8000);
    			System.out.println("selecting domain from list :" +domainname.getText());
    			driver.findElement(By.xpath("//*[@id='body']/div[1]/div[1]/div/div/div/div[2]/div[1]/div[1]/div[2]/select")).click();
    			driver.findElement(By.xpath("//*[@id='body']/div[1]/div[1]/div/div/div/div[2]/div[1]/div[1]/div[2]/select/option["+pos+"]")).click();
    			break;
    		}
    	}
    }
    
    public void addHostingAndExtrasProduct(String service, String serviceProduct, String serviceProductYear) throws Exception
    {

    	if(service.equalsIgnoreCase("Domain Manager"))
    	{
    		addDomainManagerProduct(serviceProduct, serviceProductYear);
    	}
    	if(service.equalsIgnoreCase("Web Hosting"))
    	{
    		addWebHostingProduct(serviceProduct, serviceProductYear);
    	}
    	else
    	{
    		throw new Exception("The " +service+ " Hosting service is not available");        	
    	}
    	
    }
    
    public void addWebHostingProduct(String webHostingProduct, String webHostingrProductYear) throws Exception
    {
    	int index =1;
    	WebElement hosting = driver.findElement(By.xpath("//*[@id='web-hosting']/div/div[1]/h2"));    
    	Thread.sleep(8000);
    	if(hosting.getText().equalsIgnoreCase("Web Hosting"))
    	{
    		// click the dropdown
    		driver.findElement(By.xpath("//*[@id='web-hosting']/div/div[2]/div[1]")).click();
    		Thread.sleep(8000);
    		List<WebElement> lstwebHostingProdcuts = driver.findElements(By.xpath("//*[@id='web-hosting']/div/div[2]/div[2]/div/h4"));
    		for(WebElement webHosting : lstwebHostingProdcuts)
    		{
    			int pos = index++;
    			if(webHosting.getText().contains(webHostingProduct))
    			{
    				Thread.sleep(8000);
    				// Add the cpanel product product
    	    		driver.findElement(By.xpath("//*[@id='web-hosting']/div/div[2]/div[2]/div["+pos+"]/h4")).click();
    	    		break;
    			}
    		}
    		// Select the product year
    		selectProductYear(webHostingrProductYear);
    		return;
    	}
    	else 
    	{
    		System.out.println("The " +webHostingProduct+ "service product is not available");
    	}
    }
    
    public void addDomainManagerProduct(String domainManagerProduct, String domainManagerProductYear) throws Exception
    {
    	WebElement hosting = driver.findElement(By.xpath("//div[@class='cart-box prod prod-manager ng-isolate-scope']/div/div/h2"));      
    	if(hosting.getText().equalsIgnoreCase("Domain Manager"))
    	{
    		// click the dropdown
    		driver.findElement(By.xpath("//div[@class='cart-box prod prod-manager ng-isolate-scope']/div/div[2]")).click();
    		// Add the domain manager product
    		driver.findElement(By.xpath("//div[@class='cart-box prod prod-manager ng-isolate-scope']/div/div[2]/div[2]/div/h4")).click();
    		// Select the product year
    		selectProductYear(domainManagerProductYear);
    		return;
    	}
    	else 
    	{
    		System.out.println("The " +domainManagerProduct+ "service product is not available");
    	}
    }
    
    public void selectProductYear(String serviceProductYear) throws Exception 
    {
    	List<WebElement> serviceProductYears = driver.findElements(By.xpath("//div[@class='table cs-item ng-scope'][1]/div[2]/div/div[@class='cell cs-options']/select/option[@label]"));
		for (WebElement productyear : serviceProductYears) 
    	{	
    		if (productyear.isDisplayed()) 
			{
				if(productyear.getText().equalsIgnoreCase(serviceProductYear))
				{
					productyear.click();
					Thread.sleep(8000);
					break;
				}
			}	
    		else
    		{
        	throw new Exception(serviceProductYear+" Product year is not available");
    		}
    	}
		
    }
    
    public NRGNSReviewAndPaymentPage clickContinueButton() throws InterruptedException {

    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSReviewAndPaymentPage();
    	
    }
    
}
