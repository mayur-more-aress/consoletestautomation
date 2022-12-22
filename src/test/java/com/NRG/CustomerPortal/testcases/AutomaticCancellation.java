package com.NRG.CustomerPortal.testcases;
import java.awt.AWTException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.NRG.OldShoppingCart.testcases.DomainAndProduct_Order;
import com.commonfunctionality.NRGOld_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.nrgcustomerportal.pages.NRGCAccountTabPage;
import com.nrgcustomerportal.pages.NRGCCancelServicesPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.salesdb.testcases.ProductProvisiningTest;
import com.util.TestUtil;

public class AutomaticCancellation extends NRGOld_CommonFunctionality
{
	NRGLoginPage nrgloginpage;
	NRGHeaderPage nrgheaderpage;
	DomainAndProduct_Order domainandproductorder;
	NRGCAccountTabPage nrgcaccounttabpage;
	NRGCCancelServicesPage nrgccancelservicespage;
	ProductProvisiningTest salesdbproductprovisining;
	String workflowStatusAfterCompletionManually = null;
	String workflowStatusAfterCompletionBySchedular = null;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	TestUtil testUtil;
	public static ExtentTest logger;

	String strPassword = null;
	String accountReference = null;
	String strDomainName = null;
	String strTld = null;
	boolean flag = true;
	String strRegisterDomain;
	String strDomainPassword;
	String strWorkflowStatus;
	String strWorkflowId=null;
	String registerdomain;


	public AutomaticCancellation() 
	{
		super();
	}


