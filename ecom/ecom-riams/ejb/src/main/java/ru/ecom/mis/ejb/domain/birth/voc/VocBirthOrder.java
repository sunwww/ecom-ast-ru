package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Порядок родов
 *первые, вторые, третьи и последующие 
 ** @author azviagin
 *
 */
@Comment("Порядок родов")
@Entity
@Table(schema="SQLUser")
public class VocBirthOrder extends VocBaseEntity{

}
