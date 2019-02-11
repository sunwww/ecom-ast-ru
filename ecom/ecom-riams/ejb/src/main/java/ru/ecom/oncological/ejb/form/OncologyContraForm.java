package ru.ecom.oncological.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.oncological.ejb.domain.OncologyContra;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Created by rkurbanov on 19.07.2018.
 */

@EntityForm
@EntityFormPersistance(clazz = OncologyContra.class)
@Comment("Противопоказания и отказы")
@WebTrail(comment = "Противопоказания и отказы", nameProperties= "id"
        , view="entityParentView-oncology_contra.do"
        , shortView="entityShortView-oncology_contra.do"
)
@Parent(property="oncologyCase", parentForm=OncologyCaseReestrForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Oncology/Contra")
public class OncologyContraForm extends IdEntityForm {

    /**Случай онкологического лечения */
    private Long oncologyCase;
    /** Код противопоказания */
    private Long contraindicationAndRejection;
    /** Дата регистрации противопоказания или отказа*/
    private String date;

    @Persist
    public Long getOncologyCase() {
        return oncologyCase;
    }
    public void setOncologyCase(Long oncologyCase) {
        this.oncologyCase = oncologyCase;
    }

    @Persist
    public Long getContraindicationAndRejection() {
        return contraindicationAndRejection;
    }
    public void setContraindicationAndRejection(Long contraindicationAndRejection) {
        this.contraindicationAndRejection = contraindicationAndRejection;
    }

    @DateString @DoDateString @Persist
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
