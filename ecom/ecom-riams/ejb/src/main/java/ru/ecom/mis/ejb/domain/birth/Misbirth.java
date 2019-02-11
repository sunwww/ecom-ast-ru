package ru.ecom.mis.ejb.domain.birth;

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
@EntityListeners(DeleteListener.class)
public class Misbirth extends BaseEntity {
    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата редактирования */
    @Comment("Дата редактирования")
    public Date getEditDate() {return theEditDate;}
    public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
    /** Время создания */
    @Comment("Время создания")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
    /** Время редактрования */
    @Comment("Время редактирования")
    public Time getEditTime() {return theEditTime;}
    public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Срок беременности (нед) */
    @Comment("Срок беременности (нед)")
    public Long getDurationPregnancy() {return theDurationPregnancy;}
    public void setDurationPregnancy(Long aDurationPregnancy) {theDurationPregnancy = aDurationPregnancy;}
    /** Тип выкидыша */
    @Comment("Тип выкидыша")
    @OneToOne
    public VocTypeMisbirth getTypeMisbirth() {return theTypeMisbirth;}
    public void setTypeMisbirth(VocTypeMisbirth aTypeMisbirth) {theTypeMisbirth = aTypeMisbirth;}
    /** Дата выкидыша */
    @Comment("Дата выкидыша")
    public Date getMisbirthDate() {return theMisbirthDate;}
    public void setMisbirthDate(Date aMisbirthDate) {theMisbirthDate = aMisbirthDate;}
    /** Кол-во плодов */
    @Comment("Кол-во плодов")
    public Long getNumFetus() {return theNumFetus;}
    public void setNumFetus(Long aNumFetus) {theNumFetus = aNumFetus;}
    /** ЭКО? */
    @Comment("ЭКО?")
    public Boolean getIsECO() {return theIsECO;}
    public void setIsECO(Boolean aIsECO) {theIsECO = aIsECO;}
    /** Состояла на учёте в ЖК? */
    @Comment("Состояла на учёте в ЖК?")
    public Boolean getIsRegisteredWithWomenConsultation() {return theIsRegisteredWithWomenConsultation;}
    public void setIsRegisteredWithWomenConsultation(Boolean aIsRegisteredWithWomenConsultation) {theIsRegisteredWithWomenConsultation = aIsRegisteredWithWomenConsultation;}

    /** СМО */
    private MedCase theMedCase;
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;
    /** Пользователь, который создал запись */
    private String theCreateUsername;
    /** Время редактрования */
    private Time theEditTime;
    /** Время создания */
    private Time theCreateTime;
    /** Дата редактирования */
    private Date theEditDate;
    /** Дата создания */
    private Date theCreateDate;
    /** Срок беременности (нед) */
    private Long theDurationPregnancy;
    /** Тип выкидыша */
    private VocTypeMisbirth theTypeMisbirth;
    /** Дата выкидыша */
    private Date theMisbirthDate;
    /** Кол-во плодов */
    private Long theNumFetus;
    /** ЭКО? */
    private Boolean theIsECO;
    /** Состояла на учёте в ЖК? */
    private Boolean theIsRegisteredWithWomenConsultation;
}