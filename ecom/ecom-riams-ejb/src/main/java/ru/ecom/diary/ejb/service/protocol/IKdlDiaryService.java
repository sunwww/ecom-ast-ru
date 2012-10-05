package ru.ecom.diary.ejb.service.protocol;

public interface IKdlDiaryService {
	public void parseFile(String aUri) throws Exception;
	public String getDir(String aKey, String aDefaultDir) ;
}
