package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник влагалищных выделений (околоподные воды, кровянистые выделения, отсутствуют)
 * @author oegorova
 *
 */
@Comment ("Справочник влагалищных выделений")
@Entity
@Table(schema="SQLUser")
public class VocVaginalDischarge extends VocBaseEntity{

}
