package ru.ecom.mis.ejb.uc.privilege.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class DrugClassificator extends BaseEntity{
	

	/** Дети */
	@Comment("Дети")
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL)
	public List<DrugClassificator> getChildren() {return children;}

	/** Родитель */
	@Comment("Родитель")
	@ManyToOne
	public DrugClassificator getParent() {return parent;}

	/** Позиции классификаторов лекарственных средств */
	@Comment("Позиции классификаторов лекарственных средств")
	@OneToMany(mappedBy="drugClassificator", cascade=CascadeType.ALL)
	public List<DrugClassificatorPosition> getDrugClassificatorPosition() {return drugClassificatorPosition;}

	/** Дата создания */
	private Date createDate;
	/** Пользователь */
	private String username;
	/** Наименование */
	private String name;
	/** Дети */
	private List<DrugClassificator> children;
	/** Родитель */
	private DrugClassificator parent;
	/** Позиции классификаторов лекарственных средств */
	private List<DrugClassificatorPosition> drugClassificatorPosition;

}
