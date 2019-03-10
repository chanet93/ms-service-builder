package com.glic.adminserver.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.glic.adminserver.model.AppUser;
import com.glic.adminserver.entities.AppUserRepository;
import com.glic.adminserver.model.EUserStatus;
import com.glic.jwt.AppRole;

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
            createTestingUser("root@root.com", "root", AppRole.ROLE_ADMIN, "root", EUserStatus.ACTIVE);
            createTestingUser("deleted@root.com", "deleted", AppRole.ROLE_ADMIN, "deleted user", EUserStatus.DELETED);
            createTestingUser("inactive@root.com", "inactive", AppRole.ROLE_ADMIN, "inactive user", EUserStatus.INACTIVE);
         } catch (Exception e) {
            LOG.error("Error on start up", e);
         }
         this.started = true;
      }
   }

   private void createTestingUser(String email, String password, String role, String userToShow, EUserStatus status) {
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
      user.setStatus(status);
      appUserRepository.save(user);

   }
}
