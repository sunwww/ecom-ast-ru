package ru.ecom.expert2.domain.voc;

public class E2EntryErrorCode {
    /**
     * Дата выписки не входит в период заполнения
     */
    public static final String DISCHARGE_DATE_NOT_IN_PERIOD = "DISCHARGE_DATE_NOT_IN_PERIOD";
    public static final String NO_KSG = "NO_KSG"; //Не найден КСГ
    public static final String MAYBE_OTHER_KSG = "MAYBE_OTHER_KSG"; // Подходят другие варианты КСГ
    public static final String NO_COST = "NO_COST"; //Не расчитана цена случая
    public static final String NO_PASSPORT_INOG = "NOT_FULL_PASSPORT_INOG"; //Не заполнены данные по паспорту у иногороднего
    public static final String NOT_FULL_DISP = "NOT_FULL_DISP"; //Не заполнены данные по паспорту у иногороднего
    public static final String CROSS_SPO = "CROSS_SPO"; //Пересекающиеся СПО
    public static final String NO_DIAGNOSIS = "NO_DIAGNOSIS"; //Не найден основной диагноз
    public static final String NO_PROFILE = "NO_PROFILE"; //Не найден профиль оказания мед. помощи
    public static final String DOUBLE_WITH_PREVIOUS = "DOUBLE_WITH_PREVIOUS"; //Дубль с другим заполнением
    public static final String COVID_NO_CARD = "COVID_NO_CARD"; //Не заполнена карта ковидного больного
    public static final String DIAGNOSIS_WITHOUT_UTOCHNENIE = "DIAGNOSIS_WITHOUT_UTOCHNENIE"; //Не заполнена карта ковидного больного
    public static final String NO_MED_POLICY = "NO_MED_POLICY"; //Отсутствует медицинский полис
    public static final String NO_VMP_METHOD_COST = "NO_VMP_METHOD_COST"; //Отсутствует цена метода ВМП
    public static final String NO_DISP_PRICE_SERVICES_ADMIN_SKIP = "NO_DISP_PRICE_SERVICES_ADMIN_SKIP"; //Отсутствует цена ДД
    public static final String MALO_DISP_SERVICE = "MALO_DISP_SERVICE"; //Недостаточный объем услуг по ДД
    public static final String NO_RESULT = "NO_RESULT"; //Не найден результат случая
    public static final String NO_ENTRY_SUBTYPE = "NO_ENTRY_SUBTYPE"; //Не расчитан подтип случая
    public static final String DISP_EXCEPTION = "DISP_EXCEPTION"; //Ошибка создания случая ДД
    public static final String LONG_CHLX = "LONG_CHLX"; //Обращение у врача ЧЛХ
    public static final String RAZNYE_POLIC_PROFILE = "RAZNYE_POLIC_PROFILE"; //Различные профиля мед. помощи в одном обращении
    public static final String BAD_SERVICE_CODE = "BAD_SERVICE_CODE"; //Неизвестный код услуги
    public static final String ERROR_SERVICE_CREATION = "ERROR_SERVICE_CREATION"; //Ошибка при формировании услуги

}
