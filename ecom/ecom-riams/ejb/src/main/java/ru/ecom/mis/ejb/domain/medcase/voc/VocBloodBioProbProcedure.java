package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник процедур в биологической пробе при переливаниях
 * @author Milamesher
 *
 */

@Comment("Справочник процедур в биологической пробе при переливаниях")
@Entity
@Table(schema="SQLUser")
public class VocBloodBioProbProcedure extends VocBaseEntity{

}