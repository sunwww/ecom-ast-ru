package ru.ecom.mis.ejb.domain.disability;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.disability.voc.*;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.worker.voc.VocCombo;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Документ нетрудоспособности
 * @author azviagin,stkacheva,dpetinov,rkurbanov
 *
 */
@Comment("Документ нетрудоспособности")
@Entity
@AIndexes({
		@AIndex(properties= {"disabilityCase"})
		,@AIndex(properties= {"issueDate"})
		,@AIndex(properties= {"mainWorkDocumentNumber"})
		,@AIndex(properties= {"idc10Final"})
		,@AIndex(properties= {"patient"})
		,@AIndex(properties = {"duplicate"})
		,@AIndex(properties = {"prevDocument"})
})
@Table(schema="SQLUser")
public class DisabilityDocument extends BaseEntity{

	/** Дефект экспорта */
	private String theExportDefect;
	/** Признак дубликата (ЭЛН) **/
	private Boolean isElnDuplicate;
	/** Следующий номер (ЭЛН) **/
	private String nextElnNumber;
	/** Предыдущий номер (ЭЛН) **/
	private String pervElnNumber;
	/** Хэш (ЭЛН) **/
	private String lnHash="null";
	/** Диагноз из импорта(ЭЛН) **/
	private String theDiagnos;
	/** Экспортировано закрытие (ЭЛН)*/
	private Boolean isCloseExport=false;
	/** Является ли электронным */
	private Boolean isELN=false;

	private String anotherLpuName;
	private String anotherLpuAddress;
	private String anotherLpuOGRN;
	private Date theHospitalizedTo;
	private String theSanatoriumOgrn;

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

	private VocDisabilityReason2 theDisabilityReason2;
	private Date theHospitalizedFrom;

	private VocIdc10 theIdc10Final;
	private String theJob;
	private DisabilityDocument theDuplicate;
	private Date theOtherCloseDate;
	private Date theExportDate;

	@Comment("Экспортировано закрытие (ЭЛН)")
	public Boolean getCloseExport() {
		return isCloseExport;
	}
	public void setCloseExport(Boolean closeExport) {
		isCloseExport = closeExport;
	}

	@Comment("Диагноз из импорта")
	public String getDiagnos() {
		return theDiagnos;
	}
	public void setDiagnos(String aDiagnos) {
		theDiagnos = aDiagnos;
	}

	@Comment("Медико-социальная экспертная комиссия")
	@OneToOne
	public MedSocCommission getMedSocCommission() {return theMedSocCommission;}
	public void setMedSocCommission(MedSocCommission aMedSocCommission) {theMedSocCommission = aMedSocCommission;}

	@Comment("Случай нетрудоспособности")
	@ManyToOne
	public DisabilityCase getDisabilityCase() {return theDisabilityCase;}
	public void setDisabilityCase(DisabilityCase aDisabilityCase) {theDisabilityCase = aDisabilityCase;}

	@Comment("Серия")
	public String getSeries() {return theSeries;}
	public void setSeries (String aCloseSeries) {theSeries = aCloseSeries;}

	@Comment("Номер")
	public String getNumber() {return theNumber;}
	public void setNumber(String aNumber) {theNumber = aNumber;}

	@Comment("Причина нетрудоспособности")
	@OneToOne
	public VocDisabilityReason getDisabilityReason() {return theDisabilityReason;}
	public void setDisabilityReason(VocDisabilityReason aDisabilityReason) {theDisabilityReason = aDisabilityReason;}

	@Comment("Причина закрытия")
	@OneToOne
	public VocDisabilityDocumentCloseReason getCloseReason() {return theCloseReason;}
	public void setCloseReason(VocDisabilityDocumentCloseReason aCloseReason) {theCloseReason = aCloseReason;}

	@Comment("Дата начала работы")
	public Date getBeginWorkDate() {return theBeginWorkDate;}
	public void setBeginWorkDate(Date aBeginWorkDate) {theBeginWorkDate = aBeginWorkDate;}

