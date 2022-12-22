package com.mitdpscustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITDPSBillingPage extends TestBase
{
	 //Initializing Page Objects
    public MITDPSBillingPage(){
        PageFactory.initElements(driver, this);
    }
    
    public void paySingleInvoice(String invoiceNumber) throws InterruptedException {
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//*[@id='billing-table']/tbody/tr[td//text()[contains(.,'"+invoiceNumber+"')]]/td[9]")).click();
    	
    }
    
    public void selectExisitingCardtoPayInvoice(String existingCard) throws InterruptedException {
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//*[contains(text(),'"+existingCard+"')]//preceding::td[1]/input")).click();
    	
    }
    
    public void payInvoiceButton() throws InterruptedException {
    	Thread.sleep(2000);
    	driver.findElement(By.xpath("//*[@name='payInvoiceButton']")).click();
    	driver.switchTo().alert().accept();
    }
    
    public String confirmInvoiceIsPaid() {
    	String strConfirmationMessage = driver.findElement(By.xpath("//*[@class=\"success-box\"]")).getText();
		return strConfirmationMessage;
    }
    
    public MITDPSPrepaidAccountPage clickEditPrepaidAccountLink() throws InterruptedException {

		driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div[2]/div[1]/h3/span/a")).click();
		return new MITDPSPrepaidAccountPage();
	}
}
