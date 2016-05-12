package ru.ecom.ejb.sequence.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Remote(ISequenceService.class)
@Local(ISequenceService.class)
@Stateless
public class SequenceServiceBean implements ISequenceService {

	public String startUseNextValue(String aTable,String aField) {
		return SequenceHelper.getInstance().startUseNextValue(aTable,aField, theManager);
	}
	public String startUseNextValueNoCheck(String aTable,String aField) {
		return SequenceHelper.getInstance().startUseNextValueNoCheck(aTable,aField, theManager);
	}
	
	
	@PersistenceContext
	private EntityManager theManager ;

}