	//Purchase a Web hosting(CPanel Starter)product
	public void registerDomainAndWebHostingProduct(String environment) throws InterruptedException, AWTException
	{
		domainandproductorder=new DomainAndProduct_Order();
		domainandproductorder.verifyDomainRegistrationAndHostingServiceForExistingCustomerUsingPrepaidCredit(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifyWebHostingProductCancellation(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";

		//Pre-requisite: Domain registration with web hosting product
		registerDomainAndWebHostingProduct(environment);

		System.out.println("Start Test: verifyWebHostingProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		nrgcaccounttabpage=new NRGCAccountTabPage();
		nrgcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		nrgccancelservicespage=nrgcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		nrgccancelservicespage.selectDomainName(DomainAndProduct_Order.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		nrgccancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		nrgccancelservicespage.selectCancellationReasonFifth();
		test.log(LogStatus.INFO, "Click on cancel service button");
		nrgccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();

		// Test Step 6: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
		switch (environment) 
		{
		case "dev8":
			workflowStatusAfterCompletionManually = caworkflowadminpage.processCancellationWorkflow();
			Assert.assertEquals(workflowStatusAfterCompletionManually, "Cancelled","cancellation workflow completed successfully by staff");

		case "uat1":
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
			Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "Cancelled","cancellation workflow not completed successfully by schedular");
		}
		driver.quit();
		System.out.println("End Test: verifyWebHostingProductCancellation");
	}

	//Purchase a Email hosting product
	public void registerDomainAndEmailHostingProduct(String environment) throws InterruptedException, AWTException
	{
		domainandproductorder=new DomainAndProduct_Order();
		domainandproductorder.verifyDomainRegistrationAndEmailProductForExistingBTCustomerUsingExistingCard(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifyEmailHostingProductCancellation(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";

		//Pre-requisite: Domain registration with email hosting product
		registerDomainAndEmailHostingProduct(environment);

		System.out.println("Start Test: verifyEmailHostingProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		nrgcaccounttabpage=new NRGCAccountTabPage();
		nrgcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		nrgccancelservicespage=nrgcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		nrgccancelservicespage.selectDomainName(DomainAndProduct_Order.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		nrgccancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		nrgccancelservicespage.selectCancellationReasonFourth();
		test.log(LogStatus.INFO, "Click on cancel service button");
		nrgccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();

		// Test Step 6: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
		workflowStatusAfterCompletionManually = caworkflowadminpage.processCancellationWorkflow();
		Assert.assertEquals(workflowStatusAfterCompletionManually, "Cancelled","cancellation workflow completed successfully by staff");
		driver.quit();
		System.out.println("End Test: verifyEmailHostingProductCancellation");
	}

	//Purchase a Domain Manager product
	public void registerDomainAndDomainManagerProduct(String environment) throws InterruptedException, AWTException
	{
		domainandproductorder=new DomainAndProduct_Order();
		domainandproductorder.verifyDomainRegistrationAndDomainManagerAddOnForExistingCustomerUsingExistingCard(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifyDomainManagerProductCancellation(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";

		//Pre-requisite: Domain registration with domain manager product
		registerDomainAndDomainManagerProduct(environment);

		System.out.println("Start Test: verifyDomainManagerProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		nrgcaccounttabpage=new NRGCAccountTabPage();
		nrgcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		nrgccancelservicespage=nrgcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		nrgccancelservicespage.selectDomainName(DomainAndProduct_Order.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		nrgccancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		nrgccancelservicespage.selectCancellationReasonThird();
		test.log(LogStatus.INFO, "Click on cancel service button");
		nrgccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();

		// Test Step 6: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
		switch (environment) 
		{
		case "dev8":
			workflowStatusAfterCompletionManually = caworkflowadminpage.processCancellationWorkflow();
			Assert.assertEquals(workflowStatusAfterCompletionManually, "Cancelled","cancellation workflow completed successfully by staff");

		case "uat1":
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
			Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "Cancelled","cancellation workflow not completed successfully by schedular");
		}
		driver.quit();
		System.out.println("End Test: verifyDomainManagerProductCancellation");
	}

	//Purchase a Domain Manager and Domain Privacy product
	public void registerDomainAndDomainManagerDomainPrivacyProduct(String environment) throws InterruptedException, AWTException
	{
		salesdbproductprovisining=new ProductProvisiningTest();
		salesdbproductprovisining.verify_Domain_and_DomainManagerDomainPrivacy_Order_InSalesDB(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifyDomainManagerAndDomainPrivacyProductCancellation(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";

		//Pre-requisite: Domain registration with Domain Manager and Domain Privacy product
		registerDomainAndDomainManagerDomainPrivacyProduct(environment);

		System.out.println("Start Test: verifyDomainManagerAndDomainPrivacyProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		nrgcaccounttabpage=new NRGCAccountTabPage();
		nrgcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		nrgccancelservicespage=nrgcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		nrgccancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		nrgccancelservicespage.tickSelectService();
		nrgccancelservicespage.tickSelectServiceDomainPrivacy();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		nrgccancelservicespage.selectCancellationReasonSecond();
		test.log(LogStatus.INFO, "Click on cancel service button");
		nrgccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();

		// Test Step 6: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
		switch (environment) 
		{
		case "dev8":
			workflowStatusAfterCompletionManually = caworkflowadminpage.processCancellationWorkflow();
			Assert.assertEquals(workflowStatusAfterCompletionManually, "Cancelled","cancellation workflow completed successfully by staff");

		case "uat1":
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
			Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "Cancelled","cancellation workflow not completed successfully by schedular");
		}
		driver.quit();
		System.out.println("End Test: verifyDomainManagerAndDomainPrivacyProductCancellation");
	}

	//Purchase a Domain Privacy product
	public void registerDomainAndDomainPrivcyProduct(String environment) throws InterruptedException, AWTException
	{
		salesdbproductprovisining=new ProductProvisiningTest();
		salesdbproductprovisining.verify_Domain_and_DomainPrivacy_Order_InSalesDB(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifyDomainPrivacyProductCancellation(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";

		//Pre-requisite: Domain registration with Domain Privacy product
		registerDomainAndDomainPrivcyProduct(environment);

		System.out.println("Start Test: verifyDomainPrivacyProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(accountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		nrgcaccounttabpage=new NRGCAccountTabPage();
		nrgcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		nrgccancelservicespage=nrgcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		nrgccancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		nrgccancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		nrgccancelservicespage.selectCancellationReasonFirst();
		test.log(LogStatus.INFO, "Click on cancel service button");
		nrgccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();

		// Test Step 6: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
		workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();	
		driver.quit();
		System.out.println("End Test: verifyDomainPrivacyProductCancellation");
	}
	
	//Purchase a MIXED - NON-solution product + component product
		public void registerDomainWithNonSolutionAndComponentProduct(String environment) throws InterruptedException, AWTException
		{
			salesdbproductprovisining=new ProductProvisiningTest();
			salesdbproductprovisining.verify_Domain_and_ComponentProduct_InSalesDB(environment);
		}

		@Parameters({ "environment"})
		@Test
		public void verifyNonSolutionProductAndComponentProductCancellation(String environment) throws InterruptedException, AWTException
		{
			// Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "ARQ-45";

			//Pre-requisite: Domain registration with Non-Solution product and Component product
			registerDomainWithNonSolutionAndComponentProduct(environment);

			System.out.println("Start Test: verifyNonSolutionProductAndComponentProductCancellation");
			test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_netregistry");

			// Test Step 1: Login to customer portal
			test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
			nrgloginpage = new NRGLoginPage();
			nrgloginpage.setLoginDetails(accountReference, strPassword);
			nrgheaderpage = nrgloginpage.clickLoginButton();
			test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");

			// Test Step 2: Click on account tab and click on cancel services link
			test.log(LogStatus.INFO, "Click on account tab link");
			nrgcaccounttabpage=new NRGCAccountTabPage();
			nrgcaccounttabpage.clickAccountTabLink();
			test.log(LogStatus.INFO, "Click on cancel services link");
			nrgccancelservicespage=nrgcaccounttabpage.clickOnCancelServicesLink();

			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Select the domain name");
			nrgccancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
			test.log(LogStatus.INFO, "Tick on select services checkbox ");
			nrgccancelservicespage.tickSelectService();
			nrgccancelservicespage.tickSelectServiceDomainPrivacy();
			test.log(LogStatus.INFO, "Select the cancellation reason");
			nrgccancelservicespage.selectCancellationReasonSecond();
			test.log(LogStatus.INFO, "Click on cancel service button");
			nrgccancelservicespage.clickOnCancelServiceButton();

			//Test Step 4: Get the refrence id
			getWorkflowId();
			driver.quit();

			// Test Step 5: Login to console admin 
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.clickOnWorkflowId();

			// Test Step 6: Verify workflow status and perform the execution of workflow
			test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();	
			driver.quit();
			System.out.println("End Test: verifyNonSolutionProductAndComponentProductCancellation");
		}

		//Purchase a MIXED - solution product + component product
		public void registerDomainWithSolutionAndComponentProduct(String environment) throws InterruptedException, AWTException
		{
			salesdbproductprovisining=new ProductProvisiningTest();
			salesdbproductprovisining.verify_Domain_and_SolutionProduct_InSalesDB(environment);
		}

		@Parameters({ "environment"})
		@Test
		public void verifySolutionProductAndComponentProductCancellation(String environment) throws InterruptedException, AWTException
		{
			// Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "ARQ-45";

			//Pre-requisite: Domain registration with solution and component product
			registerDomainWithSolutionAndComponentProduct(environment);

			System.out.println("Start Test: verifyNonSolutionProductAndComponentProductCancellation");
			test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_netregistry");

			// Test Step 1: Login to customer portal
			test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
			nrgloginpage = new NRGLoginPage();
			nrgloginpage.setLoginDetails(accountReference, strPassword);
			nrgheaderpage = nrgloginpage.clickLoginButton();
			test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");

			// Test Step 2: Click on account tab and click on cancel services link
			test.log(LogStatus.INFO, "Click on account tab link");
			nrgcaccounttabpage=new NRGCAccountTabPage();
			nrgcaccounttabpage.clickAccountTabLink();
			test.log(LogStatus.INFO, "Click on cancel services link");
			nrgccancelservicespage=nrgcaccounttabpage.clickOnCancelServicesLink();

			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Select the domain name");
			nrgccancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
			test.log(LogStatus.INFO, "Tick on select services checkbox ");
			nrgccancelservicespage.tickSelectService();
			nrgccancelservicespage.tickSelectServiceDomainPrivacy();
			test.log(LogStatus.INFO, "Select the cancellation reason");
			nrgccancelservicespage.selectCancellationReasonSecond();
			test.log(LogStatus.INFO, "Click on cancel service button");
			nrgccancelservicespage.clickOnCancelServiceButton();

			//Test Step 4: Get the refrence id
			getWorkflowId();
			driver.quit();

			// Test Step 5: Login to console admin 
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.clickOnWorkflowId();

			// Test Step 6: Verify workflow status and perform the execution of workflow
			test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
			switch (environment) 
			{
			case "dev2":
				workflowStatusAfterCompletionManually = caworkflowadminpage.processCancellationWorkflow();
				Assert.assertEquals(workflowStatusAfterCompletionManually, "Cancelled","cancellation workflow completed successfully by staff");

			case "uat1":
				workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
				Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "Cancelled","cancellation workflow not completed successfully by schedular");
			}
			driver.quit();
			System.out.println("End Test: verifyDomainManagerAndDomainPrivacyProductCancellation");
		}
		
		//Purchase a domain backorder product
			public void registerDomainWithDomainBackorderProduct(String environment) throws InterruptedException, AWTException
			{
				salesdbproductprovisining=new ProductProvisiningTest();
				salesdbproductprovisining.verify_Domain_and_DomainBack_Order_InSalesDB(environment);
			}

			@Parameters({ "environment"})
			@Test
			public void verifyDomainBackorderProductCancellation(String environment) throws InterruptedException, AWTException
			{
				// Initialization (Test Data Creation and Assignment)
				strPassword = "comein22";
				accountReference = "ARQ-45";

				//Pre-requisite: Domain registration with domain backorder product
				registerDomainWithDomainBackorderProduct(environment);

				System.out.println("Start Test: verifyDomainBackorderProductCancellation");
				test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
				initialization(environment, "customerportalurl_netregistry");

				// Test Step 1: Login to customer portal
				test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
				nrgloginpage = new NRGLoginPage();
				nrgloginpage.setLoginDetails(accountReference, strPassword);
				nrgheaderpage = nrgloginpage.clickLoginButton();
				test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");

				// Test Step 2: Click on account tab and click on cancel services link
				test.log(LogStatus.INFO, "Click on account tab link");
				nrgcaccounttabpage=new NRGCAccountTabPage();
				nrgcaccounttabpage.clickAccountTabLink();
				test.log(LogStatus.INFO, "Click on cancel services link");
				nrgccancelservicespage=nrgcaccounttabpage.clickOnCancelServicesLink();

				// Test Step 3: Enter the domain details and submit the details
				test.log(LogStatus.INFO, "Select the domain name");
				nrgccancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
				test.log(LogStatus.INFO, "Tick on select services checkbox ");
				nrgccancelservicespage.tickSelectService();
				test.log(LogStatus.INFO, "Select the cancellation reason");
				nrgccancelservicespage.selectCancellationReasonSecond();
				test.log(LogStatus.INFO, "Click on cancel service button");
				nrgccancelservicespage.clickOnCancelServiceButton();

				//Test Step 4: Get the refrence id
				getWorkflowId();
				driver.quit();

				// Test Step 5: Login to console admin 
				loginToConsoleAdmin(environment);
				caheaderpage=new CAHeaderPage();
				caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
				caworkflowadminpage.clickOnWorkflowId();

				// Test Step 6: Verify workflow status and perform the execution of workflow
				test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
				switch (environment) 
				{
				case "dev2":
					workflowStatusAfterCompletionManually = caworkflowadminpage.processCancellationWorkflow();
					Assert.assertEquals(workflowStatusAfterCompletionManually, "Cancelled","cancellation workflow completed successfully by staff");

				case "uat1":
					workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
					Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "Cancelled","cancellation workflow not completed successfully by schedular");
				}
				driver.quit();
				System.out.println("End Test: verifyDomainBackorderProductCancellation");
			}
}

