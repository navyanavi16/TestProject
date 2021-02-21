package term_conversion;

import junit.framework.Assert;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

public class IdentifyingthirdpartiesTest extends BaseTest {

	public void fillPagesUntilBillingPagePage(Map<String, String> data) throws InterruptedException {
		FillPages fillPage = new FillPages(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		fillPage.fillPolicyPage(data);
		fillPage.fillInsuredPage(data);
		fillPage.fillOwnerPage(data);
		commonElements.clickNextBtn(); // fill Beneficiary
	}

	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en", "policyInfoUrl");
	}

	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");

	@DataProvider(name = "IdentifyingThirdpartiesTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception {
		// Getting the Data Row against the Test Case name and store it within an array
		Object[][] testObjArray = excelUtil.getAllMatchingTestCases("IdentifyingThirdpartiesTestData",
				method.getName());
		return (testObjArray);
	}

	@Test(dataProvider = "IdentifyingThirdpartiesTestData", testName = "Verify fields - Identifying Third parties page", groups = "webBrowser")
	public void VerifyTheThirdPartiesPageTitleInENorFR(Map<String, String> data) throws InterruptedException {
		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();
		fillPagesUntilBillingPagePage(data);
		

		Assert.assertEquals(data.get("IdentifyingThirdparties_pagetitle"),
				page.getPageTitleOfThirdPartyInformationPage());

		if (page.getLanguageToggleButton().getText().equals("FR")) {
			page.getLanguageToggleButton().click();
			Assert.assertEquals("Identification des tiers", page.getPageTitleOfThirdPartyInformationPage());
		}

	}

	@Test(dataProvider = "IdentifyingThirdpartiesTestData", testName = "Validate Link of - Paying For The Policy for english Version", groups = "webBrowser")
	public void ValidateField_PayingForThePolicyforEnglishVersion(Map<String, String> data)
			throws InterruptedException {
		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();
		fillPagesUntilBillingPagePage(data);

		
		String[] getDivText = getDataForValidation(data.get("IdentifyingThirdparties_Noteforfirstfield"));
		String[] getLinkText = getDataForValidation(data.get("IdentifyingThirdparties_notelinktext"));
		String actualText = "";
		String actualLinkText = "";
		String expectedText = "";
		String expectedLinkText = "";

		if (page.clickonanyoneOtherThanThePolicyOwnerBePayingYesButton()
				&& page.getLanguageToggleButton().getText().equalsIgnoreCase("FR")) {
			actualText = page.getDivTextofPayingThirdParty();
			actualLinkText = page.getLinkTextofPayingThirdParty().getText();
			page.getLinkTextofPayingThirdParty().click();
			expectedText = getDivText[0];
			expectedLinkText = getLinkText[0];

			Assert.assertTrue(actualText.contains(expectedText));
			Assert.assertEquals(expectedLinkText, actualLinkText);

			// Now Choose language as "French"

			page.getLanguageToggleButton().click();

			actualText = page.getDivTextofPayingThirdParty();
			actualLinkText = page.getLinkTextofPayingThirdParty().getText();
			page.getLinkTextofPayingThirdParty().click();
			expectedText = getDivText[1];
			expectedLinkText = getLinkText[1];

			Assert.assertTrue(actualText.contains(expectedText));
			Assert.assertEquals(expectedLinkText, actualLinkText);

			page.getLanguageToggleButton().click();

		}
		// on click of no option for the first field no reflexive fields should display
		if (page.clickonanyoneOtherThanThePolicyOwnerBePayingNoButton()) {

			Assert.assertTrue("Fail: We have found the link of Term conversion application form",
					page.checkLinkOnPage());
		}

	}

