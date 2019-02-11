package ru.ecom.expomc.ejb.domain.registry;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Поданные записи
 * @author azviagin
 *
 */

@Comment("Поданные записи")
@Entity
@Table(name="EnteredEntry",schema="SQLUser")
@AIndex(name="Index1", unique = true, properties= {"firstname,middlename,lastname,birthDate,render,dischargeDate"})
@Deprecated
public class EnteredEntry extends LpuFond implements IImportData{
    /**
     * Импорт
     */
    public long getTime() {
        return theTime;
    }

    public void setTime(long aTime) {
        theTime = aTime;
    }
	
	/**
     * Импорт
     */
    private long theTime;
}
