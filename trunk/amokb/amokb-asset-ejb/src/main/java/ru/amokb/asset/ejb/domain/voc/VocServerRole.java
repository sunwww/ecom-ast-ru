package ru.amokb.asset.ejb.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник ролей сервера (контроллер домена, DNS, DHCP, терминальный, почтовый, маршрутизатор, фаервол, прокси и т.д.)
	 */
	@Comment("Справочник ролей сервера (контроллер домена, DNS, DHCP, терминальный, почтовый, маршрутизатор, фаервол, прокси и т.д.)")
@Entity
@Table(schema="SQLUser")
public class VocServerRole extends VocBaseEntity{
}
