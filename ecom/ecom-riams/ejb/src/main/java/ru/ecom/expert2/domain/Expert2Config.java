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

    /**
     * Значение параметра
     */
    private String value;

    /**
     * Удалено
     */
    private Boolean isDeleted;
}
