package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityRegime;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Запись сроков нетрудоспособности 
 * @author azviagin,stkacheva
 *
 */
@Entity
@AIndexes({
	@AIndex(unique = false, properties= {"dateFrom"})
	,@AIndex(unique = false, properties= {"dateTo"})
    ,@AIndex(unique = false, properties= {"disabilityDocument"})
})
@Table(schema="SQLUser")
public class DisabilityRecord extends BaseEntity{

	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@ManyToOne
	public DisabilityDocument getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(DisabilityDocument aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	/** Дата начала нетрудоспособности */
	@Comment("Дата начала нетрудоспособности")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	
	/** Дата окончания нетрудоспособности */
	@Comment("Дата окончания нетрудоспособности")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;	}

	
	/** Режим нетрудоспособности */
	@Comment("Режим нетрудоспособности")
	@OneToOne
	public VocDisabilityRegime getRegime() {return theRegime;}
	public void setRegime(VocDisabilityRegime aRegime) {theRegime = aRegime;}
	
	/** СМО, создавшего запись */
	@Comment("СМО, создавшего запись")
	@OneToOne
	public MedCase getCreateMedCase() {return theCreateMedCase;}
	public void setCreateMedCase(MedCase aCreateMedCase) {theCreateMedCase = aCreateMedCase;}

	/** Специалист */
	@Comment("Специалист")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Режим нетрудоспособности (текст) */
	@Comment("Режим нетрудоспособности (текст)")
	@Transient
	public String getRegimeText() {
		return theRegime!=null?theRegime.getName():"";
	}

	/** Информация о записи */
	@Comment("Информация о записи")
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		ret.append(getRegimeText()).append(" ") ;
		ret.append(DurationUtil.getDuration(getDateFrom(), getDateTo())) ;
		return ret.toString();
	}
	
	/** Рабочая функция инфо */
	@Comment("Рабочая функция инфо")
	@Transient
	public String getWorkFunctionInfo() {return theWorkFunction!=null?theWorkFunction.getWorkFunctionInfo():"";}
	
	/** Специалист */
	private WorkFunction theWorkFunction;
	/** Документ нетрудоспособности */
	private DisabilityDocument theDisabilityDocument;
	/** Дата начала нетрудоспособности */
	private Date theDateFrom;
	/** Дата окончания нетрудоспособности */
	private Date theDateTo;
	/** Режим нетрудоспособности */
	private VocDisabilityRegime theRegime;
	/** СМО, создавшего запись */
	private MedCase theCreateMedCase;
}
