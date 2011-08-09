package ru.ecom.mis.ejb.domain.mortality.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Возрастной период отчета по смертности
 * @author oegorova
 *
 */
@Comment("Возрастной период отчета по смертности")
@Entity
@Table(schema="SQLUser")
public class VocAgePeriod extends VocBaseEntity{

}
