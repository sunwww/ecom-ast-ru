package ru.ecom.expomc.ejb.domain.externalbase;

/**
 * Персона для экспорта в формат Шубенка
 */

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Персона для экспорта в формат Шубенка")
@Entity
@Table(schema="SQLUser")
public class ExportPerson extends BaseEntity{
	
	/** Дата регистрации пациента */
	@Comment("Дата регистрации пациента")
	public Date getDataR() {
		return theDataR;
	}

	public void setDataR(Date aDataR) {
		theDataR = aDataR;
	}

	/** Дата регистрации пациента */
	private Date theDataR;
	
	/** Время регистрации пациента */
	@Comment("Время регистрации пациента")
	public Time getTimeR() {
		return theTimeR;
	}

	public void setTimeR(Time aTimeR) {
		theTimeR = aTimeR;
	}

	/** Время регистрации пациента */
	private Time theTimeR;
	
	/** Кем выдан паспорт */
	@Comment("Кем выдан паспорт")
	public String getDocV() {
		return theDocV;
	}

	public void setDocV(String aDocV) {
		theDocV = aDocV;
	}

	/** Кем выдан паспорт */
	private String theDocV;
	
	/** 15-ти значный код предприятия */
	@Comment("15-ти значный код предприятия")
	public String getRnumber15() {
		return theRnumber15;
	}

	public void setRnumber15(String aRnumber15) {
		theRnumber15 = aRnumber15;
	}

	/** 15-ти значный код предприятия */
	private String theRnumber15;
	
	/** Пустое поле */
	@Comment("Пустое поле")
	public String getDepartDoc() {
		return theDepartDoc;
	}

	public void setDepartDoc(String aDepartDoc) {
		theDepartDoc = aDepartDoc;
	}

	/** Пустое поле */
	private String theDepartDoc;
	
	/** Дата окончания документа на льготу */
	@Comment("Дата окончания документа на льготу")
	public Date getDataEl() {
		return theDataEl;
	}

	public void setDataEl(Date aDataEl) {
		theDataEl = aDataEl;
	}

	/** Дата окончания документа на льготу */
	private Date theDataEl;
	
	/** Дата начала льготы */
	@Comment("Дата начала льготы")
	public Date getDataBl() {
		return theDataBl;
	}

	public void setDataBl(Date aDataBl) {
		theDataBl = aDataBl;
	}

	/** Дата начала льготы */
	private Date theDataBl;
	
	/** Серия документа о федеральной льготе */
	@Comment("Серия документа о федеральной льготе")
	public String getSeriaDoc() {
		return theSeriaDoc;
	}

	public void setSeriaDoc(String aSeriaDoc) {
		theSeriaDoc = aSeriaDoc;
	}

	/** Серия документа о федеральной льготе */
	private String theSeriaDoc;
	
	/** Наименование документа о федеральной льготе */
	@Comment("Наименование документа о федеральной льготе")
	public String getNameDoc() {
		return theNameDoc;
	}

	public void setNameDoc(String aNameDoc) {
		theNameDoc = aNameDoc;
	}

	/** Наименование документа о федеральной льготе */
	private String theNameDoc;
	
	/** СНИЛС пациента */
	@Comment("СНИЛС пациента")
	public String getPens() {
		return thePens;
	}

	public void setPens(String aPens) {
		thePens = aPens;
	}

	/** СНИЛС пациента */
	private String thePens;
	


	/** Пишется ОТКАЗ - если пациент отказался от федеральных льгот */
	@Comment("Пишется ОТКАЗ - если пациент отказался от федеральных льгот")
	public String getUsluga() {
		return theUsluga;
	}

	public void setUsluga(String aUsluga) {
		theUsluga = aUsluga;
	}

	/** Пишется ОТКАЗ - если пациент отказался от федеральных льгот */
	private String theUsluga;
	
	
	/** Не заполняется */
	@Comment("Не заполняется")
	public String getKodspec() {
		return theKodspec;
	}

	public void setKodspec(String aKodspec) {
		theKodspec = aKodspec;
	}

	/** Не заполняется */
	private String theKodspec;
	
