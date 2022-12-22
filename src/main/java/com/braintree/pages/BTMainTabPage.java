package com.braintree.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class BTMainTabPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = "//div[@class='nav']/div[2]/ul/li[1]")
    WebElement transactionsLink;
	
    //Initializing Page Objects
    public BTMainTabPage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods    
    public BTTransactionsSearchPage clickTransactionsLink() throws InterruptedException {
    	
    	Thread.sleep(5000);
    	transactionsLink.click();
    	return new BTTransactionsSearchPage();	
    }
	
	
}
