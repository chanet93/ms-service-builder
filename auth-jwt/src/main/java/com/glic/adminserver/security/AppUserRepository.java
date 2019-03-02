package com.glic.adminserver.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by MauricioF1 on 08/05/2018.
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface AppUserRepository extends JpaRepository<AppUser, String> {

   Optional<AppUser> findByEmail(String email);

   void deleteByEmail(String email);

   boolean existsByEmail(String email);
}
