package com.consolesmui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSMUIDomainNamePage extends TestBase
{
	// Initializing Page Objects
	public CSMUIDomainNamePage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public String getDomainPassword() throws InterruptedException
	{
		System.out.println("Clicking Dashboard tab");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='show-password']")).click();
		WebElement element = driver.findElement(By.xpath("//*[@id='domain-password']"));
		String domainPassowrd = element.getAttribute("value");
		return domainPassowrd;
	}
}
