package com.tests.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import io.swagger.Swagger2SpringBoot;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        plugin="pretty",
        tags="",
        features = "src/test/resources/features/"
)
@SpringBootTest(classes = {
        Swagger2SpringBoot.class,
        RunCucumberTests.class
},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class RunCucumberTests {
}

