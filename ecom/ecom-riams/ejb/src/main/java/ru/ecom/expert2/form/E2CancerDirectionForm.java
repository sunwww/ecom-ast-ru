package ru.ecom.expert2.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.E2CancerDirection;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz = E2CancerDirection.class)
@Comment("Направление на онк. лечение")
@WebTrail(comment = "Направление на онк. лечение", nameProperties = "id", view = "entityView-e2_cancerDirection.do")
@EntityFormSecurityPrefix("/Policy/E2")
@Parent(property = "cancerEntry", parentForm = E2CancerEntryForm.class)
@Setter
public class E2CancerDirectionForm extends IdEntityForm {

    /** ЛПУ, куда сделали направление */
    @Comment("ЛПУ, куда сделали направление")
    @Persist
    public String getDirectLpu() {return directLpu;}
    /** ЛПУ, куда сделали направление */
    private String directLpu ;

    /** Случай рака */
    @Comment("Случай рака")
    @Persist
    public Long getCancerEntry() {return cancerEntry;}
    /** Случай рака */
    private Long cancerEntry ;

    /** Дата направления */
    @Comment("Дата направления")
    @Persist
    @DateString @DoDateString
    public String getDate() {return date;}
    /** Дата направления */
    private String date ;

    /** Вид направление */
    @Comment("Вид направление")
    @Persist
    public String getType() {return type;}
    /** Вид направление */
    private String type ;

    /** Метод диагностического исследования */
    @Comment("Метод диагностического исследования")
    @Persist
    public String getSurveyMethod() {return surveyMethod;}
    /** Метод диагностического исследования */
    private String surveyMethod ;

    /** Медицинская услуга */
    @Comment("Медицинская услуга")
    @Persist
    public String getMedService() {return medService;}
    /** Медицинская услуга */
    private String medService ;
}
