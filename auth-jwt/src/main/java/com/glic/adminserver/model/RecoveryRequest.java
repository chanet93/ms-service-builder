package com.glic.adminserver.model;

import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Created by MauricioF1 on 08/05/2018.
 */
@Data
public class RecoveryRequest {

   @Id
   @javax.validation.constraints.NotBlank
   @Size(max = 50)
   private String email;

   @Size(max = 255)
   @javax.validation.constraints.NotBlank
   private String recoveryToken;

   @javax.validation.constraints.NotBlank
   @Size(max = 50)
   private String newPassword;

}
