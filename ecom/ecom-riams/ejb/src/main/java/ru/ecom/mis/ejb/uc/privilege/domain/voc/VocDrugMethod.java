package ru.ecom.mis.ejb.uc.privilege.domain.voc;

import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV035;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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
public class VocDrugMethod extends VocBaseEntity {

    private VocE2FondV035 federalMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    public VocE2FondV035 getFederalMethod() {
        return federalMethod;
    }
}
