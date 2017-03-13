package ru.ecom.mis.ejb.domain.psychiatry;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocSocialStatus;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychSuicideNature;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocSuicideMesPlace;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocSuicideMesType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Извещение о суициде")
@Entity
@AIndexes({
	@AIndex(properties={"patient"})
})
@Table(schema="SQLUser")
public class SuicideMessage extends BaseEntity {
	/** Дата суицида */
	@Comment("Дата суицида")
	public Date getSuicideDate() {return theSuicideDate;}
	public void setSuicideDate(Date aSuicideDate) {theSuicideDate = aSuicideDate;}

	/** Дата заполения извещения */
	@Comment("Дата заполения извещения")
	public Date getRegOtherLpuDate() {return theRegOtherLpuDate;}
	public void setRegOtherLpuDate(Date aRegOtherLpuDate) {theRegOtherLpuDate = aRegOtherLpuDate;}

	/** Время заполнения извещения */
	@Comment("Время заполнения извещения")
	public Time getRegOtherLpuTime() {return theRegOtherLpuTime;}
	public void setRegOtherLpuTime(Time aRegOtherLpuTime) {theRegOtherLpuTime = aRegOtherLpuTime;}

	/** Дата регистрации */
	@Comment("Дата регистрации")
	public Date getRegDate() {return theRegDate;}
	public void setRegDate(Date aRegDate) {theRegDate = aRegDate;}

	/** Время регистрации */
	@Comment("Время регистрации")
	public Time getRegTime() {return theRegTime;}
	public void setRegTime(Time aRegTime) {theRegTime = aRegTime;}

	/** Извещение заполнено в ЛПУ */
	@Comment("Извещение заполнено в ЛПУ")
	@OneToOne
	public MisLpu getRegOtherLpu() {return theRegOtherLpu;}
	public void setRegOtherLpu(MisLpu aRegOtherLpu) {theRegOtherLpu = aRegOtherLpu;}

	/** Извещение заполнено в ЛПУ */
	private MisLpu theRegOtherLpu;
	/** Время регистрации */
	private Time theRegTime;
	/** Дата регистрации */
	private Date theRegDate;
	/** Время заполнения извещения */
	private Time theRegOtherLpuTime;
	/** Дата заполения извещения */
	private Date theRegOtherLpuDate;
	/** Дата суицида */
	private Date theSuicideDate;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {
		return theCreateDate;
	}

	public void setCreateDate(Date aCreateDate) {
		theCreateDate = aCreateDate;
	}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {
		return theEditDate;
	}

	public void setEditDate(Date aEditDate) {
		theEditDate = aEditDate;
	}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {
		return theCreateUsername;
	}

	public void setCreateUsername(String aCreateUsername) {
		theCreateUsername = aCreateUsername;
	}

	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	public String getEditUsername() {
		return theEditUsername;
	}

	public void setEditUsername(String aEditUsername) {
		theEditUsername = aEditUsername;
	}

	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {
		return theCreateTime;
	}

	public void setCreateTime(Time aCreateTime) {
		theCreateTime = aCreateTime;
	}

	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {
		return theEditTime;
	}

	public void setEditTime(Time aEditTime) {
		theEditTime = aEditTime;
	}

	/** Время редактрования */
	private Time theEditTime;

	/** Время создания */
	private Time theCreateTime;

	/** Пользователь, последний редактировавший запись */
	private String theEditUsername;

	/** Пользователь, создавший запись */
	private String theCreateUsername;

	/** Дата редактирования */
	private Date theEditDate;

	/** Дата создания */
	private Date theCreateDate;
	
	/** Представитель */
	@Comment("Представитель")
	public String getKinsman() {
		return theKinsman;
	}

	public void setKinsman(String aKinsman) {
		theKinsman = aKinsman;
	}

	/** Представитель */
	private String theKinsman;
	
	/** Телефон */
	@Comment("Телефон")
	public String getPhone() {
		return thePhone;
	}

	public void setPhone(String aPhone) {
		thePhone = aPhone;
	}

	/** Телефон */
	private String thePhone;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {
		return thePatient;
	}

	public void setPatient(Patient aPatient) {
		thePatient = aPatient;
	}

	/** Пациент */
	private Patient thePatient;
	
