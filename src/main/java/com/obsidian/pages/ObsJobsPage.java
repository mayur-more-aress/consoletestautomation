package com.obsidian.pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class ObsJobsPage extends TestBase
{
	// Objects
	@FindBy(how = How.XPATH, using = "//*[@id='detailTable_filter']/input")
	WebElement searchJobName;
		
	// Initializing Page Objects
	public ObsJobsPage() 
	{
		PageFactory.initElements(driver, this);
	}
	
	// Methods
	
	/*
	 * Method to search the job
	 */
	public void searchJob(String strJobName) throws InterruptedException
	{
		Thread.sleep(3000);
		searchJobName.sendKeys(strJobName);
		Thread.sleep(3000);
	}
	
	/*
	 * Method to submit one time run for the job
	 */
	public void submitOneTimeRunForTheJob(String strJobName) throws Exception
	{
		int index = 1;
		Thread.sleep(5000);
		// list of jobs name
    	List<WebElement> jobs = driver.findElements(By.xpath("//*[@id='detailTable']/tbody/tr/td[1]/a"));
    	for (WebElement job : jobs) 
    	{  		
    		int onetimerunid = index++;
    		System.out.println("One time run id is" +onetimerunid);
    		if(job.getText().equalsIgnoreCase(strJobName))
    		{
    			Thread.sleep(1000);
    			// click submit run
				driver.findElement(By.xpath("//*[@id='detailTable']/tbody/tr["+onetimerunid+"]/td[7]/a")).click();
				enterJobDate();
				enterJobTime();
				driver.findElement(By.xpath("//button/span[contains(text(),'Submit One-Time Run')]")).click();
				break;
    		}
    	}
	}
	
	/*
	 * Method to enter the job date for submitting one time run for the job
	 */
	public void enterJobDate()
	{
		String[] serverDate = getServerDateTime();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.id("toDatePicker")).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		WebElement dateWidget = driver.findElement(By.id("ui-datepicker-div"));
		List<WebElement> dates=dateWidget.findElements(By.tagName("td"));

		for (WebElement date: dates)
		{
		   if (date.getText().equals(serverDate[0]))
		   {
			   date.findElement(By.linkText(serverDate[0])).click();
			   break;
		   }

		}
	}
	
	/*
	 * Method to enter the job time for submitting one time run for the job
	 */
	public void enterJobTime()
	{   
		// Get the current server Date time
		String[] serverTime = getServerDateTime();
		int serverminute = roundOffMinutes(Integer.parseInt(serverTime[2]));
		
		// Select hour
		if(serverminute == 00)
		{
			int serverHour = Integer.parseInt(serverTime[1]) + 1;
			serverTime[1] = Integer.toString(serverHour);
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.id("toTimePicker")).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		WebElement hourWidget = driver.findElement(By.id("ui-timepicker-hours"));
		List<WebElement> hours=hourWidget.findElements(By.tagName("td"));

		for (WebElement hour: hours)
		{
		   if (hour.getText().equals(serverTime[1]))
		   {
			   hour.findElement(By.linkText(serverTime[1])).click();
			   break;
		   }

		}
		
	    //select minute
    	driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		WebElement minuteWidget = driver.findElement(By.id("ui-timepicker-minutes"));
		List<WebElement> minutes=minuteWidget.findElements(By.tagName("td"));

		for (WebElement minute: minutes)
		{
		   if (minute.getText().equals(Integer.toString(serverminute)))
		   {
			   System.out.println("clicking minutes :" +serverminute);
			   minute.findElement(By.linkText(Integer.toString(serverminute))).click();
			   System.out.println("clicked minutes :" +serverminute);
			   break;
		   }

		}
	}
	
	/*
	 * Method to roundoff the server minutes for submitting one time run for the job
	 */
	public int roundOffMinutes(int serverMinute)
	{		
		/*if current minute is divisible by 5 add 5 to it
		 *e.g : 30(current time) = 30 + 5 =35(job run time)*/
	    if (serverMinute % 5 == 0)
	    {
	    	serverMinute = serverMinute + (5);
	    }
	    /*if current time lies in the range of 5(0-5) then roundoff it to next nearest multiple of 5
	     *e.g : 17(current time) = 20(job run time)*/
	    else
	    {
	    	serverMinute = serverMinute + (5 - serverMinute % 5);  	
	    }
	    System.out.println("Rounding up to next nearest 5------" + serverMinute);
    	if(serverMinute == 60)
    	{
    		serverMinute = 00;
    	}
    	else if(serverMinute == 5)
    	{
    		serverMinute = 05;
    	}
		return serverMinute; 
	}
	
	public String generateExpiryDate()
	{
		Calendar calendar = Calendar.getInstance();       
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
		String currentDateTime=sdf.format(calendar.getTime());
		System.out.println("The autralian current time is "+currentDateTime);	
		calendar.add(calendar.DATE, 5);
		String modifiedDateTime=sdf.format(calendar.getTime());
		System.out.println("The autralian modified time is "+modifiedDateTime);
	    return modifiedDateTime;
	}
	
	/*
	 * Method to get the current server date and time for submitting one time run for the job
	 */
	public String[] getServerDateTime()
	{
		Calendar calendar = Calendar.getInstance();       
		SimpleDateFormat sdf = new SimpleDateFormat("dd:HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
		String currentDateTime=sdf.format(calendar.getTime());
		System.out.println("The autralian time is "+currentDateTime);
		String[] serverTime = currentDateTime.split(":");
		return serverTime;
	}

}