	@Test(dataProvider = "IdentifyingThirdpartiesTestData", testName = "Validate Field label-Does the owner intend to transfer the ownership of this policy to another individual or legal entity within the next year?", groups = "webBrowser")
	public void ownerIntendToTransferTheOwnership(Map<String, String> data) throws InterruptedException {
		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();

		fillPagesUntilBillingPagePage(data);
		if (page.clickonanyoneOtherThanThePolicyOwnerBePayingNoButton()
				&& page.ClikcOnOwnerIntendToTransferTheOwnershipYesORNoButton(
						data.get("IdentifyingThirdparties_transfertheownership"))) { // to verify the subsection is
																						// displaying or not.

			page.ClickedIndividualButton().click();

			page.getFirstName().sendKeys(data.get("IdentifyingThirdparties_FirstName"));
			page.getMiddleName().sendKeys(data.get("IdentifyingThirdparties_MiddleInitial"));
			page.getLastName().sendKeys(data.get("IdentifyingThirdparties_LastName"));
			page.getDay().sendKeys(data.get("ThirdPartyInformation_Day"));

			page.selectMonthOFDOB(data.get("ThirdPartyInformation_Month"));
			page.getYear().sendKeys(data.get("ThirdPartyInformation_Year"));
			String relationShipDropdownValue = data.get("RelationshipToTheOwner");
			if (relationShipDropdownValue.equalsIgnoreCase("Others")) {
				// If Relationship to the owner is "others" then fill the textbox
				page.getRelationshipOtherTextbox().sendKeys(data.get("RelationshipToTheOwnerOther"));
			} else {
				page.selectRelationshipToOwner(relationShipDropdownValue);
			}

			page.getAddressOne().sendKeys(data.get("Address1"));
			page.getAddressTwo().sendKeys("noyal");
			page.getCityorTownTextbox().sendKeys(data.get("CityOrTown"));
			page.selectProvince(data.get("Province"));
			page.getpostalCodeTextbox().sendKeys(data.get("Postalcode"));
			page.getSomeoneElseWhoHasIndirectControl().click();
			page.clickNextBtn();

			Assert.assertTrue("Passed:Identifying third parties page script executed successfully !", true);

		} else {
			Assert.fail("Unable to perfrom exepected steps");
		}
	}

	@Test(priority = 0, dataProvider = "IdentifyingThirdpartiesTestData", testName = "Verify Required Error Message:If we are leaving all 3 fields as blank and clicking on Next button", groups = "webBrowser")
	public void verfiyRequiredErrorMessageForThirdPartiesPage(Map<String, String> data) throws InterruptedException {
		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();
		fillPagesUntilBillingPagePage(data);
		page.clickNextBtn();
		Assert.assertTrue(
				"Fail: Required Field message is not showing for (Will anyone other than the policy owner be paying for this policy)",
				page.getAnyoneOtherThanPolicyOwnerPayingOptionsErrorLable().getText().equalsIgnoreCase("Required"));
		Assert.assertTrue(
				"Fail: Required Field message is not showing for (Does the owner intend to transfer the ownership of this policy to another individual or legal entity within the next year?)",
				page.getOwnerIntendToTransferOwnershipOptionsErrorLable().getText().equalsIgnoreCase("Required"));
		Assert.assertTrue(
				"Fail: Required Field message is not showing for (Is there someone else who has indirect control or an interest in this policy?)",
				page.getSomeoneElseWhoHasIndirectControlOptionsErrorLable().getText().equalsIgnoreCase("Required"));
	}

	@Test(priority = 1, dataProvider = "IdentifyingThirdpartiesTestData", testName = "Verify Required Error Message for subsection", groups = "webBrowser")
	public void verfiyRequiredErrorMessageForSubsection(Map<String, String> data) throws InterruptedException {
		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();
		fillPagesUntilBillingPagePage(data);
		if (page.clickonanyoneOtherThanThePolicyOwnerBePayingNoButton()
				&& page.ClikcOnOwnerIntendToTransferTheOwnershipYesORNoButton(
						data.get("IdentifyingThirdparties_transfertheownership"))) {

			page.ClickedIndividualButton().click();
			page.clickNextBtn();

			Assert.assertTrue("Fail: Required Field message is not showing for (First Name)",
					page.getFirtNameErrorLable().getText().equalsIgnoreCase("Required"));
			Assert.assertTrue("Fail: Required Field message is not showing for (Last Name)",
					page.getLastNameErrorLable().getText().equalsIgnoreCase("Required"));
			Assert.assertTrue("Fail: Required Field message is not showing for (DOB)",
					page.getDOBErrorLable().getText().equalsIgnoreCase("Required"));

			Assert.assertTrue("Fail: Required Field message is not showing for (Relationship to owner)",
					page.getrelationshipToTheOwnerErrorLable().getText().equalsIgnoreCase("Required"));
			Assert.assertTrue("Fail: Required Field message is not showing for (Address1)",
					page.getaddress1ErrorLable().getText().equalsIgnoreCase("Required"));
			Assert.assertTrue("Fail: Required Field message is not showing for (City or Town)",
					page.getcityOrTownErrorLable().getText().equalsIgnoreCase("Required"));
			Assert.assertTrue("Fail: Required Field message is not showing for (Province)",
					page.getProvinceErrorLable().getText().equalsIgnoreCase("Required"));
			Assert.assertTrue("Fail: Required Field message is not showing for (Postal code)",
					page.getPostalcodeErrorLable().getText().equalsIgnoreCase("Required"));

			// Now select relationship as "Others"
			page.selectRelationshipToOwner("Other");
			page.clickNextBtn();
			Assert.assertTrue("Fail: Required Field message is not showing for (Relationship to owner as others)",
					page.getrelationshipToTheOwnerOthersErrorLable().getText().equalsIgnoreCase("Required"));

		} else {
			Assert.fail("because of filled all fields Required error message is not displayed");
		}
	}

