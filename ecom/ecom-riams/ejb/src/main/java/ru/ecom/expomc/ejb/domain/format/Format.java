package ru.ecom.expomc.ejb.domain.format;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.Collection;

import static javax.persistence.CascadeType.ALL;

/**
 * Формат файлов проверки, например LPUFOND.DBF
 * Changes:
 * IKO 070311 *** Изменение схемы наследования
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("0")
public class Format extends AbstractImportFormat {

    /** Поля  */
    @OneToMany(cascade = ALL, mappedBy = "format" )
    @OrderBy("serialNumber")
    public Collection<Field> getFields() { return fields ; }
    public void setFields(Collection<Field> aFields) { fields = aFields ; }
    private Collection<Field> fields ;
}
