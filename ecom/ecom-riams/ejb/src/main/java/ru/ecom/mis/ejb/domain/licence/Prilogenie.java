package ru.ecom.mis.ejb.domain.licence;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.licence.voc.VocTypeWork;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Приложения_к_лицензиям
 */
@Entity
@Comment("Приложения к лицензии")
@Table(schema="SQLUser")
public class Prilogenie extends BaseEntity {

    /** Номер документа */
    public String getNumberDoc() { return theNumberDoc ; }
    public void setNumberDoc(String aNumberDoc) { theNumberDoc = aNumberDoc ; }

    /** Связь с подразделениями*/
//    @OneToMany(mappedBy = "prilogenie", cascade = ALL)
//    public List<MisLpu> getLpu() { return theLpu ; }
//    public void setLpu(List<MisLpu> aLpu) { theLpu = aLpu ; }
    @OneToOne
    public MisLpu getLpu() { return theLpu ; }
    public void setLpu(MisLpu aLpu) { theLpu = aLpu ; }

    /** Связь с подразделениями*/
    @Transient
    public String getNameLpu() { return theLpu!=null ? theLpu.getName() : "" ; }
    public void setNameLpu(String aNameLpu) {  }

    /** Связь с лицензией*/
    @ManyToOne
    public Licence getLicence() { return theLicence ; }
    public void setLicence(Licence aLicence) { theLicence = aLicence ; }

    /** Вид деятельности*/
    @ManyToMany
    public List<VocTypeWork> getTypeWorks() { return theTypeWorks ; }
    public void setTypeWorks(List<VocTypeWork> aTypeWork) { theTypeWorks = aTypeWork ; }

//    /** Вид деятельности*/
//    @OneToOne
//    public VocTypeWork getTypeWork() { return theTypeWork ; }
//    public void setTypeWork(VocTypeWork aTypeWork) { theTypeWork = aTypeWork ; }

    /** Наименование деятельности */
    @Transient
    public String getNameTypeWork() {
    	StringBuilder sb = new StringBuilder() ;
    	if(theTypeWorks!=null) {
    		for(VocTypeWork t: theTypeWorks) {
    			if(t.getName()!=null) sb.append(t.getName()) ;
    			sb.append(", ") ;
    		}
    	}
    	return sb.toString() ;
    }
    
    public void setNameTypeWork(String aNameWork) {  }

    /**Вид деятельности*/
    private List<VocTypeWork> theTypeWorks ;
//
//    /**Вид деятельности*/
//    private VocTypeWork theTypeWork ;

    /**Приложения*/
    private MisLpu theLpu;

    /** Номер документа */
    private String theNumberDoc ;

    /** Лицензия */
    private Licence theLicence;


}
