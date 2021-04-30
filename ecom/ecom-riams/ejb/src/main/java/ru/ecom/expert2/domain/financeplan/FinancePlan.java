package ru.ecom.expert2.domain.financeplan;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class FinancePlan extends BaseEntity {

    /** Профиль медицинской помощи */
    @Comment("Профиль медицинской помощи")
    @OneToOne
    public VocE2MedHelpProfile getProfile() {return profile;}
    private VocE2MedHelpProfile profile;

    /** Отделение */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {return department;}
    private MisLpu department;

    /** Количество случаев */
    private Long count;

    /** Дата начала периода */
    private Date startDate;

    /** Дата окончания периода */
    private Date finishDate;

    /** Цена случая */
    @Comment("Цена случая")
    @Column( precision = 15, scale = 12 )
    public BigDecimal getCost() {return cost;}
    private BigDecimal cost;

    /** Вид случай */
    @Comment("Вид случай")
    @OneToOne
    public VocE2VidSluch getVidSluch() {return vidSluch;}
    private VocE2VidSluch vidSluch;

    /** Профиль коек V020 */
    @Comment("Профиль коек V020")
    @OneToOne
    public VocE2FondV020 getBedProfile() {return bedProfile;}
    private VocE2FondV020 bedProfile;


}
