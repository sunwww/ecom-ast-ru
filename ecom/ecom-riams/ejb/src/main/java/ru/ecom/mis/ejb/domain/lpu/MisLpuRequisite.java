package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
/** Произвольные реквизиты для организации*/
public class MisLpuRequisite extends BaseEntity {

    /** ЛПУ */
    @Comment("ЛПУ")
    @OneToOne
    public MisLpu getLpu() {return lpu;}
    /** ЛПУ */
    private MisLpu lpu ;

    /** Название */
    private String name ;

    /** Код */
    private String code ;

    /** Значение */
    private String value ;
}
