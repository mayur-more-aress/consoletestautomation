package com.consoleadmin.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class CATaxInvoicePage extends TestBase{
	
	// Objects
	@FindBy(how = How.NAME, using = "cardOwner")
	WebElement cardOwner;

	@FindBy(how = How.NAME, using = "cardType")
	WebElement cardType;

	@FindBy(how = How.XPATH, using = "//*[@name='cardType']/*[contains(text(),'Master')]")
	WebElement selectCardType;

	@FindBy(how = How.NAME, using = "cardNumber")
	WebElement cardnumber;

	@FindBy(how = How.NAME, using = "expiryMonth")
	WebElement cardExpiryMonth;

	@FindBy(how = How.NAME, using = "expiryYear")
	WebElement cardExpiryYear;

	@FindBy(how = How.XPATH, using = "//*[@type='submit'][@value='Pay Invoice']")
	WebElement btnPayInvoice;
	
	@FindBy(how = How.XPATH, using = "//*[@type='submit'][@value='Pay Invoices']")
	WebElement btnPayInvoices;
	
    @FindBy(how=How.LINK_TEXT, using = "Invoices")
    WebElement invoicesLink;

    @FindBy(how = How.XPATH, using = "//*[@name='description']")
	WebElement invoiceDescription;
    
    @FindBy(how = How.XPATH, using = "//*[@name='amount']")
	WebElement invoiceAmount;

    @FindBy(how = How.XPATH, using = "//*[@name='amount']/following::input")
	WebElement btnSubmit;
  
    @FindBy(how = How.XPATH, using = "//*[@id='msg']")
	WebElement txtSuccessMessage;
    
	public CATaxInvoicePage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setQuestCreditCardDetails(String strcardowner, String strcardnumber, String strcardexpirymonth, String strcardexpiryyear) throws InterruptedException {
		
		cardOwner.sendKeys(strcardowner);
		Thread.sleep(2000);
		cardType.click();
		Thread.sleep(1000);
		selectCardType.click();
		Thread.sleep(1000);
		cardnumber.sendKeys(strcardnumber);
		Thread.sleep(1000);
		cardExpiryMonth.sendKeys(strcardexpirymonth);
		Thread.sleep(1000);
		cardExpiryYear.sendKeys(strcardexpiryyear);
		Thread.sleep(1000);
	}
	
	public void setBTCreditCardDetails(String strcardowner, String strcardnumber, String strcardexpirymonth, String strcardexpiryyear) throws InterruptedException {
		
		Thread.sleep(2000);
		cardOwner.sendKeys(strcardowner);
		
		Thread.sleep(2000);
		driver.switchTo().frame(driver.findElement(By.xpath("//td[@class='cp']/div[@id='cardNumber']/iframe")));
		driver.findElement(By.xpath("//form/input")).sendKeys(strcardnumber);  
    	driver.switchTo().defaultContent();
		
		Thread.sleep(1000);
	   	driver.switchTo().frame(driver.findElement(By.xpath("//td[@class='cp']/div[@id='cardExpirationMonth']/iframe")));
    	driver.findElement(By.xpath("//form/select")).sendKeys(strcardexpirymonth);
    	driver.switchTo().defaultContent();
		
    	Thread.sleep(1000);
    	driver.switchTo().frame(driver.findElement(By.xpath("//td[@class='cp']/div[@id='cardExpirationYear']/iframe")));
    	driver.findElement(By.xpath("//form/select")).sendKeys(strcardexpiryyear);
    	driver.switchTo().defaultContent();
		
    	Thread.sleep(1000);
    	driver.switchTo().frame(driver.findElement(By.xpath("//td[@class='cp']/div[@id='cardCvv']/iframe")));
    	driver.findElement(By.xpath("//form/input")).sendKeys("888"); 
    	driver.switchTo().defaultContent();
    	Thread.sleep(1000);		
	}

	public void payInvoice() throws InterruptedException {

		btnPayInvoice.click();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		Thread.sleep(2000);
	}
	
	public String getInvoicePaymentConfirmation() throws InterruptedException {

		String confirmationmessage = driver.findElement(By.xpath("//*[@id=\"msg\"]")).getText();
		System.out.println(confirmationmessage);
		
		return confirmationmessage;
	}
	
    public CAInvoicesPage clickInvoicesLink() {
    	
    	invoicesLink.click();
    	return new CAInvoicesPage();
    	
    }
    
    public void enterDescription(String strDescription) throws InterruptedException {
		Thread.sleep(2000);
		invoiceDescription.sendKeys(strDescription);
	}
    
    public void enterInvoiceAmount(String strAmount) throws InterruptedException {
    	Thread.sleep(2000);
    	invoiceAmount.sendKeys(strAmount);
    }
	
    public void clickSubmitButton() throws InterruptedException {
    	Thread.sleep(2000);
    	btnSubmit.click();
    }
    
    public String getRefundConfirmationMessage() throws InterruptedException {
    	Thread.sleep(2000);
    	String successMessage = txtSuccessMessage.getText();
    	return successMessage;
    }
    
    public void creditOffProduct(String productAmount) throws InterruptedException {
    	
    	Select ddp = new Select(driver.findElement(By.name("invoiceItemId")));
    	ddp.selectByIndex(0);
    	invoiceDescription.sendKeys("Refund to Credit Card");
    	invoiceAmount.sendKeys(productAmount);
    	btnSubmit.click();
    	Thread.sleep(2000);
	}
    
 public void creditOffDomain(String domainAmount, String productAmount) throws InterruptedException {	
	 
	 float f1 = Float.parseFloat(domainAmount);
		float f2 = Float.parseFloat(productAmount);
		Float f3 = f1-f2;
		System.out.println(f3);
		String amount = f3.toString();
	//	return amount;
		
    	Select ddp = new Select(driver.findElement(By.name("invoiceItemId")));
    	ddp.selectByIndex(1);
    	invoiceDescription.sendKeys("Refund to Credit Card");
    	
    	invoiceAmount.sendKeys(amount);
    	btnSubmit.click();
    	Thread.sleep(2000);
	}
    
 	public void creditOffDomainFromSalesdb(String domainAmount, String productAmount) throws InterruptedException {	
	 
	 float f1 = Float.parseFloat(domainAmount);
		float f2 = Float.parseFloat(productAmount);
		Float f3 = f1-f2;
		System.out.println(f3);
		String amount = f3.toString();
	//	return amount;
		
    	Select ddp = new Select(driver.findElement(By.name("invoiceItemId")));
    	ddp.selectByIndex(0);
    	invoiceDescription.sendKeys("Refund to Credit Card");
    	
    	invoiceAmount.sendKeys(amount);
    	btnSubmit.click();
    	Thread.sleep(2000);
	}
 	
 	public void creditOffProductFromSalesdb(String productAmount) throws InterruptedException {
    	
    	Select ddp = new Select(driver.findElement(By.name("invoiceItemId")));
    	ddp.selectByIndex(1);
    	invoiceDescription.sendKeys("Refund to Credit Card");
    	invoiceAmount.sendKeys(productAmount);
    	btnSubmit.click();
    	Thread.sleep(2000);
	}
 	
 	public void clearCheckBoxes() throws InterruptedException
	{
		Thread.sleep(5000);
		List<WebElement> ckeckboxes = driver.findElements(By.xpath("//form/table[1]/tbody/tr/td[7]/input"));
		for(WebElement check : ckeckboxes)
		{
			Thread.sleep(1000);
			check.click();
		}
	}
 	
 	public void selectMultipleInvoices(String invoicenumber) throws Exception
	{
		int index =2;
		Thread.sleep(5000);
		List<WebElement> invoices = driver.findElements(By.xpath("//form/table[1]/tbody/tr/td[1]"));
		if(invoices.isEmpty())
		{
			System.out.println("No unpaid invoice available");
			throw new Exception("No unpaid invoice available");
		}
		else
		{
			for(WebElement invoice : invoices)
			{
			    int invoiceid=index++;
			    System.out.println("Invoice index is" +invoiceid);
			    
				if(invoice.getText().equalsIgnoreCase(invoicenumber))
				{
					Thread.sleep(1000);
					driver.findElement(By.xpath("//form/table[1]/tbody/tr["+invoiceid+"]/td[7]/input")).click();
					break;
				}
			}
		}
		
	}
	
	public CAInvoicesPage clickPayInvoice() throws InterruptedException
	{
		btnPayInvoices.click();
		Thread.sleep(5000);
		return new CAInvoicesPage();
	}	
}
