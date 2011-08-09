package ru.ecom.jaas.ejb.service;

import ru.ecom.ejb.services.util.JBossConfigUtil;

/**
 * @author esinev
 * Date: 24.10.2006
 * Time: 8:58:27
 */
public class Config {

//    private static final String JBOSS_SERVER_CONFIG_DIR = System.getProperty("jboss.server.config.url").substring("file:\\".length()) ;

    public static String getConfigDir() {
        return JBossConfigUtil.getConfigDirname();
    }

}
