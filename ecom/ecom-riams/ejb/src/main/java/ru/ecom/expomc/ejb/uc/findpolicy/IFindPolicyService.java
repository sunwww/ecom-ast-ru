package ru.ecom.expomc.ejb.uc.findpolicy;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@Deprecated
public interface IFindPolicyService {

	Collection<PolicyRow> findPolicy(String aLastname, String aFirstname, String aMiddlename, Date aBirthDate, String aSnils, long aMessageId) ;
	long  acceptPolicy(String aParameters) ;
	long getNextMessageId(long aMessageId) ;
	void restore() throws IOException ;

	
}
