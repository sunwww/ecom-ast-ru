package ru.ecom.mis.web.action.medcase.journal;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
/**
 * Поиск по отделению
 * @author stkacheva
 *
 */
public class DepartmentJournalForm extends BaseValidatorForm{
	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Отделение */
	private Long theDepartment;
}
