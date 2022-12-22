package com.MIT.CustomerPortal.testcases;
import java.awt.AWTException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.MIT.OldShoppingCart.testcases.DomainAndProduct_Order;
import com.commonfunctionality.MIT_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.melbourneitwebsite.pages.MITHeaderPage;
import com.melbourneitwebsite.pages.MITLoginPage;
import com.mitcustomerportal.pages.MITCAccountTabPage;
import com.mitcustomerportal.pages.MITCCancelServicesPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.salesdb.testcases.ProductProvisiningTestMIT;
import com.util.TestUtil;

public class AutomaticCancellation extends MIT_CommonFunctionality
{
	MITLoginPage mitloginpage;
	MITHeaderPage mitheaderpage;
	MITCAccountTabPage mitcaccounttabpage;
	MITCCancelServicesPage mitccancelservicespage;
	ProductProvisiningTestMIT salesdbproductprovisining;
	DomainAndProduct_Order domainandproductorder;
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
	String strWorkflowIdnew=null;
	String strWorkflowIdnew2=null;
	String strResponseDomain;

	public AutomaticCancellation() 
	{
		super();
	}
	
	//Purchase a Web hosting product
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
		accountReference = "MEL-6005";

		registerDomainAndWebHostingProduct(environment);
		System.out.println("Start Test: verifyWebHostingProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_melbourneit");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitcaccounttabpage=new MITCAccountTabPage();
		mitcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitccancelservicespage=mitcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain name and select the cancellation reason
		test.log(LogStatus.INFO, "Select the domain name");
		mitccancelservicespage.selectDomainName(DomainAndProduct_Order.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitccancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitccancelservicespage.selectCancellationReasonFifth();
		test.log(LogStatus.INFO, "Click on cancel service button");
		mitccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
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
		accountReference = "MEL-6005";

		//registerDomainAndEmailHostingProduct(environment);
		System.out.println("Start Test: verifyEmailHostingProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_melbourneit");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitcaccounttabpage=new MITCAccountTabPage();
		mitcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitccancelservicespage=mitcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain name and select the cancellation reason
		test.log(LogStatus.INFO, "Select the domain name");
		mitccancelservicespage.selectDomainName("testconsoleregression26032021113604.com");
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitccancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitccancelservicespage.selectCancellationReasonFourth();
		test.log(LogStatus.INFO, "Click on cancel service button");
		mitccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();

		// Test Step 6: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
		workflowStatusAfterCompletionManually = caworkflowadminpage.processCancellationWorkflow();
		Assert.assertEquals(workflowStatusAfterCompletionManually, "Cancelled","cancellation workflow completed successfully by staff");

		driver.quit();
		System.out.println("End Test: verifyEmailHostingProductCancellation");
	}

