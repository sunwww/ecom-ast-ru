package ru.medos.ejb.persdata.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jboss.annotation.security.SecurityDomain;
import org.w3c.dom.Element;

import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.report.util.XmlDocument;
import ru.medos.ejb.persdata.domain.ComingDocument;
import ru.medos.ejb.persdata.domain.CopiesTransferAct;
import ru.medos.ejb.persdata.domain.DocumentFile;
import ru.medos.ejb.persdata.domain.Person;

@Stateless
@Remote(IDefaultService.class)
@Local(IDefaultService.class)
@SecurityDomain("other")
public class DefaultServiceBean implements IDefaultService {
	public String exportIdentifierByCopiesTransferActServlet(Long aCopiesTransferAct,String aUsername) throws ParserConfigurationException, TransformerException {
		CopiesTransferAct act = theManager.find(CopiesTransferAct.class, aCopiesTransferAct) ;
		List<Object[]> list = theManager.createNativeQuery("select p.id as pid,i.identificationNumber from Identifier i left join Person p on p.id=i.person_id where i.transferAct_id="+aCopiesTransferAct).getResultList() ;
		if (act!=null && act.getCopiesAmount()!=null && act.getCopiesAmount().intValue()==list.size()) {
			java.util.Date dat = new java.util.Date() ;
	        String filename = "copiesTransferAct"+aCopiesTransferAct+"-"+dat.getTime()+".xml" ;
	        String workDir =getDir("tomcat.data.dir", "/opt/tomcat/webapps/persdata/act/export");
	        File fld = new File(workDir) ;
	        if (!fld.exists()) fld.mkdir() ;
	        File outFile = new File(workDir+"/"+filename) ;
	        XmlDocument xmlDoc = new XmlDocument() ;
	        Element root = xmlDoc.newElement(xmlDoc.getDocument(), "LIST", null);
			Element title = xmlDoc.newElement(root, "TITLE", null);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd") ;
			xmlDoc.newElement(title, "USER", aUsername);
			xmlDoc.newElement(title, "DATE", format.format(dat));
			xmlDoc.newElement(title, "FILENAME", filename);
			xmlDoc.newElement(title, "COUNT", act.getCopiesAmount().toString());
			int i=0 ;
			for (Object[] pat:list) {
				Element zap = xmlDoc.newElement(root, "ZAP", null);
				xmlDoc.newElement(zap, "ID", getStringValue(++i)) ;
				xmlDoc.newElement(zap, "ID_CONST", getStringValue(pat[0])) ;
				xmlDoc.newElement(zap, "ID_TRANSIENT", getStringValue(pat[1])) ;
			}
			xmlDoc.saveDocument(outFile) ;
			act.setFilenameExport(filename) ;
			return filename;
		} else {
			throw new IllegalStateException("Неверные данные по акту") ;
		}
	}
    private String getStringValue(Object aValue) {
    	return aValue!=null?""+aValue:"" ;
    }	
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
