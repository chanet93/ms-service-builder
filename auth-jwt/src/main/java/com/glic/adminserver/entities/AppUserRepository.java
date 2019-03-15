package com.glic.adminserver.entities;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.glic.adminserver.model.AppUser;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

   Optional<AppUser> findByEmail(String email);

   void deleteByEmail(String email);

   boolean existsByEmail(String email);
}