	@Comment("Записи сроков нетрудоспособности")
	@OneToMany(mappedBy="disabilityDocument", cascade=CascadeType.ALL)
	public List<DisabilityRecord> getDisabilityRecords() {return theDisabilityRecords;}
	public void setDisabilityRecords(List<DisabilityRecord> aDisabilityRecords) {theDisabilityRecords = aDisabilityRecords;}

	@Comment("Признак недействительности документа")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}

	@Comment("Записи нарушения режима")
	@OneToMany(mappedBy="disabilityDocument", cascade=CascadeType.ALL)
	public List<RegimeViolationRecord> getRegimeViolationRecords() {return theRegimeViolationRecords;}
	public void setRegimeViolationRecords(List<RegimeViolationRecord> aRegimeViolationRecords) {theRegimeViolationRecords = aRegimeViolationRecords;}

	@Comment("Тип документа нетрудоспособности")
	@OneToOne
	public VocDisabilityDocumentType getDocumentType() {return theDocumentType;}
	public void setDocumentType(VocDisabilityDocumentType aDocumentType) {theDocumentType = aDocumentType;}

	@Comment("Режим нетрудоспособности")
	@OneToOne
	public VocDisabilityRegime getDisabilityRegime() {return theDisabilityRegime;}
	public void setDisabilityRegime(VocDisabilityRegime aDisabilityRegime) {theDisabilityRegime = aDisabilityRegime;}

	@Comment("Первичность")
	@OneToOne
	public VocDisabilityDocumentPrimarity getPrimarity() {return thePrimarity;}
	public void setPrimarity(VocDisabilityDocumentPrimarity aPrimarity) {thePrimarity = aPrimarity;}

	@Comment("Предыдущий номер ЛН (при импорте)")
	public String getAnotherprevln() {return theAnotherprevln;}
	public void setAnotherprevln(String aAnotherprevln) {theAnotherprevln = aAnotherprevln;}
	private String theAnotherprevln;

	@Comment("Дата выдачи")
	public Date getIssueDate() {return theIssueDate;}
	public void setIssueDate(Date aIssueDate) {theIssueDate = aIssueDate;}

	@Comment("Серия документа нетрудоспособности по основному месту работы")
	public String getMainWorkDocumentSeries() {return theMainWorkDocumentSeries;}
	public void setMainWorkDocumentSeries(String aSeries) {theMainWorkDocumentSeries = aSeries;}

	@Comment("Номер документа нетрудоспособности по основному месту работы")
	public String getMainWorkDocumentNumber() {return theMainWorkDocumentNumber;}
	public void setMainWorkDocumentNumber(String aMainWorkDisabilityDocument) {theMainWorkDocumentNumber = aMainWorkDisabilityDocument;}

	@Comment("Предполагаемая дата родов")
	public Date getSupposeBirthDate() {return theSupposeBirthDate;}
	public void setSupposeBirthDate(Date aSupposeBirthDate) {theSupposeBirthDate = aSupposeBirthDate;}

	@Comment("Дата начала санаторного лечения")
	public Date getSanatoriumDateFrom() {return theSanatoriumDateFrom;}
	public void setSanatoriumDateFrom(Date aSanatoriumDateFrom) {theSanatoriumDateFrom = aSanatoriumDateFrom;}

	@Comment("Дата окончания санаторного лечения")
	public Date getSanatoriumDateTo() {return theSanatoriumDateTo;}
	public void setSanatoriumDateTo(Date aSanatoriumDateTo) {theSanatoriumDateTo = aSanatoriumDateTo;}

	@Comment("Номер санаторной путевки")
	public String getSanatoriumTicketNumber() {return theSanatoriumTicketNumber;}
	public void setSanatoriumTicketNumber(String aSanatoriumTicketNumber) {theSanatoriumTicketNumber = aSanatoriumTicketNumber;}

	@Comment("Место нахождения санатория")
	public String getSanatoriumPlace() {return theSanatoriumPlace;}
	public void setSanatoriumPlace(String aSanatoriumPlace) {theSanatoriumPlace = aSanatoriumPlace;}

	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aNewProperty) {theIdc10 = aNewProperty;	}

	@Comment("Справочник видов совмещения работ")
	@OneToOne
	public VocCombo getWorkComboType() {return theWorkComboType;}
	public void setWorkComboType(VocCombo aCombo) {theWorkComboType = aCombo;}

	@Comment("Больной по уходу")
	@OneToOne
	public Patient getNursedPatient() {return theNursedPatient;}
	public void setNursedPatient(Patient aNursedPatient) {theNursedPatient = aNursedPatient;}

	/** Место работы */
	@Comment("Место работы")
	@OneToOne
	public VocOrg getWorks() {return theWorks;}
	public void setWorks(VocOrg aWorks) {theWorks = aWorks;}

	@Comment("Диагноз текст")
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;	}

	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	@Comment("Дата начала")
	@Transient
	public Date getDateFrom() {
		if (getDisabilityRecords().isEmpty()) {
			return null;
		} else {
			Date dateFrom = null;
			for (DisabilityRecord dr:getDisabilityRecords()) {
				if (dateFrom ==null||dateFrom.getTime()>dr.getDateFrom().getTime()) {
					dateFrom = dr.getDateFrom();
				}
			}
			return dateFrom;
		}

	}

	@Comment("Дата окончания")
	@Transient
	public Date getDateTo() {
		if (getDisabilityRecords().isEmpty()) {
			return null;
		} else {
			Date dateFrom = null;
			Date dateTo = null;
			for (DisabilityRecord dr:getDisabilityRecords()) {
				if (dateFrom ==null||dateFrom.getTime()<dr.getDateFrom().getTime()) {
					dateFrom = dr.getDateFrom();
					dateTo = dr.getDateTo();
				}
			}
			return dateTo;
		}
	}

	@Transient
	@Comment("Первичность (текст)")
	public String getPrimarityInfo() {
		return thePrimarity!=null? thePrimarity.getName(): "" ;
	}

	@Transient
	@Comment("Информация о документе")
	public String getInfo() {
		return "серия " + theSeries + " номер " + theNumber;
	}

	@Transient
	public String getDocumentTypeInfo() {
		return theDocumentType!=null?theDocumentType.getName():"" ;
	}

	@Comment("Пациент ФИО")
	@Transient
	public String getPatientFio() {return thePatient!=null?thePatient.getFio():"";}

	@Comment("Пациент ФИО")
	@Transient
	public String getPatientAddress() {return thePatient!=null?thePatient.getAddressRegistration():"";}

	@Comment("Лечебно-профилактическое учреждение")
	@OneToOne
	public MisLpu getAnotherLpu() {return theAnotherLpu;}
	public void setAnotherLpu(MisLpu aAnotherLpu) {theAnotherLpu = aAnotherLpu;}

	@Comment("Другое ЛПУ. Название")
	public String getAnotherLpuName() {	return anotherLpuName;	}
	public void setAnotherLpuName(String anotherLpuName) {
		this.anotherLpuName = anotherLpuName;
	}

	@Comment("Другое ЛПУ. Адрес")
	public String getAnotherLpuAddress() {
		return anotherLpuAddress;
	}
	public void setAnotherLpuAddress(String anotherLpuAddress) {
		this.anotherLpuAddress = anotherLpuAddress;
	}

	@Comment("Другое ЛПУ. ОГРН")
	public String getAnotherLpuOGRN() {
		return anotherLpuOGRN;
	}
	public void setAnotherLpuOGRN(String anotherLpuOGRN) {
		this.anotherLpuOGRN = anotherLpuOGRN;
	}

	/** Предыдущий документ */
	@Comment("Предыдущий документ")
	@OneToOne
	public DisabilityDocument getPrevDocument() {return thePrevDocument;}
	public void setPrevDocument(DisabilityDocument aPrevDocument) {thePrevDocument = aPrevDocument;}

	@Comment("Закрыт случай")
	public Boolean getIsClose() {return theIsClose;}
	public void setIsClose(Boolean aIsClose) {theIsClose = aIsClose;}

	@Comment("Дополнительная причина нетрудоспособности")
	@OneToOne
	public VocDisabilityReason2 getDisabilityReason2() {return theDisabilityReason2;}
	public void setDisabilityReason2(VocDisabilityReason2 aDisabilityReason2) {theDisabilityReason2 = aDisabilityReason2;}

	@Comment("Дата начала госпитализации")
	public Date getHospitalizedFrom() {return theHospitalizedFrom;}
	public void setHospitalizedFrom(Date aHospitalizedFrom) {theHospitalizedFrom = aHospitalizedFrom;}

	@Comment("Дата окончания госпитализации")
	public Date getHospitalizedTo() {return theHospitalizedTo;}
	public void setHospitalizedTo(Date aHospitalizedTo) {theHospitalizedTo = aHospitalizedTo;}

	@Comment("ОГРН санатория или клиники НИИ")
	public String getSanatoriumOgrn() {return theSanatoriumOgrn;}
	public void setSanatoriumOgrn(String aSanatoriumOgrn) {theSanatoriumOgrn = aSanatoriumOgrn;}

	@Comment("Код изменения причины нетрудоспособности")
	@OneToOne
	public VocDisabilityReason getDisabilityReasonChange() {return theDisabilityReasonChange;}
	public void setDisabilityReasonChange(VocDisabilityReason aDisabilityReasonChange) {theDisabilityReasonChange = aDisabilityReasonChange;}

	@Comment("№ истории болезни")
	public String getHospitalizedNumber() {return theHospitalizedNumber;}
	public void setHospitalizedNumber(String aHospitalizedNumber) {theHospitalizedNumber = aHospitalizedNumber;}

	@Comment("Пользователь, создавший документ")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aUsernameCreate) {theCreateUsername = aUsernameCreate;}

	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aDateCreate) {theCreateDate = aDateCreate;}

	@Comment("Пользователь, редактировавший документ")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aUsernameEdit) {theEditUsername = aUsernameEdit;}

	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aDateEdit) {theEditDate = aDateEdit;}

	@Comment("Статус")
	@OneToOne
	public VocDisabilityStatus getStatus() {return theStatus;}
	public void setStatus(VocDisabilityStatus aStatus) {theStatus = aStatus;}

	@Comment("Место работы")
	public String getJob() {return theJob;}
	public void setJob(String aJob) {theJob = aJob;}

	@Comment("Заключительный диагноз")
	@OneToOne
	public VocIdc10 getIdc10Final() {return theIdc10Final;}
	public void setIdc10Final(VocIdc10 aIdc10Final) {theIdc10Final = aIdc10Final;}

	@Comment("Дубликат")
	@OneToOne
	public DisabilityDocument getDuplicate() {return theDuplicate;}
	public void setDuplicate(DisabilityDocument aDuplicate) {theDuplicate = aDuplicate;}

	@Transient
	public String getStatusInfo() {
		StringBuilder res = new StringBuilder() ;
		res.append(theStatus!=null? theStatus.getName(): "") ;
		if (theDuplicate!=null) {
			res.append(" заменен на ").append(theDuplicate.getNumber()) ;
		}
		return  res.toString();
	}

	@Comment("Дата иное")
	public Date getOtherCloseDate() {return theOtherCloseDate;}
	public void setOtherCloseDate(Date aOtherCloseDate) {theOtherCloseDate = aOtherCloseDate;}

	@Comment("Дата экспорта")
	public Date getExportDate() {return theExportDate;}
	public void setExportDate(Date aExportDate) {theExportDate = aExportDate;}

	@Comment("Дефект экспорта")
	public String getExportDefect() {return theExportDefect;}
	public void setExportDefect(String aExportDefect) {theExportDefect = aExportDefect;}

	public Boolean getElnDuplicate() {
		return isElnDuplicate;
	}
	public void setElnDuplicate(Boolean elnDuplicate) {
		isElnDuplicate = elnDuplicate;
	}

	public String getNextElnNumber() {
		return nextElnNumber;
	}
	public void setNextElnNumber(String nextElnNumber) {
		this.nextElnNumber = nextElnNumber;
	}

	public String getPervElnNumber() {
		return pervElnNumber;
	}
	public void setPervElnNumber(String pervElnNumber) {
		this.pervElnNumber = pervElnNumber;
	}

	public String getLnHash() {
		return lnHash;
	}
	public void setLnHash(String lnHash) {
		this.lnHash = lnHash;
	}

	public Boolean getELN() {
		return isELN;
	}
	public void setELN(Boolean ELN) {
		isELN = ELN;
	}
}