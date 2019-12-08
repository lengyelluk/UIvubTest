package actions;

import com.test.lengyel.actions.FrameworkActions;
import com.test.lengyel.testcase.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.AppliAppPage;
import pages.AppliChartPage;
import pages.AppliLoginPage;

import javax.swing.text.NavigationFilter;
import java.util.List;
import java.util.Map;

public class Actions extends FrameworkActions {

    private static final Logger logger = LogManager.getLogger(Actions.class);

    Navigation navigation;
    Check check;

    public Actions(TestContext testContext) {
        super(testContext);
        navigation = new Navigation(testContext);
        check = new Check(testContext);
    }

    public Actions(TestContext testContext, String testDataName) {
        super(testContext, testDataName);
        navigation = new Navigation(testContext);
        check = new Check(testContext);
    }

    public void goToLoginPage() {
        navigation.loadLoginPage();
    }


    public void toggleButton() {
        getPage(AppliLoginPage.class).toggleButton();
    }

    public void loginValid(String username, String password) {
        getPage(AppliLoginPage.class).loginValid(username, password);
        getPage(AppliAppPage.class).checkPageLoaded();
    }

    public void loginInvalid(String username, String password) {
        getPage(AppliLoginPage.class).loginInvalid(username, password);
    }

    public void sortAmounts() {
        getPage(AppliAppPage.class).sortAmounts();
    }

    public Map<Double, List<String>> getRows() { return getPage(AppliAppPage.class).getRows(); }

    public void changeOrderingByAmount() { getPage(AppliAppPage.class).changeOrderingByAmount(); }

    public void checkSorting() { getPage(AppliAppPage.class).checkSorting(); }

    public void checkChartData2017_2018() {
        getPage(AppliChartPage.class).checkChartData2017_2018();
    }

    public void checkAdds(int numOfAddsToBeFound) {
        getPage(AppliAppPage.class).checkAdds(numOfAddsToBeFound);
    }

}
