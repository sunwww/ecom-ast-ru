package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник по кол-ву коек в палате")
@Entity
@Table(schema="SQLUser")
public class VocCountBedInHospitalRoom extends VocBaseEntity{

}
