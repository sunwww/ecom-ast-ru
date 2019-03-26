package ru.ecom.expert2.domain.financeplan;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.VocE2VidSluch;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV020;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

/** Финансовый план*/
@Entity
public class FinancePlan extends BaseEntity {

    /** Профиль медицинской помощи */
    @Comment("Профиль медицинской помощи")
    @OneToOne
    public VocE2MedHelpProfile getProfile() {return theProfile;}
    public void setProfile(VocE2MedHelpProfile aProfile) {theProfile = aProfile;}
    /** Профиль медицинской помощи */
    private VocE2MedHelpProfile theProfile ;

    /** Отделение */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {return theDepartment;}
    public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}
    /** Отделение */
    private MisLpu theDepartment ;

    /** Количество случаев */
    @Comment("Количество случаев")
    public Long getCount() {return theCount;}
    public void setCount(Long aCount) {theCount = aCount;}
    /** Количество случаев */
    private Long theCount ;

    /** Дата начала периода */
    @Comment("Дата начала периода")
    public Date getStartDate() {return theStartDate;}
    public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}
    /** Дата начала периода */
    private Date theStartDate ;

    /** Дата окончания периода */
    @Comment("Дата окончания периода")
    public Date getFinishDate() {return theFinishDate;}
    public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания периода */
    private Date theFinishDate ;

    /** Цена случая */
    @Comment("Цена случая")
    @Column( precision = 15, scale = 12 )
    public BigDecimal getCost() {return theCost;}
    public void setCost(BigDecimal aCost) {theCost = aCost;}
    /** Цена случая */
    private BigDecimal theCost ;

    /** Вид случай */
    @Comment("Вид случай")
    @OneToOne
    public VocE2VidSluch getVidSluch() {return theVidSluch;}
    public void setVidSluch(VocE2VidSluch aVidSluch) {theVidSluch = aVidSluch;}
    /** Вид случай */
    private VocE2VidSluch theVidSluch ;

    /** Профиль коек V020 */
    @Comment("Профиль коек V020")
    @OneToOne
    public VocE2FondV020 getBedProfile() {return theBedProfile;}
    public void setBedProfile(VocE2FondV020 aBedProfile) {theBedProfile = aBedProfile;}
    /** Профиль коек V020 */
    private VocE2FondV020 theBedProfile ;


}
