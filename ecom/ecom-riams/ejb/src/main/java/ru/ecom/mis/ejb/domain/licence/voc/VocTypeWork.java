package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 Справочник "Вид деятельности"
*/
@Entity
@Comment("Вид деятельности")
@Table(schema="SQLUser")
public class VocTypeWork extends VocIdName {
//    /** Связь с приложением*/
//        @ManyToOne
//        public Prilogenie getPrilogenie() { return thePrilogenie ; }
//        public void setPrilogenie(Prilogenie aPrilogenie) { thePrilogenie = aPrilogenie ; }
//
//    private Prilogenie thePrilogenie;
}
