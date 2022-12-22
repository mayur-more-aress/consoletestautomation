package com.netregistryoldwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGOrderSSLProduct extends TestBase
{
	 @FindBy(how=How.XPATH, using = "//*[@id='ssl-products']/tbody/tr/td/div/div/input[1]")
	 WebElement txtDomainName;
	  
	 @FindBy(how=How.XPATH, using = "//*[@id='ssl-products']/tbody/tr/td/div/div/input[2]")
	 WebElement btnOrder;
	 
	 @FindBy(how=How.XPATH, using = "//*[@id='csrForm']/form/div[1]/div/table/tbody/tr[3]/td[3]/input")
	 WebElement txtOrgUnit;
	 
	 @FindBy(how=How.XPATH, using = "//*[@id='csrForm']/form/div[1]/div/table/tbody/tr[4]/td[3]/input")
	 WebElement txtOrgName;
	 
	 @FindBy(how=How.XPATH, using = "//*[@id='csrForm']/form/div[1]/div/table/tbody/tr[5]/td[3]/input")
	 WebElement txtCity;
	 
	//Initializing Page Objects
    public NRGOrderSSLProduct()
    {
        PageFactory.initElements(driver, this);
    }
    
    public void selectSSLProduct(String strSSLProduct) throws InterruptedException
    {
    	int index =1;
        List<WebElement> lstProduct = driver.findElements(By.xpath("/html/body/div[2]/div[1]/div[3]/div/div[2]/div/form/div/h3/span[1]"));
        Thread.sleep(8000);
        for (WebElement product : lstProduct)
        {
        	System.out.println("product" +product);
        	System.out.println("strSSLProduct" +strSSLProduct);
            int pos = index++;
            if(product.getText().equalsIgnoreCase(strSSLProduct))
            {
            	System.out.println("strSSLProduct click radio button" +strSSLProduct);
                driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[3]/div/div[2]/div/form/div["+pos+"]/div[2]/div/input")).click();
                break;
            }
        }
        Thread.sleep(8000);
    }
    
    public void enterdomainName(String domainName)
    {
    	txtDomainName.sendKeys(domainName);
    	btnOrder.click();
    }
    
    public boolean isInfoErrorDisplayed()
    {
    	
    	String strInfoError = driver.findElement(By.xpath("//*[@id='wrapper']/div[1]/ul/li")).getText();
    	return strInfoError.contains("Please contact us");
    }
    
    public void enterCertificateDetails(String strOrgUnit, String strOrgName, String strCity)
    {
    	txtOrgUnit.sendKeys(strOrgUnit);
    	txtOrgName.sendKeys(strOrgName);
    	txtCity.sendKeys(strCity);
    	driver.findElement(By.xpath("//*[@id='internal']")).click();
    	driver.findElement(By.xpath("//*[@id='submitOrder']")).click();
    }
    
    public NRGAccountContactPage clickproccedbtn() throws InterruptedException 
    {
    	driver.findElement(By.xpath("//*[@id='csrForm']/div[2]/form/input")).click();
    	Thread.sleep(8000);
    	return new NRGAccountContactPage();
    }
}
