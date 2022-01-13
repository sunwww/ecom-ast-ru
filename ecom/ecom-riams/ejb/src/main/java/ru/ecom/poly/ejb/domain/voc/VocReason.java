package ru.ecom.poly.ejb.domain.voc;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;

import javax.persistence.Entity;
import javax.persistence.Table;


//Справочник Целей посещений: заболевание, профосмотр, патронаж, другое
@Entity
@Table(schema = "SQLUser")
public class VocReason extends VocIdNameOmcCode {
}
