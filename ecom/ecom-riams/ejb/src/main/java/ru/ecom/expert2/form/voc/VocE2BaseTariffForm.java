package ru.ecom.expert2.form.voc;

import lombok.Setter;
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
@Setter
public class VocE2BaseTariffForm extends VocCoefficientForm {

    /** Тип тарифа */
    @Comment("Тип тарифа")
    @Persist
    public Long getType() {return type;}
    /** Тип тарифа */
    private Long type ;

}
