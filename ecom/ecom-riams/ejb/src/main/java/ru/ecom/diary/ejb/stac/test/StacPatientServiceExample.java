package ru.ecom.diary.ejb.stac.test;

import java.rmi.RemoteException;
import java.sql.Date;

import ru.ecom.diary.ejb.stac.IStacPatientService;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 14.12.2006
 * Time: 11:23:30
 * To change this template use File | Settings | File Templates.
 */
public class StacPatientServiceExample implements IStacPatientService {
    public String getFio(long aPatientId) throws RemoteException{
        return "Иванов ИИ" ;
    }

    public Date getBirhday(long aPatientId) throws RemoteException{
        return new Date(new java.util.Date().getTime()) ;
    }

    public String getAddress(long aPatientId) throws RemoteException {
        return "г. Астрахань ул. Моздокская, 53";
    }


}
