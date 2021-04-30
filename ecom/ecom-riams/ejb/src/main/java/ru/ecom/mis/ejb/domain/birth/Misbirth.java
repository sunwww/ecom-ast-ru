package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.birth.voc.VocTypeMisbirth;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Milamesher on 21.12.2018.
 */
@Comment("Выкидыш")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = {
        @AIndex(properties = { "medCase" })
}
)
@Getter
@Setter
@EntityListeners(DeleteListener.class)
public class Misbirth extends BaseEntity {
    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return medCase;}
    /** Тип выкидыша */
    @Comment("Тип выкидыша")
    @OneToOne
    public VocTypeMisbirth getTypeMisbirth() {return typeMisbirth;}
    /** СМО */
    private MedCase medCase;
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
    /** Срок беременности (нед) */
    private Long durationPregnancy;
    /** Тип выкидыша */
    private VocTypeMisbirth typeMisbirth;
    /** Дата выкидыша */
    private Date misbirthDate;
    /** Кол-во плодов */
    private Long numFetus;
    /** ЭКО? */
    private Boolean isECO;
    /** Состояла на учёте в ЖК? */
    private Boolean isRegisteredWithWomenConsultation;
}