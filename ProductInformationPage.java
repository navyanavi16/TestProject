package term_conversion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class ProductInformationPage {
	private DriverUtil driverUtil;
	
	//XPath Fields
	private String startApplicationId = "start-new-application-button";
	
	private String pProductXPath = "//p[@id = 'summary-selected-product']";
	private String pCoverageTypeXPath = "//p[@id = 'summary-selected-coverage']";
	private String h1PageTitleXPath = "//h1[@id = 'page-title']";
	private String labelProductConvertingToXPath = "//label[@id = 'summary-selected-product-label']";
	private String labelCoverageTypeXPath = "//label[@id = 'summary-selected-coverage-label']";
	////fieldset[@id ='insured1-smoker-status']
	private String divPersonOneSmokerStatusXPath = "//fieldset[@id ='insured1-smoker-status']";
	private String divPersonTwoSmokerStatusXPath = "//fieldset[@id ='insured2-smoker-status']";
	private String divPremiumDurationXPath = "//fieldset[@id ='premium-duration']";
	private String divDividendOptionXPath = "//fieldset[@id ='dividend-option']";
	private String divCostDurationOptionXPath = "//fieldset[@id ='cost-duration-option']";
	private String divPerformanceCreditOptionXPath = "//fieldset[@id ='performance-credit-option']";
	private String divInsuredOneHealthStyleXPath = "//div[@id = 'insured1-health-style-value']";
	private String divPremiumDurationTitleXPath = "//fieldset[@id = 'premium-duration']/legend";
	private String divDividendOptionTitleXPath = "//fieldset[@id = 'dividend-option']/legend";
	private String divToolTipXPath = "//span[@role = 'tooltip']";
	private String divDepositPaymentMonthlyNoteXPath = "//div[@id = 'deposit-payment-monthly-note']";
	private String divRiderCoverageNoteXPath = "//div[@id = 'riderCoverageNoteId']";
	private String divRiderCoverageNoteTwoXPath = "//div[./div/div[@id = 'riderCoverageNoteId']]/p//li";
	private String divSmokerStatusErrorXPath = "//fieldset[@id = 'insured1-smoker-status']/div[@id = 'insured1-smoker-status-errors']";
	private String divPremiumDurationErrorXPath = "//fieldset[@id = 'premium-duration']/div[@id = 'premium-duration-errors']";
	private String divDepositOptionPaymentErrorXPath = "//div[@id = 'deposit-option-payment--error']";
	private String divDepositPaymentMonthlyErrorXPath = "//div[@id = 'deposit-payment-monthly--error']";
	private String divDepositPaymentMonthlyAmountErrorXPath = "//div[@id = 'deposit-payment-monthly-amount-errors']";
	private String divTotalDepositPaymentErrorXPath = "//div[@id = 'total-deposit-payment-amount-errors']";
	private String divPremiumOptionErrorXPath = "//div[@id = 'premium-option-errors']";
	private String divHealthStyleErrorXPath = "//div[@id = 'insured1-health-style-errors']";
	private String divCostDurationErrorXPath = "//div[@id = 'cost-duration-option-errors']/div";
	private String divPerformanceCreditErrorXPath = "//fieldset[@id = 'performance-credit-option']/div[@id = 'performance-credit-option-errors']";
	private String divTermOptionAmountErrorXPath = "//div[@id = 'term-option-amount-errors']";
	private String divCostOfInsuranceError = "//div[@id = 'cost-of-insurance-errors']";
	private String ulInsuredOneHealthStyleXPath = "//ul[@id = 'insured1-health-style-listbox']";
	private String divInsuredTwoHealthStyleXPath = "//div[@id = 'insured2-health-style-value']";
	private String ulInsuredTwoHealthStyleXPath = "//ul[@id = 'insured2-health-style-listbox']";
	private String inputTermOptionAmountXPath = "//input[@id = 'term-option-amount']";
	private String labelDepositOptionPaymentYesXPath = "//button[@id = 'deposit-option-payment-0']";
	private String labelDepositOptionPaymentNoXPath = "//button[@id = 'deposit-option-payment-1']";
	private String labelDepositMonthlyPaymentYesXPath = "//button[@id = 'deposit-payment-monthly-0']";
	private String labelDepositMonthlyPaymentNoXPath = "//button[@id = 'deposit-payment-monthly-1']";
	private String labelSmokerStatusXPath = "//label[@id = 'smoker-status-label']";
	private String labelDepositPaymentMonthlyAmountXPath = "//label[@for = 'deposit-payment-monthly-amount']";
	private String labelTotalDepositPaymentAmountXPath = "//label[@for = 'total-deposit-payment-amount']";
	private String labelHealthStyleXPath = "//label[contains(text(),'Health style category') or contains(text(),'Indice-santé')]"; 
	private String labelTermOptionAmountXPath = "//label[@for = 'term-option-amount']";
	private String labelTIRXPath = "//label[@for = 'TIR']";
	private String inputDepositPaymentMonthlyAmountXPath = "//input[@id = 'deposit-payment-monthly-amount']";
	private String inputTotalDepositPaymentAmountXPath = "//input[@id = 'total-deposit-payment-amount']";
	private String inputTIRCheckBoxXPath = "//input[@id = 'TIR']";
	private String divCostOfInsuranceXPath = "//fieldset[@id = 'cost-of-insurance']";
	private String divPremiumOptionXPath = "//fieldset[@id = 'premium-option']";
	private String fieldsetTIRRiderOptionsXPath = "(//fieldset[contains(@id,'checkbox-id')])[2]";
	private String fieldsetTIRRiderOptionsJointXPath = "//fieldset[contains(@id,'checkbox-id')]"; 
	private String inputTotalDWRiderXPath = "//input[@id = '92']";  //input[@id = 'totalDWRider']
	private String inputTotalDWRiderOnPayorXPath = "//input[@id = '1000600002']";  //input[@id = 'totalDWRiderOnPayor']
	private String legendDepositOptionPaymentXPath = "//legend[@id = 'deposit-option-payment-label']";
	private String legendDepositPaymentMonthlyXPath = "//legend[@id = 'deposit-payment-monthly-label']";
	private String legendCostDurationLabelXPath = "//fieldset[@id ='cost-duration-option']/legend";
	private String legendPerformanceCreditLabelXPath = "//fieldset[@id ='performance-credit-option']/legend";
	private String legendPremiumOptionLabelXPath = "//fieldset[@id = 'premium-option']/legend";
	private String legendCostOfInsuranceLabelXPath = "//fieldset[@id = 'cost-of-insurance']/legend";
	private String buttonTotalDepositToolTipXPath = "//button[@id = 'total-deposit-payment-tooltip-toggle']";
	private String spanSelectRiderCoverageNoteXPath = "//span[contains(text(),'If the rules') or contains(text(),'Si les règles du contrat')]";
	
	public ProductInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	public void startApplication() {
		this.driverUtil.getWebElement(By.id(startApplicationId)).click();
	}
	
	//Fields
	public WebElement getProductName() {
		return this.driverUtil.getWebElement(By.xpath(pProductXPath));
	}
	
	public WebElement getCoverageType() {
		return this.driverUtil.getWebElement(By.xpath(pCoverageTypeXPath));
	}
	
	//Manulife Par Fields
	public WebElement getDepositOptionPaymentBln(String str) {
		String useXPath = (str.equals("Yes")) ?  labelDepositOptionPaymentYesXPath: (str.equals("No")) ? labelDepositOptionPaymentNoXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getDepositMonthlyPaymentBln(String str) {
		String useXPath = (str.equals("Yes")) ?  labelDepositMonthlyPaymentYesXPath: (str.equals("No")) ? labelDepositMonthlyPaymentNoXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getDepositPaymentMonthlyAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputDepositPaymentMonthlyAmountXPath));
	}
	
	public WebElement getTotalDepositPaymentAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputTotalDepositPaymentAmountXPath));
	}
	
	public WebElement getSmokerStatusOption(String smokerStatus) {
		return this.driverUtil.getWebElement(By.xpath(divPersonOneSmokerStatusXPath)).findElement(By.xpath("//input[@aria-label = '"+smokerStatus+"']"));
	}
	public WebElement getSmoker2StatusOption(String smokerStatus) {
		
		String useXPath = (smokerStatus.equals("Smoker")) ?  "//input[@id='insured2-smoker-status_3']": (smokerStatus.equals("Non-smoker")) ? "//input[@id='insured2-smoker-status_1']" : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));	}
	
	public WebElement getPremiumDurationOption(String premiumDuration) {
		return this.driverUtil.getWebElement(By.xpath(divPremiumDurationXPath)).findElement(By.xpath("//input[@aria-label = '"+premiumDuration+"']"));
	}
	
	public WebElement getDividendOption(String dividend) {
		return this.driverUtil.getWebElement(By.xpath(divDividendOptionXPath)).findElement(By.xpath("//input[@aria-label = '"+dividend+"']"));
	}
	
	public WebElement getPremiumOption(String premium) {
		return this.driverUtil.getWebElement(By.xpath(divPremiumOptionXPath)).findElement(By.xpath("//input[@aria-label = '"+premium+"']"));
	}
	
	//Performax Option Fields
	public WebElement getHealthStyleOption(String healthStyle) {
		this.driverUtil.click(By.xpath(divInsuredOneHealthStyleXPath));
		return this.driverUtil.getWebElement(By.xpath(ulInsuredOneHealthStyleXPath)).findElement(By.xpath("//li[contains(text(),'"+healthStyle+"')]"));
	}
	
	public WebElement getHealthStyleTwoOption(String healthStyle) {
		this.driverUtil.click(By.xpath(divInsuredTwoHealthStyleXPath));
		return this.driverUtil.getWebElement(By.xpath(ulInsuredTwoHealthStyleXPath)).findElement(By.xpath("//li[contains(text(),'"+healthStyle+"')]"));
	}
	
	public WebElement getCostDurationOption(String costDuration) {
		return this.driverUtil.getWebElement(By.xpath(divCostDurationOptionXPath)).findElement(By.xpath("//input[@aria-label = '"+costDuration+"']"));
	}
	
	public WebElement getPerformanceCreditOption(String creditOption) {
		return this.driverUtil.getWebElement(By.xpath(divPerformanceCreditOptionXPath)).findElement(By.xpath("//input[@aria-label = '"+creditOption+"']"));
	}
	
	public WebElement getTermOptionAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputTermOptionAmountXPath));
	}
	
	public WebElement getCostOfInsuranceOption(String cost) {
		return this.driverUtil.getWebElement(By.xpath(divCostOfInsuranceXPath)).findElement(By.xpath("//input[@aria-label = '"+cost+"']"));
	}
	
	//TIR field
	public WebElement getTIRCheckBox() {
		return this.driverUtil.getWebElement(By.xpath(inputTIRCheckBoxXPath));
	}
	
	public WebElement getTIRRiderOption(String rider) {
		return this.driverUtil.getWebElement(By.xpath(fieldsetTIRRiderOptionsXPath)).findElement(By.xpath("//label[contains(text(),'"+rider+"')]"));
	}
	
	public String[] getTIRRiderLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(fieldsetTIRRiderOptionsXPath,"label");
	}
	
	public WebElement getTIRRiderJointOption(String rider) {
		return this.driverUtil.getWebElement(By.xpath(fieldsetTIRRiderOptionsJointXPath)).findElement(By.xpath("//label[contains(text(),'"+rider+"')]"));
	}
	
	public String[] getTIRRiderJointLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(fieldsetTIRRiderOptionsJointXPath,"label");
	}
	
	public boolean isTotalDWRiderEnabled() {
		return this.driverUtil.getWebElement(By.xpath(inputTotalDWRiderXPath)).isEnabled();
	}
	
	public boolean isTotalDWRiderOnPayorEnabled() {
		return this.driverUtil.getWebElement(By.xpath(inputTotalDWRiderOnPayorXPath)).isEnabled();
	}
	
	public String getPremiumOptionLabel() {
		return this.driverUtil.getText(By.xpath(legendPremiumOptionLabelXPath));
	}
	
	public String getCostOfInsuranceLabel() {
		return this.driverUtil.getText(By.xpath(legendCostOfInsuranceLabelXPath));
	}
	
	//Check if a field is present
	public boolean isDepositOptionPaymentBlnVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(labelDepositOptionPaymentYesXPath));
	}
	
	public boolean isDepositMonthlyPaymentBlnVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(labelDepositMonthlyPaymentYesXPath));
	}
	
	public boolean isDepositPaymentMonthlyAmountVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputDepositPaymentMonthlyAmountXPath));
	}
	
	public boolean isTotalDepositPaymentAmountVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputTotalDepositPaymentAmountXPath));
	}
	
	public boolean isTermOptionAmountVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputTermOptionAmountXPath));
	}
	
	
	
	public boolean isInsuredNameVisible(String name) {
		return this.driverUtil.checkElementPresent(By.xpath("//*[contains(text(),'"+name+"')]"));
	}
	
	public boolean isTIRCheckBoxPresent() {
		return this.driverUtil.checkElementPresent(By.xpath(inputTIRCheckBoxXPath));
	}
	
	
	
	public boolean isCostOfInsuranceSectionPresent() {
		return this.driverUtil.checkElementPresent(By.xpath(divCostOfInsuranceXPath));
	}
	
	public boolean isPremiumOptionSectionPresent() {
		return this.driverUtil.checkElementPresent(By.xpath(divPremiumOptionXPath));
	}
	
	//General Labels
	public WebElement getPageTitle() {
		return this.driverUtil.getWebElement(By.xpath(h1PageTitleXPath));
	}
	
	public WebElement getProductLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelProductConvertingToXPath));
	}
	
	public WebElement getCoverageTypeLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelCoverageTypeXPath));
	}
	
	//Manulife Par labels
	public String[] getSmokerStatusLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divPersonOneSmokerStatusXPath,"label");
	}
	
	public String[] getSmokerStatusTwoLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divPersonTwoSmokerStatusXPath,"label");
	}
	
	public String[] getPremiumDurationLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divPremiumDurationXPath,"label");
	}
	
	public String[] getDividendLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divDividendOptionXPath,"label");
	}
	
	public String[] getPremiumOptionLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divPremiumOptionXPath,"label");
	}
	
	public String getSmokerStatusTitle() {
		return this.driverUtil.getText(By.xpath(labelSmokerStatusXPath));
	}
	
	public String getPremiumDurationTitle() {
		return this.driverUtil.getText(By.xpath(divPremiumDurationTitleXPath));
	}
	
	public String getDividendOptionTitle() {
		return this.driverUtil.getText(By.xpath(divDividendOptionTitleXPath));
	}
	
	public String getDepositOptionPaymentLabel() {
		return this.driverUtil.getText(By.xpath(legendDepositOptionPaymentXPath));
	}
	
	public String getDepositPaymentMonthlyLabel() {
		return this.driverUtil.getText(By.xpath(legendDepositPaymentMonthlyXPath));
	}
	
	public String getDepositPaymentMonthlyAmountLabel() {
		return this.driverUtil.getText(By.xpath(labelDepositPaymentMonthlyAmountXPath));
	}
	
	public String getDepositPaymentMonthlyNote() {
		return this.driverUtil.getText(By.xpath(divDepositPaymentMonthlyNoteXPath));
	}
	
	public String getTotalDepositPaymentAmountLabel() {
		return this.driverUtil.getText(By.xpath(labelTotalDepositPaymentAmountXPath));
	}
	
	public String getTotalDepositToolTip() {
		this.driverUtil.hoverWebElement(By.xpath(buttonTotalDepositToolTipXPath));
		return this.driverUtil.getText(By.xpath(divToolTipXPath));
	}
	
	//Performax Gold Labels
	public String[] getHealthStyleLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divInsuredOneHealthStyleXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulInsuredOneHealthStyleXPath,"li");
		this.driverUtil.click(By.xpath(divInsuredOneHealthStyleXPath));
		return optionLabels;
	}
	
	public String[] getHealthStyleTwoLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divInsuredTwoHealthStyleXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulInsuredTwoHealthStyleXPath,"li");
		this.driverUtil.click(By.xpath(divInsuredTwoHealthStyleXPath));
		return optionLabels;
	}
	
	public String[] getCostDurationLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divCostDurationOptionXPath,"label");
	}
	
	public String[] getPerformanceCreditOption() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divPerformanceCreditOptionXPath,"label");
	}
	
	public String[] getCostOfInsuranceLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divCostOfInsuranceXPath,"label");
	}
	
	public String getHealthStyleLabel() {
		return this.driverUtil.getText(By.xpath(labelHealthStyleXPath));
	}
	
	public String getCostDurationLabel() {
		return this.driverUtil.getText(By.xpath(legendCostDurationLabelXPath));
	}
	
	public String getPerformanceCreditLabel() {
		return this.driverUtil.getText(By.xpath(legendPerformanceCreditLabelXPath));
	}
	
	public String getTermOptionAmountLabel() {
		return this.driverUtil.getText(By.xpath(labelTermOptionAmountXPath));
	}
	
	//Rider Note
	public String getSelectRiderCoverageNote() {
		return this.driverUtil.getText(By.xpath(spanSelectRiderCoverageNoteXPath));
	}
	
	public String getRiderCoverageNote() {
		String noteOne = this.driverUtil.getText(By.xpath(divRiderCoverageNoteXPath));
		String noteTwo = this.driverUtil.getText(By.xpath(divRiderCoverageNoteTwoXPath));
		return noteOne + " " + noteTwo;
	}
	
	public String getTIRRiderNote() {
		return this.driverUtil.getText(By.xpath(labelTIRXPath));
	}
	
	//Error Fields Manulife Par	
	public String getSmokerStatusError() {
		return (this.driverUtil.getWebElements(By.xpath(divSmokerStatusErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divSmokerStatusErrorXPath)) : "";
	}
	
	public String getPremiumDurationError() {
		return (this.driverUtil.getWebElements(By.xpath(divPremiumDurationErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divPremiumDurationErrorXPath)) : "";
	}
	
	public String getDepositOptionPaymentError() {
		return (this.driverUtil.getWebElements(By.xpath(divDepositOptionPaymentErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divDepositOptionPaymentErrorXPath)) : "";
	}
	
	public String getDepositPaymentMonthlyError() {
		return (this.driverUtil.getWebElements(By.xpath(divDepositPaymentMonthlyErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divDepositPaymentMonthlyErrorXPath)) : "";
	}
	
	public String getDepositPaymentMonthlyAmountError() {
		return (this.driverUtil.getWebElements(By.xpath(divDepositPaymentMonthlyAmountErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divDepositPaymentMonthlyAmountErrorXPath)) : "";
	}
	
	public String getTotalDepositPaymentAmountError() {
		return (this.driverUtil.getWebElements(By.xpath(divTotalDepositPaymentErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divTotalDepositPaymentErrorXPath)) : "";
	}
	
	public String getPremiumOptionError() {
		return (this.driverUtil.getWebElements(By.xpath(divPremiumOptionErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divPremiumOptionErrorXPath)) : "";
	}
	
	//Error Fields Performax Gold	
	public String getHealthStyleError() {
		return (this.driverUtil.getWebElements(By.xpath(divHealthStyleErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divHealthStyleErrorXPath)) : "";
	}
	
	public String getCostDurationError() {
		return (this.driverUtil.getWebElements(By.xpath(divCostDurationErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divCostDurationErrorXPath)) : "";
	}
	
	public String getPerformanceCreditError() {
		return (this.driverUtil.getWebElements(By.xpath(divPerformanceCreditErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divPerformanceCreditErrorXPath)) : "";
	}
	
	public String getTermOptionAmountError() {
		return (this.driverUtil.getWebElements(By.xpath(divTermOptionAmountErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divTermOptionAmountErrorXPath)) : "";
	}
	
	public String getCostOfInsuranceError() {
		return (this.driverUtil.getWebElements(By.xpath(divCostOfInsuranceError)).size() > 0) ? this.driverUtil.getText(By.xpath(divCostOfInsuranceError)) : "";

	}
	
}