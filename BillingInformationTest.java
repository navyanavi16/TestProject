package term_conversion;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

public class BillingInformationTest extends BaseTest{
	
	public void fillPagesUntilBillingPagePage(Map<String,String> data) {
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
		fillPage.fillBenificiaryPage(data);
		//commonElements.clickNextBtn(); //fill Beneficiary 
	}
	
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en","policyInfoUrl");
	}	
	
	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");
	@DataProvider(name="billingInfoTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception{
		 //Getting the Data Row against the Test Case name and store it within an array
		 Object[][] testObjArray = excelUtil.getAllMatchingTestCases("billingInfoData", method.getName());
		 return (testObjArray);
	}
	
	
	
	@Test(priority=0, enabled=true,dataProvider = "billingInfoTestData",testName = "Verify fields - Manulife Par", groups = "webBrowser")
	public void verifyRegularPaymentsFieldsManulifePar(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
			
		//Verify Frequency of Payments EN
		Assert.assertArrayEquals("Fail: Incorrect Frequency EN Options for Manulife Par",data.get("billingInfo_FrequencyPaymentOptionsEN").split(";"),billingInfo.getFrequencyOfPaymentsOptions());
		//Verify the Frequencies fields and payment method
		Assert.assertFalse("Fail: Premium Amount field should not be shown if no frequency is selected",billingInfo.isPremiumAmountVisible());
		billingInfo.getFrequencyPayment("Monthly").click();
		Assert.assertTrue("Fail: Monthly frequency should be able to be selected",billingInfo.getFrequencyPayment("Monthly").isSelected());
		Assert.assertTrue("Fail: Premium Amount field should be shown if a Monthly frequency is selected",billingInfo.isPremiumAmountVisible());
		billingInfo.getFrequencyPayment("Annually").click();
		Assert.assertTrue("Fail: Annually frequency should be able to be selected",billingInfo.getFrequencyPayment("Annually").isSelected());
		Assert.assertFalse("Fail: Only one frequency should be selected at one time",billingInfo.getFrequencyPayment("Monthly").isSelected());
		Assert.assertTrue("Fail: Premium Amount field should be shown if a Annually frequency is selected",billingInfo.isPremiumAmountVisible());
		//Verify the Annual Premium fields
		billingInfo.getMonthlyAnnuallyPremiumAmount().sendKeys("100000000.00");
		billingInfo.getFrequencyPayment("Monthly").click(); //click out of field
		Assert.assertEquals("Fail: Incorrect EN formatting for premium fields","100,000,000.00",billingInfo.getMonthlyAnnuallyPremiumAmount().getAttribute("value"));
		commonElements.clickENFRToggle(); //Switch to FR
		//Verify Fields FR
		Assert.assertArrayEquals("Fail: Incorrect Frequency FR Options for Manulife Par",data.get("billingInfo_FrequencyPaymentOptionsFR").split(";"),billingInfo.getFrequencyOfPaymentsOptions());
		Assert.assertEquals("Fail: Incorrect FR formatting for premium fields","100 000 000,00",billingInfo.getMonthlyAnnuallyPremiumAmount().getAttribute("value"));
	
	}
	
	@Test(priority=1, enabled= true,dataProvider = "billingInfoTestData",testName = "Verify labels - Manulife Par", groups = "webBrowser")
	public void verifyRegularPaymentsLabelsManulifePar(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		//Verify Payment Method Type and Premium Method labels EN
		billingInfo.getFrequencyPayment("Monthly").click();
		Assert.assertEquals("Fail: Monthly Payment Method Label is Incorrect for EN",data.get("billingInfo_PaymentMethodMonthlyEN"),billingInfo.getPaymentMethod());
		Assert.assertEquals("Fail: Monthly Premium Amount Label is Incorrect for EN",data.get("billingInfo_MonthlyPremiumAmountLabelEN"),billingInfo.getPremiumAmountLabel());
		Assert.assertTrue("Fail: Withdrawal Note is visible for Monthly Payment",billingInfo.isWithdrawalNoteVisible());
		Assert.assertEquals("Fail: Withdrawal Note is Incorrect for EN",data.get("billingInfo_WithdrawalNoteEN"),billingInfo.getWithdrawalNote());
		billingInfo.getFrequencyPayment("Annually").click();
		Assert.assertEquals("Fail: Annually Payment Method Label is Incorrect for EN",data.get("billingInfo_PaymentMethodAnnuallyEN"),billingInfo.getPaymentMethod());
		Assert.assertEquals("Fail: Annually Premium Amount Label is Incorrect for EN",data.get("billingInfo_AnnuallyPremiumAmountLabelEN"),billingInfo.getPremiumAmountLabel());
		Assert.assertFalse("Fail: Withdrawal Note is not visible for Annually Payment",billingInfo.isWithdrawalNoteVisible());
		//Verify Regular Payment Title, Frequency Payment Label and Payment Method Label EN
		Assert.assertEquals("Fail: Regular Payments Title is Incorrect for EN",data.get("billingInfo_RegularPaymentsTitleEN"),billingInfo.getRegularPaymentsLabel());
		Assert.assertEquals("Fail: Frequency Payments Title is Incorrect for EN",data.get("billingInfo_FrequencyPaymentsLabelEN"),billingInfo.getFrequencyPaymentsLabel());
		Assert.assertEquals("Fail: Payment Method Title is Incorrect for EN",data.get("billingInfo_PaymentMethodLabelEN"),billingInfo.getPaymentMethodLabel());
		commonElements.clickENFRToggle(); //Switch to FR
		//Verify Payment Method Type and Premium Method labels FR
		billingInfo.getFrequencyPayment("Mensuellement").click();
		Assert.assertEquals("Fail: Monthly Payment Method Label is Incorrect for FR",data.get("billingInfo_PaymentMethodMonthlyFR"),billingInfo.getPaymentMethod());
		Assert.assertEquals("Fail: Monthly Premium Amount Label is Incorrect for FR",data.get("billingInfo_MonthlyPremiumAmountLabelFR"),billingInfo.getPremiumAmountLabel());
		Assert.assertEquals("Fail: Withdrawal Note is Incorrect for FR",data.get("billingInfo_WithdrawalNoteFR"),billingInfo.getWithdrawalNote());
		billingInfo.getFrequencyPayment("Annuellement").click();
		Assert.assertEquals("Fail: Annually Payment Method Label is Incorrect for FR",data.get("billingInfo_PaymentMethodAnnuallyFR"),billingInfo.getPaymentMethod());
		Assert.assertEquals("Fail: Annually Premium Amount Label is Incorrect for FR",data.get("billingInfo_AnnuallyPremiumAmountLabelFR"),billingInfo.getPremiumAmountLabel());
		//Verify Regular Payment Title, Frequency Payment Label and Payment Method Label FR
		Assert.assertEquals("Fail: Regular Payments Title is Incorrect for FR",data.get("billingInfo_RegularPaymentsTitleFR"),billingInfo.getRegularPaymentsLabel());
		Assert.assertEquals("Fail: Frequency Payments Title is Incorrect for FR",data.get("billingInfo_FrequencyPaymentsLabelFR"),billingInfo.getFrequencyPaymentsLabel());
		Assert.assertEquals("Fail: Payment Method Title is Incorrect for FR",data.get("billingInfo_PaymentMethodLabelFR"),billingInfo.getPaymentMethodLabel());
			 
	}
	
	@Test(priority=2, enabled= true,dataProvider = "billingInfoTestData",testName = "Verify fields - Performax Gold", groups = "webBrowser")
	public void verifyRegularPaymentsFieldsPerformaxGold(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		//Verify the Annual Premium fields
		billingInfo.getCoverPolicyCostsAmount().sendKeys("100000000.00");
		billingInfo.getFrequencyPayment("Monthly").click(); //click out of field
		Assert.assertEquals("Fail: Incorrect EN formatting for premium fields","100,000,000.00",billingInfo.getCoverPolicyCostsAmount().getAttribute("value"));
		//Verify the Frequency of Payments fields
		Assert.assertArrayEquals("Fail: Incorrect Frequency EN Options for Performax Gold",data.get("billingInfo_FrequencyPaymentOptionsEN").split(";"),billingInfo.getFrequencyOfPaymentsOptions());
		commonElements.clickENFRToggle(); //Switch to FR
		//Verify Frequency Payments FR
		Assert.assertArrayEquals("Fail: Incorrect Frequency FR Options for Performax Gold",data.get("billingInfo_FrequencyPaymentOptionsFR").split(";"),billingInfo.getFrequencyOfPaymentsOptions());
		Assert.assertEquals("Fail: Incorrect FR formatting for premium fields","100 000 000,00",billingInfo.getCoverPolicyCostsAmount().getAttribute("value"));
		commonElements.clickENFRToggle(); //Switch to EN
		//Verify that the Frequency of Payments can be selected
		billingInfo.getFrequencyPayment("Annually").click();
		Assert.assertTrue("Fail: Annually frequency should be able to be selected",billingInfo.getFrequencyPayment("Annually").isSelected());
		billingInfo.getFrequencyPayment("Monthly").click();
		Assert.assertTrue("Fail: Monthly frequency should be able to be selected",billingInfo.getFrequencyPayment("Monthly").isSelected());
		Assert.assertFalse("Fail: Only one frequency should be selected at one time",billingInfo.getFrequencyPayment("Annually").isSelected());

	}
	
	@Test(priority=3, enabled= true,dataProvider = "billingInfoTestData",testName = "Verify labels - Performax Gold", groups = "webBrowser")
	public void verifyRegularPaymentsLabelsPerformaxGold(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		//Verify Payment Method Type and Premium Method labels EN
		billingInfo.getFrequencyPayment("Monthly").click();
		Assert.assertEquals("Fail: Monthly Payment Method Label is Incorrect for EN",data.get("billingInfo_PaymentMethodMonthlyEN"),billingInfo.getPaymentMethod());
		Assert.assertTrue("Fail: Withdrawal Note is visible for Monthly Payment",billingInfo.isWithdrawalNoteVisible());
		Assert.assertEquals("Fail: Withdrawal Note is Incorrect for EN",data.get("billingInfo_WithdrawalNoteEN"),billingInfo.getWithdrawalNote());
		billingInfo.getFrequencyPayment("Annually").click();
		Assert.assertEquals("Fail: Annually Payment Method Label is Incorrect for EN",data.get("billingInfo_PaymentMethodAnnuallyEN"),billingInfo.getPaymentMethod());
		Assert.assertFalse("Fail: Withdrawal Note is not visible for Annually Payment",billingInfo.isWithdrawalNoteVisible());
		//Verify Regular Payment Title, Frequency Payment Label and Payment Method Label EN
		Assert.assertEquals("Fail: Regular Payments Title is Incorrect for EN",data.get("billingInfo_RegularPaymentsTitleEN"),billingInfo.getRegularPaymentsLabel());
		Assert.assertEquals("Fail: Frequency Payments Title is Incorrect for EN",data.get("billingInfo_FrequencyPaymentsLabelEN"),billingInfo.getFrequencyPaymentsLabel());
		Assert.assertEquals("Fail: Payment Method Title is Incorrect for EN",data.get("billingInfo_PaymentMethodLabelEN"),billingInfo.getPaymentMethodLabel());
		Assert.assertEquals("Fail: Cover Policy Costs is Incorrect for EN",data.get("billingInfo_CoverPolicyCostsEN"),billingInfo.getCoverPolicyCostsAmountLabel()); 
		commonElements.clickENFRToggle(); //Switch to FR
		//Verify Payment Method Type and Premium Method labels FR
		billingInfo.getFrequencyPayment("Mensuellement").click();
		Assert.assertEquals("Fail: Monthly Payment Method Label is Incorrect for FR",data.get("billingInfo_PaymentMethodMonthlyFR"),billingInfo.getPaymentMethod());
		Assert.assertEquals("Fail: Withdrawal Note is Incorrect for FR",data.get("billingInfo_WithdrawalNoteFR"),billingInfo.getWithdrawalNote());
		billingInfo.getFrequencyPayment("Annuellement").click();
		Assert.assertEquals("Fail: Annually Payment Method Label is Incorrect for FR",data.get("billingInfo_PaymentMethodAnnuallyFR"),billingInfo.getPaymentMethod());
		//Verify Regular Payment Title, Frequency Payment Label and Payment Method Label FR
		Assert.assertEquals("Fail: Regular Payments Title is Incorrect for FR",data.get("billingInfo_RegularPaymentsTitleFR"),billingInfo.getRegularPaymentsLabel());
		Assert.assertEquals("Fail: Frequency Payments Title is Incorrect for FR",data.get("billingInfo_FrequencyPaymentsLabelFR"),billingInfo.getFrequencyPaymentsLabel());
		Assert.assertEquals("Fail: Payment Method Title is Incorrect for FR",data.get("billingInfo_PaymentMethodLabelFR"),billingInfo.getPaymentMethodLabel());
		Assert.assertEquals("Fail: Cover Policy Costs is Incorrect for FR",data.get("billingInfo_CoverPolicyCostsFR"),billingInfo.getCoverPolicyCostsAmountLabel()); 

	}
	
	@Test(priority=-1, enabled= true,dataProvider = "billingInfoTestData",testName = "Verify fields - First Payment", groups = "webBrowser")
	public void verifyFirstPaymentFields(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		//Verify first payment boolean field
		Assert.assertFalse("Fail: There should be no default selected value for the First Payment boolean field",billingInfo.getFirstPaymentInputBln("Yes").isSelected());
		Assert.assertFalse("Fail: There should be no default selected value for the First Payment boolean field",billingInfo.getFirstPaymentInputBln("No").isSelected());
		billingInfo.getFirstPaymentBln("Yes").click();
		Assert.assertFalse("Fail: First Payment Yes should be able to be selected",billingInfo.getFirstPaymentInputBln("Yes").isSelected());
		billingInfo.getFirstPaymentBln("No").click();
		Assert.assertFalse("Fail: First Payment No should be able to be selected",billingInfo.getFirstPaymentInputBln("No").isSelected());
		Assert.assertFalse("Fail: Only one value can be selected at a time for the Fist Payment boolean field",billingInfo.getFirstPaymentInputBln("Yes").isSelected());
		billingInfo.getFirstPaymentBln("Yes").click();
		Assert.assertFalse("Fail: Only one value can be selected at a time for the Fist Payment boolean field",billingInfo.getFirstPaymentInputBln("No").isSelected());
		//Verify amount of payment options
		Assert.assertArrayEquals("Fail: First Payment EN Options are incorrect",data.get("billingInfo_AmountFirstPaymentOptionsEN").split(";"),billingInfo.getFirstPaymentAmountOptions());
		commonElements.clickENFRToggle(); //Switch to FR
		Assert.assertArrayEquals("Fail: First Payment FR Options are incorrect",data.get("billingInfo_AmountFirstPaymentOptionsFR").split(";"),billingInfo.getFirstPaymentAmountOptions());
		commonElements.clickENFRToggle(); //Switch to EN
		//Verify amount options can be selected
		for (String firstPayment : data.get("billingInfo_AmountFirstPaymentOptionsEN").split(";")) {
			billingInfo.selectFirstPaymentAmount(firstPayment);
			Assert.assertEquals("Fail: First Payment Option should be selected: " + firstPayment,firstPayment,billingInfo.getSelectedFirstPaymentAmount());
		}
		
		//Verify amount of payment input field
		billingInfo.getFirstPaymentValue().sendKeys("100000000.00");
		billingInfo.getFirstPaymentBln("No").click(); //click out of field
		Assert.assertEquals("Fail: Incorrect EN formatting for amount of first payment","100,000,000.00",billingInfo.getFirstPaymentValue().getAttribute("value"));
		commonElements.clickENFRToggle(); //Switch to FR
		Assert.assertEquals("Fail: Incorrect FR formatting for amount of first payment","100 000 000,00",billingInfo.getFirstPaymentValue().getAttribute("value"));
		//Verify payment method options
		Assert.assertArrayEquals("Fail: Incorrect First FR Payment Method Options",data.get("billingInfo_FirstPaymentMethodFR").split(";"),billingInfo.getFirstPaymentMethodOptions());
		commonElements.clickENFRToggle(); //Switch to EN
		Assert.assertArrayEquals("Fail: Incorrect First EN Payment Method Options",data.get("billingInfo_FirstPaymentMethodEN").split(";"),billingInfo.getFirstPaymentMethodOptions());
		//Verify Pad plan appears when add PAD is selected
		Assert.assertFalse("Fail: PAD field should only be visible if Add PAD to existing plan option is selected",billingInfo.isPADPolicyNumberVisible());
		billingInfo.selectFirstPaymentMethod("Add this pre-authorized debit (PAD) to an existing PAD plan");
		Assert.assertTrue("Fail: PAD field should be visible when Add PAD to existing plan option is selected",billingInfo.isPADPolicyNumberVisible());
		//Verify PAD plan fields
		billingInfo.getPADPolicyNumber().sendKeys("111111111111111");
		Assert.assertEquals("Fail: PAD field should have a max length of 11",11,billingInfo.getPADPolicyNumber().getAttribute("value").length());
		//Verify Pad plan appears when set up PAD is selected
		Assert.assertFalse("Fail: Bank Name field should only be visible if Set up a new PAD plan is selected",billingInfo.isCanadianBankVisible());
		Assert.assertFalse("Fail: Transit Number field should only be visible if Set up a new PAD plan is selected",billingInfo.isTransitNumberVisible());
		Assert.assertFalse("Fail: Institution Number field should only be visible if Set up a new PAD plan is selected",billingInfo.isInstitutionNumberVisible());
		Assert.assertFalse("Fail: Account Number field should only be visible if Set up a new PAD plan is selected",billingInfo.isAccountNumberVisible());	
		billingInfo.selectFirstPaymentMethod("Set up a new PAD plan");
		Assert.assertTrue("Fail: Bank Name field should be visible if Set up a new PAD plan is selected",billingInfo.isCanadianBankVisible());
		Assert.assertTrue("Fail: Transit Number field should be visible if Set up a new PAD plan is selected",billingInfo.isTransitNumberVisible());
		Assert.assertTrue("Fail: Institution Number field should be visible if Set up a new PAD plan is selected",billingInfo.isInstitutionNumberVisible());
		Assert.assertTrue("Fail: Account Number field should be visible if Set up a new PAD plan is selected",billingInfo.isAccountNumberVisible());	
		//Verify Banking information
		Assert.assertEquals("Fail: Bank Name field should have a max length of 50","50",billingInfo.getBankName().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Transit Number field should have a max length of 5","5",billingInfo.getTransitNumber().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Institution Number field should have a max length of 3","3",billingInfo.getInstitutionNumber().getAttribute("maxlength"));
		Assert.assertEquals("Fail: Account Number field should have a max length of 12","12",billingInfo.getAccountNumber().getAttribute("maxlength"));
	
	}
	
	@Test(priority=4, enabled= true,dataProvider = "billingInfoTestData",testName = "Verify Labels - First Payment", groups = "webBrowser")
	public void verifyFirstPaymentLabels(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		//Verify EN labels
		Assert.assertEquals("Fail: Incorrect EN label for First Payment Label field",data.get("billingInfo_FirstPaymentLabelEN"),billingInfo.getFirstPaymentTitle());
		Assert.assertEquals("Fail: Incorrect EN label for First Payment Boolean field",data.get("billingInfo_FirstPaymentBlnLabelEN"),billingInfo.getFirstPaymentBlnLabel());
		Assert.assertEquals("Fail: Incorrect EN label for First Payment Amount field",data.get("billingInfo_FirstPaymentAmountLabelEN"),billingInfo.getFirstPaymentAmountLabel());
		Assert.assertEquals("Fail: Incorrect EN label for First Payment Method field",data.get("billingInfo_PaymentMethodLabelEN"),billingInfo.getFirstPaymentMethodLabel());
		Assert.assertEquals("Fail: Incorrect EN label for First Payment Method Value field",data.get("billingInfo_PaymentMethodValueLabelEN"),billingInfo.getFirstPaymentMethodValueLabel());
		billingInfo.selectFirstPaymentMethod("Add this pre-authorized debit (PAD) to an existing PAD plan");
		Assert.assertEquals("Fail: Incorrect EN label for PAD Plan field",data.get("billingInfo_PADLabelEN"),billingInfo.getPADPlanLabel());
		billingInfo.selectFirstPaymentMethod("Set up a new PAD plan");
		Assert.assertEquals("Fail: Incorrect EN label for Bank Info field",data.get("billingInfo_BankingInfoEN"),billingInfo.getBankingInfoLabel());
		Assert.assertEquals("Fail: Incorrect EN label for Bank Name field",data.get("billingInfo_BankNameEN"),billingInfo.getBankNameLabel());
		Assert.assertEquals("Fail: Incorrect EN label for Transit Number field",data.get("billingInfo_TransitNumberEN"),billingInfo.getTransitNumberLabel());
		Assert.assertEquals("Fail: Incorrect EN label for Institution Number field",data.get("billingInfo_InstitutionNumberEN"),billingInfo.getInstitutionNumberLabel());
		Assert.assertEquals("Fail: Incorrect EN label for Account Number field",data.get("billingInfo_AccountNumberEN"),billingInfo.getAccountNumberLabel());
		//Verify FR labels
		commonElements.clickENFRToggle(); //Switch to FR
		Assert.assertEquals("Fail: Incorrect FR label for First Payment Label field",data.get("billingInfo_FirstPaymentLabelFR"),billingInfo.getFirstPaymentTitle());
		Assert.assertEquals("Fail: Incorrect FR label for First Payment Boolean field",data.get("billingInfo_FirstPaymentBlnLabelFR"),billingInfo.getFirstPaymentBlnLabel());
		Assert.assertEquals("Fail: Incorrect FR label for First Payment Amount field",data.get("billingInfo_FirstPaymentAmountLabelFR"),billingInfo.getFirstPaymentAmountLabel());
		Assert.assertEquals("Fail: Incorrect FR label for First Payment Method field",data.get("billingInfo_PaymentMethodLabelFR"),billingInfo.getFirstPaymentMethodLabel());
		Assert.assertEquals("Fail: Incorrect FR label for First Payment Method Value field",data.get("billingInfo_PaymentMethodValueLabelFR"),billingInfo.getFirstPaymentMethodValueLabel());
		billingInfo.selectFirstPaymentMethod("Ajoutez ce prélèvement automatique sur le compte (PAC) à un plan de PAC existant");
		Assert.assertEquals("Fail: Incorrect FR label for PAD Plan field",data.get("billingInfo_PADLabelFR"),billingInfo.getPADPlanLabel());
		billingInfo.selectFirstPaymentMethod("Mettre en place un nouveau plan de PAC");
		Assert.assertEquals("Fail: Incorrect FR label for Bank Info field",data.get("billingInfo_BankingInfoFR"),billingInfo.getBankingInfoLabel());
		Assert.assertEquals("Fail: Incorrect FR label for Bank Name field",data.get("billingInfo_BankNameFR"),billingInfo.getBankNameLabel());
		Assert.assertEquals("Fail: Incorrect FR label for Transit Number field",data.get("billingInfo_TransitNumberFR"),billingInfo.getTransitNumberLabel());
		Assert.assertEquals("Fail: Incorrect FR label for Institution Number field",data.get("billingInfo_InstitutionNumberFR"),billingInfo.getInstitutionNumberLabel());
		Assert.assertEquals("Fail: Incorrect FR label for Account Number field",data.get("billingInfo_AccountNumberFR"),billingInfo.getAccountNumberLabel());
	
	}
	
	@Test(priority=5, enabled= true,dataProvider = "billingInfoTestData",testName = "Verify Fields - Pay Premiums", groups = "webBrowser")
	public void verifyPayPremiumFields(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		String ownerPersonOne = data.get("ownerInfo_IndivOwnerFirstName") + " " + data.get("ownerInfo_IndivOwnerMiddleInitial") + " " + data.get("ownerInfo_IndivOwnerLastName");
		String ownerPersonTwo = data.get("owner2_FirstName") + " " + data.get("owner2_MiddleInitial") + " " + data.get("owner2_LastName");
		//String insuredPersonOne = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " " + data.get("insuredInfo_LastName");
		//String insuredPersonTwo = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " " + data.get("insuredInfo_LastName2");
		
		//Verify that names are present
		Assert.assertTrue("Fail: Owner Person One Input Label is not present",billingInfo.isPremiumPayerNameVisible(ownerPersonOne));
		Assert.assertTrue("Fail: Owner Person two Input Label is not present",billingInfo.isPremiumPayerNameVisible(ownerPersonTwo));
	//	Assert.assertTrue("Fail: Insured Person One Input Label is not present",billingInfo.isPremiumPayerNameVisible(insuredPersonOne));
		//Assert.assertTrue("Fail: Insured Person Two Input Label is not present",billingInfo.isPremiumPayerNameVisible(insuredPersonTwo));
		
		//Verify that input fields are present
		Assert.assertTrue("Fail: Owner Person One Input Field is not present",billingInfo.isPremiumPayerInputVisible(ownerPersonOne));
		Assert.assertTrue("Fail: Owner Person two Input Field is not present",billingInfo.isPremiumPayerInputVisible(ownerPersonTwo));
	//	Assert.assertTrue("Fail: Insured Person One Input Field is not present",billingInfo.isPremiumPayerInputVisible(insuredPersonOne));
		//Assert.assertTrue("Fail: Insured Person One Input Field is not present",billingInfo.isPremiumPayerInputVisible(insuredPersonTwo));
		
		
		//Assert.assertFalse("Fail: Owner Person One should be disabled when two names are selected",billingInfo.getPremiumPayer(ownerPersonOne).isEnabled());
		//billingInfo.getPremiumPayer(insuredPersonTwo).click(); //Unselect Person two
		billingInfo.getPremiumPayer(ownerPersonOne).click(); //Select Owner One
		billingInfo.getPremiumPayer(ownerPersonTwo).click(); //selecting owner2
		Assert.assertTrue("Fail: Owner Person One should be selected",billingInfo.getPremiumPayer(ownerPersonOne).isSelected());
		Assert.assertTrue("Fail: Owner Person two should be selected",billingInfo.getPremiumPayer(ownerPersonTwo).isSelected());
	
	}
	
	@Test(priority=6, enabled= true,dataProvider = "billingInfoTestData",testName = "Verify Labels - Pay Premiums and Page Titles", groups = "webBrowser")
	public void verifyPayPremiumsAndTitleLabels(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		//Verify Titles EN
		Assert.assertEquals("Fail: Incorrect Page Title EN",data.get("billingInfo_BillingPageTitleEN"),commonElements.getPageTitle());
		Assert.assertEquals("Fail: Incorrect Billing Section Title EN",data.get("billingInfo_BillingSectionTitleEN"),billingInfo.getBillingInfoSectionTitle());
		Assert.assertEquals("Fail: Incorrect Payment Section Title EN",data.get("billingInfo_PaymentSectionTitleEN"),billingInfo.getPaymentInfoSectionTitle());
		//Verify Pay premiums section labels EN
		Assert.assertEquals("Fail: Incorrect Pay Premium Question Label EN",data.get("billingInfo_PayPremiumsQuestionLabelEN"),billingInfo.getPayPremiumsQuestionLabel());
		Assert.assertEquals("Fail: Incorrect Pay Premium Paragraph Label EN",data.get("billingInfo_PayPremiumsParagraphLabelEN"),billingInfo.getPayPremiumsParagraphLabel());
		commonElements.clickENFRToggle(); //Switch to FR
		//Verify Titles FR
		Assert.assertEquals("Fail: Incorrect Page Title FR",data.get("billingInfo_BillingPageTitleFR"),commonElements.getPageTitle());
		Assert.assertEquals("Fail: Incorrect Billing Section Title FR",data.get("billingInfo_BillingSectionTitleFR"),billingInfo.getBillingInfoSectionTitle());
		Assert.assertEquals("Fail: Incorrect Payment Section Title FR",data.get("billingInfo_PaymentSectionTitleFR"),billingInfo.getPaymentInfoSectionTitle());
		//Verify Pay premiums section labels FR
		Assert.assertEquals("Fail: Incorrect Pay Premium Question Label FR",data.get("billingInfo_PayPremiumsQuestionLabelFR"),billingInfo.getPayPremiumsQuestionLabel());
		Assert.assertEquals("Fail: Incorrect Pay Premium Paragraph Label FR",data.get("billingInfo_PayPremiumsParagraphLabelFR"),billingInfo.getPayPremiumsParagraphLabel());
	}
	
	
	@Test(priority=7, enabled= true,dataProvider = "billingInfoTestData",testName = "Error Handling - Pay Premium Required Fields", groups = "webBrowser")
	public void verifyPayPremiumsRequiredErrors(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		commonElements.clickNextBtn();
		//Assert.assertEquals("Fail: EN Pay Premium checkbox field has incorrect required error message","Required",billingInfo.getPayPremiumError());
		commonElements.clickENFRToggle();
		//Assert.assertEquals("Fail: FR Pay Premium checkbox field has incorrect required error message","Obligatoire",billingInfo.getPayPremiumError());

	}
	
	@Test(priority=8,enabled= true,dataProvider = "billingInfoTestData",testName = "Error Handling - First Payment Required Fields", groups = "webBrowser")
	public void verifyFirstPaymentRequiredErrors(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN First Payment field has incorrect required error message","Required",billingInfo.getFirstPaymentError());
		Assert.assertEquals("Fail: EN Amount Payment Type field has incorrect required error message","Required",billingInfo.getAmountPaymentError());
		Assert.assertEquals("Fail: EN Amount Payment Value field has incorrect required error message","Required",billingInfo.getActualAmountError());
		Assert.assertEquals("Fail: EN Payment Method field has incorrect required error message","Required",billingInfo.getPaymentMethodError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR First Payment field has incorrect required error message","Obligatoire",billingInfo.getFirstPaymentError());
		Assert.assertEquals("Fail: FR Amount Payment Type field has incorrect required error message","Obligatoire",billingInfo.getAmountPaymentError());
		Assert.assertEquals("Fail: FR Amount Payment Value field has incorrect required error message","Obligatoire",billingInfo.getActualAmountError());
		Assert.assertEquals("Fail: FR Payment Method field has incorrect required error message","Obligatoire",billingInfo.getPaymentMethodError());
		//Add new PAD Required Fields
		billingInfo.selectFirstPaymentMethod("Ajoutez ce prélèvement automatique sur le compte (PAC) à un plan de PAC existant");
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: FR PAD plan field has incorrect required error message","Obligatoire",billingInfo.getPADError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: EN PAD plan field has incorrect required error message","Required",billingInfo.getPADError());
		//Banking Information Fields Required Fields
		billingInfo.selectFirstPaymentMethod("Set up a new PAD plan");
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Transit Number field has incorrect required error message","Required",billingInfo.getTransitError());
		Assert.assertEquals("Fail: EN Institution Number field has incorrect required error message","Required",billingInfo.getInstitutionError());
		Assert.assertEquals("Fail: EN Account Number field has incorrect required error message","Required",billingInfo.getAccountError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Transit Number field has incorrect required error message","Obligatoire",billingInfo.getTransitError());
		Assert.assertEquals("Fail: FR Institution Number field has incorrect required error message","Obligatoire",billingInfo.getInstitutionError());
		Assert.assertEquals("Fail: FR Account Number field has incorrect required error message","Obligatoire",billingInfo.getAccountError());
	}
	
	@Test(priority=9, enabled= true,dataProvider = "billingInfoTestData",testName = "Error Handling - Regular Payment Manulife Par Required Fields", groups = "webBrowser")
	public void verifyRegularPaymentManulifeParRequiredErrors(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		//Verify Frequency Payment Error
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Frequency of Payments field has incorrect required error message","Required",billingInfo.getFrequencyError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Frequency of Payments field has incorrect required error message","Obligatoire",billingInfo.getFrequencyError());
		//Verify Premium Amount Error
		billingInfo.getFrequencyPayment("Mensuellement").click();
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: FR Premium Amount field has incorrect required error message","Obligatoire",billingInfo.getMonthlyAnnuallyAmountError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: EN Premium Amount field has incorrect required error message","Required",billingInfo.getMonthlyAnnuallyAmountError());
	}
	
	@Test(priority=10, enabled= true,dataProvider = "billingInfoTestData",testName = "Error Handling - Regular Payment Performax Gold Required Fields", groups = "webBrowser")
	public void verifyRegularPaymentPerformaxGoldRequiredErrors(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		//Verify Frequency Payment and Cover Costs Error
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Frequency of Payments field has incorrect required error message","Required",billingInfo.getFrequencyError());
		//Assert.assertEquals("Fail: EN Cover Cost Amount field has incorrect required error message","Required",billingInfo.getCoverCostError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Frequency of Payments field has incorrect required error message","Obligatoire",billingInfo.getFrequencyError());
		//Assert.assertEquals("Fail: FR Cover Cost Amount field has incorrect required error message","Obligatoire",billingInfo.getCoverCostError());	
	}
	
	@Test(priority=11, enabled= true,dataProvider = "billingInfoTestData",testName = "Error Handing - First Payment Banking Info", groups = "webBrowser")
	public void verifyFirstPaymentBankNumberErrors(Map<String,String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillPagesUntilBillingPagePage(data);
		
		billingInfo.selectFirstPaymentMethod("Set up a new PAD plan");
		//Banking Information Fields Invalid Number Error Message
		billingInfo.getTransitNumber().sendKeys("1234");
		
		
		billingInfo.ClearTextBox(getDriver(),billingInfo.getInstitutionNumber());
		billingInfo.getInstitutionNumber().sendKeys("12");
		billingInfo.getAccountNumber().sendKeys("123456");
		commonElements.clickNextBtn();
		Assert.assertEquals("Fail: EN Transit Number field should have incorrect invalid number error message for values less than 5 digits",data.get("billingInfo_InvalidTransitErrorEN"),billingInfo.getTransitError());
		Assert.assertEquals("Fail: EN Institution Number field should have invalid number error message for values less than 3 digits",data.get("billingInfo_InvalidInstitutionErrorEN"),billingInfo.getInstitutionError());
		Assert.assertEquals("Fail: EN Account Number field should have invalid number error message for values less than 7 digits",data.get("billingInfo_InvalidAccountErrorEN"),billingInfo.getAccountError());
		commonElements.clickENFRToggle();
		Assert.assertEquals("Fail: FR Transit Number field should have incorrect invalid number error message for values less than 5 digits",data.get("billingInfo_InvalidTransitErrorFR"),billingInfo.getTransitError());
		Assert.assertEquals("Fail: FR Institution Number field should have invalid number error message for values less than 3 digits",data.get("billingInfo_InvalidInstitutionErrorFR"),billingInfo.getInstitutionError());
		Assert.assertEquals("Fail: FR Account Number field should have invalid number error message for values less than 7 digits",data.get("billingInfo_InvalidAccountErrorFR"),billingInfo.getAccountError());
		//Verify Message is not present when digit length is satisfied
		
		billingInfo.ClearTextBox(getDriver(),billingInfo.getTransitNumber());
		String ExpectedTransitNumber="12345";
		billingInfo.getTransitNumber().sendKeys("12345");
		String actTransitNumber = billingInfo.getTransitNumber().getAttribute("value");
		billingInfo.ClearTextBox(getDriver(),billingInfo.getInstitutionNumber());
		String ExpectedInstitutionNumber="123";
		billingInfo.getInstitutionNumber().sendKeys("123");
		String actInstitutionNumber = billingInfo.getInstitutionNumber().getAttribute("value");
		billingInfo.ClearTextBox(getDriver(),billingInfo.getAccountNumber());
		String ExpectedAccountNumber="12345677";
		billingInfo.getAccountNumber().sendKeys("12345677");
		String actAccountNumber = billingInfo.getAccountNumber().getAttribute("value");
		commonElements.clickNextBtn();
		
		Assert.assertTrue("Fail: FR Transit Number field no number error message for values = 5 digits",actTransitNumber.equalsIgnoreCase(ExpectedTransitNumber));
		Assert.assertTrue("Fail: FR Institution Number field no number error message for values = 3 digits",actInstitutionNumber.equalsIgnoreCase(ExpectedInstitutionNumber));
		Assert.assertTrue("Fail: FR Account Number field no number error message for values = 7 digits",actAccountNumber.equalsIgnoreCase(ExpectedAccountNumber));
	}
	
}
