package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
/*
 * Порядок поступления: добровольное, недобр. в соотв. со ст. 35, не требуется
 */
@Entity
@Table(schema="SQLUser")
public class VocJudgment extends VocBaseEntity {

}
