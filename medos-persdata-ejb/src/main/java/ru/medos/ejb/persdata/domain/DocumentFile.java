package ru.medos.ejb.persdata.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.medos.ejb.persdata.domain.ComingDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Файл документа
	 */
	@Comment("Файл документа")
@Entity
@Table(schema="SQLUser")
public class DocumentFile extends BaseEntity{
	/**
	 * Ссылка
	 */
	@Comment("Ссылка")
	
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
	@ManyToOne
	public ComingDocument getDocument() {
		return theDocument;
	}
	public void setDocument(ComingDocument aDocument) {
		theDocument = aDocument;
	}
	/**
	 * Документ
	 */
	private ComingDocument theDocument;
	/**
	 * Комментарии
	 */
	@Comment("Комментарии")
	
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
