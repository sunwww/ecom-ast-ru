package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Осмотр
 * @author azviagin
 *
 */
@Comment("Осмотр")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "pregnancy" }) 
	}
)
@Getter
@Setter
public abstract class Inspection extends BaseEntity{
	/** Беременность */
	@Comment("Беременность")
	@ManyToOne
	public Pregnancy getPregnancy() {return pregnancy;}

	/** История родов */
	@Comment("История родов")
	@ManyToOne
	public PregnancyHistory getPregnancyHistory() {return pregnancyHistory;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** Специалист, проводивший осмотр */
	@Comment("Специалист, проводивший осмотр")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}

	/** Описание */
	@Comment("Описание")
	@Column(length=255)
	public String getNotes() {return notes;}

	@Transient
	public String getInformation() {
		return "Осмотр №" + getId();
	}
	
	@Transient
	public String getWorkFunctionInfo() {
		return workFunction!=null? workFunction.getWorkFunctionInfo():inspector ;
	}
	
	@Transient
	public String getTypeInformation() {
		return  this.getClass().getSimpleName();
	}
	/** Специалист, проводивший осмотр */
	private WorkFunction workFunction;
	/** Случай медицинского обслуживания */
	private MedCase medCase;
	/** История родов */
	private PregnancyHistory pregnancyHistory;
	/** Беременность */
	private Pregnancy pregnancy;
	/** Кто проводил осмотр */
	private String inspector;
	/** Дата осмотра */
	private Date inspectionDate;
	/** Время осмотра */
	private Time inspectionTime;
	
	/** Описание */
	private String notes;
}
