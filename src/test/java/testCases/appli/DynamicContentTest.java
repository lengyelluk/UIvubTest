package testCases.appli;

import com.test.sogeti.testcase.SGTTestDataProvider;
import org.testng.annotations.Test;
import sgt.testcases.listeners.RegressionTestListener;

public class DynamicContentTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = SGTTestDataProvider.class)
    public void dynamicContentTest(String testDataName) {
        setTestData(testDataName);

        int numOfAddsToBeFound = 2;
        String validUserName = scActions.resolveTestData("validUserName");
        String validPassword = scActions.resolveTestData("validPassword");

        scNavigation.goToLoginPageV1WithAdds();
        scActions.loginValid(validUserName, validPassword);
        scActions.checkAdds(numOfAddsToBeFound);
    }
}
