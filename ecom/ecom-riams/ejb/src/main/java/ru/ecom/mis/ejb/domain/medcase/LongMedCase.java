package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHospType;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Time;

/**
 * Длительный СМО
 * @author oegorova
 *
 */
@Comment("Длительный СМО")
@Entity
@AIndexes({
	@AIndex(properties="ownerFunction", table="MedCase")
}) 
public abstract class LongMedCase extends MedCase {

	/** МКБ10*/
	@Comment("МКБ10")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aNewProperty) {theIdc10 = aNewProperty;	}

	/** Кто завершил */
	@Comment("Кто завершил")
	@OneToOne
	public WorkFunction getFinishFunction() {return theFinishFunction;	}
	public void setFinishFunction(WorkFunction aFinishWorker) {theFinishFunction = aFinishWorker;}

	/** Время поступления */
	@Comment("Время поступления")
	public Time getEntranceTime() {	return theEntranceTime;	}
	public void setEntranceTime(Time aEntranceTime) {theEntranceTime = aEntranceTime;}
	
	/** Время выписки */
	@Comment("Время выписки")
	public Time getDischargeTime() {return theDischargeTime;}
	public void setDischargeTime(Time aDischargeTime) {theDischargeTime = aDischargeTime;	}

	/** Рабочая функция лечащего врача */
	@Comment("Рабочая функция лечащего врача")
	@OneToOne
	public WorkFunction getOwnerFunction() {	return theOwnerFunction;}
	public void setOwnerFunction(WorkFunction aOwnerFunction) {	theOwnerFunction = aOwnerFunction;	}


	/** Рабочая функция лечащего врача */
	private WorkFunction theOwnerFunction;

	/** Время выписки */
	private Time theDischargeTime;
	/** Время поступления */
	private Time theEntranceTime;

	/** Кто завершил */
	private WorkFunction theFinishFunction;
	/** МКБ10 */
	private VocIdc10 theIdc10;
	
	/** Откуда поступил */
	@Comment("Откуда поступил")
	@OneToOne
	public VocHospType getSourceHospType() {
		return theSourceHospType;
	}
	public void setSourceHospType(VocHospType aSourceHospType) {
		theSourceHospType = aSourceHospType;
	}
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
	private VocHospType theTargetHospType;

	
	// [start] Вычисляемые поля
	/** Длительность в днях */
	@Comment("Длительность в днях")
	@Transient
	public String getDuration() {
		return getDaysCount();
	}
	
	/** Количество дней */
	@Comment("Количество дней")
	@Transient
	public String getDaysCount() {
		return DurationUtil.getDuration(getDateStart(), getDateFinish());
	}
}