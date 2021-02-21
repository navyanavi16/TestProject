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

public class AttachmentsTest extends BaseTest {
	
	public void fillUntilAttachmentsTestPage(Map<String, String> data) throws InterruptedException {
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
		fillPage.fillBillingandpaymentPage(data);
		

	}
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en", "policyInfoUrl");
	}

	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");

	@DataProvider(name = "AttachmentsTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception {
		// Getting the Data Row against the Test Case name and store it within an array
		Object[][] testObjArray = excelUtil.getAllMatchingTestCases("AttachmentsTestData", method.getName());
		return (testObjArray);
	}
	
	

	@Test(dataProvider = "AttachmentsTestData", priority = 0, testName = "Label Validation in English and in french ", groups = "webBrowser")
	public void LabelValidationInEnglishAndInFrench(Map<String, String> data) throws Exception {
		AttachmentPage page = new AttachmentPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillUntilAttachmentsTestPage(data);
		
		//verifying page title
		page.VerifyPageTitle(data.get("Attachments_TitleEN"));
		commonElements.clickENFRToggle();
		page.VerifyPageTitle(data.get("Attachments_TitleFR"));
		commonElements.clickENFRToggle();
		
		String buttonText= page.getAttachDocButton().getText();
		String expectedButonText= data.get("AttachDoc_Label_EN");
		Assert.assertTrue("Fail in English: We are expecting attach document button text as ("+expectedButonText+") but found as ("+buttonText+")", buttonText.equalsIgnoreCase(expectedButonText));
		
		page.clickOnAttachDocumenButton();
		page.selectDropDown("Beneficiary designation for life insurance form, NN0283");
		page.UploadAttachedImage("logo.jpg");
		String expectedColumnHeaderOne=data.get("Table_HeaderOne_EN");
		String expectedColumnHeaderTwo=data.get("Table_HeaderTwo_EN");
		String actColumnHeaderOne=page.getTableHeader(2);	
		
		Assert.assertTrue("Fail in English:We are expecting column 1 header as("+expectedColumnHeaderOne+") but found as ("+actColumnHeaderOne+")", actColumnHeaderOne.equalsIgnoreCase(expectedColumnHeaderOne.trim()));
		String actColumnHeaderTwo=page.getTableHeader(4);
		
		Assert.assertTrue("Fail in English:We are expecting column 2 header as("+expectedColumnHeaderTwo+") but found as ("+actColumnHeaderTwo+")", actColumnHeaderTwo.equalsIgnoreCase(expectedColumnHeaderTwo.trim()));
		
		//FR 
		commonElements.clickENFRToggle();		
		
		buttonText= page.getAttachDocButton().getText();
		expectedButonText= data.get("AttachDoc_Label_FR");
		Assert.assertTrue("Fail in French: We are expecting attahced document button text as ("+expectedButonText+") but found as ("+buttonText+")", buttonText.equalsIgnoreCase(expectedButonText));
		
		expectedColumnHeaderOne=data.get("Table_HeaderOne_FR");
		expectedColumnHeaderTwo=data.get("Table_HeaderTwo_FR");
		actColumnHeaderOne=page.getTableHeader(2);
		Assert.assertTrue("Fail in French: We are expecting column 1 header as("+expectedColumnHeaderOne+") but found as ("+actColumnHeaderOne+")", actColumnHeaderOne.equalsIgnoreCase(expectedColumnHeaderOne.trim()));
		actColumnHeaderTwo=page.getTableHeader(4);
		Assert.assertTrue("Fail in French:We are expecting column 2 header as("+expectedColumnHeaderTwo+") but found as ("+actColumnHeaderTwo+")", actColumnHeaderTwo.equalsIgnoreCase(expectedColumnHeaderTwo.trim()));
		
		commonElements.clickENFRToggle();		
	}
	
	@Test(dataProvider = "AttachmentsTestData", priority = 1, testName = "error Validation in English and in french ", groups = "webBrowser")
	public void verifyErrorValidationInEnglishInFrench(Map<String, String> data) throws Exception {
		AttachmentPage page = new AttachmentPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillUntilAttachmentsTestPage(data);
		commonElements.clickNextBtn();
		
		
		Assert.assertTrue("Fail: Required error message is not displayed for the Attach Doc ESing",page.getAttachDocError().getText().equalsIgnoreCase("Product illustration is required"));
		
		commonElements.clickENFRToggle();
		Assert.assertTrue("Fail: Required error message is not displayed for the Attach Doc ESing",page.getAttachDocError().getText().equalsIgnoreCase("Le projet informatisé est obligatoire"));
		commonElements.clickENFRToggle();
		page.clickOnAttachDocumenButton();
		page.ClickOnAttachButton();
		
		Assert.assertTrue("Fail: Required error message is not displayed for the Attach Doc ESing",page.getDropDownError().getText().equalsIgnoreCase("Required"));
		Assert.assertTrue("Fail: Attach a file above error message is not displayed in english",page.getattachmentError().getText().equalsIgnoreCase("Attach a file above"));
		page.clickOnCloseButton();
		commonElements.clickENFRToggle();
		page.clickOnAttachDocumenButton();
		page.clickOnAttachBtnFrench();
		Assert.assertTrue("Fail: Required error message is not displayed for the Attach Doc ESing",page.getDropDownError().getText().equalsIgnoreCase("Obligatoire"));
		Assert.assertTrue("Fail: Attach a file above(Joindre un fichier ci-dessus) error message is not displayed in french",page.getattachmentError().getText().equalsIgnoreCase("Joindre un fichier ci-dessus"));
		
		page.selectDropDown("Autre");
		page.UploadAttachedImage("testFile.txt");
		//page.clickOnAttachBtnFrench();
		Assert.assertTrue("Fail: Wrong file error message (Le document doit être en format PDF, JPG ou TIF) is not displayed in french",page.getattachmentError().getText().equalsIgnoreCase("Le document doit être en format PDF, JPG ou TIF."));
		page.clickOnCloseButton();
		
		commonElements.clickENFRToggle();
		page.clickOnAttachDocumenButton();
		page.selectDropDown("Beneficiary designation for life insurance form, NN0283");
		page.UploadAttachedImage("testFile.txt");
		
		Assert.assertTrue("Fail: Wrong file error message (Document must be PDF, JPG, or TIF.) is not displayed in English",page.getattachmentError().getText().equalsIgnoreCase("Document must be PDF, JPG, or TIF."));
		// verify the attachment size error message.
		//En
		page.UploadAttachedImage("TIFF_10MB.tiff");
		
		page.clickOnAttachDocumenButton();
		page.selectDropDown("Beneficiary designation for life insurance form, NN0283");
		page.UploadAttachedImage("TIFF_10MB.tiff");		
		
		Assert.assertTrue("Fail: Error message for file size(Total size can't exceed 15 MB.) is not displayed in English",page.getattachmentError().getText().equalsIgnoreCase("Total size can't exceed 15 MB."));
		page.clickOnCloseButton();
		// Fr
		commonElements.clickENFRToggle();
		page.clickOnAttachDocumenButton();
		page.selectDropDown("Autre");
		page.UploadAttachedImage("TIFF_10MB.tiff");
		
		Assert.assertTrue("Fail: Error message for file size(La taille totale ne peut pas dépasser 15 Mo.) is not displayed in French",page.getattachmentError().getText().equalsIgnoreCase("La taille totale ne peut pas dépasser 15 Mo."));
		
		
	}
	
	@Test(dataProvider = "AttachmentsTestData", priority = 2, testName = "Verifying user able to upload TIF, PDF, JPG files", groups = "webBrowser")
	public void verifyUserAbleToUploadDoc(Map<String, String> data) throws Exception {
		AttachmentPage page = new AttachmentPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		
		fillUntilAttachmentsTestPage(data);
		page.clickOnAttachDocumenButton();
		
		// Verify the User able to upload Jpg Image
		page.selectDropDown("Charter documents");
		page.UploadAttachedImage("logo.jpg");				
		boolean tablePresent = page.VerfiyTableISPresent();
		
		
		page.VerifyTableData(1,2,"Charter documents","logo.jpg");
		
		Assert.assertTrue("Fail :Attached document is not showing in Grid",tablePresent);
		// Verify the User able to upload PDF file
		page.clickOnAttachDocumenButton();
		page.selectDropDown("Establishing Tenants in Common Ownership for a Policy form, NN0967");
		page.UploadAttachedImage("manulife-guide.pdf");				
		tablePresent = page.VerfiyTableISPresent();
		page.VerifyTableData(2,2,"Establishing Tenants in Common Ownership for a Policy form, NN0967","manulife-guide.pdf");
		
		// Verify the User able to upload Tif Image
		page.clickOnAttachDocumenButton();
		page.selectDropDown("Release of collateral assignment form, NN0971");
		page.UploadAttachedImage("TIFF_1MB.tiff");				
		tablePresent = page.VerfiyTableISPresent();
		page.VerifyTableData(3,2,"Release of collateral assignment form, NN0971","TIFF_1MB.tiff");
	}
	
	@Test(dataProvider = "AttachmentsTestData", priority =3, testName = "verifying user able to delete uploaded PDF, JPG & TIF documents ", groups = "webBrowser")
	public void verifyUserAbleToDeleteDoc(Map<String, String> data) throws Exception {
		AttachmentPage page = new AttachmentPage(driverUtil);
		
		
		fillUntilAttachmentsTestPage(data);
		
		page.clickOnAttachDocumenButton();
		page.selectDropDown("Beneficiary designation for life insurance form, NN0283");
		page.UploadAttachedImage("logo.jpg");
		Thread.sleep(4000);
		page.ClickOnDeleteUploadedImage();
		
		
		boolean tablePresent = page.VerfiyTableISPresent();	
		
		Assert.assertFalse("Fail:Table should not dispaly",tablePresent);	
		
		
		
	}
	


}
