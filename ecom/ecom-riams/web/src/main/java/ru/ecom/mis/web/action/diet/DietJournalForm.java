package ru.ecom.mis.web.action.diet;

import ru.ecom.mis.web.action.medcase.PrescriptionForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class DietJournalForm extends PrescriptionForm {
  
		/** Диета */
	@Comment("Диета")
	public Long getDiet() {return theDiet;}
	public void setDiet(Long aDiet) {theDiet = aDiet;}
	/** Диета */
	private Long theDiet;
}
