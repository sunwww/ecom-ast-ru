package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник меток безопасности (строго конфиденциально, конфиденциально, неконфиденциально)
	 */
	@Comment("Справочник меток безопасности (строго конфиденциально, конфиденциально, неконфиденциально)")
@Entity
@Table(schema="SQLUser")
public class VocSecurityMark extends VocBaseEntity{
}
