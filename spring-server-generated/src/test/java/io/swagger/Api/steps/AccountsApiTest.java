package io.swagger.Api.steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",glue = "src.io.swagger.api",plugin = "pretty")
public class AccountsApiTest {
//    @When("I open account")
//    public void iOpenAccount()throws URISyntaxException{
//        String baseUrl = "http://localhost:8089/Accounts/createAccount";
//        HttpHeaders headers = new HttpHeaders();
//        URI uri = new URI(baseUrl);
//        HttpEntity<String> entity = new HttpEntity<>(null,headers);
//        RestTemplate template = new RestTemplate();
//        ResponseEntity<String> responseEntity = template.getForEntity(uri,String.class);
//    }
//    @Then("I get http status {int}")
//    public void iGetHttpStatus(int status){
//        ResponseEntity<Object> responseEntity = null;
//        Assert.assertEquals(responseEntity.getStatusCodeValue(),status);
//    }
}
