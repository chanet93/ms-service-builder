package com.glic.adminserver.api;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.glic.adminserver.model.ApiResponse;
import com.glic.adminserver.model.SignUpRequest;
import com.glic.adminserver.security.AppUser;
import com.glic.adminserver.security.AppUserRepository;
import com.glic.jwt.EAppRole;

@RestController
@RequestMapping("/users")
public class UserController {

   //TODO REVIEW THIS CONTROLLER
   @Autowired
   private AppUserRepository appUserRepository;

   @Autowired
   PasswordEncoder passwordEncoder;

   @RequestMapping(value = "/user", method = RequestMethod.GET)
   public Iterable<AppUser> listUser() {
      return appUserRepository.findAll();
   }

   @RequestMapping(value = "/user", method = RequestMethod.POST)
   public AppUser create(@RequestBody AppUser user) {
      return appUserRepository.save(user);
   }

   @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
   public String delete(@PathVariable(value = "id") Long id) {
    //  appUserRepository.deleteById(id);
      return "success";
   }

   @PostMapping("/signup")
   public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

      //TODO REVIEW THIS COULD NOT BE DONE
      if (appUserRepository.existsByEmail(signUpRequest.getEmail())) {
         return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
      }

      // Creating user's account
      AppUser user = new AppUser();
      user.setEmail(signUpRequest.getEmail());
      user.setPassword(signUpRequest.getPassword());
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setRole(EAppRole.ADMIN);
      AppUser result = appUserRepository.save(user);
      URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}").buildAndExpand(result.getUsername()).toUri();
      return ResponseEntity.created(location).body(new ApiResponse(true, "AppUser registered successfully"));
   }

}
