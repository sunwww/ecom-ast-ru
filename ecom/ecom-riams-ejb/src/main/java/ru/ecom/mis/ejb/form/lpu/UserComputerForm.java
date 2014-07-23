package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.lpu.UserComputer;
import ru.ecom.mis.ejb.domain.lpu.WorkPlace;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Пользовательский компьютер")
@EntityForm
@EntityFormPersistance(clazz = UserComputer.class)
@WebTrail(comment = "Раб. место", nameProperties = "name", view = "entitySubclassView-mis_userComp.do")
@Parent(property = "parent", parentForm = FloorBuildingForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace")
public class UserComputerForm  extends WorkPlaceForm {
	/** Адрес */
	@Comment("Адрес")
	@Persist
	public String getRemoteAddress() {return theRemoteAddress;}
	public void setRemoteAddress(String aRemoteAddress) {theRemoteAddress = aRemoteAddress;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public SecUser getUser() {return theUser;}
	public void setUser(SecUser aUser) {theUser = aUser;}

	/** Пользователь */
	private SecUser theUser;
	/** Адрес */
	private String theRemoteAddress;
}
