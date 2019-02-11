package ru.ecom.expert2.domain.voc.federal;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.MappedSuperclass;
import java.sql.Date;

@MappedSuperclass
public class VocBaseFederal extends VocBaseEntity{

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

   /** Неактуальная запись */
   @Comment("Неактуальная запись")
   public Boolean getIsNoActual() {return theIsNoActual;}
   public void setIsNoActual(Boolean aIsNoActual) {theIsNoActual = aIsNoActual;}
   /** Неактуальная запись */
   private Boolean theIsNoActual =false;
}
