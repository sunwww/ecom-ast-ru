package ru.ecom.expomc.ejb.domain.omcvoc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class OmcLpu extends OmcAbstractVoc {

    /** Адрес */
    @Comment("Адрес")
    @AFormatFieldSuggest({"ADDRESS","ADRESS"})
    public String getAddress() { return address; }

    /** Директор */
    @Comment("Директор")
    @AFormatFieldSuggest({"DIRECTOR"})
    public String getDirector() { return director; }

    /** Телефон */
    @Comment("Телефон")
    @AFormatFieldSuggest({"PHONE"})
    public String getPhone() { return phone; }

    /** ИНН */
    @Comment("ИНН")
    @AFormatFieldSuggest({"INN"})
    public String getInn() { return inn; }

    /** ОКПО */
    @Comment("ОКПО")
    @AFormatFieldSuggest({"OKPO"})
    public String getOkpo() { return okpo; }

    /** ОКОНХ */
    @Comment("ОКОНХ")
    @AFormatFieldSuggest({"OKONH"})
    public String getOkonh() { return okonh; }

    /** Эл. почта АОТФОМС */
    @Comment("Эл. почта АОТФОМС")
    @AFormatFieldSuggest({"MAIL"})
    public String getMail() { return mail; }

    /** Ведомственное подчинение */
    @Comment("Ведомственное подчинение")
    @AFormatFieldSuggest({"VEDPODCHIN"})
    public String getVedPodchin() { return vedPodchin; }

    /** ОГРН */
	@Comment("ОГРН")
	@AFormatFieldSuggest({"M_OGRN"})
	public String getOgrn() {return ogrn;}

	/** Код федеральный */
	@Comment("Код федеральный")
	@AFormatFieldSuggest({"N_REESTR"})
	public String getCodef() {return codef;}

	/** Код федеральный */
	private String codef;
	/** ОГРН */
	private String ogrn;
    /** Ведомственное подчинение */
    private String vedPodchin;
    /** Эл. почта АОТФОМС */
    private String mail;
    /** ОКОНХ */
    private String okonh;
    /** ОКПО */
    private String okpo;
    /** ИНН */
    private String inn;
    /** Телефон */
    private String phone;
    /** Директор */
    private String director;
    /** Адрес */
    private String address;
}
