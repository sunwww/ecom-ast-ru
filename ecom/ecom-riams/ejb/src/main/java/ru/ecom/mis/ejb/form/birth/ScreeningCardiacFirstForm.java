package ru.ecom.mis.ejb.form.birth;

import lombok.Setter;
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
@Setter
public class ScreeningCardiacFirstForm  extends IdEntityForm {
    /** СМО */
    @Comment("СМО")
    @Persist
    @Required
    public Long getMedCase() {return medCase;}

    /** Дата создания */
    @Comment("Дата создания")
    @DateString
    @DoDateString
    @Persist
    public String getCreateDate() {return createDate;}
    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString
    @DoDateString
    @Persist
    public String getEditDate() {return editDate;}
    /** Время создания */
    @Comment("Время создания")
    @TimeString
    @DoTimeString
    @Persist
    public String getCreateTime() {return createTime;}
    /** Время редактрования */
    @Comment("Время редактирования")
    @TimeString @DoTimeString @Persist
    public String getEditTime() {return editTime;}
    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return createUsername;}
    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return editUsername;}
    /** Кожные покровы */
    @Comment("Кожные покровы")
    @Persist
    @Required
    public Long getSkin() {return skin;}
    /** Пульсация артерий правой руки */
    @Comment("Пульсация артерий правой руки")
    @Persist
    @Required
    public Long getRightHandAP() {return rightHandAP;}
    /** Пульсация артерий ноги */
    @Comment("Пульсация артерий ноги")
    @Persist
    @Required
    public Long getLegAP() {return legAP;}
    /** Пульсоксиметрия на конечностях одновременная - правая рука */
    @Comment("Пульсоксиметрия на конечностях одновременная - правая рука")
    @Persist
    @Required
    public Long getRightHandPulse() {return rightHandPulse;}
    /** Пульсоксиметрия на конечностях одновременная - нога */
    @Comment("Пульсоксиметрия на конечностях одновременная - нога")
    @Persist
    @Required
    public Long getLegPulse() {return legPulse;}
    /** Частота дыхания в мин. */
    @Comment("Частота дыхания в мин.")
    @Persist
    @Required
    public Long getBreathingRate() {return breathingRate;}
    /** Втяжение межрёберных промежутков */
    @Comment("Втяжение межрёберных промежутков")
    @Persist
    public Boolean getRetractionIntercostalGaps() {return retractionIntercostalGaps;}
    /** Движение крыльев носа */
    @Comment("Движение крыльев носа")
    @Persist
    public Boolean getNoseWingsMovement() {return noseWingsMovement;}
    /** Шумное дыхание */
    @Comment("Шумное дыхание")
    @Persist
    public Boolean getNoisyBreathing() {return noisyBreathing;}
    /** Хрипы */
    @Comment("Хрипы")
    @Persist
    public Boolean getWheezing() {return wheezing;}
    /** Частота СД в мин. */
    @Comment("Частота СД в мин.")
    @Persist
    @Required
    public Long getHeartRate() {return heartRate;}
    /** Характеристика СД */
    @Comment("Характеристика СД")
    @Persist
    @Required
    public Long getCardiacActivity() {return cardiacActivity;}
    /** Характеристика ЦНС */
    @Comment("Характеристика ЦНС")
    @Persist
    @Required
    public Long getCns() {return cns;}
    /** Наличие шума  */
    @Comment("Наличие шума")
    @Persist
    public Boolean getNoisePresence() {return noisePresence;}
    /** Доп. информация  */
    @Comment("Доп. информация")
    @Persist
    public String getExtraInfo() {return extraInfo;}

    /** СМО */
    private Long medCase;
    /** Пользователь, который последний редактировал запись */
    private String editUsername;
    /** Пользователь, который создал запись */
    private String createUsername;
    /** Время редактрования */
    private String editTime;
    /** Время создания */
    private String createTime;
    /** Дата редактирования */
    private String editDate;
    /** Дата создания */
    private String createDate;
    /** Кожные покровы */
    private Long skin;
    /** Пульсация артерий правой руки */
    private Long rightHandAP;
    /** Пульсация артерий ноги */
    private Long legAP;
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
    /** Частота СД в минуту */
    private Long heartRate;
    /** Характеристика СД */
    private Long cardiacActivity;
    /** Наличие шума */
    private Boolean noisePresence;
    /** Характеристика ЦНС */
    private Long cns;
    /** Доп. информация */
    private String extraInfo;
}