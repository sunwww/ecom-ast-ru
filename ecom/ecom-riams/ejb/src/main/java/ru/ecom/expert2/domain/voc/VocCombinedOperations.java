package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
/** Сочетанные операции */
public class VocCombinedOperations extends BaseEntity {

    /** Операция 1 */
    @Comment("Операция 1")
    @OneToOne
    public VocMedService getMedService1() {return theMedService1;}
    public void setMedService1(VocMedService aMedService1) {theMedService1 = aMedService1;}
    /** Операция 1 */
    private VocMedService theMedService1 ;

    /** Операция 2 */
    @Comment("Операция 2")
    @OneToOne
    public VocMedService getMedService2() {return theMedService2;}
    public void setMedService2(VocMedService aMedService2) {theMedService2 = aMedService2;}
    /** Операция 2 */
    private VocMedService theMedService2 ;


    /** Дата начала действия */
    @Comment("Дата начала действия")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата начала действия */
    private Date theStartDate ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания действия */
    private Date theFinishDate ;
}
