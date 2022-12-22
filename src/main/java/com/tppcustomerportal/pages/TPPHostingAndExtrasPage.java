package com.tppcustomerportal.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPHostingAndExtrasPage extends TestBase {

	// Objects
	@FindBy(how = How.NAME, using = "save")
	WebElement continueButton;

	@FindBy(how = How.NAME, using = "showAddons")
	WebElement addExtrasButton;

	// Initializing Page Objects
	public TPPHostingAndExtrasPage() {
		PageFactory.initElements(driver, this);
	}

	public TPPAddExtrasPage clickAddExtras() {

		System.out.println("Clicking add Extras button.");
		addExtrasButton.click();
		return new TPPAddExtrasPage();
	}

	public TPPAccountContactPage clickContinueButton() {

		System.out.println("Clicking continue button.");
		continueButton.click();
		return new TPPAccountContactPage();
	}


	public TPPRegistrantContactPage clickContinueButtonWithoutAccountContact() {

		System.out.println("Clicking continue button.");
		try {
			continueButton.click();
		} catch (Exception e) {
			System.out.println();
		}
		return new TPPRegistrantContactPage();
	}


}
