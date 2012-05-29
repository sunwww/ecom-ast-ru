package ru.ecom.mis.ejb.domain.medcase.voc;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник операций")
@Table(schema="SQLUser")
@AIndexes
({@AIndex(unique = false, properties = {"codeF"})})
public class VocOperation extends VocBaseEntity{
	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	public Date getStartActualDate() {return theStartActualDate;}
	public void setStartActualDate(Date aStartActualDate) {theStartActualDate = aStartActualDate;}

	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	public Date getFinishActualDate() {return theFinishActualDate;}
	public void setFinishActualDate(Date aFinishActualDate) {theFinishActualDate = aFinishActualDate;}

	/** Уровонь сложности */
	@Comment("Уровонь сложности")
	public Long getComplexity() {return theComplexity;}
	public void setComplexity(Long aComplexity) {theComplexity = aComplexity;}

	/** Код федеральный */
	@Comment("Код федеральный")
	public String getCodeF() {return theCodeF;}
	public void setCodeF(String aCodeF) {theCodeF = aCodeF;}

	/** Отделения */
	@Comment("Отделения")
	@ManyToMany
	public List<MisLpu> getDepartments() {return theDepartments;}
	public void setDepartments(List<MisLpu> aDepartments) {theDepartments = aDepartments;}

	/** Отделения */
	private List<MisLpu> theDepartments;
	/** Код федеральный */
	private String theCodeF;
	/** Уровонь сложности */
	private Long theComplexity;
	/** Дата окончания актуальности */
	private Date theFinishActualDate;
	/** Дата начала актуальности */
	private Date theStartActualDate;

}
