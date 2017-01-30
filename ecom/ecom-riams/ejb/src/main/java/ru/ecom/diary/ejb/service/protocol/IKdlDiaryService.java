package ru.ecom.diary.ejb.service.protocol;

import java.io.IOException;

import org.json.JSONException;

import com.itextpdf.text.DocumentException;

public interface IKdlDiaryService {
	public void parseFile(String aUri) throws Exception;
	public String getDir(String aKey, String aDefaultDir) ;
	public String run(String Command) ;
	public void checkPdf() throws IOException, NoSuchFieldException, IllegalAccessException, JSONException ;
}
