package ru.ecom.expomc.ejb.services.check.checkers.registry;

import java.math.BigDecimal;
import java.util.Collection;

import javax.naming.NamingException;

import ru.ecom.alg.omc.omcprice.CalcOmcPriceServiceHelper;
import ru.ecom.alg.omc.omcprice.IOmcPriceRequest;
import ru.ecom.alg.omc.omcprice.IOmcPriceService;
import ru.ecom.alg.omc.omcprice.OmcCalcPriceException;
import ru.ecom.alg.omc.omcprice.OmcPriceCalcResult;
import ru.ecom.alg.omc.omcprice.impl.OmcPriceRequestImmutable;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.ecom.expomc.ejb.services.check.ICheckLog;
import ru.ecom.expomc.ejb.services.check.checkers.BadPropertyUtil;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Расчет цены с изменением
 */
@Comment("Расчет цены")
public class ChangeCasePrice implements ICheck {

	public ChangeCasePrice() throws NamingException {
		IOmcPriceService service = findService() ;
		theCacheService = service ; //new CacheOmcPriceService(service) ;
	}
    public CheckResult check(ICheckContext aContext) throws CheckException {
        RegistryEntry entry = (RegistryEntry) aContext.getEntry() ;
        //BigDecimal oldPrice = entry.getCasePrice() ;
        //BigDecimal oldCalcDays = entry.getCalcBedDays() ;
        OmcPriceCalcResult price = null;
        try {
            price = calcPrice(entry, aContext.getLog(), aContext);
        } catch (Exception e) {
            throw new IllegalStateException(e) ;
        }
//        boolean accepted  ;
//        if(oldPrice==null && price.getCalcPrice()!=null && oldCalcDays==null) {
//            accepted = true ;
//        } else {
//            accepted = oldPrice.equals(price.getCalcPrice()) ;
//        }
        CheckResult result = CheckResult.createAccepted(false);
    	System.out.println("Entry.CasePrice = "+entry.getCasePrice()+", Entry.CasePrice = "+price.getCalcPrice()) ; //zav
        if(canChange(entry.getCasePrice(), price.getCalcPrice())) {
        	result.setAccepted(true) ;
        	//System.out.println("Cena = "+price.getCalcPrice()) ;
        	result.set("casePrice", price.getCalcPrice());
        }
        if(canChange(entry.getTariff(), price.getTariff())){
//        	if(entry.getTariff()!=null) result.setAccepted(true) ; //zav 
        	result.setAccepted(true) ; //zav        	
        	result.set("tariff", price.getTariff());
        	
        	
        }
        if(canChange(entry.getCalcBedDays(), price.getCalcBedDays())) {
//        	if(entry.getCalcBedDays()!=null) result.setAccepted(true) ; //zav
        	result.setAccepted(true) ; //zav        	
        	result.set("calcBedDays", price.getCalcBedDays());
        }
        if(!(price.getLevel()+"").equals(entry.getLevel()))  {
        	result.setAccepted(true) ;
        	result.set("level", price.getLevel()+"") ;
        }
        System.out.println(result); //zav
        return result ;
    }

    private static boolean canChange(BigDecimal aOld, BigDecimal aNew) {
    	if(aOld==null && aNew!=null) return true ;
    	if(aNew==null && aOld!=null) return true ;
    	if(aOld==null && aNew==null) return false ;
    	return aOld.doubleValue()!=aNew.doubleValue();
    }
    

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create("casePrice","tariff","calcBedDays","level") ;
	}
    
    public OmcPriceCalcResult calcPrice(RegistryEntry aEntry, ICheckLog aLog, ICheckContext aContext) throws OmcCalcPriceException, NamingException {

        boolean isPlanovaya = theCheckIsPlanovaya.isAccepted(aEntry, aLog) ;
        CalcOmcPriceServiceHelper service = new CalcOmcPriceServiceHelper();
        IOmcPriceRequest request = new OmcPriceRequestImmutable(
                aEntry.getBirthDate()
                , aEntry.getAdmissionDate()
                , aEntry.getDischargeDate()
                , aEntry.getBedDays()
                , aEntry.getDiagnosisMain()
                , isPlanovaya
                , aEntry.getRender()
                , 1		//zav
                , aEntry.getDepType()
                , aEntry.getOsl()!=null ? Integer.parseInt(aEntry.getOsl()) : 0
                , false
                , Integer.parseInt(aEntry.getKodLpu()) //zav
        );
        OmcPriceCalcResult result = service.calc(request, theCacheService) ;
        return result ;

    }

    private IOmcPriceService findService() throws NamingException {
    	return EjbInjection.getInstance().getLocalService(IOmcPriceService.class) ;
    }

    //private final IOmcPriceService theService  ;
    private final IOmcPriceService theCacheService  ;
//    private final CheckOnko theOnko = new CheckOnko() ;
//    private final CheckDnevnoyStacionar theDnevnoyStacionar = new CheckDnevnoyStacionar();
//    private final CheckPoliclinic thePoliclinic = new CheckPoliclinic();
//    private final CheckCanUpLevelByOsl theCanUpLevelByOsl = new CheckCanUpLevelByOsl();
//    private final CheckIsChild theCheckIsChild = new CheckIsChild();
//    private final CheckProvisor theProvisor = new CheckProvisor();
    private final CheckIsPlanovaya theCheckIsPlanovaya = new CheckIsPlanovaya();
//    private @EJB IOmcPriceService theOmcPriceService ;
}
