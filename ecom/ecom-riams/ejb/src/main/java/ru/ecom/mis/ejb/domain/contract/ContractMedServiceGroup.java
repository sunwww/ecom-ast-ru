package ru.ecom.mis.ejb.domain.contract;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.contract.MedServiceInterval;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Группу медицинских услуг по договору
	 */
	@Comment("Группу медицинских услуг по договору")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(unique= false, properties = {"name"})

	})
public class ContractMedServiceGroup extends BaseEntity{
	/**
	 * Название
	 */
	@Comment("Название")
	
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
	@OneToMany(mappedBy="medServiceGroup", cascade=CascadeType.ALL)
	public List<MedServiceInterval> getIntervals() {
		return theIntervals;
	}
	public void setIntervals(List<MedServiceInterval> aIntervals) {
		theIntervals = aIntervals;
	}
	/**
	 * Интервалы медицинских услуг
	 */
	private List<MedServiceInterval> theIntervals;
}
