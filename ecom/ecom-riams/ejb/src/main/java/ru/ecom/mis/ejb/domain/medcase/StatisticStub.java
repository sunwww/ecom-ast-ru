package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPigeonHole;
import ru.ecom.mis.ejb.domain.medcase.voc.VocReasonDischarge;
import ru.ecom.mis.ejb.domain.medcase.voc.VocResultDischarge;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@AIndexes({
    @AIndex(properties="year"),
    @AIndex(properties="code")
    }) 
@Table(schema="SQLUser")
@Getter
@Setter
public abstract class StatisticStub extends BaseEntity {

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/**Информация (текст)*/
	@Transient
	@Comment("Информация")
	public String getInfo() {
		return "Номер стат.карты " + getCode() + "-" + getYear();
	}
	
	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@OneToOne
	public MisLpu getLpu() {return lpu;}

	/**Лечебное учреждение инфо */
	@Transient
	@Comment("Лечебное учреждение инфо")
	public String getLpuInfo() {
		return "";
	}
	
	
	/** Приемник */
	@Comment("Приемник")
	@OneToOne
	public VocPigeonHole getPigeonHole() {return pigeonHole;}

	/** Планово */
	private Boolean isPlan;
	/** Экстренно */
	private Boolean isEmergency;
	/** Приемник */
	private VocPigeonHole pigeonHole;
	/** Лечебное учреждение */
	private MisLpu lpu;
	/** СМО */
	private MedCase medCase;
	/** Код */
	private String code;
	/** Год */
	private Long year;
	/** Причина выписки */
	@Comment("Причина выписки")
	@OneToOne
	public VocReasonDischarge getReasonDischarge() {return reasonDischarge;}

	/** Причина выписки */
	private VocReasonDischarge reasonDischarge;
	
	/** Итог лечения */
	@Comment("Итог лечения")
	@OneToOne
	public VocResultDischarge getResultDischarge() {return resultDischarge;}

	/** Итог лечения */
	private VocResultDischarge resultDischarge;
	
	/** Номер архивного дела */
	private Long archiveCase;

	/** Рост */
	private Integer height;

	/** Вес */
	private Integer weight;

	/** Индекс массы тела */
	private Double iMT;

	/** Визит оформлен диетологом */
	private Boolean dietDone;

}
