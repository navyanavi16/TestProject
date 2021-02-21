package term_conversion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class ThirdPartiesPage {
	private DriverUtil driverUtil;
	
	private String labelOtherPayorYesXPath = "//label[@for = 'policy-other-payor-id-1']";
	private String labelOtherPayorNoXPath = "//label[@for = 'policy-other-payor-id-2']";
	private String labelTransferOwnershipYesXPath = "//label[@for = 'ownership-id-1']";
	private String labelTransferOwnershipNoXPath = "//label[@for = 'ownership-id-2']";
	private String labelIndirectControlYesXPath = "//label[@for = 'control-id-1']";
	private String labelIndirectControlNoXPath = "//label[@for = 'control-id-2']";
	
	public ThirdPartiesPage(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}
	
	
	
}