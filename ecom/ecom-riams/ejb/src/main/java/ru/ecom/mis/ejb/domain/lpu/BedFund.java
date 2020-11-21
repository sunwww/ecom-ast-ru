package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedReductionType;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Коечный фонд
 * @author azviagin
 *
 */

@Comment("Коечный фонд")
@Entity
@AIndexes(
	{
		@AIndex(unique= false, properties = {"lpu","serviceStream","bedType"})
		,@AIndex(unique= false, properties = {"bedType"})
		,@AIndex(unique= false, properties = {"lpu"})
		,@AIndex(unique= false, properties = {"serviceStream"})
			,@AIndex(unique= false, properties = {"bedSubType"})
		}
)
@Table(schema="SQLUser")
public class BedFund extends BaseEntity{
	
	/** Количество коек */
	@Comment("Количество коек")
	public Integer getAmount() {
		return theAmount;
	}

	public void setAmount(Integer aAmount) {
		theAmount = aAmount;
	}

	/** Количество коек */
	private Integer theAmount;
	
	/** Тип развертывания-свертывания */
	@Comment("Тип развертывания-свертывания")
	@OneToOne
	public VocBedReductionType getReductionType() {
		return theReductionType;
	}

	public void setReductionType(VocBedReductionType aReductionType) {
		theReductionType = aReductionType;
	}

	/** Тип развертывания-свертывания */
	private VocBedReductionType theReductionType;
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {
		return theServiceStream;
	}

	public void setServiceStream(VocServiceStream aServiceStream) {
		theServiceStream = aServiceStream;
	}

	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {
		return theBedType;
	}

	public void setBedType(VocBedType aBedType) {
		theBedType = aBedType;
	}

	/** Профиль коек */
	private VocBedType theBedType;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getLpu() {
		return theLpu;
	}

	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}

	/** ЛПУ */
	private MisLpu theLpu;
	
	/** Для детей */
	@Comment("Для детей")
	public Boolean getForChild() {
		return theForChild;
	}

	public void setForChild(Boolean aForChild) {
		theForChild = aForChild;
	}

	/** Для детей */
	private Boolean theForChild;

	/** Тип свертывания (текст) */
	@Comment("Тип свертывания (текст)")
	@Transient
	public String getReductionTypeName() {
		return theReductionType!=null? theReductionType.getName():"";
	}
	

	public void setReductionTypeName(String aReductionTypeName) {
	
	}

	/** Поток обслуживания (текст) */
	@Comment("Поток обслуживания (текст)")
	@Transient
	public String getServiceStreamName() {
		return theServiceStream!=null?theServiceStream.getName():"";
	}

	public void setServiceStreamName(String aServiceStreamName) {
	}

	/** Профиль коек (текст) */
	@Comment("Профиль коек (текст)")
	@Transient
	public String getBedTypeName() {
		return theBedType!=null?theBedType.getName():"";
	}

	public void setBedTypeName(String aBedTypeName) {
	}
	
	/** Тип госпитального обслуживания (текст) */
	@Comment("Тип госпитального обслуживания (текст)")
	@Transient
	public String getServiceTypeName() {
		return "";
	}

	/** Время окончания актуальности */
	@Comment("Время окончания актуальности")
	public Timestamp getVTE() {
		return theVTE;
	}

	public void setVTE(Timestamp aVTE) {
		theVTE = aVTE;
	}

	/** Время окончания актуальности */
	private Timestamp theVTE;
	
	/** Время начала актуальности */
	@Comment("Время начала актуальности")
	public Timestamp getVTS() {
		return theVTS;
	}

	public void setVTS(Timestamp aVTS) {
		theVTS = aVTS;
	}

	/** Время начала актуальности */
	private Timestamp theVTS;

	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	public Date getDateStart() {
		return theDateStart;
	}

	public void setDateStart(Date aDateStart) {
		theDateStart = aDateStart;
	}

	/** Дата начала актуальности */
	private Date theDateStart;

	/** Время начала актуальности */
	@Comment("Время начала актуальности")
	public Time getTimeStart() {
		return theTimeStart;
	}

	public void setTimeStart(Time aTimeStart) {
		theTimeStart = aTimeStart;
	}

	/** Время начала актуальности */
	private Time theTimeStart;
	
	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	public Date getDateFinish() {
		return theDateFinish;
	}

	public void setDateFinish(Date aDateFinish) {
		theDateFinish = aDateFinish;
	}

	/** Дата окончания актуальности */
	private Date theDateFinish;
	
	/** Время окончания актуальности */
	@Comment("Время окончания актуальности")
	public Time getTimeFinish() {
		return theTimeFinish;
	}

	public void setTimeFinish(Time aTimeFinish) {
		theTimeFinish = aTimeFinish;
	}

	/** Время окончания актуальности */
	private Time theTimeFinish;
	
	/** Тип койки */
	@Comment("Тип койки")
	@OneToOne
	public VocBedSubType getBedSubType() {return theBedSubType;}
	public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;	}

	/** Тип койки */
	private VocBedSubType theBedSubType;
	
	/** Тип койки (текст) */
	@Comment("Тип койки (текст)")
	@Transient
	public String getBedSubTypeName() {
		return theBedSubType!=null? theBedSubType.getName():"";
	}
	
	/** Без питания */
	@Comment("Без питания")
	public Boolean getNoFood() {
		return theNoFood;
	}

	public void setNoFood(Boolean aNoFood) {
		theNoFood = aNoFood;
	}

	/** Без питания */
	private Boolean theNoFood;
	
	/** День выписки и день поступления считать разными днями */
	@Comment("День выписки и день поступления считать разными днями")
	public Boolean getAddCaseDuration() {return theAddCaseDuration;}
	public void setAddCaseDuration(Boolean aAddCaseDuration) {theAddCaseDuration = aAddCaseDuration;}

	/** День выписки и день поступления считать разными днями */
	private Boolean theAddCaseDuration;
	
	/** Реабилитационные */
	@Comment("Реабилитационные")
	public Boolean getIsRehab() {return theIsRehab;}
	public void setIsRehab(Boolean aIsRehab) {theIsRehab = aIsRehab;}

	/** Реабилитационные */
	private Boolean theIsRehab;
	
	/** По умолчанию снилс врача генерации направлений для 263 приказа */
	@Comment("По умолчанию снилс врача генерации направлений для 263 приказа")
	@Deprecated
	public String getSnilsDoctorDirect263() {return theSnilsDoctorDirect263;}
	public void setSnilsDoctorDirect263(String aSnilsDoctorDirect263) {theSnilsDoctorDirect263 = aSnilsDoctorDirect263;}

	/** По умолчанию снилс врача генерации направлений для 263 приказа */
	private String theSnilsDoctorDirect263;
}
