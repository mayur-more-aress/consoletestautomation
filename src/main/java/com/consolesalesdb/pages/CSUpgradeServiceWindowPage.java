package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSUpgradeServiceWindowPage extends TestBase{

	//Objects  
    @FindBy(how=How.ID, using = "upgrade-service-buttun")
    WebElement upgradeService;
    
    @FindBy(how=How.XPATH, using = "//div[3]/div/div/img")
    WebElement mainProductDropdownButton;
    
    @FindBy(how=How.XPATH, using = "//div[4]/div/div/img")
    WebElement productPeriodDropdownButton;
    
    //Initializing Page Objects
    public CSUpgradeServiceWindowPage() throws InterruptedException {
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setUpgradeProduct (String strupgradeproduct, String strperiod) throws InterruptedException {    	
    	Thread.sleep(3000);  	
    	mainProductDropdownButton.click();
    	Thread.sleep(3000); 
    	driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[text()='"+strupgradeproduct+"']")).click(); 
    	productPeriodDropdownButton.click();
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/div[contains(text(),'"+strperiod+" x M AU$')]")).click();        
    }
    
    public CSNotificationPage clickUpgradeService() throws InterruptedException {
    	Thread.sleep(3000);
    	upgradeService.click();
    	return new CSNotificationPage();
    }
}
