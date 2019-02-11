package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocCoefficient;
import ru.ecom.expert2.domain.voc.VocCoefficientLpuLevel;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Коэффицинт уровня оказания мед. помощи
 */
@EntityForm
@EntityFormPersistance(clazz = VocCoefficient.class)
@Comment("Коэффициент")
@WebTrail(comment = "Коэффициент", nameProperties = "id", view = "entityView-e2_vocCofficient.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocCoefficientForm extends IdEntityForm {

    /** Дата начала действия */
    @Comment("Дата начала действия")
    @Persist @DateString @DoDateString
    public String getStartDate() {return theStartDate;}
    public void setStartDate(String aStartDate) {theStartDate = aStartDate;}
    /** Дата начала действия */
    private String theStartDate ;

    /** Дата окончания действия */
    @Comment("Дата окончания действия")
    @Persist @DateString @DoDateString
    public String getFinishDate() {return theFinishDate;}
    public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}
    /** Дата окончания действия */
    private String theFinishDate ;

    /** Значение коэффициента */
    @Comment("Значение коэффициента")
    @Persist
    public String getValue() {return theValue;}
    public void setValue(String aValue) {theValue = aValue;}
    /** Значение коэффициента */
    private String theValue ;
}
