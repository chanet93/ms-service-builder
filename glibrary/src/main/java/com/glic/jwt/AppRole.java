package com.glic.jwt;

import java.util.ArrayList;
import java.util.List;

public class AppRole {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String ROLE_GTOKEN_ONLINE = "ROLE_GTOKEN_ONLINE";

    public static final String ROLE_GTOKEN_ADMIN = "ROLE_GTOKEN_ADMIN";

    public static final String ROLE_GPORTAL_ADMIN = "ROLE_GPORTAL_ADMIN";

    public static final String ROLE_GPORTAL_MUSER = "ROLE_GPORTAL_MUSER";

    //GTOKEN ROLES


    public static List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add(ROLE_ADMIN);
        roles.add(ROLE_GTOKEN_ONLINE);
        roles.add(ROLE_GTOKEN_ADMIN);
        roles.add(ROLE_GPORTAL_ADMIN);
        roles.add(ROLE_GPORTAL_MUSER);
        return roles;
    }

}
