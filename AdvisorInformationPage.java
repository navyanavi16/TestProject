package term_conversion;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class AdvisorInformationPage {

	private DriverUtil driverUtil;

	// -------------------------Xpaths---------------------
	private String pageTitleID = "page-title"; // Advisor info page title
	private String pageTitleXpath = "//h1[@id='page-title']/span";
	private String tabAdvisorXpath = "//h2[@id='form-title']/span[@tabindex='%S']";
	private String advisorDivDropDownXpath = "//div[@id='advisor-code-%s-value']";
	private String advisorULDropDownXpath = "//ul[@id='advisor-code-%s-listbox']";
	private String advisorNameValueXpath = "//p[@id='advisor-name-%s']";
	private String advisorBranchCodeValueXpath = "//p[@id='advisor-branch-code-%s']";
	private String advisorBranchNameValueXpath = "//p[@id='branch-name-%s']";
	private String advisorBranchEmailValueXpath = "//p[@id='advisor-email-%s']";
	// add

	private String advisorPhoneNumberlInputBoxXpath = "//input[@id='advisor-phone-number-%s']";
	private String advisorExtInputboxXpath = "//input[@id='advisor-phone-ext-%s']";
	private String advisorCellPhoneNumberInputboxXpath = "//input[@id='advisor-cellphone-number-%s']";

	// ---------------xpaths for labels validation---------
	private String advisorCodeLabelXapth = "//label[@id='advisor-code-%S-label']";
	private String advisorNameLabelXapth = "//label[@id='advisor-name-%S-label']";
	private String advisorBranchCodeLabelXapth = "//label[@id='advisor-branch-code-%s-label']";
	private String advisorBranchNameLabelXapth = "//label[@id='branch-name-%s-label']";
	private String advisorEmailLabelXapth = "//label[@id='advisor-email-%s-label']";

	private String advisorPhoneNumberLabelXpath = "//label[@for='advisor-phone-number-%s']";
	private String advisorExtLabelpath = "//label[@for='advisor-phone-ext-%s']";
	private String advisorCellPhoneNumberLabelboxXpath = "//label[@for='advisor-cellphone-number-%s']";

	// Adding Second Advisor
	private String secondAdvisorButtonId = "add-advisor-button";
	// Remove Second Advisor Link
	private String removeAdvisorLinkXapth = "//a[text()='Remove advisor']";

	// Advisor 2 Details. for phone number we need to pass index as 1

	private String advisor2CodeInputboxXpath = "//input[@id='advisor-code-%s']";
	private String advisor2FirstNameInputboxXpath = "//input[@id='advisor-name-%s-firstName']";
	private String advisor2MiddleNameInputboxXpath = "//input[@id='advisor-name-%s-middleName']";
	private String advisor2LastNameInputboxXpath = "//input[@id='advisor-name-%s-lastName']";
	private String advisor2BranchCodeInputboxXpath = "//input[@id='advisor-branch-code-%s']";
	private String advisor2BranchEmailInputXpath = "//input[@id='advisor-email-%s']";

	// For Yes or No Button -- Pass parameter as Yes or No
	private String applicationCompleteYeButtonXapth = "//button[starts-with(@id,'owner-in-person') and contains(text(),'Yes')]";
	private String applicationCompleteNoButtonXapth = "//button[starts-with(@id,'owner-in-person') and contains(text(),'No')]";

	private String tellMePolicyID = "tell-us-who-completed";
	private String tellMeSomethingTextboxID = "tell-us-anything";
	private String saveAndCloseButtonID = "save-close-btn";
	private String lockESignButtonXpath = "//button[@id='next-btn']";
	private String startApplicationId = "//button[@id='start-new-application-button']";

	private String getTable = "//*[@id='advisorForm-container']/div[3]/div/div[2]/table";

	// ----error handling-"Required" error message Field xpaths----
	private String advisorCodeForAdvisor1ErrorXpath = "//div[@id='advisor-code-%s-errors']/div";
	private String adivsor1PhoneNumberErrorXpath = "//div[@id='advisor-phone-number-%s-errors']/div";
	private String advisor2FirstNameErrorXpath = "//div[@id='advisor-name-1-firstName-errors']/div";
	private String advisor2LastNameErrorXpath = "//div[@id='advisor-name-1-lastName-errors']/div";
	private String advisor2BranchCodeErrorXpath = "//div[@id='advisor-branch-code-1-errors']/div";
	private String advisor2EmailErrorXpath = "//div[@id='advisor-email-1-errors']/div";
	private String didYouCompleteAppInPersonYesOrNoErrorXpath = "//div[@id='owner-in-person--error']/span";

	private String howTheAppWasCompletedErrorXpath = "//div[@id='common.required-error']/span";
	
	private String totalShareXpath="//td[@title='totalAdvisorShareNumber']/span";
	
	
	private String totalErrorLableXpath="//div[@id='totalShareError-error']/span";
	
	private String advisor1PercentageShareErrorXpath="//div[@id='advisor-share-0-errors']/div";
	private String advisor2PercentageShareErrorXpath="//div[@id='advisor-share-1-errors']/div";
	
	public WebElement getadvisor1PercentageShareError() {		
		return this.driverUtil.getWebElement(By.xpath(advisor1PercentageShareErrorXpath));

	}
	public WebElement getadvisor2PercentageShareError() {		
		return this.driverUtil.getWebElement(By.xpath(advisor2PercentageShareErrorXpath));

	}
	
	
	

	public WebElement getAdvisorCodeForAdvisor1ErrorLabel(int index) {
		String newXapth = formatXpath(advisorCodeForAdvisor1ErrorXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}
	
	public WebElement gettotalErrorLable() {		
		return this.driverUtil.getWebElement(By.xpath(totalErrorLableXpath));

	}

	public WebElement getAdivsor1PhoneNumberErrorLabel(int index) {
		String newXapth = formatXpath(adivsor1PhoneNumberErrorXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}
	
	public WebElement gettotalShareXpathErrorLabel() {		
		return this.driverUtil.getWebElement(By.xpath(totalShareXpath));

	}

	public WebElement getAdvisor2FirstNameError() {

		return this.driverUtil.getWebElement(By.xpath(advisor2FirstNameErrorXpath));

	}

	public WebElement getAdvisor2LastNameErrorLabel(int index) {

		return this.driverUtil.getWebElement(By.xpath(advisor2LastNameErrorXpath));

	}

	public WebElement getAdvisor2BranchCodeErrorLabel() {

		return this.driverUtil.getWebElement(By.xpath(advisor2BranchCodeErrorXpath));

	}

	public WebElement getAdvisor2EmailErrorLabel() {

		return this.driverUtil.getWebElement(By.xpath(advisor2EmailErrorXpath));

	}

	public WebElement getDidYouCompleteAppInPersonYesOrNoErrorLabel() {

		return this.driverUtil.getWebElement(By.xpath(didYouCompleteAppInPersonYesOrNoErrorXpath));

	}

	public WebElement getHowTheAppWasCompletedErrorLabel() {

		return this.driverUtil.getWebElement(By.xpath(howTheAppWasCompletedErrorXpath));

	}

	public WebElement elockSignButton() {
		// Add Comment
		return this.driverUtil.getWebElement(By.xpath(lockESignButtonXpath));
	}

	public String formatXpath(String value, int index) {

		String xapth = String.format(value, Integer.toString(index));
		return xapth;
	}

	public AdvisorInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}

	public void startApplication() {
		this.driverUtil.getWebElement(By.xpath(startApplicationId)).click();
	}

	public String[] getCommisionTableRow(Integer row) {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getTableBody(getTable, row);
	}

	public String getPageTitle() {
		String title = "";
		title = this.driverUtil.getWebElement(By.xpath(pageTitleXpath)).getText() + " "
				+ this.driverUtil.getWebElement(By.id(pageTitleID)).getText();
		return title;
	}

	public void ClearTextBox(WebDriver driver, WebElement element) {

		Actions act = new Actions(driver);
		act.click(element).pause(50).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).pause(50)
				.sendKeys(Keys.BACK_SPACE).build().perform();

	}

	public WebElement getAdvisorName(int index) {
		String newXapth = formatXpath(advisorNameValueXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorBranchCode(int index) {
		String newXapth = formatXpath(advisorBranchCodeValueXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorBranchName(int index) {
		String newXapth = formatXpath(advisorBranchNameValueXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorEmail(int index) {
		String newXapth = formatXpath(advisorBranchEmailValueXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));
	}

	public WebElement getAdvisorPhoneNumber(int index) {
		String newXapth = formatXpath(advisorPhoneNumberlInputBoxXpath, index);

		return this.driverUtil.getWebElement(By.xpath(newXapth));
	}

	public WebElement getAdvisorExtensionNumber(int index) {
		String newXapth = formatXpath(advisorExtInputboxXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));
	}

	public WebElement getAdvisorCellPhoneNumber(int index) {
		String newXapth = formatXpath(advisorCellPhoneNumberInputboxXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));
	}

	public void selectAdvisorCode(int index, String advisorCode) {
		String newXapth = formatXpath(advisorDivDropDownXpath, index);

		this.driverUtil.click(By.xpath(newXapth));

		newXapth = formatXpath(advisorULDropDownXpath, index);
		WebElement advisorCodeDropDown = this.driverUtil.getWebElement(By.xpath(newXapth));
		advisorCodeDropDown.findElement(By.xpath("//li[contains(text(),'" + advisorCode + "')]")).click();
	}

	public WebElement getDidYouButton(String str) {

		String useXPath = (str.equals("Yes")) ? applicationCompleteYeButtonXapth
				: (str.equals("No")) ? applicationCompleteNoButtonXapth : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}

	public WebElement getMeAboutApplicationTextArea() {

		return this.driverUtil.getWebElement(By.id(tellMePolicyID));
	}

	public WebElement getMeAboutYourSelf() {

		return this.driverUtil.getWebElement(By.id(tellMeSomethingTextboxID));
	}

	public WebElement saveAndCloseButton() {

		return this.driverUtil.getWebElement(By.id(saveAndCloseButtonID));
	}

	public WebElement getAdvisorCodeLabel(int index) {
		String newXapth = formatXpath(advisorCodeLabelXapth, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorNameLabel(int index) {
		String newXapth = formatXpath(advisorNameLabelXapth, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorBracnhCodeLabel(int index) {
		String newXapth = formatXpath(advisorBranchCodeLabelXapth, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorBranchNameLabel(int index) {
		String newXapth = formatXpath(advisorBranchNameLabelXapth, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorBranchEmailLabel(int index) {
		String newXapth = formatXpath(advisorEmailLabelXapth, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorPhoneNumberLabel(int index) {
		String newXapth = formatXpath(advisorPhoneNumberLabelXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorExtNumberLabel(int index) {
		String newXapth = formatXpath(advisorExtLabelpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisorCellPhoneNumberLabel(int index) {
		String newXapth = formatXpath(advisorCellPhoneNumberLabelboxXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getSecondAdvisorButton() {
		return this.driverUtil.getWebElement(By.id(secondAdvisorButtonId));
	}

	public WebElement getRemoveAdvisorLink() {
		return this.driverUtil.getWebElement(By.xpath(removeAdvisorLinkXapth));
	}

	public WebElement advisor2CodeInputboxXpath(int index) {
		String newXapth = formatXpath(advisor2CodeInputboxXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisor2FirstNameInputbox(int index) {
		String newXapth = formatXpath(advisor2FirstNameInputboxXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAvisor2MiddleNameInputbox(int index) {
		String newXapth = formatXpath(advisor2MiddleNameInputboxXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisor2LastNameInputbox(int index) {
		String newXapth = formatXpath(advisor2LastNameInputboxXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisor2BranchCodeInputbox(int index) {
		String newXapth = formatXpath(advisor2BranchCodeInputboxXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}

	public WebElement getAdvisor2BranchEmailInput(int index) {
		String newXapth = formatXpath(advisor2BranchEmailInputXpath, index);
		return this.driverUtil.getWebElement(By.xpath(newXapth));

	}
	public WebElement EnterTheValueinTableCell(int rowNumber, int colIndex)	{
		
		List<WebElement> rowData = this.driverUtil.getWebElement(By.xpath(getTable + "/tbody")).findElements(By.tagName("tr")).get(rowNumber).findElements(By.tagName("td"));
		
		
		String useXpath=String.format("//input[contains(@id,'advisor-share-%s')]", rowNumber);		
		
		return rowData.get(colIndex).findElement(By.xpath(useXpath));
		
		
		
	}

}
