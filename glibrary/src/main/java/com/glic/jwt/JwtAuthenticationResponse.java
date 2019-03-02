package com.glic.jwt;

import lombok.Data;

/**
 * Created by MauricioF1 on 08/05/2018.
 */
@Data
public class JwtAuthenticationResponse {

   private String accessToken;

   private String tokenType = "Bearer";

}
