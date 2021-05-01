package ru.ecom.mis.ejb.domain.disability;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class DisabilityDocument extends BaseEntity{

	/** Дефект экспорта */
	private String exportDefect;
	/** Признак дубликата (ЭЛН) **/
	private Boolean elnDuplicate;
	/** Следующий номер (ЭЛН) **/
	private String nextElnNumber;
	/** Предыдущий номер (ЭЛН) **/
	private String pervElnNumber;
	/** Хэш (ЭЛН) **/
	private String lnHash="null";
	/** Диагноз из импорта(ЭЛН) **/
	private String diagnos;
	/** Экспортировано закрытие (ЭЛН)*/
	private Boolean closeExport=false;
	/** Является ли электронным */
	private Boolean eln=false;

	private String anotherLpuName;
	private String anotherLpuAddress;
	private String anotherLpuOGRN;
	private Date hospitalizedTo;
	private String sanatoriumOgrn;

	/** Статус */
	private VocDisabilityStatus status;
	/** Дата редактирования */
	private Date editDate;
	/** Пользователь, редактировавший документ */
	private String editUsername;
	/** Дата создания */
	private Date createDate;
	/** Пользователь, создавший документ */
	private String createUsername;
	/** № истории болезни */
	private String hospitalizedNumber;
	/** Код изменения причины нетрудоспособности*/
	private VocDisabilityReason disabilityReasonChange;
	/** Закрыт случай */
	private Boolean isClose;
	/** Предыдущий документ */
	private DisabilityDocument prevDocument;
	/** Лечебно-профилактическое учреждение */
	private MisLpu anotherLpu;
	/** Пациент */
	private Patient patient;
	/** Диагноз текст */
	private String diagnosis;
	/** Дата выдачи */
	private Date issueDate;
	/** Режим нетрудоспособности */
	private VocDisabilityRegime disabilityRegime;
	/** Первичность */
	private VocDisabilityDocumentPrimarity primarity;
	/** Серия документа нетрудоспособности по основному месту работы */
	private String mainWorkDocumentSeries;
	/** Номер документа нетрудоспособности по основному месту работы */
	private String mainWorkDocumentNumber;
	/** Место работы */
	private VocOrg works;
	/** Больной по уходу */
	private Patient nursedPatient;
	/** Медико-социальная экспертная комиссия */
	private MedSocCommission medSocCommission;
	/** Случай нетрудоспособности */
	private DisabilityCase disabilityCase;
	/** Серия документа нетрудоспособности по основному месту работы */
	private String series;
	/** Номер */
	private String number;
	/** Причина нетрудоспособности */
	private VocDisabilityReason disabilityReason;
	/** Причина закрытия */
	private VocDisabilityDocumentCloseReason closeReason;
	/** Дата начала работы */
	private Date beginWorkDate;
	/** Записи сроков нетрудоспособности */
	private List<DisabilityRecord> disabilityRecords;
	/** Признак недействительности документа */
	private Boolean noActuality;
	/** Записи нарушения режима */
	private List<RegimeViolationRecord> regimeViolationRecords;
	/** Тип документа нетрудоспособности */
	private VocDisabilityDocumentType documentType;
	/** Номер санаторной путевки */
	private String sanatoriumTicketNumber;
	/** Место нахождения санатория */
	private String sanatoriumPlace;
	/** МКБ10 */
	private VocIdc10 idc10;
	/** Справочник видов совмещения работ */
	private VocCombo workComboType;
	/** Предполагаемая дата родов */
	private Date supposeBirthDate;
	/** Дата начала санаторного лечения */
	private Date sanatoriumDateFrom;
	/** Дата окончания санаторного лечения */
	private Date sanatoriumDateTo;

	private VocDisabilityReason2 disabilityReason2;
	private Date hospitalizedFrom;

	private VocIdc10 idc10Final;
	private String job;
	private DisabilityDocument duplicate;
	private Date otherCloseDate;
	private Date exportDate;
	private String previouslyIssuedCode;

	@Comment("Медико-социальная экспертная комиссия")
	@OneToOne
	public MedSocCommission getMedSocCommission() {return medSocCommission;}

	@Comment("Случай нетрудоспособности")
	@ManyToOne
	public DisabilityCase getDisabilityCase() {return disabilityCase;}

	@Comment("Причина нетрудоспособности")
	@OneToOne
	public VocDisabilityReason getDisabilityReason() {return disabilityReason;}

	@Comment("Причина закрытия")
	@OneToOne
	public VocDisabilityDocumentCloseReason getCloseReason() {return closeReason;}


	@Comment("Записи сроков нетрудоспособности")
	@OneToMany(mappedBy="disabilityDocument", cascade=CascadeType.ALL)
	public List<DisabilityRecord> getDisabilityRecords() {return disabilityRecords;}


	@Comment("Записи нарушения режима")
	@OneToMany(mappedBy="disabilityDocument", cascade=CascadeType.ALL)
	public List<RegimeViolationRecord> getRegimeViolationRecords() {return regimeViolationRecords;}

	@Comment("Тип документа нетрудоспособности")
	@OneToOne
	public VocDisabilityDocumentType getDocumentType() {return documentType;}

	@Comment("Режим нетрудоспособности")
	@OneToOne
	public VocDisabilityRegime getDisabilityRegime() {return disabilityRegime;}

	@Comment("Первичность")
	@OneToOne
	public VocDisabilityDocumentPrimarity getPrimarity() {return primarity;}

	private String anotherprevln;


	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {return idc10;}

	@Comment("Справочник видов совмещения работ")
	@OneToOne
	public VocCombo getWorkComboType() {return workComboType;}

	@Comment("Больной по уходу")
	@OneToOne
	public Patient getNursedPatient() {return nursedPatient;}

	/** Место работы */
	@Comment("Место работы")
	@OneToOne
	public VocOrg getWorks() {return works;}

	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

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
		return primarity!=null? primarity.getName(): "" ;
	}

	@Transient
	@Comment("Информация о документе")
	public String getInfo() {
		return "серия " + series + " номер " + number;
	}

	@Transient
	public String getDocumentTypeInfo() {
		return documentType!=null?documentType.getName():"" ;
	}

	@Comment("Пациент ФИО")
	@Transient
	public String getPatientFio() {return patient!=null?patient.getFio():"";}

	@Comment("Пациент ФИО")
	@Transient
	public String getPatientAddress() {return patient!=null?patient.getAddressRegistration():"";}

	@Comment("Лечебно-профилактическое учреждение")
	@OneToOne
	public MisLpu getAnotherLpu() {return anotherLpu;}

	/** Предыдущий документ */
	@Comment("Предыдущий документ")
	@OneToOne
	public DisabilityDocument getPrevDocument() {return prevDocument;}

	@Comment("Дополнительная причина нетрудоспособности")
	@OneToOne
	public VocDisabilityReason2 getDisabilityReason2() {return disabilityReason2;}

	@Comment("Код изменения причины нетрудоспособности")
	@OneToOne
	public VocDisabilityReason getDisabilityReasonChange() {return disabilityReasonChange;}

	@Comment("Статус")
	@OneToOne
	public VocDisabilityStatus getStatus() {return status;}

	@Comment("Заключительный диагноз")
	@OneToOne
	public VocIdc10 getIdc10Final() {return idc10Final;}

	@Comment("Дубликат")
	@OneToOne
	public DisabilityDocument getDuplicate() {return duplicate;}

	@Transient
	public String getStatusInfo() {
		StringBuilder res = new StringBuilder() ;
		res.append(status!=null? status.getName(): "") ;
		if (duplicate!=null) {
			res.append(" заменен на ").append(duplicate.getNumber()) ;
		}
		return  res.toString();
	}
}