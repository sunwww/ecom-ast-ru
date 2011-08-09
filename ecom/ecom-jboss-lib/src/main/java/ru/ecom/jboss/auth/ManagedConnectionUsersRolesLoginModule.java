package ru.ecom.jboss.auth;

import java.io.PrintWriter;
import java.util.Set;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.jboss.security.auth.spi.UsersRolesLoginModule;

/**
 * Добавляет PasswordCredential в getPrivateCredential()
 * , чтобы каждый пользователь мог заходить под своим именем и паролем в базу
 */
public class ManagedConnectionUsersRolesLoginModule extends UsersRolesLoginModule {
	
	private final static Logger LOG = Logger
			.getLogger(ManagedConnectionUsersRolesLoginModule.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public boolean login() throws LoginException {
		boolean ret = super.login() ;
		if(ret) {
			Set<PasswordCredential> creds = subject.getPrivateCredentials(PasswordCredential.class) ;
			
			if(creds==null || creds.isEmpty()) {
				String username = getUsername() ;
				String password = getUsernameAndPassword()[1] ; // very bad ;)
				                                           
				PasswordCredential cred = new PasswordCredential(username, password.toCharArray()) ;
				cred.setManagedConnectionFactory(new EmptyManagedConnectionFactory());
				subject.getPrivateCredentials().add(cred) ;
			} else {
				if (CAN_DEBUG)
					LOG.debug("login: creds = " + creds); 
				throw new IllegalStateException("Кажись их не должно быть") ;

			}
			
		}
		return ret ;
	}
	
	static class EmptyManagedConnectionFactory implements ManagedConnectionFactory {

		public Object createConnectionFactory() throws ResourceException {
			throw new java.lang.IllegalStateException("Не реализовано") ;
		}

		public Object createConnectionFactory(ConnectionManager arg0) throws ResourceException {
			throw new java.lang.IllegalStateException("Не реализовано") ;
		}

		public ManagedConnection createManagedConnection(Subject arg0, ConnectionRequestInfo arg1) throws ResourceException {
			throw new java.lang.IllegalStateException("Не реализовано") ;
		}

		public PrintWriter getLogWriter() throws ResourceException {
			throw new java.lang.IllegalStateException("Не реализовано") ;
		}

		public ManagedConnection matchManagedConnections(Set arg0, Subject arg1, ConnectionRequestInfo arg2) throws ResourceException {
			throw new java.lang.IllegalStateException("Не реализовано") ;
		}

		public void setLogWriter(PrintWriter arg0) throws ResourceException {
			throw new java.lang.IllegalStateException("Не реализовано") ;
		}
		
		public boolean equals(Object aObj) {
			if (CAN_DEBUG)
				LOG.debug("equals: aObj = " + aObj); 
			return true ;

		}
	}

}
