package com.mitdpsresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import org.openqa.selenium.By;


import com.base.TestBase;

public class MITDPSBulkRegisterPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "domain")
	WebElement newDomainSearchBox;

	// @FindBy(how = How.XPATH, using = "//*[@id=\"availability\"]/div/span")
	@FindBy(how = How.XPATH, using = "//input[@class='ui-button ui-widget ui-state-default ui-corner-all ui-widget-content']")
	WebElement searchButton;

	@FindBy(how = How.XPATH, using = "//input[@class='ui-button ui-widget ui-state-default ui-corner-all ui-widget-content']")
	WebElement domainAvailability;

	// Initializing Page Objects
	public MITDPSBulkRegisterPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setAllDomainNames(String[] domainNames) throws Exception {
		if(newDomainSearchBox.isDisplayed()||newDomainSearchBox.isEnabled()) {
			Thread.sleep(2000);
			newDomainSearchBox.clear();
			newDomainSearchBox.sendKeys(domainNames);	
		}
	}

	public ArrayList<String> setMultipleDomainNameandTld(String domainname, String tldname) throws InterruptedException
    {
		ArrayList<String> lstDomainName = new ArrayList<String>();
    	Thread.sleep(2000);
    	newDomainSearchBox.sendKeys(domainname+"."+tldname+"\n");
    	String domainName = domainname+"."+tldname;
    	System.out.print("Domain" +domainName);
    	lstDomainName.add(domainName);
    	return lstDomainName;
    }
	
	public void clickSearchButton() throws InterruptedException {

		System.out.println("Return search availability message");

		if (searchButton.isDisplayed() || searchButton.isEnabled()) {
			searchButton.click();
		} else {
			System.out.println("element not found");
		}
		Thread.sleep(1000);
	}

	public String getSearchAvailabilityMessage(String domainNames, int intElement) throws InterruptedException {
		String beforeXpath = "//*[@id='searchResult']//table/tbody/tr[";
		String afterXpath = "]/td[2]";
		String afterXpath1 = "]/td[3]";
		String afterXpath2 = "]/td[1]";
		int i = intElement + 2;

		String actualXpath = beforeXpath + i + afterXpath;
		String priceXpath = beforeXpath + i + afterXpath1;
		String domainXpath = beforeXpath + i + afterXpath2;
		WebElement availability = driver.findElement(By.xpath(actualXpath));
		WebElement domainName = driver.findElement(By.xpath(domainXpath));

		if (availability.getText().contains("Available")) {
			System.out.println(domainName.getText() + " - domain is available");
			WebElement price = driver.findElement(By.xpath(priceXpath));
			if (price.isEnabled() == true) {
				System.out.println("price dropdown displayed");
			}
		}

		return availability.getText();
	}

	public String getSearchNotAvailabilityMessage(String domainNames, int intElement) throws InterruptedException {
		String beforeXpath = "//*[@id='searchResult']//table/tbody/tr[";
		String afterXpath = "]/td[2]";

		String afterXpath2 = "]/td[1]";
		int i = intElement + 2;

		String actualXpath = beforeXpath + i + afterXpath;

		String domainXpath = beforeXpath + i + afterXpath2;
		WebElement availability = driver.findElement(By.xpath(actualXpath));
		WebElement domainName = driver.findElement(By.xpath(domainXpath));

		if (availability.getText().contains("Unavailable")) {
			System.out.println(domainName.getText() + " - domain is Not available");
		}
		return availability.getText();
	}

	public String getSearchPremiumMessage(String domainNames, int intElement) throws InterruptedException {
		String beforeXpath = "//*[@id='searchResult']//table/tbody/tr[";
		String afterXpath = "]/td[2]";

		String afterXpath2 = "]/td[1]";
		int i = intElement + 2;

		String actualXpath = beforeXpath + i + afterXpath;

		String domainXpath = beforeXpath + i + afterXpath2;
		WebElement availability = driver.findElement(By.xpath(actualXpath));
		WebElement domainName = driver.findElement(By.xpath(domainXpath));

		if (availability.getText().contains("Premium")) {
			System.out.println(domainName.getText() + " - domain is available for Premium");

		}

		return availability.getText();

	}

}
