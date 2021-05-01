package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@AIndexes({
  @AIndex(properties="diary", table="Prescription")
})
@Getter
@Setter
public class DrugPrescriptionByTicket extends DrugPrescription {
	/** Протокол */
	@Comment("Протокол")
	@OneToOne 
	public Diary getDiary() {return diary;}

	/** Протокол */
	private Diary diary;
	
	/** Кол-во строковое */
	private String amountString;

	/** Номер */
	private String numberPrescript;
}
