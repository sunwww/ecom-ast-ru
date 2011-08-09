package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник хронических очагов инфекции или острых инфекций
 * , перенесенных за месяц до родов или выявленных у матери 
 * в течение 1-х суток после родов (оценка риска инфекционного заболевания у новорожденного)
 * @author azviagin
 *
 */

@Comment("Справочник хронических очагов инфекции или острых инфекций, перенесенных за месяц до родов или выявленных у матери в течение 1-х суток после родов")
@Entity
@Table(schema="SQLUser")
public class VocInfRiskMotherDiseases extends VocBall{

}
