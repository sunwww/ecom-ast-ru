package ru.ecom.alg.omc.omcprice;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Получение необходимых данных для расчета цены
 */
public interface IOmcPriceService {

    /**
     * Поиск цены
     * @param aActualDate дата выписки для стационара, дата оказазания услуги для поликлиники
     * @param aUslOmcCode код услуги (ОМС)
     * @param aLevel      клинический уровень
     * @param aIsChild    ребенок?
     * @return цена
     */
    BigDecimal findTariffByUsl(Date aActualDate, String aUslOmcCode, int aLevel, boolean aIsChild);

    /**
     * Поиск среднего количества дней для дневного стационара
     * @param aDischargeDate    дата выписки для стационара
     * @param aDepartmentCode   код отделения (ОМС)
     * @param aDepartmentLevel  уровень отделения
     * @param aIsChild          ребенок?
     * @return ср. кол-во дней, null - если не найдено
     */
    BigDecimal findAverageDaysByDailyHospital(Date aDischargeDate, String aDepartmentCode, int aDepartmentLevel, boolean aIsChild);

    /**
     * Поиск среднего количества дней для круглосуточного стационара
     * @param aDischargeDate   дата выписки
     * @param aDepartmentCode  код отделения (ОМС)
     * @param aIsChild         ребенок?
     * @return  ср. кол-во дней, null - не найдено
     */
    BigDecimal findAverageDaysByHospital(Date aDischargeDate, String aDepartmentCode, boolean aIsChild) ;

    /**
     * Поиск уровня по КСГ
     * @param aDischargeDate дата выписки
     * @param aMkb           код МКБ по фонду
     * @param aIsChild       ребенок?
     * @return уровень , null - не найдено
     */
    Integer findKsgLevel(Date aDischargeDate, String aMkb, boolean aIsChild);

    /**
     * Поиск ср. кол-во койко-дней по КСГ
     * @param aDischargeDate дата выписки
     * @param aMkb           код МКБ по фонду
     * @param aIsChild       ребенок?
     * @return ср. кол-во койко-дней, null - не найдено
     */
    BigDecimal findKsgAverageDays(Date aDischargeDate, String aMkb, boolean aIsChild);
//  zav
    /**
     * Поиск уровня отделения ЛПУ
     * @param aDischargeDate дата выписки
     * @param aOmcLpu 		ЛПУ
     * @param aOmcDepType	уровень отделения  
     * @return уровень отделения, null - не найдено
     */
    Integer findDepartmentLevel(Date aDischargeDate, int aKodLpu, String aOmcDepType);
//  zav
	}
	


