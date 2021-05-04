package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Коэффицинт уровня оказания мед. помощи
 */
@Entity
@Getter
@Setter
public class VocCoefficientLpuLevel extends VocCoefficient {
    
    /** Идентификатор отделения */
    @Comment("Идентификатор отделения")
    @OneToOne
    public MisLpu getDepartment() {return department;}
    private MisLpu department;
}
