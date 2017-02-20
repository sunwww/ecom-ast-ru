package ru.ecom.mis.ejb.form.medcase.kili;


/* import com.sun.java.swing.plaf.windows.XPStyle.Prop; */

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.kili.ProtocolKili;
import ru.ecom.mis.ejb.form.medcase.death.DeathCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = ProtocolKili.class)
@Comment("Протокол решения КИЛИ")
@WebTrail(comment = "Протокол решения КИЛИ", nameProperties= "id", view="entityParentView-mis_protocolKili.do")
@Parent(property="deathCase", parentForm=DeathCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/ProtocolKili")

public class ProtocolKiliForm extends IdEntityForm {
	/** Список дефектов (текст) */
	@Comment("Список дефектов (текст)")
	public String getDefectSaveList() {return theDefectSaveList;}
	public void setDefectSaveList(String aDefectSaveList) {theDefectSaveList = aDefectSaveList;}
	/** Список дефектов (текст) */
	private String theDefectSaveList;
	
	/** ID протокола */
	@Comment("ID протокола")
	public Long getIDProtocol() {return theIDProtocol;}
	public void setIDProtocol(Long aIDProtocol) {theIDProtocol = aIDProtocol;}
	/** ID протокола */
	private Long theIDProtocol;
	
	/** Профиль 
	@Comment("Профиль")
	@Persist @Required
	public Long getProfile() {return theProfile;}
	public void setProfile(Long aProfile) {theProfile = aProfile;}
	*/
	/** Профиль 
	private Long theProfile;
	*/
	
	/** Случай смерти */
	@Comment("Случай смерти")
	@Persist
	public Long getDeathCase() {return theDeathCase;}
	public void setDeathCase(Long aDeathCase) {theDeathCase = aDeathCase;}
	/** Случай смерти */
	private Long theDeathCase;
	
	/** Дата проведения КИЛИ */
	@Comment("Дата проведения КИЛИ")
	@DoDateString @DateString
	@Persist @Required
	public String getProtocolDate() {return theProtocolDate;}
	public void setProtocolDate(String aProtocolDate) {theProtocolDate = aProtocolDate;}
	/** Дата проведения КИЛИ */
	private String theProtocolDate;
	
	/** Номер протокола */
	@Comment("Номер протокола")
	@Persist @Required
	public String getProtocolNumber() {return theProtocolNumber;}
	public void setProtocolNumber(String aProtocolNumber) {theProtocolNumber = aProtocolNumber;}
	/** Номер протокола */
	private String theProtocolNumber;

	/** Решение КИЛИ */
	@Comment("Решение КИЛИ")
	@Persist @Required
	public Long getConclusion() {return theConclusion;}
	public void setConclusion(Long aConclusion) {theConclusion = aConclusion;}
	/** Решение КИЛИ */
	private Long theConclusion;
	
	/** Дата создания */
	@Comment("Дата создания")
	@DoDateString @DateString
	@Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата создания */
	private String theCreateDate;

	/** Время создания */
	@Comment("Время создания")
	@DoTimeString @TimeString
	@Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время создания */
	private String theCreateTime;

	/** Создано пользователем */
	@Comment("Создано пользователем")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Создано пользователем */
	private String theCreateUsername;
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DoDateString @DateString
	@Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	/** Дата редактирования */
	private String theEditDate;

	/** Время редактирования */
	@Comment("Время редактирования")
	@DoTimeString @TimeString
	@Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Время редактирования */
	private String theEditTime;

	/** Редактирование пользователем */
	@Comment("Редактирование пользователем")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
	/** Редактирование пользователем */
	private String theEditUsername;
	
	/** Примечание */
	@Comment("Примечание") 
	@Persist
	public String getProtocolComment() {return theProtocolComment;}
	public void setProtocolComment(String aProtocolComment) {theProtocolComment = aProtocolComment;}
	/** Примечание */
	private String theProtocolComment;
}