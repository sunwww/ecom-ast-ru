package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип причины смерти:
 * 		1. Болезнь или состояние, непосредственно приведшее к смерти
 * 		2. Патологические состояния, которые привели к возникновению вышеуказанной причины
 * 		3. Основная причина смерти, указывается последней
 * 		4. Внешние причины при травмах и отравлениях
 * 		5. Прочие важные состояния, способствовавшие смерти, но не связанные с болезнью или патологическим состоянием, приведшим к ней
 * @author stkacheva
 */
@Entity
@Comment("Тип причин смерти")
@Table(schema="SQLUser")
public class VocDeathReasonType  extends VocBaseEntity{

}
