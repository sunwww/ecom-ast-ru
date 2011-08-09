package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.disability.DisabilityCase;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospType;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Длительный СМО
 * @author oegorova
 *
 */
@Comment("Длительный СМО")
@Entity
@Table(schema="SQLUser")
public abstract class LongMedCase extends MedCase{
	
	
	/**
	 * Владелец
	 */
	@Comment("Владелец")
	@OneToOne
	public Worker getOwner() {return theOwner;	}
	public void setOwner(Worker aNewProperty) {theOwner = aNewProperty;}

	/** МКБ10*/
	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aNewProperty) {theIdc10 = aNewProperty;	}

	/** Кто завершил */
	@Comment("Кто завершил")
	@OneToOne
	public Worker getFinishWorker() {return theFinishWorker;	}
	public void setFinishWorker(Worker aFinishWorker) {theFinishWorker = aFinishWorker;}

	/** Дата окончания */
	@Comment("Дата окончания")
	public Date getDateFinish() {return theDateFinish;	}
	public void setDateFinish(Date aNewProperty) {theDateFinish = aNewProperty;}

	/** Случаи нетрудоспособности */
	@Comment("Случаи нетрудоспособности")
	@OneToMany(mappedBy="medCase", cascade=CascadeType.ALL)
	public List<DisabilityCase> getDisabilityCases() {return theDisabilityCases;}
	public void setDisabilityCases(List<DisabilityCase> aDisabilityCases) {theDisabilityCases = aDisabilityCases;}

	/** Время поступления */
	@Comment("Время поступления")
	public Time getEntranceTime() {	return theEntranceTime;	}
	public void setEntranceTime(Time aEntranceTime) {theEntranceTime = aEntranceTime;}
	
	/** Время выписки */
	@Comment("Время выписки")
	public Time getDischargeTime() {return theDischargeTime;}
	public void setDischargeTime(Time aDischargeTime) {theDischargeTime = aDischargeTime;	}
	
	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}
	
	


	/** Рабочая функция лечащего врача */
	@Comment("Рабочая функция лечащего врача")
	@OneToOne
	public WorkFunction getOwnerFunction() {	return theOwnerFunction;}
	public void setOwnerFunction(WorkFunction aOwnerFunction) {	theOwnerFunction = aOwnerFunction;	}

	/** Рабочая функция лечащего врача */
	private WorkFunction theOwnerFunction;
	/** Отделение */
	private MisLpu theDepartment;
	/** Время выписки */
	private Time theDischargeTime;
	/** Время поступления */
	private Time theEntranceTime;
	/** Случаи нетрудоспособности */
	private List<DisabilityCase> theDisabilityCases;
	/** Дата окончания */
	private Date theDateFinish;
	/** Кто завершил */
	private Worker theFinishWorker;
	/** МКБ10 */
	private VocIdc10 theIdc10;
	/**Владелец*/
	@Deprecated
	private Worker theOwner;
	
	
	
	/** Откуда поступил */
	@Comment("Откуда поступил")
	@OneToOne
	public VocHospType getSourceHospType() {
		return theSourceHospType;
	}

	public void setSourceHospType(VocHospType aSourceHospType) {
		theSourceHospType = aSourceHospType;
	}

	/** Откуда поступил */
	private VocHospType theSourceHospType;
	
	/** Куда переведен */
	@Comment("Куда переведен")
	@OneToOne
	public VocHospType getTargetHospType() {
		return theTargetHospType;
	}

	public void setTargetHospType(VocHospType aTargetHospType) {
		theTargetHospType = aTargetHospType;
	}

	/** Куда переведен */
	private VocHospType theTargetHospType;
	

	
	
	
	// [start] Вычисляемые поля
	/** Длительность в днях */
	@Comment("Длительность в днях")
	@Transient
	public String getDuration() {
		return getDaysCount();
	}
	/** Владелец (текст) */
	@Comment("Владелец (текст)")
	@Transient @Deprecated
	public String getOwnerText() {
		return theOwner!=null ? theOwner.getDoctorInfo() : "";
	}
	/** Владелец (текст) */
	@Comment("Владелец (текст)")
	@Transient
	public String getOwnerFunctionInfo() {
		return theOwnerFunction!=null ? theOwnerFunction.getWorkFunctionInfo() : "";
	}

	/** Кто закрыл */
	@Comment("Кто закрыл")
	@Transient
	public String getFinishWorkerText() {
		return theFinishWorker!=null ? theFinishWorker.getDoctorInfo():"";
	}
	
	/** Количество дней */
	@Comment("Количество дней")
	@Transient
	public String getDaysCount() {
		return DurationUtil.getDuration(getDateStart(), getDateFinish());
	}

	/** Отделение (текст) */
	@Comment("Отделение (текст)")
	@Transient
	public String getDepartmentInfo() {
		return theDepartment!=null? theDepartment.getName():"";
	}
	// [end]

}
