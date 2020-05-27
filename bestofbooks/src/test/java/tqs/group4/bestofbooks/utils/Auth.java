package tqs.group4.bestofbooks.utils;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class Auth {

    public static String fetchToken(MockMvc mvc, String username, String password) throws Exception {
        String loginUrl = "/api/session/login";

        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(StandardCharsets.US_ASCII));
        String header = "Basic " + new String( encodedAuth );

        MvcResult result = mvc.perform(get(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", header)
        ).andReturn();

        return result.getResponse().getHeader("x-auth-token");
    }
}
