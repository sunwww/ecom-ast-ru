package ru.medos.ant;

import java.util.Set;

import org.apache.tools.ant.Task;



public class FindAndSaveEntitiesToPersistenceXmlTask extends Task {

	
	@Override
	public void execute() {
		String path = getProject().getBaseDir().getAbsolutePath() ;
		PersistenceXmlHelper p = new PersistenceXmlHelper(path+"/"+theInputXml) ;
		Set<String> classes = new FindEntityClassesHelper().findClasses(path+"/target/classes");
		for(String cl : classes) {
			p.addClassname(cl);
		}
		p.write(path+"/"+theOutputXml);
	}
	
	
	/** Input XML */
	public void setInputXml(String aInputXml) {
		theInputXml = aInputXml;
	}

	/** Выходной XML */
	public void setOutputXml(String aOutputXml) {
		theOutputXml = aOutputXml;
	}	
	/** Выходной XML */
	private String theOutputXml ;
	/** Input XML */
	private String theInputXml;
}