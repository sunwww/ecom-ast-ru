package ru.ecom.expert2;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
/**Вид случая для поликлинике для отчётов*/
public class JasperVidSluchPolyclinic extends BaseEntity {
    /** Наименование */
    private String name ;

    /** Коды видов случая */
    @Comment("Коды видов случая")
    @OneToMany
    public List<VocE2VidSluch> getVidSluchCode() {return vidSluchCode;}
    /** Коды видов случая */
    private List<VocE2VidSluch> vidSluchCode ;
}