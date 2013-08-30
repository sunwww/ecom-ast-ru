package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник факторов рисков заболеваний при проведении дополнительной диспансеризации
	 */
	@Comment("Справочник факторов рисков заболеваний при проведении дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
public class VocExtDispRisk extends VocBaseEntity{
}
