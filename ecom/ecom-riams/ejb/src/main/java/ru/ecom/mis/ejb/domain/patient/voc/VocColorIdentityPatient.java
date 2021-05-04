package ru.ecom.mis.ejb.domain.patient.voc;/**
 * Created by Milamesher on 30.04.2019.
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

@Entity
@Table(schema="SQLUser")
@Comment("Доп. информация о пациенте (для браслета)")
@NamedQueries({
        @NamedQuery( name="VocColorIdentityPatient.getByCode"
                , query="from VocColorIdentityPatient where code=:code ")
})
@Getter
@Setter
public class VocColorIdentityPatient extends VocBaseEntity {
    /** Может ли заполняться в родах?  */
    private Boolean isForNewborn ;

    /** Цвет */
    @OneToOne
    public VocColor getColor() { return color ; }
    private VocColor color;

    /** ЛПУ */
    @ManyToOne
    public MisLpu getLpu() { return lpu ; }
    private MisLpu lpu;

    /** Патология в лаборатории?  */
    private Boolean isForPatology ;

    /** Запрещено создавать вручную?  */
    private Boolean isDeniedManual ;
}