package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Причина смерти установлена
 * 		1. Врач, только установившем смерть
 * 		2. Врач, лечивший умершего
 * 		3. Фельдшер
 * 		4. Патологоанатом
 * 		5. Судебно-медицинский эксперт
 * @author stkacheva
 *
 */
@Comment("Кем установлена причина смерти")
@Entity
@Table(schema="SQLUser")
public class VocDeathWitnessFunction extends VocBaseEntity {

}
