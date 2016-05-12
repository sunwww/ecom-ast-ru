package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник втяжения межреберных промежутков по Downes
 * @author azviagin
 *
 */

@Comment("Справочник втяжения межреберных промежутков по Downes")
@Entity
@Table(schema="SQLUser")
public class VocDownesIntercostalRet extends VocBall{

}
