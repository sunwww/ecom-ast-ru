package ru.ecom.address.web.dwr;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.ecom.address.ejb.service.IAddressService;
import ru.ecom.address.ejb.service.AddressPointCheck;
import ru.ecom.address.ejb.service.AddressPointCheckHelper;
import ru.ecom.web.util.Injection;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.mis.ejb.service.patient.IPatientService;

import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import javax.ejb.EJBException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 */
public class AddressServiceJs {
    private final static Log LOG = LogFactory.getLog(AddressServiceJs.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;
    public String getAddressRayon(Long aAddressId, String aHouse
    		,HttpServletRequest aRequest) throws NamingException {
    	IAddressService service = Injection.find(aRequest).getService(IAddressService.class);
    	return service.getRayon(aAddressId, aHouse) ;
    }
    public String getAddressNonresidentString(Long aTerritory, String aRegion
        	, Long aTypeSettlement
        	, String aSettlement
        	, Long aTypeStreet
        	, String aStreet
        	, String aHouseNumber
        	, String aHouseBuilding, String aFlatNumber, String aZipCode, HttpServletRequest aRequest) throws NamingException {
            IAddressService service = Injection.find(aRequest).getService(IAddressService.class);
            return service.getAddressNonresidentString(aTerritory, aRegion, aTypeSettlement
            		, aSettlement, aTypeStreet, aStreet, aHouseNumber, aHouseBuilding, aFlatNumber
            		,aZipCode);
            
    	
    }


    public String getAddressString(String aAddressId, String aHouse, String aCorpus, String aFlat, String aZipCode, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aAddressId)) {
            return "";
        } else {
            try {
                IAddressService service = Injection.find(aRequest).getService(IAddressService.class);
                return service.getAddressString(Long.parseLong(aAddressId), aHouse, aCorpus, aFlat,aZipCode);
            } catch (Exception e) {
                e.printStackTrace() ;
                throw new IllegalStateException(e) ;
            }
        }
    }

    public String getIdForLevel(String aLevel, String aAddressId, HttpServletRequest aRequest) throws NamingException {
        long level = Long.parseLong(aLevel);
        IAddressService service = Injection.find(aRequest).getService(IAddressService.class);

        Long code = !StringUtil.isNullOrEmpty(aAddressId) ? Long.valueOf(aAddressId) : null;

        Long ret = service.getIdForLevel(level, code);
        return ret != null ? ret.toString() : "";
    }

    public String parseAddressPoints(long aLpuAreaId, Long aLpuAddressTextId, Long aAddress, String aPoints, HttpServletRequest aRequest) throws Exception {
        if(aAddress==null || aAddress==0) throw new IllegalArgumentException("Не введен адрес") ;
        try {
            AddressPointCheckHelper helper = new AddressPointCheckHelper();
            IAddressPointService service = Injection.find(aRequest).getService(IAddressPointService.class);
            List<AddressPointCheck> list = helper.parsePoints(aPoints) ;
            if(list!=null && !list.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                boolean firstAdded = false ;
                for (AddressPointCheck point : list) {
                    service.checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, point.getNumber(), point.getBuilding(), point.getFlat());
                    if(!firstAdded) {
                        firstAdded = true ;
                    } else {
                        sb.append(", ") ;
                    }
                    sb.append(point.getNumber()) ;
                    if(point.getBuilding()!=null) {
                        sb.append(" корпус ") ;
                        sb.append(point.getBuilding()) ;
                    }
                    if(point.getFlat()!=null) {
                        sb.append(" кв. ") ;
                        sb.append(point.getFlat()) ;
                    }
                }
                return sb.toString() ;
            } else {
                service.checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, null, null, null);
                return "" ;
            }
        } catch (Exception e) {
            if(e instanceof EJBException) {
                EJBException ejbException = (EJBException) e ;
                if(ejbException.getCause()!=null) throw (Exception)ejbException.getCause();
                else throw e;
            } else {
                throw e ;
            }
        }
    }

    public String findPatientLpu(Long aAddress, String aNumber, String aBuilding, String aBirthday, String aFlat, HttpServletRequest aRequest) throws Exception {
        try {
        	java.util.Date utilDate = DateFormat.parseDate(aBirthday);
            return Injection.find(aRequest).getService(IPatientService.class)
            	.findPatientLpuInfo(aAddress, aNumber, aBuilding, new java.sql.Date(utilDate.getTime()), aFlat);
        } catch (Exception e) {
            LOG.error("Ошибка "+e.getMessage(), e) ;
            throw e ;
        }
    }
    public String getZipcode(Long aAddress5, Long aAddress6, HttpServletRequest aRequest) throws Exception {
    	IAddressService service = Injection.find(aRequest).getService(IAddressService.class);
    	return service.getZipcode(aAddress5, aAddress6);
    }

}
