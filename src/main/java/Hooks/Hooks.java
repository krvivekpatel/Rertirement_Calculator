package Hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import factory.BrowserFactory;
import helper.Constant;
import helper.Utility;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks 
{
	WebDriver driver;
	Scenario scenario;

	@Before(order = 0)
	public void startBrowser()
	{
		System.out.println("Browser getting started");		
		BrowserFactory browserFactory=new BrowserFactory();		
		driver=browserFactory.init_driver(Utility.getValueFromPropFile("browser"));
	}
	@Before(order = 1)
	   public  void getTestCaseID(Scenario scenario) {
			String namearr[] = scenario.getName().toString().split("]");
			String testCaseID =  namearr[0].substring(1, namearr[0].length());
			System.out.println("test case id-"+testCaseID);
			Constant.testCaseID= testCaseID;
			
	    }
	@After
	public void closeBrowser()
	{
		driver.quit();
		System.out.println("Browser closed");
	}
	
	@AfterStep
	public void tearDown(Scenario scenario)
	{
		
		String name=scenario.getName();		
		TakesScreenshot ts=(TakesScreenshot)driver;		
		 byte[] screenshotBytes=ts.getScreenshotAs(OutputType.BYTES);		
		 scenario.embed(screenshotBytes, "image/png", name);		
			/*
			 * if(scenario.isFailed()) { String name=scenario.getName();
			 * 
			 * TakesScreenshot ts=(TakesScreenshot)driver;
			 * 
			 * byte[] screenshotBytes=ts.getScreenshotAs(OutputType.BYTES);
			 * 
			 * scenario.embed(screenshotBytes, "image/png", name); }
			 */
		
	}
	
	
	
}
