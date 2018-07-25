package ru.ecom.oncological.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.oncological.ejb.domain.OncologyDirection;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by rkurbanov on 19.07.2018.
 */

@EntityForm
@EntityFormPersistance(clazz = OncologyDirection.class)
@Comment("Онкологическое направление")
@WebTrail(comment = "Онкологическое направление", nameProperties= "id"
        , view="entityParentView-oncology_direction.do"
        , shortView="entityShortView-oncology_direction.do"
)
@Parent(property="oncologyCase", parentForm=OncologyCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Oncology/Direction")
public class OncologyDirectionForm extends IdEntityForm {

    /** Случай окологического лечения */
    private Long oncologyCase;
    /** Метод диагностического лечения */
    private Long methodDiagTreat;
    /** Вид направления */
    private Long typeDirection;
    /** мед услуга по V001 */
    private Long medService;




    @Persist
    public Long getOncologyCase() {
        return oncologyCase;
    }
    public void setOncologyCase(Long oncologyCase) {
        this.oncologyCase = oncologyCase;
    }

    @Persist
    public Long getMethodDiagTreat() {
        return methodDiagTreat;
    }
    public void setMethodDiagTreat(Long methodDiagTreat) {
        this.methodDiagTreat = methodDiagTreat;
    }

    @Persist
    public Long getTypeDirection() {
        return typeDirection;
    }
    public void setTypeDirection(Long typeDirection) {
        this.typeDirection = typeDirection;
    }

    @Persist
    public Long getMedService() {
        return medService;
    }
    public void setMedService(Long medService) {
        this.medService = medService;
    }
}
