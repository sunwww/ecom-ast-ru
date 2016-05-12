package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import javax.persistence.Entity;

@Entity
public class LpuDiagnosisRule  extends BaseEntity{
	
	/** Разрешающее правило */
	@Comment("Разрешающее правило")
	 
	public Boolean getPermissionRule() {return thePermissionRule;}
	public void setPermissionRule(Boolean aPermissionRule) {thePermissionRule = aPermissionRule;}
	/** Разрешающее правило */
	private Boolean thePermissionRule;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	 
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}
	/** Поток обслуживания */
	private Long theServiceStream;
	
	/** Отделение */
	@Comment("Отделение")
	 
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	/** Отделение */
	private Long theDepartment;
	
	/** Тип регистрации диагноза */
	@Comment("Тип регистрации диагноза")
	 
	public Long getDiagnosisRegistrationType() {return theDiagnosisRegistrationType;}
	public void setDiagnosisRegistrationType(Long aDiagnosisRegistrationType) {theDiagnosisRegistrationType = aDiagnosisRegistrationType;}
	/** Тип регистрации диагноза */
	private Long theDiagnosisRegistrationType;
	
	/** Приоритет диагноза */
	@Comment("Приоритет диагноза")
	 
	public Long getDiagnosisPriority() {return theDiagnosisPriority;}
	public void setDiagnosisPriority(Long aDiagnosisPriority) {theDiagnosisPriority = aDiagnosisPriority;}
	/** Приоритет диагноза */
	private Long theDiagnosisPriority;
	
	/** Пол */
	@Comment("Пол")
	 
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}
	/** Пол */
	private Long theSex;
}
