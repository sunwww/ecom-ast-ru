package ru.ecom.expomc.ejb.services.form.importformat.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.Properties;

/**
 * @author ikouzmin 19.03.2007 15:36:30
 */
@Deprecated
public class SystemDirectory {

    private static final Logger LOG = Logger.getLogger(SystemDirectory.class) ;


    private static Properties getEcomProperties() throws Exception {
        Properties prop = new Properties();

        String s = System.getProperty("jboss.server.config.url");
        LOG.info("s='"+s+"'");
        return null;


        /*String filename = "";
        try {
            filename = JBossConfigUtil.getConfigDirname()+"/ecom.properties";
            FileInputStream in = new FileInputStream(filename);
            try {
                prop.load(in) ;
                return prop;
            } finally {
                in.close() ;
            }
        } catch (Exception e) {
            LOG.warn("Ошибка загрузки файла "+filename+" : "+e) ;
            throw e;
        } */


    }

    public static File getImportTempDirectory() {
        //return new File("/tmp");
        try {
            return new File(getEcomProperties().getProperty("tomcat.export.dir","/tmp"));
        } catch (Exception e) {
            return new File("/tmp");
        }
    }
}
