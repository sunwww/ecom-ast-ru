package ru.ecom.mis.ejb.domain.birth;/**
 * Created by Milamesher on 23.01.2019.
 */

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
public class ScreeningCardiac extends BaseEntity {
    /** СМО */
    @Comment("СМО")
    @OneToOne
    public MedCase getMedCase() {return theMedCase;}
    public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
    /** Дата создания */
    @Comment("Дата создания")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата редактирования */
    @Comment("Дата редактирования")
    public Date getEditDate() {return theEditDate;}
    public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
    /** Время создания */
    @Comment("Время создания")
    public Time getCreateTime() {return theCreateTime;}
    public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
    /** Время редактрования */
    @Comment("Время редактирования")
    public Time getEditTime() {return theEditTime;}
    public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Кожные покровы */
    @Comment("Кожные покровы")
    @OneToOne
    public VocScreeningSkin getSkin() {return theSkin;}
    public void setSkin(VocScreeningSkin aSkin) {theSkin = aSkin;}
    /** Пульсация артерий правой руки */
    @Comment("Пульсация артерий правой руки")
    @OneToOne
    public VocScreeningArterialPulsation getRightHandAP() {return theRightHandAP;}
    public void setRightHandAP(VocScreeningArterialPulsation aRightHandAP) {theRightHandAP = aRightHandAP;}
    /** Пульсация артерий ноги */
    @Comment("Пульсация артерий ноги")
    @OneToOne
    public VocScreeningArterialPulsation getLegAP() {return theLegAP;}
    public void setLegAP(VocScreeningArterialPulsation aLegAP) {theLegAP = aLegAP;}
    /** Пульсоксиметрия на конечностях одновременная - правая рука */
    @Comment("Пульсоксиметрия на конечностях одновременная - правая рука")
    public Long getRightHandPulse() {return theRightHandPulse;}
    public void setRightHandPulse(Long aRightHandPulse) {theRightHandPulse = aRightHandPulse;}
    /** Пульсоксиметрия на конечностях одновременная - нога */
    @Comment("Пульсоксиметрия на конечностях одновременная - нога")
    public Long getLegPulse() {return theLegPulse;}
    public void setLegPulse(Long aLegPulse) {theLegPulse = aLegPulse;}
    /** Частота дыхания в мин. */
    @Comment("Частота дыхания в мин.")
    public Long getBreathingRate() {return theBreathingRate;}
    public void setBreathingRate(Long aBreathingRate) {theBreathingRate = aBreathingRate;}
    /** Втяжение межрёберных промежутков */
    @Comment("Втяжение межрёберных промежутков")
    public Boolean getRetractionIntercostalGaps() {return theRetractionIntercostalGaps;}
    public void setRetractionIntercostalGaps(Boolean aRetractionIntercostalGaps) {theRetractionIntercostalGaps = aRetractionIntercostalGaps;}
    /** Движение крыльев носа */
    @Comment("Движение крыльев носа")
    public Boolean getNoseWingsMovement() {return theNoseWingsMovement;}
    public void setNoseWingsMovement(Boolean aNoseWingsMovement) {theNoseWingsMovement = aNoseWingsMovement;}
    /** Шумное дыхание */
    @Comment("Шумное дыхание")
    public Boolean getNoisyBreathing() {return theNoisyBreathing;}
    public void setNoisyBreathing(Boolean aNoisyBreathing) {theNoisyBreathing = aNoisyBreathing;}
    /** Хрипы */
    @Comment("Хрипы")
    public Boolean getWheezing() {return theWheezing;}
    public void setWheezing(Boolean aWheezing) {theWheezing = aWheezing;}
    /** Характеристика ЦНС */
    @Comment("Характеристика ЦНС")
    @OneToOne
    public VocScreeningCNS getCNS() {return theCNS;}
    public void setCNS(VocScreeningCNS aCNS) {theCNS = aCNS;}
    /** Частота СД в мин. */
    @Comment("Частота СД в мин.")
    public Long getHeartRate() {return theHeartRate;}
    public void setHeartRate(Long aHeartRate) {theHeartRate = aHeartRate;}
    /** Характеристика СД */
    @Comment("Характеристика СД")
    @OneToOne
    public VocScreeningCardiacActivity getCardiacActivity() {return theCardiacActivity;}
    public void setCardiacActivity(VocScreeningCardiacActivity aCardiacActivity) {theCardiacActivity = aCardiacActivity;}
    /** Наличие шума  */
    @Comment("Наличие шума")
    public Boolean getNoisePresence() {return theNoisePresence;}
    public void setNoisePresence(Boolean aNoisePresence) {theNoisePresence = aNoisePresence;}
    /** Доп. информация  */
    @Comment("Доп. информация")
    public String getExtraInfo() {return theExtraInfo;}
    public void setExtraInfo(String aExtraInfo) {theExtraInfo = aExtraInfo;}

    /** СМО */
    private MedCase theMedCase;
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;
    /** Пользователь, который создал запись */
    private String theCreateUsername;
    /** Время редактрования */
    private Time theEditTime;
    /** Время создания */
    private Time theCreateTime;
    /** Дата редактирования */
    private Date theEditDate;
    /** Дата создания */
    private Date theCreateDate;
    /** Кожные покровы */
    private VocScreeningSkin theSkin;
    /** Пульсация артерий правой руки */
    private VocScreeningArterialPulsation theRightHandAP;
    /** Пульсация артерий ноги */
    private VocScreeningArterialPulsation theLegAP;
    /** Пульсоксиметрия на конечностях одновременная - правая рука*/
    private Long theRightHandPulse;
    /** Пульсоксиметрия на конечностях одновременная - нога*/
    private Long theLegPulse;
    /** Частота дыхания в мин. */
    private Long theBreathingRate;
    /** Втяжение межрёберных промежутков */
    private Boolean theRetractionIntercostalGaps;
    /** Движение крыльев носа */
    private Boolean theNoseWingsMovement;
    /** Шумное дыхание */
    private Boolean theNoisyBreathing;
    /** Хрипы */
    private Boolean theWheezing;
    /** Характеристика ЦНС */
    private VocScreeningCNS theCNS;
    /** Частота СД в минуту */
    private Long theHeartRate;
    /** Характеристика СД */
    private VocScreeningCardiacActivity theCardiacActivity;
    /** Наличие шума */
    private Boolean theNoisePresence;
    /** Доп. информация */
    private String theExtraInfo;
}