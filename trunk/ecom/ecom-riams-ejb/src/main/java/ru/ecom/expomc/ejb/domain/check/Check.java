package ru.ecom.expomc.ejb.domain.check;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * @author esinev
 * Date: 23.08.2006
 * Time: 13:33:18
 */
@SuppressWarnings("serial")
@Entity
@Table(name="CHECKER",schema="SQLUser")
@AIndexes({
	@AIndex(unique=true, properties={"code"})
})
public class Check extends BaseEntity {


    public static final int TYPE_INFO = 1 ;
    public static final int TYPE_CHANGE = 2 ;
    public static final int TYPE_CRITICAL = 3;

    public static String getTypeName(int aType) {
        switch(aType) {
            case TYPE_INFO: return "Сигнальное сообщение" ;
            case TYPE_CHANGE: return "Изменение" ;
            case TYPE_CRITICAL: return "Критическая ошибка" ;
        }
        return "Неизвестный тип сообщения: "+aType ;
    }

    /** Название */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Проверка */
    public long getCheckId() { return theCheckId ; }
    public void setCheckId(long aCheckId) { theCheckId = aCheckId ; }

    /** Тип проверки */
    public int getCheckType() { return theCheckType ; }
    public void setCheckType(int aCheckType) { theCheckType = aCheckType ; }

    /** Настройка свойств */
    @OneToMany(cascade = ALL, mappedBy = "check")
    public Collection<CheckProperty> getProperties() { return theProperties ; }
    public void setProperties(Collection<CheckProperty> aProperties) { theProperties = aProperties ; }

    /** Документ */
    @ManyToOne
    public ImportDocument getDocument() { return theDocument ; }
    public void setDocument(ImportDocument aDocument) { theDocument = aDocument ; }


    /** Дата действия с  */
    public Date getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(Date aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата действия по */
    public Date getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(Date aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Отключен */
    public boolean getDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }

    /** Порядковый номер */
	public Integer getSn() {return theSn;	}
	public void setSn(Integer aSn) {theSn = aSn;	}

	/** Порядковый номер */
	private Integer theSn;
    /** Документ */
    private ImportDocument theDocument ;
    /** Отключен */
    private boolean theDisabled ;
    /** Дата действия по */
    private Date theActualDateTo ;
    /** Дата действия с  */
    private Date theActualDateFrom ;
    /** Настройка свойств */
    private Collection<CheckProperty> theProperties ;
    /** Проверка */
    private long theCheckId ;
    /** Комментарий */
    private String theComment ;
    /** Название */
    private String theName ;
    /** Тип проверки */
    private int theCheckType ;

    @Transient
    public String getCheckTypeName() {
        return getTypeName(theCheckType);
    }
    
    /** Код */
	@Comment("Код")
	public String getCode() {
		return theCode;
	}

	public void setCode(String aCode) {
		theCode = aCode;
	}

	/** Код */
	private String theCode;
	
	/** Код ОМС */
	@Comment("Код ОМС")
	public String getOmcCode() {
		return theOmcCode;
	}

	public void setOmcCode(String aOmcCode) {
		theOmcCode = aOmcCode;
	}

	/** Код ОМС */
	private String theOmcCode;
}
