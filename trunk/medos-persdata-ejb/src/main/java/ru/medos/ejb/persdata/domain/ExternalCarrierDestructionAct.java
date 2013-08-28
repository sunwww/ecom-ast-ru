package ru.medos.ejb.persdata.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.medos.personaldata.ExternalCarrier;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Акт уничтожения внешних носителей
	 */
	@Comment("Акт уничтожения внешних носителей")
@Entity
@Table(schema="SQLUser")
public class ExternalCarrierDestructionAct extends Act{
	@OneToMany(mappedBy="destructionAct", cascade=CascadeType.ALL)
	public List<ExternalCarrier> getExternalCarriers() {
		return theExternalCarriers;
	}
	public void setExternalCarriers(List<ExternalCarrier> aExternalCarriers) {
		theExternalCarriers = aExternalCarriers;
	}
	/**
	 * Внешние носители
	 */
	private List<ExternalCarrier> theExternalCarriers;
}
