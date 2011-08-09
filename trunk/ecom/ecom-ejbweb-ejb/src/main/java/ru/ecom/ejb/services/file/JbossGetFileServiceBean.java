package ru.ecom.ejb.services.file;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.Service;

import ru.ecom.ejb.services.util.JBossConfigUtil;

/**
 *
 */
@Service
@Remote(IJbossGetFileService.class)
@Local(IJbossGetFileLocalService.class)
public class JbossGetFileServiceBean implements IJbossGetFileService, IJbossGetFileLocalService{

    private final static Logger LOG = Logger.getLogger(JbossGetFileServiceBean.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;
    

    private static long theNext = 1;
    //private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");

    private String getTomcatExportDir() {
        Properties prop = new Properties();
        String filename = JBossConfigUtil.getConfigDirname()+"/ecom.properties" ;
        try {
            FileInputStream in = new FileInputStream(filename);
            try {
                prop.load(in) ;
            } finally {
                in.close() ;
            }
        } catch (Exception e) {
            LOG.warn("Ошибка загрузки файла "+filename+" : "+e) ;
        }
        String dir = prop.getProperty("tomcat.export.dir", "/opt/tomcat/webapps/export") ;
        return dir!=null ? dir : "/opt/tomcat/webapps/export" ;
    }

    public String getRelativeFilename(long aFileId) {
        if(!theHash.containsKey(aFileId)) {
            throw new IllegalArgumentException("Нет загеристрированного файла с идентификатором "+aFileId) ;
        }
        return theHash.get(aFileId).getRelativeFilename() ;
    }

    // IKO 070404 +++
    public String getAbsoluteFilename(long aFileId) {
        StringBuffer sb = new StringBuffer();
        String exportDir = getTomcatExportDir();
        sb.append(exportDir);
        if (!exportDir.endsWith("/")) {
            sb.append("/");
        }
        sb.append(getRelativeFilename(aFileId));
        return sb.toString();
    }
    // IKO 070404 ===

    private String getUniqueString() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSSSSS").format(new Date()) ;
    }

    public long register() {
        long id = getNext() ;
        StringBuilder sb = new StringBuilder();
        sb.append(theContext.getCallerPrincipal().getName()) ;
        sb.append('/') ;
        sb.append(getUniqueString()) ;
        sb.append('/') ;
        sb.append(id) ;
        JbossFileInfoLocal file = new JbossFileInfoLocal(id, getTomcatExportDir(), sb.toString());
        theHash.put(id, file) ;
        return id ;
    }

    public File createFile(long aId, String aFilename) {
        if(!theHash.containsKey(aId)) {
            throw new IllegalArgumentException("Нет загеристрированного файла с идентификатором "+aId) ;
        }
        return theHash.get(aId).createFile(aFilename) ;
    }

    private static synchronized long getNext() {
        return theNext++ ;
    }


    private final HashMap<Long, JbossFileInfoLocal> theHash = new HashMap<Long, JbossFileInfoLocal>();
    private @Resource SessionContext theContext;

}
