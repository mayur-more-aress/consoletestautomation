package com.nrgcustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;

public class NRGRegistrantNameChangePage extends TestBase
{
	//Objects
    @FindBy(how=How.XPATH, using = "//input[@name='domain']")
    WebElement textDomainName;

    @FindBy(how=How.XPATH, using = "//input[@name='password']")
    WebElement textDomainPassword;

    @FindBy(how=How.XPATH, using = "(//input[@name='search'])[1]")
    WebElement buttonContinue;

    @FindBy(how=How.XPATH, using = "//*[@id='addToCart']")
    WebElement buttonAddToSelectCart;
    
	//Initializing Page Objects
	public NRGRegistrantNameChangePage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void enterDomainName(String domainname) throws InterruptedException 
    {
    	textDomainName.sendKeys(domainname);
    }
    
    public void selectTld()
    {
    	 Select eligibilitytype= new Select(driver.findElement(By.xpath("//select[@name='tld']")));
    	 eligibilitytype.selectByVisibleText(".com.au");
    }
   
    public void enterDomainPassword(String domainpassword) throws InterruptedException 
    {
    	textDomainPassword.sendKeys(domainpassword);
    	Thread.sleep(2000);
    }
    
    public void clickOnContinueButton() throws InterruptedException {

    	System.out.println("clicking continue button");
    	if(buttonContinue.isDisplayed()||buttonContinue.isEnabled()) {
    		buttonContinue.click();
    		Thread.sleep(5000);
    	}
		else {
			System.out.println("element not found");
		}
    }
    
    public NRGRegistrantContactPage clickOnAddSelectedDomainToCartButton() throws InterruptedException {
    	System.out.println("clicking continue button");
    	if(buttonAddToSelectCart.isDisplayed()||buttonAddToSelectCart.isEnabled()) {
    		buttonAddToSelectCart.click();
    		Thread.sleep(4000);
    	}
		else {
			System.out.println("element not found");
		}
    	return new NRGRegistrantContactPage();
    }
    
}
