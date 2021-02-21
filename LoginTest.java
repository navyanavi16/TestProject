package term_conversion;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.manulife.automation.datareader.ExcelUtil;
import com.manulife.automation.selenium_execution.base.BaseTest;

import junit.framework.Assert;

public class LoginTest extends BaseTest {

	 
	
	@Override
	public void initializeTest() throws Exception {
		super.initializeTest("en","baseUrl" );
	}

	ExcelUtil excelUtil = new ExcelUtil("src/test/resources/testdata/termConversionTestData.xlsx");

	@DataProvider(name = "LoginTestData")
	public Object[][] getExcelDatafromSheet(Method method) throws Exception {
		// Getting the Data Row against the Test Case name and store it within an array
		Object[][] testObjArray = excelUtil.getAllMatchingTestCases("LoginTestData", method.getName());
		return (testObjArray);

	}

	@Test(enabled=true,dataProvider = "LoginTestData", priority = 1, testName = "verifying login page with valid credentials", groups = "webBrowser")
	public void verifyLoginPage(Map<String, String> data) throws Exception {

		String userName = data.get("userName");
		String password = data.get("userPassword");
		String landingPageTitle = data.get("landingPageTitle");
		LoginPage page = new LoginPage(driverUtil);
		
		
		boolean logged = page.login(userName, password);
		
		System.out.println("login page is enetered successfully");
		Assert.assertTrue("Fail: user uanble to login in system", logged);
		Thread.sleep(5000);
		boolean landingPageIsDispalyed = page.landingPage(landingPageTitle);
		Assert.assertTrue("Fail: user uanble to login in system", landingPageIsDispalyed);
	}

	@Test(enabled=true,dataProvider = "LoginTestData", priority = 0, testName = "verifying login page with Invalid credentials", groups = "webBrowser")
	public void verifyLoginPageWithInvalidCredentials(Map<String, String> data) throws Exception {

		String userName = data.get("userName");
		String password = data.get("userPassword");
		String expectedErrroMessage = data.get("inValidErrorMessage");
		LoginPage page = new LoginPage(driverUtil);
		boolean logged = page.InValidLogin(userName, password);
		
		boolean landingPageIsDispalyed = page.verifyErrorMessage(expectedErrroMessage);
		Assert.assertTrue("Fail: user uanble to login in systemc("+expectedErrroMessage+")", landingPageIsDispalyed);
		
		
		
	}

	@Test(enabled=true,dataProvider = "LoginTestData", priority = 2, testName = "verifying login page label in EN & FR", groups = "webBrowser")
	public void verifyLabelInEnglishAndFrench(Map<String, String> data) throws Exception {

		String userNameLableValueInEnglish = data.get("userNameLableEN");
		String passwordLabelValueInEnglish = data.get("userPasswordLabelEN");

		String userNameLableValueInFrench = data.get("userNameLableFR");
		String passwordLabelValueInFrench = data.get("userPasswordLabelFR");

		String loginButtonLabelEnglish = data.get("loginButtonLabelEN");
		String loginButtonLabelFrench = data.get("loginButtonLabelFR");

		LoginPage page = new LoginPage(driverUtil);

		String actualUserNameLable[] = page.getUserNameLabel().getText().split(Pattern.quote("/"));
		String actualPasswordLabel[] = page.getPasswordLabel().getText().split(Pattern.quote("/"));
		String actualLoginButtonLable[] = page.getLoginBtn().getAttribute("value").split(Pattern.quote("/"));
		String englishLableText= actualUserNameLable[0];

		Assert.assertTrue("Fail English: Label is expected as ("+englishLableText+") but found as ("+userNameLableValueInEnglish+")", englishLableText.trim().contains(userNameLableValueInEnglish.trim()));
		englishLableText= actualUserNameLable[1];
		Assert.assertTrue("Fail French: Label is expected as ("+userNameLableValueInFrench+") but found as ("+userNameLableValueInFrench+")", englishLableText.trim().contains(userNameLableValueInFrench.trim()));
		englishLableText= actualPasswordLabel[0];
		Assert.assertTrue("Fail English:Label is expected as ("+passwordLabelValueInEnglish+") but found as ("+passwordLabelValueInEnglish+")", englishLableText.trim().equalsIgnoreCase(passwordLabelValueInEnglish));
		
		englishLableText= actualPasswordLabel[1];
		
		Assert.assertTrue("Fail French:Label is expected as ("+passwordLabelValueInFrench+") but found as ("+passwordLabelValueInFrench+")", englishLableText.trim().equalsIgnoreCase(passwordLabelValueInFrench));
		englishLableText= actualLoginButtonLable[0];
		Assert.assertTrue("Fail English:Label is expected as ("+loginButtonLabelEnglish+") but found as ("+loginButtonLabelEnglish+") ", englishLableText.trim().equalsIgnoreCase(loginButtonLabelEnglish));
		englishLableText= actualLoginButtonLable[1];
		
		Assert.assertTrue("Fail French: Label is expected as ("+loginButtonLabelFrench+") but found as ("+loginButtonLabelFrench+")", englishLableText.trim().equalsIgnoreCase(loginButtonLabelFrench));

	}
}