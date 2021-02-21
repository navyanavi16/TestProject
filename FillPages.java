package term_conversion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.ElementNotSelectableException;

import com.manulife.automation.selenium_execution.utils.DriverUtil;

public class FillPages {
	private DriverUtil driverUtil;
	String expectedBaseUrl=" https://qadistws.manulife.com/sps/auth?FedName=termconversionidp&FedId";

	public FillPages(DriverUtil driverUtil) {
		this.driverUtil = driverUtil;
	}

	public void goToNextPage(Boolean bln) {
		CommonElements commonElements = new CommonElements(driverUtil);
		if (bln) {
			commonElements.clickNextBtn();
		}
	}
	
	public void runSystemCommand(String command) {

		try {
			
			String ipAddress=command;
			InetAddress inet = InetAddress.getByName(ipAddress);
			System.out.println("Sending Ping Request to " + ipAddress);
			if (inet.isReachable(5000)) {
				System.out.println(ipAddress + " is reachable.");
			} else {
				System.out.println(ipAddress + " NOT reachable.");
				Assert.fail("Unable to connect host ("+ipAddress+")");
				
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	
	public void fillLogin() 
	{
		String userName ="Apiaadveng1";
		String password ="Tester11";
		LoginPage loginPage= new LoginPage(driverUtil);
		
		System.out.println("*************************launching new URL*******************");
			driverUtil.goToUrl("https://qadistws.manulife.com/sps/auth?FedName=termconversionidp&FedId=uuid1c641edb-0170-1f8d-80e8-80cd8296fdc8");
			runSystemCommand("qadistws.manulife.com");
			
			
	   System.out.println("login object is created");
	   boolean logged=loginPage.login(userName, password);		
	  Assert.assertTrue("After login, we are unable to find (Start a new application button) after 10 seconds",logged);
	  loginPage=null;
				
		

	}


	public void fillPolicyPage(Map<String, String> data) {
		PolicyInformationPage policyInfo = new PolicyInformationPage(driverUtil);
		policyInfo.getOriginalPolicyNumber().sendKeys(data.get("policyInfo_PolicyNumber"));
		policyInfo.selectOriginalPolicy(data.get("policyInfo_Policy"));
		if(data.get("policyInfo_Policy").equalsIgnoreCase("Former Commercial Union")) 
		{
			policyInfo.getCoverageIDNumber().sendKeys(data.get("PolicyInfo_CoverageIDNum"));
		}
		policyInfo.getIssuedInQuebec((data.get("policyInfo_IsIssuedQuebec").equals("Yes")) ? true : false).click();
		policyInfo.getDisabilityWaiver((data.get("policyInfo_DisabilityWaiver").equals("Yes")) ? true : false).click();
		if(data.get("policyInfo_DisabilityWaiver").equalsIgnoreCase("Yes"))
		{
			policyInfo.getUnableToPerformWork(data.get("PolicyInfo_UnableToPerformWork")).click();
		}
		policyInfo.selectProductType(data.get("policyInfo_Product"));
		policyInfo.selectCoverageType(data.get("policyInfo_CoverageType"));
		policyInfo.clickNext();
	}

	public void fillBasciOwnerInformation(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);

		String canadaTaxResident = data.get("ownerInfo_CanadaTaxResident");
		String providingSIN = data.get("ownerInfo_ProvidingSIN");
		String sinReason = data.get("ownerInfo_SINReason");
		String usTaxResident = data.get("ownerInfo_USTaxResident");
		String usSSNOption = data.get("ownerInfo_ProvidingSSN");
		String ssnNumberForUS = data.get("ownerInfo_SSN");
		String countryJuridiction = data.get("ownerInfo_Jurisdiction");
		String taxResidentOfJuridiction = data.get("ownerInfo_JurisdictionTaxResident");
		String providingTINOption = data.get("ownerInfo_ProvidingTIN");
		String inputTIN = data.get("ownerInfo_InputTIN");
		String reasonNotProvidingTIN = data.get("ownerInfo_ReasoNoTIN");
		String otherTINReason = data.get("ownerInfo_OtherTINReason");
		// employment info overlay page
		String employmentstatus = data.get("OwnerInfo_EmpStatus");

		ownerInfo.getAddress1().sendKeys(data.get("ownerInfo_Address1"));
		ownerInfo.getAddress2().sendKeys(data.get("ownerInfo_Address2"));
		ownerInfo.getCity().sendKeys(data.get("ownerInfo_City"));
		ownerInfo.selectProvince(data.get("ownerInfo_Province"));
		ownerInfo.getPostalCode().sendKeys(data.get("ownerInfo_PostalCode"));
		// ownerInfo.getSaveOwnerInfo().click();
		ownerInfo.getContinue().click(); // Tax Residence Form
		// Tax Resident Canada

		ownerInfo.getCATaxResident(canadaTaxResident, "1").click();
		if (canadaTaxResident.equalsIgnoreCase("Yes")) {

			ownerInfo.getSINProvided(providingSIN).click();
			if (providingSIN.equalsIgnoreCase("Yes")) {
				ownerInfo.getSIN().sendKeys(data.get("ownerInfo_SIN"));

			} else {

				ownerInfo.getNoSINReason().sendKeys(sinReason);

			}

		}

		ownerInfo.getUSTaxResident(usTaxResident).click();
		if (usTaxResident.equalsIgnoreCase("Yes")) {

			ownerInfo.getSSNProvided(usSSNOption).click();

			if (usSSNOption.equalsIgnoreCase("Yes")) {
				ownerInfo.getSSN().sendKeys(ssnNumberForUS);
			}

		}

		ownerInfo.getOtherTaxResident(taxResidentOfJuridiction).click();
		if (taxResidentOfJuridiction.equalsIgnoreCase("Yes")) {

			ownerInfo.selectJurisdiction(countryJuridiction);
			ownerInfo.getTINProvided(providingTINOption).click();
			if (providingTINOption.equalsIgnoreCase("Yes")) {

				ownerInfo.getTIN().sendKeys(inputTIN);
			} else if (providingTINOption.equalsIgnoreCase("No")) {
				ownerInfo.selectReasonNoTIN(reasonNotProvidingTIN);
				if (reasonNotProvidingTIN.equalsIgnoreCase("Other")) {
					ownerInfo.getSpecifyReasonNoTIN().sendKeys(otherTINReason);
				}

			}

		}
		ownerInfo.getContinue().click();

		// filling the employment information overlay page

		ownerInfo.selectEmploymentStatus(data.get("OwnerInfo_EmpStatus"));

		if (employmentstatus.equalsIgnoreCase("Never been employed")) 
		{
			ownerInfo.getContinue().click();
			fillIdentityVerificationOverlayPage(data);
		} else 
		{
			if (employmentstatus.equalsIgnoreCase("Self-employed"))
			{
				ownerInfo.getSoleProprietor(data.get("OwnerInfo_SoleProprietor")).click();
			}
			ownerInfo.selectIndustry(data.get("OwnerInfo_EmpIndustry"));
			ownerInfo.selectOccupation(data.get("OwnerInfo_Occupation"));
			ownerInfo.getNameOfYourCompany().sendKeys(data.get("OwnerInfo_NameOfCompany"));
			ownerInfo.getContinue().click();
			fillIdentityVerificationOverlayPage(data);
		}

	}

	public void fillIdentityVerificationOverlayPage(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		// Identity Verification overlay page
		String identificationMethod = data.get("OwnerInfo_IdentificationMethod");
		ownerInfo.selectIdentificationMethod(identificationMethod);
		switch (identificationMethod.toUpperCase()) {
		case "SINGLE METHOD":
			ownerInfo.getIdentificationDocument(data.get("OwnerInfo_IdentificationDocument")).click();
			if (!data.get("OwnerInfo_IdentificationDocument").equalsIgnoreCase("Federal")) {
				ownerInfo.selectProvinceIssuedIn(data.get("OwnerInfo_ProvinceIssuedIn"));
			}

			ownerInfo.selectOriginalDocumentReviewed(data.get("OwnerInfo_OriginalDocReviewed"));
			ownerInfo.getNameOnTheDocumentFirstName().sendKeys(data.get("ownerInfo_IndivOwnerFirstName"));
			ownerInfo.getNameOnTheDocumentMiddleInitial().sendKeys(data.get("ownerInfo_IndivOwnerMiddleInitial"));
			ownerInfo.getNameOnTheDocumentLastName().sendKeys(data.get("ownerInfo_IndivOwnerLastName"));
			ownerInfo.getIdentifyingNumberOnTheDocument().sendKeys(data.get("Identifying_Number"));
			ownerInfo.getDocumentExpiryDateDay().sendKeys("22");
			ownerInfo.selectMonthOFDocumentExpiryDate("February");

			ownerInfo.getDocumentExpiryDateYear().sendKeys("2025");
			ownerInfo.getDateInfoVerifiedDay().sendKeys("12");
			ownerInfo.selectMonthOFDateInfoVerified("January");
			ownerInfo.getDateInfoVerifiedYear().sendKeys("2017");

			ownerInfo.getSaveOwnerInfo().click();
			break;

		case "DUAL METHOD":
		case "CLIENT CREDIT FILE":
			ownerInfo.getSaveOwnerInfo().click();
			break;
		default:
			throw new ElementNotSelectableException(
					"selected option is not present in the Identification method dropdown");

		}

	}

	public void fillOwnerPage(Map<String, String> data) {
		OwnerInformationPage ownerInfo = new OwnerInformationPage(driverUtil);
		String insuredInfo_IsOwnerInsured = data.get("insuredInfo_IsOwnerInsured");
		String insuredInfo_IsOwnerInsuredTwo = data.get("insuredInfo_IsOwnerInsured2");
		String coverageType = data.get("policyInfo_CoverageType");
		CommonElements cs = new CommonElements(driverUtil);
		if (coverageType.contains("Joint")) {
			if (insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("Yes")
					&& insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes")) {
				ownerInfo.getEnterOwnerInfo().click();
				fillBasciOwnerInformation(data);
				ownerInfo.getEnterOwnerInfo2().click();
				cs.clickBackBtn();
				cs.clickBackBtn();
				cs.clickBackBtn();
				fillBasciOwnerInformation(data);

			} else if (insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("No")
					&& insuredInfo_IsOwnerInsured.equalsIgnoreCase("No")) {
				ownerInfo.getIndivOwnerFirstName("0").sendKeys(data.get("ownerInfo_IndivOwnerFirstName"));
				ownerInfo.getIndivOwnerMiddleInitial("0").sendKeys(data.get("ownerInfo_IndivOwnerMiddleInitial"));
				ownerInfo.getIndivOwnerLastName("0").sendKeys(data.get("ownerInfo_IndivOwnerLastName"));
				ownerInfo.selectRelationshipToInsured(data.get("ownerInfo_IndivOwnerRelationship"), "0");
				ownerInfo.getEnterOwnerInfo().click();

				ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("ownerInfo_IndivOwnerBirthDay"));
				ownerInfo.selectIndivOwnerBirthMonth(data.get("ownerInfo_IndivOwnerBirthMonth"), "0");
				ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("ownerInfo_IndivOwnerBirthYear"));
				fillBasciOwnerInformation(data);
				ownerInfo.getAddOwner2Button().click();
				ownerInfo.getIndivOwnerFirstName("1").sendKeys(data.get("owner2_FirstName"));
				ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(data.get("owner2_MiddleInitial"));
				ownerInfo.getIndivOwnerLastName("1").sendKeys(data.get("owner2_LastName"));
				ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
				ownerInfo.getEnterOwnerInfo2().click();
				// cs.clickBackBtn();
				// cs.clickBackBtn();
				// cs.clickBackBtn();
				ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("owner2_Day"));
				ownerInfo.selectIndivOwnerBirthMonth(data.get("owner2_Month"), "1");
				ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("owner2_Year"));
				fillBasciOwnerInformation(data);
			} else if ((insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("No")
					&& insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes"))
					|| (insuredInfo_IsOwnerInsuredTwo.equalsIgnoreCase("Yes")
							&& insuredInfo_IsOwnerInsured.equalsIgnoreCase("No"))) {

				ownerInfo.getEnterOwnerInfo().click();
				fillBasciOwnerInformation(data);

				ownerInfo.getAddOwner2Button().click();
				ownerInfo.getIndivOwnerFirstName("1").sendKeys(data.get("owner2_FirstName"));
				ownerInfo.getIndivOwnerMiddleInitial("1").sendKeys(data.get("owner2_MiddleInitial"));
				ownerInfo.getIndivOwnerLastName("1").sendKeys(data.get("owner2_LastName"));
				ownerInfo.selectRelationshipToInsured(data.get("owner2_Relationship"), "1");
				ownerInfo.getEnterOwnerInfo2().click();
				// cs.clickBackBtn();
				// cs.clickBackBtn();
				// cs.clickBackBtn();
				ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("owner2_Day"));
				ownerInfo.selectIndivOwnerBirthMonth(data.get("owner2_Month"), "1");
				ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("owner2_Year"));
				fillBasciOwnerInformation(data);

			}

		} else if (coverageType.contains("Single")) {
			if (insuredInfo_IsOwnerInsured.equalsIgnoreCase("Yes")) {
				ownerInfo.getEnterOwnerInfo().click();
				fillBasciOwnerInformation(data);
			} else {
				ownerInfo.getIndivOwnerFirstName("0").sendKeys(data.get("ownerInfo_IndivOwnerFirstName"));
				ownerInfo.getIndivOwnerMiddleInitial("0").sendKeys(data.get("ownerInfo_IndivOwnerMiddleInitial"));
				ownerInfo.getIndivOwnerLastName("0").sendKeys(data.get("ownerInfo_IndivOwnerLastName"));
				ownerInfo.selectRelationshipToInsured(data.get("ownerInfo_IndivOwnerRelationship"), "0");
				ownerInfo.getEnterOwnerInfo().click();
				ownerInfo.getIndivOwnerBirthDay().sendKeys(data.get("ownerInfo_IndivOwnerBirthDay"));
				ownerInfo.selectIndivOwnerBirthMonth(data.get("ownerInfo_IndivOwnerBirthMonth"), "0");
				ownerInfo.getIndivOwnerBirthYear().sendKeys(data.get("ownerInfo_IndivOwnerBirthYear"));
				fillBasciOwnerInformation(data);
			}
		}

		cs.clickNextBtn();

	}

	public void fillThirdPartiesPage(Map<String, String> data) {
		ThirdPartyInformationPage thirdPartyPage = new ThirdPartyInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);

		thirdPartyPage.getOtherPayor(data.get("thirdParty_OtherPayer")).click();
		thirdPartyPage.getTransferOwnership(data.get("thirdParty_TransferOwnership")).click();
		thirdPartyPage.getIndirectControl(data.get("thirdParty_IndirectControl")).click();
		commonElements.clickNextBtn();
	}

	public void fillSuccessorPage(Map<String, String> data) {
		SuccessorSubrogatedOwnerPage successorInfo = new SuccessorSubrogatedOwnerPage(driverUtil);
		String nameaSuccessor = data.get("successorInfo_NameASuccessor");
		successorInfo.nameASuccessorOwnerLabel((nameaSuccessor.equals("Yes")) ? true : false).click();
		if (nameaSuccessor.equalsIgnoreCase("Yes")) {
			successorInfo.getSubrogatedFirstName().sendKeys(data.get("successorInfo_FirstName"));
			successorInfo.getSubrogatedMidleName().sendKeys(data.get("successorInfo_MiddleInitial"));
			successorInfo.getSubrogatedLastName().sendKeys(data.get("successorInfo_LastName"));
			successorInfo.selectRelation(data.get("successorInfo_Relationship"));
		}
		successorInfo.nextButton();
	}

	public void fillInsuredPage(Map<String, String> data) {
		InsuredInformationPage insuredInfo = new InsuredInformationPage(driverUtil);
		// Fill in Insured person one field

		String insuredInfo_IsOwnerInsured = data.get("insuredInfo_IsOwnerInsured");

		insuredInfo.ClickOngIsOwnerInsuredOnPolicyButton(insuredInfo_IsOwnerInsured, 0);

		// Fill in insured person 1 fields if there is no individual owner 1 or the
		// individual owner 1 is not the insured
		String policyInfo_CoverageType = data.get("policyInfo_CoverageType");

		insuredInfo.getFirstNamePerson(0).sendKeys(data.get("insuredInfo_FirstName"));
		insuredInfo.getMiddleInitialPerson(0).sendKeys(data.get("insuredInfo_MiddleInitial"));
		insuredInfo.getLastNamePerson(0).sendKeys(data.get("insuredInfo_LastName"));
		insuredInfo.getInsuredPerson1Sex((data.get("insuredInfo_Sex").equals("Male")) ? true : false, 0).click();
		insuredInfo.getBirthDayPerson1().sendKeys(data.get("insuredInfo_Day"));
		insuredInfo.selectBirthMonthPerson1(data.get("insuredInfo_Month"));
		insuredInfo.getBirthYearPerson1().sendKeys(data.get("insuredInfo_Year"));
		if (policyInfo_CoverageType.contains("Joint")) {
			insuredInfo.ClickOngIsOwnerInsuredOnPolicyButton(data.get("insuredInfo_IsOwnerInsured2"), 1);
			insuredInfo.getFirstNamePerson(1).sendKeys(data.get("insuredInfo_FirstName2"));
			insuredInfo.getMiddleInitialPerson(1).sendKeys(data.get("insuredInfo_MiddleInitial2"));
			insuredInfo.getLastNamePerson(1).sendKeys(data.get("insuredInfo_LastName2"));
			insuredInfo.getInsuredPerson1Sex((data.get("insuredInfo_Sex").equals("Male")) ? true : false, 1).click();
			insuredInfo.getBirthDayPerson2().sendKeys(data.get("insuredInfo_Day2"));
			insuredInfo.selectBirthMonthPerson2(data.get("insuredInfo_Month2"));
			insuredInfo.getBirthYearPerson2().sendKeys(data.get("insuredInfo_Year2"));
		}
		insuredInfo.clickNextButton();
	}

	public void fillCoveragePage(Map<String, String> data) {

		CoverageInformationPage coverageInfo = new CoverageInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		coverageInfo.getCoverageNumber().sendKeys(data.get("coverageInfo_CoverageNumber"));
		String coverageAmount = data.get("coverageInfo_CoverageAmount");
		String convertingAmount = data.get("coverageInfo_ConvertingAmount");

		int remainingAmout = Integer.parseInt(coverageAmount) - Integer.parseInt(convertingAmount);

		coverageInfo.getCoverageAmount().sendKeys(coverageAmount);
		coverageInfo.getConvertingAmount().sendKeys(convertingAmount);
		if (data.get("policyInfo_CoverageType").equals("Single life")) {
			if (remainingAmout > 0) {
				String tirAmount = data.get("coverageInfo_CarriedTIRAmount");
				coverageInfo.getCarriedTIRAmount().sendKeys(tirAmount);
				remainingAmout = remainingAmout - Integer.parseInt(tirAmount);

			}

		}
		if (remainingAmout > 0) {
			coverageInfo.getKeepAmount().sendKeys((Integer.toString(remainingAmout)));
		}

		commonElements.clickNextBtn();
	}

	public void fillProductPage(Map<String, String> data) {
		ProductInformationPage productInfo = new ProductInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		String coverageType = data.get("policyInfo_CoverageType");

		if (data.get("policyInfo_Product").equals("Manulife Par")) {

			productInfo.getSmokerStatusOption(data.get("productInfo_SmokerStatus")).click();
			if (coverageType.contains("Joint")) {

				productInfo.getSmoker2StatusOption(data.get("productInfo_SmokerStatus")).click();

			}
			productInfo.getPremiumDurationOption(data.get("productInfo_PremiumDuration")).click();
			productInfo.getDividendOption(data.get("productInfo_DividendOption")).click();
			String dividendOption = data.get("productInfo_DividendOption");
			if (dividendOption.equalsIgnoreCase("Paid-up insurance")) {
				productInfo.getDepositOptionPaymentBln("Yes").click();
				productInfo.getDepositMonthlyPaymentBln("Yes").click();
				productInfo.getDepositPaymentMonthlyAmount().sendKeys("500");
				productInfo.getTotalDepositPaymentAmount().sendKeys("500");

			}

			if (!coverageType.contains("Joint")) {
				productInfo.getPremiumOption(data.get("productInfo_PremiumOption")).click();
			}

		} else if (data.get("policyInfo_Product").equals("Performax Gold")) {

			productInfo.getHealthStyleOption(data.get("productInfo_HealthStyle")).click();
			if (!data.get("policyInfo_CoverageType").equals("Single life")) {
				productInfo.getHealthStyleTwoOption(data.get("productInfo_HealthStyleTwo")).click();
			}
			productInfo.getCostDurationOption(data.get("productInfo_CostDuration")).click();
			String performanceCreditValue = data.get("productInfo_PerformanceCreditOption");
			productInfo.getPerformanceCreditOption(performanceCreditValue).click();

			if (performanceCreditValue.equalsIgnoreCase("Term option amount")) {

				productInfo.getTermOptionAmount().sendKeys(data.get("productInfo_InputTermOptionAmount"));
			}
			if (data.get("policyInfo_CoverageType").equals("Single life")
					&& (!data.get("coverageInfo_CarriedTIRAmount").isEmpty())
					&& (!data.get("coverageInfo_CarriedTIRAmount").equals("0"))) {
				productInfo.getCostOfInsuranceOption(data.get("productInfo_CostOfInsurance")).click();
			}
		}

		commonElements.clickNextBtn();
	}

	public void fillBenificiaryPage(Map<String, String> data) {
		BeneficiaryInformationPage beneficiaryInfo = new BeneficiaryInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);

		String quebecOptions = data.get("policyInfo_IsIssuedQuebec");

		beneficiaryInfo.getAddBeneficiaryButton().click(); // Open Primary Beneficiary Overlay

		String firstNameTestStr = data.get("beneficiaryInfo_TestFirstName");
		String middleNameTestStr = data.get("beneficiaryInfo_TestMiddleName");
		String lastNameTestStr = data.get("beneficiaryInfo_TestLastName");
		beneficiaryInfo.getBeneficiaryFirstName().sendKeys(firstNameTestStr);
		beneficiaryInfo.getBeneficiaryMiddleName().sendKeys(middleNameTestStr);
		beneficiaryInfo.getBeneficiaryLastName().sendKeys(lastNameTestStr);
		beneficiaryInfo.selectOwnerInsuredRelationship(data.get("beneficiaryInfo_TestRelationshipEN"));

		beneficiaryInfo.getBeneficiaryShare().sendKeys(data.get("beneficiaryInfo_TestShare"));
		beneficiaryInfo.getBeneficiaryDesignation(data.get("beneficiaryInfo_TestDesignationEN")).click();

		String minorStatus = data.get("beneficiaryInfo_MinorChild");
		String trustyStatus = data.get("beneficiaryInfo_NameATrustee");

		if (quebecOptions.equalsIgnoreCase("Yes")) {
			beneficiaryInfo.getSaveCloseBtn().click();

		} else if (quebecOptions.equalsIgnoreCase("No")) {

			beneficiaryInfo.getIsBeneficiaryAMinor(minorStatus).click();

			
			if (minorStatus.equalsIgnoreCase("Yes")) {

				beneficiaryInfo.getNameBeneficiaryTrustee(trustyStatus).click();
				
				if (trustyStatus.equalsIgnoreCase("Yes")) {
					beneficiaryInfo.getTrusteeFirstName().sendKeys(data.get("beneficiaryInfo_TrusteeFirstName"));
					beneficiaryInfo.getTrusteeMiddleName().sendKeys(data.get("beneficiaryInfo_TrusteeMiddleName"));
					beneficiaryInfo.getTrusteeLastName().sendKeys(data.get("beneficiaryInfo_TrusteeLastName"));
					beneficiaryInfo.selectTrusteeRelationship(data.get("beneficiaryInfo_TrusteeRelationship"));
				}

			}
			beneficiaryInfo.getSaveCloseBtn().click();
		}

		

		if (!data.get("policyInfo_CoverageType").equals("Single life")) {
			beneficiaryInfo.getAddBeneficiaryButton2().click();
			String firstNameTestStr2 = data.get("beneficiaryInfo_TestFirstName2");
			String middleNameTestStr2 = data.get("beneficiaryInfo_TestMiddleName2");
			String lastNameTestStr2 = data.get("beneficiaryInfo_TestLastName2");

			beneficiaryInfo.getBeneficiaryFirstName().sendKeys(firstNameTestStr2);
			beneficiaryInfo.getBeneficiaryMiddleName().sendKeys(middleNameTestStr2);
			beneficiaryInfo.getBeneficiaryLastName().sendKeys(lastNameTestStr2);
			beneficiaryInfo.selectOwnerInsuredRelationship(data.get("beneficiaryInfo_TestRelationshipEN2"));

			beneficiaryInfo.getBeneficiaryShare().sendKeys(data.get("beneficiaryInfo_TestShare2"));
			beneficiaryInfo.getBeneficiaryDesignation(data.get("beneficiaryInfo_TestDesignationEN2")).click();
			
			minorStatus = data.get("beneficiaryInfo_MinorChild2");
			
			if (quebecOptions.equalsIgnoreCase("Yes")) {
				beneficiaryInfo.getSaveCloseBtn().click();

			} else if (quebecOptions.equalsIgnoreCase("No"))
			
			{
				beneficiaryInfo.getIsBeneficiaryAMinor(minorStatus).click();
				trustyStatus = data.get("beneficiaryInfo_NameATrustee2");
				if (minorStatus.equalsIgnoreCase("Yes")) {

					beneficiaryInfo.getNameBeneficiaryTrustee(trustyStatus).click();
					if (trustyStatus.equalsIgnoreCase("Yes")) {
						beneficiaryInfo.getTrusteeFirstName().sendKeys(data.get("beneficiaryInfo_TrusteeFirstName"));
						beneficiaryInfo.getTrusteeMiddleName().sendKeys(data.get("beneficiaryInfo_TrusteeMiddleName"));
						beneficiaryInfo.getTrusteeLastName().sendKeys(data.get("beneficiaryInfo_TrusteeLastName"));
						beneficiaryInfo.selectTrusteeRelationship(data.get("beneficiaryInfo_TrusteeRelationship"));
					}

				}
				beneficiaryInfo.getSaveCloseBtn().click();
			}

		}
		commonElements.clickNextBtn();

	}

	public void fillBillingandpaymentPage(Map<String, String> data) {
		BillingInformationPage billingInfo = new BillingInformationPage(driverUtil);
		CommonElements commonElements = new CommonElements(driverUtil);
		String firstPayment = data.get("billingInfo_FirstPaymentAmount");
		String firstPaymentAmountOptions = data.get("billingInfo_FirstPaymentDropdown");
		String paymentMethod = data.get("billingInfo_PaymentMethod");
		String nameOfCandianBank = data.get("billingInfo_BankName");
		String transitNumber = data.get("billingInfo_TransitNumber");
		String institutionNumber = data.get("billingInfo_InstitutionNumber");
		String accountNumber = data.get("billingInfo_AccountNumber");
		String policyNumberForPAD = data.get("billingInfo_PolicyNumber");

		String insuredPersonTwo = data.get("ownerInfo_IndivOwnerFirstName") + " "
				+ data.get("ownerInfo_IndivOwnerMiddleInitial") + " " + data.get("ownerInfo_IndivOwnerLastName");
		// if we have one owner then under "Who will pay the premiums?" field the owner
		// checkbox is disabled by default
		// if we have two owners then under this section "Who will pay the premiums?"
		// then the two checkboxes
		// will display for each owner and we need to select either one or both owners

		String coverageType = data.get("policyInfo_CoverageType");

		if (coverageType.contains("Joint")) {
			billingInfo.SelectOwner(0);
			billingInfo.SelectOwner(1);
		}

		billingInfo.getFirstPaymentBln("Yes").click();
		billingInfo.selectFirstPaymentAmount(firstPaymentAmountOptions);
		billingInfo.getFirstPaymentValue().sendKeys(firstPayment);

		billingInfo.selectFirstPaymentMethod(paymentMethod);
		if (paymentMethod.equalsIgnoreCase("Add this pre-authorized debit (PAD) to an existing PAD plan")) {
			billingInfo.getPADPolicyNumber().sendKeys(policyNumberForPAD);

		} else if (paymentMethod.equalsIgnoreCase("Set up a new PAD plan")) {
			billingInfo.getBankName().sendKeys(nameOfCandianBank);
			billingInfo.getTransitNumber().sendKeys(transitNumber);
			billingInfo.getInstitutionNumber().sendKeys(institutionNumber);
			billingInfo.getAccountNumber().sendKeys(accountNumber);
		}

		if (data.get("policyInfo_Product").equals("Performax Gold")) {
			billingInfo.getCoverPolicyCostsAmount().sendKeys(firstPayment);
		}
		billingInfo.getFrequencyPayment("Monthly").click(); // click out of field

		if (data.get("policyInfo_Product").equals("Manulife Par")) {

			billingInfo.getMonthlyAnnuallyPremiumAmount().sendKeys(data.get("billingInfo_MonthlyPremiumAmount"));
		}
		commonElements.clickNextBtn();

	}

	public void fillAttachmentspage(Map<String, String> data) throws InterruptedException {

		AttachmentPage attachmentPage = new AttachmentPage(driverUtil);
		// click next button
		CommonElements commonElements = new CommonElements(driverUtil);
		try {
			attachmentPage.clickOnAttachDocumenButton();
			attachmentPage.selectDropDown("Beneficiary designation for life insurance form, NN0283");
			attachmentPage.UploadAttachedImage("logo.jpg");

			commonElements.clickNextBtn();
		} catch (Exception ex) {
			Assert.fail(ex.getMessage());
		}

	}

	public void fillAdvisorpage(Map<String, String> data) throws InterruptedException {

		AdvisorInformationPage advisorPage = new AdvisorInformationPage(driverUtil);
		// String advisorCode = data.get("Advisor_Code");
		// advisorPage.selectAdvisorCode(0, advisorCode);
		advisorPage.getDidYouButton("Yes").click();
		advisorPage.getMeAboutYourSelf().sendKeys("Yes I am doing");
		CommonElements commonElements = new CommonElements(driverUtil);
		commonElements.clickNextBtn();

	}

}