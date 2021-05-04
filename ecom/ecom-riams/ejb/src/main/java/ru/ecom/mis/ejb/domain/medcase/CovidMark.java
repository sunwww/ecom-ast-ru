package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.covidMarcVocs.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


/**
 * Форма оценки тяжести заболевания при ковиде
 * @author milamesher
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class CovidMark  extends BaseEntity {
    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return medCase;}
    private MedCase medCase ;

    /** Дата создания */
    private Date createDate ;

    /** Время создания */
    private Time createTime ;

    /** Создатель */
    private String createUsername ;

    /** Дата редактирования */
    private Date editDate ;

    /** Время редактирования */
    private Time editTime;

    /** Пользователь последний, изменявший запись */
    private String editUsername;

    /** Изменение в лёгких в оценке ковида */
    @Comment("Изменение в лёгких в оценке ковида")
    @OneToOne
    public VocChangeLungs getChangeLungs() {return changeLungs;}
    private VocChangeLungs changeLungs ;

    /** Частота дыхательных движений в оценке ковида */
    @Comment("Частота дыхательных движений в оценке ковида")
    @OneToOne
    public VocChdd getChdd() {return chdd;}
    private VocChdd chdd ;

    /** Пульсоксиметрия в оценке ковида */
    @Comment("Пульсоксиметрия в оценке ковида")
    @OneToOne
    public VocPuls getPuls() {return puls;}
    private VocPuls puls ;

    /** Температура тела в оценке ковида */
    @Comment("Температура тела в оценке ковида")
    @OneToOne
    public VocTemp getTemp() {return temp;}
    private VocTemp temp ;

    /** Нарушение сознания (3) */
    private Boolean isBadSozn ;

    /** Тяжесть заболевания в оценке ковида */
    @Comment("Тяжесть заболевания в оценке ковида")
    @OneToOne
    public VocSost getSost() {return sost;}
    private VocSost sost ;
}