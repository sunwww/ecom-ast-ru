package ru.ecom.diary.ejb.service.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParsedPdfInfoResult {
	/** Код показателя */
	private String code;

	/** Значение показателя */
	private String value;
	
	/** Едицица измерения */
	private String measurementUnit;
	
	/** Референтный интервал */
	private String refInterval;
	
}
