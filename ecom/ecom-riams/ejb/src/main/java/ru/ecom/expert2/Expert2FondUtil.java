package ru.ecom.expert2;

import ru.ecom.expert2.domain.E2CoefficientPatientDifficultyEntryLink;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.voc.E2Enumerator;
import ru.nuzmsh.util.StringUtil;

import java.util.List;

public class Expert2FondUtil {

    /** Расчитываем признак перевода для случая*/ //Реализовано
    public static String calculateFondP_PER(E2Entry aEntry) { //TODO реализовать по нормальному!!!
        String ret ; // по умолчанию - самостоятельно
        String lpuType =aEntry.getDirectLpuType();
        if (aEntry.getExternalPrevMedcaseId()!=null && aEntry.getExternalPrevMedcaseId()>0L) { //Переведен из другого отделения
            ret="4"; //Перевод внутри МО с другого профиля
        } else if ("К".equals(lpuType)) { //карета скорой помощи
            ret="2"; //СМП
        } else if ("С".equals(lpuType)) { //Переведен с другой МО
            ret = "3"; //Перевод в другой МО
        } else {
            ret="1";
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
        StringBuilder ret= new StringBuilder();
        if (!StringUtil.isNullOrEmpty(aEntry.getKinsmanLastname()) && aEntry.getMultiplyBirth()!=null && aEntry.getMultiplyBirth()) {
            ret.append("1;");
        }
        if (StringUtil.isNullOrEmpty(aEntry.getKinsmanMiddlename()) && StringUtil.isNullOrEmpty(aEntry.getMiddlename())) {
            ret.append("2;");
        }
        if (aEntry.getIsCriminalMessage()!=null && aEntry.getIsCriminalMessage()) {
            ret.append("9;");
        }
        if (aEntry.getMedicalAbort()!=null && aEntry.getMedicalAbort()) {
            ret.append("10;");
        }
        if (aEntry.getEntryType().equals(E2Enumerator.HOSPITALTYPE)||aEntry.getEntryType().equals(E2Enumerator.VMPTYPE)) { //Только для стац
            List<E2CoefficientPatientDifficultyEntryLink> list = aEntry.getPatientDifficulty();
            if (list!=null && !list.isEmpty()) {
                for (E2CoefficientPatientDifficultyEntryLink diff: list) {
                    if (diff.getDifficulty().getCode().equals("11")) {
                        ret.append("21;22;");
                        break;
                    }
                }
            }
        }
        //TODO сделать признак ДТП
        return ret.length()>0 ? ret.substring(0,ret.length()-1) : null; //формат 2020
    }
}