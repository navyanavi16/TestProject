package term_conversion;
import junit.framework.Assert;
import java.lang.reflect.Method;
import java.util.Map;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

public class CoverageInformationTest extends BaseTest {
	
	public void fillPagesUntilCoveragePage(Map<String,String> data) {
	
	FillPages fillPage = new FillPages(driverUtil);
	//fillPage.fillLogin();
	CommonElements commonElements = new CommonElements(driverUtil);
	commonElements.startApplication();
	
	fillPage.fillPolicyPage(data);
	fillPage.fillInsuredPage(data);
	fillPage.fillOwnerPage(data);
	fillPage.fillSuccessorPage(data);
	}
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en","policyInfoUrl");
	}
	
	
	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");
	@DataProvider(name="coverageInfoTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception{
		 //Getting the Data Row against the Test Case name and store it within an array
		 Object[][] testObjArray = excelUtil.getAllMatchingTestCases("coverageInfoData", method.getName());
		 return (testObjArray);
	}
	
	
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify that TIR field is present on Coverage Page for Single Life",groups = "webBrowser")
	public void singleLifeTIRFieldTest(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		//Fill in all pages up to coverage page. Coverage Type = Single Life
		fillPagesUntilCoveragePage(data);
		//Assert that the TIR field is present
		Assert.assertTrue("Fail: TIR field should be present for Single life Coverage",coverageInfo.isCarriedTIRAmountVisible());
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify that TIR field is not present on Coverage Page for Joint accounts",groups = "webBrowser")
	public void jointTIRFieldTest(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		//Fill in all pages up to coverage page. Coverage Type = Joint Type
		fillPagesUntilCoveragePage(data);
		//Assert TIR field is not present
		Assert.assertFalse("Fail: TIR field should not be present for " + data.get("policyInfo_CoverageType"),coverageInfo.isCarriedTIRAmountVisible());
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify that insured person name 1 is displayed - Single Life",groups = "webBrowser")
	public void insuredPerson1NameDisplayedTest(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		//Fill in all pages: Coverage Type = "Single life" and only Insured Person 1 Name filled in
		fillPagesUntilCoveragePage(data);
		//Assert that insured person 1 name is used
		String fullName = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " " + data.get(("insuredInfo_LastName"));
		Assert.assertEquals("Fail: Insured Person 1 name needs to be shown in form title",fullName,coverageInfo.getFormNameTitle().getText());
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify that individual owner name is displayed - Single Life",groups = "webBrowser")
	public void ownerInsuredNameDisplayedTest(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		//Fill in all pages: Coverage Type = "Single life" and only individual owner 1 name filled in
		fillPagesUntilCoveragePage(data);
		//Assert that individual owner 1 name is used
		String fullName = data.get("ownerInfo_IndivOwnerFirstName") + " " + data.get("ownerInfo_IndivOwnerMiddleInitial") + " " + data.get(("ownerInfo_IndivOwnerLastName"));
		Assert.assertEquals("Fail: Individual Owner name needs to be shown in form title",fullName,coverageInfo.getFormNameTitle().getText());
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify that insured person 1 name is displayed when individual owner is not insured - Single Life",groups = "webBrowser")
	public void ownerNotInsuredNameDisplayedTest(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		//Fill in all pages: Coverage Type = "Single life" and Individual and Insured Person 1 Name filled in
		fillPagesUntilCoveragePage(data);
		//Assert that insured person 1 name is used
		String fullName = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " " + data.get(("insuredInfo_LastName"));
		Assert.assertEquals("Fail: Insured Person 1 name needs to be shown in form title when individual owner is not insured",fullName,coverageInfo.getFormNameTitle().getText());
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify that insured person names 1 and 2 are displayed - Joint",groups = "webBrowser")
	public void insuredPersonsNamesDisplayedTest(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		//Fill in all pages: Coverage Type is a joint account both insured people names filled in 
		fillPagesUntilCoveragePage(data);
		//Assert that insured person 1 and insured person 2 names are displayed
		String name1 = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " " + data.get(("insuredInfo_LastName"));
		String name2 = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " " + data.get(("insuredInfo_LastName2"));
		String fullName = name1 + " and " + name2;
		Assert.assertEquals("Fail: Insured Person 1 and 2 names needs to be shown in form title",fullName,coverageInfo.getFormNameTitle().getText());
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify that individual owner and insured person 2 names are displayed - Joint",groups = "webBrowser")
	public void owner1InsuredandPerson2NamesDisplayedTest(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		//Fill in all pages: Coverage Type is a joint account and individual owner and insured people names are filled in 
		fillPagesUntilCoveragePage(data);
		//Assert that individual owner 1 and insured person 2 names are displayed
		String name1 = data.get("ownerInfo_IndivOwnerFirstName") + " " + data.get("ownerInfo_IndivOwnerMiddleInitial") + " " + data.get(("ownerInfo_IndivOwnerLastName"));
		String name2 = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " " + data.get(("insuredInfo_LastName2"));
		String fullName = name1 + " and " + name2;
		Assert.assertEquals("Fail: Individual Owner and Insured Person 2 names needs to be shown in form title when owner 1 is not insured",fullName,coverageInfo.getFormNameTitle().getText());
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify that insured person 1 and insured person 2 names are displayed when owner 1 is not insured - Joint",groups = "webBrowser")
	public void owner1NotInsuredandPerson2NamesDisplayedTest(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		//Fill in all pages: Coverage Type is a joint account and individual owner and insured people names are filled in
		fillPagesUntilCoveragePage(data);
		//Assert that insured person 1 and insured person 2 names are displayed
		String name1 = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " " + data.get(("insuredInfo_LastName"));
		String name2 = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " " + data.get(("insuredInfo_LastName2"));
		String fullName = name1 + " and " + name2;
		Assert.assertEquals("Fail: Insured Person 1 and 2 names needs to be shown in form title when owner 1 is not insured",fullName,coverageInfo.getFormNameTitle().getText());
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify disable fields logic",groups = "webBrowser")
	public void verifyDisabledAmountFieldLogic(Map<String,String> data){
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);	
		
		fillPagesUntilCoveragePage(data);
		
		//A - B = 0 Disables fields C and D. Test data A = 10, B = 10
		coverageInfo.getCoverageAmount().sendKeys("10");
		coverageInfo.getConvertingAmount().sendKeys("10");
		coverageInfo.getCoverageAmount().click(); //click out of field
		Assert.assertFalse("Fail: TIR carried amount field must be disabled",coverageInfo.getCarriedTIRAmount().isEnabled());
		Assert.assertFalse("Fail: Term keep amount field must be disabled",coverageInfo.getKeepAmount().isEnabled());
		//A - (B+C) = 0 disabled fields D. Test data A = 100, B = 10, C = 90
		coverageInfo.getCoverageAmount().sendKeys("0");
		coverageInfo.getConvertingAmount().click(); //click out of field
		Assert.assertTrue("Fail: TIR carried amount field must be enabled",coverageInfo.getCarriedTIRAmount().isEnabled());
		coverageInfo.getCarriedTIRAmount().sendKeys("90");
		coverageInfo.getConvertingAmount().click(); //click out of field
		Assert.assertFalse("Fail: Term keep amount field must be disabled",coverageInfo.getKeepAmount().isEnabled());
		//Field D is enabled when  A > B+C+D
		coverageInfo.getCoverageAmount().sendKeys("0");
		coverageInfo.getConvertingAmount().click(); //click out of field
		Assert.assertTrue("Fail: Term keep amount field must be enabled",coverageInfo.getKeepAmount().isEnabled());
		
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Verify Calculation Error Fields",groups = "webBrowser")
	public void verifyErrorHandlingFields(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		CommonElements commonElements = new CommonElements(driverUtil);
		fillPagesUntilCoveragePage(data);
		
		//Check Required Fields
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Required Message should appear Coverage Number","Required",coverageInfo.getCoverageNumberErrorText());
		Assert.assertEquals("Fail: EN Required Message should appear Coverage Amount","Required",coverageInfo.getCoverageAmountErrorText());
		Assert.assertEquals("Fail: EN Required Message should appear Converting Amount","Required",coverageInfo.getConvertingAmountNumberErrorText());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Required Message should appear Coverage Number","Obligatoire",coverageInfo.getCoverageNumberErrorText());
		Assert.assertEquals("Fail: FR Required Message should appear Coverage Amount","Obligatoire",coverageInfo.getCoverageAmountErrorText());
		Assert.assertEquals("Fail: FR Required Message should appear Converting Amount","Obligatoire",coverageInfo.getConvertingAmountNumberErrorText());
		commonElements.clickENFRToggle();
		
		//B > A Error. Test data A = 1, B = 2  
		coverageInfo.getCoverageAmount().sendKeys("1");
		coverageInfo.getConvertingAmount().sendKeys("2");
		coverageInfo.getCoverageAmount().click(); //click out of field
		Assert.assertEquals("Fail: Incorrect EN error message B > A",data.get("coverageInfo_ConvertCoverageBErrorEN"),coverageInfo.getCommonAmountErrorText());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Incorrect FR error message B > A",data.get("coverageInfo_ConvertCoverageBErrorFR"),coverageInfo.getCommonAmountErrorText());
		
		//B+C > A Error. Test data A = 10, B = 2, C = 9
		coverageInfo.getCoverageAmount().sendKeys("0");
		coverageInfo.getConvertingAmount().click(); //click out of field
		coverageInfo.getCarriedTIRAmount().sendKeys("9");
		coverageInfo.getCoverageAmount().click(); //click out of field
		Assert.assertEquals("Fail: Incorrect FR error message B+C > A",data.get("coverageInfo_ConvertCoverageBCErrorFR"),coverageInfo.getCommonAmountErrorText());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Incorrect EN error message B+C > A",data.get("coverageInfo_ConvertCoverageBCErrorEN"),coverageInfo.getCommonAmountErrorText());
		
		//B + D > A Error. Test Data A = 10, B = 2, D = 9
		coverageInfo.getCarriedTIRAmount().sendKeys(Keys.chord(Keys.CONTROL, "a"),"0");
		coverageInfo.getConvertingAmount().click(); //click out of field
		coverageInfo.getKeepAmount().sendKeys("9");
		coverageInfo.getConvertingAmount().click(); //click out of field
		Assert.assertEquals("Fail: Incorrect EN error message B+D > A",data.get("coverageInfo_ConvertCoverageBDErrorEN"),coverageInfo.getCommonAmountErrorText());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Incorrect FR error message B+D > A",data.get("coverageInfo_ConvertCoverageBDErrorFR"),coverageInfo.getCommonAmountErrorText());
		
		//B+C+D > A Error. Test Data A = 10, B = 2, C = 7, D = 2
		coverageInfo.getKeepAmount().sendKeys(Keys.chord(Keys.CONTROL, "a"),"0");
		coverageInfo.getCarriedTIRAmount().sendKeys("7");
		coverageInfo.getKeepAmount().sendKeys("2");
		coverageInfo.getConvertingAmount().click(); //click out of field
		Assert.assertEquals("Fail: Incorrect FR error message B+C+D > A",data.get("coverageInfo_ConvertCoverageBCDErrorFR"),coverageInfo.getCommonAmountErrorText());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Incorrect EN error message B+C+D > A",data.get("coverageInfo_ConvertCoverageBCDErrorEN"),coverageInfo.getCommonAmountErrorText());
		
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Validate Term Coverage Left Over Field",groups = "webBrowser")
	public void verifyRemainingTermCoverage(Map<String,String> data) throws InterruptedException {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		fillPagesUntilCoveragePage(data);
		
		//Verify that calculation is correct
		Integer coverageAmount = 10;
		Integer convertingAmount = 1;
		Integer carriedTIRAmount = 1;
		Integer keepAmount = 1;
		String remainingAmount = String.valueOf(coverageAmount - (convertingAmount + carriedTIRAmount + keepAmount));
		coverageInfo.getCoverageAmount().sendKeys(String.valueOf(coverageAmount));
		coverageInfo.getCoverageAmount().sendKeys(Keys.chord(Keys.TAB));
		Thread.sleep(1000);
		coverageInfo.getConvertingAmount().sendKeys(String.valueOf(convertingAmount));
		coverageInfo.getConvertingAmount().sendKeys(Keys.chord(Keys.TAB));
		Thread.sleep(1000);
		coverageInfo.getCarriedTIRAmount().sendKeys(String.valueOf(carriedTIRAmount));
		coverageInfo.getCarriedTIRAmount().sendKeys(Keys.chord(Keys.TAB));
		Thread.sleep(2000);
		coverageInfo.getKeepAmount().sendKeys(String.valueOf(keepAmount));
		coverageInfo.getKeepAmount().sendKeys(Keys.chord(Keys.TAB));
		Thread.sleep(2000);
		coverageInfo.getConvertingAmount().click(); //click out of field
		Assert.assertEquals("Fail: Calculation incorrect for remaining term coverage","$" + remainingAmount,coverageInfo.getRemainingAmountText());
		
		//Verify that calculation changes when TIR and keep amount field are disabled (set to 0). Test data A = 10, B = 10, C = 1, D = 1. 
		convertingAmount = 10;
		remainingAmount = String.valueOf(coverageAmount - convertingAmount);
		
		coverageInfo.ClearTextBox(getDriver(),coverageInfo.getKeepAmount());
		coverageInfo.getKeepAmount().sendKeys(Keys.chord(Keys.TAB));
		Thread.sleep(1000);
		
		coverageInfo.ClearTextBox(getDriver(),coverageInfo.getCarriedTIRAmount());
		coverageInfo.getCarriedTIRAmount().sendKeys(Keys.chord(Keys.TAB));
		Thread.sleep(1000);
		coverageInfo.ClearTextBox(getDriver(),coverageInfo.getConvertingAmount());
		
		coverageInfo.getConvertingAmount().sendKeys(Keys.chord(Keys.TAB));
		Thread.sleep(1000);
		
		
		
		coverageInfo.getConvertingAmount().sendKeys(String.valueOf(convertingAmount));
		coverageInfo.getConvertingAmount().sendKeys(Keys.chord(Keys.TAB));
		Thread.sleep(1000);		
		coverageInfo.getCoverageAmount().click(); //click out of field
		Assert.assertEquals("Fail: Calculation incorrect for remaining term coverage when TIR field and Keep field becomes disabled","$" + remainingAmount,coverageInfo.getRemainingAmountText());
	
		//Verify that the calculation changes when the Keep amount field is disabled. Test data A = 20, B = 10, C = 10, D = 1. 
		coverageAmount = 20;
		carriedTIRAmount = 10;
		remainingAmount = String.valueOf(coverageAmount - (convertingAmount + carriedTIRAmount));
		coverageInfo.getCoverageAmount().sendKeys(Keys.chord(Keys.CONTROL, "a"),String.valueOf(coverageAmount));
		coverageInfo.getConvertingAmount().click(); //click out of field
		coverageInfo.getCarriedTIRAmount().sendKeys(Keys.chord(Keys.CONTROL, "a"),String.valueOf(carriedTIRAmount));
		coverageInfo.getConvertingAmount().click(); //click out of field
		Assert.assertEquals("Fail: Calculation incorrect for remaining term coverage when Keep field becomes disabled","$" + remainingAmount,coverageInfo.getRemainingAmountText());

	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Label Validation",groups = "webBrowser")
	public void labelValidationCoverageInfo(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		CommonElements commonElements = new CommonElements(driverUtil);
		fillPagesUntilCoveragePage(data);
		
		//Verify coverage field labels
		Assert.assertEquals("Fail: Incorrect page title EN",data.get("coverageInfo_PageTitleEN"),coverageInfo.getPageTitle());
		Assert.assertEquals("Fail: Incorrect Label EN Coverage Number",data.get("coverageInfo_CoverageNumberLabelEN"),coverageInfo.getCoverageNumberLabel());
		Assert.assertEquals("Fail: Incorrect Label EN Coverage Amount",data.get("coverageInfo_CoverageAmountLabelEN"),coverageInfo.getCoverageAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label EN Converting Amount",data.get("coverageInfo_ConvertingAmountLabelEN"),coverageInfo.getConvertingAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label EN Carried Amount",data.get("coverageInfo_CarriedAmountLabelEN"),coverageInfo.getCarriedAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label EN Keep Amount",data.get("coverageInfo_KeepAmountLabelEN"),coverageInfo.getKeepAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label EN Remaining Amount",data.get("coverageInfo_RemainingAmountLabelEN"),coverageInfo.getRemainingAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label EN Admin Rules Note",data.get("coverageInfo_AdminRulesNoteEN"),coverageInfo.getAdminRulesNote());
		Assert.assertEquals("Fail: Incorrect Label EN Taxable Gain Note",data.get("coverageInfo_TaxableGainNoteEN"),coverageInfo.getTaxableGainNote());
		Assert.assertEquals("Fail: Incorrect Label EN Tool Tip Note",data.get("coverageInfo_ToolTipEN"),coverageInfo.getCoverageNumberTipLabel());
		commonElements.clickENFRToggle(); //Switch to French
		Assert.assertEquals("Fail: Incorrect page title FR",data.get("coverageInfo_PageTitleFR"),coverageInfo.getPageTitle());
		//Assert.assertEquals("Fail: Incorrect Label FR Coverage Number",data.get("coverageInfo_CoverageNumberLabelFR"),coverageInfo.getCoverageNumberLabel());
		Assert.assertTrue("Fail:Incorrect Label FR Coverage Number",coverageInfo.getCoverageNumberLabel().contains(data.get("coverageInfo_CoverageNumberLabelFR")));
		Assert.assertEquals("Fail: Incorrect Label FR Coverage Amount",data.get("coverageInfo_CoverageAmountLabelFR"),coverageInfo.getCoverageAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label FR Converting Amount",data.get("coverageInfo_ConvertingAmountLabelFR"),coverageInfo.getConvertingAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label FR Carried Amount",data.get("coverageInfo_CarriedAmountLabelFR"),coverageInfo.getCarriedAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label FR Keep Amount",data.get("coverageInfo_KeepAmountLabelFR"),coverageInfo.getKeepAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label FR Remaining Amount",data.get("coverageInfo_RemainingAmountLabelFR"),coverageInfo.getRemainingAmountLabel());
		Assert.assertEquals("Fail: Incorrect Label FR Admin Rules Note",data.get("coverageInfo_AdminRulesNoteFR"),coverageInfo.getAdminRulesNote());
		Assert.assertEquals("Fail: Incorrect Label FR Taxable Gain Note",data.get("coverageInfo_TaxableGainNoteFR"),coverageInfo.getTaxableGainNote());
		Assert.assertEquals("Fail: Incorrect Label FR Tool Tip Note",data.get("coverageInfo_ToolTipFR"),coverageInfo.getCoverageNumberTipLabel());
		
	}
	
	@Test(enabled=true,dataProvider = "coverageInfoTestData", testName = "Field Validation",groups = "webBrowser")
	public void fieldValidationCoverageInfo(Map<String,String> data) {
		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		
		CommonElements commonElements = new CommonElements(driverUtil);
		fillPagesUntilCoveragePage(data);
		
		//Test Data
		String coverageAmountTestStr = "100000000";
		String amountOtherTestStr = "1000000";	
		//Send test data
		coverageInfo.getCoverageAmount().sendKeys(coverageAmountTestStr);
		coverageInfo.getConvertingAmount().sendKeys(amountOtherTestStr);
		coverageInfo.getCarriedTIRAmount().sendKeys(amountOtherTestStr);
		coverageInfo.getKeepAmount().sendKeys(amountOtherTestStr);
		coverageInfo.getCoverageNumber().sendKeys("1");
		//Verify that input fields have correct formatting
		Assert.assertEquals("Fail: Incorrect EN formatting Coverage Amount field","100,000,000",coverageInfo.getCoverageAmount().getAttribute("value"));
		Assert.assertEquals("Fail: Incorrect EN formatting Converting Amount field","1,000,000",coverageInfo.getConvertingAmount().getAttribute("value"));
		Assert.assertEquals("Fail: Incorrect EN formatting Carried Amount field","1,000,000",coverageInfo.getCarriedTIRAmount().getAttribute("value"));
		Assert.assertEquals("Fail: Incorrect EN formatting Keep Amount field","1,000,000",coverageInfo.getKeepAmount().getAttribute("value"));
		Assert.assertEquals("Fail: Incorrect EN formatting Remaining Amount field","$97,000,000",coverageInfo.getRemainingAmountText());
		commonElements.clickENFRToggle();		
		Assert.assertEquals("Fail: Incorrect FR formatting Coverage Amount field","100 000 000",coverageInfo.getCoverageAmount().getAttribute("value"));
		Assert.assertEquals("Fail: Incorrect FR formatting Converting Amount field","1 000 000",coverageInfo.getConvertingAmount().getAttribute("value"));
		Assert.assertEquals("Fail: Incorrect FR formatting Carried Amount field","1 000 000",coverageInfo.getCarriedTIRAmount().getAttribute("value"));
		Assert.assertEquals("Fail: Incorrect FR formatting Keep Amount field","1 000 000",coverageInfo.getKeepAmount().getAttribute("value"));
		Assert.assertEquals("Fail: Incorrect FR formatting Remaining Amount field","97 000 000$",coverageInfo.getRemainingAmountText());
		//Verify that coverage field follows format 99
		Assert.assertEquals("Fail: One digit numbers should have a zero in front - Coverage Number","01",coverageInfo.getCoverageNumber().getAttribute("value"));
		//Verify that coverage field only accepts two digit numbers
		coverageInfo.getCoverageNumber().sendKeys("1234");
		Assert.assertEquals("Fail: Coverage Number field should only accept up to two digit numbers",2,coverageInfo.getCoverageNumber().getAttribute("value").length());

	}
	
}