package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Категория льготы")
@Table(schema="SQLUser")
public class VocPrivilegeCategory extends VocIdNameOmcCode {
}
