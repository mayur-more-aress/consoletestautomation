package com.domainzwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class DMZDomainTransferPage extends TestBase {

	//Objects     
    @FindBy(how=How.CLASS_NAME, using = "domain")
    WebElement domainSearchBox;
	
	//Initializing Page Objects
    public DMZDomainTransferPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setDomainNameAndTld(String domainname, String tldname){
    	domainSearchBox.clear();
    	domainSearchBox.sendKeys(domainname);
    	
    	Select drpTld = new Select (driver.findElement(By.name("tld")));
    	drpTld.selectByVisibleText(tldname);
    }
    
    
}
