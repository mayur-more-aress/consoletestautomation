package com.consolesmui.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSMUIManageO365MainPage extends TestBase {
	// Objects
	@FindBy(how = How.XPATH, using = "//div[@id='create-mex-user-form']//div[@class='new-email editor']//input[@value='Simple Email - Email Only']")
	WebElement simpleEmail;

	@FindBy(how = How.XPATH, using = "//div[@id='create-mex-user-form']//div[@class='new-email editor']//input[@value='Email Essentials - Email Only']")
	WebElement emailEssentials;

	@FindBy(how = How.XPATH, using = "//div[@id='create-mex-user-form']//div[@class='new-email editor']//input[@value='Business Essentials - Email and Office Online']")
	WebElement businessEssentials;

	@FindBy(how = How.XPATH, using = "//div[@id='create-mex-user-form']//div[@class='new-email editor']//input[@value='Business Premium - Email and Office']")
	WebElement businessPremium;

	@FindBy(how = How.XPATH, using = "//div[@id='create-mex-user-form']//div[@class='new-email editor']//input[@value='Business - Office Only']")
	WebElement businessOffice;

	@FindBy(how = How.XPATH, using = "//*[@id='create-button']")
	WebElement createUserButton;

	@FindBy(how = How.XPATH, using = "//div[@id='create-mex-user-form']//div[@class='control-group submit last']//button")
	WebElement createEmailButton;

	@FindBy(how = How.XPATH, using = "//div[@class='notification success']/span")
	WebElement accountStatusMessage;

	// Objects
	@FindBy(how = How.CSS, using = ".advance_portal td:nth-of-type(1)")
	WebElement adminEmailAddress;

	@FindBy(how = How.CSS, using = ".advance_portal td:nth-child(2) a")
	WebElement adminUpdateLink;

	@FindBy(how = How.CSS, using = ".advance_portal td:nth-child(3) a")
	WebElement adminLoginLink;

	// Initializing Page Objects
	public CSMUIManageO365MainPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public CSMUIManageO365MainPage clickCreateUser() throws InterruptedException {

		createUserButton.click();
		return new CSMUIManageO365MainPage();

	}

	public CSMUIManageO365MainPage setUserAccountDetails(String username, String firstName, String lastName,
			String displayName, String password, String confirmPassword) {

		System.out.println("set username ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='userName']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='userName']")).sendKeys(username);

		System.out.println("set firstName ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='firstName']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='firstName']")).sendKeys(firstName);

		System.out.println("set lastName ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='lastName']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='lastName']")).sendKeys(lastName);

		System.out.println("set displayName ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='displayName']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='displayName']"))
				.sendKeys(displayName);

		System.out.println("set password ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='password']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='password']")).sendKeys(password);

		System.out.println("set confirmPassword ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='confirmPassword']")).clear();
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@id='confirmPassword']"))
				.sendKeys(confirmPassword);

		System.out.println("click on change the password nexttime checkbox ");
		driver.findElement(By.xpath("//div[@id='create-mex-user-form']//input[@type='checkbox']")).click();

		return new CSMUIManageO365MainPage();
	}

	public CSMUIManageO365MainPage setSelectLicence(String strLicenceType) throws InterruptedException {
		System.out.println("Selecting Licence Type");

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

		return new CSMUIManageO365MainPage();
	}

	public CSMUIManageO365MainPage clickCreateEmail() throws InterruptedException {

		Thread.sleep(3000);

		createEmailButton.click();
		return new CSMUIManageO365MainPage();

	}

	public boolean isAccountCreated() throws InterruptedException {
		boolean flag = false;
		System.out.println("Now in office365 Manage page");

		Thread.sleep(3000);

		if (accountStatusMessage.getText().contentEquals("New account have been created successfully.")) {
			System.out.println(accountStatusMessage.getText());
			flag = true;
		}

		return flag;
	}

	public CSMUIUpdateUserAccountPage clickUserUpdateLink(String strUserDetails) throws Exception {
		Thread.sleep(3000);
		List<WebElement> noOfUsers = driver.findElements(By.cssSelector(".scrollable-table tbody tr"));
		System.out.println(noOfUsers.size());

		for (int i = 0; i <= noOfUsers.size(); i++) {
			System.out.println(noOfUsers.get(i).getText());
			if (noOfUsers.get(i).findElement(By.cssSelector("td:first-child")).getText().equals(strUserDetails)) {
				noOfUsers.get(i).findElement(By.cssSelector("td:nth-child(3) a")).click();
				System.out.println(strUserDetails);
				return new CSMUIUpdateUserAccountPage();
			}
		}
		throw new Exception("Email address not found");
	}

	public boolean verifyAdminEmail(String domainName) {

		System.out.println("Verify admin email address");
		return adminEmailAddress.getText().equals("o365-admin@" + domainName);
	}

	public CSMUIUpdateAdminAccountPage clickAdminUpdateLink() throws InterruptedException {

		System.out.println("Click update link");
		adminUpdateLink.click();
		Thread.sleep(3000);
		return new CSMUIUpdateAdminAccountPage();
	}

	public void clickAdminLoginLink() {

		System.out.println("Click login link");
		adminLoginLink.click();
	}

	public String getAssignLicence(String strUserDetails) throws Exception {

		List<WebElement> noOfUsers = driver.findElements(By.cssSelector(".scrollable-table tbody tr"));
		System.out.println(noOfUsers.size());

		for (int i = 0; i <= noOfUsers.size(); i++) {

			if (noOfUsers.get(i).findElement(By.cssSelector("td:first-child")).getText().equals(strUserDetails)) {
				String assignedLicence = noOfUsers.get(i).findElement(By.cssSelector("td:nth-child(2)")).getText();
				System.out.println("This is the assigned licence " + assignedLicence);
				return assignedLicence;

			}
		}
		throw new Exception("Email address not found");
	}

	public String getUserLinkText(String strUserDetails) throws Exception {

		List<WebElement> noOfUsers = driver.findElements(By.cssSelector(".scrollable-table tbody tr"));
		System.out.println(noOfUsers.size());

		for (int i = 0; i <= noOfUsers.size(); i++) {

			if (noOfUsers.get(i).findElement(By.cssSelector("td:first-child")).getText().equals(strUserDetails)) {
				String actualUserLinkText = noOfUsers.get(i).findElement(By.cssSelector("td:nth-child(4) a")).getText();
				System.out.println("This is the user link " + actualUserLinkText);
				return actualUserLinkText;

			}
		}
		throw new Exception("Email address not found");
	}

	public boolean verifyUserLink(String strUserDetails) throws Exception {

		boolean flag = false;
		List<WebElement> noOfUsers = driver.findElements(By.cssSelector(".scrollable-table tbody tr"));
		System.out.println(noOfUsers.size());

		for (int i = 0; i <= noOfUsers.size(); i++) {

			if (noOfUsers.get(i).findElement(By.cssSelector("td:first-child")).getText().equals(strUserDetails)) {

				noOfUsers.get(i).findElement(By.cssSelector("td:nth-child(4) a")).click();
				Thread.sleep(3000);
				// Store all currently open tabs in tabs
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				System.out.println(tabs.size());

				// Switch newly open Tab
				driver.switchTo().window(tabs.get(2));
				Thread.sleep(3000);
				String newTabUrl = driver.getCurrentUrl();
				System.out.println(newTabUrl);

				if (newTabUrl.contains("login.microsoftonline.com")) {
					
					flag = true;
				}
				driver.switchTo().window(tabs.get(1));
				driver.switchTo().frame(driver.findElement(By.name("o365_frame")));
				return flag;
			}
		}
		throw new Exception("Email address not found");
	}
}
