package term_conversion;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class BillingInformationPage {
	private DriverUtil driverUtil;
	
	//XPath
	private String ulFirstPaymentAmountXPath = "//ul[@id = 'amount-first-payment-listbox']";
	private String divFirstPaymentAmountXPath = "//div[@id = 'amount-first-payment-value']";
	private String divFirstPaymentMethodXPath = "//fieldset[@id = 'first-payment-methid']";
	private String divPaymentFrequencyXPath = "//fieldset[@id = 'payment-frequency']";
	private String divWithdrawalDebitPlanXPath = "//div[@id = 'withdrawal-note']";
	private String divFirstPaymentErrorXPath = "//div[@id = 'first-payment--error']/span";
	private String divAmountFirstPaymentErrorXPath = "//div[@id = 'amount-first-payment-errors']/div";
	private String divActualAmountFirstPaymentErrorXPath = "//div[@id = 'actual-amount-first-payment-errors']/div";
	private String divFirstPaymentMethodErorrXPath = "//div[@id = 'first-payment-methid-errors']/div";
	private String divPADErrorXPath = "//div[@id = 'pad-policy-number-errors']/div";
	private String divTransitErrorXPath = "//div[@id = 'transit-number-errors']/div";
	private String divInstitutionErrorXPath = "//div[@id = 'institution-number-errors']/div";
	private String divAccountErrorXPath = "//div[@id = 'account-number-errors']/div";
	private String divFrequencyErrorXPath = "//div[@id ='payment-frequency-errors']/div";
	private String divMonthlyAnnuallyErrorXPath = "//div[@id = 'amount-first-payment-errors']/div";
	private String divCoverCostErrorXPath = "//div[@id = 'amt-covering-policy-cost-errors']";
	private String divPayPremiumErrorXPath = "//div[@id = 'premium-payers-checkbox-group-errors']";
	private String inputFirstPaymentValueXPath = "//input[@id = 'actual-amount-first-payment']";
	private String inputPADPolicyNumber = "//input[@id = 'pad-policy-number']";
	private String inputBankNumberXPath = "//input[@id = 'bank-name']";
	private String inputTransitNumberXPath = "//input[@id = 'transit-number']";
	private String inputInstitutionNumberXPath = "//input[@id = 'institution-number']";
	private String inputAccountNumberXPath = "//input[@id = 'account-number']";
	private String inputMonthlyAnnuallyXPath = "//input[@id = 'amount-of-monthly-or-yearly']";
	private String inputCoverPolicyCostsAmount = "//input[@id = 'amt-covering-policy-cost']";
	private String inputFirstPaymentYesXPath = "//button[@id = 'first-payment-0']";
	private String inputFirstPaymentNoXPath = "//button[@id = 'first-payment-1']";
	
	private String labelFirstPaymentYesXPath = "//button[starts-with(@id,'first-payment') and contains(text(),'Yes')]";	
	
	private String labelFirstPaymentNoXPath = "//button[starts-with(@id,'first-payment') and contains(text(),'No')]";
	
	private String labelMonthlyAnnuallyXPath = "//label[@for = 'amount-of-monthly-or-yearly']";
	private String labelWhoPaysPremiumsXPath = "//label[@id = 'who-pays-premiums-label']";
	private String labelBankNumberXPath = "//label[contains(text(),'Provide the banking') or contains(text(),'Fournir les renseignements')]";
	private String labelFirstPaymentAmountXPath = "//label[@id = 'amount-first-payment-label']";
	private String labelFirstPaymentMethodXPath = "//label[@id = 'first-payment-methid-label']";
	private String labelPaymentMethodXPath = "//label[@id = 'payment-method-label']";
	private String labelCoverPolicyCostsAmount = "//label[@for = 'amt-covering-policy-cost']";
	private String labelPADPolicyNumber = "//label[@for = 'pad-policy-number']";
	private String legendFirstPaymentXPath = "//legend[@id = 'first-payment-label']";
	private String legendPaymentFrequencyXPath = "//fieldset[@id = 'payment-frequency']/legend";
	private String pWhoPaysPremiumsXPath = "//p[@id = 'who-pays-premiums']";
	private String pFirstPaymentXPath = "//p[@id = 'first-payment-methid']";
	private String pPaymentMethodXPath = "//p[@id = 'payment-method']";
	private String fieldsetPremiumPayersGroupXPath = "//fieldset[@id = 'premium-payers-checkbox-group']";
	private String h2BillingInfoSectionTitleXPath = "(//h2[@id = 'form-title'])[1]";
	private String h2PaymentInfoSectionTitleXPath = "(//h2[@id = 'form-title'])[2]";
	private String h3RegularPaymentsXPath = "//h3[@id = 'regular-payments']";
	private String bFirstPaymentLabelXPath = "//div[..//div/fieldset/legend[@id = 'first-payment-label']]/b";
	
	public BillingInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	//Elements
	public WebElement getPremiumPayer(String premiumPayer) {
		return this.driverUtil.getWebElement(By.xpath(fieldsetPremiumPayersGroupXPath)).findElement(By.xpath("//input[@id = '"+premiumPayer+"']"));
	}
	
	public void  SelectOwner(int numberOfOwner){
		List<WebElement> allOwner=  this.driverUtil.getWebElements(By.xpath("//fieldset[@id='premium-payers-checkbox-group']/div/span/input"));
		int ownerCount =allOwner.size();
		
		if(ownerCount >1)
		{			
			allOwner.get(numberOfOwner).click();
		}else
		{
			throw new ElementNotVisibleException("We did not find any ouwner using xpath");
		}
	}
	
	public WebElement getFirstPaymentBln(String str) {
		String useXPath = (str.equals("Yes")) ?  labelFirstPaymentYesXPath: (str.equals("No")) ? labelFirstPaymentNoXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getFirstPaymentInputBln(String str) {
		String useXPath = (str.equals("Yes")) ?  inputFirstPaymentYesXPath: (str.equals("No")) ? inputFirstPaymentNoXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public void selectFirstPaymentAmount(String amountOption) {
		this.driverUtil.click(By.xpath(divFirstPaymentAmountXPath));
		this.driverUtil.getWebElement(By.xpath(ulFirstPaymentAmountXPath)).findElement(By.xpath("//li[contains(text(),'"+amountOption+"')]")).click();
	}
	
	public String getSelectedFirstPaymentAmount() {
		return this.driverUtil.getText(By.xpath(divFirstPaymentAmountXPath));
	}
	
	public WebElement getFirstPaymentValue() {
		return this.driverUtil.getWebElement(By.xpath(inputFirstPaymentValueXPath));
	}
	
	public void selectFirstPaymentMethod(String paymentMethod) {
		this.driverUtil.getWebElement(By.xpath(divFirstPaymentMethodXPath)).findElement(By.xpath("//input[@aria-label = '"+paymentMethod+"']")).click();
	}
	
	public WebElement getPADPolicyNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputPADPolicyNumber));
	}
	
	public WebElement getBankName() {
		return this.driverUtil.getWebElement(By.xpath(inputBankNumberXPath));
	}
	
	public WebElement getTransitNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputTransitNumberXPath));
	}
	public void ClearTextBox(WebDriver driver, WebElement element) {

		Actions act = new Actions(driver);
		act.click(element).pause(50).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).pause(50)
				.sendKeys(Keys.BACK_SPACE).build().perform();

	}
	public WebElement getInstitutionNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputInstitutionNumberXPath));
	}
	
	public WebElement getAccountNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputAccountNumberXPath));
	}
	
	public WebElement getFrequencyPayment(String frequency) {
		return this.driverUtil.getWebElement(By.xpath(divPaymentFrequencyXPath)).findElement(By.xpath("//input[@aria-label = '"+frequency+"']"));
	}
	
	public WebElement getMonthlyAnnuallyPremiumAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputMonthlyAnnuallyXPath));
	}
	
	public WebElement getCoverPolicyCostsAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputCoverPolicyCostsAmount));
	}
	
	//Section Titles
	public String getBillingInfoSectionTitle() {
		return this.driverUtil.getText(By.xpath(h2BillingInfoSectionTitleXPath));
	}
	
	public String getPaymentInfoSectionTitle() {
		return this.driverUtil.getText(By.xpath(h2PaymentInfoSectionTitleXPath));
	}
	
	//Pay Premium Labels
	public String getPayPremiumsQuestionLabel() {
		return this.driverUtil.getText(By.xpath(labelWhoPaysPremiumsXPath));
	}
	
	public String getPayPremiumsParagraphLabel() {
		return this.driverUtil.getText(By.xpath(pWhoPaysPremiumsXPath));
	}
	
	//First Payment Labels
	public String getFirstPaymentTitle() {
		return this.driverUtil.getText(By.xpath(bFirstPaymentLabelXPath));
	}
	
	public String getFirstPaymentBlnLabel() {
		return this.driverUtil.getText(By.xpath(legendFirstPaymentXPath));
	}
	
	public String getFirstPaymentAmountLabel() {
		return this.driverUtil.getText(By.xpath(labelFirstPaymentAmountXPath));
	}
	
	public String getFirstPaymentMethodLabel() {
		return this.driverUtil.getText(By.xpath(labelFirstPaymentMethodXPath));
	}
	
	public String getFirstPaymentMethodValueLabel() {
		return this.driverUtil.getText(By.xpath(pFirstPaymentXPath));
	}
	
	public String getPADPlanLabel() {
		return this.driverUtil.getText(By.xpath(labelPADPolicyNumber));
	}
	
	public String getBankingInfoLabel() {
		return this.driverUtil.getText(By.xpath(labelBankNumberXPath));
	}
	
	public String getBankNameLabel() {
		return this.driverUtil.getWebElement(By.xpath(inputBankNumberXPath)).getAttribute("placeholder");
	}
	
	public String getTransitNumberLabel() {
		return this.driverUtil.getWebElement(By.xpath(inputTransitNumberXPath)).getAttribute("placeholder");
	}
	
	public String getInstitutionNumberLabel() {
		return this.driverUtil.getWebElement(By.xpath(inputInstitutionNumberXPath)).getAttribute("placeholder");
	}
	
	public String getAccountNumberLabel() {
		return this.driverUtil.getWebElement(By.xpath(inputAccountNumberXPath)).getAttribute("placeholder");
	}
	
	public String[] getFirstPaymentAmountOptions() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divFirstPaymentAmountXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulFirstPaymentAmountXPath,"li");
		this.driverUtil.click(By.xpath(divFirstPaymentAmountXPath));
		return optionLabels;
	}
	
	public String[] getFirstPaymentMethodOptions() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divFirstPaymentMethodXPath,"label");
	}
	
	//Regular Payments Label
	public String[] getFrequencyOfPaymentsOptions() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getOptionLabels(divPaymentFrequencyXPath,"label");
	}
	
	public String getRegularPaymentsLabel() {
		return this.driverUtil.getText(By.xpath(h3RegularPaymentsXPath));
	}
	
	public String getPaymentMethod() {
		return this.driverUtil.getText(By.xpath(pPaymentMethodXPath));
	}
	
	public String getPaymentMethodLabel() {
		return this.driverUtil.getText(By.xpath(labelPaymentMethodXPath));
	}
	
	public String getPremiumAmountLabel() {
		return this.driverUtil.getText(By.xpath(labelMonthlyAnnuallyXPath));
	}
	
	public String getFrequencyPaymentsLabel() {
		return this.driverUtil.getText(By.xpath(legendPaymentFrequencyXPath));
	}
	
	public String getWithdrawalNote() {
		return this.driverUtil.getText(By.xpath(divWithdrawalDebitPlanXPath));
	}
	
	public String getCoverPolicyCostsAmountLabel() {
		return this.driverUtil.getText(By.xpath(labelCoverPolicyCostsAmount));
	}
	
	//Is Visible
	public boolean isPremiumAmountVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputMonthlyAnnuallyXPath));
	}
	
	public boolean isPADPolicyNumberVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputPADPolicyNumber));
	}
	
	public boolean isCanadianBankVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputBankNumberXPath));
	}
	
	public boolean isTransitNumberVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputTransitNumberXPath));
	}
	
	public boolean isInstitutionNumberVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputInstitutionNumberXPath));
	}
	
	public boolean isAccountNumberVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputAccountNumberXPath));
	}
	
	public boolean isWithdrawalNoteVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(divWithdrawalDebitPlanXPath));
	}
	
	public boolean isPremiumPayerNameVisible(String insuredName) {
		return this.driverUtil.getWebElement(By.xpath(fieldsetPremiumPayersGroupXPath)).findElements(By.xpath("//label[text() ='"+insuredName+"']")).size() > 0;
	}
	
	public boolean isPremiumPayerInputVisible(String insuredName) {
		return this.driverUtil.getWebElement(By.xpath(fieldsetPremiumPayersGroupXPath)).findElements(By.xpath("//input[@id = '"+insuredName+"']")).size() > 0;
	}
	
	//Errors
	public String getFirstPaymentError() {
		return this.driverUtil.getText(By.xpath(divFirstPaymentErrorXPath));
	}
	
	public String getAmountPaymentError() {
		return this.driverUtil.getText(By.xpath(divAmountFirstPaymentErrorXPath));
	}
	
	public String getActualAmountError() {
		return this.driverUtil.getText(By.xpath(divActualAmountFirstPaymentErrorXPath));
	}
	
	public String getPaymentMethodError() {
		return this.driverUtil.getText(By.xpath(divFirstPaymentMethodErorrXPath));
	}
	
	public String getPADError() {
		return this.driverUtil.getText(By.xpath(divPADErrorXPath));
	}
	
	public String getTransitError() {
		return this.driverUtil.getText(By.xpath(divTransitErrorXPath));
	}
	public boolean getTransitErrorIsDisplayed() {
		return this.driverUtil.getWebElement(By.xpath(divTransitErrorXPath)).isDisplayed();
	}
	public boolean getInstitutionErrorIsDisplayed() {
		return this.driverUtil.getWebElement(By.xpath(divInstitutionErrorXPath)).isDisplayed();
	}
	public boolean getAccountErrorIsDisplayed() {
		return this.driverUtil.getWebElement(By.xpath(divAccountErrorXPath)).isDisplayed();
	}
	
	public String getInstitutionError() {
		return this.driverUtil.getText(By.xpath(divInstitutionErrorXPath));
	}
	
	public String getAccountError() {
		return this.driverUtil.getText(By.xpath(divAccountErrorXPath));
	}
	
	public String getFrequencyError() {
		return this.driverUtil.getText(By.xpath(divFrequencyErrorXPath));
	}
	
	public String getMonthlyAnnuallyAmountError() {
		return this.driverUtil.getText(By.xpath(divMonthlyAnnuallyErrorXPath));
	}
	
	public String getCoverCostError() {
		return this.driverUtil.getText(By.xpath(divCoverCostErrorXPath));
	}
	
	public String getPayPremiumError() {
		return this.driverUtil.getText(By.xpath(divPayPremiumErrorXPath));
	}
	
}