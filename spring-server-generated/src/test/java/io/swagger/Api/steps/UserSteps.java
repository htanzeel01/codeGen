package io.swagger.Api.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

public class UserSteps {
    private ResponseEntity<String> stringResponse;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private ObjectMapper objectMapper;
    private User user;
    private String protocol = "http";
    private String port = "8089";
    private String host = "localhost";
    private String basePath = "/";
    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getRequest(URI uri, HttpHeaders headers) throws URISyntaxException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.getForEntity(uri, String.class);
        return responseEntity;
    }

    public ResponseEntity<String> postRequest(URI uri, HttpHeaders headers, Map<String, String> body) throws URISyntaxException {
        JSONObject bodyJson = new JSONObject(body);
        HttpEntity<String> entity = new HttpEntity<>(bodyJson.toString(), headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.postForEntity(uri, entity, String.class);
        return responseEntity;
    }

    public String getJwtToken(String userName, String passWord) throws Exception {
        //assertTrue("User is an employee",this.user.getUserType().getAuthority().contains("Employee"));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        URI uri = uriBuilder.
                scheme(this.protocol)
                .host(this.host)
                .port(this.port)
                .path(this.basePath)
                .path("login")
                .build().toUri();

        // Build headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build body
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", userName);
        map.put("Password", passWord);

        ResponseEntity<String> responseEntity = postRequest(uri, headers, map);
        String response = responseEntity.getBody();
        String key = new JSONObject(response).getString("key");
        return "Bearer " + key;
    }
    @When("An employee makes a request to the /users API endpoint")
    public void anEmployeeMakesARequestToTheUsersAPIEndpoint() throws Exception {
        // Create request
        URI uri = new URI("http://localhost:8089" + "/users");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJwtToken("mah","mah!"));

        // Perform request
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        stringResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    @Then("The server will return list of {int} users")
    public void theServerWillReturnListOfUsers(int expectedNumberOfUsers) throws JSONException {
        JSONArray jsonArray = new JSONArray(stringResponse.getBody());
        Assert.assertEquals(expectedNumberOfUsers, jsonArray.length());
    }
    @When("An employee makes a request with userId {int} API endpoint")
    public void someoneMakesARequestToTheUsersAPIEndpointWithoutAnAuthenticationToken(int userId) throws Exception {

        URI uri = new URI("http://localhost:8089" + "/users/" + userId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJwtToken("mah","mah!"));

        // Perform request
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        stringResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

    }
    @When("An employee makes a request with userId {int} API endpoint to get account")
    public void someoneMakesARequestToTheUsersAPIEndpointWithAnAuthenticationToken(int userId) throws Exception {

        URI uri = new URI("http://localhost:8089" + "/users/" + userId + "/accounts");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJwtToken("mah","mah!"));

        // Perform request
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        stringResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

    }
    @Then("The server will return a {int} ok")
    public void theServerWillReturnA200(int statusCode) {
        Assert.assertEquals(statusCode, stringResponse.getStatusCodeValue());
    }
}