	@Test(priority = 2, dataProvider = "IdentifyingThirdpartiesTestData", testName = "Verify Invalid Date Error Message", groups = "webBrowser")
	public void verifyInvalidDateErrorMessage(Map<String, String> data) throws InterruptedException {

		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();
		fillPagesUntilBillingPagePage(data);
		if (page.clickonanyoneOtherThanThePolicyOwnerBePayingNoButton()
				&& page.ClikcOnOwnerIntendToTransferTheOwnershipYesORNoButton(
						data.get("IdentifyingThirdparties_transfertheownership"))) {
			LocalDate date = LocalDate.now();
			page.ClickedIndividualButton().click();
			page.getFirstName().sendKeys(data.get("IdentifyingThirdparties_FirstName"));
			page.getMiddleName().sendKeys(data.get("IdentifyingThirdparties_MiddleInitial"));
			page.getLastName().sendKeys(data.get("IdentifyingThirdparties_LastName"));

			String dayData = data.get("ThirdPartyInformation_Day");
			String yearData = data.get("ThirdPartyInformation_Year");
			// Invalid Date error message is displayed if day is selected >31 and year is
			// current year
			page.getDay().sendKeys(dayData);
			page.selectMonthOFDOB(data.get("ThirdPartyInformation_Month"));
			String year = Integer.toString(date.getYear());
			page.getYear().sendKeys(year);
			page.clickNextBtn();

			Assert.assertTrue("Fail: Invalid day (" + dayData + ") is selected",
					page.getDOBErrorLable().getText().equalsIgnoreCase("Invalid date"));
			// Invalid Date error message is displayed if year is <1900 and day is correct
			page.getLegalEntityField().click();
			page.getIndividualField().click();
			page.getDay().clear();

			page.getDay().sendKeys("12");
			page.selectMonthOFDOB(data.get("ThirdPartyInformation_Month"));
			System.out.println("--Incorrect date is : " + yearData);
			page.getYear().clear();

			page.getYear().sendKeys(yearData);
			page.clickNextBtn();
			Assert.assertTrue("Fail: Invalid Year (" + yearData + ") is selected",
					page.getDOBErrorLable().getText().equalsIgnoreCase("Invalid date"));

			// Future date is selected
			page.getLegalEntityField().click();
			page.getIndividualField().click();
			page.getDay().clear();

			page.getDay().sendKeys("23");
			page.selectMonthOFDOB(data.get("ThirdPartyInformation_Month"));
			year = Integer.toString(date.getYear() + 1);

			page.getYear().clear();

			page.getYear().sendKeys(year);
			page.clickNextBtn();
			Assert.assertTrue("Fail: We are choosing future Year (" + year + ")",
					page.getDOBErrorLable().getText().equalsIgnoreCase("Date cannot be in the future"));

		} else {
			Assert.fail("Third party Information subsection is not displayed ");
		}
	}

