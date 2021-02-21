package term_conversion;

import java.util.ArrayList;
import java.util.Map;
import java.util.*;  
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class OwnerInformationPage {
	private DriverUtil driverUtil;
	
	//XPath
	private String removeOwnerButtonXpath="//h2[@id='form-title']/following-sibling::a";
	private String startApplicationId="//button[@id='start-new-application-button']";
	private String enterOwnerInformatoinButtonID="owner-modal-button-0";
	private String divModalXPath = "//div[contains(@id,'modal-')]";
	private String labelConvertCoverageNewPolicycYesXPath = "//label[@for = 'convertCoveragePolicy-1']";
	private String labelConvertCoverageNewPolicycNoXPath = "//label[@for = 'convertCoveragePolicy-2']";
	private String labelOwnerNewPolicyDifferentYesXPath = "//label[@for = 'ownerNewPolicy-1']";
	private String labelOwnerNewPolicyDifferentNoXPath = "//label[@for = 'ownerNewPolicy-2']";
	private String labelOwnerExistingPolicyChangingYesXPath = "//label[@for = 'ownerExistingPolicy-1']";
	private String labelOwnerExistingPolicyChangingNoXPath = "//label[@for = 'ownerExistingPolicy-2']";
	private String labelPolicyLanguageEnglishXPath = "//label[@for = 'language-new-policy-English']";
	private String labelPolicyLanguageFrenchXPath = "//label[@for = 'language-new-policy-French']";
	private String inputExistingPolicyNumberXPath = "//input[@id = 'existingPolicyNumber']";
	private String inputConvertCoverageNewPolicyYesXPath = "//input[@id = 'convertCoveragePolicy-1']";
	private String inputConvertCoverageNewPolicyNoXPath = "//input[@id = 'convertCoveragePolicy-2']";
	private String inputPolicyLanguageEnglishXPath = "//input[@id = 'language-new-policy-English']";
	private String inputPolicyLanguageFrenchXPath = "//input[@id = 'language-new-policy-French']";
	private String inputOwnerNewPolicyDifferentYesXPath = "//input[@id = 'ownerNewPolicy-1']";
	private String inputOwnerNewPolicyDifferentNoXPath = "//input[@id = 'ownerNewPolicy-2']";
	private String inputOwnerExistingPolicyChangingYesXPath = "//input[@id = 'ownerExistingPolicy-1']";
	private String inputOwnerExistingPolicyChangingNoXPath = "//input[@id = 'ownerExistingPolicy-2']";
	private String inputOtherRelationXPath = "//input[@id='other-relation-%s']";
	//Owner Types
	private String labelOwnerTypeLegalEntityXPath = "//label[@for = 'ownerType-0-LegalEntity']";
	private String labelOwnerTypeIndividualXPath = "//label[@for = 'ownerType-0-Individual']";
	//Individual
	private String divOwnerPersonalInfoXPath = "//div[./div/h2[@id = 'form-title']]";
	private String inputIndivOwnerFirstNameXPath = "//input[contains(@id, 'owner-name-%S-firstName')]";
	private String inputIndivOwnerLastNameXPath = "//input[contains(@id,'owner-name-%S-lastName')]";
	private String inputIndivOwnerMiddleInitialXPath = "//input[contains(@id,'owner-name-%S-middleName')]";
	// Personal Contact Information
	private String inputIndivOwnerSinXPath = "//input[@id = 'individualOwnerSIN']";
	
	private String inputIndividualOwnerMaleXPath = "//input[@id = 'owner-sex-%S-1']";
	private String inputIndividualOwnerFemaleXPath = "//input[@id = 'owner-sex-%S-2']";
	
	private String labelIndividualOwnerMaleXPath = "//label[text()='Male']";
	private String labelIndividualOwnerFemaleXPath = "//label[text()='Female']";
	
	private String inputBirthDayXPath = "//input[@aria-label='Date of birth' and @placeholder='dd']";
	private String divBirthMonthXPath = "//div[@id='owner-dob-%s-month-value']";
	private String ulBirthMonthXPath = "//ul[@id = 'owner-dob-%s-month-listbox']";
	private String inputBirthYearXPath = "//input[@aria-label='Date of birth' and @placeholder='yyyy']";
	private String divRelationshipToInsuredXPath = "//div[@id = 'owner-relation-%S-value']";
	private String ulRelationshipToInsuredXPath = "//ul[@id = 'owner-relation-%S-listbox']";
	
	
	//Mailing address
		private String inputAddress1XPath = "//input[@id = 'address1']";
		private String inputAddress2XPath = "//input[@id = 'address2']";
		private String inputCityTownXPath = "//input[@id = 'cityTown']";
		private String divProvinceXPath = "//div[@id = 'province-value']";
		private String ulProvinceXPath = "//ul[@id = 'province-listbox']";
		private String inputPostalCodeXPath = "//input[@id = 'postalCode']";
	//Individual Owner Errors
	private String divOwnerTypeErrorsXPath = "//div[@id = 'ownerType-0--error']";
	private String divOwnerFirstNameErrorXPath = "//div[contains(@id, '-firstName-errors')]";
	private String divOwnerLastNameErrorXPath = "//div[contains(@id,'-lastName-errors')]";
	private String divOwnerMiddleInitialErrorXPath = "//div[contains(@id,'-middleName-errors')]";
	private String divOwnerSexErrorXPath = "//div[@id = 'owner-sex--error']";
	private String divRelationToInsuredErrorXPath = "//div[@id = 'relationToInsured-errors']";
	private String divDOBErrorXPath = "//div[@id ='dob']//div[@id = 'undefined-error']";
	private String divAddress1ErrorXPath = "//div[@id = 'address1-errors']";
	private String divCityErrorXPath = "//div[@id = 'cityTown-errors']";
	private String divProvinceErrorXPath = "//div[@id = 'province-errors']";
	private String divPostalCodeErrorXPath = "//div[@id = 'postalCode-errors']";
	//Legal Entity
	private String inputOwnerFullNameXPath = "//input[contains(@id,'full-name')]";
	private String inputBusinessNumberXPath = "//input[contains(@id,'business-number')]";
	
	//Tax Information
	private String ulReasonNoTINXPath = "//ul[contains(@id,'no-tin-reason-listbox')]";
	private String ulJurisdictionXPath = "//ul[contains(@id,'country-listbox')]";
	private String divJurisdictionXPath = "//div[contains(@id,'country-value')]";
	private String divTINNoReasonXPath = "//div[contains(@id,'no-tin-reason-value')]";
	private String labelCATaxResidentYesXPath = "//button[starts-with(@id,'resident-CA-0')]";
	private String labelCATaxResidentNoXPath = "//button[starts-with(@id,'resident-CA-1')]";	
	
	private String buttonOtherTaxResidentYesXPath = "//button[starts-with(@id,'resident-other-0')]";
	private String buttonOtherTaxResidentNoXPath = "//button[starts-with(@id,'resident-other-1')]";
	
	private String buttonSINProvidedYesXPath = "//button[starts-with(@id,'sin-provided-0')]";
	private String buttonSINProvidedNoXPath = "//button[starts-with(@id,'sin-provided-1')]";
	
	private String buttonSSNProvidedYesXPath = "//button[starts-with(@id,'ssn-provided-0')]";
	private String buttonSSNProvidedNoXPath = "//button[starts-with(@id,'ssn-provided-1')]";
	
	private String buttonTINProvidedYesXPath = "//button[contains(@id,'tin-provided-0')]";
	private String buttonTINProvidedNoXPath = "//button[contains(@id,'tin-provided-1')]";
	
	private String inputSINXPath = "//input[@id = 'sin']";
	private String inputSSNXPath = "//input[@id = 'ssn']";
	private String inputNoSINReasonXPath = "//input[@id = 'no-SIN-reason']";
	private String buttonCATaxResidentYesXPath = "//button[@id = 'resident-CA-0']";
	private String buttonCATaxResidentNoXPath = "//button[@id = 'resident-CA-1']";
	private String buttonUSTaxResidentYesXPath = "//button[@id = 'resident-US-0']";
	private String buttonUSTaxResidentNoXPath = "//button[@id = 'resident-US-1']";
	private String inputOtherTaxResidentYesXPath = "//button[@id = 'resident-other-0']";
	private String inputOtherTaxResidentNoXPath = "//button[@id = 'resident-other-1']";
	private String inputSINProvidedYesXPath = "//input[@id = 'sin']";
	private String inputSINProvidedNoXPath = "//input[@id = 'no-SIN-reason']";
	private String inputSSNProvidedYesXPath = "//button[@id = 'ssn-provided-0']";
	private String inputSSNProvidedNoXPath = "//button[@id = 'ssn-provided-1']";
	private String inputTINXPath = "//input[@id = 'residence-form-0-tin' or @id = 'residence-form-1-tin']";
	private String inputSpecifyReasonNoTINXPath = "//input[contains(@id,'no-tin-specific-reason')]";
	//Tax Residence Errors
	private String divTaxResidentCAErrorXPath = "//div[@id = 'resident-CA--error']";
	private String divTaxResidentUSErrorXPath = "//div[@id = 'resident-US--error']";
	private String divTaxResidentOtherErrorXPath = "//div[@id = 'resident-other--error']";
	private String divProvidingSINErrorXPath = "//div[@id = 'sin-provided--error']";
	private String divSINErrorXPath = "//div[@id = 'sin-errors']";
	private String divNotProvidingSINErrorXPath = "//div[@id = 'no-SIN-reason-errors']";
	private String divProvidingSSNErrorXPath = "//div[@id = 'ssn-provided--error']";
	private String divSSNErrorXPath = "//div[@id = 'ssn-errors']";
	private String divJurisdictionErrorXPath = "//div[@id = 'residence-form-0-country-errors']";
	private String divProvidingTINErrorXPath = "//div[@id = 'residence-form-0-tin-provided--error']";
	private String divTINErrorXPath = "//div[@id = 'residence-form-0-tin-errors']";
	private String divNotProvidingTINErrorXPath = "//div[@id = 'residence-form-0-no-tin-reason-errors']";
	private String divSpecifyOtherNoTINErrorXPath = "//div[@id = 'residence-form-0-no-tin-specific-reason-errors']";
	private String divNoSINReasonErrorXPath = "//div[@id = 'no-SIN-reason-errors']";
	//Personal Accordion for Owner1
	private String pOwnerNameXPath = "//p[@id = 'owner-panel-name']";
	
	//personal and contact Info Accordion for Owner1
	private String AccordionPersonalContactInfoOwner1XPath ="(//div[@role='button']/span[contains(text(),'Personal and contact information')])[1]";
	private String pDOBXPath = "(//p[@id = 'owner-panel-dob'])[1]";
	private String pAddressXPath = "(//p[@id = 'owner-panel-address'])[1]";
	private String pCityXPath = "(//p[@id = 'owner-panel-city'])[1]";
	private String pProvinceXPath = "(//p[@id = 'owner-panel-province'])[1]";
	private String pPostalCodeXPath = "(//p[@id = 'owner-panel-postal'])[1]";
	
	//personal and contact Info Accordion for Owner2
	private String AccordionPersonalContactInfoOwner2XPath ="(//div[@role='button']/span[contains(text(),'Personal and contact information')])[2]";
	private String personalAccordionDOBOwner2Xpath="(//p[@id = 'owner-panel-dob'])[2]";
	private String personalAccordionAddressOwner2XPath = "(//p[@id = 'owner-panel-address'])[2]";
	private String personalAccordionCityOwner2XPath = "(//p[@id = 'owner-panel-city'])[2]";
	private String personalAccordionProvinceOwner2XPath = "(//p[@id = 'owner-panel-province'])[2]";
	private String personalAccordionPostalCodeOwner2XPath = "(//p[@id = 'owner-panel-postal'])[2]";
	
	
	
	//Tax Accordion for Owner1
	private String spanTaxContactInformationXPath = "(//span[contains(text(),'Tax residence')])[1]";
	private String pTaxResidentCAXPath = "(//p[@id = 'taxInfo-panel-resident-CA'])[1]";
	private String pSINProvidedXPath = "(//p[@id = 'taxInfo-panel-sin-provided'])[1]";
	private String pSINXPath = "(//p[@id = 'taxInfo-panel-sin'])[1]";
	private String pNoSINReasonXPath = "(//p[@id = 'taxInfo-panel-no-SIN-reason'])[1]";
	private String pTaxResidentUSXPath = "(//p[@id = 'taxInfo-panel-resident-US'])[1]";
	private String pSSNProvidedXPath = "(//p[@id = 'taxInfo-panel-ssn-provided'])[1]";
	private String pSSNXPath = "(//p[@id = 'taxInfo-panel-ssn'])[1]";
	private String pTaxResidentOtherXPath = "(//p[@id = 'taxInfo-panel-resident-other'])[1]";
	private String pJurisdictionXPath = "(//p[@id = 'taxInfo-panel-0-country'])[1]";
	private String pTINProvidedXPath = "(//p[@id = 'taxInfo-panel-0-tin-provided'])[1]";
	private String pTINXPath = "(//p[@id = 'taxInfo-panel-0-tin'])[1]";
	private String pNoTINReasonXPath = "(//p[@id = 'taxInfo-panel-0-no-tin-reason'])[1]";
	private String pNoTINSpecificReasonXPath = "(//p[@id = 'taxInfo-panel-0-no-tin-specific-reason'])[1]";
	
	//Tax Accordion for Owner2
	private String owner2TaxAccordionInformationXPath = "(//span[contains(text(),'Tax residence')])[2]";
	private String owner2TaxAccordionTaxResidentCanadaXPath = "(//p[@id = 'taxInfo-panel-resident-CA'])[2]";
	private String owner2TaxAccordionSINProvidedXPath = "(//p[@id = 'taxInfo-panel-sin-provided'])[2]";
	private String owner2TaxAccordionSINXPath = "(//p[@id = 'taxInfo-panel-sin'])[2]";
	private String owner2TaxAccordionNoSINReasonXPath = "(//p[@id = 'taxInfo-panel-no-SIN-reason'])[2]";
	private String owner2TaxAccordionTaxResidentUSXPath = "(//p[@id = 'taxInfo-panel-resident-US'])[2]";
	private String owner2TaxAccordionSSNProvidedXPath = "(//p[@id = 'taxInfo-panel-ssn-provided'])[2]";
	private String owner2TaxAccordionSSNXPath = "(//p[@id = 'taxInfo-panel-ssn'])[2]";
	private String owner2TaxAccordionTaxResidentOtherXPath = "(//p[@id = 'taxInfo-panel-resident-other'])[2]";
	private String owner2TaxAccordionJurisdictionXPath = "(//p[@id = 'taxInfo-panel-0-country'])[2]";
	private String owner2TaxAccordionTINProvidedXPath = "(//p[@id = 'taxInfo-panel-0-tin-provided'])[2]";
	private String owner2TaxAccordionTINXPath = "(//p[@id = 'taxInfo-panel-0-tin'])[2]";
	private String owner2TaxAccordionNoTINReasonXPath = "(//p[@id = 'taxInfo-panel-0-no-tin-reason'])[2]";
	private String owner2TaxAccordionNoTINSpecificReasonXPath = "(//p[@id = 'taxInfo-panel-0-no-tin-specific-reason'])[2]";
	
	//Convert To New Policy Labels
	private String buttonPolicyLanguageXPath = "//button[@id = 'language-new-policy-tooltip-toggle']";
	private String divToolTipXPath = "//div[@role = 'tooltip']";
	private String divExistingPolicyNoteXPath = "//div[@id = 'existingPolicyNote']";
	private String divChangeOwnerExistingPolicyNoteXPath = "//div[@id = 'ChangeOwnerExistingPolicyNote']";
	private String divChangeOwnerNewPolicyNoteXPath = "//div[@id = 'ChangeOwnerNewPolicyNote']";
	private String divExistingInfoXPath = "//span[contains(text(),\"We'll use the existing owner info\") or contains(text(),'Nous utiliserons les renseignements')]";
	private String labelExistingPolicyNumberXPath = "//label[@for = 'existingPolicyNumber']";
	private String legendOwnerExistingPolicyXPath = "//legend[@id = 'ownerExistingPolicy-label']";
	private String legendConvertCoveragePolicyXPath = "//legend[@id = 'convertCoveragePolicy-label']";
	private String legendPolicyLanguageXPath = "//legend[@id = 'language-new-policy-label']";
	private String legendOwnerNewPolicyXPath = "//legend[@id = 'ownerNewPolicy-label']";
	//Convert To New Policy Errors
	private String divConvertCoveragePolicyErrorXPath = "//div[@id = 'convertCoveragePolicy--error']";
	private String divOwnerNewPolicyErrorXPath = "//div[@id = 'ownerNewPolicy--error']";
	private String divExistingPolicyNumberErrorXPath = "//div[@id = 'existingPolicyNumber-errors']";
	private String divOwnerExistingPolicyErrorXPath = "//div[@id = 'ownerExistingPolicy--error']";
	//Buttons
	private String buttonSaveOwnerInfoXPath = "//div[@class = 'saveAndClose-btn']/button";
	private String buttonContinueXPath = "//button[@id = 'contine-btn']";
	private String buttonEnterOwnerInfoXPath = "//button[@id='owner-modal-button-0']";
	private String buttonNextXPath = "//button[@id = 'next-btn']";
	
	
	// Employment Information overlay page
	
	private String employmentStatusDivCssSelector="button[aria-label^='What is your employment status']";
	private String employmentStatusUlXpath="//ul[starts-with(@id,'emp-status-dd') and contains(@id,'listbox')]";
	
	private String employmentIndustryDivID="//div[starts-with(@id,'owner-industry') and contains(@id,'value')]";
	private String employmentIndustryDropDownValueID="//ul[starts-with(@id,'owner-industry') and contains(@id,'listbox')]";
	
	private String currentOccupationDivXpath="//div[starts-with(@id,'owner-occupation') and contains(@id,'value')]";
		
	private String currentOccupationDropdownXpath="//ul[starts-with(@id,'owner-occupation') and contains(@id,'listbox')]";
	
	private String nameOfTheCompanyXpath="//input [starts-with(@id,'owner-company')]";

	private String soleProprietorYesXpath="//button[starts-with(@id,'sole-prop-btn') and contains(text(),'Yes')]";
	private String soleProprietorNoXpath="//button[starts-with(@id,'sole-prop-btn') and contains(text(),'No')]";
	
	//Identity verification overlay page
	
	private String identificationMethodDivXpath="//div[starts-with(@id,'idv-method-dd') and contains(@id,'value')]";
	private String identificationMethodDropdownXpath="//ul[starts-with(@id,'idv-method-dd') and contains(@id,'listbox')]";
	
	private String identificationDocumentFederalXpath="//button[text()='Federal']";
	private String identificationDocumentProvincialXpath="//button[text()='Provincial or Territorial']";
	
	private String provinceIssuedInDivId="//div[starts-with(@id,'doc-province-issued-dd') and contains(@id,'value')]";
	private String provinceIssuedInDropdownId="//ul[starts-with(@id,'doc-province-issued-dd') and contains(@id,'listbox')]";
	
	private String originalDocumentReviewedDivId="//div[starts-with(@id,'doc-original-dd') and contains(@id,'value')]";
		
	private String originalDocumentReviewedDropdownId="//ul[starts-with(@id,'doc-original-dd') and contains(@id,'listbox')]";
	
	private String nameOnTheDocumentFirstNameXpath="//input[starts-with(@id,'legal-name') and contains(@id,'firstName')]";
			
	private String nameOnTheDocumentMiddleInitialXpath="//input[starts-with(@id,'legal-name') and contains(@id,'middleName')]";
	private String nameOnTheDocumentLastNameXpath="//input[starts-with(@id,'legal-name') and contains(@id,'lastName')]";
	
	private String identifyingNumberOnTheDocumentXpath="//input[starts-with(@id,'doc-number')]";
		
	
	private String documentExpiryDateDayXpath="//input[starts-with(@id,'doc-expiry-date') and contains(@id,'day')]";
			
	private String  documentExpiryDateMonthDivXpath="//div[starts-with(@id,'doc-expiry-date') and contains(@id,'month-value')]";
			
	private String  documentExpiryDateMonthUlXpath="//ul[starts-with(@id,'doc-expiry-date') and contains(@id,'month-listbox')]";
	private String documentExpiryDateYearXpath="//input[starts-with(@id,'doc-expiry-date') and contains(@id,'year')]";
	
	private String dateInformationWasVerifiedDayXpath="//input[starts-with(@id,'doc-verified-date') and contains(@id,'day')]";
		
	private String dateInformationWasVerifiedMonthDivXpath="//div[starts-with(@id,'doc-verified-date') and contains(@id,'month-value')]";
			
	private String dateInformationWasVerifiedMonthUlXpath="//ul[starts-with(@id,'doc-verified-date') and contains(@id,'month-listbox')]";
	private String dateInformationWasVerifiedYearXpath="//input[starts-with(@id,'doc-verified-date') and contains(@id,'year')]";
	
	private String AccordionofIndentityXapth="//span[text()='Identity verification']";
	//when "Federal" option is selected as an Identification document-jurisdiction of issue field then the following reflexive fields will appear
	private String federalOriginalDocumentReviewedDivXpath="//div[@id='doc-original-dd-0-value']";
	private String federalOriginalDocumentReviewedUlXpath="//ul[@id='doc-original-dd-0-listbox']";
	
	private String EmpStatusLabelXpath="//label[contains(text(),'What is your employment status?')]";
	private String solePropietorLabelXpath="//legend[contains(text(),'Are you a sole proprietor?')]";
	private String industryLabelXpath="//label[contains(text(),'In what industry are you currently employed?')]";
	private String occupationLabelXpath="//label[contains(text(),'Current occupation')]";
	private String nameOfCompanyLabelXpath="//label[contains(text(),'Name of the company / current employer ')]";
	private String labelOfIdentificationMethodXpath="//label[contains(text(),'Identification method')]";
	private String labelOfIdentificationDocumentXpath="//legend[contains(text(),'Identification document - jurisdiction of issue')]";
	private String labelOfProvinceIssuedInXpath="//label[contains(text(),'Province issued in')]";
	private String empStatusErrorLabelXpath="//div[@id='emp-status-dd-0-errors']/div";
	private String soleProprietorErrorLabelXpath="//div[@id='sole-prop-btn-0--error']/span";
	private String industryErrorLabelXpath="//div[@id='owner-industry-0-errors']/div";
	private String occupationErrorLabelXpath="//div[@id='owner-occupation-0-errors']/div";
	private String nameOfCompanyErrorLabelXpath="//div[@id='owner-company-0-errors']/div";
	
	private String errorLableOfIdentificationmethodXpath="//div[@id='idv-method-dd-0-errors']/div";
	private String errorLableOfIdentificationDocumentXpath="//div[@id='doc-jurisdiction-btn-0--error']/span";
	private String errorLableOfProvinceIssuedInXpath="//div[@id='doc-province-issued-dd-0-errors']/div";
	private String errorLableOfOriginaldocumentreviewedXpath="//div[@id='doc-original-dd-0-errors']/div";
	private String errorLableOfNameOnTheDocumentFirstNameXpath="//div[@id='legal-name-0-firstName-errors']/div";
	private String errorLableOfNameOnTheDocumentLastNameXpath="//div[@id='legal-name-0-lastName-errors']/div";
	private String errorLableOfDocumentexpirydateXpath="//div[@id='expiryDate']//div[@id='undefined-error']/span";
	private String errorLableOfDateinformationwasverifiedXpath="//div[@id='verifiedDate']//div[@id='undefined-error']/span";
	private String errorLableOfIdentifyingNumberOnTheDocXpath="//div[@id='doc-number-0-errors']/div";
	
	//Employment Information overlay page accordion
	
	private String accordinanEmpStatusValueXpath="//p[@id = 'empInfo-panel-emp-status-pnl']";
	private String AccordionSolePropritorId="empInfo-panel-sole-prop-pnl";
	private String accordinanCurrentIndurstyValueXpath="//p[@id='empInfo-panel-industry-pnl']";
	private String AccordionCurrentOccupationValueXpath="//p[@id='empInfo-panel-occupation-pnl']";
	private String AccordionCompanyNameValueXpath="//p[@id='empInfo-panel-company-pnl']";
	private String empInfoAccordionXapth="//span[contains(text(),'Employment information')]";
	
	//Identity verification overlay page accordion
	private String accodianIdentityVerificationXpath="//span[text()='Identity verification']/parent::div";
	private String AccordionIdentificationMethodValueXpath="//p[@id='idvInfo-panel-idv-method-pnl']";
	
	private String AccordionIdentificationdocumentValueXpath="//p[@id='idvInfo-panel-doc-jurisdiction-pnl']";
	
	
	private String AccordionIdentityProvinceIssuedInValueXpath="//p[@id='idvInfo-panel-doc-province-issued-pnl']";
	private String AccordionIdentityOriginalDocValueXpath="//p[@id='idvInfo-panel-doc-original-pnl']";
	private String AccordionIdentityNameOnDocumentValueXpath="//p[@id='idvInfo-panel-legal-name-pnl']";
	private String AccordionIdentifyingNumOnDocValueXpath="//p[@id='idvInfo-panel-doc-number-pnl']";
	private String AccordionIdentityDocExpiryDateValueXpath="//p[@id='doc-expiry-date-pnl']";
	private String AccordionIdentityDocVerifiedDateValueXpath="//p[@id='doc-verified-date-pnl']";
	
	
	
	
	
	
	
	
	public OwnerInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	public WebElement getRemoveOwnerBtn() {
		
		return this.driverUtil.getWebElements(By.xpath(removeOwnerButtonXpath)).get(0);
		
		
		
	}
	
	public boolean isRemoveOwnerBtnVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(removeOwnerButtonXpath));
	}
	
	
	public String getRemoveOwnerBtnText()
	{
		return getRemoveOwnerBtn().getText();
	}
	
	public void startApplication() {
		this.driverUtil.getWebElement(By.xpath(startApplicationId)).click();
	}
	
	//Owner Elements
	public WebElement getConvertCoverageNewPolicy(boolean bln) {
		String useXPath = (bln) ?  labelConvertCoverageNewPolicycYesXPath: labelConvertCoverageNewPolicycNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getConvertCoverageNewPolicyInput(boolean bln) {
		String useXPath = (bln) ?  inputConvertCoverageNewPolicyYesXPath: inputConvertCoverageNewPolicyNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getPolicyLanguage(String str) {
		String useXPath = (str.equals("English")) ?  labelPolicyLanguageEnglishXPath: (str.equals("French")) ? labelPolicyLanguageFrenchXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getPolicyLanguageInput(String str) {
		String useXPath = (str.equals("English")) ?  inputPolicyLanguageEnglishXPath: (str.equals("French")) ? inputPolicyLanguageFrenchXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getOwnerNewPolicyDifferent(boolean bln) {
		String useXPath = (bln) ?  labelOwnerNewPolicyDifferentYesXPath: labelOwnerNewPolicyDifferentNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getOwnerNewPolicyDifferentInput(boolean bln) {
		String useXPath = (bln) ?  inputOwnerNewPolicyDifferentYesXPath: inputOwnerNewPolicyDifferentNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	public WebElement getEnterOwnerInformatoinButtonID()
	{
		return this.driverUtil.getWebElement(By.id(enterOwnerInformatoinButtonID));
	}
	
	public WebElement getOwnerType(String str) {
		String useXPath = (str.equals("Legal entity")) ?  labelOwnerTypeLegalEntityXPath: (str.equals("Individual")) ? labelOwnerTypeIndividualXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getExistingPolicyNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputExistingPolicyNumberXPath));
	}
	
	public WebElement getOwnerExistingPolicyChanging(String str) {
		String useXPath = (str.equals("Yes")) ?  labelOwnerExistingPolicyChangingYesXPath: (str.equals("No")) ? labelOwnerExistingPolicyChangingNoXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getOwnerExistingPolicyChangingInput(String str) {
		String useXPath = (str.equals("Yes")) ?  inputOwnerExistingPolicyChangingYesXPath: (str.equals("No")) ? inputOwnerExistingPolicyChangingNoXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	//Convert To New Policy Labels
	public String getConvertCoverageLabel() {
		return this.driverUtil.getText(By.xpath(legendConvertCoveragePolicyXPath));
	}
	
	public String getPolicyLanguageLabel() {
		return this.driverUtil.getText(By.xpath(legendPolicyLanguageXPath));
	}
	
	public String getPolicyLanguageTooltip() {
		this.driverUtil.hoverWebElement(By.xpath(buttonPolicyLanguageXPath));
		return this.driverUtil.getText(By.xpath(divToolTipXPath));
	}
	
	public String getPolicyDifferentExistingLabel() {
		return this.driverUtil.getText(By.xpath(legendOwnerNewPolicyXPath));
	}
	
	public String getExistingPolicyNumberLabel() {
		return this.driverUtil.getText(By.xpath(labelExistingPolicyNumberXPath));
	}
	
	public String getOwnerExistingPolicyChangingLabel() {
		return this.driverUtil.getText(By.xpath(legendOwnerExistingPolicyXPath));
	}
	
	public String getExistingPolicyNote() {
		return this.driverUtil.getText(By.xpath(divExistingPolicyNoteXPath)).replace("\n","");
	}
	
	public String getChangeOwnerExistingPolicyNote() {
		return this.driverUtil.getText(By.xpath(divChangeOwnerExistingPolicyNoteXPath)).replace("\n","");
	}
	
	public String getChangeOwnerNewPolicyNote() {
		return this.driverUtil.getText(By.xpath(divChangeOwnerNewPolicyNoteXPath)).replace("\n","");
	}
	
	public String getExistingInfoNote() {
		return this.driverUtil.getText(By.xpath(divExistingInfoXPath));
	}
	
	//Convert To New Policy Errors
	public String getConvertCoveragePolicyError() {
		return this.driverUtil.getText(By.xpath(divConvertCoveragePolicyErrorXPath));
	}
	
	public String getOwnerNewPolicyError() {
		return this.driverUtil.getText(By.xpath(divOwnerNewPolicyErrorXPath));
	}
	
	public String getExistingPolicyNumberError() {
		return this.driverUtil.getText(By.xpath(divExistingPolicyNumberErrorXPath));
	}
	
	public String getOwnerExistingPolicyError() {
		return this.driverUtil.getText(By.xpath(divOwnerExistingPolicyErrorXPath));
	}
	
	//Individual Owner Elements
	public WebElement getIndivOwnerFirstName(String ownerNumber) {
		
		String xpath =formatXpath(inputIndivOwnerFirstNameXPath,ownerNumber);
		
		return this.driverUtil.getWebElement(By.xpath(xpath));
	}
	
	public WebElement getIndivOwnerMiddleInitial(String ownerNumber) {
		String xpath =formatXpath(inputIndivOwnerMiddleInitialXPath,ownerNumber );
		return this.driverUtil.getWebElement(By.xpath(xpath));
	}
	
	public WebElement getIndivOwnerLastName(String ownerNumber) {
		String xpath =formatXpath(inputIndivOwnerLastNameXPath,ownerNumber);
		return this.driverUtil.getWebElement(By.xpath(xpath));
	}
	
	public WebElement getIndivOwnerSIN() {
		return this.driverUtil.getWebElement(By.xpath(inputIndivOwnerSinXPath));
	}
	
	public WebElement getIndivOwnerSex(String str) {
		String useXPath = (str.equals("Male")) ?  labelIndividualOwnerMaleXPath: (str.equals("Female")) ? labelIndividualOwnerFemaleXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getIndivOwnerSexInput(String str,String index) {
		String useXPath = (str.equals("Male")) ?  inputIndividualOwnerMaleXPath: (str.equals("Female")) ? inputIndividualOwnerFemaleXPath : "Error";
		String Xapth= formatXpath(useXPath, index);
		return this.driverUtil.getWebElement(By.xpath(Xapth));
	}
	
	public WebElement getIndivOwnerBirthDay() {
		return this.driverUtil.getWebElement(By.xpath(inputBirthDayXPath));
	}
	
	
	
	//Dropdown method.
	public void selectIndivOwnerBirthMonth(String month,String index) {		
		String xpath= formatXpath(divBirthMonthXPath, index);
		this.driverUtil.click(By.xpath(xpath));
		xpath= formatXpath(ulBirthMonthXPath, index);
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(xpath));
		monthDropdown.findElement(By.xpath("//li[contains(text(),'"+month+"')]")).click();
	}
	
	public WebElement getIndivOwnerBirthMonth(String index) {
		String xpath= formatXpath(divBirthMonthXPath, index);
		return this.driverUtil.getWebElement(By.xpath(xpath));
	}
	
	public WebElement getIndivOwnerBirthYear() {
		
		
		return this.driverUtil.getWebElement(By.xpath(inputBirthYearXPath));
	}
	
	public void selectRelationshipToInsured(String relationship, String Index) {
		String xpathDiv= formatXpath(divRelationshipToInsuredXPath, Index);
		this.driverUtil.click(By.xpath(xpathDiv));
		String xpathUL= formatXpath(ulRelationshipToInsuredXPath, Index);		
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(xpathUL));
		monthDropdown.findElement(By.xpath("//li[contains(text(),'"+relationship+"')]")).click();
	}
	
	public WebElement getRelationship(String index) {
		String xpathDiv= formatXpath(divRelationshipToInsuredXPath, index);
		return this.driverUtil.getWebElement(By.xpath(xpathDiv));
	}
	
	public WebElement getOtherRelationship(String ownerNumber) {
		String xpathDiv= formatXpath(inputOtherRelationXPath, ownerNumber);		
		return this.driverUtil.getWebElement(By.xpath(xpathDiv));
	}
	
	public WebElement getEnterOwnerInfo() {
		return this.driverUtil.getWebElement(By.xpath(buttonEnterOwnerInfoXPath));
	}
	public WebElement getEnterOwnerInfo2() {
		return this.driverUtil.getWebElement(By.xpath("//button[@id='owner-modal-button-1']"));
	}
		
	//Individual Owner Labels
	public String[] getMonthOptions(String index) {
		CommonElements commonElements = new CommonElements(driverUtil);
		String xpathUL= formatXpath(divBirthMonthXPath, index);
		this.driverUtil.click(By.xpath(xpathUL));	
		String[] optionLabels = commonElements.getOptionLabels(formatXpath(ulBirthMonthXPath, index),"li");
		this.driverUtil.click(By.xpath(xpathUL));
		return optionLabels;
	}
	
	public String[] getInsuredRelationshipOptions() {
		CommonElements commonElements = new CommonElements(driverUtil);
		divRelationshipToInsuredXPath= formatXpath(divRelationshipToInsuredXPath, "0");
		this.driverUtil.click(By.xpath(divRelationshipToInsuredXPath));
		ulRelationshipToInsuredXPath= formatXpath(ulRelationshipToInsuredXPath, "0");
		String[] optionLabels = commonElements.getOptionLabels(ulRelationshipToInsuredXPath,"li");
		this.driverUtil.click(By.xpath(divRelationshipToInsuredXPath));
		return optionLabels;
	}
	
	//Individual Owner Errors
	public String getOwnerTypeError() {
		return this.driverUtil.getText(By.xpath(divOwnerTypeErrorsXPath));
	}
	
	public String getFirstNameError() {
		return this.driverUtil.getText(By.xpath(divOwnerFirstNameErrorXPath));
	}
	
	public String getMiddleNameError() {
		return this.driverUtil.getText(By.xpath(divOwnerMiddleInitialErrorXPath));
	}
	
	public String getLastNameError() {
		return this.driverUtil.getText(By.xpath(divOwnerLastNameErrorXPath));
	}
	
	public String getRelationToInsuredError() {
		return this.driverUtil.getText(By.xpath(divRelationToInsuredErrorXPath));
	}
	
	public String getOwnerSexError() {
		return this.driverUtil.getText(By.xpath(divOwnerSexErrorXPath));
	}
	
	public String getDOBError() {
		return this.driverUtil.getText(By.xpath(divDOBErrorXPath));
	}
	
	public String getAddressError() {
		return this.driverUtil.getText(By.xpath(divAddress1ErrorXPath));
	}
	
	public String getCityError() {
		return this.driverUtil.getText(By.xpath(divCityErrorXPath));
	}
	
	public String getProvinceError() {
		return this.driverUtil.getText(By.xpath(divProvinceErrorXPath));
	}
	
	public String getPostalCodeError() {
		return this.driverUtil.getText(By.xpath(divPostalCodeErrorXPath));
	}
	
	//Legal Entity
	public WebElement getOwnerFullName() {
		return this.driverUtil.getWebElement(By.xpath(inputOwnerFullNameXPath));
	}
	
	public WebElement getBusinessNumber() {
		return this.driverUtil.getWebElement(By.xpath(inputBusinessNumberXPath));
	}
	
	//Mailing address
	public WebElement getAddress1() {
		return this.driverUtil.getWebElement(By.xpath(inputAddress1XPath));
	}
	
	public WebElement getAddress2() {
		return this.driverUtil.getWebElement(By.xpath(inputAddress2XPath));
	}
	
	public WebElement getCity() {
		return this.driverUtil.getWebElement(By.xpath(inputCityTownXPath));
	}
	
	public void selectProvince(String province) {		
		this.driverUtil.click(By.xpath(divProvinceXPath));
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(ulProvinceXPath));
		monthDropdown.findElement(By.xpath("//li[contains(text(),'"+province+"')]")).click();
	}
	
	public WebElement getProvince() {
		return this.driverUtil.getWebElement(By.xpath(divProvinceXPath));
	}
	
	public WebElement getPostalCode() {
		return this.driverUtil.getWebElement(By.xpath(inputPostalCodeXPath));
	}
	
	public String[] getProvinceOptions() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divProvinceXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulProvinceXPath,"li");
		this.driverUtil.click(By.xpath(divProvinceXPath));
		return optionLabels;
	}
	
	//Tax Information
	public WebElement getCATaxResident(String str,String index) {
		
		String useXPath = (str.equals("Yes")) ?  labelCATaxResidentYesXPath: (str.equals("No")) ?  labelCATaxResidentNoXPath: "Error";
		
		
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getUSTaxResident(String str) {
		String useXPath = (str.equals("Yes")) ?  buttonUSTaxResidentYesXPath: (str.equals("No")) ?  buttonUSTaxResidentNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getOtherTaxResident(String str) {
		String useXPath = (str.equals("Yes")) ?  buttonOtherTaxResidentYesXPath: (str.equals("No")) ?  buttonOtherTaxResidentNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getCATaxResidentInput(String str) {
		String useXPath = (str.equals("Yes")) ?  buttonCATaxResidentYesXPath: (str.equals("No")) ?  buttonCATaxResidentNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getUSTaxResidentButton(String str) {
		String useXPath = (str.equals("Yes")) ?  buttonUSTaxResidentYesXPath: (str.equals("No")) ?  buttonUSTaxResidentNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getOtherTaxResidentInput(String str) {
		String useXPath = (str.equals("Yes")) ?  inputOtherTaxResidentYesXPath: (str.equals("No")) ?  inputOtherTaxResidentNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getSINProvided(String str) {
		String useXPath = (str.equals("Yes")) ?  buttonSINProvidedYesXPath: (str.equals("No")) ?  buttonSINProvidedNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getSINProvidedInput(String str) {
		String useXPath = (str.equals("Yes")) ?  inputSINProvidedYesXPath: (str.equals("No")) ?  inputSINProvidedNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getSIN() {
		return this.driverUtil.getWebElement(By.xpath(inputSINXPath));
	}
	
	public WebElement getSSN() {
		return this.driverUtil.getWebElement(By.xpath(inputSSNXPath));
	}
	
	public WebElement getNoSINReason() {
		return this.driverUtil.getWebElement(By.xpath(inputNoSINReasonXPath));
	}
	
	public WebElement getSSNProvided(String str) {
		String useXPath = (str.equals("Yes")) ?  buttonSSNProvidedYesXPath: (str.equals("No")) ?  buttonSSNProvidedNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getSSNProvidedInput(String str) {
		String useXPath = (str.equals("Yes")) ?  inputSSNProvidedYesXPath: (str.equals("No")) ?  inputSSNProvidedNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getTINProvided(String str) {
		String useXPath = (str.equals("Yes")) ?  buttonTINProvidedYesXPath: (str.equals("No")) ? buttonTINProvidedNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getTIN() {
		return this.driverUtil.getWebElement(By.xpath(inputTINXPath));
	}
	
	public String[] getJurisdictionOptions() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divJurisdictionXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulJurisdictionXPath,"li");
		this.driverUtil.click(By.xpath(divJurisdictionXPath));
		return optionLabels;
	}
	
	public void selectJurisdiction(String country) {		
		this.driverUtil.click(By.xpath(divJurisdictionXPath));
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(ulJurisdictionXPath));
		monthDropdown.findElement(By.xpath("//li[contains(text(),'"+country+"')]")).click();
	}
	
	public WebElement getJurisdiction() {
		return this.driverUtil.getWebElement(By.xpath(divJurisdictionXPath));
	}
	
	public String[] getReasonNoTINOptions() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divTINNoReasonXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulReasonNoTINXPath,"li");
		this.driverUtil.click(By.xpath(divTINNoReasonXPath));
		return optionLabels;
	}
	
	public void selectReasonNoTIN(String reason) {		
		this.driverUtil.click(By.xpath(divTINNoReasonXPath));
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(ulReasonNoTINXPath));
		monthDropdown.findElement(By.xpath("//li[contains(text(),'"+reason+"')]")).click();
	}
	
	public WebElement getReasonNoTIN() {
		return this.driverUtil.getWebElement(By.xpath(divTINNoReasonXPath));
	}
	
	public WebElement getSpecifyReasonNoTIN() {
		return this.driverUtil.getWebElement(By.xpath(inputSpecifyReasonNoTINXPath));
	}
	
	//Tax Residence Errors
	public String getTaxResidentCAError() {
		return this.driverUtil.getText(By.xpath(divTaxResidentCAErrorXPath));
	}
	
	public String getTaxResidentUSError() {
		return this.driverUtil.getText(By.xpath(divTaxResidentUSErrorXPath));
	}
	
	public String getTaxResidentOtherError() {
		return this.driverUtil.getText(By.xpath(divTaxResidentOtherErrorXPath));
	}
	
	public String getProvidedSINError() {
		return this.driverUtil.getText(By.xpath(divProvidingSINErrorXPath));
	}
	
	public String getSINError() {
		return this.driverUtil.getText(By.xpath(divSINErrorXPath));
	}
	
	public String getNoSINError() {
		return this.driverUtil.getText(By.xpath(divNotProvidingSINErrorXPath));
	}
	
	public String getProvidedSSNError() {
		return this.driverUtil.getText(By.xpath(divProvidingSSNErrorXPath));
	}
	
	public String getSSNError() {
		return this.driverUtil.getText(By.xpath(divSSNErrorXPath));
	}
	
	public String getJurisdictionError() {
		return this.driverUtil.getText(By.xpath(divJurisdictionErrorXPath));
	}
	
	public String getProvidedTINError() {
		return this.driverUtil.getText(By.xpath(divProvidingTINErrorXPath));
	}
	
	public String getTINError() {
		return this.driverUtil.getText(By.xpath(divTINErrorXPath));
	}
	
	public String getReasonNoTINError() {
		return this.driverUtil.getText(By.xpath(divNotProvidingTINErrorXPath));
	}
	
	public String getReasonNoSINError() {
		return this.driverUtil.getText(By.xpath(divNoSINReasonErrorXPath));
	}
	
	public String getSpecifyNoTINError() {
		return this.driverUtil.getText(By.xpath(divSpecifyOtherNoTINErrorXPath));
	}
	
	
	public String tst() {
		return this.driverUtil.getText(By.xpath(""));
	}
	
	//Is Element Visible
	public boolean isPolicyLanguageVisible() {
		return this.driverUtil.getWebElements(By.xpath(labelPolicyLanguageEnglishXPath)).size() > 0;
	}
	
	public boolean isNewPolicyDifferentVisible() {
		return this.driverUtil.getWebElements(By.xpath(labelOwnerNewPolicyDifferentYesXPath)).size() > 0;
	}
	
	public boolean isOwnerTypeVisible() {
		return this.driverUtil.getWebElements(By.xpath(labelOwnerTypeLegalEntityXPath)).size() > 0;
	}
	
	public boolean isPolicyNumberVisible() {
		return this.driverUtil.getWebElements(By.xpath(inputExistingPolicyNumberXPath)).size() > 0;
	}
	
	public boolean isPolicyChangingVisible() {
		return this.driverUtil.getWebElements(By.xpath(inputOwnerExistingPolicyChangingYesXPath)).size() > 0;
	}
	
	public boolean isSpecifyRelationshipVisible(String ownerNumber) {
		String xpathDiv= formatXpath(inputOtherRelationXPath, ownerNumber);		
		
		return this.driverUtil.checkElementPresent(By.xpath(xpathDiv));
	}
	
	public boolean isModalVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(divModalXPath));
	}
	
	public boolean isSINProvidedVisible() {
		return this.driverUtil.getElements(By.xpath(buttonSINProvidedYesXPath)).size() > 0;
	}
	
	public boolean isSINVisible() {
		return this.driverUtil.getElements(By.xpath(inputSINXPath)).size() > 0;
	}
	
	public boolean isNoSINReasonVisible() {
		return this.driverUtil.getElements(By.xpath(inputNoSINReasonXPath)).size() > 0;
	}
	
	public boolean isSSNProvidedVisible() {
		return this.driverUtil.getElements(By.xpath(inputSSNProvidedYesXPath)).size() > 0;
	}
	
	public boolean isSSNTextboxVisible() {
		return this.driverUtil.getElements(By.xpath(inputSSNXPath)).size() > 0;
	}
	
	public boolean isJurisdictionVisible() {
		return this.driverUtil.getElements(By.xpath(divJurisdictionXPath)).size() > 0;
	}
	
	public boolean isTINProvidedButtonVisible() {
		return this.driverUtil.getElements(By.xpath(buttonTINProvidedYesXPath)).size() > 0;
	}
	
	public boolean isTINVisible() {
		return this.driverUtil.getElements(By.xpath(inputTINXPath)).size() > 0;
	}
	
	public boolean isReasonNoTINVisible() {
		return this.driverUtil.getElements(By.xpath(divTINNoReasonXPath)).size() > 0;
	}
	
	public boolean isSpecifyNoTINVisible() {
		return this.driverUtil.getElements(By.xpath(inputSpecifyReasonNoTINXPath)).size() > 0;
	}
	
	public boolean isChangeOwnerNewPolicyNoteVisible() {
		return this.driverUtil.getElements(By.xpath(divChangeOwnerNewPolicyNoteXPath)).size() > 0;
	}
	
	//Modal Buttons
	public WebElement getSaveOwnerInfo() {
		return this.driverUtil.getWebElement(By.xpath(buttonSaveOwnerInfoXPath));
	}
	
	public WebElement getContinue() {
		return this.driverUtil.getWebElement(By.xpath(buttonContinueXPath));
	}
	
	//Personal Accordion
	public WebElement getPersonalAccordion() {		
	
		return this.driverUtil.getWebElement(By.xpath(AccordionPersonalContactInfoOwner1XPath));
	}
	public WebElement getPersonalAccordionOwner2() {		
		
		return this.driverUtil.getWebElement(By.xpath(AccordionPersonalContactInfoOwner2XPath));
	}
	
	public String getAccOwnerName() {
		return this.driverUtil.getText(By.xpath(pOwnerNameXPath));
	}
	
	
	public String getAccDOB() {
		return this.driverUtil.getText(By.xpath(pDOBXPath));
	}
	
	public String getPersonalAccordionDOBOwner2() {
		return this.driverUtil.getText(By.xpath(personalAccordionDOBOwner2Xpath));
	}
	
	public String getAccAddress() {
		return this.driverUtil.getText(By.xpath(pAddressXPath));
	}
	
	public String getPersonalAccordionAddressOwner2() {
		return this.driverUtil.getText(By.xpath(personalAccordionAddressOwner2XPath));
	}
	
	public String getAccCity() {
		return this.driverUtil.getText(By.xpath(pCityXPath));
	}
	
	public String getPersonalAccordionCityOwner2() {
		return this.driverUtil.getText(By.xpath(personalAccordionCityOwner2XPath));
	}
	
	public String getAccProvince() {
		return this.driverUtil.getText(By.xpath(pProvinceXPath));
	}
	
	public String getPersonalAccordionProvinceOwner2() {
		return this.driverUtil.getText(By.xpath(personalAccordionProvinceOwner2XPath));
	}
	
	public String getAccPostalCode() {
		return this.driverUtil.getText(By.xpath(pPostalCodeXPath));
	}
	
	public String getPersonalAccordionPostalCodeOwner2() {
		return this.driverUtil.getText(By.xpath(personalAccordionPostalCodeOwner2XPath));
	}
	
	//Tax Residence Accordion
	public WebElement getTaxAccordion() {
		return this.driverUtil.getWebElement(By.xpath(spanTaxContactInformationXPath));
	}
	public WebElement getTaxAccordionOwner2() {
		return this.driverUtil.getWebElement(By.xpath(owner2TaxAccordionInformationXPath));
	}
	
	public String getAccTaxResidentCAN() {
		return this.driverUtil.getText(By.xpath(pTaxResidentCAXPath));
	}
	
	public String getAccTaxResidentCANOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionTaxResidentCanadaXPath));
	}
	
	public String getAccSINProvided() {
		return this.driverUtil.getText(By.xpath(pSINProvidedXPath));
	}
	
	public String getAccSINProvidedOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionSINProvidedXPath));
	}
	
	public String getAccSIN() {
		return this.driverUtil.getText(By.xpath(pSINXPath));
	}
	
	public String getAccSINOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionSINXPath));
	}
	
	public String getAccNoSINReason() {
		return this.driverUtil.getText(By.xpath(pNoSINReasonXPath));
	}
	
	public String getAccNoSINReasonOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionNoSINReasonXPath));
	}
	
	public String getAccTaxResidentUS() {
		return this.driverUtil.getText(By.xpath(pTaxResidentUSXPath));
	}
	
	public String getAccTaxResidentUSOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionTaxResidentUSXPath));
	}
	
	public String getAccSSNProvided() {
		return this.driverUtil.getText(By.xpath(pSSNProvidedXPath));
	}
	

	public String getAccSSNProvidedOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionSSNProvidedXPath));
	}
	
	public String getAccSSN() {
		return this.driverUtil.getText(By.xpath(pSSNXPath));
	}
	
	public String getAccSSNOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionSSNXPath));
	}
	
	public String getAccTaxResidentOther() {
		return this.driverUtil.getText(By.xpath(pTaxResidentOtherXPath));
	}
	
	public String getAccTaxResidentOtherOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionTaxResidentOtherXPath));
	}
	
	public String getAccJurisdiction() {
		return this.driverUtil.getText(By.xpath(pJurisdictionXPath));
	}
	
	public String getAccJurisdictionOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionJurisdictionXPath));
	}
	
	public String getAccTINProvided() {
		return this.driverUtil.getText(By.xpath(pTINProvidedXPath));
	}
	
	public String getAccTINProvidedOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionTINProvidedXPath));
	}
	
	public String getAccTIN() {
		return this.driverUtil.getText(By.xpath(pTINXPath));
	}
	
	public String getAccTINOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionTINXPath));
	}
	
	public String getAccNoTINReason() {
		return this.driverUtil.getText(By.xpath(pNoTINReasonXPath));
	}
	
	public String getAccNoTINReasonOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionNoTINReasonXPath));
	}
	
	public String getAccNoTINSpecificReason() {
		return this.driverUtil.getText(By.xpath(pNoTINSpecificReasonXPath));
	}
	
	public String getAccNoTINSpecificReasonOwner2() {
		return this.driverUtil.getText(By.xpath(owner2TaxAccordionNoTINSpecificReasonXPath));
	}
	
	
	//Label Validation
	public void validateModalLabels(String[] labelKeys, Map<String,String> data) {
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.validateLabels(labelKeys, data, divModalXPath);
	}
	
	public void validateOwnerPersonalLabels(String[] labelKeys, Map<String,String> data) {
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.validateLabels(labelKeys, data, divOwnerPersonalInfoXPath);
	}
	
	//Next Button
	public void clickNext() {
		this.driverUtil.click(By.xpath(buttonNextXPath));
	}
	
	//Employment Information Overlay page
	public void selectEmploymentStatus(String employmentStatus) {
		this.driverUtil.click(By.cssSelector(employmentStatusDivCssSelector));		
		this.driverUtil.getWebElement(By.xpath(employmentStatusUlXpath))
				.findElement(By.xpath("//li[contains(text(),'" + employmentStatus + "')]")).click();
	}
	
	public WebElement getSoleProprietorYesButtton() {
		return this.driverUtil.getWebElement(By.xpath(soleProprietorYesXpath));
	}
	
	public WebElement getSoleProprietorNoButtton() {
		return this.driverUtil.getWebElement(By.xpath(soleProprietorNoXpath));
	}
	
