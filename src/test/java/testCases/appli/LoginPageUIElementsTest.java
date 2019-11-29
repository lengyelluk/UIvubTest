package testCases.appli;

import com.test.sogeti.testcase.SGTTestDataProvider;
import org.testng.annotations.Test;
import sgt.testcases.listeners.RegressionTestListener;

public class LoginPageUIElementsTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = SGTTestDataProvider.class)
    public void loginPageUIElementsTest(String testDataName) {
        setTestData(testDataName);


        scActions.goToLoginPageV1();
        //1. Login Pge UI Elements Test
        scCheck.checkHeader();
        scCheck.checkLabels();
        scCheck.checkIconsPresent();
        scCheck.checkTextFieldsPlaceholders();
        scCheck.checkLoginButton();
        scCheck.checkRememberMe();
        scCheck.checkSocialIconsPresent();
    }
}
