package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип госпитального обслуживания 
 * (круглосуточная, дневная, на дому, стационар дневного пребывания, гостиничное)
 * @author azviagin
 *
 */

@Comment("Тип госпитального обслуживания")
@Entity
@Table(schema="SQLUser")
public class VocHospitalServiceType extends VocBaseEntity{

}
