package term_conversion;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

public class BeneficiaryInformationTest extends BaseTest {

	public void fillPagesUntilBeneficiaryPage(Map<String, String> data) {
		FillPages fillPage = new FillPages(driverUtil);
		//fillPage.fillLogin();
		
		CommonElements commonElements = new CommonElements(driverUtil);
		
		commonElements.startApplication();
		fillPage.fillPolicyPage(data);
		fillPage.fillInsuredPage(data);
		fillPage.fillOwnerPage(data);
		fillPage.fillSuccessorPage(data);
		fillPage.fillCoveragePage(data);
		fillPage.fillProductPage(data);
	}
	
	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en", "policyInfoUrl");
	}


	@DataProvider(name="beneficiaryInfoTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception{
		 //Getting the Data Row against the Test Case name and store it within an array
		 Object[][] testObjArray = excelUtil.getAllMatchingTestCases("beneficiaryInfoData", method.getName());
		 return (testObjArray);
	}
	
	

	public void fillPrimaryBeneficiaryModal(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		beneficiaryInfo.getAddBeneficiaryButton().click();
		fillPrimaryCommonBeneficiaryModal(data);
	}

	public void fillPrimaryBeneficiary2Modal(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		beneficiaryInfo.getAddBeneficiaryButton2().click();
		fillPrimaryCommonBeneficiaryModal(data);
	}

	public void fillPrimaryCommonBeneficiaryModal(Map<String, String> data) {
		
		String quebecOptions = data.get("policyInfo_IsIssuedQuebec");
		String minorStatus = data.get("beneficiaryInfo_MinorChild");
		String trustyStatus = data.get("beneficiaryInfo_NameATrustee");
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);

		beneficiaryInfo.getBeneficiaryFirstName().sendKeys(data.get("beneficiaryInfo_TestFirstName"));
		beneficiaryInfo.getBeneficiaryMiddleName().sendKeys(data.get("beneficiaryInfo_TestMiddleName"));
		beneficiaryInfo.getBeneficiaryLastName().sendKeys(data.get("beneficiaryInfo_TestLastName"));
		beneficiaryInfo.selectOwnerInsuredRelationship(data.get("beneficiaryInfo_TestRelationshipEN"));
		beneficiaryInfo.getBeneficiaryShare().sendKeys(data.get("beneficiaryInfo_TestShare"));
		beneficiaryInfo.getBeneficiaryDesignation(data.get("beneficiaryInfo_TestDesignationEN")).click();
		if (quebecOptions.equalsIgnoreCase("Yes")) {
			
			beneficiaryInfo.getSaveCloseBtn().click();
		}else if (quebecOptions.equalsIgnoreCase("No")) {
			
			beneficiaryInfo.getIsBeneficiaryAMinor(minorStatus).click();
			if (minorStatus.equalsIgnoreCase("Yes")) {

				beneficiaryInfo.getNameBeneficiaryTrustee(trustyStatus).click();
				if (trustyStatus.equalsIgnoreCase("Yes")) {
					beneficiaryInfo.getTrusteeFirstName().sendKeys(data.get("beneficiaryInfo_TrusteeFirstName"));
					beneficiaryInfo.getTrusteeMiddleName().sendKeys(data.get("beneficiaryInfo_TrusteeMiddleName"));
					beneficiaryInfo.getTrusteeLastName().sendKeys(data.get("beneficiaryInfo_TrusteeLastName"));
					beneficiaryInfo.selectTrusteeRelationship(data.get("beneficiaryInfo_TrusteeRelationship"));
				}
			}
			beneficiaryInfo.getSaveCloseBtn().click();
		}
	
		
	}

	public void fillSecondaryBeneficiaryModal(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		beneficiaryInfo.getAddSecondaryBeneficiaryButton().click();
		fillSecondaryCommonBeneficiaryModal(data);
	}

	public void fillSecondaryBeneficiary2Modal(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		beneficiaryInfo.getAddSecondaryBeneficiaryButton2().click();
		fillSecondaryCommonBeneficiaryModal(data);
	}

	public void fillSecondaryCommonBeneficiaryModal(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);

		beneficiaryInfo.getBeneficiaryFirstName().sendKeys(data.get("beneficiaryInfo_TestFirstName"));
		beneficiaryInfo.getBeneficiaryMiddleName().sendKeys(data.get("beneficiaryInfo_TestMiddleName"));
		beneficiaryInfo.getBeneficiaryLastName().sendKeys(data.get("beneficiaryInfo_TestLastName2"));
		beneficiaryInfo.selectOwnerInsuredRelationship(data.get("beneficiaryInfo_TestRelationshipEN"));
		beneficiaryInfo.getBeneficiaryShare().sendKeys(data.get("beneficiaryInfo_TestShare"));

		// beneficiaryInfo.getBeneficiary2DesignationInput("Revocable").click();
		// beneficiaryInfo.getIsBeneficiaryAMinor("No").click();
		beneficiaryInfo.getSaveCloseBtn().click();
	}	
	
	
	
	@Test(priority=0,enabled=true,dataProvider="beneficiaryInfoTestData", testName="Verify fields - Primary Beneficiary Overlay", groups="webBrowser")
	public void verifyFieldsPrimaryBeneficiaryTest(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);		
		fillPagesUntilBeneficiaryPage(data);
		
		String firstNameTestStr = "FirstName";
		String middleNameTestStr = "M";
		String lastNameTestStr = "LastName";

		// Verify that Beneficiary modal appears when button is clicked
		Assert.assertFalse("Fail: Beneficiary Modal should not be visible when nothing is selected",
				beneficiaryInfo.isBeneficiaryModalVisible());
		beneficiaryInfo.getAddBeneficiaryButton().click(); // Open Primary Beneficiary Overlay
		Assert.assertTrue("Fail: Beneficiary Modal should be visible when Add Beneficiary is selected",
				beneficiaryInfo.isBeneficiaryModalVisible());
		// Verify common beneficiary fields
		verifyCommonBeneficiayFields(data);

		// Verify Designation Fields
		beneficiaryInfo.getBeneficiaryDesignation("Revocable").click();
		Assert.assertTrue("Fail: The user should be able to select Revocable", beneficiaryInfo
				.getBeneficiaryDesignationInput("Revocable").getAttribute("aria-pressed").equals("true"));
		beneficiaryInfo.getBeneficiaryDesignation("Irrevocable").click();
		Assert.assertTrue("Fail: The user should be able to select Irrevocable", beneficiaryInfo
				.getBeneficiaryDesignationInput("Irrevocable").getAttribute("aria-pressed").equals("true"));
		Assert.assertFalse("Fail: The user should only be able to select one designation", beneficiaryInfo
				.getBeneficiaryDesignationInput("Revocable").getAttribute("aria-pressed").equals("true"));

		// Verify Is this beneficiary a minor child field = 'No'
		beneficiaryInfo.getIsBeneficiaryAMinor("No").click();
		Assert.assertTrue("Fail: The user should be able to select No for Is the Beneficiary a minor",
				beneficiaryInfo.getIsBeneficiaryAMinorInput("No").getAttribute("aria-pressed").equals("true"));
		// Verify that the trustee field is not present
		Assert.assertFalse("Fail: Name a trustee field should not be present when beneficiary is not a minor",
				beneficiaryInfo.isNameBeneficiaryTrusteeVisible());
		// Verify Is this beneficiary a minor child field = 'Yes' and only one field can
		// be selected
		beneficiaryInfo.getIsBeneficiaryAMinor("Yes").click();
		Assert.assertTrue("Fail: The user should be able to select Yes for Is the Beneficiary a minor",
				beneficiaryInfo.getIsBeneficiaryAMinorInput("Yes").getAttribute("aria-pressed").equals("true"));
		Assert.assertFalse(
				"Fail: The user should only be able to select one value for Is the Beneficiary a minor field",
				beneficiaryInfo.getIsBeneficiaryAMinorInput("No").getAttribute("aria-pressed").equals("true"));
		// Verify that the trustee field is present
		Assert.assertTrue("Fail: Name a trustee field should be present when beneficiary is a minor",
				beneficiaryInfo.isNameBeneficiaryTrusteeVisible());

		// Verify the name a trustee field = 'No'
		beneficiaryInfo.getNameBeneficiaryTrustee("No").click();
		Assert.assertTrue("Fail: The user should be able to select No for the Name a Trustee Field",
				beneficiaryInfo.getNameBeneficiaryTrusteeInput("No").getAttribute("aria-pressed").equals("true"));
		// Verify that trustee input fields are not there
		Assert.assertFalse("Fail: First Name field should not be present when the user does not want to name a trustee",
				beneficiaryInfo.isTrusteeFirstNameVisible());
		Assert.assertFalse(
				"Fail: Middle Initial field should not be present when the user does not want to name a trustee",
				beneficiaryInfo.isTrusteeMiddleNameVisible());
		Assert.assertFalse("Fail: Last Name field should not be present when the user does not want to name a trustee",
				beneficiaryInfo.isTrusteeLastNameVisible());
		Assert.assertFalse(
				"Fail: Relationship to th Beneficiary field should not be present when the user does not want to name a trustee",
				beneficiaryInfo.isTrusteeRelationshipVisible());
		// Verify the name a trustee field = 'Yes' and only one trustee field can be
		// selected
		beneficiaryInfo.getNameBeneficiaryTrustee("Yes").click();
		Assert.assertTrue("Fail: The user should be able to select Yes for the Name a Trustee Field",
				beneficiaryInfo.getNameBeneficiaryTrusteeInput("Yes").getAttribute("aria-pressed").equals("true"));
		Assert.assertFalse("Fail: The user should only be able to select one value for the Name a Trustee Field",
				beneficiaryInfo.getNameBeneficiaryTrusteeInput("No").getAttribute("aria-pressed").equals("true"));
		// Verify that trustee input fields are there
		Assert.assertTrue("Fail: First Name field is not present when the user does not want to name a trustee",
				beneficiaryInfo.isTrusteeFirstNameVisible());
		Assert.assertTrue("Fail: Middle Initial field is not present when the user does not want to name a trustee",
				beneficiaryInfo.isTrusteeMiddleNameVisible());
		Assert.assertTrue("Fail: Last Name field is not present when the user does not want to name a trustee",
				beneficiaryInfo.isTrusteeLastNameVisible());
		Assert.assertTrue(
				"Fail: Relationship to th Beneficiary field is not present when the user does not want to name a trustee",
				beneficiaryInfo.isTrusteeRelationshipVisible());

		// Verify Trustee Fields
		beneficiaryInfo.getTrusteeFirstName().sendKeys(firstNameTestStr);
		beneficiaryInfo.getTrusteeMiddleName().sendKeys(middleNameTestStr);
		beneficiaryInfo.getTrusteeLastName().sendKeys(lastNameTestStr);
		Assert.assertEquals("Fail: Beneficiary First Name should accept values", firstNameTestStr,
				beneficiaryInfo.getTrusteeFirstName().getAttribute("value"));
		Assert.assertEquals("Fail: Beneficiary Middle Name should accept values", middleNameTestStr,
				beneficiaryInfo.getTrusteeMiddleName().getAttribute("value"));
		Assert.assertEquals("Fail: Beneficiary Last Name should accept values", lastNameTestStr,
				beneficiaryInfo.getTrusteeLastName().getAttribute("value"));
		Assert.assertEquals("Fail: Beneficiary First Name has incorrect maxlength", "25",
				beneficiaryInfo.getTrusteeFirstName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Beneficiary Middle Name has incorrect maxlength", "25",
				beneficiaryInfo.getTrusteeMiddleName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Beneficiary Last Name has incorrect maxlength", "25",
				beneficiaryInfo.getTrusteeLastName().getAttribute("maxlength"));

		// Verify Trustee Relationship Fields
		Assert.assertArrayEquals("Fail: Incorrect EN option for relationship to owner/insured field",
				data.get("beneficiaryInfo_OwnerInsuredRelationshipEN").split(";"),
				beneficiaryInfo.getTrusteeRelationshipLabels());
		// Verify that relationship can be selected
		beneficiaryInfo.selectTrusteeRelationship("Brother");
		Assert.assertEquals("Fail: The user should be able to select a trustee relationship", "Brother",
				beneficiaryInfo.getTrusteeRelationship().getText());
	}

	@Test(priority=1,enabled=true,dataProvider="beneficiaryInfoTestData", testName ="Verify fields - Secondary Beneficiary Overlay", groups="webBrowser")
	public void verifyFieldsSecondaryBeneficiaryTest(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		fillPagesUntilBeneficiaryPage(data);
// Verify when add secondary button
		Assert.assertFalse("Fail: Secondary Button should not be visible if there is no primary beneficiary data",
				beneficiaryInfo.isSecondaryAddBeneficiaryBtnVisible());
		fillPrimaryBeneficiaryModal(data);
		Assert.assertTrue("Fail: Secondary Button should be visible if there is primary beneficiary data",
				beneficiaryInfo.isSecondaryAddBeneficiaryBtnVisible());
		// Verify that Beneficiary modal appears when button is clicked
		Assert.assertFalse("Fail: Beneficiary Modal should not be visible when nothing is selected",
				beneficiaryInfo.isBeneficiaryModalVisible());
		beneficiaryInfo.getAddSecondaryBeneficiaryButton().click(); // Open Primary Beneficiary Overlay
		Assert.assertTrue("Fail: Beneficiary Modal should be visible when Add Beneficiary is selected",
				beneficiaryInfo.isBeneficiaryModalVisible());
		// Verify fields in secondary modal
		verifyCommonBeneficiayFields(data);
	}

	public void verifyCommonBeneficiayFields(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		String firstNameTestStr = "FirstName";
		String middleNameTestStr = "M";
		String lastNameTestStr = "LastName";

		// Verify Beneficiary Name Fields
		beneficiaryInfo.getBeneficiaryFirstName().sendKeys(firstNameTestStr);
		beneficiaryInfo.getBeneficiaryMiddleName().sendKeys(middleNameTestStr);
		beneficiaryInfo.getBeneficiaryLastName().sendKeys(lastNameTestStr);
		Assert.assertEquals("Fail: Beneficiary First Name should accept values", firstNameTestStr,
				beneficiaryInfo.getBeneficiaryFirstName().getAttribute("value"));
		Assert.assertEquals("Fail: Beneficiary Middle Name should accept values", middleNameTestStr,
				beneficiaryInfo.getBeneficiaryMiddleName().getAttribute("value"));
		Assert.assertEquals("Fail: Beneficiary Last Name should accept values", lastNameTestStr,
				beneficiaryInfo.getBeneficiaryLastName().getAttribute("value"));
		Assert.assertEquals("Fail: Beneficiary First Name has incorrect maxlength", "25",
				beneficiaryInfo.getBeneficiaryFirstName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Beneficiary Middle Name has incorrect maxlength", "25",
				beneficiaryInfo.getBeneficiaryMiddleName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Beneficiary Last Name has incorrect maxlength", "25",
				beneficiaryInfo.getBeneficiaryLastName().getAttribute("maxlength"));

		// Verify Owner/Insured Relationship Fields
		Assert.assertArrayEquals("Fail: Incorrect EN option for relationship to owner/insured field",
				data.get("beneficiaryInfo_OwnerInsuredRelationshipEN").split(";"),
				beneficiaryInfo.getOwnerInsuredRelationshipLabels());
		beneficiaryInfo.selectOwnerInsuredRelationship("Brother");
		Assert.assertEquals("Fail: The user should be able to select a beneficiary relationship", "Brother",
				beneficiaryInfo.getOwnerInsuredRelationship());

		// Percentage Share
		beneficiaryInfo.getBeneficiaryShare().sendKeys("999");
		Assert.assertEquals("Fail: Beneficiary should only accept values between 1-100", 2,
				beneficiaryInfo.getBeneficiaryShare().getAttribute("value").length());
		beneficiaryInfo.getBeneficiaryShare().sendKeys(Keys.chord(Keys.CONTROL, "a"), "100");
		Assert.assertEquals("Fail: Beneficiary should accept values between 1-100", "100",
				beneficiaryInfo.getBeneficiaryShare().getAttribute("value"));
	}

	@Test(priority = 2,enabled=true, dataProvider = "beneficiaryInfoTestData", testName = "Verify Table Headers - Primary Beneficiary Quebec", groups = "webBrowser")
	public void verifyQuebecPrimaryBeneficiaryTableHeader(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);
		// Verify Table Headers
		Assert.assertArrayEquals("Fail: Primary Beneficiary Table EN Header is incorrect for Issued in Quebec ='Yes'",
				data.get("beneficiaryInfo_QuebecPrimaryTableHeaderEN").split(";"),
				beneficiaryInfo.getPrimaryTableHeader());
		commonElements.clickENFRToggle();
		String[] myHeaders = data.get("beneficiaryInfo_QuebecPrimaryTableHeaderFR").split(Pattern.quote(";"));
		System.out.println("Size my header" + myHeaders.length);

		String[] myActHeaders = beneficiaryInfo.getPrimaryTableHeader();
		System.out.println("Size my header" + myActHeaders.length);

		Assert.assertArrayEquals("Fail: Primary Beneficiary Table FR Header is incorrect for Issued in Quebec = 'Yes'",
				myHeaders, beneficiaryInfo.getPrimaryTableHeader());
	}

	@Test(priority = 3, enabled = true, dataProvider = "beneficiaryInfoTestData", testName = "Not a Valid test case-Verify Table Headers - Primary Beneficiary Non Quebec", groups = "webBrowser")
	public void verifyNonQuebecPrimaryBeneficiaryTableHeader(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);
		// Verify Table Headers
		Assert.assertArrayEquals("Fail: Primary Beneficiary Table EN Header is incorrect for Issued in Quebec ='No'",
				data.get("beneficiaryInfo_NonQuebecPrimaryTableHeaderEN").split(";"),
				beneficiaryInfo.getPrimaryTableHeader());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals("Fail: Primary Beneficiary Table FR Header is incorrect for Issued in Quebec = 'No'",
				data.get("beneficiaryInfo_NonQuebecPrimaryTableHeaderFR").split(";"),
				beneficiaryInfo.getPrimaryTableHeader());
	}

	@Test(priority = 4, enabled = true, dataProvider = "beneficiaryInfoTestData", testName = "Not Validate test case - Verify Table Headers - Secondary Beneficiary Quebec", groups = "webBrowser")
	public void verifyQuebecSecondaryBeneficiaryTableHeader(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);
		fillSecondaryBeneficiaryModal(data);
		// Verify Table Headers
		Assert.assertArrayEquals("Fail: Secondary Beneficiary Table EN Header is incorrect for Issued in Quebec ='Yes'",
				data.get("beneficiaryInfo_QuebecSecondaryTableHeaderEN").split(";"),
				beneficiaryInfo.getSecondaryTableHeader());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals(
				"Fail: Secondary Beneficiary Table FR Header is incorrect for Issued in Quebec = 'Yes'",
				data.get("beneficiaryInfo_QuebecSecondaryTableHeaderFR").split(";"),
				beneficiaryInfo.getSecondaryTableHeader());
	}

	@Test(priority = 5, enabled = true, dataProvider = "beneficiaryInfoTestData", testName = "Verify Table Values - Primary Beneficiary", groups = "webBrowser")
	public void verifyPrimaryBeneficiaryTableValues(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);
		// Verify Table Headers
		String testNameStr = data.get("beneficiaryInfo_TestFirstName") + " "
				+ data.get("beneficiaryInfo_TestMiddleName") + " " + data.get("beneficiaryInfo_TestLastName");
		String[] beneficiaryTableValuesEN = { testNameStr, data.get("beneficiaryInfo_TestRelationshipEN"),
				data.get("beneficiaryInfo_TestDesignationEN"), data.get("beneficiaryInfo_TestShare"),
				data.get("beneficiaryInfo_ChangeEN"), data.get("beneficiaryInfo_DeleteEN") };
		String[] beneficiaryTableValuesFR = { testNameStr, data.get("beneficiaryInfo_TestRelationshipFR"),
				data.get("beneficiaryInfo_TestDesignationFR"), data.get("beneficiaryInfo_TestShare"),
				data.get("beneficiaryInfo_ChangeFR"), data.get("beneficiaryInfo_DeleteFR") };

		Assert.assertArrayEquals("Fail: Primary Beneficiary Table EN Values are incorrect", beneficiaryTableValuesEN,
				beneficiaryInfo.getPrimaryTableRow(0));
		Assert.assertEquals("Fail: Primary Beneficiary Total Share EN label is incorrect",
				data.get("beneficiaryInfo_TotalShareEN"), beneficiaryInfo.getPrimaryShareTitle());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals("Fail: Primary Beneficiary Table FR Values are incorrect", beneficiaryTableValuesFR,
				beneficiaryInfo.getPrimaryTableRow(0));
		Assert.assertEquals("Fail: Primary Beneficiary Total Share FR label is incorrect",
				data.get("beneficiaryInfo_TotalShareFR"), beneficiaryInfo.getPrimaryShareTitle());
	}

	@Test(priority = 6,enabled=true, dataProvider = "beneficiaryInfoTestData", testName = "Verify Table Values - Secondary Beneficiary", groups = "webBrowser")
	public void verifySecondaryBeneficiaryTableValues(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);
		fillSecondaryBeneficiaryModal(data);
		// Verify Table Headers
		String testNameStr = data.get("beneficiaryInfo_TestFirstName") + " "
				+ data.get("beneficiaryInfo_TestMiddleName") + " " + data.get("beneficiaryInfo_TestLastName2");
		String[] beneficiaryTableValuesEN = { testNameStr, data.get("beneficiaryInfo_TestRelationshipEN"), " ",
				data.get("beneficiaryInfo_TestShare"), data.get("beneficiaryInfo_ChangeEN"),
				data.get("beneficiaryInfo_DeleteEN") };
		String[] beneficiaryTableValuesFR = { testNameStr, data.get("beneficiaryInfo_TestRelationshipFR"), " ",
				data.get("beneficiaryInfo_TestShare"), data.get("beneficiaryInfo_ChangeFR"),
				data.get("beneficiaryInfo_DeleteFR") };

		Assert.assertArrayEquals("Fail: Secondary Beneficiary Table EN Values are incorrect", beneficiaryTableValuesEN,
				beneficiaryInfo.getSecondaryTableRow(0));
		Assert.assertEquals("Fail: Primary Beneficiary Total Share EN label is incorrect",
				data.get("beneficiaryInfo_TotalShareEN"), beneficiaryInfo.getPrimaryShareTitle());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals("Fail: Secondary Beneficiary Table FR Values are incorrect", beneficiaryTableValuesFR,
				beneficiaryInfo.getSecondaryTableRow(0));
		Assert.assertEquals("Fail: Primary Beneficiary Total Share FR label is incorrect",
				data.get("beneficiaryInfo_TotalShareFR"), beneficiaryInfo.getPrimaryShareTitle());
	}

	@Test(priority = 7, dataProvider = "beneficiaryInfoTestData", testName = "Verify Values - Total Share", groups = "webBrowser")
	public void verifyTotalShareCalculation(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);
		fillPrimaryBeneficiaryModal(data);
		fillSecondaryBeneficiaryModal(data);
		fillSecondaryBeneficiaryModal(data);

		String totalActualShareInt = Integer.toString(Integer.valueOf(data.get("beneficiaryInfo_TestShare"))
				+ Integer.valueOf(data.get("beneficiaryInfo_TestShare"))) + "%";
		Assert.assertEquals("Fail: Total Share calculation is incorrect for primary", totalActualShareInt,
				beneficiaryInfo.getPrimaryShareNumber());
		Assert.assertEquals("Fail: Total Share calculation is incorrect for secondary", totalActualShareInt,
				beneficiaryInfo.getSecondaryShareNumber());
	}

	@Test(priority = 8, dataProvider = "beneficiaryInfoTestData", testName = "Verify Values - Primary Beneficiary Change/Delete", groups = "webBrowser")
	public void verifyChangeDeleteTableButtons(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);
		fillSecondaryBeneficiaryModal(data);
