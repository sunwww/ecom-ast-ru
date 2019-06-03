package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Table(name="MedCase_MedPolicy",schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "medCase" }) })
public class MedCaseMedPolicy extends BaseEntity{

	private MedPolicy thePolicies;
	private MedCase theMedCase;
	private Date dateCheck;

	@Comment("Случай медицинского обслуживания")
	@ManyToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	@Comment("Медицинский полис")
	@OneToOne
	public MedPolicy getPolicies() {
		return thePolicies;
	}
	public void setPolicies(MedPolicy aMedPolicy) {
		thePolicies = aMedPolicy;
	}

	@Comment("Дата проверки актуальности полиса")
	public Date getDateSync() {
		return dateCheck;
	}
	public void setDateSync(Date dateSync) {
		this.dateCheck = dateSync;
	}

	/** Ручная проверка актуальности полиса */
	@Comment("Ручная проверка актуальности полиса")
	public Boolean getIsManualSync() {return theIsManualSync;}
	public void setIsManualSync(Boolean aIsManualSync) {theIsManualSync = aIsManualSync;}
	/** Ручная проверка актуальности полиса */
	private Boolean theIsManualSync ;
}
