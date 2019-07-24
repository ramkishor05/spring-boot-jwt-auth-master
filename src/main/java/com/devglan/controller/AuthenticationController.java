package com.devglan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devglan.config.JwtTokenUtil;
import com.devglan.entities.User;
import com.devglan.model.ApiResponse;
import com.devglan.model.AuthToken;
import com.devglan.model.LoginUser;
import com.devglan.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",new AuthToken(token, user.getUsername()));
    }
    
    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public ApiResponse<AuthToken> validate(@RequestBody AuthToken authToken) throws AuthenticationException {
        if(jwtTokenUtil.isTokenExpired(authToken.getToken())) {
        	final String username = jwtTokenUtil.getUsernameFromToken(authToken.getToken());
        	return new ApiResponse<>(200, "success",new AuthToken(authToken.getToken(), username));
        }else {
        	final String username = jwtTokenUtil.getUsernameFromToken(authToken.getToken());
        	return new ApiResponse<>(400, "error",new AuthToken(authToken.getToken(), username));
        }
    }

}
