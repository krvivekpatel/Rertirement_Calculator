package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utility {

public static String getValueFromPropFile(String key) {
	String propFilePath =System.getProperty("user.dir") + "/config.properties";
	FileInputStream fis = null;
    Properties prop = null;
    String value = null;
    try {
       fis = new FileInputStream(propFilePath);
       prop = new Properties();
       prop.load(fis);
        value = prop.getProperty(key);
    } catch(FileNotFoundException fnfe) {
       fnfe.printStackTrace();
    } catch(IOException ioe) {
       ioe.printStackTrace();
    } finally {
       try {
		fis.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    return value;
 }
	

	public static WebElement waitForWebElement(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
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

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
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

	public static Map<String, Map<String, String>> getDataFromExcel() {
		
		String testCaseID = "";
		Map<String, Map<String, String>> excelData = new HashMap<String, Map<String, String>>();
		String path = System.getProperty("user.dir") + "/testData/TestData.xlsx";
		File file = new File(path);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheet("testdata");
			int rowCount = sheet.getLastRowNum();
			for (int i = 1; i <= rowCount; i++) {
				Row row = sheet.getRow(0);
				testCaseID=sheet.getRow(i).getCell(0).getStringCellValue();
				Map<String, String> rowData = new HashMap<>();
				for (int j = 0; j < row.getLastCellNum(); j++) {

					String key = row.getCell(j).getStringCellValue();
					String value = sheet.getRow(i).getCell(j).getStringCellValue();
					//id = i;
					rowData.put(key, value);
				}
				excelData.put(testCaseID, rowData);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return excelData;
	}
	
	public static void main(String[] args) {
		System.out.println(getValueFromPropFile("url"));
		String name = "[TC_001] User should be able to submit form with all required fields filled in";
		String namearr[] = name.split("]");
		String testcaseid =  namearr[0].substring(1, namearr[0].length());
		System.out.println(testcaseid);
		
		/*
		 * int row = getData("TC_002"); System.out.println(row);
		 */
		
		
		  Map<String, Map<String,String>> excel = getDataFromExcel(); 
		  System.out.println(excel.get(testcaseid).get("Description"));
		  for(Entry<String,
		  Map<String, String>> map : excel.entrySet()) {
		  System.out.println(map.getKey()); System.out.println(map.getValue()); }
		 		 
	}
}
