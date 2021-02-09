package com.example.syshology.jpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
//@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response) throws Exception {
        System.out.println("로그인 입니다.");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        Boolean aBoolean = jwtTokenUtil.validateToken(token, userDetails);
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println("username :: " + usernameFromToken);
        System.out.println("token::" + token);
        System.out.println("token::validate" + aBoolean);
        response.addHeader("Authorization", "Bearer "+ token);
        Cookie bearer = new Cookie("Bearer", "Bearer_" + token);
        bearer.setHttpOnly(Boolean.TRUE);
        bearer.setMaxAge(100);
        response.addCookie(bearer);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public ResponseEntity<?> Hello(@RequestBody JwtRequest authenticationRequest) throws Exception {
        System.out.println("Hello 테스트 입니다.");

        System.out.println("hello : " +  authenticationRequest.getUsername());

        return ResponseEntity.ok("안녕하셍!!");
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}