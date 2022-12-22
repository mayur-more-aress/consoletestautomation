package com.netregistrynewwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSOrderPage extends TestBase
{
	// Objects
	@FindBy(how = How.XPATH, using = "//*[@id='cpanel-hosing']/div/div/div/div[1]/div[2]/div[2]/a")
	WebElement webhosting;
	
	@FindBy(how = How.XPATH, using = "//*[@id='addProductInput']")
	WebElement domainName;
	
	@FindBy(how = How.XPATH, using = "//*[@id='addProductSubmitButton']")
	WebElement searchButton;
	
	// Initializing Page Objects
	public NRGNSOrderPage() 
	{
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void selectWebhosting(String strProductYear) throws Exception 
	{
		driver.findElement(By.xpath("//*[@id='dd2MenuButton']")).click();
		Thread.sleep(8000);
		webhosting.click();
		Thread.sleep(8000);
		selectProductYear(strProductYear);
	}
	
	 public void selectProductYear(String serviceProductYear) throws Exception 
    {
    	List<WebElement> serviceProductYears = driver.findElements(By.xpath("//*[@id='body']/div[1]/div[1]/div/div/div/div[1]/div[1]/div/div/div[2]/div[2]/select/option"));
		for (WebElement productyear : serviceProductYears) 
    	{	
    		if (productyear.isDisplayed()) 
			{
				if(productyear.getText().equalsIgnoreCase(serviceProductYear))
				{
					productyear.click();
					return;
				}
			}	
    	}
    	throw new Exception(serviceProductYear+" Product year is not available");
    }
	 
	 public NRGNSEmailAndOffice365PackagesPage  enterDomainName(String strDomainName) throws Exception
	 {
		 domainName.sendKeys(strDomainName);
		 searchButton.click();
		 Thread.sleep(8000);
		 boolean domainordermsg = driver.findElement(By.xpath("//*[@id='assoc-domain-box']/div[2]/div[2]/p/b")).isDisplayed();
		 if(domainordermsg)
		 {
			 driver.findElement(By.xpath("//*[@id='assoc-domain-box']/div[2]/div[2]/div/div")).click();
		 }
		 driver.findElement(By.xpath("//*[@id='body']/div[1]/div[1]/div/div/div/div[2]/button")).click();
		 return new NRGNSEmailAndOffice365PackagesPage();
	 }
}
