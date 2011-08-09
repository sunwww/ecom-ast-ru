package ru.ecom.document.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Тип свидетельства (окончательное, предварительное, взамен предварительного)
 * @author oegorova
 *
 */
@Entity
@Table(schema="SQLUser")
public class VocCertificateType extends VocBaseEntity {

}
