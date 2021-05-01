package ru.ecom.mis.ejb.domain.disability;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.util.DurationUtil;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityRegime;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Запись сроков нетрудоспособности
 * @author azviagin,stkacheva,rkurbanov
 *
 */
@Entity
@AIndexes({
		@AIndex(properties= {"dateFrom"})
		,@AIndex(properties= {"dateTo"})
		,@AIndex(properties= {"disabilityDocument"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class DisabilityRecord extends BaseEntity{

	private String docName;
	private String docRole;
	private String vkName;
	private String vkRole;
	private Boolean isExport=false;
	private WorkFunction workFunctionAdd;
	private WorkFunction workFunction;
	private DisabilityDocument disabilityDocument;
	private Date dateFrom;
	private Date dateTo;
	private VocDisabilityRegime regime;
	private MedCase createMedCase;


	@Comment("Документ нетрудоспособности")
	@ManyToOne
	public DisabilityDocument getDisabilityDocument() {return disabilityDocument;}

	/**Дата создания*/
	private Date createDate;

	/**Время создания*/
	private Time createTime;

	@Comment("Режим нетрудоспособности")
	@OneToOne
	public VocDisabilityRegime getRegime() {return regime;}

	@Comment("СМО, создавшего запись")
	@OneToOne
	public MedCase getCreateMedCase() {return createMedCase;}

	@Comment("Специалист")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}

	@Comment("Режим нетрудоспособности (текст)")
	@Transient
	public String getRegimeText() {
		return regime!=null?regime.getName():"";
	}

	@Comment("Информация о записи")
	@Transient
	public String getInfo() {
		return getRegimeText() + " " +
				DurationUtil.getDuration(getDateFrom(), getDateTo());
	}

	@Comment("Рабочая функция инфо")
	@Transient
	public String getWorkFunctionInfo() {return workFunction!=null?workFunction.getWorkFunctionInfo():"";}

	@Comment("Рабочая функция председ. ВК инфо")
	@Transient
	public String getWorkFunctionAddInfo() {return workFunctionAdd!=null?workFunctionAdd.getWorkFunctionInfo():"";}

	@Comment("Доп. рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunctionAdd() {return workFunctionAdd;}

}
