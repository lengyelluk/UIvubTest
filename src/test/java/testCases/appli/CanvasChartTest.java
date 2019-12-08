package testCases.appli;

import com.test.lengyel.testcase.FrameworkTestDataProvider;
import org.testng.annotations.Test;
import testCases.listeners.RegressionTestListener;

public class CanvasChartTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = FrameworkTestDataProvider.class)
    public void canvasChartTest(String testDataName) {
        setTestData(testDataName);

        //4 canvas chart test
        actions.goToLoginPage();
        String validUserName = actions.resolveTestData("validUserName");
        String validPassword = actions.resolveTestData("validPassword");
        actions.loginValid(validUserName, validPassword);

        navigation.goToCompareExpenses();
        actions.checkChartData2017_2018();
        navigation.goToChartData2019();
        //no check for 2019???
    }

}
