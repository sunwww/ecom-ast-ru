package ru.ecom.mis.ejb.domain.prescription;

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
public class DrugPrescriptionByTicket extends DrugPrescription {
	/** Протокол */
	@Comment("Протокол")
	@OneToOne 
	public Diary getDiary() {return theDiary;}
	public void setDiary(Diary aDiary) {theDiary = aDiary;}

	/** Протокол */
	private Diary theDiary;
	
	/** Кол-во строковое */
	@Comment("Кол-во строковое")
	public String getAmountString() {
		return theAmountString;
	}

	public void setAmountString(String aAmountString) {
		theAmountString = aAmountString;
	}

	/** Кол-во строковое */
	private String theAmountString;
	
	/** Номер */
	@Comment("Номер")
	public String getNumberPrescript() {
		return theNumberPrescript;
	}

	public void setNumberPrescript(String aNumberPrescript) {
		theNumberPrescript = aNumberPrescript;
	}

	/** Номер */
	private String theNumberPrescript;
}
