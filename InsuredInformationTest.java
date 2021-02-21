package term_conversion;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;
import junit.framework.Assert;

public class InsuredInformationTest extends BaseTest {
	
	public void fillPagesUntilInsuredPage(Map<String,String> data) {
		FillPages fillPage = new FillPages(driverUtil);
		//fillPage.fillLogin();
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		
		fillPage.fillPolicyPage(data);
		
		
	}
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en","policyInfoUrl");
	}
	
	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");
	@DataProvider(name="insuredInfoTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception{
		 //Getting the Data Row against the Test Case name and store it within an array
		 Object[][] testObjArray = excelUtil.getAllMatchingTestCases("insuredInfoData", method.getName());
		 return (testObjArray);
	}
	
	
	
	@Test(priority=0,enabled= true,dataProvider = "insuredInfoTestData", testName = "Field Validation Insured Person One",groups = "webBrowser")
	public void fieldValidationInsuredOneTest(Map<String,String> data) {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilInsuredPage(data);		
		
		
		//Verify Is Owner Insured boolean fields
			
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("Yes",0);
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("No",0);	
		
			
		//Insured Person One Labels Map
		Map<String, WebElement> actualFields = new HashMap<String, WebElement>();
		actualFields.put("firstName",insuredPage.getFirstNamePerson(0));
		actualFields.put("lastName",insuredPage.getLastNamePerson(0));
		actualFields.put("middleName",insuredPage.getMiddleInitialPerson(0));
		actualFields.put("male",insuredPage.getInsuredPerson1Sex(true,0));
		actualFields.put("female",insuredPage.getInsuredPerson1Sex(false,0));	
		actualFields.put("day",insuredPage.getBirthDayPerson1());
		actualFields.put("month",insuredPage.getBirthMonthPerson1());
		actualFields.put("year",insuredPage.getBirthYearPerson1());
		//Validate Labels Insured Person One
		verifyFields(data,actualFields);
	
	}
	
	@Test(priority=1,enabled= true,dataProvider = "insuredInfoTestData", testName = "Field Validation Insured Person Two",groups = "webBrowser")
	public void fieldValidationInsuredTwoTest(Map<String,String> data) {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilInsuredPage(data);
					
		
//		//Verify Is Owner Insured boolean fields
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("Yes",1);		
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("No",1);
		
		//Validate Month
		
		//Insured Person One Labels Map
		Map<String, WebElement> actualFields = new HashMap<String, WebElement>();
		actualFields.put("firstName",insuredPage.getFirstNamePerson(1));
		actualFields.put("lastName",insuredPage.getLastNamePerson(1));
		actualFields.put("middleName",insuredPage.getMiddleInitialPerson(1));
		actualFields.put("male",insuredPage.getInsuredPerson1Sex(true,1));
		actualFields.put("female",insuredPage.getInsuredPerson1Sex(false,1));		
		actualFields.put("day",insuredPage.getBirthDayPerson2());
		actualFields.put("month",insuredPage.getBirthMonthPerson2());
		actualFields.put("year",insuredPage.getBirthYearPerson2());
		//Validate Labels Insured Person One
		verifyFields(data,actualFields);
//	
	}
	
	public void verifyFields(Map<String,String> data, Map<String, WebElement> actualFields) {
		//Verify that name fields are alphanumeric
		String alphanumericTestStr = "Name";
		actualFields.get("firstName").sendKeys(alphanumericTestStr);
		Assert.assertEquals("Fail: Insured Person First Name Field must be alphanumeric",alphanumericTestStr,actualFields.get("firstName").getAttribute("value"));
		actualFields.get("lastName").sendKeys(alphanumericTestStr);
		Assert.assertEquals("Fail: Insured Person Last name Field must be alphanumeric",alphanumericTestStr,actualFields.get("lastName").getAttribute("value"));
		actualFields.get("middleName").sendKeys("A");
		Assert.assertEquals("Fail: Insured Middle name (optional) Field must be alphanumeric","A",actualFields.get("middleName").getAttribute("value"));
		
		
		
		//Verify date of birth fields
		String numericDayTestStr = "01";
		String numericYearTestStr = "1955";
		actualFields.get("day").sendKeys(numericDayTestStr);
		actualFields.get("year").sendKeys(numericYearTestStr);
		Assert.assertEquals("Fail: Insured Person Birth Day Field must be numeric",numericDayTestStr,actualFields.get("day").getAttribute("value"));
		Assert.assertEquals("Fail: Insured Person Birth Year Field must be numeric",numericYearTestStr,actualFields.get("year").getAttribute("value"));
		actualFields.get("day").sendKeys("012");
		actualFields.get("year").sendKeys("1977");
		Assert.assertEquals("Fail: Insured Person Birth Day Field must have length 2",2,actualFields.get("day").getAttribute("value").length());
		Assert.assertEquals("Fail: Insured Person Birth Year Field must have length 4",4,actualFields.get("year").getAttribute("value").length());
		
		//Verify the max lengths of name fields
		Assert.assertEquals("Fail: Insured Person First Name Field should have a max length of 25","25",actualFields.get("firstName").getAttribute("maxlength"));
		Assert.assertEquals("Fail: Insured Person Last name Field should have a max length of 25","25",actualFields.get("lastName").getAttribute("maxlength"));
		Assert.assertEquals("Fail: Insured Person Middle name (optional) Field should have a max length of 25","25",actualFields.get("middleName").getAttribute("maxlength"));
		
		//Verify that name field labels are present
		
		Assert.assertEquals("Fail: Insured Person First Name field should have placeholder label 'First Name'","First name",actualFields.get("firstName").getAttribute("placeholder"));
		Assert.assertEquals("Fail: Insured Person Last name field should have placeholder label 'Last name'","Last name",actualFields.get("lastName").getAttribute("placeholder"));
		Assert.assertEquals("Fail: Insured Person Middle name (optional) field should have placeholder label 'Middle name (optional)'","Middle name (optional)",actualFields.get("middleName").getAttribute("placeholder"));
		
	}
	
	@Test(priority=2,enabled= true,dataProvider = "insuredInfoTestData", testName = "Error Handling",groups = "webBrowser")
	public void errorHandlingTest(Map<String,String> data) throws Exception {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilInsuredPage(data);
		
		//Required Fields
		insuredPage.clickNextButton();
		Assert.assertEquals("Fail: Required Message should appear for 'Is the owner an insured on this policy' field","Required",insuredPage.getOwnerInsuredOnPolicyRequiredError());
		Thread.sleep(3000);
		insuredPage.getENFRToggle().click();
		Assert.assertEquals("Fail: Required Message should appear for 'Is the owner an insured on this policy' field","Obligatoire",insuredPage.getOwnerInsuredOnPolicyRequiredError());
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("Non",0);
		insuredPage.clickNextButton();
		Thread.sleep(3000);
		Assert.assertEquals("Fail: Required Message should appear for the First Name field","Obligatoire",insuredPage.getFirstNameRequiredError());
		Assert.assertEquals("Fail: Required Message should appear for the Last name field","Obligatoire",insuredPage.getLastNameRequiredError());
		Assert.assertEquals("Fail: Required Message should appear for Insured Sex field","Obligatoire",insuredPage.getInsuredPersonSexRequiredError());
		Assert.assertEquals("Fail: Required Message should appear for date of birth fields","Obligatoire",insuredPage.getDateofBirthRequiredError());
		insuredPage.getENFRToggle().click();
		Assert.assertEquals("Fail: Required Message should appear for the First Name field","Required",insuredPage.getFirstNameRequiredError());
		Assert.assertEquals("Fail: Required Message should appear for the Last name field","Required",insuredPage.getLastNameRequiredError());
		Assert.assertEquals("Fail: Required Message should appear for Insured Sex field","Required",insuredPage.getInsuredPersonSexRequiredError());
		Assert.assertEquals("Fail: Required Message should appear for date of birth fields","Required",insuredPage.getDateofBirthRequiredError());
		
		
		//Invalid Date Errors
		insuredPage.getBirthDayPerson1().sendKeys("32");
		insuredPage.selectBirthMonthPerson1("January");
		insuredPage.getBirthYearPerson1().sendKeys("1980");
		Thread.sleep(3000);
		insuredPage.clickNextButton();
		Thread.sleep(3000);
		Assert.assertEquals("Fail: Invalid Date should appear for test: 32 January 1980","Invalid date",insuredPage.getDateofBirthInvalidDateError());
		insuredPage.getENFRToggle().click();
		Thread.sleep(3000);
		Assert.assertEquals("Fail: Invalid Date should appear for test: 32 January 1980","Date invalide",insuredPage.getDateofBirthInvalidDateError());
		insuredPage.getENFRToggle().click();
		
		insuredPage.getBirthDayPerson1().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		insuredPage.getBirthYearPerson1().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		insuredPage.getBirthDayPerson1().sendKeys("31");
		insuredPage.selectBirthMonthPerson1("January");
		insuredPage.getBirthYearPerson1().sendKeys("1899");
		insuredPage.clickNextButton();
		Thread.sleep(3000);
		Assert.assertEquals("Fail: Invalid Date should appear for test: 31 January 1899","Invalid date",insuredPage.getDateofBirthInvalidDateError());
		insuredPage.getENFRToggle().click();
		Assert.assertEquals("Fail: Invalid Date should appear for test: 31 January 1899","Date invalide",insuredPage.getDateofBirthInvalidDateError());
		insuredPage.getENFRToggle().click();
		Thread.sleep(3000);
		
		insuredPage.getBirthYearPerson1().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		insuredPage.selectBirthMonthPerson1("February");
		insuredPage.getBirthYearPerson1().sendKeys("1980");
		insuredPage.clickNextButton();
		Thread.sleep(3000);
		Assert.assertEquals("Fail: Invalid Date should appear for test: 31 February 1980","Invalid date",insuredPage.getDateofBirthInvalidDateError());
		insuredPage.getENFRToggle().click();
		Assert.assertEquals("Fail: Invalid Date should appear for test: 31 February 1980","Date invalide",insuredPage.getDateofBirthInvalidDateError());
		insuredPage.getENFRToggle().click();
		Thread.sleep(3000);
		
		//Future Date errors
		insuredPage.getBirthYearPerson1().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		insuredPage.selectBirthMonthPerson1("January");
		insuredPage.getBirthYearPerson1().sendKeys("2100");
		insuredPage.clickNextButton();
		Assert.assertEquals("Fail: Date should not be a future date: 31 January 2100","Date cannot be in the future",insuredPage.getDateofBirthFutureDateError());
		insuredPage.getENFRToggle().click();
		Assert.assertEquals("Fail: Date should not be a future date: 31 January 2100","La date ne peut pas être postérieure à la date du jour",insuredPage.getDateofBirthFutureDateError());
		Thread.sleep(3000);
	}
	
	@Test(priority=3,enabled= true,dataProvider = "insuredInfoTestData", testName = "General Label Validation",groups = "webBrowser")
	public void generalLabelValidationTest(Map<String,String> data) {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilInsuredPage(data);
		
		String insuredPageTitleENStr = data.get("insuredInfo_PageTitleEN");
		String completionNoteENStr = data.get("insuredInfo_CompletionNoteEN");
		String toolTipENStr = data.get("insuredInfo_ToolTipEN");
		String insuredPageTitleFRStr = data.get("insuredInfo_PageTitleFR");
		String completionNoteFRStr = data.get("insuredInfo_CompletionNoteFR");
		String toolTipFRStr = data.get("insuredInfo_ToolTipFR");
		
		//check labels English
		Assert.assertEquals("Fail: Incorrect page title for EN version",insuredPageTitleENStr,insuredPage.getPageTitle().getText());
		Assert.assertEquals("Fail: Incorrect note label for EN version",completionNoteENStr,insuredPage.getCompletionNoteLabel().getText());
		insuredPage.clickHelpToolTipLabel();
		Assert.assertEquals("Fail: Incorrect tooltip label for EN version",toolTipENStr,insuredPage.getHelpToolTipLabel().getText());
		insuredPage.getENFRToggle().click(); //switch to French
		//check labels French
		Assert.assertEquals("Fail: Incorrect page title for FR version",insuredPageTitleFRStr,insuredPage.getPageTitle().getText());
		Assert.assertEquals("Fail: Incorrect note label for FR version",completionNoteFRStr,insuredPage.getCompletionNoteLabel().getText());
		insuredPage.clickHelpToolTipLabel();
		Assert.assertEquals("Fail: Incorrect tooltip label for FR version",toolTipFRStr,insuredPage.getHelpToolTipLabel().getText());

	}
	
	@Test(priority=4,enabled =true,dataProvider = "insuredInfoTestData", testName = "Label Validation Insured Person One",groups = "webBrowser")
	public void labelValidationInsuredOneTest(Map<String,String> data) {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilInsuredPage(data);
		
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("No",0);
		//Show insured person one form
		//Insured Person One Labels Map
		Map<String, WebElement> actualLabels = new HashMap<String, WebElement>();
		actualLabels.put("insuredPersonLabel",insuredPage.getInsuredPersonLabel());
		actualLabels.put("isOwnerInsuredLabel",insuredPage.getIsOwnerInsuredLabel());
		actualLabels.put("insuredNameLabel",insuredPage.getInsuredNameLabel());
		actualLabels.put("sexOfInsuredLabel",insuredPage.getSexofInsuredLabel());
		actualLabels.put("dobLabel",insuredPage.getDOBLabel());
		actualLabels.put("birthDayLabel",insuredPage.getBirthDayLabel());
		actualLabels.put("birthMonthLabel",insuredPage.getBirthMonthLabel());
		actualLabels.put("birthYearLabel",insuredPage.getBirthYearLabel());
		//Validate Labels Insured Person One
		verifyLabels(data,actualLabels);

	}
	
	@Test(priority=5,enabled=true,dataProvider = "insuredInfoTestData", testName = "Label Validation Insured Person Two",groups = "webBrowser")
	public void labelValidationInsuredTwoTest(Map<String,String> data) throws Exception {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilInsuredPage(data);
		
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("No",1); //Show insured person one form
		Thread.sleep(3000);
		//Insured Person One Labels Map
		Map<String, WebElement> actualLabels = new HashMap<String, WebElement>();
		actualLabels.put("insuredPersonLabel",insuredPage.getInsuredPersonTwoLabel());
		actualLabels.put("isOwnerInsuredLabel",insuredPage.getIsOwnerInsuredTwoLabel());
		actualLabels.put("insuredNameLabel",insuredPage.getInsuredNameTwoLabel());
		actualLabels.put("sexOfInsuredLabel",insuredPage.getSexofInsuredTwoLabel());
		actualLabels.put("dobLabel",insuredPage.getDOBTwoLabel());
		actualLabels.put("birthDayLabel",insuredPage.getBirthDayTwoLabel());
		actualLabels.put("birthMonthLabel",insuredPage.getBirthMonthTwoLabel());
		actualLabels.put("birthYearLabel",insuredPage.getBirthYearTwoLabel());
		//Validate Labels Insured Person One
		verifyLabels(data,actualLabels);
		
	}
	
	public void verifyLabels(Map<String,String> data, Map<String, WebElement> actualLabels) {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		
		//label titles English
		String insuredPerson1ENStr = data.get("insuredInfo_InsuredPersonOneEN");
		String isOwnerInsuredENStr = data.get("insuredInfo_IsOwnerInsuredEN");
		String insuredNameENStr = data.get("insuredInfo_InsuredNameEN");
		String sexOfInsuredENStr = data.get("insuredInfo_SexOfInsuredEN");
		String dobENStr = data.get("insuredInfo_DateOfBirthEN");
		String dayENStr = data.get("insuredInfo_DayEN");
		String monthENStr = data.get("insuredInfo_MonthEN");
		String yearENStr = data.get("insuredInfo_YearEN");
		//label titles French
		String insuredPerson1FRStr = data.get("insuredInfo_InsuredPersonOneFR");
		String isOwnerInsuredFRStr = data.get("insuredInfo_IsOwnerInsuredFR");
		String insuredNameFRStr = data.get("insuredInfo_InsuredNameFR");
		String sexOfInsuredFRStr = data.get("insuredInfo_SexOfInsuredFR");
		String dobFRStr = data.get("insuredInfo_DateOfBirthFR");
		String dayFRStr = data.get("insuredInfo_DayFR");
		String monthFRStr = data.get("insuredInfo_MonthFR");
		String yearFRStr = data.get("insuredInfo_YearFR");

		//Insured Person One Fields EN
		Assert.assertEquals("Fail: Incorrect label for insured person section EN version",insuredPerson1ENStr,actualLabels.get("insuredPersonLabel").getText());
		System.out.println(actualLabels.get("isOwnerInsuredLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for is owner insured on policy field EN version",isOwnerInsuredENStr,actualLabels.get("isOwnerInsuredLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for insured name fields EN version",insuredNameENStr,actualLabels.get("insuredNameLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for sex of Insured Field",sexOfInsuredENStr,actualLabels.get("sexOfInsuredLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for Date of Birth fields EN version",dobENStr,actualLabels.get("dobLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for Day label EN version",dayENStr,actualLabels.get("birthDayLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for Month label EN version",monthENStr,actualLabels.get("birthMonthLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for Year label EN version",yearENStr,actualLabels.get("birthYearLabel").getText());
		
		insuredPage.getENFRToggle().click(); //switch to French
		//Insured Person One Fields FR
		String actualLable=actualLabels.get("insuredPersonLabel").getText();
		
		System.out.println("actualLabels  "+actualLable +"|| Expected value  "+insuredPerson1FRStr );
		Assert.assertEquals("Fail: Incorrect label for insured person section FR version",insuredPerson1FRStr,actualLable);
		Assert.assertEquals("Fail: Incorrect label for is owner insured on policy field FR version",isOwnerInsuredFRStr,actualLabels.get("isOwnerInsuredLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for insured name fields FR version",insuredNameFRStr,actualLabels.get("insuredNameLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for sex of Insured Field",sexOfInsuredFRStr,actualLabels.get("sexOfInsuredLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for Date of Birth fields FR version",dobFRStr,actualLabels.get("dobLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for Day label FR version",dayFRStr,actualLabels.get("birthDayLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for Month label FR version",monthFRStr,actualLabels.get("birthMonthLabel").getText());
		Assert.assertEquals("Fail: Incorrect label for Year label FR version",yearFRStr,actualLabels.get("birthYearLabel").getText());
		
	}
	
	@Test(priority=6,dataProvider = "insuredInfoTestData", testName = "Verify both insured forms are present for joint",groups = "webBrowser")
	public void insuredFieldsAppearJointCoverageTest(Map<String,String> data) throws Exception {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilInsuredPage(data);		
		Assert.assertTrue("Fail: Insured Person One header should be present for joint accounts",insuredPage.isInsuredPersonOneHeaderVisible());
		
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("Yes",0);
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Is Owner Insured = 'Yes'. Insured Person One input form should be present for joint accounts & no individual owner",insuredPage.isInsuredPersonOneSectionVisible());
		
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("No",0);
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Is Owner Insured = 'No'. Insured Person One input form should be present for joint accounts & no individual owner",insuredPage.isInsuredPersonOneSectionVisible());
		
		Assert.assertTrue("Fail: Insured Person Two header should be present for joint accounts",insuredPage.isInsuredPersonTwoHeaderVisible());
		Assert.assertTrue("Fail: Insured Person Two owner insured boolean field should be present for joint accounts",insuredPage.isOwnerOneInsuredFieldVisible(1));
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("Yes",1);
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Is Owner Insured = 'Yes'. Insured Person Two input form should be present for joint accounts & no individual owner",insuredPage.isInsuredPersonTwoSectionVisible());
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("No",1);
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Is Owner Insured = 'No'. Insured Person Two input form should be present for joint accounts & no individual owner",insuredPage.isInsuredPersonTwoSectionVisible());
	}
	
	@Test(priority=7,dataProvider = "insuredInfoTestData", testName = "Owner Is the Insured Form logic",groups = "webBrowser")
	public void insuredFieldIndividualOwnerInsured(Map<String,String> data) throws Exception {
		InsuredInformationPage insuredPage = new InsuredInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilInsuredPage(data);
		
		//If the individual owner 1 is the insured do not show the input form
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("No",0);
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Insured Person One input form should be present if Is Owner Insured = 'No'",insuredPage.isInsuredPersonOneSectionVisible());
		insuredPage.ClickOngIsOwnerInsuredOnPolicyButton("Yes",0);
		Thread.sleep(3000);
		Assert.assertTrue("Fail: Insured Person One input form should NOT be present if Is Owner Insured = 'Yes'",insuredPage.isInsuredPersonOneSectionVisible());
		
	}
	
}