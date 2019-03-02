package com.glic.jwt;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Created by MauricioF1 on 08/05/2018.
 */
@Data
public class LoginRequest {

   @NotBlank
   private String email;

   @NotBlank
   private String password;

   private EAppRole role;

}
