package com.consolesmui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSMUIUpdateUserAccountPage extends TestBase {

	// objects
	@FindBy(how = How.XPATH, using = "//div[@id='create-mex-user-form']//div[@class='control-group submit last' ]//button[@name='editMexEmail']")
	WebElement saveButton;

	@FindBy(how = How.XPATH, using = "//div[@class='notification success']//span[text() = 'Your changes have been saved successfully.']")
	WebElement editUserStatusMessage;

	@FindBy(how = How.XPATH, using = "//div[@id='create-mex-user-form']//div[@class='control-group submit last' ]//button[@name='resetPassword']")
	WebElement resetButton;

	@FindBy(how = How.XPATH, using = "//div[@class='notification success']//span[text() = 'Password successfully changed.']")
	WebElement editPwdStatusMessage;

	@FindBy(how = How.XPATH, using = "//input[@value='Simple Email - Email Only']")
	WebElement simpleEmail;

	@FindBy(how = How.XPATH, using = "//input[@value='Email Essentials - Email Only']")
	WebElement emailEssentials;

	@FindBy(how = How.XPATH, using = "//input[@value='Business Essentials - Email and Office Online']")
	WebElement businessEssentials;

	@FindBy(how = How.XPATH, using = "//input[@value='Business Premium - Email and Office']")
	WebElement businessPremium;

	@FindBy(how = How.XPATH, using = "//input[@value='Business - Office Only']")
	WebElement businessOffice;

	@FindBy(how = How.XPATH, using = "//div[@id='email-account-div']//span[text() = 'Your changes have been saved successfully.']")
	WebElement editLicenceStatusMessage;

	@FindBy(how = How.XPATH, using = "//div[@class='control-group submit last' ]//button[@name='assignLicenceBtn']")
	WebElement licenceSaveButton;
	
	@FindBy(how = How.ID, using = "back-btn")
	WebElement goBackButton;

	// Initializing Page Objects
	public CSMUIUpdateUserAccountPage() {
		PageFactory.initElements(driver, this);
	}

	// methods
	public CSMUIUpdateUserAccountPage editUserAccountDetails(String username, String firstName, String lastName,
			String displayName) {

		// driver.switchTo().frame("o365_frame");
		System.out.println("set username ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='userName']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='userName']")).sendKeys(username);

		System.out.println("set firstname ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='firstName']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='firstName']")).sendKeys(firstName);

		System.out.println("set lastname ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='lastName']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='lastName']")).sendKeys(lastName);

		System.out.println("set displayname ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='displayName']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='displayName']"))
				.sendKeys(displayName);

		return new CSMUIUpdateUserAccountPage();
	}

	public CSMUIUpdateUserAccountPage clickSave() throws InterruptedException {

		Thread.sleep(3000);

		saveButton.click();
		return new CSMUIUpdateUserAccountPage();

	}

	public boolean isAccountUpdated() throws InterruptedException {
		boolean flag = false;
		System.out.println("Now in office365 update page");

		Thread.sleep(3000);

		if (editUserStatusMessage.getText().contentEquals("Your changes have been saved successfully.")) {
			System.out.println(editUserStatusMessage.getText());
			flag = true;
		}

		return flag;
	}

	public CSMUIUpdateUserAccountPage editUserPwdDetails(String newPassword, String repeatPassword) {

		System.out.println("set newpassword ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='password']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='password']")).sendKeys(newPassword);

		System.out.println("set repeatmpassword ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='confirmPassword']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='confirmPassword']"))
				.sendKeys(repeatPassword);

		System.out.println("click on change the password nexttime checkbox ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@type='checkbox']")).click();

		return new CSMUIUpdateUserAccountPage();
	}

	public CSMUIUpdateUserAccountPage clickReset() throws InterruptedException {

		Thread.sleep(3000);

		resetButton.click();
		return new CSMUIUpdateUserAccountPage();

	}

	public boolean isPwdUpdated() throws InterruptedException {
		boolean flag = false;
		System.out.println("Now in office365 update page");

		Thread.sleep(3000);

		if (editPwdStatusMessage.getText().contentEquals("Password successfully changed.")) {
			System.out.println(editPwdStatusMessage.getText());
			flag = true;
		}

		return flag;
	}

	public CSMUIUpdateUserAccountPage setNewLicence(String strNewLicenceType) throws InterruptedException {
		System.out.println("Selecting New Licence Type");

		try {
			Thread.sleep(3000);
			if (strNewLicenceType == "Simple Email - Email Only") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", simpleEmail);
				simpleEmail.click();
			}

			if (strNewLicenceType == "Email Essentials - Email Only") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailEssentials);
				emailEssentials.click();
			}

			if (strNewLicenceType == "Business Essentials - Email and Office Online") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessEssentials);
				businessEssentials.click();
			}

			if (strNewLicenceType == "Business Premium - Email and Office") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessPremium);
				businessPremium.click();
			}

			if (strNewLicenceType == "Business - Office Only") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessOffice);
				businessOffice.click();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
			Thread.sleep(3000);
		}

		return new CSMUIUpdateUserAccountPage();
	}

	
	
	public CSMUIUpdateUserAccountPage clickLicenceSave() throws InterruptedException {

		Thread.sleep(3000);
		licenceSaveButton.click();
		return new CSMUIUpdateUserAccountPage();

	}

	public boolean isLicenceUpdated() throws InterruptedException {
		boolean flag = false;
		System.out.println("Now in office365 update page");

		Thread.sleep(3000);

		if (editLicenceStatusMessage.getText().contentEquals("Your changes have been saved successfully.")) {
			System.out.println(editLicenceStatusMessage.getText());
			flag = true;
		}

		return flag;
	}
	
	public CSMUIManageO365MainPage clickGoBackButton() {

		System.out.println("Click go back button");
		goBackButton.click();
		return new CSMUIManageO365MainPage();

	}

	public CSMUIUpdateUserAccountPage unAssignLicence(String strLicenceType) throws InterruptedException {
		System.out.println("Unassign licence");

		try {
			Thread.sleep(3000);
			if (strLicenceType == "Simple Email - Email Only") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", simpleEmail);
				simpleEmail.click();
			}

			if (strLicenceType == "Email Essentials - Email Only") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailEssentials);
				emailEssentials.click();
			}

			if (strLicenceType == "Business Essentials - Email and Office Online") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessEssentials);
				businessEssentials.click();
			}

			if (strLicenceType == "Business Premium - Email and Office") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessPremium);
				businessPremium.click();
			}

			if (strLicenceType == "Business - Office Only") {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessOffice);
				businessOffice.click();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
			Thread.sleep(3000);
		}

		return new CSMUIUpdateUserAccountPage();
	}
		

}
