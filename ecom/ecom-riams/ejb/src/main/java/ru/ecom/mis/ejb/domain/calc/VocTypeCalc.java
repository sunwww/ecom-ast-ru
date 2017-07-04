package ru.ecom.mis.ejb.domain.calc;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;



/** Типы калькуляции */
@Comment("Типы калькуляции")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class VocTypeCalc extends VocBaseEntity{
	
}