	//Purchase a domain manager product
	public void registerDomainAndDomainManagerProduct(String environment) throws InterruptedException, AWTException
	{
		domainandproductorder=new DomainAndProduct_Order();
		domainandproductorder.verifyDomainRegistrationAndDomainManagerAddOnForExistingCustomerUsingExistingCard(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifDomainManagerProductCancellation(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6005";

		registerDomainAndDomainManagerProduct(environment);
		System.out.println("Start Test: verifyDomainManagerProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_melbourneit");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitcaccounttabpage=new MITCAccountTabPage();
		mitcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitccancelservicespage=mitcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain name and select the cancellation reason
		test.log(LogStatus.INFO, "Select the domain name");
		mitccancelservicespage.selectDomainName(DomainAndProduct_Order.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitccancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitccancelservicespage.selectCancellationReasonThird();
		test.log(LogStatus.INFO, "Click on cancel service button");
		mitccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
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
		salesdbproductprovisining=new ProductProvisiningTestMIT();
		salesdbproductprovisining.verify_Domain_and_DomainManagerDomainPrivacy_Order_InSalesDB(environment);
	}

	@Parameters({ "environment"})
	@Test
	public void verifyDomainManagerAndDomainPrivacyProductCancellation(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6005";

		registerDomainAndDomainManagerDomainPrivcayProduct(environment);

		System.out.println("Start Test: verifyDomainManagerAndDomainPrivacyProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_melbourneit");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitcaccounttabpage=new MITCAccountTabPage();
		mitcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitccancelservicespage=mitcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		mitccancelservicespage.selectDomainName(ProductProvisiningTestMIT.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitccancelservicespage.tickSelectService();
		mitccancelservicespage.tickSelectServiceDomainPrivacy();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitccancelservicespage.selectCancellationReasonSecond();
		test.log(LogStatus.INFO, "Click on cancel service button");
		mitccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
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
	
	public void registerDomainAndDomainPrivcyProduct(String environment) throws InterruptedException, AWTException
	{
		salesdbproductprovisining=new ProductProvisiningTestMIT();
		salesdbproductprovisining.verify_Domain_and_DomainPrivacy_Order_InSalesDB(environment);
	}
	

	@Parameters({ "environment"})
	@Test
	public void verifyDomainPrivacyProductCancellation(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6005";
		
		registerDomainAndDomainPrivcyProduct(environment);
		System.out.println("Start Test: verifyDomainPrivacyProductCancellation");
		test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_melbourneit");

		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);

		// Test Step 2: Click on account tab and click on cancel services link
		test.log(LogStatus.INFO, "Click on account tab link");
		mitcaccounttabpage=new MITCAccountTabPage();
		mitcaccounttabpage.clickAccountTabLink();
		test.log(LogStatus.INFO, "Click on cancel services link");
		mitccancelservicespage=mitcaccounttabpage.clickOnCancelServicesLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Select the domain name");
		mitccancelservicespage.selectDomainName(ProductProvisiningTestMIT.registerdomain);
		test.log(LogStatus.INFO, "Tick on select services checkbox ");
		mitccancelservicespage.tickSelectService();
		test.log(LogStatus.INFO, "Select the cancellation reason");
		mitccancelservicespage.selectCancellationReasonFirst();
		test.log(LogStatus.INFO, "Click on cancel service button");
		mitccancelservicespage.clickOnCancelServiceButton();

		//Test Step 4: Get the refrence id
		getWorkflowId();
		driver.quit();

		// Test Step 5: Login to console admin 
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();
		
		// Test Step 6: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
	    workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();	
		driver.quit();
		System.out.println("End Test: verifyDomainPrivacyProductCancellation");
	}
	
	//Purchase a MIXED - NON-solution product + component product
		public void registerDomainWitnNonSolutionAndComponentProduct(String environment) throws InterruptedException, AWTException
		{
			salesdbproductprovisining=new ProductProvisiningTestMIT();
			salesdbproductprovisining.verify_Domain_and_ComponentProduct_InSalesDB(environment);
		}

		@Parameters({ "environment"})
		@Test
		public void verifyNonSolutionProductAndComponentProductCancellation(String environment) throws InterruptedException, AWTException
		{
			// Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6005";

			//Pre-requisite: Domain registration with Non-Solution product and Component product
			registerDomainWitnNonSolutionAndComponentProduct(environment);

			System.out.println("Start Test: verifyNonSolutionProductAndComponentProductCancellation");
			test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_melbourneit");

			// Test Step 1: Login to customer portal
			loginToCustomerPortal(accountReference,strPassword);

			// Test Step 2: Click on account tab and click on cancel services link
			test.log(LogStatus.INFO, "Click on account tab link");
			mitcaccounttabpage=new MITCAccountTabPage();
			mitcaccounttabpage.clickAccountTabLink();
			test.log(LogStatus.INFO, "Click on cancel services link");
			mitccancelservicespage=mitcaccounttabpage.clickOnCancelServicesLink();

			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Select the domain name");
			mitccancelservicespage.selectDomainName(ProductProvisiningTestMIT.registerdomain);
			test.log(LogStatus.INFO, "Tick on select services checkbox ");
			mitccancelservicespage.tickSelectService();
			mitccancelservicespage.tickSelectServiceDomainPrivacy();
			test.log(LogStatus.INFO, "Select the cancellation reason");
			mitccancelservicespage.selectCancellationReasonSecond();
			test.log(LogStatus.INFO, "Click on cancel service button");
			mitccancelservicespage.clickOnCancelServiceButton();

			//Test Step 4: Get the refrence id
			getWorkflowId();
			driver.quit();

			// Test Step 5: Login to console admin 
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
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
			salesdbproductprovisining=new ProductProvisiningTestMIT();
			salesdbproductprovisining.verify_Domain_and_SolutionProduct_InSalesDB(environment);
		}


		@Parameters({ "environment"})
		@Test
		public void verifySolutionProductAndComponentProductCancellation(String environment) throws InterruptedException, AWTException
		{
			// Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6005";

			//Pre-requisite: Domain registration with solution and component product
			registerDomainWithSolutionAndComponentProduct(environment);

			System.out.println("Start Test: verifySolutionProductAndComponentProductCancellation");
			test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_melbourneit");

			// Test Step 1: Login to customer portal
			loginToCustomerPortal(accountReference,strPassword);

			// Test Step 2: Click on account tab and click on cancel services link
			test.log(LogStatus.INFO, "Click on account tab link");
			mitcaccounttabpage=new MITCAccountTabPage();
			mitcaccounttabpage.clickAccountTabLink();
			test.log(LogStatus.INFO, "Click on cancel services link");
			mitccancelservicespage=mitcaccounttabpage.clickOnCancelServicesLink();

			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Select the domain name");
			mitccancelservicespage.selectDomainName(ProductProvisiningTestMIT.registerdomain);
			test.log(LogStatus.INFO, "Tick on select services checkbox ");
			mitccancelservicespage.tickSelectService();
			mitccancelservicespage.tickSelectServiceDomainPrivacy();
			test.log(LogStatus.INFO, "Select the cancellation reason");
			mitccancelservicespage.selectCancellationReasonSecond();
			test.log(LogStatus.INFO, "Click on cancel service button");
			mitccancelservicespage.clickOnCancelServiceButton();

			//Test Step 4: Get the refrence id
			getWorkflowId();
			driver.quit();

			// Test Step 5: Login to console admin 
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.clickOnWorkflowId();

			// Test Step 6: Verify workflow status and perform the execution of workflow
			test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();	
			driver.quit();
			System.out.println("End Test: verifySolutionProductAndComponentProductCancellation");
		}

		//Purchase a domain backorder product
		public void registerDomainWithDomainBackorderProduct(String environment) throws InterruptedException, AWTException
		{
			salesdbproductprovisining=new ProductProvisiningTestMIT();
			salesdbproductprovisining.verify_Domain_and_DomainBack_Order_InSalesDB(environment);
		}


		@Parameters({ "environment"})
		@Test
		public void verifyDomainBackorderProductCancellation(String environment) throws InterruptedException, AWTException
		{
			// Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6005";

			//Pre-requisite: Domain registration with solution and component product
			registerDomainWithDomainBackorderProduct(environment);

			System.out.println("Start Test: verifySolutionProductAndComponentProductCancellation");
			test.log(LogStatus.INFO, "Automatic product cancellation via SMUI " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_melbourneit");

			// Test Step 1: Login to customer portal
			loginToCustomerPortal(accountReference,strPassword);

			// Test Step 2: Click on account tab and click on cancel services link
			test.log(LogStatus.INFO, "Click on account tab link");
			mitcaccounttabpage=new MITCAccountTabPage();
			mitcaccounttabpage.clickAccountTabLink();
			test.log(LogStatus.INFO, "Click on cancel services link");
			mitccancelservicespage=mitcaccounttabpage.clickOnCancelServicesLink();

			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Select the domain name");
			mitccancelservicespage.selectDomainName(ProductProvisiningTestMIT.registerdomain);
			test.log(LogStatus.INFO, "Tick on select services checkbox ");
			mitccancelservicespage.tickSelectService();
			test.log(LogStatus.INFO, "Select the cancellation reason");
			mitccancelservicespage.selectCancellationReasonSecond();
			test.log(LogStatus.INFO, "Click on cancel service button");
			mitccancelservicespage.clickOnCancelServiceButton();

			//Test Step 4: Get the refrence id
			getWorkflowId();
			driver.quit();

			// Test Step 5: Login to console admin 
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
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
		
		
}


