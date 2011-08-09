package ru.ecom.diary.ejb.stac;

import java.rmi.RemoteException;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 14.12.2006
 * Time: 11:21:54
 * To change this template use File | Settings | File Templates.
 */
public interface IStacPatientService {
    String getFio(long aPatientId) throws RemoteException ;
    Date getBirhday(long aPatientId) throws RemoteException  ;
    String getAddress(long aPatientId) throws RemoteException  ;
}
