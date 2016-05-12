package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

/**
 * Справочник осложнений родов: 01-Гипертермия во время родов, 02-Предлежание плаценты, 
 * 03-Преждевременная отслойка плаценты, 04-Неудачная попытка стимуляции родов, 
 * 05-Стремительные роды, 06-Затруднительные роды вследствие неправильного положения или предлежания плода,
 * 07-Кровотечение во время родов, 08-Роды и родоразрешение, осложнившиеся стрессом плода (дистресс),
 * 09-Роды и родоразрешение, осложнившиеся патологическим состоянием пуповины, 10-Другие, 00-Не было. 
 * @author oegorova
 *
 */

@Entity
@Table(schema="SQLUser")
public class VocChildbirthComplication extends VocBaseEntity {

}
