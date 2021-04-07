package ru.ecom.expert2;

import ru.ecom.expert2.domain.E2CoefficientPatientDifficultyEntryLink;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.voc.E2Enumerator;

import java.util.List;

import static ru.nuzmsh.util.BooleanUtils.isTrue;
import static ru.nuzmsh.util.CollectionUtil.isNotEmpty;
import static ru.nuzmsh.util.EqualsUtil.isOneOf;
import static ru.nuzmsh.util.StringUtil.isNotEmpty;
import static ru.nuzmsh.util.StringUtil.isNullOrEmpty;

public class Expert2FondUtil {

    /**
     * Расчитываем признак перевода для случая
     */
    public static String calculateFondP_PER(E2Entry entry) {
        String ret; // по умолчанию - самостоятельно
        String lpuType = entry.getDirectLpuType();
        if (entry.havePrevMedCase()) { //Переведен из другого отделения
            ret = "4"; //Перевод внутри МО с другого профиля
        } else if ("К".equals(lpuType)) { //карета скорой помощи
            ret = "2"; //СМП
        } else if ("С".equals(lpuType)) { //Переведен с другой МО
            ret = "3"; //Перевод в другой МО
        } else {
            ret = "1";
        }
        return ret;
    }

    /**
     * Считаем признак "особые случай "
     */
    public static String calculateFondOsSluch(E2Entry entry) {
        /*
         * Указываются все имевшиеся особые случаи.
         1 – медицинская помощь оказана новорожденному ребенку до государственной регистрации рождения при многоплодных родах;
         2 – в документе, удостоверяющем личность пациента /родителя (представителя) пациента, отсутствует отчество.
         9 – противоправные действия
         10 - аборт по мед. показаниям
         21(22) - парная операция на правом(левом) органе,
         23 - внутрибольничное инфицирование

         */
        StringBuilder ret = new StringBuilder();
        if (isTrue(entry.getMultiplyBirth()) && isNotEmpty(entry.getKinsmanLastname())) {
            ret.append("1;");
        }
        if (isNullOrEmpty(entry.getKinsmanMiddlename()) && isNullOrEmpty(entry.getMiddlename())) {
            ret.append("2;");
        }
        if (isTrue(entry.getIsCriminalMessage())) {
            ret.append("9;");
        }
        if (isTrue(entry.getMedicalAbort())) {
            ret.append("10;");
        }
        if (isOneOf(entry.getEntryType(), E2Enumerator.HOSPITALTYPE, E2Enumerator.VMPTYPE)) { //Только для стац
            List<E2CoefficientPatientDifficultyEntryLink> list = entry.getPatientDifficulty();
            if (isNotEmpty(list)) {
                for (E2CoefficientPatientDifficultyEntryLink diff : list) {
                    if (diff.getDifficulty().getCode().equals("11")) {
                        ret.append("21;22;");
                        break;
                    }
                }
            }
        }
        //TODO сделать признак ДТП
        return ret.length() > 0 ? ret.substring(0, ret.length() - 1) : null;
    }
}