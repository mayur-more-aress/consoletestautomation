package com.consoleadmin.pages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class CAAccountReferencePage extends TestBase{

	//Objects
    @FindBy(how=How.LINK_TEXT, using = "View Billing Accounts")
    WebElement viewBillingAccountsLink;
    
    @FindBy(how=How.LINK_TEXT, using = "Pay outstanding invoices")
    WebElement payOutstandingInvoicesLink;
		
    @FindBy(how=How.XPATH, using = "//div[@id='clientContactDetails']/table/tbody/tr[16]/td[@class='cp'][2]/form/input[@value='Update Password']")
    WebElement updatePasswordButton;
    
    @FindBy(how=How.XPATH, using = "//div[@id='clientContactDetails']/table/tbody/tr[16]/td[2]/form/table/tbody/tr[1]/td[2]/input")
    WebElement enterNewPasswordTextField;
    
    @FindBy(how=How.XPATH, using = "//div[@id='clientContactDetails']/table/tbody/tr[16]/td[2]/form/table/tbody/tr[2]/td[2]/input")
    WebElement reenterToVerifyTextField;
    
    @FindBy(how=How.LINK_TEXT, using = "View Invoice & Prepaid detail")
    WebElement clickInvoiceAndPrepaidDetail;
    
    @FindBy(how=How.XPATH, using = "//input[@value='Administer']")
    WebElement administratorButton;;
    
	//Initializing Page Objects
	public CAAccountReferencePage(){
    	PageFactory.initElements(driver, this);
    }
	
    //Methods
    public void updatePassword(String strnewpassword) throws InterruptedException, AWTException {
    	
    	//Enter New Password
    	Thread.sleep(3000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", enterNewPasswordTextField);
    	enterNewPasswordTextField.sendKeys(strnewpassword);
    	Thread.sleep(3000);
    	
    	//Reenter to Verify
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", reenterToVerifyTextField);
    	reenterToVerifyTextField.sendKeys(strnewpassword);
    	Thread.sleep(2000);
    	
    	//Click update password 
    	System.out.println ("Searching for password button");
    	Thread.sleep(8000);
    	driver.findElement(By.xpath("//tbody/tr[16]/td[@class='cp'][2]/form/input[@value='Update Password']")).click();
    	System.out.println ("Update Password button clicked");
        Thread.sleep(8000);
    }
    
    public CAViewCreditCardsPage clickViewBillingAccounts() {
    	
    	viewBillingAccountsLink.click();
    	return new CAViewCreditCardsPage();
    	
    }
    
    public CAInvoicesPage clickPayOutstandingInvoices() {
    	
    	payOutstandingInvoicesLink.click();
    	return new CAInvoicesPage();
    	
    }
    
    public CAInvoicesPage clickInvoiceAndPrepaidDetail() {
    	clickInvoiceAndPrepaidDetail.click();
    	return new CAInvoicesPage();
    }
    
    public void selectDomainName(String domainname) throws InterruptedException
   	{
   		Select domainName= new Select(driver.findElement(By.xpath("//*[@id='domainAndServiceAdministration']/table/tbody/tr[3]/td[2]/form/select")));
   		domainName.selectByVisibleText(domainname);
   		Thread.sleep(2000);
   	}
       
    public CADomainLevelPage clickOnAdministartorButton() throws InterruptedException {
       	
   	 administratorButton.click();
   	 Thread.sleep(2000);
       	return new CADomainLevelPage();
       	
       }
}
