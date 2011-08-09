package ru.ecom.alg.omc.omcprice;

import ru.ecom.alg.common.IsChild;
import ru.ecom.alg.common.IsOnkoUtil;
import ru.ecom.alg.omc.OmcUslUtil;
import ru.nuzmsh.util.StringUtil;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Расчет цены и уровня по ОМС
 */
public class CalcOmcPriceServiceHelper {

    public OmcPriceCalcResult calc(IOmcPriceRequest aRequest, IOmcPriceService aService) throws OmcCalcPriceException {
        OmcUslUtil.checkUsl(aRequest.getUslOmcCode());
        if(StringUtil.isNullOrEmpty(aRequest.getDepartmentCode())) throw new IllegalArgumentException("Нет кода отделения") ;
        int DepartmentLevel=findDepartmentLevel(aService, aRequest.getDischargeDate(), aRequest.getKodLpu(), aRequest.getDepartmentCode()); //zav
        OmcPriceCalcResult result = new OmcPriceCalcResult();
        CalcLog log = result.getLog();
        log.log(aRequest) ;
        log.log("DepartmentLevel="+DepartmentLevel) ;        
        boolean isChild = theIsChild.isChild(aRequest.getBirthDate(), aRequest.getAdmissionDate()) ;
        String isChildString = isChild ? "ребенок" : "взрослый" ;
        boolean canUpLevel = canUpLevel(log, aRequest.getOsl()) ;
        log.log(isChildString) ;
        int bedDays = aRequest.getBedDays();
        String usluga = aRequest.getUslOmcCode();


        if(OmcUslUtil.isPolyclinic(aRequest.getUslOmcCode())) {
            log.log("Поликлиника [ услуга = "+aRequest.getUslOmcCode(),"]");
            BigDecimal tariff = aService.findTariffByUsl(aRequest.getAdmissionDate(), usluga, 4, isChild) ;
            if(tariff==null) throw new IllegalArgumentException("Нет тарифа для улуги "+usluga+", "+isChildString) ;
            log.log("  Тариф - "+tariff) ;
            result.setCalcPrice(tariff);
            result.setTariff(tariff);
            result.setLevel(4);
            result.setCalcBedDays(new BigDecimal(1));
        } else if(OmcUslUtil.isDailyHospital(aRequest.getUslOmcCode())){
            log.log("Дневной стационар") ;
            result.setLevel(4);
            BigDecimal tarif = findTariffByUsl(log, aRequest.getDischargeDate(), aRequest.getUslOmcCode(), isChild, 4, canUpLevel,aService);
            if(tarif !=null) {
                BigDecimal srKolDney = aService.findAverageDaysByDailyHospital(aRequest.getDischargeDate(), aRequest.getDepartmentCode(), DepartmentLevel, isChild) ;
                if(srKolDney!=null) {
                    log.log("Расчет стоимости по формуле среднее кол.дней * цену [ ср. кол. дней = ", srKolDney, ", цена = ", tarif, " ]");
                    //result.setCalcBedDays(srKolDney);
                    //result.setCalcPrice(multiply(srKolDney, tarif));
                    //result.setTariff(tarif) ;
                	// для дневного стационара не надо!!!	
                    setPriceByDays(log, result, aRequest.getBedDays(), srKolDney, tarif);
                } else {
                    throw new OmcCalcPriceException("Нет среднего кол-во дней по отделению "+aRequest.getDepartmentCode(), log);
                }
            } else {
                throw new OmcCalcPriceException("Нет цены по услуге "+aRequest.getUslOmcCode(),log);
            }
        } else {
            log.log("Стационар") ;
            if(OmcUslUtil.isProvisional(usluga)) {
                log.log("1.0-3.0 Провизорность [ дней=", bedDays, ", услуга=", usluga, " ]");
                setPriceByDepartment(aService, aRequest, result, DepartmentLevel);
            } else {
                String mkb = aRequest.getMkbWithOmcExtension();
                if(IsOnkoUtil.isOnko(mkb)) {
                    log.log("1.1-2.0 Первичная или паллиативная онкопатология") ;
                    setPriceByDepartment(aService, aRequest, result, DepartmentLevel);
                } else {
                    KsgInfo ksg = findKsgInfo(aService, aRequest.getDischargeDate(), mkb, isChild) ;
                    if(ksg==null) {
                        log.log("1.3 - 2.0 Отсутствие уровня по КСГ [МКБ=",mkb,", ",isChildString,"]") ;
                        setPriceByDepartment(aService, aRequest, result, DepartmentLevel);
                    } else {
                        log.log("1.2,1.3-1.4 Наличие уровня по КСГ [уровень КСГ=", ksg.getLevel(), ", ср. койко дней=", ksg.getBedDays(), " , departmentLevel=", DepartmentLevel, "]");
                        BigDecimal tariff = findTariffByUsl(log, aRequest.getDischargeDate(), usluga, isChild, ksg.getLevel(), canUpLevel, aService) ;
                        if(tariff==null) {
                            log.log("1.4 - 2.0 Нет стоимости койко-дня по услуге ",usluga," по уровню ", ksg.getLevel(), ", ", isChildString) ;
                            setPriceByDepartment(aService, aRequest, result, DepartmentLevel);
                        } else {
                            if(ksg.getLevel() < DepartmentLevel) {
                                log.log("Уровень КСГ(",ksg.getLevel(),") меньше уровня по отделению(",DepartmentLevel,")") ;
                                setPriceByDepartment(aService, aRequest, result, DepartmentLevel);
                            } else {
                                log.log("Цена по КСГ") ;
                                result.setLevel(ksg.getLevel());
                                result.setCalcBedDays(ksg.getBedDays());
                                result.setTariff(tariff);
                                result.setCalcPrice(multiply(ksg.getBedDays(), tariff));
                                // кажись, не надо // FIXME НУжно ли 
                                //setPriceByDays(log, result, aRequest.getBedDays(), ksg.getBedDays(), tariff) ;
                            }
                        }
                    }
                }
            }
        }


        if(aRequest.canUseEmergencyKoef() && !aRequest.isPlanovaya()) {
            log.log("Для экстренной госпитализации цена умножена на 1.16 коэффициент") ;
            result.setCalcPrice(result.getCalcPrice().multiply(new BigDecimal(1.16)));
        }
        //log.log(result);
        return result ;
    }

