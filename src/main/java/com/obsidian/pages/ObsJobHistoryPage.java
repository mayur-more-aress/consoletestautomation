package com.obsidian.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class ObsJobHistoryPage extends TestBase
{
	// Objects
	@FindBy(how = How.XPATH, using = "//*[@id='detailTable_filter']/input")
	WebElement searchJobName;
	
	// Initializing Page Objects
	public ObsJobHistoryPage() 
	{
		PageFactory.initElements(driver, this);
	}
	
	// Methods
	public void searchJob(String strJobName) throws InterruptedException
	{
		Thread.sleep(40000);
		searchJobName.sendKeys(strJobName);
		Thread.sleep(3000);
	}
	
	public String checkJobStatus(String strJobName) throws Exception
	{
		String jobStatus = null;
		int index = 1;
		
		TimeUnit.MINUTES.sleep(5);
		searchJob(strJobName);
		driver.get(driver.getCurrentUrl());
		TimeUnit.MINUTES.sleep(1);
    	List<WebElement> jobs = driver.findElements(By.xpath("//*[@id='detailTable']/tbody/tr/td[2]/a"));
    	for (WebElement job : jobs) 
    	{  		
    		int statusid = index++;
    		System.out.println("Status id is" +statusid);
    		if(job.getText().equalsIgnoreCase(strJobName))
    		{
    			Thread.sleep(1000);
				jobStatus = driver.findElement(By.xpath("//*[@id='detailTable']/tbody/tr["+statusid+"]/td[5]")).getText();
				break;
    		}
    	}
		return jobStatus;
	}
}
