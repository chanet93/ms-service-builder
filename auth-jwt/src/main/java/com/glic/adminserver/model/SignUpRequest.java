package com.glic.adminserver.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * Created by MauricioF1 on 08/05/2018.
 */
@Data
public class SignUpRequest {

   @NotBlank
   @Size(max = 40)
   private String email;

   @NotBlank
   @Size(min = 6, max = 20)
   private String password;
}
