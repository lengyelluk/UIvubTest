package actions;

import com.test.lengyel.actions.FrameworkActions;
import com.test.lengyel.testcase.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Preparation extends FrameworkActions {

    private static final Logger logger = LogManager.getLogger(Preparation.class);

    public Preparation(TestContext testContext) {
        super(testContext);
    }

    public Preparation(TestContext testContext, String testDataName) {
        super(testContext, testDataName);
    }
}
