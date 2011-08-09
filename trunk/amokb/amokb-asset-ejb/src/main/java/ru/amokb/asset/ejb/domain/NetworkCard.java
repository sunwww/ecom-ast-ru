package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocNetworkCardBoot;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Сетевая карта
	 */
	@Comment("Сетевая карта")
@Entity
@Table(schema="SQLUser")
public class NetworkCard extends Component{
	/**
	 * MAK адрес
	 */
	@Comment("MAK адрес")
	
	public String getMAC() {
		return theMAC;
	}
	public void setMAC(String aMAC) {
		theMAC = aMAC;
	}
	/**
	 * MAK адрес
	 */
	private String theMAC;
	/**
	 * IP адрес
	 */
	@Comment("IP адрес")
	
	public String getIdaddress() {
		return theIdaddress;
	}
	public void setIdaddress(String aIdaddress) {
		theIdaddress = aIdaddress;
	}
	/**
	 * IP адрес
	 */
	private String theIdaddress;
	/**
	 * Тип загрузки
	 */
	@Comment("Тип загрузки")
	@OneToOne
	public VocNetworkCardBoot getBootType() {
		return theBootType;
	}
	public void setBootType(VocNetworkCardBoot aBootType) {
		theBootType = aBootType;
	}
	/**
	 * Тип загрузки
	 */
	private VocNetworkCardBoot theBootType;
	/**
	 * Оптическая
	 */
	@Comment("Оптическая")
	
	public Boolean getOptical() {
		return theOptical;
	}
	public void setOptical(Boolean aOptical) {
		theOptical = aOptical;
	}
	/**
	 * Оптическая
	 */
	private Boolean theOptical;
	/**
	 * Скорость в Мб\с
	 */
	@Comment("Скорость в Мб/с")
	public int getSpeed() {
		return theSpeed;
	}
	public void setSpeed(int aSpeed) {
		theSpeed = aSpeed;
	}
	/**
	 * Скорость в Мб\с
	 */
	private int theSpeed;
}
