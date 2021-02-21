package term_conversion;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class LoginPage {
	
	private DriverUtil driverUtil; 
//	********************************************Xpaths.... for login page*******************************************
	private String userNameXpath="//input[@name='username']";
	private String password="//input[@id='idPassword']";
	private String pageTitle="//button[@id='start-new-application-button']";
	private String loginBtn="//input[@id='idSubmit' and @name='Submit']";
	private String invalidCredential="errorMsgadvisorCode";
	
	private String landingPageTitleXpath="//button[@id='start-new-application-button']";
	
	
	// Label xpaths
	
	private String userNameLabelXpath="//div[@id='idLogin']/table/tbody/tr[1]/td[1]/font";
	private String passwordLabelXpath="//div[@id='idLogin']/table/tbody/tr[2]/td[1]/font";
	
	
	
	
	
	public LoginPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	public WebElement getUserNameTextbox() {
		WebElement el= null;
		try {
			
			el= this.driverUtil.getWebElement(By.xpath(userNameXpath));
		}catch(Exception ex )
		{
		 System.out.println("Unable to find userName textbox " + ex.getStackTrace());	
		}
		return el;
	}
	
	public WebElement getPasswordTextbox() {
		WebElement el= null;
		try {
			
			el= this.driverUtil.getWebElement(By.xpath(password));
		}catch(Exception ex )
		{
			System.out.println("Unable to find Password textbox " + ex.getStackTrace());	
		}
		return el;
		
		
	}
	
	public WebElement getLoginBtn() {
		return this.driverUtil.getWebElement(By.xpath(loginBtn));
	}
	
	public WebElement getPageTitle() {
		return this.driverUtil.getWebElement(By.xpath(pageTitle));
	}
	
	public WebElement getInvalidCredentials() {
		return this.driverUtil.getWebElement(By.id(invalidCredential));
	}
	public WebElement getUserNameLabel() {
		return this.driverUtil.getWebElement(By.xpath(userNameLabelXpath));
	}
	public WebElement getPasswordLabel() {
		return this.driverUtil.getWebElement(By.xpath(passwordLabelXpath));
	}
	
	public boolean login(String userName,String password)
	{
		try {
			
			System.out.println("Login URL is ("+ driverUtil.getUrl()+")");
			
			// verify the username text is visible or not 
			
			if(!driverUtil.checkElementVisible(By.xpath(userNameXpath), 30))
			{
				Assert.fail("User name textbox is not visible after 10 seconds");
			}
			
			
			getUserNameTextbox().sendKeys(userName);
			getPasswordTextbox().sendKeys(password);
			System.out.println("successfully entered username and password");
			
			getLoginBtn().click();
			
			System.out.println("login button is clicked");
			// verifying the start a new application button is visible or not
			
			if(!driverUtil.checkElementVisible(By.xpath(landingPageTitleXpath), 20))
			{
				
				return false;
				
			}
			
			return true; 
		}catch(Exception ex)
		{
			throw new NoSuchElementException("Unable to login in system using user name("+userName+") and password" +password+") and getting exception"+ ex.getMessage());
		}
		
	}
	
	public boolean InValidLogin(String userName,String password)
	{
		try {
			
			System.out.println("Login URL is ("+ driverUtil.getUrl()+")");
			
			// verify the username text is visible or not 
			
			if(!driverUtil.checkElementVisible(By.xpath(userNameXpath), 20))
			{
				Assert.fail("User name textbox is not visible after 10 seconds");
			}
			
			
			getUserNameTextbox().sendKeys(userName);
			getPasswordTextbox().sendKeys(password);
			System.out.println("successfully entered username and password");
			
			getLoginBtn().click();
			
			System.out.println("login button is clicked");
			
			return true; 
		}catch(Exception ex)
		{
			throw new NoSuchElementException("Unable to login in system using user name("+userName+") and password" +password+") and getting exception"+ ex.getMessage());
		}
	}
	
	public boolean verifyPageTitle(String expectedTitle)
	{
		return getPageTitle().getText().equalsIgnoreCase(expectedTitle);
	}
 
	public boolean verifyErrorMessage(String expectedErrorMessage)
	{
		boolean text=getInvalidCredentials().getText().contains(expectedErrorMessage);
		return text;
	}
    
	public boolean landingPage(String title)
	{
		
		return this.driverUtil.getWebElement(By.xpath(landingPageTitleXpath)).getText().equalsIgnoreCase(title);
	}

}
