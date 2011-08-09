package ru.ecom.expomc.ejb.services.exportformat.driver;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.expomc.ejb.services.exportformat.IExportFomatDriver;

/**
 * @author ikouzmin 14.03.2007 11:06:07
 */
public class DriverManager {
    private final static Logger LOG = Logger.getLogger(DriverManager.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;


    public static IExportFomatDriver getDriver(String driverString, EntityManager theManager, boolean aNative, String query) throws Exception {

        String[] driverParams = (driverString+"::~").split(":");
        String driverName = driverParams[0];
        String driverConfig = driverParams[1];
        if (driverName.equals("")) driverName = "hibernate";
        
        if (driverName.equals("hibernate")) {
            return new DefaultExportDriver(theManager,false,query,driverConfig);
        } else if (driverName.equals("sql")) {
            return new DefaultExportDriver(theManager,true,query,driverConfig);
        } else if (driverName.equals("voc")) {
            return new VocExportDriver(theManager,query);
        } else if (driverName.equals("voc-check")) {
            return new VocCheckExportDriver(theManager,query);
        }

        throw new Exception("Driver not found");
    }
}
