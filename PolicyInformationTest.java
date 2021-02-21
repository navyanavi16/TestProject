package term_conversion;
import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;
import com.manulife.automation.selenium_execution.utils.DriverUtil;

import java.lang.reflect.Method;
import java.util.Map;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PolicyInformationTest extends BaseTest {
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en","policyInfoUrl");
	}
	
	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");
	@DataProvider(name="policyInfoTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception{
		 //Getting the Data Row against the Test Case name and store it within an array
		 Object[][] testObjArray = excelUtil.getAllMatchingTestCases("policyInfoData", method.getName());
		 return (testObjArray);
	}
	
	
	
	  
	@Test(priority=-1,enabled = true,dataProvider = "policyInfoTestData", testName = "Form Validation - Original Policy Section",groups = "webBrowser")
	public void userShouldNotMovedIntoInsuredPage(Map<String,String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();		
		//Verify the Original Policy Number Field is alphanumeric and has a length of 11 characters
		String alphanumericTestStr = "ABCabc123";
		policyInfo.getOriginalPolicyNumber().sendKeys(alphanumericTestStr);
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		policyInfo.getIssuedInQuebec(true).click();
		policyInfo.getDisabilityWaiver(true).click();
		policyInfo.getDisabilityInsured(true).click();
		policyInfo.selectProductType("Manulife Par");
		commonElements.clickNextBtn();
		Assert.assertTrue("Fail: We are not expecting insured page",true);
		
		
	}
	
	@Test(priority=0,enabled = true,dataProvider = "policyInfoTestData", testName = "Form Validation - Original Policy Section",groups = "webBrowser")
	public void fieldValidationOriginalPolicySection(Map<String,String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		//Verify the Original Policy Number Field is alphanumeric and has a length of 11 characters
		String alphanumericTestStr = "ABCabc123";
		policyInfo.getOriginalPolicyNumber().sendKeys(alphanumericTestStr);
		Assert.assertEquals("Fail: The Original Policy Number field must be alphanumeric",alphanumericTestStr,policyInfo.getOriginalPolicyNumber().getAttribute("value"));
		Assert.assertEquals("Fail: The Original Policy Number field has the wrong max length",data.get("policyInfo_PolicyNumberLength"),policyInfo.getOriginalPolicyNumber().getAttribute("maxlength"));
		//Verify the options in the Original Policy field
		Assert.assertArrayEquals("Fail: Incorrect Original Policy Type Options EN",data.get("policyInfo_OriginalPolicy").split(";"),policyInfo.getOriginalPolicyOptionLabels());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals("Fail: Incorrect Original Policy Type Options FR",data.get("policyInfo_OriginalPolicyFR").split(";"),policyInfo.getOriginalPolicyOptionLabels());
		commonElements.clickENFRToggle();
		//Ensure that Coverage ID field is only present if Original Policy = "Former Commercial Union"
		Assert.assertFalse("Fail: Coverage ID field should not be visible if nothing is selected in Original Policy field",policyInfo.isCoverageIDVisible());
		policyInfo.selectOriginalPolicy("Former Standard Life");
		Assert.assertFalse("Fail: Coverage ID field should not be visible if 'Former Standard Life' is selected in the Original Policy field",policyInfo.isCoverageIDVisible());
		policyInfo.selectOriginalPolicy("Manulife/Other");
		Assert.assertFalse("Fail: Coverage ID field should not be visible if 'Manulife/Other' is selected in Original Policy field",policyInfo.isCoverageIDVisible());
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		Assert.assertTrue("Fail: Coverage ID field should be visible if 'Former Commercial Union' is selected in Original Policy field",policyInfo.isCoverageIDVisible());

		
		//Verify the Coverage ID field: Must start with an 8,must only accept numeric values, seven digits in length
		policyInfo.getCoverageIDNumber().sendKeys("aB@#$%*");
		Assert.assertEquals("Fail: Coverage ID field must only accept numeric values","",policyInfo.getCoverageIDNumber().getText());
		policyInfo.getCoverageIDNumber().sendKeys("0123456798");
		Assert.assertEquals("Fail: Coverage ID field must only accept numbers that start with 8","8",policyInfo.getCoverageIDNumber().getAttribute("value"));
		policyInfo.getCoverageIDNumber().sendKeys("81234567890");
		Assert.assertEquals("Fail: Coverage ID field has the wrong max length",data.get("policyInfo_CoverageIDLength"),String.valueOf(policyInfo.getCoverageIDNumber().getAttribute("value").length()));
		
		//Verify Text Values of the the field Issued in Quebec and check if buttons can be selected
		policyInfo.getIssuedInQuebec(true).click();
		Assert.assertTrue("Issued In Quebec option 'Yes' should be able to be selected",policyInfo.isIssuedInQuebecSelected(true));
		policyInfo.getIssuedInQuebec(false).click();
		Assert.assertTrue("Issued In Quebec option 'No' should be able to be selected",policyInfo.isIssuedInQuebecSelected(false));
		Assert.assertFalse("Only one option can be selected at one time - Issued In Quebec",policyInfo.isIssuedInQuebecSelected(true));
		
		//Verify the Disability Waiver Field and verify that the insured person field is only shown if Yes is selected in the Disability waiver field
		
		policyInfo.getDisabilityWaiver(true).click();
		Assert.assertTrue("Disability Waiver option 'Yes' should be able to be selected",policyInfo.isDisabilityWaiverSelected(true));
		Assert.assertTrue("Fail: Disability Insured Boolean Field should appear when the disability waiver field is 'Yes'", policyInfo.isDisabilityInsuredVisible());
		policyInfo.getDisabilityWaiver(false).click();
		Assert.assertTrue("Disability Waiver option 'No' should be able to be selected",policyInfo.isDisabilityWaiverSelected(false));
		Assert.assertFalse("Only one option can be selected at one time - Disability Waiver",policyInfo.isDisabilityWaiverSelected(true));
		Assert.assertFalse("Fail: Disability Insured Boolean Field should only appear when the disability waiver field is 'Yes'", policyInfo.isDisabilityInsuredVisible());		
	   //Verify that the disability insured field can be selected
		policyInfo.getDisabilityWaiver(true).click();
		policyInfo.getDisabilityInsured(true).click();
		Assert.assertTrue("Disability Insured option 'Yes' should be able to be selected",policyInfo.isDisabilityInsuredSelected(true));
		policyInfo.getDisabilityInsured(false).click();
		Assert.assertTrue("Disability Insured option 'No' should be able to be selected",policyInfo.isDisabilityInsuredSelected(false));
		Assert.assertFalse("Only one option can be selected at one time  - Disability Insured",policyInfo.isDisabilityInsuredSelected(true));
	}
	
	@Test(priority=1,enabled = true,dataProvider = "policyInfoTestData", testName = "Form Validation - Conversion Information Section",groups = "webBrowser")
	public void fieldValidationConversionSection(Map<String,String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
	
		
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		
		Assert.assertFalse("Fail: Product Field should be disabled when there is no original policy selected", policyInfo.isProductEnabled());
		//Validate Product options
		//Former Standard Life
		policyInfo.selectOriginalPolicy("Former Standard Life");
		Assert.assertTrue("Fail: Product Field should be enabled when there is a original product selected", policyInfo.isProductEnabled());
		Assert.assertArrayEquals("Fail: Incorrect EN product options for Original Policy Type--Former Standard Life",data.get("policyInfo_StandardLifeProductList").split(";"),policyInfo.getAllProducts());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals("Fail: Incorrect FR product options for Original Policy Type--Former Standard Life",data.get("policyInfo_StandardLifeProductListFR").split(";"),policyInfo.getAllProducts());
		commonElements.clickENFRToggle();
		//Former Commercial Union
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		Assert.assertArrayEquals("Fail: Incorrect EN product options for Original Policy Type--Former Commercial Union",data.get("policyInfo_CommercialUnionProductList").split(";"),policyInfo.getAllProducts());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals("Fail: Incorrect FR product options for Original Policy Type--Former Commercial Union",data.get("policyInfo_CommercialUnionProductListFR").split(";"),policyInfo.getAllProducts());
		commonElements.clickENFRToggle();
		//Manulife/Other
		policyInfo.selectOriginalPolicy("Manulife/Other");
		Assert.assertArrayEquals("Fail: Incorrect EN product options for Original Policy Type--Manulife/Other",data.get("policyInfo_ManulifeOtherProductList").split(";"),policyInfo.getAllProducts());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals("Fail: Incorrect FR product options for Original Policy Type--Manulife/Other",data.get("policyInfo_ManulifeOtherProductListFR").split(";"),policyInfo.getAllProducts());
		commonElements.clickENFRToggle();

		//Validate Coverage Types
		//Manulife Par
		policyInfo.selectProductType("Manulife Par");
		Assert.assertArrayEquals("Fail: Incorrect EN coverage types - Manulife Par",data.get("policyInfo_ManulifeParCoverageList").split(";"),policyInfo.getAllCoverageTypes());
		commonElements.clickENFRToggle();
	Assert.assertArrayEquals("Fail: Incorrect FR coverage types - Manulife Par",data.get("policyInfo_ManulifeParCoverageListFR").split(";"),policyInfo.getAllCoverageTypes());
		commonElements.clickENFRToggle();
		//Performax Gold
		policyInfo.selectProductType("Performax Gold");
		Assert.assertArrayEquals("Fail: Incorrect EN coverage types - Performax Gold",data.get("policyInfo_PerformaxGoldCoverageList").split(";"),policyInfo.getAllCoverageTypes());
		commonElements.clickENFRToggle();
		Assert.assertArrayEquals("Fail: Incorrect FR coverage types - Performax Gold",data.get("policyInfo_PerformaxGoldCoverageListFR").split(";"),policyInfo.getAllCoverageTypes());

	}
	
	@Test(priority=2,enabled = true,dataProvider = "policyInfoTestData",testName = "Title and note validation",groups = "webBrowser")
	public void labelValidationPolicyInstructions(Map<String,String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		//Verify policy instructions English
		String[] termConversionInstructionsList = policyInfo.getTermConversionInstructions();
		Assert.assertEquals("Fail: Policy Info EN Page title is incorrect",data.get("policyInfo_PageTitle"),commonElements.getPageTitle());
		Assert.assertEquals("Fail: Policy instructions are incorrect EN",data.get("policyInfo_PolicyInstructionsNote1"),policyInfo.getBeforeYouBeginInstructions());
		Assert.assertEquals("Fail: Policy instructions are incorrect EN",data.get("policyInfo_PolicyInstructionsNote2"),termConversionInstructionsList[0].replace("external icon"," ").replace("\n",""));
		Assert.assertEquals("Fail: Policy instructions are incorrect EN",data.get("policyInfo_PolicyInstructionsNote3"),termConversionInstructionsList[1].replace("\n"," "));
		Assert.assertEquals("Fail: Policy instructions are incorrect EN",data.get("policyInfo_PolicyInstructionsNote4"),termConversionInstructionsList[2].replace("\n"," "));
		Assert.assertEquals("Fail: Policy instructions are incorrect EN",data.get("policyInfo_PolicyInstructionsNote5"),termConversionInstructionsList[3].replace("\n"," "));
		//Verify policy instructions French
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Policy Info FR Page title is incorrect",data.get("policyInfo_PageTitleFR"),commonElements.getPageTitle());
		termConversionInstructionsList = policyInfo.getTermConversionInstructions();
		Assert.assertEquals("Fail: Policy instructions are incorrect FR",data.get("policyInfo_PolicyInstructionsNote1FR"),policyInfo.getBeforeYouBeginInstructions());
		Assert.assertEquals("Fail: Policy instructions are incorrect FR",data.get("policyInfo_PolicyInstructionsNote2FR"),termConversionInstructionsList[0].replace("external icon"," ").replace("\n",""));
		Assert.assertEquals("Fail: Policy instructions are incorrect FR",data.get("policyInfo_PolicyInstructionsNote3FR"),termConversionInstructionsList[1].replace("\n"," "));
		Assert.assertEquals("Fail: Policy instructions are incorrect FR",data.get("policyInfo_PolicyInstructionsNote4FR"),termConversionInstructionsList[2].replace("\n"," "));
		Assert.assertEquals("Fail: Policy instructions are incorrect FR",data.get("policyInfo_PolicyInstructionsNote5FR"),termConversionInstructionsList[3].replace("\n"," "));
	}
	
	@Test(priority=3,enabled = true,dataProvider = "policyInfoTestData", testName = "Text Validation - Original Policy Information Section",groups = "webBrowser")
	public void labelValidationOriginalPolicySection(Map<String,String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		//Verify English Labels
		Assert.assertEquals("Fail: Incorrect Label for Original Policy Section Title - EN version",data.get("policyInfo_OriginalPolicyTitleEN"),policyInfo.getOriginalPolicyInfoTitle());
		Assert.assertEquals("Fail: Incorrect Label for Original Policy Number - EN version",data.get("policyInfo_OriginalPolicyNumberLabelEN"),policyInfo.getOriginalPolicyNumberLabel());
		Assert.assertEquals("Fail: Incorrect Label for Original Policy - EN version",data.get("policyInfo_OriginalPolicyLabelEN"),policyInfo.getOriginalPolicyLabel());
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		Assert.assertTrue("Fail: Incorrect Label for Coverage ID - EN version",policyInfo.getCoverageIDNumberLabel().contains(data.get("policyInfo_CoverageIDLabelEN")));
		Assert.assertTrue("Fail: Incorrect Label for Issued in Quebec - EN version",policyInfo.getPolicyIssuedQuebecLabel().contains(data.get("policyInfo_IssuedQuebecLabelEN")));
		Assert.assertEquals("Fail : Incorrect Label for Quebec Tool Tip - EN version",data.get("policyInfo_IssuedQuebecToolTipEN"),policyInfo.getPolicyIssuedQuebecToolTipLabel());
		Assert.assertEquals("Fail: Incorrect Label for Disability Waiver - EN version",data.get("policyInfo_DisabilityWaiverLabelEN"),policyInfo.getDisabilityWaiverLabel());
		policyInfo.getDisabilityWaiver(true).click();
		Assert.assertEquals("Fail: Incorrect Label for Disability Insured - EN version",data.get("policyInfo_DisabilityInsuredLabelEN"),policyInfo.getDisabilityInsuredLabel());
		policyInfo.getDisabilityInsured(true).click();
		Assert.assertTrue("False: Disability Insured Note should appear when disability insured field is 'Yes'",policyInfo.isDisabilityInsuredNoteVisible());
		Assert.assertEquals("Fail: Incorrect Label for Disability Insured Note - EN version",data.get("policyInfo_DisabilityInsuredNoteLabelEN"),policyInfo.getDisabilityInsuredNoteLabel().replace("\n", ""));
		policyInfo.getDisabilityInsured(false).click();
		Assert.assertFalse("False: Disability Insured Note should only appear when disability insured field is 'Yes'",policyInfo.isDisabilityInsuredNoteVisible());
		//Verify French Labels
		commonElements.clickENFRToggle();
		Assert.assertTrue("Fail: Incorrect Label for Issued in Quebec - FR version",policyInfo.getPolicyIssuedQuebecLabel().contains(data.get("policyInfo_IssuedQuebecLabelFR")));
		Assert.assertTrue("Fail: Incorrect Label for Quebec Tool Tip - FR version",policyInfo.getPolicyIssuedQuebecToolTipLabel().contains(data.get("policyInfo_IssuedQuebecToolTipFR")));
		Assert.assertTrue("Fail: Incorrect Label for Disability Waiver - FR version",policyInfo.getDisabilityWaiverLabel().contains(data.get("policyInfo_DisabilityWaiverLabelFR")));
		Assert.assertTrue("Fail: Incorrect Label for Disability Insured - FR version",policyInfo.getDisabilityInsuredLabel().contains(data.get("policyInfo_DisabilityInsuredLabelFR")));
		Assert.assertEquals("Fail: Incorrect Label for Original Policy Section Title - FR version",data.get("policyInfo_OriginalPolicyTitleFR"),policyInfo.getOriginalPolicyInfoTitle());
		Assert.assertEquals("Fail: Incorrect Label for Original Policy Number - FR version",data.get("policyInfo_OriginalPolicyNumberLabelFR"),policyInfo.getOriginalPolicyNumberLabel());
		Assert.assertEquals("Fail: Incorrect Label for Original Policy - FR version",data.get("policyInfo_OriginalPolicyLabelFR"),policyInfo.getOriginalPolicyLabel());
		Assert.assertTrue("Fail: Incorrect Label for Coverage ID - FR version",policyInfo.getCoverageIDNumberLabel().contains(data.get("policyInfo_CoverageIDLabelFR")));
		policyInfo.getDisabilityInsured(true).click();
		Assert.assertEquals("Fail: Incorrect Label for Disability Insured Note - FR version",data.get("policyInfo_DisabilityInsuredNoteLabelFR"),policyInfo.getDisabilityInsuredNoteLabel().replace("\n", ""));
	}
	
	@Test(priority=4,dataProvider = "policyInfoTestData",testName = "Text Validation Conversion Information",groups = "webBrowser")
	public void labelValidationConversionInformationSection(Map<String,String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		//Verify the conversion title and the product label
		Assert.assertEquals("Fail: Incorrect Label for Conversion Information Title - EN version",data.get("policyInfo_ConversionInfoTitleEN"),policyInfo.getConversionInformationTitle());
		Assert.assertEquals("Fail: Incorrect Label for Conversion Information Title - EN version",data.get("policyInfo_ProductLabelEN"),policyInfo.getProductLabel());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Incorrect Label for Conversion Information Title - FR version",data.get("policyInfo_ConversionInfoTitleFR"),policyInfo.getConversionInformationTitle());
		Assert.assertEquals("Fail: Incorrect Label for Conversion Information Title - FR version",data.get("policyInfo_ProductLabelFR"),policyInfo.getProductLabel());
		commonElements.clickENFRToggle();
		
		//Go through every coverage type and verify that the joint note appears
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		for (String productType : data.get("policyInfo_CommercialUnionProductList").split(";")) {
			//Verify Coverage Type Label names
			policyInfo.selectProductType(productType);
			Assert.assertEquals("Fail: Incorrect Label for Coverage Type - EN version",data.get("policyInfo_CoverageLabelEN"),policyInfo.getCoverageTypeLabel());
			commonElements.clickENFRToggle();
			Assert.assertEquals("Fail: Incorrect Label for Coverage Type - FR version",data.get("policyInfo_CoverageLabelFR"),policyInfo.getCoverageTypeLabel());
			commonElements.clickENFRToggle();
			for (String coverageType : data.get("policyInfo_"+productType.replace(" ", "")+"CoverageList").split(";")) {
				policyInfo.selectCoverageType(coverageType);
				//If the coverage account is joint verify that the note appears
				if (coverageType.toLowerCase().contains("joint")) {
					//Verify that the joint note is present and is correct
					Assert.assertTrue("Fail: Joint coverage type note should appear for " + coverageType, policyInfo.isJointCoverageTypeNoteVisible());
					Assert.assertEquals("Fail: Incorrect Label for Joint Coverage Type - EN version",data.get("policyInfo_JointCoverageTypeLabelEN"),policyInfo.getJointCoverageTypeNoteLabel());
					commonElements.clickENFRToggle();
					Assert.assertEquals("Fail: Incorrect Label for Joint Coverage Type - FR version",data.get("policyInfo_JointCoverageTypeLabelFR"),policyInfo.getJointCoverageTypeNoteLabel());
					commonElements.clickENFRToggle();
				}else {
					//Joint note must not appear for Single life
					Assert.assertFalse("Fail: Joint coverage type note should appear for " + coverageType, policyInfo.isJointCoverageTypeNoteVisible());
				}
			}
		}	
	}
	
	@Test( priority=5,dataProvider = "policyInfoTestData",testName = "Error Handling Mandatory Fields Error Text",groups = "webBrowser")
	public void errorHandlingMandatoryFields(Map<String,String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
		
		
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		//Check that required error fields appear when next is clicked
		commonElements.clickNextBtn();
		Assert.assertTrue("Required Message Not Shown EN: Original Policy Number",policyInfo.isOriginalPolicyNumberErrorVisible("Required"));
		Assert.assertTrue("Required Message Not Shown EN: Original Policy",policyInfo.isOriginalPolicyErrorVisible("Required"));
		Assert.assertTrue("Required Message Not Shown EN: Quebec Policy",policyInfo.isPolicyQuebecErrorVisible("Required"));
		Assert.assertTrue("Required Message Not Shown EN: Disability Waiver",policyInfo.isDisabilityWaiverErrorVisible("Required"));
		Assert.assertTrue("Required Message Not Shown EN: Product",policyInfo.isProductErrorVisible("Required"));
		commonElements.clickENFRToggle();
		Assert.assertTrue("Required Message Not Shown FR: Original Policy Number",policyInfo.isOriginalPolicyNumberErrorVisible("Obligatoire"));
		Assert.assertTrue("Required Message Not Shown FR: Original Policy",policyInfo.isOriginalPolicyErrorVisible("Obligatoire"));
		Assert.assertTrue("Required Message Not Shown FR: Quebec Policy",policyInfo.isPolicyQuebecErrorVisible("Obligatoire"));
		Assert.assertTrue("Required Message Not Shown FR: Disability Waiver",policyInfo.isDisabilityWaiverErrorVisible("Obligatoire"));
		Assert.assertTrue("Required Message Not Shown FR: Product",policyInfo.isProductErrorVisible("Obligatoire"));
		commonElements.clickENFRToggle();
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		policyInfo.getDisabilityWaiver(true).click();
		policyInfo.selectProductType("Manulife Par");
		commonElements.clickNextBtn();
		
		Assert.assertTrue("Required Message Not Shown EN: Disability Insured",policyInfo.isDisabilityInsuredErrorVisible("Required"));
		Assert.assertTrue("Required Message Not Shown EN: Coverage Type",policyInfo.isCoverageTypeErrorVisible("Required"));
		commonElements.clickENFRToggle();
		Assert.assertFalse("Required Message Not Shown FR: Coverage ID",policyInfo.isCoverageIDErrorVisible("Obligatoire"));
		Assert.assertTrue("Required Message Not Shown FR: Disability Insured",policyInfo.isDisabilityInsuredErrorVisible("Obligatoire"));
		Assert.assertTrue("Required Message Not Shown FR: Coverage Type",policyInfo.isCoverageTypeErrorVisible("Obligatoire"));	
	}
	
	@Test(priority=6, enabled=true, dataProvider = "policyInfoTestData",testName = "Coverage ID Error messages",groups = "webBrowser") 
	public void errorHandlingCoverageIDField(Map<String,String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		//Verify Coverage ID should start with 8 message 
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		policyInfo.getCoverageIDNumber().sendKeys("7");
		Assert.assertTrue("Coverage ID should start with 8 - EN Error Message Incorrect",policyInfo.isCoverageIDErrorVisible(data.get("policyInfo_CoverageIDFirstDigitErrorEN")));
		commonElements.clickENFRToggle();
		String actualValue=data.get("policyInfo_CoverageIDFirstDigitErrorFR");
		policyInfo.selectOriginalPolicy("Anciennement Standard Life");
		policyInfo.selectOriginalPolicy("Anciennement Union Commerciale");
		policyInfo.getCoverageIDNumber().sendKeys("7");
		Assert.assertTrue("Coverage ID should start with 8 - FR Error Message Incorrect("+actualValue+")",policyInfo.isCoverageIDErrorVisible(actualValue));
		commonElements.clickENFRToggle();
		//Verify Coverage ID must be 7 digits error message
		policyInfo.getCoverageIDNumber().sendKeys("8");
		commonElements.clickNextBtn();
		Assert.assertTrue("Coverage ID should be 7 digits - EN Error Message Incorrect",policyInfo.isCoverageIDErrorVisible(data.get("policyInfo_CoverageIDLengthErrorEN")));
		commonElements.clickENFRToggle();
		Assert.assertTrue("Coverage ID should be 7 digits - FR Error Message Incorrect",policyInfo.isCoverageIDErrorVisible(data.get("policyInfo_CoverageIDLengthErrorFR")));	
	}
	@Test(priority=7,dataProvider = "policyInfoTestData",testName = "verifying coverage Id number is optional",groups = "webBrowser") 
	public void verifyIfCoverageIdNumIsOptional(Map<String,String> data) {
		//getPageTitle
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);
		
		
		InsuredInformationPage insuredPage= new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		String alphanumericTestStr = "1234";
		policyInfo.getOriginalPolicyNumber().sendKeys(alphanumericTestStr);
		//Verify Coverage ID should start with 8 message 
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		policyInfo.getIssuedInQuebec(true).click();
		policyInfo.getDisabilityWaiver(false).click();
		policyInfo.selectProductType("Performax Gold");
		policyInfo.selectCoverageType("Single life");
		commonElements.clickNextBtn();
		System.out.println(insuredPage.getPageTitle().getText());
		Assert.assertTrue("Fail: We are not moved into next page Insured Page",insuredPage.getPageTitle().isDisplayed());
			}
	@Test(priority=8,dataProvider = "policyInfoTestData",testName = "verifying user can able to fill the coverage id number field",groups = "webBrowser") 
	public void verifyIfCoverageIdNumIsNoOptional(Map<String,String> data) {
		//getPageTitle
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		LoginSteps(driverUtil);	
		
		InsuredInformationPage insuredPage= new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		String alphanumericTestStr = "1234";
		policyInfo.getOriginalPolicyNumber().sendKeys(alphanumericTestStr);
		//Verify Coverage ID should start with 8 message 
		policyInfo.selectOriginalPolicy("Former Commercial Union");
		policyInfo.getCoverageIDNumber().sendKeys("8888888");
		policyInfo.getIssuedInQuebec(true).click();
		policyInfo.getDisabilityWaiver(false).click();
		policyInfo.selectProductType("Performax Gold");
		policyInfo.selectCoverageType("Single life");
		commonElements.clickNextBtn();
		System.out.println(insuredPage.getPageTitle().getText());
		Assert.assertTrue("Fail: We are not moved into next page Insured Page",insuredPage.getPageTitle().isDisplayed());		
	
	}
	@Test(priority=9,dataProvider = "policyInfoTestData",testName = "verifying coverage id number field as optional in french",groups = "webBrowser") 
	public void verifyIfCoverageIdNumIsOptionalWithFR(Map<String,String> data) {
		//getPageTitle
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);		
		LoginSteps(driverUtil);
		InsuredInformationPage insuredPage= new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		commonElements.clickENFRToggle();
		String alphanumericTestStr = "1234";
		policyInfo.getOriginalPolicyNumber().sendKeys(alphanumericTestStr);
		//Verify Coverage ID should start with 8 message 
		policyInfo.selectOriginalPolicy("Anciennement Union Commerciale");
		policyInfo.getFRIssuedInQuebec(true).click();
		policyInfo.getFRDisabilityWaiver(false).click();
		policyInfo.selectProductType("Performax Or");
		policyInfo.selectCoverageType("Sur une tête");
		commonElements.clickNextBtn();
		System.out.println(insuredPage.getPageTitle().getText());
		
		Assert.assertTrue("Fail: We are not moved into next page Insured Page",insuredPage.getPageTitle().isDisplayed());
			}
	
	public void LoginSteps(DriverUtil driver)
	{
		FillPages fillPage = new FillPages(driverUtil);
		//fillPage.fillLogin();
		
	}
		
}