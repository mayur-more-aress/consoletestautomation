package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CAInvoicesPage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Pay Outstanding Invoices')]")
	WebElement PayOutstandingInvoicesLink ;

	public CAInvoicesPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public CATaxInvoicePage selectInvoiceNumber(String strinvoicenumber) throws InterruptedException {

		Thread.sleep(5000);
		driver.findElement(
				By.xpath("//*[@class='cp'][text()='" + strinvoicenumber + "']/parent::td/parent::tr/td[9]/a[2]"))
				.click();
		Thread.sleep(5000);

		return new CATaxInvoicePage();

	}

	public String getInvoiceNumber() throws InterruptedException {

		String invoicenumber;

		Thread.sleep(10000);
		// invoicenumber =
		// driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[4]/tbody/tr[2]/td[1]/a")).getText();
		invoicenumber = driver.findElement(By.xpath("//table[@class='results']/tbody/tr[2]/td[1]/a")).getText();

		Thread.sleep(5000);

		return invoicenumber;

	}

	public CAPrepaidCreidtPage clickPrepaidAccountDetails() {
		
		System.out.println("Click Prepaid Account Details link.");
		driver.findElement(By.linkText("Prepaid Account Details")).click();
		return new CAPrepaidCreidtPage();
		
	}
	
	public void clickCreatePrepaidCreditAccount() throws InterruptedException {
		Thread.sleep(2000);
		System.out.println("Click Create Prepaid Credit Account link.");
		driver.findElement(By.linkText("Create Prepaid Credit Account")).click();
	}
	
	public CATaxInvoicePage clickInvoiceID(String invoiceID) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.linkText(invoiceID)).click();
		return new CATaxInvoicePage();
	}
	
	public CATaxInvoicePage clickPayOutstandingInvoices() throws Exception 
	{
		
		Boolean isPayOutstandingLinkAvailable = driver.findElements(By.xpath("//a[contains(text(),'Pay Outstanding Invoices')]")).size() > 0;
    	if(isPayOutstandingLinkAvailable)
    	{
    		if(PayOutstandingInvoicesLink.isDisplayed())
			System.out.println("Clicking on Pay outstanding invoices link");
			PayOutstandingInvoicesLink.click();
    	}
    	else
    	{
    		System.out.println("No unpaid invoice available");
			throw new Exception("No unpaid invoice available");
    	}
		return new CATaxInvoicePage();
		
	}
	
	public String getPaymentSuccessfulmsg()
	{
		return driver.findElement(By.xpath("//*[@id='msg']")).getText();
	}
}
