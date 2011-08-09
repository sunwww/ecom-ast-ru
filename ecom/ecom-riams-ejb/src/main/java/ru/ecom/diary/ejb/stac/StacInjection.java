package ru.ecom.diary.ejb.stac;

import ru.ecom.diary.ejb.stac.test.StacDoctorServiceExample;
import ru.ecom.diary.ejb.stac.test.StacPatientServiceExample;
import ru.ecom.diary.ejb.stac.test.StacPolyceServiceExample;
import ru.ecom.diary.ejb.stac.test.StacSloServiceExample;
import ru.ecom.diary.ejb.stac.test.StacSlsServiceExample;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 14.12.2006
 * Time: 11:22:56
 * To change this template use File | Settings | File Templates.
 */
public class StacInjection {

    private StacInjection() {

    }

    public static StacInjection getInstance() {
        return new StacInjection();
    }

    public IStacPatientService getPatientService() {
        return new StacPatientServiceExample() ;
    }
    public IStacDoctorService getDoctorService() {
        return new StacDoctorServiceExample() ;
    }
    public IStacSlsService getSlsService() {
        return new StacSlsServiceExample() ;
    }

    public IStacSloService getSloService() {
        return new StacSloServiceExample() ;
    }

    public IStacPolyceService getPolyceService() {
        return new StacPolyceServiceExample() ;
    }

}
