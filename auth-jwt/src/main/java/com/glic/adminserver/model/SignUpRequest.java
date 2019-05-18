package com.glic.adminserver.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequest {

   @Id
   @javax.validation.constraints.NotBlank
   @Size(max = 50)
   private String email;

   @javax.validation.constraints.NotBlank
   private String nameToShow;

   @javax.validation.constraints.NotBlank
   @Size(max = 255)
   private String description;

   @NotNull
   private String role;

   @javax.validation.constraints.NotBlank
   private String entityId;

}
