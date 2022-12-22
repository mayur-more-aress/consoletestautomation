package com.melbourneitwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITWebHostingPage extends TestBase{

	//Objects         

	
    //Initializing Page Objects
    public MITWebHostingPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods  
    public void addCPanelStarter (String subscription){
    	System.out.println("adding cpanel starter in the cart");
    	WebElement productDropdown = driver.findElement(By.xpath("//div[@class='CPANEL-STARTER']/div[2]/form/div/span"));
    	if(productDropdown.isDisplayed()||productDropdown.isEnabled()) {
    		productDropdown.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
	
}
