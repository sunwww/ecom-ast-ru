package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.jaas.ejb.form.interceptor.SecPolicySaveInterceptor;
import ru.ecom.jaas.ejb.form.interceptor.SecPolicyViewInterceptor;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.ecom.mis.ejb.form.worker.interceptor.VocWorkFunctionSaveInterceptor;
import ru.ecom.mis.ejb.form.worker.interceptor.VocWorkFunctionViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= VocWorkFunction.class)
@Comment("Рабочая функция")
@WebTrail(comment = "Рабочая функция", nameProperties= "name", view="entityView-voc_workFunction.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocWorkFunction")
/*
@ASaveInterceptors(
        @AEntityFormInterceptor(VocWorkFunctionSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(VocWorkFunctionViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(VocWorkFunctionSaveInterceptor.class)
	
})*/
public class VocWorkFunctionForm extends IdEntityForm {
	/** Должности */
	@Comment("Должности")
	@Persist @Required
	public Long getVocPost() {return theVocPost;}
	public void setVocPost(Long aVocPost) {theVocPost = aVocPost;}
	
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	
	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код */
	private String theCode;
	/** Наименование */
	private String theName;
	/** Должности */
	private Long theVocPost;
	
	/** Короткое название */
	@Comment("Короткое название")
	@Persist
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Короткое название */
	private String theShortName;
	/** Не заполняется диагноз */
	@Comment("Не заполняется диагноз")
	@Persist
	public Boolean getIsNoDiagnosis() {return theIsNoDiagnosis;}
	public void setIsNoDiagnosis(Boolean aIsNoDiagnosis) {theIsNoDiagnosis = aIsNoDiagnosis;}

	/** Не заполняется диагноз */
	private Boolean theIsNoDiagnosis;

}
