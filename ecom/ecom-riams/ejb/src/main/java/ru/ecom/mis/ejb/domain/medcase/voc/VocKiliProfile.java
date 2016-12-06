package ru.ecom.mis.ejb.domain.medcase.voc;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник профилей КИЛИ")
@Entity
@Table(schema="SQLUser")
public class VocKiliProfile extends VocBaseEntity{

}
