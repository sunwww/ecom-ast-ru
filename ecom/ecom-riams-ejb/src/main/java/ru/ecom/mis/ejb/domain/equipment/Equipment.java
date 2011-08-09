package ru.ecom.mis.ejb.domain.equipment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.equipment.voc.VocCreater;
import ru.ecom.mis.ejb.domain.equipment.voc.VocMarka;
import ru.ecom.mis.ejb.domain.equipment.voc.VocProvider;
import ru.ecom.mis.ejb.domain.equipment.voc.VocTypeEquip;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * @author  azviagin
 */
@Entity
@Comment("Оборудование")
@Table(schema="SQLUser")
public class Equipment extends BaseEntity {

    /** Марка оборудования */
    @OneToOne
    public VocMarka getMarka() { return theMarka ; }
    public void setMarka(VocMarka aMarka) { theMarka = aMarka ; }

    /** Наименование марки оборудования */
    @Transient
    public String getNameMarka() { return theMarka!=null ? theMarka.getName() : "" ; }
    public void setNameMarka(String aNameMarka) {  }

    /** Тип оборудования */
    @OneToOne
    public VocTypeEquip getTypeEquip() { return theTypeEquip ; }
    public void setTypeEquip(VocTypeEquip aTypeEquip) { theTypeEquip = aTypeEquip ; }

    /** Наименование типа оборудования */
    @Transient
    public String getNameTypeEquip() { return theTypeEquip!=null ? theTypeEquip.getName() : "" ; }
    public void setNameTypeEquip(String aNameTypeEquip) {  }

    /** Год выпуска */
    public Integer getCreateYear() { return theCreateYear ; }
    public void setCreateYear(Integer aCreateYear) { theCreateYear = aCreateYear ; }

    /** Год установки */
    public Integer getStayYear() { return theStayYear ; }
    public void setStayYear(Integer aStayYear) { theStayYear = aStayYear ; }

    /** Производитель */
    @OneToOne
    public VocCreater getCreater() { return theCreater ; }
    public void setCreater(VocCreater aCreater) { theCreater = aCreater ; }

    /** Поставщик */
    @OneToOne
    public VocProvider getProvider() { return theProvider ; }
    public void setProvider(VocProvider aProvider) { theProvider = aProvider ; }

    /** Примечание */
    public String getInfo() { return theInfo ; }
    public void setInfo(String aInfo) { theInfo = aInfo ; }

    /** ЛПУ */
    @ManyToOne
    public MisLpu getLpu() { return theLpu ; }
    public void setLpu(MisLpu aLpu) { theLpu = aLpu ; }

    /** ЛПУ */
    private MisLpu theLpu;
    /** Примечание */
    private String theInfo;
    /** Поставщик */
    private VocProvider theProvider ;
    /** Производитель */
    private VocCreater theCreater ;
    /** Год установки */
    private Integer theStayYear;
    /** Год выпуска */
    private Integer theCreateYear;
    /** Тип оборудования */
    private VocTypeEquip theTypeEquip;
    /** Марка оборудования */
    private VocMarka theMarka;
 
}
