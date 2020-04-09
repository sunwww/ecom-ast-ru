package ru.ecom.diary.ejb.domain.protocol.template;

import ru.ecom.ejb.domain.simple.VocBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 11.01.2007
 * Time: 11:45:59
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
@Deprecated //unused 03-2020
public class TemplateProtocolType extends VocBaseEntity {
}
