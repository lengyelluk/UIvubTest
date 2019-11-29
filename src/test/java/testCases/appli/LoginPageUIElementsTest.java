package testCases.appli;

import com.test.lengyel.testcase.FrameworkTestDataProvider;
import org.testng.annotations.Test;
import testCases.listeners.RegressionTestListener;

public class LoginPageUIElementsTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = FrameworkTestDataProvider.class)
    public void loginPageUIElementsTest(String testDataName) {
        setTestData(testDataName);


        actions.goToLoginPageV1();
        //1. Login Pge UI Elements Test
        check.checkHeader();
        check.checkLabels();
        check.checkIconsPresent();
        check.checkTextFieldsPlaceholders();
        check.checkLoginButton();
        check.checkRememberMe();
        check.checkSocialIconsPresent();
    }
}
