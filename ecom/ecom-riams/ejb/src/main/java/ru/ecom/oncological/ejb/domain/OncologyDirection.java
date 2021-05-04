package ru.ecom.oncological.ejb.domain;


import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyMethodDiagTreat;
import ru.ecom.oncological.ejb.domain.voc.VocOncologyTypeDirection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;

/**
 * Направление
 * @author rkurbanov
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class OncologyDirection extends BaseEntity {

    /** Случай окологического лечения */
    private OncologyCase oncologyCase;
    /** Метод диагностического лечения */
    private VocOncologyMethodDiagTreat methodDiagTreat;
    /** Вид направления */
    private VocOncologyTypeDirection typeDirection;
    /** мед услуга по V001 */
    private VocMedService medService;
    /** дата направления */
    private Date date;

    @Comment("Случай окологического лечения")
    @ManyToOne(fetch = FetchType.LAZY)
    public OncologyCase getOncologyCase() {
        return oncologyCase;
    }

    @Comment("Метод диагностического лечения")
    @OneToOne
    public VocOncologyMethodDiagTreat getMethodDiagTreat() {
        return methodDiagTreat;
    }

    @Comment("Вид направления")
    @OneToOne
    public VocOncologyTypeDirection getTypeDirection() {
        return typeDirection;
    }

    @Comment("мед услуга по V001")
    @OneToOne
    public VocMedService getMedService() {
        return medService;
    }

    /** ЛПУ, куда сделано направление */
    @Comment("ЛПУ, куда сделано направление")
    @OneToOne
    public MisLpu getDirectLpu() {return directLpu;}
    /** ЛПУ, куда сделано направление */
    private MisLpu directLpu ;
}
