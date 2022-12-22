package com.consoleadmin.pages;

import java.awt.AWTException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.mitcustomerportal.pages.MITManageDomainsPage;
import com.mitdpsresellerportal.pages.MITDPSManageDomainsPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.nrgcustomerportal.pages.NRGManageDomainsPage;

public class CAHeaderPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "domainInput")
	WebElement domainInput;

	@FindBy(how = How.XPATH, using = "//table[@class='headerbar']/tbody/tr[1]/td[2]/form/input[@class='cp'][2]")
	WebElement searchButton;

	@FindBy(how = How.XPATH, using = "//table[@class='headerbar']/tbody/tr[1]/td[3]/form/input[@name='submit']")
	WebElement submitButton;

	@FindBy(how = How.XPATH, using = "//table[@class='headerbar']/tbody/tr[1]/td[4]/form/input[@class='cp'][2]")
	WebElement getButton;

	@FindBy(how = How.NAME, using = "greencode")
	WebElement accountReferenceInput;

	@FindBy(how = How.NAME, using = "domain")
	WebElement workflowInput;
	
	@FindBy(how = How.XPATH, using = "//*[@id='domainInput']")
	WebElement domainNameInput;

	@FindBy(how = How.LINK_TEXT, using = "View Invoice & Prepaid detail")
	WebElement ViewInvoiceAndPrepaidDetailLink ;
	
	@FindBy(how = How.LINK_TEXT, using = "Pay outstanding invoices")
	WebElement PayOutstandingInvoicesLink ;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'View Billing Accounts')]")
	WebElement viewBillingAccountLink ;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Login as reseller')]")
	WebElement loginAsResellerLink ;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Login as client')]")
	WebElement loginAsClientLink ;
	
	// Initializing Page Objects
	public CAHeaderPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public CADomainLevelPage searchDomain(String strdomainname) throws InterruptedException {

		Thread.sleep(3000);
		domainInput.sendKeys(strdomainname);
		Thread.sleep(3000);
		searchButton.click();
		Thread.sleep(5000);

		return new CADomainLevelPage();
	}

	public CAAccountReferencePage searchAccountReference(String strAccountReference)
			throws InterruptedException, AWTException {
		Thread.sleep(5000);
		accountReferenceInput.sendKeys(strAccountReference);
		System.out.println("Searching for submit button");

		try {
			driver.findElement(
					By.xpath("//table/tbody/tr[1]/td/table[@class='headerbar']/tbody/tr[1]/td[3]/form/input[2]"))
					.click();
			// driver.findElement(By.xpath("//table[@class='headerbar']/tbody/tr[1]/td[3]/form/input[@name='submit']")).click();
		} catch (Exception e) {

			System.out.println("Timeout after clicking submit button");
		}

		System.out.println("Submit button clicked");

		// To add a waiting time for account reference page to load

		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#top h1")));

		return new CAAccountReferencePage();
	}

	public CAWorkflowAdminPage searchWorkflow(String strworkflow) throws InterruptedException {


		Thread.sleep(10000);
		workflowInput.sendKeys(strworkflow);
		Thread.sleep(2000);
		System.out.println("Searching for get button");
		Thread.sleep(5000);
		getButton.click();
		System.out.println("Get button clicked");
		// Thread.sleep(8000);

		return new CAWorkflowAdminPage();
	}

	public String verifyHeaderPageTitle() {
		return driver.getTitle();
	}

	public boolean verifyDomainSearchButtonExists() {
		return searchButton.isDisplayed();
	}

	public CAInvoicesPage clickViewInvoiceAndPrepaidDetail() {
		
		System.out.println("Clicking on View Invoice & Prepaid detail link");
		ViewInvoiceAndPrepaidDetailLink.click();
		return new CAInvoicesPage();
		
	}
	
	public CAInvoicesPage clickPayOutstandingInvoices() 
	{
		System.out.println("Clicking on Pay outstanding invoices link");
		PayOutstandingInvoicesLink.click();
		return new CAInvoicesPage();
	}
	
	public CADomainLevelPage searchDomainName(String strdomainname) throws InterruptedException {
		Thread.sleep(10000);
		domainNameInput.sendKeys(strdomainname);
		System.out.println("Get button clicked");
		searchButton.click();
		// Thread.sleep(8000);

		return new CADomainLevelPage();
	}
	  public void clickViewBillingAccountLink() throws InterruptedException {
			System.out.println("Clicking on View billing account link");
			viewBillingAccountLink.click();
			Thread.sleep(1000);
			
		}
	  
	  public MITDPSManageDomainsPage clickOnLoginAsResellerLink() throws InterruptedException {
		   Thread.sleep(2000);
			System.out.println("Clicking on Login as Reseller link");
			loginAsResellerLink.click();
			Thread.sleep(2000);
			ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			return new MITDPSManageDomainsPage();
		}
	 
	  public NRGHeaderPage clickOnLoginAsClientLink() throws InterruptedException {
		   Thread.sleep(2000);
			System.out.println("Clicking on Login as client link");
			loginAsClientLink.click();	
			Thread.sleep(2000);
			return new NRGHeaderPage();
		}
	  public NRGManageDomainsPage clickOnLoginasClientLink() throws InterruptedException {
		   Thread.sleep(2000);
			System.out.println("Clicking on Login as client link");
			loginAsClientLink.click();	
			Thread.sleep(2000);
			 ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			return new NRGManageDomainsPage();
		}
	  
	  public MITManageDomainsPage clickLoginasClientLink() throws InterruptedException {
		   Thread.sleep(2000);
			System.out.println("Clicking on Login as client link");
			loginAsClientLink.click();	
			Thread.sleep(2000);
			 ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			return new MITManageDomainsPage();
		}
	  
	  public MITOnlineOrderPage clickOnLoginAsClient() throws InterruptedException {
		    String oldtab=driver.getWindowHandle();
		    Thread.sleep(2000);
			System.out.println("Clicking on Login as client link");
			loginAsClientLink.click();	
			Thread.sleep(3000);
			
			 ArrayList<String> newtab = new ArrayList<String> (driver.getWindowHandles());
			 newtab.remove(oldtab);
			 driver.switchTo().window(newtab.get(0));
			return new MITOnlineOrderPage();
		}
	  
	  public NRGOnlineOrderPage clickonLoginasClientLink() throws InterruptedException {
		    String oldtab=driver.getWindowHandle();
		    Thread.sleep(2000);
			System.out.println("Clicking on Login as client link");
			loginAsClientLink.click();	
			Thread.sleep(3000);
			
			 ArrayList<String> newtab = new ArrayList<String> (driver.getWindowHandles());
			 newtab.remove(oldtab);
			 driver.switchTo().window(newtab.get(0));
			
			return new NRGOnlineOrderPage();
		}
}
