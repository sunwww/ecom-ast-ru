package ru.ecom.oncological.ejb.form;

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
    private String theMKB;
    /**Признак проведения профилактики тошноты и рвотного рефлекса*/
    private Boolean isNauseaAndGagReflexPrev;

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
    private String theDateCons;
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
    private String theAllMeds;

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

    @DateString @DoDateString
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Persist
    public Boolean getIsFirst() { return isFirst; }
    public void setIsFirst(Boolean first) { isFirst = first; }

    @DateString @DoDateString
    public String getDateBiops() {
        return dateBiops;
    }
    public void setDateBiops(String dateBiops) {
        this.dateBiops = dateBiops;
    }

    @DateString @DoDateString
    public String getDateCons() { return theDateCons; }
    public void setDateCons(String dateCons) { this.theDateCons = dateCons; }

    @DateString @DoDateString
    public String getDate1() {
        return date1;
    }
    public void setDate1(String date1) {
        this.date1 = date1;
    }

    @DateString @DoDateString
    public String getDate2() {
        return date2;
    }
    public void setDate2(String date2) {
        this.date2 = date2;
    }

    @DateString @DoDateString
    public String getDate3() {
        return date3;
    }
    public void setDate3(String date3) {
        this.date3 = date3;
    }

    @DateString @DoDateString
    public String getDate4() {
        return date4;
    }
    public void setDate4(String date4) {
        this.date4 = date4;
    }

    @DateString @DoDateString
    public String getDate5() {
        return date5;
    }
    public void setDate5(String date5) {
        this.date5 = date5;
    }

    @DateString @DoDateString
    public String getDate6() {
        return date6;
    }
    public void setDate6(String date6) {
        this.date6 = date6;
    }

    public String getHistString() {
        return histString;
    }
    public void setHistString(String histString) {
        this.histString = histString;
    }

    public String getContraString() {
        return contraString;
    }
    public void setContraString(String contraString) {
        this.contraString = contraString;
    }

    @Persist
    public String getMKB() {
        return theMKB;
    }
    public void setMKB(String aMKB) {
        theMKB = aMKB;
    }

    @Persist
    public Boolean getIsNauseaAndGagReflexPrev() { return isNauseaAndGagReflexPrev; }
    public void setIsNauseaAndGagReflexPrev(Boolean nauseaAndGagReflexPrev) { isNauseaAndGagReflexPrev = nauseaAndGagReflexPrev; }


    public String getAllMeds() {
        return theAllMeds;
    }
    public void setAllMeds(String aAllMeds) { theAllMeds = aAllMeds; }
}