package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.UserComputer;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Пользовательский компьютер")
@EntityForm
@EntityFormPersistance(clazz = UserComputer.class)
@WebTrail(comment = "Раб. место", nameProperties = "name", view = "entitySubclassView-mis_userComp.do")
@Parent(property = "parent", parentForm = WorkPlaceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/WorkPlace/UserComputer")
@Setter
public class UserComputerForm  extends WorkPlaceForm {
	/** Адрес */
	@Comment("Адрес")
	@Persist
	public String getRemoteAddress() {return remoteAddress;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public Long getUser() {return user;}

	/** Com порт */
	@Comment("Com порт")
	@Persist
	public String getComPort() {return comPort;}

	/** Динамический адрес */
	@Comment("Динамический адрес")
	@Persist
	public Boolean getDynamicIp() {return dynamicIp;}

	/** Динамический адрес */
	private Boolean dynamicIp;
	/** Com порт */
	private String comPort;
	/** Пользователь */
	private Long user;
	/** Адрес */
	private String remoteAddress;
}
