package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKindHighCare;
import ru.ecom.mis.ejb.domain.medcase.voc.VocMethodHighCare;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Случай оказания высокотехнологичной мед. помощи (ВМП)")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="medCase")
}) 
public class HitechMedicalCase extends BaseEntity {

	/** Случай оказания мед. помощи */
	@Comment("Случай оказания мед. помощи")
	@ManyToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}
	/** Случай оказания мед. помощи */
	private MedCase theMedCase;
	
	/** Вид ВМП */
	@Comment("Вид ВМП")
	@OneToOne
	public VocKindHighCare getKind() {return theKind;}
	public void setKind(VocKindHighCare aKind) {theKind = aKind;}
	/** Вид ВМП */
	private VocKindHighCare theKind;
	
	/** Метод ВМП */
	@Comment("Метод ВМП")
	@OneToOne
	public VocMethodHighCare getMethod() {return theMethod;}
	public void setMethod(VocMethodHighCare aMethod) {theMethod = aMethod;}
	/** Метод ВМП */
	private VocMethodHighCare theMethod;
	
	/** Дата выдачи талона нв ВМП */
	@Comment("Дата выдачи талона нв ВМП")
	public Date getTicketDate() {return theTicketDate;}
	public void setTicketDate(Date aTicketDate) {theTicketDate = aTicketDate;}
	/** Дата выдачи талона нв ВМП */
	private Date theTicketDate;
	
	/** Дата планируемой госпитализации */
	@Comment("Дата планируемой госпитализации")
	public Date getPlanHospDate() {return thePlanHospDate;}
	public void setPlanHospDate(Date aPlanHospDate) {thePlanHospDate = aPlanHospDate;}
	/** Дата планируемой госпитализации */
	private Date thePlanHospDate;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	/** Дата создания */
	private Date theCreateDate;
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	/** Дата редактирования */
	private Date theEditDate;
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	
	/** Пользователь, редактировавший запись */
	@Comment("Пользователь, редактировавший запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
	/** Пользователь, редактировавший запись */
	private String theEditUsername;

	/** Количество установленных стентов */
	@Comment("Количество установленных стентов")
	public Long getStantAmount() {return theStantAmount;}
	public void setStantAmount(Long aStantAmount) {theStantAmount = aStantAmount;}
	/** Количество установленных стентов */
	private Long theStantAmount ;

	/** Номер талона ВМП */
	@Comment("Номер талона ВМП")
	public String getTicketNumber() {return theTicketNumber;}
	public void setTicketNumber(String aTicketNumber) {theTicketNumber = aTicketNumber;}
	/** Номер талона ВМП */
	private String theTicketNumber ;

	
}
