package ru.ecom.mis.ejb.domain.birth;/**
 * Created by Milamesher on 23.12.2019.
 */

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
public class BirthNosologyCard extends BaseEntity {
    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}/** Дата создания */
    private Date theCreateDate;

    /** Время создания */
    @Comment("Время создания")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
    /** Время создания */
    private Time theCreateTime;

    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который создал запись */
    private String theCreateUsername;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    public Date getEditDate() {return theEditDate;}
    public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования */
    private Date theEditDate;

    /** Время редактрования */
    @Comment("Время редактрования")
    public Time getEditTime() {return theEditTime;}
    public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
    /** Время редактрования */
    private Time theEditTime;

    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;

    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    /** СМО */
    private MedCase theMedCase;

    /** Пользователь */
    @Comment("Пользователь")
    @OneToOne
    public WorkFunction getCreator() {return theCreator;}
    public void setCreator(WorkFunction aCreator) {theCreator = aCreator;}
    /** Пользователь */
    private WorkFunction theCreator;

    /** Отмеченные нозологии */
    private List<VocBirthNosology> theNosologies;

    /** Отмеченные нозологии */
    @Comment("Отмеченные нозологии")
    @ManyToMany
    public List<VocBirthNosology> getNosologies() {return theNosologies;}
    public void setNosologies(List<VocBirthNosology> aNosologies) {theNosologies = aNosologies;}

    /** Пользователь, который последний отредактировал */
    @Comment("Пользователь, который последний отредактировал")
    @OneToOne
    public WorkFunction getEditor() {return theEditor;}
    public void setEditor(WorkFunction aEditor) {theEditor = aEditor;}
    /** Пользователь, который последний отредактировал */
    private WorkFunction theEditor;
}