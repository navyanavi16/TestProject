package term_conversion;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

import junit.framework.Assert;

public class AdvisorInformationTest extends BaseTest {

	public void fillPageAdvisorInformationPage(Map<String, String> data) throws InterruptedException {
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
		
		

	}
	
	

	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en", "policyInfoUrl");
	}

	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");

	@DataProvider(name = "AdvisorInformationTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception {
		// Getting the Data Row against the Test Case name and store it within an array
		Object[][] testObjArray = excelUtil.getAllMatchingTestCases("AdvisorInformationTestData", method.getName());
		return (testObjArray);
	}
	
		
	
	@Test(dataProvider = "AdvisorInformationTestData",priority=1, testName = "Verify Advisor 1-Details ", groups = "webBrowser")
	public void VerifyAdvisorOneDetails(Map<String, String> data) throws Exception {
		AdvisorInformationPage advisorPage = new AdvisorInformationPage(driverUtil);
		
		fillPageAdvisorInformationPage(data);

		// ------------- Capture all the Test Data---------
		String advisorCode = data.get("Advisor_Code");
		String advisorName = data.get("Advisor_Name");
		String branchName = data.get("Branch_Name");
		String branchCode = data.get("Branch_Code");
		String brancEmail = data.get("Email");
		String branchPhoneNumber = data.get("Phone_Number");
		String branchExt = data.get("Ext");
		String branchCellPhoneNumber = data.get("Cell_Phone");
		String DidyoucompletedButtonValue = data.get("Didyoucomplete_YesorNo");
		String HowTheApplicationWasCompletedInputBoxValue = data.get("HowTheApplicationWasCompleted");
		String DidyoucompletedButtonValueInputBoxValue = data.get("Instructions_WhenWeReviewTheApplication");
		// ------- End ---------------------
		// Select Advisor Code and Verify the details.

//		advisorPage.selectAdvisorCode(0, advisorCode);
	String actAdvisorName = advisorPage.getAdvisorName(0).getText();

		Assert.assertTrue("Fail:We are unable to find expected Advisor name (" + advisorName + ") as actual ("
			+ actAdvisorName + ") for advisor code (" + advisorCode + ")",
			actAdvisorName.equalsIgnoreCase(advisorName));

		// Verify the Branch Name
//		String actBranchName = advisorPage.getAdvisorBranchName(0).getText();
//		System.out.println(actBranchName);

//		Assert.assertTrue(
//				"Fail:We are unable to find expected Advisor branch name (" + branchName + ") as actual ("
//						+ actBranchName + ") for advisor code (" + advisorCode + ")",
//				actBranchName.equalsIgnoreCase(branchName));

		 //verify the branch code
		String actBranchCode = advisorPage.getAdvisorBranchCode(0).getText();

	Assert.assertTrue("Fail:We are unable to find expected Advisor branch code (" + branchCode + ") as actual ("+ actBranchCode + ") ",actBranchCode.equalsIgnoreCase(branchCode));

		String actBranchEmail = advisorPage.getAdvisorEmail(0).getText();

		Assert.assertTrue("Fail:We are unable to find expected Advisor email (" + brancEmail + ") as actual ("+ actBranchEmail + ")",actBranchEmail.equalsIgnoreCase(brancEmail));

		String actBranchPhoneNumber = advisorPage.getAdvisorPhoneNumber(0).getAttribute("value");

		Assert.assertTrue(
				"Fail:We are unable to find expected Advisor branch PhoneNumber (" + branchPhoneNumber + ") as actual ("
						+ actBranchPhoneNumber + ")",
				actBranchPhoneNumber.equalsIgnoreCase(branchPhoneNumber));

	//	String actBranchExt = advisorPage.getAdvisorExtensionNumber(0).getAttribute("value");

		//Assert.assertTrue("Fail:We are unable to find expected Advisor branch extension number (" + branchExt + ") as actual ("+ actBranchExt + ")",actBranchExt.equalsIgnoreCase(branchExt));

		//String actBranchCellPhoneNumber = advisorPage.getAdvisorCellPhoneNumber(0).getAttribute("value");

		//Assert.assertTrue("Fail:We are unable to find expected Advisor branch extension number (" + branchCellPhoneNumber+ ") as actual (" + actBranchCellPhoneNumber + ") for advisor code (" + advisorCode + ")actBranchCellPhoneNumber.equalsIgnoreCase(branchCellPhoneNumber));
		
		//------------ editing the phone number, cell phone and Ext for advisor 1-------------------
				
		String editedPhoneNumber = "7007070700";
				
				advisorPage.getAdvisorPhoneNumber(0).sendKeys(Keys.BACK_SPACE);
				
				
				advisorPage.ClearTextBox(getDriver(),advisorPage.getAdvisorPhoneNumber(0));
				
				
				advisorPage.getAdvisorPhoneNumber(0).sendKeys("700 707 0700");
			     String Expected="700 707 0700";
				actBranchPhoneNumber = advisorPage.getAdvisorPhoneNumber(0).getAttribute("value");

				Assert.assertTrue(
						"Fail:We are unable to find expected Advisor1 edited PhoneNumber (" + editedPhoneNumber
								+ ") as actual (" + actBranchPhoneNumber + ") for advisor code (" + advisorCode + ")",
						actBranchPhoneNumber.equalsIgnoreCase(Expected));

				String editedExt = "087";
				advisorPage.ClearTextBox(getDriver(),advisorPage.getAdvisorExtensionNumber(0));
				
				advisorPage.getAdvisorExtensionNumber(0).sendKeys(editedExt);

			//	actBranchExt = advisorPage.getAdvisorExtensionNumber(0).getAttribute("value");

			//	Assert.assertTrue("Fail:We are unable to find expected Advisor1 edited extension number (" + editedExt + ") as actual ("+ actBranchExt + ") for advisor code (" + advisorCode + ")", actBranchExt.equalsIgnoreCase(editedExt));

				String editedCellPhoneNumber = "9687878765";			
				String expectedCellNumber ="968 787 8765";
				//advisorPage.ClearTextBox(getDriver(),advisorPage.getAdvisorCellPhoneNumber(0));
				advisorPage.getAdvisorCellPhoneNumber(0).sendKeys(editedCellPhoneNumber);

				//actBranchCellPhoneNumber = advisorPage.getAdvisorCellPhoneNumber(0).getAttribute("value");

			//	Assert.assertTrue("Fail:We are unable to find expected Advisor1 edited extension number (" + expectedCellNumber+ ") as actual (" + actBranchCellPhoneNumber + ") for advisor code (" + advisorCode + ")",actBranchCellPhoneNumber.equalsIgnoreCase(expectedCellNumber));

		

		if (!DidyoucompletedButtonValue.equalsIgnoreCase("Yes")) {
			advisorPage.getDidYouButton("No").click();
			advisorPage.getMeAboutApplicationTextArea().sendKeys(HowTheApplicationWasCompletedInputBoxValue);

		} else {
			advisorPage.getDidYouButton("Yes").click();
		}
		
		advisorPage.getMeAboutYourSelf().sendKeys(DidyoucompletedButtonValueInputBoxValue);
		

				advisorPage.elockSignButton().click();

	}

	@Test(dataProvider = "AdvisorInformationTestData", priority=2,testName = "Verify Advisor 1-Label validation ", groups = "webBrowser")
	public void verifyAdvisorOneLabelValidation(Map<String, String> data) throws InterruptedException  {

		AdvisorInformationPage advisorPage = new AdvisorInformationPage(driverUtil);
		
		fillPageAdvisorInformationPage(data);
		// ------------- Capture all the Test Data---------
		String advisorCodeLabel = "Advisor code";
		String advisorNameLabel = "Advisor name";
		String branchNameLabel = "Branch name";
		String branchCodeLabel = "Branch code";
		String brancEmailLabel = "Email";
		String branchPhoneNumberLabel = "Phone number";
		String branchExtLabel = "Ext (optional)";
		String cellPhoneLabel = "Cell phone (optional)";
		String DidyoucompletedButtonValueLabel = "Did you complete this application with the person(s) to be insured and the policy owner(s) in person?";
		String HowTheApplicationWasCompletedInputBoxValueLabel = "Tell us about how the application was completed and who completed it.";

		String DidyoucompletedButtonValueInputBoxValueLabel = "Tell us about anything that might be helpful when we review the application. Include special policy date or other requests (optional).Tell us about anything that might be helpful when we review the application. Include special policy date or other requests (optional).";

		//advisorPage.selectAdvisorCode(0, data.get("Advisor_Code"));
		
		String actBranchCode = advisorPage.getAdvisorBracnhCodeLabel(0).getText();

		Assert.assertTrue("Fail:We did not find the label(" + branchCodeLabel + " ) and found that (" + actBranchCode + ")",
				actBranchCode.equalsIgnoreCase(branchCodeLabel));

		String actBranchName = advisorPage.getAdvisorBranchNameLabel(0).getText();

		Assert.assertTrue(
				"Fail:We did not find the label(" + branchNameLabel + " ) and found that (" + actBranchName + ")",
				actBranchName.equalsIgnoreCase(branchNameLabel));

		String actAdvisorName = advisorPage.getAdvisorNameLabel(0).getText();

		Assert.assertTrue(
				"Fail:We did not find the label(" + advisorNameLabel + " ) and found that (" + actAdvisorName + ")",
				actAdvisorName.equalsIgnoreCase(advisorNameLabel));

		String actAdvisorCode = advisorPage.getAdvisorCodeLabel(0).getText();

		Assert.assertTrue(
				"Fail:We did not find the label(" + advisorCodeLabel + " ) and found that (" + actAdvisorCode + ")",
				actAdvisorCode.equalsIgnoreCase(advisorCodeLabel));

		String actAdvisorEmail = advisorPage.getAdvisorBranchEmailLabel(0).getText();

		Assert.assertTrue(
				"Fail:We did not find the label(" + brancEmailLabel + " ) and found that (" + actAdvisorEmail + ")",
				actAdvisorEmail.equalsIgnoreCase(brancEmailLabel));

		String actAdvisorPhoneNumber = advisorPage.getAdvisorPhoneNumberLabel(0).getText();

		Assert.assertTrue("Fail:We did not find the label(" + branchPhoneNumberLabel + " ) and found that ("
				+ actAdvisorPhoneNumber + ")", actAdvisorPhoneNumber.equalsIgnoreCase(branchPhoneNumberLabel));

		String actAdvisorExt = advisorPage.getAdvisorExtNumberLabel(0).getText();

		Assert.assertTrue(
				"Fail:We did not find the label(" + branchExtLabel + " ) and found that (" + actAdvisorExt + ")",
				actAdvisorExt.equalsIgnoreCase(branchExtLabel));

		String actAdvisorCellPhone = advisorPage.getAdvisorCellPhoneNumberLabel(0).getText();

		Assert.assertTrue(
				"Fail:We did not find the label(" + cellPhoneLabel + " ) and found that (" + actAdvisorCellPhone + ")",
				actAdvisorCellPhone.equalsIgnoreCase(cellPhoneLabel));
		
		System.out.println("Advisor 1 label validation is passed");

	}
	
	@Test(dataProvider = "AdvisorInformationTestData", priority=3,testName = "Verify ability to add advisor ", groups = "webBrowser")
	public void verifyAddingAdvisorTwo(Map<String, String> data) throws Exception {
		
		// Pre Steps
		AdvisorInformationPage advisorPage = new AdvisorInformationPage(driverUtil);
		
		fillPageAdvisorInformationPage(data);
		// Click on Add Advisor Button 
	//	advisorPage.selectAdvisorCode(0, data.get("Advisor_Code"));
		advisorPage.getSecondAdvisorButton().click();
	
		// ------------- Capture all the Test Data---------
				String advisorCode = data.get("Advisor_Code1");
				String name[]=data.get("Advisor_Name").split(Pattern.quote("|"));;
				String advisorFirstName = name[0];
				String advisorMiddleName = name[1];
				String advisorLastName =name[2];
				String branchCode = data.get("Branch_Code");
				String brancEmail = data.get("Email");
				String branchPhoneNumber = data.get("Phone_Number");
				String branchExt = data.get("Ext");
				String cellPhone =  data.get("Cell_Phone");
				String DidyoucompletedButtonValue = data.get("Didyoucomplete_YesorNo");
				String HowTheApplicationWasCompletedInputBoxValue = data.get("HowTheApplicationWasCompleted");
				String DidyoucompletedButtonValueInputBoxValue = data.get("Instructions_WhenWeReviewTheApplication");
				
				// Enter the Advisor 2 details like branch code, Advisor name, advisor code etc.
				advisorPage.advisor2CodeInputboxXpath(1).sendKeys(advisorCode);
				advisorPage.getAdvisor2FirstNameInputbox(1).sendKeys(advisorFirstName);
				advisorPage.getAvisor2MiddleNameInputbox(1).sendKeys(advisorMiddleName);
				advisorPage.getAdvisor2LastNameInputbox(1).sendKeys(advisorLastName);
				advisorPage.getAdvisor2BranchCodeInputbox(1).sendKeys(branchCode);
				advisorPage.getAdvisor2BranchEmailInput(1).sendKeys(brancEmail);
				advisorPage.getAdvisorPhoneNumber(1).sendKeys(branchPhoneNumber);
				advisorPage.getAdvisorExtensionNumber(1).sendKeys(branchExt);
				advisorPage.getAdvisorCellPhoneNumber(1).sendKeys(cellPhone);
				
				
				// Verify the Commission Share % of Advisor 2									
			
				String advisorOneSharePercentage = data.get("Advisor_AdvisorOneShare");
				String advisorTwoSharePercentage = data.get("Advisor_AdvisorTwoShare");
				// Pass the Row and Column number to get element 
				advisorPage.ClearTextBox(getDriver(),advisorPage.EnterTheValueinTableCell(0, 2));
				advisorPage.EnterTheValueinTableCell(0, 2).sendKeys(advisorOneSharePercentage);
				advisorPage.ClearTextBox(getDriver(),advisorPage.EnterTheValueinTableCell(1, 2));
				
				advisorPage.EnterTheValueinTableCell(1, 2).sendKeys(advisorTwoSharePercentage);
				
				Assert.assertTrue("Fail:we are expecting Percentage as (100%) but found as a ("+advisorPage.gettotalShareXpathErrorLabel().getText()+")", advisorPage.gettotalShareXpathErrorLabel().getText().contains("100"));
				
				Thread.sleep(3000);
				
				if (!DidyoucompletedButtonValue.equalsIgnoreCase("Yes")) {
					advisorPage.getDidYouButton("No").click();
					advisorPage.getMeAboutApplicationTextArea().sendKeys(HowTheApplicationWasCompletedInputBoxValue);

				} else {
					advisorPage.getDidYouButton("Yes").click();
				}
				Thread.sleep(3000);
				advisorPage.getMeAboutYourSelf().sendKeys(DidyoucompletedButtonValueInputBoxValue);
				Thread.sleep(3000);
				advisorPage.elockSignButton().click();

				
		
	}
	
	@Test(dataProvider = "AdvisorInformationTestData", priority=3,testName = "Validation on Sharing percentage of Advisors ", groups = "webBrowser")
	public void validateSharingPercentage(Map<String, String> data) throws Exception {
		// Pre Steps
				AdvisorInformationPage advisorPage = new AdvisorInformationPage(driverUtil);
				CommonElements common= new CommonElements(driverUtil);
				
				fillPageAdvisorInformationPage(data);
			//	advisorPage.selectAdvisorCode(0, data.get("Advisor_Code"));
				// Click on Add Advisor Button 
				advisorPage.getSecondAdvisorButton().click();
			
				// ------------- Capture all the Test Data---------
						String advisorCode = data.get("Advisor_Code1");
						String name[]=data.get("Advisor_Name").split(Pattern.quote("|"));;
						String advisorFirstName = name[0];
						String advisorMiddleName = name[1];
						String advisorLastName =name[2];
						String branchCode = data.get("Branch_Code");
						String brancEmail = data.get("Email");
						String branchPhoneNumber = data.get("Phone_Number");
						String branchExt = data.get("Ext");
						String cellPhone =  data.get("Cell_Phone");
						String DidyoucompletedButtonValue = data.get("Didyoucomplete_YesorNo");
						String HowTheApplicationWasCompletedInputBoxValue = data.get("HowTheApplicationWasCompleted");
						String DidyoucompletedButtonValueInputBoxValue = data.get("Instructions_WhenWeReviewTheApplication");
						
						// Enter the Advisor 2 details like branch code, Advisor name, advisor code etc.
						advisorPage.advisor2CodeInputboxXpath(1).sendKeys(advisorCode);
						advisorPage.getAdvisor2FirstNameInputbox(1).sendKeys(advisorFirstName);
						advisorPage.getAvisor2MiddleNameInputbox(1).sendKeys(advisorMiddleName);
						advisorPage.getAdvisor2LastNameInputbox(1).sendKeys(advisorLastName);
						advisorPage.getAdvisor2BranchCodeInputbox(1).sendKeys(branchCode);
						advisorPage.getAdvisor2BranchEmailInput(1).sendKeys(brancEmail);
						advisorPage.getAdvisorPhoneNumber(1).sendKeys(branchPhoneNumber);
						advisorPage.getAdvisorExtensionNumber(1).sendKeys(branchExt);
						advisorPage.getAdvisorCellPhoneNumber(1).sendKeys(cellPhone);
						
						
						// Verify the Commission Share % of Advisor 2									
					
						// check if First Advisor percentage 70 and second one 40 %
						String advisorOneSharePercentage ="70";
						String advisorTwoSharePercentage = "40";
						// Pass the Row and Column number to get element 
						advisorPage.ClearTextBox(getDriver(),advisorPage.EnterTheValueinTableCell(0, 2));
						advisorPage.EnterTheValueinTableCell(0, 2).sendKeys(advisorOneSharePercentage);
						advisorPage.ClearTextBox(getDriver(),advisorPage.EnterTheValueinTableCell(1, 2));
						
						advisorPage.EnterTheValueinTableCell(1, 2).sendKeys(advisorTwoSharePercentage);
						advisorPage.elockSignButton().click();
						
						
						Assert.assertTrue("Fail:Percentage is not correct("+advisorPage.gettotalShareXpathErrorLabel().getText()+")", advisorPage.gettotalShareXpathErrorLabel().getText().contains("110"));
						
						String finalErrorValue="Total share must be 100%";
						Assert.assertTrue("Fail: Verifying the Total share must be 100% error message when the %share of advisor1 and advisor2 are not equals to 100% ",advisorPage.gettotalErrorLable().getText().equalsIgnoreCase(finalErrorValue));
						 
						// check in FR Language
						
						common.clickENFRToggle();
						Thread.sleep(2000);
						finalErrorValue="Part totale doit être de 100%";
						Assert.assertTrue("Fail: Verifying in French Part totale doit être de 100% error message when the %share of advisor1 and advisor2 are not equals to 100% ",advisorPage.gettotalErrorLable().getText().equalsIgnoreCase(finalErrorValue));
						common.clickENFRToggle();
						Thread.sleep(1000);
						
						
						advisorOneSharePercentage ="40";
						advisorTwoSharePercentage = "70";
						// Pass the Row and Column number to get element 
						advisorPage.ClearTextBox(getDriver(),advisorPage.EnterTheValueinTableCell(0, 2));
						advisorPage.EnterTheValueinTableCell(0, 2).sendKeys(advisorOneSharePercentage);
						advisorPage.ClearTextBox(getDriver(),advisorPage.EnterTheValueinTableCell(1, 2));
						
						advisorPage.EnterTheValueinTableCell(1, 2).sendKeys(advisorTwoSharePercentage);
						
						advisorPage.elockSignButton().click();
						Assert.assertTrue("Fail:Percentage is not correct("+advisorPage.gettotalShareXpathErrorLabel().getText()+")", advisorPage.gettotalShareXpathErrorLabel().getText().contains("110"));
						
						
						finalErrorValue="Total share must be 100%";
						Assert.assertTrue("Fail:Verifying the Total share must be 100% error message when the %share of advisor1 and advisor2 are not equals to 100%",advisorPage.gettotalErrorLable().getText().equalsIgnoreCase(finalErrorValue));
						// check in FR Language						
						common.clickENFRToggle();
						Thread.sleep(1000);
						finalErrorValue="Part totale doit être de 100%";
						Assert.assertTrue("Fail: Verifying in French Part totale doit être de 100% error message when the %share of advisor1 and advisor2 are not equals to 100%",advisorPage.gettotalErrorLable().getText().equalsIgnoreCase(finalErrorValue));
						common.clickENFRToggle();
						
						// if not entering any %share values for advisor1 and advisor2 then it throws required error msgs
						advisorPage.ClearTextBox(getDriver(),advisorPage.EnterTheValueinTableCell(0, 2));
						advisorPage.EnterTheValueinTableCell(0, 2).sendKeys("0");
						
						advisorPage.ClearTextBox(getDriver(),advisorPage.EnterTheValueinTableCell(1, 2));
						
						advisorPage.EnterTheValueinTableCell(1, 2).sendKeys("0");
						
						
						advisorPage.elockSignButton().click();
						Assert.assertTrue("Fail: Required error message is not displayed for the blank %share field for advisor1",advisorPage.getadvisor1PercentageShareError().getText().equalsIgnoreCase("Required"));
						Assert.assertTrue("Fail:Required error message is not displayed for the blank %share field for advisor2",advisorPage.getadvisor2PercentageShareError().getText().equalsIgnoreCase("Required"));
						
						//checking in french
						Thread.sleep(3000);
						common.clickENFRToggle();
						Assert.assertTrue("Fail: Obligatoire error message is not displayed for the blank %share field for advisor1",advisorPage.getadvisor1PercentageShareError().getText().equalsIgnoreCase("Obligatoire"));
						Assert.assertTrue("Fail: Obligatoire error message is not displayed for the blank %share field for advisor2",advisorPage.getadvisor2PercentageShareError().getText().equalsIgnoreCase("Obligatoire"));
						
						Thread.sleep(3000);
						common.clickENFRToggle();
						
						
						
						
						if (!DidyoucompletedButtonValue.equalsIgnoreCase("Yes")) {
							advisorPage.getDidYouButton("No").click();
							advisorPage.getMeAboutApplicationTextArea().sendKeys(HowTheApplicationWasCompletedInputBoxValue);

						} else {
							advisorPage.getDidYouButton("Yes").click();
						}
						Thread.sleep(3000);
						advisorPage.getMeAboutYourSelf().sendKeys(DidyoucompletedButtonValueInputBoxValue);
						Thread.sleep(3000);
						advisorPage.elockSignButton().click();
	}
	
	@Test(dataProvider = "AdvisorInformationTestData",priority=4, testName = "Verify ability to remove advisor 2 ", groups = "webBrowser")
	public void verifyRemoveAdvisorTwo(Map<String, String> data) throws Exception {
		//pre Steps
		AdvisorInformationPage advisorPage = new AdvisorInformationPage(driverUtil);
		
		fillPageAdvisorInformationPage(data);
		// Click on Add Advisor Button 
		advisorPage.getSecondAdvisorButton().click();		
		Assert.assertTrue("Fail:We are not able to see Branch code text box", advisorPage.getAdvisor2BranchCodeInputbox(1).isDisplayed());
		
		Thread.sleep(2000);
		
		advisorPage.getRemoveAdvisorLink().click();		
		Assert.assertTrue("Fail: We are not able to see add button of advisor 2", advisorPage.getSecondAdvisorButton().isDisplayed());
		
		Thread.sleep(2000);
		
	}	
	
	
	@Test(dataProvider = "AdvisorInformationTestData", testName = "Verify Error-handling mandatory fields", groups = "webBrowser")
	public void verifyErrorValidation(Map<String, String> data) throws Exception  {
		
				
		AdvisorInformationPage advisorPage = new AdvisorInformationPage(driverUtil);
		
		fillPageAdvisorInformationPage(data);
		Thread.sleep(5000);
		advisorPage.elockSignButton().click();
		Assert.assertTrue("Fail:Required error message is not showing for the field Did you complete this application with the person(s) to be insured and the policy owner(s) in person?", advisorPage.getDidYouCompleteAppInPersonYesOrNoErrorLabel().getText().equalsIgnoreCase("Required"));
		advisorPage.getSecondAdvisorButton().click();
		advisorPage.elockSignButton().click();
		
		//Assert.assertTrue("Fail:Required error message is not showing for the field Phone number for Advisor 1", advisorPage.getAdivsor1PhoneNumberErrorLabel(0).getText().equalsIgnoreCase("Required"));
		Assert.assertTrue("Fail:Required error message is not showing for the field Phone number for Advisor 2", advisorPage.getAdivsor1PhoneNumberErrorLabel(1).getText().equalsIgnoreCase("Required"));
		Assert.assertTrue("Fail:Required error message is not showing for the field Advisor code for Advisor 2", advisorPage.getAdvisorCodeForAdvisor1ErrorLabel(1).getText().equalsIgnoreCase("Required"));
		Assert.assertTrue("Fail:Required error message is not showing for the field Advisor name(first name) for Advisor 2", advisorPage.getAdvisor2FirstNameError().getText().equalsIgnoreCase("Required"));
		Assert.assertTrue("Fail:Required error message is not showing for the field Advisor name(last name) for Advisor 2", advisorPage.getAdvisor2LastNameErrorLabel(1).getText().equalsIgnoreCase("Required"));
		Assert.assertTrue("Fail:Required error message is not showing for the field branch code for Advisor 2", advisorPage.getAdvisor2BranchCodeErrorLabel().getText().equalsIgnoreCase("Required"));
		Assert.assertTrue("Fail:Required error message is not showing for the field Email for Advisor 2", advisorPage.getAdvisor2EmailErrorLabel().getText().equalsIgnoreCase("Required"));
	   advisorPage.getDidYouButton("No").click();
		advisorPage.elockSignButton().click();
		Assert.assertTrue("Fail:Required error message is not showing for the field Tell us about how the application was completed and who completed it.", advisorPage.getHowTheAppWasCompletedErrorLabel().getText().equalsIgnoreCase("Required"));
		//negative testing for email field for Advisor 2
	    advisorPage.getAdvisor2BranchEmailInput(1).sendKeys("sgmail.com");
		advisorPage.elockSignButton().click();		
		Assert.assertTrue("Fail:Invalid email format error message is not showing for the field Email for Advisor 2", advisorPage.getAdvisor2EmailErrorLabel().getText().equalsIgnoreCase("Invalid email format"));

		
		}
}
