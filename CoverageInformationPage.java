package term_conversion;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class CoverageInformationPage {
	private DriverUtil driverUtil;
	
	//XPath
	private String h1PageTitleXPath = "//h1[@id = 'page-title']";
	private String h2FormTitleXPath = "(//h2[@id = 'form-title'])[1]";
	private String buttonCoverageNumberToolTipXPath = "//button[@id = 'coverage-num-tooltip-toggle']";
	private String divCommonAmountErrorXPath = "//div[@id = 'common-amount-error']";
	private String divAdminRulesNoteXPath = "//div[@id = 'ChangeOwnerExistingPolicyNote']";
	private String divTaxableGainNoteXPath = "(//div[./div/div[@id = 'ChangeOwnerExistingPolicyNote']]/div)[2]";
	private String divToolTipXPath = "//span[@role = 'tooltip']";
	private String divCoverageNumberErrorXPath = "//div[@id = 'coverage-person-number-errors']";
	private String divCoverageAmountErrorXPath = "//div[@id = 'coverage-amount-errors']";
	private String divConvertingAmountErrorXPath = "//div[@id = 'converting-amount-errors']";
	private String labelCoverageNumberLabelXPath = "//label[@for = 'coverage-person-number']";
	private String labelCoverageAmountLabelXPath = "//label[@for = 'coverage-amount']";
	private String labelConvertingAmountLabelXPath = "//label[@for = 'converting-amount']";
	private String labelCarriedAmountLabelXPath = "//label[@for = 'carried-amount']";
	private String labelKeepAmountLabelXPath = "//label[@for = 'keep-amount']";
	private String labelRemainingAmountLabelXPath = "//div[./span[@id = 'remaining-amount-label']]";
	private String inputCoverageNumberXPath = "//input[@id = 'coverage-person-number']";
	private String inputCoverageAmountXPath = "//input[@id = 'coverage-amount']"; 
	private String inputConvertingAmountXPath = "//input[@id = 'converting-amount']";
	private String inputCarriedTIRAmountXPath = "//input[@id = 'carried-amount']";
	private String inputKeepAmountXPath = "//input[@id = 'keep-amount']";
	private String spanRemainingAmountXPath = "//span[@id = 'remaining-amount-amount-label']";
	
	public CoverageInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	//Get WebElements
	public WebElement getFormNameTitle() {
		return this.driverUtil.getWebElement(By.xpath(h2FormTitleXPath));
	}
	
	public WebElement getCoverageNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputCoverageNumberXPath));
	}
	
	public WebElement getCoverageAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputCoverageAmountXPath));
	}
	
	public WebElement getConvertingAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputConvertingAmountXPath));
	}
	
	public WebElement getCarriedTIRAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputCarriedTIRAmountXPath));
	}
	public void ClearTextBox(WebDriver driver, WebElement element) {

		Actions act = new Actions(driver);
		act.click(element).pause(50).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).pause(50)
				.sendKeys(Keys.BACK_SPACE).build().perform();

	}
	
	public boolean isCarriedTIRAmountVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputCarriedTIRAmountXPath));
	}
	

	
	public WebElement getKeepAmount() {
		return this.driverUtil.getWebElement(By.xpath(inputKeepAmountXPath));
	}
	
	//Label Displayed
	public String getPageTitle() {
		return this.driverUtil.getWebElement(By.xpath(h1PageTitleXPath)).getText();
	}
	
	public String getCoverageNumberLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelCoverageNumberLabelXPath)).getText();
	}
	
	public String getCoverageAmountLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelCoverageAmountLabelXPath)).getText();
	}
	
	public String getConvertingAmountLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelConvertingAmountLabelXPath)).getText();
	}
	
	public String getCarriedAmountLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelCarriedAmountLabelXPath)).getText();
	}
	
	public String getKeepAmountLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelKeepAmountLabelXPath)).getText();
	}
	
	public String getRemainingAmountLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelRemainingAmountLabelXPath)).getText().replace("\n"," ");
	}
	
	public String getAdminRulesNote() {
		return this.driverUtil.getWebElement(By.xpath(divAdminRulesNoteXPath)).getText();
	}
	
	public String getTaxableGainNote() {
		return this.driverUtil.getWebElement(By.xpath(divTaxableGainNoteXPath)).getText();
	}
	
	public String getCoverageNumberTipLabel() {
		this.driverUtil.hoverWebElement(By.xpath(buttonCoverageNumberToolTipXPath));
		return this.driverUtil.waitForElementToBeVisible(By.xpath(divToolTipXPath)).getText();
	}
	
	public String getRemainingAmountText() {
		return this.driverUtil.getText(By.xpath(spanRemainingAmountXPath));
	}
	
	//Errors Displayed
	public String getCommonAmountErrorText() {
		String errorMessage = (this.driverUtil.checkElementVisible(By.xpath(divCommonAmountErrorXPath),5))? this.driverUtil.getText(By.xpath(divCommonAmountErrorXPath)) : "";
		return errorMessage;
	}
	
	public String getCoverageNumberErrorText() {
		return this.driverUtil.getText(By.xpath(divCoverageNumberErrorXPath));
	}
	
	public String getCoverageAmountErrorText() {
		return this.driverUtil.getText(By.xpath(divCoverageAmountErrorXPath));
	}
	
	public String getConvertingAmountNumberErrorText() {
		return this.driverUtil.getText(By.xpath(divConvertingAmountErrorXPath));
	}
	private String startApplicationXpath="//button[@id='start-new-application-button']";;
	public void startApplication() {
		this.driverUtil.getWebElement(By.xpath(startApplicationXpath)).click();
	}
		
}