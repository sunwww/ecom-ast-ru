package ru.ecom.diary.ejb.stac.test;

import ru.ecom.diary.ejb.stac.IStacDepartamentService;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 11.01.2007
 * Time: 11:14:37
 * To change this template use File | Settings | File Templates.
 */
public class StacDepartamentServiceExample implements IStacDepartamentService {
    public ArrayList getChild(long aId) {

        return new ArrayList() ;
    }
    public Long getParent(long aId) {
        switch((int)aId) {
//            case 0:
//                return Long.valueOf(1) ;
            case 1:
                return 0L ;
            case 2:
                return 1L ;
            case 3:
                return 1L ;
            case 4:
                return 2L ;
            default:
                return null ;

        }

//        return Long.valueOf(1) ;
    }
//    public
}
