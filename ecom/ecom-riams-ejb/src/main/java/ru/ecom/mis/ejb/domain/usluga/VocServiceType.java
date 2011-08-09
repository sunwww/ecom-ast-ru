package ru.ecom.mis.ejb.domain.usluga;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocIdCodeName;

/**
 *      Тип услуги
 *          Заполняется из справочника TARIFS.DBF
 *          файла у меня нет, поля ставлю стандартные
 */
@Entity
@Table(schema="SQLUser")
public class VocServiceType extends VocIdCodeName {
 
 

}
