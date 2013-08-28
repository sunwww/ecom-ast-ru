package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Контроль качества
	 */
	@Comment("Контроль качества")
@Entity
@Table(schema="SQLUser")
public class QualityControl extends BaseEntity{
}
