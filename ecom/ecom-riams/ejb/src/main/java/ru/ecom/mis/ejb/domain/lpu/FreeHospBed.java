package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity(name = "FreeHospBed")
@Table
public class FreeHospBed  extends BaseEntity {
    /** ЛПУ */
    @Comment("ЛПУ")
    public Long getLpu() { return theLpu ; }
    public void setLpu(Long aLpu) { theLpu = aLpu ; }
    
    private Long theLpu;

    /** Мужские, с кислородом */
    @Comment("Мужские, с кислородом")
    public Long getMenO2() { return theMenO2 ; }
    public void setMenO2(Long aMenO2) { theMenO2 = aMenO2 ; }

    private Long theMenO2;
    
    /** Мужские, без кислорода */
    @Comment("Мужские, без кислорода")
    public Long getMenNoO2() { return theMenNoO2 ; }
    public void setMenNoO2(Long aMenNoO2) { theMenNoO2 = aMenNoO2 ; }

    private Long theMenNoO2;

    /** Женские, с кислородом */
    @Comment("Женские, с кислородом")
    public Long getWomenO2() { return theWomenO2 ; }
    public void setWomenO2(Long aWomenO2) { theWomenO2 = aWomenO2 ; }

    private Long theWomenO2;

    /** Женские, без кислорода */
    @Comment("Женские, без кислорода")
    public Long getWomenNoO2() { return theWomenNoO2 ; }
    public void setWomenNoO2(Long aWomenNoO2) { theWomenNoO2 = aWomenNoO2 ; }

    private Long theWomenNoO2;

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
}