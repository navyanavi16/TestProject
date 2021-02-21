package term_conversion;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Map;
import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

public class ProductInformationTest extends BaseTest{
	
	public void fillPagesUntilProductPage(Map<String,String> data) {
		FillPages fillPage = new FillPages(driverUtil);
		//fillPage.fillLogin();
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();
		
		fillPage.fillPolicyPage(data);
		fillPage.fillInsuredPage(data);
		fillPage.fillOwnerPage(data);
		fillPage.fillSuccessorPage(data);
		fillPage.fillCoveragePage(data);
		
		
	}
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en","policyInfoUrl");
	}
	
	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");
	@DataProvider(name="productInfoTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception{
		 //Getting the Data Row against the Test Case name and store it within an array
		 Object[][] testObjArray = excelUtil.getAllMatchingTestCases("productInfoData", method.getName());
		 return (testObjArray);
	}
	
	
	@Test(priority=0, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Product Page appears",groups = "webBrowser")
	public void verifyProductPageAppearTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilProductPage(data);
		Assert.assertTrue("Fail: The user should go to the Product Page",productInfo.getPageTitle().getText().equals(data.get("productInfo_PageTitleEN")));
	}
	
	@Test(priority=1, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Product Page does not appear",groups = "webBrowser")
	public void verifyProductPageDoesNotAppearTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilProductPage(data);
		System.out.println(productInfo.getPageTitle().getText() +"|||"+data.get("productInfo_PageTitleEN"));
		//Assert.assertFalse("Fail: The user should not go to the Product Page",productInfo.getPageTitle().getText().equalsIgnoreCase(data.get("productInfo_PageTitleEN")));
	}
	
	@Test(priority=2, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Product Page title and product information fields",groups = "webBrowser")
	public void verifyProductPageInfoFieldsTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
			
		
		fillPagesUntilProductPage(data);
		
		Assert.assertEquals("Fail: Product Type does not match product inputted in policy page",data.get("policyInfo_Product"),productInfo.getProductName().getText());
		productInfo.getProductName().sendKeys("test");
		Assert.assertEquals("Fail: Product Type field must not be editable",data.get("policyInfo_Product"),productInfo.getProductName().getText());
		Assert.assertEquals("Fail: Coverage Type does not match product inputted in policy page",data.get("policyInfo_CoverageType"),productInfo.getCoverageType().getText());
		productInfo.getCoverageType().sendKeys("test");
		Assert.assertEquals("Fail: Coverage Type field must not be editable",data.get("policyInfo_CoverageType"),productInfo.getCoverageType().getText());
		Assert.assertEquals("Fail: Product Page title EN is not correct",data.get("productInfo_PageTitleEN"),productInfo.getPageTitle().getText());
		Assert.assertEquals("Fail: Product Type label EN is not correct",data.get("productInfo_ProductLabelEN"),productInfo.getProductLabel().getText());
		Assert.assertEquals("Fail: Coverage label EN is not correct",data.get("productInfo_CoverageLabelEN"),productInfo.getCoverageTypeLabel().getText());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Product Page title FR is not correct",data.get("productInfo_PageTitleFR"),productInfo.getPageTitle().getText());
		Assert.assertEquals("Fail: Product Type label FR is not correct",data.get("productInfo_ProductLabelFR"),productInfo.getProductLabel().getText());
		Assert.assertEquals("Fail: Coverage label FR is not correct",data.get("productInfo_CoverageLabelFR"),productInfo.getCoverageTypeLabel().getText());
	}
	
	@Test(priority=3, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Manulife Par Options",groups = "webBrowser")
	public void verifyProductManulifeParOptionsTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
	    fillPagesUntilProductPage(data);
	//Verify if insured names are displayed on the page
		String insuredPersonOneName = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");
		String insuredPersonTwoName = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " " + data.get("insuredInfo_LastName2");
		Assert.assertTrue("Fail: Insured Person 1 Name should be displayed on page",productInfo.isInsuredNameVisible(insuredPersonOneName));
		Assert.assertTrue("Fail: Insured Person 2 Name should be displayed on page",productInfo.isInsuredNameVisible(insuredPersonTwoName));
		//Validate Field Options - Smoker, Premium and Dividend
		Assert.assertArrayEquals("Fail: Smoker Status Person One EN Options are not correct",data.get("productInfo_SmokerStatusENOptions").split(","),productInfo.getSmokerStatusLabels());
		//Assert.assertArrayEquals("Fail: Smoker Status Person Two EN Options are not correct",data.get("productInfo_SmokerStatusENOptions").split(","),productInfo.getSmokerStatusTwoLabels());
		Assert.assertArrayEquals("Fail: Premium Duration EN Options are not correct",data.get("productInfo_PremiumDurationENOptions").split(","),productInfo.getPremiumDurationLabels());
		Assert.assertArrayEquals("Fail: Dividend Option EN Options are not correct",data.get("productInfo_DividendENOptions").split(","),productInfo.getDividendLabels());
		//Check Premium field logic
		Assert.assertTrue("Fail: Dividend Default should be " + data.get("productInfo_DividendOptionDefault"),productInfo.getDividendOption(data.get("productInfo_DividendOptionDefault")).isSelected());
		productInfo.getDividendOption("Cash").click();
		Assert.assertFalse("Fail: Deposit Option Payment should not be present for Cash Dividend",productInfo.isDepositOptionPaymentBlnVisible());
		productInfo.getDividendOption(data.get("productInfo_DividendOptionDefault")).click();
		Assert.assertTrue("Fail: Deposit Option Payment should be present for paid up insurance ",productInfo.isDepositOptionPaymentBlnVisible());
		//Following should not be visible yet
		Assert.assertFalse("Fail: Deposit Monthly Payment boolean should only appear if Deposit option payment is 'Yes'",productInfo.isDepositMonthlyPaymentBlnVisible());
		Assert.assertFalse("Fail: Deposit Monthly Payment Amount field should only appear if Monthly Payment is 'Yes'",productInfo.isDepositPaymentMonthlyAmountVisible());
		Assert.assertFalse("Fail: Total Deposit Payment field should only appear if Deposit option payment is 'Yes'",productInfo.isTotalDepositPaymentAmountVisible());
		//Make sure no fields are visible when "No" is selected
		productInfo.getDepositOptionPaymentBln("No").click();
		Assert.assertFalse("Fail: Deposit Monthly Payment boolean should only appear if Deposit option payment is 'Yes'",productInfo.isDepositMonthlyPaymentBlnVisible());
		Assert.assertFalse("Fail: Deposit Monthly Payment Amount field should only appear if Monthly Payment is 'Yes'",productInfo.isDepositPaymentMonthlyAmountVisible());
		Assert.assertFalse("Fail: Total Deposit Payment field should only appear if Deposit option payment is 'Yes'",productInfo.isTotalDepositPaymentAmountVisible());
		//Verify fields for Deposit option payment = "Yes"
		productInfo.getDepositOptionPaymentBln("Yes").click();
		Assert.assertFalse("Fail: Deposit Monthly Payment Amount field should only appear if Monthly Payment is 'Yes'",productInfo.isDepositPaymentMonthlyAmountVisible());
		productInfo.getDepositMonthlyPaymentBln("Yes").click();
		Assert.assertTrue("Fail: Deposit Monthly Payment boolean should appear if Deposit option payment is 'Yes'",productInfo.isDepositMonthlyPaymentBlnVisible());
		Assert.assertTrue("Fail: Total Deposit Payment field should appear if Deposit option payment is 'Yes'",productInfo.isTotalDepositPaymentAmountVisible());
		//Verify fields for Monthly Deposit options
		productInfo.getDepositMonthlyPaymentBln("No").click();
		Assert.assertFalse("Fail: Deposit Monthly Payment Amount field should only appear if Monthly Payment is 'Yes'",productInfo.isDepositPaymentMonthlyAmountVisible());
		productInfo.getDepositMonthlyPaymentBln("Yes").click();
		Assert.assertTrue("Fail: Deposit Monthly Payment Amount field should appear if Monthly Payment is 'Yes'",productInfo.isDepositPaymentMonthlyAmountVisible());
		//Validate Input Fields
		productInfo.getDepositPaymentMonthlyAmount().sendKeys("100");
		productInfo.getTotalDepositPaymentAmount().sendKeys("100");
	}
	
	@Test(priority=4, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Performax Gold Options",groups = "webBrowser")
	public void verifyProductPerformaxGoldOptionsTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
	
		fillPagesUntilProductPage(data);
		
		//Verify if insured names are displayed on the page
		String insuredPersonOneName = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");
		String insuredPersonTwoName = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " " + data.get("insuredInfo_LastName2");
		Assert.assertTrue("Fail: Insured Person 1 Name should be displayed on page",productInfo.isInsuredNameVisible(insuredPersonOneName));
		Assert.assertTrue("Fail: Insured Person 2 Name should be displayed on page",productInfo.isInsuredNameVisible(insuredPersonTwoName));
		//Verify Health Options 
		Assert.assertArrayEquals("Fail: Health Style Options Insured 1 are not correct",data.get("productInfo_HealthStyle").split(","),productInfo.getHealthStyleLabels());
		Assert.assertArrayEquals("Fail: Health Style Options Insured 2 are not correct",data.get("productInfo_HealthStyle").split(","),productInfo.getHealthStyleTwoLabels());
		Assert.assertArrayEquals("Fail: Cost Duration Options are not correct",data.get("productInfo_CostDuration").split(","),productInfo.getCostDurationLabels());
		productInfo.getHealthStyleOption("HS1").click();
		productInfo.getHealthStyleTwoOption("HS2").click();
		//Cost Duration and Performance Credit Options
		productInfo.getCostDurationOption("Costs for 15 years").click();
		Assert.assertArrayEquals("Fail: Credit Options not correct for 'Costs for 15 years'",data.get("productInfo_PerformanceCreditOption15Y").split(","),productInfo.getPerformanceCreditOption());
		productInfo.getCostDurationOption("Costs until attained age 100").click();
		Assert.assertArrayEquals("Fail: Credit Options not correct for 'Costs until attained age 100'",data.get("productInfo_PerformanceCreditOptionAge100").split(","),productInfo.getPerformanceCreditOption());
		//Verify when Term Option Amount should appear
		productInfo.getPerformanceCreditOption("Paid-up insurance").click();
		Assert.assertFalse("Fail: Term Option amount field should  only appear when credit option is 'Term option amount'",productInfo.isTermOptionAmountVisible());
		productInfo.getPerformanceCreditOption("Accumulation amount").click();
		Assert.assertFalse("Fail: Term Option amount field should only appear when credit option is 'Term option amount'",productInfo.isTermOptionAmountVisible());
		productInfo.getPerformanceCreditOption("Term option amount").click();
		Assert.assertTrue("Fail: Term Option amount field should appear when credit option is 'Term option amount'",productInfo.isTermOptionAmountVisible());
		//Verify Term Option Amount Formatting
		productInfo.getTermOptionAmount().sendKeys("1000.55");
		productInfo.getProductName().click(); //Click Outside of field so input field can be updated
		Assert.assertEquals("Fail: Term Option Amount has improper formatting for EN","1,000.55",productInfo.getTermOptionAmount().getAttribute("value"));
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: Term Option Amount has improper formatting for FR","1 000,55",productInfo.getTermOptionAmount().getAttribute("value"));
	}
	
	@Test(priority=5, enabled= true,dataProvider = "productInfoTestData", testName = "Verify TIR fields are present for Manulife Par",groups = "webBrowser")
	public void verifyTIRFieldPresentManulifeParTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilProductPage(data);
		
		Assert.assertTrue("Fail: TIR checkbox should be present if TIR field on coverage page is positive",productInfo.isTIRCheckBoxPresent());
		Assert.assertTrue("Fail: TIR field should be selected by default and disabled",productInfo.getTIRCheckBox().isSelected() && ! productInfo.getTIRCheckBox().isEnabled());
		Assert.assertTrue("Fail: Premium Option fields should be present if TIR checkbox is selected",productInfo.isPremiumOptionSectionPresent());
		Assert.assertArrayEquals("Fail: Premium Options are not correct",data.get("productInfo_PremiumOptionsEN").split(","),productInfo.getPremiumOptionLabels());
		productInfo.getPremiumOption("Term-10").click();
		productInfo.getPremiumOption("Term-20").click();
	}
	
	@Test(priority=6, enabled= true,dataProvider = "productInfoTestData", testName = "Verify TIR fields are present for Performax Gold",groups = "webBrowser")
	public void verifyTIRFieldPresentPerformaxGoldTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilProductPage(data);
		
		Assert.assertTrue("Fail: TIR checkbox should be present if TIR field on coverage page is positive",productInfo.isTIRCheckBoxPresent());
		Assert.assertTrue("Fail: TIR field should be selected by default and disabled",productInfo.getTIRCheckBox().isSelected() && ! productInfo.getTIRCheckBox().isEnabled());
		Assert.assertTrue("Fail: Cost of Insurance fields should be present if TIR checkbox is selected",productInfo.isCostOfInsuranceSectionPresent());
		Assert.assertArrayEquals("Fail: Cost of Insurance Options are not correct",data.get("productInfo_CostOfInsurance").split(","),productInfo.getCostOfInsuranceLabels());
		productInfo.getCostOfInsuranceOption("10-year renewable").click();
		productInfo.getCostOfInsuranceOption("20-year renewable").click();
	}
	
	@Test(priority=7, enabled= true,dataProvider = "productInfoTestData", testName = "Verify TIR fields are not present - TIR zero or joint",groups = "webBrowser")
	public void verifyTIRFieldNotPresentTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilProductPage(data);
		
		Assert.assertFalse("Fail: TIR checkbox should not be present if coverage type is joint or if TIR field on coverage page is zero",productInfo.isTIRCheckBoxPresent());
		Assert.assertFalse("Fail: Premium Option fields should not be present if TIR checkbox is not selected",productInfo.isPremiumOptionSectionPresent());
		Assert.assertFalse("Fail: Premium Option fields should not be present if TIR checkbox is not selected",productInfo.isCostOfInsuranceSectionPresent());
	}
	
	@Test(priority=8, enabled= true,dataProvider = "productInfoTestData", testName = "Verify TIR riders for Single Life",groups = "webBrowser")
	public void verifyTIRRidersSingleLifeTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilProductPage(data);
		
		//Verify rider options
		Assert.assertArrayEquals("Fail: Rider Options are not correct - Single Life",data.get("productInfo_RiderOptionsSingleLifeEN").split(","),productInfo.getTIRRiderLabels());
		//Verify that the following riders can be selected at the same time
		productInfo.getTIRRiderOption("Child protection rider").click();
		productInfo.getTIRRiderOption("Guaranteed insurability option rider").click();
		//Verify that only one disability rider can be selected at a time
		productInfo.getTIRRiderOption("Total disability waiver rider").click();
		Assert.assertFalse("Fail: Total DW Rider On Payor must be disabled if Total DW is selected",productInfo.isTotalDWRiderOnPayorEnabled());
		productInfo.getTIRRiderOption("Total disability waiver rider").click();
		productInfo.getTIRRiderOption("Total disability waiver rider on the payor (if the payor is different than the insured person)").click();
		Assert.assertFalse("Fail: Total DW Rider must be disabled if Total DW On Payor is selected",productInfo.isTotalDWRiderEnabled());
	}
	
	@Test(priority=9, enabled= true,dataProvider = "productInfoTestData", testName = "Verify TIR riders for Joint Type",groups = "webBrowser")
	public void verifyTIRRidersJointTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilProductPage(data);
		Assert.assertArrayEquals("Fail: Rider Options are not correct - Joint",data.get("productInfo_RiderOptionsJointEN").split(","),productInfo.getTIRRiderJointLabels());
		productInfo.getTIRRiderJointOption("Child protection rider").click();
	}
	
	@Test(priority=10, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Manulife Par Labels",groups = "webBrowser")
	public void verifyProductManulifeParLabelsTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
	
		
		fillPagesUntilProductPage(data);
		
		//Smoker Status
		Assert.assertEquals("Fail: Smoker Status EN label is incorrect",data.get("productInfo_SmokerStatusLabelEN"),productInfo.getSmokerStatusTitle());
		//Premium Duration
		Assert.assertEquals("Fail: Premium Duration EN label is incorrect",data.get("productInfo_PremiumDurationLabelEN"),productInfo.getPremiumDurationTitle());
		//Dividend option
		Assert.assertEquals("Fail: Dividend Option EN label is incorrect",data.get("productInfo_DividendOptionLabelEN"),productInfo.getDividendOptionTitle());
		//For Prepaid Insurance:
		//make deposit option payments
		Assert.assertEquals("Fail: Deposit Option Payment boolean field EN label is incorrect",data.get("productInfo_DepositOptionPaymentLabelEN"),productInfo.getDepositOptionPaymentLabel());
		productInfo.getDepositOptionPaymentBln("Yes").click();
		//add monthly withdrawal
		Assert.assertEquals("Fail: Deposit Payment Monthly boolean field EN label is incorrect",data.get("productInfo_DepositPaymentMonthlyEN"),productInfo.getDepositPaymentMonthlyLabel());
		productInfo.getDepositMonthlyPaymentBln("Yes").click();
		//add monthly withdrawal amount
		Assert.assertEquals("Fail: Deposit Payment Monthly Amount field EN label is incorrect",data.get("productInfo_DepositPaymentMonthlyAmountEN"),productInfo.getDepositPaymentMonthlyAmountLabel());
		productInfo.getDepositMonthlyPaymentBln("No").click();
		//add monthly withdrawal amount note
		Assert.assertEquals("Fail: Deposit Payment Monthly Note field EN label is incorrect",data.get("productInfo_DepositPaymentMonthlyNoteEN"),productInfo.getDepositPaymentMonthlyNote());
		//Total Amount deposit
		Assert.assertEquals("Fail: Total Deposit Payment Amount field EN label is incorrect",data.get("productInfo_TotalDepositPaymentAmountEN"),productInfo.getTotalDepositPaymentAmountLabel());
		//Total Amount deposit tooltip		
		
		commonElements.clickENFRToggle(); //Switch to FR
		//Smoker Status
		Assert.assertEquals("Fail: Smoker Status FR label is incorrect",data.get("productInfo_SmokerStatusLabelFR"),productInfo.getSmokerStatusTitle());
		//Premium Duration
		Assert.assertEquals("Fail: Premium Duration FR label is incorrect",data.get("productInfo_PremiumDurationLabelFR"),productInfo.getPremiumDurationTitle());
		//Dividend option
		Assert.assertEquals("Fail: Dividend Option FR label is incorrect",data.get("productInfo_DividendOptionLabelFR"),productInfo.getDividendOptionTitle());
		//For Prepaid Insurance:
		//make deposit option payments
		Assert.assertEquals("Fail: Deposit Option Payment boolean field FR label is incorrect",data.get("productInfo_DepositOptionPaymentLabelFR"),productInfo.getDepositOptionPaymentLabel());
		//add monthly withdrawal
		Assert.assertEquals("Fail: Deposit Payment Monthly boolean field FR label is incorrect",data.get("productInfo_DepositPaymentMonthlyFR"),productInfo.getDepositPaymentMonthlyLabel());
		productInfo.getDepositMonthlyPaymentBln("Yes").click();
		//add monthly withdrawal amount
		Assert.assertEquals("Fail: Deposit Payment Monthly Amount field FR label is incorrect",data.get("productInfo_DepositPaymentMonthlyAmountFR"),productInfo.getDepositPaymentMonthlyAmountLabel());
		productInfo.getDepositMonthlyPaymentBln("No").click();
		//add monthly withdrawal amount note
		Assert.assertEquals("Fail: Deposit Payment Monthly Note field FR label is incorrect",data.get("productInfo_DepositPaymentMonthlyNoteFR"),productInfo.getDepositPaymentMonthlyNote());
		//Total Amount deposit
		Assert.assertEquals("Fail: Total Deposit Payment Amount field FR label is incorrect",data.get("productInfo_TotalDepositPaymentAmountFR"),productInfo.getTotalDepositPaymentAmountLabel());
		
		//Total Amount deposit tooltip
		Assert.assertEquals("Fail: Total Deposit Payment Tooltip field FR label is incorrect",data.get("productInfo_TotalDepositPaymentToolTipFR"),productInfo.getTotalDepositToolTip());
		commonElements.clickENFRToggle(); //Switch to EN
		Assert.assertEquals("Fail: Total Deposit Payment Tooltip field EN label is incorrect",data.get("productInfo_TotalDepositPaymentToolTipEN"),productInfo.getTotalDepositToolTip());
	
	}
	
	@Test(priority=11, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Performax Gold Labels",groups = "webBrowser")
	public void verifyProductPerformaxGoldLabelsTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		
		fillPagesUntilProductPage(data);
		
		//Health style category
		Assert.assertEquals("Fail: Health style EN label is incorrect",data.get("productInfo_HealthStyleLabelEN"),productInfo.getHealthStyleLabel());
		//Cost Duration 
		Assert.assertEquals("Fail: Cost Duration EN label is incorrect",data.get("productInfo_CostDurationLabelEN"),productInfo.getCostDurationLabel());
		productInfo.getCostDurationOption("Costs until attained age 100").click();
		//Performance Credit Option
		Assert.assertEquals("Fail: Performance Credit EN label is incorrect",data.get("productInfo_PerformanceCreditLabelEN"),productInfo.getPerformanceCreditLabel());
		productInfo.getPerformanceCreditOption("Term option amount").click();
		//Verify term option amount
		Assert.assertEquals("Fail: Term Option Amount EN label is incorrect",data.get("productInfo_TermOptionAmountLabelEN"),productInfo.getTermOptionAmountLabel());
		
		commonElements.clickENFRToggle(); //Switch to FR
		//Health style category
		Assert.assertEquals("Fail: Health style FR label is incorrect",data.get("productInfo_HealthStyleLabelFR"),productInfo.getHealthStyleLabel());
		//Cost Duration 
		Assert.assertEquals("Fail: Cost Duration FR label is incorrect",data.get("productInfo_CostDurationLabelFR"),productInfo.getCostDurationLabel());
		//Performance Credit Option
		Assert.assertEquals("Fail: Performance Credit FR label is incorrect",data.get("productInfo_PerformanceCreditLabelFR"),productInfo.getPerformanceCreditLabel());
		//Verify term option amount
		Assert.assertEquals("Fail: Term Option Amount FR label is incorrect",data.get("productInfo_TermOptionAmountLabelFR"),productInfo.getTermOptionAmountLabel());

	}
	
	@Test(priority=12, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Manulife Par Labels",groups = "webBrowser")
	public void verifyTIRRiderLabelManulifeParTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		
		fillPagesUntilProductPage(data);
		
		DecimalFormat formatEN = new DecimalFormat("#,###");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator(' ');
		DecimalFormat formatFR = new DecimalFormat("#,###",symbols);	
		String tirRiderAmountENStr = "Term insurance rider: $" + formatEN.format(Integer.parseInt(data.get("coverageInfo_CarriedTIRAmount")));
		String tirRiderAmountFRStr = "Garantie Assurance temporaire: " + formatFR.format(Integer.parseInt(data.get("coverageInfo_CarriedTIRAmount"))) + "$";
		
		Assert.assertEquals("Fail: TIR amount EN label is incorrect",tirRiderAmountENStr,productInfo.getTIRRiderNote());
		Assert.assertEquals("Fail: Premium Option EN label is incorrect",data.get("productInfo_PremiumOptionLabelEN"),productInfo.getPremiumOptionLabel());
		Assert.assertEquals("Fail: EN Rider Note above the rider list is incorrect",data.get("productInfo_RiderNote1EN"),productInfo.getSelectRiderCoverageNote());
		Assert.assertEquals("Fail: EN Rider Note below the rider list is incorrect",data.get("productInfo_RiderNote2EN"),productInfo.getRiderCoverageNote());
		commonElements.clickENFRToggle(); //Switch to FR
		Assert.assertEquals("Fail: TIR amount EN label is incorrect",tirRiderAmountFRStr,productInfo.getTIRRiderNote());
		Assert.assertEquals("Fail: Premium Option FR label is incorrect",data.get("productInfo_PremiumOptionLabelFR"),productInfo.getPremiumOptionLabel());
		Assert.assertEquals("Fail: EN Rider Note above the rider list is incorrect",data.get("productInfo_RiderNote1FR"),productInfo.getSelectRiderCoverageNote());
		Assert.assertEquals("Fail: EN Rider Note below rider list is incorrect",data.get("productInfo_RiderNote2FR"),productInfo.getRiderCoverageNote());
	
	}
	
	@Test(priority=13, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Manulife Par Labels",groups = "webBrowser")
	public void verifyTIRRiderLabelPerformaxGoldTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
	
		
		fillPagesUntilProductPage(data);
		
		DecimalFormat formatEN = new DecimalFormat("#,###");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator(' ');
		DecimalFormat formatFR = new DecimalFormat("#,###",symbols);	
		String tirRiderAmountENStr = "Term insurance rider: $" + formatEN.format(Integer.parseInt(data.get("coverageInfo_CarriedTIRAmount")));
		String tirRiderAmountFRStr = "Garantie Assurance temporaire: " + formatFR.format(Integer.parseInt(data.get("coverageInfo_CarriedTIRAmount"))) + "$";
		
		Assert.assertEquals("Fail: TIR amount EN label is incorrect",tirRiderAmountENStr,productInfo.getTIRRiderNote());
		Assert.assertEquals("Fail: Cost of Insurance EN label is incorrect",data.get("productInfo_CostOfInsuranceLabelEN"),productInfo.getCostOfInsuranceLabel());
		Assert.assertEquals("Fail: EN Rider Note above the rider list is incorrect",data.get("productInfo_RiderNote1EN"),productInfo.getSelectRiderCoverageNote());
		Assert.assertEquals("Fail: EN Rider Note below the rider list is incorrect",data.get("productInfo_RiderNote2EN"),productInfo.getRiderCoverageNote());
		commonElements.clickENFRToggle(); //Switch to FR
		Assert.assertEquals("Fail: TIR amount EN label is incorrect",tirRiderAmountFRStr,productInfo.getTIRRiderNote());
		Assert.assertEquals("Fail: Cost of Insurance FR label is incorrect",data.get("productInfo_CostOfInsuranceLabelFR"),productInfo.getCostOfInsuranceLabel());
		Assert.assertEquals("Fail: EN Rider Note above the rider list is incorrect",data.get("productInfo_RiderNote1FR"),productInfo.getSelectRiderCoverageNote());
		Assert.assertEquals("Fail: EN Rider Note below rider list is incorrect",data.get("productInfo_RiderNote2FR"),productInfo.getRiderCoverageNote());
	}
	
	@Test(priority=14, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Manulife Par Error Handling",groups = "webBrowser")
	public void verifyErrorHandlingManulifeParTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		
		fillPagesUntilProductPage(data);
		
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Required Error Message should appear for Smoker Status","Required",productInfo.getSmokerStatusError());
		Assert.assertEquals("Fail: EN Required Error Message should appear for Premium Duration","Required",productInfo.getPremiumDurationError());
		Assert.assertEquals("Fail: EN Required Error Message should appear for Deposit Option Payment","Required",productInfo.getDepositOptionPaymentError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Required Error Message should appear for Smoker Status","Obligatoire",productInfo.getSmokerStatusError());
		Assert.assertEquals("Fail: FR Required Error Message should appear for Premium Duration","Obligatoire",productInfo.getPremiumDurationError());
		Assert.assertEquals("Fail: FR Required Error Message should appear for Deposit Option Payment","Obligatoire",productInfo.getDepositOptionPaymentError());
	
		productInfo.getDepositOptionPaymentBln("Yes").click();
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: FR Required Error Message should appear for Deposit Option Payment Monthly","Obligatoire",productInfo.getDepositPaymentMonthlyError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: EN Required Error Message should appear for Deposit Option Payment Monthly","Required",productInfo.getDepositPaymentMonthlyError());
		
		productInfo.getDepositMonthlyPaymentBln("Yes").click();
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Required Error Message should appear for Deposit Option Payment Monthly Amount","Required",productInfo.getDepositPaymentMonthlyAmountError());
		Assert.assertEquals("Fail: EN Required Error Message should appear for Total Deposit Option Payment Amount","Required",productInfo.getTotalDepositPaymentAmountError());
		Assert.assertEquals("Fail: EN Required Error Message should appear for Premium Option","Required",productInfo.getPremiumOptionError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Required Error Message should appear for Deposit Option Payment Monthly Amount","Obligatoire",productInfo.getDepositPaymentMonthlyAmountError());
		Assert.assertEquals("Fail: FR Required Error Message should appear for Total Deposit Option Payment Amount","Obligatoire",productInfo.getTotalDepositPaymentAmountError());
		Assert.assertEquals("Fail: FR Required Error Message should appear for Premium Option","Obligatoire",productInfo.getPremiumOptionError());

	}
	
	@Test(priority=15, enabled= true,dataProvider = "productInfoTestData", testName = "Verify Performax Gold Error Handling",groups = "webBrowser")
	public void verifyErrorHandlingPerformaxGoldTest(Map<String,String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilProductPage(data);
		
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Required Error Message should appear for Health Style","Required",productInfo.getHealthStyleError());
		Assert.assertEquals("Fail: EN Required Error Message should appear for Cost Duration","Required",productInfo.getCostDurationError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Required Error Message should appear for Health Style","Obligatoire",productInfo.getHealthStyleError());
		Assert.assertEquals("Fail: FR Required Error Message should appear for Cost Duration","Obligatoire",productInfo.getCostDurationError());
		
		productInfo.getCostDurationOption("Coûts jusqu’à l’âge atteint de 100 ans").click();
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: FR Required Error Message should appear for Performance Credit Option","Obligatoire",productInfo.getPerformanceCreditError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: EN Required Error Message should appear for Performance Credit Option","Required",productInfo.getPerformanceCreditError());
		
		productInfo.getPerformanceCreditOption("Term option amount").click();
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Required Error Message should appear for Term option amount","Required",productInfo.getTermOptionAmountError());
		Assert.assertEquals("Fail: EN Required Error Message should appear for Cost of Insurance","Required",productInfo.getCostOfInsuranceError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Required Error Message should appear for Term option amount","Obligatoire",productInfo.getTermOptionAmountError());
		Assert.assertEquals("Fail: FR Required Error Message should appear for Cost of Insurance","Obligatoire",productInfo.getCostOfInsuranceError());
		
	}
	
}