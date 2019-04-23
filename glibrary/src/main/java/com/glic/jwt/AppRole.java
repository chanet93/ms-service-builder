package com.glic.jwt;

import java.util.ArrayList;
import java.util.List;

import com.glic.payment.model.general.BasicString;

public class AppRole {

   public static final String ROLE_ADMIN = "ROLE_ADMIN";

   public static final String ROLE_GTOKEN_ONLINE = "ROLE_GTOKEN_ONLINE";

   public static final String ROLE_GTOKEN_ADMIN = "ROLE_GTOKEN_ADMIN";

   public static final String ROLE_GPORTAL_ADMIN = "ROLE_GPORTAL_ADMIN";

   public static final String ROLE_GPORTAL_MUSER = "ROLE_GPORTAL_MUSER";

   //GTOKEN ROLES

   public static List<BasicString> getRoles() {
      List<BasicString> roles = new ArrayList<>();
      roles.add(BasicString.builder().name(ROLE_ADMIN).build());
      roles.add(BasicString.builder().name(ROLE_GTOKEN_ONLINE).build());
      roles.add(BasicString.builder().name(ROLE_GTOKEN_ADMIN).build());
      roles.add(BasicString.builder().name(ROLE_GPORTAL_ADMIN).build());
      roles.add(BasicString.builder().name(ROLE_GPORTAL_MUSER).build());
      return roles;
   }

}
