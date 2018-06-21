package ru.ecom.expert2;

import org.apache.log4j.Logger;
import ru.ecom.expert2.domain.E2CoefficientPatientDifficultyEntryLink;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.voc.E2Enumerator;
import ru.nuzmsh.util.date.AgeUtil;

import java.util.List;

public class Expert2FondUtil {

    private static final Logger log = Logger.getLogger(Expert2FondUtil.class);
    public static boolean isNull(String aValue) {
        return aValue==null||aValue.trim().equals("");
    }

    public static String getUslOkByStacType(String sStacType) {
        if (sStacType.toUpperCase().equals("ALLTIMEHOSP")) {
            return "1";
        } else if (sStacType.toUpperCase().equals("DAYTIMEHOSP")) {
            return "2";
        } else if (sStacType.toUpperCase().equals("POLIC")) {
            return "3";
        } else {
            throw new IllegalStateException("BAD StacType = "+sStacType);
        }
    }
    public static String addSql(String aField, String aValue) {
        return  " ("+aField+" is null or "+aField+"="+(isNull(aValue)?"''":"'"+aValue+"'")+")";
    }
    /** Расчитываем признак перевода для случая*/ //Реализовано
    public static String calculateFondP_PER(E2Entry aEntry) { //TODO реализовать по нормальному!!!
        String ret = "1"; // по умолчанию - самостоятельно
        String lpuType =aEntry.getDirectLpuType();
        if (aEntry.getExternalPrevMedcaseId()!=null&&aEntry.getExternalPrevMedcaseId()>0L) { //Переведен из другого отделения
         //   asdasdasdas
            ret="4"; //Перевод внутри МО с другого профиля
        } else if (lpuType!=null&&lpuType.equals("К")) { //карета скорой помощи
            ret="2"; //СМП
        } else if (lpuType!=null&&lpuType.equals("С")) { //Переведен с другой МО
            ret = "3"; //Перевод в другой МО
        }
        return ret;
    }

    /**
     * Считаем признак "особые случай " */ //Реализовано
    public static String calculateFondOsSluch(E2Entry aEntry) {
        /**
         * Указываются все имевшиеся особые случаи.
         1 – медицинская помощь оказана новорожденному ребенку до государственной регистрации рождения при многоплодных родах;
         2 – в документе, удостоверяющем личность пациента /родителя (представителя) пациента, отсутствует отчество.
         9 – противоправные действия
         10 - аборт по мед. показаниям
         21(22) - парная операция на правом(левом) органе,
         23 - внутрибольничное инфицирование

         */
        String ret= "";
        if (!isNull(aEntry.getKinsmanLastname())&&aEntry.getMultiplyBirth()!=null&&aEntry.getMultiplyBirth()) {
            ret+="1";
        }
        if (isNull(aEntry.getKinsmanMiddlename())&&isNull(aEntry.getMiddlename())) {
            ret+=ret.length()>0?";2":"2";
        }
        if (aEntry.getIsCriminalMessage()!=null&&aEntry.getIsCriminalMessage()) {
            ret+=ret.length()>0?";9":"9";
        }
        if (aEntry.getMedicalAbort()!=null&&aEntry.getMedicalAbort()) {
            ret+=ret.length()>0?";10":"10";
        }
        if (aEntry.getEntryType().equals(E2Enumerator.HOSPITALTYPE)||aEntry.getEntryType().equals(E2Enumerator.VMPTYPE)) { //Только для стац
            List<E2CoefficientPatientDifficultyEntryLink> list = aEntry.getPatientDifficulty();
            for (E2CoefficientPatientDifficultyEntryLink diff: list) {
                if (diff.getDifficulty().getCode().equals("11")) {
                    ret+=ret.length()>0?";21;22":"21;22";
                    break;
                }
            }
        }
        //TODO сделать признак ДТП
        //if (ret!=null) log.info("calc OSLUCH. ID = "+aEntry.getId()+", sluch="+ret);
        return ret.length()>0?ret:"0";

    }


    /** Способ госпитализации */ //Реализовано
    public static String calculateFondIDGOSP(E2Entry aEntry) {
        /**
         * Обязательно для заполнения при круглосуточном стационаре(в том числе ВМП).
         1 – Самообращение
         2 – Направление лечащего врача
         3 – Бригада скорой помощи
         4 – Бригада неотложной помощи --нах

         */
        if (aEntry.getIsEmergency()==null||!aEntry.getIsEmergency()) { //Если планово - по но направлению врача
            return "2";
        } else if (aEntry.getDirectLpuType()!=null&&aEntry.getDirectLpuType().equals("К")) { //Карета скорой мед. помощи
            return "3";
        }  else return "1"; //Если экстренно и не карета скорой помощи - пришел сам



    }

}
