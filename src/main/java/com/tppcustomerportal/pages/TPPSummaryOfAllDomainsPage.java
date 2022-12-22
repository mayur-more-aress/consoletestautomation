package com.tppcustomerportal.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class TPPSummaryOfAllDomainsPage extends TestBase {

	// Objects
	@FindBy(how = How.NAME, using = "getMultiRenewal")
	WebElement renewSelectedButton;

	public TPPSummaryOfAllDomainsPage() {
		PageFactory.initElements(driver, this);
	}

	public void tickDomainNameCheckbox(String domainNameWithTld) throws Exception {

		System.out.println("Get the list of domains.");
		List<WebElement> domainRows = driver.findElements(By.cssSelector("#alldomains-table tbody tr"));

		for (WebElement domain : domainRows) {

			String strDomainName = domain.findElement(By.className("domain-wrap")).getText();
			System.out.println("Found domain name: " + strDomainName);

			if (strDomainName.equalsIgnoreCase(domainNameWithTld)) {

				System.out.println("Found the domain. Tick the checkbox of the domain.");
				domain.findElement(By.name("domainIdsToRenew")).click();
				return;
			}

		}
		throw new Exception("Domain not found");
	}

	public TPPRenewDomainPage clickRenewSelectedButton() {

		System.out.println("Click Renew Selected button.");
		renewSelectedButton.click();
		return new TPPRenewDomainPage();
	}

	public Date getCurrentExpirationDate(String domainNameWithTld) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#alldomains-table tbody tr")));
		System.out.println("Get the list of domains.");
		List<WebElement> domainRows = driver.findElements(By.cssSelector("#alldomains-table tbody tr"));

		for (WebElement domain : domainRows) {

			String strDomainName = domain.findElement(By.className("domain-wrap")).getText();
			System.out.println("Found domain name: " + strDomainName);

			if (strDomainName.equalsIgnoreCase(domainNameWithTld)) {

				System.out.println("Get domain's current expiration date");
				String stringCurrentExpirtaionDate = domain.findElement(By.cssSelector("td:nth-child(6)")).getText();
				System.out.println("This is the current expiration date: " + stringCurrentExpirtaionDate);
				Date currentExpirationDate = sdf.parse(stringCurrentExpirtaionDate);
				return currentExpirationDate;
			}

		}

		throw new Exception("Domain not found");

	}

	public int getDiffDays(Date renewedExpirtaionDate, Date expirtaionDateBeforeRebewal) {

		System.out.println("Get the days difference between old expiration date and new expiration date.");
		long diff = renewedExpirtaionDate.getTime() - expirtaionDateBeforeRebewal.getTime();
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		System.out.println("THis is the days different between the two dates: " + diffDays);
		return diffDays;
	}

}
