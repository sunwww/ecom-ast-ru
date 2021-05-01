package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@AIndexes({
	   @AIndex(properties= {"patient"})
})
@Getter
@Setter
public class PatientListRecord extends BaseEntity{

	
	/** Список */
	private Long patientList;
	
	/** Пациент */
	private Long patient;
	
	/** Сообщение */
	private String message;

	/** Номер телефона */
	private String phoneNumber ;

}
