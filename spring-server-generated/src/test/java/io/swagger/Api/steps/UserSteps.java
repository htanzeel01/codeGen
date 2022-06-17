package io.swagger.Api.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;


public class UserSteps {
    RestTemplate template = new RestTemplate();
    ResponseEntity<String> responseEntity;
    private String response;

    HttpHeaders headers = new HttpHeaders();
    String baseUrl = "http://localhost:8089/users";


    @When("I retrieve all users")
    public void iRetrieveAllGuitars() throws URISyntaxException {
        URI uri = new URI(baseUrl);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        responseEntity = template.getForEntity(uri, String.class);
    }
    @Then("I get http status {int}")
    public void iGetHttpStatus(int status) {
        Assert.assertEquals(responseEntity.getStatusCodeValue(), status);

    }
    @When("I retrieve userToCreate with id {int}")
    public void iRetrieveUserToCreateWithId(int id) throws URISyntaxException {
        URI uri = new URI(baseUrl + "/" + id);
        responseEntity = template.getForEntity(uri, String.class);
    }

    @Then("The The User is Mahedi {string}")
    public void theGuitarBrandIs(String name) throws JSONException {
        response = responseEntity.getBody();
        Assert.assertEquals(name,
                new JSONObject(response)
                        .getString("Mahedi"));
    }
}
