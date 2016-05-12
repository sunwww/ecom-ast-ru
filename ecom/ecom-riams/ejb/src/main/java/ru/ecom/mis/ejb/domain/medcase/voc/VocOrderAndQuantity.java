package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник порядка рождения ребенка: 1-При одноплодных родах, 
 * 2-Первым из двойни, 3-Вторым из двойни, 4-При других многоплодных родах
 * @author oegorova
 *
 */

@Entity
@Table(schema="SQLUser")
public class VocOrderAndQuantity extends VocBaseEntity {

}
