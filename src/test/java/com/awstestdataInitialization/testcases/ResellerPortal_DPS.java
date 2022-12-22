package com.awstestdataInitialization.testcases;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.base.TestBase;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.mitdpsresellerportal.pages.MITDPSBulkRegisterPage;
import com.mitdpsresellerportal.pages.MITDPSLoginPage;
import com.mitdpsresellerportal.pages.MITDPSRegisterADomainPage;
import com.mitdpsresellerportal.pages.MITDPSTabPage;
import com.relevantcodes.extentreports.LogStatus;


public class ResellerPortal_DPS extends TestBase
{
	public ResellerPortal_DPS() 
	{
		super();
	}
	
	//DPS Reseller portal Pages
	MITDPSLoginPage mitdpsLoginPage;
	MITDPSTabPage mitdpsTabPage;
	MITDPSBulkRegisterPage mitdpsBulkRegisterPage;
	MITDPSRegisterADomainPage mitdpsRegisterADomainPage;
		
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CADomainLevelPage cadomainlevelpage;
		
	//Data Initialization
	String strCustomerAccountReference = null;
	String strCustomerPassword = "comein22";
	String strDomainName = null;
	String strTld = null;
	String strWorkflowId = null;
	String strWorkflowStatus = null;
	String strEligibilityIDType = "ABN";
	String strEligibilityIDNumber ="54 109 565 095";
	String strEligibilityType = "Company";
	String strCompanyName ="ARQ GROUP WHOLESALE PTY LTD";
	 String strAccountReference = null;
	ArrayList<String> lstDomainNames = null;
	ArrayList<String> lstWorkflowId = null;
	SoftAssert softAssert = new SoftAssert();
	String[] arrTldsForReg = {"com.au","com","net","org","biz","info","us"};
	String[] arrTldsForRenewal = {"com","net","org","biz","info","us"};
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	
	/*
	 *Test case : to verify Bulk domain registration from DPS Reseller portal 
	 *Scenario : Existing customer and Existing credit card
	 *Tld's to be register : "com", "net", "org", "biz", "info", "us", "com.au", "net.au", "nz"
	 */
	@Parameters({ "environment","numberofdomains","accountrefrence"})
	@Test
	public void verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard(String environment, int numberOfDomains,String accountRefrence) throws InterruptedException, IOException
	{
		
		strCustomerAccountReference = accountRefrence;
		strDomainName = "Bulkdomainreg" + df.format(d);
		
		System.out.println("Start Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
		
		// Test Step 1: Login to Reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: checkBulkDomainAvailability");
		initialization(environment, "resellerportalurl_mitdps");
		mitdpsLoginPage = new MITDPSLoginPage();
		mitdpsLoginPage.setLoginDetails(strCustomerAccountReference, strCustomerPassword);
		mitdpsTabPage = mitdpsLoginPage.clickLoginButton();
		
		// Test Step 2: Navigate to Bulk Domain Register
		test.log(LogStatus.INFO, "Navigate to Bulk Domain Register");
		mitdpsBulkRegisterPage = mitdpsTabPage.clickBulkRegisterLink();
		System.out.print("number of domains" +numberOfDomains);
	    for(int i= 0; i < numberOfDomains ; i++)
	    {
	    	strTld = arrTldsForReg[i % arrTldsForReg.length];
			System.out.print(strTld + " ");  
			lstDomainNames = mitdpsBulkRegisterPage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
	    }
	    mitdpsBulkRegisterPage.clickSearchButton();
	    
	    // Test Step 3: verify domain available for Registration
 		test.log(LogStatus.INFO, "verify domain available for Registraion");
 		int domainCount = 0;
 		for(String domainName : lstDomainNames)
 		{
 			Assert.assertEquals(mitdpsBulkRegisterPage.getSearchAvailabilityMessage(domainName, domainCount), "Available","Available");
 			domainCount++;
 		}
 		
 		// Test Step 4: Select existing customer details
 		test.log(LogStatus.INFO, "Enter exisiting customer details");
 		mitdpsRegisterADomainPage = new MITDPSRegisterADomainPage();
 		mitdpsRegisterADomainPage.selectExistingCustomer();
 		mitdpsRegisterADomainPage.selectRegistranContact("Tim Coupland");
 		
 		// Test Step 5: if array of tlds contains .au domains, enter au eligibility details
        if(Arrays.asList(arrTldsForReg).contains("com.au") || Arrays.asList(arrTldsForReg).contains("net.au"))
        {
        	mitdpsRegisterADomainPage.provideEligibilityDetailsForAu(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
        }
        
        // Test Step 6: Enter name servers and click register
        test.log(LogStatus.INFO, "Enter nameservers and click register button");
        mitdpsRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
        mitdpsRegisterADomainPage.inputNameServerFields("ns1.partnerconsole.net", "ns2.partnerconsole.net");
        mitdpsRegisterADomainPage.tickTermsAndConditions();
        mitdpsRegisterADomainPage.clickRegisterDomainButton();
        
        // Test Step 7: Verify if order is placed and fetch the orderid
 		test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
 		lstWorkflowId = mitdpsRegisterADomainPage.getMultipleReferenceID();
 		for(String refID : lstWorkflowId)
 		{
 			System.out.println("Reference ID:" + refID);
 			test.log(LogStatus.INFO, "Reference ID:" + refID);
 		}
 		test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
 		driver.quit();
 		
 		//Test Step 8: Verify if domain registration workflow is completed in console admin
 		test.log(LogStatus.INFO, "Login to admin and execute workflow");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID : lstWorkflowId)
		{
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			caworkflowadminpage.processWorkFlow(refID, strTld);
			caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
						|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
				
		}
		softAssert.assertAll();
		driver.quit();
		
		System.out.println("End Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
	}
	
	/*
	 *Test case : to verify Bulk domain registration from DPS Reseller portal and generate Renewal WF from A2 admin
	 *Scenario : Existing customer and Existing credit card
	 *Tld's to be register : "com", "net", "org", "biz", "info", "us"
	 */
	@Parameters({ "environment","numberofdomains","accountrefrence"})
	@Test
	public void generateARenewalWorkflowForDomainsFromConsoleAdmin(String environment, int numberOfDomains,String accountRefrence) throws InterruptedException, IOException
	{
		
		strCustomerAccountReference = accountRefrence;
		strDomainName = "GenerateRenewalWF" + df.format(d);
		
		System.out.println("Start Test: generateARenewalWorkflowForDomainsFromConsoleAdmin");
		
		// Test Step 1: Login to Reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: checkBulkDomainAvailability");
		initialization(environment, "resellerportalurl_mitdps");
		mitdpsLoginPage = new MITDPSLoginPage();
		mitdpsLoginPage.setLoginDetails(strCustomerAccountReference, strCustomerPassword);
		mitdpsTabPage = mitdpsLoginPage.clickLoginButton();
		
		// Test Step 2: Navigate to Bulk Domain Register
		test.log(LogStatus.INFO, "Navigate to Bulk Domain Register");
		mitdpsBulkRegisterPage = mitdpsTabPage.clickBulkRegisterLink();
		System.out.print("number of domains" +numberOfDomains);
	    for(int i= 0; i < numberOfDomains ; i++)
	    {
	    	strTld = arrTldsForRenewal[i % arrTldsForRenewal.length];
			System.out.print(strTld + " ");  
			lstDomainNames = mitdpsBulkRegisterPage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
	    }
	    mitdpsBulkRegisterPage.clickSearchButton();
	    
	    // Test Step 3: verify domain available for Registration
 		test.log(LogStatus.INFO, "verify domain available for Registraion");
 		int domainCount = 0;
 		for(String domainName : lstDomainNames)
 		{
 			Assert.assertEquals(mitdpsBulkRegisterPage.getSearchAvailabilityMessage(domainName, domainCount), "Available","Available");
 			domainCount++;
 		}
 		
 		// Test Step 4: Select existing customer details
 		test.log(LogStatus.INFO, "Enter exisiting customer details");
 		mitdpsRegisterADomainPage = new MITDPSRegisterADomainPage();
 		mitdpsRegisterADomainPage.selectExistingCustomer();
 		mitdpsRegisterADomainPage.selectRegistranContact("Tim Coupland");
 		
 		// Test Step 5: if array of tlds contains .au domains, enter au eligibility details
        if(Arrays.asList(arrTldsForRenewal).contains("com.au") || Arrays.asList(arrTldsForRenewal).contains("net.au"))
        {
        	mitdpsRegisterADomainPage.provideEligibilityDetailsForAu(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
        }
        
        // Test Step 6: Enter name servers and click register
        test.log(LogStatus.INFO, "Enter nameservers and click register button");
        mitdpsRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
        mitdpsRegisterADomainPage.inputNameServerFields("ns1.partnerconsole.net", "ns2.partnerconsole.net");
        mitdpsRegisterADomainPage.tickTermsAndConditions();
        mitdpsRegisterADomainPage.clickRegisterDomainButton();
        
        // Test Step 7: Verify if order is placed and fetch the orderid
 		test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
 		lstWorkflowId = mitdpsRegisterADomainPage.getMultipleReferenceID();
 		for(String refID : lstWorkflowId)
 		{
 			System.out.println("Reference ID:" + refID);
 			test.log(LogStatus.INFO, "Reference ID:" + refID);
 		}
 		test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
 		driver.quit();
 		
 		//Test Step 8: Verify if domain registration workflow is completed in console admin
 		test.log(LogStatus.INFO, "Login to admin and execute workflow");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID : lstWorkflowId)
		{
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			caworkflowadminpage.processWorkFlow(refID, strTld);
			caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
					|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
			if(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed"))
			{
				String domainName = caworkflowadminpage.getEntityName();
				String testdata[]=domainName.split("\\.",2);
				strDomainName = testdata[0];
				System.out.println("Domain Name: " + strDomainName);
				strTld = "."+testdata[1];
				System.out.println("Domain tld: " + strTld);
				if(strTld.contains("au"))
				{
					System.out.println("Renewal workflows can't be generated for .au domains");
				}
				else
				{
					cadomainlevelpage = caheaderpage.searchDomain(strDomainName+strTld);
					cadomainlevelpage.clickGenerateRenewalWorkflow();
					test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
					caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName+strTld);
					String workflowType = caworkflowadminpage.searchWorkflowType("renewal2");
					if(workflowType.equalsIgnoreCase("renewal2"))
					{
						System.out.println("Renewal workflow created for domain: "+strDomainName+strTld);
						test.log(LogStatus.INFO,"Renewal workflow created for domain: "+strDomainName+strTld);
					}
					softAssert.assertEquals(workflowType, "renewal2", "renewal2 workflow is not created");
				}
			}
			else
			{
				System.out.println("Domain registration failed for RefId: "+ refID+"Cant generate renewal workflow");
				test.log(LogStatus.INFO,"Domain registration failed for RefId: "+ refID+"Cant generate renewal workflow");
			}
		}
		softAssert.assertAll();
		driver.quit();
		
		System.out.println("End Test: generateARenewalWorkflowForDomainsFromConsoleAdmin");
	}
	
	
	/*
	 *Test case : to verify Bulk domain and product registration from DPS Reseller portal 
	 *Scenario : Existing customer and Existing credit card
	 *
	 */
	@Parameters({ "environment","numberofdomains","accountrefrence"})
	@Test
	public void verifyDomainAndProductRegistrationForExistingCustomerUsingExistingCard(String environment, int numberOfDomains,String accountRefrence) throws Exception
	{
		strCustomerAccountReference = accountRefrence;
		
		strDomainName = "DomainAndProdcutReg" + df.format(d);
		String arrtlds[] = {"com","net"};
		
		System.out.println("Start Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
		
		for(String strTld : arrtlds )
		{	
			
			// Test Step 1: Login to Reseller portal
			test.log(LogStatus.INFO, "Login to Reseller portal");
			System.out.println("Start Test: verifyDomainRegistrationInResellerPortal");
			initialization(environment, "resellerportalurl_mitdps");
			mitdpsLoginPage = new MITDPSLoginPage();
			mitdpsLoginPage.setLoginDetails(strCustomerAccountReference, "comein22");
			mitdpsTabPage = mitdpsLoginPage.clickLoginButton();

			// Test Step 2: Navigate to Register Domain
			test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
			mitdpsTabPage.clickDomainsTab();
			mitdpsRegisterADomainPage = mitdpsTabPage.clickRegisterLink();
			

			// Test Step 3: Register a domain
			test.log(LogStatus.INFO, "Verify search result message");
			mitdpsRegisterADomainPage.setDomainNameAndTld(strDomainName, "." + strTld);
			mitdpsRegisterADomainPage.selectExistingCustomer();
			mitdpsRegisterADomainPage.selectRegistranContact("Tim Coupland");
			
			if (Arrays.asList(arrtlds).contains("com.au") || Arrays.asList(arrtlds).contains("net.au")) 
			{
				System.out.println("This is the namespace " + strTld + ". Eligibility details is requied.");
				mitdpsRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Company");
			}
			mitdpsRegisterADomainPage.selectHosting("Domain Manager 1 Year");
			mitdpsRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
			mitdpsRegisterADomainPage.inputNameServerFields("ns1.partnerconsole.net", "ns2.partnerconsole.net");
			mitdpsRegisterADomainPage.tickTermsAndConditions();
			mitdpsRegisterADomainPage.clickRegisterDomainButton();
			
			// Test Step 4: Get the Order Reference ID
			test.log(LogStatus.INFO, "Verify if order is completed and get the reference ID if it is");
			Assert.assertTrue(mitdpsRegisterADomainPage.isOrderComplete(
					"domain order for " + strDomainName + "." + strTld + " created."), "Order is not completed");
			strWorkflowId = mitdpsRegisterADomainPage.getSingleReferenceID();
			System.out.println("Reference ID:" + strWorkflowId);
			driver.quit();
			
			//Test Step 5: Verify if domain registration workflow is completed in console admin
	 		test.log(LogStatus.INFO, "Login to admin and execute workflow");
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processWorkFlow(strWorkflowId, strTld);
			caworkflowadminpage.processProductSetupWF();
			caheaderpage.searchWorkflow(strDomainName+"."+strTld );
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("productSetup2");
			System.out.println( "Workflow status"+strWorkflowStatus);
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved") ,"The productSetup2 WF " +strWorkflowId+ " FAILED");
		
		}
		softAssert.assertAll();
		driver.quit();
		System.out.println("End Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
	}
}
