package term_conversion;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class ThirdPartyInformationPage {

	private DriverUtil driverUtil;
	

	
	
	//button[contains(@id,'policy-other-payor') and (text()='No')] 
	
	String pageTitleId = "page-title";
	String pageTitleOfInsuredPageXpath = "//h1[@id='page-title']/span";
	String anyoneOtherThanThePolicyOwnerBePayingYesXpath = "//button[contains(@id,'policy-other-payor-id-0')]";

	String anyoneOtherThanThePolicyOwnerBePayingNoXpath = "//button[contains(@id,'policy-other-payor-id-1')]";

	String anyoneOtherThanThePolicyOwnerBePayingLabelId = "policy-other-payor-id-label";

	String ownerIntendToTransferTheOwnershipLabelId = "ownership-id-label";

	String ownerIntendToTransferTheOwnershipYesXpath = "//button[contains(@id,'ownership-id-0')]";

	String ownerIntendToTransferTheOwnershipNoXpath = "//button[contains(@id,'ownership-id-1')]";

	String someoneElseWhoHasIndirectControlLabelId = "control-id-label";

	String someoneElseWhoHasIndirectControlYesXpath = "//button[contains(@id,'control-id-0')]";

	String someoneElseWhoHasIndirectControlNoXpath = "//button[contains(@id,'control-id-1')]";

	String thirdPartiesNextBtnId = "next-btn";
	String languageToggleBtnId = "language-toggle-button";
	String anyoneOtherThanThePolicyOwnerBePayingYesOptionSelectedNoteXpath = "//div[@id='policy-other-payor-note']/div[2]";

	String anyoneOtherThanThePolicyOwnerBePayingYesOptionSelectedLinkXpath = "//div[@id='policy-other-payor-note']/div[2]/div/a";

	// *********** Third party information Sub Section ****//

	String subSectionThirdPartyInformationTitleXpath = "//span[contains(text(),'Third party information')]";

	String legalEntityFieldCssSelector = "button[value='LegalEntity']";

	String individualFieldCssSelector = "button[value='Individual']";

	String firstNameFieldCssSelector = "input[id='0-third-party-name-firstName']";

	String middleInitialFieldCssSelector = "input[id='0-third-party-name-middleName']";

	String lastNameFieldCssSelector = "input[id='0-third-party-name-lastName']";

	String dayFieldCssSelector = "input[id='undefined-day']";

	String monthDropdownCssSelector = "button[id='undefined-month-toggle']";
	String ulMonthDropDownXPath = "//ul[contains(@id,'undefined-month-listbox')]";
	String ulRelationshipDropDownXPath = "//ul[contains(@id,'relationToInsured-listbox')]";
	String ulProvinceDropDownXPath = "//ul[contains(@id,'province-listbox')]";

	String yearFieldCssSelector = "input[id='undefined-year']";

	String relationshipToTheOwnerDropDownCssSelector = "button[id='relationToInsured-toggle']";

	String relationshipToTheOwnerOtherTextboxCssSelector = "input[id='0-third-party-other-relation']";

	String address1CssSelector = "input[id='address1']";

	String address2CssSelector = "input[id='address2']";

	String cityOrTownTextBoxCssSelector = "input[id='cityTown']";
	String postalCodeTextBoxCssSelector = "input[id='postalCode']";
	String provinceDropdownCssSelector = "button[id='province-toggle']";

	String tooltipCssSelector = "button[id='control-tooltip-toggle']";
	String tooltipDivXpath = "//span[@id='control-tooltip-wrapper']";
	// ********* End ***************************************//

	// Third party information subsection error messages validation
	String firstNameErrorLableXpath = "//div[@id='0-third-party-name-firstName-errors']/div";
	String lastNameErrorLableXpath = "//div[@id='0-third-party-name-lastName-errors']/div";
	String dobErrorLableXpath = "//div[@id='undefined-error']/span";
	String relationshipToTheOwnerErrorLableXpath = "//div[@id='relationToInsured-errors']/div";
	String relationshipToTheOwnerOtherErrorLableXpath = "//div[@id='0-third-party-other-relation-errors']/div";
	String address1ErrorLableXpath = "//div[@id='address1-errors']/div";
	String cityOrTownErrorLableXpath = "//div[@id='cityTown-errors']/div";
	String provinceErrorLableXpath = "//div[@id='province-errors']/div";
	String postalcodeErrorLableXpath = "//div[@id='postalCode-errors']/div";
	String anyoneOtherThanPolicyOwnerPayingOptionsErrorLableXpath = "//div[@id='policy-other-payor-id--error']/span";
	String ownerIntendToTransferOwnershipOptionsErrorLableXpath = "//div[@id='ownership-id--error']/span";
	String someoneElseWhoHasIndirectControlOptionsErrorLableXpath = "//div[@id='control-id--error']/span";
	
	private String startApplicationId = "start-new-application-button";
	

	public ThirdPartyInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	public void startApplication() {
		this.driverUtil.getWebElement(By.id(startApplicationId)).click();
	}

	public WebElement getAnyoneOtherThanPolicyOwnerPayingOptionsErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(anyoneOtherThanPolicyOwnerPayingOptionsErrorLableXpath));
	}

	public WebElement getOwnerIntendToTransferOwnershipOptionsErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(ownerIntendToTransferOwnershipOptionsErrorLableXpath));
	}

	public WebElement getSomeoneElseWhoHasIndirectControlOptionsErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(someoneElseWhoHasIndirectControlOptionsErrorLableXpath));
	}

	public WebElement getFirtNameErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(firstNameErrorLableXpath));
	}

	public WebElement getLastNameErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(lastNameErrorLableXpath));
	}

	public WebElement getDOBErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(dobErrorLableXpath));
	}

	public WebElement getrelationshipToTheOwnerErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(relationshipToTheOwnerErrorLableXpath));
	}

	public WebElement getaddress1ErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(address1ErrorLableXpath));
	}

	public WebElement getcityOrTownErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(cityOrTownErrorLableXpath));
	}

	public WebElement getProvinceErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(provinceErrorLableXpath));
	}

	public WebElement getPostalcodeErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(postalcodeErrorLableXpath));
	}

	public WebElement getrelationshipToTheOwnerOthersErrorLable() {
		return this.driverUtil.getWebElement(By.xpath(relationshipToTheOwnerOtherErrorLableXpath));
	}

	public String getPageTitleOfThirdPartyInformationPage() {
		return this.driverUtil.getText(By.id(pageTitleId));
	}

	public WebElement getAnyoneOtherThanThePolicyOwnerBePayingYesButtton() {
		return this.driverUtil.getWebElement(By.xpath(anyoneOtherThanThePolicyOwnerBePayingYesXpath));
	}

	public WebElement getAnyoneOtherThanThePolicyOwnerBePayingNoButtton() {
		return this.driverUtil.getWebElement(By.xpath(anyoneOtherThanThePolicyOwnerBePayingNoXpath));
	}

	public WebElement getLanguageToggleButton() {
		return this.driverUtil.getWebElement(By.id(languageToggleBtnId));

	}

	public boolean clickonanyoneOtherThanThePolicyOwnerBePayingYesButton() {
		boolean clicked = false;
		try {

			WebElement yesButton = this.driverUtil
					.getWebElement(By.xpath(anyoneOtherThanThePolicyOwnerBePayingYesXpath));

			yesButton.click();
			clicked = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return clicked;

	}

	public WebElement getLegalEntityField() {
		return this.driverUtil.getWebElement(By.cssSelector(legalEntityFieldCssSelector));
	}

	public WebElement getIndividualField() {
		return this.driverUtil.getWebElement(By.cssSelector(individualFieldCssSelector));
	}

	public WebElement getFirstName() {
		return this.driverUtil.getWebElement(By.cssSelector(firstNameFieldCssSelector));
	}

	public WebElement getMiddleName() {
		return this.driverUtil.getWebElement(By.cssSelector(middleInitialFieldCssSelector));
	}

	public WebElement getLastName() {
		return this.driverUtil.getWebElement(By.cssSelector(lastNameFieldCssSelector));
	}

	public WebElement getDay() {
		return this.driverUtil.getWebElement(By.cssSelector(dayFieldCssSelector));
	}

	public void selectMonthOFDOB(String monthName) {
		this.driverUtil.click(By.cssSelector(monthDropdownCssSelector));
		this.driverUtil.getWebElement(By.xpath(ulMonthDropDownXPath))
				.findElement(By.xpath("//li[contains(text(),'" + monthName + "')]")).click();
	}

	public void selectRelationshipToOwner(String relationshipName) {
		this.driverUtil.click(By.cssSelector(relationshipToTheOwnerDropDownCssSelector));
		this.driverUtil.getWebElement(By.xpath(ulRelationshipDropDownXPath))
				.findElement(By.xpath("//li[contains(text(),'" + relationshipName + "')]")).click();
	}

	public void selectProvince(String province) {
		this.driverUtil.click(By.cssSelector(provinceDropdownCssSelector));
		this.driverUtil.getWebElement(By.xpath(ulProvinceDropDownXPath))
				.findElement(By.xpath("//li[contains(text(),'" + province + "')]")).click();
	}

	public WebElement getYear() {
		return this.driverUtil.getWebElement(By.cssSelector(yearFieldCssSelector));
	}

	public WebElement getReleationShipDropdown() {
		return this.driverUtil.getWebElement(By.cssSelector(relationshipToTheOwnerDropDownCssSelector));
	}

	public WebElement getAddressOne() {
		return this.driverUtil.getWebElement(By.cssSelector(address1CssSelector));
	}

	public WebElement getAddressTwo() {
		return this.driverUtil.getWebElement(By.cssSelector(address2CssSelector));
	}

	public WebElement getCityorTownTextbox() {
		return this.driverUtil.getWebElement(By.cssSelector(cityOrTownTextBoxCssSelector));
	}

	public WebElement getpostalCodeTextbox() {
		return this.driverUtil.getWebElement(By.cssSelector(postalCodeTextBoxCssSelector));
	}

	public WebElement getTooltip() {
		return this.driverUtil.getWebElement(By.cssSelector(tooltipCssSelector));
	}

	public WebElement ClickedIndividualButton() {

		return this.driverUtil.getWebElement(By.cssSelector(individualFieldCssSelector));

	}

	public boolean clickonanyoneOtherThanThePolicyOwnerBePayingNoButton() {

		boolean clicked = false;
		try {
			WebElement noButton = this.driverUtil.getWebElement(By.xpath(anyoneOtherThanThePolicyOwnerBePayingNoXpath));
			noButton.click();
			clicked = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return clicked;

	}

	public boolean ClikcOnOwnerIntendToTransferTheOwnershipYesORNoButton(String buttonType) {

		String useXPath = (buttonType.equalsIgnoreCase("Yes") ? ownerIntendToTransferTheOwnershipYesXpath
				: ownerIntendToTransferTheOwnershipYesXpath);
		boolean clicked = false;
		try {

			WebElement button = this.driverUtil.getWebElement(By.xpath(useXPath));

			button.click();
			clicked = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return clicked;

	}

	public String getSubSectionHeader() {
		return this.driverUtil.getText(By.xpath("subSectionThirdPartyInformationTitleXpath"));
	}

	public String getDivTextofPayingThirdParty() {
		return this.driverUtil.getText(By.xpath(anyoneOtherThanThePolicyOwnerBePayingYesOptionSelectedNoteXpath));
	}

	public WebElement getLinkTextofPayingThirdParty() {
		return this.driverUtil.getWebElement(By.xpath(anyoneOtherThanThePolicyOwnerBePayingYesOptionSelectedLinkXpath));
	}

	public boolean checkLinkOnPage() {

		if (this.driverUtil.getWebElements(By.xpath(anyoneOtherThanThePolicyOwnerBePayingYesOptionSelectedLinkXpath))
				.size() == 0) {
			return true;
		}

		return false;
	}

	public WebElement getOwnerIntendToTransferTheOwnershipLink() {
		return this.driverUtil.getWebElement(By.xpath(ownerIntendToTransferTheOwnershipNoXpath));
	}

	public WebElement getRelationshipOtherTextbox() {
		return this.driverUtil.getWebElement(By.cssSelector(relationshipToTheOwnerOtherTextboxCssSelector));
	}

	public WebElement getSomeoneElseWhoHasIndirectControl() {
		return this.driverUtil.getWebElement(By.xpath(someoneElseWhoHasIndirectControlYesXpath));
	}

	public void clickNextBtn() {
		// Click on Next Button
		this.driverUtil.getWebElement(By.id(thirdPartiesNextBtnId)).click();
	}

	public String getTitleofInsured()

	{
		return this.driverUtil.getWebElement(By.xpath(pageTitleOfInsuredPageXpath)).getText();
	}

	public String getSomeoneElseWhoHasIndirectControlToolTipLabel() {

		this.driverUtil.hoverWebElement(By.cssSelector(tooltipCssSelector));
		return this.driverUtil.waitForElementToBeVisible(By.xpath(tooltipDivXpath)).getText();
	}
	public WebElement getOtherPayor(String str) {
		String useXPath = (str.equals("Yes")) ?  anyoneOtherThanThePolicyOwnerBePayingYesXpath: (str.equals("No")) ? anyoneOtherThanThePolicyOwnerBePayingNoXpath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}

	public WebElement getTransferOwnership(String str) {
		String useXPath = (str.equals("Yes")) ?  ownerIntendToTransferTheOwnershipYesXpath: (str.equals("No")) ? ownerIntendToTransferTheOwnershipNoXpath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getIndirectControl(String str) {
		String useXPath = (str.equals("Yes")) ?  someoneElseWhoHasIndirectControlYesXpath: (str.equals("No")) ? someoneElseWhoHasIndirectControlNoXpath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
}
