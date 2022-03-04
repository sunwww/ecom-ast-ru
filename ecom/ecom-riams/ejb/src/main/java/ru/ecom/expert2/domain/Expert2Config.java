package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.entityform.annotation.UnDeletable;

import javax.persistence.Entity;

/**
 * Справочник настроек экспертизы
 */
@Entity
@UnDeletable
@Getter
@Setter
public class Expert2Config extends VocBaseEntity {
    public static final String NEED_IMPORT_PRICE_FROM_DEFECT = "NEED_IMPORT_PRICE_FROM_DEFECT"; //загружать цену при импорте дефектов
    public static final String EXCHANGE_COVID_DS = "EXCHANGE_COVID_DS"; //менять ли местами основной и сопут. ковид диагнозы
    public static final String LPU_REG_NUMBER = "LPU_REG_NUMBER"; //рег. номер текущей ЛПУ (F002)
    public static final String EXPORT_DISP_SERVICE_NO_DATE = "EXPORT_DISP_SERVICE_NO_DATE"; //выгружать услуги ДД без даты
    public static final String DONT_EXPORT_DEFECTS = "DONT_EXPORT_DEFECTS"; //не выгружать дефектные случаи (для теста в амокб)
    public static final String DISP_DIAGNOSIS_LIST = "DISP_DIAGNOSIS_LIST"; //Список диагнозов, по которым нужно формировать информацию о дисп. наблюдении

    /**
     * Значение параметра
     */
    private String value;

    /**
     * Удалено
     */
    private Boolean isDeleted;
}
