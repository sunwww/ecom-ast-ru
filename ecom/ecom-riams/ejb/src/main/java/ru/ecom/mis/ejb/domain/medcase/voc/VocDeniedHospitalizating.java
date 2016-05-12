package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Comment("Стационар. Справочник отказов при госпитализации.")
@Table(schema="SQLUser")
public class VocDeniedHospitalizating extends VocBaseEntity{

}
