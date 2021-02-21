package term_conversion;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Map;
import org.junit.Assert;
import org.openqa.selenium.ElementNotSelectableException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

public class OwnerInformationTest extends BaseTest {

	public void fillPagesUntilOwnerPage(Map<String, String> data) {
		FillPages fillPage = new FillPages(driverUtil);
		// fillPage.fillLogin();
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();

		fillPage.fillPolicyPage(data);
		fillPage.fillInsuredPage(data);
		if (data.get("OwnerType").equalsIgnoreCase("Owner2")) {
			fillFirstOwner(fillPage, data);
		}
		

	}

	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en", "policyInfoUrl");
	}

	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");

	@DataProvider(name = "ownerInfoTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception {
		// Getting the Data Row against the Test Case name and store it within an array
		Object[][] testObjArray = excelUtil.getAllMatchingTestCases("ownerInfoData", method.getName());
		return (testObjArray);
	}

	public void fillFirstOwner(FillPages fillPage, Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		String insuredInfo_IsOwnerInsured = data.get("insuredInfo_IsOwnerInsured");
		String insuredInfo_IsOwnerInsuredTwo = data.get("insuredInfo_IsOwnerInsured2");
		String coverageType = data.get("policyInfo_CoverageType");
		String firstNameTestStr = data.get("ownerInfo_FirstName");
		String lastNameTestStr = data.get("ownerInfo_LastName");
		String middleNameTestStr = data.get("ownerInfo_MiddleName");
		String relationshipTestStr = data.get("ownerInfo_Relationship");
		

		if (insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes") || insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("Yes") ) {
			ownerInfo.getEnterOwnerInfo().click();

		} else {

			ownerInfo.getIndivOwnerFirstName("0").sendKeys(firstNameTestStr);
			ownerInfo.getIndivOwnerLastName("0").sendKeys(lastNameTestStr);
			ownerInfo.getIndivOwnerMiddleInitial("0").sendKeys(middleNameTestStr);
			ownerInfo.selectRelationshipToInsured(relationshipTestStr, "0");
			if(data.get("ownerInfo_Relationship").equalsIgnoreCase("Other"))
			{
				ownerInfo.getOtherRelationship("0").sendKeys(data.get("ownerInfo_OtherRelationship"));
			}

			ownerInfo.getEnterOwnerInfo().click();

			ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("ownerInfo_Day"));
			ownerInfo.selectIndivOwnerBirthMonth(data.get("ownerInfo_Month"), "0");
			ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("ownerInfo_Year"));
		}

		ownerInfo.getAddress1().sendKeys(data.get("ownerInfo_Address1"));
		ownerInfo.getAddress2().sendKeys(data.get("ownerInfo_Address2"));
		ownerInfo.getCity().sendKeys(data.get("ownerInfo_City"));
		ownerInfo.selectProvince(data.get("ownerInfo_Province"));
		ownerInfo.getPostalCode().sendKeys(data.get("ownerInfo_PostalCode"));

		ownerInfo.getContinue().click();
		// Tax residence information
		fillingTaxResidenceInfoForOwner1(data);
		ownerInfo.getContinue().click();
		
		// Enter Owner1 Employment information
		
		fillingEmploymentInfoForOwner1(data);
		

	}
	public void fillingTaxResidenceInfoForOwner1(Map<String, String> data) {
		
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		// Tax residence information
		// Tax Resident Canada
				String canadaTaxResident = data.get("OwnerInfo_CanadaTaxResident");
				String providingSIN = data.get("OwnerInfo_ProvidingSIN");

				String usTaxResident = data.get("OwnerInfo_USTaxResident");
				String usProvidingSSN = data.get("OwnerInfo_ProvidingSSN");
				String ssnNumberForUS = data.get("ownerInfo_SSN");

				String countryJurisdiction = data.get("ownerInfo_Jurisdiction");
				String taxResidentOfJurisdiction = data.get("OwnerInfo_JurisdictionTaxResident");
				String providingTINYesOrNo = data.get("OwnerInfo_ProvidingTIN");
				String inputTIN = data.get("ownerInfo_TIN");
				String reasonForNotProvidingTIN = data.get("OwnerInfo_DropdownReasonForNotProvidingTIN");
				String otherTINReason = data.get("ownerInfo_OtherTINReason");

				ownerInfo.getCATaxResident(canadaTaxResident, "0").click();
				if (canadaTaxResident.equalsIgnoreCase("Yes")) {

					ownerInfo.getSINProvided(providingSIN).click();
					if (providingSIN.equalsIgnoreCase("Yes")) {
						ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));

					} else {

						ownerInfo.getNoSINReason().sendKeys(data.get("ownerInfo_SINReason"));

					}

				}

				ownerInfo.getUSTaxResident(usTaxResident).click();
				if (usTaxResident.equalsIgnoreCase("Yes")) {

					ownerInfo.getSSNProvided(usProvidingSSN).click();

					if (usProvidingSSN.equalsIgnoreCase("Yes")) {
						ownerInfo.getSSN().sendKeys(ssnNumberForUS);
					}

				}

				ownerInfo.getOtherTaxResident(taxResidentOfJurisdiction).click();
				if (taxResidentOfJurisdiction.equalsIgnoreCase("Yes")) {

					ownerInfo.selectJurisdiction(countryJurisdiction);
					ownerInfo.getTINProvided(providingTINYesOrNo).click();
					if (providingTINYesOrNo.equalsIgnoreCase("Yes")) {

						ownerInfo.getTIN().sendKeys(inputTIN);
					} else if (providingTINYesOrNo.equalsIgnoreCase("No")) {
						ownerInfo.selectReasonNoTIN(reasonForNotProvidingTIN);
						if (reasonForNotProvidingTIN.equalsIgnoreCase("Other")) {
							ownerInfo.getSpecifyReasonNoTIN().sendKeys(otherTINReason);
						}

					}

				}
			}
	public void fillingEmploymentInfoForOwner1(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		
		// Enter Owner1 Employment information
				ownerInfo.selectEmploymentStatus(data.get("OwnerInfo_EmpStatus"));
				if (data.get("OwnerInfo_EmpStatus").equalsIgnoreCase("Never been employed")) {
					ownerInfo.getContinue().click();
					fillingIdentityVerificationOverlayPageForOwner1(data);

				} else if (data.get("OwnerInfo_EmpStatus").equalsIgnoreCase("Self-employed")) {
					ownerInfo.getSoleProprietor(data.get("OwnerInfo_SoleProprietor")).click();
				}

				ownerInfo.selectIndustry(data.get("OwnerInfo_EmpIndustry"));
				ownerInfo.selectOccupation(data.get("OwnerInfo_Occupation"));
				ownerInfo.getNameOfYourCompany().sendKeys(data.get("OwnerInfo_NameOfCompany"));
				ownerInfo.getContinue().click();
				fillingIdentityVerificationOverlayPageForOwner1(data);
		
	}

	public void fillingIdentityVerificationOverlayPageForOwner1(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		// Identity Verification overlay page
		String identificationMethod = data.get("OwnerInfo_IdentificationMethod");
		Assert.assertTrue("Fail: We did not find the label Identification method",
				ownerInfo.getLabelOfIdentificationMethod().equalsIgnoreCase("Identification method"));
		ownerInfo.selectIdentificationMethod(identificationMethod);
		switch (identificationMethod.toUpperCase()) {
		case "SINGLE METHOD":
			Assert.assertTrue("Fail: We did not find the label Identification document",
					ownerInfo.getLabelOfIdentificationDocument()
							.equalsIgnoreCase("Identification document - jurisdiction of issue"));
			ownerInfo.getIdentificationDocument(data.get("OwnerInfo_IdentificationDocument")).click();
			if (!data.get("OwnerInfo_IdentificationDocument").equalsIgnoreCase("Federal")) {
				Assert.assertTrue("Fail: we did not find the label province issued in",
						ownerInfo.getLabelOfProvinceIssuedIn().equalsIgnoreCase("Province issued in"));
				
				ownerInfo.selectProvinceIssuedIn(data.get("OwnerInfo_ProvinceIssuedIn"));
			}

			ownerInfo.selectOriginalDocumentReviewed(data.get("OwnerInfo_OriginalDocReviewed"));
             ownerInfo.getNameOnTheDocumentFirstName().sendKeys(data.get("ownerInfo_FirstName"));
			ownerInfo.getNameOnTheDocumentMiddleInitial().sendKeys(data.get("ownerInfo_MiddleName"));
			ownerInfo.getNameOnTheDocumentLastName().sendKeys(data.get("ownerInfo_LastName"));
			ownerInfo.getIdentifyingNumberOnTheDocument().sendKeys(data.get("Identifying_Number"));
			ownerInfo.getDocumentExpiryDateDay().sendKeys(data.get("DocExpiryDate_Day"));
			ownerInfo.selectMonthOFDocumentExpiryDate(data.get("DocExpiryDate_Month"));

			ownerInfo.getDocumentExpiryDateYear().sendKeys(data.get("DocExpiryDate_Year"));
			ownerInfo.getDateInfoVerifiedDay().sendKeys(data.get("DateInfoVerified_Day"));
			ownerInfo.selectMonthOFDateInfoVerified(data.get("DateInfoVerified_Month"));
			ownerInfo.getDateInfoVerifiedYear().sendKeys(data.get("DateInfoVerified_Year"));

			ownerInfo.getSaveOwnerInfo().click();
			break;

		case "DUAL METHOD":
		case "CLIENT CREDIT FILE":
			ownerInfo.getSaveOwnerInfo().click();
			break;
		default:
			throw new ElementNotSelectableException(
					"selected option is not present in the Identification method dropdown");

		}

	}

	public boolean verifyIndividualOwnerFieldsArePresent(OwnerInformationPage ownerInfo, Map<String, String> data,
			String ownereType) {
		boolean result = false;

		String firstNameTestStr = data.get("ownerInfo_FirstName");
		String lastNameTestStr = data.get("ownerInfo_LastName");
		String middleNameTestStr = data.get("ownerInfo_MiddleName");
		String otherRelationshipTestStr = data.get("ownerInfo_OtherRelationship");

		// Name Fields
		ownerInfo.getIndivOwnerFirstName("0").sendKeys(firstNameTestStr);
		ownerInfo.getIndivOwnerLastName("0").sendKeys(lastNameTestStr);
		ownerInfo.getIndivOwnerMiddleInitial("0").sendKeys(middleNameTestStr);
		ownerInfo.selectRelationshipToInsured("Other", "0");
		ownerInfo.getOtherRelationship("0").sendKeys(otherRelationshipTestStr);
		if(ownerInfo.getEnterOwnerInfo().isEnabled())
		{
			ownerInfo.getEnterOwnerInfo().click();
			ownerInfo.getEnterOwnerInfo().click();
		}
		
		Assert.assertEquals("Fail:" + ownereType + "Relationship field should show selected value", "Other",
				ownerInfo.getRelationship("0").getText());
		Assert.assertTrue(
				"Fail:" + ownereType + " Other Relationship field should be visible when relationship is 'Other'",
				ownerInfo.isSpecifyRelationshipVisible("0"));
		Assert.assertEquals("Fail:" + ownereType + " Other Relationship Owner has incorrect maxlength", "50",
				ownerInfo.getOtherRelationship("0").getAttribute("maxlength"));
		
		Assert.assertEquals("Fail:" + ownereType + " Other Relationship Owner should be alphanumeric",
				otherRelationshipTestStr, ownerInfo.getOtherRelationship("0").getAttribute("value"));
		
		Assert.assertEquals("Fail:" + ownereType + "Indiv Owner First Name has incorrect maxlength", "25",
				ownerInfo.getIndivOwnerFirstName("0").getAttribute("maxlength"));
		Assert.assertEquals("Fail:" + ownereType + "Indiv Owner Last Name has incorrect maxlength", "25",
				ownerInfo.getIndivOwnerLastName("0").getAttribute("maxlength"));
		Assert.assertEquals("Fail:" + ownereType + " Indiv Owner Middle Name has incorrect maxlength", "25",
				ownerInfo.getIndivOwnerMiddleInitial("0").getAttribute("maxlength"));
		Assert.assertEquals("Fail:" + ownereType + "Indiv Owner First Name should be alphanumeric", firstNameTestStr,
				ownerInfo.getIndivOwnerFirstName("0").getAttribute("value"));
		Assert.assertEquals("Fail:" + ownereType + "Indiv Owner Last Name should be alphanumeric", lastNameTestStr,
				ownerInfo.getIndivOwnerLastName("0").getAttribute("value"));
		Assert.assertEquals("Fail:" + ownereType + " Indiv Owner Middle Name should be alphanumeric", middleNameTestStr,
				ownerInfo.getIndivOwnerMiddleInitial("0").getAttribute("value"));

		// Verify that modal opens
		
		Assert.assertTrue("Fail:" + ownereType + " Individual Owner Modal should appear when enter owner info button is clicked",ownerInfo.isModalVisible());
		result = true;
		return result;

	}
	
	public boolean verifyIndividualOwnerFieldsArePresentForOwner2(OwnerInformationPage ownerInfo, Map<String, String> data,
			String ownereType) {
		boolean result = false;

		String firstNameTestStrOwner2 = data.get("owner2_FirstName");
		String lastNameTestStrOwner2 = data.get("owner2_LastName");
		String middleNameTestStrOwner2 = data.get("owner2_MiddleInitial");
		String otherRelationshipTestStrOwner2 = data.get("owner2_Relationship");
		

		// Name Fields
		ownerInfo.getIndivOwnerFirstName("1").sendKeys(firstNameTestStrOwner2);
		ownerInfo.getIndivOwnerLastName("1").sendKeys(lastNameTestStrOwner2);
		ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(middleNameTestStrOwner2);
		ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
		if(data.get("owner2_Relationship").equalsIgnoreCase("Other"))
		{
			ownerInfo.getOtherRelationship("1").sendKeys(data.get("Owner2_OtherRelationship"));
		}

			
		
		
		Assert.assertEquals("Fail:" + ownereType + "Relationship field should show selected value", data.get("owner2_Relationship"),
				ownerInfo.getRelationship("1").getText());
		Assert.assertTrue(
				"Fail:" + ownereType + " Other Relationship field should be visible when relationship is 'Other'",
				ownerInfo.isSpecifyRelationshipVisible("1"));
		Assert.assertEquals("Fail:" + ownereType + " Other Relationship Owner has incorrect maxlength", "50",
				ownerInfo.getOtherRelationship("1").getAttribute("maxlength"));
		
		Assert.assertEquals("Fail:" + ownereType + " Other Relationship Owner should be alphanumeric",
				data.get("Owner2_OtherRelationship"), ownerInfo.getOtherRelationship("1").getAttribute("value"));
		
		Assert.assertEquals("Fail:" + ownereType + "Indiv Owner First Name has incorrect maxlength", "25",
				ownerInfo.getIndivOwnerFirstName("1").getAttribute("maxlength"));
		Assert.assertEquals("Fail:" + ownereType + "Indiv Owner Last Name has incorrect maxlength", "25",
				ownerInfo.getIndivOwnerLastName("1").getAttribute("maxlength"));
		Assert.assertEquals("Fail:" + ownereType + " Indiv Owner Middle Name has incorrect maxlength", "25",
				ownerInfo.getIndivOwnerMiddleInitial("1").getAttribute("maxlength"));
		Assert.assertEquals("Fail:" + ownereType + "Indiv Owner First Name should be alphanumeric", firstNameTestStrOwner2,
				ownerInfo.getIndivOwnerFirstName("1").getAttribute("value"));
		Assert.assertEquals("Fail:" + ownereType + "Indiv Owner Last Name should be alphanumeric", lastNameTestStrOwner2,
				ownerInfo.getIndivOwnerLastName("1").getAttribute("value"));
		Assert.assertEquals("Fail:" + ownereType + " Indiv Owner Middle Name should be alphanumeric", middleNameTestStrOwner2,
				ownerInfo.getIndivOwnerMiddleInitial("1").getAttribute("value"));

		// Verify that modal opens
		
		Assert.assertTrue("Fail:" + ownereType + " Individual Owner Modal should appear when enter owner info button is clicked",ownerInfo.isModalVisible());
		result = true;
		return result;

	}

	@Test(priority = 3, dataProvider = "ownerInfoTestData", testName = "Individual Owner Name and Relationship Information", groups = "webBrowser")
	public void verifyIndividualOwnerFields(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		verifyIndividualOwnerFieldsArePresent(ownerInfo, data, "Owner1");

	}

	public boolean verifyIndividualOwnerOverlayFieldsArePresent(OwnerInformationPage ownerInfo,
			Map<String, String> data, String ownereType) {
		String index = "";
		String Address="";
		switch(ownereType){
			case "Owner1" :
				index = "0";
				Address = "ownerInfo_Address1";
			break;
			
			case "Owner2" :
				index= "1";
			    Address = "Owner2_Address1";
			break;
			 
		}
		
		//String dayDOB= ownereType.equalsIgnoreCase("Owner1") ? "ownerInfo_Day" : 
		boolean result = false;
		String insuredInfo_IsOwnerInsured = data.get("insuredInfo_IsOwnerInsured");
		String firstNameTestStr = data.get("ownerInfo_FirstName");
		String lastNameTestStr = data.get("ownerInfo_LastName");
		String middleNameTestStr = data.get("ownerInfo_MiddleName");
		String relationshipTestStr = data.get("ownerInfo_Relationship");

		// Fill Data until Modal
		
		
		if (insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes")) {
			ownerInfo.getEnterOwnerInfo().click();

		} else {

			ownerInfo.getIndivOwnerFirstName("0").sendKeys(firstNameTestStr);
			ownerInfo.getIndivOwnerLastName("0").sendKeys(lastNameTestStr);
			ownerInfo.getIndivOwnerMiddleInitial("0").sendKeys(middleNameTestStr);
			ownerInfo.selectRelationshipToInsured(relationshipTestStr, "0");
			if(data.get("ownerInfo_Relationship").equalsIgnoreCase("Other"))
			{
				ownerInfo.getOtherRelationship("0").sendKeys(data.get("ownerInfo_OtherRelationship"));
			}

			ownerInfo.getEnterOwnerInfo().click();

			ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("ownerInfo_Day"));
			// DOB fields
			Assert.assertArrayEquals("Fail:" + ownereType + " Month dropdown options are incorrect",
					data.get("ownerInfo_MonthOptionsEN").split(";"), ownerInfo.getMonthOptions("0"));
			ownerInfo.selectIndivOwnerBirthMonth(data.get("ownerInfo_Month"), "0");
			ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("ownerInfo_Year"));
			Assert.assertEquals("Fail:" + ownereType + " Birth Day should accept numeric values", data.get("ownerInfo_Day"),
					ownerInfo.getIndivOwnerBirthDay().getAttribute("value"));
			Assert.assertEquals("Fail:" + ownereType + " Birth Month value should be the same as the one selected",
					data.get("ownerInfo_Month"), ownerInfo.getIndivOwnerBirthMonth("0").getText());
			Assert.assertEquals("Fail:" + ownereType + " Birth Year should accept numeric values",
					data.get("ownerInfo_Year"), ownerInfo.getIndivOwnerBirthYear().getAttribute("value"));
		}
		Assert.assertArrayEquals("Fail:" + ownereType + " Province dropdown options are incorrect",
				data.get("ownerInfo_ProvinceEN").split(";"), ownerInfo.getProvinceOptions());

		ownerInfo.getAddress1().sendKeys(data.get(Address));
		ownerInfo.getAddress2().sendKeys(data.get("ownerInfo_Address2"));
		ownerInfo.getCity().sendKeys(data.get("ownerInfo_City"));
		ownerInfo.selectProvince(data.get("ownerInfo_Province"));
		ownerInfo.getPostalCode().sendKeys(data.get("ownerInfo_PostalCode"));
		Assert.assertEquals("Fail:" + ownereType + " Address 1 fields is alphanumeric", data.get("ownerInfo_Address1"),
				ownerInfo.getAddress1().getAttribute("value"));
		Assert.assertEquals("Fail:" + ownereType + " Address 2 fields is alphanumeric", data.get("ownerInfo_Address2"),
				ownerInfo.getAddress2().getAttribute("value"));
		Assert.assertEquals("Fail:" + ownereType + " City should be alphanumeric", data.get("ownerInfo_City"),
				ownerInfo.getCity().getAttribute("value"));
		Assert.assertEquals("Fail:" + ownereType + " Province should be alphanumeric", data.get("ownerInfo_Province"),
				ownerInfo.getProvince().getText());
		Assert.assertEquals("Fail:" + ownereType + " Postal code should accept values with the format x9x9x9",
				data.get("ownerInfo_PostalCode"), ownerInfo.getPostalCode().getAttribute("value"));


		ownerInfo.getContinue().click();
		 // Tax Residence Form
		// Tax Resident Canada
		
		ownerInfo.getCATaxResident("No", "1").click();
		Assert.assertTrue("Fail:" + ownereType + " Tax Resident Canada = 'No' field should be selectable",
				ownerInfo.getCATaxResidentInput("No").getAttribute("aria-pressed").contains("true"));
		Assert.assertFalse(
				"Fail:" + ownereType
						+ " SIN Provided boolean field should not be visible for Tax Resident Canada = 'No'",
				ownerInfo.isSINProvidedVisible());
		ownerInfo.getCATaxResident("Yes", "1").click(); // SIN Provided boolean field is displayed
		Assert.assertTrue("Fail:" + ownereType + " Tax Resident Canada = 'Yes' field should be selectable",
				ownerInfo.getCATaxResidentInput("Yes").getAttribute("aria-pressed").contains("true"));
		Assert.assertTrue(
				"Fail:" + ownereType + " SIN Provided boolean field should be visible for Tax Resident Canada = 'Yes'",
				ownerInfo.isSINProvidedVisible());
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		Assert.assertTrue("Fail:" + ownereType + " SIN provided = 'Yes' should be selectable",
				ownerInfo.getSINProvidedInput("Yes").isEnabled());
		Assert.assertTrue("Fail:" + ownereType + " SIN field should be visible when SIN Provided field = 'Yes'",
				ownerInfo.isSINVisible());

		ownerInfo.getSIN().sendKeys("ABC");
		Assert.assertEquals("Fail:" + ownereType + " SIN field should only accept numeric values", "",
				ownerInfo.getSIN().getAttribute("value"));

		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		Assert.assertEquals("Fail:" + ownereType + " SIN field should accept numeric values", data.get("ownerInfo_SIN"),
				ownerInfo.getSIN().getAttribute("value"));
		ownerInfo.getSINProvided("No").click(); // Reason for not providing SIN field is displayed
		Assert.assertTrue("Fail:" + ownereType + " SIN provided = 'No' should be selectable",
				ownerInfo.getSINProvidedInput("No").isDisplayed());
		Assert.assertTrue(
				"Fail:" + ownereType + " Reason for not providing SIN field is present when SIN Provided field = 'No'",
				ownerInfo.isNoSINReasonVisible());
		ownerInfo.getNoSINReason().sendKeys(data.get("ownerInfo_SINReason"));
		Assert.assertEquals("Fail:" + ownereType + " Reason for not providing SIN should be alphanumeric",
				data.get("ownerInfo_SINReason"), ownerInfo.getNoSINReason().getAttribute("value"));

		// Tax Resident USA
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getUSTaxResident("No").click(); // No additional fields are displayed
		Assert.assertTrue("Fail:" + ownereType + " Tax Resident US = 'No' field should be selectable",
				ownerInfo.getUSTaxResident("No").getAttribute("aria-pressed").contains("true"));
		Assert.assertFalse(
				"Fail:" + ownereType + " SSN Provided boolean field should not be visible for Tax Resident US = 'No'",
				ownerInfo.isSSNProvidedVisible());

		ownerInfo.getUSTaxResident("Yes").click(); // SSN Provided boolean field is displayed
		Assert.assertTrue("Fail:" + ownereType + " Tax Resident US = 'Yes' field should be selectable",
				ownerInfo.getUSTaxResidentButton("Yes").getAttribute("aria-pressed").contains("true"));

		Assert.assertTrue(
				"Fail:" + ownereType + " SSN Provided boolean field should be visible for Tax Resident US = 'Yes'",
				ownerInfo.isSSNProvidedVisible());

		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		Assert.assertTrue("Fail:" + ownereType + " SSN provided = 'Yes' should be selectable",
				ownerInfo.getSSNProvidedInput("Yes").getAttribute("aria-pressed").contains("true"));

		Assert.assertTrue("Fail:" + ownereType + " SSN field should be visible when SSN Provided field = 'Yes'",
				ownerInfo.isSSNTextboxVisible());

		ownerInfo.getSSN().sendKeys("ABC");
		Assert.assertEquals("Fail:" + ownereType + " SSN field should only accept numeric values", "",
				ownerInfo.getSSN().getAttribute("value"));
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		Assert.assertEquals("Fail:" + ownereType + " SSN field should accept numeric values", data.get("ownerInfo_SSN"),
				ownerInfo.getSSN().getAttribute("value"));
		ownerInfo.getSSNProvided("No").click(); // No additional fields are displayed
		Assert.assertTrue("Fail:" + ownereType + " SSN provided = 'No' should be selectable",
				ownerInfo.getSSNProvidedInput("No").isEnabled());
		Assert.assertFalse("Fail:" + ownereType + " SSN field should not be visible when SSN Provided field = 'No'",
				ownerInfo.isSSNTextboxVisible());

		// Tax Resident Other
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.getOtherTaxResident("No").click(); // No additional fields are displayed
		Assert.assertTrue("Fail:" + ownereType + " Tax Resident Other = 'No' field should be selectable",
				ownerInfo.getOtherTaxResidentInput("No").isEnabled());
		Assert.assertFalse(
				"Fail:" + ownereType + " Jurisdiction field should not be visible for Tax Residence Other ='No'",
				ownerInfo.isJurisdictionVisible());

		Assert.assertFalse(
				"Fail:" + ownereType + " TIN Provided field should not be visible for Tax Residence Other ='No'",
				ownerInfo.isTINProvidedButtonVisible());
		ownerInfo.getOtherTaxResident("Yes").click(); // Jurisdiction fields and TIN provided field is displayed

		Assert.assertTrue("Fail:" + ownereType + " Tax Resident Other = 'Yes' field should be selectable",
				ownerInfo.getOtherTaxResidentInput("Yes").isEnabled());

		Assert.assertTrue("Fail:" + ownereType + " Jurisdiction field should be visible for Tax Residence Other ='Yes'",
				ownerInfo.isJurisdictionVisible());

		Assert.assertTrue("Fail:" + ownereType + " TIN Provided field should be visible for Tax Residence Other ='Yes'",
				ownerInfo.isTINProvidedButtonVisible());

		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));

		Assert.assertEquals("Fail:" + ownereType + " The user should be able to select a jurisdiction",
				data.get("ownerInfo_Jurisdiction"), ownerInfo.getJurisdiction().getText());

		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		Assert.assertTrue("Fail:" + ownereType + " TIN field should be visible for Providing TIN = 'Yes'",
				ownerInfo.isTINVisible());
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));

		Assert.assertEquals("Fail:" + ownereType + " The TIN field should be alphanumeric", data.get("ownerInfo_TIN"),
				ownerInfo.getTIN().getAttribute("value"));
		ownerInfo.getTINProvided("No").click(); // Reason for not providing TIN is visible

		Assert.assertTrue(
				"Fail:" + ownereType + " Reason for not providing TIN field should be visible for Providing TIN = 'No'",
				ownerInfo.isReasonNoTINVisible());

		Assert.assertArrayEquals("Fail:" + ownereType + " Reason for no TIN provided option dropdown is incorrect",
				data.get("ownerInfo_DropdownReasonNoTINEN").split(";"), ownerInfo.getReasonNoTINOptions());

		ownerInfo.selectReasonNoTIN("Other"); // Specify Reason Field is present

		Assert.assertEquals("Fail:" + ownereType + " Reason for not providing TIN should be displayed when seleted",
				"Other", ownerInfo.getReasonNoTIN().getText());
		Assert.assertTrue("Fail:" + ownereType + " Specify a reason field should be present",
				ownerInfo.isSpecifyNoTINVisible());
		ownerInfo.getSpecifyReasonNoTIN().sendKeys(data.get("ownerInfo_OtherTINReason"));
		Assert.assertEquals("Fail:" + ownereType + " The specify TIN reason should be alphanumeric",
				data.get("ownerInfo_OtherTINReason"), ownerInfo.getSpecifyReasonNoTIN().getAttribute("value"));

		result = true;
		return result;

	}

	@Test(priority = 4, dataProvider = "ownerInfoTestData", testName = "Individual Owner Overlay Fields", groups = "webBrowser")
	public void verifyIndividualOwnerOverlayFields(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		verifyIndividualOwnerOverlayFieldsArePresent(ownerInfo, data, "Owner1");
	}

	@Test(priority = 6, dataProvider = "ownerInfoTestData", testName = "Label Validation - Personal Accordion for Owner1", groups = "webBrowser")
	public void verifyIndividualOwnerPersonalAccordion(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getSaveOwnerInfo().click();
		ownerInfo.getPersonalAccordion().click();
		verifyIndividualOwnerPersonalAccordionValuesArePresent(ownerInfo, data, "OWNER 1");

	}

	public void verifyIndividualOwnerPersonalAccordionValuesArePresent(OwnerInformationPage ownerInfo,
			Map<String, String> data, String ownerType) {
		String ownerFullNameStr = data.get("ownerInfo_FirstName") + " " + data.get("ownerInfo_MiddleName") + " "
				+ data.get("ownerInfo_LastName");
		String ownerDOBStr = data.get("ownerInfo_Day") + "/" + data.get("ownerInfo_Month") + "/"
				+ data.get("ownerInfo_Year");
		String addressStr = data.get("ownerInfo_Address1") + " " + data.get("ownerInfo_Address2");
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion DOB is not displayed correctly", ownerDOBStr,
				ownerInfo.getAccDOB());
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion Address is not displayed correctly", addressStr,
				ownerInfo.getAccAddress());
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion City is not displayed correctly",
				data.get("ownerInfo_City"), ownerInfo.getAccCity());
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion Province is not displayed correctly",
				data.get("ownerInfo_Province"), ownerInfo.getAccProvince());
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion Postal Code is not displayed correctly",
				data.get("ownerInfo_PostalCode"), ownerInfo.getAccPostalCode());

	}
	
	public void verifyPersonalAccordionValuesForOwner2(OwnerInformationPage ownerInfo,
			Map<String, String> data, String ownerType) {
		
		String owner2DOBStr = data.get("owner2_Day") + "/" + data.get("owner2_Month") + "/"
				+ data.get("owner2_Year");
		String owner2addressStr = data.get("Owner2_Address1") + " " + data.get("Owner2_Address2");
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion DOB is not displayed correctly", owner2DOBStr,
				ownerInfo.getPersonalAccordionDOBOwner2());
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion Address is not displayed correctly", owner2addressStr,
				ownerInfo.getPersonalAccordionAddressOwner2());
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion City is not displayed correctly",
				data.get("Owner2_City"), ownerInfo.getPersonalAccordionCityOwner2());
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion Province is not displayed correctly",
				data.get("Owner2_Province"), ownerInfo.getPersonalAccordionProvinceOwner2());
		Assert.assertEquals("Fail:" + ownerType + " Personal Accordion Postal Code is not displayed correctly",
				data.get("Owner2_PostalCode"), ownerInfo.getPersonalAccordionPostalCodeOwner2());

	}

	@Test(priority = 7, dataProvider = "ownerInfoTestData", testName = "Verifying Tax Accordion for Owner1", groups = "webBrowser")
	public void verifyIndividualOwnerTaxAccordion(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getContinue().click();
		//filling the Tax Residence Info for Owner1
		fillingTaxResidenceInfoForOwner1(data);
		ownerInfo.getSaveOwnerInfo().click();
		ownerInfo.getTaxAccordion().click();

		Assert.assertEquals("Fail: Tax Accordion Tax Resident Canada is not displayed correctly", data.get("OwnerInfo_CanadaTaxResident"),
				ownerInfo.getAccTaxResidentCAN());
		Assert.assertEquals("Fail: Tax Accordion SIN Provided is not displayed correctly", data.get("OwnerInfo_ProvidingSIN"),
				ownerInfo.getAccSINProvided());
		Assert.assertEquals("Fail: Tax Accordion SIN is not displayed correctly", data.get("ownerInfo_SIN"),
				ownerInfo.getAccSIN());
		Assert.assertEquals("Fail: Tax Accordion Tax Resident US is not displayed correctly", data.get("OwnerInfo_USTaxResident"),
				ownerInfo.getAccTaxResidentUS());
		Assert.assertEquals("Fail: Tax Accordion SSN Provided is not displayed correctly", data.get("OwnerInfo_ProvidingSSN"),
				ownerInfo.getAccSSNProvided());
		Assert.assertEquals("Fail: Tax Accordion SSN is not displayed correctly", data.get("ownerInfo_SSN"),
				ownerInfo.getAccSSN());
		Assert.assertEquals("Fail: Tax Accordion Tax Resident Other is not displayed correctly", data.get("OwnerInfo_JurisdictionTaxResident"),
				ownerInfo.getAccTaxResidentOther());
		Assert.assertEquals("Fail: Tax Accordion Jurisdiction is not displayed correctly",
				data.get("ownerInfo_Jurisdiction"), ownerInfo.getAccJurisdiction());
		Assert.assertEquals("Fail: Tax Accordion TIN provided is not displayed correctly", data.get("OwnerInfo_ProvidingTIN"),
				ownerInfo.getAccTINProvided());
		//Assert.assertEquals("Fail: Tax Accordion TIN is not displayed correctly", data.get("ownerInfo_TIN"),ownerInfo.getAccTIN());
        
	//	Assert.assertEquals("Fail: Tax Accordion No SIN reason is not displayed correctly",data.get("ownerInfo_SINReason"), ownerInfo.getAccNoSINReason());
		Assert.assertEquals("Fail: Tax Accordion No TIN reason is not displayed correctly", data.get("OwnerInfo_DropdownReasonForNotProvidingTIN"),
				ownerInfo.getAccNoTINReason());
		Assert.assertEquals("Fail: Tax Accordion No TIN specific reason is not displayed correctly",
				data.get("ownerInfo_OtherTINReason"), ownerInfo.getAccNoTINSpecificReason());
	}
