package ru.ecom.mis.ejb.domain.equipment.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Классификатор основных фондов")
@Table(schema="SQLUser")
public class VocOKOF extends VocIdName{

}
