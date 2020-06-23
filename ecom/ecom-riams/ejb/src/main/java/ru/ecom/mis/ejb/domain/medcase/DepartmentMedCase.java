package ru.ecom.mis.ejb.domain.medcase;


import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcStandart;
import ru.ecom.mis.ejb.domain.lpu.BedFund;
import ru.ecom.mis.ejb.domain.lpu.HospitalBed;
import ru.ecom.mis.ejb.domain.lpu.HospitalRoom;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Comment("Случай лечения в отделении")
@Entity
@AIndexes({
	@AIndex(properties="bedNumber", table="MedCase")
	,@AIndex(properties="department", table="MedCase")
	,@AIndex(properties="dateFinish", table="MedCase")
	,@AIndex(properties="transferDate", table="MedCase")
    ,@AIndex(properties="prevMedCase", table="MedCase")
}) 
public class DepartmentMedCase extends HospitalMedCase {

	@Comment("Главный диагноз случая")
	@Transient
	public Diagnosis getMainDiagnosis () {
		for (Diagnosis diagnosis : getDiagnoses()) {
			if ("1".equals(diagnosis.getPriority().getCode()) && "4".equals(diagnosis.getRegistrationType().getCode())) return diagnosis;
		}
		return null;
	}
	
	/** Отделение перевода */
	@Comment("Отделение перевода")
	@OneToOne
	public MisLpu getTransferDepartment() {return theTransferDepartment;}
	public void setTransferDepartment(MisLpu aTransferDepartment) {theTransferDepartment = aTransferDepartment;}

	/** Предыдущий случай лечения в отделении */
	@Comment("Предыдущий случай лечения в отделении")
	@OneToOne
	public MedCase getPrevMedCase() {return thePrevMedCase;}
	public void setPrevMedCase(MedCase aPrevMedCase) {thePrevMedCase = aPrevMedCase;}
	
	/** Коечный фонд */
	@Comment("Коечный фонд")
	@OneToOne
	public BedFund getBedFund() {return theBedFund;	}
	public void setBedFund(BedFund aBedFund) {theBedFund = aBedFund;}

	/** № палаты */
	@Comment("№ палаты")
	@OneToOne
	public HospitalRoom getRoomNumber() {return theRoomNumber;	}
	public void setRoomNumber(HospitalRoom aRoomNumber) {theRoomNumber = aRoomNumber;}
	
	/** № койки */
	@Comment("№ койки")
	@OneToOne
	public HospitalBed getBedNumber() {return theBedNumber;}
	public void setBedNumber(HospitalBed aBedNumber) {theBedNumber = aBedNumber;}
	
	/** Тип палаты */
	@Comment("Тип палаты")
	@OneToOne
	public VocRoomType getRoomType() {return theRoomType;}
	public void setRoomType(VocRoomType aRoomType) {theRoomType = aRoomType;}

	/** Тип палаты */
	private VocRoomType theRoomType;
	/** № койки */
	private HospitalBed theBedNumber;


	@Transient
	@OneToOne
	@Comment("Лечебное учреждение")
	public MisLpu getLpu() {
		MisLpu lpu=null; 
		if (getParent()!=null) {
			lpu = getParent().getLpu() ;
		}
		return  lpu ;
	}
	@Transient
	@Comment("Отделение перевода. Инфо")
	public String getTransferDepartmentInfo(){
		
		return theTransferDepartment!=null?theTransferDepartment.getName():"";
	}
	@Transient
	@Comment("Отделение поступления. Инфо")
	@Column(length = 255)
	public String getDepartmentInfo() {
		return getDepartment()!=null? getDepartment().getName():"";
	}
	@Comment("Количество дней")
	@Transient
	public String getDaysCount() {
		return getTransferDate()!=null 
			? DurationUtil.getDurationMedCase(getDateStart(), getTransferDate(),1)
					: DurationUtil.getDurationMedCase(getDateStart(), getDateFinish(),1)
			;
	}
	/** № палаты */
	private HospitalRoom theRoomNumber;
	/** Коечный фонд */
	private BedFund theBedFund;
	/** Предыдущий случай лечения в отделении */
	private MedCase thePrevMedCase;
	/** Отделение перевода */
	private MisLpu theTransferDepartment;

	
	/** Палата матери */
	@Comment("Палата матери")
	public String getMotherWard() {
		return theMotherWard;
	}

	public void setMotherWard(String aMotherWard) {
		theMotherWard = aMotherWard;
	}
	@Transient
	public String getStatCardBySLS() {
		if (getParent() instanceof HospitalMedCase) {
			HospitalMedCase par = (HospitalMedCase) getParent() ;
			return par.getStatCardNumber() ;
		}
		return "" ;
	}

	/** Палата матери */
	private String theMotherWard;
	@Transient
	public String getInfo() {
		Patient patient = getPatient();
		return "Пациент " +
				patient.getLastname()+" "+ patient.getFirstname()
				+" " +(patient.getMiddlename()!=null ? patient.getMiddlename():"")+ " номер стат.карты " + getStatCardBySLS();
	}
	/** Стандарт */
	@Comment("Стандарт")
	@OneToOne
	public OmcStandart getOmcStandart() {
		return theOmcStandart;
	}

	public void setOmcStandart(OmcStandart aOmcStandart) {
		theOmcStandart = aOmcStandart;
	}

	/** Стандарт */
	private OmcStandart theOmcStandart;
	
	/** Омс стандарт, установленный экспертом */
	@Comment("Омс стандарт, установленный экспертом")
	@OneToOne
	public OmcStandart getOmcStandartExpert() {
		return theOmcStandartExpert;
	}

	public void setOmcStandartExpert(OmcStandart aOmcStandartExpert) {
		theOmcStandartExpert = aOmcStandartExpert;
	}

	/** Омс стандарт, установленный экспертом */
	private OmcStandart theOmcStandartExpert;

}
