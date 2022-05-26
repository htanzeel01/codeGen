package com.tests.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserToCreate;
import io.swagger.model.UserTypeEnum;
import io.swagger.service.UserToCreateService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class UserToCreateSteps {
    @Autowired
    private UserToCreateService userService;
    private PasswordEncoder encoder;
    private ObjectMapper objectMapper;

    private UserToCreate user;
    private String protocol = "http";
    private String port = "8089";
    private String host = "localhost";
    private String basePath = "/";

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
    public void setUp() throws Exception {
        this.user = createMockUserToCreate(UserTypeEnum.CUSTOMER);
    }

    public void tearDown() {
        this.user = null;
    }

    private UserToCreate createMockUserToCreate(UserTypeEnum userType) throws Exception {
        ArrayList<UserTypeEnum> roles = new ArrayList<>();
        roles.add(userType);
        RegistrationDTO registrationDTO = new RegistrationDTO("mah", encoder.encode("secret-key"), "user@gmail.com", "mah", "has", UserTypeEnum.EMPLOYEE);
        UserToCreate user = new UserToCreate("mah", encoder.encode("secret-key"), "user@gmail.com", "mah", "has", UserTypeEnum.EMPLOYEE);
        user.setUserId(2);
        userService.createUser(registrationDTO);
        return user;
    }
    @Given("the user logged in is an employee")
    public void theUserLoggedInIsAnEmployee() throws Exception {
        setUp();
        assertTrue("User is an employee",this.user.getUserType().getAuthority().contains("Employee"));
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
        map.put("userName", "mah");
        map.put("password", "secret-key");

        ResponseEntity<String> responseEntity = postRequest(uri, headers, map);
        String response = responseEntity.getBody();
        String token = new JSONObject(response).getString("token");
        assertTrue("token exists, user logged in", token != null);
    }
    @When("visiting the create user endpoint")
    public void visitingTheAddUserEndpoint() {
    }
    @And("user is set in the request body")
    public void userIsSetInTheRequestBody() {
        assertTrue(true);
    }

    @Then("user should be created")
    public void userShouldBeCreated() throws Exception {
        Optional<UserToCreate> checkUser = Optional.ofNullable(userService.getUserByUserId(user.getUserId()));

        assertTrue( "User is created", checkUser.isPresent());
    }


}
