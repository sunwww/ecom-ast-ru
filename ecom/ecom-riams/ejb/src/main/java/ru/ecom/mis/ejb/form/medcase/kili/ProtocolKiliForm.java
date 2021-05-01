package ru.ecom.mis.ejb.form.medcase.kili;


/* import com.sun.java.swing.plaf.windows.XPStyle.Prop; */

import lombok.Setter;
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
@Setter
public class ProtocolKiliForm extends IdEntityForm {
	/** Список дефектов (текст) */
	@Comment("Список дефектов (текст)")
	public String getDefectSaveList() {return defectSaveList;}
	/** Список дефектов (текст) */
	private String defectSaveList;
	
	/** ID протокола */
	@Comment("ID протокола")
	public Long getIDProtocol() {return iDProtocol;}
	/** ID протокола */
	private Long iDProtocol;

	/** Случай смерти */
	@Comment("Случай смерти")
	@Persist
	public Long getDeathCase() {return deathCase;}
	/** Случай смерти */
	private Long deathCase;
	
	/** Дата проведения КИЛИ */
	@Comment("Дата проведения КИЛИ")
	@DoDateString @DateString
	@Persist @Required
	public String getProtocolDate() {return protocolDate;}
	private String protocolDate;
	
	/** Номер протокола */
	@Comment("Номер протокола")
	@Persist @Required
	public String getProtocolNumber() {return protocolNumber;}
	private String protocolNumber;

	/** Решение КИЛИ */
	@Comment("Решение КИЛИ")
	@Persist @Required
	public Long getConclusion() {return conclusion;}
	private Long conclusion;
	
	/** Дата создания */
	@Comment("Дата создания")
	@DoDateString @DateString
	@Persist
	public String getCreateDate() {return createDate;}
	private String createDate;

	/** Время создания */
	@Comment("Время создания")
	@DoTimeString @TimeString
	@Persist
	public String getCreateTime() {return createTime;}
	private String createTime;

	/** Создано пользователем */
	@Comment("Создано пользователем")
	@Persist
	public String getCreateUsername() {return createUsername;}
	private String createUsername;
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DoDateString @DateString
	@Persist
	public String getEditDate() {return editDate;}
	private String editDate;

	/** Время редактирования */
	@Comment("Время редактирования")
	@DoTimeString @TimeString
	@Persist
	public String getEditTime() {return editTime;}
	private String editTime;

	/** Редактирование пользователем */
	@Comment("Редактирование пользователем")
	@Persist
	public String getEditUsername() {return editUsername;}
	private String editUsername;
	
	/** Примечание */
	@Comment("Примечание") 
	@Persist
	public String getProtocolComment() {return protocolComment;}
	private String protocolComment;
}