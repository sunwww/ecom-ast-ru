package ru.ecom.diary.ejb.service.protocol;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParsedPdfInfo {

	/** Номер пробирки */
	private String barcode;
	
	/** Список показателей */
	private List<ParsedPdfInfoResult> results;

}
