package ru.medos.ejb.persdata.domain.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * КЛАДР
	 */
	@Comment("КЛАДР")
@Entity
@Table(schema="SQLUser")
public class VocKladr extends VocBaseEntity{
		/**
		 * Короткое название типа адреса
		 */
		@Comment("Короткое название типа адреса")
		
		public String getShortName() {
			return theShortName;
		}
		public void setShortName(String aShortName) {
			theShortName = aShortName;
		}
		/**
		 * Короткое название типа адреса
		 */
		private String theShortName;
		/**
		 * Почтовый индекс
		 */
		@Comment("Почтовый индекс")
		
		public String getPostindex() {
			return thePostindex;
		}
		public void setPostindex(String aPostindex) {
			thePostindex = aPostindex;
		}
		/**
		 * Почтовый индекс
		 */
		private String thePostindex;
		/**
		 * Код ИФНС
		 */
		@Comment("Код ИФНС")
		
		public String getGni() {
			return theGni;
		}
		public void setGni(String aGni) {
			theGni = aGni;
		}
		/**
		 * Код ИФНС
		 */
		private String theGni;
		/**
		 * Код территориального участка ИФНС
		 */
		@Comment("Код территориального участка ИФНС")
		
		public String getUno() {
			return theUno;
		}
		public void setUno(String aUno) {
			theUno = aUno;
		}
		/**
		 * Код территориального участка ИФНС
		 */
		private String theUno;
		/**
		 * Код ОКАТО
		 */
		@Comment("Код ОКАТО")
		
		public String getOkato() {
			return theOkato;
		}
		public void setOkato(String aOkato) {
			theOkato = aOkato;
		}
		/**
		 * Код ОКАТО
		 */
		private String theOkato;
		/**
		 * Статус
		 */
		@Comment("Статус")
		
		public String getStatus() {
			return theStatus;
		}
		public void setStatus(String aStatus) {
			theStatus = aStatus;
		}
		/**
		 * Статус
		 */
		private String theStatus;
		/**
		 * Территория
		 */
		@Comment("Территория")
		
		public String getProvice() {
			return theProvice;
		}
		public void setProvice(String aProvice) {
			theProvice = aProvice;
		}
		/**
		 * Территория
		 */
		private String theProvice;
		/**
		 * Район
		 */
		@Comment("Район")
		
		public String getRegion() {
			return theRegion;
		}
		public void setRegion(String aRegion) {
			theRegion = aRegion;
		}
		/**
		 * Район
		 */
		private String theRegion;
		/**
		 * Город
		 */
		@Comment("Город")
		
		public String getCity() {
			return theCity;
		}
		public void setCity(String aCity) {
			theCity = aCity;
		}
		/**
		 * Город
		 */
		private String theCity;
		/**
		 * Подчиненный населенный пункт
		 */
		@Comment("Подчиненный населенный пункт")
		
		public String getSettlement() {
			return theSettlement;
		}
		public void setSettlement(String aSettlement) {
			theSettlement = aSettlement;
		}
		/**
		 * Подчиненный населенный пункт
		 */
		private String theSettlement;
		/**
		 * Улица
		 */
		@Comment("Улица")
		
		public String getStreet() {
			return theStreet;
		}
		public void setStreet(String aStreet) {
			theStreet = aStreet;
		}
		/**
		 * Улица
		 */
		private String theStreet;
		
		/** Домен */
		@Comment("Домен")
		public int getDomen() {return theDomen;}
		public void setDomen(int aDomen) {theDomen = aDomen;}

		/** Домен */
		private int theDomen;
		
		/** Полное имя */
		@Comment("Полное имя")
		public String getFullname() {
			return theFullname;
		}

		public void setFullname(String aFullname) {
			theFullname = aFullname;
		}

		/** Полное имя */
		private String theFullname;
}
