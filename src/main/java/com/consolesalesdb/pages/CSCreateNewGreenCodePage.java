package com.consolesalesdb.pages;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;


public class CSCreateNewGreenCodePage extends TestBase{
	
	//Objects
    @FindBy(how=How.ID, using = "create-greencode-greencodeName-field-1")
    WebElement organizationName;
    
    @FindBy(how=How.ID, using = "create-greencode-addr1-field-1")
    WebElement address;
    
    @FindBy(how=How.ID, using = "create-greencode-cntry-field-1")
    WebElement country;
    
    @FindBy(how=How.ID, using = "create-greencode-city-field-1")
    WebElement city;
    
    @FindBy(how=How.ID, using = "create-greencode-county-field-1")
    WebElement state;
  
    @FindBy(how=How.ID, using = "create-greencode-pcode-field-1")
    WebElement pincode;
    
    @FindBy(how=How.ID, using = "create-greencode-tel-field-1")
    WebElement telephone;
   
    @FindBy(how=How.ID, using = "create-contact-salutation-field-1")
    WebElement salutation;
    
    @FindBy(how=How.ID, using = "create-contact-title-field-1")
    WebElement title;
    
    @FindBy(how=How.ID, using = "create-contact-firstName-field-1")
    WebElement firstName;
    
    
    @FindBy(how=How.ID, using = "create-contact-lastName-field-1")
    WebElement lastName;
    
    @FindBy(how=How.ID, using = "create-contact-email-field-1")
    WebElement email;
    
    @FindBy(how=How.XPATH, using = "//*[@id=\"ext-gen2211\"]")
    WebElement secondaryEmail;
    
    @FindBy(how=How.ID, using = "create-greencode-coas-field-1")
    WebElement source;
    
    @FindBy(how=How.ID, using = "create-greencode-industries-field-1")
    WebElement industry;
    
    @FindBy(how=How.ID, using = "create-greencode-virtualisation-field-1")
    WebElement virtualization;
    
    @FindBy(how=How.XPATH, using = "//*[text()='Create']")
    WebElement createGreencode;
//    
//    @FindBy(how=How.ID, using = "ext-gen2853")
//    WebElement getGreencode;
    
    @FindBy(how=How.XPATH, using = "//*[text()='Yes']")
    WebElement clickYesOnPopUp;
        
    @FindBy(how=How.XPATH, using = "//*[@id=\"coId\"]")
    WebElement getGreencode;
    
    @FindBy(how=How.LINK_TEXT, using = "Create Reseller")
    WebElement createReseller;
    
    @FindBy(how=How.NAME, using = "resellerData")
    WebElement enterResellerData;
    
    //Initializing Page Objects
    public CSCreateNewGreenCodePage(){
        PageFactory.initElements(driver, this);
    }
    
    //Methods
    public void setGreencodeDetails(String OrgName, String Address, String Country, String City, String State, String PinCode, String Telephone, String Source, String Industry, String Virtualisation) throws InterruptedException {
    	organizationName.sendKeys(OrgName);
    	Thread.sleep(1000);
        address.sendKeys(Address);
        Thread.sleep(1000);
        country.sendKeys(Country);
        country.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        city.sendKeys(City);
        Thread.sleep(1000);
        state.sendKeys(State);
        state.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        pincode.sendKeys(PinCode);
        Thread.sleep(1000);
        telephone.sendKeys(Telephone);
        Thread.sleep(1000);
        source.sendKeys(Keys.DOWN);
        source.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        industry.sendKeys(Industry);
        industry.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        virtualization.click();
        virtualization.sendKeys(Virtualisation);
        Thread.sleep(1000);
        virtualization.sendKeys(Keys.ENTER);
        
    }
    
    public void setDefaultContactDetails(String Salutation, String Title, String FirstName, String LastName, String Email, String SecondaryEmail) {
    	     salutation.sendKeys(Salutation);
    	     title.sendKeys(Title);
    	     firstName.sendKeys(FirstName);  
    	     lastName.sendKeys(LastName);
    	     email.sendKeys(Email);
    	 //    secondaryEmail.sendKeys(SecondaryEmail);
    }
    
    public void createGreencodeButton() throws InterruptedException {
    	Thread.sleep(3000);
    	createGreencode.click();
    }
 
    public void clickSecondCreateButton() throws InterruptedException {
    	Thread.sleep(12000);
    	try {
			createGreencode.click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		System.out.println("Could not find create button");
		}
    }
    
    public String getGreencode() throws InterruptedException {
    	Thread.sleep(5000);
    	clickYesOnPopUp.click();
    	Thread.sleep(3000);
    	String greenCode = getGreencode.getText();
    	System.out.println(greenCode);
    	return greenCode;
    }
    
    public void clickCreateReseller() throws InterruptedException {
    	Thread.sleep(2000);
    	createReseller.click();
    }
    
    public void enterResellerData(String strResellerData) throws InterruptedException {
    	Thread.sleep(2000);
    	enterResellerData.sendKeys(strResellerData);
    }
}