package ru.ecom.expomc.ejb.domain.message;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.check.Check;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;

/**
 * Сообщение
 */
@SuppressWarnings("serial")
@Entity
@Table(name="MESSAGE", schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"importTime"})
})
public class Message extends NoLiveBaseEntity {

    /** Список изменений в записи */
    @OneToMany (cascade= CascadeType.ALL, mappedBy="message")
    public Collection<MessageChange> getChanges() { return theChanges ; }
    public void setChanges(Collection<MessageChange> aChanges) { theChanges = aChanges ; }

    /** Идентификатор данных */
    public long getDataId() { return theDataId ; }
    public void setDataId(long aDataId) { theDataId = aDataId ; }

    /** Время данных */
    @ManyToOne
    public ImportTime getImportTime() { return theImportTime ; }
    public void setImportTime(ImportTime aImportTime) { theImportTime = aImportTime ; }


    /** Лог */
    @OneToMany (cascade = CascadeType.ALL, mappedBy="message")
    public Collection<MessageLog> getLogs() { return theLogs ; }
    public void setLogs(Collection<MessageLog> aLogs) { theLogs = aLogs ; }

    /** Тип проверки */
    @OneToOne
    public Check getCheck() { return theCheck ; }
    public void setCheck(Check aCheck) { theCheck = aCheck ; }

    
    /** Тип проверки */
    private Check theCheck ;
    /** Список изменений в записи */
    private Collection<MessageChange> theChanges ;
    /** Идентификатор данных */
    private long theDataId ;
    /** Лог */
    private Collection<MessageLog> theLogs ;
    /** Время данных */
    private ImportTime theImportTime ;
}
