package com.netregistrynewwebsite.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSOffice365LicenseQuantityPage extends TestBase{

	
	//Objects    
    @FindBy(how=How.XPATH, using = ".//*[@id='ngdialog1']/div[2]/form/input")
    WebElement businessEssentialsQuantity;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='ngdialog1']/div[2]/form/div/button")
    WebElement addToCartButton;
	
    
    //Initializing Page Objects
    public NRGNSOffice365LicenseQuantityPage(){
    	PageFactory.initElements(driver, this);
    }

 
    //Methods
    public void addBusinessEssentialsQuantity(String quantity) throws InterruptedException {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessEssentialsQuantity);
    	businessEssentialsQuantity.clear();
    	businessEssentialsQuantity.sendKeys(quantity);

    }
    
    public NRGNSEmailAndOffice365PackagesPage clickAddToCartButton() throws InterruptedException {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
    	addToCartButton.click();
    	
    	return new NRGNSEmailAndOffice365PackagesPage();
    }
    
}
