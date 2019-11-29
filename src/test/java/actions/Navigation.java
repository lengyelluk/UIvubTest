package actions;

public class Navigation extends FrameworkActions {

    private static final Logger logger = LogManager.getLogger(Navigation.class);

    public Navigation(TestContext testContext) {
        super(testContext);
    }

    public Navigation(TestContext testContext, String testDataName) {
        super(testContext, testDataName);
    }

    public void loadLoginPageV1() { loadUrl(getProperty(Constants.URL_V1));}

    public void goToCompareExpenses() {
        getPage(AppliAppPage.class).goToCompareExpenses();
        getPage(AppliChartPage.class).waitForPageLoaded();
    }

    public void goToChartData2019() {
        getPage(AppliChartPage.class).goToChartData2019();
    }

    public void goToLoginPageV1WithAdds() {
        loadUrl(getProperty(Constants.URL_V1_ADDS));
    }
}
