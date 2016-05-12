package ru.ecom.mis.ejb.domain.diet;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *  Шаблон блюда меню приема пищи
 * @author oegorova
 *
 */

@Comment(" Шаблон блюда меню приема пищи")
@Entity
@Table(schema="SQLUser")
public class DishMealMenuOrder extends DishMealMenu{

}
