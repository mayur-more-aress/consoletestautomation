package com.netregistrynewwebsite.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSOrderCompletePage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//div[@class='pg-body ng-scope']/div[1]/h1")
	WebElement orderStatus;

	@FindBy(how = How.XPATH, using = "//div[@class='pg-body ng-scope']/div[1]/p")
	WebElement orderCompleteMessage;

	@FindBy(how = How.XPATH, using = "//div[@class='pg-body ng-scope']/div[2]/div[2]/h2")
	WebElement freeConsultationText;

	@FindBy(how = How.XPATH, using = "//div[@class='pg-body ng-scope']/div[2]/div[3]//button")
	WebElement requestConsultationForWebsiteDesignButton;

	@FindBy(how = How.XPATH, using = "//div[@class='pg-body ng-scope']/div[2]/div[4]//button")
	WebElement requestConsultationForSEOServicesButton;

	@FindBy(how = How.XPATH, using = "//div[@class='pg-body ng-scope']/div[2]/div[5]/h2")
	WebElement requestConsulationResponseText;

	@FindBy(how = How.XPATH, using = "//div[@class='pg-body ng-scope']/div[2]/div[6]//div/span")
	WebElement netregistryText;
	
	@FindBy(how = How.XPATH, using = "//div[@class='summary-table']/h3/b")
	public static WebElement accountReferenceElement;

	// Initializing Page Objects
	public NRGNSOrderCompletePage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public Boolean isOrderComplete() throws InterruptedException {
		Boolean flag = false;
		System.out.println("Now in order complete page");

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderStatus);

		Thread.sleep(5000);
		if (orderStatus.getText().contentEquals("Thank you for your order")) {
			System.out.println("Order Complete Status");
			if (orderCompleteMessage.getText().contentEquals(
					"Your order has been submitted successfully. An email with your order details has been sent to your registration email address. You can also print this page as a reference. The Reference IDs for your domains are listed below.")) {
				System.out.println(orderCompleteMessage.getText());
				flag = true;
			}
		}
		return flag;
	}

	public String getAccountReferenceID() throws InterruptedException {
		String accountReference = null;

		Thread.sleep(5000);

		//WebElement accountReferenceElement = driver.findElement(By.xpath("//div[@class='summary-table']/h3/b"));
		if (accountReferenceElement.isDisplayed()) {
			accountReference = accountReferenceElement.getText();
		}
		return accountReference;
	}

	public String getSingleReferenceID() throws InterruptedException {
		String referenceIDNumber = null;

		Thread.sleep(5000);

		WebElement referenceIdNumberElement = driver.findElement(By.xpath("//div[@class='row row-domain']/div[1]"));
		if (referenceIdNumberElement.isDisplayed()) {
			referenceIDNumber = referenceIdNumberElement.getText();
		}
		return referenceIDNumber;
	}
	
	public ArrayList<String> getMultipleReferenceID() throws InterruptedException {
		
		ArrayList<String> referenceIDNumber = new ArrayList<String>();
		Thread.sleep(5000);

		List<WebElement> referenceIdNumberElement = driver.findElements(By.xpath("//div[@class='cell refid ng-binding']"));
		System.out.println("list of refrence id" +referenceIdNumberElement);
		for (WebElement refID : referenceIdNumberElement) 
    	{  		
			if (refID.isDisplayed()) 
			{
				referenceIDNumber.add(refID.getText());
			}
    	}
    	return referenceIDNumber;
		
	}

	public Boolean getRequestForFreeConsultationText() throws InterruptedException {
		Boolean flag = false;
		System.out.println("Now in order complete page");

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", freeConsultationText);

		Thread.sleep(5000);
		if (freeConsultationText.getText().contentEquals("Need some help to grow your business online?\n"
				+ "Request a FREE consultation from one of our Online Solutions Advisors.")) {
			System.out.println(freeConsultationText.getText());

			flag = true;
		}

		return flag;
	}

	public NRGNSOrderCompletePage clickRequestConsultationForWebsiteDesign() throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				requestConsultationForWebsiteDesignButton);
		System.out.println("Clicking request consultation for Website Design button");
		if (requestConsultationForWebsiteDesignButton.isDisplayed()
				|| requestConsultationForWebsiteDesignButton.isEnabled()) {
			requestConsultationForWebsiteDesignButton.click();
		} else {
			System.out.println("element not found");
		}

		Thread.sleep(4000);
		return new NRGNSOrderCompletePage();
	}

	public NRGNSOrderCompletePage clickRequestConsultationForSEOServices() throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				requestConsultationForSEOServicesButton);
		System.out.println("clicking request consultation For SEO Services button");
		if (requestConsultationForSEOServicesButton.isDisplayed()
				|| requestConsultationForSEOServicesButton.isEnabled()) {
			requestConsultationForSEOServicesButton.click();
		} else {
			System.out.println("element not found");
		}

		Thread.sleep(4000);
		return new NRGNSOrderCompletePage();
	}

	public Boolean getRequestConsulationResponseText() throws InterruptedException {
		Boolean flag = false;
		System.out.println("Now in order complete page");

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				requestConsulationResponseText);

		Thread.sleep(5000);
		if (requestConsulationResponseText.getText()
				.contentEquals("Thank you! One of our Advisors will be in touch soon!")) {
			System.out.println(requestConsulationResponseText.getText());

			flag = true;
		}

		return flag;
	}

	public Boolean getNetregistryText() throws InterruptedException {
		Boolean flag = false;
		System.out.println("Now in order complete page");

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", netregistryText);

		Thread.sleep(5000);
		if (netregistryText.getText()
				.contentEquals("Netregistry, helping Australian businesses succeed online for over 20 years.")) {
			System.out.println(netregistryText.getText());

			flag = true;
		}

		return flag;
	}

}
