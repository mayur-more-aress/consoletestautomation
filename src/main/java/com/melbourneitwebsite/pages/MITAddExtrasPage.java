package com.melbourneitwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITAddExtrasPage extends TestBase{

	//Objects         

	
    //Initializing Page Objects
    public MITAddExtrasPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public MITHostingAndExtrasPage clickAddProduct(String productname){
    	System.out.println("clicking add button for " + productname);
    	WebElement addProductButton = driver.findElement(By.xpath("//h3[text()='"+productname+"']/ancestor::div[@class='product']/div[2]/input"));

    	if(addProductButton.isDisplayed()||addProductButton.isEnabled()) {
    		addProductButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new MITHostingAndExtrasPage();
    }
}
