package com.base;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.PageLoadStrategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;
import com.util.WebEventListener;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	static Environment testEnvironment;
	public static ExtentReports report;
	public static ExtentTest test;

	@BeforeSuite
	public void suiteSetup() {
		report = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
	}

	@BeforeMethod
	public void registerMethod(Method method) {
		test = report.startTest(method.getName());
		test.log(LogStatus.INFO, "Test" + method.getName() + " has been started");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception {

		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Test" + result.getName() + " PASSED");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test" + result.getName() + " FAILED");
			test.log(LogStatus.FAIL, "Test failure" + result.getThrowable());
			
			String Screenshotpath	= TestBase.getScreenhot(driver, result.getName());
			test.log(LogStatus.FAIL,test.addScreenCapture(Screenshotpath));

		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test" + result.getName() + " SKIPPED");
		}

		test.log(LogStatus.INFO, "Test" + result.getName() + " completed");

		report.endTest(test);
	}

	@AfterSuite
	public void tearDown() {
		report.flush();
		report.close();
	}

	public TestBase() {

	}

	public static void initialization(String environment, String entrypoint) {

		ConfigFactory.setProperty("env", environment);
		testEnvironment = ConfigFactory.create(Environment.class);
		System.out.println("Environment: " + environment);
		String browserName = testEnvironment.browser();

		if (browserName.equals("chrome")) {

			System.setProperty("webdriver.chrome.driver", "seleniumwebdriver/chromedriver/chromedriver.exe");
//
	        ChromeOptions options = new ChromeOptions();
//	       
//	        options.addArguments("--disable-gpu");
//	        options.addArguments("--disable-browser-side-navigation");
	        options.setPageLoadStrategy(PageLoadStrategy.NONE);
//	        
//	        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//	        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
	        driver = new ChromeDriver(options);
//			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
		} else if (browserName.equals("firefox")) {

			System.setProperty("webdriver.gecko.driver", "seleniumwebdriver/firefoxdriver/geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equals("edge")) {

			System.setProperty("webdriver.edge.driver", "seleniumwebdriver/edgedriver/MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}

		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		//driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		// Sales DB
		if (entrypoint.equals("salesdburl")) {
			driver.get(testEnvironment.salesdburl());
		}

		// Console Admin
		else if (entrypoint.equals("consoleadmin")) {
			driver.get(testEnvironment.consoleadminurl());
		}

		// Old Shopping Cart
		else if (entrypoint.equals("oldcart_domainsearchurl_domainz")) {
			driver.get(testEnvironment.oldcart_domainsearchurl_domainz());
		} else if (entrypoint.equals("oldcart_domainsearchurl_netregistry")) {
			driver.get(testEnvironment.oldcart_domainsearchurl_netregistry());
		} else if (entrypoint.equals("oldcart_domainsearchurl_melbourneit")) {
			driver.get(testEnvironment.oldcart_domainsearchurl_melbourneit());
		}else if (entrypoint.equals("oldcart_domainsearchurl_tppw")) {
			driver.get(testEnvironment.oldcart_domainsearchurl_tppw());
		}

		// New Shopping Cart
		else if (entrypoint.equals("newcart_domainsearchurl_netregistry")) {
			driver.get(testEnvironment.newcart_domainsearchurl_netregistry());
		}

		// Customer Portal
		else if (entrypoint.equals("customerportalurl_domainz")) {
			driver.get(testEnvironment.customerportalurl_domainz());
		} else if (entrypoint.equals("customerportalurl_netregistry")) {
			driver.get(testEnvironment.customerportalurl_netregistry());
		} else if (entrypoint.equals("customerportalurl_melbourneit")) {
			driver.get(testEnvironment.customerportalurl_melbourneit());
		}else if (entrypoint.equals("customerportalurl_tpp")) {
			driver.get(testEnvironment.customerportalurl_tpp());
		}else if (entrypoint.equals("customerportalurl_mitdps")) {
			driver.get(testEnvironment.customerportalurl_mitdps());
		}
		
		//Obsidian URl
		else if (entrypoint.equals("obsidian_url")) {
			driver.get(testEnvironment.obsidian_url());
		}
		
		// Reseller Portal
		else if (entrypoint.equals("resellerportalurl_tpp")) {
			driver.get(testEnvironment.resellerportalurl_tpp());
		}
		
		// RRPproxy Portal
		else if (entrypoint.equals("rrpproxy_tpp")){
			driver.get(testEnvironment.rrpproxy_tpp());
		}

		// Payment Gateway
		else if (entrypoint.equals("braintree")) {
			driver.get(testEnvironment.braintreeurl());
		}
		
		// ResellerApi Portal
		else if (entrypoint.equals("resellerapiportalurl_mitdps")) {
				driver.get(testEnvironment.resellerapiportalurl_mitdps());
				}

		else if (entrypoint.equals("resellerapiportalurl_tpp")) {
				driver.get(testEnvironment.resellerapiportalurl_tpp());
				}

		// Others
		else if (entrypoint.equals("cart")) {
			driver.get(testEnvironment.carturl());
		} else if (entrypoint.equals("cartlogin")) {
			driver.get(testEnvironment.cartloginurl());
		} else if(entrypoint.equals("oldcart_domainsearchurl_webcentral")){
			driver.get(testEnvironment.oldcart_domainsearchurl_webcentral());
		}else if(entrypoint.equals("customerportalurl_webcentral")){
			driver.get(testEnvironment.customerportalurl_webcentral());
		}
		else if(entrypoint.equals("resellerportalurl_mitdps")){
			driver.get(testEnvironment.resellerportalurl_mitdps());
		}else {
			/* for any url */
		}
	}
	

	public static void testStepResultVerification(WebElement webElement) throws InterruptedException {

		try {
			if (webElement.isDisplayed()) {
				test.log(LogStatus.PASS, "STEP PASSED");
			} else {
				test.log(LogStatus.FAIL, "STEP FAILED");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "STEP FAILED");
		}

	}
	
	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}
