package ru.ecom.mis.ejb.domain.pharmacy;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rkurbanov on 06.09.2017.
 */
@Comment("Операция резервирования")
@Entity
@Table(schema="SQLUser")
public class PharmReserveOperation extends PharmOperation {
}
