package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник травм
 * 	производственная: промышленная - 1, транспортная - 2, в т.ч. ДТП - 3, с/хоз. - 4, прочие - 5
 * 	непроизводственная: бытовая - 6, уличная - 7, транспортная - 8, в т.ч. ДТП - 9, школьная - 10,
 * 		спортивная - 11, противоправная травма - 12, прочие - 13 
 * @author stkacheva
 *
 */
@Entity
@Comment("Справочник травм")
@Table(schema="SQLUser")
public class VocHospitalizationTrauma extends VocBaseEntity {

}
