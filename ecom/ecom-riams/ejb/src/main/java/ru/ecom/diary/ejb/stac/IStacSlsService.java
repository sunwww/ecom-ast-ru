package ru.ecom.diary.ejb.stac;

import java.rmi.RemoteException;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 14.12.2006
 * Time: 11:27:10
 * To change this template use File | Settings | File Templates.
 */
public interface IStacSlsService {
    Date getDateTo(long aSlsId) throws RemoteException;
    Date getDateFrom(long aSlsId) throws RemoteException;
    IStacDoctorService getDoctor(long aSlsId) throws RemoteException;
}
