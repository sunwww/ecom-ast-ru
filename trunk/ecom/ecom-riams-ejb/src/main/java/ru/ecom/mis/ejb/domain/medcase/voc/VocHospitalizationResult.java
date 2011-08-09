package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник результатов госпитализации
 * 	выздоровление, улучшение, без перемен, ухудшение, здоров, умер
 */
@Entity
@Comment("Справочник результатов госпитализации")
@Table(schema="SQLUser")
public class VocHospitalizationResult extends VocBaseEntity {

}
