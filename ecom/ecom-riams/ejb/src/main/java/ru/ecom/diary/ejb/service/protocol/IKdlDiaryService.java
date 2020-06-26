package ru.ecom.diary.ejb.service.protocol;

import java.io.FileNotFoundException;

public interface IKdlDiaryService {
	void importCovidAnalysis(String filename, String username) throws FileNotFoundException;
	void parseFile(String aUri) throws Exception;
	String getDir(String aKey, String aDefaultDir) ;
	String run(String Command) ;
	}
