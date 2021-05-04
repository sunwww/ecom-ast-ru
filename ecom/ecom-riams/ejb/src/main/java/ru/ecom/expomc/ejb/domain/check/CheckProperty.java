package ru.ecom.expomc.ejb.domain.check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;

/**
 * @author esinev
 * Date: 23.08.2006
 * Time: 13:34:32
 *
 * Changes:
 *  IKO-002 070321 *** Задан размер поля
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name="CHECKER_PROPERTY",schema="SQLUser")
@Getter
@Setter
public class CheckProperty extends BaseEntity {

    /** Значение свойства */
    @Column(length = 400)
    public String getValue() { return value; }

    /** Проверка */
    @ManyToOne
    public Check getCheck() { return check; }

    /** Проверка */
    private Check check;
    /** Значение свойства */
    private String value;
    /** Свойство */
    private String property;
}
