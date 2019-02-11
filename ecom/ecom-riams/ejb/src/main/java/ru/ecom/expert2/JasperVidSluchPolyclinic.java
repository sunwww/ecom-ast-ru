package ru.ecom.expert2;/**
 * Created by Milamesher on 21.01.2019.
 */

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2VidSluch;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(schema="SQLUser")
/**Вид случая для поликлинике для отчётов*/
public class JasperVidSluchPolyclinic extends BaseEntity {
    /** Наименование */
    @Comment("Наименование")
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Наименование */
    private String theName ;

    /** Коды видов случая */
    @Comment("Коды видов случая")
    @OneToMany
    public List<VocE2VidSluch> getVidSluchCode() {return theVidSluchCode;}
    public void setVidSluchCode(List<VocE2VidSluch> aVidSluchCode) {theVidSluchCode = aVidSluchCode;}
    /** Коды видов случая */
    private List<VocE2VidSluch> theVidSluchCode ;
}