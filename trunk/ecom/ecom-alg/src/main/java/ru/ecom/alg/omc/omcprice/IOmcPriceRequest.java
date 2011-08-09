package ru.ecom.alg.omc.omcprice;

import java.util.Date;

/**
 * Запрос цены
 */
public interface IOmcPriceRequest {
    /** Использовать коэффициент 1.06 для экстренных госпитализаций */
    boolean canUseEmergencyKoef() ;

    /** Плановая госпитализация ?*/
    boolean isPlanovaya() ;

    /** Дата рождения */
    Date getBirthDate() ;

    /** Дата поступления */
    Date getAdmissionDate() ;

    /** Дата выписки */
    Date getDischargeDate() ;

    /** Код услуги ОМС */
    String getUslOmcCode() ;

    /** Дигноз по МКБ по справочнику фонда с расширением */
    String getMkbWithOmcExtension() ;

    /** Код осложнения по справочнику фонда */
    int getOsl() ;

    /** Код отделения по фонду */
    String getDepartmentCode() ;

    /** Уровень отделения, где лечился */
    int getDepartmentLevel() ;

    /** Количество койко-дней*/
    int getBedDays() ;

    /** Код ЛПУ по ОМС*/
    int getKodLpu() ;
}
