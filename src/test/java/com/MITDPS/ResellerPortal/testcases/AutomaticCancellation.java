package com.MITDPS.ResellerPortal.testcases;
import java.awt.AWTException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MITDPS_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.mitcustomerportal.pages.MITCAccountTabPage;
import com.mitdpscustomerportal.pages.MITDPSAccountTabPage;
import com.mitdpscustomerportal.pages.MITDPSCancelServicesPage;
import com.mitdpscustomerportal.pages.MITDPSHeaderPage;
import com.mitdpscustomerportal.pages.MITDPSLoginPage;
import com.mitdpsresellerportal.pages.MITDPSManageDomainsPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.salesdb.testcases.ProductProvisiningTest;
import com.util.TestUtil;

public class AutomaticCancellation extends MITDPS_CommonFunctionality
{
	MITDPSLoginPage mitdpsLoginPage;
	DomainAnd_ProductOrder domainandproductorder;
	MITCAccountTabPage mitdpsTabPage;
	String workflowStatusAfterCompletionManually = null;
	String workflowStatusAfterCompletionBySchedular = null;
	MITDPSHeaderPage mitdpsheaderpage;
	MITDPSAccountTabPage mitdpsaccounttabpage;
	MITDPSCancelServicesPage mitdpscancelservicespage;
	ProductProvisiningTest salesdbproductprovisining;
	MITDPSManageDomainsPage mitdpsmanagedomainspage;


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
	String strResponseDomain;
	String registerdomain;


	public AutomaticCancellation() 
	{
		super();
	}