//the below method is to fill the personal and contact information for Owner1
	public void fillPersonalInformation(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		String insuredInfo_IsOwnerInsured = data.get("insuredInfo_IsOwnerInsured");
		String firstNameTestStr = data.get("ownerInfo_FirstName");
		String lastNameTestStr = data.get("ownerInfo_LastName");
		String middleNameTestStr = data.get("ownerInfo_MiddleName");
		String relationshipTestStr = data.get("ownerInfo_Relationship");

		
		if (insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes")) {
			ownerInfo.getEnterOwnerInfo().click();

		} else {

			ownerInfo.getIndivOwnerFirstName("0").sendKeys(firstNameTestStr);
			ownerInfo.getIndivOwnerLastName("0").sendKeys(lastNameTestStr);
			ownerInfo.getIndivOwnerMiddleInitial("0").sendKeys(middleNameTestStr);
			ownerInfo.selectRelationshipToInsured(relationshipTestStr, "0");
			if(data.get("ownerInfo_Relationship").equalsIgnoreCase("Other"))
			{
				ownerInfo.getOtherRelationship("0").sendKeys(data.get("ownerInfo_OtherRelationship"));
			}

			ownerInfo.getEnterOwnerInfo().click();

			ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("ownerInfo_Day"));
			ownerInfo.selectIndivOwnerBirthMonth(data.get("ownerInfo_Month"), "0");
			ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("ownerInfo_Year"));
		}

		ownerInfo.getAddress1().sendKeys(data.get("ownerInfo_Address1"));
		ownerInfo.getAddress2().sendKeys(data.get("ownerInfo_Address2"));
		ownerInfo.getCity().sendKeys(data.get("ownerInfo_City"));
		ownerInfo.selectProvince(data.get("ownerInfo_Province"));
		ownerInfo.getPostalCode().sendKeys(data.get("ownerInfo_PostalCode"));
}
	
	//the below method is to fill the personal and contact information for Owner2
		public void fillPersonalAndContactInfoForOwner2(Map<String, String> data) {
			
			OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
			String insuredInfo_IsInsuredOwnerTwo = data.get("insuredInfo_IsOwnerInsured2");

			// -------------------- Filling the Second owner information-----
			String ownerTwoFirstName = data.get("owner2_FirstName");
			String ownerTwoMiddleName = data.get("owner2_MiddleInitial");
			String ownerTwoLastName = data.get("owner2_LastName");


	if (insuredInfo_IsInsuredOwnerTwo.equalsIgnoreCase("Yes")) {
				ownerInfo.getEnterOwnerInfo2().click();

			} else {

				ownerInfo.getIndivOwnerFirstName("1").sendKeys(ownerTwoFirstName);
			ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(ownerTwoMiddleName);
			ownerInfo.getIndivOwnerLastName("1").sendKeys(ownerTwoLastName);
			ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
			if(data.get("owner2_Relationship").equalsIgnoreCase("Other"))
			{
				ownerInfo.getOtherRelationship("1").sendKeys(data.get("Owner2_OtherRelationship"));
			}

				ownerInfo.getEnterOwnerInfo2().click();

				ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("owner2_Day"));
			ownerInfo.selectIndivOwnerBirthMonth(data.get("owner2_Month"), "1");
			ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("owner2_Year"));
			}

			ownerInfo.getAddress1().sendKeys(data.get("Owner2_Address1"));
			ownerInfo.getAddress2().sendKeys(data.get("Owner2_Address2"));
			ownerInfo.getCity().sendKeys(data.get("Owner2_City"));
			ownerInfo.selectProvince(data.get("Owner2_Province"));
			ownerInfo.getPostalCode().sendKeys(data.get("Owner2_PostalCode"));

			ownerInfo.getSaveOwnerInfo().click();
			
		}

	public void verifyPersonalInformationLabels(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		ownerInfo.getIndivOwnerFirstName("0").sendKeys(data.get("ownerInfo_FirstName"));
		ownerInfo.getIndivOwnerMiddleInitial("0").sendKeys(data.get("ownerInfo_MiddleName"));
		ownerInfo.getIndivOwnerLastName("0").sendKeys(data.get("ownerInfo_LastName"));
		ownerInfo.selectRelationshipToInsured(data.get("ownerInfo_Relationship"), "0");
		ownerInfo.getEnterOwnerInfo().click();
		// Validate Personal and Contact Information
		String[] labelKeys2 = { "ownerInfo_PersonalInfoTitleEN", "ownerInfo_SexLabelEN", "ownerInfo_DOBLabelEN",
				"ownerInfo_DayLabelEN", "ownerInfo_MonthLabelEN", "ownerInfo_YearLabelEN",
				"ownerInfo_MailingAddressLabelEN", "ownerInfo_Address1LabelEN", "ownerInfo_Address2LabelEN",
				"ownerInfo_CityLabelEN", "ownerInfo_ProvinceLabelEN", "ownerInfo_PostalCodeLabelEN" };
		ownerInfo.validateModalLabels(labelKeys2, data);
	}

	@Test(priority = 13, dataProvider = "ownerInfoTestData", testName = "Label Validation - Individual Owner Tax Information", groups = "webBrowser")
	public void verifyTaxInformationLabels(Map<String, String> data) throws InterruptedException {
		// Validate Tax Information Labels

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getContinue().click();

		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click();
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click();
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.getTINProvided("Yes").click();
		String[] labelKeys = { "ownerInfo_TaxResidenceCALabelEN", "ownerInfo_TaxResidenceUSLabelEN",
				"ownerInfo_TaxResidenceOtherLabelEN", "ownerInfo_ProvidingSINLabelEN", "ownerInfo_SINLabelEN",
				"ownerInfo_ProvidingSSNLabelEN", "ownerInfo_SSNLabelEN", "ownerInfo_JurisdictionLabelEN",
				"ownerInfo_ProvidingTINLabelEN", "ownerInfo_TINLabelEN" };
		Thread.sleep(3000);
		ownerInfo.validateModalLabels(labelKeys, data);

		ownerInfo.getSINProvided("No").click();
		ownerInfo.getSSNProvided("No").click();
		ownerInfo.getTINProvided("No").click();
		String[] labelKeys2 = { "ownerInfo_NoSINLabelEN", "ownerInfo_NoSINNoteEN", "ownerInfo_NoSSNNoteEN",
				"ownerInfo_NoTINReasonLabelEN" };
		ownerInfo.validateModalLabels(labelKeys2, data);
		Thread.sleep(3000);
	}

	@Test(priority = 16, dataProvider = "ownerInfoTestData", testName = "Error Handling - Individual Owner Tax", groups = "webBrowser")
	public void verifyTaxResidenceErrors(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getContinue().click();

		ownerInfo.getSaveOwnerInfo().click();
		Assert.assertEquals("Fail: Tax Residence Canada required error field is incorrect", "Required",
				ownerInfo.getTaxResidentCAError());
		Assert.assertEquals("Fail: Tax Residence US required error field is incorrect", "Required",
				ownerInfo.getTaxResidentUSError());
		Assert.assertEquals("Fail: Tax Residence Other required error field is incorrect", "Required",
				ownerInfo.getTaxResidentOtherError());

		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.getSaveOwnerInfo().click();
		Assert.assertEquals("Fail: SIN Provided required error field is incorrect", "Required",
				ownerInfo.getProvidedSINError());
		Assert.assertEquals("Fail: SSN Provided required error field is incorrect", "Required",
				ownerInfo.getProvidedSSNError());
		Assert.assertEquals("Fail: TIN Provided required error field is incorrect", "Required",
				ownerInfo.getProvidedTINError());
		Assert.assertEquals("Fail: Jurisdiction required error field is incorrect", "Required",
				ownerInfo.getJurisdictionError());

		ownerInfo.getSINProvided("Yes").click();
		ownerInfo.getSSNProvided("Yes").click();
		ownerInfo.getTINProvided("Yes").click();
		ownerInfo.getSaveOwnerInfo().click();
		Assert.assertEquals("Fail: SIN input required error field is incorrect", "Required", ownerInfo.getSINError());
		Assert.assertEquals("Fail: SSN input required error field is incorrect", "Required", ownerInfo.getSSNError());
		Assert.assertEquals("Fail: TIN input required error field is incorrect", "Required", ownerInfo.getTINError());

		ownerInfo.getSIN().sendKeys("1");
		ownerInfo.getSSN().sendKeys("1");
		ownerInfo.getSaveOwnerInfo().click();
		Assert.assertEquals("Fail: SIN invalid error field is incorrect. Should follow format: 111 111 111",
				data.get("ownerInfo_InvalidSINEN"), ownerInfo.getSINError());
		Assert.assertEquals("Fail: SSN invalid error field is incorrect. Should follow format: 111 11 1111",
				data.get("ownerInfo_InvalidSSNEN"), ownerInfo.getSSNError());

		ownerInfo.getSINProvided("No").click();
		ownerInfo.getSSNProvided("No").click();
		ownerInfo.getTINProvided("No").click();
		ownerInfo.getSaveOwnerInfo().click();
		Assert.assertEquals("Fail: Reason no SIN error field is incorrect", "Required",
				ownerInfo.getReasonNoSINError());
		Assert.assertEquals("Fail: Reason no TIN error field is incorrect", "Required",
				ownerInfo.getReasonNoTINError());

		ownerInfo.selectReasonNoTIN("Other");
		ownerInfo.getSaveOwnerInfo().click();
		Assert.assertEquals("Fail: Specify No TIN Provided error field is incorrect", "Required",
				ownerInfo.getSpecifyNoTINError());
	}

	@Test(priority = 17, dataProvider = "ownerInfoTestData", testName = "Verifying Employment Info Overlay page", groups = "webBrowser")
	public void verifyEmploymentInfoOverlayPage(Map<String, String> data) {

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getContinue().click();
		// Tax Information page
		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));
		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));
		ownerInfo.getContinue().click();

		// End

		// Enter the information in Employment information overlay page

		String empStatus = data.get("OwnerInfo_EmpStatus");
		if (empStatus.equalsIgnoreCase("Self-employed")) {
			ownerInfo.selectEmploymentStatus(empStatus);
			// verify the SoleProprietor Yes or not Button present or not
			Assert.assertTrue("Fail: Sole Proprietor Yes Button is not visible",
					ownerInfo.getSoleProprietorYesButtton().isDisplayed());
			Assert.assertTrue("Fail: Sole Proprietor No Button is not visible",
					ownerInfo.getSoleProprietorNoButtton().isDisplayed());
			// Select SoleProprietor as "Yes" option
			ownerInfo.getSoleProprietorYesButtton().click();

		} else {
			ownerInfo.selectEmploymentStatus(empStatus);

		}
		ownerInfo.selectIndustry(data.get("OwnerInfo_EmpIndustry"));
		ownerInfo.selectOccupation(data.get("OwnerInfo_Occupation"));
		ownerInfo.getNameOfYourCompany().sendKeys(data.get("OwnerInfo_NameOfCompany"));

	}

	@Test(priority = 18, dataProvider = "ownerInfoTestData", testName = "Verifying Identity Verification overlay page", groups = "webBrowser")
	public void verifyIdentityVerificationOverlayPage(Map<String, String> data) {

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);

		ownerInfo.getContinue().click();
		// Enter the information in Tax Residence overlay Page
		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));
		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));
		ownerInfo.getContinue().click();
		// Enter Owner1 Employment information
		fillingEmploymentInfoForOwner1(data);
       
		System.out.println(ownerInfo.getAccordionofIndentityXapth(0).getText());
		Assert.assertTrue("Fail: unable to save Identity Verification overlay page Info",
				ownerInfo.getAccordionofIndentityXapth(0).getText().equals("Identity verification"));

	}

	@Test(priority = 19, dataProvider = "ownerInfoTestData", testName = "Verifying Emp Info Overlay Page Label Validation", groups = "webBrowser")
	public void verifyEmploymentInfoOverlayPageLabelVerification(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);

		ownerInfo.getContinue().click();
		// Enter the information in Tax Residence overlay Page
		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));
		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));
		ownerInfo.getContinue().click();

		Assert.assertTrue("Fail: we did not find label Employment status",
				ownerInfo.getLableEmploymentStatus().equalsIgnoreCase("What is your employment status?"));
		ownerInfo.selectEmploymentStatus("Employed");

		Assert.assertTrue("Fail: We did not find the label In what industry are you currently employed? ",
				ownerInfo.getLableIndustry().equalsIgnoreCase("In what industry are you currently employed?"));
		ownerInfo.selectIndustry("Education");
		Assert.assertTrue("Fail: We did not find the label Current Occupation",
				ownerInfo.getLableOccupation().equalsIgnoreCase("Current occupation"));
		ownerInfo.selectOccupation("Accountant");
		Assert.assertTrue("Fail:We did not find the label Name of the company / current employer ",
				ownerInfo.getLableCompanyName().equalsIgnoreCase("Name of the company / current employer"));

	}

	@Test(priority = 20, dataProvider = "ownerInfoTestData", testName = "Verifying Identity Verification Overlay Page Label Validation", groups = "webBrowser")
	public void verifyIdentityVerificationOverlayPageLabelValidation(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
        ownerInfo.getContinue().click();
		// Enter the information in Tax Residence overlay Page
     // Tax residence information
     		fillingTaxResidenceInfoForOwner1(data);
     		ownerInfo.getContinue().click();
		fillingEmploymentInfoForOwner1(data);
}

	@Test(priority = 21, dataProvider = "ownerInfoTestData", testName = "Verify Required Error Message:If we are leaving all fields as blank and clicking on continue button", groups = "webBrowser")
	public void verifyEmploymentInfoErrorValidation(Map<String, String> data) throws Exception {

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);

		ownerInfo.getContinue().click();
		// Enter the information in Tax Residence overlay Page
		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));
		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));
		ownerInfo.getContinue().click();

		ownerInfo.getContinue().click();

		Assert.assertTrue("Fail: Required error message is not showing for What is your employment status? label",
				ownerInfo.getErrorLableOfEmpStatus().getText().equalsIgnoreCase("Required"));
		ownerInfo.selectEmploymentStatus("Self-employed");
		ownerInfo.getContinue().click();
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Required error message is not showing for the label Are you a sole proprietor?",
				ownerInfo.getErrorLableOfSoleProprietor().getText().equalsIgnoreCase("Required"));
		ownerInfo.getSoleProprietorYesButtton().click();
		ownerInfo.getContinue().click();
		Thread.sleep(3000);
		Assert.assertTrue(
				"Fail: Required error message is not showing for the label In what industry are you currently employed?",
				ownerInfo.getErrorLableOfIndustry().getText().equalsIgnoreCase("Required"));
		ownerInfo.selectIndustry(data.get("OwnerInfo_EmpIndustry"));
		ownerInfo.getContinue().click();
		Thread.sleep(3000);

		Assert.assertTrue("Fail: Required error message is not showing for the label Current occupation",
				ownerInfo.getErrorLableOfOccupation().getText().equalsIgnoreCase("Required"));
		ownerInfo.selectOccupation(data.get("OwnerInfo_Occupation"));
		ownerInfo.getContinue().click();
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Required error message is not showing for the label Name of your company",
				ownerInfo.getErrorLableOfNameOfYourCompany().getText().equalsIgnoreCase("Required"));

	}

	@Test(priority = 22, dataProvider = "ownerInfoTestData", testName = "Verify Required Error Message:If we are leaving all fields as blank and clicking on save and close button", groups = "webBrowser")
	public void verifyIdentityVerificationErrorValidation(Map<String, String> data) throws Exception {

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getContinue().click();
		// Tax Information page
		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));
		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));
		ownerInfo.getContinue().click();

		ownerInfo.selectEmploymentStatus(data.get("OwnerInfo_EmpStatus"));
		ownerInfo.selectIndustry(data.get("OwnerInfo_EmpIndustry"));
		ownerInfo.selectOccupation(data.get("OwnerInfo_Occupation"));
		ownerInfo.getNameOfYourCompany().sendKeys(data.get("OwnerInfo_NameOfCompany"));
		ownerInfo.getContinue().click();

		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);
		Assert.assertTrue(
				"Fail: Required error message is not showing for the field Identification document - jurisdiction of issue",
				ownerInfo.getErrorLableOfIdentificationmethod().getText().equalsIgnoreCase("Required"));

		ownerInfo.selectIdentificationMethod("Single method");
		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);

		// Now we neeed validate Indetity page errors
		Assert.assertTrue("Fail: Required error message is not showing for the field Identification method",
				ownerInfo.getErrorLableOfIdentificationDocument().getText().equalsIgnoreCase("Required"));

		ownerInfo.getIdentificationDocument("Provincial or Territorial").click();

		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);

		Assert.assertTrue("Fail: Required error message is not showing for the field Province issued in",
				ownerInfo.getErrorLableOfProvinceIssuedIn().getText().equalsIgnoreCase("Required"));

		ownerInfo.selectProvinceIssuedIn(data.get("OwnerInfo_ProvinceIssuedIn"));

		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);

		Assert.assertTrue(
				"Fail: Required error message is not showing for the field Original document reviewed to verify the identity",
				ownerInfo.getErrorLableOfOriginaldocumentreviewed().getText().equalsIgnoreCase("Required"));

		ownerInfo.selectOriginalDocumentReviewed("MB ID card");

		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);
		Assert.assertTrue(
				"Fail: Required error message is not showing for the field Name (as it appears on the document) FirstName",
				ownerInfo.getErrorLableOfNameOnTheDocumentFirstName().getText().equalsIgnoreCase("Required"));
		ownerInfo.getNameOnTheDocumentFirstName().sendKeys("sumit");
		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);
		Assert.assertTrue(
				"Fail: Required error message is not showing for the field Name (as it appears on the document) LastName",
				ownerInfo.getErrorLableOfNameOnTheDocumentLastName().getText().equalsIgnoreCase("Required"));

		ownerInfo.getNameOnTheDocumentLastName().sendKeys("Test");

		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);
		Assert.assertTrue(
				"Fail: Required error message is not showing for the field Identifying number on the document",
				ownerInfo.getErrorLableOfIdentifyingNumberOnTheDoc().getText().equalsIgnoreCase("Required"));
		ownerInfo.getIdentifyingNumberOnTheDocument().sendKeys("1212");

		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Required error message is not showing for the field Document expiry date",
				ownerInfo.getErrorLableOfDocumentexpirydate().getText().equalsIgnoreCase("Required"));
		ownerInfo.getDocumentExpiryDateDay().sendKeys("12");
		ownerInfo.selectMonthOFDocumentExpiryDate("August");
		ownerInfo.getDocumentExpiryDateYear().sendKeys("2022");
		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Required error message is not showing for the field Date information was verified",
				ownerInfo.getErrorLableOfDateinformationwasverified().getText().equalsIgnoreCase("Required"));
		ownerInfo.getSaveOwnerInfo().click();
	}

	@Test(priority = 23, dataProvider = "ownerInfoTestData", testName = "Verify negative scenarios for Document expiry date and Date information was verified ", groups = "webBrowser", enabled = true)
	public void verifyIdentityVerificationErrorValidationDocDate(Map<String, String> data) throws Exception {

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getContinue().click();
		// Tax Information page
		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));
		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));
		ownerInfo.getContinue().click();

		ownerInfo.selectEmploymentStatus(data.get("OwnerInfo_EmpStatus"));
		ownerInfo.selectIndustry(data.get("OwnerInfo_EmpIndustry"));
		ownerInfo.selectOccupation(data.get("OwnerInfo_Occupation"));
		ownerInfo.getNameOfYourCompany().sendKeys(data.get("OwnerInfo_NameOfCompany"));
		ownerInfo.getContinue().click();

		ownerInfo.selectIdentificationMethod("Single method");

		ownerInfo.getIdentificationDocument("Provincial or Territorial").click();

		if (!data.get("OwnerInfo_IdentificationDocument").equalsIgnoreCase("Federal")) {
			ownerInfo.selectProvinceIssuedIn(data.get("OwnerInfo_ProvinceIssuedIn"));
		}

		ownerInfo.selectOriginalDocumentReviewed("BC Services card");
		ownerInfo.getNameOnTheDocumentFirstName().sendKeys("sumit");

		ownerInfo.getNameOnTheDocumentMiddleInitial().sendKeys("M");
		ownerInfo.getNameOnTheDocumentLastName().sendKeys("Test");
		ownerInfo.getIdentifyingNumberOnTheDocument().sendKeys("1212");

		ownerInfo.getDocumentExpiryDateDay().sendKeys("32");
		ownerInfo.selectMonthOFDocumentExpiryDate("August");
		ownerInfo.getDocumentExpiryDateYear().sendKeys("2022");

		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);

		Assert.assertTrue("Fail:Document expiry date- Invalid day ( 32 ) is selected",
				ownerInfo.getErrorLableOfDocumentexpirydate().getText().equalsIgnoreCase("Invalid date"));

		ownerInfo.getBackButon(1).click();
		ownerInfo.getContinue().click();
		ownerInfo.getDocumentExpiryDateDay().sendKeys("30");
		ownerInfo.selectMonthOFDocumentExpiryDate("August");
		ownerInfo.getDocumentExpiryDateYear().sendKeys("1987");

		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);
		Assert.assertTrue("Fail:Document expiry date- Invalid year (1987 ) is selected",
				ownerInfo.getErrorLableOfDocumentexpirydate().getText().equalsIgnoreCase("Date cannot be in the past"));

		ownerInfo.getBackButon(1).click();
		ownerInfo.getContinue().click();
		ownerInfo.getDocumentExpiryDateDay().sendKeys("30");
		ownerInfo.selectMonthOFDocumentExpiryDate("August");
		ownerInfo.getDocumentExpiryDateYear().sendKeys("2025");

		// validate date information is verified

		ownerInfo.getDateInfoVerifiedDay().sendKeys("32");
		ownerInfo.selectMonthOFDateInfoVerified("August");
		ownerInfo.getDateInfoVerifiedYear().sendKeys("2022");
		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);

		Assert.assertTrue("Fail:Date information was verified- Invalid day ( 32 ) is selected",
				ownerInfo.getErrorLableOfDateinformationwasverified().getText().equalsIgnoreCase("Invalid date"));

		ownerInfo.getBackButon(1).click();
		ownerInfo.getContinue().click();

		ownerInfo.getDateInfoVerifiedDay().sendKeys("12");
		ownerInfo.selectMonthOFDateInfoVerified("August");
		ownerInfo.getDateInfoVerifiedYear().sendKeys("2022");
		ownerInfo.getSaveOwnerInfo().click();
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Date information was verified- Invalid year ( 2022 ) is selected",
				ownerInfo.getErrorLableOfDateinformationwasverified().getText()
						.equalsIgnoreCase("Date cannot be in the future"));

	}

	@Test(priority = 24, dataProvider = "ownerInfoTestData", testName = "verifying emp info overlay page Accordion ", groups = "webBrowser")
	public void verifyEmploymentInfoAccordion(Map<String, String> data) {

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getContinue().click();
		// Tax Information page
		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));
		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));
		ownerInfo.getContinue().click();
		// End

		// Enter the information in Employment information overlay page

		String empStatus = data.get("OwnerInfo_EmpStatus");
		if (empStatus.equalsIgnoreCase("Self-employed")) {
			ownerInfo.selectEmploymentStatus(empStatus);
			// Select SoleProprietor as "Yes" option
			ownerInfo.getSoleProprietorYesButtton().click();

		} else {
			ownerInfo.selectEmploymentStatus(empStatus);

		}
		String industry = data.get("OwnerInfo_EmpIndustry");
		String empOcp = data.get("OwnerInfo_Occupation");
		String empCompanyName = data.get("OwnerInfo_NameOfCompany");
		ownerInfo.selectIndustry(industry);
		ownerInfo.selectOccupation(empOcp);
		ownerInfo.getNameOfYourCompany().sendKeys(empCompanyName);
		ownerInfo.getSaveOwnerInfo().click();
		ownerInfo.AccordionPlusButton().click();

		Assert.assertTrue(
				"Fail: The value of Employment status field is not matched with the value in the emp info overlay page",
				ownerInfo.accordinanEmpStatusLable().getText().equalsIgnoreCase(empStatus));
		if (empStatus.equalsIgnoreCase("Self-employed")) {
			Assert.assertTrue(
					"Fail: The value of sole proprietor field is not matched with the value in the emp info overlay page",
					ownerInfo.AccordionSolePropritorLabel().getText().equalsIgnoreCase("Yes"));
		}

		Assert.assertTrue(
				"Fail:The value of Industry field is not matched with the value in the emp info overlay page ",
				ownerInfo.accordinanCurrentIndustryLabel().getText().equalsIgnoreCase(industry));
		Assert.assertTrue(
				"Fail:The value of Occupation field is not matched with the value in the emp info overlay page ",
				ownerInfo.AccordionCurrentOccupationLabel().getText().equalsIgnoreCase(empOcp));
		Assert.assertTrue(
				"Fail:The value of company name field is not matched with the value in the emp info overlay page ",
				ownerInfo.AccordionCompanyNameLabel().getText().equalsIgnoreCase(empCompanyName));

		System.out.println("EmpInfo Accordion is passed");
	}

	@Test(priority = 25, dataProvider = "ownerInfoTestData", testName = "verifying Identity Verification overlay page Accordion ", groups = "webBrowser")
	public void verifyIdentityVerificationAccordion(Map<String, String> data) {
		String DocumentExpiryDateDay = "12";
		String DocumentExpiryDateMonth = "August";
		String DocumentExpiryDateYear = "2030";
		String DateInfoVerifiedDay = "22";
		String DateInfoVerifiedMonth = "September";
		String DateInfoVerifiedYear = "2017";

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);
		fillPersonalInformation(data);
		ownerInfo.getContinue().click();
		// Tax Information page
		ownerInfo.getCATaxResident("Yes", "1").click();
		ownerInfo.getSINProvided("Yes").click(); // SIN field is displayed
		ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));
		ownerInfo.getUSTaxResident("Yes").click();
		ownerInfo.getSSNProvided("Yes").click(); // SSN field is displayed
		ownerInfo.getSSN().sendKeys(data.get("ownerInfo_SSN"));
		ownerInfo.getOtherTaxResident("Yes").click();
		ownerInfo.selectJurisdiction(data.get("ownerInfo_Jurisdiction"));
		ownerInfo.getTINProvided("Yes").click(); // TIN field is present
		ownerInfo.getTIN().sendKeys(data.get("ownerInfo_TIN"));
		ownerInfo.getContinue().click();

		ownerInfo.selectEmploymentStatus(data.get("OwnerInfo_EmpStatus"));
		ownerInfo.selectIndustry(data.get("OwnerInfo_EmpIndustry"));
		ownerInfo.selectOccupation(data.get("OwnerInfo_Occupation"));
		ownerInfo.getNameOfYourCompany().sendKeys(data.get("OwnerInfo_NameOfCompany"));
		ownerInfo.getContinue().click();

		ownerInfo.selectIdentificationMethod(data.get("OwnerInfo_IdentificationMethod"));
		ownerInfo.getIdentificationDocument(data.get("OwnerInfo_IdentificationDocument")).click();

		if (!data.get("OwnerInfo_IdentificationDocument").equalsIgnoreCase("Federal")) {
			ownerInfo.selectProvinceIssuedIn(data.get("OwnerInfo_ProvinceIssuedIn"));
		}

		ownerInfo.selectOriginalDocumentReviewed(data.get("OwnerInfo_OriginalDocReviewed"));
		ownerInfo.getNameOnTheDocumentFirstName().sendKeys(data.get("ownerInfo_FirstName"));

		ownerInfo.getNameOnTheDocumentMiddleInitial().sendKeys(data.get("ownerInfo_MiddleName"));
		ownerInfo.getNameOnTheDocumentLastName().sendKeys(data.get("ownerInfo_LastName"));
		ownerInfo.getIdentifyingNumberOnTheDocument().sendKeys(data.get("Identifying_Number"));
		ownerInfo.getDocumentExpiryDateDay().sendKeys(DocumentExpiryDateDay);
		ownerInfo.selectMonthOFDocumentExpiryDate(DocumentExpiryDateMonth);
		ownerInfo.getDocumentExpiryDateYear().sendKeys(DocumentExpiryDateYear);
		ownerInfo.getDateInfoVerifiedDay().sendKeys(DateInfoVerifiedDay);
		ownerInfo.selectMonthOFDateInfoVerified(DateInfoVerifiedMonth);
		ownerInfo.getDateInfoVerifiedYear().sendKeys(DateInfoVerifiedYear);
		ownerInfo.getSaveOwnerInfo().click();
		ownerInfo.accodianPlusIconIdentityVerification().click();

		Assert.assertTrue(
				"Fail:The value of Identification method is not matched with the value in Identity verification overlay page",
				ownerInfo.AccordionIdentityMethodLabel().getText()
						.equalsIgnoreCase(data.get("OwnerInfo_IdentificationMethod")));
		Assert.assertTrue(
				"Fail:The value of Identification document is not matched with the value in Identity verification overlay page",
				ownerInfo.AccordionIdentitydocumentLabel().getText()
						.equalsIgnoreCase(data.get("OwnerInfo_IdentificationDocument")));
		Assert.assertTrue(
				"Fail:The value of Province issued in is not matched with the value in Identity verification overlay page",
				ownerInfo.AccordionIdentityProvinceLabel().getText()
						.equalsIgnoreCase(data.get("OwnerInfo_ProvinceIssuedIn")));
		Assert.assertTrue(
				"Fail:The value of Original Document Reviewed is not matched with the value in Identity verification overlay page",
				ownerInfo.AccordionIdentityOriginalDocLabel().getText()
						.equalsIgnoreCase(data.get("OwnerInfo_OriginalDocReviewed")));
		Assert.assertTrue(
				"Fail:The value of Name (as it appears on the document) is not matched with the value in Identity verification overlay page",
				ownerInfo.AccordionIdentityNameOnDocumentLabel().getText()
						.equalsIgnoreCase(data.get("ownerInfo_FirstName") + " " + data.get("ownerInfo_MiddleName") + " "
								+ data.get("ownerInfo_LastName")));
		Assert.assertTrue(
				"Fail:The value of Identifying Number on the Document field is not matched with the value in Identity verification overlay page",
				ownerInfo.AccordionIdentityNumberLabel().getText().equalsIgnoreCase(data.get("Identifying_Number")));
		Assert.assertTrue(
				"Fail:The value of Document Expiry date is not matched with the value in Identity verification overlay page",
				ownerInfo.AccordionIdentityDocExpiryDateLabel().getText().equalsIgnoreCase(
						DocumentExpiryDateDay + "/" + DocumentExpiryDateMonth + "/" + DocumentExpiryDateYear));
		Assert.assertTrue(
				"Fail:The value of Date Info was verified is not matched with the value in Identity verification overlay page",
				ownerInfo.AccordionIdentityDocVerifiedDateLabel().getText().equalsIgnoreCase(
						DateInfoVerifiedDay + "/" + DateInfoVerifiedMonth + "/" + DateInfoVerifiedYear));

		System.out.println("Identity Verification accodion is passed");
	}

	public void fillDataForIdentityVerificationOverlayPageForOwner2(OwnerInformationPage ownerInfo,
			Map<String, String> data) {

		String DocumentExpiryDateDay = data.get("Owner2_DocExpiryDate_Day");
		String DocumentExpiryDateMonth = data.get("Owner2_DocExpiryDate_Month");
		String DocumentExpiryDateYear = data.get("Owner2_DocExpiryDate_Year");
		String DateInfoVerifiedDay = data.get("Owner2_DateInfoVerified_Day");
		String DateInfoVerifiedMonth = data.get("Owner2_DateInfoVerified_Month");
		String DateInfoVerifiedYear = data.get("Owner2_DateInfoVerified_Year");

		// ---------Identity Verification overlay page----------
		String owner2IdentificationMethod =data.get("Owner2_IdentificationMethod") ;
		ownerInfo.selectIdentificationMethod(owner2IdentificationMethod);
		switch (owner2IdentificationMethod.toUpperCase()) {
		case "SINGLE METHOD":
		ownerInfo.getIdentificationDocument(data.get("Owner2_IdentificationDocument")).click();

		if (!data.get("Owner2_IdentificationDocument").equalsIgnoreCase("Federal")) {
			ownerInfo.selectProvinceIssuedIn(data.get("Owner2_ProvinceIssuedIn"));
		}

		ownerInfo.selectOriginalDocumentReviewed(data.get("Owner2_OriginalDocReviewed"));
		ownerInfo.getNameOnTheDocumentFirstName().sendKeys(data.get("owner2_FirstName"));

		ownerInfo.getNameOnTheDocumentMiddleInitial().sendKeys(data.get("owner2_MiddleInitial"));
		ownerInfo.getNameOnTheDocumentLastName().sendKeys(data.get("owner2_LastName"));
		ownerInfo.getIdentifyingNumberOnTheDocument().sendKeys(data.get("Owner2_IdentifyingNumber"));
		ownerInfo.getDocumentExpiryDateDay().sendKeys(DocumentExpiryDateDay);
		ownerInfo.selectMonthOFDocumentExpiryDate(DocumentExpiryDateMonth);
		ownerInfo.getDocumentExpiryDateYear().sendKeys(DocumentExpiryDateYear);
		ownerInfo.getDateInfoVerifiedDay().sendKeys(DateInfoVerifiedDay);
		ownerInfo.selectMonthOFDateInfoVerified(DateInfoVerifiedMonth);
		ownerInfo.getDateInfoVerifiedYear().sendKeys(DateInfoVerifiedYear);
		ownerInfo.getSaveOwnerInfo().click();
		break;

	case "DUAL METHOD":
	case "CLIENT CREDIT FILE":
		ownerInfo.getSaveOwnerInfo().click();
		break;
	default:
		throw new ElementNotSelectableException(
				"selected option is not present in the Identification method dropdown");

	}
}

	private void fillEmploymentInfoForOwner2(OwnerInformationPage ownerInfo, Map<String, String> data) {

		// Entering the Owner2 Employment information
		ownerInfo.selectEmploymentStatus(data.get("Owner2_EmpStatus"));
		if (data.get("Owner2_EmpStatus").equalsIgnoreCase("Never been employed")) {
			ownerInfo.getContinue().click();
			fillDataForIdentityVerificationOverlayPageForOwner2(ownerInfo, data);
			
		} else if (data.get("Owner2_EmpStatus").equalsIgnoreCase("Self-employed")) 
		{
				ownerInfo.getSoleProprietor(data.get("Owner2_SoleProprietor")).click();
			}
			
		
		ownerInfo.selectIndustry(data.get("Owner2_EmpIndustry"));
		ownerInfo.selectOccupation(data.get("Owner2_Occupation"));
		ownerInfo.getNameOfYourCompany().sendKeys(data.get("Owner2_NameOfCompany"));
		ownerInfo.getContinue().click();
		fillDataForIdentityVerificationOverlayPageForOwner2(ownerInfo, data);

	}

	@Test(priority = 28, dataProvider = "ownerInfoTestData", testName = "verifying user able to add two owner infromation  ", groups = "webBrowser")
	public void verifyUserAbleToAddSecondOwner(Map<String, String> data) {

		// -----Enter the Information for Owner 1-------
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		fillPagesUntilOwnerPage(data);
		// Adding the second owner
		ownerInfo.getAddOwner2Button().click();
		fillSecondOwner(data);

	}

	public void fillSecondOwner(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		
		String insuredInfo_IsInsuredOwnerTwo = data.get("insuredInfo_IsOwnerInsured2");

		// -------------------- Filling the Second owner information-----
		String ownerTwoFirstName = data.get("owner2_FirstName");
		String ownerTwoMiddleName = data.get("owner2_MiddleInitial");
		String ownerTwoLastName = data.get("owner2_LastName");


if (insuredInfo_IsInsuredOwnerTwo.equalsIgnoreCase("Yes")) {
			ownerInfo.getEnterOwnerInfo2().click();

		} else {

			ownerInfo.getIndivOwnerFirstName("1").sendKeys(ownerTwoFirstName);
		ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(ownerTwoMiddleName);
		ownerInfo.getIndivOwnerLastName("1").sendKeys(ownerTwoLastName);
		ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
		if(data.get("owner2_Relationship").equalsIgnoreCase("Other"))
		{
			ownerInfo.getOtherRelationship("1").sendKeys(data.get("Owner2_OtherRelationship"));
		}

			ownerInfo.getEnterOwnerInfo2().click();

			ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("owner2_Day"));
		ownerInfo.selectIndivOwnerBirthMonth(data.get("owner2_Month"), "1");
		ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("owner2_Year"));
		}

		ownerInfo.getAddress1().sendKeys(data.get("Owner2_Address1"));
		ownerInfo.getAddress2().sendKeys(data.get("Owner2_Address2"));
		ownerInfo.getCity().sendKeys(data.get("Owner2_City"));
		ownerInfo.selectProvince(data.get("Owner2_Province"));
		ownerInfo.getPostalCode().sendKeys(data.get("Owner2_PostalCode"));

		ownerInfo.getContinue().click();
		// Enter the Owner2 Tax residence information

		fillTaxResidenceInfoForOwner2(data);
		ownerInfo.getContinue().click();

		// Enter Owner2 Employment information
		fillEmploymentInfoForOwner2(ownerInfo, data);
}
	
	public void fillTaxResidenceInfoForOwner2(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		// Enter the Owner2 Tax residence information
		String owner2CanadaTaxResident = data.get("Owner2_CanadaTaxResident");
		String owner2ProvidingSIN = data.get("Owner2_ProvidingSIN");

		String owner2UsTaxResident = data.get("Owner2_USTaxResident");
		String owner2UsProvidingSSN = data.get("Owner2_ProvidingSSN");
		String owner2SSNNumberForUS = data.get("Owner2_SSN");

		String owner2CountryJurisdiction = data.get("Owner2_Jurisdiction");
		String owner2TaxResidentOfJurisdiction = data.get("Owner2_JurisdictionTaxResident");
		String owner2ProvidingTINYesOrNo = data.get("Owner2_ProvidingTIN");
		String owner2InputTIN = data.get("Owner2_TIN");
		String owner2ReasonForNotProvidingTIN = data.get("Owner2_DropdownReasonForNotProvidingTIN");
		String owner2OtherTINReason = data.get("Owner2_OtherTINReason");

		ownerInfo.getCATaxResident(owner2CanadaTaxResident, "1").click();
		if (owner2CanadaTaxResident.equalsIgnoreCase("Yes")) {

			ownerInfo.getSINProvided(owner2ProvidingSIN).click();
			if (owner2ProvidingSIN.equalsIgnoreCase("Yes")) {
				ownerInfo.getSIN().sendKeys(data.get("Owner2_SIN"));

			} else {

				ownerInfo.getNoSINReason().sendKeys(data.get("Owner2_SINReason"));

			}

		}

		ownerInfo.getUSTaxResident(owner2UsTaxResident).click();
		if (owner2UsTaxResident.equalsIgnoreCase("Yes")) {

			ownerInfo.getSSNProvided(owner2UsProvidingSSN).click();

			if (owner2UsProvidingSSN.equalsIgnoreCase("Yes")) {
				ownerInfo.getSSN().sendKeys(owner2SSNNumberForUS);
			}

		}

		ownerInfo.getOtherTaxResident(owner2TaxResidentOfJurisdiction).click();
		if (owner2TaxResidentOfJurisdiction.equalsIgnoreCase("Yes")) {

			ownerInfo.selectJurisdiction(owner2CountryJurisdiction);
			ownerInfo.getTINProvided(owner2ProvidingTINYesOrNo).click();
			if (owner2ProvidingTINYesOrNo.equalsIgnoreCase("Yes")) {

				ownerInfo.getTIN().sendKeys(owner2InputTIN);
			} else if (owner2ProvidingTINYesOrNo.equalsIgnoreCase("No")) {
				ownerInfo.selectReasonNoTIN(owner2ReasonForNotProvidingTIN);
				if (owner2ReasonForNotProvidingTIN.equalsIgnoreCase("Other")) {
					ownerInfo.getSpecifyReasonNoTIN().sendKeys(owner2OtherTINReason);
				}

			}

		}
		
	}

	@Test(priority = 29, dataProvider = "ownerInfoTestData", testName = "verifying user able to add two owner infromation  ", groups = "webBrowser")
	public void verifyIndividualOwnerFieldsOwner2(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		fillPagesUntilOwnerPage(data);
		ownerInfo.getAddOwner2Button().click();
		verifyIndividualOwnerFieldsArePresentForOwner2(ownerInfo, data, "Owner2");
	}

	@Test(priority = 30,enabled = false, dataProvider = "ownerInfoTestData", testName = "verifying user able to add two owner infromation  ", groups = "webBrowser")
	public void verifyIndividualOwnerOverlayFieldsOwner2(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		fillPagesUntilOwnerPage(data);
		ownerInfo.getAddOwner2Button().click();
		verifyIndividualOwnerOverlayFieldsArePresent(ownerInfo, data, "Owner2");
	}

	@Test(priority = 31, dataProvider = "ownerInfoTestData", testName = "verifying personal accordion values for second owner", groups = "webBrowser")
	public void verifyIndividualOwnerPersonalAccordionOwner2(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		fillPagesUntilOwnerPage(data);
		fillPersonalAndContactInfoForOwner2(data);
		ownerInfo.getPersonalAccordionOwner2().click();
		verifyPersonalAccordionValuesForOwner2(ownerInfo, data, "OWNER 2");
	}

	@Test(priority = 32, dataProvider = "ownerInfoTestData", testName = "verifying user able to add two owner infromation  ", groups = "webBrowser")
	public void verifyIndividualOwnerTaxAccordionOwner2(Map<String, String> data) {

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		fillPagesUntilOwnerPage(data);
		ownerInfo.getAddOwner2Button().click();
		String insuredInfo_IsInsuredOwnerTwo = data.get("insuredInfo_IsOwnerInsured2");

		// -------------------- Filling the Second owner information-----
		String ownerTwoFirstName = data.get("owner2_FirstName");
		String ownerTwoMiddleName = data.get("owner2_MiddleInitial");
		String ownerTwoLastName = data.get("owner2_LastName");


if (insuredInfo_IsInsuredOwnerTwo.equalsIgnoreCase("Yes")) {
			ownerInfo.getEnterOwnerInfo2().click();

		} else {

			ownerInfo.getIndivOwnerFirstName("1").sendKeys(ownerTwoFirstName);
		ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(ownerTwoMiddleName);
		ownerInfo.getIndivOwnerLastName("1").sendKeys(ownerTwoLastName);
		ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
		if(data.get("owner2_Relationship").equalsIgnoreCase("Other"))
		{
			ownerInfo.getOtherRelationship("1").sendKeys(data.get("Owner2_OtherRelationship"));
		}

			ownerInfo.getEnterOwnerInfo2().click();

			ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("owner2_Day"));
		ownerInfo.selectIndivOwnerBirthMonth(data.get("owner2_Month"), "1");
		ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("owner2_Year"));
		}

		ownerInfo.getAddress1().sendKeys(data.get("Owner2_Address1"));
		ownerInfo.getAddress2().sendKeys(data.get("Owner2_Address2"));
		ownerInfo.getCity().sendKeys(data.get("Owner2_City"));
		ownerInfo.selectProvince(data.get("Owner2_Province"));
		ownerInfo.getPostalCode().sendKeys(data.get("Owner2_PostalCode"));

		ownerInfo.getContinue().click();
		// Enter the Owner2 Tax residence information
       fillTaxResidenceInfoForOwner2(data);
       ownerInfo.getSaveOwnerInfo().click();
		ownerInfo.getTaxAccordionOwner2().click();
		Assert.assertTrue("Fail: Tax Accordion is not visible for Second Owner ",ownerInfo.getTaxAccordionOwner2().isDisplayed());

