package ru.ecom.mis.ejb.domain.birth;

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
public class RobsonClass extends BaseEntity {
    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    /** Классификация */
    @Comment("Классификация")
    @OneToOne
    public VocRobsonClass getRobsonType() {return theRobsonType;}
    public void setRobsonType(VocRobsonClass aRobsonType) {theRobsonType = aRobsonType;}
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
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    @Comment("Подгруппа классификации")
    @OneToOne
    public VocSubRobson getRobsonSub() {return theRobsonSub;}
    public void setRobsonSub(VocSubRobson aRobsonSub) {theRobsonSub = aRobsonSub;}

    /** СМО */
    private MedCase theMedCase;
    /** Классификация */
    private VocRobsonClass theRobsonType;
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
    /** Подгруппа классификации */
    private VocSubRobson theRobsonSub;
}