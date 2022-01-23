package ru.ecom.mis.ejb.domain.prescription.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Справочник единиц измерения количества лекарства
 *
 * @author azviagin
 */

@Comment("Справочник единиц измерения количества лекарства")
@Entity
@Table(schema = "SQLUser")
@Setter
@Getter
public class VocDrugAmountUnit extends VocBaseEntity {
    /**
     * Код по справочнику ТФОМС V034
     */
    private String federalCode;

}
