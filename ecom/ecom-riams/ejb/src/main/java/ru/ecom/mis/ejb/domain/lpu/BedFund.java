package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
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
		@AIndex(properties = {"lpu","serviceStream","bedType"})
		,@AIndex(properties = {"bedType"})
		,@AIndex(properties = {"lpu"})
		,@AIndex(properties = {"serviceStream"})
			,@AIndex(properties = {"bedSubType"})
		}
)
@Table(schema="SQLUser")
@Getter
@Setter
public class BedFund extends BaseEntity{

	/** Количество коек */
	private Integer amount;

	/** Тип развертывания-свертывания */
	@Comment("Тип развертывания-свертывания")
	@OneToOne
	public VocBedReductionType getReductionType() {
		return reductionType;
	}

	/** Тип развертывания-свертывания */
	private VocBedReductionType reductionType;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {
		return serviceStream;
	}

	/** Поток обслуживания */
	private VocServiceStream serviceStream;

	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {
		return bedType;
	}

	/** Профиль коек */
	private VocBedType bedType;

	/** ЛПУ */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getLpu() {
		return lpu;
	}

	/** ЛПУ */
	private MisLpu lpu;

	/** Для детей */
	private Boolean forChild;

	/** Тип свертывания (текст) */
	@Comment("Тип свертывания (текст)")
	@Transient
	public String getReductionTypeName() {
		return reductionType!=null? reductionType.getName():"";
	}

	/** Поток обслуживания (текст) */
	@Comment("Поток обслуживания (текст)")
	@Transient
	public String getServiceStreamName() {
		return serviceStream!=null?serviceStream.getName():"";
	}


	/** Профиль коек (текст) */
	@Comment("Профиль коек (текст)")
	@Transient
	public String getBedTypeName() {
		return bedType!=null?bedType.getName():"";
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
		return VTE;
	}

	/** Время окончания актуальности */
	private Timestamp VTE;

	/** Время начала актуальности */
	@Comment("Время начала актуальности")
	public Timestamp getVTS() {
		return VTS;
	}

	/** Время начала актуальности */
	private Timestamp VTS;

	/** Дата начала актуальности */
	private Date dateStart;

	/** Время начала актуальности */
	private Time timeStart;

	/** Дата окончания актуальности */
	private Date dateFinish;

	/** Время окончания актуальности */
	private Time timeFinish;

	/** Тип койки */
	@Comment("Тип койки")
	@OneToOne
	public VocBedSubType getBedSubType() {return bedSubType;}
	/** Тип койки */
	private VocBedSubType bedSubType;

	/** Тип койки (текст) */
	@Comment("Тип койки (текст)")
	@Transient
	public String getBedSubTypeName() {
		return bedSubType!=null? bedSubType.getName():"";
	}

	/** Без питания */
	private Boolean noFood;

	/** День выписки и день поступления считать разными днями */
	private Boolean addCaseDuration;

	/** Реабилитационные */
	private Boolean isRehab;

	/** По умолчанию снилс врача генерации направлений для 263 приказа */
	@Comment("По умолчанию снилс врача генерации направлений для 263 приказа")
	@Deprecated
	public String getSnilsDoctorDirect263() {return snilsDoctorDirect263;}
	/** По умолчанию снилс врача генерации направлений для 263 приказа */
	private String snilsDoctorDirect263;
	/**
	 * Код адреса отделения
	 */
	private String departmentAddressCode;
}
