package ru.medos.ejb.persdata.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Внешний электронный носитель
	 */
	@Comment("Внешний электронный носитель")
@Entity
@Table(schema="SQLUser")
public class ExternalCarrier extends JournalData{
	/**
	 * Номер носителя
	 */
	@Comment("Номер носителя")
	
	public String getExternalCarrierNumber() {
		return theExternalCarrierNumber;
	}
	public void setExternalCarrierNumber(String aExternalCarrierNumber) {
		theExternalCarrierNumber = aExternalCarrierNumber;
	}
	/**
	 * Номер носителя
	 */
	private String theExternalCarrierNumber;
	@OneToMany(mappedBy="externalCarrier", cascade=CascadeType.ALL)
	public List<DataOperation> getOperations() {
		return theOperations;
	}
	public void setOperations(List<DataOperation> aOperations) {
		theOperations = aOperations;
	}
	/**
	 * Операции
	 */
	private List<DataOperation> theOperations;
	/**
	 * Акт уничтожения внешних носителей
	 */
	@Comment("Акт уничтожения внешних носителей")
	@ManyToOne
	public ExternalCarrierDestructionAct getDestructionAct() {
		return theDestructionAct;
	}
	public void setDestructionAct(ExternalCarrierDestructionAct aDestructionAct) {
		theDestructionAct = aDestructionAct;
	}
	/**
	 * Акт уничтожения внешних носителей
	 */
	private ExternalCarrierDestructionAct theDestructionAct;
}
