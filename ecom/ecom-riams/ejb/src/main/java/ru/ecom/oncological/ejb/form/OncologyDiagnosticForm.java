package ru.ecom.oncological.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.oncological.ejb.domain.OncologyDiagnostic;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/** Created by rkurbanov on 19.07.2018. */
@EntityForm
@EntityFormPersistance(clazz = OncologyDiagnostic.class)
@Comment("Диагностический блок")
@WebTrail(comment = "Диагностический блок", nameProperties= "id"
        , view="entityParentView-oncology_diagnostic.do"
        , shortView="entityShortView-oncology_diagnostic.do"
)
@Parent(property="oncologyCase", parentForm=OncologyCaseReestrForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Oncology/Diagnostic")
public class OncologyDiagnosticForm extends IdEntityForm {

    /**Случай онкологического лечения */
    private Long oncologyCase;
    /**Тип диагностического показателя*/
    private Long vocOncologyDiagType;
    /**Код диагностического показателя*/
    private Long histiology;
    /**Код диагностического показателя*/
    private Long markers;
    /**Код результата диагностики*/
    private Long resultHistiology;
    /**Код результата диагностики*/
    private Long valueMarkers;

    @Persist
    public Long getOncologyCase() {
        return oncologyCase;
    }
    public void setOncologyCase(Long oncologyCase) {
        this.oncologyCase = oncologyCase;
    }

    @Persist
    public Long getVocOncologyDiagType() {
        return vocOncologyDiagType;
    }
    public void setVocOncologyDiagType(Long vocOncologyDiagType) {
        this.vocOncologyDiagType = vocOncologyDiagType;
    }

    @Persist
    public Long getHistiology() {
        return histiology;
    }
    public void setHistiology(Long histiology) {
        this.histiology = histiology;
    }

    @Persist
    public Long getMarkers() {
        return markers;
    }
    public void setMarkers(Long markers) {
        this.markers = markers;
    }

    @Persist
    public Long getResultHistiology() {
        return resultHistiology;
    }
    public void setResultHistiology(Long resultHistiology) {
        this.resultHistiology = resultHistiology;
    }

    @Persist
    public Long getValueMarkers() {
        return valueMarkers;
    }
    public void setValueMarkers(Long valueMarkers) {
        this.valueMarkers = valueMarkers;
    }
}
