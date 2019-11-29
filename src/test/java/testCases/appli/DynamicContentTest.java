package testCases.appli;

import com.test.lengyel.testcase.FrameworkTestDataProvider;
import org.testng.annotations.Test;
import testCases.listeners.RegressionTestListener;

public class DynamicContentTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = FrameworkTestDataProvider.class)
    public void dynamicContentTest(String testDataName) {
        setTestData(testDataName);

        int numOfAddsToBeFound = 2;
        String validUserName = actions.resolveTestData("validUserName");
        String validPassword = actions.resolveTestData("validPassword");

        navigation.goToLoginPageV1WithAdds();
        actions.loginValid(validUserName, validPassword);
        actions.checkAdds(numOfAddsToBeFound);
    }
}
