package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
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
@Setter

public class LpuDiagnosisRuleForm extends IdEntityForm {
	
	/** Разрешающее правило */
	@Comment("Разрешающее правило")
	@Persist
	public Boolean getPermissionRule() {return permissionRule;}
	/** Разрешающее правило */
	private Boolean permissionRule;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist
	public Long getServiceStream() {return serviceStream;}
	/** Поток обслуживания */
	private Long serviceStream;
	
	/** Отделение */
	@Comment("Отделение")
	@Persist
	public Long getDepartment() {return department;}
	/** Отделение */
	private Long department;
	
	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	@Persist
	public Long getDiagnosisRegistrationType() {return diagnosisRegistrationType;}
	private Long diagnosisRegistrationType;
	
	/** Приоритет диагноза */
	@Comment("Приоритет диагноза")
	@Persist
	public Long getDiagnosisPriority() {return diagnosisPriority;}
	private Long diagnosisPriority;
	
	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return sex;}
	private Long sex;
}