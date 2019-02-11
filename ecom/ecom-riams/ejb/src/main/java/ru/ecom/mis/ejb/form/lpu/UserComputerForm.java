package ru.ecom.mis.ejb.form.lpu;

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
public class UserComputerForm  extends WorkPlaceForm {
	/** Адрес */
	@Comment("Адрес")
	@Persist
	public String getRemoteAddress() {return theRemoteAddress;}
	public void setRemoteAddress(String aRemoteAddress) {theRemoteAddress = aRemoteAddress;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public Long getUser() {return theUser;}
	public void setUser(Long aUser) {theUser = aUser;}

	/** Com порт */
	@Comment("Com порт")
	@Persist
	public String getComPort() {return theComPort;}
	public void setComPort(String aComPort) {theComPort = aComPort;}

	/** Динамический адрес */
	@Comment("Динамический адрес")
	@Persist
	public Boolean getDynamicIp() {return theDynamicIp;}
	public void setDynamicIp(Boolean aDynamicIp) {theDynamicIp = aDynamicIp;}

	/** Динамический адрес */
	private Boolean theDynamicIp;
	/** Com порт */
	private String theComPort;
	/** Пользователь */
	private Long theUser;
	/** Адрес */
	private String theRemoteAddress;
}
