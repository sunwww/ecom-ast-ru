package ru.ecom.mis.ejb.domain.prescription.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Справочник причин отмены назначений
 * @author azviagin
 *
 */

@Comment("Справочник причин отмены назначений")
@Entity
@Table(schema="SQLUser")
@AIndexes({
		@AIndex(properties = {"serviceType","biomaterial"})
})
public class VocPrescriptCancelReason extends VocBaseEntity{
	/** Код типа */
	@Comment("Код типа")
	public String getServiceType() {return theServiceType;}
	public void setServiceType(String aServiceType) {theServiceType = aServiceType;}

	/** Код типа */
	private String theServiceType;
	
	/** Тип биоматериала */
	@Comment("Тип биоматериала")
	public String getBiomaterial() {return theBiomaterial;}
	public void setBiomaterial(String aBiomaterial) {theBiomaterial = aBiomaterial;}

	/** Тип биоматериала */
	private String theBiomaterial;
	
	/** Дополнительные данные надо указывать */
	@Comment("Дополнительные данные надо указывать")
	public Boolean getAdditionData() {return theAdditionData;}
	public void setAdditionData(Boolean aAdditionData) {theAdditionData = aAdditionData;}

	/** Дополнительные данные надо указывать */
	private Boolean theAdditionData;
}
