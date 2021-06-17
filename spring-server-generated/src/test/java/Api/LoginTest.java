package Api;


import cucumber.api.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class LoginTest {
    private final String baseLoginUrl = "http://localhost:8080/login";
    private RestTemplate restTemplate = new RestTemplate();

    private World world;

    public AuthenticationSteps(World world) {
        this.world = world;
    }

    @When("i log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String arg0, String arg1) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        LoginPostDTO login = new LoginPostDTO(arg0, arg1);

        ResponseEntity<LoginPostResponseDTO> loginResponse = world.postRequest(baseLoginUrl, LoginPostResponseDTO.class, login);

        world.matchLastResponse(200);
        headers.add("Authorization", "Bearer " + loginResponse.getBody().getToken());
        world.setHeaders(headers);
    }

    @When("i log in with username {string} and password {string} and store the result")
    public void iLogInWithUsernameAndPasswordAndStoreTheResult(String arg0, String arg1) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        LoginPostDTO login = new LoginPostDTO(arg0, arg1);

        ResponseEntity<LoginPostResponseDTO> loginResponse = world.postRequest(baseLoginUrl, LoginPostResponseDTO.class, login);

        if (world.getLastResponseCode() == 200) {
            headers.add("Authorization", "Bearer " + loginResponse.getBody().getToken());
            world.setHeaders(headers);
        }
    }
}
