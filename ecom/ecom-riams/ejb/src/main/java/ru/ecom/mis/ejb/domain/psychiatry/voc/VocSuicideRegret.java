package ru.ecom.mis.ejb.domain.psychiatry.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник отношения к совершенной суицидальной попытке (сожалеет, не сожалеет, сожалеет, что попытка не удалась)")
@Entity
@Table(schema="SQLUser")
public class VocSuicideRegret extends VocBaseEntity {

}
