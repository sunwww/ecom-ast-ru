package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник. Причина смерти
 * @author oegorova
 */
@Comment("Справочник. Причина смерти")
@Entity
@Table(schema="SQLUser")
public class VocDeathCause extends VocBaseEntity{

}
