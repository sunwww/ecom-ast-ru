package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Место родов (в стационаре, на дому, в др.месте)
 * @author oegorova
 *
 */

@Entity
@Table(schema="SQLUser")
public class VocChildbirthPlace  extends VocBaseEntity{

}
