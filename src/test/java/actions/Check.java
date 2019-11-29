package actions;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Check extends FrameworkActions {

    private static final Logger logger = LogManager.getLogger(Check.class);

    public Check(TestContext testContext) {
        super(testContext);
    }

    public Check(TestContext testContext, String testDataName) {
        super(testContext, testDataName);
    }

    public void checkInitialLoginPage() {
        getPage(AppliLoginPage.class).checkInitialLoginPage();
    }

    public void checkHeader() {
        getPage(AppliLoginPage.class).checkHeader();
    }

    public void checkLabels() {
        getPage(AppliLoginPage.class).checkLabels();
    }

    public void checkIconsPresent() {
        getPage(AppliLoginPage.class).checkIconsPresent();
    }

    public void checkTextFieldsPlaceholders() {
        getPage(AppliLoginPage.class).checkTextFieldsPlaceholders();
    }

    public void checkLoginButton() {
        getPage(AppliLoginPage.class).checkLoginButton();
    }

    public void checkRememberMe() {
        getPage(AppliLoginPage.class).checkRememberMe();
    }

    public void checkSocialIconsPresent() {
        getPage(AppliLoginPage.class).checkSocialIconsPresent();
    }
}
