package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class CSProcessTransactionPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "ext-gen2833")
	WebElement tabProcessTransaction;
	
	 @FindBy(how=How.XPATH, using = "//*[@id=\"ext-gen941\"]")
	 WebElement txtGreenCode;
	
	 @FindBy(how= How.XPATH,using ="//input[@name='invoiceId']")
	 WebElement Invoice;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'11489216')]")
	 WebElement selectInvoice;

	 @FindBy(how= How.XPATH,using ="//*[@id=\"ext-gen951\"]")
	 WebElement TransactionType;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'PAYMENT')]")
	 WebElement selectTransactionType;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'REFUND')]")
	 WebElement selectTransactionTypeRefund;
	
	 @FindBy(how= How.XPATH,using ="//*[@id=\"ext-gen957\"]")
	 WebElement PaymentType;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'Credit Card on file or new card')]")
	 WebElement selectPaymentType;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'CC refund of existing transaction')]")
	 WebElement selectPaymentTypeRefund;
	 
	 @FindBy(how= How.XPATH,using ="//input[@name='invoiceItem.amount']")
	 WebElement Amount;
	 
	 @FindBy(how= How.XPATH,using ="//*[@id=\'ext-gen2315\']")
	 WebElement existingCreditCard;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'Visa: 4111xxxxxxxx1111')]")
	 WebElement SelectExistingCreditCard;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'Create Transaction')][@type='button']")
	 WebElement CreateTransactionButton;
	 
	 @FindBy(how= How.XPATH,using ="//input[@name='txnRef']")
	 WebElement refundCard;
	 
	 @FindBy(how= How.XPATH,using ="//*[contains(text(),'NZ$-185.45')]")
	 WebElement selectrefundCardTransaction;
	 
	 @FindBy(how=How.XPATH, using = "//button[contains(text(),'OK')]")
	 WebElement okButton;
	 
	 @FindBy(how=How.XPATH, using = "//*[@name='invoiceItem.greencode']")
	 WebElement enterGreencode;
	 
	 @FindBy(how=How.XPATH, using = "//*[contains(text(),'CC refund of existing transaction')]")
	 WebElement transactionTypeCreditCard;
	 
	 @FindBy(how=How.XPATH, using = "//*[contains(text(),'AUTO-Refund to prepaid account')]")
	 WebElement transactionTypePrepaidCredit;
	 
	 @FindBy(how=How.XPATH, using = "//*[@id='ext-gen973']")
	 WebElement radiobuttonNewCard;
	 
	 @FindBy(how=How.XPATH, using = "//input[@name='ccard.cardOwner']")
	 WebElement textCardHolderName;
	 
	 @FindBy(how=How.XPATH, using = "//input[@name='ccard.unencryptedCardDigits']")
	 WebElement textCardNumber;
	 
	 @FindBy(how=How.XPATH, using = "//input[@name='ccard.cardExpire']")
	 WebElement textCardExpiry;
	 
	 @FindBy(how=How.XPATH, using = "//*[@id='ext-gen2346']")
	 WebElement textCardType;
	
	
	// Initializing Page Objects
	public CSProcessTransactionPage() {
		PageFactory.initElements(driver, this);
	}

	public void clickProcessTransaction() {
		tabProcessTransaction.click();
	}
	
	public void setProcessTransactionDetails(String strgreencode, String strinvoicenumber, String strtransactiontype, String stramount, String strcardnumber, String paymentMethod) throws InterruptedException {
		
		// Enter details on process transaction page
		System.out.println("Navigating to process transaction page");
		Thread.sleep(2000);
		enterGreencode.sendKeys(strgreencode);

		Thread.sleep(2000);
		Invoice.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(text(),'"+strinvoicenumber+"')]")).click();

		TransactionType.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(text(),'"+strtransactiontype+"')]")).click();
		Thread.sleep(2000);

		PaymentType.click();
		Thread.sleep(2000);


		if (strtransactiontype == "PAYMENT") {
			
			driver.findElement(By.xpath("//*[contains(text(),'Credit Card on file or new card')]")).click(); 
			Thread.sleep(2000);
			Amount.sendKeys(stramount);
			Thread.sleep(2000);
			existingCreditCard.click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[contains(text(),'"+strcardnumber+"')]")).click(); 
			Thread.sleep(2000);

		}
		
		else if (strtransactiontype == "REFUND"){
	
			if(paymentMethod.equalsIgnoreCase("Credit card")) {
			transactionTypeCreditCard.click();
			Thread.sleep(2000);
			Amount.sendKeys(stramount);
			Thread.sleep(5000);			
			driver.findElement(By.xpath("//input[@name='txnRef']/parent::div/img")).click();
			Thread.sleep(2000);
			refundCard.sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(2000);
			refundCard.sendKeys(Keys.ENTER);
			}else {
				transactionTypePrepaidCredit.click();
				Thread.sleep(2000);
				Amount.sendKeys(stramount);
				Thread.sleep(2000);	
			}
			Thread.sleep(2000);
		}
		
		CreateTransactionButton.click();
		Thread.sleep(2000);
		
	}
	
	public void setProcessTransactiondetails(String strgreencode, String strinvoicenumber, String strtransactiontype, String stramount, String strcardnumber, String paymentMethod,String cardholdername, String cardnumber, String cardexpiry, String cardtype) throws InterruptedException {
		
		// Enter details on process transaction page
				System.out.println("Navigating to process transaction page");
				Thread.sleep(2000);
				enterGreencode.sendKeys(strgreencode);

				Thread.sleep(2000);
				Invoice.click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[contains(text(),'"+strinvoicenumber+"')]")).click();

				TransactionType.click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[contains(text(),'"+strtransactiontype+"')]")).click();
				Thread.sleep(2000);

				PaymentType.click();
				Thread.sleep(2000);


				if (strtransactiontype == "PAYMENT") {
					
					driver.findElement(By.xpath("//*[contains(text(),'Credit Card on file or new card')]")).click(); 
					Thread.sleep(2000);
					Amount.sendKeys(stramount);
					Thread.sleep(2000);
					enterNewCardDetails(cardholdername, cardnumber, cardexpiry, cardtype);

				}
				
				else if (strtransactiontype == "REFUND"){
			
					if(paymentMethod.equalsIgnoreCase("Credit card")) {
					transactionTypeCreditCard.click();
					Thread.sleep(2000);
					Amount.sendKeys(stramount);
					Thread.sleep(5000);			
					driver.findElement(By.xpath("//input[@name='txnRef']/parent::div/img")).click();
					Thread.sleep(2000);
					refundCard.sendKeys(Keys.ARROW_DOWN);
					Thread.sleep(2000);
					refundCard.sendKeys(Keys.ENTER);
					}else {
						transactionTypePrepaidCredit.click();
						Thread.sleep(2000);
						Amount.sendKeys(stramount);
						Thread.sleep(2000);	
					}
					Thread.sleep(2000);
				}
				
				CreateTransactionButton.click();
				Thread.sleep(2000);
	}
		
	
    public void clickOKButton() throws InterruptedException {
    	
    	Thread.sleep(2000);
		okButton.click();
	}
	
    public String getConfirmationMessage() throws InterruptedException {
    	Thread.sleep(2000);
    	String confirmationMessage = driver.findElement(By.xpath("//*[@class='ext-mb-text']")).getText();
		return confirmationMessage;
  	}
    
    public String addDomainAndProductAmount(String domainAmount, String productAmount) {
		float f1 = Float.parseFloat(domainAmount);
		float f2 = Float.parseFloat(productAmount);
		Float f3 = Float.sum(f1, f2);
		System.out.println(f3);
		String amount = f3.toString();
		return amount;
    }
    
    public void enterNewCardDetails(String cardholdername,String cardnumber,String cardexpiry,String cardtype) throws InterruptedException
    {
    	radiobuttonNewCard.click();
    	textCardHolderName.sendKeys(cardholdername);
    	textCardNumber.sendKeys(cardnumber);
    	textCardExpiry.sendKeys(cardexpiry);
    	textCardType.click();
    	Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(text(),'"+cardtype+"')]")).click();
		Thread.sleep(2000);
    }
	
}



