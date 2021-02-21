package term_conversion;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class SuccessorSubrogatedOwnerPage{
	private DriverUtil driverUtil;
	
	private String startApplicationId="//button[@id='start-new-application-button']";//start application
	//button[contains(@id,'convert-successor-owner') and (text()='Yes')]

	private String labelNameASuccessorOwnerYesXPath = "//button[contains(@id,'convert-successor-owner') and (text()='Yes')]";
	private String labelNameASuccessorOwnerNoXPath = "//button[contains(@id,'convert-successor-owner') and (text()='No')]";
	private String inputNameASuccessorOwnerYesXPath = "//button[@id = 'convert-successor-owner-name-0']";
	private String inputNameASuccessorOwnerNoXPath = "//button[@id = 'convert-successor-owner-name-1']";
	private String inputOwnerFirstNameXPath = "//input[@id = '-firstName']";
	private String inputOwnerMiddleInitialXPath = "//input[@id = '-middleName']";
	private String inputLastNameXPath = "//input[@id = '-lastName']";
	private String inputSuccessorFirstNameXPath = "//input[@id = '-firstName']";
	private String inputSuccessorMiddleInitialXPath = "//input[@id = '-middleName']";
	private String inputSuccessorLastNameXPath = "//input[@id = '-lastName']";
	private String bodySuccessorInfoXPath = "//body[..//div[@id = 'Successor/subrogated ownerinformation-container']]";
	private String divSuccessorFormContainerXPath = "//div[@id = 'successorForm-container']";
	private String divNameASuccessorErrorXPath = "//div[@id = 'convert-successor-owner-name--error'][@aria-live = 'off']";
	private String divSuccessorFirstNameErrorXPath = "//div[@id = '-firstName-errors']/div";
	private String divSuccessorLastNameErrorXPath = "//div[@id = '-lastName-errors']/div";
	private String divRelationErrorXPath = "//div[@id = 'relation-Successor-errors']/div";
	private String divRelationXPath = "//div[@id = 'relation-Successor-value']";
	private String ulRelationXPath = "//ul[@id = 'relation-Successor-listbox']";
	//next button XPath
	private String buttonNextXPath = "//button[@id = 'next-btn']";
	//label title XPath
	private String h1PageTitleXPath = "//h1[@id = 'page-title']";
	private String legendNameSuccessorLabelXPath = "//legend[contains(text(),'Do you want to name a successor/subrogated owner?') or contains(text(),'Souhaitez-vous désigner un titulaire successeur/subrogé')]";  
	private String spanSuccessorSectionLabelXPath = "//h2[@id= 'succesorInfo.form.titleNum1']/span";
	private String divOwnerNameLabelXPath = "//div[contains(text(),'Owner name') or contains(text(),'Nom du titulaire')]";
	private String divSuccessorNameLabelXPath = "//div[contains(text(),'Successor/subrogated owner name') or contains(text(),'Nom du titulaire successeur/subrogé')]";
	private String labelRelationshipLabelXPath = "//label[@id = 'relation-Successor-label']";
	
	// Successor/Subrogated owner name
	
	private String SubrogatedFirstName="//div[contains(text(),'Successor/subrogated owner name')]/../../following-sibling::div/div/div[1]/div/div/div/input";
	private String SubrogatedMidleName="//div[contains(text(),'Successor/subrogated owner name')]/../../following-sibling::div/div/div[2]/div/div/div/input";
	private String SubrogatedLastName="//div[contains(text(),'Successor/subrogated owner name')]/../../following-sibling::div/div/div[3]/div/div/div/input";
	
	//EN FR toggle XPath 
	private String buttonENFRXPath = "//button[contains(text(),'EN') or contains(text(),'FR')]";
	
	public SuccessorSubrogatedOwnerPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	public void startApplication()
	{
		this.driverUtil.getWebElement(By.xpath(startApplicationId)).click();
	}
	
	public WebElement getSubrogatedFirstName() {
		
		return this.driverUtil.getWebElement(By.xpath(SubrogatedFirstName));
	}
	public WebElement getSubrogatedMidleName() {
		
		return this.driverUtil.getWebElement(By.xpath(SubrogatedMidleName));
	}
	public WebElement getSubrogatedLastName() {	
		
		return this.driverUtil.getWebElement(By.xpath(SubrogatedLastName));
	}
	
	public WebElement nameASuccessorOwnerLabel(boolean bln) {
		String useXPath = (bln) ?  labelNameASuccessorOwnerYesXPath: labelNameASuccessorOwnerNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement nameASuccessorOwnerInput(boolean bln) {
		String useXPath = (bln) ?  inputNameASuccessorOwnerYesXPath: inputNameASuccessorOwnerNoXPath;
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getSuccessorFirstName() {
		return this.driverUtil.getWebElements(By.xpath(inputSuccessorFirstNameXPath)).get(1);
	}
	
	public WebElement getSuccessorMiddleInitial() {
		return this.driverUtil.getWebElements(By.xpath(inputSuccessorMiddleInitialXPath)).get(1);
	}
	
	public WebElement getSuccessorLastName() {
		return this.driverUtil.getWebElements(By.xpath(inputSuccessorLastNameXPath)).get(1);
	}
	
	public WebElement getOwnerFirstName() {
		return this.driverUtil.getWebElements(By.xpath(inputOwnerFirstNameXPath)).get(0);
	}
	
	public WebElement getOwnerMiddleInitial() {
		return this.driverUtil.getWebElements(By.xpath(inputOwnerMiddleInitialXPath)).get(0);
	}
	
	public WebElement getOwnerLastName() {
		return this.driverUtil.getWebElements(By.xpath(inputLastNameXPath)).get(0);
	}
	
	public String getRelation() {
		return this.driverUtil.getText(By.xpath(divRelationXPath));
	}
		
	public boolean isSuccessorFormPresent() {
		WebElement formRootDiv = this.driverUtil.getWebElement(By.xpath(divSuccessorFormContainerXPath));
		boolean isPresent = formRootDiv.findElements(By.tagName("div")).size() > 0;
		return isPresent;
	}
	
	public boolean isSuccessorPageVisible() {
		return this.driverUtil.getWebElements(By.xpath(bodySuccessorInfoXPath)).size() > 0;
	}
	
	public void selectRelation(String relation) {		
		this.driverUtil.click(By.xpath(divRelationXPath));
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(ulRelationXPath));
		monthDropdown.findElement(By.xpath("//*[contains(text(),'"+relation+"')]")).click();
	}
	
	public boolean isRelationPresent(String relation) {		
		this.driverUtil.click(By.xpath(divRelationXPath));
		WebElement monthDropdown = this.driverUtil.getWebElement(By.xpath(ulRelationXPath));
		boolean bln = monthDropdown.findElements(By.xpath("//*[contains(text(),'"+relation+"')]")).size() != 0;
		this.driverUtil.click(By.xpath(divRelationXPath));
		return bln;
	}
	
	//Label fields
	public WebElement getPageTitle() {
		return this.driverUtil.getWebElement(By.xpath(h1PageTitleXPath));
	}
	
	public WebElement getNameSuccessorOwnerLabel() {
		return this.driverUtil.getWebElement(By.xpath(legendNameSuccessorLabelXPath));
	}
	 
	public WebElement getSuccessorOwnerSectionLabel() {
		return this.driverUtil.getWebElement(By.xpath(spanSuccessorSectionLabelXPath));
	}
	
	public WebElement getOwnerNameLabel() {
		return this.driverUtil.getWebElement(By.xpath(divOwnerNameLabelXPath));
	}
	
	public WebElement getSuccessorNameLabel() {
		return this.driverUtil.getWebElement(By.xpath(divSuccessorNameLabelXPath));
	}
	
	public WebElement getRelationshipLabel() {
		return this.driverUtil.getWebElement(By.xpath(labelRelationshipLabelXPath));
	}
	
	//error fields
	public String getErrorNameASuccessor() {	
		return this.driverUtil.getWebElement(By.xpath(divNameASuccessorErrorXPath)).getText();
	}
	
	public String getErrorSuccessorFirstName() {
		return this.driverUtil.getWebElement(By.xpath(divSuccessorFirstNameErrorXPath)).getText();
	}
	
	public String getErrorSuccessorLastName() {
		return this.driverUtil.getWebElement(By.xpath(divSuccessorLastNameErrorXPath)).getText();
	}
	
	public String getErrorOwnerRelationship() {
		return this.driverUtil.getWebElement(By.xpath(divRelationErrorXPath)).getText();
	}
	
	//next
	public void nextButton() {
		this.driverUtil.click(By.xpath(buttonNextXPath));
	}
	
	//English French toggle
	public WebElement getENFRToggle() {
		return this.driverUtil.getWebElement(By.xpath(buttonENFRXPath));
	}
	
}