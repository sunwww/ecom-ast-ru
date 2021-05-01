package ru.ecom.expomc.ejb.domain.format;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

/**
 * Поле
 */
@SuppressWarnings("serial")
@Entity
@Table(name="EFFIELD",schema="SQLUser")
@Getter
@Setter
public class Field extends BaseEntity {

    public static final int TEXT = 1 ;
    public static final int NUMERIC = 2 ;
    public static final int DATE = 3 ;
    public static final int BOOLEAN = 4 ;

    /** Формат */
    @ManyToOne
    @Comment("Формат")
    public AbstractImportFormat getFormat() { return format; }

    /** Документ */
    @OneToOne
    public ImportDocument getLinkedDocument() { return linkedDocument; }

    @Transient
    public String getDbfInfo() {
        StringBuilder sb = new StringBuilder();
        switch(dbfType) {
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
            	if(dbfDecimal !=0) {
            		sb.append(".") ;
            		sb.append(dbfDecimal) ;
            	}
            	break;
            case BOOLEAN:
                sb.append("L") ;
        }
        return sb.toString() ;
    }


	/** Значение по-умолчанию */
	private String defaultValue;
    /** Поле с кодом у документа, по которому делать проверку */
    private String linkedDocumentCodeField;
    /** Документ */
    private ImportDocument linkedDocument;
    /** Пояснения */
    private String description;
    /** Обязательное поле */
    private boolean required;
    /** Порядковый номер */
    private int serialNumber;
    /** Свойство для сохранения */
    private String property;
    /** Формат */
    private AbstractImportFormat format;
    /** DBF: количество знаков после запятой */
    private int dbfDecimal;
    /** DBF: размер поля */
    private int dbfSize;
    /** DBF: тип поля */
    private int dbfType;
    /** Комментарий */
    private String comment;
    /** Название поля */
    private String name;
}