	//Purchase a Web hosting product
	public void registerDomainAndWebHostingProduct(String environment) throws Exception
	{
		domainandproductorder=new DomainAnd_ProductOrder();
		domainandproductorder.verifyDomainAndWebHostingProductRegistration(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifyWebHostingProductCancellation(String environment) throws Exception
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6040";

		registerDomainAndWebHostingProduct(environment);
		System.out.println("Start Test: verifyWebHostingProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_mitdps");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitdpsaccounttabpage=new MITDPSAccountTabPage();
		mitdpsaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitdpscancelservicespage=mitdpsaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		mitdpscancelservicespage.selectDomainName(DomainAnd_ProductOrder.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitdpscancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitdpscancelservicespage.selectCancellationReasonFifth();
		test.log(LogStatus.INFO, "Click on cancel service button");
		mitdpscancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
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
	
	//Purchase a email hosting product
	public void registerDomainAndEmailHostingProduct(String environment) throws Exception
	{
		domainandproductorder=new DomainAnd_ProductOrder();
		domainandproductorder.verifyPurchaseOffice365Product(environment);
	}


	@Parameters({ "environment"})
	@Test
	public void verifyEmailHostingProductCancellation(String environment) throws Exception
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6040";

		registerDomainAndEmailHostingProduct(environment);
		System.out.println("Start Test: verifyEmailHostingProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_mitdps");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitdpsaccounttabpage=new MITDPSAccountTabPage();
		mitdpsaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitdpscancelservicespage=mitdpsaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		mitdpscancelservicespage.selectDomainName(DomainAnd_ProductOrder.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitdpscancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitdpscancelservicespage.selectCancellationReasonFourth();
		test.log(LogStatus.INFO, "Click on cancel service button");
		mitdpscancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();
		workflowStatusAfterCompletionManually = caworkflowadminpage.processCancellationWorkflow();
		Assert.assertEquals(workflowStatusAfterCompletionManually, "Cancelled","cancellation workflow completed successfully by staff");
		driver.quit();
		System.out.println("End Test: verifyEmailHostingProductCancellation");
	}
	
	//Purchase a domain manager product
	public void registerDomainAndDomainManagerProduct(String environment) throws Exception
	{
		domainandproductorder=new DomainAnd_ProductOrder();
		domainandproductorder.verifyDomainAndDomainManagerProductRegistrationInResellerPortal(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifyDomainManagerProductCancellation(String environment) throws Exception
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6040";

		registerDomainAndDomainManagerProduct(environment);
     	System.out.println("Start Test: verifyDomainManagerProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_mitdps");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitdpsaccounttabpage=new MITDPSAccountTabPage();
		mitdpsaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitdpscancelservicespage=mitdpsaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		mitdpscancelservicespage.selectDomainName(DomainAnd_ProductOrder.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitdpscancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitdpscancelservicespage.selectCancellationReasonThird();
		test.log(LogStatus.INFO, "Click on cancel service button");
		mitdpscancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
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

	//Purchase a domain manager and domain privacy product
	public void registerDomainAndDomainManagerDomainPrivcayProduct(String environment) throws InterruptedException, AWTException
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
		accountReference = "MEL-6040";

		registerDomainAndDomainManagerDomainPrivcayProduct(environment);

		System.out.println("Start Test: verifyDomainManagerAndDomainPrivacyProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_mitdps");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitdpsaccounttabpage=new MITDPSAccountTabPage();
		mitdpsaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitdpscancelservicespage=mitdpsaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		mitdpscancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitdpscancelservicespage.tickSelectService();
		mitdpscancelservicespage.tickSelectServiceDomainPrivacy();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitdpscancelservicespage.selectCancellationReasonSecond();

		test.log(LogStatus.INFO, "Click on cancel service button");
		mitdpscancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
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
	
	//Purchase a MIXED - NON-solution product + component product
		public void registerDomainWitnNonSolutionAndComponentProduct(String environment) throws InterruptedException, AWTException
		{
			salesdbproductprovisining=new ProductProvisiningTest();
			salesdbproductprovisining.verify_Domain_and_ComponentProduct_InSalesDB(environment);
		}

		@Parameters({ "environment"})
		@Test
		public void verifyNonSolutionAndComponentProductCancellation(String environment) throws InterruptedException, AWTException
		{
			// Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6040";

			registerDomainWitnNonSolutionAndComponentProduct(environment);

			System.out.println("Start Test: verifyNonSolutionAndComponentProductCancellation");
			test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_mitdps");

			// Test Step 1: Login to customer portal
			loginToCustomerPortal(accountReference,strPassword);

			// Test Step 2: Click on account tab and click on cancel services link
			test.log(LogStatus.INFO, "Click on account tab link");
			mitdpsaccounttabpage=new MITDPSAccountTabPage();
			mitdpsaccounttabpage.clickAccountTabLink();
			test.log(LogStatus.INFO, "Click on cancel services link");
			mitdpscancelservicespage=mitdpsaccounttabpage.clickOnCancelServicesLink();

			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Select the domain name");
			mitdpscancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
			test.log(LogStatus.INFO, "Tick on select services checkbox ");
			mitdpscancelservicespage.tickSelectService();
			mitdpscancelservicespage.tickSelectServiceDomainPrivacy();
			test.log(LogStatus.INFO, "Select the cancellation reason");
			mitdpscancelservicespage.selectCancellationReasonFirst();
			test.log(LogStatus.INFO, "Click on cancel service button");
			mitdpscancelservicespage.clickOnCancelServiceButton();

			//Test Step 4: Get the refrence id
			getWorkflowId();
			driver.quit();

			// Test Step 5: Login to console admin 
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
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
			System.out.println("End Test: verifyNonSolutionAndComponentProductCancellation");
		}


		//Purchase a MIXED - solution product + component product
		public void registerDomainWithSolutionAndComponentProduct(String environment) throws InterruptedException, AWTException
		{
			salesdbproductprovisining=new ProductProvisiningTest();
			salesdbproductprovisining.verify_Domain_and_SolutionProduct_InSalesDB(environment);
		}

		@Parameters({ "environment"})
		@Test
		public void verifySolutionAndComponentProductCancellation(String environment) throws InterruptedException, AWTException
		{
			// Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6040";

			registerDomainWithSolutionAndComponentProduct(environment);

			System.out.println("Start Test: verifySolutionAndComponentProductCancellation");
			test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_mitdps");

			// Test Step 1: Login to customer portal
			loginToCustomerPortal(accountReference,strPassword);

			// Test Step 2: Click on account tab and click on cancel services link
			test.log(LogStatus.INFO, "Click on account tab link");
			mitdpsaccounttabpage=new MITDPSAccountTabPage();
			mitdpsaccounttabpage.clickAccountTabLink();
			test.log(LogStatus.INFO, "Click on cancel services link");
			mitdpscancelservicespage=mitdpsaccounttabpage.clickOnCancelServicesLink();

			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Select the domain name");
			mitdpscancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
			test.log(LogStatus.INFO, "Tick on select services checkbox ");
			mitdpscancelservicespage.tickSelectService();
			mitdpscancelservicespage.tickSelectServiceDomainPrivacy();
			test.log(LogStatus.INFO, "Select the cancellation reason");
			mitdpscancelservicespage.selectCancellationReasonFirst();
			test.log(LogStatus.INFO, "Click on cancel service button");
			mitdpscancelservicespage.clickOnCancelServiceButton();

			//Test Step 4: Get the refrence id
			getWorkflowId();
			driver.quit();

			// Test Step 5: Login to console admin 
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
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
			System.out.println("End Test: verifySolutionAndComponentProductCancellation");
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
			accountReference = "MEL-6040";

			registerDomainWithDomainBackorderProduct(environment);

			System.out.println("Start Test: verifySolutionAndComponentProductCancellation");
			test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_mitdps");

			// Test Step 1: Login to customer portal
			loginToCustomerPortal(accountReference,strPassword);

			// Test Step 2: Click on account tab and click on cancel services link
			test.log(LogStatus.INFO, "Click on account tab link");
			mitdpsaccounttabpage=new MITDPSAccountTabPage();
			mitdpsaccounttabpage.clickAccountTabLink();
			test.log(LogStatus.INFO, "Click on cancel services link");
			mitdpscancelservicespage=mitdpsaccounttabpage.clickOnCancelServicesLink();

			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Select the domain name");
			mitdpscancelservicespage.selectDomainName(ProductProvisiningTest.registerdomain);
			test.log(LogStatus.INFO, "Tick on select services checkbox ");
			mitdpscancelservicespage.tickSelectService();
			test.log(LogStatus.INFO, "Select the cancellation reason");
			mitdpscancelservicespage.selectCancellationReasonFirst();
			test.log(LogStatus.INFO, "Click on cancel service button");
			mitdpscancelservicespage.clickOnCancelServiceButton();

			//Test Step 4: Get the refrence id
			getWorkflowId();
			driver.quit();

			// Test Step 5: Login to console admin 
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
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
			System.out.println("End Test: verifySolutionAndComponentProductCancellation");
		}

		@Parameters({ "environment"})
		@Test
		public void testLoginToResellerPortal(String environment)
				throws InterruptedException, AWTException {

			accountReference = "MEL-6040";
			initialization(environment, "consoleadmin");
			// Test Step 1: Login to Console Admin and search for Account reference
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
			caheaderpage.searchAccountReference(accountReference);
			mitdpsmanagedomainspage=caheaderpage.clickOnLoginAsResellerLink();
			Assert.assertEquals(mitdpsmanagedomainspage.verifyManageDomainsPageisDisplayed(),"Manage Domains", mitdpsmanagedomainspage.verifyManageDomainsPageisDisplayed());
			driver.quit();
		}
}

