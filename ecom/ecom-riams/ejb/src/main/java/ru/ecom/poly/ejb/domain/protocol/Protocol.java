package ru.ecom.poly.ejb.domain.protocol;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.domain.patient.ObservationSheet;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocProtocolMode;
import ru.ecom.poly.ejb.domain.voc.VocTypeProtocol;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 07.02.2007
 * Time: 15:42:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
@AIndexes({
	@AIndex(properties="medCase",table="Diary"),
	@AIndex(properties={"medCase","printDate"},table="Diary"),
	@AIndex(properties="dateRegistration",table="Diary"),
	@AIndex(properties="obsSheet",table="Diary")
    })
@Table(schema="SQLUser")
public class Protocol extends Diary {

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@OneToOne
	public ServiceMedCase getServiceMedCase() {return theServiceMedCase;}
	public void setServiceMedCase(ServiceMedCase aServiceMedCase) {theServiceMedCase = aServiceMedCase;}
	/** Медицинская услуга */
	private ServiceMedCase theServiceMedCase;
	
	/** Шаблон, на основе которого создано заключение */
	@Comment("Шаблон, на основе которого создано заключение")
	public Long getTemplateProtocol() {return theTemplateProtocol;}
	public void setTemplateProtocol(Long aTemplateProtocol) {theTemplateProtocol = aTemplateProtocol;}
	/** Шаблон, на основе которого создано заключение */
	private Long theTemplateProtocol;
	
	/** Запрет на ручное редактирование */
	@Comment("Запрет на ручное редактирование")
	public Boolean getDisableEdit() {return theDisableEdit;}
	public void setDisableEdit(Boolean aDisableEdit) {theDisableEdit = aDisableEdit;}
	/** Запрет на ручное редактирование */
	private Boolean theDisableEdit;

	/** Дата регистрации талона */
    public Date getDateRegistration() { return theDateRegistration ; }
    public void setDateRegistration(Date aDateRegistration) { theDateRegistration = aDateRegistration ; }

    /** Визит */
	@Comment("Визит")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Специалист */
	@Comment("Специалист")
	@OneToOne
	public WorkFunction getSpecialist() {return theSpecialist;}
	public void setSpecialist(WorkFunction aSpecialist) {theSpecialist = aSpecialist;}
	
	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}
	
	/** Тип протокола */
	@Comment("Тип протокола")
	@OneToOne
	public VocTypeProtocol getType() {return theType;}
	public void setType(VocTypeProtocol aType) {theType = aType;}
	
	/** Для выписки */
	@Comment("Для выписки")
	public Boolean getIsDischarge() {return theIsDischarge;}
	public void setIsDischarge(Boolean aIsDischange) {theIsDischarge = aIsDischange;}
	
	/** Время регистрации */
	@Comment("Время регистрации")
	public Time getTimeRegistration() {return theTimeRegistration;}
	public void setTimeRegistration(Time aTimeRegistration) {theTimeRegistration = aTimeRegistration;}

	/** Общая информация о протоколе */
	@Transient
    public String getInfo() {
        StringBuilder info = new StringBuilder() ;
        if (theType!=null) {
        	info.append(theType.getName()) ;
        }
        if (theSpecialist!=null) {
            info.append(" Врач: ").append(theSpecialist.getName()).append(". ") ;
        }
        return info.append(" Дата: ").append(DateFormat.formatToDate(theDateRegistration)).toString() ;
    }

	@Transient
	public String getSpecialistInfo() {
		return theSpecialist!=null ?theSpecialist.getWorkFunctionInfo() :"" ;
	}
	
	@Transient
	public String getTypeInfo() {
		return theType!=null?theType.getName():"" ;
	}
	
	/** Дата печати */
	@Comment("Дата печати")
	public Date getPrintDate() {return thePrintDate;}
	public void setPrintDate(Date aPrintDate) {thePrintDate = aPrintDate;}
	
	/** Время печати */
	@Comment("Время печати")
	public Time getPrintTime() {return thePrintTime;}
	public void setPrintTime(Time aPrintTime) {thePrintTime = aPrintTime;}

	/** Время печати */
	private Time thePrintTime;
	/** Дата печати */
	private Date thePrintDate;
	/** Время регистрации*/
	private Time theTimeRegistration;
	/** Для выписки */
	private Boolean theIsDischarge;
	/** Тип протокола */
	private VocTypeProtocol theType;
	/** Пользователь */
	private String theUsername;
	/** Специалист */
	private WorkFunction theSpecialist;
	/** Визит */
	private MedCase theMedCase;
    /** Дата регистрации талона */
    private Date theDateRegistration ;
    
    /** Информация для журнала */
	@Comment("Информация для журнала")
	public String getJournalText() {return theJournalText;}
	public void setJournalText(String aJournalText) {theJournalText = aJournalText;}

	/** Информация для журнала */
	private String theJournalText;
	
	/** Режим */
	@Comment("Режим")
	@OneToOne
	public VocProtocolMode getMode() {return theMode;}
	public void setMode(VocProtocolMode aMode) {theMode = aMode;}

	/** Режим */
	private VocProtocolMode theMode;

	/** Лист наблюдения (для ЕДКЦ) */
	@Comment("Лист наблюдения (для ЕДКЦ)")
	@ManyToOne
	public ObservationSheet getObsSheet() {return theObsSheet;}
	public void setObsSheet(ObservationSheet aObsSheet) {theObsSheet = aObsSheet;}

	/** Лист наблюдения (для ЕДКЦ) */
	private ObservationSheet theObsSheet;

	/** Заголовок дневника */
	@Comment("Заголовок дневника")
	public String getTitle() {return theTitle;}
	public void setTitle(String aTitle) {theTitle = aTitle;}
	private String theTitle ="";

	/** Подвал */
	@Comment("Подвал")
	public String getBottom() {return theBottom;}
	public void setBottom(String aBottom) {theBottom = aBottom;}
	private String theBottom ;
}
