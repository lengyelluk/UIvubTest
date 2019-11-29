package pages;

import com.test.sogeti.gui.SGTGuiElement;
import com.test.sogeti.testcase.SGTTestContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppliChartPage extends Page {

    private static final Logger logger = LogManager
            .getLogger(AppliChartPage.class);

    public AppliChartPage(TestContext testContext) {
        super(testContext);
    }


    public GuiElement

    //canvas
    canvas,
    showDataForNextYearBtn
    ;

    public void waitForPageLoaded() {
        waitForElementVisible(canvas);
    }

    public void goToChartData2019() { click(showDataForNextYearBtn); }

    public void checkChartData2017_2018() {
        Map<String, Integer> map_data_2017 = new HashMap<>();
        Map<String, Integer> map_data_2018 = new HashMap<>();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July");
        List<Integer> data_2017 = Arrays.asList(10, 20, 30, 40, 50, 60, 70);
        List<Integer> data_2018 = Arrays.asList(8, 9, -10, 10, 40, 60, 40);

        for(int i=0; i<7; i++) {
            map_data_2017.put(months.get(i), data_2017.get(i));
            map_data_2018.put(months.get(i), data_2018.get(i));
        }


        String html = getSource();
        String monthsInDatasets = StringUtils.substringBetween(html, "labels: [", "]");
        String datasets = StringUtils.substringBetween(html, "datasets:", "};");
        String datasets1 = StringUtils.substringBetween(datasets, "data: [", "]");
        String datasets2whole = StringUtils.substringBetween(datasets, "2018", "}]");
        String datasets2 = StringUtils.substringBetween(datasets2whole, "data: [", "]");

        Map<String, Integer> chartData2017 = new HashMap<>();
        Map<String, Integer> chartData2018 = new HashMap<>();

        Pattern pForMonths = Pattern.compile("\\w+");
        Pattern pForNums = Pattern.compile("[-]?\\d+");
        Matcher mForMonths = pForMonths.matcher(monthsInDatasets);
        Matcher mForNums2017 = pForNums.matcher(datasets1);
        Matcher mForNums2018 = pForNums.matcher(datasets2);
        while(mForMonths.find() && mForNums2017.find() && mForNums2018.find()) {
            String s1 = mForMonths.group();
            Integer i2 = Integer.parseInt(mForNums2017.group());
            Integer i3 = Integer.parseInt(mForNums2018.group());
            chartData2017.put(s1, i2);
            chartData2018.put(s1, i3);
        }

        assertActions().verifyTrue(map_data_2017.equals(chartData2017), "Displayed chart data for 2017 does not match with expecting values");
        assertActions().verifyTrue(map_data_2018.equals(chartData2018), "Displayed chart data for 2018 does not match with expecting values");
    }


}
