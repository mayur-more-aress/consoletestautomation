package com.obsidian.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class ObsLoginPage extends TestBase
{
	// Objects
	@FindBy(how = How.NAME, using = "user")
	WebElement userName;

	@FindBy(how = How.NAME, using = "password")
	WebElement password;

	@FindBy(how = How.XPATH, using = "//*[@id='loginButton']")
	WebElement loginButton;

	// Initializing Page Objects
	public ObsLoginPage() 
	{
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setLoginDetails(String strusername, String strpassword) throws InterruptedException 
	{
		new WebDriverWait(driver, 80).until(ExpectedConditions.visibilityOf(userName));
		if(userName.isDisplayed()||userName.isEnabled()) 
		{
			Thread.sleep(2000);
			userName.sendKeys(strusername );	
		}
		else 
		{
			System.out.println("element not found");
		}
		
		password.sendKeys(strpassword);

	}

	public ObsTabPage clickLoginButton() throws InterruptedException 
	{
		System.out.println("clicking login button");
		if (loginButton.isDisplayed() || loginButton.isEnabled()) 
		{
			loginButton.click();
		} 
		else 
		{
			System.out.println("element not found");
		}
		return new ObsTabPage();
	}
}
