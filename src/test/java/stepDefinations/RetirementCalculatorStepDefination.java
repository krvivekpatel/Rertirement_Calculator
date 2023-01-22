package stepDefinations;

import org.junit.Assert;

import Hooks.Hooks;
import factory.BrowserFactory;
import helper.Constant;
import helper.Utility;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.RetirementCalculatorPage;

public class RetirementCalculatorStepDefination {

	RetirementCalculatorPage calculatorPage;
	Scenario scenario;
	@Given("User is available on calulator screen")
	public void user_is_available_on_calulator_screen() {
		calculatorPage=new RetirementCalculatorPage(BrowserFactory.getDriver());
		BrowserFactory.getDriver().get(Utility.getValueFromPropFile("url"));
		BrowserFactory.getDriver().manage().window().maximize();
		System.out.println(calculatorPage.getTestCaseID());
		calculatorPage.setTestCaseID(Constant.testCaseID);
	}

	@When("User provide all input details")
	public void user_provide_all_input_details() throws InterruptedException {
		calculatorPage.enterRetrimentDetails();
	}

	@When("click calculate button")
	public void click_calculate_button() {
		calculatorPage.clickCalculateButton();
	}

	@Then("pre retirement calculator details should be displayed")
	public void pre_retirement_calculator_details_should_be_displayed() {
	System.out.println("pre retirement calculator details should be displayed");
	}

	@Then("monthly saving details should be displyed")
	public void monthly_saving_details_should_be_displyed() {
		System.out.println("monthly saving details should be displyed");
	}
	@Then("result message should be displayed")
	public void result_message_should_be_displayed() {
		Assert.assertTrue(calculatorPage.verifyResultMessage());
	}
	@Then("social security benefit select as Yes")
	public void social_security_benefit_select_as_yes() {
		calculatorPage.selectSocialSecurityBenefitAsYes();
	}

	@Then("martial status section should be displayed")
	public void martial_status_section_should_be_displayed() {
		Assert.assertTrue(calculatorPage.verifyMartialStatusSection());
	}

	@Then("social security benefit select as No")
	public void social_security_benefit_select_as_no() {
		calculatorPage.selectSocialSecurityBenefitAsNo();
	}

	@Then("martial status section should not be displayed")
	public void martial_status_section_should_not_be_displayed() {
		Assert.assertFalse(calculatorPage.verifyMartialStatusSection());
	}
	@Then("click on Adjust default values link")
	public void click_on_adjust_default_values_link() {
		calculatorPage.clickAdjustDefaultValueLink();
	}
	@When("user provide Default calculator values")
	public void user_provide_Default_calculator_values() throws InterruptedException {
	   calculatorPage.updateDefaultCalulatorValue();
	}
	@Then("click save changes button")
	public void click_save_changes_button() {
	    calculatorPage.clickSaveChangeButton();
	}
}
