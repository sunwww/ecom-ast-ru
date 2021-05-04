package ru.ecom.mis.ejb.domain.patient.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
/**Справочник причин снятия с Д учета*/
@Getter
@Setter
public class VocDispensaryEnd extends VocBaseEntity {
    
    /** Примечание */
    private String comment ;
}
