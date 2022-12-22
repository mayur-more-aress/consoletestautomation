package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSCreateDomainWindowPage extends TestBase{
	
	//Objects
    @FindBy(how=How.ID, using = "create-domain-domainName-field-0.2")
    WebElement domainName;
    
    @FindBy(how=How.XPATH, using = "//div[2]/div/div/img")
    WebElement tldDropdownButton;
    
    @FindBy(how=How.XPATH, using = "//div[3]/div/div/img")
    WebElement regPeriodDropdownButton;
    
    @FindBy(how=How.XPATH, using = "//div[4]/div/div/img")
    WebElement majorProductButton;
    
    @FindBy(how=How.XPATH, using = "//div[5]/div/div/img")
    WebElement productPeriodButton;
  
    @FindBy(how=How.XPATH, using = "//div/div/div/div/div/div/input[2]")
    WebElement paymentMethodButton;
    
    @FindBy(how=How.XPATH, using = "//div/div/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[2]/em/button")
    WebElement createDomainButton;
   
    @FindBy(how=How.ID, using = "ext-gen2311")
    WebElement updateButton;
	
    //Initializing Page Objects
    public CSCreateDomainWindowPage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods
    public void setDomainDetails(String strDomainName, String strtld, String strPeriod, String strPaymentMethod) throws InterruptedException {
    	
    	//Thread.sleep(5000);
    	domainName.sendKeys(strDomainName);
    	//Thread.sleep(2000);
    	tldDropdownButton.click();
    	//Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+"."+strtld+"']")).click();
        regPeriodDropdownButton.click();
       // Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strPeriod+"')]")).click();
        
        paymentMethodButton.click();
        //Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strPaymentMethod+"')]")).click();
        //Thread.sleep(2000);
        Thread.sleep(1000);
        this.clickCreateDomainButton();
    }
 
    public void setDomainDetailswithoutRegister(String strDomainName, String strTld, String strPaymentMethod) throws InterruptedException {
    	
    	Thread.sleep(5000);
    	domainName.sendKeys(strDomainName);
    	Thread.sleep(2000);
    	tldDropdownButton.click();
    	Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+"."+strTld+"']")).click();
        paymentMethodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strPaymentMethod+"')]")).click();
        Thread.sleep(2000);
        this.clickCreateDomainButton();
    }
    
    public void setDomainandMajorProductDetails(String strDomainName, String strTld, String strPeriod, String strMajorProduct, String strProductPeriod, String strPaymentMethod) throws InterruptedException {
    	
    	//Thread.sleep(5000);
    	Thread.sleep(2000);
    	domainName.sendKeys(strDomainName);
    	//Thread.sleep(2000);
    	tldDropdownButton.click();
    	//Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+"."+strTld+"']")).click();
        regPeriodDropdownButton.click();
        //Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strPeriod+"')]")).click();
        //Thread.sleep(2000);
        majorProductButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+strMajorProduct+"']")).click();       
       Thread.sleep(2000);
       productPeriodButton.click();
       // Thread.sleep(5000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strProductPeriod+"')]")).click();
        paymentMethodButton.click();
        //Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strPaymentMethod+"')]")).click();
        Thread.sleep(2000);
        this.clickCreateDomainButton();
    }
    
    public void setDomainandMajorProductwithoutRegistration(String strDomainName, String strTld, String strMajorProduct, String strProductPeriod, String strPaymentMethod) throws InterruptedException {
    	
    	Thread.sleep(5000);
    	domainName.sendKeys(strDomainName);
    	Thread.sleep(2000);
    	tldDropdownButton.click();
    	Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+"."+strTld+"']")).click();
        Thread.sleep(2000);
        majorProductButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+strMajorProduct+"']")).click();     
        productPeriodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strProductPeriod+" AU$')]")).click();
        paymentMethodButton.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strPaymentMethod+"')]")).click();
        Thread.sleep(2000);
        this.clickCreateDomainButton();
    }

    public CSNrCRMPage clickCreateDomainButton() throws InterruptedException {
    	
    	Thread.sleep(5000);
    	System.out.println("clicking new domain (new price system)");
    	if(createDomainButton.isDisplayed()||createDomainButton.isEnabled()) {
    		createDomainButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new CSNrCRMPage();
    }

}
