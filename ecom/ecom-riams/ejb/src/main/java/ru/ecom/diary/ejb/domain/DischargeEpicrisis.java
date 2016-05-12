package ru.ecom.diary.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.poly.ejb.domain.protocol.Protocol;

@Entity
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
public class DischargeEpicrisis extends Protocol {

}
