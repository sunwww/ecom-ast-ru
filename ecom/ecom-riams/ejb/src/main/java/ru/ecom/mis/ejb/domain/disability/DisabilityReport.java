package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Медико-социальная экспертная комиссия")
@Entity
@AIndexes({
	@AIndex(unique = false, properties= {"lineR"})
	,@AIndex(unique = false, properties= {"caseR"})
})
@Table(schema="SQLUser")
public class DisabilityReport extends BaseEntity {
	/** Случай нетрудоспособности */
	@Comment("Случай нетрудоспособности")
	public Long getCaseR() {
		return theCaseR;
	}

	public void setCaseR(Long aCase) {
		theCaseR = aCase;
	}

	/** Случай нетрудоспособности */
	private Long theCaseR;
	
	/** Строка */
	@Comment("Строка")
	public Long getLineR() {
		return theLineR;
	}

	public void setLineR(Long aLineR) {
		theLineR = aLineR;
	}

	/** Строка */
	private Long theLineR;
	
	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getFinishDate() {
		return theFinishDate;
	}

	public void setFinishDate(Date aFinishDate) {
		theFinishDate = aFinishDate;
	}

	/** Дата окончания */
	private Date theFinishDate;
}
