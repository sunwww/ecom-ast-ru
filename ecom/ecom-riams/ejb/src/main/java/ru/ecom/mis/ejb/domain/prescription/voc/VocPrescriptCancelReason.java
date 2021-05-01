package ru.ecom.mis.ejb.domain.prescription.voc;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class VocPrescriptCancelReason extends VocBaseEntity{
	/** Код типа */
	private String serviceType;
	/** Тип биоматериала */
	private String biomaterial;
	/** Дополнительные данные надо указывать */
	private Boolean additionData;
}
