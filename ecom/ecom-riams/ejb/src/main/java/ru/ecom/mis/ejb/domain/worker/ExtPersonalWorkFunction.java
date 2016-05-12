package ru.ecom.mis.ejb.domain.worker;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Сотрудник из другой организации
 */
@Entity
@Table(schema="SQLUser")
public class ExtPersonalWorkFunction extends GroupWorkFunction {

}
