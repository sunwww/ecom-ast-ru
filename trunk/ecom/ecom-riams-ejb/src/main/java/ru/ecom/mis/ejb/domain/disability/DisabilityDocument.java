package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentCloseReason;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentPrimarity;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentType;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityReason;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityReason2;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityRegime;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityStatus;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.worker.voc.VocCombo;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Документ нетрудоспособности
 * @author azviagin,stkacheva,dpetinov
 *
 */
@Comment("Документ нетрудоспособности")
@Entity
@AIndexes({
	@AIndex(unique = false, properties= {"disabilityCase"})
	,@AIndex(unique = false, properties= {"issueDate"})
	,@AIndex(unique = false, properties= {"mainWorkDocumentNumber"})
	,@AIndex(unique = false, properties= {"idc10Final"})
	,@AIndex(unique = false, properties= {"patient"})
	,@AIndex(unique = false , properties = {"duplicate"})
	,@AIndex(unique = false, properties = {"prevDocument"})
})
@Table(schema="SQLUser")
public class DisabilityDocument extends BaseEntity{
	
	/** Медико-социальная экспертная комиссия */
	@Comment("Медико-социальная экспертная комиссия")
	@OneToOne
	public MedSocCommission getMedSocCommission() {return theMedSocCommission;}
	public void setMedSocCommission(MedSocCommission aMedSocCommission) {theMedSocCommission = aMedSocCommission;}

	/** Случай нетрудоспособности */
	@Comment("Случай нетрудоспособности")
	@ManyToOne
	public DisabilityCase getDisabilityCase() {return theDisabilityCase;}
	public void setDisabilityCase(DisabilityCase aDisabilityCase) {theDisabilityCase = aDisabilityCase;}

	/** Серия */
	@Comment("Серия")
	public String getSeries() {return theSeries;}
	public void setSeries (String aCloseSeries) {theSeries = aCloseSeries;}
	
	/** Номер */
	@Comment("Номер")
	public String getNumber() {return theNumber;}
	public void setNumber(String aNumber) {theNumber = aNumber;}
	
	/** Причина нетрудоспособности */
	@Comment("Причина нетрудоспособности")
	@OneToOne
	public VocDisabilityReason getDisabilityReason() {return theDisabilityReason;}
	public void setDisabilityReason(VocDisabilityReason aDisabilityReason) {theDisabilityReason = aDisabilityReason;}
	
	/** Причина закрытия */
	@Comment("Причина закрытия")
	@OneToOne
	public VocDisabilityDocumentCloseReason getCloseReason() {return theCloseReason;}
	public void setCloseReason(VocDisabilityDocumentCloseReason aCloseReason) {theCloseReason = aCloseReason;}
	
	/** Дата начала работы */
	@Comment("Дата начала работы")
	public Date getBeginWorkDate() {return theBeginWorkDate;}
	public void setBeginWorkDate(Date aBeginWorkDate) {theBeginWorkDate = aBeginWorkDate;}
		
	/** Записи сроков нетрудоспособности */
	@Comment("Записи сроков нетрудоспособности")
	@OneToMany(mappedBy="disabilityDocument", cascade=CascadeType.ALL)
	public List<DisabilityRecord> getDisabilityRecords() {return theDisabilityRecords;}
	public void setDisabilityRecords(List<DisabilityRecord> aDisabilityRecords) {theDisabilityRecords = aDisabilityRecords;}
	
