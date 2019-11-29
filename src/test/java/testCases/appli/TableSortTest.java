package testCases.appli;

import com.test.sogeti.testcase.SGTTestDataProvider;
import org.testng.annotations.Test;
import sgt.testcases.listeners.RegressionTestListener;

public class TableSortTest extends RegressionTestListener {

    @Test(dataProvider = "dataProvider", dataProviderClass = SGTTestDataProvider.class)
    public void tableSortTest(String testDataName) {
        setTestData(testDataName);

        //3 sort table
        scActions.goToLoginPageV1();

        String validUserName = scActions.resolveTestData("validUserName");
        String validPassword = scActions.resolveTestData("validPassword");
        scActions.loginValid(validUserName, validPassword);

        //3 sort table
        scActions.checkSorting();
    }

}
