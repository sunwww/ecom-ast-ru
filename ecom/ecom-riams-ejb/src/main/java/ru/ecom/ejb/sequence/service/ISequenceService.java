package ru.ecom.ejb.sequence.service;

public interface ISequenceService {

	String startUseNextValue(String aTable, String aField) ;
	String startUseNextValueNoCheck(String aTable, String aField) ;
}
