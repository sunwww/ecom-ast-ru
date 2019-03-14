package ru.ecom.expomc.ejb.domain.message;

import ru.ecom.ejb.domain.simple.NoLiveBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.check.Check;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Сообщение
 */
@SuppressWarnings("serial")
@Entity
@Table(name="MESSAGE", schema="SQLUser")
@AIndexes({
	@AIndex(properties = {"importTime"})
})
@Deprecated
public class Message extends NoLiveBaseEntity {


    /** Идентификатор данных */
    public long getDataId() { return theDataId ; }
    public void setDataId(long aDataId) { theDataId = aDataId ; }

    /** Время данных */
    @ManyToOne
    public ImportTime getImportTime() { return theImportTime ; }
    public void setImportTime(ImportTime aImportTime) { theImportTime = aImportTime ; }


    /** Тип проверки */
    @OneToOne
    public Check getCheck() { return theCheck ; }
    public void setCheck(Check aCheck) { theCheck = aCheck ; }

    
    /** Тип проверки */
    private Check theCheck ;

    /** Идентификатор данных */
    private long theDataId ;
    /** Время данных */
    private ImportTime theImportTime ;
}