    private KsgInfo findKsgInfo(IOmcPriceService aService, Date aDischargeDate, String aMkb, boolean aIsChild) {
        if(aService==null) throw new IllegalArgumentException("Нет сервиса "+aService+" (IOmcPriceService)") ;
        Integer level = aService.findKsgLevel(aDischargeDate, aMkb, aIsChild);
        BigDecimal days = aService.findKsgAverageDays(aDischargeDate, aMkb, aIsChild);
        return level!=null && level!=0 && !isNullOrZero(days) ? new KsgInfo(level, days) : null ;
    }
//  zav
    private Integer findDepartmentLevel(IOmcPriceService aService, Date aDischargeDate, int aKodLpu, String aOmcDepType){
    	Integer level = aService.findDepartmentLevel(aDischargeDate, aKodLpu, aOmcDepType);
    	return level;	
    }
//  zav
    
    private static boolean isNullOrZero(BigDecimal aNumber) {
    	if(aNumber==null) return true ;
    	return BigDecimal.valueOf(0).compareTo(aNumber)==0 ;
    }
    
    private void setPriceByDepartment(IOmcPriceService aService, IOmcPriceRequest aRequest, OmcPriceCalcResult aResult, int aDepartmentLevel) throws OmcCalcPriceException {
        if(aDepartmentLevel<=0) throw new IllegalArgumentException("Нет уровня отделения [ уровень отделения = "+aDepartmentLevel+"]") ;
        CalcLog log = aResult.getLog();
        log.log("Получение цены по уровню отделения") ;
        boolean isChild = theIsChild.isChild(aRequest.getBirthDate(), aRequest.getAdmissionDate());
        boolean canUpLevel = canUpLevel(log, aRequest.getOsl()) ;

        BigDecimal tariff = findTariffByUsl(log, aRequest.getDischargeDate(), aRequest.getUslOmcCode(), isChild, aDepartmentLevel
                , canUpLevel, aService) ;

        if(tariff==null) throw new OmcCalcPriceException("Нет тарифа по отделению [ отделение = "+aRequest.getDepartmentCode()+", услуга =  "+aRequest.getUslOmcCode()+"]",log);
        log.log("  Тариф = "+tariff) ;
        BigDecimal avarage = aService.findAverageDaysByHospital(aRequest.getDischargeDate(), aRequest.getDepartmentCode(), isChild) ;
        if(avarage==null) throw new OmcCalcPriceException("Нет среднего количества дней [ код отделения="
                +aRequest.getDepartmentCode()
                +", "+(isChild?"ребенок":"взрослый")
                +" ]",log);

        aResult.setLevel(aDepartmentLevel);

        if(OmcUslUtil.isProvisional(aRequest.getUslOmcCode())) {
            log.log("  Отделение. Вычисление цены по настоящим койко дням : [ койко-дней=", aRequest.getBedDays(), " , услуга=", aRequest.getUslOmcCode(), " ]");
            aResult.setCalcPrice(multiply(aRequest.getBedDays(), tariff));
            aResult.setTariff(tariff);
            aResult.setCalcBedDays(new BigDecimal(aRequest.getBedDays()));
        } else {
            log.log("  Отделение. Вычисление цены по средним койко-дня: [ ср. койко-дни=", avarage, " ]");
            aResult.setCalcPrice(multiply(avarage, tariff));
            aResult.setTariff(tariff);
            aResult.setCalcBedDays(avarage);
        }
    }


