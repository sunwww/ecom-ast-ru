package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by Milamesher on 05.10.2020.
 */

/** Лист наблюдения беременной*/
@Entity
@Table(schema="SQLUser")
@AIndexes({
        @AIndex(properties="patient"),
        @AIndex(properties="startDate")
})
public class ObservationSheetPregnant extends ObservationSheet {
}