package ru.ecom.diary.ejb.stac.test;

import java.rmi.RemoteException;
import java.sql.Date;

import ru.ecom.diary.ejb.stac.IStacDoctorService;
import ru.ecom.diary.ejb.stac.IStacSlsService;
import ru.ecom.diary.ejb.stac.StacInjection;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 14.12.2006
 * Time: 11:34:28
 * To change this template use File | Settings | File Templates.
 */
public class StacSlsServiceExample implements IStacSlsService {

    public Date getDateFrom(long aSlsId) throws RemoteException {
        return new Date(new java.util.Date().getTime());
    }

    public Date getDateTo(long aSlsId) throws RemoteException{
        return new Date(new java.util.Date().getTime());  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IStacDoctorService getDoctor(long aSlsId) throws RemoteException {
        return StacInjection.getInstance().getDoctorService() ;
    }


}
