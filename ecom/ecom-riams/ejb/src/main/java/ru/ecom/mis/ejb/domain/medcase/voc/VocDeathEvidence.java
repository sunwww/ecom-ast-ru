package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Основания установления смерти
 * 		1. осмотр трупа
 * 		2. записи в медицинской документации
 * 		3. предшествующего наблюдения за больным
 * 		4. вскрытие
 * @author stkacheva
 *
 */
@Entity
@Comment("Основания установления смерти")
@Table(schema="SQLUser")
public class VocDeathEvidence extends VocBaseEntity{

}
