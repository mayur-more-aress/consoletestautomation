package com.obsidian.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class ObsTabPage extends TestBase
{
	// Objects
	@FindBy (how = How.XPATH, using = "//*[@id='tabs']/li[2]/a")
	WebElement jobsLink;
	
	@FindBy (how = How.XPATH, using = "//*[@id='tabs']/li[1]/a")
	WebElement jobHistoryLink;
	
	// Initializing Page Objects
	public ObsTabPage() 
	{
		PageFactory.initElements(driver, this);
	}
	
	// Methods
	public ObsJobsPage clickJobsLink() throws InterruptedException 
	{
		Thread.sleep(3000);
		System.out.println("Clicking Jobs link");
		if (jobsLink.isDisplayed() || jobsLink.isEnabled())
		{
			jobsLink.click();
		} 
		else 
		{
			System.out.println("element not found");
		}
		return new ObsJobsPage();
	}
	
	public ObsJobHistoryPage clickJobHistoryLink() throws InterruptedException 
	{
		Thread.sleep(3000);
		System.out.println("Clicking Job History link");
		if (jobHistoryLink.isDisplayed() || jobHistoryLink.isEnabled())
		{
			jobHistoryLink.click();
		} 
		else 
		{
			System.out.println("element not found");
		}
		
		return new ObsJobHistoryPage();
	}
}
