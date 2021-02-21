package term_conversion;

import java.lang.reflect.Method;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

import junit.framework.Assert;

public class EsignTest extends BaseTest {

	String name1 = "";
	String name2 = "";
	String name3 = "";
	String name4 = "";
	boolean EnterCityflag = false;
	boolean advisorFlag = true;

	public void fillUntilEsignTestPage(Map<String, String> data) throws InterruptedException {
		FillPages fillPage = new FillPages(driverUtil);
		fillPage.fillLogin();
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.startApplication();

		fillPage.fillPolicyPage(data);
		fillPage.fillInsuredPage(data);
		fillPage.fillOwnerPage(data);
		fillPage.fillSuccessorPage(data);
		fillPage.fillCoveragePage(data);
		fillPage.fillProductPage(data);
		fillPage.fillBenificiaryPage(data);
		fillPage.fillBillingandpaymentPage(data);
		fillPage.fillAttachmentspage(data);
		fillPage.fillAdvisorpage(data);

	}

	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en", "policyInfoUrl");
	}

	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");

	@DataProvider(name = "eSignTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception {
		// Getting the Data Row against the Test Case name and store it within an array
		Object[][] testObjArray = excelUtil.getAllMatchingTestCases("eSignTestData", method.getName());
		return (testObjArray);
	}

	

	@Test(dataProvider = "eSignTestData", priority = 0, testName = "Label Validation in English and in French ", groups = "webBrowser")
	public void LabelValidationInEnglishInFrench(Map<String, String> data) throws Exception {
		ESignPage page = new ESignPage(driverUtil);

		CommonElements commonElements = new CommonElements(driverUtil);
		

		fillUntilEsignTestPage(data);

		String InsuredOneName = data.get("insuredInfo_FirstName");
		String InsuredTwoName = data.get("insuredInfo_FirstName2");
		String OwnerTwoName = data.get("owner2_FirstName");

		if (InsuredOneName.equalsIgnoreCase(InsuredTwoName) || InsuredOneName.equalsIgnoreCase(OwnerTwoName)
				|| InsuredTwoName.equalsIgnoreCase(OwnerTwoName)) {

			Assert.fail("Insured or owner name should be different");
		}

		page.VerifyPageTitle(data.get("eSignTest_PageTitle"));
		page.VerifyEsignMethodLabel(InsuredOneName, data.get("eSignTest_Method_Label_EN"));
		page.VerifyESignClinetNowLabel(InsuredOneName, data.get("eSignTest_ClientNowLabel_EN"));
		page.VerifyEsignEmailLabel(InsuredOneName, data.get("eSignTest_EmailLaterLabel_EN"));

		page.clickOnESignEmailCheckBox(InsuredOneName);
		page.VerifyAdvancePasswordLabel(data.get("eSignTest_AdvancePasswordLabel_EN"));
		page.VerifyAdvanceConfirmPasswordLabel(data.get("eSignTest_AdvanceConfirmPasswordLabel_EN"));

		// Test in French Label

		commonElements.clickENFRToggle();
		page.VerifyPageTitle(data.get("eSignTest_PageTitleFR"));
		page.VerifyEsignMethodLabel(InsuredOneName, data.get("eSignTest_Method_Label_FR"));
		page.VerifyESignClinetNowLabel(InsuredOneName, data.get("eSignTest_ClientNowLabel_FR"));
		page.VerifyEsignEmailLabel(InsuredOneName, data.get("eSignTest_EmailLaterLabel_FR"));
		page.VerifyAdvancePasswordLabel(data.get("eSignTest_AdvancePasswordLabel_FR"));
		page.VerifyAdvanceConfirmPasswordLabel(data.get("eSignTest_AdvanceConfirmPasswordLabel_FR"));

	}

	@Test(enabled = true, dataProvider = "eSignTestData", priority = 1, testName = "Error Validation in English and in french ", groups = "webBrowser")
	public void ErrorValidationInEnglishInFrench(Map<String, String> data) throws Exception {
		ESignPage page = new ESignPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillUntilEsignTestPage(data);

		String esignMethod1 = data.get("eSignature method_InsuredOne");
		String esignMethod2 = data.get("eSignature method_InsuredTwo");
		String InsuredOneName = data.get("insuredInfo_FirstName");
		String InsuredTwoName = data.get("insuredInfo_FirstName2");
		String OwnerTwoName = data.get("owner2_FirstName");

		if (InsuredOneName.equalsIgnoreCase(InsuredTwoName) || InsuredOneName.equalsIgnoreCase(OwnerTwoName)
				|| InsuredTwoName.equalsIgnoreCase(OwnerTwoName)) {

			Assert.fail("Insured or owner name should be different");
		}

		if (data.get("policyInfo_CoverageType").contains("Joint")) {
			commonElements.clickNextBtn();
			Assert.assertTrue("Fail: we are not getting Required error message in English for Insured person 1 ",
					page.getEsignMethodError(InsuredOneName).getText().equalsIgnoreCase("Required"));
			Assert.assertTrue("Fail: we are not getting Required error message in English for Insured person 2",
					page.getEsignMethodError(InsuredTwoName).getText().equalsIgnoreCase("Required"));
			Thread.sleep(3000);
			commonElements.clickENFRToggle();
			Assert.assertTrue("Fail first insured eSignMethod: we are not getting Obligatoire error message in French ",
					page.getEsignMethodError(InsuredOneName).getText().equalsIgnoreCase("Obligatoire"));
			Assert.assertTrue(
					"Fail second insured eSignMethod : we are not getting Obligatoire error message in French",
					page.getEsignMethodError(InsuredTwoName).getText().equalsIgnoreCase("Obligatoire"));
			Thread.sleep(3000);
			// verify Error message in EN

			commonElements.clickENFRToggle();

			page.clickOnESignEmailCheckBox(InsuredOneName);
			page.clickOnESignEmailCheckBox(InsuredTwoName);
			commonElements.clickNextBtn();
			page.VerifyRequiredErrorMessageForEmail(InsuredOneName, "Required");
			page.VerifyRequiredErrorMessageForEmail(InsuredTwoName, "Required");

			page.VerifyRequiredErrorMessageForAdvancePassword("Required");
			page.VerifyRequiredErrorMessageForAdvanceConfirmPassword("Required");
			Thread.sleep(3000);
			// verify Error message in FR
			commonElements.clickENFRToggle();
			page.VerifyRequiredErrorMessageForEmail(InsuredOneName, "Obligatoire");
			page.VerifyRequiredErrorMessageForEmail(InsuredTwoName, "Obligatoire");
			page.VerifyRequiredErrorMessageForAdvancePassword("Obligatoire");
			page.VerifyRequiredErrorMessageForAdvanceConfirmPassword("Obligatoire");
			Thread.sleep(3000);

			commonElements.clickENFRToggle();
			// password does not match error

			page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);
			page.EnterEmailID(InsuredTwoName, data.get("Email_InsuredTwo"), esignMethod2);

			page.EnterAdvancePassword("Test1234");
			page.EnterConfirmPassword("T123456A");

			commonElements.clickNextBtn();

			page.VerifyRequiredErrorMessageForAdvanceConfirmPassword("Passwords don't match");
			Thread.sleep(3000);

			commonElements.clickENFRToggle();
			Thread.sleep(3000);
			page.VerifyRequiredErrorMessageForAdvanceConfirmPassword("Les mots de passe ne correspondent pas");
			// validation in ENG
			commonElements.clickENFRToggle();
			// verifying invalid email format error message
			page.EnterEmailID(InsuredOneName, "navya_t@manulifeca", esignMethod1);
			page.EnterEmailID(InsuredTwoName, "navya_t@manulifeca", esignMethod2);
			commonElements.clickNextBtn();
			page.VerifyRequiredErrorMessageForEmail(InsuredOneName, "Invalid email format");
			page.VerifyRequiredErrorMessageForEmail(InsuredTwoName, "Invalid email format");
			Thread.sleep(3000);
			commonElements.clickENFRToggle();
			page.VerifyRequiredErrorMessageForEmail(InsuredOneName, "Courriel invalide");
			page.VerifyRequiredErrorMessageForEmail(InsuredTwoName, "Courriel invalide");
			commonElements.clickENFRToggle();
			// verifying password must be between 4 to 8 characters, alphanumeric or numeric
			// error message
			page.ClearTextBox(getDriver(), page.getAdvancePasswordElement());

			page.EnterAdvancePassword(data.get("eSignTest_AdvancePassword"));
			page.ClearTextBox(getDriver(), page.getAdvanceConfirmPasswordElement());
			page.EnterConfirmPassword(data.get("eSignTest_ConfirmPassword"));
			commonElements.clickNextBtn();
			page.VerifyRequiredErrorMessageForAdvancePassword(
					"Password must be between 4 to 8 characters, alphanumeric, or numeric");
			page.VerifyRequiredErrorMessageForAdvanceConfirmPassword(
					"Password must be between 4 to 8 characters, alphanumeric, or numeric");
			Thread.sleep(3000);

			commonElements.clickENFRToggle();
			Thread.sleep(3000);
			page.VerifyRequiredErrorMessageForAdvancePassword(
					"Le mot de passe doit comporter de 4 à 8 caractères alphanumériques ou numériques");
			page.VerifyRequiredErrorMessageForAdvanceConfirmPassword(
					"Le mot de passe doit comporter de 4 à 8 caractères alphanumériques ou numériques");
			// validation in ENG
			commonElements.clickENFRToggle();

		} else {
			Assert.fail("Fail:We are  expecting coverage type as Joint but found as  ( "
					+ data.get("policyInfo_CoverageType") + "). Please change the coverage type in test data ");
		}

	}

	@Test(dataProvider = "eSignTestData",enabled=true, priority = 2, testName = "Verifying selecting both insureds as eSign with client now", groups = "webBrowser")
	public void validateEsignWithClientForBothInsureds(Map<String, String> data) throws Exception

	{
		// selecting both insureds as eSign with client now
		ESignPage page = new ESignPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillUntilEsignTestPage(data);
		CompleteEsignaturesPage completePage = new CompleteEsignaturesPage(driverUtil);

		String InsuredOneName = data.get("insuredInfo_FirstName");
		String InsuredTwoName = data.get("insuredInfo_FirstName2");
		String OwnerTwoName = data.get("owner2_FirstName");
		String insuredname = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " "
				+ data.get("insuredInfo_LastName");
		String esignMethod1 = data.get("eSignature method_InsuredOne");
		String esignMethod2 = data.get("eSignature method_InsuredTwo");

		String esignMethod3 = data.get("eSignature method_InsuredThree");
		String esignMethod4 = data.get("eSignature method_InsuredFour");

		String insuredOneOption = data.get("insuredInfo_IsOwnerInsured");
		String insuredTwoOption = data.get("insuredInfo_IsOwnerInsured2");

		// if single life is choosen
		if (data.get("policyInfo_CoverageType").contains("Single")) {
			// filling the data for insured 1
			page.VerifyInsuredName(0, insuredname);
			page.clickOnESignCheckBox(InsuredOneName);
			page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);
			commonElements.clickNextBtn();
             Thread.sleep(7000);
			System.out.println("eSign application page is completed");

			completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));
			completePage.VerifyESignCompleteFromTitle(data.get("completeEsign_FormTitle_EN_One"), 0);
			insuredname = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " "
					+ data.get("insuredInfo_LastName");
			completePage.verifyInsuredName(insuredname, 0);
			completePage.clickOnESignNowButton(0);
			completePage.waitUntilElementDisplayed(completePage.getEsignCheckBox(getDriver()), getDriver());
			Thread.sleep(7000);
			completePage.getEsignCheckBox(getDriver()).sendKeys(Keys.SPACE);

			completePage.getContinuButton().click();

			for (int i = 0; i < 16; i++) {
				completePage.getNextButton().click();
				Thread.sleep(100);

				if (completePage.getNextButton().getText().equalsIgnoreCase("SIGN")) {
					Thread.sleep(7000);
					completePage.getSingnatureButton().click();
					completePage.ClickOnAdoptButton();

					break;
				}
			}
			completePage.getSignedAtCityOwner("waterloo");
			Thread.sleep(2000);
			completePage.getFinishButton().click();
			Thread.sleep(10000);

			// Advisor is signing
			ESignForAdvisor(completePage);

		}
		if (data.get("policyInfo_CoverageType").contains("Joint")) {

			if (insuredOneOption.equalsIgnoreCase("Yes") && insuredTwoOption.equalsIgnoreCase("Yes")) {
				// esgin will contins only 2 options and 1 advisor
				page.VerifyInsuredName(0, insuredname);
				// filling the data for insured 2
				insuredname = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " "
						+ data.get("insuredInfo_LastName2");

				page.VerifyInsuredName(1, insuredname);
				page.clickOnESignCheckBox(InsuredOneName);
				page.clickOnESignCheckBox(InsuredTwoName);

				page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);
				page.EnterEmailID(InsuredTwoName, data.get("Email_InsuredTwo"), esignMethod2);
				commonElements.clickNextBtn();
				Thread.sleep(5000);
				System.out.println("eSign application page is completed");
				completePage.waitUntilElementNotDisplayed(getDriver());
				completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));

				String insurednameOne = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial")
						+ " " + data.get("insuredInfo_LastName");
				String insurednameTwo = data.get("insuredInfo_FirstName2") + " "
						+ data.get("insuredInfo_MiddleInitial2") + " " + data.get("insuredInfo_LastName2");
				completePage.verifyInsuredName(insurednameOne, 0);
				completePage.verifyInsuredName(insurednameTwo, 1);
				String advisorname = data.get("Advisor_Name");
				completePage.verifyInsuredName(advisorname, 2);

				completePage.clickOnESignNowButton(0);

				// First Insured Esign client Now Button

				// completePage.getAccessCodeElement(getDriver()).sendKeys("11");
				// completePage.getValidateButton().click();

				completePage.waitUntilElementDisplayed(completePage.getEsignCheckBox(getDriver()), getDriver());
				Thread.sleep(7000);
				completePage.getEsignCheckBox(getDriver()).sendKeys(Keys.SPACE);

				completePage.getContinuButton().click();

				for (int i = 0; i < 16; i++) {
					completePage.getNextButton().click();
					Thread.sleep(100);

					if (completePage.getNextButton().getText().equalsIgnoreCase("SIGN")) {
						Thread.sleep(7000);
						completePage.getSingnatureButton().click();
						completePage.ClickOnAdoptButton();

						break;
					}
				}
				completePage.getSignedAtCityOwner("waterloo");
				Thread.sleep(2000);
				completePage.getFinishButton().click();
				Thread.sleep(10000);

				// second insured
				completePage.clickOnESignNowButton(0);

				// completePage.getAccessCodeElement(getDriver()).sendKeys("12");
				// completePage.getValidateButton().click();
				completePage.waitUntilElementDisplayed(completePage.getEsignCheckBox(getDriver()), getDriver());
				Thread.sleep(7000);
				completePage.getEsignCheckBox(getDriver()).sendKeys(Keys.SPACE);
				completePage.getContinuButton().click();

				for (int i = 0; i < 16; i++) {
					completePage.getNextButton().click();
					Thread.sleep(100);

					if (completePage.getNextButton().getText().equalsIgnoreCase("SIGN")) {
						Thread.sleep(6000);
						completePage.getSingnatureButton().click();
						completePage.ClickOnAdoptButton();

						break;
					}
				}
				completePage.getSignedAtCityOwner("waterloo");
				Thread.sleep(2000);
				completePage.getFinishButton().click();
				Thread.sleep(10000);

			}

			// Advisor is signing

			if (!completePage.CheckEsignButtonIsDisabled(0)) {

				completePage.clickOnESignNowButton(0);

				// completePage.getAccessCodeElement(getDriver()).sendKeys("31");
				// completePage.getValidateButton().click();
				Thread.sleep(8000);
				completePage.getEsignCheckBox(getDriver()).sendKeys(Keys.SPACE);
				completePage.getContinuButton().click();
				// while(!completePage.getNextButton().getText().equalsIgnoreCase("SIGN"))
				// {
				//
				// }

				for (int i = 0; i <= 17; i++) {
					completePage.getNextButton().click();
					Thread.sleep(500);
					if (completePage.getNextButton().getText().equalsIgnoreCase("SIGN")) {
						Thread.sleep(7000);
						completePage.getSingnatureButton().click();
						completePage.ClickOnAdoptButton();
						break;
					}
				}
				completePage.getFinishButton().click();
				Thread.sleep(10000);

			}

		}

	}

	@Test(dataProvider = "eSignTestData",enabled=false, priority = 3, testName = "Verifying selecting eSign with client now for  insured 1 and selecting eSign by email later for insured2 ", groups = "webBrowser")
	public void validateEsignWithClientForInsuredOne(Map<String, String> data) throws Exception {
		// selecting eSign with client now for insured 1 and selecting eSign by email
		// later for insured2
		ESignPage page = new ESignPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		CompleteEsignaturesPage completePage = new CompleteEsignaturesPage(driverUtil);
		fillUntilEsignTestPage(data);

		String InsuredOneName = data.get("insuredInfo_FirstName");
		String InsuredTwoName = data.get("insuredInfo_FirstName2");

		String insuredname = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " "
				+ data.get("insuredInfo_LastName");

		String password = data.get("eSignTest_AdvancePassword");
		String confirmPassword = data.get("eSignTest_ConfirmPassword");
		String esignMethod1 = data.get("eSignature method_InsuredOne");
		String esignMethod2 = data.get("eSignature method_InsuredTwo");
		// if single life is choosen
		if (data.get("policyInfo_CoverageType").contains("Single")) {
			// filling the data for insured 1
			page.VerifyInsuredName(0, insuredname);
			page.clickOnESignEmailCheckBox(InsuredOneName);

			page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);

			boolean passwordfield = page.EnterAdvancePassword(password);
			Assert.assertTrue("Fail: User unable to enter the value in password field", passwordfield);
			passwordfield = page.EnterConfirmPassword(confirmPassword);
			Assert.assertTrue("Fail:User unable to enter the value in  confirm password field", passwordfield);

			commonElements.clickNextBtn();
			// eSign application page is completed

			completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));

			completePage.VerifyESignCompleteFromTitle("eSign by email", 0);

			String insurePersonOneName = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial")
					+ " " + data.get("insuredInfo_LastName");
			String insurePersonOneEmail = data.get("Email_InsuredOne");

			String[] actualArrayOne = commonElements.getTableBody("//table[@role = 'table']", 0);
			if (actualArrayOne.length > 1) {

				Assert.assertTrue("Fail:We are expecting insured 1 name as  (" + insurePersonOneName
						+ ") but found as (" + actualArrayOne[1] + ")",
						actualArrayOne[1].equalsIgnoreCase(insurePersonOneName));
				Assert.assertTrue("Fail:We are expecting insured 1 email as  (" + insurePersonOneEmail
						+ ") but found as (" + actualArrayOne[2] + ")",
						actualArrayOne[2].equalsIgnoreCase(insurePersonOneEmail));
			} else {
				Assert.fail("No data find in table");
			}

		}
		if (data.get("policyInfo_CoverageType").contains("Joint")) {

			page.VerifyInsuredName(0, insuredname);
			// filling the data for insured 2
			insuredname = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " "
					+ data.get("insuredInfo_LastName2");

			page.VerifyInsuredName(1, insuredname);
			page.clickOnESignCheckBox(InsuredOneName);

			page.clickOnESignEmailCheckBox(InsuredTwoName);

			page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);
			page.EnterEmailID(InsuredTwoName, data.get("Email_InsuredTwo"), esignMethod2);

			boolean passwordfield = page.EnterAdvancePassword(password);
			Assert.assertTrue("Fail Joint: User unable to enter the value in password field", passwordfield);
			passwordfield = page.EnterConfirmPassword(confirmPassword);
			Assert.assertTrue("Fail Joint :User unable to enter the value in  confirm password field", passwordfield);

			commonElements.clickNextBtn();
			Thread.sleep(12000);

			System.out.println("eSign application page is completed");
			// completePage.waitUntilElementDisplayed(completePage.getEsignCheckBox(getDriver()),getDriver());
			completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));

			completePage.clickOnESignNowButton(0);
			Thread.sleep(7000);

			// First Insured Esign client Now Button

			// completePage.getAccessCodeElement(getDriver()).sendKeys("11");
			// completePage.getValidateButton().click();

			completePage.getEsignCheckBox(getDriver()).sendKeys(Keys.SPACE);
			completePage.getContinuButton().click();

			for (int i = 0; i <= 14; i++) {
				completePage.getNextButton().click();
				Thread.sleep(1000);
				if (completePage.getNextButton().getText().equalsIgnoreCase("SIGN")) {

					Thread.sleep(8000);
					completePage.getSingnatureButton().click();
					completePage.ClickOnAdoptButton();
					break;
				}
			}
			Thread.sleep(3000);
			completePage.getFinishButton().click();
			Thread.sleep(6000);
			Assert.assertTrue("Fail: User (" + InsuredOneName + ") unable to eSigned",
					completePage.getEsignedLabel(InsuredOneName).getText().equalsIgnoreCase("eSigned"));

		}

	}

	@Test(dataProvider = "eSignTestData",enabled=false, priority = 4, testName = "Verifyingselecting eSign by email later for  insured 1 and selecting eSign with client now for insured2  ", groups = "webBrowser")
	public void validateEsignWithClientForInsuredTwo(Map<String, String> data) throws Exception {
		// selecting eSign by email later for insured 1 and selecting eSign with client
		// now for insured2
		ESignPage page = new ESignPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		CompleteEsignaturesPage completePage = new CompleteEsignaturesPage(driverUtil);

		fillUntilEsignTestPage(data);
		String InsuredOneName = data.get("insuredInfo_FirstName");
		String InsuredTwoName = data.get("insuredInfo_FirstName2");

		String insuredname = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " "
				+ data.get("insuredInfo_LastName");

		String password = data.get("eSignTest_AdvancePassword");
		String confirmPassword = data.get("eSignTest_ConfirmPassword");
		String esignMethod1 = data.get("eSignature method_InsuredOne");
		String esignMethod2 = data.get("eSignature method_InsuredTwo");

		if (data.get("policyInfo_CoverageType").contains("Joint")) {

			page.VerifyInsuredName(0, insuredname);
			// filling the data for insured 2
			insuredname = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " "
					+ data.get("insuredInfo_LastName2");

			page.VerifyInsuredName(1, insuredname);

			page.clickOnESignEmailCheckBox(InsuredOneName);
			page.clickOnESignCheckBox(InsuredTwoName);

			page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);
			page.EnterEmailID(InsuredTwoName, data.get("Email_InsuredTwo"), esignMethod2);

			boolean passwordfield = page.EnterAdvancePassword(password);
			Assert.assertTrue("Fail Joint: User unable to enter the value in password field", passwordfield);
			passwordfield = page.EnterConfirmPassword(confirmPassword);
			Assert.assertTrue("Fail Joint :User unable to enter the value in  confirm password field", passwordfield);

		} else {
			Assert.fail("Fail:We are  expecting coverage type as Joint but found as  ( "
					+ data.get("policyInfo_CoverageType") + "). Please change the coverage type in test data ");
		}

		commonElements.clickNextBtn();
		Thread.sleep(5000);

		System.out.println("eSign application page is completed");

		completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));
		completePage.clickOnESignNowButton(0);

		// First Insured Esign client Now Button

		// completePage.getAccessCodeElement(getDriver()).sendKeys("12");
		// completePage.getValidateButton().click();

		completePage.getEsignCheckBox(getDriver()).sendKeys(Keys.SPACE);
		completePage.getContinuButton().click();

		for (int i = 0; i <= 7; i++) {
			completePage.getNextButton().click();

			if (completePage.getNextButton().getText().equalsIgnoreCase("SIGN")) {
				completePage.getSingnatureButton().click();
				completePage.ClickOnAdoptButton();
				break;
			}
		}
		completePage.getFinishButton().click();
		Thread.sleep(6000);

	}

	@Test(dataProvider = "eSignTestData",enabled=true, priority = 5, testName = "Verifying selecting eSign by email later for both insureds ", groups = "webBrowser")
	public void validateEsignByEmailForBothInsureds(Map<String, String> data) throws Exception {
		// selecting eSign by email later for both insureds
		ESignPage page = new ESignPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		CompleteEsignaturesPage completePage = new CompleteEsignaturesPage(driverUtil);

		fillUntilEsignTestPage(data);
		String InsuredOneName = data.get("insuredInfo_FirstName");
		String InsuredTwoName = data.get("insuredInfo_FirstName2");

		String insuredname = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial") + " "
				+ data.get("insuredInfo_LastName");

		String password = data.get("eSignTest_AdvancePassword");
		String confirmPassword = data.get("eSignTest_ConfirmPassword");

		String esignMethod1 = data.get("eSignature method_InsuredOne");
		String esignMethod2 = data.get("eSignature method_InsuredTwo");
		if (data.get("policyInfo_CoverageType").contains("Joint")) {

			page.VerifyInsuredName(0, insuredname);
			// filling the data for insured 2
			insuredname = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2") + " "
					+ data.get("insuredInfo_LastName2");

			page.VerifyInsuredName(1, insuredname);

			page.clickOnESignEmailCheckBox(InsuredOneName);
			page.clickOnESignEmailCheckBox(InsuredTwoName);
			Thread.sleep(2000);

			page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);
			page.EnterEmailID(InsuredTwoName, data.get("Email_InsuredTwo"), esignMethod2);
			Thread.sleep(2000);

			boolean passwordfield = page.EnterAdvancePassword(password);
			Assert.assertTrue("Fail Joint: User unable to enter the value in password field", passwordfield);
			passwordfield = page.EnterConfirmPassword(confirmPassword);
			Assert.assertTrue("Fail Joint :User unable to enter the value in  confirm password field", passwordfield);
			Thread.sleep(2000);
		} else {
			Assert.fail("Fail:We are  expecting coverage type as Joint but found as  ( "
					+ data.get("policyInfo_CoverageType") + "). Please change the coverage type in test data ");
		}

		commonElements.clickNextBtn();

		completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));
		completePage.VerifyESignCompleteFromTitle("eSign by email", 0);

		String insurePersonOneName = data.get("insuredInfo_FirstName") + " " + data.get("insuredInfo_MiddleInitial")
				+ " " + data.get("insuredInfo_LastName");
		String insurePersonOneEmail = data.get("Email_InsuredOne");

		String insurePersonTwoName = data.get("insuredInfo_FirstName2") + " " + data.get("insuredInfo_MiddleInitial2")
				+ " " + data.get("insuredInfo_LastName2");
		String insurePersonTwoEmail = data.get("Email_InsuredTwo");

		String[] actualArrayOne = commonElements.getTableBody("//table[@role = 'table']", 0);
		if (actualArrayOne.length > 1) {

			Assert.assertTrue("Fail:We are expecting insured 1 name as  (" + insurePersonOneName + ") but found as ("
					+ actualArrayOne[1] + ")", actualArrayOne[1].equalsIgnoreCase(insurePersonOneName));
			Assert.assertTrue("Fail:We are expecting insured 1 email as  (" + insurePersonOneEmail + ") but found as ("
					+ actualArrayOne[2] + ")", actualArrayOne[2].equalsIgnoreCase(insurePersonOneEmail));
		} else {
			Assert.fail("No data find in table");
		}

		String[] actualArrayTwo = commonElements.getTableBody("//table[@role = 'table']", 1);

		if (actualArrayOne.length > 1) {
			Assert.assertTrue("Fail:We are expecting insured 2 name as  (" + insurePersonTwoName + ") but found as ("
					+ actualArrayTwo[1] + ")", actualArrayTwo[1].equalsIgnoreCase(insurePersonTwoName));
			Assert.assertTrue("Fail:We are expecting insured 2 email as  (" + insurePersonTwoEmail + ") but found as ("
					+ actualArrayTwo[2] + ")", actualArrayTwo[2].equalsIgnoreCase(insurePersonTwoEmail));

		} else {
			Assert.fail("No data find in table");
		}

	}

	@Test(dataProvider = "eSignTestData",enabled=true, priority = 5, testName = "verifying lable validation when selecting both insureds as eSign with client now for complete eSignatures page in english and french ", groups = "webBrowser")
	public void validateLablesForCompleteEsignaturesInEnglishAndFrench(Map<String, String> data) throws Exception {
		ESignPage page = new ESignPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillUntilEsignTestPage(data);
		String coverageType = data.get("policyInfo_CoverageType");
		CompleteEsignaturesPage completePage = new CompleteEsignaturesPage(driverUtil);

		if (coverageType.contains("Joint")) {
			fillEsignApplicationPage(data);

			Thread.sleep(5000);
			completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));
			// FR
			commonElements.clickENFRToggle();
			completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleFR"));
			commonElements.clickENFRToggle();
			// EN
			completePage.VerifyESignCompleteFromTitle(data.get("completeEsign_FormTitle_EN_One"), 0);

			commonElements.clickENFRToggle();
			// Fr
			completePage.VerifyESignCompleteFromTitle(data.get("completeEsign_FormTitle_FR_One"), 0);

		} else {
			Assert.fail(
					"Fail: complete eSignatures page lable validation:We are  expecting coverage type as Joint but found as  ( "
							+ coverageType + "). Please change the coverage type in test data ");
		}

	}

	public void fillEsignApplicationPage(Map<String, String> data) {
		ESignPage page = new ESignPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);

		String InsuredOneName = data.get("insuredInfo_FirstName");
		String InsuredTwoName = data.get("insuredInfo_FirstName2");
		String OwnerTwoName = data.get("owner2_FirstName");

		if (InsuredOneName.equalsIgnoreCase(InsuredTwoName) || InsuredOneName.equalsIgnoreCase(OwnerTwoName)
				|| InsuredTwoName.equalsIgnoreCase(OwnerTwoName)) {

			Assert.fail("Insured or owner name should be different");
		}

		String esignMethod1 = data.get("eSignature method_InsuredOne");
		String esignMethod2 = data.get("eSignature method_InsuredTwo");
		String esignMethod3 = data.get("eSignature method_OwnerTwo");

		page.clickOnESignCheckBox(InsuredOneName);
		page.clickOnESignCheckBox(InsuredTwoName);
		page.clickOnESignCheckBox(OwnerTwoName);

		page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);
		page.EnterEmailID(InsuredTwoName, data.get("Email_InsuredTwo"), esignMethod2);
		page.EnterEmailID(OwnerTwoName, data.get("Email_OwnerTwo"), esignMethod3);

		commonElements.clickNextBtn();

	}

	@Test(dataProvider = "eSignTestData",enabled=true, priority = 6, testName = "verifying lable validation when selecting one insured as eSign with client now and insured 2 as eSign by email later for complete eSignatures page in english and french ", groups = "webBrowser")
	public void verifyLablesForeSignWithClientAndEsignByEmailInEnAndFr(Map<String, String> data) throws Exception {
		ESignPage page = new ESignPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillUntilEsignTestPage(data);

		String InsuredOneName = data.get("insuredInfo_FirstName");
		String InsuredTwoName = data.get("insuredInfo_FirstName2");

		String coverageType = data.get("policyInfo_CoverageType");
		CompleteEsignaturesPage completePage = new CompleteEsignaturesPage(driverUtil);

		if (coverageType.contains("Joint")) {

			String esignMethod1 = data.get("eSignature method_InsuredOne");
			String esignMethod2 = data.get("eSignature method_InsuredTwo");

			page.clickOnESignCheckBox(InsuredOneName);
			page.clickOnESignEmailCheckBox(InsuredTwoName);

			page.EnterEmailID(InsuredOneName, data.get("Email_InsuredOne"), esignMethod1);
			page.EnterEmailID(InsuredTwoName, data.get("Email_InsuredTwo"), esignMethod2);
			page.EnterAdvancePassword("A1234");
			page.EnterConfirmPassword("A1234");

			commonElements.clickNextBtn();

			// page.EnterAdvancePassword("A1234");
			// page.EnterConfirmPassword("A1234");

			Thread.sleep(6000);

			completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));
			// EN
			completePage.VerifyESignCompleteFromTitle(data.get("completeEsign_FormTitle_EN_One"), 0);

			commonElements.clickENFRToggle();
			// Fr
			completePage.VerifyESignCompleteFromTitle(data.get("completeEsign_FormTitle_FR_One"), 0);

			commonElements.clickENFRToggle();

			// EN
			completePage.VerifyESignCompleteFromTitle(data.get("completeEsign_FormTitle_EN_Two"), 1);

			commonElements.clickENFRToggle();
			// Fr
			completePage.VerifyESignCompleteFromTitle(data.get("completeEsign_FormTitle_FR_Two"), 1);

		} else {
			Assert.fail(
					"Fail: complete eSignatures page lable validation:We are  expecting coverage type as Joint but found as  ( "
							+ coverageType + "). Please change the coverage type in test data ");
		}

	}

	@Test(dataProvider = "eSignTestData", enabled = true, priority = 7, testName = "Verifying with the different combinations for two insureds", groups = "webBrowser")
	public void verifyOwnersAsSignors(Map<String, String> data) throws Exception {
		ESignPage page = new ESignPage(driverUtil);
		
		//verifying with different combinations for two insureds as insured1= Yes, insured2= No
		//insured1= No, insured2= Yes
		//insured1= No, insured2= No
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillUntilEsignTestPage(data);
		String insuredOneOption = data.get("insuredInfo_IsOwnerInsured");
		String insredTwoOption = data.get("insuredInfo_IsOwnerInsured2");
		String insuredOneFirstName = data.get("insuredInfo_FirstName");
		String insredTwoFirstName = data.get("insuredInfo_FirstName2");
		String OwnerOneFirstName = data.get("ownerInfo_IndivOwnerFirstName");
		String OwnerTwoFirstName = data.get("owner2_FirstName");

		if (insuredOneFirstName.equalsIgnoreCase(insredTwoFirstName)
				|| insuredOneFirstName.equalsIgnoreCase(OwnerOneFirstName)
				|| insredTwoFirstName.equalsIgnoreCase(OwnerTwoFirstName)) {
			Assert.fail("Insured or owner name should be different");
		}

		String coverageType = data.get("policyInfo_CoverageType");
		CompleteEsignaturesPage completePage = new CompleteEsignaturesPage(driverUtil);

		int counter = 0;

		if (coverageType.contains("Joint")) {

			if (insuredOneOption.equalsIgnoreCase("No") && insredTwoOption.equalsIgnoreCase("No")) {
				// ------- Verify the ESign with client Now test Scenario with insured and
				// owners.------
				counter = SelectESignOptions(data, page, commonElements);

				completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));

				String accessCode = "";

				for (int i = 0; i < counter; i++) {
					System.out.println("counter  value is " + i);
					VerifyeSignDocPDF(completePage, accessCode);
					completePage.getFinishButton().click();
					Thread.sleep(13000);
				}
				ValidationOfEsign(completePage, name1, name2, name3, name4);

				// fill Advisor page
				if (advisorFlag) {
					ESignForAdvisor(completePage);
				}
				counter = 0;

			} else if ((insuredOneOption.equalsIgnoreCase("Yes") && insredTwoOption.equalsIgnoreCase("No"))
					|| insuredOneOption.equalsIgnoreCase("No") && insredTwoOption.equalsIgnoreCase("Yes")) {
				counter = SelectESignOptions(data, page, commonElements);

				System.out.println(name1 + "||" + name2 + "||" + name3 + "||" + name4);

				completePage.VerifyPageTitle(getDriver(), data.get("completeEsign_PageTitleEN"));

				String accessCode = "";

				for (int i = 0; i < counter; i++) {
					System.out.println("counter  value is " + i);
					VerifyeSignDocPDF(completePage, accessCode);
					completePage.getFinishButton().click();
					Thread.sleep(10000);
				}
				ValidationOfEsign(completePage, name1, name2, name3, name4);
				// fill Advisor page
				if (advisorFlag) {
					ESignForAdvisor(completePage);
				}

			}

		} else {
			Assert.fail(
					"Fail: complete eSignatures page lable validation:We are  expecting coverage type as Joint but found as  ( "
							+ coverageType + "). Please change the coverage type in test data ");
		}

	}

	public void VerifyeSignDocPDF(CompleteEsignaturesPage completePage, String accessCode) throws InterruptedException {
		completePage.clickOnESignNowButton(0);
		
		completePage.waitUntilElementDisplayed(completePage.getEsignCheckBox(getDriver()), getDriver());
		Thread.sleep(4000);
		completePage.getEsignCheckBox(getDriver()).sendKeys(Keys.SPACE);

		completePage.getContinuButton().click();

		for (int i = 0; i < 16; i++) {
			completePage.getNextButton().click();
			Thread.sleep(100);

			if (completePage.getNextButton().getText().equalsIgnoreCase("SIGN")) {
				Thread.sleep(6000);
				completePage.getSingnatureButton().click();
				completePage.ClickOnAdoptButton();

				break;
			}
		}
		completePage.getSignedAtCityOwner("waterloo");
		Thread.sleep(2000);

	}

	public void ESignForAdvisor(CompleteEsignaturesPage completePage) throws InterruptedException {
		if (!completePage.CheckEsignButtonIsDisabled(0)) {

			completePage.clickOnESignNowButton(0);
			Thread.sleep(15000);

			// completePage.getAccessCodeElement(getDriver()).sendKeys("31");
			// completePage.getValidateButton().click();

			completePage.getEsignCheckBox(getDriver()).sendKeys(Keys.SPACE);
			completePage.getContinuButton().click();
			for (int i = 0; i <= 17; i++) {
				completePage.getNextButton().click();
				Thread.sleep(500);
				if (completePage.getNextButton().getText().equalsIgnoreCase("SIGN")) {
					Thread.sleep(7000);
					completePage.getSingnatureButton().click();
					completePage.ClickOnAdoptButton();
					break;
				}
			}
			completePage.getFinishButton().click();
			Thread.sleep(10000);

		}
	}

	public int SelectESignOptions(Map<String, String> data, ESignPage page, CommonElements commonElements) {
		String insuredOneOption = data.get("insuredInfo_IsOwnerInsured");
		String insredTwoOption = data.get("insuredInfo_IsOwnerInsured2");

		System.out.println("Insured one option value is : --" + insuredOneOption);
		System.out.println("Insured two option value is : --" + insredTwoOption);

		System.out.println("-----------------------------------");

		String insuredOneFirstName = data.get("insuredInfo_FirstName");
		String insredTwoFirstName = data.get("insuredInfo_FirstName2");
		String OwnerOneFirstName = data.get("ownerInfo_IndivOwnerFirstName");
		String OwnerTwoFirstName = data.get("owner2_FirstName");
		String eSignatureMethodInsuredOne = data.get("eSignature method_InsuredOne");
		String eSignatureMethodInsuredTwo = data.get("eSignature method_InsuredTwo");
		String eSignatureMethodOwnerOne = data.get("eSignature method_OwnerOne");
		String eSignatureMethodOwneTwo = data.get("eSignature method_OwnerTwo");
		String insuredOneEmail = data.get("Email_InsuredOne");
		String insuredTwoEmail = data.get("Email_InsuredTwo");
		String ownerOneEmail = data.get("Email_OwnerOne");
		String ownerTwoEmail = data.get("Email_OwnerTwo");

		int counter = 0;
		if (eSignatureMethodInsuredOne.equalsIgnoreCase("eSign with client now")
				&& !eSignatureMethodInsuredOne.isEmpty()) {
			page.clickOnESignCheckBox(insuredOneFirstName);

			name1 = insuredOneFirstName;

			counter++;
		} else if (eSignatureMethodInsuredOne.equalsIgnoreCase("eSign by email later")
				&& !eSignatureMethodInsuredOne.isEmpty()) {
			page.clickOnESignEmailCheckBox(insuredOneFirstName);
			advisorFlag = false;
		}

		page.EnterEmailID(insuredOneFirstName, insuredOneEmail, eSignatureMethodInsuredOne);

		if (eSignatureMethodInsuredTwo.equalsIgnoreCase("eSign with client now")
				&& !eSignatureMethodInsuredTwo.isEmpty()) {
			page.clickOnESignCheckBox(insredTwoFirstName);
			name2 = insredTwoFirstName;

			counter++;
		} else if (eSignatureMethodInsuredTwo.equalsIgnoreCase("eSign by email later")
				&& !eSignatureMethodInsuredTwo.isEmpty()) {
			page.clickOnESignEmailCheckBox(insredTwoFirstName);
			advisorFlag = false;
		}

		page.EnterEmailID(insredTwoFirstName, insuredTwoEmail, eSignatureMethodInsuredTwo);

		if (insuredOneOption.equalsIgnoreCase("Yes") && insredTwoOption.equalsIgnoreCase("No")) {
			if (eSignatureMethodOwneTwo.equalsIgnoreCase("eSign with client now")
					&& !eSignatureMethodOwneTwo.isEmpty()) {
				page.clickOnESignCheckBox(OwnerTwoFirstName);
				name4 = OwnerTwoFirstName;
				counter++;
			} else if (eSignatureMethodOwneTwo.equalsIgnoreCase("eSign by email later")
					&& !eSignatureMethodOwneTwo.isEmpty()) {
				page.clickOnESignEmailCheckBox(OwnerTwoFirstName);
				advisorFlag = false;
			}

			page.EnterEmailID(OwnerTwoFirstName, ownerTwoEmail, eSignatureMethodOwneTwo);
		} else if (insuredOneOption.equalsIgnoreCase("No") && insredTwoOption.equalsIgnoreCase("Yes")) {
			if (eSignatureMethodOwneTwo.equalsIgnoreCase("eSign with client now")
					&& !eSignatureMethodOwneTwo.isEmpty()) {
				page.clickOnESignCheckBox(OwnerTwoFirstName);
				name4 = OwnerTwoFirstName;
				counter++;
			} else if (eSignatureMethodOwneTwo.equalsIgnoreCase("eSign by email later")
					&& !eSignatureMethodOwneTwo.isEmpty()) {
				page.clickOnESignEmailCheckBox(OwnerTwoFirstName);
				advisorFlag = false;
			}

			page.EnterEmailID(OwnerTwoFirstName, ownerTwoEmail, eSignatureMethodOwneTwo);
		}

		else if (insuredOneOption.equalsIgnoreCase("No") && insredTwoOption.equalsIgnoreCase("No")) {

			if (eSignatureMethodOwnerOne.equalsIgnoreCase("eSign with client now")
					&& !eSignatureMethodOwnerOne.isEmpty()) {
				page.clickOnESignCheckBox(OwnerOneFirstName);
				name3 = OwnerOneFirstName;

				counter++;
			} else if (eSignatureMethodOwnerOne.equalsIgnoreCase("eSign by email later")
					&& !eSignatureMethodOwnerOne.isEmpty()) {
				page.clickOnESignEmailCheckBox(OwnerOneFirstName);
				advisorFlag = false;
			}

			page.EnterEmailID(OwnerOneFirstName, ownerOneEmail, eSignatureMethodOwnerOne);

			if (eSignatureMethodOwneTwo.equalsIgnoreCase("eSign with client now")
					&& !eSignatureMethodOwneTwo.isEmpty()) {
				page.clickOnESignCheckBox(OwnerTwoFirstName);
				name4 = OwnerTwoFirstName;
				counter++;
			} else if (eSignatureMethodOwneTwo.equalsIgnoreCase("eSign by email later")
					&& !eSignatureMethodOwneTwo.isEmpty()) {
				page.clickOnESignEmailCheckBox(OwnerTwoFirstName);
				advisorFlag = false;
			}

			page.EnterEmailID(OwnerTwoFirstName, ownerTwoEmail, eSignatureMethodOwneTwo);
		}

		if (eSignatureMethodInsuredOne.equalsIgnoreCase("eSign by email later")
				|| eSignatureMethodInsuredTwo.equalsIgnoreCase("eSign by email later")
				|| eSignatureMethodOwnerOne.equalsIgnoreCase("eSign by email later")
				|| eSignatureMethodOwneTwo.equalsIgnoreCase("eSign by email later")) {

			page.EnterAdvancePassword("A1234");
			page.EnterConfirmPassword("A1234");
		}

		System.out.println("Names " + name1 + "||" + name2 + "||" + name3 + "||" + name4);
		commonElements.clickNextBtn();
		return counter;

	}

	public void ValidationOfEsign(CompleteEsignaturesPage completePage, String insuredOne, String insuredTwo,
			String ownereOne, String ownerTwo) {

		if (!insuredOne.isEmpty()) {
			Assert.assertTrue("Fail: User (" + insuredOne + ") unable to eSigned",
					completePage.getEsignedLabel(insuredOne).getText().equalsIgnoreCase("eSigned"));
		}
		if (!insuredTwo.isEmpty()) {
			Assert.assertTrue("Fail: User (" + insuredOne + ") unable to eSigned",
					completePage.getEsignedLabel(insuredOne).getText().equalsIgnoreCase("eSigned"));
		}
		if (!ownereOne.isEmpty()) {
			Assert.assertTrue("Fail: User (" + ownereOne + ") unable to eSigned",
					completePage.getEsignedLabel(ownereOne).getText().equalsIgnoreCase("eSigned"));
		}
		if (!ownerTwo.isEmpty()) {
			Assert.assertTrue("Fail: User (" + ownerTwo + ") unable to eSigned",
					completePage.getEsignedLabel(ownerTwo).getText().equalsIgnoreCase("eSigned"));
		}
		name1 = "";
		name2 = "";
		name3 = "";
		name4 = "";

	}
}