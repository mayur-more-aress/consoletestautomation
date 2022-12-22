package com.braintree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class BTTransactionDetailForIDPage extends TestBase{

	//Objects
	
    //Initializing Page Objects
    public BTTransactionDetailForIDPage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods    
    public String getCustomerInformation(String parametername) throws InterruptedException{
    	
    	String parametertextvalue = null;
    	Thread.sleep(1000);
    	
    	if (parametername.contentEquals("Company")) {
    		WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[3]/dl/dd[2]"));
    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    		parametertextvalue = parametervalue.getText();	
    	}
    	return parametertextvalue;		
    }
    
    public String getPaymentInformation(String parametername, String transactionstatus) throws InterruptedException{
    	
    	String parametertextvalue = null;
    	Thread.sleep(1000);
    	
    	if (transactionstatus.contentEquals("Settling")) {
    	
    			if (parametername.contentEquals("Card Type")) {
    		
    					Thread.sleep(2000);
    					WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[4]/dl/dd[3]"));
    					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    					parametertextvalue = parametervalue.getText();
    			}
    			else if (parametername.contentEquals("Cardholder Name")) {
    					
    					Thread.sleep(2000);
    					WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[4]/dl/dd[4]"));
    					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    					parametertextvalue = parametervalue.getText();
    			}	
    			else if (parametername.contentEquals("Credit Card Number")) {
    		
    					Thread.sleep(2000);
    					WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[4]/dl/dd[5]/span"));
    					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    					parametertextvalue = parametervalue.getText();
    					System.out.println("Before Masking in Console Admin Format(BT Actual Card Number Value): "+parametertextvalue);
    		
    		
    					char[] maskedparametertextvalue = parametertextvalue.toCharArray();
    					maskedparametertextvalue[4] = '*';
    					maskedparametertextvalue[5] = '*';
    					parametertextvalue = String.valueOf(maskedparametertextvalue);
    	
    					System.out.println("After Masking in Console Admin Format(Card Number Value): "+parametertextvalue);
    			}	
    			else if (parametername.contentEquals("Expiration Date")) {
    		
    					Thread.sleep(2000);
    					WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[4]/dl/dd[7]"));
    					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
    					parametertextvalue = parametervalue.getText();
    			}
    	}
    	else if (transactionstatus.contentEquals("Authorized")) {
    		
				if (parametername.contentEquals("Card Type")) {
	    		
						Thread.sleep(2000);
						WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[5]/dl/dd[3]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
						parametertextvalue = parametervalue.getText();
				}
				else if (parametername.contentEquals("Cardholder Name")) {
				
						Thread.sleep(2000);
						WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[5]/dl/dd[4]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
						parametertextvalue = parametervalue.getText();
				}	
				else if (parametername.contentEquals("Credit Card Number")) {
	
						Thread.sleep(2000);
						WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[5]/dl/dd[5]/span"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
						parametertextvalue = parametervalue.getText();
						System.out.println("Before Masking in Console Admin Format(BT Actual Card Number Value): "+parametertextvalue);
						
						char[] maskedparametertextvalue = parametertextvalue.toCharArray();
						maskedparametertextvalue[4] = '*';
						maskedparametertextvalue[5] = '*';
						parametertextvalue = String.valueOf(maskedparametertextvalue);

						System.out.println("After Masking in Console Admin Format(Card Number Value): "+parametertextvalue);
				}	
				else if (parametername.contentEquals("Expiration Date")) {
	
						Thread.sleep(2000);
						WebElement parametervalue = driver.findElement(By.xpath("//div[@class='content_wrap-inner']/div[5]/dl/dd[7]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", parametervalue);
						parametertextvalue = parametervalue.getText();
	
				}
    	}
    	
    	return parametertextvalue;		
    }
}
