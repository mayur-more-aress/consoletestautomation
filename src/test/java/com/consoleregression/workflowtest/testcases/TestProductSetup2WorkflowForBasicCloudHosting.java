package com.consoleregression.workflowtest.testcases;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGAddExtrasPage;
import com.netregistryoldwebsite.pages.NRGAddHostingPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.netregistryoldwebsite.pages.NRGOnlineOrderAccountDetailsPage; 
import com.util.TestUtil;


public class TestProductSetup2WorkflowForBasicCloudHosting extends TestBase{

	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	NRGOnlineOrderAccountDetailsPage nrgonlineorderaccountdetailspage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	public static ExtentTest logger;

	public TestProductSetup2WorkflowForBasicCloudHosting() {
		super();
	}

	
	@Parameters({"environment", "virtualization"}) 
	@Test
	public void testProductSetup2WorkflowForBasicCloudHosting(String environment, String virtualization) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strAccountReference = null;
		String strPassword = null;
		String strWorkflowId = null;
		String strPeriod = null;
		
		strDomainName = "testconsoleregression18062019040129";
		
		if ((environment.equals("uat1"))&&(virtualization.equals("netregistry"))) {
			strAccountReference = "TES-2168";
			strPassword = "comein22";
			strPeriod = "4 Years";
		}
			
		//Test Step 1: Navigate to online order page and input an existing domain
		System.out.println("Start Test: testDomainRegistration2WorkflowForCom");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.setExistingDomainName(strDomainName);
		nrgonlineorderaccountdetailspage = nrgonlineorderpage.setExistingDomainSearchButton();
		
		//Test Step 2: Provide account reference and password details to login
		nrgonlineorderaccountdetailspage.setLoginDetails(strAccountReference, strPassword);
		nrghostingandextraspage = nrgonlineorderaccountdetailspage.clickLoginButton();
	
		//Test Step 3: Adding Basic cloud hosting product  
		nrghostingandextraspage.selectBasicCloudHosting(strPeriod);
		nrgbillingpage = nrghostingandextraspage.clickContinueButtonToBillingPage();

		//Test Step 4: Select the terms and conditions and continue to order
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
		
		//Test Step 5: Verify if order is complete
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		driver.close();
		
		//Test Step 6: Process productsetup2 workflow in console admin
		initialization(environment, "consoleadmin");		
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId);
		caworkflowadminpage.processApprove();
		
		//Test Step 7: Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));
		
		//driver.close();
	}
}
