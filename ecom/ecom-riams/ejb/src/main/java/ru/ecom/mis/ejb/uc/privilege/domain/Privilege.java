package ru.ecom.mis.ejb.uc.privilege.domain;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.licence.Document;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

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


	private Patient thePerson;
	private VocPrivilegeCategory theCategory;
	private String serialDoc;
	private String numberDoc;
	private Boolean isDelete;
	private Date theEndDate;
	private Date theBeginDate;

	private VocPrivilegeCode thePrivilegeCode;
	private Document theDocument;
	private String theTakeover;

/*	private VocIdc10 theIdc10;
	private boolean theActive;
	private Boolean theTakeover;
	private VocPrivilegeCode thePrivilegeCode;
	private List<DrugNeed> theDrugNeeds;*/


	@OneToOne
	public VocPrivilegeCode getThePrivilegeCode() {
		return thePrivilegeCode;
	}
	public void setThePrivilegeCode(VocPrivilegeCode thePrivilegeCode) {
		this.thePrivilegeCode = thePrivilegeCode;
	}
	@OneToOne
	public Document getTheDocument() {
		return theDocument;
	}
	public void setTheDocument(Document theDocument) {
		this.theDocument = theDocument;
	}

    public String getTheTakeover() {
        return theTakeover;
    }
    public void setTheTakeover(String theTakeover) {
        this.theTakeover = theTakeover;
    }

    @Comment("Категория льготников")
	@OneToOne
	public VocPrivilegeCategory getCategory() {return theCategory;}
	public void setCategory(VocPrivilegeCategory aCategory) {theCategory = aCategory;}

	@Comment("Дата начала действия")
	public Date getBeginDate() {return theBeginDate;}
	public void setBeginDate(Date aBeginDate) {theBeginDate = aBeginDate;}

	@Comment("Дата окончания действия")
	public Date getEndDate() {return theEndDate;}
	public void setEndDate(Date aEndDate) {theEndDate = aEndDate;}

	@Comment("Персона")
	@OneToOne
	public Patient getPerson() {return thePerson;}
	public void setPerson(Patient aPerson) {thePerson = aPerson;}

	@Comment("Серия документа")
	public String getSerialDoc() {
		return serialDoc;
	}
	public void setSerialDoc(String serialDoc) {
		this.serialDoc = serialDoc;
	}

	@Comment("Номер")
	public String getNumberDoc() {
		return numberDoc;
	}
	public void setNumberDoc(String numberDoc) {
		this.numberDoc = numberDoc;
	}

	@Comment("Признак удаления")
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

/*	@Comment("Потребность в лекарственных препаратах")
	@OneToMany(mappedBy="privilege", cascade=ALL)
	public List<DrugNeed> getDrugNeeds() {return theDrugNeeds;}
	public void setDrugNeeds(List<DrugNeed> aDrugNeeds) {theDrugNeeds = aDrugNeeds;}

	@Comment("Действительность")
	public boolean getActive() {return theActive;}
	public void setActive(boolean aActive) {theActive = aActive;}

	@Comment("МКБ 10")
	@OneToOne
	public VocIdc10 getIdc10() {return theIdc10;}
	public void setIdc10(VocIdc10 aIdc10) {theIdc10 = aIdc10;}

	@Comment("Код льготы")
	@OneToOne
	public VocPrivilegeCode getPrivilegeCode() {return thePrivilegeCode;}
	public void setPrivilegeCode(VocPrivilegeCode aPrivilegeCode) {thePrivilegeCode = aPrivilegeCode;}

	@Comment("Отказ от льготы")
	public Boolean getTakeover() {return theTakeover;}
	public void setTakeover(Boolean aTakeover) {theTakeover = aTakeover;}
*/
}
