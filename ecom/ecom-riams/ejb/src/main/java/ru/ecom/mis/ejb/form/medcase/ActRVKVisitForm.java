package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.*;
import ru.ecom.mis.ejb.domain.medcase.ActRVK;
import ru.ecom.mis.ejb.form.medcase.poly.VisitMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Created by Milamesher on 05.12.2019.
 */
@Comment("Акт РВК")
@EntityForm
@EntityFormPersistance(clazz= ActRVK.class)
@WebTrail(comment = "Акт РВК амбулаторно", nameProperties= "id", view="entityParentView-rvk_aktVisit.do")
@Parent(property="medCase", parentForm= VisitMedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/ActRVK")
@ACreateInterceptors(
        @AEntityFormInterceptor(ActRVKCreateInterceptor.class)
)
public class ActRVKVisitForm extends IdEntityForm {
    /** Дата начала акта */
    @Comment("Дата начала акта")
    @DateString @DoDateString
    @Persist @Required
    public String getDateStart() {return theDateStart;	}
    public void setDateStart(String aDateStart) {theDateStart = aDateStart;}
    /** Дата начала акта */
    private String theDateStart;

    /** Дата окончания акта */
    @Comment("Дата окончания акта")
    @DateString @DoDateString
    @Persist
    public String getDateFinish() {return theDateFinish;	}
    public void setDateFinish(String aDateFinish) {theDateFinish = aDateFinish;}
    /** Дата окончания акта */
    private String theDateFinish;

    /** Примечание */
    @Comment("Примечание")
    @Persist
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}
    /** Примечание */
    private String theComment;

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {	return thePatient;	}
    public void setPatient(Long aPatient) {thePatient = aPatient;	}
    /** Пациент */
    private Long thePatient;

    /** Код диагноза */
    @Comment("Код диагноза")
    @Persist
    public Long getIdc10() {return theIdc10;}
    public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}
    /** Код диагноза */
    private Long theIdc10;

    /** Диагноз */
    @Comment("Диагноз")
    @Persist
    public String getDiagnosis() {return theDiagnosis;}
    public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}
    /** Диагноз */
    private String theDiagnosis;

    /** Parent госпитализация/визит */
    @Comment("Parent госпитализация/визит")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}
    /** Parent госпитализация/визит */
    private Long theMedCase;

    /** Отделение */
    @Comment("Отделение")
    @Persist
    public Long getDepartment() {return theDepartment;	}
    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;	}
    /** Отделение */
    private Long theDepartment;

    /** Номер акта */
    @Comment("Номер акта")
    @Persist
    public String getNumAct() {return theNumAct;}
    public void setNumAct(String aNumAct) {theNumAct = aNumAct;}
    /** Номер акта */
    private String theNumAct;

    /** Дата создания */
    @Comment("Дата создания")
    @DateString @DoDateString
    @Persist
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата создания */
    private String theCreateDate;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString @DoDateString
    @Persist
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
    /** Дата редактирования */
    private String theEditDate;

    /** Время создания */
    @Comment("Время создания")
    @TimeString @DoTimeString
    @Persist
    public String getCreateTime() {return theCreateTime;}
    public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
    /** Время создания */
    private String theCreateTime;

    /** Время редактрования */
    @Comment("Время редактирования")
    @TimeString
    @DoTimeString @Persist
    public String getEditTime() {return theEditTime;}
    public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
    /** Время редактрования */
    private String theEditTime;

    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
    /** Пользователь, который создал запись */
    private String theCreateUsername;

    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
    /** Пользователь, который последний редактировал запись */
    private String theEditUsername;

    /** Рабочая функция открывшего */
    @Comment("Рабочая функция открывшего")
    @Persist
    public Long getWorkFunctionStart() {return theWorkFunctionStart;}
    public void setWorkFunctionStart(Long aWorkFunctionStart) {theWorkFunctionStart = aWorkFunctionStart;}
    /** Рабочая функция открывшего */
    private Long theWorkFunctionStart;

    /** Рабочая функция закрывшего */
    @Comment("Рабочая функция закрывшего")
    @Persist
    public Long getWorkFunctionFinish() {return theWorkFunctionFinish;}
    public void setWorkFunctionFinish(Long aWorkFunctionFinish) {theWorkFunctionFinish = aWorkFunctionFinish;}
    /** Рабочая функция закрывшего */
    private Long theWorkFunctionFinish;

    /** Название раб. функции открывшего акт*/
    @Comment("Название раб. функции открывшего акт")
    @Persist
    public Long getSpecName() {return theSpecName;}
    public void setSpecName(Long aSpecName) {theSpecName = aSpecName;}
    /** Название раб. функции открывшего акт*/
    private Long theSpecName;
}