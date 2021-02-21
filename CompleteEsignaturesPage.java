package term_conversion;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class CompleteEsignaturesPage {

	private DriverUtil driverUtil;

	// --------------------------------------Complete eSignatures page
	// Xpaths-------------------------------------
	public String esignWithClientNowLabelId = "form-title";
	private String h1PageTitleXPath = "//h1[@id = 'page-title']";
	public String eSignColumnNameXpath = "//p[@id='owner-panel-sex']";
	public String esignNowButtonXpath = "//button[contains(text(),'eSign now')]";

	public String checkboxDocuSignXpath = "//input[@id='disclosureAccepted']";
	public String continueDocuSignXpath = "//button[contains(text(),'Continue')]";
	public String signButtonDocuSignXpath = "//button[@id='navigate-btn']/span";

	public String signatureButtonDocuSignXpath = "//div[@class='signature-tab-content']";
	public String adoptAndSignBuuton = "//button[contains(text(),'Adopt and Sign')]";
	public String finishButtonDocuSignXpath = "//button[@id='action-bar-btn-finish']";
	public String validateButtonDocuSignXpath = "//button[contains(text(),'Validate')]";
	public String accessCodeDocuSignXpath = "//input[@id='ds_hldrBdy_txtAccessCode']";

	String eSignedTextXpath = "//p[contains(text(),'%s')]/../../../../parent::div/div[2]/div/div/span";
	public String signedAtCityOwnerXpath = "//input[starts-with(@id,'tab-form-element')]";

	public boolean getSignedAtCityOwner(String cityName) {
		List<WebElement> list = this.driverUtil.getWebElements(By.xpath(signedAtCityOwnerXpath));
		if (list.size() > 0) {
			list.get(0).sendKeys(cityName);
			return true;
		}
		return false;
	}

	public WebElement getEsignedLabel(String name) {
		String useXapth = formatXpath(eSignedTextXpath, name);
		return this.driverUtil.getWebElement(By.xpath(useXapth));
	}

	public CompleteEsignaturesPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}

	public WebElement getAccessCodeElement(WebDriver driver) throws InterruptedException {

		Thread.sleep(8000);
		WebElement accessCode = this.driverUtil.waitForElementToBeAppear(By.xpath(accessCodeDocuSignXpath));

		return accessCode;
	}

	public WebElement getValidateButton() {

		return this.driverUtil.getWebElement(By.xpath(validateButtonDocuSignXpath));
	}

	public boolean errorMessage() {
		return this.driverUtil.getWebElements(By.xpath("//*[@id='ds_hldrBdy_lblErrorMessageAccessCode']")).size() == 1;
	}

	public WebElement getEsignCheckBox(WebDriver driver) throws InterruptedException {

		WebElement esignCheckBox = this.driverUtil.waitForElementToBeAppear(By.xpath(checkboxDocuSignXpath));
		return esignCheckBox;
	}

	public WebElement getContinuButton() {

		return this.driverUtil.getWebElement(By.xpath(continueDocuSignXpath));
	}

	public WebElement getNextButton() {

		return this.driverUtil.getWebElement(By.xpath(signButtonDocuSignXpath));
	}

	public WebElement getSingnatureButton() throws InterruptedException {

		return this.driverUtil.getWebElement(By.xpath(signatureButtonDocuSignXpath));
	}

	public void ClickOnAdoptButton() throws InterruptedException {
		Thread.sleep(1000);
		List<WebElement> element = this.driverUtil.getWebElements(By.xpath(adoptAndSignBuuton));

		if (element.size() > 0) {
			element.get(0).click();
		}
		Thread.sleep(2000);
	}

	public WebElement getFinishButton() throws InterruptedException {

		return this.driverUtil.getWebElement(By.xpath(finishButtonDocuSignXpath));
	}

	public void clickOnESignNowButton(int InsuredIndex) {

		getESignButton(InsuredIndex).click();
	}

	public boolean CheckEsignButtonIsDisabled(int InsuredIndex) {

		boolean result = false;
		try {
			String value = getESignButton(InsuredIndex).getAttribute("disabled");
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;

	}

	public WebElement getESignButton(int InsuredIndex) {
		WebElement button = null;
		List<WebElement> esignButtons = this.driverUtil.getWebElements(By.xpath(esignNowButtonXpath));
		if (esignButtons.size() > 0) {
			button = this.driverUtil.getWebElements(By.xpath(esignNowButtonXpath)).get(InsuredIndex);
			return button;
		} else {
			throw new NoSuchElementException("Unable to find any element using xpath" + esignNowButtonXpath);
		}

	}

	public String formatXpath(String value, int index) {

		String xpath = String.format(value, Integer.toString(index));
		return xpath;
	}

	public void VerifyESignCompleteFromTitle(String expectedEsignWithClientLable, int formNumber) {
		String actualEsignWithClientLable = this.driverUtil.getWebElements(By.id(esignWithClientNowLabelId))
				.get(formNumber).getText();
		Assert.assertTrue(
				"Fail:We are expecting (" + expectedEsignWithClientLable + ") but found as ("
						+ actualEsignWithClientLable + ")",
				actualEsignWithClientLable.equalsIgnoreCase(expectedEsignWithClientLable));

	}

	public void VerifyPageTitle(WebDriver driver, String expectedTitle) throws InterruptedException {

		Thread.sleep(12000);
		String actualPageTitle = getPageTitle().getText();

		Assert.assertTrue("Fail:We are expecting complete eSignatures  page title as  (" + expectedTitle
				+ ") but found as (" + actualPageTitle + ")", actualPageTitle.equalsIgnoreCase(expectedTitle));
	}

	public void waitUntilElementNotDisplayed(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='inProcess']/div/span[2]")));
	}

	public void waitUntilElementDisplayed(WebElement element, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement getPageTitle() {
		return this.driverUtil.getWebElement(By.xpath(h1PageTitleXPath));
	}

	public String WaitFroElementIsVisiable(int timeoutInSecond, WebDriver driver, WebElement elementXpath) {

		try {
			(new WebDriverWait(driver, timeoutInSecond)).until(ExpectedConditions.visibilityOf(elementXpath));
			return null;
		} catch (TimeoutException e) {
			return "Build your own errormessage...";
		}

	}

	public void verifyInsuredName(String ExpectedInsuredName, int InsuredIndex) {

		String actualInsuredName = this.driverUtil.getWebElements(By.xpath(eSignColumnNameXpath)).get(InsuredIndex)
				.getText();
		Assert.assertTrue("Fail:We are expecting insured name as (" + ExpectedInsuredName + ") but found as ("
				+ actualInsuredName + ")", actualInsuredName.equalsIgnoreCase(ExpectedInsuredName));

	}

	public void verifyEsignNowButtonLable(String Expectedesignnowbtnlable, int InsuredIndex) {

		String actualesignnowbtnlable = this.driverUtil.getWebElements(By.xpath(esignNowButtonXpath)).get(InsuredIndex)
				.getText();
		Assert.assertTrue(
				"Fail:We are expecting eSign now button lable as (" + Expectedesignnowbtnlable + ") but found as ("
						+ actualesignnowbtnlable + ")",
				actualesignnowbtnlable.equalsIgnoreCase(Expectedesignnowbtnlable));

	}

	public String formatXpath(String value, String name) {

		String xpath = String.format(value, name);
		return xpath;
	}

}
