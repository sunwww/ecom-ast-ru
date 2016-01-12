package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class LpuContractNosologyGroup extends BaseEntity {
	
	/** Правило диагноза в отделении */
	@Comment("Правило диагноза в отделении")
	public Long getLpuDiagnosisRule() {return theLpuDiagnosisRule;}
	public void setLpuDiagnosisRule(Long aLpuDiagnosisRule) {theLpuDiagnosisRule = aLpuDiagnosisRule;}
	/** Правило диагноза в отделении */
	private Long theLpuDiagnosisRule;
	
	/** Нозологическая группа */
	@Comment("Нозологическая группа")
	public Long getNosologyGroup() {return theNosologyGroup;}
	public void setNosologyGroup(Long aNosologyGroup) {theNosologyGroup = aNosologyGroup;}
	/** Нозологическая группа */
	private Long theNosologyGroup;

}
 	