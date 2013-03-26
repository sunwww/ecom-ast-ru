
package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Визит
 * @author oegorova
 *
 */
@Comment("Визит")
@Entity
@Table(schema="SQLUser")
public class Visit extends ShortMedCase{
	

}
