package pages;

import com.test.lengyel.constants.FrameworkConstants;
import com.test.lengyel.gui.FrameworkKindEnum;
import com.test.lengyel.gui.GuiElement;
import com.test.lengyel.gui.web.FrameworkWebGuiActions;
import com.test.lengyel.page.web.FrameworkWebPage;
import com.test.lengyel.testcase.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Page extends FrameworkWebPage {

	List<GuiElement> alreadyClickedList = new ArrayList<GuiElement>();

	public Page(TestContext testContext) {
		super(testContext);
		setGuiActions(new FrameworkWebGuiActions());
	}

	public void click(GuiElement guiElement) {

		String guiElementName = resolveTestData("id_logging_element");
		System.err.println("[Click] ->" + guiElement.getName());

		super.click(guiElement);
	}
	

	public void clickDirty(GuiElement guiElement) {
		System.err.println("[Click Dirty] ->" + guiElement.getName());
		super.clickDirty(guiElement);
	}

	public void useCorrectValue(GuiElement guiElement) {
		System.err.println(
				"[Fill Textfield] ->" + guiElement.getName() + ". Value:" + resolveTestData(guiElement.getName()));
		// click needed to focus element
		if (resolveTestData(guiElement.getName()) != null && isElementEnabled(guiElement)) {
			scrollTo(guiElement);
			click(guiElement);
			super.useCorrectValue(guiElement);
		} else {
			logger.error("Field " + guiElement.getName() + " not enabled. Skipping...");
		}
	}

	public void useCorrectValue(GuiElement guiElement, String id) {
		// click needed to focus element
		click(guiElement);
		super.useCorrectValue(guiElement, id);
	}

	public void useSpecifiedValue(GuiElement guiElement, String value) {
		String guiElementName = resolveTestData("id_logging_element");
		
		super.useSpecifiedValue(guiElement, value);
	}

	public void useSpecifiedValue(GuiElement guiElement, int value) {
		// click needed to focus element
		click(guiElement);
		super.useSpecifiedValue(guiElement, value);
	}

	public void sendTab(GuiElement guiElement) {
		super.sendTab(guiElement);
	}

	public void clearInput(GuiElement guiElement) {
		super.clearInput(guiElement);
	}

	public void zoomPageOut(String zoom) {
		WebDriver driver = getTestContext().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.body.style.zoom='" + zoom + "%'");
	}

	public String getDataFromTestData(String stringToResolve) {
		return resolveTestData(stringToResolve);
	}


	public BigDecimal getBigDecimalValue(GuiElement guiElement) {
		String priceString = "";
		GuiElement completeAmount = new GuiElement();
		completeAmount.setKind(FrameworkKindEnum.LABEL);
		completeAmount.setIdentifier(FrameworkConstants.IDENTIFIER_XPATH);
		completeAmount.setIdentValue("//*[@id='" + guiElement.getIdentValue() + "']/..");
		if (isElementPresent(completeAmount))
			priceString = getCurrentText(completeAmount);
		BigDecimal returnValue = getBigDecimalValue(priceString);
		return returnValue;
	}

	public BigDecimal getBigDecimalValue(String stringToExtractValue) {
		// Current Format at this point:
		// UK: 12x£4,399.72 or CH:12xCHF4,399.72

		// First get rid of payment rates
		stringToExtractValue = stringToExtractValue.substring(stringToExtractValue.indexOf("x") + 1,
				stringToExtractValue.length());

		// Format at this point:
		// UK: £4,399.72 CH:CHF4,399.72

		// Format for BigDecimal conversion is €1234.56 (no commas!)
		// Parsing into BigDecimal in format €1.234,56 or €1,234.56 doesn't work
		// therefore we have to convert/cleanup the stringToExtractValue

		Integer indexPoint = stringToExtractValue.indexOf(".");
		Integer indexComma = stringToExtractValue.indexOf(",");

		if (indexPoint < indexComma) {
			// £3.429,45 to £3429.45
			// First: delete . then
			// Second: change , to .
			stringToExtractValue = stringToExtractValue.replace(".", "");
			stringToExtractValue = stringToExtractValue.replace(",", ".");

		} else {
			// £3,429.45 to £3429.45
			// delete comma
			stringToExtractValue = stringToExtractValue.replace(",", "");
		}

		// get rid of all other characters except numbers, comma and point
		stringToExtractValue = stringToExtractValue.replaceAll("[^0-9,.]", "");

		if (stringToExtractValue.length() > 0) {
			BigDecimal listPrice = new BigDecimal(stringToExtractValue);
			return listPrice;
		} else {
			BigDecimal listPrice = new BigDecimal(-1);
			return listPrice;
		}
	}

	public BigDecimal getBigDecimalValue(WebElement webElement) {
		String priceString = "";
		priceString = webElement.getAttribute("value");
		if (priceString == null) {
			priceString = webElement.getText();
		}
		return getBigDecimalValue(priceString);
	}

	public BigDecimal round(BigDecimal value) {
		BigDecimal roundedValue = value.setScale(2, RoundingMode.HALF_UP);
		return roundedValue;
	}

	public void compare(BigDecimal calculatedValue, GuiElement guiElement, String requirement) {
		compare(calculatedValue, guiElement, new BigDecimal("0"), requirement);
	}

	public void compare(BigDecimal calculatedValue, GuiElement guiElement, BigDecimal acceptedDifference,
			String requirement) {
		BigDecimal actual = getBigDecimalValue(guiElement);
		BigDecimal expected = round(calculatedValue);
		BigDecimal difference = expected.subtract(actual).abs();
		assertActions().verifyTrue(difference.compareTo(acceptedDifference) <= 0,
				requirement + " values differ: " + actual + " and " + expected + " for Input: " + calculatedValue);
	}

	public void compareSum(GuiElement listGuiElement, GuiElement sumGuiElement, String requirement,
			boolean containsRoundingDifference) {
		List<WebElement> listOfSummands = getAllMatchingElements(listGuiElement);
		BigDecimal sum = new BigDecimal("0");
		BigDecimal roundingDifference = new BigDecimal("0");
		for (WebElement summandElement : listOfSummands) {
			BigDecimal summand = getBigDecimalValue(summandElement);
			sum = sum.add(summand);
			if (containsRoundingDifference) {
				roundingDifference = roundingDifference.add(new BigDecimal("0.01"));
			}
		}
		compare(sum, sumGuiElement, roundingDifference, requirement);
	}

	public void compareSum(GuiElement sumGuiElement, String requirement, boolean containsRoundingDifference,
			GuiElement... summandList) {
		BigDecimal sum = new BigDecimal("0");
		BigDecimal roundingDifference = new BigDecimal("0");
		for (GuiElement summandElement : summandList) {
			if (isElementPresent(summandElement)) {
				BigDecimal summand = getBigDecimalValue(summandElement);
				sum = sum.add(summand);
				if (containsRoundingDifference) {
					roundingDifference = roundingDifference.add(new BigDecimal("0.01"));
				}
			}
		}
		compare(sum, sumGuiElement, roundingDifference, requirement);

	}

	public void compareNegativeSum(GuiElement sumGuiElement, String requirement, boolean containsRoundingDifference,
			GuiElement... summandList) {
		BigDecimal sum = new BigDecimal("0");
		BigDecimal roundingDifference = new BigDecimal("0");
		for (GuiElement summandElement : summandList) {
			if (isElementPresent(summandElement)) {
				BigDecimal summand = getBigDecimalValue(summandElement);
				sum = sum.add(summand);
				if (containsRoundingDifference) {
					roundingDifference = roundingDifference.add(new BigDecimal("0.01"));
				}
			}
		}
		sum = sum.multiply(new BigDecimal("-1"));
		compare(sum, sumGuiElement, roundingDifference, requirement);
	}



	public String trySetValue(String[] values, int i) {
		try {
			String returnValue = values[i];
			if (returnValue.contains(FrameworkConstants.TESTDATA_ADDITION)) {
				returnValue = returnValue.substring(0, returnValue.indexOf(FrameworkConstants.TESTDATA_ADDITION));
			}
			return returnValue;
		} catch (Exception e) {
			return "";
		}
	}

	

	public void refreshCurrentPage() {
		// we refresh the current URL
		getTestContext().getDriver().get(getTestContext().getDriver().getCurrentUrl());
	}

	public String getCurrentURL() {
		return getTestContext().getDriver().getCurrentUrl();
	}


	/**
	 * to verify if element is present in base frame, after check it
	 * switches back to default frame
	 * 
	 * @param guiElementInIFrame
	 * @return true if element is present
	 */
	public boolean isElementPresentInBase(GuiElement guiElementInIFrame) {
		switchToBase();
		boolean result = super.isElementPresent(guiElementInIFrame);
		switchToDefaultFrame();
		return result;
	}
	
	/**
	 * to switch to base frame only if not already in base frame
	 */
	public void switchToBase() {
		int size = this.getTestContext().getDriver().findElements(By.tagName("iframe")).size();
		if(size==1) {
			switchToFrame("SearchFormSubDlgFrame");
			logger.info("Switch to BASE frame");
		}
	}


	
	/**
	 * method should find a dropdown arrow and click it which would
	 * expand the dropdown, then save all dropdown options in the list and check if the dropdown option
	 * in test data is also in dropdown options
	 * if dropdown option in test data is in dropdown options => select the dropdown option specified in test data
	 * if not => select any random dropdown option
	 * This method should be used only for the dropdowns that are not crucial in the specific test case
	 * e.g. if only one option e.g.'smart' must be selected and no other option, do not use this method
	 * by now tested only in lead create page
	 * @param dropdownSelect - an element that is used with methods useCorrectValue
	 * label, this should be part of for attribute of label to find the dropdown options
	 */

	public void useRandomDropdownValue(GuiElement dropdownSelect, String label) {
		//create list of webElements of all the dropdown options
		GuiElement dropdownOptions = new GuiElement();
		dropdownOptions.setKind(FrameworkKindEnum.LABEL);
		dropdownOptions.setIdentifier(FrameworkConstants.IDENTIFIER_XPATH);
		dropdownOptions.setIdentValue("//label[contains(@for, '" + label + "')]//following-sibling::div//div[contains(@class, 'dropdown-extended-arrow')]//following-sibling::ul//li[not(contains(@data-value, 'BLANK')) and not(@data-index='1')]");
		List<WebElement> list = getAllMatchingElements(dropdownOptions);
		int length = list.size();
		//create list of text of all dropdown options
		List<String> options = new ArrayList<>();
		//if there is at least one valid dropdown option
		if(length > 0) {
			for(WebElement e : list) {
				String eText = e.getAttribute("textContent").replaceAll("\\s{2,}", " ").trim();
				//&amp; = & added to the condition because of defect PAT-12695
				//Alle, All, Tout - added because of problem that is under analysis => temporary fix
				//the same for " that are displayed in sales classes of VANs e.g. V-class "Marco Polo"
				if(eText.length() > 1 && !eText.contains("&")
						&& !eText.contains("All")
						&& !eText.contains("Alle")
						&& !eText.contains("Tout")
						&& !eText.contains("\""))
					options.add(eText);
			}
		logger.info("One of these options in dropdown \"" + dropdownSelect.getName() + "\" will be selected: " + options);
		
		//check if there is any value in test data 
		String dropdownOptionInTestData = resolveTestDataForGuiElement(dropdownSelect);
		
		//if there is a value check if there is a match in dropdown options
		if (dropdownOptionInTestData != null && options.contains(dropdownOptionInTestData)) {
			logger.info("Selecting dropdown options " + dropdownOptionInTestData + " "
					+ "as specified in test data");
			useSpecifiedValue(dropdownSelect, dropdownOptionInTestData);
		} else if(options.size() > 0) {
			//create a random value from 0 until length size was changed as selecting
			//value from the bottom of the list is causing problem (dropdowns with more than 30 values)
			//keeping the random value from 0 to 5 exclusively as the values are not really important
			int optionsSize = options.size();
			Random random = new Random();
			int randomNumber = optionsSize < 5 ? random.nextInt(optionsSize) : random.nextInt(5);
			//get the text of random dropdown option that should be selected
			String toBeSelected = options.get(randomNumber);
			
			logger.info("Selecting random dropdown option \"" + toBeSelected + "\"");
			//finally select the dropdown option
			useSpecifiedValue(dropdownSelect, toBeSelected);
			} else {
				logger.info("No dropdown options that can be selected by test in \"" + dropdownSelect.getName() + "\". Skipping...");
			}
		}
		else {
			logger.info("No dropdown option available in dropdown \"" + dropdownSelect.getName() + "\". Skipping...");
		}	
	}
	
	
	
	
	/**
	 * used to select random dropdown options in case two clicks
	 * are needed (to expand the dropdown and click on the desired dropdown option).
	 * Under testing => two elements are needed => one that targets the element to
	 * expand dropdown, other that captures all the dropdown options, so they can
	 * be captured
	 * @param guiElement - element to expand dropdown
	 * @param dropdownOptions = all dropdown options
	 */
	public String useRandomDropdownValueWithoutText(GuiElement guiElement, GuiElement dropdownOptions) {
		//get the value that is selected
		String selectedValue = getDropdownSelectedValue(guiElement);
		List<String> list = new ArrayList<>();
		List<String> listIfSelected = new ArrayList<>();
		
		//to expand the dropdown
		scrollTo(guiElement);
		click(guiElement);
		
		//save all valid dropdown options
		List<WebElement> dropdownValues = getAllMatchingElements(dropdownOptions);
		for(WebElement e : dropdownValues) {
			String eText = e.getText();
			if (eText != null && eText.length() > 1)
				list.add(e.getText());
		}
		
		logger.info("Visible dropdown options of dropdown \"" + guiElement.getName() + "\" are: "
				+ list.toString());
		
		//only if there is at least one valid dropdown option
		int length = list.size();
		if(length > 0) {
			//if there is already a selected one, exclude it
			if(selectedValue != null && !selectedValue.trim().isEmpty()) {
			//sort the list in alphabetic order
			Collections.sort(list);
			//create sublist only with elements that are after the select one
			int splitPoint = 0;
			for(int i = 0; i < length; i++) {
				if(list.get(i).compareTo(selectedValue) > 0) {
					splitPoint = i;
					break;
				}
				
			}
			//create a sublist only with options that are after the selected one
			listIfSelected = list.subList(splitPoint, length);
			}
			
			//get a random number for a list or listIfSelected
			int lenghtForRandomNum = listIfSelected.size() > 0 ? listIfSelected.size() : list.size(); 
			Random random = new Random();
			int randomNumber = lenghtForRandomNum < 5 ? random.nextInt(lenghtForRandomNum) : random.nextInt(5);
			//get the text of random dropdown option that should be selected
			String toBeSelected = null;
			if(listIfSelected.size() > 0)
				toBeSelected = listIfSelected.get(randomNumber);
			else
				toBeSelected = list.get(randomNumber);
			useSpecifiedValue(guiElement, toBeSelected);
			return toBeSelected;
		}
		return "";
	}
	
	public String getDropdownSelectedValue(GuiElement dropdown) {
		GuiElement dropDownElement = new GuiElement();
		dropDownElement.setIdentifier(FrameworkConstants.IDENTIFIER_XPATH);
		dropDownElement.setKind(FrameworkKindEnum.SELECTWITHOUTTEXT);
		dropDownElement.setName("selectedDropdown");
		if (dropdown.isAjax())
			dropDownElement.setAjax(true);
		
		String xpath = dropdown.getIdentValue() + "//span[@class='ui-selectmenu-text']";
		dropDownElement.setIdentValue(xpath);
		
		String dropdownText = getCurrentText(dropDownElement);
		logger.info("The value of the \"" + dropdown.getName() + "\" is: " + dropdownText);
		return dropdownText;
	}
	
	public String getDropdownSelectedValue(GuiElement dropdown, String index) {
		replaceSearchString(dropdown, index);
		
		GuiElement dropDownElement = new GuiElement();
		dropDownElement.setIdentifier(FrameworkConstants.IDENTIFIER_XPATH);
		dropDownElement.setKind(FrameworkKindEnum.SELECTWITHOUTTEXT);
		dropDownElement.setName("selectedDropdown");
		if (dropdown.isAjax())
			dropDownElement.setAjax(true);
		
		String xpath = dropdown.getIdentValue() + "//span[@class='ui-selectmenu-text']";
		dropDownElement.setIdentValue(xpath);
		
		String dropdownText = getCurrentText(dropDownElement);
		logger.info("The value of the \"" + dropdown.getName() + "\" is: " + dropdownText);
		return dropdownText;
	}
	
	/**to check if a dropdown in contact details section
	 * has a value
	 * @param dropdown - specific dropdown of contact section
	 * @param index - index of desired section => private or business section
	 * @return true if any value is found in the field
	 */
	public boolean checkDropdownSelected(GuiElement dropdown, String index) {
		GuiElement dropDownElement = new GuiElement();
		dropDownElement.setIdentifier(FrameworkConstants.IDENTIFIER_XPATH);
		dropDownElement.setKind(FrameworkKindEnum.SELECTWITHOUTTEXT);
		dropDownElement.setName("selectedDropdown");
		if (dropdown.isAjax())
			dropDownElement.setAjax(true);
		
		replaceSearchString(dropdown, index);
		String xpath = dropdown.getIdentValue() + "//span[@class='ui-selectmenu-text']";
		dropDownElement.setIdentValue(xpath);
		
		String dropdownText = getCurrentText(dropDownElement);
		logger.info("The value of the \"" + dropdown.getName() + "\" is: " + dropdownText);
		
		return dropdownText != null && !dropdownText.trim().isEmpty();
	}
	
	/**
	 * to check if a value is selected in dropdown
	 * @param field => dropdown element used to expand dropdown
	 * @return true => if a value is selected in dropdown
	 */
	public boolean checkDropdownSelected(GuiElement field) {
		GuiElement dropDownElement = new GuiElement();
		dropDownElement.setIdentifier(FrameworkConstants.IDENTIFIER_XPATH);
		dropDownElement.setKind(FrameworkKindEnum.SELECTWITHOUTTEXT);
		dropDownElement.setName("selectedDropdown");
		if (field.isAjax())
			dropDownElement.setAjax(true);
		
		String xpath = field.getIdentValue() + "//span[@class='ui-selectmenu-text']";
		dropDownElement.setIdentValue(xpath);
		
		String dropdownText = getCurrentText(dropDownElement);
		logger.info("The value of the \"" + field.getName() + "\" is: " + dropdownText);
		
		return dropdownText != null && !dropdownText.trim().isEmpty();
		}
	
	
	public Boolean isDisplayed(GuiElement guiElement) {
		waitForElementVisible(guiElement, 30);
		return isElementPresent(guiElement);
	}
	
}		
