package ru.ecom.mis.ejb.domain.pharmacy;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rkurbanov on 06.09.2017.
 */
@Comment("Операция списания")
@Entity
@Table(schema="SQLUser")
public class PharmOutcomeOperation extends PharmOperation {

}