	/** Признак недействительности документа */
	@Comment("Признак недействительности документа")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}
	
	/** Записи нарушения режима */
	@Comment("Записи нарушения режима")
	@OneToMany(mappedBy="disabilityDocument", cascade=CascadeType.ALL)
	public List<RegimeViolationRecord> getRegimeViolationRecords() {return theRegimeViolationRecords;}
	public void setRegimeViolationRecords(List<RegimeViolationRecord> aRegimeViolationRecords) {theRegimeViolationRecords = aRegimeViolationRecords;}

	/** Тип документа нетрудоспособности */
	@Comment("Тип документа нетрудоспособности")
	@OneToOne
	public VocDisabilityDocumentType getDocumentType() {return theDocumentType;}
	public void setDocumentType(VocDisabilityDocumentType aDocumentType) {theDocumentType = aDocumentType;}
	
	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности")
	@OneToOne
	public VocDisabilityRegime getDisabilityRegime() {return theDisabilityRegime;}
	public void setDisabilityRegime(VocDisabilityRegime aDisabilityRegime) {theDisabilityRegime = aDisabilityRegime;}

	/** Первичность */
	@Comment("Первичность")
	@OneToOne
	public VocDisabilityDocumentPrimarity getPrimarity() {return thePrimarity;}
	public void setPrimarity(VocDisabilityDocumentPrimarity aPrimarity) {thePrimarity = aPrimarity;}

	/** Дата выдачи */
	@Comment("Дата выдачи")
	public Date getIssueDate() {return theIssueDate;}
	public void setIssueDate(Date aIssueDate) {theIssueDate = aIssueDate;}

	
	/** Разрешение на выдачу документа нетрудоспособности иногороднему */
	@Comment("Разрешенение на выдачу документа нетрудоспособности иногороднему")
	@OneToOne
	public DisabilityPermission getPermission() {return thePermission;}
	public void setPermission(DisabilityPermission aOpenPermission) {thePermission = aOpenPermission;}

	/** Серия документа нетрудоспособности по основному месту работы */
	@Comment("Серия документа нетрудоспособности по основному месту работы")
	public String getMainWorkDocumentSeries() {return theMainWorkDocumentSeries;}
	public void setMainWorkDocumentSeries(String aSeries) {theMainWorkDocumentSeries = aSeries;}
	
	/** Номер документа нетрудоспособности по основному месту работы */
	@Comment("Номер документа нетрудоспособности по основному месту работы")
	public String getMainWorkDocumentNumber() {return theMainWorkDocumentNumber;}
	public void setMainWorkDocumentNumber(String aMainWorkDisabilityDocument) {theMainWorkDocumentNumber = aMainWorkDisabilityDocument;}

	/** Предполагаемая дата родов */
	@Comment("Предполагаемая дата родов")
	public Date getSupposeBirthDate() {return theSupposeBirthDate;}
	public void setSupposeBirthDate(Date aSupposeBirthDate) {theSupposeBirthDate = aSupposeBirthDate;}

	
	/** Дата начала санаторного лечения */
	@Comment("Дата начала санаторного лечения")
	public Date getSanatoriumDateFrom() {return theSanatoriumDateFrom;}
	public void setSanatoriumDateFrom(Date aSanatoriumDateFrom) {theSanatoriumDateFrom = aSanatoriumDateFrom;}

	
	/** Дата окончания санаторного лечения */
	@Comment("Дата окончания санаторного лечения")
	public Date getSanatoriumDateTo() {return theSanatoriumDateTo;}
	public void setSanatoriumDateTo(Date aSanatoriumDateTo) {theSanatoriumDateTo = aSanatoriumDateTo;}

	/** Номер санаторной путевки */
	@Comment("Номер санаторной путевки")
	public String getSanatoriumTicketNumber() {return theSanatoriumTicketNumber;}
	public void setSanatoriumTicketNumber(String aSanatoriumTicketNumber) {theSanatoriumTicketNumber = aSanatoriumTicketNumber;}

	
	/** Место нахождения санатория */
	@Comment("Место нахождения санатория")
	public String getSanatoriumPlace() {return theSanatoriumPlace;}
	public void setSanatoriumPlace(String aSanatoriumPlace) {theSanatoriumPlace = aSanatoriumPlace;}

	
	/** МКБ10*/
	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aNewProperty) {theIdc10 = aNewProperty;	}

	/** Справочник видов совмещения работ */
	@Comment("Справочник видов совмещения работ")
	@OneToOne
	public VocCombo getWorkComboType() {return theWorkComboType;}
	public void setWorkComboType(VocCombo aCombo) {theWorkComboType = aCombo;}

	/** Больной по уходу */
	@Comment("Больной по уходу")
	@OneToOne
	public Patient getNursedPatient() {return theNursedPatient;}
	public void setNursedPatient(Patient aNursedPatient) {theNursedPatient = aNursedPatient;}

	/** Место работы */
	@Comment("Место работы")
	@OneToOne
	public VocOrg getWorks() {return theWorks;}
	public void setWorks(VocOrg aWorks) {theWorks = aWorks;}

	/** Диагноз текст */
	@Comment("Диагноз текст")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;	}

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	//// Вычисляемые поля 
	/** Дата начала */
	@Comment("Дата начала")
	@Transient
	public Date getDateFrom() {
		return getDisabilityRecords().isEmpty() 
		? null 
		: getDisabilityRecords().get(0).getDateFrom(); 
	}


	/** Дата окончания */
	@Comment("Дата окончания")
	@Transient
	public Date getDateTo() {
		return getDisabilityRecords().isEmpty() 
			? null 
			: getDisabilityRecords().get(getDisabilityRecords().size()-1).getDateTo(); 
	}
	/** Первичность (текст) */
	@Transient
	@Comment("Первичность (текст)")
	public String getPrimarityInfo() {
		return thePrimarity!=null? thePrimarity.getName(): "" ;
	}
	
	@Transient
	@Comment("Информация о документе")
	public String getInfo() {
		return new StringBuilder().append("серия ").append(theSeries).append(" номер ").append(theNumber).toString() ;
	}
	
	/** Тип документа инфо */
	@Transient 
	public String getDocumentTypeInfo() {
		return theDocumentType!=null?theDocumentType.getName():"" ;
	}
	
	/** Пациент ФИО */
	@Comment("Пациент ФИО")
	@Transient
	public String getPatientFio() {return thePatient!=null?thePatient.getFio():"";}
	
	/** Пациент ФИО */
	@Comment("Пациент ФИО")
	@Transient
	public String getPatientAddress() {return thePatient!=null?thePatient.getAddressRegistration():"";}


	/** Лечебно-профилактическое учреждение */
	@Comment("Лечебно-профилактическое учреждение")
	@OneToOne
	public MisLpu getAnotherLpu() {return theAnotherLpu;}
	public void setAnotherLpu(MisLpu aAnotherLpu) {theAnotherLpu = aAnotherLpu;}

	/** Предыдущий документ */
	@Comment("Предыдущий документ")
	@OneToOne
	public DisabilityDocument getPrevDocument() {return thePrevDocument;}
	public void setPrevDocument(DisabilityDocument aPrevDocument) {thePrevDocument = aPrevDocument;}

	/** Закрыт случай */
	@Comment("Закрыт случай")
	public Boolean getIsClose() {return theIsClose;}
	public void setIsClose(Boolean aIsClose) {theIsClose = aIsClose;}

	/**
	 * Дополнительная причина нетрудоспособности
	 */
	@Comment("Дополнительная причина нетрудоспособности")
	@OneToOne
	public VocDisabilityReason2 getDisabilityReason2() {return theDisabilityReason2;}
	public void setDisabilityReason2(VocDisabilityReason2 aDisabilityReason2) {theDisabilityReason2 = aDisabilityReason2;}
	/**
	 * Дополнительная причина нетрудоспособности
	 */
	private VocDisabilityReason2 theDisabilityReason2;
	
	/**
	 * Дата начала госпитализации
	 */
	@Comment("Дата начала госпитализации")
	public Date getHospitalizedFrom() {return theHospitalizedFrom;}
	public void setHospitalizedFrom(Date aHospitalizedFrom) {theHospitalizedFrom = aHospitalizedFrom;}
	/**
	 * Дата начала госпитализации
	 */
	private Date theHospitalizedFrom;
	/**
	 * Дата окончания госпитализации
	 */
	@Comment("Дата окончания госпитализации")	
	public Date getHospitalizedTo() {return theHospitalizedTo;}
	public void setHospitalizedTo(Date aHospitalizedTo) {theHospitalizedTo = aHospitalizedTo;}
	/**
	 * Дата окончания госпитализации
	 */
	private Date theHospitalizedTo;
	/**
	 * ОГРН санатория или клиники НИИ
	 */
	@Comment("ОГРН санатория или клиники НИИ")	
	public String getSanatoriumOgrn() {return theSanatoriumOgrn;}
	public void setSanatoriumOgrn(String aSanatoriumOgrn) {theSanatoriumOgrn = aSanatoriumOgrn;}
	/**
	 * ОГРН санатория или клиники НИИ
	 */
	private String theSanatoriumOgrn;
	/**
	 * Код изменения причины нетрудоспособности
	 */
	@Comment("Код изменения причины нетрудоспособности")
	@OneToOne
	public VocDisabilityReason getDisabilityReasonChange() {return theDisabilityReasonChange;}
	public void setDisabilityReasonChange(VocDisabilityReason aDisabilityReasonChange) {theDisabilityReasonChange = aDisabilityReasonChange;}
	
	/** № истории болезни */
	@Comment("№ истории болезни")
	public String getHospitalizedNumber() {return theHospitalizedNumber;}
	public void setHospitalizedNumber(String aHospitalizedNumber) {theHospitalizedNumber = aHospitalizedNumber;}

	/** Пользователь, создавший документ */
	@Comment("Пользователь, создавший документ")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aUsernameCreate) {theCreateUsername = aUsernameCreate;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aDateCreate) {theCreateDate = aDateCreate;}

	/** Пользователь, редактировавший документ */
	@Comment("Пользователь, редактировавший документ")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aUsernameEdit) {theEditUsername = aUsernameEdit;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aDateEdit) {theEditDate = aDateEdit;}

	/** Статус */
	@Comment("Статус")
	@OneToOne
	public VocDisabilityStatus getStatus() {return theStatus;}
	public void setStatus(VocDisabilityStatus aStatus) {theStatus = aStatus;}

	/** Статус */
	private VocDisabilityStatus theStatus;
	/** Дата редактирования */
	private Date theEditDate;
	/** Пользователь, редактировавший документ */
	private String theEditUsername;
	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь, создавший документ */
	private String theCreateUsername;
	/** № истории болезни */
	private String theHospitalizedNumber;
	/** Код изменения причины нетрудоспособности*/
	private VocDisabilityReason theDisabilityReasonChange;	
	/** Закрыт случай */
	private Boolean theIsClose;
	/** Предыдущий документ */
	private DisabilityDocument thePrevDocument;
	/** Лечебно-профилактическое учреждение */
	private MisLpu theAnotherLpu;
	/** Пациент */
	private Patient thePatient;	
	/** Диагноз текст */
	private String theDiagnosis;
	/** Дата выдачи */
	private Date theIssueDate;
	/** Режим нетрудоспособности */
	private VocDisabilityRegime theDisabilityRegime;
	/** Первичность */
	private VocDisabilityDocumentPrimarity thePrimarity;
	/** Разрешение на выдачу документа нетрудоспособности иногороднему */
	private DisabilityPermission thePermission;
	/** Серия документа нетрудоспособности по основному месту работы */
	private String theMainWorkDocumentSeries;
	/** Номер документа нетрудоспособности по основному месту работы */
	private String theMainWorkDocumentNumber;
	/** Место работы */
	private VocOrg theWorks;
	/** Больной по уходу */
	private Patient theNursedPatient;
	/** Медико-социальная экспертная комиссия */
	private MedSocCommission theMedSocCommission;
	/** Случай нетрудоспособности */
	private DisabilityCase theDisabilityCase;
	/** Серия документа нетрудоспособности по основному месту работы */
	private String theSeries; 
	/** Номер */
	private String theNumber;
	/** Причина нетрудоспособности */
	private VocDisabilityReason theDisabilityReason;
	/** Причина закрытия */
	private VocDisabilityDocumentCloseReason theCloseReason;
	/** Дата начала работы */
	private Date theBeginWorkDate;	
	/** Записи сроков нетрудоспособности */
	private List<DisabilityRecord> theDisabilityRecords;
	/** Признак недействительности документа */
	private Boolean theNoActuality;
	/** Записи нарушения режима */
	private List<RegimeViolationRecord> theRegimeViolationRecords;
	/** Тип документа нетрудоспособности */
	private VocDisabilityDocumentType theDocumentType;
	/** Номер санаторной путевки */
	private String theSanatoriumTicketNumber;
	/** Место нахождения санатория */
	private String theSanatoriumPlace;
	/** МКБ10 */
	private VocIdc10 theIdc10;
	/** Справочник видов совмещения работ */
	private VocCombo theWorkComboType;
	/** Предполагаемая дата родов */
	private Date theSupposeBirthDate;
	/** Дата начала санаторного лечения */
	private Date theSanatoriumDateFrom;
	/** Дата окончания санаторного лечения */
	private Date theSanatoriumDateTo;
	
	/** Место работы */
	@Comment("Место работы")
	public String getJob() {return theJob;}
	public void setJob(String aJob) {theJob = aJob;}

	/** Место работы */
	private String theJob;
	
	/** Заключительный диагноз */
	@Comment("Заключительный диагноз")
	@OneToOne
	public VocIdc10 getIdc10Final() {return theIdc10Final;}
	public void setIdc10Final(VocIdc10 aIdc10Final) {theIdc10Final = aIdc10Final;}

	/** Заключительный диагноз */
	private VocIdc10 theIdc10Final;
	
	/** Дубликат */
	@Comment("Дубликат")
	@OneToOne
	public DisabilityDocument getDuplicate() {return theDuplicate;}
	public void setDuplicate(DisabilityDocument aDuplicate) {theDuplicate = aDuplicate;}

	/** Дубликат */
	private DisabilityDocument theDuplicate;
	
	@Transient
	public String getStatusInfo() {
		StringBuilder res = new StringBuilder() ;
		res.append(theStatus!=null? theStatus.getName(): "") ;
		if (theDuplicate!=null) {
			res.append(" заменен на ").append(theDuplicate.getNumber()) ;
		}
		return  res.toString();
	}

}