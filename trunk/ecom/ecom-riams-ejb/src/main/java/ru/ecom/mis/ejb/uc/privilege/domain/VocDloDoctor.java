package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocIdCodeName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("П код врача")
@Table(schema="SQLUser")
public class VocDloDoctor extends VocIdCodeName {

}
