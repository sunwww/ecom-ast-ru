package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.birth.voc.VocRobsonClass;
import ru.ecom.mis.ejb.domain.birth.voc.VocSubRobson;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import java.sql.Time;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Milamesher on 10.12.2018.
 */
@Comment("Классификация Робсона")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = {
        @AIndex(properties = { "medCase" })
}
)
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class RobsonClass extends BaseEntity {
    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return medCase;}
    /** Классификация */
    @Comment("Классификация")
    @OneToOne
    public VocRobsonClass getRobsonType() {return robsonType;}
    @Comment("Подгруппа классификации")
    @OneToOne
    public VocSubRobson getRobsonSub() {return robsonSub;}

    /** СМО */
    private MedCase medCase;
    /** Классификация */
    private VocRobsonClass robsonType;
    /** Пользователь, который последний редактировал запись */
    private String editUsername;
    /** Пользователь, который создал запись */
    private String createUsername;
    /** Время редактрования */
    private Time editTime;
    /** Время создания */
    private Time createTime;
    /** Дата редактирования */
    private Date editDate;
    /** Дата создания */
    private Date createDate;
    /** Подгруппа классификации */
    private VocSubRobson robsonSub;
}