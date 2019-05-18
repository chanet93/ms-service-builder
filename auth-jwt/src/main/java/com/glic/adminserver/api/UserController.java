package com.glic.adminserver.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.glic.adminserver.entities.AppUserRepository;
import com.glic.adminserver.mails.EmailService;
import com.glic.adminserver.model.ApiResponse;
import com.glic.adminserver.model.AppUser;
import com.glic.adminserver.model.SignUpRequest;
import com.glic.adminserver.security.SecureRandomService;
import com.glic.jwt.AppRole;
import com.glic.jwt.EUserStatus;
import com.glic.payment.model.general.BasicString;

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/users")
@RolesAllowed({ AppRole.ROLE_ADMIN, AppRole.ROLE_GPORTAL_ADMIN })
public class UserController {

   @Autowired
   PasswordEncoder passwordEncoder;

   @Autowired
   private AppUserRepository appUserRepository;

   @Autowired
   private EmailService emailService;

   @Autowired
   private SecureRandomService secureRandomService;

   @RequestMapping(value = "/user", method = RequestMethod.GET)
   public Page<AppUser> listUser(Pageable pageable, @RequestParam Optional<String> email) {
      if (email.isPresent()) {
         return appUserRepository.findByEmail(email, pageable);
      } else {
         return appUserRepository.findAll(pageable);
      }
   }

   @RequestMapping(value = "/user/{email}", method = RequestMethod.GET)
   public Optional<AppUser> getUser(@PathVariable(value = "email") String email) {
      return appUserRepository.findByEmail(email);
   }

   @RequestMapping(value = "/user", method = RequestMethod.PUT)
   public ResponseEntity<AppUser> updateUser(@RequestBody AppUser userToUpdate) {
      Optional<AppUser> user = appUserRepository.findByEmail(userToUpdate.getEmail());
      if (user.isPresent()) {
         AppUser userExist = user.get();
         userExist.setDescription(userToUpdate.getDescription());
         userExist.setNameToShow(userToUpdate.getNameToShow());
         return new ResponseEntity<>(appUserRepository.save(userExist), HttpStatus.OK);
      } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }

   @RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
   public ResponseEntity<AppUser> delete(@PathVariable(value = "email") String email) {
      Optional<AppUser> user = appUserRepository.findByEmail(email);
      if (user.isPresent()) {
         AppUser userExist = user.get();
         userExist.setStatus(EUserStatus.DELETED);
         return new ResponseEntity<>(appUserRepository.save(userExist), HttpStatus.OK);
      } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }

   @RequestMapping(value = "/user/forcedelete/{email}", method = RequestMethod.DELETE)
   public ResponseEntity<AppUser> forcedelete(@PathVariable(value = "email") String email) {
      Optional<AppUser> user = appUserRepository.findByEmail(email);
      if (user.isPresent()) {
         AppUser userExist = user.get();
         appUserRepository.delete(userExist);
         return new ResponseEntity<>(userExist, HttpStatus.OK);
      } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }

   @RequestMapping(value = "/user", method = RequestMethod.POST)
   public ResponseEntity<AppUser> registerUser(@Valid @RequestBody SignUpRequest userSignUp)
         throws TemplateException, IOException, MessagingException {

      //TODO REVIEW THIS COULD NOT BE DONE
      if (appUserRepository.existsByEmail(userSignUp.getEmail())) {
         return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
      }
      //TODO validate permissions
      AppUser user = new AppUser();
      user.setEmail(userSignUp.getEmail());
      user.setNameToShow(userSignUp.getNameToShow());
      user.setDescription(userSignUp.getDescription());
      user.setRole(userSignUp.getRole());
      user.setStatus(EUserStatus.INACTIVE);
      user.setActivatioToken(secureRandomService.getActivationToken());
      user.setActivationTokenValidity(LocalDateTime.now().plusMinutes(15));
      //TODO Send userId to activate the user
      emailService.sendUserEmail(user, EmailService.EmailTypes.ACTIVATION);
      return new ResponseEntity<>(appUserRepository.save(user), HttpStatus.OK);
   }

   @RequestMapping(value = "/roles", method = RequestMethod.GET)
   public List<BasicString> getRoles() {
      return AppRole.getRoles();
   }

}
