package com.consoleadmin.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CAEditDNSPage extends TestBase
{
	// Initializing Page Objects
	public CAEditDNSPage() 
	{
		PageFactory.initElements(driver, this);
	}

	public String getDomainEppPassowrd() throws InterruptedException 
	{
        int index = 1;
        String eppPassword = null;
		Thread.sleep(3000);
		List <WebElement> tablelist = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[1]/th"));
		for(WebElement eppTable : tablelist)
		{
			int epptablenumber = index++;
			if(eppTable.getText().contains("epp password"))
			{
				eppPassword = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table["+epptablenumber+"]/tbody/tr[1]/td")).getText();
			    break;
			}
		}
		return eppPassword;
	}
}
