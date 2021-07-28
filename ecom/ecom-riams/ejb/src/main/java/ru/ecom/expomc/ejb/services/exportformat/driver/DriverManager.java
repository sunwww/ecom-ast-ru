package ru.ecom.expomc.ejb.services.exportformat.driver;

import ru.ecom.expomc.ejb.services.exportformat.IExportFormatDriver;

import javax.persistence.EntityManager;

/**
 * @author ikouzmin 14.03.2007 11:06:07
 */
public class DriverManager {


    public static IExportFormatDriver getDriver(String driverString, EntityManager manager, String query) throws Exception {

        String[] driverParams = (driverString + "::~").split(":");
        String driverName = driverParams[0];
        String driverConfig = driverParams[1];
        if (driverName.equals("")) driverName = "hibernate";

        switch (driverName) {
            case "hibernate":
                return new DefaultExportDriver(manager, false, query, driverConfig);
            case "sql":
                return new DefaultExportDriver(manager, true, query, driverConfig);
            case "voc":
                return new VocExportDriver(manager, query);
            case "voc-check":
                return new VocCheckExportDriver(manager, query);
        }

        throw new Exception("Driver not found");
    }
}
