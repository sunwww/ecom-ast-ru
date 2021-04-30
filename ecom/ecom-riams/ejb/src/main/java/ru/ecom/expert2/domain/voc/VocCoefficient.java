package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
public class VocCoefficient extends BaseEntity {
    
    /** Дата начала действия */
    private Date startDate;
    
    /** Дата окончания действия */
    private Date finishDate;

    /** Значение коэффициента */
    @Comment("Значение коэффициента")
    @Column( precision = 15, scale = 5)
    public BigDecimal getValue() {return value;}
    private BigDecimal value;

    /** Вид случая */
    @Comment("Вид случая")
    @OneToOne
    public VocE2VidSluch getVidSluch() {return vidSluch;}
    private VocE2VidSluch vidSluch;

    /** Профиль мед. помощи */
    @Comment("Профиль мед. помощи")
    @OneToOne
    public VocE2MedHelpProfile getProfile() {return profile;}
    private VocE2MedHelpProfile profile;

}
