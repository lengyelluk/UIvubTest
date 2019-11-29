package testCases.appli;

import com.test.sogeti.testcase.SGTTestDataProvider;
import org.testng.annotations.Test;
import sgt.testcases.listeners.RegressionTestListener;

public class DataDrivenTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = SGTTestDataProvider.class)
    public void dataDrivenTest(String testDataName) {
        setTestData(testDataName);

        //2. Data driven test
        scActions.goToLoginPageV1();

        String validUserName = scActions.resolveTestData("validUserName");
        String validPassword = scActions.resolveTestData("validPassword");

        scActions.loginInvalid("", "");
        scActions.loginInvalid(validUserName, "");
        scActions.loginInvalid("", validPassword);
        scActions.loginValid(validUserName, validPassword);
    }

}
