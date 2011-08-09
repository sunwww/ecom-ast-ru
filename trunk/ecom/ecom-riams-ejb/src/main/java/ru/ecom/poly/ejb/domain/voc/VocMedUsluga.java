package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

//Справочник кодов мед. услуг (посещения, СМП, КЭС)
@Entity
@Table(schema="SQLUser")
public class VocMedUsluga extends VocBaseEntity {
}
