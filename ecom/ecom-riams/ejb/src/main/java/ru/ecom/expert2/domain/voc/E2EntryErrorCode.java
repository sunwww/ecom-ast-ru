package ru.ecom.expert2.domain.voc;

public class E2EntryErrorCode {
    /** Дата выписки не входит в период заполнения*/
    public static final String DISCHARGE_DATE_NOT_IN_PERIOD = "DISCHARGE_DATE_NOT_IN_PERIOD";
    public static final String NO_KSG = "NO_KSG";
    public static final String NO_COST = "NO_COST"; //Не расчитана цена случая
    public static final String NO_PASSPORT_INOG = "NOT_FULL_PASSPORT_INOG"; //Не заполнены данные по паспорту у иногороднего
    public static final String NOT_FULL_DISP = "NOT_FULL_PASSPORT_INOG"; //Не заполнены данные по паспорту у иногороднего

}
