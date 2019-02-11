package ru.ecom.mis.ejb.form.birth;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.birth.ScreeningCardiacFirst;
import ru.ecom.mis.ejb.form.birth.interceptors.ScreeningCardiacFirstPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Created by Milamesher on 23.01.2019.
 */
@EntityForm
@EntityFormPersistance(clazz= ScreeningCardiacFirst.class)
@Comment("Скрининг новорождённых на наличие врождённых пороков сердца I этап")
@WebTrail(comment = "Скрининг новорождённых I этап", nameProperties= "id", view="entityParentView-stac_screeningCardiacFirst.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/CardiacScreening")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(ScreeningCardiacFirstPreCreateInterceptor.class)
)
public class ScreeningCardiacFirstForm  extends IdEntityForm {
    /** СМО */
    @Comment("СМО")
    @Persist
    @Required
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

    /** Дата создания */
    @Comment("Дата создания")
    @DateString
    @DoDateString
    @Persist
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString
    @DoDateString
    @Persist
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
    /** Время создания */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
    /** Время редактрования */
    @Comment("Время редактирования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return theEditTime;}
    public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Кожные покровы */
    @Comment("Кожные покровы")
    @Persist
    @Required
    public Long getSkin() {return theSkin;}
    public void setSkin(Long aSkin) {theSkin = aSkin;}
    /** Пульсация артерий правой руки */
    @Comment("Пульсация артерий правой руки")
    @Persist
    @Required
    public Long getRightHandAP() {return theRightHandAP;}
    public void setRightHandAP(Long aRightHandAP) {theRightHandAP = aRightHandAP;}
    /** Пульсация артерий ноги */
    @Comment("Пульсация артерий ноги")
    @Persist
    @Required
    public Long getLegAP() {return theLegAP;}
    public void setLegAP(Long aLegAP) {theLegAP = aLegAP;}
    /** Пульсоксиметрия на конечностях одновременная - правая рука */
    @Comment("Пульсоксиметрия на конечностях одновременная - правая рука")
    @Persist
    @Required
    public Long getRightHandPulse() {return theRightHandPulse;}
    public void setRightHandPulse(Long aRightHandPulse) {theRightHandPulse = aRightHandPulse;}
    /** Пульсоксиметрия на конечностях одновременная - нога */
    @Comment("Пульсоксиметрия на конечностях одновременная - нога")
    @Persist
    @Required
    public Long getLegPulse() {return theLegPulse;}
    public void setLegPulse(Long aLegPulse) {theLegPulse = aLegPulse;}
    /** Частота дыхания в мин. */
    @Comment("Частота дыхания в мин.")
    @Persist
    @Required
    public Long getBreathingRate() {return theBreathingRate;}
    public void setBreathingRate(Long aBreathingRate) {theBreathingRate = aBreathingRate;}
    /** Втяжение межрёберных промежутков */
    @Comment("Втяжение межрёберных промежутков")
    @Persist
    public Boolean getRetractionIntercostalGaps() {return theRetractionIntercostalGaps;}
    public void setRetractionIntercostalGaps(Boolean aRetractionIntercostalGaps) {theRetractionIntercostalGaps = aRetractionIntercostalGaps;}
    /** Движение крыльев носа */
    @Comment("Движение крыльев носа")
    @Persist
    public Boolean getNoseWingsMovement() {return theNoseWingsMovement;}
    public void setNoseWingsMovement(Boolean aNoseWingsMovement) {theNoseWingsMovement = aNoseWingsMovement;}
    /** Шумное дыхание */
    @Comment("Шумное дыхание")
    @Persist
    public Boolean getNoisyBreathing() {return theNoisyBreathing;}
    public void setNoisyBreathing(Boolean aNoisyBreathing) {theNoisyBreathing = aNoisyBreathing;}
    /** Хрипы */
    @Comment("Хрипы")
    @Persist
    public Boolean getWheezing() {return theWheezing;}
    public void setWheezing(Boolean aWheezing) {theWheezing = aWheezing;}
    /** Частота СД в мин. */
    @Comment("Частота СД в мин.")
    @Persist
    @Required
    public Long getHeartRate() {return theHeartRate;}
    public void setHeartRate(Long aHeartRate) {theHeartRate = aHeartRate;}
    /** Характеристика СД */
    @Comment("Характеристика СД")
    @Persist
    @Required
    public Long getCardiacActivity() {return theCardiacActivity;}
    public void setCardiacActivity(Long aCardiacActivity) {theCardiacActivity = aCardiacActivity;}
    /** Характеристика ЦНС */
    @Comment("Характеристика ЦНС")
    @Persist
    @Required
    public Long getCNS() {return theCNS;}
    public void setCNS(Long aCNS) {theCNS = aCNS;}
    /** Наличие шума  */
    @Comment("Наличие шума")
    @Persist
    public Boolean getNoisePresence() {return theNoisePresence;}
    public void setNoisePresence(Boolean aNoisePresence) {theNoisePresence = aNoisePresence;}
    /** Доп. информация  */
    @Comment("Доп. информация")
    @Persist
    public String getExtraInfo() {return theExtraInfo;}
    public void setExtraInfo(String aExtraInfo) {theExtraInfo = aExtraInfo;}

    /** СМО */
    private Long theMedCase;
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;
    /** Пользователь, который создал запись */
    private String theCreateUsername;
    /** Время редактрования */
    private String theEditTime;
    /** Время создания */
    private String theCreateTime;
    /** Дата редактирования */
    private String theEditDate;
    /** Дата создания */
    private String theCreateDate;
    /** Кожные покровы */
    private Long theSkin;
    /** Пульсация артерий правой руки */
    private Long theRightHandAP;
    /** Пульсация артерий ноги */
    private Long theLegAP;
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
    /** Частота СД в минуту */
    private Long theHeartRate;
    /** Характеристика СД */
    private Long theCardiacActivity;
    /** Наличие шума */
    private Boolean theNoisePresence;
    /** Характеристика ЦНС */
    private Long theCNS;
    /** Доп. информация */
    private String theExtraInfo;
}