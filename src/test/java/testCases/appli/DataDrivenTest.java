package testCases.appli;

import com.test.lengyel.testcase.FrameworkTestDataProvider;
import org.testng.annotations.Test;
import testCases.listeners.RegressionTestListener;

public class DataDrivenTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = FrameworkTestDataProvider.class)
    public void dataDrivenTest(String testDataName) {
        setTestData(testDataName);

        //2. Data driven test
        actions.goToLoginPage();

        String validUserName = actions.resolveTestData("validUserName");
        String validPassword = actions.resolveTestData("validPassword");

        actions.loginInvalid("", "");
        actions.loginInvalid(validUserName, "");
        actions.loginInvalid("", validPassword);
        actions.loginValid(validUserName, validPassword);
    }

}
