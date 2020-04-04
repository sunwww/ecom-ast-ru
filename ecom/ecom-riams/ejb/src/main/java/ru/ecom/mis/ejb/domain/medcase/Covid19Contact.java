package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class Covid19Contact extends BaseEntity {

    /** Карта коронавируса 19 */
    @Comment("Карта коронавируса 19")
    @ManyToOne
    public Covid19 getCard() {return theCard;}
    public void setCard(Covid19 aCard) {theCard = aCard;}
    private Covid19 theCard ;

    /** Фамилия */
    @Comment("Фамилия")
    public String getLastname() {return theLastname;}
    public void setLastname(String aLastname) {theLastname = aLastname;}
    private String theLastname ;

    /** Имя */
    @Comment("Имя")
    public String getFirstname() {return theFirstname;}
    public void setFirstname(String aFirstname) {theFirstname = aFirstname;}
    private String theFirstname ;

    /** Отчество */
    @Comment("Отчество")
    public String getMiddlename() {return theMiddlename;}
    public void setMiddlename(String aMiddlename) {theMiddlename = aMiddlename;}
    private String theMiddlename ;

    /** Дата рождения */
    @Comment("Дата рождения")
    public Date getBirthDate() {return theBirthDate;}
    public void setBirthDate(Date aBirthDate) {theBirthDate = aBirthDate;}
    private Date theBirthDate ;

    /** Телефон */
    @Comment("Телефон")
    public String getPhone() {return thePhone;}
    public void setPhone(String aPhone) {thePhone = aPhone;}
    private String thePhone ;

    /** Адресс */
    @Comment("Адресс")
    public String getAddress() {return theAddress;}
    public void setAddress(String aAddress) {theAddress = aAddress;}
    private String theAddress ;

}