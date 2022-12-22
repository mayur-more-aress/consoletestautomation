package com.tppcustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class TPPRenewDomainPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "EXISTING")
	WebElement existingPaymentMethod;

	@FindBy(how = How.NAME, using = "existingBillID")
	WebElement drpExistingCards;

	@FindBy(how = How.ID, using = "newBilling")
	WebElement newCreditCard;

	@FindBy(how = How.ID, using = "agree")
	WebElement termsAndConditions;

	@FindBy(how = How.ID, using = "completeRenewalButton")
	WebElement completOrderButton;

	// Initializing Page Objects
	public TPPRenewDomainPage() {
		PageFactory.initElements(driver, this);
	}

	public void tickExistingPaymentMethod() throws InterruptedException {

		Thread.sleep(10000);
		System.out.println("Tick existing payment method.");
		existingPaymentMethod.click();

	}

	public void selectExistingCard(String existingCardDetails) {

		WebElement elExistingCardDetails = driver.findElement(By.xpath("//*[@name=\"existingBillID\"]/option[contains(text(),'"+ existingCardDetails + "')]"));
   		System.out.println("Card Details: " + elExistingCardDetails.getText());
   		
   		elExistingCardDetails.click();
   		System.out.println("Existing credit card was clicked");

	}

	public void tickTermsAndConditions() {

		System.out.println("Agree to Terms and Conditions.");
		termsAndConditions.click();

	}

	public void clickCompleteOrder() {

		System.out.println("Click Complete Order button.");
		completOrderButton.click();

	}

	public boolean isOrderComplete(String message) {

		boolean flag = false;
		new WebDriverWait(driver,30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr/td[contains(text(),'Order ID:')]")));
		WebElement orderMessage = driver.findElement(By.xpath("//tr/td[contains(text(),'Order ID:')]"));

		if (orderMessage.getText().contains(message)) {
			System.out.println(orderMessage.getText());

			flag = true;
		}
		return flag;

	}

	
	public String getOrderID() {

		String strOrderID = null;
		String strOrderMessage = null;
		
		WebElement orderMessage = driver.findElement(By.xpath("//tr/td[contains(text(),'Order ID:')]"));

		if (orderMessage.isDisplayed()) {
			strOrderMessage = orderMessage.getText();
			strOrderID = strOrderMessage.replaceAll("\\D+","");
			System.out.println("Message displayed. Returning the Order ID: " + strOrderID);
			return strOrderID;
		}

		System.out.println("Message is not displayed.");
		return "Message is not displayed.";
	}

}
