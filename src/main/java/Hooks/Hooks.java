package Hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import factory.BrowserFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks 
{
	WebDriver driver;
	
	
	@Before
	public void startBrowser()
	{
		System.out.println("Browser getting started");
		
		BrowserFactory browserFactory=new BrowserFactory();
		
		driver=browserFactory.init_driver("Chrome");
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
