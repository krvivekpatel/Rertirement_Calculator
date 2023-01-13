package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = "classpath:featureFiles",
	glue = {"stepDefinations","Hooks"},
	plugin = { "pretty","html:target/cucumber-reports" },
	monochrome = true,
	dryRun = false,
	//tags = {"@tc001 or @tc002"},
	tags ="@regression",
	strict = false
	)
public class CucumberTestRunner {

}
