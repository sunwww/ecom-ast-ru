package ru.ecom.mis.ejb.domain.patient;


import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

import javax.persistence.Entity;
import javax.persistence.Table;

/** Лист наблюдения новорождённого*/
@Entity
@Table(schema="SQLUser")
@AIndexes({
        @AIndex(properties="patient"),
        @AIndex(properties="startDate")
})
public class ObservationSheetNewBorn extends ObservationSheet {
}