// Verify secondary change and delete buttons
		String testNameStr = data.get("beneficiaryInfo_TestFirstName") + " "
				+ data.get("beneficiaryInfo_TestMiddleName") + " " + data.get("beneficiaryInfo_TestLastName2");
		beneficiaryInfo.selectChangeDelete(testNameStr, "Change");
		Assert.assertTrue("Fail: The beneficiary overlay should appear when the user clicks Change",
				beneficiaryInfo.isBeneficiaryModalVisible());
		beneficiaryInfo.getCancelBtn().click();
		beneficiaryInfo.selectChangeDelete(testNameStr, "Delete");
		Assert.assertFalse("Fail: Secondary Table should be deleted when the only entry is deleted",
				beneficiaryInfo.isSecondaryTableVisible());
// Verify primary change and delete buttons
		testNameStr = data.get("beneficiaryInfo_TestFirstName") + " " + data.get("beneficiaryInfo_TestMiddleName") + " "
				+ data.get("beneficiaryInfo_TestLastName");
		beneficiaryInfo.selectChangeDelete(testNameStr, "Change");
		Assert.assertTrue("Fail: The beneficiary overlay should appear when the user clicks Change",
				beneficiaryInfo.isBeneficiaryModalVisible());
		beneficiaryInfo.getCancelBtn().click();
		beneficiaryInfo.selectChangeDelete(testNameStr, "Delete");
		Assert.assertFalse("Fail: Primary Table should be deleted when the only entry is deleted",
				beneficiaryInfo.isPrimaryTableVisible());
	}

	@Test(priority = 9, dataProvider = "beneficiaryInfoTestData", testName = "Verify Labels - General Validation", groups = "webBrowser")
	public void generalLabelValidation(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		String insuredOneNameTitleFRStr = "Bénéficiaire de " + data.get("insuredInfo_FirstName") + " "
				+ data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");
		String insuredTwoNameTitleFRStr = "Bénéficiaire de " + data.get("insuredInfo_FirstName2") + " "
				+ data.get("insuredInfo_MiddleInitial2") + " " + data.get("insuredInfo_LastName2");
		String insuredOneNameTitleENStr = "Beneficiary for " + data.get("insuredInfo_FirstName") + " "
				+ data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");
		String insuredTwoNameTitleENStr = "Beneficiary for " + data.get("insuredInfo_FirstName2") + " "
				+ data.get("insuredInfo_MiddleInitial2") + " " + data.get("insuredInfo_LastName2");

		Assert.assertEquals("Fail: Incorrect EN Page Title for the Beneficiary Page",
				data.get("beneficiaryInfo_PageTitleEN"), commonElements.getPageTitle());
		Assert.assertEquals("Fail: Incorrect EN Disclaimer Note", data.get("beneficiaryInfo_DisclaimerNote1EN"),
				beneficiaryInfo.getDisclaimerNote1Label());
		Assert.assertEquals("Fail: Incorrect EN Disclaimer Note", data.get("beneficiaryInfo_DisclaimerNote2EN"),
				beneficiaryInfo.getDisclaimerNote2Label());
		Assert.assertEquals("Fail: Insured 1 FR Information Title is incorrect", insuredOneNameTitleENStr,
				beneficiaryInfo.getInsuredOneBeneficiaryTitle());
		Assert.assertEquals("Fail: Insured 2 FR Information Title is incorrect", insuredTwoNameTitleENStr,
				beneficiaryInfo.getInsuredTwoBeneficiaryTitle());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Incorrect FR Page Title for the Beneficiary Page",
				data.get("beneficiaryInfo_PageTitleFR"), commonElements.getPageTitle());
		Assert.assertEquals("Fail: Incorrect FR Disclaimer Note", data.get("beneficiaryInfo_DisclaimerNote1FR"),
				beneficiaryInfo.getDisclaimerNote1Label());
		Assert.assertEquals("Fail: Incorrect FR Disclaimer Note", data.get("beneficiaryInfo_DisclaimerNote2FR"),
				beneficiaryInfo.getDisclaimerNote2Label());
		Assert.assertEquals("Fail: Insured 1 FR Information Title is incorrect", insuredOneNameTitleFRStr,
				beneficiaryInfo.getInsuredOneBeneficiaryTitle());
		Assert.assertEquals("Fail: Insured 2 FR Information Title is incorrect", insuredTwoNameTitleFRStr,
				beneficiaryInfo.getInsuredTwoBeneficiaryTitle());
	}

	@Test(priority = 10, enabled = true, dataProvider = "beneficiaryInfoTestData", testName = "Verify Labels - Primary Overlay", groups = "webBrowser")
	public void verifyPrimaryOverlayLabels(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		String insuredOneNameTitleENStr = "Beneficiary for " + data.get("insuredInfo_FirstName") + " "
				+ data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");
		String insuredOneNameTitleFRStr = "Bénéficiaire de " + data.get("insuredInfo_FirstName") + " "
				+ data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");

		// Validate EN labels
		beneficiaryInfo.getAddBeneficiaryButton().click();
		commonOverlayLabelsEN(data, insuredOneNameTitleENStr);
		Assert.assertEquals("Fail: Overlay EN Designation Label is incorrect",
				data.get("beneficiaryInfo_DesignationLabelEN"), beneficiaryInfo.getDesignationLabel());
		beneficiaryInfo.getBeneficiaryDesignation("Irrevocable").click();
		Assert.assertEquals("Fail: Overlay EN Designation Note is incorrect",
				data.get("beneficiaryInfo_DesignationNoteEN"), beneficiaryInfo.getDesignationNote());
		Assert.assertEquals("Fail: Overlay EN Minor Child Label is incorrect",
				data.get("beneficiaryInfo_MinorChildLabelEN"), beneficiaryInfo.getMinorChildLabel());
		beneficiaryInfo.getIsBeneficiaryAMinor("Yes").click();
		Assert.assertEquals("Fail: Overlay EN Name A Trustee Label is incorrect",
				data.get("beneficiaryInfo_TrusteeLabelEN"), beneficiaryInfo.getNameATrusteeLabel());
		Assert.assertEquals("Fail: Overlay EN Name A Trustee Note Label is incorrect",
				data.get("beneficiaryInfo_TrusteeNoteEN"), beneficiaryInfo.getNameATrusteeNoteLabel());
		beneficiaryInfo.getNameBeneficiaryTrustee("Yes").click();
		Assert.assertEquals("Fail: Overlay EN Trustee Name Label is incorrect",
				data.get("beneficiaryInfo_TrusteeNameEN"), beneficiaryInfo.getTrusteeNameLabel());
		Assert.assertEquals("Fail: Overlay EN Trustee Relationship Label is incorrect",
				data.get("beneficiaryInfo_TrusteeRelationshipEN"), beneficiaryInfo.getTrusteeRelationshipLabelXPath());
		// Validate FR labels
		beneficiaryInfo.getCancelBtn().click();
		commonElements.clickENFRToggle();
		beneficiaryInfo.getAddBeneficiaryButton().click();
		commonOverlayLabelsFR(data, insuredOneNameTitleFRStr);
		Assert.assertEquals("Fail: Overlay FR Designation Label is incorrect",
				data.get("beneficiaryInfo_DesignationLabelFR"), beneficiaryInfo.getDesignationLabel());
		// beneficiaryInfo.getBeneficiaryDesignation("Irrevocable").click();
		Assert.assertEquals("Fail: Overlay FR Designation Note is incorrect",
				data.get("beneficiaryInfo_DesignationNoteFR"), beneficiaryInfo.getDesignationNote());
		Assert.assertEquals("Fail: Overlay FR Minor Child Label is incorrect",
				data.get("beneficiaryInfo_MinorChildLabelFR"), beneficiaryInfo.getMinorChildLabel());
		// beneficiaryInfo.getIsBeneficiaryAMinor("Yes").click();
		Assert.assertEquals("Fail: Overlay FR Name A Trustee Label is incorrect",
				data.get("beneficiaryInfo_TrusteeLabelFR"), beneficiaryInfo.getNameATrusteeLabel());
		Assert.assertEquals("Fail: Overlay FR Name A Trustee Note Label is incorrect",
				data.get("beneficiaryInfo_TrusteeNoteFR"), beneficiaryInfo.getNameATrusteeNoteLabel());
		// beneficiaryInfo.getNameBeneficiaryTrustee("Yes").click();
		Assert.assertEquals("Fail: Overlay FR Trustee Name Label is incorrect",
				data.get("beneficiaryInfo_TrusteeNameFR"), beneficiaryInfo.getTrusteeNameLabel());
		Assert.assertEquals("Fail: Overlay FR Trustee Relationship Label is incorrect",
				data.get("beneficiaryInfo_TrusteeRelationshipFR"), beneficiaryInfo.getTrusteeRelationshipLabelXPath());
	}

	@Test(priority = 11, dataProvider = "beneficiaryInfoTestData", testName = "Verify Labels - Secondary Overlay", groups = "webBrowser")
	public void verifySecondaryOverlayLabels(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);
		String insuredOneNameTitleENStr = "Secondary beneficiary for " + data.get("insuredInfo_FirstName") + " "
				+ data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");
		String insuredOneNameTitleFRStr = "Bénéficiaire sous-ordre pour " + data.get("insuredInfo_FirstName") + " "
				+ data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");

		// Verify EN labels
		beneficiaryInfo.getAddSecondaryBeneficiaryButton().click();
		commonOverlayLabelsEN(data, insuredOneNameTitleENStr);
		// Verify FR labels
		beneficiaryInfo.getCancelBtn().click();
		commonElements.clickENFRToggle();
		beneficiaryInfo.getAddSecondaryBeneficiaryButton().click();
		commonOverlayLabelsFR(data, insuredOneNameTitleFRStr);
	}

	public void commonOverlayLabelsEN(Map<String, String> data, String insuredEN) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);

		Assert.assertEquals("Fail: Overlay EN Header is incorrect", insuredEN,
				beneficiaryInfo.getBeneficiaryOverlayHeader());
		Assert.assertEquals("Fail: Overlay EN Title is incorrect", data.get("beneficiaryInfo_OverlayTitleEN"),
				beneficiaryInfo.getBeneficiaryOverlayTitle());
		Assert.assertEquals("Fail: Overlay EN Beneficiary Name Label is incorrect",
				data.get("beneficiaryInfo_BeneficiaryNameLabelEN"), beneficiaryInfo.getBeneficiaryNameLabel());
		Assert.assertEquals("Fail: Overlay EN Share Label is incorrect", data.get("beneficiaryInfo_ShareLabelEN"),
				beneficiaryInfo.getShareLabel());
		Assert.assertEquals("Fail: Overlay EN Share Label Note is incorrect", data.get("beneficiaryInfo_ShareNoteEN"),
				beneficiaryInfo.getShareLabelNote());
	}

	public void commonOverlayLabelsFR(Map<String, String> data, String insuredFR) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);

		Assert.assertEquals("Fail: Overlay FR Header is incorrect", insuredFR,
				beneficiaryInfo.getBeneficiaryOverlayHeader());
		Assert.assertEquals("Fail: Overlay FR Title is incorrect", data.get("beneficiaryInfo_OverlayTitleFR"),
				beneficiaryInfo.getBeneficiaryOverlayTitle());
		Assert.assertEquals("Fail: Overlay FR Beneficiary Name Label is incorrect",
				data.get("beneficiaryInfo_BeneficiaryNameLabelFR"), beneficiaryInfo.getBeneficiaryNameLabel());
		Assert.assertEquals("Fail: Overlay FR Share Label is incorrect", data.get("beneficiaryInfo_ShareLabelFR"),
				beneficiaryInfo.getShareLabel());
		Assert.assertEquals("Fail: Overlay FR Share Label Note is incorrect", data.get("beneficiaryInfo_ShareNoteFR"),
				beneficiaryInfo.getShareLabelNote());
	}

	@Test(priority = 12, dataProvider = "beneficiaryInfoTestData", testName = "Verify Labels - Quebec Overlay", groups = "webBrowser")
	public void verifyQuebecOverlayLabels(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		fillPagesUntilBeneficiaryPage(data);
		beneficiaryInfo.getAddBeneficiaryButton().click();

		// EN labels
		Assert.assertEquals("Fail: Beneficiary EN Relationship label is incorrect for Quebec policy",
				data.get("beneficiaryInfo_QuebecRelationshipLabelEN"),
				beneficiaryInfo.getBeneficiaryRelationshipLabel());
		Assert.assertEquals("Fail: Beneficiary EN Designation Instruction label is incorrect for Quebec policy",
				data.get("beneficiaryInfo_QuebecDesignationInstructionEN"),
				beneficiaryInfo.getQDesignationInstruction());
		// FR labels
		beneficiaryInfo.getCancelBtn().click();
		commonElements.clickENFRToggle();
		beneficiaryInfo.getAddBeneficiaryButton().click();
		Assert.assertEquals("Fail: Beneficiary FR Relationship label is incorrect for Quebec policy",
				data.get("beneficiaryInfo_QuebecRelationshipLabelFR"),
				beneficiaryInfo.getBeneficiaryRelationshipLabel());
		Assert.assertEquals("Fail: Beneficiary FR Designation Instruction label is incorrect for Quebec policy",
				data.get("beneficiaryInfo_QuebecDesignationInstructionFR"),
				beneficiaryInfo.getQDesignationInstruction());
	}

	@Test(priority = 13, dataProvider = "beneficiaryInfoTestData", testName = "Verify Labels - Non Quebec Overlay", groups = "webBrowser")
	public void verifyNonQuebecOverlayLabels(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		beneficiaryInfo.getAddBeneficiaryButton().click();

		// EN labels
		Assert.assertEquals("Fail: Beneficiary EN Relationship label is incorrect for non-Quebec policy",
				data.get("beneficiaryInfo_NonQuebecRelationshipLabelEN"),
				beneficiaryInfo.getBeneficiaryRelationshipLabel());
		Assert.assertEquals("Fail: Beneficiary EN Designation Instruction label is incorrect for non-Quebec policy",
				data.get("beneficiaryInfo_NonQuebecDesignationInstructionEN"),
				beneficiaryInfo.getNQDesignationInstruction());
		// FR labels
		beneficiaryInfo.getCancelBtn().click();
		commonElements.clickENFRToggle();
		beneficiaryInfo.getAddBeneficiaryButton().click();
		Assert.assertEquals("Fail: Beneficiary FR Relationship label is incorrect for non-Quebec policy",
				data.get("beneficiaryInfo_NonQuebecRelationshipLabelFR"),
				beneficiaryInfo.getBeneficiaryRelationshipLabel());
		Assert.assertEquals("Fail: Beneficiary FR Designation Instruction label is incorrect for non-Quebec policy",
				data.get("beneficiaryInfo_NonQuebecDesignationInstructionFR"),
				beneficiaryInfo.getNQDesignationInstruction());
	}

	@Test(priority = 14, enabled=true,dataProvider = "beneficiaryInfoTestData", testName = "Error Handling - EN Required Primary Overlay Fields", groups = "webBrowser")
	public void verifyPrimaryBeneficiaryOverlayENRequiredFields(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);

		beneficiaryInfo.getAddBeneficiaryButton().click();
		beneficiaryInfo.getSaveCloseBtn().click();
		verifyCommonOverlayENRequiredErrors(data);
		//Assert.assertEquals("Fail: Beneficiary Designation field has an incorrect EN required label", "Required",beneficiaryInfo.getBeneficiaryDesignationError());
		Assert.assertEquals("Fail: Minor Child field has an incorrect EN required label", "Required",
				beneficiaryInfo.getBeneficiaryMinorChildError());
		beneficiaryInfo.getIsBeneficiaryAMinor("Yes").click();
		beneficiaryInfo.getSaveCloseBtn().click();
		Assert.assertEquals("Fail: Name A Trustee field has an incorrect EN required label", "Required",
				beneficiaryInfo.getBeneficiaryNameATrusteeError());
		beneficiaryInfo.getNameBeneficiaryTrustee("Yes").click();
		beneficiaryInfo.getSaveCloseBtn().click();
		Assert.assertEquals("Fail: Trustee First Name field has an incorrect EN required label", "Required",
				beneficiaryInfo.getTrusteeFirstNameError());
		Assert.assertEquals("Fail: Trustee Last Name field has an incorrect EN required label", "Required",
				beneficiaryInfo.getTrusteeLastNameError());
		Assert.assertEquals("Fail: Trustee Relationship field has an incorrect EN required label", "Required",
				beneficiaryInfo.getTrusteeRelationshipError());
	}

	@Test(priority = 15, enabled = true, dataProvider = "beneficiaryInfoTestData", testName = "Error Handling - FR Required Primary Overlay Fields", groups = "webBrowser")
	public void verifyPrimaryBeneficiaryOverlayFRRequiredFields(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);

		commonElements.clickENFRToggle();
		beneficiaryInfo.getAddBeneficiaryButton().click();
		beneficiaryInfo.getSaveCloseBtn().click();
		verifyCommonOverlayFRRequiredErrors(data);
		//Assert.assertEquals("Fail: Beneficiary Designation field has an incorrect FR required label", "Obligatoire",beneficiaryInfo.getBeneficiaryDesignationError());
		Assert.assertEquals("Fail: Minor Child field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getBeneficiaryMinorChildError());
		beneficiaryInfo.getIsBeneficiaryAMinor("Yes").click();
		beneficiaryInfo.getSaveCloseBtn().click();
		Assert.assertEquals("Fail: Name A Trustee field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getBeneficiaryNameATrusteeError());
		beneficiaryInfo.getNameBeneficiaryTrustee("Yes").click();
		beneficiaryInfo.getSaveCloseBtn().click();
		Assert.assertEquals("Fail: Trustee First Name field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getTrusteeFirstNameError());
		Assert.assertEquals("Fail: Trustee Last Name field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getTrusteeLastNameError());
		Assert.assertEquals("Fail: Trustee Relationship field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getTrusteeRelationshipError());
	}

	@Test(priority = 16,enabled=true, dataProvider = "beneficiaryInfoTestData", testName = "Error Handling - EN Required Secondary Overlay Fields", groups = "webBrowser")
	public void verifySecondaryBeneficiaryOverlayENRequiredFields(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);

		beneficiaryInfo.getAddSecondaryBeneficiaryButton().click();
		beneficiaryInfo.getSaveCloseBtn().click();
		verifyCommonOverlayENRequiredErrors(data);
	}

	@Test(priority = 17,enabled=true, dataProvider = "beneficiaryInfoTestData", testName = "Error Handling - FR Required Secondary Overlay Fields", groups = "webBrowser")
	public void verifySecondaryBeneficiaryOverlayFRRequiredFields(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);
		fillPrimaryBeneficiaryModal(data);

		commonElements.clickENFRToggle();
		beneficiaryInfo.getAddSecondaryBeneficiaryButton().click();
		beneficiaryInfo.getSaveCloseBtn().click();
		verifyCommonOverlayFRRequiredErrors(data);
	}

	public void verifyCommonOverlayENRequiredErrors(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		Assert.assertEquals("Fail: Beneficiary First Name field has an incorrect EN required label", "Required",
				beneficiaryInfo.getBeneficiaryFirstNameError());
		Assert.assertEquals("Fail: Beneficiary Last Name field has an incorrect EN required label", "Required",
				beneficiaryInfo.getBeneficiaryLastNameError());
		Assert.assertEquals("Fail: Beneficiary Relationship field has an incorrect EN required label", "Required",
				beneficiaryInfo.getBeneficiaryRelationshipError());
		Assert.assertEquals("Fail: Percentage Share field has an incorrect EN required label", "Required",
				beneficiaryInfo.getBeneficiaryShareError());
	}

	public void verifyCommonOverlayFRRequiredErrors(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		Assert.assertEquals("Fail: Beneficiary First Name field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getBeneficiaryFirstNameError());
		Assert.assertEquals("Fail: Beneficiary Last Name field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getBeneficiaryLastNameError());
		Assert.assertEquals("Fail: Beneficiary Relationship field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getBeneficiaryRelationshipError());
		Assert.assertEquals("Fail: Percentage Share field has an incorrect FR required label", "Obligatoire",
				beneficiaryInfo.getBeneficiaryShareError());
	}

	@Test(priority = 18, dataProvider = "beneficiaryInfoTestData", testName = "Error Handling - Total Share Error", groups = "webBrowser")
	public void verifyTotalShareError(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBeneficiaryPage(data);

		fillPrimaryBeneficiaryModal(data);
		fillPrimaryBeneficiaryModal(data);
		fillSecondaryBeneficiaryModal(data);
		fillSecondaryBeneficiaryModal(data);
		Assert.assertEquals(
				"Fail: Primary Beneficiary Total Share must be 100% EN error should appear if total share > 100% ",
				data.get("beneficiaryInfo_TotalShareErrorEN"), beneficiaryInfo.getPrimaryTotalShareError());
		Assert.assertEquals(
				"Fail: Secondary Beneficiary Total Share must be 100% EN error should appear if total share > 100% ",
				data.get("beneficiaryInfo_TotalShareErrorEN"), beneficiaryInfo.getSecondaryTotalShareError());
		commonElements.clickENFRToggle();
		Assert.assertEquals(
				"Fail: Primary Beneficiary Total Share must be 100% FR error should appear if total share > 100% ",
				data.get("beneficiaryInfo_TotalShareErrorFR"), beneficiaryInfo.getPrimaryTotalShareError());
		Assert.assertEquals(
				"Fail: Secondary Beneficiary Total Share must be 100% FR error should appear if total share > 100% ",
				data.get("beneficiaryInfo_TotalShareErrorFR"), beneficiaryInfo.getSecondaryTotalShareError());
	}

	@Test(priority = 19,enabled=true, dataProvider = "beneficiaryInfoTestData", testName = "Verify Insured Person 2 Beneficiary Forms", groups = "webBrowser")
	public void verifyInsuredPerson2BeneficiaryForm(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		
		
		fillPagesUntilBeneficiaryPage(data);

		fillPrimaryBeneficiary2Modal(data);
		fillSecondaryBeneficiary2Modal(data);
		String testNameStr = data.get("beneficiaryInfo_TestFirstName") + " "
				+ data.get("beneficiaryInfo_TestMiddleName") + " " + data.get("beneficiaryInfo_TestLastName");
		String testName2Str = data.get("beneficiaryInfo_TestFirstName") + " "
				+ data.get("beneficiaryInfo_TestMiddleName") + " " + data.get("beneficiaryInfo_TestLastName2");

		String[] beneficiaryTableValues = { testNameStr, data.get("beneficiaryInfo_TestRelationshipEN"),
				data.get("beneficiaryInfo_TestDesignationEN"), data.get("beneficiaryInfo_TestShare"),
				data.get("beneficiaryInfo_ChangeEN"), data.get("beneficiaryInfo_DeleteEN") };
		String[] beneficiaryTableValues2 = { testName2Str, data.get("beneficiaryInfo_TestRelationshipEN"), " ",
				data.get("beneficiaryInfo_TestShare"), data.get("beneficiaryInfo_ChangeEN"),
				data.get("beneficiaryInfo_DeleteEN") };
		Assert.assertArrayEquals("Fail: Insured 2 Primary Beneficiary Table EN Values are incorrect",
				beneficiaryTableValues, beneficiaryInfo.getPrimaryTableRow(0));
		Assert.assertArrayEquals("Fail: Insured 2 Secondary Beneficiary Table EN Values are incorrect",
				beneficiaryTableValues2, beneficiaryInfo.getSecondaryTableRow(0));
	}
	
	
}