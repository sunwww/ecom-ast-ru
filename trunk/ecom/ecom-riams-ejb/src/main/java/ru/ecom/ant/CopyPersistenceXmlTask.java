package ru.ecom.ant;

import org.apache.tools.ant.Task;

public class CopyPersistenceXmlTask extends Task {
	
	/** Input XML */
	public void setInputXml(String aInputXml) {
		theInputXml = aInputXml;
	}

	/** Проект */
	public void setProjects(String aProjects) {
		theProjects = aProjects;
	}

	/** Проект */
	private String theProjects;

	/** Выходной XML */
	public void setOutputXml(String aOutputXml) {
		theOutputXml = aOutputXml;
	}
	
	@Override
	public void execute() {
		String path = getProject().getBaseDir().getAbsolutePath() ;
		PersistenceXmlHelper p = new PersistenceXmlHelper(path+"/"+theInputXml) ;
		
		for(String project : theProjects.split(",")) {
			
			p.add(path+"/../../ecom-"+project+"/ejb/src/main/resources/"+project+"-ejb_jar/META-INF/persistence.xml") ;
		}
		p.write(path+"/"+theOutputXml) ;
	}
	
	/** Выходной XML */
	private String theOutputXml ;
	/** Input XML */
	private String theInputXml;
}
