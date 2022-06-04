package io.swagger;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        features = "src/test/resources/features",
        tags="",
        plugin = "pretty"
)
@SpringBootTest(classes = {
        Swagger2SpringBoot.class,
        CucumberApiTest.class
},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class CucumberApiTest {
}
