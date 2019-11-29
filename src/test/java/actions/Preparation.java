package actions;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Preparation extends FrameworkAction {

    private static final Logger logger = LogManager.getLogger(Preparation.class);

    public Preparation(TestContext testContext) {
        super(testContext);
    }

    public Preparation(TestContext testContext, String testDataName) {
        super(testContext, testDataName);
    }
}
