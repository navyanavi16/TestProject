package term_conversion;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class InsuredInformationPage {
	private DriverUtil driverUtil;
	
	//owner section
	private String divInsuredPersonOneFormXPath = "//div[@id = 'insured-person-form-insuredPerson']";
	private String divInsuredPersonTwoFormXPath = "//div[@id = 'insured-person-form2-insuredPerson']";
	
	
	//owner insured Yes or No Button, 0 for first owner and 1 for second owner.
//	private String labelOwnerInsured1YesXPath = "//input[@name='insured-person-form-insured-an-owner']/following-sibling::label[text()='Yes']";
	
	private String labelOwnerInsuredYesXPath = "//button[contains(@id,'insured-an-owner') and (text()='%s')]";
	private String labelOwnerInsuredNoXPath = "//button[contains(@id,'insured-an-owner') and (text()='%s')]";
	
	
	
//	private String labelOwnerInsured1NoXPath = "//input[@name='insured-person-form-insured-an-owner']/following-sibling::label[text()='No']";
//	private String labelOwnerInsured2YesXPath = "//label[@for = 'insured-person-form2-ownerAnInsuredButtonSelector-1']";
//	private String labelOwnerInsured2NoXPath = "//label[@for = 'insured-person-form2-ownerAnInsuredButtonSelector-2']";
//	private String inputOwnerInsured1YesXPath = "//input[@id = 'insured-person-form-ownerAnInsuredButtonSelector-1']";
//	private String inputOwnerInsured1NoXPath = "//input[@id = 'insured-person-form-ownerAnInsuredButtonSelector-2']";
//	private String inputOwnerInsured2YesXPath = "//input[@id = 'insured-person-form2-ownerAnInsuredButtonSelector-1']";
//	private String inputOwnerInsured2NoXPath = "//input[@id = 'insured-person-form2-ownerAnInsuredButtonSelector-2']";
//	
	private String inputInsuredFirstNameXPath="//input[contains(@id,'insured-name-firstName')]";
	private String inputInsuredLastNameXPath = "//input[contains(@id,'insured-name-lastName')]";
	private String inputInsuredMiddleInitialXPath = "//input[contains(@id,'insured-name-middleName')]"; 
	
	
	//sex of insured for first owner 
	private String labelInsuredPerson1MaleXPath = "//button[contains(@id,'insured-sex') and (text()='Male')]";	
	private String labelInsuredPerson1FemaleXPath = "//button[contains(@id,'insured-sex') and (text()='Female')]";
	
	
	
	
//	private String inputInsuredPerson1MaleXPath = "//input[@id = 'insured-person-form-insuredPersonSex-1']";
//	private String inputInsuredPerson1FemaleXPath = "//input[@id = 'insured-person-form-insuredPersonSex-2']";
//	private String inputInsuredPerson2MaleXPath = "//input[@id = 'insured-person-form2-insuredPersonSex-1']";
//	private String inputInsuredPerson2FemaleXPath = "//input[@id = 'insured-person-form2-insuredPersonSex-2']";
//	//date of birth insured
	
	private String inputBirthDayXPath = "//input[@id = 'insured-person-form-insured-dob-day']";	
	private String divBirthMonthXPath = "//div[@id = 'insured-person-form-insured-dob-month-value']";
	private String ulBirthMonthXPath ="//ul[@id = 'insured-person-form-insured-dob-month-listbox']";
	private String inputBirthYearXPath = "//input[@id = 'insured-person-form-insured-dob-year']";
	
	// Second owner DOB information
	private String inputBirthDay2XPath = "//input[@id = 'insured-person-form2-insured-dob-day']";
	private String divBirthMonth2XPath = "//div[@id = 'insured-person-form2-insured-dob-month-value']";
	private String ulBirthMonth2XPath ="//ul[@id = 'insured-person-form2-insured-dob-month-listbox']";
	private String inputBirthYear2XPath = "//input[@id = 'insured-person-form2-insured-dob-year']";
	
	
	
	//error handling
	private String divIsOwnerInsuredErrorFieldXPath = "//div[@id = 'insured-person-form-insured-an-owner--error'][@aria-live = 'off']";
	private String divInsured1FirstNameErrorFieldXPath = "//div[@id = 'insured-person-form-insured-name-firstName-errors']";
	private String divInsured1LastNameErrorFieldXPath = "//div[@id = 'insured-person-form-insured-name-lastName-errors']";
	private String divInsuredPersonSexErrorFieldXPath = "//div[@id = 'insured-person-form-insured-sex--error'][@aria-live = 'off']";
	private String divDateOfBirthErrorFieldXPath = "//div[@id = 'dob']/..//div[@class = 'row error']/div/span";
	//next
	private String buttonNextButtonXPath = "//button[@id = 'next-btn']";
	//EN FR toggle XPath
	private String buttonENFRXPath = "//button[contains(text(),'EN') or contains(text(),'FR')]";
	//labels
	private String h1InsuredPageTitleXPath = "//h1[@id = 'page-title']";
	private String spanInfoCompletionNoteXPath = "//span[@id = 'information-Completion-Note']";
	
	
	private String divHelpToolTipWrapperXPath = "//span[@id = 'info-completion-note-tooltip-wrapper']";		
	private String spanHelpToolTipIconXPath = "//span[@id = 'info-completion-note-tooltip-icon']";
	
	// Header Xpath like Insured Person 1 or person 2
	private String h2InsuredPersonOneXPath = "//h2[@id = 'Insured person 1' or @id = 'Personne assurée n°1']";
	private String h2InsuredPersonTwoXPath = "//h2[@id = 'Insured person 2' or @id = 'Personne assurée n°2']";
	
	private String legendIsOwnerInsuredLabelXPath = "//legend[@id = 'insured-person-form-insured-an-owner-label']";
	private String legendIsOwnerInsuredTwoLabelXPath = "//legend[@id = 'insured-person-form2-insured-an-owner-label']";
	
	private String divInsuredNameLabelXPath = "//div[@id = 'insured-person-form-insuredPerson']//div[contains(text(),'Insured name') or contains(text(),'Nom de l’assuré')]";
	private String divInsuredNameTwoLabelXPath = "//div[@id = 'insured-person-form2-insuredPerson']//div[contains(text(),'Insured name') or contains(text(),'Nom de l’assuré')]";
	
	private String legendSexOfInsuredLabelXPath = "//legend[@id = 'insured-person-form-insured-sex-label']";
	private String legendSexOfInsuredTwoLabelXPath = "//legend[@id = 'insured-person-form2-insured-sex-label']";
	
	private String divDOBLabelXPath = "//div[@id = 'insured-person-form-insuredPerson']//div[./div[@id = 'dob']]/div";
	private String divDOBLabelTwoXPath = "//div[@id = 'insured-person-form2-insuredPerson']//div[./div[@id = 'dob']]/div";
	
	private String labelBirthDayXPath = "//label[@for = 'insured-person-form-insured-dob-day']";
	private String labelBirthMonthXPath = "//label[@id = 'insured-person-form-insured-dob-month-label']";	
	private String labelBirthYearXPath = "//label[@for = 'insured-person-form-insured-dob-year']";
	
	private String labelBirthDayTwoXPath = "//label[@for = 'insured-person-form2-insured-dob-day']";	
	private String labelBirthMonthTwoXPath = "//label[@id = 'insured-person-form2-insured-dob-month-label']";
	private String labelBirthYearTwoXPath = "//label[@for = 'insured-person-form2-insured-dob-year']";
	
	private String startApplicationId="start-new-application-button";
	
	public InsuredInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	public void startApplication()
	{
		this.driverUtil.getWebElement(By.id(startApplicationId)).click();
	}
	
	//Insured Person One Elements	
	public void ClickOngIsOwnerInsuredOnPolicyButton(String options, int ownerNumber)	
	{
		String useXPath = (options.equals("Yes")||options.equals("Oui")) ? String.format(labelOwnerInsuredYesXPath, options): (options.equals("No")||options.equals("Non")) ?  String.format(labelOwnerInsuredNoXPath,options) : "Error";
		this.driverUtil.getWebElements(By.xpath(useXPath)).get(ownerNumber).click();	
	}
	
	
	public WebElement getInsuredPerson1Sex(boolean bln,int index) {
		String useXPath = (bln) ?  labelInsuredPerson1MaleXPath: labelInsuredPerson1FemaleXPath;
		return this.driverUtil.getWebElements(By.xpath(useXPath)).get(index);
	}
	
	
	
	public WebElement getBirthDayPerson1() {
		return this.driverUtil.getWebElement(By.xpath(inputBirthDayXPath));
	}
	
	public void selectBirthMonthPerson1(String month) {		
		this.driverUtil.click(By.xpath(divBirthMonthXPath));
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(ulBirthMonthXPath));
		monthDropdown.findElement(By.xpath("//li[contains(text(),'"+month+"')]")).click();
	}
	
	public WebElement getBirthMonthPerson1() {		
		return this.driverUtil.getWebElement(By.xpath(divBirthMonthXPath));
	}
	
	public WebElement getBirthYearPerson1() {
		return this.driverUtil.getWebElement(By.xpath(inputBirthYearXPath));
	}
	
	//Insured Person Two Elements
	
	

	public WebElement getFirstNamePerson(int personeNumber) {
		return this.driverUtil.getWebElements(By.xpath(inputInsuredFirstNameXPath)).get(personeNumber);
	}
	public WebElement getMiddleInitialPerson(int personeNumber) {
		return this.driverUtil.getWebElements(By.xpath(inputInsuredMiddleInitialXPath)).get(personeNumber);
	}
	public WebElement getLastNamePerson(int personeNumber) {
		return this.driverUtil.getWebElements(By.xpath(inputInsuredLastNameXPath)).get(personeNumber);
	}
	
	
	
	
	
	
	
	
	
	public WebElement getBirthDayPerson2() {
		return this.driverUtil.getWebElement(By.xpath(inputBirthDay2XPath));
	}
	
	public void selectBirthMonthPerson2(String month) {		
		this.driverUtil.click(By.xpath(divBirthMonth2XPath));
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(ulBirthMonth2XPath));
		monthDropdown.findElement(By.xpath("//li[contains(text(),'"+month+"')]")).click();
	}
	
	public WebElement getBirthMonthPerson2() {		
		return this.driverUtil.getWebElement(By.xpath(divBirthMonth2XPath));
	}
	
	public WebElement getBirthYearPerson2() {
		return this.driverUtil.getWebElement(By.xpath(inputBirthYear2XPath));
	}
	
	//Error Fields
	public String getOwnerInsuredOnPolicyRequiredError() {	
		return this.driverUtil.getText(By.xpath(divIsOwnerInsuredErrorFieldXPath));
	}
	
	public String getFirstNameRequiredError() {	
		return this.driverUtil.getText(By.xpath(divInsured1FirstNameErrorFieldXPath));
	}
	
	public String getLastNameRequiredError() {	
		return this.driverUtil.getText(By.xpath(divInsured1LastNameErrorFieldXPath));
	}
	
	public String getInsuredPersonSexRequiredError() {
		return this.driverUtil.getText(By.xpath(divInsuredPersonSexErrorFieldXPath));
	}
	
	public String getDateofBirthRequiredError() {
		return this.driverUtil.getText(By.xpath(divDateOfBirthErrorFieldXPath));
	}
	
	public String getDateofBirthInvalidDateError() {
		return this.driverUtil.getText(By.xpath(divDateOfBirthErrorFieldXPath));
	}
	
	public String getDateofBirthFutureDateError() {
		return this.driverUtil.getText(By.xpath(divDateOfBirthErrorFieldXPath));
	}
	
	//Label Fields
	public WebElement getPageTitle() {
		return this.driverUtil.getWebElement(By.xpath(h1InsuredPageTitleXPath));
	}
	
	public WebElement getCompletionNoteLabel() {
		return this.driverUtil.getWebElement(By.xpath(spanInfoCompletionNoteXPath));
	}
	
	public WebElement getHelpToolTipLabel() {
		return this.driverUtil.getWebElement(By.xpath(divHelpToolTipWrapperXPath));
	}
	
	public void clickHelpToolTipLabel() {
		this.driverUtil.hoverWebElement(By.xpath(spanHelpToolTipIconXPath));
	}
	
	//Label Insured Person 1
	public WebElement getInsuredPersonLabel() {
		return this.driverUtil.getWebElement(By.xpath(h2InsuredPersonOneXPath));
	}
	
	public WebElement getIsOwnerInsuredLabel() {
		return this.driverUtil.getWebElement(By.xpath(legendIsOwnerInsuredLabelXPath));
	}
	
	public WebElement getInsuredNameLabel() {
		return this.driverUtil.getWebElement(By.xpath(divInsuredNameLabelXPath));
	}
	
	public WebElement getSexofInsuredLabel() {
		return this.driverUtil.getWebElement(By.xpath(legendSexOfInsuredLabelXPath));
	}
	
	public WebElement getDOBLabel() {
		return this.driverUtil.getWebElement(By.xpath(divDOBLabelXPath));
	}
	
	public WebElement getBirthDayLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelBirthDayXPath));
	}
	
	public WebElement getBirthMonthLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelBirthMonthXPath));
	}
	
	public WebElement getBirthYearLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelBirthYearXPath));
	}
	
	//Label Insured Person 2
	public WebElement getInsuredPersonTwoLabel() {
		return this.driverUtil.getWebElement(By.xpath(h2InsuredPersonTwoXPath));
	}
	
	public WebElement getIsOwnerInsuredTwoLabel() {
		return this.driverUtil.getWebElement(By.xpath(legendIsOwnerInsuredTwoLabelXPath));
	}
	
	public WebElement getInsuredNameTwoLabel() {
		return this.driverUtil.getWebElement(By.xpath(divInsuredNameTwoLabelXPath));
	}
	
	public WebElement getSexofInsuredTwoLabel() {
		return this.driverUtil.getWebElement(By.xpath(legendSexOfInsuredTwoLabelXPath));
	}
	
	public WebElement getDOBTwoLabel() {
		return this.driverUtil.getWebElement(By.xpath(divDOBLabelTwoXPath));
	}
	
	public WebElement getBirthDayTwoLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelBirthDayTwoXPath));
	}
	
	public WebElement getBirthMonthTwoLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelBirthMonthTwoXPath));
	}
	
	public WebElement getBirthYearTwoLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelBirthYearTwoXPath));
	}
	
	//Is the field visible
	public boolean isInsuredPersonOneHeaderVisible() {
		return this.driverUtil.getWebElements(By.xpath(h2InsuredPersonOneXPath)).size() > 0;
	}
	
	public boolean isInsuredPersonTwoHeaderVisible() {
		return this.driverUtil.getWebElements(By.xpath(h2InsuredPersonTwoXPath)).size() > 0;
	}
	
	public boolean isOwnerOneInsuredFieldVisible(int ownerNumber) {
		 int owner=0;
		 
		 if(ownerNumber==0) {
			 owner=0; 
		 }else{ 
			 owner=1;			 
		 }
		return this.driverUtil.getWebElements(By.xpath(String.format(labelOwnerInsuredYesXPath, "Yes"))).size() > owner;
	}
	
	
	
	public boolean isInsuredPersonOneSectionVisible() {
		return this.driverUtil.getWebElements(By.xpath(divInsuredPersonOneFormXPath)).size() > 0;
	}
	
	public boolean isInsuredPersonTwoSectionVisible() {
		return this.driverUtil.getWebElements(By.xpath(divInsuredPersonTwoFormXPath)).size() > 0;
	}
	
	//next
	public void clickNextButton() {
		this.driverUtil.click(By.xpath(buttonNextButtonXPath));
	}
	
	//English French toggle
	public WebElement getENFRToggle() {
		return this.driverUtil.getWebElement(By.xpath(buttonENFRXPath));
	}
	
}