package io.swagger;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "io.swagger.Api.steps",
        plugin = "pretty"
)
public class CucumberApiTest {
}
