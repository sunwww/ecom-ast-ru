package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник критериев живорождения: 1- Дыхание, 2-Сердцебиение, 3-Пульсация пуповины, 4-Определенные движения произвольной мускулатуры 
 * @author oegorova
 *
 */

@Entity
@Table(schema="SQLUser")
public class VocLivebirthCriterion extends VocBaseEntity {

}
