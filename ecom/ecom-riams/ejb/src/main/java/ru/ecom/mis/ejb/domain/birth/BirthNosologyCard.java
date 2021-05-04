package ru.ecom.mis.ejb.domain.birth;/**
 * Created by Milamesher on 23.12.2019.
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.birth.voc.VocBirthNosology;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Comment("Карта нозологий в акушерстве")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = {
        @AIndex(properties = { "medCase" })
}
)
@Getter
@Setter
public class BirthNosologyCard extends BaseEntity {
    /** Дата создания */
    private Date createDate;

    /** Время создания */
    private Time createTime;

    /** Пользователь, который создал запись */
    private String createUsername;

    /** Дата редактирования */
    private Date editDate;

    /** Время редактрования */
    private Time editTime;

    /** Пользователь, который последний редактировал запись */
    private String editUsername;

    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return medCase;}
    /** СМО */
    private MedCase medCase;

    /** Пользователь */
    @Comment("Пользователь")
    @OneToOne
    public WorkFunction getCreator() {return creator;}
    /** Пользователь */
    private WorkFunction creator;

    /** Отмеченные нозологии */
    private List<VocBirthNosology> nosologies;

    /** Отмеченные нозологии */
    @Comment("Отмеченные нозологии")
    @ManyToMany
    public List<VocBirthNosology> getNosologies() {return nosologies;}

    /** Пользователь, который последний отредактировал */
    @Comment("Пользователь, который последний отредактировал")
    @OneToOne
    public WorkFunction getEditor() {return editor;}
    private WorkFunction editor;
}