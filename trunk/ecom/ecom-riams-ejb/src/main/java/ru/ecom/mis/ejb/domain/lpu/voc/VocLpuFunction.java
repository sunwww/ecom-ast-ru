package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник функций ЛПУ
 * 1 - больничное учреждение
 * 2 - амбулаторно-поликлиническое учреждение
 * @author stkacheva
 */
@Entity
@Table(schema="SQLUser")
public class VocLpuFunction extends VocBaseEntity {

}