package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class PatientFondCheckData extends BaseEntity {
	
	/** Необходимо автоматически обновлять данные пациента */
	private Boolean  needUpdatePatient;
	
	/** Необходимо обновлять данные паспорта */
	private Boolean needUpdateDocument;
	
	/** Необходимо обновлять данные полиса */
	private Boolean needUpdatePolicy;
	
	/** Необходимо обновлять данные прикрепления */
	private Boolean needUpdateAttachment;

	/** Статус(текст) */
	private String statusText;
	
	/** Комментарий */
	private String comment;
	
	/** Дата начала импорта */
	private Date startDate;
	

	/** Дата окончания импорта */
	private Date finishDate;

	/** Список запией по проверке */
	@Comment("Список запией по проверке")
	@OneToMany
	public List<PatientFond> getPatientList() {return patientList;}
	/** Список запией по проверке */
	private List<PatientFond> patientList;
}
