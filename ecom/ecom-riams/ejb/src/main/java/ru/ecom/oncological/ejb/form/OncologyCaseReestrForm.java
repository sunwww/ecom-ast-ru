package ru.ecom.oncological.ejb.form;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.OncologyCaseReestrPreCreateInterceptor;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.OncologyCaseReestrSaveInterceptor;
import ru.ecom.oncological.ejb.domain.OncologyCase;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
/**
 * Created by Milamesher on 01.10.2018.
 */
@EntityForm
@EntityFormPersistance(clazz = OncologyCase.class)
@Comment("Cлучай онкологического лечения")
@WebTrail(comment = "Cлучай онкологического лечения", nameProperties= "id"
        , view="entityParentView-oncology_case_reestr.do"
        , shortView="entityShortView-oncology_case_reestr.do"
)
@Parent(property="medCase", parentForm=MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Oncology/Case")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(OncologyCaseReestrPreCreateInterceptor.class)
)
@ASaveInterceptors(
        @AEntityFormInterceptor(OncologyCaseReestrSaveInterceptor.class)
)
@Setter
public class OncologyCaseReestrForm extends IdEntityForm {

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
    /**Подозрение на ЗНО*/
    private Boolean isSuspicionOncologist;

    /**Сведения об услуге при онкологического заболевания */
    /**Сведения о проведении консилиума*/
    private Long consilium;
    /**Тип услуги - для неспецифического лечения*/
    private Long typeTreatment;
    /**Тип хирургического лечения*/
    private Long surgTreatment;
    /**Линия лекарственной терамии*/
    private Long lineDrugTherapy;
    /**Цикл лекарственной терапии*/
    private Long cycleDrugTherapy;
    /**Тип лучевой терапии*/
    private Long typeRadTherapy;
    /** диагноз, с которым создана онкологическая форма*/
    private String mKB;
    /**Признак проведения профилактики тошноты и рвотного рефлекса*/
    private Boolean isNauseaAndGagReflexPrev;

    @Persist
    public Long getMedCase() {
        return medCase;
    }

    @Persist
    public Long getVocOncologyReasonTreat() {
        return vocOncologyReasonTreat;
    }

    @Persist
    public Long getStad() {
        return stad;
    }

    @Persist
    public Long getTumor() {
        return tumor;
    }

    @Persist
    public Long getNodus() {
        return nodus;
    }

    @Persist
    public Long getMetastasis() {
        return metastasis;
    }

    @Persist
    public Boolean getDistantMetastasis() {
        return isDistantMetastasis;
    }

    public void setDistantMetastasis(Boolean isDistantMetastasis) {
        this.isDistantMetastasis = isDistantMetastasis;
    }

    @Persist
    public String getSumDose() {
        return sumDose;
    }

    @Persist
    public Boolean getSuspicionOncologist() {
        return isSuspicionOncologist;
    }

    public void setSuspicionOncologist(Boolean suspicionOncologist) {
        this.isSuspicionOncologist = suspicionOncologist;
    }

    @Persist
    public Long getTypeTreatment() {
        return typeTreatment;
    }

    @Persist
    public Long getSurgTreatment() {
        return surgTreatment;
    }

    @Persist
    public Long getLineDrugTherapy() {
        return lineDrugTherapy;
    }

    @Persist
    public Long getCycleDrugTherapy() {
        return cycleDrugTherapy;
    }

    @Persist
    public Long getTypeRadTherapy() {
        return typeRadTherapy;
    }

    @Persist
    public Long getConsilium() {
        return consilium;
    }

    /** дата направления */
    private String date;
    /** Метод диагностического лечения */
    private Long methodDiagTreat;
    /** Вид направления */
    private Long typeDirection;
    /** мед услуга по V001 */
    private Long medService;
    /**Выявлено впервые?*/
    private Boolean isFirst;
    /** дата взятия биопсийного материала */
    private String dateBiops;
    /** дата проведения консилиума */
    private String dateCons;
    /** дата 1 сontra */
    private String date1;
    /** дата 2 сontra */
    private String date2;
    /** дата 3 сontra */
    private String date3;
    /** дата 4 сontra */
    private String date4;
    /** дата 5 сontra */
    private String date5;
    /** дата 6 сontra */
    private String date6;
    /** данные для сохранения гистологии и маркеров*/
    private String histString;
    /** данные для сохранения противопоказаний и отказов*/
    private String contraString;
    /** данные для сохранения медикаментов*/
    private String allMeds;

    public Long getMethodDiagTreat() {
        return methodDiagTreat;
    }

    public Long getTypeDirection() {
        return typeDirection;
    }

    public Long getMedService() {
        return medService;
    }

    @DateString @DoDateString
    public String getDate() {
        return date;
    }

    @Persist
    public Boolean getIsFirst() { return isFirst; }

    @DateString @DoDateString
    public String getDateBiops() {
        return dateBiops;
    }

    @DateString @DoDateString
    public String getDateCons() { return dateCons; }

    @DateString @DoDateString
    public String getDate1() {
        return date1;
    }

    @DateString @DoDateString
    public String getDate2() {
        return date2;
    }

    @DateString @DoDateString
    public String getDate3() {
        return date3;
    }

    @DateString @DoDateString
    public String getDate4() {
        return date4;
    }

    @DateString @DoDateString
    public String getDate5() {
        return date5;
    }

    @DateString @DoDateString
    public String getDate6() {
        return date6;
    }

    public String getHistString() {
        return histString;
    }

    public String getContraString() {
        return contraString;
    }

    @Persist
    public String getMKB() {
        return mKB;
    }

    @Persist
    public Boolean getIsNauseaAndGagReflexPrev() { return isNauseaAndGagReflexPrev; }


    public String getAllMeds() {
        return allMeds;
    }
}