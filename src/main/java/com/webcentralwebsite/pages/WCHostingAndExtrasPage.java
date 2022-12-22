package com.webcentralwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class WCHostingAndExtrasPage extends TestBase{

	//Objects         
    @FindBy(how=How.XPATH, using = "//div[@class='next']/form/input[2]")
    WebElement continueButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[1]/input")
    WebElement webHostingRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[2]/input")
    WebElement websiteRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[3]/input")
    WebElement emailHostingRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='catalogue-options']/div[4]/input")
    WebElement domainManagerRadioButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='domainAdd']/form/input[2]")
    WebElement addHostingButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='CLOUD-BASIC']/div[2]/form/div/div[1]")
    WebElement basicCouldHosting;
    
    @FindBy(how=How.XPATH, using = "//*[@id='CLOUD-BASIC']/div[2]/form/div/div[2]/div[2]/span[1]")
    WebElement monthlyProduct;
    
    @FindBy(how=How.XPATH, using = "//*[@id=\"CLOUD-BASIC\"]/div[2]/form/div/div[2]/div[3]/span[2]")
    WebElement yearlyProduct;
    
    @FindBy(how=How.XPATH, using = "//*[@id='product-catalogue-wrapper']/div[2]/div/div[1]/label")
    WebElement websiteAndHostingLink; 
    
    //Initializing Page Objects
    public WCHostingAndExtrasPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickWebHostingRadioButton(){
    	System.out.println("clicking web hosting radio button");
    	if(webHostingRadioButton.isDisplayed()||webHostingRadioButton.isEnabled()) {
    		webHostingRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickWebsiteRadioButton(){
    	System.out.println("clicking website radio button");
    	if(websiteRadioButton.isDisplayed()||websiteRadioButton.isEnabled()) {
    		websiteRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickEmailHostingRadioButton(){
    	System.out.println("clicking email hosting radio button");
    	if(emailHostingRadioButton.isDisplayed()||emailHostingRadioButton.isEnabled()) {
    		emailHostingRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void clickDomainManagerRadioButton(){
    	System.out.println("clicking domain manager radio button");
    	if(domainManagerRadioButton.isDisplayed()||domainManagerRadioButton.isEnabled()) {
    		domainManagerRadioButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	
    }
    
    public void addCPanelStarter (String subscription){
    	System.out.println("adding cpanel starter in the cart");
    	
    	
    	WebElement productDropdown = driver.findElement(By.xpath("//div[@id='CPANEL-STARTER']/div/form/div/span"));
//    	if(productDropdown.isDisplayed()||productDropdown.isEnabled()) {
//    		productDropdown.click();
//    	}
//		else {
//			System.out.println("element not found");
//		}    
 //   	new Select(productDropdown).selectByVisibleText("1 Month");
    	productDropdown.click(); 	
    	
    	//driver.findElement(By.xpath("//div[@class='x-combo-list-item']/*[contains(text(),'"+straddonproduct+"')]")).click();
    }
       
    public WCAccountContactPage clickContinueButton() {
    	
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new WCAccountContactPage();
    }

  //Methods
    public WCAddHostingPage clickAddHostingButton(){
    	System.out.println("clicking add hosting button");
    	if(addHostingButton.isDisplayed()||addHostingButton.isEnabled()) {
    		addHostingButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new WCAddHostingPage();
    }
    
    
    public WCAddHostingPage selectBasicCloudHosting() throws InterruptedException {
    	System.out.println("Selecting hosting product");
    	try {
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", basicCouldHosting);
			basicCouldHosting.click();
			Thread.sleep(2000);
			monthlyProduct.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
		}
    	
		return new WCAddHostingPage();
    	
    }
    
    public WCAddHostingPage selectBasicCloudHostingYearly() throws InterruptedException {
    	System.out.println("Selecting hosting product");
    	try {
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", basicCouldHosting);
			basicCouldHosting.click();
			yearlyProduct.click();
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
		}
    	
		return new WCAddHostingPage();
    	
    }
    
    public void clickWebsiteAndHostingLink() throws InterruptedException {
    	
    	System.out.println("Click Website and Hosting Link");
    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", websiteAndHostingLink);
    	websiteAndHostingLink.click();
    }
    
    public void addCPanelStarter1Month (String subscription){
    	System.out.println("adding cpanel starter in the cart");
    	WebElement productDropdown = driver.findElement(By.xpath("//div[@id='CPANEL-STARTER']/div/form/div/span"));
		productDropdown.click();
    	driver.findElement(By.xpath("//*[@id=\"CPANEL-STARTER\"]/div[2]/form//*[contains(text(),'"+subscription+"')]")).click();
    	
    }
}
