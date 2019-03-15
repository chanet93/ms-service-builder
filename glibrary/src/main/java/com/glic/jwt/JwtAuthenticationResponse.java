package com.glic.jwt;

import lombok.Data;


@Data
public class JwtAuthenticationResponse {

   private String accessToken;

   private String tokenType = "Bearer";

}
