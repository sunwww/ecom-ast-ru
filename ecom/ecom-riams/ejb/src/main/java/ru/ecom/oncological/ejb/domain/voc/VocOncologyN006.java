package ru.ecom.oncological.ejb.domain.voc;
/** Created by rkurbanov on 17.07.2018. */

import ru.ecom.expert2.domain.voc.federal.VocBaseFederal;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**N006 - справочник соответсвия стадий (Stad+Tumor+Nodus+Metastasis)*/
@Entity
@Table(schema="SQLUser")
@Deprecated
public class VocOncologyN006 extends VocBaseFederal {

    private String ds;

    public String getDs() {
        return ds;
    }
    public void setDs(String ds) {
        this.ds = ds;
    }

     /** Стадия */
     @Comment("Стадия")
     public String getStad() {return theStad;}
     public void setStad(String aStad) {theStad = aStad;}
     /** Стадия */
     private String theStad ;

     /** Tumor */
     @Comment("Tumor")
     public String getTumor() {return theTumor;}
     public void setTumor(String aTumor) {theTumor = aTumor;}
     /** Tumor */
     private String theTumor ;

     /** Nodus */
     @Comment("Nodus")
     public String getNodus() {return theNodus;}
     public void setNodus(String aNodus) {theNodus = aNodus;}
     /** Nodus */
     private String theNodus ;

     /** Metastasis */
     @Comment("Metastasis")
     public String getMetastasis() {return theMetastasis;}
     public void setMetastasis(String aMetastasis) {theMetastasis = aMetastasis;}
     /** Metastasis */
     private String theMetastasis ;






}
