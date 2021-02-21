package term_conversion;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class ESignPage {
	private DriverUtil driverUtil;

	// Xpaths
	String getOwnerCountXapth = "//div[@id='esign-application']/div/div[2]/div";
	String eSignMethodLabelXpath = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div/div/div/fieldset/legend";
	String eSignClientNowLabelXpath = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div/div/div/fieldset/div[1]/div[1]/label";
	String eSignByEmailNowLabelXpath = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div/div/div/fieldset/div[1]/div[2]/label";

	
String eSignClientNowXpath="//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div/div/div/fieldset/div[1]/div[1]/input";
String eSignByEmailXpath="//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div/div/div/fieldset/div[1]/div[2]/input";

	String emailIDInputboxXapth = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div[2]/div/div/div/div[1]/input";
	String emailILabelXapth = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div[2]/div/div/div/label";

	String SecondEmailIDInputboxXapth = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div[3]/div/div/div/div/input";
	String SecondEmailILabelXapth = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div[3]/div/div/div[1]/label";

	// Form starting from 1 to 3

	String emailPasswordInputXapth = "//input[@id='eSign-code-form%s']";
	String emailPasswordILabelXapth = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div[2]/div/div/div[1]/label";

	String advancePasswordInputID = "eSign-adv-pwd";
	String advanceConfirmInputID = "eSign-adv-cfm-pwd";

	String advancePasswordLabelXapth = "//label[@for='eSign-adv-pwd']";
	String advanceConfirmLabelXpath = "//label[@for='eSign-adv-cfm-pwd']";

	String showPasswordLinkXapth = "//input[@id='eSign-adv-pwd']/following-sibling::a";
	String showConfirmPasswordXpath = "//input[@id='eSign-adv-cfm-pwd']/following-sibling::a";

	// Required field validation
	String  eSignMethodError="//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div/div/div/fieldset/div[2]/div";
	String emailIDDivRequiredFieldXpath = "//h2[contains(text(),'%s')]/parent::div/following-sibling::div[2]/div[3]/div/div/div[2]/div[2]/div";
	String advancePasswordRequiredFieldXpath = "//div[@id='eSign-adv-pwd-errors']/div";
	String advanceConfirmPasswordRequiredFieldXpath = "//div[@id='eSign-adv-cfm-pwd-errors']/div";
	
	
	
	

	public ESignPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}

	
	public  WebElement  getEsignMethodError(String name)
	{
		String useXapth = formatXpath(eSignMethodError, name);
		return this.driverUtil.getWebElement(By.xpath(useXapth));
	}
	
	
	
	public void VerifyInsuredName(int InsuredIndex, String ownexpectedInsuredName) {

		String actInsuredName = this.driverUtil.getWebElements(By.xpath(getOwnerCountXapth)).get(InsuredIndex)
				.findElement(By.tagName("h2")).getText();
		
		Assert.assertTrue("Fail:We are expecting ("+ownexpectedInsuredName+") but found insured name as ("+actInsuredName+")", actInsuredName.equalsIgnoreCase(ownexpectedInsuredName));
		 
	}

	public void clickOnESignCheckBox(String name) {
		String useXapth = formatXpath(eSignClientNowXpath, name);
		this.driverUtil.getWebElement(By.xpath(useXapth)).click();
	}

	public void clickOnESignEmailCheckBox(String name) {
		String useXapth = formatXpath(eSignByEmailXpath, name);
		this.driverUtil.getWebElement(By.xpath(useXapth)).click();
	}

	
	
	
	public void EnterEmailID(String name, String yourEmailId,String options) {
		
		
		String orignal = (options.equals("eSign with client now")) ?  emailIDInputboxXapth: (options.equals("eSign by email later")) ? SecondEmailIDInputboxXapth : "Error";
		String useXapth = formatXpath(orignal, name);
		this.driverUtil.getWebElement(By.xpath(useXapth)).sendKeys(yourEmailId);
	}
	public boolean EnterAdvancePassword(String password) {		
		getAdvancePasswordElement().sendKeys(password);
		return true ;
	}
	public WebElement getAdvancePasswordElement() {		
		return this.driverUtil.getWebElement(By.id(advancePasswordInputID));
		
	}
	public WebElement getAdvanceConfirmPasswordElement() {		
		return this.driverUtil.getWebElement(By.id(advanceConfirmInputID));
		
	}
	
	public boolean EnterConfirmPassword(String confirmPassword) {		
		getAdvanceConfirmPasswordElement().sendKeys(confirmPassword);
		return true ;
	}
	public void ClearTextBox(WebDriver driver, WebElement element) {

		Actions act = new Actions(driver);
		act.click(element).pause(50).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).pause(50)
				.sendKeys(Keys.BACK_SPACE).build().perform();

	}

	// label validation

	public void VerifyPageTitle(String expectedTitle)
	{
		CommonElements commonElements = new CommonElements(driverUtil);		
		String  actualPageTitle =commonElements.getPageTitle();
		Assert.assertTrue("Fail:We are expecting page title as  ("+expectedTitle+") but found as ("+actualPageTitle+")", actualPageTitle.equalsIgnoreCase(expectedTitle));
	}
	public void VerifyEsignMethodLabel(String name, String expectedLabel) {
		String useXapth = formatXpath(eSignMethodLabelXpath, name);
		String actul = this.driverUtil.getWebElement(By.xpath(useXapth)).getText();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel));
	}

	public void VerifyESignClinetNowLabel(String name, String expectedLabel) {
		String useXapth = formatXpath(eSignClientNowLabelXpath, name);
		String actul = this.driverUtil.getWebElement(By.xpath(useXapth)).getText();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel));
	}

	public void VerifyEsignEmailLabel(String name, String expectedLabel) {
		String useXapth = formatXpath(eSignByEmailNowLabelXpath, name);

		String actul = this.driverUtil.getWebElement(By.xpath(useXapth)).getText();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel));	}

	public void VerifyEmailIDLabel(String name, String expectedLabel) {
		String useXapth = formatXpath(emailILabelXapth, name);

		String actul = this.driverUtil.getWebElement(By.xpath(useXapth)).getText();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel));
	}

	public void VerifyPasswordLabel(String name, String expectedLabel) {
		String useXapth = formatXpath(emailPasswordILabelXapth, name);

		String actul = this.driverUtil.getWebElement(By.xpath(useXapth)).getText();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel));
	}

	public void VerifyAdvancePasswordLabel(String expectedLabel) {

		String actul = this.driverUtil.getWebElement(By.xpath(advancePasswordLabelXapth)).getText().trim();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel.trim()));
	}

	public void VerifyAdvanceConfirmPasswordLabel(String expectedLabel) {

		String actul = this.driverUtil.getWebElement(By.xpath(advanceConfirmLabelXpath)).getText().trim();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel.trim()));
	}
	// Verify the Required Error Message

	public void VerifyRequiredErrorMessageForEmail(String name, String expectedLabel) {
		String useXapth = formatXpath(emailIDDivRequiredFieldXpath, name);

		String actul = this.driverUtil.getWebElement(By.xpath(useXapth)).getText();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel));
	}

	public void VerifyRequiredErrorMessageForAdvancePassword(String expectedLabel) {

		String actul = this.driverUtil.getWebElement(By.xpath(advancePasswordRequiredFieldXpath)).getText();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel));

	}

	public void VerifyRequiredErrorMessageForAdvanceConfirmPassword(String expectedLabel) {
		String actul = this.driverUtil.getWebElement(By.xpath(advanceConfirmPasswordRequiredFieldXpath)).getText();
		Assert.assertTrue("Fail:We are expecting ("+expectedLabel+") but found as ("+actul+")", actul.equalsIgnoreCase(expectedLabel));

	}

	public String formatXpath(String value, String name) {

		String xpath = String.format(value, name);
		return xpath;
	}

}