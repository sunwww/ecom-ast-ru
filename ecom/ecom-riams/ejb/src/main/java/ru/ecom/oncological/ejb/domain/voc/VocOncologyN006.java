package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import lombok.Getter;
import lombok.Setter;
import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N006 - справочник соответсвия стадий (Stad+Tumor+Nodus+Metastasis)*/
@Entity
@Table(schema="SQLUser")
@Deprecated
@Getter
@Setter
public class VocOncologyN006 extends VocBaseFederal {

    private String ds;


     /** Стадия */
     private String stad ;

     /** Tumor */
     private String tumor ;
     /** Nodus */
     private String nodus ;

     /** Metastasis */
     private String metastasis ;






}
