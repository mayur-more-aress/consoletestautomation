package com.mitdpsresellerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


import com.base.TestBase;

public class MITDPSPurchaseOffice365ProductPage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//*[@id='menu']/ul/li[2]/h3")
	WebElement purchaseTab;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Purchase Microsoft 365')]")
	WebElement purchaseOffice365ProductLink;

	@FindBy(how = How.XPATH, using = "//*[@id='domain']")
	WebElement domainNameTextfield;
	
	@FindBy(how = How.XPATH, using = "//*[@id='o365-bprem']/label/p/b")
	WebElement selectButton;
	
	@FindBy(how = How.XPATH, using = "//input[@name='existingCustomer.greencode']")
	WebElement existingCustomerTextfield;
	
	@FindBy(how = How.XPATH, using = "//input[@name='termsConditions']")
	WebElement termsAndConditionCheckbox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='orderO3652']")
	WebElement oder365ProductButton;
	
	// Initializing Page Objects
	public MITDPSPurchaseOffice365ProductPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	
	public void clickOnPurchaseTab() throws InterruptedException {

		purchaseTab.click();
		Thread.sleep(1000);

	}
	
	public void clickOnPurchaseOffice365ProductLink() throws InterruptedException {
		Thread.sleep(1000);
		purchaseOffice365ProductLink.click();

	}

	public void enterDomainName(String domainname, String tlds)
	{
		domainNameTextfield.sendKeys(domainname,tlds);
	}
	
	public void selectProductMicrosoftOffice365BusinessPremium(String productName)
	{
		System.out.println("Select the product");
		selectButton.click();
		Select monthlyProduct= new Select(driver.findElement(By.xpath("//*[@id='o365BPremium']")));
		monthlyProduct.selectByVisibleText(productName);
	}
	
	public void enterExistingCustomerGreenCode(String greencode) throws InterruptedException
	{
		existingCustomerTextfield.sendKeys(greencode);
		Thread.sleep(2000);
	}
	
	public void tickTermsAndConditions(){
       	System.out.println("Tick agree to terms and conditions");
       	if(termsAndConditionCheckbox.isDisplayed()||termsAndConditionCheckbox.isEnabled()) {
       		termsAndConditionCheckbox.click();
       	}
    	else {
    		System.out.println("element not found");
    	}
    }
	
	public void clickOnOrder365ProductButton() throws InterruptedException{
       	System.out.println("Click on order 365 product");
       	if(oder365ProductButton.isDisplayed()||oder365ProductButton.isEnabled()) {
       		oder365ProductButton.click();
       		Thread.sleep(2000);
       	}
    	else {
    		System.out.println("element not found");
    	}
    }
	
	 public String getSingleReferenceID() throws InterruptedException{
			String referenceIDNumber = null;
			Thread.sleep(10000);
	    	WebElement referenceIdNumberElement = driver.findElement(By.xpath("//*[@id='content']/div/p"));
			if (referenceIdNumberElement.isDisplayed()) {
				referenceIDNumber = referenceIdNumberElement.getText(); 
	    	}
	    	return referenceIDNumber;
	    }
	    
	    public String getWorkflowId(String strResponse) throws InterruptedException {
			/*String[] parts = strResponse.split("");
			String workflowId = parts[1];
			String workflowID=workflowId.replaceAll("\\s", "");
			System.out.println("WorkflowId"+workflowID);
			return workflowID;*/
			
			String strOrderReferenceID = null;
			String strOrderMessage = null;
			Thread.sleep(8000);
			WebElement orderMessage = driver.findElement(By.className("messages"));

			if (orderMessage.isDisplayed()) {
				strOrderMessage = orderMessage.getText();
				strOrderReferenceID = strOrderMessage.substring(strOrderMessage.lastIndexOf(" "));
				System.out.println("Message displayed. Returning the Order Reference ID: " + strOrderReferenceID);
			}

			System.out.println("Here's the Order Reference ID: " + strOrderReferenceID);
			String refId = strOrderReferenceID.replaceAll("\\s","");
			return refId.replace(".", "");
		}
}
