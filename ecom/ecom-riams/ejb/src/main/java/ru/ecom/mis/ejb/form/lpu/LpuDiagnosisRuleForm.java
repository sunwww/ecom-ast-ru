package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.LpuDiagnosisRule;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = LpuDiagnosisRule.class)
@Comment("Правило установки диагноза по отделению")
@WebTrail(comment = "Правило установки диагноза по отделению", nameProperties= "id", list="entityList-mis_LpuDiagnosisRule.do", view="entityView-mis_LpuDiagnosisRule.do")
@EntityFormSecurityPrefix("/Policy/Mis/MisLpu/LpuDiagnosisRule")
@Parent(property="department", parentForm=MisLpuForm.class)

public class LpuDiagnosisRuleForm extends IdEntityForm {
	
	/** Разрешающее правило */
	@Comment("Разрешающее правило")
	@Persist
	public Boolean getPermissionRule() {return thePermissionRule;}
	public void setPermissionRule(Boolean aPermissionRule) {thePermissionRule = aPermissionRule;}
	/** Разрешающее правило */
	private Boolean thePermissionRule;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	/** Поток обслуживания */
	private Long theServiceStream;
	
	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	/** Отделение */
	private Long theDepartment;
	
	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	@Persist
	public Long getDiagnosisRegistrationType() {return theDiagnosisRegistrationType;}
	public void setDiagnosisRegistrationType(Long aDiagnosisRegistrationType) {theDiagnosisRegistrationType = aDiagnosisRegistrationType;}
	/** Тип регистрации диагноза */
	private Long theDiagnosisRegistrationType;
	
	/** Приоритет диагноза */
	@Comment("Приоритет диагноза")
	@Persist
	public Long getDiagnosisPriority() {return theDiagnosisPriority;}
	public void setDiagnosisPriority(Long aDiagnosisPriority) {theDiagnosisPriority = aDiagnosisPriority;}
	/** Приоритет диагноза */
	private Long theDiagnosisPriority;
	
	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}
	/** Пол */
	private Long theSex;
}