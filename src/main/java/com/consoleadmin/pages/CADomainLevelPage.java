package com.consoleadmin.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;
import com.consolesmui.pages.CSMUITabPage;

public class CADomainLevelPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "addDomainAddon")
	WebElement addOnProductSubmitButton;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'OK')]")
	WebElement okButton;

	@FindBy(how = How.XPATH, using = "//*[@id='domain-level']/table[3]/tbody/*/td[1]/b[contains(text(),'" + "ZONE"
			+ "')]/parent::td/parent::tr/td[5]/a")
	WebElement editZone;

	@FindBy(how = How.XPATH, using = "html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[20]/td[5]")
	WebElement txtValue;

	@FindBy(how = How.XPATH, using = "/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/div[@id='domain-level']/a[@class='cp'][2]")
	WebElement loginAsClientLink;

	@FindBy(how = How.LINK_TEXT, using = "Account Interface")
	WebElement accountInterfaceLink;

	@FindBy(how = How.XPATH, using = "//*[text()='Generate renewal workflow']")
	WebElement generateRenewalWorkflow;

	@FindBy(how = How.XPATH, using = "//*[@id='domain-level']/table[3]/tbody/tr[1]/td[5]/a")
	WebElement editDNSLink;
	
	@FindBy(how = How.XPATH, using = "//*[@id='domain-level']/table[4]/tbody/tr[2]/td[2]/a")
	WebElement cancelDomainLink;
	
	// Initializing Page Objects
	public CADomainLevelPage() {
		PageFactory.initElements(driver, this);
	}

	public CAAccountReferencePage clickAccountInterfaceLink() throws InterruptedException {

		Thread.sleep(3000);
		accountInterfaceLink.click();
		Thread.sleep(3000);

		return new CAAccountReferencePage();
	}

	public CSMUITabPage clickloginAsClientLink() {

		System.out.println("Clicking Login AS Client");
		loginAsClientLink.click();

		// Store all currently open tabs in tabs
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// Switch newly open Tab
		driver.switchTo().window(tabs.get(1));
		return new CSMUITabPage();
	}

	public void clickGenerateRenewalWorkflow() throws InterruptedException {
		if (generateRenewalWorkflow.isDisplayed() || generateRenewalWorkflow.isEnabled()) {
			generateRenewalWorkflow.click();
		} else {
			System.out.println("element not found");
		}
		Thread.sleep(10000);
	}

	public Date getExpirationDate() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String strExpirationDate = driver.findElement(By.xpath("//*[@id=\"domain-level\"]/table[2]/tbody/tr/td[1]/text()[3]")).getText().substring(0, 9);
		System.out.println("This is the expiration date after the domain has been renewed: " + strExpirationDate);
		Date expirationDate = sdf.parse(strExpirationDate);
		return expirationDate;

	}

	public CAEditDNSPage clickEditDNSLink() {

		System.out.println("Clicking Edit DNS link");
		editDNSLink.click();
		return new CAEditDNSPage();
	}
	
	public void clickCancelDomain() throws InterruptedException
	{ 
		try 
		{
			System.out.println("Clicking cancel domain link");
			Thread.sleep(8000);
			cancelDomainLink.click();
			Thread.sleep(8000);
			driver.switchTo().alert().accept();
		}
		catch(Exception ex)
		{
			System.out.println("Cancel link not found");
		}
	}
	
	public void clickCancelService() throws InterruptedException
	{
		System.out.println("Clicking cancel service link");
		//cancelServiceLink.click();
		Thread.sleep(8000);
		WebElement element = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/div/table[3]/tbody/tr/td[2]/a"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
		   Alert promptAlert  = driver.switchTo().alert();
		   String alertText = promptAlert.getText();
		   System.out.println("Alert text is " + alertText);
		  //Send some text to the alert
		   promptAlert.sendKeys("Test cancel service");
		   Thread.sleep(8000);
		   promptAlert.accept();
		   
		   Thread.sleep(8000);
		   
	
	}
	
	public void purchaseProduct(String strProductName) throws InterruptedException
	{
		Thread.sleep(8000);
	    
		Select productName= new Select(driver.findElement(By.xpath("//*[@id='domain-level']/table[5]/tbody/tr/td[1]/form/select")));
		productName.selectByVisibleText(strProductName);

		driver.findElement(By.xpath("//*[@id='domain-level']/table[5]/tbody/tr/td[1]/form/input[2]")).click();
		driver.switchTo().alert().accept();
		Thread.sleep(10000);
	}
}
