package com.consolesmui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSMUIUpdateAdminAccountPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "userName")
	WebElement userName;

	@FindBy(how = How.ID, using = "username-domain")
	WebElement usernameDomain;

	@FindBy(how = How.ID, using = "firstName")
	WebElement firstName;

	@FindBy(how = How.ID, using = "lastName")
	WebElement lastName;

	@FindBy(how = How.ID, using = "displayName")
	WebElement displayName;

	@FindBy(how = How.NAME, using = "editMexEmail")
	WebElement saveButton;

	@FindBy(how = How.ID, using = "password")
	WebElement newPassword;

	@FindBy(how = How.ID, using = "confirmPassword")
	WebElement repeatPassword;

	@FindBy(how = How.NAME, using = "checkbox")
	WebElement repsetPasswordCheckbox;

	@FindBy(how = How.NAME, using = "resetPassword")
	WebElement resetButton;

	@FindBy(how = How.ID, using = "back-btn")
	WebElement goBackButton;

	@FindBy(how = How.CSS, using = "#emailSection > div:nth-child(1) > .notification")
	WebElement editDetailsSuccessMessage;

	@FindBy(how = How.CSS, using = "#emailSection > div:nth-child(2) > .notification")
	WebElement resetPasswordSuccessMessage;

	// Initializing Page Objects
	public CSMUIUpdateAdminAccountPage() {
		PageFactory.initElements(driver, this);
	}

	public String getUsernameDomain() {

		System.out.println("Return username domain");
		return usernameDomain.getText().replace("@", "");

	}

	public CSMUIUpdateAdminAccountPage enterFirstName(String strFirstName) {

		System.out.println("Enter admin first name");
		firstName.clear();
		firstName.sendKeys(strFirstName);
		return new CSMUIUpdateAdminAccountPage();

	}

	public CSMUIUpdateAdminAccountPage enterLastName(String strLastName) {

		System.out.println("Enter admin last name");
		lastName.clear();
		lastName.sendKeys(strLastName);
		return new CSMUIUpdateAdminAccountPage();

	}

	public CSMUIUpdateAdminAccountPage enterDisplayName(String strDisplayName) {

		System.out.println("Enter admin display name");
		displayName.clear();
		displayName.sendKeys(strDisplayName);
		return new CSMUIUpdateAdminAccountPage();

	}

	private void clickSaveButton() {

		System.out.println("Click save button");
		saveButton.click();

	}

	public void updateAdminDetails(String firstName, String lastName, String displayName) {

		System.out.println("Enter new admin details");
		enterFirstName(firstName);
		enterLastName(lastName);
		enterDisplayName(displayName);
		clickSaveButton();

	}

	public CSMUIUpdateAdminAccountPage enterNewPassword(String strNewPassword) {

		System.out.println("Enter new password");
		newPassword.clear();
		newPassword.sendKeys(strNewPassword);
		return new CSMUIUpdateAdminAccountPage();

	}

	public CSMUIUpdateAdminAccountPage enterRepeatPassword(String strConfirmPassword) {

		System.out.println("Enter confirm password");
		repeatPassword.clear();
		repeatPassword.sendKeys(strConfirmPassword);
		return new CSMUIUpdateAdminAccountPage();

	}

	private void clickResetButton() {

		System.out.println("Click reset button");
		resetButton.click();

	}

	public void resetPassword(String password, String confirmPassword) {

		System.out.println("Reset admin password");
		enterNewPassword(password);
		enterRepeatPassword(confirmPassword);
		clickResetButton();

	}

	public CSMUIUpdateAdminAccountPage tickCheckbox() {

		System.out.println("Tick reset password checkbox");
		repsetPasswordCheckbox.click();
		return new CSMUIUpdateAdminAccountPage();

	}

	public CSMUIManageO365MainPage clickGoBackButton() {

		System.out.println("Click go back button");
		goBackButton.click();
		return new CSMUIManageO365MainPage();

	}

	public String getEditDetailsSuccessMessage() {

		System.out.println("Return edit details success message");
		return editDetailsSuccessMessage.getText();

	}

	public String getResetPasswordSuccessMessage() {

		System.out.println("Return reset password success message");
		return resetPasswordSuccessMessage.getText();

	}

}
