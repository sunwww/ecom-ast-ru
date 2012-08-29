package ru.ecom.mis.ejb.domain.licence;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * 
	 */
	@Comment("")
@Entity
@Table(schema="SQLUser")
public class ExternalMedservice extends ExternalDocument{
}
