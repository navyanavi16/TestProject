package term_conversion;
import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;
import junit.framework.Assert;
import term_conversion.SuccessorSubrogatedOwnerPage;

public class SuccessorSubrogatedOwnerTest extends BaseTest {
	
	public void fillPagesUntilSuccessorPage(Map<String,String> data) throws Exception {
		FillPages fillPage = new FillPages(driverUtil);
		//fillPage.fillLogin();
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		
		fillPage.fillPolicyPage(data);
		fillPage.fillInsuredPage(data);
		fillPage.fillOwnerPage(data);
		if (data.get("OwnerType") == "Owner2") {
			fillPage.fillOwnerPage(data);
		}
		
	}
	
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en","policyInfoUrl");
	}
	
	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");
	@DataProvider(name="successorOwnerInfoTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception{
		 //Getting the Data Row against the Test Case name and store it within an array
		 Object[][] testObjArray = excelUtil.getAllMatchingTestCases("successorOwnerInfoData", method.getName());
		 return (testObjArray);
	}
	
	
	
	@Test( priority=0,enabled=true,dataProvider = "successorOwnerInfoTestData", testName = "Verify that Page Appears",groups = "webBrowser")
	public void verifySuccessorPagePresent(Map<String,String> data) throws Exception {
		SuccessorSubrogatedOwnerPage successorPage = new SuccessorSubrogatedOwnerPage(driverUtil);
		
		fillPagesUntilSuccessorPage(data);
		
		Assert.assertTrue("Fail: Successor Page Should be visible if there is an Individual Owner",successorPage.isSuccessorPageVisible());
		
	}	
	
	
	@Test(priority=1,enabled=true,dataProvider = "successorOwnerInfoTestData", testName = "Field Validation",groups = "webBrowser")
	public void formValidationTest(Map<String,String> data) throws Exception {
		SuccessorSubrogatedOwnerPage successorPage = new SuccessorSubrogatedOwnerPage(driverUtil);
		
		CommonElements commonElements = new CommonElements(driverUtil);
		fillPagesUntilSuccessorPage(data);
		
		//Validate Boolean field 'Do you want to name a successor/subrogated?' 
		Assert.assertFalse("Fail: 'Do you want to name a successor/subrogated?' field, should not have a default value: Yes",successorPage.nameASuccessorOwnerInput(true).getAttribute("aria-pressed").equalsIgnoreCase("true"));
		Assert.assertFalse("Fail: 'Do you want to name a successor/subrogated?' field, should not have a default value: No",successorPage.nameASuccessorOwnerInput(false).getAttribute("aria-pressed").equalsIgnoreCase("true"));
		successorPage.nameASuccessorOwnerLabel(true).click();
		Assert.assertTrue("Fail: 'Do you want to name a successor/subrogated?' field, 'Yes' button cannot be selected",successorPage.nameASuccessorOwnerInput(true).getAttribute("aria-pressed").equalsIgnoreCase("true"));
		successorPage.nameASuccessorOwnerLabel(false).click();
		Assert.assertTrue("Fail: 'Do you want to name a successor/subrogated?' field, 'No' button cannot be selected",successorPage.nameASuccessorOwnerInput(false).getAttribute("aria-pressed").equalsIgnoreCase("true"));
		Assert.assertEquals("Fail: Label Value for 'Do you want to name a successor/subrogated?' field should be 'Yes/No'","Yes",successorPage.nameASuccessorOwnerLabel(true).getText());
		Assert.assertEquals("Fail: Label Value for 'Do you want to name a successor/subrogated?' field should be 'Yes/No'","No",successorPage.nameASuccessorOwnerLabel(false).getText());
		
		//Validate that Successor Form shows up only if field above = "Yes"
		Assert.assertFalse("Fail: Successor Form should only appear if 'Do you want to name a successor/subrogated?' field = Yes",successorPage.isSuccessorFormPresent());
		successorPage.nameASuccessorOwnerLabel(true).click();
		Assert.assertTrue("Fail: Successor Form should only appear if 'Do you want to name a successor/subrogated?' field = Yes",successorPage.isSuccessorFormPresent());
		
		//Validate Successor Owner Form - Max Lengths
		Assert.assertEquals("Fail: Successor First Name Field should have a max length of 25","25",successorPage.getSuccessorFirstName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Successor Last Name Field should have a max length of 25","25",successorPage.getSuccessorLastName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Successor Middle Initial Field should have a max length of 25","25",successorPage.getSuccessorMiddleInitial().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Owner First Name Field should have a max length of 25","25",successorPage.getOwnerFirstName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Owner Last Name Field should have a max length of 25","25",successorPage.getOwnerLastName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Owner Middle Initial Field should have a max length of 25","25",successorPage.getOwnerMiddleInitial().getAttribute("maxlength"));
		
		//Validate Successor Owner Form - Alphanumeric
		String alphanumericTestStr = "Ab";
		successorPage.getSuccessorFirstName().sendKeys(alphanumericTestStr);
		Assert.assertEquals("Fail: Successor First Name Field must be alphanumeric",alphanumericTestStr,successorPage.getSuccessorFirstName().getAttribute("value"));
		successorPage.getSuccessorLastName().sendKeys(alphanumericTestStr);
		Assert.assertEquals("Fail: Successor Last Name Field must be alphanumeric",alphanumericTestStr,successorPage.getSuccessorLastName().getAttribute("value"));
		successorPage.getSuccessorMiddleInitial().sendKeys("A");
		Assert.assertEquals("Fail: Successor Middle Initial Field must be alphanumeric","A",successorPage.getSuccessorMiddleInitial().getAttribute("value"));
		
		//Validate Owner Fields are not editable
		successorPage.getOwnerFirstName().sendKeys("TestNotEditable");
		Assert.assertEquals("Fail: Owner First Name Field should have the same value as the one inputted in the Individual Owner field and must not be editable",data.get("ownerInfo_IndivOwnerFirstName"),successorPage.getOwnerFirstName().getAttribute("value"));
		successorPage.getOwnerLastName().sendKeys("TestNotEditable");
		Assert.assertEquals("Fail: Owner Last Name Field should have the same value as the one inputted in the Individual Owner field and must not be editable",data.get("ownerInfo_IndivOwnerLastName"),successorPage.getOwnerLastName().getAttribute("value"));
		successorPage.getOwnerMiddleInitial().sendKeys("TestNotEditable");
		Assert.assertEquals("Fail: Owner First Name Field should have the same value as the one inputted in the Individual Owner field and must not be editable",data.get("ownerInfo_IndivOwnerMiddleInitial"),successorPage.getOwnerMiddleInitial().getAttribute("value"));
		
		//Validate Relationship To Owner Field
		successorPage.selectRelation("Brother");
		Assert.assertEquals("Fail: User should be able to select a relation","Brother",successorPage.getRelation());
		Assert.assertFalse("Fail: Self should not be in the relationship to owner EN list",successorPage.isRelationPresent("Self"));
		commonElements.clickENFRToggle();
		Assert.assertFalse("Fail: Moi should not be in the relationship to owner FR list",successorPage.isRelationPresent("Moi"));
		
		//Validate Required Fields
		Assert.assertEquals("Fail: Successor First Name Field must be a required field","true",successorPage.getSuccessorFirstName().getAttribute("aria-required"));
		Assert.assertEquals("Fail: Successor Last Name Field must be a required field","true",successorPage.getSuccessorLastName().getAttribute("aria-required"));
		Assert.assertEquals("Fail: Successor Middle Initial Field must be a required field","false",successorPage.getSuccessorMiddleInitial().getAttribute("aria-required"));	
	}
	
	@Test(priority=2,enabled=true,dataProvider = "successorOwnerInfoTestData", testName = "Error Handling Test",groups = "webBrowser")
	public void errorHandlingValidation(Map<String,String> data) throws Exception {
		SuccessorSubrogatedOwnerPage successorPage = new SuccessorSubrogatedOwnerPage(driverUtil);
		
		CommonElements commonElements = new CommonElements(driverUtil);
		fillPagesUntilSuccessorPage(data);
		
		//Verify EN labels
		successorPage.nextButton();
		Assert.assertEquals("Fail: EN Required Message should appear for 'Do you want to name a successor/subrogated?' field","Required",successorPage.getErrorNameASuccessor());
		successorPage.nameASuccessorOwnerLabel(true).click();
		successorPage.nextButton();
		Assert.assertEquals("Fail: EN Required Message should appear for Successor First Name field","Required",successorPage.getErrorSuccessorFirstName());
		Assert.assertEquals("Fail: EN Required Message should appear for Successor Last Name Field","Required",successorPage.getErrorSuccessorLastName());
		Assert.assertEquals("Fail: EN Required Message should appear for Relationship to the owner field","Required",successorPage.getErrorOwnerRelationship());
		//Verify FR labels
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Required Message should appear for 'Do you want to name a successor/subrogated?' field","Obligatoire",successorPage.getErrorNameASuccessor());
		Assert.assertEquals("Fail: FR Required Message should appear for Successor First Name field","Obligatoire",successorPage.getErrorSuccessorFirstName());
		Assert.assertEquals("Fail: FR Required Message should appear for Successor Last Name Field","Obligatoire",successorPage.getErrorSuccessorLastName());
		Assert.assertEquals("Fail: FR Required Message should appear for Relationship to the owner field","Obligatoire",successorPage.getErrorOwnerRelationship());
	}
	
	@Test(priority=3,enabled=true,dataProvider = "successorOwnerInfoTestData", testName = "Label Validation",groups = "webBrowser")
	public void labelValidationSuccessorPage(Map<String,String> data) throws Exception {
		SuccessorSubrogatedOwnerPage successorPage = new SuccessorSubrogatedOwnerPage(driverUtil);
		
		fillPagesUntilSuccessorPage(data);
		
		successorPage.nameASuccessorOwnerLabel(true).click();
		//label string EN
		String pageTitleENStr = data.get("successorInfo_pageTitleEN");
		String nameASuccessorOwnerLabelENStr = data.get("successorInfo_NameASuccessorLabelEN");
		String successorSectionLabelENStr = data.get("successorInfo_SuccessorSectionLabelEN");
		String ownerNamLabelENStr = data.get("successorInfo_OwnerNameLabelEN");
		String successorNameLabelENStr = data.get("successorInfo_SuccessorLabelEN");
		String relationshipOwnerLabelENStr = data.get("successorInfo_RelationshipEN");
		//label string FR
		String pageTitleFRStr = data.get("successorInfo_pageTitleFR");
		String nameASuccessorOwnerLabelFRStr = data.get("successorInfo_NameASuccessorLabelFR");
		String successorSectionLabelFRStr = data.get("successorInfo_SuccessorSectionLabelFR");
		String ownerNamLabelFRStr = data.get("successorInfo_OwnerNameLabelFR");
		String successorNameLabelFRStr = data.get("successorInfo_SuccessorLabelFR");
		String relationshipOwnerLabelFRStr = data.get("successorInfo_RelationshipFR");
		//assert English 
		Assert.assertEquals("Fail: Page title is incorrect, EN version",pageTitleENStr,successorPage.getPageTitle().getText());
		Assert.assertEquals("Fail: Label title for name a successor owner field is incorrect, EN version",nameASuccessorOwnerLabelENStr,successorPage.getNameSuccessorOwnerLabel().getText());
		Assert.assertEquals("Fail: Label title for successor input section is incorrect, EN version",successorSectionLabelENStr,successorPage.getSuccessorOwnerSectionLabel().getText());
		Assert.assertEquals("Fail: Label title for owner name fields is incorrect, EN version",ownerNamLabelENStr,successorPage.getOwnerNameLabel().getText());
		Assert.assertEquals("Fail: Label title for successor name field is incorrect, EN version",successorNameLabelENStr,successorPage.getSuccessorNameLabel().getText());
		Assert.assertEquals("Fail: Label title for relationship to owner field is incorrect, EN version",relationshipOwnerLabelENStr,successorPage.getRelationshipLabel().getText());
		//switch to French
		successorPage.getENFRToggle().click();
		//assert French
		Assert.assertEquals("Fail: Page title is incorrect, FR version",pageTitleFRStr,successorPage.getPageTitle().getText());
		Assert.assertEquals("Fail: Label title for name a successor owner field is incorrect, FR version",nameASuccessorOwnerLabelFRStr,successorPage.getNameSuccessorOwnerLabel().getText());
		Assert.assertEquals("Fail: Label title for successor input section is incorrect, FR version",successorSectionLabelFRStr,successorPage.getSuccessorOwnerSectionLabel().getText());
		Assert.assertEquals("Fail: Label title for owner name fields is incorrect, FR version",ownerNamLabelFRStr,successorPage.getOwnerNameLabel().getText());
		Assert.assertEquals("Fail: Label title for successor name field is incorrect, FR version",successorNameLabelFRStr,successorPage.getSuccessorNameLabel().getText());
		Assert.assertEquals("Fail: Label title for relationship to owner field is incorrect, FR version",relationshipOwnerLabelFRStr,successorPage.getRelationshipLabel().getText());
	}
}
	
	
