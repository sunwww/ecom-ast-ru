package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.form.simple.AFormatFieldSuggest;
import ru.ecom.expomc.ejb.services.exportformat.security.EntitySecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Лечебно-профилактические учреждения
 */
@Comment("Лечебно-профилактические учреждения ")
@Entity
@Table(name = "OMC_LPU",schema="SQLUser")
@EntitySecurityPrefix("/Policy/Mis/MisLpu")
public class OmcLpu extends OmcAbstractVoc {

    /** Адрес */
    @Comment("Адрес")
    @AFormatFieldSuggest({"ADDRESS","ADRESS"})
    public String getAddress() { return theAddress ; }
    public void setAddress(String aAddress) { theAddress = aAddress ; }

    /** Директор */
    @Comment("Директор")
    @AFormatFieldSuggest({"DIRECTOR"})
    public String getDirector() { return theDirector ; }
    public void setDirector(String aDirector) { theDirector = aDirector ; }

    /** Телефон */
    @Comment("Телефон")
    @AFormatFieldSuggest({"PHONE"})
    public String getPhone() { return thePhone ; }
    public void setPhone(String aPhone) { thePhone = aPhone ; }

    /** ИНН */
    @Comment("ИНН")
    @AFormatFieldSuggest({"INN"})
    public String getInn() { return theInn ; }
    public void setInn(String aInn) { theInn = aInn ; }

    /** ОКПО */
    @Comment("ОКПО")
    @AFormatFieldSuggest({"OKPO"})
    public String getOkpo() { return theOkpo ; }
    public void setOkpo(String aOkpo) { theOkpo = aOkpo ; }

    /** ОКОНХ */
    @Comment("ОКОНХ")
    @AFormatFieldSuggest({"OKONH"})
    public String getOkonh() { return theOkonh ; }
    public void setOkonh(String aOkonh) { theOkonh = aOkonh ; }

    /** Эл. почта АОТФОМС */
    @Comment("Эл. почта АОТФОМС")
    @AFormatFieldSuggest({"MAIL"})
    public String getMail() { return theMail ; }
    public void setMail(String aMail) { theMail = aMail ; }

    /** Ведомственное подчинение */
    @Comment("Ведомственное подчинение")
    @AFormatFieldSuggest({"VEDPODCHIN"})
    public String getVedPodchin() { return theVedPodchin ; }
    public void setVedPodchin(String aVedPodchin) { theVedPodchin = aVedPodchin ; }

    /** ОГРН */
	@Comment("ОГРН")
	@AFormatFieldSuggest({"M_OGRN"})
	public String getOgrn() {return theOgrn;}
	public void setOgrn(String aOgrn) {theOgrn = aOgrn;}

	/** Код федеральный */
	@Comment("Код федеральный")
	public String getCodef() {return theCodef;}
	public void setCodef(String aCodef) {theCodef = aCodef;}

	/** Код федеральный */
	private String theCodef;
	/** ОГРН */
	private String theOgrn;
    /** Ведомственное подчинение */
    private String theVedPodchin ;
    /** Эл. почта АОТФОМС */
    private String theMail ;
    /** ОКОНХ */
    private String theOkonh ;
    /** ОКПО */
    private String theOkpo ;
    /** ИНН */
    private String theInn ;
    /** Телефон */
    private String thePhone ;
    /** Директор */
    private String theDirector ;
    /** Адрес */
    private String theAddress ;
}