public WebElement getSoleProprietor(String str) {
		
		String useXPath = (str.equals("Yes")) ?  soleProprietorYesXpath: (str.equals("No")) ?  soleProprietorNoXpath: "Error";
		
		
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public void selectIndustry(String industry) {
		this.driverUtil.click(By.xpath(employmentIndustryDivID));
		this.driverUtil.getWebElement(By.xpath(employmentIndustryDropDownValueID))
				.findElement(By.xpath("//li[contains(text(),'" + industry + "')]")).click();
	}
	
	public void selectOccupation(String occupation) {
		this.driverUtil.click(By.xpath(currentOccupationDivXpath));
		this.driverUtil.getWebElement(By.xpath(currentOccupationDropdownXpath))
				.findElement(By.xpath("//li[contains(text(),'" + occupation + "')]")).click();
	}
	
	public WebElement getNameOfYourCompany() {
		return this.driverUtil.getWebElement(By.xpath(nameOfTheCompanyXpath));
	}
	
	//Identity verification overlay page
	
	public void selectIdentificationMethod(String identificationMethod) {
		this.driverUtil.click(By.xpath(identificationMethodDivXpath));
		this.driverUtil.getWebElement(By.xpath(identificationMethodDropdownXpath))
				.findElement(By.xpath("//li[contains(text(),'" + identificationMethod + "')]")).click();
	}
	
	public WebElement getIdentificationDocument(String option) {
		
		String useXPath = (option.equals("Federal")) ?  identificationDocumentFederalXpath: (option.equals("Provincial or Territorial")) ? identificationDocumentProvincialXpath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));		
	}
	
	
	
	public void selectProvinceIssuedIn(String provinceIssuedIn) {
		this.driverUtil.click(By.xpath(provinceIssuedInDivId));
		this.driverUtil.getWebElement(By.xpath(provinceIssuedInDropdownId))
				.findElement(By.xpath("//li[contains(text(),'" + provinceIssuedIn + "')]")).click();
	}
	
	public void selectOriginalDocumentReviewed(String originalDocumentReviewed) {
		this.driverUtil.click(By.xpath(originalDocumentReviewedDivId));
		this.driverUtil.getWebElement(By.xpath(originalDocumentReviewedDropdownId)).findElement(By.xpath("//li[contains(text(),'" + originalDocumentReviewed + "')]")).click();
	}
	
	public WebElement getNameOnTheDocumentFirstName() {
		return this.driverUtil.getWebElement(By.xpath(nameOnTheDocumentFirstNameXpath));
	}
	public WebElement getNameOnTheDocumentMiddleInitial() {
		return this.driverUtil.getWebElement(By.xpath(nameOnTheDocumentMiddleInitialXpath));
	}
	
	public WebElement getNameOnTheDocumentLastName() {
		return this.driverUtil.getWebElement(By.xpath(nameOnTheDocumentLastNameXpath));
	}
	
	public WebElement getIdentifyingNumberOnTheDocument() {
		return this.driverUtil.getWebElement(By.xpath(identifyingNumberOnTheDocumentXpath));
	}
	
	public WebElement getDocumentExpiryDateDay() {
		return this.driverUtil.getWebElement(By.xpath(documentExpiryDateDayXpath));
	}
	public void selectMonthOFDocumentExpiryDate(String monthName) {
		this.driverUtil.click(By.xpath(documentExpiryDateMonthDivXpath));
		this.driverUtil.getWebElement(By.xpath(documentExpiryDateMonthUlXpath))
				.findElement(By.xpath("//li[contains(text(),'" + monthName + "')]")).click();
	}
	public WebElement getDocumentExpiryDateYear() {
		return this.driverUtil.getWebElement(By.xpath(documentExpiryDateYearXpath));
	}
	
	public WebElement getDateInfoVerifiedDay() {
		return this.driverUtil.getWebElement(By.xpath(dateInformationWasVerifiedDayXpath));
	}
	public void selectMonthOFDateInfoVerified(String monthNameOfDateInfoVerified) {
		this.driverUtil.click(By.xpath(dateInformationWasVerifiedMonthDivXpath));
		this.driverUtil.getWebElement(By.xpath(dateInformationWasVerifiedMonthUlXpath))
				.findElement(By.xpath("//li[contains(text(),'" + monthNameOfDateInfoVerified + "')]")).click();
	}
	
	public WebElement getDateInfoVerifiedYear() {
		return this.driverUtil.getWebElement(By.xpath(dateInformationWasVerifiedYearXpath));
	}
	
	public void selectFederalOriginalDocumentReviewed(String originalDocuReviewed) {
		this.driverUtil.click(By.xpath(federalOriginalDocumentReviewedDivXpath));
		this.driverUtil.getWebElement(By.xpath(federalOriginalDocumentReviewedUlXpath))
				.findElement(By.xpath("//li[contains(text(),'" + originalDocuReviewed + "')]")).click();
	}
	
	public WebElement getAccordionofIndentityXapth(int id)
	{
		
		return this.driverUtil.getWebElements(By.xpath(AccordionofIndentityXapth)).get(id);
		
	}
	
	
	public String getLableEmploymentStatus()
	{
		return this.driverUtil.getWebElement(By.xpath(EmpStatusLabelXpath)).getText();	
	}
	public String getLableIndustry()
	{
		return this.driverUtil.getWebElement(By.xpath(industryLabelXpath)).getText();
	}
	public String getLableOccupation()
	{
		return this.driverUtil.getWebElement(By.xpath(occupationLabelXpath)).getText();
	}
	public String getLableCompanyName()
	{
		return this.driverUtil.getWebElement(By.xpath(nameOfCompanyLabelXpath)).getText()	;
				
	}
	
	public String getLabelOfIdentificationMethod()
	{
		return this.driverUtil.getWebElement(By.xpath(labelOfIdentificationMethodXpath)).getText();			
	
	}
	public String getLabelOfIdentificationDocument()
	{
		return this.driverUtil.getWebElement(By.xpath(labelOfIdentificationDocumentXpath)).getText();
				
	
	}
	public String getLabelOfProvinceIssuedIn()
	{
		return this.driverUtil.getWebElement(By.xpath(labelOfProvinceIssuedInXpath)).getText();
				
	
	}
	public WebElement getErrorLableOfEmpStatus() {
		return this.driverUtil.getWebElement(By.xpath(empStatusErrorLabelXpath));
	}
	public WebElement getErrorLableOfSoleProprietor() {
		return this.driverUtil.getWebElement(By.xpath(soleProprietorErrorLabelXpath));
	}
	public WebElement getErrorLableOfIndustry() {
		return this.driverUtil.getWebElement(By.xpath(industryErrorLabelXpath));
	}
	public WebElement getErrorLableOfOccupation() {
		return this.driverUtil.getWebElement(By.xpath(occupationErrorLabelXpath));
	}
	public WebElement getErrorLableOfNameOfYourCompany() {
		return this.driverUtil.getWebElement(By.xpath(nameOfCompanyErrorLabelXpath));
	}
	
	public WebElement getErrorLableOfIdentificationmethod() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfIdentificationmethodXpath));
	}
	public WebElement getErrorLableOfIdentificationDocument() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfIdentificationDocumentXpath));
	}
	public WebElement getErrorLableOfProvinceIssuedIn() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfProvinceIssuedInXpath));
	}
	public WebElement getErrorLableOfOriginaldocumentreviewed() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfOriginaldocumentreviewedXpath));
	}
	public WebElement getErrorLableOfNameOnTheDocumentFirstName() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfNameOnTheDocumentFirstNameXpath));
	}
	public WebElement getErrorLableOfNameOnTheDocumentLastName() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfNameOnTheDocumentLastNameXpath));
	}
	public WebElement getErrorLableOfDocumentexpirydate() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfDocumentexpirydateXpath));
	}
	public WebElement getErrorLableOfDateinformationwasverified() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfDateinformationwasverifiedXpath));
	}
	public WebElement getErrorLableOfIdentifyingNumberOnTheDoc() {
		return this.driverUtil.getWebElement(By.xpath(errorLableOfIdentifyingNumberOnTheDocXpath));
	}
	
	public WebElement accordinanEmpStatusLable()
	{
		return this.driverUtil.getWebElement(By.xpath(accordinanEmpStatusValueXpath));
	}
	
	public WebElement AccordionSolePropritorLabel()
	{
		return this.driverUtil.getWebElement(By.id(AccordionSolePropritorId));
	}
	
	public WebElement accordinanCurrentIndustryLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(accordinanCurrentIndurstyValueXpath));
	}
	public WebElement AccordionCurrentOccupationLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionCurrentOccupationValueXpath));
	}
	
	public WebElement AccordionCompanyNameLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionCompanyNameValueXpath));
	}
	
	
	public WebElement AccordionPlusButton()
	{
		return this.driverUtil.getWebElement(By.xpath(empInfoAccordionXapth));
	}
	
	public WebElement accodianPlusIconIdentityVerification()
	{
		return this.driverUtil.getWebElement(By.xpath(accodianIdentityVerificationXpath));
		
	}
	public WebElement AccordionIdentityMethodLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionIdentificationMethodValueXpath));
		
	}
	public WebElement AccordionIdentitydocumentLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionIdentificationdocumentValueXpath));
		
	}
	public WebElement AccordionIdentityProvinceLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionIdentityProvinceIssuedInValueXpath));
		
	}
	public WebElement AccordionIdentityOriginalDocLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionIdentityOriginalDocValueXpath));
		
	}
	public WebElement AccordionIdentityNameOnDocumentLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionIdentityNameOnDocumentValueXpath));
		
	}
	public WebElement AccordionIdentityNumberLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionIdentifyingNumOnDocValueXpath));
		
	}
	public WebElement AccordionIdentityDocExpiryDateLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionIdentityDocExpiryDateValueXpath));
		
	}
	public WebElement AccordionIdentityDocVerifiedDateLabel()
	{
		return this.driverUtil.getWebElement(By.xpath(AccordionIdentityDocVerifiedDateValueXpath));
		
	}
	String backButonXpath="//*[@id='back-btn']";
	
	public WebElement getBackButon(Integer buttonNumber) {
		return this.driverUtil.getWebElements(By.xpath(backButonXpath)).get(buttonNumber);
	}
private	String addOwner2ButtonId="add-owner-button";
	public WebElement getAddOwner2Button() {
		return this.driverUtil.getWebElement(By.id(addOwner2ButtonId));
	}
	
	public String formatXpath(String value, String index)
	{
		String xapth=String.format(value, index);
		return xapth;
		
	}
	
	
	
	
}