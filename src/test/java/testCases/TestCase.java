package testCases;

import actions.*;
import com.test.lengyel.constants.FrameworkConstants;
import com.test.lengyel.testcase.FrameworkTestCase;
import com.test.lengyel.testcase.web.FrameworkWebTestCase;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.internal.InvokedMethod;
import testCases.listeners.ScreenshotOnFailureListener;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Listeners(ScreenshotOnFailureListener.class)
public class TestCase extends FrameworkWebTestCase {

	protected Actions actions;
	protected Navigation navigation;
	protected Check check;
	protected Preparation preparation;
	private static String domain;
	private static String subDomain;
	

	@BeforeMethod
	public void setUpTemplates() {
		actions = new Actions(testContext);
		navigation = new Navigation(testContext);
		check = new Check(testContext);
		preparation = new Preparation(testContext);
	}

	@BeforeClass
	public void setEnvironment() {
		useDriverPool = false;
		// these intervals wont be used see
		// com.test.sogeti.gui.web.SGTGrapheneConfiguration
		// System.setProperty("waitGuiInterval", "240");
		// System.setProperty("waitAjaxInterval", "240");
		// System.setProperty("waitModelInterval", "240");
		// System.setProperty("waitGuardInterval", "240");
	}

	public void setSubDomain(String subdomain) {
		TestCase.subDomain = subdomain;
	}

	public void setDomain(String domain) {
		TestCase.domain = domain;
	}

	public String getDomain() {
		return domain;
	}

	public String getSubDomain() {
		return subDomain;
	}

	protected String getTestSuiteStartTime() {
		String startTime = System.getProperty(Constants.TESTRUN_START_TIME_PROPERTY);
		System.out.println("start time property" + startTime);
		if (startTime == null) {
			DateFormat formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
			Date currentDate = new Date();
			startTime = formatter.format(currentDate);
			System.out.println("start time current" + startTime);
		}
		return startTime;
	}

	protected String getAddition() {
		String numberCacheAddition = getProperty(FrameworkConstants.NUMBERCACHE_ADDITION_PROPERTY);
		if (numberCacheAddition == null) {
			numberCacheAddition = "";
		}
		return numberCacheAddition;
	}

	protected void setTestChainID(IInvokedMethod method, String testChainID) {
		Field privateStringField;
		try {
			privateStringField = InvokedMethod.class.getDeclaredField("m_instance");
			privateStringField.setAccessible(true);
			FrameworkTestCase fieldValue = (FrameworkTestCase) privateStringField.get(method);
			fieldValue.getTestContext().setTestChainID(testChainID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String trySetValue(String[] values, int i) {
		try {
			String returnValue = values[i];
			if (returnValue.contains(FrameworkConstants.TESTDATA_ADDITION)) {
				returnValue = returnValue.substring(0, returnValue.indexOf(FrameworkConstants.TESTDATA_ADDITION));
			}
			return returnValue;
		} catch (Exception e) {
			return "";
		}
	}

	protected String getScreenshotName(IInvokedMethod method, ITestResult testResult) {
		String screenshotName;
		if (testResult.getParameters()[0] != null) {
			screenshotName = (String) testResult.getParameters()[0];
		} else {
			screenshotName = method.getTestMethod().getRealClass().getSimpleName();
		}

		screenshotName += getAddition();
		return screenshotName;
	}


	protected String getTestCaseName() {
		String testDataName = getTestContext().getTestDataName();
		String[] values = testDataName.split("_");
		String testCaseName = trySetValue(values, 0);
		return testCaseName;
	}

	
}
