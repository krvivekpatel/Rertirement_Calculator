package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utility {

	
	public static WebElement waitForWebElement(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		 wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return null;
	}
	public static boolean isElementPresent(WebDriver driver, By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			 wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
		return false;
		}
	}
	public static WebElement clickRadioButton(WebDriver driver, By locator) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator)); 
		System.out.println("Radio button "+element.isSelected());
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		return null;
	}
	
	public static void clickElement(WebDriver driver, By locator) {
		try {
			waitForWebElement(driver, locator).click();
		} catch (Exception e) {
			System.out.println("LOG:INFO- WebElement click did not work Trying with JS Click");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", waitForWebElement(driver, locator));

		}
	}
	public static void typeElement(WebDriver driver, By locator, String text) {

		try {
			waitForWebElement(driver, locator).sendKeys(text);
		} catch (Exception e) {
			System.out.println("LOG:INFO- WebElement sendKeys did not work Trying with JS values");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].value=arguments[1]", waitForWebElement(driver, locator), text);

		}
	}
	public static void enterValue(WebDriver driver, By locator, String text,String elementID) {		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementById('"+elementID+"').focus();");
		driver.findElement(locator).sendKeys(text);
	}

	/*

	public static void switchToParentFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public static void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	public static void dismissAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

*/
	public static Map<Integer, Map<String, String>> getData() throws IOException {

		Map<Integer, Map<String, String>> excelData = new HashMap<Integer, Map<String, String>>();
		String excelPath = System.getProperty("user.dir") + "/testData/TestData.xlsx";
		File file = new File(excelPath);
		FileInputStream fileInputStream = null;
		int id = 0;
		try {
			fileInputStream = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheet("testdata");
			int rowCount = sheet.getLastRowNum();
			for (int i = 1; i <= rowCount; i++) {
				Row row = sheet.getRow(0);
				Map<String, String> rowData = new HashMap<>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					String key = row.getCell(j).getStringCellValue();
					String value = sheet.getRow(i).getCell(j).getStringCellValue();
					id = i;
					rowData.put(key, value);
				}
				excelData.put(id, rowData);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not found");
		} catch (InvalidFormatException e) {
			System.out.println("Invalid format exception");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fileInputStream.close();
		}

		return excelData;

	}

	public static int getData(String testCaseId) throws IOException {
		String testCaseIdColumn = "TestCaseID";
		int rowNumber =-1;
		Map<Integer, Map<String, String>> excelData = new HashMap<Integer, Map<String, String>>();
		String excelPath = System.getProperty("user.dir") + "/testData/TestData.xlsx";
		File file = new File(excelPath);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheet("testdata");
			int rowCount = sheet.getLastRowNum();
			for (int i = 1; i <= rowCount; i++) {
				Row row = sheet.getRow(0);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					String colname = row.getCell(j).getStringCellValue();
					if (colname.equals(testCaseIdColumn)) {
						if (testCaseId.equalsIgnoreCase(sheet.getRow(i).getCell(j).getStringCellValue())) {
							rowNumber=i;
							break;
							

						}
					}

				}
				

			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not found");
		} catch (InvalidFormatException e) {
			System.out.println("Invalid format exception");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fileInputStream.close();
		}
		return rowNumber;


	}

	public static void main(String[] args) throws IOException {
		int row = getData("TC_002");
		System.out.println(row);
		/*
		 * Map<Integer, Map<String,String>> excel = getData();
		 * for(Entry<Integer,Map<String,String>> map : excel.entrySet()) {
		 * System.out.println(map.getValue()); }
		 */
	}
}
