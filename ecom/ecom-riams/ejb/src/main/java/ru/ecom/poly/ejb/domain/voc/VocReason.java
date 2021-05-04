package ru.ecom.poly.ejb.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;


//Справочник Целей посещений: заболевание, профосмотр, патронаж, другое
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocReason extends VocIdNameOmcCode {

	/** Код для талона */
	private String codeTicket;
}
