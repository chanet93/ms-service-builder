package com.glic.adminserver.security;

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
import com.glic.jwt.EAppRole;

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
   @NotBlank
   @Size(max = 255)
   private String password;

   @NotBlank
   @Size(max = 255)
   private String description;

   private EAppRole role;

   private String entityId;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<GrantedAuthority> authorities = new HashSet<>();
      String roleName = StringUtils.startsWith(role.name(), "ROLE_") ? role.name() : "ROLE_" + role.name();
      authorities.add(new SimpleGrantedAuthority(roleName));
      return authorities;
   }

   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

}
