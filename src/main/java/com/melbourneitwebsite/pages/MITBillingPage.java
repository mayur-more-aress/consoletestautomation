package com.melbourneitwebsite.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class MITBillingPage extends TestBase {

	private static String clienttoken;

	// Objects
	@FindBy(how = How.XPATH, using = "//div[@class='orderBox']/input")
	WebElement BTClientToken;

	@FindBy(how = How.ID, using = "processOrderButton")
	WebElement continueButton;

	@FindBy(how = How.XPATH, using = "//div[@class='auto-check']/input")
	WebElement autoRenew;

	@FindBy(how = How.XPATH, using = "//p[@class='checkContainer']/input")
	WebElement agreeTermsAndConditions;

	@FindBy(how = How.XPATH, using = "//table[@class='form']/tbody/tr[4]/td[3]/input[@name='btbilling.nonce']")
	WebElement paymentMethodNonce;

	@FindBy(how = How.NAME, using = "billing.id")
	WebElement existingCardSelection;

	@FindBy(how = How.XPATH, using = "//div[@class='next']/input[2]")
	WebElement placeYourOrderButton;
	
    @FindBy(how=How.XPATH, using = "//body/input")
    WebElement recaptchaChallenge;
    
    @FindBy(how=How.XPATH, using = "//table[@class='prepaid']/parent::div/ul/li[1]")
    WebElement addPrepaidCreditLink;

	// Initializing Page Objects
	public MITBillingPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setBTFormCreditCardDetails(String cardowner, String cardnumber, String cardexpirymonth,
			String cardexpiryyear, String cardsecuritycode) throws InterruptedException {
		driver.findElement(By.xpath("//table[@class='form']/tbody/tr[1]/td[2]/input[@name='btbilling.owner']")).clear();
		driver.findElement(By.xpath("//table[@class='form']/tbody/tr[1]/td[2]/input[@name='btbilling.owner']"))
				.sendKeys(cardowner);
		driver.switchTo().frame(driver
				.findElement(By.xpath("//table[@class='form']/tbody/tr[2]/td[2]/div[@id='btbilling.number']/iframe")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//form/input")).clear();
		driver.findElement(By.xpath("//form/input")).sendKeys(cardnumber);
		driver.switchTo().defaultContent();

		driver.switchTo().frame(driver.findElement(
				By.xpath("//table[@class='form']/tbody/tr[3]/td[2]/div[@id='btbilling.expirationMonth']/iframe")));
		driver.findElement(By.xpath("//form/select")).sendKeys(cardexpirymonth);
		driver.switchTo().defaultContent();

		driver.switchTo().frame(driver.findElement(
				By.xpath("//table[@class='form']/tbody/tr[3]/td[2]/div[@id='btbilling.expirationYear']/iframe")));
		driver.findElement(By.xpath("//form/select")).sendKeys(cardexpiryyear);
		driver.switchTo().defaultContent();

		driver.switchTo().frame(driver
				.findElement(By.xpath("//table[@class='form']/tbody/tr[4]/td[2]/div[@id='btbilling.cvv']/iframe")));
		driver.findElement(By.xpath("//form/input")).clear();
		driver.findElement(By.xpath("//form/input")).sendKeys(cardsecuritycode);
		driver.switchTo().defaultContent();
	}

	public void setQuestFormCreditCardDetails(String cardowner, String cardtype, String cardnumber,
			String cardexpirymonth, String cardexpiryyear, String cardsecuritycode) {
		driver.findElement(
				By.xpath("//div[@id='creditCardFormAud']/table/tbody/tr[1]/td[2]/input[@name='billing.owner']"))
				.sendKeys(cardowner);
		driver.findElement(
				By.xpath("//div[@id='creditCardFormAud']/table/tbody/tr[2]/td[2]/select[@name='billing.type']"))
				.sendKeys(cardtype);
		driver.findElement(
				By.xpath("//div[@id='creditCardFormAud']/table/tbody/tr[3]/td[2]/input[@name='billing.digits']"))
				.sendKeys(cardnumber);
		driver.findElement(
				By.xpath("//div[@id='creditCardFormAud']/table/tbody/tr[4]/td[2]/select[@name='billing.expiryMonth']"))
				.sendKeys(cardexpirymonth);
		driver.findElement(
				By.xpath("//div[@id='creditCardFormAud']/table/tbody/tr[4]/td[2]/select[@name='billing.expiryYear']"))
				.sendKeys(cardexpiryyear);
		driver.findElement(
				By.xpath("//div[@id='creditCardFormAud']/table/tbody/tr[5]/td[2]/input[@name='billing.cvv']"))
				.sendKeys(cardsecuritycode);
	}

	public String getBTClientToken() {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('type', '')", BTClientToken);
		clienttoken = BTClientToken.getAttribute("value");
		return clienttoken;
	}

	public Boolean isBTFormDisplayed() {
		Boolean flag = false;
		if (driver.findElement(By.xpath("//table[@class='form']/tbody/tr[2]/td[2]/div[@id='btbilling.number']/iframe"))
				.isDisplayed()) {
			System.out.println("BT Form is displayed");
			flag = true;
		}
		return flag;
	}

	public Boolean isQuestFormDisplayed() {
		Boolean flag = false;
		if (driver
				.findElement(
						By.xpath("//div[@id='creditCardForm']/table/tbody/tr[2]/td[2]/select[@name='billing.type']"))
				.isDisplayed()) {
			System.out.println("Quest Form is displayed");
			flag = true;
		}
		return flag;
	}

	public MITOrderCompletePage clickContinueButton() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
		System.out.println("clicking place your order button");
		if (continueButton.isDisplayed() || continueButton.isEnabled()) {
			continueButton.click();
		} else {
			System.out.println("element not found");
		}
		return new MITOrderCompletePage();
	}

	public void tickAutoRenew() {
		System.out.println("tick auto renew");
		if (autoRenew.isDisplayed() || autoRenew.isEnabled()) {
			autoRenew.click();
		} else {
			System.out.println("element not found");
		}
	}

	public void tickTermsAndConditions() {
		System.out.println("tick agree to terms and conditions");
		if (agreeTermsAndConditions.isDisplayed() || agreeTermsAndConditions.isEnabled()) {
			agreeTermsAndConditions.click();
		} else {
			System.out.println("element not found");
		}
	}

	public String getPaymentMethodNonce() {
		String paymentmethodnoncevalue;
		paymentmethodnoncevalue = paymentMethodNonce.getText();
		return paymentmethodnoncevalue;
	}

	public Boolean isCardDetailHighlightedInRed(String carddetailname) {
		Boolean flag = false;
		WebElement cardDetail = null;
		if (carddetailname.contentEquals("Card Owner")) {
			cardDetail = driver
					.findElement(By.xpath("//table[@class='form']/tbody/tr[1]/td[2]/input[@name='btbilling.owner']"));
		} else if (carddetailname.contentEquals("Card Number")) {
			cardDetail = driver
					.findElement(By.xpath("//table[@class='form']/tbody/tr[2]/td[2]/div[@id='btbilling.number']"));
		} else if (carddetailname.contentEquals("Card Security Code")) {
			cardDetail = driver
					.findElement(By.xpath("//table[@class='form']/tbody/tr[4]/td[2]/div[@id='btbilling.cvv']"));
		} else {
			System.out.println("Card Detail not found");
		}

		if (cardDetail.getCssValue("border-color").contentEquals("rgb(255, 0, 0)")) {
			System.out.println(carddetailname + " Text Box is Highlighted in Red: "
					+ cardDetail.getCssValue("border-color") + "'");
			flag = true;
		}
		return flag;
	}

	public Boolean isErroMessageCorrect(String carddetailname, String expectederrormessage) {
		Boolean flag = false;
		String actualErrorMessage = null;
		if (carddetailname.contentEquals("Card Owner")) {
			actualErrorMessage = driver.findElement(By.xpath("//table[@class='form']/tbody/tr[1]/td[3]/div")).getText();
		} else if (carddetailname.contentEquals("Card Number")) {
			actualErrorMessage = driver.findElement(By.xpath("//table[@class='form']/tbody/tr[2]/td[3]/div")).getText();
		} else if (carddetailname.contentEquals("Card Security Code")) {
			actualErrorMessage = driver.findElement(By.xpath("//table[@class='form']/tbody/tr[4]/td[3]/div")).getText();
		} else {
			System.out.println("Card Detail not found");
		}

		if (actualErrorMessage.contentEquals(expectederrormessage)) {
			System.out.println(carddetailname + " Error Message: " + actualErrorMessage);
			flag = true;
		}
		return flag;
	}

	public void selectExistingCreditCard(String cardnumber) {
		System.out.println("select existing credit card");
		if (existingCardSelection.isDisplayed() || existingCardSelection.isEnabled()) {
			existingCardSelection.sendKeys(cardnumber);
		} else {
			System.out.println("element not found");
		}
	}

	public void selectNewCreditCardOption() {
		System.out.println("select new credit card option");
		driver.findElement(By.xpath("//div[@class='orderBox']/div[2]/input")).click();
	}

	public void selectExistingCreditCardOption(String cardtype) {

		String visacarddetails;
		String mastercarddetails;

		System.out.println("select existing credit card option");

		if (cardtype == "Visa") {

			WebElement visacard = driver.findElement(By.xpath(
					"//div[@class='orderBox']/div[1]/select[@class='existingAccounts']/option[contains(text(),'Visa credit card')]"));
			visacarddetails = visacard.getText();
			System.out.println("Card Details: " + visacarddetails);

			visacard.click();
			System.out.println("Existing Visa credit card was clicked");

		} else if (cardtype == "MasterCard") {

			WebElement mastercard = driver.findElement(By.xpath(
					"//div[@class='orderBox']/div[1]/select[@class='existingAccounts']/option[contains(text(),'MasterCard credit card')]"));
			mastercarddetails = mastercard.getText();
			System.out.println("Card Details: " + mastercarddetails);

			mastercard.click();
			System.out.println("Existing MasterCard credit card was clicked");

		}

	}

	public Boolean isReCaptchaChallengeDisplayed() throws InterruptedException {

		Boolean flag = false;
		String recaptchatoken = null;

		Thread.sleep(3000);
		System.out.println("Verify if recaptcha is displayed");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='recaptcha challenge']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('type', '')", recaptchaChallenge);
		recaptchatoken = recaptchaChallenge.getAttribute("value");

		if (recaptchaChallenge != null) {

			System.out.println("Recaptcha Token: " + recaptchatoken);
			System.out.println("Recaptcha Challenge Displayed!");
			flag = true;
		}

		Thread.sleep(10000);

		return flag;
	}

	public MITOrderCompletePage clickPlaceYourOrder() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeYourOrderButton);
		System.out.println("clicking place your order button");
		if (placeYourOrderButton.isDisplayed() || placeYourOrderButton.isEnabled()) {
			placeYourOrderButton.click();
		} else {
			System.out.println("element not found");
		}
		return new MITOrderCompletePage();
	}

	public MITPrepaidAccountPage clickEditPrepaidAccountLink() throws InterruptedException {

		driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div[2]/div[1]/h3/span/a")).click();
		return new MITPrepaidAccountPage();
	}
	
	public void selectPrepaidPaymentMethod(String paymentMethod) throws InterruptedException
	{	
		WebElement selectPayment = driver.findElement(By.xpath("//*[@id='paymentForm']/div[2]/div/div[1]/select"));
		selectPayment.click();
		Thread.sleep(5000);
		List <WebElement> paymentOptions = driver.findElements(By.xpath("//select[@name='billing.id']/option"));
		for(WebElement option : paymentOptions)
		{
			if(option.getText().contains(paymentMethod))
			{
				Thread.sleep(8000);
				option.click();
			}
		}
	 }
}
