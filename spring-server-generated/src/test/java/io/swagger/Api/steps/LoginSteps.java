package io.swagger.Api.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.junit.Cucumber;
import io.swagger.model.DTO.RegistrationDTO;
import io.swagger.model.User;
import io.swagger.model.UserTypeEnum;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import org.json.JSONObject;
import org.junit.runner.RunWith;
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

import static org.junit.Assert.assertTrue;

public class LoginSteps {
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
        this.user = createMockUserToCreate(UserTypeEnum.ROLE_EMPLOYEE);
    }

    public void tearDown() {
        this.user = null;
    }

    private User createMockUserToCreate(UserTypeEnum userType) throws Exception {
        ArrayList<UserTypeEnum> roles = new ArrayList<>();
        roles.add(userType);
        RegistrationDTO registrationDTO = new RegistrationDTO("mri", "mri!", "mri@gmail.nl", "mad", "mri", UserTypeEnum.ROLE_EMPLOYEE);

        userService.createUser(registrationDTO);
        return user;
    }
    @Given("the user logged in is an employee")
    public void theUserLoggedInIsAnEmployee() throws Exception {
        setUp();
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
        map.put("username", "mri");
        map.put("Password", "mri!");

        ResponseEntity<String> responseEntity = postRequest(uri, headers, map);
        String response = responseEntity.getBody();
        String key = new JSONObject(response).getString("key");
        assertTrue("token exists, user logged in", key != null);
    }
}