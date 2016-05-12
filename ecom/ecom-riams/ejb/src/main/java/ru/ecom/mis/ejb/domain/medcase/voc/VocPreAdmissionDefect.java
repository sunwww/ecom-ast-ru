package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник догоспитального этапа:
 * 	несвоевременность госпитализации, 
 * 	недостаточный объем клинико-диагностического обследования, 
 * 	неправильная тактика лечения, 
 * 	несовпадение диагноза
 * @author stkacheva
 */
@Entity
@Comment("Справочник дефектов догоспитального этапа")
@Table(schema="SQLUser")
public class VocPreAdmissionDefect extends VocBaseEntity{

}
