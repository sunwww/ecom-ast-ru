package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocLaserDrive;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Лазерный дисковод
	 */
	@Comment("Лазерный дисковод")
@Entity
@Table(schema="SQLUser")
public class LaserDrive extends Component{
	/**
	 * Тип дисковода
	 */
	@Comment("Тип дисковода")
	@OneToOne
	public VocLaserDrive getDriveType() {
		return theDriveType;
	}
	public void setDriveType(VocLaserDrive aDriveType) {
		theDriveType = aDriveType;
	}
	/**
	 * Тип дисковода
	 */
	private VocLaserDrive theDriveType;
}
