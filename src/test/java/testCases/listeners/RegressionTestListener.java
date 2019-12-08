package testCases.listeners;

import actions.Constants;
import com.test.lengyel.actions.FrameworkAssertActions;
import com.test.lengyel.dependency.FrameworkDependencyManager;
import com.test.lengyel.dependency.FrameworkTestCaseStatusEnum;
import com.test.lengyel.gui.web.FrameworkDriverFactory;
import com.test.lengyel.testcase.FrameworkTestCase;
import com.test.lengyel.testcase.TestContextFactory;
import org.testng.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import testCases.TestCase;
import testCases.listeners.model.TestResultEntity;
import java.util.ArrayList;
import java.util.List;

public class RegressionTestListener extends TestCase implements IInvokedMethodListener, IMethodInterceptor {

	private static String testrunStartTime = null;
	private static boolean insertDB = false;
	private static String testsystem = "Applitools App";
	private static String testsuiteDefault = "Hackathon";
	

	@BeforeSuite
	public void beforeSuite() {
		String insertTestRunInDB = System.getProperty(Constants.INSERT_TESTRUN_PROPERTY, "false");
		insertDB = insertTestRunInDB.equalsIgnoreCase("true");
		if (insertDB) {
			testrunStartTime = getTestSuiteStartTime();
		}
		FrameworkDependencyManager.resetStatus(getAddition());
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			String market = null;
			String testDataName;
			if (testResult.getParameters()[0] != null) {
				testDataName = (String) testResult.getParameters()[0];
				String[] values = testDataName.split("_");
				market = trySetValue(values, 1);
			} else {
				testDataName = method.getTestMethod().getRealClass().getSimpleName();
			}
			testResult.setThrowable(null);
		}
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			String test = null;
			String testDataName;
			FrameworkTestCaseStatusEnum status = null;
			if (testResult.getParameters()[0] != null) {
				testDataName = (String) testResult.getParameters()[0];
				String[] values = testDataName.split("_");
				test = trySetValue(values, 0);
			} else {
				testDataName = method.getTestMethod().getRealClass().getSimpleName();
			}
			TestResultEntity testResultEntity = new TestResultEntity();
			testResultEntity.setTestrunStarttime(testrunStartTime);
			testResultEntity.setTestcaseName(test);
			testResultEntity.setCheckpoints(FrameworkAssertActions.getCheckPointsString());

			//check if checkpoints contain NOK
			boolean testFailed = testResultEntity.getCheckpoints().contains("[NOK]");

			//if (testResult.getThrowable() != null) {
			if(testFailed) {
				if (testResult.getThrowable() != null && testResult.getThrowable().getClass().equals(SkipException.class)) {
					testResultEntity.setTeststatus(TestResultEntity.BLOCKED);
					testResultEntity.setErrortext(testResult.getThrowable().getMessage());
					testResultEntity.setCauseOfError(testResult.getThrowable().getMessage());
					status = FrameworkTestCaseStatusEnum.BLOCKED;
				} else {
					testResultEntity.setTeststatus(TestResultEntity.FAILED);
					testResultEntity.setErrortext("Error");
					//testResultEntity.setErrortext(testResult.getThrowable().getMessage());
					//testResultEntity.setErrorline(testResult.getThrowable());
					try {
						testDataName = testDataName.substring(0, testDataName.indexOf("#"));
					} catch (Exception e) {
					}
					String screenshotName = testDataName;
					screenshotName += getAddition();
					testResultEntity.setScreenshot(getScreenshotLink(screenshotName));
					status = FrameworkTestCaseStatusEnum.FAILED;
				}
				//
				//
			} else {
				if (testResult.isSuccess()) {
					testResultEntity.setTeststatus(TestResultEntity.PASSED);
					status = FrameworkTestCaseStatusEnum.OK;
				} else {
					testResultEntity.setTeststatus(TestResultEntity.SKIPPED);
					status = FrameworkTestCaseStatusEnum.SKIPPED;
				}
			}
			testResultEntity.setEnvironment(System.getProperty(Constants.ENVIRONMENT_POS_PROPERTY,
					TestContextFactory.loadFunctionalSetting().getProperty(Constants.ENVIRONMENT_POS_PROPERTY)));
			testResultEntity.setTestsuite(System.getProperty(Constants.TESTSUITE_NAME_PROPERTY, testsuiteDefault));
			testResultEntity.setTestsystem(testsystem);
			//where to write results?
			//SmoketestDBHelper.callWriteTestresult(testResultEntity);
			FrameworkDependencyManager.setStatusForTestCaseAndTestChain(getTestChainID(testResultEntity), test, status, getAddition());
		}
	}

	private String getTestChainID(TestResultEntity testResultEntity) {
		String testChainID = "";
		if (testResultEntity.getMarket() != null)
			testChainID += testResultEntity.getMarket();
		if (testResultEntity.getCarType() != null)
			testChainID += testResultEntity.getCarType();
		if (testResultEntity.getBrand() != null)
			testChainID += testResultEntity.getBrand();
		if (testResultEntity.getCustomerType() != null)
			testChainID += testResultEntity.getCustomerType();
		if (testResultEntity.getFinancingType() != null)
			testChainID += testResultEntity.getFinancingType();
		//return testChainID;
		return "HAC";
	}

	private String getScreenshotLink(String testDataName) {
		String logPath = getProperty(Constants.SCREENSHOT_PROPERTY);
		return logPath + "/" + testDataName + ".png";
	}

	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		List<IMethodInstance> dummies = new ArrayList<IMethodInstance>();
		List<IMethodInstance> searches = new ArrayList<IMethodInstance>();
		List<IMethodInstance> ordering = new ArrayList<IMethodInstance>();
		List<IMethodInstance> create = new ArrayList<IMethodInstance>();
		List<IMethodInstance> result = new ArrayList<IMethodInstance>();
		for (IMethodInstance m : methods) {
			if (m.getMethod().getRealClass().getSimpleName().toLowerCase().contains("dummy")) {
				dummies.add(m);
			} else if (m.getMethod().getRealClass().getSimpleName().toLowerCase().contains("search")) {
				searches.add(m);
			} else if (m.getMethod().getRealClass().getSimpleName().toLowerCase().contains("create")) {
				create.add(m);
			} else if (m.getMethod().getRealClass().getSimpleName().toLowerCase().contains("getlog")) {
				result.add(m);
			} else {
				ordering.add(m);
			}
		}
		result.addAll(0, ordering);
		result.addAll(0, searches);
		result.addAll(0, create);
		result.addAll(0, dummies);
		return result;
	}

	@AfterSuite
	public void cleanUp() {
		FrameworkDriverFactory.quit();
		//what to do with results?
		//SmoketestDBHelper.closeConnection();
	}

}
