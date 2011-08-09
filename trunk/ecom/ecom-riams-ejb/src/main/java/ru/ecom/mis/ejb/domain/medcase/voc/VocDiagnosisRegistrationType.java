package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип регистрации диагноза 
 * (при поступлении, направлении, выписке, клинический, патанатомический)  
 * @author azviagin
 *
 */
@Comment("Тип регистрации диагноза")
@Entity
@Table(schema="SQLUser")
public class VocDiagnosisRegistrationType extends VocBaseEntity{

}
