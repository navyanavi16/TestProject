package term_conversion;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class AttachmentPage {

	private DriverUtil driverUtil;
	String attachDocumentButtonXpath = "//button[@id='undefined-addButton']";
	String dropdownDivId = "attach-doctype-dd-value";
	String ulValueXpath = "//ul[@id='attach-doctype-dd-listbox']";
	String selectFromComputerXpath = "//div[@class='dnd-text']/span";
	String uploadButtonXpath = "//*[@id='cancel-btn']";
	String closeButtonXpath="//button[contains(@id,'modal-closeButton-id')]";
	String attachBtnFrenchXpath="//button[@id='cancel-btn']";
	
	public void clickOnAttachBtnFrench()
	{
		this.driverUtil.getWebElements(By.xpath(attachBtnFrenchXpath)).get(1).click();
		
	}
	public void clickOnCloseButton()
	{
		this.driverUtil.getWebElement(By.xpath(closeButtonXpath)).click();
	}
	
	String tableHeaderXpath="//table[@role='table']/thead/tr/th[%s]/span";
	//table[@role='table']/thead/tr/th[4]/span
	
	//table[@role='table']/thead/tr/th
	
	//error validation Xpaths
     private String attachDocErrorXpath="//div[@id='attach-empty-error-error']/span";
     private String dropdownErrorXpath="//div[@id='attach-doctype-dd-errors']/div";
     private String attachmentErrorXpath="//div[@id='attach-file-error-error']/span"; //(this Xpath is for both error messages: Document must be PDF, JPG, or TIF, Attach a file above, Total size can't exceed 15 MB)
	
     private String deleteXpath="//a[text()='Delete']";
     
     private String tableXapth="//table[@role='table']";
     private String tableRowXapth="//table[@role='table']/tbody/tr[%s]";
     
     public WebElement getattachmentError()
     {
    	 
    	return this.driverUtil.getWebElement(By.xpath(attachmentErrorXpath));
     }
     
     
     
     
     public void VerifyTableData(int rowNumber, int columNumber, String expectedDropdownValue,String uploadImageName)
     {
    	 String useXapth=String.format(tableRowXapth, rowNumber);
    	 String columnXapth=useXapth+"/td["+columNumber+"]"+"/div/div[1]";
    	 String columnXapth2=useXapth+"/td["+columNumber+"]"+"/div/div[2]/button";
    	 
    	 String dropdownValue=this.driverUtil.getWebElement(By.xpath(columnXapth)).getText();
    	    String imageName=this.driverUtil.getWebElement(By.xpath(columnXapth2)).getText();
   Assert.assertTrue("Fail:We are expecting dropdwon valuse as  ("+expectedDropdownValue+") but found as ("+dropdownValue+")", dropdownValue.equalsIgnoreCase(expectedDropdownValue));
   Assert.assertTrue("Fail:We are expecting upload file name as  ("+uploadImageName+") but found as ("+imageName+")", imageName.equalsIgnoreCase(uploadImageName));
    	
     }
     
     public void ClickOnDeleteUploadedImage()
     {
    	 this.driverUtil.getWebElement(By.xpath(deleteXpath)).click();
     }
     public WebElement getAttachDocError()
     {
    	 return  this.driverUtil.getWebElement(By.xpath(attachDocErrorXpath));
     }
     public WebElement getDropDownError()
     {
    	 return  this.driverUtil.getWebElement(By.xpath(dropdownErrorXpath));
     }
     
     public boolean VerfiyTableISPresent() throws InterruptedException
     {
    	 Thread.sleep(4000);
    	 return this.driverUtil.checkElementPresent(By.xpath(tableXapth));
     }
     
     public boolean VerfiyTableISPresent1()
     {
    	  if(((WebElement) this.driverUtil.getWebElements(By.xpath("//table[@role='table']/tbody"))).isDisplayed()){
      	    System.out.println("is present");
      	}else {
      	    System.out.println("not");
      	}
		return false;
     }
     
   
     
     
	public AttachmentPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	public void VerifyPageTitle(String expectedTitle)
	{
		CommonElements commonElements = new CommonElements(driverUtil);		
		String  actualPageTitle =commonElements.getPageTitle();
		Assert.assertTrue("Fail:We are expecting page title as  ("+expectedTitle+") but found as ("+actualPageTitle+")", actualPageTitle.equalsIgnoreCase(expectedTitle));
	}

	public String getTableHeader(int columnNumber)
	
	{
		String useXapthforHeader= String.format(tableHeaderXpath, columnNumber);
		return this.driverUtil.getWebElement(By.xpath(useXapthforHeader)).getText();
		
	}

	public void clickOnAttachDocumenButton() {
		getAttachDocButton().click();
	}
	
	public WebElement getAttachDocButton() {
		return this.driverUtil.getWebElement(By.xpath(attachDocumentButtonXpath));
	}

	public void selectDropDown(String value) {

		this.driverUtil.click(By.id(dropdownDivId));
		WebElement ulElement = this.driverUtil.getWebElement(By.xpath(ulValueXpath));
		ulElement.findElement(By.xpath("//li[contains(text(),'" + value + "')]")).click();
	}

	public void ClickOnAttachButton()
	{
		this.driverUtil.getWebElements(By.xpath(uploadButtonXpath)).get(1).click();
	}
	
	public WebElement uploadInput()
	{
		return this.driverUtil.getWebElement(By.xpath("//div[@id='dnd-upload-id']/input"));
	}
	public void UploadAttachedImage(String imageName) throws InterruptedException, IOException{
		String currentDirectory = System.getProperty("user.dir");
		String imagePath = currentDirectory + "\\src\\test\\resources\\images\\"+imageName;
		File fs= new File(imagePath);
		String absolutPath= fs.getAbsolutePath();
		
		System.out.println("Path ---" + absolutPath);
		
		boolean exists = fs.exists();
		
		if(exists)
		{
			System.out.println("File present in the location "+absolutPath );
		}else
		{
			System.out.println("File is not present in the location "+absolutPath );
		}
		
		//String imagePath = currentDirectory + "\\src\\test\\resources\\images\\";
		
		
		CommonElements comElement = new CommonElements(driverUtil);
		//comElement.moveFileFromProjectToTempFolder(imagePath, imageName);
		
		//String finalPath= "C:\\Windows\\Temp"+ imageName;
		
		uploadInput().sendKeys(absolutPath);
		
		//String finalPath= imagePath;
		
		//String newImagePath="E:\\Images"+ imageName;
		
		System.out.println("Path ---" + imagePath);
		//			Robot robot = null;
//			robot = new Robot();
//			this.driverUtil.getWebElement(By.xpath(selectFromComputerXpath)).click();
//			// Change the variable imagePAth to newimagepath
//			StringSelection fileName = new StringSelection(imagePath);
//			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileName, null);
//			robot.setAutoDelay(1000);			
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_V);
//			robot.setAutoDelay(1000);
//			robot.keyRelease(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.keyRelease(KeyEvent.VK_ENTER);
			ClickOnAttachButton();

	}

}