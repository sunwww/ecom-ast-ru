package ru.ecom.miniejb.impl;

import java.security.Principal;

/**
 *
 */
public class SimplePrincipal implements Principal {
    private final String theName ;

    public SimplePrincipal(String aName) {
        theName=aName;
    }

    public String getName() {
        return theName ;
    }
}
