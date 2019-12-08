package pages;

import com.test.lengyel.gui.GuiElement;
import com.test.lengyel.testcase.TestContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppliLoginPage extends Page {

    private static final Logger logger = LogManager
            .getLogger(AppliLoginPage.class);

    public AppliLoginPage(TestContext testContext) {
        super(testContext);
    }


    public GuiElement

    //buttons
    toggleButton, loginButton, signInButton,
    //textfield
    usernameTextField, passwordTextField,
    //header
    loginFormHeader,
    //labels
    usernameLabel, passwordLabel, rememberMeLabel,
    //checkbox
    rememberMeCheckbox,
    //icons
    twitterIcon, facebookIcon, linkedinIcon, maleIcon, fingerprintIcon,
    //error msg
    loginFailedMsg
    ;

    public void checkInitialLoginPage() {
        checkHeader();
        checkLabels();
        checkIconsPresent();
        checkTextFieldsPlaceholders();
        checkLoginButton();
        checkRememberMe();
        checkSocialIconsPresent();
    }

    public void checkLoginPageAfterButtonToggled() {
        checkHeader();
        checkLabels();
        checkTextFieldsPlaceholders();
        checkIconsPresent();
        checkSignInButton();
        checkRememberMe();
        checkSocialIconsPresent();
    }

    public void loginValid(String username, String password) {
        sendText(usernameTextField, username);
        sendText(passwordTextField, password);
        if(isElementPresent(loginButton)) {
            click(loginButton);
        }

    }

    public void loginInvalid(String username, String password) {
        sendText(usernameTextField, username);
        sendText(passwordTextField, password);
        if(isElementPresent(loginButton)) {
            click(loginButton);
        }

        boolean loginFailedMsgDisplayed = isElementPresent(loginFailedMsg);
        assertActions().verifyTrue(loginFailedMsgDisplayed, "Login failed msg not displayed");

        if(loginFailedMsgDisplayed) {
            final String LOGIN_FAILED_MSG = "Both Username and Password must be present";
            final String PASSWORD_MISSING_MSG = "Password must be present";
            final String USERNAME_MISSING_MSG = "Username must be present";

            String msgText = getCurrentText(loginFailedMsg).trim();

            if (username.isEmpty() && password.isEmpty())
                assertActions().verifyTrue(LOGIN_FAILED_MSG.equals(msgText), "Error message text is incorrect");
            else if (username.isEmpty())
                assertActions().verifyTrue(USERNAME_MISSING_MSG.equals(msgText), "Error message when username is missing is incorrect");
            else
                assertActions().verifyTrue(PASSWORD_MISSING_MSG.equals(msgText), "Error message when password is missing is incorrect");

            //based on V2 => invalid style of error msg
            final String ERROR_MSG_STYLE = "display: block;";
            String errorMsgStyleCaptured = getAttributeValue(loginFailedMsg, "style");
            assertActions().verifyTrue(errorMsgStyleCaptured.equals(ERROR_MSG_STYLE), "The style of error message is incorrect");
        }

        refreshCurrentPage();
    }

    public void checkHeader() {
        final String HEADER_TEXT = "Login Form";

        String headerTextCaptured = getCurrentText(loginFormHeader).trim();

        logger.info("Checking header text of Login form");
        assertActions().verifyEquals(headerTextCaptured, HEADER_TEXT, "Login form header text incorrect");
    }

    public void checkLabels() {
        final String USERNAME_LABEL_TEXT = "Username";
        final String PASSWORD_LABEL_TEXT = "Password";

        String usernameLabelCaptured = getLabelText(usernameLabel);
        String passwordLabelCaptured = getLabelText(passwordLabel);

        logger.info("Checking label text of Login form");
        assertActions().verifyEquals(usernameLabelCaptured, USERNAME_LABEL_TEXT,
                "Username label incorrect.");
        assertActions().verifyEquals(passwordLabelCaptured, PASSWORD_LABEL_TEXT,
                "Password label incorrect.");

    }

    public void checkTextFieldsPlaceholders() {
        //based on V2
        final String USERNAME_PLACEHOLDER = "John Smith";
        final String PASSWORD_PLACEHOLDER = "ABC$*1@";

        String usernamePlaceholderCaptured = getPlaceholderText(usernameTextField);
        String passwordPlaceholderCaptured = getPlaceholderText(passwordTextField);

        logger.info("Checking placeholders of username and password text fields");
        assertActions().verifyEquals(usernamePlaceholderCaptured, USERNAME_PLACEHOLDER,
                "Username placeholder incorrect.");
        assertActions().verifyEquals(passwordPlaceholderCaptured, PASSWORD_PLACEHOLDER,
                "Password placeholder incorrect.");
    }

    public void checkLoginButton() {
        final String LOGIN_BTN_TEXT = "Log In";

        String btnTextCaptured = getCurrentText(loginButton).trim();

        logger.info("Checking Login button in Login form");
        assertActions().verifyEquals(btnTextCaptured, LOGIN_BTN_TEXT,
                "Login button text incorrect.");
    }

    public void checkSignInButton() {
        final String SIGN_IN_BTN_TEXT = "Sign in";

        String btnTextCaptured = getCurrentText(signInButton).trim();

        assertActions().verifyTrue(SIGN_IN_BTN_TEXT.equals(btnTextCaptured), "Sign in button text incorrect");
    }

    public void checkRememberMe() {
        final String REMEMBER_ME_TEXT = "Remember Me";

        String labelTextCaptured = getCurrentText(rememberMeLabel).trim();
        String labelTextStyle = getAttributeValue(rememberMeCheckbox, "style");

        logger.info("Checking Remember me label in Login form");
        assertActions().verifyEquals(labelTextCaptured, REMEMBER_ME_TEXT,
                "Remember me label text incorrect.");
        //based on V2 to check the offset of Remember me text form the checkbox
        assertActions().verifyTrue(StringUtils.isBlank(labelTextStyle), "The style attribute of Remember me checkbox should be empty.");
    }

    public void checkSocialIconsPresent() {
        logger.info("Checking social icons in Login form");
        assertActions().verifyTrue(isElementPresent(facebookIcon), "Facebook icon is present");
        assertActions().verifyTrue(isElementPresent(twitterIcon), "Twitter icon is present");
        assertActions().verifyTrue(isElementPresent(linkedinIcon), "LinkedIn icon is present");
    }

    public void checkIconsPresent() {

        logger.info("Checking if icons next to the text fields are displayed in Login form");
        assertActions().verifyTrue(isElementPresent(maleIcon), "Male icon is present");
        assertActions().verifyTrue(isElementPresent(fingerprintIcon), "Fingerprint icon is present");
    }

    public void toggleButton() {
        boolean test = isElementPresent(toggleButton);
        click(toggleButton);
    }


    public String getLabelText(GuiElement label) {
        return getCurrentText(label);
    }

    public String getPlaceholderText(GuiElement textfield) {
        return getAttributeValue(textfield, "placeholder");
    }



}
