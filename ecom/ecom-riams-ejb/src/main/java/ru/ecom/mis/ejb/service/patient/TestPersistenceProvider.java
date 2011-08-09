package ru.ecom.mis.ejb.service.patient;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

public class TestPersistenceProvider extends org.hibernate.ejb.HibernatePersistence {

	private static void l(String aStr) {
		System.out.println("TestPersistenceProvider:" +aStr) ;
	}
	
	@Override
	public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo arg0, Map arg1) {
		l("createContainerEntityManagerFactory("+arg0+", "+arg1+")") ;
		return new TestEntityManagerFactory(super.createContainerEntityManagerFactory(arg0, arg1));
	}

	@Override
	public EntityManagerFactory createEntityManagerFactory(Map arg0) {
		l("createEntityManagerFactory("+arg0+")") ;
		return new TestEntityManagerFactory(super.createEntityManagerFactory(arg0));
	}

	@Override
	public EntityManagerFactory createEntityManagerFactory(String arg0, Map arg1) {
		l("createEntityManagerFactory("+arg0+", "+arg1+")") ;
		return new TestEntityManagerFactory(super.createEntityManagerFactory(arg0, arg1));
	}
	

}
