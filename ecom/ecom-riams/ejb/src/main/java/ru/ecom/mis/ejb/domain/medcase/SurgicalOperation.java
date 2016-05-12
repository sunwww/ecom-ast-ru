package ru.ecom.mis.ejb.domain.medcase;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAbortion;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia;
import ru.ecom.mis.ejb.domain.medcase.voc.VocComplication;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalAspect;
import ru.ecom.mis.ejb.domain.medcase.voc.VocOperation;
import ru.ecom.mis.ejb.domain.medcase.voc.VocOperationMethod;
import ru.ecom.mis.ejb.domain.medcase.voc.VocOperationOutcome;
import ru.ecom.mis.ejb.domain.medcase.voc.VocOperationTechnology;
import ru.ecom.mis.ejb.domain.medcase.voc.VocSurgicalProfile;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Хирургическая операция")
@AIndexes({
	@AIndex(properties="medCase"),
	@AIndex(properties="operationDate"),
	@AIndex(properties={"surgeon"}),
	@AIndex(properties={"operation"}),
	@AIndex(properties={"medService"}),
	@AIndex(properties="patient"),
	@AIndex(properties="serviceStream"),
	@AIndex(properties="department")
    }) 
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class SurgicalOperation extends BaseEntity {
	/** Дата операции */
	@Comment("Дата операции")
	public Date getOperationDate() {return theOperationDate;}
	public void setOperationDate(Date aOperationDate) {theOperationDate = aOperationDate;	}

	/** Время операции */
	@Comment("Время операции")
	public Time getOperationTime() {return theOperationTime;}
	public void setOperationTime(Time aOperationTime) {theOperationTime = aOperationTime;	}

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}

	/** Операция */
	@Comment("Операция")
	@OneToOne
	public VocOperation getOperation() {return theOperation;}
	public void setOperation(VocOperation aOperation) {theOperation = aOperation;}

	/** Анестизия */
	@Comment("Анестезия")
	@OneToOne
	public VocAnesthesia getAnesthesia() {return theAnesthesia;}
	public void setAnesthesia(VocAnesthesia aAnesthesia) {theAnesthesia = aAnesthesia;	}

	/** Флаг основной операции */
	@Comment("Флаг основной операции")
	public boolean getBase() {return theBase;	}
	public void setBase(boolean aBase) {theBase = aBase;}

	/** Флаг использования эндоскопии */
	@Comment("Флаг использования эндоскопии")
	public boolean getEndoscopyUse() {return theEndoscopyUse;	}
	public void setEndoscopyUse(boolean aEndoscopyUse) {theEndoscopyUse = aEndoscopyUse;	}

	/** Флаг использования лазерной аппаратуры */
	@Comment("Флаг использования лазерной аппаратуры")
	public boolean getLaserUse() {	return theLaserUse;	}
	public void setLaserUse(boolean aLaserUse) {theLaserUse = aLaserUse;}

	/** Использование криогенной аппаратуры */
	@Comment("Использование криогенной аппаратуры")
	public boolean getCryogenicUse() {return theCryogenicUse;	}
	public void setCryogenicUse(boolean aCryogenicUse) {theCryogenicUse = aCryogenicUse;	}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@ManyToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}
	
	/** Осложнения */
	@Comment("Осложнения")
	@ManyToMany
	public List<VocComplication> getComplications() {return theComplications;	}
	public void setComplications(List<VocComplication> aComplications) {theComplications = aComplications;	}
	
	
	/** Кол-во  анастезии */
	@Comment("Кол-во  анастезии")
	public BigDecimal getAnesthesiaAmount() {return theAnesthesiaAmount;}
	public void setAnesthesiaAmount(BigDecimal aAnesthesiaAmount) {theAnesthesiaAmount = aAnesthesiaAmount;}

	/** Хирург инфо 
	@Comment("Хирург инфо")
	@Transient
	public String getSurgeonsInfo() {
		StringBuilder ret = new StringBuilder() ;
		for (WorkFunction wf: theSurgeonFunctions) {
			ret.append(", ").append(wf.getWorkFunctionInfo()) ;
		}
		return ret.length()>2?ret.substring(2):"" ;
		//return theSurgeon!=null ? theSurgeon.getDoctorInfo(): "";	
	}*/
	
	/**Рабочая функция врача, проводившего операцию info */
	@Comment("Рабочая функция врача, проводившего операцию info")
	@Transient
	public String getSurgeonInfo(){
		return theSurgeon!=null?theSurgeon.getWorkFunctionInfo():"";
	}
	/*
	@Comment("Отделение инфо")
	@Transient
	public String getDepartmentInfo() {	return theDepartment!=null ? theDepartment.getName():"";}
	
	@Comment("Анестезия инфо")
	@Transient
	public String getAnesthesiaInfo(){return theAnesthesia!=null ? theAnesthesia.getName():"";}
	
	@Comment("Операция инфо")
	@Transient
	public String getOperationInfo(){return theOperation!=null ? theOperation.getName():"";}
	*/
	/** Лечебное учреждение 
	@Comment("Лечебное учреждение")
	@Transient
	public MisLpu getLpu() {
		MisLpu lpu = null ;
		if (theMedCase!=null && theMedCase.getLpu()!=null) lpu = theMedCase.getLpu() ;
		return lpu ;
	}*/
	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}
	
	/** Дата операции по */
	@Comment("Дата операции по")
	public Date getOperationDateTo() {return theOperationDateTo;}
	public void setOperationDateTo(Date aOperationDateTo) {theOperationDateTo = aOperationDateTo;}
	
	/** Анестезиолог */
	@Comment("Анестезиолог")
	@OneToOne
	public WorkFunction getAnaesthetist() {return theAnaesthetist;}
	public void setAnaesthetist(WorkFunction aAnaesthetist) {theAnaesthetist = aAnaesthetist;}

	/** Операционная медсестра */
	@Comment("Операционная медсестра")
	@OneToOne
	public WorkFunction getOperatingNurse() {return theOperatingNurse;}
	public void setOperatingNurse(WorkFunction aOperatingNurse) {theOperatingNurse = aOperatingNurse;}
	
	/** Предоперационный эпикриз */
	@Comment("Предоперационный эпикриз")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getPreoperativeEpicrisis() {return thePreoperativeEpicrisis;}
	public void setPreoperativeEpicrisis(String aPreoperativeEpicrisis) {thePreoperativeEpicrisis = aPreoperativeEpicrisis;}
	
	/** Описание операции */
	@Comment("Описание операции")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getOperationText() {return theOperationText;}
	public void setOperationText(String aOperationText) {theOperationText = aOperationText;}

	
	/** МКБ до операции */
	@Comment("МКБ до операции")
	@OneToOne
	public VocIdc10 getIdc10Before() {return theIdc10Before;}
	public void setIdc10Before(VocIdc10 aIdc10Before) {theIdc10Before = aIdc10Before;}
	
	@Transient
	public String getIdc10BeforeInfo() {return theIdc10Before!=null?theIdc10Before.getCode():"" ;}
	@Transient
	public String getIdc10AfterInfo() {return theIdc10After!=null?theIdc10After.getCode():"" ;}
	
	/** МКБ после операции */
	@Comment("МКБ после операции")
	@OneToOne
	public VocIdc10 getIdc10After() {return theIdc10After;}
	public void setIdc10After(VocIdc10 aIdc10After) {theIdc10After = aIdc10After;}

	
	/** Гистологическое исследование */
	@Comment("Гистологическое исследование")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getHistologicalStudy() {return theHistologicalStudy;}
	public void setHistologicalStudy(String aHistologicalStudy) {theHistologicalStudy = aHistologicalStudy;}

	
	/** Время операции по */
	@Comment("Время операции по")
	public Time getOperationTimeTo() {return theOperationTimeTo;}
	public void setOperationTimeTo(Time aOperationTimeTo) {theOperationTimeTo = aOperationTimeTo;}
	
	/** Хирурги */
	@Comment("Хирурги")
	@ManyToMany
	public List<WorkFunction> getSurgeonFunctions() {return theSurgeonFunctions;}
	public void setSurgeonFunctions(List<WorkFunction> aSurgeonFunctions) {theSurgeonFunctions = aSurgeonFunctions;}

	@Comment("Информации")
	@Transient
	public String getInformation() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("Период: ").append(theOperationDate).append(" ").append(theOperationTime).append(" - ")
			.append(theOperationDateTo).append(" ").append(theOperationTimeTo);
		ret.append("Операция: ").append(theOperation);
		ret.append("Хирург: ").append(getSurgeonInfo());
		//ret.append("Анестезиолог: ").append(theAnaesthetist) ;
		//ret.append("Анестезия: ").append(theAnesthesia).append(" кол-во:").append(theAnesthesiaAmount);
		
		return ret.toString() ;
	}
		
		@Comment("Период")
		@Transient
		public String getPeriod() {
			StringBuilder ret = new StringBuilder() ;
			ret.append(theOperationDate).append(" ").append(theOperationTime).append(" - ")
				.append(theOperationDateTo).append(" ").append(theOperationTimeTo);
			return ret.toString() ;
	}
		
		 /** Информация о пациенте */
	    @Comment("Информация о пациенте")
	    @Transient
	    public String getPatientInfo() {
	        
	        return thePatient!=null?thePatient.getPatientInfo():"";
	    }
	
	 /** Экстренность */
	@Comment("Экстренность")
	public Boolean getEmergency() {
		return theEmergency;
	}

	public void setEmergency(Boolean aEmergency) {
		theEmergency = aEmergency;
	}
	/** Показания для операции */
	@Comment("Показания для операции")
	@OneToOne
	public VocHospitalAspect getAspect() {return theAspect;}
	public void setAspect(VocHospitalAspect aAspect) {theAspect = aAspect;}

	/** Показания для операции */
	private VocHospitalAspect theAspect;

	/** Экстренность */
	private Boolean theEmergency;
	
	/** Малая */
	@Comment("Малая")
	public Boolean getMinor() {
		return theMinor;
	}

	public void setMinor(Boolean aMinor) {
		theMinor = aMinor;
	}

	/** Малая */
	private Boolean theMinor;
	
	/** Анестезии */
	@Comment("Анестезии")
	@OneToMany(mappedBy="surgicalOperation", cascade=CascadeType.ALL)
	public List<Anesthesia> getAnesthesies() {
		return theAnesthesies;
	}

	public void setAnesthesies(List<Anesthesia> aAnesthesies) {
		theAnesthesies = aAnesthesies;
	}
	
	/** Номер в журнале */
	@Comment("Номер в журнале")
	public String getNumberInJournal() {
		return theNumberInJournal;
	}

	public void setNumberInJournal(String aNumberInJournal) {
		theNumberInJournal = aNumberInJournal;
	}
	
	/** Хирург */
	@Comment("Хирург")
	@OneToOne
	public WorkFunction getSurgeon() {return theSurgeon;}
	public void setSurgeon(WorkFunction aSurgeon) {theSurgeon = aSurgeon;}
	
	/** Профиль */
	@Comment("Профиль")
	@OneToOne
	public VocSurgicalProfile getProfile() {return theProfile;}
	public void setProfile(VocSurgicalProfile aProfile) {theProfile = aProfile;}

	/** Операция с использованием высоких медицинских технологий */
	@Comment("Операция с использованием высоких медицинских технологий")
	@OneToOne
	public VocOperationTechnology getTechnology() {return theTechnology;}
	public void setTechnology(VocOperationTechnology aTechnology) {theTechnology = aTechnology;}
	
	/** Исход операции */
	@Comment("Исход операции")
	@OneToOne
	public VocOperationOutcome getOutcome() {return theOutcome;}
	public void setOutcome(VocOperationOutcome aOutcome) {theOutcome = aOutcome;}

	
	/** Метод операции */
	@Comment("Метод операции")
	@OneToOne
	public VocOperationMethod getMethod() {return theMethod;}
	public void setMethod(VocOperationMethod aMethod) {theMethod = aMethod;}

	/** Метод операции */
	private VocOperationMethod theMethod;
	/** Исход операции */
	private VocOperationOutcome theOutcome;

	/** Операция с использованием высоких медицинских технологий */
	private VocOperationTechnology theTechnology;
	/** Профиль */
	private VocSurgicalProfile theProfile;

	/** Хирург */
	private WorkFunction theSurgeon;

	/** Номер в журнале */
	private String theNumberInJournal;

	/** Анестезии */
	private List<Anesthesia> theAnesthesies;
	
	    
	/** Кол-во  анастезии */
	private BigDecimal theAnesthesiaAmount;
	/** Хирурги */
	private List<WorkFunction> theSurgeonFunctions;
	/** Анестезиолог */
	private WorkFunction theAnaesthetist;
	/** Дата операции по */
	private Date theOperationDateTo;
	/** Предоперационный эпикриз */
	private String thePreoperativeEpicrisis;
	/** Описание операции */
	private String theOperationText;
	/** МКБ до операции */
	private VocIdc10 theIdc10Before;
	/** Операционная медсестра */
	private WorkFunction theOperatingNurse;
	/** МКБ после операции */
	private VocIdc10 theIdc10After;
	/** Гистологическое исследование */
	private String theHistologicalStudy;
	/** Время операции по */
	private Time theOperationTimeTo;

	/** Лечебное учреждение */
	private MisLpu theLpu;
	/** Осложнения */
	private List<VocComplication> theComplications;
	/** Пациент */
	private Patient thePatient;
	/** Случай медицинского обслуживания */
	private MedCase theMedCase;
	/** Использование криогенной аппаратуры */
	private boolean theCryogenicUse;
	/** Флаг использования лазерной аппаратуры */
	private boolean theLaserUse;
	/** Флаг использования эндоскопии */
	private boolean theEndoscopyUse;
	/** Флаг основной операции */
	private boolean theBase;
	/** Анестезия */
	private VocAnesthesia theAnesthesia;
	/** Операция */
	private VocOperation theOperation;
	/** Отделение */
	private MisLpu theDepartment;
	/** Время операции */
	private Time theOperationTime;
	/** Дата операции */
	private Date theOperationDate;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aUsername) {theCreateUsername = aUsername;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Пользователь, последний изменявший запись */
	@Comment("Пользователь, последний изменявший запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, последний изменявший запись */
	private String theEditUsername;
	/** Дата редактирования */
	private Date theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}

	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@OneToOne
	public MedService getMedService() {return theMedService;}
	public void setMedService(MedService aMedService) {theMedService = aMedService;}

	/** Мед. услуга */
	private MedService theMedService;
	
	/** Аборта */
	@Comment("Аборта")
	@OneToOne
	public VocAbortion getAbortion() {return theAbortion;}
	public void setAbortion(VocAbortion aAbortion) {theAbortion = aAbortion;}

	/** Аборта */
	private VocAbortion theAbortion;
	
	/** Дата печати */
	@Comment("Дата печати")
	public Date getPrintDate() {return thePrintDate;}
	public void setPrintDate(Date aPrintDate) {thePrintDate = aPrintDate;}

	/** Дата печати */
	private Date thePrintDate;
	
	/** Время печати */
	@Comment("Время печати")
	public Time getPrintTime() {return thePrintTime;}
	public void setPrintTime(Time aPrintTime) {thePrintTime = aPrintTime;}

	/** Время печати */
	private Time thePrintTime;
	
	/** Пользователь, посл. распечат. документ */
	@Comment("Пользователь, посл. распечат. документ")
	public String getPrintUsername() {return thePrintUsername;}
	public void setPrintUsername(String aPrintUsername) {thePrintUsername = aPrintUsername;}

	/** Пользователь, посл. распечат. документ */
	private String thePrintUsername;

}
