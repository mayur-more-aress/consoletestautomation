package com.tppcustomerportal.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.netregistryoldwebsite.pages.NRGOnlineOrderAccountDetailsPage;

public class TPPOnlineOrderPage extends TestBase {

//Objects 
	@FindBy(how = How.XPATH, using = "//div[@class='domain']/input")
	WebElement newDomainSearchBox;

	@FindBy(how = How.XPATH, using = "//div[@class='search']/input")
	WebElement newDomainSearchButton;

	@FindBy(how = How.XPATH, using = "//*[@id='singleDomainSearchBox']/form/div[1]/div[2]/input")
	WebElement existingDomainSearchBox;

	@FindBy(how = How.XPATH, using = "//input[@name='searchHosting']")
	WebElement existingDomainSearchButton;

//Initializing Page Objects
	public TPPOnlineOrderPage() {
		PageFactory.initElements(driver, this);
	}

//Methods
	public void setDomainNameAndTld(String domainname, String tldname) {
		newDomainSearchBox.clear();
		newDomainSearchBox.sendKeys(domainname);
		tickTld(tldname);

		try {
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='chatNow']/div/a[1]/img")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Chat window not present");
		}
	}

	public void setExistingDomainName(String domainname) {
		existingDomainSearchBox.clear();
		existingDomainSearchBox.sendKeys(domainname);
	}

	public NRGOnlineOrderAccountDetailsPage setExistingDomainSearchButton() {
		System.out.println("clicking Existing domain search button");
		if (existingDomainSearchButton.isDisplayed() || existingDomainSearchButton.isEnabled()) {
			existingDomainSearchButton.click();
		} else {
			System.out.println("element not found");
		}
		// need to write return
		return new NRGOnlineOrderAccountDetailsPage();

	}

	public void clearDefaultTldSelections() {

		List<WebElement> defaultTLDs = driver.findElements(By.cssSelector("#search-tlds [checked]"));
		for (WebElement tld : defaultTLDs) {
			tld.click();
		}
	}

	public void tickTld(String tldname) {
		WebElement tldUL = driver.findElement(By.xpath("//ul[@id='search-tlds']"));
		List<WebElement> tldList = tldUL.findElements(By.tagName("li"));
		for (WebElement li : tldList) {
			if (li.getText().equals(tldname)) {
				li.findElement(By.tagName("input")).click();
			}
		}
	}

	public TPPDomainSearchPage clickNewDomainSearchButton() {
		System.out.println("clicking new domain search button");
		if (newDomainSearchButton.isDisplayed() || newDomainSearchButton.isEnabled()) {
			newDomainSearchButton.click();
		} else {
			System.out.println("element not found");
		}
		return new TPPDomainSearchPage();
	}
}