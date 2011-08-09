package ru.ecom.expomc.ejb.domain.format;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Поле
 */
@SuppressWarnings("serial")
@Entity
@Table(name="EFFIELD",schema="SQLUser")
public class Field extends BaseEntity {

    public static final int TEXT = 1 ;
    public static final int NUMERIC = 2 ;
    public static final int DATE = 3 ;

    /** Название поля */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** DBF: тип поля */
    @Comment("DBF: тип поля")
    public int getDbfType() { return theDbfType ; }
    public void setDbfType(int aDbfType) { theDbfType = aDbfType ; }

    /** DBF: размер поля */
    @Comment("DBF: размер поля")
    public int getDbfSize() { return theDbfSize ; }
    public void setDbfSize(int aDbfSize) { theDbfSize = aDbfSize ; }

    /** DBF: количество знаков после запятой */
    @Comment("DBF: количество знаков после запятой")
    public int getDbfDecimal() { return theDbfDecimal ; }
    public void setDbfDecimal(int aDbfDecimal) { theDbfDecimal = aDbfDecimal ; }

    /** Формат */
    @ManyToOne
    @Comment("Формат")
    public Format getFormat() { return theFormat ; }
    public void setFormat(Format aFormat) { theFormat = aFormat ; }

    /** Свойство для сохранения */
    @Comment("Свойство для сохранения")
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /**
     * Порядковый номер
     */
    public int getSerialNumber() { return theSerialNumber ; }
    public void setSerialNumber(int aSerialNumber) { theSerialNumber = aSerialNumber ; }

    /** Обязательное поле */
    public boolean getRequired() { return theRequired ; }
    public void setRequired(boolean aRequired) { theRequired = aRequired ; }

    /** Пояснения */
    public String getDescription() { return theDescription ; }
    public void setDescription(String aDescription) { theDescription = aDescription ; }

    /** Документ */
    public ImportDocument getLinkedDocument() { return theLinkedDocument ; }
    public void setLinkedDocument(ImportDocument aLinkedDocument) { theLinkedDocument = aLinkedDocument ; }

    /** Поле с кодом у документа, по которому делать проверку */
    public String getLinkedDocumentCodeField() { return theLinkedDocumentCodeField ; }
    public void setLinkedDocumentCodeField(String aLinkedDocumentCodeField) { theLinkedDocumentCodeField = aLinkedDocumentCodeField ; }

    @Transient
    public String getDbfInfo() {
        StringBuilder sb = new StringBuilder();
        switch(theDbfType) {
            case TEXT:
                sb.append("A") ;
                sb.append(getDbfSize()) ;
                break ;
            case DATE:
                sb.append("D") ;
                break ;
            case NUMERIC:
                sb.append("N") ;
                sb.append(getDbfSize()) ;
                if(theDbfDecimal!=0) {
                    sb.append(".") ;
                    sb.append(theDbfDecimal) ;
                }
        }
        return sb.toString() ;
    }


    /** Значение по-умолчанию */
	@Comment("Значение по-умолчанию")
	public String getDefaultValue() {
		return theDefaultValue;
	}

	public void setDefaultValue(String aDefaultValue) {
		theDefaultValue = aDefaultValue;
	}

	/** Значение по-умолчанию */
	private String theDefaultValue;
    /** Поле с кодом у документа, по которому делать проверку */
    private String theLinkedDocumentCodeField ;
    /** Документ */
    private ImportDocument theLinkedDocument ;
    /** Пояснения */
    private String theDescription ;
    /** Обязательное поле */
    private boolean theRequired ;
    /** Порядковый номер */
    private int theSerialNumber ;
    /** Свойство для сохранения */
    private String theProperty ;
    /** Формат */
    private Format theFormat ;
    /** DBF: количество знаков после запятой */
    private int theDbfDecimal ;
    /** DBF: размер поля */
    private int theDbfSize ;
    /** DBF: тип поля */
    private int theDbfType ;
    /** Комментарий */
    private String theComment ;
    /** Название поля */
    private String theName ;
}
