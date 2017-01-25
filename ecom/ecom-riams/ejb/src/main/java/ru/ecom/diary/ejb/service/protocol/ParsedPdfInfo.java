package ru.ecom.diary.ejb.service.protocol;

import java.util.List;

public class ParsedPdfInfo {

	/** Номер пробирки */
	public String getBarcode() {return theBarcode;}
	public void setBarcode(String aBarcode) {theBarcode = aBarcode;}
	/** Номер пробирки */
	private String theBarcode;
	
	/** Список показателей */
	public List<ParsedPdfInfoResult> getResults() {return theResults;}
	public void setResults(List<ParsedPdfInfoResult> aResults) {theResults = aResults;}
	/** Список показателей */
	private List<ParsedPdfInfoResult> theResults;

}
