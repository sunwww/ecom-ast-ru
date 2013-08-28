package ru.medos.ejb.persdata.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.medos.ejb.persdata.domain.DocumentFile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = DocumentFile.class)
@Comment("Файл документа")
@WebTrail(comment = "Файл документа", nameProperties= "id", list="entityParentList-personaldata_documentFile.do", view="entityParentView-personaldata_documentFile.do")
//@Parent(property="parent", parentForm=PARENT.class)
@EntityFormSecurityPrefix("/Policy/Mis")
public class DocumentFileForm extends IdEntityForm{
	/**
	 * Ссылка
	 */
	@Comment("Ссылка")
	@Persist
	public String getUrl() {
		return theUrl;
	}
	public void setUrl(String aUrl) {
		theUrl = aUrl;
	}
	/**
	 * Ссылка
	 */
	private String theUrl;
	/**
	 * Документ
	 */
	@Comment("Документ")
	@Persist
	public Long getDocument() {
		return theDocument;
	}
	public void setDocument(Long aDocument) {
		theDocument = aDocument;
	}
	/**
	 * Документ
	 */
	private Long theDocument;
	/**
	 * Комментарии
	 */
	@Comment("Комментарии")
	@Persist
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Комментарии
	 */
	private String theComment;
}
