/**
 *
 * @author ikouzmin 08.03.2007 18:12:39
 */

package ru.ecom.expomc.ejb.domain.format;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;



@Entity
@SuppressWarnings("serial")
@DiscriminatorValue("1")
public class ImportFormat  extends Format {

    /** XML - конфигурации импорта */
    @Column(length = 150000)
    public String getConfig() { return theConfig ; }
    public void setConfig(String aConfig) { theConfig = aConfig ; }
    private String theConfig ;
}
