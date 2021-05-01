package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class MedCaseMedPolicy extends BaseEntity{

	private MedPolicy policies;
	private MedCase medCase;
	private Date dateCheck;

	@Comment("Случай медицинского обслуживания")
	@ManyToOne
	public MedCase getMedCase() {return medCase;}

	@Comment("Медицинский полис")
	@OneToOne
	public MedPolicy getPolicies() {
		return policies;
	}
	/** Ручная проверка актуальности полиса */
	private Boolean isManualSync ;
}
