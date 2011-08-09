package ru.ecom.miniejb.ctx;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Роли пользователей
 */
public class UsersRoles {

    public Set<String> getRoles(String aUsername) {
        return theHash.get(aUsername) ;
    }

    private void load() {
        File file = new File(theJbossConfigDir, "roles.properties") ;
        if(file.lastModified()!=theVersion) {
//            Properties
            theVersion = file.lastModified() ;

        }
    }

    private File theJbossConfigDir = new File(System.getProperty("jboss.server.config.dir")) ;
    private long theVersion = 0 ;
    private final HashMap<String, HashSet<String>> theHash = new HashMap<String, HashSet<String>>();
}
