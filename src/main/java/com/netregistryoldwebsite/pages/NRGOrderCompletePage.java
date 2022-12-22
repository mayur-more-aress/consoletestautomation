package com.netregistryoldwebsite.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class NRGOrderCompletePage extends TestBase{

	//Objects   
	@FindBy(how=How.XPATH, using = "//div/div[@class='alignCompleteOrder']/h1")
	WebElement orderStatus;
	
	@FindBy(how=How.XPATH, using = "//div[@id='completeOrder']/p[1]")
	WebElement orderCompleteMessage; 
	
	//Initializing Page Objects
	public NRGOrderCompletePage(){
		
		PageFactory.initElements(driver, this);
		
	}

	//Methods
    public Boolean isOrderComplete() throws InterruptedException{
    	Boolean flag = false;
    	System.out.println("Now in order complete page");
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderStatus);
  
    	Thread.sleep(10000);
    	if (orderStatus.getText().contentEquals("Order Complete")) {
    		System.out.println("Order Complete Status");
    		if (orderCompleteMessage.getText().contentEquals("Your order has been submitted successfully. Print out this page as a reference for your order confirmation. The reference ID(s) for your domain(s) are also listed below.")) {
    			System.out.println(orderCompleteMessage.getText());
    			flag = true;
    		}
    	}
    	return flag;
    }
    
    public String getAccountReferenceID() throws InterruptedException{
		String accountReference = null;
		
		Thread.sleep(10000);
		
    	WebElement accountReferenceElement = driver.findElement(By.xpath("//div[@id='completeOrder']/p[2]"));
		if (accountReferenceElement.isDisplayed()) {
			accountReference = accountReferenceElement.getText().substring(27);
    	}
    	return accountReference;
    }
    	
	public String getSingleReferenceID() throws InterruptedException{
		String referenceIDNumber = null;
		
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='shoppingCart']/tbody/tr[2]/td[1]")));
		
    	WebElement referenceIdNumberElement = driver.findElement(By.xpath("//table[@id='shoppingCart']/tbody/tr[2]/td[1]"));
		if (referenceIdNumberElement.isDisplayed()) {
			referenceIDNumber = referenceIdNumberElement.getText(); 
    	}
    	return referenceIDNumber;
    }
	
	 public ArrayList<String> getMultipleReferenceID() throws InterruptedException 
	 {
		ArrayList<String> referenceIDNumber = new ArrayList<String>();
		Thread.sleep(5000);
		List<WebElement> referenceIdNumberElement = driver.findElements(By.xpath("//table[@id='shoppingCart']/tbody/tr/td[1]"));
		for (WebElement refID : referenceIdNumberElement)
		{  	
			Thread.sleep(2000);
			if (refID.isDisplayed()) 
			{
				if(refID.getText().isEmpty())
				{
					System.out.println("The refid is empty ");
				}
				else
				{
					referenceIDNumber.add(refID.getText());
				}	
			}
    	}
    	return referenceIDNumber;	
	}
	
	public String[] getMultipleReferenceIDs(Integer expectedReferenceIDCount) throws InterruptedException{
		String[] referenceIDNumber;

		referenceIDNumber = new String[expectedReferenceIDCount];
		
		Thread.sleep(5000);
		int j=2;
		for (int i=0; i<expectedReferenceIDCount; i++){
			WebElement referenceIdNumberElement = driver.findElement(By.xpath("//table[@id='shoppingCart']/tbody/tr["+ j +"]/td[1]"));
			if (referenceIdNumberElement.isDisplayed()) {
				referenceIDNumber[i] = referenceIdNumberElement.getText(); 
	    	}
			j=j+2;
		}
    	return referenceIDNumber;
    }
}