package ru.ecom.ejb.util.injection;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.util.JBossConfigUtil;
import ru.nuzmsh.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class EjbEcomConfig {

    private static final Logger LOG = Logger.getLogger(EjbEcomConfig.class);

    public static final String VOC_DIR_PREFIX = "voc.dir.prefix";
    public static final String FORM_JS_PREFIX = "form.js.prefix";
    public static final String SCRIPT_SERVICE_PREFIX = "script.service.prefix";

    private static final EjbEcomConfig INSTANCE = new EjbEcomConfig();

    private EjbEcomConfig() {
    }

    public static EjbEcomConfig getInstance() {
        return INSTANCE;
    }


    private void reloadProperties() {
        try {
            ecomProperties.clear();
            FileInputStream in = new FileInputStream(JBossConfigUtil.getConfigDirname() + "/ecom.properties");
            try {
                ecomProperties.load(in);
            } finally {
                in.close();
            }
        } catch (Exception e) {
            LOG.error(e);
        }

    }

    public String get(String aKey, String aDefaultValue) {
        reloadProperties();
        return ecomProperties.getProperty(aKey, aDefaultValue);
    }

    public InputStream getInputStream(String aResourceString, String aPrefixConfigKey) throws FileNotFoundException {
        return getInputStream(aResourceString, aPrefixConfigKey, true);
    }

    public InputStream getInputStream(String aResourceString, String aPrefixConfigKey, boolean aShowException) throws FileNotFoundException {
        String append = get(aPrefixConfigKey, "");
        InputStream in;
        if (StringUtil.isNullOrEmpty(append)) {
            in = getClass().getResourceAsStream(aResourceString);
            if (in == null) {
                if (aShowException) {
                    throw new IllegalStateException("Ресурс " + aResourceString + " не найден");
                }
            }
        } else {
            File file = new File(append + aResourceString);
            if (!file.exists()) {
                if (aShowException) {
                    throw new IllegalStateException("Не найден файл " + file.getAbsolutePath());
                } else {
                    in = null;
                }
            } else {
                in = new FileInputStream(file);
            }
        }
        return in;
    }

    private final Properties ecomProperties = new Properties();

}
