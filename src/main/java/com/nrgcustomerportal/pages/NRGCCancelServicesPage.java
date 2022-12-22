package com.nrgcustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class NRGCCancelServicesPage extends TestBase
{
	//Objects
    @FindBy(how=How.XPATH, using = "//input[@name='serviceIds']")
    WebElement checkboxSelectService;
    
    @FindBy(how=How.XPATH, using = "(//input[@name='serviceIds'])[2]")
    WebElement checkboxSelectServiceDomainPrivacy;

    @FindBy(how=How.XPATH, using = "//input[@value='value for money']")
    WebElement checkboxCancelReasonFirst;
    
    @FindBy(how=How.XPATH, using = "//input[@value='customer service']")
    WebElement checkboxCancelReasonSecond;
    
    @FindBy(how=How.XPATH, using = "//input[@value='service reliability']")
    WebElement checkboxCancelReasonThird;
    
    @FindBy(how=How.XPATH, using = "//input[@value='account or system issues']")
    WebElement checkboxCancelReasonFourth;
    
    @FindBy(how=How.XPATH, using = "//input[@value='no longer in use']")
    WebElement checkboxCancelReasonFifth;
    
    @FindBy(how=How.XPATH, using = "//input[@name='cancelService']")
    WebElement cencelServiceButton;
  

	//Initializing Page Objects
	public NRGCCancelServicesPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
	public void selectDomainName(String domainname) throws InterruptedException
	{
		Select domainName= new Select(driver.findElement(By.xpath("//*[@id='cancel-service-form']/div[1]/div/select")));
		domainName.selectByVisibleText(domainname);
		Thread.sleep(2000);
	}
	
	public void tickSelectService()
	{
		checkboxSelectService.click();
	}
	
	public void tickSelectServiceDomainPrivacy()
	{
		checkboxSelectServiceDomainPrivacy.click();
	}
	
    public void selectCancellationReasonFirst() throws InterruptedException
    {
    	checkboxCancelReasonFirst.click();
    	Thread.sleep(1000);
    }
    
    public void selectCancellationReasonSecond() throws InterruptedException
    {
    	checkboxCancelReasonSecond.click();
    	Thread.sleep(1000);
    }
    
    public void selectCancellationReasonThird() throws InterruptedException
    {
    	checkboxCancelReasonThird.click();	
    	Thread.sleep(1000);
    }
    
    public void selectCancellationReasonFourth() throws InterruptedException
    {
    	checkboxCancelReasonFourth.click();	
    	Thread.sleep(1000);
    }
    
    public void selectCancellationReasonFifth() throws InterruptedException
    {
    	checkboxCancelReasonFifth.click();	
    	Thread.sleep(1000);
    }
    
    public void clickOnCancelServiceButton() throws InterruptedException {

    	System.out.println("clicking on cancel services link");
    	if(cencelServiceButton.isDisplayed()||cencelServiceButton.isEnabled()) {
    		cencelServiceButton.click();
    		Thread.sleep(2000);
    	}
		else {
			System.out.println("element not found");
		}
    	
    }
    
    
    public String getSingleReferenceID() throws InterruptedException{
		String referenceIDNumber = null;
		
		Thread.sleep(10000);
		
    	WebElement referenceIdNumberElement = driver.findElement(By.xpath("//*[@id='cancel-services']/div/div[2]/div[1]/div/p"));
		if (referenceIdNumberElement.isDisplayed()) {
			referenceIDNumber = referenceIdNumberElement.getText(); 
    	}
    	return referenceIDNumber;
    }
    
    public String getWorkflowId(String strResponse) {
		String[] parts = strResponse.split(":");
		String workflowId = parts[1];
		String workflowID=workflowId.replaceAll("\\s", "");
		System.out.println("WorkflowId"+workflowID);
		return workflowID;
	}
}
