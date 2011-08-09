package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;

//Справочник Диспансерных учетов
@Entity
@Table(schema="SQLUser")
public class VocDispanseryRegistration extends VocIdNameOmcCode {
}
