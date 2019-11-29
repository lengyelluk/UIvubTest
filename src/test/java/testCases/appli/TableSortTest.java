package testCases.appli;

import com.test.lengyel.testcase.FrameworkTestDataProvider;
import com.test.lengyel.testcase.TestDataProvider;
import org.testng.annotations.Test;
import testCases.listeners.RegressionTestListener;
import testCases.listeners.ScreenshotOnFailureListener;

public class TableSortTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = FrameworkTestDataProvider.class)
    public void tableSortTest(String testDataName) {
        setTestData(testDataName);

        //3 sort table
        actions.goToLoginPageV1();

        String validUserName = actions.resolveTestData("validUserName");
        String validPassword = actions.resolveTestData("validPassword");
        actions.loginValid(validUserName, validPassword);

        //3 sort table
        actions.checkSorting();
    }

}
