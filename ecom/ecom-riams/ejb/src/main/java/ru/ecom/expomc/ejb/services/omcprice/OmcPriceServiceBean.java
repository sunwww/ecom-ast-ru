package ru.ecom.expomc.ejb.services.omcprice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ru.ecom.alg.omc.omcprice.IOmcPriceService;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcDepType;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKsg;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcLpuDepartmentLevel;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcProfilDs;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcTariff;
import ru.ecom.expomc.ejb.services.timeservice.IImportTimeService;

/**
 * Получение необходимых данных для расчета цены
 */
@Stateless
@Remote(IOmcPriceService.class)
@Local(IOmcPriceService.class)
public class OmcPriceServiceBean implements IOmcPriceService {
    private final static Logger LOG = Logger.getLogger(OmcPriceServiceBean.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    public BigDecimal findTariffByUsl(Date aActualDate, String aUslOmcCode, int aLevel, boolean aIsChild) {
        long time = theTimeService.findTime(OmcTariff.class, aActualDate) ;
        if (CAN_DEBUG) {
            LOG.debug("aActualDate = " + aActualDate);
            LOG.debug("aUslOmcCode = " + aUslOmcCode);
            LOG.debug("aLevel = " + aLevel);
            LOG.debug("aIsChild = " + aIsChild);
            LOG.debug("time = " + time);
        }
        List<OmcTariff> list = theManager.createNamedQuery("OmcTariff.findByTimeAndCode")
                .setParameter("time", time)
                .setParameter("code", aUslOmcCode)
                .setMaxResults(1)
                .getResultList();
        if (CAN_DEBUG) LOG.debug("list = " + list);
        if(list!=null && !list.isEmpty()) {
            OmcTariff tariff = list.iterator().next() ;
            if (CAN_DEBUG) LOG.debug("tariff.getX1() = " + tariff.getX1());
            if (CAN_DEBUG) LOG.debug("tariff.getX2() = " + tariff.getX2());
            if (CAN_DEBUG) LOG.debug("tariff.getX3() = " + tariff.getX3());
            if (CAN_DEBUG) LOG.debug("tariff.getX4() = " + tariff.getX4());
            BigDecimal ret ;
            switch(aLevel) {
                case 1: ret = tariff.getX1() ; break ;
                case 2: ret = tariff.getX2() ; break ;
                case 3: ret = tariff.getX3() ; break ;
                case 4: ret = tariff.getX4() ; break ;
                default: throw new IllegalArgumentException("Неизвестный уровень "+aLevel) ;
            }
            return ret;
        } else return null;
    }

    private static <T> T getFirst(Class<T> aClass, Query aQuery) {
        List<T> list = aQuery.setMaxResults(1).getResultList();
        if(list!=null && !list.isEmpty()) {
            return list.iterator().next();
        } else return null ;
    }

//    /** Сначало по ребенку, потом по взрослому */
//	public BigDecimal findAverageDaysByDailyHospital(Date aDischargeDate, String aDepartmentCode, int aLevel, boolean aIsChild) {
//    	BigDecimal ret = _findAverageDaysByDailyHospital(aDischargeDate, aDepartmentCode, aLevel, aIsChild) ;
//    	if(ret==null || ret.intValue()==0) {
//    		ret = _findAverageDaysByDailyHospital(aDischargeDate, aDepartmentCode, aLevel, !aIsChild) ;
//    	}
//    	return ret ;
//    }
//    
    @SuppressWarnings("unchecked")
	public BigDecimal findAverageDaysByDailyHospital(Date aDischargeDate, String aDepartmentCode, int aLevel, boolean aIsChild) {
    	if("0A".equals(aDepartmentCode) && aIsChild) {
    		aIsChild = false ;
    	}
        long time = theTimeService.findTime(OmcProfilDs.class, aDischargeDate) ;
        List<OmcProfilDs> list = theManager.createNamedQuery("OmcProfilDs.findByTimeAndCode")
        .setParameter("time", time)
        .setParameter("code", aDepartmentCode)
        .setMaxResults(1)
        .getResultList();
		if (CAN_DEBUG) LOG.debug("list = " + list);
		if(list!=null && !list.isEmpty()) {
		    OmcProfilDs profilDs = list.iterator().next() ;
		    return aIsChild ? profilDs.getChild1() : profilDs.getAdult1() ;
		} else return null;
		        
        
    }

    public BigDecimal findAverageDaysByHospital(Date aDischargeDate, String aDepartmentCode, boolean aIsChild) {
        long time = theTimeService.findTime(OmcDepType.class, aDischargeDate) ;
        OmcDepType type = getFirst(OmcDepType.class
                , theManager.createNamedQuery("OmcDepType.findByTimeAndCode")
                .setParameter("time",time)
                .setParameter("code", aDepartmentCode)
        ) ;
        return type!=null ? type.getAverageDays() : null ;
    }



    private OmcKsg findKsg(Date aDischargeDate, String aMkb) {
        long time = theTimeService.findTime(OmcKsg.class, aDischargeDate) ;            
        List<OmcKsg> list = theManager.createNamedQuery("OmcKsg.findByTimeAndCode")
                .setParameter("time", time)
                .setParameter("code", aMkb)
                .setMaxResults(1).getResultList();
        OmcKsg ret ;
        if(list!=null && !list.isEmpty()) {
            ret =  list.iterator().next();
        } else ret = null;
        if(CAN_DEBUG) LOG.debug("mkb="+aMkb+", "+ret+", time = "+time) ;
        return ret ;
    }

    public Integer findKsgLevel(Date aDischargeDate, String aMkb, boolean aIsChild) {
        OmcKsg ksg = findKsg(aDischargeDate, aMkb) ;
        if(ksg!=null) {
            BigDecimal level = aIsChild ? ksg.getChildLevel() : ksg.getAdultLevel() ;
            if(level==null) return null ;
            return level.intValue()!=0 ? level.intValue() : null ; 
        } else return null ;
    }


    public BigDecimal findKsgAverageDays(Date aDischargeDate, String aMkb, boolean aIsChild) {
        OmcKsg ksg = findKsg(aDischargeDate, aMkb) ;
        return ksg!=null ? (aIsChild?ksg.getChildDays():ksg.getAdultDays()) : null ;
    }
    //zav
    public Integer findDepartmentLevel(Date aDischargeDate, int aKodLpu, String aOmcDepType){
        StringBuilder query = new StringBuilder("from OmcLpuDepartmentLevel");
        query.append(" where omcLpu = :omcLpu and omcDepartmentType=:omcDepType");
        query.append(" and ((dateTo is null and dateFrom <= :dischargeDate) or (dateTo is not null and :dischargeDate between dateFrom and dateTo))");
    	List<OmcLpuDepartmentLevel> list = theManager.createQuery(query.toString())
        .setParameter("omcLpu", aKodLpu)
        .setParameter("omcDepType", aOmcDepType)
        .setParameter("dischargeDate", aDischargeDate)
        .setMaxResults(1).getResultList();
    	OmcLpuDepartmentLevel ret;
        if(list!=null && !list.isEmpty()) {
            ret =  list.iterator().next();
        } else ret = null;
        Integer level = ret!=null? ret.getOmcLevel() : 0;
    	return level;
    }
    //zav
    @EJB IImportTimeService theTimeService ;
    @PersistenceContext EntityManager theManager ;
}
