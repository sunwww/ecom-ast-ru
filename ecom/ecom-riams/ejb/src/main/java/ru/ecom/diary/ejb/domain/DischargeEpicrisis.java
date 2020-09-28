package ru.ecom.diary.ejb.domain;

import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.poly.ejb.domain.protocol.Protocol;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners(DeleteListener.class)
public class DischargeEpicrisis extends Protocol {

}