package ru.ecom.mis.ejb.uc.privilege.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Классификатор лекарственных средств
 * @author azviagin
 *
 */
@Comment("Классификатор лекарственных средств")
@Entity
@Table(schema="SQLUser")
public class DrugClassificator extends BaseEntity{
	
	/** Наименование */
	@Comment("Наименование")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	
	/** Дети */
	@Comment("Дети")
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	public List<DrugClassificator> getChildren() {return theChildren;}
	public void setChildren(List<DrugClassificator> aChildren) {theChildren = aChildren;}
	
	/** Родитель */
	@Comment("Родитель")
	@ManyToOne
	public DrugClassificator getParent() {return theParent;}
	public void setParent(DrugClassificator aParent) {theParent = aParent;}
	
	/** Позиции классификаторов лекарственных средств */
	@Comment("Позиции классификаторов лекарственных средств")
	@OneToMany(mappedBy="drugClassificator", cascade=CascadeType.ALL)
	public List<DrugClassificatorPosition> getDrugClassificatorPosition() {return theDrugClassificatorPosition;}
	public void setDrugClassificatorPosition(List<DrugClassificatorPosition> aDrugClassificatorPosition) {theDrugClassificatorPosition = aDrugClassificatorPosition;}

	/** Пользователь */
	@Comment("Пользователь")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Дата создания */
	private Date theCreateDate;
	/** Пользователь */
	private String theUsername;
	/** Наименование */
	private String theName;
	/** Дети */
	private List<DrugClassificator> theChildren;
	/** Родитель */
	private DrugClassificator theParent;
	/** Позиции классификаторов лекарственных средств */
	private List<DrugClassificatorPosition> theDrugClassificatorPosition;

}
