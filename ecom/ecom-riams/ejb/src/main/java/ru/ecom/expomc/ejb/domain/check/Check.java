package ru.ecom.expomc.ejb.domain.check;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;

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
@Getter
@Setter
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

    /** Настройка свойств */
    @OneToMany(cascade = ALL, mappedBy = "check")
    public Collection<CheckProperty> getProperties() { return properties; }

    /** Документ */
    @ManyToOne
    public ImportDocument getDocument() { return document; }


	/** Порядковый номер */
	private Integer sn;
    /** Документ */
    private ImportDocument document;
    /** Отключен */
    private boolean disabled;
    /** Дата действия по */
    private Date actualDateTo;
    /** Дата действия с  */
    private Date actualDateFrom;
    /** Настройка свойств */
    private Collection<CheckProperty> properties;
    /** Проверка */
    private long checkId;
    /** Комментарий */
    private String comment;
    /** Название */
    private String name;
    /** Тип проверки */
    private int checkType;

    @Transient
    public String getCheckTypeName() {
        return getTypeName(checkType);
    }
    
	/** Код */
	private String code;
	
	/** Код ОМС */
	private String omcCode;
}
