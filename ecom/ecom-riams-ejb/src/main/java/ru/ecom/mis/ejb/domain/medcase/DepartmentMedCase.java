package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcStandart;
import ru.ecom.mis.ejb.domain.lpu.BedFund;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Случай лечения в отделении")
@Entity
@Table(schema="SQLUser")
public class DepartmentMedCase extends HospitalMedCase {
	
	/** Дата перевода */
	@Comment("Дата перевода")
	public Date getTransferDate() {return theTransferDate;	}
	public void setTransferDate(Date aTransferDate) {theTransferDate = aTransferDate;}

	/** Время перевода */
	@Comment("Время перевода")
	public Time getTransferTime() {return theTransferTime;	}
	public void setTransferTime(Time aTransferTime) {theTransferTime = aTransferTime;}

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
	public String getRoomNumber() {return theRoomNumber;	}
	public void setRoomNumber(String aRoomNumber) {theRoomNumber = aRoomNumber;}
	
	/** № койки */
	@Comment("№ койки")
	public String getBedNumber() {return theBedNumber;}
	public void setBedNumber(String aBedNumber) {theBedNumber = aBedNumber;}
	
	/** Тип палаты */
	@Comment("Тип палаты")
	@OneToOne
	public VocRoomType getRoomType() {return theRoomType;}
	public void setRoomType(VocRoomType aRoomType) {theRoomType = aRoomType;}

	/** Тип палаты */
	private VocRoomType theRoomType;
	/** № койки */
	private String theBedNumber;


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
	private String theRoomNumber;
	/** Коечный фонд */
	private BedFund theBedFund;
	/** Предыдущий случай лечения в отделении */
	private MedCase thePrevMedCase;
	/** Отделение перевода */
	private MisLpu theTransferDepartment;
	/** Время перевода */
	private Time theTransferTime;
	/** Дата перевода */
	private Date theTransferDate;
	
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
		if (getParent()!=null && getParent() instanceof HospitalMedCase) {
			HospitalMedCase par = (HospitalMedCase) getParent() ;
			return par.getStatCardNumber() ;
		}
		return "" ;
	}

	/** Палата матери */
	private String theMotherWard;
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("СЛО ").append(getId()).append(" номер стат.карты СЛС ").append(getStatCardBySLS()) ;
		return ret.toString() ;
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
