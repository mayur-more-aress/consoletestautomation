package com.braintree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class BTTransactionsSearchPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = "//form[@id='advanced_search_form']/p/input")
    WebElement searchButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='text_field_inputs']/div[1]/select/optgroup[1]/option[1]")
    WebElement searchFieldForTransactionID;
    
    
    @FindBy(how=How.XPATH, using = "//div[@class='text_field_inputs']/div[1]/select/optgroup[3]/option[3]")
    WebElement searchFieldForCreditCardNumber;
	
    //Initialize
    public BTTransactionsSearchPage() throws InterruptedException {
        PageFactory.initElements(driver, this);
    }
    
    //Methods    
    public void searchTransactionID(String transactionid) throws InterruptedException {
    	
    	Thread.sleep(3000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchFieldForTransactionID);	
    	searchFieldForTransactionID.click();
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//div[@class='text_field_inputs']/input")).sendKeys(transactionid);
    	Thread.sleep(3000);
    }
    
    public void searchCreditCardNumber(String cardnumber) throws InterruptedException {
    	
    	Thread.sleep(3000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchFieldForCreditCardNumber);	
    	searchFieldForCreditCardNumber.click();
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//div[@class='text_field_inputs']/input")).sendKeys(cardnumber);
    	Thread.sleep(3000);
    }
    
    
    public BTFoundTransactionPage clickSearchButton() throws InterruptedException {

    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchButton);
    	System.out.println("clicking search button");
    	
    	if(searchButton.isDisplayed()||searchButton.isEnabled()) {
    		searchButton.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new BTFoundTransactionPage();
    	
    }
    
    
	
}
