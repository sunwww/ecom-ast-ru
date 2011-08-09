package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 10.02.2007
 * Time: 11:46:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
public class VocSpecLabel extends VocIdNameOmcCode {
}
