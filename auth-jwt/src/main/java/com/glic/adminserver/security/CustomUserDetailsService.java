package com.glic.adminserver.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
   private AppUserRepository userRepository;

   @Override
   @Transactional
   public UserDetails loadUserByUsername(String email) {

      Optional<AppUser> user = userRepository.findByEmail(email);

      if (!user.isPresent()) {
         throw new BadCredentialsException("Bad credentials");
      }

      new AccountStatusUserDetailsChecker().check(user.get());
      return user.get();
   }

   // This method is used by JWTAuthenticationFilter
   @Transactional
   public UserDetails loadUserById(String email) {
      AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + email));

      return user;
   }

}



