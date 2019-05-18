package com.glic.adminserver.model;

import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ActivateRequest {

   @Id
   @javax.validation.constraints.NotBlank
   @Size(max = 50)
   private String email;

   @Size(max = 255)
   private String activationToken;

   @javax.validation.constraints.NotBlank
   @Size(max = 50)
   private String password;
}
