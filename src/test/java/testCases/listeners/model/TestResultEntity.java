package testCases.listeners.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.TreeMap;

public class TestResultEntity {

	public static final String FAILED = "failed";
	public static final String PASSED = "passed";
	public static final String BLOCKED = "blocked";
	public static final String SKIPPED = "skipped";
	/**
	 * FieldNames HAVE to match the ColumnNames in the DB
	 */

	private String testrunStarttime;
	private String environment;
	private String testsystem;
	private String testsuite;
	private String teststatus;
	private String testcaseName;
	private String market;
	private String brand;
	private String customerType;
	private String financingType;
	private String baumuster;
	private String carType;
	private String leadID;
	private String fscaID;
	private String fscaDescription;
	private String fscaProduct;
	private String fscaProductLine;
	private String fscaDate;
	private String fscaCampaign;
	private String fscaContractLength;
	private String fscaAnnualMileage;
	private String fscaDeposit;
	private String screenshot;
	private String errortext;
	private String errorline;
	private String causeOfError;
	private String proposal;
	private String user;
	private String userCesar;
	private String vin;
	private String comissionNumber;
	private String preorderNumber;
	private String leadNumber;
	private String salesAlternativeNumber;
	private String verificationResult;
	private String domain;
	private String subDomain;
	private String dealerOptionType;
	private String externalSystem;
	private String checkpoints;
	private String proposalTimestamp;
	private String customerName;
	private TreeMap<String, String> milestonesMap;
	private String milestones;

	public String getTestrunStarttime() {
		return testrunStarttime;
	}

