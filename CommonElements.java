package term_conversion;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.manulife.automation.selenium_core.utils.DriverUtilBase.HeaderColumn;
import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class CommonElements {
	private DriverUtil driverUtil;
	
	//XPath
	private String buttonNextXPath = "//button[@id = 'next-btn']";
	private String buttonBackXPath = "//button[@id = 'back-btn']";
	private String buttonLanguageXPath = "//button[@id = 'language-toggle-button']";
	private String h1PageTitleXPath = "//h1[@id = 'page-title']";
	private String startApplicationXpath="//button[@id='start-new-application-button']";
	
	public CommonElements(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	public void moveFileFromProjectToTempFolder(String sourceLocation, String fileName) throws IOException
	{
		String source = sourceLocation+fileName;
        //directory where file will be copied
		
		File file = new File("C:\\AttachmentImages");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        String target ="C:\\AttachmentImages\\"+fileName;
     
        //name of source file
        File sourceFile = new File(source);
      
     
        File targetFile = new File(target);
        System.out.println("Copying file : " + sourceFile.getName() +" from Java Program");
     
        //copy file from one location to other
        FileUtils.copyFile(sourceFile, targetFile);
	
	
	}

	
	
	
	//Elements
	public String getPageTitle() {
		return this.driverUtil.getWebElement(By.xpath(h1PageTitleXPath)).getText();
	}
	
	
	
 public String WaitFroElementIsVisiable(int timeoutInSecond,WebDriver driver, WebElement elementXpath )
 {
	 
		 try {
		        (new WebDriverWait(driver, timeoutInSecond)).until(ExpectedConditions.visibilityOf(elementXpath));
		        return null;
		    } catch (TimeoutException e) {
		        return "Build your own errormessage...";
		    }		 
	
 }
	
	public void clickNextBtn() {
		this.driverUtil.click(By.xpath(buttonNextXPath));
	}
	
	public void clickBackBtn() {
		this.driverUtil.getWebElements(By.xpath(buttonBackXPath)).get(1).click();
	}
	
	public void clickENFRToggle() {
		this.driverUtil.click(By.xpath(buttonLanguageXPath));
	}
	public void startApplication()
	{
		this.driverUtil.getWebElement(By.xpath(startApplicationXpath)).click();
	}
	
	//Common Functions
	public String[] getOptionLabels(String xPath, String tagName) {
		WebElement div = this.driverUtil.getWebElement(By.xpath(xPath));
		List<WebElement> listElem = div.findElements(By.tagName(tagName));
		String[] listText = new String[listElem.size()];
		for(int i = 0; i < listElem.size(); i++) {
		    listText[i] = listElem.get(i).getText();
		}
		return listText;
	}

	
	
	public String[] getTableBody(String table, Integer row) {
		List<HeaderColumn> listElem = this.driverUtil.getTableAsArrayWithHeader(By.xpath(table)).get(0).headerColumnsData;
		String[] listText = new String[listElem.size()];		
		List<WebElement> rowData = this.driverUtil.getWebElement(By.xpath(table + "/tbody")).findElements(By.tagName("tr")).get(row).findElements(By.tagName("td"));
		
		for(int i = 0; i < listElem.size(); i++) {
			if(rowData.get(i).findElements(By.tagName("input")).size() > 0 ) {
				
				listText[i] = rowData.get(i).findElement(By.tagName("input")).getAttribute("value");	
				
			}else if(rowData.get(i).getText() != null && !rowData.get(i).getText().isEmpty()){
				listText[i] = rowData.get(i).getText();
			}else {
				listText[i] = " ";
			}
		}
		return listText;
	}
	
	
	
	public String[] getTableHeader(String table) {
		List<HeaderColumn> listElem = this.driverUtil.getTableAsArrayWithHeader(By.xpath(table)).get(0).headerColumnsData;

		String[] listText = new String[listElem.size()];		
		for(int i = 0; i < listElem.size(); i++) {
			if(listElem.get(i).columnValue != null && !listElem.get(i).columnValue.isEmpty()) {
				listText[i] = listElem.get(i).columnValue;
			}else {
				listText[i] = " ";
			}
		}
		return listText;
	}
	
	public void validateLabels(String[] labelList, Map<String,String> data, String xpath) {
		for(int i = 0; i < labelList.length; i++) {
			String expectedLabelStr = data.get(labelList[i]);
			List<WebElement> elems = this.driverUtil.getWebElement(By.xpath(xpath)).findElements(By.xpath("(" + xpath + "//*["
					+ "translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = " + 
					"'"+expectedLabelStr.toLowerCase()+"'])[last()]"));
			
			if(elems.size() != 0) {
				Assert.assertEquals("Incorrect label value",expectedLabelStr,elems.get(0).getText());
			}else {
				Assert.assertTrue("Incorrect label value. Expected label:" + expectedLabelStr,false);
			}
		}
	}
	
}