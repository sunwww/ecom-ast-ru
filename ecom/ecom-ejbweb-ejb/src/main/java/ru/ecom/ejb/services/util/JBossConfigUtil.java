package ru.ecom.ejb.services.util;

import java.io.File;
import java.io.FileNotFoundException;

/**
 */
public class JBossConfigUtil {

    private static final String JBOSS_SERVER_DATA_DIR = System.getProperty("jboss.server.data.dir") ;
    private static final String JBOSS_SERVER_CONFIG_DIR ;

    static {
        String dir = System.getProperty("jboss.server.config.url"
        		, "file:/opt/jboss-4.0.4.GAi/server/default/conf") // FIXME
            .substring("file:\\".length()) ;
        if(dir.indexOf(':')==-1) {
            JBOSS_SERVER_CONFIG_DIR = "/"+dir ;
        } else {
            JBOSS_SERVER_CONFIG_DIR = dir ;
        }
    }

    public static File getDataFile(String aFilename) throws FileNotFoundException {
    	File file = new File(JBOSS_SERVER_DATA_DIR+"/"+aFilename) ;
    	if(!file.exists()) throw new FileNotFoundException("Нет файла "+file.getAbsolutePath()) ;
    	return file ;
    }
    public static String getConfigDirname() {
        return JBOSS_SERVER_CONFIG_DIR ;
    }
}
