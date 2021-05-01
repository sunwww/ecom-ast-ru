package ru.ecom.expomc.ejb.domain.impdoc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.check.Check;
import ru.ecom.expomc.ejb.domain.format.ExportFormat;
import ru.ecom.expomc.ejb.domain.format.Format;
import ru.ecom.expomc.ejb.domain.format.ImportFormat;

import javax.persistence.*;
import java.util.Collection;

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
@NamedQueries({
        @NamedQuery(name = "ImportDocument.findByName", query = "from ImportDocument where entityClassName = :entityClassName")
        , @NamedQuery(name = "ImportDocument.findByKey", query = "from ImportDocument where keyName = :key")
}
		)
@Getter
@Setter
public class ImportDocument extends BaseEntity {

    /** Системный формат */
    @Transient
    public Format getDefaultFormat() {
        for (Format format: formats) {
            if (Boolean.TRUE.equals(format.getSystemFormat())) return format;
        }
        return null;
    }

    /** Время импорта */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    public Collection<ImportTime> getTimes() { return times; }

    /** Форматы импорта */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    public Collection<Format> getFormats() { return formats; }
    private Collection<Format> formats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    public Collection<ExportFormat> getExportFormats() { return exportFormats; }
    private Collection<ExportFormat> exportFormats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    public Collection<ImportFormat> getImportFormats() { return importFormats; }
    private Collection<ImportFormat> importFormats;

    /** Проверки */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    @OrderBy("sn")
    public Collection<Check> getChecks() { return checks; }
    private Collection<Check> checks;


    @Transient
    public boolean isTimeSupport() {
        try {
            Class clazz = Class.forName(getEntityClassName()) ;
            return clazz.newInstance() instanceof IImportData;
        } catch (Exception e) {
            throw new IllegalArgumentException(e) ;
        }
    }

    /** Время импорта */
    private Collection<ImportTime> times;
    /** Комментарий */
    private String comment;
    /** Ключ */
    private String keyName;
    /** Клас для сохранения */
    private String entityClassName;
}