package pages;


import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import helper.Utility;

public class RetirementCalculatorPage {
	WebDriver driver;
	String testCaseID;
	Map<String, Map<String,String>> excelData ;
	public String getTestCaseID() {
		return testCaseID;
	}
	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}
	public RetirementCalculatorPage(WebDriver driver) {		
		excelData = Utility.getDataFromExcel();
		this.driver = driver;
	}

	By current_age = By.id("current-age");
	By retirement_age = By.id("retirement-age");
	By current_income = By.id("current-income");
	By spouse_income = By.id("spouse-income");
	By current_total_savings = By.id("current-total-savings");
	By current_annual_savings = By.id("current-annual-savings");
	By savings_increase_rate = By.id("savings-increase-rate");
	By yes_social_benefits = By.id("yes-social-benefits");
	By no_social_benefits = By.id("no-social-benefits");
	By marital_status_label = By.id("marital-status-label");
	By single = By.id("single");
	By married = By.id("married");
	By social_security_override = By.xpath("//input[@id='social-security-override']");
	By additional_income = By.id("additional-income");
	By retirement_duration = By.id("retirement-duration");
	By retirement_duration_container = By.id("retirement-duration-container");
	By include_nflation = By.id("include-inflation");
	//input[@type='radio' and @id='include-inflation']
	By include_nflation_yes= By.xpath("//input[@type='radio' and @id='include-inflation']");
	By expected_inflation_rate = By.id("expected-inflation-rate");
	By pre_retirement_invst_return = By.id("pre-retirement-roi");
	By post_retirement_invst_return = By.id("post-retirement-roi");
	By retirement_annual_income = By.id("retirement-annual-income");
	By save_change_button = By.xpath("//button[@type='button' and text()='Save changes']");
	By Calculate = By.xpath("//button[@class='dsg-btn-primary btn-block' and text()='Calculate']");
	By result_message = By.id("result-message");
	By retirement_amount_results = By.id("retirement-amount-results");
	By current_savings_results = By.id("current-savings-results");
	By detailed_results_table = By.id("detailed-results-table");
	By adjust_default_value = By.xpath("//a[text()='Adjust default values']");

	public void enterRetrimentDetails() throws InterruptedException {
		driver.findElement(current_age).sendKeys(excelData.get(testCaseID).get("current-age"));
		driver.findElement(retirement_age).sendKeys(excelData.get(testCaseID).get("retirement-age"));
		driver.findElement(current_income).click();
		driver.findElement(current_income).sendKeys(excelData.get(testCaseID).get("current-income"));
		Utility.enterValue(driver, spouse_income, excelData.get(testCaseID).get("spouse-income"), "spouse-income");
		Utility.enterValue(driver, current_total_savings, excelData.get(testCaseID).get("current-total-savings"), "current-total-savings");
		driver.findElement(current_annual_savings).sendKeys(excelData.get(testCaseID).get("current-annual-savings"));
		driver.findElement(savings_increase_rate).sendKeys(excelData.get(testCaseID).get("savings-increase-rate"));
		/*
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("arguments[0].scrollIntoView(true);",
		 * savings_increase_rate);
		 */
	}
public void updateDefaultCalulatorValue() throws InterruptedException {
	Thread.sleep(3000);
	driver.findElement(additional_income).click();
	driver.findElement(additional_income).sendKeys(excelData.get(testCaseID).get("additional-income"));
	driver.findElement(retirement_duration).sendKeys(excelData.get(testCaseID).get("retirement-duration"));
	//driver.findElement(By.id("inflation-label")).click();
	Utility.clickRadioButton(driver, include_nflation_yes);
	Utility.enterValue(driver, expected_inflation_rate, excelData.get(testCaseID).get("expected_inflation_rate"), "expected-inflation-rate");
	Utility.enterValue(driver, retirement_annual_income, excelData.get(testCaseID).get("expected_inflation_rate"), "retirement-annual-income");
	Utility.enterValue(driver, pre_retirement_invst_return, excelData.get(testCaseID).get("pre_retirement_invst_return"), "pre-retirement-roi");
	Utility.enterValue(driver, post_retirement_invst_return,excelData.get(testCaseID).get("post_retirement_invst_return"), "post-retirement-roi");
	//driver.findElement(By.id("expected_inflation_rate")).sendKeys("4");
	//driver.findElement(By.id("retirement_annual_income")).sendKeys("24");
	//driver.findElement(By.id("pre_retirement_invst_return")).sendKeys("3");
	//driver.findElement(By.id("pre_retirement_invst_return")).sendKeys("7");
}
	public void clickCalculateButton() {
		driver.findElement(Calculate).click();
	}
	public void clickAdjustDefaultValueLink() {
		driver.findElement(adjust_default_value).click();
	}
	public void clickSaveChangeButton() {
		driver.findElement(save_change_button).click();
	}
	public boolean verifyResultMessage() {
		Utility.waitForWebElement(driver, result_message);
		System.out.println(driver.findElement(result_message).getText());
		return driver.findElement(result_message).isDisplayed();
	}

	public void selectSocialSecurityBenefitAsNo() {
		boolean selectState = driver.findElement(no_social_benefits).isSelected();
		if(selectState == false) 
			Utility.clickRadioButton(driver, no_social_benefits);
	}

	public void selectSocialSecurityBenefitAsYes() {
		driver.findElement(By.id("include-social-label")).click();
		Utility.clickRadioButton(driver, yes_social_benefits);
		Utility.enterValue(driver, social_security_override,excelData.get(testCaseID).get("social_security_override"), "social-security-override");
	}
	public boolean verifyMartialStatusSection() {
		return driver.findElement(married).isDisplayed();		

		
	}
}
