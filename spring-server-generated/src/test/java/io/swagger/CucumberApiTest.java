package io.swagger;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "io.swagger.Api.steps",
        plugin = "pretty",
        strict = true
)
public class CucumberApiTest {
}
