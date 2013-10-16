package ru.medos.ejb.persdata.service;

public interface IDefaultService {
	public float getImageCompress() ;
	public void insertExternalDocumentByObject(String aObject,Long aObjectId,String aReferenceComp,String aReferenceTo, String aComment,String aUsername) ;
	public String getDir(String a1,String a2) ;
	public String run(String aCommand) ;

}
