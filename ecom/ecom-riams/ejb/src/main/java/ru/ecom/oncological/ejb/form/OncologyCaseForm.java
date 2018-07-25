package ru.ecom.oncological.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.oncological.ejb.domain.OncologyCase;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Cлучай онкологического лечения
 * @author rkurbanov
 */

@EntityForm
@EntityFormPersistance(clazz = OncologyCase.class)
@Comment("Cлучай онкологического лечения")
@WebTrail(comment = "Cлучай онкологического лечения", nameProperties= "id"
        , view="entityParentView-oncology_case.do"
        , shortView="entityShortView-oncology_case.do"
)
@Parent(property="medCase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Oncology/Case")
public class OncologyCaseForm extends IdEntityForm {

    private Long medCase;
    /**Сведения о случае лечения онкологического заболевания */
    /**Повод обращения*/
    private Long vocOncologyReasonTreat;
    /**Стадия заболевания*/
    private Long stad;
    /**Значение Tumor*/
    private Long tumor;
    /**Значение Nodus*/
    private Long nodus;
    /**Значение Metastasis_*/
    private Long metastasis;
    /**Признак отдаленных метастазов*/
    private Boolean isDistantMetastasis;
    /**Суммарная очаговая доза*/
    private String sumDose;
    /**Подозрение на онкологию*/
    private Boolean isSuspicionOncologist;

    /**Сведения об услуге при онкологического заболевания */
    /**Сведения о проведении консилиума*/
     private Long consilium;
    /**Тип услуги*/
    private Long typeTreatment;
    /**Тип хирургического лечения*/
    private Long surgTreatment;
    /**Линия лекарственной терамии*/
    private Long lineDrugTherapy;
    /**Цикл лекарственной терапии*/
    private Long cycleDrugTherapy;
    /**Тип лучевой терапии*/
    private Long typeRadTherapy;

    @Persist
    public Long getMedCase() {
        return medCase;
    }
    public void setMedCase(Long medCase) {
        this.medCase = medCase;
    }

    @Persist
    public Long getVocOncologyReasonTreat() {
        return vocOncologyReasonTreat;
    }
    public void setVocOncologyReasonTreat(Long vocOncologyReasonTreat) {
        this.vocOncologyReasonTreat = vocOncologyReasonTreat;
    }

    @Persist
    public Long getStad() {
        return stad;
    }
    public void setStad(Long stad) {
        this.stad = stad;
    }

    @Persist
    public Long getTumor() {
        return tumor;
    }
    public void setTumor(Long tumor) {
        this.tumor = tumor;
    }

    @Persist
    public Long getNodus() {
        return nodus;
    }
    public void setNodus(Long nodus) {
        this.nodus = nodus;
    }

    @Persist
    public Long getMetastasis() {
        return metastasis;
    }
    public void setMetastasis(Long metastasis) {
        this.metastasis = metastasis;
    }

    @Persist
    public Boolean getDistantMetastasis() {
        return isDistantMetastasis;
    }
    public void setDistantMetastasis(Boolean distantMetastasis) {
        isDistantMetastasis = distantMetastasis;
    }

    @Persist
    public String getSumDose() {
        return sumDose;
    }
    public void setSumDose(String sumDose) {
        this.sumDose = sumDose;
    }

    @Persist
    public Boolean getSuspicionOncologist() {
        return isSuspicionOncologist;
    }
    public void setSuspicionOncologist(Boolean suspicionOncologist) {
        isSuspicionOncologist = suspicionOncologist;
    }

    @Persist
    public Long getTypeTreatment() {
        return typeTreatment;
    }
    public void setTypeTreatment(Long typeTreatment) {
        this.typeTreatment = typeTreatment;
    }

    @Persist
    public Long getSurgTreatment() {
        return surgTreatment;
    }
    public void setSurgTreatment(Long surgTreatment) {
        this.surgTreatment = surgTreatment;
    }

    @Persist
    public Long getLineDrugTherapy() {
        return lineDrugTherapy;
    }
    public void setLineDrugTherapy(Long lineDrugTherapy) {
        this.lineDrugTherapy = lineDrugTherapy;
    }

    @Persist
    public Long getCycleDrugTherapy() {
        return cycleDrugTherapy;
    }
    public void setCycleDrugTherapy(Long cycleDrugTherapy) {
        this.cycleDrugTherapy = cycleDrugTherapy;
    }

    @Persist
    public Long getTypeRadTherapy() {
        return typeRadTherapy;
    }
    public void setTypeRadTherapy(Long typeRadTherapy) {
        this.typeRadTherapy = typeRadTherapy;
    }

    @Persist
    public Long getConsilium() {
        return consilium;
    }
    public void setConsilium(Long consilium) {
        this.consilium = consilium;
    }


    /** Метод диагностического лечения */
    private Long methodDiagTreat;
    /** Вид направления */
    private Long typeDirection;
    /** мед услуга по V001 */
    private Long medService;

    public Long getMethodDiagTreat() {
        return methodDiagTreat;
    }
    public void setMethodDiagTreat(Long methodDiagTreat) {
        this.methodDiagTreat = methodDiagTreat;
    }

    public Long getTypeDirection() {
        return typeDirection;
    }
    public void setTypeDirection(Long typeDirection) {
        this.typeDirection = typeDirection;
    }

    public Long getMedService() {
        return medService;
    }
    public void setMedService(Long medService) {
        this.medService = medService;
    }
}
