package com.domainzwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZAddHostingPage extends TestBase{
	
	//Objects         

	
    //Initializing Page Objects
    public DMZAddHostingPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public DMZHostingAndExtrasPage clickAddProduct(String productname){
    	System.out.println("clicking add button for " + productname);
    	WebElement addProductButton = driver.findElement(By.xpath("//h3[text()='"+productname+"']/ancestor::div[@class='product']/div[2]/input"));

    	if(addProductButton.isDisplayed()||addProductButton.isEnabled()) {
    		addProductButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new DMZHostingAndExtrasPage();
    }

}
