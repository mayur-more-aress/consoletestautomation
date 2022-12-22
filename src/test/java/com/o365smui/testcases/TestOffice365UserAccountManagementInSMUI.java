package com.o365smui.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesmui.pages.CSMUIManageO365MainPage;
import com.consolesmui.pages.CSMUITabPage;
import com.consolesmui.pages.CSMUIUpdateAdminAccountPage;
import com.consolesmui.pages.CSMUIUpdateUserAccountPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class TestOffice365UserAccountManagementInSMUI extends TestBase {

	// Console Admin Pages

	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CADomainLevelPage cadomainlevelpage;
	CSMUITabPage csmuitabpage;
	CSMUIManageO365MainPage csmuimanageo365mainpage;
	CSMUIUpdateUserAccountPage csmuiupdateuseraccountpage;
	CSMUIUpdateAdminAccountPage csmuiupdateadminaccountpage;

	TestUtil testUtil;
	public static ExtentTest logger;

	public TestOffice365UserAccountManagementInSMUI() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void testOffice365UserAccountManagementInSMUI(String environment) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = "testoffice365smui.com";
		String username = "ggg";
		String firstName = "test";
		String lastName = "zzz";
		String displayName = "test";
		String password = "passW0rd";
		String confirmPassword = "passW0rd";
		String strLicenceType = "Business Essentials - Email and Office Online";
		String strUserDetails = username + "@" + strDomainName;

		String eUsername = "ggg1";
		String eFirstName = "test1";
		String eLastName = "zzz1";
		String eDisplayName = "test1";
		String newPassword = "passW0rd1";
		String repeatPassword = "passW0rd1";
		String strNewLicenceType = "Email Essentials - Email Only";
		
		String assignedLicence = null;
		String actualUserLinkText = null;

		// Test Step 1: Login to console and search for domain
		System.out.println("Start Test: TestOffice365UserAccountManagementInSMUI");
		initialization(environment, "consoleadmin");

		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		cadomainlevelpage = caheaderpage.searchDomain(strDomainName);
		csmuitabpage = cadomainlevelpage.clickloginAsClientLink();

		// Test Step 2: In Office365 SMUI, create a new user account and assign an
		// office365 license.
		csmuimanageo365mainpage = csmuitabpage.clickOffice365Tab();
		csmuimanageo365mainpage.clickCreateUser();
		csmuimanageo365mainpage.setUserAccountDetails(username, firstName, lastName, displayName, password,
				confirmPassword);
		csmuimanageo365mainpage.setSelectLicence(strLicenceType);
		csmuimanageo365mainpage.clickCreateEmail();
		Assert.assertTrue(csmuimanageo365mainpage.isAccountCreated());

		/*
		 * Test Step 3: Verify if user account is added in the table with the correct
		 * license and user link. Test Step 3.1: Verify if user account is added in the
		 * table with the correct license.
		 */
		assignedLicence = csmuimanageo365mainpage.getAssignLicence(strUserDetails);
		Assert.assertEquals(assignedLicence, strLicenceType,
				"Verify that assigned licence is equal to the one selected during user ceation.");

		// Test Step 3.2: Verify if user account is added in the table with the correct
		// user link.
		actualUserLinkText = csmuimanageo365mainpage.getUserLinkText(strUserDetails);
		Assert.assertEquals(actualUserLinkText, "Access Email Online",
				"Verify that assigned licence is equal to the one selected during user ceation.");
		
		// Test Step 5: Verify if user will be directed to microsoft login page when the
		// "user link" is clicked.
		Assert.assertTrue(csmuimanageo365mainpage.verifyUserLink(strUserDetails));

		// Test Step 4: Verify if user can edit details and reset a new password.
		// Test Step 4.1: Verify if user details updated successfully
		csmuiupdateuseraccountpage = csmuimanageo365mainpage.clickUserUpdateLink(strUserDetails);
		csmuiupdateuseraccountpage.editUserAccountDetails(eUsername, eFirstName, eLastName, eDisplayName);
		csmuiupdateuseraccountpage.clickSave();
		Assert.assertTrue(csmuiupdateuseraccountpage.isAccountUpdated());

		// Test Step 4.2: Verify if user password details updated successfully
		csmuiupdateuseraccountpage.editUserPwdDetails(newPassword, repeatPassword);
		csmuiupdateuseraccountpage.clickReset();
		Assert.assertTrue(csmuiupdateuseraccountpage.isPwdUpdated());

		// Test Step 6: Verify if user Licence details updated successfully
		csmuiupdateuseraccountpage.unAssignLicence(strLicenceType);
		csmuiupdateuseraccountpage.clickLicenceSave();
		Assert.assertTrue(csmuiupdateuseraccountpage.isLicenceUpdated());

		// Test Step 7: Verify if username for admin portal is o365-admin@[domainname]
		csmuimanageo365mainpage = csmuiupdateuseraccountpage.clickGoBackButton();
		Assert.assertTrue(csmuimanageo365mainpage.verifyAdminEmail(strDomainName));

		// Test Step 8: Verify if user admin can edit details and reset a new password.
		// Edit details
		csmuiupdateadminaccountpage = csmuimanageo365mainpage.clickAdminUpdateLink();
		csmuiupdateadminaccountpage.updateAdminDetails("newFirstName", "newFirstName", "New Admin Name");
		Assert.assertEquals(csmuiupdateadminaccountpage.getEditDetailsSuccessMessage(),
				"Your changes have been saved successfully.", "Verify successful edit details message.");

		// Reset password
		csmuiupdateadminaccountpage.resetPassword("P@ssw0rd123", "P@ssw0rd123");
		Assert.assertEquals(csmuiupdateadminaccountpage.getResetPasswordSuccessMessage(),
				"Password successfully changed.", "Verify successful password changed message.");

	}
}