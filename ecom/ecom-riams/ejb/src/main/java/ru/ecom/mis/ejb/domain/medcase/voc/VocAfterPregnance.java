package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Смерть после окончания беременности
 * 		1. - в течении 42 дней после окончания беременности, родов 
 * 				(от какой-либо причины, связанной с беременностью, отягощенной ею или ее ведением, 
 * 				но не от несчастного случая или от случайно возникшей причины)
 * 		2. - в течении 43-365 дней после окончания беременности , родов (от непосредственной
 * 				 акушерской причины или причины косвенно связанной с ней)
 * @author stkacheva
 *
 */
@Entity
@Comment("Смерть после окончания беременности")
@Table(schema="SQLUser")
public class VocAfterPregnance extends VocBaseEntity{

}