    private static void setPriceByDays(CalcLog aLog, OmcPriceCalcResult aResult, int bedDays, BigDecimal aSkKolDney, BigDecimal aTariff) {
        if(bedDays < aSkKolDney.doubleValue()) {
            aLog.log("Количество дней меньше среднего [ кол. дней = ", bedDays, ", ср. к/дни = ", aSkKolDney, " ]");
            aLog.log("Расчет стоимости по формуле кол.дней * цену [ кол. дней = ", bedDays, ", тариф = ", aTariff, " ]");
            aResult.setCalcBedDays(new BigDecimal(bedDays));
            aResult.setCalcPrice(multiply(bedDays, aTariff));
        } else {
            aLog.log("Количество дней больше среднего среднего [ кол. дней = ", bedDays, ", ср. к/дни = ", aSkKolDney, " ]");
            aLog.log("Расчет стоимости по формуле среднее кол.дней * цену [ ср. кол. дней = ", aSkKolDney, ", цена = ", aTariff, " ]");
            aResult.setCalcBedDays(aSkKolDney);
            aResult.setCalcPrice(multiply(aSkKolDney, aTariff));
        }
        aResult.setTariff(aTariff);
    }


    private static boolean canUpLevel(CalcLog aLog, long aOsl) {
        boolean ret ;
        ret = aOsl==5 || aOsl==6 || aOsl==4 ;
        if(ret) {
            aLog.log("Уровень поднимается, т.к. есть осложенение типа "+aOsl);
        }
        return ret ;

    }

    private static BigDecimal multiply(int aKoyko, BigDecimal aPrice) {
        return aPrice.multiply(new BigDecimal(aKoyko));
    }

    private static BigDecimal multiply(BigDecimal aKoyko, BigDecimal aPrice) {
        return aPrice.multiply(aKoyko);
    }

    private static BigDecimal findTariffByUsl(CalcLog aLog, Date aActualDate, String usluga, boolean child, int level, boolean aCanUpLevel, IOmcPriceService aService) {
        BigDecimal price ;
        if(aCanUpLevel) {
            aLog.log("Поднимаем уровень");
            if(level==1) {
                aLog.log("Уровень уже 1. Поднимать не требуется") ;
                price = aService.findTariffByUsl(aActualDate, usluga, level, child) ;
            } else {
                int upperLevel  = level - 1 ;
                price = aService.findTariffByUsl(aActualDate, usluga, level, child) ;
                if(price==null) {
                    aLog.log("Нет цены для уровня "+upperLevel+", услуга "+usluga+". Цена будет браться по уровню  "+level) ;
                    price = aService.findTariffByUsl(aActualDate, usluga, level, child) ;
                }
            }
        } else {
            price = aService.findTariffByUsl(aActualDate, usluga, level, child) ;
        }
        if(price!=null && price.intValue()==0) price = null ; // цена нулевая
        return price ;
    }


    private IsChild theIsChild = new IsChild();
}
