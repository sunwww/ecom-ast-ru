package ru.medos.ejb.persdata.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.annotation.security.SecurityDomain;

import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.medos.ejb.persdata.domain.ComingDocument;
import ru.medos.ejb.persdata.domain.DocumentFile;
import ru.medos.ejb.persdata.domain.Person;

@Stateless
@Remote(IDefaultService.class)
@Local(IDefaultService.class)
@SecurityDomain("other")
public class DefaultServiceBean implements IDefaultService {
	public String getDir(String aParam,String aDefaultValue) {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =config.get(aParam, aDefaultValue);
		return workDir ;
	}
	public float getImageCompress() {
		EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String comp = config.get("tomcat.image.compress", "0.5") ;
		return Float.valueOf(comp) ;
	}
	public String run(String Command){
		try{
		Runtime.getRuntime().exec(Command);
		return ("0");
		}
		catch (Exception e){
		System.out.println("Error running command: " + Command +
		"\n" + e.getMessage());
		return(e.getMessage());
		}
	} 
	
	public void insertExternalDocumentByObject(String aObject,Long aObjectId,String aReferenceComp,String aReferenceTo, String aComment,String aUsername) {
		DocumentFile doc = new DocumentFile() ;
		doc.setReferenceTo(aReferenceTo) ;
		doc.setReferenceCompTo(aReferenceComp) ;
		java.util.Date date = new java.util.Date() ;
		doc.setCreateDate(new java.sql.Date(date.getTime())) ;
		doc.setCreateUsername(aUsername) ;
		doc.setCreateTime(new java.sql.Time(date.getTime()));
		if (aObject.equals("Person")) {
			Person pat = theManager.find(Person.class, aObjectId) ;
			//doc.setPatient(pat) ;
		} else if (aObject.equals("ComingDocument")) {
			ComingDocument cd = theManager.find(ComingDocument.class, aObjectId) ;
			doc.setDocument(cd) ;
		} else {
			throw new IllegalStateException("Не определен object типа:"+aObject);
		}
		//VocExternalDocumentType type = theManager.find(VocExternalDocumentType.class, aType) ;
		//doc.setType(type) ;
		theManager.persist(doc) ;
	}
	private @PersistenceContext EntityManager theManager;
}
