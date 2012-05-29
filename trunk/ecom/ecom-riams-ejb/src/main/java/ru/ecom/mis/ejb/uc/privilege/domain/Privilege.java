package ru.ecom.mis.ejb.uc.privilege.domain;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.licence.Document;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Льгота
 */
@Entity
@Comment("Льгота")
@Table(schema="SQLUser")
@AIndexes(
	@AIndex(properties={"person"})
)
public class Privilege extends BaseEntity {
	
	/** Категория льготников */
	@Comment("Категория льготников")
	@OneToOne
	public VocPrivilegeCategory getCategory() {return theCategory;}
	public void setCategory(VocPrivilegeCategory aCategory) {theCategory = aCategory;}

	/** Потребность в лекарственных препаратах */
	@Comment("Потребность в лекарственных препаратах")
	@OneToMany(mappedBy="privilege", cascade=ALL)
	public List<DrugNeed> getDrugNeeds() {return theDrugNeeds;}
	public void setDrugNeeds(List<DrugNeed> aDrugNeeds) {theDrugNeeds = aDrugNeeds;}

	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getBeginDate() {return theBeginDate;}
	public void setBeginDate(Date aBeginDate) {theBeginDate = aBeginDate;}

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getEndDate() {return theEndDate;}
	public void setEndDate(Date aEndDate) {theEndDate = aEndDate;}

	/** Действительность */
	@Comment("Действительность")
	public boolean getActive() {return theActive;}
	public void setActive(boolean aActive) {theActive = aActive;}

	/** МКБ 10 */
	@Comment("МКБ 10")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}


	/** Персона */
	@Comment("Персона")
	@ManyToOne
	public Patient getPerson() {return thePerson;}
	public void setPerson(Patient aPerson) {thePerson = aPerson;}

	/** Льготный документ */
	@Comment("Льготный документ")
	@OneToOne
	public Document getDocument() {return theDocument;}
	public void setDocument(Document aDocument) {theDocument = aDocument;}

	/** Код льготы */
	@Comment("Код льготы")
	@OneToOne
	public VocPrivilegeCode getPrivilegeCode() {return thePrivilegeCode;}
	public void setPrivilegeCode(VocPrivilegeCode aPrivilegeCode) {thePrivilegeCode = aPrivilegeCode;}
	
	@Transient
	public String getInfo() {
		StringBuilder ret = new StringBuilder() ;
		if (thePrivilegeCode!=null) ret.append(thePrivilegeCode.getCode()).append(". ").append(thePrivilegeCode.getName()) ;
		if (theDocument!=null) {
			ret.append(theDocument.getSeriaDoc()).append(" ").append(theDocument.getNumberDoc()) ;
		}
		return ret.toString() ;
	}
	
	/** Отказ от льготы */
	@Comment("Отказ от льготы")
	public Boolean getTakeover() {return theTakeover;}
	public void setTakeover(Boolean aTakeover) {theTakeover = aTakeover;}

	/** Отказ от льготы */
	private Boolean theTakeover;
	
	/** Код льготы */
	private VocPrivilegeCode thePrivilegeCode;
	/** Льготный документ */
	private Document theDocument;
	/** Персона */
	private Patient thePerson;
	/** МКБ 10 */
	private VocIdc10 theIdc10;
	/** Действительность */
	private boolean theActive;
	/** Дата окончания действия */
	private Date theEndDate;
	/** Дата начала действия */
	private Date theBeginDate;
	/** Потребность в лекарственных препаратах */
	private List<DrugNeed> theDrugNeeds;
	/** Категория льготников */
	private VocPrivilegeCategory theCategory;
}
