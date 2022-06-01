package Api;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;

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
