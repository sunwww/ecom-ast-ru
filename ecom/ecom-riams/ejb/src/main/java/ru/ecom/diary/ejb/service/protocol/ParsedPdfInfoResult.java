package ru.ecom.diary.ejb.service.protocol;

public class ParsedPdfInfoResult {
	/** Код показателя */
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}
	/** Код показателя */
	private String theCode;

	/** Значение показателя */
	public String getValue() {return theValue;}
	public void setValue(String aValue) {theValue = aValue;}
	/** Значение показателя */
	private String theValue;
	
	/** Единица измерения */
	public String getMeasurementUnit() {return theMeasurementUnit;}
	public void setMeasurementUnit(String aMeasurementUnit) {theMeasurementUnit = aMeasurementUnit;}
	/** Едицица измерения */
	private String theMeasurementUnit;
	
	/** Референтный интервал */
	public String getRefInterval() {return theRefInterval;}
	public void setRefInterval(String aRefInterval) {theRefInterval = aRefInterval;}
	/** Референтный интервал */
	private String theRefInterval;
	
}
