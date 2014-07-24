package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.WorkPlace;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Раб. место")
@EntityForm
@EntityFormPersistance(clazz = WorkPlace.class)
@WebTrail(comment = "Раб. место", nameProperties = "name", view = "entitySubclassView-mis_workPlace.do")
@Parent(property = "parent", parentForm = WorkPlaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace")
@Subclasses(value = { ConsultingRoomForm.class, HospitalRoomForm.class
		,OperatingRoomForm.class,BuildingPlaceForm.class, FloorBuildingForm.class,UserComputerForm.class })
public class WorkPlaceForm extends IdEntityForm {
	/** Название */
	@Comment("Название")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return theParent;}
	public void setParent(Long aParent) {theParent = aParent;}


	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Не актуален */
	@Comment("Неактуален")
	@Persist
	public Boolean getIsNoActuality() {return theIsNoActuality;}
	public void setIsNoActuality(Boolean aIsNoActuality) {theIsNoActuality = aIsNoActuality;}

	/**
	 * Номер телефона
	 */
	@Comment("Номер телефона")
	@Persist
	public String getPhoneNumber() {return thePhoneNumber;}
	public void setPhoneNumber(String aPhoneNumber) {thePhoneNumber = aPhoneNumber;}
	/**
	 * Номер телефона
	 */
	private String thePhoneNumber;
	/** Неактуален */
	private Boolean theIsNoActuality;
	/** Комментарий */
	private String theComment;
	/** Родитель */
	private Long theParent;
	/** Лечебное учреждение */
	private Long theLpu;
	/** Название */
	private String theName;

}
