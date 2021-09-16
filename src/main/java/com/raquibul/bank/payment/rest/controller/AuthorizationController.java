package com.raquibul.bank.payment.rest.controller;

import com.raquibul.bank.payment.rest.config.JwtTokenProvider;
import com.raquibul.bank.payment.rest.model.Error;
import com.raquibul.bank.payment.rest.model.User;
import com.raquibul.bank.payment.rest.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the controller which provides functionalities related to authentication and authorization
 */
@Tag(name = "authorization", description = "The Authorization API which generates token upon successful authentication")
@RestController
@RequestMapping("/authorize")
@Slf4j
public class AuthorizationController extends BaseController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    public AuthorizationController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    /**
     * This method generates the token upon successful authentication of the user
     *
     * @param user payload as {@link User}
     * @return https response
     */
    @Operation(summary = "generate Token for authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication failed",
                    content = @Content), @ApiResponse(responseCode = "403", description = "Authorization failed",
            content = @Content)})
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody User user) {
        log.info("authenticateAndGetToken : method invoked");
        ResponseEntity<?> responseEntity = null;
        try {
            log.info("authenticating the user");
            log.debug("Request payload:{}", user);
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            log.debug("is Authenticated:{}", authentication.isAuthenticated());
            if (authentication.isAuthenticated()) {
                String email = user.getEmail();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", authentication.getName());
                jsonObject.put("authorities", authentication.getAuthorities());
                jsonObject.put("token", tokenProvider.createToken(email, userRepository.findByEmail(email).getRole()));
                log.info("authenticateAndGetToken : Token is generated.");
                log.debug("authenticateAndGetToken : The generated token : {}", jsonObject.toString());
                responseEntity = buildResponse(jsonObject.toString(), HttpStatus.OK);
            }
        } catch (JSONException e) {
            log.error("authenticateAndGetToken : There was some error while authenticating and generating token");
            responseEntity = buildError(Error.AUTHENTICATION_ERROR, HttpStatus.UNAUTHORIZED);
        }
        return responseEntity;
    }
}