	public void setTestrunStarttime(String testrunStarttime) {
		this.testrunStarttime = testrunStarttime;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getTestsystem() {
		return testsystem;
	}

	public void setTestsystem(String testsystem) {
		this.testsystem = testsystem;
	}

	public String getTestsuite() {
		return testsuite;
	}

	public void setTestsuite(String testsuite) {
		this.testsuite = testsuite;
	}

	public String getTeststatus() {
		return teststatus;
	}

	public void setTeststatus(String teststatus) {
		this.teststatus = teststatus;
	}

	public String getTestcaseName() {
		return testcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getFinancingType() {
		return financingType;
	}

	public void setFinancingType(String financingType) {
		this.financingType = financingType;
	}

	public String getBaumuster() {
		return baumuster;
	}

	public void setBaumuster(String baumuster) {
		this.baumuster = baumuster;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getLeadID() {
		return leadID;
	}

	public void setLeadID(String leadID) {
		this.leadID = leadID;
	}

	public String getFscaID() {
		return fscaID;
	}

	public void setFscaID(String fscaID) {
		this.fscaID = fscaID;
	}

	public String getFscaDescription() {
		return fscaDescription;
	}

	public void setFscaDescription(String fscaDescription) {
		this.fscaDescription = fscaDescription;
	}

	public String getFscaProduct() {
		return fscaProduct;
	}

	public void setFscaProduct(String fscaProduct) {
		this.fscaProduct = fscaProduct;
	}

	public String getFscaProductLine() {
		return fscaProductLine;
	}

	public void setFscaProductLine(String fscaProductLine) {
		this.fscaProductLine = fscaProductLine;
	}

	public String getFscaDate() {
		return fscaDate;
	}

	public void setFscaDate(String fscaDate) {
		this.fscaDate = fscaDate;
	}

	public String getFscaCampaign() {
		return fscaCampaign;
	}

	public void setFscaCampaign(String fscaCampaign) {
		this.fscaCampaign = fscaCampaign;
	}

	public String getFscaContractLength() {
		return fscaContractLength;
	}

	public void setFscaContractLength(String fscaContractLength) {
		this.fscaContractLength = fscaContractLength;
	}

	public String getFscaAnnualMileage() {
		return fscaAnnualMileage;
	}

	public void setFscaAnnualMileage(String fscaAnnualMileage) {
		this.fscaAnnualMileage = fscaAnnualMileage;
	}

	public String getFscaDeposit() {
		return fscaDeposit;
	}

	public void setFscaDeposit(String fscaDeposit) {
		this.fscaDeposit = fscaDeposit;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public String getErrortext() {
		return errortext;
	}

	public void setErrortext(String errortext) {
		this.errortext = errortext;
	}

	public String getErrorline() {
		return errorline;
	}

	public void setErrorline(String errorline) {
		this.errorline = errorline;
	}

	public String getCauseOfError() {
		return causeOfError;
	}

	public void setCauseOfError(String causeOfError) {
		this.causeOfError = causeOfError;
	}

	public String getProposal() {
		return proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
	}

	public String getDictionaryForDB() {
		if (testrunStarttime == null || testrunStarttime.length() == 0) {
			throw new RuntimeException("No correct testrunStarttime provided");
		}
		if (testsystem == null || testsystem.length() == 0) {
			throw new RuntimeException("No correct testsystem provided");
		}
		if (testsuite == null || testsuite.length() == 0) {
			throw new RuntimeException("No correct testsuite provided");
		}
		StringBuilder dictionary = new StringBuilder();
		dictionary.append(";");
		Field[] fields = TestResultEntity.class.getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().equals(String.class)
					&& Modifier.isPrivate(field.getModifiers())) {
				field.setAccessible(true);
				dictionary.append(getAppendValueForDictionaryForField(field));
			}
		}
		return dictionary.toString();
	}

	private String getAppendValueForDictionaryForField(Field field) {

		StringBuilder appendValue = new StringBuilder();
		try {
			String value = (String) field.get(this);
			if (value != null) {
				appendValue.append(field.getName());
				appendValue.append("=");
				appendValue.append(value.substring(0,
						Math.min(20000, Math.max(0, value.length()))));
				System.out.println("Value length to store: " + Math.min(80000, Math.max(0, value.length())));
				appendValue.append(";");
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return "";
		}
		return appendValue.toString();
	}

	public void setErrorline(Throwable throwable) {
		for (StackTraceElement element : throwable.getStackTrace()) {
			if (element.getClassName().toLowerCase().contains("pages")) {
				this.errorline = element.toString();
			}
		}
		if (this.errorline == null) {
			for (StackTraceElement element : throwable.getStackTrace()) {
				if (element.getClassName().toLowerCase().contains("assert")) {
					this.errorline = element.toString();
				}
			}
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUserCesar() {
		return userCesar;
	}

	public void setUserCesar(String userCesar) {
		this.userCesar = userCesar;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getComissionNumber() {
		return comissionNumber;
	}

	public void setComissionNumber(String comissionNumber) {
		this.comissionNumber = comissionNumber;
	}

	public String getPreorderNumber() {
		return preorderNumber;
	}

	public void setPreorderNumber(String preorderNumber) {
		this.preorderNumber = preorderNumber;
	}

	public String getLeadNumber() {
		return leadNumber;
	}

	public void setLeadNumber(String leadNumber) {
		this.leadNumber = leadNumber;
	}

	public String getSalesAlternativeNumber() {
		return salesAlternativeNumber;
	}

	public void setSalesAlternativeNumber(String salesAlternativeNumber) {
		this.salesAlternativeNumber = salesAlternativeNumber;
	}

	public void setVerificationResult(String verificationResult) {
		this.verificationResult = verificationResult;
	}

	public String getVerificationResult() {
		return verificationResult;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSubDomain() {
		return subDomain;
	}

	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}

	public String getDealerOptionType() {
		return dealerOptionType;
	}

	public void setDealerOptionType(String dealerOptionType) {
		this.dealerOptionType = dealerOptionType;
	}

	public String getExternalSystem() {
		return externalSystem;
	}

	public void setExternalSystem(String externalSystem) {
		this.externalSystem = externalSystem;
	}

	public String getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(String checkpoints) {
		this.checkpoints = checkpoints;
	}

	public ArrayList<String> getExcelRowFS() {
		ArrayList<String> columns = new ArrayList<String>();

		// columns with most important attributes
		columns.add(teststatus);
		columns.add(testcaseName);
		columns.add(market);
		columns.add(brand);
		columns.add(customerType);
		columns.add(financingType);
		columns.add(baumuster);
		columns.add(fscaID);
		columns.add(fscaProduct);
		columns.add(fscaCampaign);
		columns.add(salesAlternativeNumber);
		columns.add(leadID);

		// columns for error analysis
		columns.add(screenshot);
		columns.add(checkpoints);
		columns.add(errortext);
		columns.add(errorline);

		// columns with less important FSCA attributes
		columns.add(fscaDate);
		columns.add(fscaAnnualMileage);
		columns.add(fscaDeposit);
		columns.add(fscaContractLength);

		return columns;
	}

	public ArrayList<String> getExcelHeaderFS() {

		ArrayList<String> columns = new ArrayList<String>();

		// columns with most important attributes
		columns.add("Status");
		columns.add("Test Case");
		columns.add("Market");
		columns.add("Brand");
		columns.add("Customer type");
		columns.add("Financing type");
		columns.add("Baumuster");
		columns.add("FSCA ID");
		columns.add("FSCA Product");
		columns.add("FSCA Campaign");
		columns.add("Sales Alternative ID");
		columns.add("Lead ID");

		// columns for error analysis
		columns.add("Screenshot");
		columns.add("Checkpoints");
		columns.add("Error text");
		columns.add("Error line");

		// columns with less important FSCA attributes
		columns.add("FSCA Date");
		columns.add("Annual mileage");
		columns.add("Deposit");
		columns.add("Contract Length");

		return columns;
	}

	public String getProposalTimestamp() {
		return proposalTimestamp;
	}

	public void setProposalTimestamp(String proposalTimestamp) {
		this.proposalTimestamp = proposalTimestamp;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	public ArrayList<String> getExcelHeaderPOS() {

		ArrayList<String> columns = new ArrayList<String>();

		// columns with most important attributes
		columns.add("Status");
		columns.add("Test Case");
		columns.add("Market");
		columns.add("Brand");
		columns.add("Baumuster");
		columns.add("Sales Alternative ID");
		columns.add("Lead ID");
		columns.add("Milestones");
		columns.add("Error text");
		columns.add("Error line");
		columns.add("Screenshot");
		
		return columns;
	}

	public ArrayList<String> getExcelRowPOS() {
		ArrayList<String> rows = new ArrayList<String>();

		// rows with most important attributes
		rows.add(teststatus);
		rows.add(testcaseName);
		rows.add(market);
		rows.add(brand);
		rows.add(baumuster);
		rows.add(salesAlternativeNumber);
		rows.add(leadID);
		rows.add(milestones);
		rows.add(errortext);
		rows.add(errorline);
		rows.add(screenshot);

		return rows;
	}

	
}
	

