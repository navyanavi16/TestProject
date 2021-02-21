package term_conversion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class BeneficiaryInformationPage {
	private DriverUtil driverUtil;
	
	//Fields
	private String buttonAddBeneficiaryXPath = "//button[@id = 'beneficiary-form-addButton']";
	private String buttonAddSecondaryBeneficiaryXPath = "//button[@id = 'beneficiary-form-addSecondaryButton']";
	private String buttonAddBeneficiary2XPath = "//button[@id = 'beneficiary-form2-addButton']";
	private String buttonAddSecondaryBeneficiary2XPath = "//button[@id = 'beneficiary-form2-addSecondaryButton']";
	private String buttonModalSaveCloseXPath = "//button[@id = 'save-and-close-btn']";
	private String buttonCancelXPath = "//button[@id = 'cancel-btn']";
	private String divBeneficiaryRelationshipXPath = "//div[contains(@id,'beneficiaryRelation-value')]";
	private String divTrusteeRelationshipXPath = "//div[contains(@id,'trusteeRelation-value')]";
	private String divBeneficiaryModalXPath = "//div[contains(@id,'beneficiary-modal')]";
	private String divDesignationNoteXPath = "//div[@id = 'DesignationNote']";
	private String divQDesignationInstructionXPath = "//div[contains(text(),'Beneficiary designations are revocable') or contains(text(),'La désignation de bénéficiaires est révocable')]";
	private String divNQDesignationInstructionXPath = "//div[contains(text(),'Beneficiary designations are revocable') or contains(text(),'La désignation de bénéficiaires est révocable')]";
	private String inputBeneficiaryFirstNameXPath = "//input[contains(@id,'firstName')]";
	private String inputBeneficiaryMiddleNameXPath = "//input[contains(@id,'middleName')]";
	private String inputBeneficiaryLastNameXPath = "//input[contains(@id,'lastName')]";
	private String inputBeneficiaryShareXPath = "//div[contains(@id,'beneficiary-modal')]//input[contains(@id,'beneficiaryShare')]";
	private String inputBeneficiaryDesignationRevocableXPath = "//button[starts-with(@id,'beneficiary-form-beneficiaryDesignation') and contains(text(),'Revocable')]";	
	private String inputBeneficiaryDesignationIrrevocableXPath = "//button[starts-with(@id,'beneficiary-form-beneficiaryDesignation') and contains(text(),'Irrevocable')]";
	private String inputBeneficiary2DesignationRevocableXPath = "//button[starts-with(@id,'beneficiary-form2-beneficiaryDesignation') and contains(text(),'Revocable')]";
	private String inputBeneficiary2DesignationIrrevocableXPath = "//button[starts-with(@id,'beneficiary-form2-beneficiaryDesignation') and contains(text(),'Irrevocable')]";
	
	private String inputTrusteeFirstNameXPath = "(//input[contains(@id,'firstName')])[2]";
	private String inputTrusteeMiddleNameXPath = "(//input[contains(@id,'middleName')])[2]";
	private String inputTrusteeLastNameXPath = "(//input[contains(@id,'lastName')])[2]";
	
	private String inputIsBeneficiaryAMinorYesXPath = "//button[starts-with(@id,'beneficiary-form-beneficiary-form-beneficiaryMinorChild') and contains(text(),'Yes')]";
	private String inputIsBeneficiaryAMinorNoXPath = "//button[starts-with(@id,'beneficiary-form-beneficiary-form-beneficiaryMinorChild') and contains(text(),'No')]";
	
	
	private String inputNameBeneficiaryTrusteeYesXPath = "//button[starts-with(@id,'beneficiary-form-beneficiaryTrustee') and contains(text(),'Yes')]";
	private String inputNameBeneficiaryTrusteeNoXPath = "//button[starts-with(@id,'beneficiary-form-beneficiaryTrustee') and contains(text(),'No')]";
	
	private String tablePrimaryBeneficiaryXPath = "//table[@role = 'table']";
	private String tableSecondaryBeneficiaryXPath = "//*[@id='beneficiaryForm-container']/div/div/div[5]/table";
	private String ulBeneficiaryRelationshipXPath = "//ul[contains(@id,'beneficiaryRelation-listbox')]";
	private String ulTrusteeRelationshipXPath = "//ul[contains(@id,'trusteeRelation-listbox')]";
	//labels
	private String h1BeneficiaryOverlayHeaderXPath = "//h1[@id = 'modal-header']";
	private String h2BeneficiaryOverlayTitleXPath = "//h2[@id = 'modal-title']";
	private String h2Insured1BeneficiaryTitleXPath = "//h2[@id = 'form-title-beneficiaryForm']";
	private String h2Insured2BeneficiaryTitleXPath = "(//h2[@id = 'form-title-beneficiaryForm'])[2]";
	private String divBeneficiaryNameLabelXPath = "(//div[@id = 'beneficiary-form-beneficiary']//div[@class = 'row']/div)[1]";
	private String divDesignationLabelXPath = "//div[contains(text(),'Beneficiary designation') or contains(text(),'Désignation de bénéficiaires')]";
	private String divNameATrusteeXPath = "//div[contains(text(),'Do you want to name a trustee for this beneficiary?') or contains(text(),'Voulez-vous nommer un fiduciaire pour ce bénéficiaire?')]";
	private String divNameATrusteeNoteXPAth = "//div[contains(text(),'By naming a trustee') or contains(text(),'En nommant un fiduciaire')]";
	private String divTrusteeNameXPath = "//div[contains(text(),'Trustee name') or contains(text(),'Nom du fiduciaires')]";
	private String divShareNoteXPath = "//div[./label[@id = 'beneficiary-share-label']]";
	private String divBeneficiaryFirstNameErrorXPath = "//div[@id = 'beneficiary-form-firstName-errors']";
	private String divBeneficiaryLastNameErrorXPath = "//div[@id = 'beneficiary-form-lastName-errors']";
	private String divBeneficiaryRelationshipErrorXPath = "//div[@id = 'beneficiary-form-beneficiaryRelation-errors']";
	private String divBeneficiaryShareErrorXPath = "//div[@id = 'beneficiary-form-beneficiaryShare-errors']";
	private String beneficiaryDesignation ="//*[@id='beneficiary-form-beneficiary']/div[4]/div/div[3]/div/fieldset/div[1]/div[1]/label";
	
	private String divBeneficiaryDesignationErrorXPath = "//div[@id = 'beneficiary-form-beneficiaryDesignation--error']";
	private String divBeneficiaryMinorChildErrorXPath = "//div[@id = 'beneficiary-form-beneficiary-form-beneficiaryMinorChild--error']";
	private String divNameATrusteeErrorXPath = "//div[@id = 'beneficiary-form-beneficiaryTrustee--error']";
	private String divTrusteeFirstNameErrorXPath = "(//div[@id = 'beneficiary-form-firstName-errors'])[2]";
	private String divTrusteeLastNameErrorXPath = "(//div[@id = 'beneficiary-form-lastName-errors'])[2]";
	private String divTrusteeRelationshipErrorXPath = "//div[@id = 'beneficiary-form-trusteeRelation-errors']";
	private String divPrimaryTotalShareErrorXPath = "//table[@role = 'table']//div[@id = 'totalShareError-error']";
	private String divSecondaryTotalShareErrorXPath = "(//table[@role = 'table'])[2]//div[@id = 'totalShareError-error']";
	private String labelShareXPath = "//label[@for = 'beneficiary-form-beneficiaryShare']";
	private String labelTrusteeRelationshipXPath = "//label[@id = 'beneficiary-form-trusteeRelation-label']";
	private String legendMinorChildLabelXPath = "//legend[@id = 'beneficiary-form-beneficiary-form-beneficiaryMinorChild-label']";
	
	private String labelBeneficiaryDesignationRevocableXPath = "//button[contains(@id,'beneficiaryDesignation-0')]";
	
	
	private String labelBeneficiaryDesignationIrrevocableXPath = "//button[contains(@id,'beneficiaryDesignation-1')]";
	
	private String labelNameBeneficiaryTrusteeYesXPath = "//button[contains(@id,'beneficiaryTrustee-0')]";
	private String labelNameBeneficiaryTrusteeNoXPath = "//button[contains(@id,'beneficiaryTrustee-1')]";
	
	
	private String labelIsBeneficiaryAMinorYesXPath = "//button[starts-with(@id,'beneficiary-form') and contains(@id,'beneficiary-form-beneficiaryMinorChild-0')]";	
	
	
	private String labelIsBeneficiaryAMinorNoXPath = "//button[starts-with(@id,'beneficiary-form') and contains(@id,'beneficiary-form-beneficiaryMinorChild-1')]";
	
		
	
	
	
	private String labelBeneficiaryRelationshipXPath = "//label[@id = 'beneficiary-form-beneficiaryRelation-label']";
	private String pDisclaimerNote1XPath = "//p[@id = 'beneficiary-disclaimer-1']";
	private String pDisclaimerNote2XPath = "//p[@id = 'beneficiary-disclaimer-2']";
	private String tdPrimaryShareXPath = "(//table[@role = 'table'])[1]//td[@title = 'totalBeneficiaryShareNumber']";
	private String tdSecondaryShareXPath = "(//table[@role = 'table'])[2]//td[@title = 'totalBeneficiaryShareNumber']";
	private String tdPrimaryTitleXPath = "(//table[@role = 'table'])[1]//td[@title = 'totalBeneficiaryShareTitle']";
	private String tdSecondaryTitleXPath = "(//table[@role = 'table'])[2]//td[@title = 'totalBeneficiaryShareTitle']";
	
	public BeneficiaryInformationPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	
	//Elements
	
	public WebElement getBneficiaryDesignation() {
		return this.driverUtil.getWebElement(By.xpath(beneficiaryDesignation));
	}
	
	public WebElement getAddBeneficiaryButton() {
		return this.driverUtil.getWebElement(By.xpath(buttonAddBeneficiaryXPath));
	}
	
	public WebElement getAddSecondaryBeneficiaryButton() {
		return this.driverUtil.getWebElement(By.xpath(buttonAddSecondaryBeneficiaryXPath));
	}
	
	public WebElement getAddBeneficiaryButton2() {
		return this.driverUtil.getWebElement(By.xpath(buttonAddBeneficiary2XPath));
	}
	
	public WebElement getAddSecondaryBeneficiaryButton2() {
		return this.driverUtil.getWebElement(By.xpath(buttonAddSecondaryBeneficiary2XPath));
	}
	
	public WebElement getBeneficiaryFirstName() {
		return this.driverUtil.getWebElement(By.xpath(inputBeneficiaryFirstNameXPath));
	}
	
	public WebElement getBeneficiaryMiddleName() {
		return this.driverUtil.getWebElement(By.xpath(inputBeneficiaryMiddleNameXPath));
	}
	
	public WebElement getBeneficiaryLastName() {
		return this.driverUtil.getWebElement(By.xpath(inputBeneficiaryLastNameXPath));
	}
	
	public String getOwnerInsuredRelationship() {
		return this.driverUtil.getText(By.xpath(divBeneficiaryRelationshipXPath));
	}
	
	public WebElement getBeneficiaryShare() {
		return this.driverUtil.getWebElement(By.xpath(inputBeneficiaryShareXPath));
	}
	
	public WebElement getBeneficiaryDesignation(String str) {
		String useXPath = (str.equals("Revocable")) ?  labelBeneficiaryDesignationRevocableXPath: (str.equals("Irrevocable")) ? labelBeneficiaryDesignationIrrevocableXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getBeneficiaryDesignationInput(String str) {
		String useXPath = (str.equals("Revocable")) ?  inputBeneficiaryDesignationRevocableXPath: (str.equals("Irrevocable")) ? inputBeneficiaryDesignationIrrevocableXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	public WebElement getBeneficiary2DesignationInput(String str) {
		String useXPath = (str.equals("Revocable")) ?  inputBeneficiary2DesignationRevocableXPath: (str.equals("Irrevocable")) ? inputBeneficiary2DesignationIrrevocableXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getIsBeneficiaryAMinor(String str) {
		String useXPath = (str.equals("Yes")) ?  labelIsBeneficiaryAMinorYesXPath: (str.equals("No")) ? labelIsBeneficiaryAMinorNoXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getIsBeneficiaryAMinorInput(String str) {
		String useXPath = (str.equals("Yes")) ?  inputIsBeneficiaryAMinorYesXPath: (str.equals("No")) ? inputIsBeneficiaryAMinorNoXPath : "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getNameBeneficiaryTrustee(String str) {
		String useXPath = (str.equals("Yes")) ? labelNameBeneficiaryTrusteeYesXPath: (str.equals("No")) ?  labelNameBeneficiaryTrusteeNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getNameBeneficiaryTrusteeInput(String str) {
		String useXPath = (str.equals("Yes")) ? inputNameBeneficiaryTrusteeYesXPath: (str.equals("No")) ?  inputNameBeneficiaryTrusteeNoXPath: "Error";
		return this.driverUtil.getWebElement(By.xpath(useXPath));
	}
	
	public WebElement getTrusteeFirstName() {
		return this.driverUtil.getWebElement(By.xpath(inputTrusteeFirstNameXPath));
	}
	
	public WebElement getTrusteeMiddleName() {
		return this.driverUtil.getWebElement(By.xpath(inputTrusteeMiddleNameXPath));
	}
	
	public WebElement getTrusteeLastName() {
		return this.driverUtil.getWebElement(By.xpath(inputTrusteeLastNameXPath));
	}
	
	public WebElement getTrusteeRelationship() {
		return this.driverUtil.getWebElement(By.xpath(divTrusteeRelationshipXPath));
	}
	
	public WebElement getSaveCloseBtn() {
		return this.driverUtil.getWebElement(By.xpath(buttonModalSaveCloseXPath));
	}
	
	public WebElement getCancelBtn() {
		return this.driverUtil.getWebElement(By.xpath(buttonCancelXPath));
	}
	
	//Labels
	public String getBeneficiaryOverlayHeader() {
		return this.driverUtil.getText(By.xpath(h1BeneficiaryOverlayHeaderXPath));
	}
	
	public String getBeneficiaryOverlayTitle() {
		return this.driverUtil.getText(By.xpath(h2BeneficiaryOverlayTitleXPath));
	}
		
	public String getDisclaimerNote1Label() {
		return this.driverUtil.getText(By.xpath(pDisclaimerNote1XPath));
	}
	
	public String getDisclaimerNote2Label() {
		return this.driverUtil.getText(By.xpath(pDisclaimerNote2XPath));
	}
	
	public String getInsuredOneBeneficiaryTitle() {
		return this.driverUtil.getText(By.xpath(h2Insured1BeneficiaryTitleXPath));
	}
	
	public String getInsuredTwoBeneficiaryTitle() {
		return this.driverUtil.getText(By.xpath(h2Insured2BeneficiaryTitleXPath));
	}
	
	public String getBeneficiaryNameLabel() {
		return this.driverUtil.getText(By.xpath(divBeneficiaryNameLabelXPath));
	}
	
	public String getBeneficiaryRelationshipLabel() {
		return this.driverUtil.getText(By.xpath(labelBeneficiaryRelationshipXPath));
	}
	
	public String getShareLabel() {
		return this.driverUtil.getText(By.xpath(labelShareXPath));
	}
	
	public String getShareLabelNote() {
		return this.driverUtil.getText(By.xpath(divShareNoteXPath)).replace("\n"," ");
	}
	
	public String getDesignationLabel() {
		return this.driverUtil.getText(By.xpath(divDesignationLabelXPath));
	}
	
	public String getQDesignationInstruction() {
		return this.driverUtil.getText(By.xpath(divQDesignationInstructionXPath));
	}
	
	public String getNQDesignationInstruction() {
		return this.driverUtil.getText(By.xpath(divNQDesignationInstructionXPath));
	}
	
	public String getDesignationNote() {
		return this.driverUtil.getText(By.xpath(divDesignationNoteXPath)).replace("\n"," ");
	}
	
	public String getMinorChildLabel() {
		return this.driverUtil.getText(By.xpath(legendMinorChildLabelXPath));
	}
	
	public String getNameATrusteeLabel() {
		return this.driverUtil.getText(By.xpath(divNameATrusteeXPath));
	}
	
	public String getNameATrusteeNoteLabel() {
		return this.driverUtil.getText(By.xpath(divNameATrusteeNoteXPAth));
	}
	
	public String getTrusteeNameLabel() {
		return this.driverUtil.getText(By.xpath(divTrusteeNameXPath));
	}
	
	public String getTrusteeRelationshipLabelXPath() {
		return this.driverUtil.getText(By.xpath(labelTrusteeRelationshipXPath));
	}
	
	public String getPrimaryShareNumber() {
		return this.driverUtil.getText(By.xpath(tdPrimaryShareXPath));
	}
	
	public String getSecondaryShareNumber() {
		return this.driverUtil.getText(By.xpath(tdSecondaryShareXPath));
	}
	
	public String getPrimaryShareTitle() {
		return this.driverUtil.getText(By.xpath(tdPrimaryTitleXPath));
	}
	
	public String getSecondaryShareTitle() {
		return this.driverUtil.getText(By.xpath(tdSecondaryTitleXPath));
	}
	
	public String[] getOwnerInsuredRelationshipLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divBeneficiaryRelationshipXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulBeneficiaryRelationshipXPath,"li");
		this.driverUtil.click(By.xpath(divBeneficiaryRelationshipXPath));
		return optionLabels;
	}
	
	public String[] getTrusteeRelationshipLabels() {
		CommonElements commonElements = new CommonElements(driverUtil);
		this.driverUtil.click(By.xpath(divTrusteeRelationshipXPath));
		String[] optionLabels = commonElements.getOptionLabels(ulTrusteeRelationshipXPath,"li");
		this.driverUtil.click(By.xpath(divTrusteeRelationshipXPath));
		return optionLabels;
	}
	
	public String[] getPrimaryTableHeader() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getTableHeader(tablePrimaryBeneficiaryXPath);
	}
	
	public String[] getSecondaryTableHeader() {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getTableHeader(tableSecondaryBeneficiaryXPath);
	}
	
	public String[] getPrimaryTableRow(Integer row) {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getTableBody(tablePrimaryBeneficiaryXPath,row);
	}
	
	public String[] getSecondaryTableRow(Integer row) {
		CommonElements commonElements = new CommonElements(driverUtil);
		return commonElements.getTableBody(tableSecondaryBeneficiaryXPath,row);
	}
	
	//Select Dropdown Option
	public void selectOwnerInsuredRelationship(String relationship) {
		this.driverUtil.click(By.xpath(divBeneficiaryRelationshipXPath));
		this.driverUtil.getWebElement(By.xpath(ulBeneficiaryRelationshipXPath)).findElement(By.xpath("//li[contains(text(),'"+relationship+"')]")).click();
	}
	
	public void selectTrusteeRelationship(String relationship) {
		this.driverUtil.click(By.xpath(divTrusteeRelationshipXPath));
		this.driverUtil.getWebElement(By.xpath(ulTrusteeRelationshipXPath)).findElement(By.xpath("//li[contains(text(),'"+relationship+"')]")).click();
	}
	
	//Select Change Delete
	public void selectChangeDelete(String fullName,String changeDelete) {
		this.driverUtil.getWebElement(By.xpath("//tr[./td/span[contains(text(),'"+fullName+"')]]/td/a[contains(text(),'"+changeDelete+"')]")).click();
	}
	
	//Is Visible
	public boolean isBeneficiaryModalVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(divBeneficiaryModalXPath));
	}
	
	public boolean isNameBeneficiaryTrusteeVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputNameBeneficiaryTrusteeYesXPath));
	}
	
	public boolean isTrusteeFirstNameVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputTrusteeFirstNameXPath));
	}
	
	public boolean isTrusteeMiddleNameVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputTrusteeMiddleNameXPath));
	}
	
	public boolean isTrusteeLastNameVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(inputTrusteeLastNameXPath));
	}
	
	public boolean isTrusteeRelationshipVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(divTrusteeRelationshipXPath));
	}
	
	public boolean isSecondaryAddBeneficiaryBtnVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(buttonAddSecondaryBeneficiaryXPath));
	}
	
	public boolean isPrimaryTableVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(tablePrimaryBeneficiaryXPath));
	}
	
	
	
	public boolean isSecondaryTableVisible() {
		return this.driverUtil.checkElementPresent(By.xpath(tableSecondaryBeneficiaryXPath));
	}
	
	
	//Errors
	public String getBeneficiaryFirstNameError() {
		return this.driverUtil.getText(By.xpath(divBeneficiaryFirstNameErrorXPath));
	}
	
	public String getBeneficiaryLastNameError() {
		return this.driverUtil.getText(By.xpath(divBeneficiaryLastNameErrorXPath));
	}
	
	public String getBeneficiaryRelationshipError() {
		return this.driverUtil.getText(By.xpath(divBeneficiaryRelationshipErrorXPath));
	}
	
	public String getBeneficiaryShareError() {
		return this.driverUtil.getText(By.xpath(divBeneficiaryShareErrorXPath));
	}
	
	public String getBeneficiaryDesignationError() {
		return this.driverUtil.getText(By.xpath(divBeneficiaryDesignationErrorXPath));
	}
	
	public String getBeneficiaryMinorChildError() {
		return this.driverUtil.getText(By.xpath(divBeneficiaryMinorChildErrorXPath));
	}
	
	public String getBeneficiaryNameATrusteeError() {
		return this.driverUtil.getText(By.xpath(divNameATrusteeErrorXPath));
	}
	
	public String getTrusteeFirstNameError() {
		return this.driverUtil.getText(By.xpath(divTrusteeFirstNameErrorXPath));
	}
	
	public String getTrusteeLastNameError() {
		return this.driverUtil.getText(By.xpath(divTrusteeLastNameErrorXPath));
	}
	
	public String getTrusteeRelationshipError() {
		return this.driverUtil.getText(By.xpath(divTrusteeRelationshipErrorXPath));
	}
	
	public String getPrimaryTotalShareError() {
		return (this.driverUtil.getWebElements(By.xpath(divPrimaryTotalShareErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divPrimaryTotalShareErrorXPath)) : "";
	}
	
	public String getSecondaryTotalShareError() {
		return (this.driverUtil.getWebElements(By.xpath(divSecondaryTotalShareErrorXPath)).size() > 0) ? this.driverUtil.getText(By.xpath(divSecondaryTotalShareErrorXPath)) : "";
	}
	
}