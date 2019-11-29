package testCases.appli;

import com.test.sogeti.testcase.SGTTestDataProvider;
import org.testng.annotations.Test;
import sgt.testcases.listeners.RegressionTestListener;

public class CanvasChartTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = SGTTestDataProvider.class)
    public void canvasChartTest(String testDataName) {
        setTestData(testDataName);

        //4 canvas chart test
        scActions.goToLoginPageV1();
        String validUserName = scActions.resolveTestData("validUserName");
        String validPassword = scActions.resolveTestData("validPassword");
        scActions.loginValid(validUserName, validPassword);

        scNavigation.goToCompareExpenses();
        scActions.checkChartData2017_2018();
        scNavigation.goToChartData2019();
        //no check for 2019???
    }

}
