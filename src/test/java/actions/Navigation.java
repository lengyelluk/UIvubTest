package actions;

import com.test.lengyel.actions.FrameworkActions;
import com.test.lengyel.testcase.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.AppliAppPage;
import pages.AppliChartPage;

public class Navigation extends FrameworkActions {

    private static final Logger logger = LogManager.getLogger(Navigation.class);

    public Navigation(TestContext testContext) {
        super(testContext);
    }

    public Navigation(TestContext testContext, String testDataName) {
        super(testContext, testDataName);
    }

    public void loadLoginPage() { loadUrl(getProperty(Constants.URL));}

    public void goToCompareExpenses() {
        getPage(AppliAppPage.class).goToCompareExpenses();
        getPage(AppliChartPage.class).waitForPageLoaded();
    }

    public void goToChartData2019() {
        getPage(AppliChartPage.class).goToChartData2019();
    }

    public void goToLoginPageWithAdds() {
        loadUrl(getProperty(Constants.URL_ADDS));
    }
}
