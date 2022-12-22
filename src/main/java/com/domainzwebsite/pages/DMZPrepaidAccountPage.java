package com.domainzwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZPrepaidAccountPage extends TestBase {

	public DMZPrepaidAccountPage() {
		PageFactory.initElements(driver, this);
	}

	// methods

	public void clickRechargeUsingCreditCard() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='recharge-cc']")).click();
	}

	public void enterRechargeAmount(String strAmount) throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.id("amount")).clear();
		Thread.sleep(1000);
		driver.findElement(By.id("amount")).sendKeys(strAmount);
	}

	public void clickSubmitButton() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.name("rechargeUsingCreditCardButton")).click();
		
	}

	public void clickOnNewCreditCard() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='ccNew']")).click();
	}

	public void setNewCreditCardDetailsQuest(String cardowner, String cardnumber, String cardexpirymonth,
			String cardexpiryyear, String cardsecuritycode) throws InterruptedException {
		driver.findElement(By.name("creditCard.owner")).sendKeys(cardowner);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class=\"control-group select\"]/div/select[@name='creditCard.type']"))
				.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(
				"//*[@class=\"control-group select\"]/div/select[@name='creditCard.type']/option[text()='MasterCard']"))
				.click();
		Thread.sleep(1000);
		driver.findElement(By.name("creditCard.digits")).sendKeys(cardnumber);
		Thread.sleep(1000);
		driver.findElement(By.name("creditCard.expiryMonth")).sendKeys(cardexpirymonth);
		Thread.sleep(1000);
		driver.findElement(By.name("creditCard.expiryYear")).sendKeys(cardexpiryyear);
		Thread.sleep(1000);
		driver.findElement(By.name("creditCard.cvv")).sendKeys(cardsecuritycode);
	}

	public void setNewCreditCardDetailsBT(String cardowner, String cardnumber, String cardexpirymonth,
			String cardexpiryyear, String cardsecuritycode) throws InterruptedException {

		driver.findElement(By.name("creditCardBt.owner")).clear();
		driver.findElement(By.name("creditCardBt.owner")).sendKeys(cardowner);

		driver.switchTo().frame("braintree-hosted-field-number");
		driver.findElement(By.name("credit-card-number")).clear();
		driver.findElement(By.name("credit-card-number")).sendKeys(cardnumber);
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame("braintree-hosted-field-expirationMonth");
		driver.findElement(By.name("expiration-month")).sendKeys(cardexpirymonth);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("braintree-hosted-field-expirationYear");
		driver.findElement(By.name("expiration-year")).sendKeys(cardexpiryyear);
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame("braintree-hosted-field-cvv");
		driver.findElement(By.name("cvv")).clear();
		driver.findElement(By.name("cvv")).sendKeys(cardsecuritycode);
		driver.switchTo().defaultContent();
	}
}