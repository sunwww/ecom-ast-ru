package ru.ecom.mis.ejb.domain.disability;

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
		@AIndex(unique = false, properties= {"dateFrom"})
		,@AIndex(unique = false, properties= {"dateTo"})
		,@AIndex(unique = false, properties= {"disabilityDocument"})
})
@Table(schema="SQLUser")
public class DisabilityRecord extends BaseEntity{

	private String docName;
	private String docRole;
	private String vkName;
	private String vkRole;
	private Boolean theIsExport=false;
	private WorkFunction theWorkFunctionAdd;
	private WorkFunction theWorkFunction;
	private DisabilityDocument theDisabilityDocument;
	private Date theDateFrom;
	private Date theDateTo;
	private VocDisabilityRegime theRegime;
	private MedCase theCreateMedCase;


	@Comment("Документ нетрудоспособности")
	@ManyToOne
	public DisabilityDocument getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(DisabilityDocument aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	@Comment("Дата начала нетрудоспособности")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	@Comment("Дата окончания нетрудоспособности")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;	}

	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	private Date theCreateDate;

	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	private Time theCreateTime;

	@Comment("Режим нетрудоспособности")
	@OneToOne
	public VocDisabilityRegime getRegime() {return theRegime;}
	public void setRegime(VocDisabilityRegime aRegime) {theRegime = aRegime;}

	@Comment("СМО, создавшего запись")
	@OneToOne
	public MedCase getCreateMedCase() {return theCreateMedCase;}
	public void setCreateMedCase(MedCase aCreateMedCase) {theCreateMedCase = aCreateMedCase;}

	@Comment("Специалист")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	@Comment("Режим нетрудоспособности (текст)")
	@Transient
	public String getRegimeText() {
		return theRegime!=null?theRegime.getName():"";
	}

	@Comment("Информация о записи")
	@Transient
	public String getInfo() {
		return getRegimeText() + " " +
				DurationUtil.getDuration(getDateFrom(), getDateTo());
	}

	@Comment("Рабочая функция инфо")
	@Transient
	public String getWorkFunctionInfo() {return theWorkFunction!=null?theWorkFunction.getWorkFunctionInfo():"";}

	@Comment("Рабочая функция председ. ВК инфо")
	@Transient
	public String getWorkFunctionAddInfo() {return theWorkFunctionAdd!=null?theWorkFunctionAdd.getWorkFunctionInfo():"";}

	@Comment("Доп. рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunctionAdd() {return theWorkFunctionAdd;}
	public void setWorkFunctionAdd(WorkFunction aWorkFunctionAdd) {theWorkFunctionAdd = aWorkFunctionAdd;}

	@Comment("Экспортировано")
	public Boolean getIsExport(){return theIsExport;}
	public void setIsExport(Boolean aIsExport) {theIsExport = aIsExport;}

	@Comment("ФИО врача")
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}

	@Comment("Роль врача")
	public String getDocRole() {
		return docRole;
	}
	public void setDocRole(String docRole) {
		this.docRole = docRole;
	}

	@Comment("ФИО ВК")
	public String getVkName() {
		return vkName;
	}
	public void setVkName(String vkName) {
		this.vkName = vkName;
	}

	@Comment("Роль ВК")
	public String getVkRole() {
		return vkRole;
	}
	public void setVkRole(String vkRole) {
		this.vkRole = vkRole;
	}


}
