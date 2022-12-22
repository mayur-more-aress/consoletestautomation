package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSShowDomainServicesPage extends TestBase {

	//Objects  
    @FindBy(how=How.XPATH, using = "//button[contains(text(),'Confirm All Services')]")
    WebElement confirmAllServices;
        
    @FindBy(how=How.XPATH, using ="//table/tbody/tr/td[2]/em/*[contains(text(),'Add')]")
    WebElement addOnProductAddButton;
    
    @FindBy(how=How.XPATH, using = "//form[@method='POST']/div[6]/div/div/div/div[1]/div/div/div/div/div/img") 
    WebElement addOnProductDropdownButton;
    
    @FindBy(how=How.XPATH, using = "//button[contains(text(),'OK')]")
    WebElement okButton;
    
    //Initializing Page Objects
    public CSShowDomainServicesPage() throws InterruptedException {
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setAddOnProduct (String straddonproduct) throws InterruptedException {    	
    	Thread.sleep(3000);  	
    	addOnProductDropdownButton.click();
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//div[@class='x-combo-list-item']/*[contains(text(),'"+straddonproduct+"')]")).click();
    	Thread.sleep(3000);
    	addOnProductAddButton.click();
    	Thread.sleep(3000);
    }
    
    public void setAddOnQuantity (String straddonname, String straddonquantity) throws InterruptedException {
    	Thread.sleep(3000);  	
    	driver.findElement(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-6'][text()='"+straddonname+"']/parent::td/parent::tr/td[12]/div[@class='x-grid3-cell-inner x-grid3-col-11']")).click();;
    	Thread.sleep(2000);	
    	driver.findElement(By.xpath("//div[@class='x-layer x-editor x-small-editor x-grid-editor']/input")).clear();
    	driver.findElement(By.xpath("//div[@class='x-layer x-editor x-small-editor x-grid-editor']/input")).sendKeys(straddonquantity);
    	driver.findElement(By.xpath("//div[@class='x-layer x-editor x-small-editor x-grid-editor']/input")).sendKeys(Keys.ENTER);
    	Thread.sleep(1000);	
    }
    
    public CSWorkflowNotificationPage clickConfirmAllServices() throws InterruptedException {
    	//Thread.sleep(10000);
    	Thread.sleep(2000);
    	confirmAllServices.click();
    	return new CSWorkflowNotificationPage();
    }
    
//    public void getAddOnProductList() throws InterruptedException {
//    	
//    	Thread.sleep(3000);  	
//    	addOnProductDropdownButton.click();
//    	Thread.sleep(3000);
//    	driver.findElement(By.xpath("//div[@class='x-combo-list-item']/*[contains(text(),'"+straddonproduct+"')]")).click();
//    	Thread.sleep(3000);
//    	addOnProductAddButton.click();
//    	Thread.sleep(3000);
// 	
//    }

}
