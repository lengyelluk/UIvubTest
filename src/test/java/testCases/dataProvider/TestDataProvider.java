package testCases.dataProvider;

import com.google.common.base.Strings;
import com.test.sogeti.constants.SGTConstants;
import com.test.sogeti.helper.SGTResourceMapper;
import com.test.sogeti.testcase.SGTPropertiesManager;
import com.test.sogeti.testcase.SGTTestContextFactory;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {

	@DataProvider(name = "dataProvider")
	public static Object[][] provideData(Method m) {
		String testCaseName = m.getDeclaringClass().getSimpleName();
		String specificMarket = System.getProperty("market");
		System.out.println("Specific Market="+specificMarket);

		String customPath = SGTPropertiesManager.getFunctionalProperty(null,
				SGTConstants.CUSTOM_TESTDATA_PATH_PROPERTY, true);
		if (Strings.isNullOrEmpty(customPath)) {
			customPath = SGTConstants.TESTDATA_PATH;
		}
		Object[][] returnValue;
		
		if (specificMarket!=null && !specificMarket.isEmpty() && specificMarket.equals("ALL")) {
			System.out.println("Specific Market="+specificMarket+".Prepare test for all markets");
			returnValue = filterFoundFiles(SGTResourceMapper
					.getFileNamesWithWildCard(testCaseName + "_*", customPath));
		} 
		else if (specificMarket!=null && !specificMarket.isEmpty() && !specificMarket.equals("ALL")) {
			System.out.println("Specific Market="+specificMarket+".Prepare test only for this market");
			returnValue = filterFoundFiles(SGTResourceMapper
					.getFileNamesWithWildCard(testCaseName + "_M-"+specificMarket+"*", customPath));
		}else {
			System.out.println("Specific Market is not set. Prepare test for all markets");
			returnValue = filterFoundFiles(SGTResourceMapper
					.getFileNamesWithWildCard(testCaseName + "_*", customPath));
		}
		if (returnValue.length == 0) {
			returnValue = new Object[1][1];
			returnValue[0][0] = testCaseName;
		}
		return returnValue;
	}

	private static Object[][] filterFoundFiles(Object[][] fileNamesWithWildCard) {
		List<String> matchingFiles = new ArrayList<String>();
		for (int i = 0; i < fileNamesWithWildCard.length; i++) {
			String filename = (String) fileNamesWithWildCard[i][0];
			if (hasAddition(filename)) {
				if (isAdditionMatching(filename)) {
					matchingFiles.add(filename);
				}
			} else {
				String market = filename.substring(filename.indexOf("_M-")+3, filename.indexOf("_M-")+5);
				System.out.println("Current market: " + market);
				if (market != null) {
					if (filename.contains(market)) {
						System.out.println("Adding test data xml: " + filename);
						matchingFiles.add(filename);
					}
				} else
					matchingFiles.add(filename);
			}
		}
		Object[][] returnValue = new Object[matchingFiles.size()][1];
		for (int i = 0; i < matchingFiles.size(); i++) {
			returnValue[i][0] = matchingFiles.get(i);
		}
		return returnValue;
	}

	private static boolean isAdditionMatching(String filename) {
		String additionFromProperties = SGTTestContextFactory
				.getFunctionalProperty(
						SGTConstants.NUMBERCACHE_ADDITION_PROPERTY)
				.toLowerCase();
		String additionFromFileName = filename.substring(
				filename.indexOf(SGTConstants.TESTDATA_ADDITION)
						+ SGTConstants.TESTDATA_ADDITION.length())
				.toLowerCase();
		return additionFromFileName.equals(additionFromProperties);
	}

	private static boolean hasAddition(String filename) {
		return filename.contains(SGTConstants.TESTDATA_ADDITION);
	}
}
