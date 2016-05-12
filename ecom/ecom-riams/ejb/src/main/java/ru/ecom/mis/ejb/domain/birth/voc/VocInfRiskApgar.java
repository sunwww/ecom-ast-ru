package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник оценки степени риска бактериальной инфекции по шкале Апгар
 * @author oegorova
 *
 */
@Comment("Справочник оценки степени риска бактериальной инфекции по шкале Апгар")
@Entity
@Table(schema="SQLUser")
public class VocInfRiskApgar extends VocBall {

}
