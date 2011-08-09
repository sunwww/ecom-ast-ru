package ru.ecom.mis.ejb.service.patient;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TestEntityManagerFactory extends org.jboss.ejb3.entity.InjectedEntityManagerFactory {

	public TestEntityManagerFactory(EntityManagerFactory aTarget) {
		if(aTarget==null) l("entityFactory is null") ;
		theTarget = (org.jboss.ejb3.entity.InjectedEntityManagerFactory)aTarget ;
	}
	
	private final org.jboss.ejb3.entity.InjectedEntityManagerFactory theTarget ;
	
	private static void l(String aStr) {
		System.out.println("TestEntityManagerFactory:" +aStr) ;
	}
	
	@Override
	public void close() {
		l("close()") ;
		// TODO Auto-generated method stub
		theTarget.close();
	}

	@Override
	public EntityManager createEntityManager() {
		l("createEntityManager()") ;
		return theTarget.createEntityManager();
	}

	@Override
	public EntityManager createEntityManager(Map arg0) {
		l("createEntityManager("+arg0+")") ;
		return theTarget.createEntityManager(arg0);
	}

	@Override
	public EntityManagerFactory getDelegate() {
		l("getDelegate()") ;
		return theTarget.getDelegate();
	}

	@Override
	public EntityManager getEntityManager() {
		l("getEntityManager()") ;
		return theTarget.getEntityManager();
	}

	@Override
	public boolean isOpen() {
		l("isOpen()") ;
		return theTarget.isOpen();
	}

	@Override
	public void readExternal(ObjectInput arg0) throws IOException, ClassNotFoundException {
		l("readExternal("+arg0+")") ;
		theTarget.readExternal(arg0);
	}

	@Override
	public void writeExternal(ObjectOutput arg0) throws IOException {
		l("writeExternal("+arg0+")") ;
		theTarget.writeExternal(arg0);
	}
	

}
