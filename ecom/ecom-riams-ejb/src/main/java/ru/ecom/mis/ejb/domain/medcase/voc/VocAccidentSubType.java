package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник видов травм, не связанных с производством
 * 		1. бытовая
 * 		2. уличная (кроме транспортной)
 * 		3. дорожно-транспортная
 * 		4. школьная
 * 		5. спортивная
 * 		6. прочие
 * @author oegorova
 *
 */

@Entity
@Comment("Справочник видов травм, не связанных с производством")
@Table(schema="SQLUser")
public class VocAccidentSubType extends VocBaseEntity{

}
