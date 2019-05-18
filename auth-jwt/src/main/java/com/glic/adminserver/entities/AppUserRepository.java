package com.glic.adminserver.entities;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.glic.adminserver.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

   Optional<AppUser> findByEmail(String email);

   void deleteByEmail(String email);

   boolean existsByEmail(String email);

   @Query(value = "SELECT * FROM APP_USER WHERE email like %?1%", countQuery = "SELECT count(*) FROM APP_USER WHERE email like %?1%", nativeQuery =
         true)
   Page<AppUser> findByEmail(Optional<String> email, Pageable pageable);

}
