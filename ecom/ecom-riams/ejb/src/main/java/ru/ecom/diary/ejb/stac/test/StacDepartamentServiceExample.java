package ru.ecom.diary.ejb.stac.test;

import java.util.ArrayList;

import ru.ecom.diary.ejb.stac.IStacDepartamentService;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 11.01.2007
 * Time: 11:14:37
 * To change this template use File | Settings | File Templates.
 */
public class StacDepartamentServiceExample implements IStacDepartamentService {
    public ArrayList getChild(long aId) {
        ArrayList list = new ArrayList() ;

        return list ;
    }
    public Long getParent(long aId) {
        switch((int)aId) {
//            case 0:
//                return Long.valueOf(1) ;
            case 1:
                return Long.valueOf(0) ;
            case 2:
                return Long.valueOf(1) ;
            case 3:
                return Long.valueOf(1) ;
            case 4:
                return Long.valueOf(2) ;
            default:
                return null ;

        }

//        return Long.valueOf(1) ;
    }
//    public
}
