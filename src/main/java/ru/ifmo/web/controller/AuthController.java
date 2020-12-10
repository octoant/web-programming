package ru.ifmo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.web.converter.UserFormToUser;
import ru.ifmo.web.domain.User;
import ru.ifmo.web.domain.UserForm;
import ru.ifmo.web.service.UserService;
import ru.ifmo.web.util.JwtTokenService;

import javax.validation.Valid;

/**
 * @author Bobur Zakirov
 * @since 11/29/2020 21:28:23
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

      private final UserDetailsService userDetailsService;
      private final UserService userService;

      @Autowired
      public AuthController(UserService userService, UserDetailsService userDetailsServiceImpl) {
            this.userService = userService;
            this.userDetailsService = userDetailsServiceImpl;
      }

      /**
       * A converter {UserForm -> User}
       */
      public UserFormToUser userFormToUser;
      @Autowired
      public void setUserFormToUser(UserFormToUser userFormToUser) {
            this.userFormToUser = userFormToUser;
      }

      /**
       * Spring's authentication manager
       */
      private AuthenticationManager authenticationManager;
      @Autowired
      public void setAuthenticationManager(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
      }

      /**
       * Jwt token generating service
       */
      private JwtTokenService jwtTokenService;
      @Autowired
      public void setJwtTokenService(JwtTokenService jwtTokenService) {
            this.jwtTokenService = jwtTokenService;
      }

      /**
       * Generates new token by username
       */
      private String getNewToken(String username) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return jwtTokenService.generateNewToken(userDetails);
      }

      @PostMapping("/login")
      public ResponseEntity<String> login(@RequestBody UserForm userForm) {
            try {
                  authenticationManager.authenticate(
                          new UsernamePasswordAuthenticationToken(userForm.getUsername(), userForm.getPassword())
                  );
            } catch (BadCredentialsException exception) {
                  return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(getNewToken(userForm.getUsername()), HttpStatus.OK);
      }

      @PostMapping("/register")
      public ResponseEntity<String> register(@RequestBody @Valid UserForm userForm) {
            User user = userFormToUser.convert(userForm);

            if (userService.findByUsername(userForm.getUsername()) == null) {
                  userService.addNewUser(user);
                  return new ResponseEntity<>(getNewToken(userForm.getUsername()), HttpStatus.OK);
            }
            return new ResponseEntity<>("User already exists!", HttpStatus.CONFLICT);
      }
}




