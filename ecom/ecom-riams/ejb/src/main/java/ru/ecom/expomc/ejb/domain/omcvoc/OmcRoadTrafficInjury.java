package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник ОМС по ДТП
 * @author oegorova
 *
 */

@Comment("Справочник ОМС по ДТП")
@Entity
@Table(schema="SQLUser")
public class OmcRoadTrafficInjury extends OmcAbstractVoc{

}