	/** КЛАДР улицы */
	@Comment("КЛАДР улицы")
	public String getCodeU() {
		return theCodeU;
	}

	public void setCodeU(String aCodeU) {
		theCodeU = aCodeU;
	}

	/** КЛАДР улицы */
	private String theCodeU;
	
	/** КЛАДР населенного пункта */
	@Comment("КЛАДР населенного пункта")
	public String getCode() {
		return theCode;
	}

	public void setCode(String aCode) {
		theCode = aCode;
	}

	/** КЛАДР населенного пункта */
	private String theCode;
	
	/** Не заполняется */
	@Comment("Не заполняется")
	public String getRZ() {
		return theRZ;
	}

	public void setRZ(String aRZ) {
		theRZ = aRZ;
	}

	/** Не заполняется */
	private String theRZ;
	
	/** Индекс прописки */
	@Comment("Индекс прописки")
	public int getSSity() {
		return theSSity;
	}

	public void setSSity(int aSSity) {
		theSSity = aSSity;
	}

	/** Индекс прописки */
	private int theSSity;
	
	/** Дата выдачи паспорта */
	@Comment("Дата выдачи паспорта")
	public Date getDPasport() {
		return theDPasport;
	}

	public void setDPasport(Date aDPasport) {
		theDPasport = aDPasport;
	}

	/** Дата выдачи паспорта */
	private Date theDPasport;
	
	/** Тип документа (П-паспорт или не заполняется) */
	@Comment("Тип документа (П-паспорт или не заполняется)")
	public String getDocum() {
		return theDocum;
	}

	public void setDocum(String aDocum) {
		theDocum = aDocum;
	}

	/** Тип документа (П-паспорт или не заполняется) */
	private String theDocum;
	
	/** Дата конца действия полиса */
	@Comment("Дата конца действия полиса")
	public Date getDataPE() {
		return theDataPE;
	}

	public void setDataPE(Date aDataPE) {
		theDataPE = aDataPE;
	}

	/** Дата конца действия полиса */
	private Date theDataPE;
	
	/** Дата начала действия полиса */
	@Comment("Дата начала действия полиса")
	public Date getDataP() {
		return theDataP;
	}

	public void setDataP(Date aDataP) {
		theDataP = aDataP;
	}

	/** Дата начала действия полиса */
	private Date theDataP;
	
	/** У всех 00 */
	@Comment("У всех 00")
	public String getTyps() {
		return theTyps;
	}

	public void setTyps(String aTyps) {
		theTyps = aTyps;
	}

	/** У всех 00 */
	private String theTyps;
	
	/** Не заполяется */
	@Comment("Не заполяется")
	public String getIstor() {
		return theIstor;
	}

	public void setIstor(String aIstor) {
		theIstor = aIstor;
	}

	/** Не заполяется */
	private String theIstor;
	
	/** Местность (г-город, с-село) */
	@Comment("Местность (г-город, с-село)")
	public String getMestnost() {
		return theMestnost;
	}

	public void setMestnost(String aMestnost) {
		theMestnost = aMestnost;
	}

	/** Местность (г-город, с-село) */
	private String theMestnost;
	
	/** Код района */
	@Comment("Код района")
	public String getAbr() {
		return theAbr;
	}

	public void setAbr(String aAbr) {
		theAbr = aAbr;
	}

	/** Код района */
	private String theAbr;
	
	/** Код образования */
	@Comment("Код образования")
	public int getObrazov() {
		return theObrazov;
	}

	public void setObrazov(int aObrazov) {
		theObrazov = aObrazov;
	}

	/** Код образования */
	private int theObrazov;
	
	/** Код страны (643) */
	@Comment("Код страны (643)")
	public String getStrana() {
		return theStrana;
	}

	public void setStrana(String aStrana) {
		theStrana = aStrana;
	}

	/** Код страны (643) */
	private String theStrana;
	
	/** Группа инвалидности */
	@Comment("Группа инвалидности")
	public String getInvalid() {
		return theInvalid;
	}

	public void setInvalid(String aInvalid) {
		theInvalid = aInvalid;
	}

