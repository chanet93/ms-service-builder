package com.glic.adminserver.api;

import static com.glic.jwt.JwtUtil.getJwtCookie;
import static com.glic.jwt.JwtUtil.getJwtFromRequest;
import static com.glic.jwt.JwtUtil.getLogoutJwtCookie;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.glic.adminserver.entities.AppUserRepository;
import com.glic.adminserver.mails.EmailService;
import com.glic.adminserver.model.ActivateRequest;
import com.glic.adminserver.model.AppUser;
import com.glic.adminserver.model.RecoveryRequest;
import com.glic.adminserver.security.JwtTokenProvider;
import com.glic.adminserver.security.SecureRandomService;
import com.glic.jwt.EUserStatus;
import com.glic.jwt.JwtAuthenticationResponse;
import com.glic.jwt.LoginRequest;

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/auth")
public class AuthController {

   @Autowired
   AuthenticationManager authenticationManager;

   @Autowired
   AppUserRepository appUserRepository;

   @Autowired
   PasswordEncoder passwordEncoder;

   @Autowired
   JwtTokenProvider tokenProvider;

   @Autowired
   private SecureRandomService secureRandomService;

   @Autowired
   private EmailService emailService;

   @PostMapping("/login")
   public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpResponse) {
      Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = tokenProvider.generateToken(authentication);
      JwtAuthenticationResponse response = new JwtAuthenticationResponse();
      response.setAccessToken(jwt);
      Optional<AppUser> user = appUserRepository.findByEmail(loginRequest.getEmail());
      if (user.isPresent()) {
         AppUser userExist = user.get();
         userExist.setLastLogin(LocalDateTime.now());
         appUserRepository.save(userExist);
      }

      httpResponse.addCookie(getJwtCookie(jwt));
      return ResponseEntity.ok(response);
   }

   @GetMapping("/validateToken")
   public ResponseEntity<?> validateToken(HttpServletRequest request) {
      String jwt = getJwtFromRequest(request);
      return ResponseEntity.ok(tokenProvider.validateToken(jwt));
   }

   @GetMapping("/renewToken")
   public ResponseEntity<?> renewToken(HttpServletRequest request) {
      String jwt = getJwtFromRequest(request);
      String jwtRenew = tokenProvider.renewToken(jwt);
      if (StringUtils.isNotEmpty(jwtRenew)) {
         return ResponseEntity.ok(jwtRenew);
      } else {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
   }

   @PostMapping("/logout")
   public ResponseEntity<?> logout(HttpServletResponse httpResponse) {
      httpResponse.addCookie(getLogoutJwtCookie());
      return ResponseEntity.status(HttpStatus.OK).build();
   }

   @RequestMapping(value = "/activate", method = RequestMethod.PUT)
   public ResponseEntity<AppUser> activate(@Valid @RequestBody ActivateRequest activateRequest) {
      Optional<AppUser> user = appUserRepository.findByEmail(activateRequest.getEmail());
      if (user.isPresent()) {
         AppUser userExist = user.get();
         if (userExist.getStatus() == EUserStatus.INACTIVE && StringUtils.equalsIgnoreCase(userExist.getActivatioToken(),
               activateRequest.getActivatioToken()) && isActiveToken(userExist.getActivationTokenValidity())) {
            userExist.setStatus(EUserStatus.ACTIVE);
            userExist.setActivationTokenValidity(LocalDateTime.MIN);
            userExist.setPassword(passwordEncoder.encode(activateRequest.getPassword()));
            return new ResponseEntity<>(appUserRepository.save(userExist), HttpStatus.OK);
         } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
      } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }

   @RequestMapping(value = "/sendRecovery/{email}", method = RequestMethod.GET)
   public ResponseEntity<AppUser> sendRecovery(@PathVariable(value = "email") String email)
         throws TemplateException, IOException, MessagingException {
      Optional<AppUser> user = appUserRepository.findByEmail(email);
      if (user.isPresent() && user.get().getStatus() == EUserStatus.ACTIVE) {
         AppUser userExist = user.get();
         userExist.setRecoveryToken(secureRandomService.getActivationToken());
         userExist.setRecoveryTokenValidity(LocalDateTime.now().plusMinutes(15));
         emailService.sendUserEmail(userExist, EmailService.EmailTypes.RECOVERY);
         return new ResponseEntity<>(appUserRepository.save(userExist), HttpStatus.OK);
      } else {
         return new ResponseEntity<>(HttpStatus.OK);
      }
   }

   @RequestMapping(value = "/recovery", method = RequestMethod.PUT)
   public ResponseEntity<AppUser> activate(@Valid @RequestBody RecoveryRequest recoveryRequest) {
      Optional<AppUser> user = appUserRepository.findByEmail(recoveryRequest.getEmail());
      if (user.isPresent()) {
         AppUser userExist = user.get();
         if (userExist.getStatus() == EUserStatus.ACTIVE && StringUtils.equalsIgnoreCase(userExist.getRecoveryToken(),
               recoveryRequest.getRecoveryToken()) && isActiveToken(userExist.getRecoveryTokenValidity())) {
            userExist.setPassword(passwordEncoder.encode(recoveryRequest.getNewPassword()));
            userExist.setRecoveryTokenValidity(LocalDateTime.MIN);
            return new ResponseEntity<>(appUserRepository.save(userExist), HttpStatus.OK);
         } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
      } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }

   private boolean isActiveToken(LocalDateTime expDateTime) {
      return expDateTime.isBefore(LocalDateTime.now());
   }

}
