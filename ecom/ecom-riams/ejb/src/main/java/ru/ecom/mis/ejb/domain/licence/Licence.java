package ru.ecom.mis.ejb.domain.licence;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.licence.voc.VocStatus;
import ru.ecom.mis.ejb.domain.licence.voc.VocTypeWork;
import ru.ecom.mis.ejb.domain.licence.voc.VocUrMember;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Лицензия
 */
@Entity
@Comment("Лицензия")
@Table(schema="SQLUser")
public class Licence extends BaseEntity {

      /** Номер документа */
    public String getNumberDoc() { return theNumberDoc ; }
    public void setNumberDoc(String aNumberDoc) { theNumberDoc = aNumberDoc ; }
     
    /** Основание комисия*/
   @OneToOne
    public VocUrMember getOsnovanieCom() { return theOsnovanieCom ; }
    public void setOsnovanieCom(VocUrMember aOsnovanieCom) { theOsnovanieCom = aOsnovanieCom ; }

     /** Наименование комисия */
    @Transient
    public String getNameOsnovanieCom() { return theOsnovanieCom!=null ? theOsnovanieCom.getName() : "" ; }
    public void setNameOsnovanieCom(String aNameOsnovanieCom) {  }

    /** Кто выдал*/
    public String getVidal() { return theVidal ; }
    public void setVidal(String aVidal) { theVidal = aVidal ; }
//    @OneToOne
//    public VocUrMember getVidal() { return theVidal ; }
//    public void setVidal(VocUrMember aVidal) { theVidal = aVidal ; }
//
//    /** Наименование Кто выдал */
//    @Transient
//    public String getNameVidal() { return theVidal!=null ? theVidal.getName() : "" ; }
//    public void setNameVidal(String aNameVidal) {  }

     /** Кто получил юр. лицо*/
    @OneToOne
    public MisLpu getLpu() { return theLpu ; }
    public void setLpu(MisLpu aLpu) { theLpu = aLpu ; }

    /** Наименование Кто получил*/
    @Transient
    public String getNameLpu() { return theLpu!=null ? theLpu.getName() : "" ; }
    public void setNameLpu(String aNameLpu) {  }

    /** Дата выдачи*/
    public Date getDateVidal() { return theDateVidal ; }
    public void setDateVidal(Date aDateVidal) { theDateVidal = aDateVidal ; }

    /** Дата с*/
    public Date getDateStart() { return theDateStart ; }
    public void setDateStart(Date aDateStart) { theDateStart = aDateStart ; }

    /** Дата по*/
    public Date getDateFinish() { return theDateFinish ; }
    public void setDateFinish(Date aDateFinish) { theDateFinish = aDateFinish ; }

    /** Дата продления*/
    public Date getDateProlong() { return theDateProlong ; }
    public void setDateProlong(Date aDateProlong) { theDateProlong = aDateProlong ; }

    /** Дата приостановки*/
    public Date getDateStop() { return theDateStop ; }
    public void setDateStop(Date aDateStop) { theDateStop = aDateStop ; }

    /** Дата прекращения*/
    public Date getDateBreak() { return theDateBreak ; }
    public void setDateBreak(Date aDateBreak) { theDateBreak = aDateBreak ; }

     /** Приложения*/
    @OneToMany(mappedBy = "licence", cascade = ALL)
    public List<Prilogenie> getPrilogenie() { return thePrilogenie ; }
    public void setPrilogenie(List<Prilogenie> aPrilogenie) { thePrilogenie = aPrilogenie ; }

    /** Вид деятельности*/
    @OneToOne
    public VocTypeWork getTypeWork() { return theTypeWork ; }
    public void setTypeWork(VocTypeWork aTypeWork) { theTypeWork = aTypeWork ; }

    /** Наименование деятельности */
    @Transient
    public String getNameTypeWork() { return theTypeWork!=null ? theTypeWork.getName() : "" ; }
    public void setNameTypeWork(String aNameWork) {  }

     /**Статус*/
    @OneToOne
    public VocStatus getStatus() { return theStatus ; }
    public void setStatus(VocStatus aStatus) { theStatus = aStatus ; }

    /** Наименование статус */
    @Transient
    public String getNameStatus() { return theStatus!=null ? theStatus.getName() : "" ; }
    public void setNameStatus(String aNameStatus) {  }

//    /** Действует = 1 Не действует = 0 */
//    @Comment("Действует = 1 Не действует = 0")
//    public long getUse() { return theUse ; }
//    public void setUse(long aUse) { theUse = aUse ; }
//
//    /**  Статус*/
//    private long theUse ;

    /**Статус*/
    private VocStatus theStatus ;

    /**Вид деятельности*/
    private VocTypeWork theTypeWork ;

    /** Номер документа */
    private String theNumberDoc ;
    /** Основание */
    public Document theOsnovanieDoc ;
    /** Основание */
    public VocUrMember theOsnovanieCom;
    /** Дата выдачи */
    private Date theDateVidal ;
    /** Дата выдачи */
    private Date theDateStart ;
    /** Дата выдачи */
    private Date theDateFinish ;
    /** Дата продления*/
    private Date theDateProlong;
    /** Дата приостановки*/
    private Date theDateStop;
    /** Дата прекращения*/
    private Date theDateBreak;
    /** Кто выдал */
    private String theVidal ;
//    private VocUrMember theVidal ;
    /** Кто получил юр лицо */
    private MisLpu theLpu;
    /** Приложение */
    private List<Prilogenie> thePrilogenie;
//    private Prilogenie thePrilogenie;

}
