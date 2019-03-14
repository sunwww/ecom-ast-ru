package ru.ecom.mis.ejb.domain.birth;

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
public abstract class Inspection extends BaseEntity{
	
	/** Кто проводил осмотр */
	@Comment("Кто проводил осмотр")
	public String getInspector() {return theInspector;}
	public void setInspector(String aInspector) {theInspector = aInspector;}

	
	/** Дата осмотра */
	@Comment("Дата осмотра")
	public Date getInspectionDate() {return theInspectionDate;}
	public void setInspectionDate(Date aInspectionDate) {theInspectionDate = aInspectionDate;}


	/** Время осмотра */
	@Comment("Время осмотра")
	public Time getInspectionTime() {return theInspectionTime;}
	public void setInspectionTime(Time aInspectionTime) {theInspectionTime = aInspectionTime;}
	
	/** Беременность */
	@Comment("Беременность")
	@ManyToOne
	public Pregnancy getPregnancy() {return thePregnancy;}
	public void setPregnancy(Pregnancy aPregnancy) {thePregnancy = aPregnancy;}

	/** История родов */
	@Comment("История родов")
	@ManyToOne
	public PregnancyHistory getPregnancyHistory() {return thePregnancyHistory;}
	public void setPregnancyHistory(PregnancyHistory aPregnancyHistory) {thePregnancyHistory = aPregnancyHistory;}

	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** Специалист, проводивший осмотр */
	@Comment("Специалист, проводивший осмотр")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Описание */
	@Comment("Описание")
	@Column(length=255)
	public String getNotes() {return theNotes;}
	public void setNotes(String aNotes) {theNotes = aNotes;}

	@Transient
	public String getInformation() {
		return "Осмотр №" + getId();
	}
	
	@Transient
	public String getWorkFunctionInfo() {
		return theWorkFunction!=null? theWorkFunction.getWorkFunctionInfo():theInspector ;
	}
	
	@Transient
	public String getTypeInformation() {
		return  this.getClass().getSimpleName();
	}
	/** Специалист, проводивший осмотр */
	private WorkFunction theWorkFunction;
	/** Случай медицинского обслуживания */
	private MedCase theMedCase;
	/** История родов */
	private PregnancyHistory thePregnancyHistory;
	/** Беременность */
	private Pregnancy thePregnancy;
	/** Кто проводил осмотр */
	private String theInspector;
	/** Дата осмотра */
	private Date theInspectionDate;
	/** Время осмотра */
	private Time theInspectionTime;
	
	/** Описание */
	private String theNotes;
}
