package ru.ecom.expert2.domain.voc.federal;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.MappedSuperclass;
import java.sql.Date;

@MappedSuperclass
@Getter
@Setter
public class VocBaseFederal extends VocBaseEntity{

    /** Дата начала действия */
    private Date startDate ;

    /** Дата окончания действия */
    private Date finishDate ;

   /** Неактуальная запись */
   private Boolean isNoActual =false;
}
