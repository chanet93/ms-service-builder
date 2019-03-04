package com.glic.adminserver.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.glic.adminserver.security.AppUser;
import com.glic.adminserver.security.AppUserRepository;
import com.glic.jwt.EAppRole;

@Component
public class SecurityStartUp implements ApplicationListener<ApplicationReadyEvent> {

   private static final Logger LOG = LoggerFactory.getLogger(SecurityStartUp.class);

   @Autowired
   private AppUserRepository appUserRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   private boolean started = false;

   @Override
   public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

      if (!started) {
         try {
            createTestingUser("root@root.com", "root", EAppRole.ADMIN, "root");
         } catch (Exception e) {
            LOG.error("Error on start up", e);
         }
         this.started = true;
      }

   }

   private void createTestingUser(String email, String password, EAppRole role, String userToShow) {
      if (appUserRepository.existsByEmail(email)) {
         appUserRepository.deleteByEmail(email);
      }

      LOG.info("User to be created:{}", email);
      AppUser user = new AppUser();
      user.setEmail(email);
      user.setPassword(passwordEncoder.encode(password));
      user.setRole(role);
      user.setNameToShow(userToShow);
      user.setDescription(userToShow);
      user.setEntityId("entity-0001");
      appUserRepository.save(user);

   }
}
