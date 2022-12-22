package com.consoleadmin.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class CAWorkflowAdminPage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//table/tbody/tr/td/table[3]/tbody/tr[3]/td/input[@value='Execute Action']")
	WebElement executeActionButton;

	@FindBy(how = How.XPATH, using = "//th[contains(text(),'payment.preauth.preauth')]")
	WebElement preAuthVariableName;

	@FindBy(how = How.XPATH, using = "//td/table/tbody/tr/td/table[3]/tbody/tr[2]/td[1]/a")
	WebElement clickOnWorkflowId;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Status:')]//following::td[1]")
	WebElement workflowStatus;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Cancel This Workflow')]")
	WebElement clickOnCancelWorkflow;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Workflow Type')]//following::td[1]")  
	WebElement workflowType;
	
	@FindBy(how = How.XPATH, using = "//tbody/tr[3]/td[contains(text(),'ok')]/parent::tr/td[3]/a[text()='Execute']")  
	WebElement okButton;
	
	@FindBy(how = How.XPATH, using = "//th[contains(text(),'TotalForWorkflow')]/parent::tr/td")  
	WebElement productAmount;
	

	// Initializing Page Objects
	public CAWorkflowAdminPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void processDomainRegistrationWF(String strworkflowid) throws InterruptedException {

		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(8000);

		// Click Execute for Check ASIC
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'check asic')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		Thread.sleep(8000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(10000);
	}

	public void processDomainRegistration2Workflow(String strworkflowid, String strtld) throws InterruptedException {

		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(5000);

		if (strtld.equals("com") || strtld.equals("net") || strtld.equals(".com")) {
			this.processCheckASIC();
		} else if (strtld.equals("nz") || strtld.equals(".nz")) {
			this.processCheckASIC();
		} else if (strtld.equals("com.au") || strtld.equals(".com.au")) {
			this.processCheckASIC();
		} else if (strtld.equals("org") || strtld.equals("info")) {
			// this.processCheckASIC();
			this.processDelegateDomain();
		}
		
		Thread.sleep(10000);
		try {

			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("msg")));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}

	}

	public void processCheckASIC() throws InterruptedException {

		// Click Execute for Check ASIC
		try {
			Thread.sleep(5000);
			System.out.println("Searching for check asic button");
			driver.findElement(
					By.xpath("//tbody/tr[3]/td[contains(text(),'check asic')]/parent::tr/td[3]/a[text()='Execute']"))
					.click();
			System.out.println("Check asic button clicked");
			// Thread.sleep(10000);

			// Click Execute Action
			System.out.println("Searching for Execute Action button");

			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//table[3]/tbody/tr[3]/td[@class='cp']/input[@value='Execute Action']")));

			try {

				driver.findElement(By.xpath("//table[3]/tbody/tr[3]/td[@class='cp']/input[@value='Execute Action']"))
						.click();
			} catch (Exception e) {

				System.out.println("Transaction timeout");
			}

			System.out.println("Execute Action button clicked");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Could not find element");
		}
		// Thread.sleep(60000);
	}

	public void processFraudCheck() throws InterruptedException {

		// Click Ok for Fraud Check
		Thread.sleep(5000);
		try {
			System.out.println("Searching for ok button");
			driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'ok') or contains(text(),'not fraud')]/parent::tr/td[3]/a[text()='Execute']"))
					.click();
			System.out.println("Ok button clicked");
			Thread.sleep(10000);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("Element not found");
		}

		// Click Execute Action
		System.out.println("Searching for Execute Action button");

		try {

			driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		} catch (Exception e) {

			System.out.println("Transaction timeout");
		}

		System.out.println("Execute Action button clicked");
		Thread.sleep(10000);
	}
	
	public String getEntityName() throws InterruptedException {

		String count;
		String entitynametext= null;

		// Thread.sleep(3000);
		Thread.sleep(1000);
		List<WebElement> workflowtablerows = driver.findElements(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr"));
		int rowcount = workflowtablerows.size();

		for (int i = 2; i <= rowcount; i++) {

			count = String.valueOf(i);
			Thread.sleep(1000);
			entitynametext = driver
					.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[3]")).getText();
			System.out.println("Workflow Type Text: " + entitynametext);

		}

		return entitynametext;

	}
	
	public String searchWorkflowType(String strworkflowtype) throws InterruptedException
	{
		String count;
		String workflowtypetext = null;

		// Thread.sleep(3000);
		Thread.sleep(1000);
		List<WebElement> workflowtablerows = driver.findElements(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr"));
		int rowcount = workflowtablerows.size();

		for (int i = 2; i <= rowcount; i++) 
		{

			count = String.valueOf(i);
			Thread.sleep(1000);
			workflowtypetext = driver
					.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[2]")).getText();
			System.out.println("Workflow Type Text: " + workflowtypetext);
			if (workflowtypetext.equals(strworkflowtype)) 
			{
				System.out.println("Workflow Type Found");
			}
			
		}
		return workflowtypetext;
	}
	
	public void processProductRenewalWF(String  workflowName) throws InterruptedException
	{
		//click workflowid
		int index = 2;
		List<WebElement> workflowsList = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr/td[2]"));
		for(WebElement workflow : workflowsList )
		{ 
			int workflowId = index++;
			if(workflow.getText().equalsIgnoreCase(workflowName))
			{
				driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr["+workflowId+"]/td[1]/a")).click();
				break;
			}
		}
		// Click Execute for renew
		int indexpos = 3;
		List<WebElement> actionsList = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr/td[2]"));
		for(WebElement action :actionsList)
		{
			int actionid = indexpos++;
			if(action.getText().equalsIgnoreCase("renew"))
			{
				
				System.out.println("Searching for Run Setup");
				driver.findElement( By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr["+actionid+"]/td[3]/a")).click();
				//driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'renew')]/parent::tr/td[3]/a[text()='Execute']"))
				//		.click();
				System.out.println("Run Setup button clicked");
				break;	
			}
		}
		// Click Execute Action
		System.out.println("Searching for Execute Action button");
		try 
		{
			driver.findElement(By.xpath("//table[3]/tbody/tr[3]/td[@class='cp']/input[@value='Execute Action']")).click();
		} catch (Exception e) 
		{
			System.out.println("Transaction timeout");
		}

		System.out.println("Execute Action button clicked");
		Thread.sleep(10000);
		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("msg"))));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}
	}
	public void processDelegateDomain() throws InterruptedException {

		// Click Execute for Delegate Domain
		Thread.sleep(5000);
		driver.findElement(By.xpath(
				"//tbody/tr[4]/td[contains(text(),'I have manually delegated this domain')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		Thread.sleep(10000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(45000);
	}

	public void processSkipDelegation() throws InterruptedException {

		// Click Execute for Delegate Domain
		Thread.sleep(5000);
		driver.findElement(
				By.xpath("//tbody/tr[4]/td[contains(text(),'skip delegation')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		Thread.sleep(10000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(5000);
	}

	public void processMarkAsRegistered(String strworkflowid) throws InterruptedException {

		// Click Execute for Mark as Registered
		Thread.sleep(5000);
		driver.findElement(By.xpath(
				"//a[@href='/admin/execute/workflow/getActionForm?action_id=7014&workflow_id=" + strworkflowid + "']"))
				.click();
		Thread.sleep(10000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(5000);
	}

	public void processProductSetup2DIFM() throws InterruptedException {

		// Click DIFM workflowid
		Thread.sleep(5000);
		driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[1]/a")).click();
		Thread.sleep(5000);

		// Click Execute for Run Setup
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		Thread.sleep(5000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(5000);

	}

	public void processProductSetup2() throws InterruptedException {

		// Click workflowid
		//driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[1]/a")).click();
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/font[3]/a")).click();

		// Click Execute for Run Setup
		System.out.println("Searching for Run Setup");
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		System.out.println("Run Setup button clicked");

		// Click Execute Action
		System.out.println("Searching for Execute Action button");
		try {

			driver.findElement(By.xpath("//table[3]/tbody/tr[3]/td[@class='cp']/input[@value='Execute Action']"))
					.click();
		} catch (Exception e) {

			System.out.println("Transaction timeout");
		}

		System.out.println("Execute Action button clicked");

		Thread.sleep(10000);
		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("msg"))));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}


	}

	public void processProductSetup2ByWFID(String strworkflowid) throws InterruptedException {

		// Click ProductSetup2 by Workflow ID
		Thread.sleep(10000);
		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(10000);

		// Click Execute for Run Setup
		System.out.println("Searching for Run Setup");
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		System.out.println("Run Setup button clicked");
		Thread.sleep(10000);

		// Click Execute Action
		System.out.println("Searching for Execute Action button");
		try {

			driver.findElement(By.xpath("//table[3]/tbody/tr[3]/td[@class='cp']/input[@value='Execute Action']"))
					.click();
		} catch (Exception e) {

			System.out.println("Transaction timeout");
		}

		System.out.println("Execute Action button clicked");

		// To add a waiting time for workflow to complete processing
		Thread.sleep(120000);

	}

	public void processApprove() throws InterruptedException {

		// Click Execute for Approve
		Thread.sleep(5000);
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'approve')]/parent::tr/td[3]/a[text()='Execute']")).click();
		Thread.sleep(10000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(45000);

		System.out.println("Execute Action button clicked");
	}

	public String processProductSetup2O365() throws InterruptedException {

		// Click Office365 workflowid
		Thread.sleep(10000);
		//driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[1]/a")).click();
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/font[3]/a")).click();
		Thread.sleep(10000);

		// Click Execute for Run Setup
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		Thread.sleep(10000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(250000);

		String invoiceAmount = this.getProductAmount();
		return invoiceAmount;
	}
	
	public void processProductSetupWF() throws InterruptedException {

		// Click Office365 workflowid
		Thread.sleep(10000);
		//driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[1]/a")).click();
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/font[3]/a")).click();
		Thread.sleep(12000);

		// Click Execute for Run Setup
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		Thread.sleep(13000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(35000);
	}

	public void processProductSetup2DIFMUpgrade(String strworkflowid) throws InterruptedException {

		// Click DIFM Upgrade workflowid
		Thread.sleep(10000);
		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(10000);

		// Click Execute for Run Setup
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		Thread.sleep(10000);

		// Click Execute Action
		driver.findElement(By.xpath("//tbody/tr[3]/td/input[@value='Execute Action']")).click();
		Thread.sleep(10000);

	}

	public String getProductSetup2WorkflowID() throws InterruptedException {

		String workflowid;

		// Get workflow id for productsetup2 after processing domainregistration2
		// workflow
		workflowid = driver.findElement(By.xpath("//tbody/tr/td/font[@class='cp'][3]/a[@class='cp']")).getText();
		Thread.sleep(3000);

		return workflowid;
	}

	public boolean verifyWorflowStatus(String strworkflowtype, String strworkflowstep) throws InterruptedException {

		String count;
		String workflowtypetext;
		String workflowsteptext;
		Boolean workflowstepcheck = false;

		Thread.sleep(3000);
		List<WebElement> workflowtablerows = driver.findElements(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr"));
		int rowcount = workflowtablerows.size();

		for (int i = 2; i <= rowcount; i++) {

			count = String.valueOf(i);
			Thread.sleep(1000);
			workflowtypetext = driver
					.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[2]")).getText();
			System.out.println("Workflow Type Text: " + workflowtypetext);

			if (workflowtypetext.equals(strworkflowtype)) {
				System.out.println("Workflow Type Found");

				workflowsteptext = driver
						.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[5]")).getText();
				System.out.println("Workflow Step Text: " + workflowsteptext);

				if (workflowsteptext.equals(strworkflowstep)) {
					workflowstepcheck = true;
				}

			}
		}

		return workflowstepcheck;

	}

	public String getWorkflowStatus(String strworkflowtype) throws InterruptedException {

		String count;
		String workflowtypetext;
		String workflowsteptext = strworkflowtype + " status: ";

		 Thread.sleep(3000);
		//Thread.sleep(1000);
		List<WebElement> workflowtablerows = driver.findElements(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr"));
		int rowcount = workflowtablerows.size();

		for (int i = 2; i <= rowcount; i++) {

			count = String.valueOf(i);
			Thread.sleep(1000);
			workflowtypetext = driver
					.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[2]")).getText();
			System.out.println("Workflow Type Text: " + workflowtypetext);

			if (workflowtypetext.equals(strworkflowtype)) {
				System.out.println("Workflow Type Found");

				workflowsteptext = driver
						.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[5]")).getText();
				System.out.println("Workflow Step Text: " + workflowsteptext);

			}
		}

		return workflowsteptext;

	}

	public String getWorkflowEntity(String strworkflowid) throws InterruptedException {

		String workflowentity;

		Thread.sleep(3000);
		workflowentity = driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[2]/td[3]/a")).getText();
		
		System.out.println("Workflow Entity Found");

		return workflowentity;

	}

	public boolean verifyWorflowStatusViaID(String strworkflowid, String strworkflowstep) throws InterruptedException {

		String count;
		String workflowidnumber;
		String workflowsteptext;
		Boolean workflowstepcheck = false;

		Thread.sleep(3000);
		List<WebElement> workflowtablerows = driver.findElements(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr"));
		int rowcount = workflowtablerows.size();

		for (int i = 2; i <= rowcount; i++) {

			count = String.valueOf(i);
			Thread.sleep(1000);
			workflowidnumber = driver
					.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[1]")).getText();
			System.out.println("Workflow ID : " + workflowidnumber);

			if (workflowidnumber.equals(strworkflowid)) {
				System.out.println("Workflow ID Found");

				workflowsteptext = driver
						.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[" + count + "]/td[5]")).getText();
				if (workflowsteptext.equals(strworkflowstep)) {
					workflowstepcheck = true;
				}

				System.out.println("Workflow Step Text: " + workflowsteptext);
			}
		}

		return workflowstepcheck;

	}

	public Boolean isWorkflowIDExist(String strworkflowid) throws InterruptedException {

		Boolean flag = false;
		Thread.sleep(2000);

		if (driver.findElement(By.linkText(strworkflowid)).isDisplayed()) {
			System.out.println("Workflow ID Found");
			flag = true;
		}

		return flag;

	}

	public String getPreAuthNumber(String strworkflowid) throws InterruptedException {

		String preauthnumber = null;

		Thread.sleep(2000);
//        	driver.findElement(By.linkText(strworkflowid)).click();
//        	Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", preAuthVariableName);
		Thread.sleep(5000);
		if (preAuthVariableName.isDisplayed()) {

			preauthnumber = driver
					.findElement(By.xpath("//th[contains(text(),'payment.preauth.preauth')]/parent::tr/td")).getText();
			System.out.println("Preauth Number: " + preauthnumber);

		}

		return preauthnumber;

	}

//    	public String getWorkflowParameterValues(String workflowparamname) throws InterruptedException {
//            
//    		String preauthnumber = null;
//    		
//        	Thread.sleep(2000);
//        	driver.findElement(By.linkText(strworkflowid)).click();
//        	Thread.sleep(1000);
//        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", preAuthVariableName);
//        	Thread.sleep(5000);
//        	if (preAuthVariableName.isDisplayed()) {
//        	
//        		preauthnumber = driver.findElement(By.xpath("//th[contains(text(),'payment.preauth.preauth')]/parent::tr/td")).getText();
//        		System.out.println("Preauth Number: "+ preauthnumber);
//      
//        	}
//
//        	return preauthnumber;
//
//        }

	public String getDomainRegistration2WorkflowParameterValue(String workflowparametername)
			throws InterruptedException {

		String workflowparametervalue;

		Thread.sleep(3000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath("//th[contains(text(),'" + workflowparametername + "')]")));

		workflowparametervalue = driver
				.findElement(By.xpath("//th[contains(text(), '" + workflowparametername + "')]/parent::tr/td"))
				.getText();
		System.out.println(workflowparametername + ": " + workflowparametervalue);

		return workflowparametervalue;
	}

	public void refreshWorkflowAdminPage() throws InterruptedException {

		int maximumNumberOfRetry = 3;
		// Thread.sleep(2000);

		for (int i = 1; i <= maximumNumberOfRetry; i++) {

			// Added refresh action to retrieve the latest workflow status
			driver.navigate().refresh();
			// Thread.sleep(10000);
			Thread.sleep(5000);

		}
	}

	public void clickOnWorkflowId() throws InterruptedException {
		clickOnWorkflowId.click();
		Thread.sleep(2000);
	}

	public String getWorkflowStatus() throws InterruptedException {
		// Check status of workflow from parameters
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(workflowStatus));
		return workflowStatus.getText();
	}

	public void cancelWorkflow() {
		clickOnCancelWorkflow.click();
	}

	public String verifyWorkflowType() {
		return workflowType.getText();
	}
	
	public String processRenewal2Workflow(){
		//Click Start Auto Renewal action
		driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[3]/a")).click();
		driver.findElement(By.xpath("//*[contains(@value,'Execute Action')]")).click();
		WebDriverWait wait = new WebDriverWait(driver,300);
		wait.until(ExpectedConditions.visibilityOf(workflowStatus));
		return workflowStatus.getText();
	}
	
	public String getInvoiceNumber() {
		String invoiceNumber = driver.findElement(By.xpath("//th[contains(text(),'invoiceId')]/parent::tr/td")).getText();
		return invoiceNumber.trim();
		
	}
	
	public String getInvoiceAmount() {
		String invoiceNumber = driver.findElement(By.xpath("//th[contains(text(),'PreAuthAmount')]/parent::tr/td")).getText();
		if(invoiceNumber.contains("none"))
		{
			invoiceNumber = driver.findElement(By.xpath("//th[contains(text(),'TotalForWorkflow')]/parent::tr/td")).getText();
		}
		return invoiceNumber.trim();
		
	}
	
	public String getUnpaidInvoiceAmount() {
		String invoiceNumber = driver.findElement(By.xpath("//th[contains(text(),'TotalForWorkflow')]/parent::tr/td")).getText();
		return invoiceNumber.trim();
		
	}
	
	public String[] processWF(String strworkflowid, String strtld) throws InterruptedException {

		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(5000);

		if (strtld.equals("com") || strtld.equals("net") || strtld.equals(".com") || strtld.equals(".net"))  {
			this.processCheckASIC();
			this.processFraudCheck();
		} else if (strtld.equals("nz")) {
			this.processCheckASIC();
		} else if (strtld.equals("com.au") || strtld.equals(".com.au")) {
			this.processCheckASIC();
			this.processFraudCheck();
		} else if (strtld.equals("org") || strtld.equals("info")) {
			// this.processCheckASIC();
			this.processDelegateDomain();
		}
		
		Thread.sleep(10000);
		try {

			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("msg")));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}
		 String ar[] = new String[2];
		 ar[0] = this.getInvoiceNumber();
		 ar[1] =this.getInvoiceAmount();
		return ar;
		
	}
	
	public void processWorkFlow(String strworkflowid, String strtld) throws InterruptedException {

		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(5000);

		if (strtld.equals("com") || strtld.equals("net") || strtld.equals(".com") || strtld.equals(".net") || strtld.equals("biz") || strtld.equals("us") )  {
			this.processCheckASIC();
			this.processFraudCheck();
		} else if (strtld.equals("nz")) {
			this.processCheckASIC();
		} else if (strtld.equals("com.au") || strtld.equals(".com.au")) {
			this.processCheckASIC();
			this.processFraudCheck();
		} else if (strtld.equals("org") || strtld.equals("info")) {
			this.processCheckASIC();
			this.processFraudCheck();
			//this.processDelegateDomain();
		}
		
		Thread.sleep(10000);
		try {

			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("msg")));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}
		
	}
	
	public void processicannTransfer2WorkFlow(String strworkflowid) throws InterruptedException 
	{

		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(5000);
		// Click Execute for requestConfirmation
		int indexpos = 3;
		List<WebElement> actionsList = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr/td[2]"));
		for(WebElement action :actionsList)
		{
			int actionid = indexpos++;
			if(action.getText().equalsIgnoreCase("requestConfirmation"))
			{
				
				System.out.println("Searching for Run Setup");
				driver.findElement( By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr["+actionid+"]/td[3]/a")).click();
				//driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'renew')]/parent::tr/td[3]/a[text()='Execute']"))
				//		.click();
				System.out.println("Run Setup button clicked");
				break;	
			}
		}
		
		//Enter admin Email and clcik execute
		Thread.sleep(5000);
		WebElement adminEmail = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[3]/td/input"));
		adminEmail.sendKeys("testaccount@gmail.com");
		Thread.sleep(5000);
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[4]/td/input")).click();
		
		
		// Click Execute for confirm action
		int indexposition = 3;
		List<WebElement> transferactionsList = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr/td[2]"));
		for(WebElement action :transferactionsList)
		{
			int transferactionid = indexposition++;
			if(action.getText().equalsIgnoreCase("confirm"))
			{
				
				System.out.println("Searching for Run Setup");
				driver.findElement( By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr["+transferactionid+"]/td[3]/a")).click();
				System.out.println("Run Setup button clicked");
				break;	
			}
		}
		//click execute
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[3]/td/input")).click();
		Thread.sleep(10000);
		
		try {

			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("msg")));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}
		
	}
	
	public void processtransferral2WorkFlow(String strworkflowid) throws InterruptedException 
	{
		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(5000);
		// Click Execute for confirm transfer action
		int indexpos = 3;
		List<WebElement> actionsList = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr/td[2]"));
		for(WebElement action :actionsList)
		{
			int actionid = indexpos++;
			if(action.getText().equalsIgnoreCase("confirm transfer"))
			{
				
				System.out.println("Searching for Run Setup");
				driver.findElement( By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr["+actionid+"]/td[3]/a")).click();
				//driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'renew')]/parent::tr/td[3]/a[text()='Execute']"))
				//		.click();
				System.out.println("Run Setup button clicked");
				break;	
			}
		}
		
		// click execute
		Thread.sleep(5000);
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[3]/td/input")).click();
		
		
		// Click Execute for complete transfer action
		int indexposition = 3;
		List<WebElement> transferactionsList = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr/td[2]"));
		for(WebElement action :transferactionsList)
		{
			int transferactionid = indexposition++;
			if(action.getText().equalsIgnoreCase("complete transfer"))
			{
				
				System.out.println("Searching for Run Setup");
				driver.findElement( By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr["+transferactionid+"]/td[3]/a")).click();
				System.out.println("Run Setup button clicked");
				break;	
			}
		}
		//click execute
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[3]/td/input")).click();
		Thread.sleep(10000);
		
		try {

			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("msg")));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}
		
	}
	
	public void processDomainBackorderWF(String strworkflowid) throws InterruptedException 
	{
		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(5000);
		// Click Execute for confirm transfer action
		int indexpos = 3;
		List<WebElement> actionsList = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr/td[2]"));
		for(WebElement action :actionsList)
		{
			int actionid = indexpos++;
			if(action.getText().equalsIgnoreCase("start backorder"))
			{
				
				System.out.println("Searching for backorder domain");
				Thread.sleep(5000);
				driver.findElement( By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr["+actionid+"]/td[3]/a")).click();
				System.out.println("Run Setup button clicked");
				break;	
			}
		}
		
		// click execute
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[3]/td/input")).click();
		Thread.sleep(5000);
	}
	
	
	
	public String[] clickOnWorkflowIdInProduction() throws InterruptedException {
		clickOnWorkflowId.click();
		//checkStatusInProduction();
		Thread.sleep(2000);
		String ar[] = new String[2];
		 ar[0] = this.getInvoiceNumber();
		 ar[1] = this.getInvoiceAmount();
		return ar;
	}

	public String getProductAmount() throws InterruptedException {
		Thread.sleep(2000);
		String invoiceNumber = null;
		if(productAmount.isDisplayed() || productAmount.isEnabled()) {
		invoiceNumber = productAmount.getText();
		}
		else {
			System.out.println("Element not found");
		}
		return invoiceNumber.trim();		
	}
	
	public void processMultipleWF(String strworkflowid) throws InterruptedException
	{
		driver.findElement(By.linkText(strworkflowid)).click();
		Thread.sleep(5000);
		this.processCheckASIC();
		this.processFraudCheck();
		Thread.sleep(10000);
		try {

			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.id("msg")));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}
	}
	
	public void processTransferl2StatusWorkflow(String strworkflowid) throws InterruptedException
	{
		driver.findElement(By.linkText(strworkflowid)).click();
		//driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[2]/td[1]/a")).click();
		Thread.sleep(5000);
		if(workflowStatus.getText().equalsIgnoreCase("domain resolution failed"))
			{
		       // click on confirm transfer
			    driver.findElement(By.xpath("//tbody/tr[3]/td[contains(text(),'retry resolve domain')]/parent::tr/td[3]/a[text()='Execute']")).click();
			    System.out.println("Retry resolve domain action is clicked");
				Thread.sleep(10000);
				// Click Execute Action
				System.out.println("Searching for Execute Action button");
			}
		    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[3]/tbody/tr[3]/td[@class='cp']/input[@value='Execute Action']")));
			try {

				driver.findElement(By.xpath("//table[3]/tbody/tr[3]/td[@class='cp']/input[@value='Execute Action']")).click();
				Thread.sleep(15000);
			   } 
			catch (Exception e)
			{

				System.out.println("Transaction timeout");
			}

			System.out.println("Execute Action button clicked");
		}
	
	public String getProductSetup2O365AmountProduction() throws InterruptedException 
	{

		// Click Office365 workflowid
		Thread.sleep(10000);
		//driver.findElement(By.xpath("//table/tbody/tr/td/table[3]/tbody/tr[3]/td[1]/a")).click();
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/font[3]/a")).click();
		Thread.sleep(10000);

		String invoiceAmount = this.getProductAmount();
		return invoiceAmount;
	}
	
	public void processProductSetup2Workflow(String strworkflowid) throws InterruptedException {

		// Click workflowid
		driver.findElement(By.linkText(strworkflowid)).click();
		// Click Execute for Run Setup
		System.out.println("Searching for Run Setup");
		driver.findElement(
				By.xpath("//tbody/tr[3]/td[contains(text(),'run setup')]/parent::tr/td[3]/a[text()='Execute']"))
				.click();
		System.out.println("Run Setup button clicked");

		// Click Execute Action
		System.out.println("Searching for Execute Action button");
		try {

			driver.findElement(By.xpath("//table[3]/tbody/tr[3]/td[@class='cp']/input[@value='Execute Action']"))
					.click();
		} catch (Exception e) {

			System.out.println("Transaction timeout");
		}

		System.out.println("Execute Action button clicked");
		Thread.sleep(10000);
		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("msg"))));
		} catch (Exception e) {
			System.out.println("Processing the workflow has timed out. Search the workflow instead");
			e.printStackTrace();
		}
	}
	
	public void checkStatusInProduction() throws InterruptedException
	{
		String stepStatus = driver.findElement(By.xpath("//th[contains(text(),'Step')]/parent::tr/td")).getText();
		while(stepStatus.equalsIgnoreCase("domain registration completed"))
		{
			driver.navigate().refresh();
			Thread.sleep(8000);
		}
	}
	
	public String processCancellationWorkflow() throws InterruptedException{
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[3]/td[3]/a")).click();
		driver.findElement(By.xpath("//*[contains(@value,'Execute Action')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table[3]/tbody/tr[3]/td[3]/a")).click();
		driver.findElement(By.xpath("//input[@name='Comments']")).sendKeys("test");
		driver.findElement(By.xpath("//*[contains(@value,'Execute Action')]")).click();
		Thread.sleep(10000);
		WebDriverWait wait = new WebDriverWait(driver,300);
		wait.until(ExpectedConditions.visibilityOf(workflowStatus));
		return workflowStatus.getText();
	}
}
