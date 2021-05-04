package ru.ecom.mis.ejb.form.medcase;

import lombok.Setter;
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
@Setter
public class ActRVKVisitForm extends IdEntityForm {
    /** Дата начала акта */
    @Comment("Дата начала акта")
    @DateString @DoDateString
    @Persist @Required
    public String getDateStart() {return dateStart;	}
    /** Дата начала акта */
    private String dateStart;

    /** Дата окончания акта */
    @Comment("Дата окончания акта")
    @DateString @DoDateString
    @Persist
    public String getDateFinish() {return dateFinish;	}
    /** Дата окончания акта */
    private String dateFinish;

    /** Примечание */
    @Comment("Примечание")
    @Persist
    public String getComment() {return comment;}
    /** Примечание */
    private String comment;

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {	return patient;	}
    /** Пациент */
    private Long patient;

    /** Код диагноза */
    @Comment("Код диагноза")
    @Persist
    public Long getIdc10() {return idc10;}
    /** Код диагноза */
    private Long idc10;

    /** Диагноз */
    @Comment("Диагноз")
    @Persist
    public String getDiagnosis() {return diagnosis;}
    /** Диагноз */
    private String diagnosis;

    /** Parent госпитализация/визит */
    @Comment("Parent госпитализация/визит")
    @Persist
    public Long getMedCase() {return medCase;}
    /** Parent госпитализация/визит */
    private Long medCase;

    /** Отделение */
    @Comment("Отделение")
    @Persist
    public Long getDepartment() {return department;	}
    /** Отделение */
    private Long department;

    /** Номер акта */
    @Comment("Номер акта")
    @Persist
    public String getNumAct() {return numAct;}
    /** Номер акта */
    private String numAct;

    /** Дата создания */
    @Comment("Дата создания")
    @DateString @DoDateString
    @Persist
    public String getCreateDate() {return createDate;}
    /** Дата создания */
    private String createDate;

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @DateString @DoDateString
    @Persist
    public String getEditDate() {return editDate;}
    /** Дата редактирования */
    private String editDate;

    /** Время создания */
    @Comment("Время создания")
    @TimeString @DoTimeString
    @Persist
    public String getCreateTime() {return createTime;}
    /** Время создания */
    private String createTime;

    /** Время редактрования */
    @Comment("Время редактирования")
    @TimeString
    @DoTimeString @Persist
    public String getEditTime() {return editTime;}
    /** Время редактрования */
    private String editTime;

    /** Пользователь, который создал запись */
    @Comment("Пользователь, который создал запись")
    @Persist
    public String getCreateUsername() {return createUsername;}
    /** Пользователь, который создал запись */
    private String createUsername;

    /** Пользователь, который последний редактировал запись */
    @Comment("Пользователь, который последний редактировал запись")
    @Persist
    public String getEditUsername() {return editUsername;}
    /** Пользователь, который последний редактировал запись */
    private String editUsername;

    /** Рабочая функция открывшего */
    @Comment("Рабочая функция открывшего")
    @Persist
    public Long getWorkFunctionStart() {return workFunctionStart;}
    /** Рабочая функция открывшего */
    private Long workFunctionStart;

    /** Рабочая функция закрывшего */
    @Comment("Рабочая функция закрывшего")
    @Persist
    public Long getWorkFunctionFinish() {return workFunctionFinish;}
    /** Рабочая функция закрывшего */
    private Long workFunctionFinish;

    /** Название раб. функции открывшего акт*/
    @Comment("Название раб. функции открывшего акт")
    @Persist
    public Long getSpecName() {return specName;}
    /** Название раб. функции открывшего акт*/
    private Long specName;
}