	/** Группа инвалидности */
	private String theInvalid;
	
	/** Код льготы */
	@Comment("Код льготы")
	public int getUchast() {
		return theUchast;
	}

	public void setUchast(int aUchast) {
		theUchast = aUchast;
	}

	/** Код льготы */
	private int theUchast;
	
	/** Дата формирования служ. поля */
	@Comment("Дата формирования служ. поля")
	public Date getDataPassw() {
		return theDataPassw;
	}

	public void setDataPassw(Date aDataPassw) {
		theDataPassw = aDataPassw;
	}

	/** Дата формирования служ. поля */
	private Date theDataPassw;
	
	/** Служебное поле (любые цифры) */
	@Comment("Служебное поле (любые цифры)")
	public int getPassw() {
		return thePassw;
	}

	public void setPassw(int aPassw) {
		thePassw = aPassw;
	}

	/** Служебное поле (любые цифры) */
	private int thePassw;
	
	/** Номер паспорта */
	@Comment("Номер паспорта")
	public String getNPasport() {
		return theNPasport;
	}

	public void setNPasport(String aNPasport) {
		theNPasport = aNPasport;
	}

	/** Номер паспорта */
	private String theNPasport;
	
	/** Серия паспорта */
	@Comment("Серия паспорта")
	public String getSPasport() {
		return theSPasport;
	}

	public void setSPasport(String aSPasport) {
		theSPasport = aSPasport;
	}

	/** Серия паспорта */
	private String theSPasport;
	
	/** Номер участка (прикрепленного) */
	@Comment("Номер участка (прикрепленного)")
	public int getNaprav() {
		return theNaprav;
	}

	public void setNaprav(int aNaprav) {
		theNaprav = aNaprav;
	}

	/** Номер участка (прикрепленного) */
	private int theNaprav;
	
	/** Количество регистраций пациента */
	@Comment("Количество регистраций пациента")
	public int getKolRegist() {
		return theKolRegist;
	}

	public void setKolRegist(int aKolRegist) {
		theKolRegist = aKolRegist;
	}

	/** Количество регистраций пациента */
	private int theKolRegist;
	
	/** Отделение (всегда 99) */
	@Comment("Отделение (всегда 99)")
	public int getOtdel() {
		return theOtdel;
	}

	public void setOtdel(int aOtdel) {
		theOtdel = aOtdel;
	}

	/** Отделение (всегда 99) */
	private int theOtdel;
	
	/** Адрес фактического проживания */
	@Comment("Адрес фактического проживания")
	public String getFaktProg() {
		return theFaktProg;
	}

	public void setFaktProg(String aFaktProg) {
		theFaktProg = aFaktProg;
	}

	/** Адрес фактического проживания */
	private String theFaktProg;
	
	/** Фио регистратора */
	@Comment("Фио регистратора")
	public String getFamRegist() {
		return theFamRegist;
	}

	public void setFamRegist(String aFamRegist) {
		theFamRegist = aFamRegist;
	}

	/** Фио регистратора */
	private String theFamRegist;
	
	/** Дата перерегистрации пациента (дата ввода + 14 дней) */
	@Comment("Дата перерегистрации пациента (дата ввода + 14 дней)")
	public Date getDPerreg() {
		return theDPerreg;
	}

	public void setDPerreg(Date aDPerreg) {
		theDPerreg = aDPerreg;
	}

	/** Дата перерегистрации пациента (дата ввода + 14 дней) */
	private Date theDPerreg;
	
	/** Дата ввода сведений о пациенте */
	@Comment("Дата ввода сведений о пациенте")
	public Date getDVvoda() {
		return theDVvoda;
	}

	public void setDVvoda(Date aDVvoda) {
		theDVvoda = aDVvoda;
	}

	/** Дата ввода сведений о пациенте */
	private Date theDVvoda;
	
	/** Адрес (улица, дом, корпус, квартира) */
	@Comment("Адрес (улица, дом, корпус, квартира)")
	public String getAdres() {
		return theAdres;
	}

	public void setAdres(String aAdres) {
		theAdres = aAdres;
	}

