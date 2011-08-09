package ru.ecom.mis.ejb.domain.worker;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Сотрудник ЛПУ")
@Table(schema="SQLUser")
public class Worker extends BaseEntity{
}
