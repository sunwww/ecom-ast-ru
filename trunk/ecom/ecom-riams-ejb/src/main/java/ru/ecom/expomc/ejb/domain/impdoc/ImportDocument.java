package ru.ecom.expomc.ejb.domain.impdoc;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.check.Check;
import ru.ecom.expomc.ejb.domain.format.ExportFormat;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.format.ImportFormat;

/**
 * Документ.
 * Changes:
 * IKO 070301 *** Поддержка форматов импорта/экспорта XML 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="IMPDOC",schema="SQLUser")
@AIndexes(
		@AIndex(properties="entityClassName")
)
@NamedQueries(
		@NamedQuery(name="ImportDocument.findByName", query="from ImportDocument where entityClassName = :entityClassName")
		) 
public class ImportDocument extends BaseEntity {

    /** Клас для сохранения */
    public String getEntityClassName() { return theEntityClassName ; }
    public void setEntityClassName(String aEntityClassName) { theEntityClassName = aEntityClassName ; }

    /** Комментарий */
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Ключ */
    public String getKeyName() { return theKeyName ; }
    public void setKeyName(String aKeyName) { theKeyName = aKeyName ; }

    /** Время импорта */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    public Collection<ImportTime> getTimes() { return theTimes ; }
    public void setTimes(Collection<ImportTime> aTimes) { theTimes = aTimes ; }

    /** Форматы импорта */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    public Collection<Format> getFormats() { return theFormats ; }
    public void setFormats(Collection<Format> aFormats) { theFormats = aFormats ; }

    // IKO 070301 +++

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    public Collection<ExportFormat> getExportFormats() { return theExportFormats; }
    public void setExportFormats(Collection<ExportFormat> anExportFormats) { theExportFormats = anExportFormats; }

    // 070308
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    public Collection<ImportFormat> getImportFormats() { return theImportFormats; }
    public void setImportFormats(Collection<ImportFormat> anImportFormats) { theImportFormats = anImportFormats; }

    // IKO 070301 ===

    /** Проверки */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    @OrderBy("sn")
    public Collection<Check> getChecks() { return theChecks ; }
    public void setChecks(Collection<Check> aChecks) { theChecks = aChecks ; }


    @Transient
    public boolean isTimeSupport() {
        try {
            Class clazz = Class.forName(getEntityClassName()) ;
            return clazz.newInstance() instanceof IImportData;
        } catch (Exception e) {
            throw new IllegalArgumentException(e) ;
        }
    }

    /** Проверки */
    private Collection<Check> theChecks ;
    /** Форматы импорта */
    private Collection<Format> theFormats ;

    // IKO 070301 +++ Форматы экспорта

    private Collection<ExportFormat> theExportFormats ;

    // 070308
    private Collection<ImportFormat> theImportFormats;

    // IKO 070301 ===


    /** Время импорта */
    private Collection<ImportTime> theTimes ;
    /** Комментарий */
    private String theComment ;
    /** Ключ */
    private String theKeyName ;
    /** Клас для сохранения */
    private String theEntityClassName ;

}
