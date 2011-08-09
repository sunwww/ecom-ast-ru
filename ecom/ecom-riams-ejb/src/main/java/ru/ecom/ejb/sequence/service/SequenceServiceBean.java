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

	public String startUseNextValue(String aKey) {
		return SequenceHelper.getInstance().startUseNextValue(aKey, theManager);
	}
	
	
	@PersistenceContext
	private EntityManager theManager ;

}