	/** Вид суицида */
	@Comment("Вид суицида")
	@OneToOne
	public VocSuicideMesType getType() {
		return theType;
	}

	public void setType(VocSuicideMesType aType) {
		theType = aType;
	}

	/** Вид суицида */
	private VocSuicideMesType theType;
	
	/** Другой вид суицида */
	@Comment("Другой вид суицида")
	public String getOtherType() {
		return theOtherType;
	}

	public void setOtherType(String aOtherType) {
		theOtherType = aOtherType;
	}

	/** Другой вид суицида */
	private String theOtherType;
	
	/** Место */
	@Comment("Место")
	@OneToOne
	public VocSuicideMesPlace getPlace() {
		return thePlace;
	}

	public void setPlace(VocSuicideMesPlace aPlace) {
		thePlace = aPlace;
	}
	
	/** Другое место суицида */
	@Comment("Другое место суицида")
	public String getOtherPlace() {
		return theOtherPlace;
	}

	public void setOtherPlace(String aOtherPlace) {
		theOtherPlace = aOtherPlace;
	}

	/** Другое место суицида */
	private String theOtherPlace;

	/** Место */
	private VocSuicideMesPlace thePlace;
	
	/** Присутствовали др. люди при суициде */
	@Comment("Присутствовали др. люди при суициде")
	@OneToOne
	public VocYesNo getOtherPeople() {
		return theOtherPeople;
	}

	public void setOtherPeople(VocYesNo aOtherPeople) {
		theOtherPeople = aOtherPeople;
	}

	/** Присутствовали др. люди при суициде */
	private VocYesNo theOtherPeople;
	
	/** Опьянение */
	@Comment("Опьянение")
	@OneToOne
	public VocYesNo getIntoxication() {
		return theIntoxication;
	}

	public void setIntoxication(VocYesNo aIntoxication) {
		theIntoxication = aIntoxication;
	}

	/** Опьянение */
	private VocYesNo theIntoxication;
	
	/** Направлен */
	@Comment("Направлен")
	@OneToOne
	public MisLpu getOrderLpu() {
		return theOrderLpu;
	}

	public void setOrderLpu(MisLpu aOrderLpu) {
		theOrderLpu = aOrderLpu;
	}

	/** Направлен */
	private MisLpu theOrderLpu;
	
	/** Доставлен */
	@Comment("Доставлен")
	@OneToOne
	public MisLpu getPostedLpu() {
		return thePostedLpu;
	}

	public void setPostedLpu(MisLpu aPostedLpu) {
		thePostedLpu = aPostedLpu;
	}

	/** Доставлен */
	private MisLpu thePostedLpu;
	
	/** Завершен */
	@Comment("Завершен")
	public Boolean getIsFinished() {
		return theIsFinished;
	}

	public void setIsFinished(Boolean aIsFinished) {
		theIsFinished = aIsFinished;
	}

	/** Завершен */
	private Boolean theIsFinished;
	
	/** Социальный статус */
	@Comment("Социальный статус")
	@OneToOne
	public VocSocialStatus getSocialStatus() {return theSocialStatus;}
	public void setSocialStatus(VocSocialStatus aSocialStatus) {theSocialStatus = aSocialStatus;}

	/** Социальный статус */
	private VocSocialStatus theSocialStatus;
	
	/** Мед.помощь оказано СМП */
	@Comment("Мед.помощь оказано СМП")
	@OneToOne
	public VocYesNo getHelpSMP() {return theHelpSMP;}
	public void setHelpSMP(VocYesNo aHelpSMP) {theHelpSMP = aHelpSMP;}

	/** Мед.помощь оказано СМП */
	private VocYesNo theHelpSMP;
 	/** Характер суицида */
	@Comment("Характер суицида")
	@OneToOne
	public VocPsychSuicideNature getNature() {
		return theNature;
	}

	public void setNature(VocPsychSuicideNature aNature) {
		theNature = aNature;
	}
	/** Характер суицида */
	private VocPsychSuicideNature theNature;
	
	/**
	  * Описание
	  */
	 @Comment("Описание")
	 public String getNotes() {
	  return theNotes;
	 }
	 public void setNotes(String aNotes) {
	  theNotes = aNotes;
	 }
	 /**
	  * Описание
	  */
	 private String theNotes;
}
