package ru.ecom.ejb.domain.simple;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Справочник
 */
@MappedSuperclass
public class VocBaseEntity extends  VocIdCodeName {

}
