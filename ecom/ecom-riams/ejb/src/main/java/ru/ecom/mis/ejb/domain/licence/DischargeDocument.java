package ru.ecom.mis.ejb.domain.licence;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Выписки")
@Table(schema="SQLUser")
public class DischargeDocument extends InternalDocuments {
	
}
