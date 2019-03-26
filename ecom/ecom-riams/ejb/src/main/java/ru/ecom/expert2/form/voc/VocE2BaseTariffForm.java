package ru.ecom.expert2.form.voc;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocE2BaseTariff;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = VocE2BaseTariff.class)
@Comment("Справочник базовых тарифов")
@WebTrail(comment = "Справочник базовых тарифов", nameProperties = "id", view = "entityView-e2_vocBaseTariff.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class VocE2BaseTariffForm extends VocCoefficientForm {
    /** Тип стационар (круглосуточный, дневной */
    @Comment("Тип стационар (круглосуточный, дневной")
    @Persist
    public Long getStacType() {return theStacType;}
    public void setStacType(Long aStacType) {theStacType = aStacType;}
    /** Тип стационар (круглосуточный, дневной */
    private Long theStacType ;

    /** Тип тарифа */
    @Comment("Тип тарифа")
    @Persist
    public Long getType() {return theType;}
    public void setType(Long aType) {theType = aType;}
    /** Тип тарифа */
    private Long theType ;


    /** Вид случая */
    @Comment("Вид случая")
    @Persist
    public Long getVidSluch() {return theVidSluch;}
    public void setVidSluch(Long aVidSluch) {theVidSluch = aVidSluch;}
    /** Вид случая */
    private Long theVidSluch ;

}