	@Test(priority = 2,enabled=true, dataProvider = "IdentifyingThirdpartiesTestData", testName = "Verify Invalid Date Error Message", groups = "webBrowser")
	public void verifyInvalidDateErrorMessageFR(Map<String, String> data) throws InterruptedException {

		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();
		fillPagesUntilBillingPagePage(data);
		if (page.getLanguageToggleButton().getText().equals("FR")) {
			page.getLanguageToggleButton().click();
			Assert.assertEquals("Identification des tiers", page.getPageTitleOfThirdPartyInformationPage());
		}

		if (page.clickonanyoneOtherThanThePolicyOwnerBePayingNoButton()&& page.ClikcOnOwnerIntendToTransferTheOwnershipYesORNoButton(
						data.get("IdentifyingThirdparties_transfertheownership"))) {
			LocalDate date = LocalDate.now();
			page.ClickedIndividualButton().click();
			page.getFirstName().sendKeys(data.get("IdentifyingThirdparties_FirstName"));
			page.getMiddleName().sendKeys(data.get("IdentifyingThirdparties_MiddleInitial"));
			page.getLastName().sendKeys(data.get("IdentifyingThirdparties_LastName"));

			String dayData = data.get("ThirdPartyInformation_Day");
			String month = data.get("ThirdPartyInformation_Month");
			String yearData = data.get("ThirdPartyInformation_Year");
			// Invalid Date error message is displayed if day is selected >31 and year is
			// current year
			page.getDay().sendKeys(dayData);
			page.selectMonthOFDOB(month);
			String year = Integer.toString(date.getYear());
			page.getYear().sendKeys(year);
			page.clickNextBtn();

			Assert.assertTrue("Fail: Invalid day (" + dayData + ") is selected",
					page.getDOBErrorLable().getText().equalsIgnoreCase("Date invalide"));
			// Invalid Date error message is displayed if year is <1900 and day is correct
			page.getLegalEntityField().click();
			page.getIndividualField().click();
			page.getDay().clear();

			page.getDay().sendKeys("12");
			page.selectMonthOFDOB(month);
			System.out.println("--Incorrect date is : " + yearData);
			page.getYear().clear();

			page.getYear().sendKeys(yearData);
			page.clickNextBtn();
			Assert.assertTrue("Fail: Invalid Year (" + yearData + ") is selected",
					page.getDOBErrorLable().getText().equalsIgnoreCase("Date invalide"));

			// Future date is selected
			page.getLegalEntityField().click();
			page.getIndividualField().click();
			page.getDay().clear();

			page.getDay().sendKeys("23");
			page.selectMonthOFDOB(month);
			year = Integer.toString(date.getYear() + 1);
			System.out.println("----------Future Date is---- " + year);
			page.getYear().clear();

			page.getYear().sendKeys(year);
			page.clickNextBtn();
			Assert.assertTrue("Fail: We are choosing future Year (" + year + ")", page.getDOBErrorLable().getText()
					.equalsIgnoreCase("La date ne peut pas être postérieure à la date du jour"));

		} else {
			Assert.fail("Third party Information subsection is not displayed ");
		}
	}

	@Test(priority = 3, dataProvider = "IdentifyingThirdpartiesTestData", testName = "Verify Invalid postal code error message", groups = "webBrowser")
	public void verifyInvalidPostalCodeErrorMessage(Map<String, String> data) throws InterruptedException {

		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();
		fillPagesUntilBillingPagePage(data);
		if (page.clickonanyoneOtherThanThePolicyOwnerBePayingNoButton()
				&& page.ClikcOnOwnerIntendToTransferTheOwnershipYesORNoButton(
						data.get("IdentifyingThirdparties_transfertheownership"))) {
			LocalDate date = LocalDate.now();
			page.ClickedIndividualButton().click();
			page.getFirstName().sendKeys(data.get("IdentifyingThirdparties_FirstName"));
			page.getMiddleName().sendKeys(data.get("IdentifyingThirdparties_MiddleInitial"));
			page.getLastName().sendKeys(data.get("IdentifyingThirdparties_LastName"));

			String dayData = data.get("ThirdPartyInformation_Day");
			String yearData = data.get("ThirdPartyInformation_Year");
			page.getDay().sendKeys(dayData);
			page.selectMonthOFDOB(data.get("ThirdPartyInformation_Month"));
			String year = Integer.toString(date.getYear());
			page.getYear().sendKeys(year);
			page.getAddressOne().sendKeys(data.get("Address1"));
			page.getAddressTwo().sendKeys("xyz@address2");
			page.getCityorTownTextbox().sendKeys(data.get("CityOrTown"));
			page.selectProvince(data.get("Province"));
			String postalCode = data.get("Postalcode");
			page.getpostalCodeTextbox().sendKeys(postalCode);
			page.clickNextBtn();
			Assert.assertTrue("Fail: We have entered the wrong postal code (" + postalCode + ")",
					page.getPostalcodeErrorLable().getText().equalsIgnoreCase("Invalid postal code"));

		} else {
			Assert.fail("We have not found sub header section ");
		}
	}

	

	@Test(priority = 5, dataProvider = "IdentifyingThirdpartiesTestData", testName = "Verify tooltip is displaying on mouse hover", groups = "webBrowser")
	public void verifyTheToolTip(Map<String, String> data) throws InterruptedException {
		ThirdPartyInformationPage page = new ThirdPartyInformationPage(driverUtil);
		page.startApplication();
		fillPagesUntilBillingPagePage(data);
		String actTooltip = page.getSomeoneElseWhoHasIndirectControlToolTipLabel();

		String expectedTooltipValue = data.get("ThirdPartyToolTip");
		
		System.out.println("Act ---" + actTooltip + "|| Ect" + expectedTooltipValue);
		Assert.assertEquals(expectedTooltipValue, actTooltip);
	}

	public String[] getDataForValidation(String value) {
		String[] getText = value.split(Pattern.quote("|"));
		return getText;

	}

}
