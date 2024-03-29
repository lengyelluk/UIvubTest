package testCases.listeners;

import com.test.lengyel.constants.FrameworkConstants;
import com.test.lengyel.testcase.listener.FrameworkScreenshotOnFailureListener;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import testCases.TestCase;

public class ScreenshotOnFailureListener extends TestCase {


	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			if (!testResult.isSuccess()) {
				String logPath = getTechnicalProperty(FrameworkConstants.CURRENT_LOGPATH_PROPERTY);
				String testDataName = null;
				if (testResult.getParameters() != null && testResult.getParameters().length > 0 && testResult.getParameters()[0] != null) {
					testDataName = (String) testResult.getParameters()[0];
				} else {
					testDataName = method.getTestMethod().getRealClass().getSimpleName();
				}
				try {
					testDataName = testDataName.substring(0, testDataName.indexOf("#"));
				} catch (Exception e) {
				}
				String numberCacheAddition = getProperty(FrameworkConstants.NUMBERCACHE_ADDITION_PROPERTY);
				if (numberCacheAddition == null) {
					numberCacheAddition = "";
				}
				testDataName += numberCacheAddition;
				String screenshotName = testDataName + ".png";
				String screenshotPath = logPath + "\\" + screenshotName;

				/*if (new File(screenshotPath).exists()) {
					ArrayList<SGTMailAttachement> checkedAttachements = new ArrayList<SGTMailAttachement>();
					checkedAttachements.add(new SGTMailAttachement(screenshotPath, screenshotName));
					SGTEmailer.sendEmail(null, getProperty(MBCPOSConstants.SMOKETEST_MAILADRESS_PROPERTY), "MBC PoS", "Text", checkedAttachements);
				}*/
			}
		}
	}

}
