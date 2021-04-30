package ru.ecom.mis.ejb.domain.birth;/**
 * Created by Milamesher on 23.01.2019.
 */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.birth.voc.*;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = {
        @AIndex(properties = { "medCase" })
}
)
@Getter
@Setter
public class ScreeningCardiac extends BaseEntity {
    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return medCase;}
    /** Кожные покровы */
    @Comment("Кожные покровы")
    @OneToOne
    public VocScreeningSkin getSkin() {return skin;}
    /** Пульсация артерий правой руки */
    @Comment("Пульсация артерий правой руки")
    @OneToOne
    public VocScreeningArterialPulsation getRightHandAP() {return rightHandAP;}
    /** Пульсация артерий ноги */
    @Comment("Пульсация артерий ноги")
    @OneToOne
    public VocScreeningArterialPulsation getLegAP() {return legAP;}
    /** Характеристика ЦНС */
    @Comment("Характеристика ЦНС")
    @OneToOne
    public VocScreeningCNS getCns() {return cns;}
    /** Характеристика СД */
    @Comment("Характеристика СД")
    @OneToOne
    public VocScreeningCardiacActivity getCardiacActivity() {return cardiacActivity;}
    /** СМО */
    private MedCase medCase;
    /** Пользователь, который последний редактировал запись */
    private String editUsername;
    /** Пользователь, который создал запись */
    private String createUsername;
    /** Время редактрования */
    private Time editTime;
    /** Время создания */
    private Time createTime;
    /** Дата редактирования */
    private Date editDate;
    /** Дата создания */
    private Date createDate;
    /** Кожные покровы */
    private VocScreeningSkin skin;
    /** Пульсация артерий правой руки */
    private VocScreeningArterialPulsation rightHandAP;
    /** Пульсация артерий ноги */
    private VocScreeningArterialPulsation legAP;
    /** Пульсоксиметрия на конечностях одновременная - правая рука*/
    private Long rightHandPulse;
    /** Пульсоксиметрия на конечностях одновременная - нога*/
    private Long legPulse;
    /** Частота дыхания в мин. */
    private Long breathingRate;
    /** Втяжение межрёберных промежутков */
    private Boolean retractionIntercostalGaps;
    /** Движение крыльев носа */
    private Boolean noseWingsMovement;
    /** Шумное дыхание */
    private Boolean noisyBreathing;
    /** Хрипы */
    private Boolean wheezing;
    /** Характеристика ЦНС */
    private VocScreeningCNS cns;
    /** Частота СД в минуту */
    private Long heartRate;
    /** Характеристика СД */
    private VocScreeningCardiacActivity cardiacActivity;
    /** Наличие шума */
    private Boolean noisePresence;
    /** Доп. информация */
    private String extraInfo;
}