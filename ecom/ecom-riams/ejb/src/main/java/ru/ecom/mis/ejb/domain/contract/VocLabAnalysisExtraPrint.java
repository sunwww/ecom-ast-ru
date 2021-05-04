package ru.ecom.mis.ejb.domain.contract;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Milamesher
 * Список лабораторных анализов, которые нужно дополнительно печатать после договора
 */

@Comment("Список лабораторных анализов, которые нужно дополнительно печатать после договора")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocLabAnalysisExtraPrint extends BaseEntity {
    /**Код услуги */
    private String medService;
}