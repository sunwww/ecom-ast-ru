package ru.ecom.expomc.ejb.uc.findpolicy;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

public interface IFindPolicyService {

	public Collection<PolicyRow> findPolicy(String aLastname, String aFirstname, String aMiddlename, Date aBirthDate, String aSnils, long aMessageId) ;
	public long  acceptPolicy(String aParameters) ;
	public long getNextMessageId(long aMessageId) ;
	public void restore() throws IOException ;

	
}
