package term_conversion;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class PolicyInformationPage {
	private DriverUtil driverUtil;
	
	//XPath
	private String divOriginalPolicyTypeXPath = "//div[@id = 'original-policy-type-value']";
	private String divCoverageTypeXPath = "//fieldset[@id = 'coverage-type']";	
	private String divDisabilityInsuredNoteXPath = "//*[@id='disability-insured-note']";
	private String divProductTypeXPath = "//div[@id = 'product-dropdown-value']";
	private String divQuebecToolTipXPath = "//span[@id = 'policy-quebec-tooltip-wrapper']";
	private String divJointCoverageTypeXPath = "//div[@id = 'joint-coverage-type-note']/div[2]";
	private String divPolicyInstructionsXPath = "//div[@id='PolicyInformation']/div/div/div/ul[1]/li";
	private String divOriginalPolicyNumberErrorXPath = "//div[@id = 'original-policy-number-errors']";
	private String divOriginalPolicyErrorXPath = "//div[@id = 'original-policy-type-errors']";
	private String divPolicyQuebecErrorXPath = "//div[@id = 'policy-quebec--error']";
	private String divDisabilityWaiverErrorXPath = "//div[@id = 'disability-waiver--error']";
	private String divDisabilityInsuredErrorXPath = "//div[@id = 'disability-insured--error']";
	private String divCoverageIDErrorXPath = "//div[@id = 'coverageId-number-errors']";
	private String divProductErrorXPath = "//div[@id = 'product-dropdown-errors']";
	private String divCoverageTypeErrorXPath = "//div[@id='coverage-type-errors']/div";
	private String ulOriginalPolicyTypeXPath = "//ul[@id = 'original-policy-type-listbox']";
	private String ulProductTypeXPath = "//ul[@id = 'product-dropdown-listbox']";
	private String inputOriginalPolicyNumberXPath = "//input[@id = 'original-policy-number']";
	private String inputCoverageIDNumberXPath = "//input[@id = 'coverageId-number']";
	private String inputPolicyIssuedQuebecYesXPath = "//button[@id = 'policy-quebec-0']";
	private String inputPolicyIssuedQuebecNoXPath = "//button[@id = 'policy-quebec-1']";
	private String inputDisabilityWaiverYesXPath = "//button[@id = 'disability-waiver-0']";
	private String inputDisabilityWaiverNoXPath = "//button[@id = 'disability-waiver-1']";
	private String inputDisabilityInsuredYesXPath = "//button[@id = 'disability-insured-0']";
	private String inputDisabilityInsuredNoXPath = "//button[@id = 'disability-insured-1']";
	private String h2PolicyInfoFormTitleXPath = "//h2[@id = 'policyInfo-form-title']";
	private String h2ConversionInfoFormTitleXPath = "//h2[@id = 'form-title']";
	private String labelOriginalPolicyNumberXPath = "//label[@for = 'original-policy-number']";
	private String labelOriginalPolicyXPath = "//label[@id = 'original-policy-type-label']";
	private String labelCoverageIDNumberXPath = "//label[@for = 'coverageId-number']";
	
	private String labelPolicyIssuedQuebecYesXPath = "//button[starts-with(@id,'policy-quebec') and contains(text(),'Yes')]";
	private String labelPolicyIssuedQuebecNoXPath = "//button[starts-with(@id,'policy-quebec') and contains(text(),'No')]";
	// for Fench label 
	private String FRlabelPolicyIssuedQuebecYesXPath = "//button[starts-with(@id,'policy-quebec') and contains(text(),'Oui')]";
	private String FRlabelPolicyIssuedQuebecNoXPath = "//button[starts-with(@id,'policy-quebec') and contains(text(),'Non')]";
	private String FRlabelDisabilityWaiverYesXPath = "//button[starts-with(@id,'disability-waiver') and contains(text(),'Oui')]";
	private String FRlabelDisabilityWaiverNoXPath = "//button[starts-with(@id,'disability-waiver') and contains(text(),'Non')]";
	
	
	private String labelDisabilityWaiverYesXPath = "//button[starts-with(@id,'disability-waiver') and contains(text(),'Yes')]";
	private String labelDisabilityWaiverNoXPath = "//button[starts-with(@id,'disability-waiver') and contains(text(),'No')]";
	
	
	private String labelDisabilityInsuredYesXPath = "//button[@id ='disability-insured-0']";
	
	private String labelDisabilityInsuredNoXPath = "//button[@id = 'disability-insured-1']";
	
	private String labelProductXPath = "//label[@id = 'product-dropdown-label']";
	private String legendCoverageTypeXPath = "//fieldset[@id = 'coverage-type']/legend";
	private String legendPolicyIssuedQuebecXPath = "//legend[@id = 'policy-quebec-label']";
	private String legendDisabilityWaiverXPath = "//legend[@id = 'disability-waiver-label']";
	private String legendDisabilityInsuredXPath = "//legend[@id = 'disability-insured-label']";
	private String pBeforeYouBeginInstructionsXPath = "//p[@id = 'Before you begin:-subtitle' or @id = 'Avant de commencer:-subtitle']";
	private String buttonQuebecToolTip = "//button[@id = 'policy-quebec-tooltip-toggle']";
	private String buttonProductXPath = "//button[@id = 'product-dropdown-toggle']";
	private String buttonNextXPath = "//button[@id = 'next-btn']";
	
	public PolicyInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	//Elements
	
	public WebElement getOriginalPolicyNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputOriginalPolicyNumberXPath));
	}
	
	public void selectOriginalPolicy(String policy) {		
		this.driverUtil.click(By.xpath(divOriginalPolicyTypeXPath));
		WebElement dropdown = this.driverUtil.getWebElement(By.xpath(ulOriginalPolicyTypeXPath));
		dropdown.findElement(By.xpath("//li[contains(text(),'"+policy+"')]")).click();
	}
	
	public WebElement getCoverageIDNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputCoverageIDNumberXPath));
	}
	
	public WebElement getIssuedInQuebec(boolean bln) {
		String useXPath = (bln) ?  labelPolicyIssuedQuebecYesXPath: labelPolicyIssuedQuebecNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	public WebElement getFRIssuedInQuebec(boolean bln) {
		String useXPath = (bln) ?  FRlabelPolicyIssuedQuebecYesXPath: FRlabelPolicyIssuedQuebecNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getDisabilityWaiver(boolean bln) {
		String useXPath = (bln) ?  labelDisabilityWaiverYesXPath: labelDisabilityWaiverNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	public WebElement getFRDisabilityWaiver(boolean bln) {
		String useXPath = (bln) ?  FRlabelDisabilityWaiverYesXPath: FRlabelDisabilityWaiverNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getDisabilityInsured(boolean bln) {
		String useXPath = (bln) ?  labelDisabilityInsuredYesXPath: labelDisabilityInsuredNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
public WebElement getUnableToPerformWork(String str) {
		
		String useXPath = (str.equals("Yes")) ?  labelDisabilityInsuredYesXPath: (str.equals("No")) ?  labelDisabilityInsuredNoXPath: "Error";
		
		
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public void selectProductType(String product) {		
		this.driverUtil.click(By.xpath(divProductTypeXPath));
		WebElement dropdown = this.driverUtil.getWebElement(By.xpath(ulProductTypeXPath));
		dropdown.findElement(By.xpath("//li[contains(text(),'"+product+"')]")).click();
	}
	
	public void selectCoverageType(String coverageType) {
		String useXPath = "//input[@aria-label= '" + coverageType + "']";
		this.driverUtil.click(By.xpath(useXPath));
	}
	
	//Labels
	public String getBeforeYouBeginInstructions() {
		return this.driverUtil.getText(By.xpath(pBeforeYouBeginInstructionsXPath));
	}
	
	public String[] getTermConversionInstructions() {
		List<WebElement> listElem = this.driverUtil.getElements(By.xpath(divPolicyInstructionsXPath));
		String[] listText = new String[listElem.size()];
		for(int i = 0; i < listElem.size(); i++) {
		    listText[i] = listElem.get(i).getText();
		}
		return listText;
			}
	
	public String[] getOriginalPolicyOptionLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divOriginalPolicyTypeXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulOriginalPolicyTypeXPath,"li");
		this.driverUtil.click(By.xpath(divOriginalPolicyTypeXPath));
		return optionLabels;
	}
	
	public String[] getAllProducts() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divProductTypeXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulProductTypeXPath,"li");
		this.driverUtil.click(By.xpath(divProductTypeXPath));
		return optionLabels;
	}
	
	public String[] getAllCoverageTypes() {
		CommonElements commonElements = new CommonElements(driverUtil);
		String[] optionLabels = commonElements.getOptionLabels(divCoverageTypeXPath,"label");
		return optionLabels;
	}
	
	//Original Policy Label
	public String getOriginalPolicyInfoTitle() {
		return this.driverUtil.getWebElement(By.xpath(h2PolicyInfoFormTitleXPath)).getText();
	}
	
	public String getOriginalPolicyNumberLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelOriginalPolicyNumberXPath)).getText();
	}
	
	public String getOriginalPolicyLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelOriginalPolicyXPath)).getText();
	}
	
	public String getCoverageIDNumberLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelCoverageIDNumberXPath)).getText();
	}
	
	public String getPolicyIssuedQuebecLabel() {
		return this.driverUtil.getWebElement(By.xpath(legendPolicyIssuedQuebecXPath)).getText();
	}
	
	public String getDisabilityWaiverLabel() {
		return this.driverUtil.getWebElement(By.xpath(legendDisabilityWaiverXPath)).getText();
	}
	
	public String getDisabilityInsuredLabel() {
		return this.driverUtil.getWebElement(By.xpath(legendDisabilityInsuredXPath)).getText();
	}
	
	public String getDisabilityInsuredNoteLabel() {
		return this.driverUtil.getWebElement(By.xpath(divDisabilityInsuredNoteXPath)).getText();
	}
	
	public String getPolicyIssuedQuebecToolTipLabel() {
		this.driverUtil.hoverWebElement(By.xpath(buttonQuebecToolTip));
		return this.driverUtil.waitForElementToBeVisible(By.xpath(divQuebecToolTipXPath)).getText();
	}
	
	//Conversion Information Labels
	public String getConversionInformationTitle() {
		return this.driverUtil.waitForElementToBeVisible(By.xpath(h2ConversionInfoFormTitleXPath)).getText();
	}
	
	public String getProductLabel() {
		return this.driverUtil.waitForElementToBeVisible(By.xpath(labelProductXPath)).getText();
	}
	
	public String getCoverageTypeLabel() { 
		return this.driverUtil.waitForElementToBeVisible(By.xpath(legendCoverageTypeXPath)).getText();
	}
	
	public String getJointCoverageTypeNoteLabel() {
		return this.driverUtil.waitForElementToBeVisible(By.xpath(divJointCoverageTypeXPath)).getText();
	}

	//Is Element Visible
	public boolean isCoverageIDVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputCoverageIDNumberXPath));
	}
	
	
	
	public boolean isDisabilityInsuredVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(labelDisabilityInsuredYesXPath));
	}
	
	
	
	public boolean isDisabilityInsuredNoteVisible() {	
		return this.driverUtil.checkElementPresent(By.xpath(divDisabilityInsuredNoteXPath));
	}
	
	
	
	public boolean isJointCoverageTypeNoteVisible() {	
		return this.driverUtil.checkElementPresent(By.xpath(divJointCoverageTypeXPath));
	}
	
	//Is Element Selected
	public boolean isIssuedInQuebecSelected(boolean bln) {
		String useXPath = (bln) ?  inputPolicyIssuedQuebecYesXPath: inputPolicyIssuedQuebecNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath)).getAttribute("aria-pressed").equalsIgnoreCase("true");
	}
	
	public boolean isDisabilityWaiverSelected(boolean bln) {
		String useXPath = (bln) ?  inputDisabilityWaiverYesXPath: inputDisabilityWaiverNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath)).getAttribute("aria-pressed").equalsIgnoreCase("true");
	}
	
	public boolean isDisabilityInsuredSelected(boolean bln) {
		String useXPath = (bln) ?  inputDisabilityInsuredYesXPath: inputDisabilityInsuredNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath)).getAttribute("aria-pressed").equalsIgnoreCase("true");
	}
	
	//Is Error Visible
	public boolean isOriginalPolicyNumberErrorVisible(String expectedErrorMessage) {
		String errorMessage = this.driverUtil.getWebElement(By.xpath(divOriginalPolicyNumberErrorXPath)).getText();
		return  (! errorMessage.isEmpty()) && errorMessage.equals(expectedErrorMessage);		
	}
	
	public boolean isOriginalPolicyErrorVisible(String expectedErrorMessage) {
		String errorMessage = this.driverUtil.getWebElement(By.xpath(divOriginalPolicyErrorXPath)).getText();
		return  (! errorMessage.isEmpty()) && errorMessage.equals(expectedErrorMessage);
	}
	
	public boolean isCoverageIDErrorVisible(String expectedErrorMessage) {
		String errorMessage = this.driverUtil.getWebElement(By.xpath(divCoverageIDErrorXPath)).getText();
		return  (! errorMessage.isEmpty()) && errorMessage.equals(expectedErrorMessage);
	}
	
	public boolean isPolicyQuebecErrorVisible(String expectedErrorMessage) {
		String errorMessage = this.driverUtil.getWebElement(By.xpath(divPolicyQuebecErrorXPath)).getText();
		return  (! errorMessage.isEmpty()) && errorMessage.equals(expectedErrorMessage);
	}
	
	public boolean isDisabilityWaiverErrorVisible(String expectedErrorMessage) {
		String errorMessage = this.driverUtil.getWebElement(By.xpath(divDisabilityWaiverErrorXPath)).getText();
		return  (! errorMessage.isEmpty()) && errorMessage.equals(expectedErrorMessage);
	}
	
	public boolean isDisabilityInsuredErrorVisible(String expectedErrorMessage) {
		String errorMessage = this.driverUtil.getWebElement(By.xpath(divDisabilityInsuredErrorXPath)).getText();
		return  (! errorMessage.isEmpty()) && errorMessage.equals(expectedErrorMessage);
	}
	
	public boolean isProductErrorVisible(String expectedErrorMessage) {
		String errorMessage = this.driverUtil.getWebElement(By.xpath(divProductErrorXPath)).getText();
		return  (! errorMessage.isEmpty()) && errorMessage.equals(expectedErrorMessage);
	}
	
	public boolean isCoverageTypeErrorVisible(String expectedErrorMessage) {
		String errorMessage = this.driverUtil.getWebElement(By.xpath(divCoverageTypeErrorXPath)).getText();
		return  (! errorMessage.isEmpty()) && errorMessage.equals(expectedErrorMessage);
	}
	
	//Is Element Enabled
	public boolean isProductEnabled() {
		return this.driverUtil.getWebElement(By.xpath(buttonProductXPath)).isEnabled();
	}
	
	//Next Button
	public void clickNext() {
		this.driverUtil.click(By.xpath(buttonNextXPath));
	}

}