package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
/** Произвольные реквизиты для организации*/
public class MisLpuRequisite extends BaseEntity {

    /** ЛПУ */
    @Comment("ЛПУ")
    @OneToOne
    public MisLpu getLpu() {return theLpu;}
    public void setLpu(MisLpu aLpu) {theLpu = aLpu;}
    /** ЛПУ */
    private MisLpu theLpu ;

    /** Название */
    @Comment("Название")
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Название */
    private String theName ;

    /** Код */
    @Comment("Код")
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код */
    private String theCode ;

    /** Значение */
    @Comment("Значение")
    public String getValue() {return theValue;}
    public void setValue(String aValue) {theValue = aValue;}
    /** Значение */
    private String theValue ;
}
