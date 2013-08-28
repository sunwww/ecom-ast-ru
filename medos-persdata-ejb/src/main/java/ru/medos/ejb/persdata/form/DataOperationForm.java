package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.DataOperation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = DataOperation.class)
@Comment("Операция с данными (CRUD)+P(purge)")
@WebTrail(comment = "Операция с данными (CRUD)+P(purge)", nameProperties= "id", list="entityParentList-personaldata_dataOperation.do", view="entityParentView-personaldata_dataOperation.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class DataOperationForm extends IdEntityForm{
	/**
	 * Изменения данных
	 */
	@Comment("Изменения данных")
	@Persist
	public String getChanges() {
		return theChanges;
	}
	public void setChanges(String aChanges) {
		theChanges = aChanges;
	}
	/**
	 * Изменения данных
	 */
	private String theChanges;
	/**
	 * Пользователь
	 */
	@Comment("Пользователь")
	@Persist
	public Long getUser() {
		return theUser;
	}
	public void setUser(Long aUser) {
		theUser = aUser;
	}
	/**
	 * Пользователь
	 */
	private Long theUser;
	/**
	 * Имя класса данных
	 */
	@Comment("Имя класса данных")
	@Persist
	public String getClassName() {
		return theClassName;
	}
	public void setClassName(String aClassName) {
		theClassName = aClassName;
	}
	/**
	 * Имя класса данных
	 */
	private String theClassName;
	/**
	 * ИД данных
	 */
	@Comment("ИД данных")
	@Persist
	public Long getDataId() {
		return theDataId;
	}
	public void setDataId(Long aDataId) {
		theDataId = aDataId;
	}
	/**
	 * ИД данных
	 */
	private Long theDataId;
	/**
	 * Дата операции
	 */
	@Comment("Дата операции")
	@Persist
	@DateString @DoDateString
	public String getOperationDate() {
		return theOperationDate;
	}
	public void setOperationDate(String aOperationDate) {
		theOperationDate = aOperationDate;
	}
	/**
	 * Дата операции
	 */
	private String theOperationDate;
	/**
	 * Время операции
	 */
	@Comment("Время операции")
	@Persist
	@TimeString @DoTimeString
	public String getOperationTime() {
		return theOperationTime;
	}
	public void setOperationTime(String aOperationTime) {
		theOperationTime = aOperationTime;
	}
	/**
	 * Время операции
	 */
	private String theOperationTime;
}
