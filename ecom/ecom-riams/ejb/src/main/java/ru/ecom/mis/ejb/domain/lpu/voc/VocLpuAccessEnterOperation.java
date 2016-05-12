package ru.ecom.mis.ejb.domain.lpu.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник доступа на созданий операций по отделению")
@Entity
@Table(schema="SQLUser")
public class VocLpuAccessEnterOperation extends VocBaseEntity{

}
