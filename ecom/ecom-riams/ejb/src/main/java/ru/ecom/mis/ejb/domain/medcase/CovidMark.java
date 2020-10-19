package ru.ecom.mis.ejb.domain.medcase;

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
public class CovidMark  extends BaseEntity {
    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    private MedCase theMedCase ;

    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    private Date theCreateDate ;

    /** Время создания */
    @Comment("Время создания")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
    private Time theCreateTime ;

    /** Создатель */
    @Comment("Создатель")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    private String theCreateUsername ;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    public Date getEditDate() {return theEditDate;}
    public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
    private Date theEditDate ;

    /** Время редактирования */
    @Comment("Время редактирования")
    public Time getEditTime() {return theEditTime;}
    public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
    /** Время редактирования */
    private Time theEditTime;

    /** Пользователь последний, изменявший запись */
    @Comment("Пользователь последний, изменявший запись")
    public String getEditUsername() {
        return theEditUsername;
    }
    public void setEditUsername(String aEditUsername) {
        theEditUsername = aEditUsername;
    }
    /** Пользователь последний, изменявший запись */
    private String theEditUsername;

    /** Изменение в лёгких в оценке ковида */
    @Comment("Изменение в лёгких в оценке ковида")
    @OneToOne
    public VocChangeLungs getChangeLungs() {return theChangeLungs;}
    public void setChangeLungs(VocChangeLungs aChangeLungs) {theChangeLungs = aChangeLungs;}
    private VocChangeLungs theChangeLungs ;

    /** Частота дыхательных движений в оценке ковида */
    @Comment("Частота дыхательных движений в оценке ковида")
    @OneToOne
    public VocChdd getChdd() {return theChdd;}
    public void setChdd(VocChdd aChdd) {theChdd = aChdd;}
    private VocChdd theChdd ;

    /** Пульсоксиметрия в оценке ковида */
    @Comment("Пульсоксиметрия в оценке ковида")
    @OneToOne
    public VocPuls getPuls() {return thePuls;}
    public void setPuls(VocPuls aPuls) {thePuls = aPuls;}
    private VocPuls thePuls ;

    /** Температура тела в оценке ковида */
    @Comment("Температура тела в оценке ковида")
    @OneToOne
    public VocTemp getTemp() {return theTemp;}
    public void setTemp(VocTemp aTemp) {theTemp = aTemp;}
    private VocTemp theTemp ;

    /** Нарушение сознания (3) */
    @Comment("Нарушение сознания (3)")
    public Boolean getIsBadSozn() {return theIsBadSozn;}
    public void setIsBadSozn(Boolean aIsBadSozn) {theIsBadSozn = aIsBadSozn;}
    private Boolean theIsBadSozn ;

    /** Тяжесть заболевания в оценке ковида */
    @Comment("Тяжесть заболевания в оценке ковида")
    @OneToOne
    public VocSost getSost() {return theSost;}
    public void setSost(VocSost aSost) {theSost = aSost;}
    private VocSost theSost ;
}