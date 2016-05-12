package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник положений плода (продольное, поперечное, косое, двойня)
 * @author oegorova
 *
 */
@Comment ("Справочник положений плода")
@Entity
@Table(schema="SQLUser")
public class VocFetusLocation extends VocBaseEntity{

}
