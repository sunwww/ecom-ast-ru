package ru.ecom.mis.ejb.domain.medcase.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Справочник профилей КИЛИ")
@Entity
@Table(schema="SQLUser")
public class VocKiliProfile extends VocBaseEntity{

}