Assert.assertEquals("Fail: Tax Accordion Tax Resident Canada is not displayed correctly", data.get("Owner2_CanadaTaxResident"),
				ownerInfo.getAccTaxResidentCANOwner2());
		Assert.assertEquals("Fail: Tax Accordion SIN Provided is not displayed correctly", data.get("Owner2_ProvidingSIN"),
				ownerInfo.getAccSINProvidedOwner2());
		//Assert.assertEquals("Fail: Tax Accordion SIN is not displayed correctly", data.get("Owner2_SIN"),ownerInfo.getAccSINOwner2());
		Assert.assertEquals("Fail: Tax Accordion No SIN reason is not displayed correctly",data.get("Owner2_SINReason"), ownerInfo.getAccNoSINReasonOwner2());
		Assert.assertEquals("Fail: Tax Accordion Tax Resident US is not displayed correctly", data.get("Owner2_USTaxResident"),
				ownerInfo.getAccTaxResidentUSOwner2());
		Assert.assertEquals("Fail: Tax Accordion SSN Provided is not displayed correctly", data.get("Owner2_ProvidingSSN"),
				ownerInfo.getAccSSNProvidedOwner2());
	//	Assert.assertEquals("Fail: Tax Accordion SSN is not displayed correctly", data.get("Owner2_SSN"),ownerInfo.getAccSSNOwner2());
		Assert.assertEquals("Fail: Tax Accordion Tax Resident Other is not displayed correctly", data.get("Owner2_JurisdictionTaxResident"),
				ownerInfo.getAccTaxResidentOtherOwner2());
		Assert.assertEquals("Fail: Tax Accordion Jurisdiction is not displayed correctly",data.get("Owner2_Jurisdiction"), ownerInfo.getAccJurisdictionOwner2());
		Assert.assertEquals("Fail: Tax Accordion TIN provided is not displayed correctly", data.get("Owner2_ProvidingTIN"),ownerInfo.getAccTINProvidedOwner2());
		//Assert.assertEquals("Fail: Tax Accordion TIN is not displayed correctly", data.get("Owner2_TIN"),ownerInfo.getAccTINOwner2());
        Assert.assertEquals("Fail: Tax Accordion No TIN reason is not displayed correctly", data.get("Owner2_DropdownReasonForNotProvidingTIN"),ownerInfo.getAccNoTINReasonOwner2());
		Assert.assertEquals("Fail: Tax Accordion No TIN specific reason is not displayed correctly",
				data.get("Owner2_OtherTINReason"), ownerInfo.getAccNoTINSpecificReasonOwner2());
		
	}

	@Test(priority = 27, dataProvider = "ownerInfoTestData", testName = "verifying user able to add two owner infromation  ", groups = "webBrowser")
	public void verifyPersonalInformationLabelsOwner2(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		fillPagesUntilOwnerPage(data);
		ownerInfo.getAddOwner2Button().click();

		ownerInfo.getIndivOwnerFirstName("1").sendKeys(data.get("owner2_FirstName"));
		ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(data.get("owner2_MiddleInitial"));
		ownerInfo.getIndivOwnerLastName("1").sendKeys(data.get("owner2_LastName"));
		ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
		ownerInfo.getEnterOwnerInfo2().click();
		// Validate Personal and Contact Information
		String[] labelKeys2 = { "ownerInfo_PersonalInfoTitleEN", "ownerInfo_DOBLabelEN", "ownerInfo_DayLabelEN",
				"ownerInfo_MonthLabelEN", "ownerInfo_YearLabelEN", "ownerInfo_MailingAddressLabelEN",
				"ownerInfo_Address1LabelEN", "ownerInfo_Address2LabelEN", "ownerInfo_CityLabelEN",
				"ownerInfo_ProvinceLabelEN", "ownerInfo_PostalCodeLabelEN" };
		ownerInfo.validateModalLabels(labelKeys2, data);
	}

	@Test(priority = 27, dataProvider = "ownerInfoTestData", testName = "verifying user able to add two owner infromation  ", groups = "webBrowser")
	public void verifyTaxInformationLabelsOwner2(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);

		fillPagesUntilOwnerPage(data);

		String insuredInfo_IsOwnerInsured = data.get("insuredInfo_IsOwnerInsured");
		String insuredInfo_IsOwnerInsuredTwo = data.get("insuredInfo_IsOwnerInsured2");
		String coverageType = data.get("policyInfo_CoverageType");

		if (coverageType.contains("Joint"))
		{
			if (insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes") && insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("Yes")) 
			{
				 ownerInfo.getEnterOwnerInfo2().click();
			} 
			else
			{
				ownerInfo.getAddOwner2Button().click();
				ownerInfo.getIndivOwnerFirstName("1").sendKeys(data.get("owner2_FirstName"));
				ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(data.get("owner2_MiddleInitial"));
				ownerInfo.getIndivOwnerLastName("1").sendKeys(data.get("owner2_LastName"));
				ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
				if(data.get("owner2_Relationship").equalsIgnoreCase("Other"))
				{
					ownerInfo.getOtherRelationship("1").sendKeys(data.get("Owner2_OtherRelationship"));
				}
				ownerInfo.getEnterOwnerInfo2().click();
				
				ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("owner2_Day"));
				ownerInfo.selectIndivOwnerBirthMonth(data.get("owner2_Month"), "1");
				ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("owner2_Year"));
          }

		}

	

		ownerInfo.getAddress1().sendKeys(data.get("Owner2_Address1"));
		ownerInfo.getAddress2().sendKeys(data.get("Owner2_Address2"));
		ownerInfo.getCity().sendKeys(data.get("Owner2_City"));
		ownerInfo.selectProvince(data.get("Owner2_Province"));
		ownerInfo.getPostalCode().sendKeys(data.get("Owner2_PostalCode"));
        ownerInfo.getContinue().click();
        
     // Enter the Owner2 Tax residence information
          fillTaxResidenceInfoForOwner2(data);
		String[] labelKeys = { "ownerInfo_TaxResidenceCALabelEN", "ownerInfo_TaxResidenceUSLabelEN",
				"ownerInfo_TaxResidenceOtherLabelEN", "ownerInfo_ProvidingSINLabelEN", "ownerInfo_SINLabelEN",
				"ownerInfo_ProvidingSSNLabelEN", "ownerInfo_SSNLabelEN", "ownerInfo_JurisdictionLabelEN",
				"ownerInfo_ProvidingTINLabelEN", "ownerInfo_TINLabelEN" };

		ownerInfo.validateModalLabels(labelKeys, data);
		ownerInfo.getSINProvided("No").click();
		ownerInfo.getSSNProvided("No").click();
		ownerInfo.getTINProvided("No").click();

		
       String[] labelKeys2 = { "ownerInfo_NoSINLabelEN", "ownerInfo_NoSINNoteEN", "ownerInfo_NoSSNNoteEN",
				"ownerInfo_NoTINReasonLabelEN" };
		ownerInfo.validateModalLabels(labelKeys2, data);
		 ownerInfo.getSaveOwnerInfo().click();

	}

	@Test(priority = 28, dataProvider = "ownerInfoTestData", testName = "verifying   ", groups = "webBrowser")
	public void verifyDifferentScenariosIfInsuredAsOwner(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		fillPagesUntilOwnerPage(data);

		String insuredInfo_IsOwnerInsured = data.get("insuredInfo_IsOwnerInsured");
		String insuredInfo_IsOwnerInsuredTwo = data.get("insuredInfo_IsOwnerInsured2");
		String coverageType = data.get("policyInfo_CoverageType");

		if (coverageType.contains("Joint")) {
			if (insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("Yes")
					&& insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes")) {

				String firstNameTestStr = data.get("ownerInfo_FirstName");
				String lastNameTestStr = data.get("ownerInfo_LastName");
				String middleNameTestStr = data.get("ownerInfo_MiddleName");
				ownerInfo.getEnterOwnerInfo().click();
				ownerInfo.getAddress1().sendKeys(data.get("ownerInfo_Address1"));
				ownerInfo.getAddress2().sendKeys(data.get("ownerInfo_Address2"));
				ownerInfo.getCity().sendKeys(data.get("ownerInfo_City"));
				ownerInfo.selectProvince(data.get("ownerInfo_Province"));
				ownerInfo.getPostalCode().sendKeys(data.get("ownerInfo_PostalCode"));
				
				ownerInfo.getContinue().click(); // Tax Residence Form
				// Tax residence information
				fillingTaxResidenceInfoForOwner1(data);
				ownerInfo.getContinue().click(); 
				// Enter Owner1 Employment information
				fillingEmploymentInfoForOwner1(data);
				
				ownerInfo.getEnterOwnerInfo2().click();

				ownerInfo.getAddress1().sendKeys(data.get("Owner2_Address1"));
				ownerInfo.getAddress2().sendKeys(data.get("Owner2_Address2"));
				ownerInfo.getCity().sendKeys(data.get("Owner2_City"));
				ownerInfo.selectProvince(data.get("Owner2_Province"));
				ownerInfo.getPostalCode().sendKeys(data.get("Owner2_PostalCode"));
				ownerInfo.getContinue().click(); 
				// Enter the Owner2 Tax residence information

				fillTaxResidenceInfoForOwner2(data);
				ownerInfo.getContinue().click();
				// Enter Owner2 Employment information
				fillEmploymentInfoForOwner2(ownerInfo, data);
				
			}
			else if (insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("No")
					&& insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes")) 
			{
                 ownerInfo.getEnterOwnerInfo().click();
				ownerInfo.getAddress1().sendKeys(data.get("ownerInfo_Address1"));
				ownerInfo.getAddress2().sendKeys(data.get("ownerInfo_Address2"));
				ownerInfo.getCity().sendKeys(data.get("ownerInfo_City"));
				ownerInfo.selectProvince(data.get("ownerInfo_Province"));
				ownerInfo.getPostalCode().sendKeys(data.get("ownerInfo_PostalCode"));
				
				ownerInfo.getContinue().click(); // Tax Residence Form
				// Tax residence information
				fillingTaxResidenceInfoForOwner1(data);
				ownerInfo.getContinue().click(); 
				// Enter Owner1 Employment information
				fillingEmploymentInfoForOwner1(data);
				
				//*******************************************Entering the Second Owner Information*****************************************
				addingSecondOwnerAndFillData(data);
				
				
           } 
			else if (insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("Yes")
					&& insuredInfo_IsOwnerInsured.equalsIgnoreCase("No"))
           {
				ownerInfo.getEnterOwnerInfo().click();
				ownerInfo.getAddress1().sendKeys(data.get("ownerInfo_Address1"));
				ownerInfo.getAddress2().sendKeys(data.get("ownerInfo_Address2"));
				ownerInfo.getCity().sendKeys(data.get("ownerInfo_City"));
				ownerInfo.selectProvince(data.get("ownerInfo_Province"));
				ownerInfo.getPostalCode().sendKeys(data.get("ownerInfo_PostalCode"));

				ownerInfo.getContinue().click();
				// Tax residence information
				fillingTaxResidenceInfoForOwner1(data);
				ownerInfo.getContinue().click();
				
				// Enter Owner1 Employment information
				
				fillingEmploymentInfoForOwner1(data);
				
				// ************** Second Owner data *******************
				addingSecondOwnerAndFillData(data);

			} else if (insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("No")
					&& insuredInfo_IsOwnerInsured.equalsIgnoreCase("No")) 
			{
				ownerInfo.getIndivOwnerFirstName("0").sendKeys(data.get("ownerInfo_FirstName"));
				ownerInfo.getIndivOwnerMiddleInitial("0").sendKeys(data.get("ownerInfo_MiddleName"));
				ownerInfo.getIndivOwnerLastName("0").sendKeys(data.get("ownerInfo_LastName"));
				ownerInfo.selectRelationshipToInsured(data.get("ownerInfo_Relationship"), "0");
				if(data.get("ownerInfo_Relationship").equalsIgnoreCase("Other"))
				{
					ownerInfo.getOtherRelationship("0").sendKeys(data.get("ownerInfo_OtherRelationship"));
				}

				ownerInfo.getEnterOwnerInfo().click();

				ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("ownerInfo_Day"));
				ownerInfo.selectIndivOwnerBirthMonth(data.get("ownerInfo_Month"), "0");
				ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("ownerInfo_Year"));
	            ownerInfo.getAddress1().sendKeys(data.get("ownerInfo_Address1"));
			    ownerInfo.getAddress2().sendKeys(data.get("ownerInfo_Address2"));
			    ownerInfo.getCity().sendKeys(data.get("ownerInfo_City"));
			    ownerInfo.selectProvince(data.get("ownerInfo_Province"));
			    ownerInfo.getPostalCode().sendKeys(data.get("ownerInfo_PostalCode"));

			ownerInfo.getContinue().click();
	        // Tax residence information
			fillingTaxResidenceInfoForOwner1(data);
			ownerInfo.getContinue().click();
			// Enter Owner1 Employment information
			 fillingEmploymentInfoForOwner1(data);
			 //**********************************************Adding Second Owner and filling the Info for Owner2
			 addingSecondOwnerAndFillData(data);
			 
			
			}

		}

	}
	public void addingSecondOwnerAndFillData(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		ownerInfo.getAddOwner2Button().click();
		ownerInfo.getIndivOwnerFirstName("1").sendKeys(data.get("owner2_FirstName"));
		ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(data.get("owner2_MiddleInitial"));
		ownerInfo.getIndivOwnerLastName("1").sendKeys(data.get("owner2_LastName"));
		ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
		if(data.get("owner2_Relationship").equalsIgnoreCase("Other"))
		{
			ownerInfo.getOtherRelationship("1").sendKeys(data.get("Owner2_OtherRelationship"));
		}
        ownerInfo.getEnterOwnerInfo2().click();
        ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("owner2_Day"));
		ownerInfo.selectIndivOwnerBirthMonth(data.get("owner2_Month"), "1");
		ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("owner2_Year"));
		
		ownerInfo.getAddress1().sendKeys(data.get("Owner2_Address1"));
		ownerInfo.getAddress2().sendKeys(data.get("Owner2_Address2"));
		ownerInfo.getCity().sendKeys(data.get("Owner2_City"));
		ownerInfo.selectProvince(data.get("Owner2_Province"));
		ownerInfo.getPostalCode().sendKeys(data.get("Owner2_PostalCode"));
         ownerInfo.getContinue().click();
		// Enter the Owner2 Tax residence information
          fillTaxResidenceInfoForOwner2(data);
		ownerInfo.getContinue().click();
		// Enter Owner2 Employment information
		fillEmploymentInfoForOwner2(ownerInfo, data);
	}
	

	@Test(priority = 29, dataProvider = "ownerInfoTestData", testName = "verifying user able to remove owner 2", groups = "webBrowser")
	public void verifyRemoveOwner(Map<String, String> data) {

		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);

		fillPagesUntilOwnerPage(data);
		// remove owner 2 by using remove owner button
		String coverageType = data.get("policyInfo_CoverageType");
		String insuredTwoOption = data.get("insuredInfo_IsOwnerInsured2");

		if (coverageType.contains("Joint") && insuredTwoOption.equalsIgnoreCase("No")) {
			ownerInfo.getAddOwner2Button().click();
			// lable validation in English and in french
			Assert.assertTrue("Fail: remove owner button lable is not present in English ",
					ownerInfo.getRemoveOwnerBtnText().equalsIgnoreCase("Remove owner"));
			commonElements.clickENFRToggle();
			Assert.assertTrue("Fail: remove owner button lable is not present in french ",
					ownerInfo.getRemoveOwnerBtnText().equalsIgnoreCase("Supprimer de titulaire"));
			commonElements.clickENFRToggle();

			// clicking on the remove owner button
			ownerInfo.getRemoveOwnerBtn().click();
			Assert.assertFalse("Fail: remove owner button should not display in english",
					ownerInfo.isRemoveOwnerBtnVisible());

		} else if (coverageType.contains("Joint") && insuredTwoOption.equalsIgnoreCase("Yes")) {

			Assert.assertFalse("Fail: remove owner button should not display in english",
					ownerInfo.isRemoveOwnerBtnVisible());
		} else if (coverageType.contains("Single life")) {
			ownerInfo.getAddOwner2Button().click();
			Assert.assertTrue("Fail: We are expecting to present remove owner button in English ",
					ownerInfo.getRemoveOwnerBtnText().equalsIgnoreCase("Remove owner"));

			ownerInfo.getRemoveOwnerBtn().click();
			Assert.assertFalse("Fail: We are not expecting remove owner button should display in english",
					ownerInfo.isRemoveOwnerBtnVisible());

		}

	}

}
