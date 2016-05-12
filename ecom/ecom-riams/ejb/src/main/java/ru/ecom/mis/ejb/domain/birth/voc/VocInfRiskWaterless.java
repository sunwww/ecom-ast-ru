package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник безводного периода оценки риска инфекционного заболевания новорожденного
 * @author azviagin
 *
 */

@Comment("Справочник безводного периода оценки риска инфекционного заболевания новорожденного")
@Entity
@Table(schema="SQLUser")
public class VocInfRiskWaterless extends VocBall{

}
