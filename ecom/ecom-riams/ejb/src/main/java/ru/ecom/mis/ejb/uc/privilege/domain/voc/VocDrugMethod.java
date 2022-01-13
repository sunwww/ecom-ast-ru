package ru.ecom.mis.ejb.uc.privilege.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Метод введения лекарства
 *
 * @author azviagin
 */

@Comment("Метод введения лекарства")
@Entity
@Table(schema = "SQLUser")
@Setter
@Getter
public class VocDrugMethod extends VocBaseEntity {

    /**
     * Код по справочнику ТФОМС V035
     */
    private String federalCode;

}
