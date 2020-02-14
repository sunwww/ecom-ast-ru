package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class VocCoefficient extends BaseEntity {
    
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

    /** Значение коэффициента */
    @Comment("Значение коэффициента")
    @Column( precision = 15, scale = 5)
    public BigDecimal getValue() {return theValue;}
    public void setValue(BigDecimal aValue) {theValue = aValue;}
    /** Значение коэффициента */
    private BigDecimal theValue ;

    /** Вид случая */
    @Comment("Вид случая")
    @OneToOne
    public VocE2VidSluch getVidSluch() {return theVidSluch;}
    public void setVidSluch(VocE2VidSluch aVidSluch) {theVidSluch = aVidSluch;}
    /** Вид случая */
    private VocE2VidSluch theVidSluch ;

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @OneToOne
    public VocE2MedHelpProfile getProfile() {return theProfile;}
    public void setProfile(VocE2MedHelpProfile aProfile) {theProfile = aProfile;}
    /** Медицинская специальность */
    private VocE2MedHelpProfile theProfile ;

}
