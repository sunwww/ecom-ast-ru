package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник врожденных аномалий развития: 01-Аненцефалия, 02-Спинно-мозговая грыжа, 03-Энцефалоцеле,
 * 04-Гидроцефалия, 05-Расщелина нёба, 06-Тотальная расщелина губы, 07-Атрезия пищевода,
 * 08-Атрезия ануса, 09-Эписпадия, 10-Редукционные пороки конечностей, 11-Омфалоцеле, 
 * 12-Синдром Дауна, 13-Другие, 00-Не было.
 * 
 * @author oegorova
 *
 */
@Entity
@Table(schema="SQLUser")
public class VocCongenitalAnomaly extends VocBaseEntity {

}
