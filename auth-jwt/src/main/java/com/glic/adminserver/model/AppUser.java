package com.glic.adminserver.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glic.jwt.EUserStatus;
import com.glic.payment.model.GlicLocalDateTime;

import lombok.Data;

@Entity
@Data
public class AppUser implements UserDetails {

   @Id
   @NotBlank
   @Size(max = 50)
   private String email;

   private String nameToShow;

   @JsonIgnore
   @Size(max = 255)
   private String password;

   @JsonIgnore
   @Size(max = 255)
   private String activatioToken;

   @GlicLocalDateTime
   private LocalDateTime activationTokenValidity;

   @JsonIgnore
   @Size(max = 255)
   private String recoveryToken;

   @GlicLocalDateTime
   private LocalDateTime recoveryTokenValidity;

   @GlicLocalDateTime
   private LocalDateTime lastLogin;

   @NotBlank
   @Size(max = 255)
   private String description;

   private String role;

   private EUserStatus status;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<GrantedAuthority> authorities = new HashSet<>();
      String roleName = StringUtils.startsWith(role, "ROLE_") ? role : "ROLE_" + role;
      authorities.add(new SimpleGrantedAuthority(roleName));
      return authorities;
   }

   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return this.getStatus() == EUserStatus.ACTIVE;
   }

   @Override
   public boolean isAccountNonLocked() {
      return this.getStatus() == EUserStatus.ACTIVE;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return this.getStatus() == EUserStatus.ACTIVE;
   }

   @Override
   public boolean isEnabled() {
      return this.getStatus() == EUserStatus.ACTIVE;
   }

}
