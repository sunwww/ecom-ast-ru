package ru.ecom.ejb.util.injection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.util.JBossConfigUtil;
import ru.nuzmsh.util.StringUtil;

public class EjbEcomConfig {

	private final static Logger LOG = Logger.getLogger(EjbEcomConfig.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public static final String VOC_DIR_PREFIX = "voc.dir.prefix";
    public static final String FORM_JS_PREFIX = "form.js.prefix";
    public static final String SCRIPT_SERVICE_PREFIX = "script.service.prefix";
	
	private static final EjbEcomConfig INSTANCE = new EjbEcomConfig() ; 
	
	private EjbEcomConfig() {}
	
	public static EjbEcomConfig getInstance() {
		return INSTANCE;
	}

	
	private void reloadProperties() {
		try {
			theEcomProperties.clear() ;
			FileInputStream in = new FileInputStream(JBossConfigUtil.getConfigDirname()+"/ecom.properties") ;
			try {
				theEcomProperties.load(in) ;
			} finally {
				in.close() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
		}
		
	}
	
	public String get(String aKey, String aDefaultValue) {
		reloadProperties();
		return theEcomProperties.getProperty(aKey, aDefaultValue);
	}
	
    public InputStream getInputStream(String aResourceString, String aPrefixConfigKey) throws FileNotFoundException {
    	return getInputStream(aResourceString, aPrefixConfigKey, true) ;
    }
    public InputStream getInputStream(String aResourceString, String aPrefixConfigKey, boolean aShowException) throws FileNotFoundException {
    	String append = get(aPrefixConfigKey, "") ;
    	InputStream in ;
    	if(StringUtil.isNullOrEmpty(append)) {
    		if(CAN_DEBUG) LOG.debug("Loading resource " +aResourceString) ;
    		in = getClass().getResourceAsStream(aResourceString);
            if(in==null) {
            	if(aShowException) {
                	throw new IllegalStateException("Ресурс "+aResourceString+" не найден") ;
            	} else {
            		if (CAN_DEBUG) LOG.debug("Ресурс "+aResourceString+" не найден");
            	}
            }
    	} else {
    		File file = new File(append + aResourceString);
    		if(!file.exists()) {
    			if(aShowException) {
    				throw new IllegalStateException("Не найден файл "+file.getAbsolutePath()) ;
    			} else {
    				if (CAN_DEBUG) LOG.debug("Не найден файл "+file.getAbsolutePath());
    				in = null ;
    			}
    		} else {
        		if(CAN_DEBUG) LOG.debug("Loading file "+file.getAbsolutePath());
        		in = new FileInputStream(file) ;
    		}
    	}
		return in ;
    }
	
	private final Properties theEcomProperties = new Properties() ;
	
}
