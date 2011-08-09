package ru.ecom.poly.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


//Справочник Целей посещений: заболевание, профосмотр, патронаж, другое
@Entity
@Table(schema="SQLUser")
public class VocReason extends VocIdNameOmcCode {
}