	/** Адрес (улица, дом, корпус, квартира) */
	private String theAdres;
	
	/** Населенный пункт */
	@Comment("Населенный пункт")
	public String getNaselPun() {
		return theNaselPun;
	}

	public void setNaselPun(String aNaselPun) {
		theNaselPun = aNaselPun;
	}

	/** Населенный пункт */
	private String theNaselPun;
	
	/** Район прописки (три буквы) */
	@Comment("Район прописки (три буквы)")
	public String getRaion() {
		return theRaion;
	}

	public void setRaion(String aRaion) {
		theRaion = aRaion;
	}

	/** Район прописки (три буквы) */
	private String theRaion;
	
	/** Должность */
	@Comment("Должность")
	public String getDolgnost() {
		return theDolgnost;
	}

	public void setDolgnost(String aDolgnost) {
		theDolgnost = aDolgnost;
	}

	/** Должность */
	private String theDolgnost;
	
	/** Цифры старого кода предприятия */
	@Comment("Цифры старого кода предприятия")
	public String getNomReg() {
		return theNomReg;
	}

	public void setNomReg(String aNomReg) {
		theNomReg = aNomReg;
	}

	/** Цифры старого кода предприятия */
	private String theNomReg;
	
	/** Название предприятия */
	@Comment("Название предприятия")
	public String getDogovor() {
		return theDogovor;
	}

	public void setDogovor(String aDogovor) {
		theDogovor = aDogovor;
	}

	/** Название предприятия */
	private String theDogovor;
	
	/** Буква старого кода предприятия */
	@Comment("Буква старого кода предприятия")
	public String getGroupIm() {
		return theGroupIm;
	}

	public void setGroupIm(String aGroupIm) {
		theGroupIm = aGroupIm;
	}

	/** Буква старого кода предприятия */
	private String theGroupIm;
	
	/** Номер полиса */
	@Comment("Номер полиса")
	public String getNPolis() {
		return theNPolis;
	}

	public void setNPolis(String aNPolis) {
		theNPolis = aNPolis;
	}

	/** Номер полиса */
	private String theNPolis;
	
	/** Серия полиса */
	@Comment("Серия полиса")
	public String getSPolis() {
		return theSPolis;
	}

	public void setSPolis(String aSPolis) {
		theSPolis = aSPolis;
	}

	/** Серия полиса */
	private String theSPolis;
	
	/** Тип страхования */
	@Comment("Тип страхования")
	public String getTipS() {
		return theTipS;
	}

	public void setTipS(String aTipS) {
		theTipS = aTipS;
	}

	/** Тип страхования */
	private String theTipS;
	
	/** Код компании по ОМС */
	@Comment("Код компании по ОМС")
	public int getCompany() {
		return theCompany;
	}

	public void setCompany(int aCompany) {
		theCompany = aCompany;
	}

	/** Код компании по ОМС */
	private int theCompany;
	
	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getDataRogd() {
		return theDataRogd;
	}

	public void setDataRogd(Date aDataRogd) {
		theDataRogd = aDataRogd;
	}

	/** Дата рождения */
	private Date theDataRogd;
	
	/** Пол */
	@Comment("Пол")
	public String getSex() {
		return theSex;
	}

	public void setSex(String aSex) {
		theSex = aSex;
	}

	/** Пол */
	private String theSex;
	
	/** ФИО */
	@Comment("ФИО")
	public String getFio() {
		return theFio;
	}

	public void setFio(String aFio) {
		theFio = aFio;
	}

	/** ФИО */
	private String theFio;
	
	/** Дата регистрации в регистратуре  */
	@Comment("Дата регистрации в регистратуре ")
	public Date getRData() {
		return theRData;
	}

	public void setRData(Date aRData) {
		theRData = aRData;
	}

	/** Дата регистрации в регистратуре  */
	private Date theRData;
	
	/** Номер медкарты */
	@Comment("Номер медкарты")
	public String getRegNom() {
		return theRegNom;
	}

	public void setRegNom(String aRegNom) {
		theRegNom = aRegNom;
	}

	/** Номер медкарты */
	private String theRegNom;

}
