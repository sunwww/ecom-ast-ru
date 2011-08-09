package ru.ecom.diary.ejb.stac.test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import ru.ecom.diary.ejb.stac.IStacDoctorService;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 14.12.2006
 * Time: 11:53:15
 * To change this template use File | Settings | File Templates.
 */
public class StacDoctorServiceExample implements IStacDoctorService {
    public String getFio(long aId) throws RemoteException {
        switch ((int)aId) {
            case 1:
                return ""  ;
            case 2:
                return "" ;
            case 3:
                return "" ;
            case 4:
                return "Смальков Георгий Иванович" ;
            case 5:
                return "Петров Петр Петрович" ;
            case 6:
                return "Иванов Иван Иванович" ;
            case 7:
                return "Сидоров Сид Сидорович" ;
            default:
                return null ;
        }
    }



    public ArrayList<Long> getChild(long aId) throws RemoteException {
        ArrayList<Long> list = new ArrayList<Long>();
        switch ((int)aId) {
            case 0:
                list.add(Long.valueOf(1)) ;
            case 1:
                list.add(Long.valueOf(2)) ;
                list.add(Long.valueOf(3)) ;
                break ;
            case 2:
                list.add(Long.valueOf(4)) ;
                break ;
            case 3:
                list.add(Long.valueOf(5)) ;
                break ;
            default:
                list = null ;
        }
        return list ;
    }

    public ArrayList<Long> getDepartment(long aId) throws RemoteException {
        return new ArrayList<Long>() ;
    }
}
