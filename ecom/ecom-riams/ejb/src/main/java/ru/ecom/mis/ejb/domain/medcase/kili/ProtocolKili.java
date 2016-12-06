package ru.ecom.mis.ejb.domain.medcase.kili;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliConclusion;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliProfile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class ProtocolKili extends BaseEntity{

/** Случай смерти */
@Comment("Случай смерти")
@OneToOne
public DeathCase getDeathCase() {return theDeathCase;}
public void setDeathCase(DeathCase aDeathCase) {theDeathCase = aDeathCase;}
/** Случай смерти */
private DeathCase theDeathCase;

/** Профиль КИЛИ */
/**
@Comment("Профиль КИЛИ")
@OneToOne
public VocKiliProfile getProfile() {return theProfile;}
public void setProfile(VocKiliProfile aProfile) {theProfile = aProfile;}
*/
/** Профиль КИЛИ */
/**
 * private VocKiliProfile theProfile;
 */
	
/** Дата проведения КИЛИ */
@Comment("Дата проведения КИЛИ")
public Date getProtocolDate() {return theProtocolDate;}
public void setProtocolDate(Date aProtocolDate) {theProtocolDate = aProtocolDate;}
/** Дата проведения КИЛИ */
private Date theProtocolDate;

/** Номер протокола */
@Comment("Номер протокола")
public String getProtocolNumber() {return theProtocolNumber;}
public void setProtocolNumber(String aProtocolNumber) {theProtocolNumber = aProtocolNumber;}
/** Номер протокола */
private String theProtocolNumber;

/** Список дефектов */
@Comment("Список дефектов")
@OneToMany(mappedBy="protocol", cascade=CascadeType.ALL)
public List<ProtocolKiliDefect> getDefects() {return theDefects;}
public void setDefects(List<ProtocolKiliDefect> aDefects) {theDefects = aDefects;}
/** Список дефектов */
private List<ProtocolKiliDefect> theDefects;

/** Решение КИЛИ */
@Comment("Решение КИЛИ")
@OneToOne
public VocKiliConclusion getConclusion() {return theConclusion;}
public void setConclusion(VocKiliConclusion aConclusion) {theConclusion = aConclusion;}
/** Решение КИЛИ */
private VocKiliConclusion theConclusion;

/** Дата создания */
@Comment("Дата создания")
public Date getCreateDate() {return theCreateDate;}
public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
/** Дата создания */
private Date theCreateDate;

/** Время создания */
@Comment("Время создания")
public Time getCreateTime() {return theCreateTime;}
public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
/** Время создания */
private Time theCreateTime;

/** Пользователь */
@Comment("Пользователь")
public String getCreateUsername() {return theCreateUsername;}
public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
/** Пользователь */
private String theCreateUsername;

/** Дата редактирования */
@Comment("Дата редактирования")
public Date getEditDate() {return theEditDate;}
public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
/** Дата редактирования */
private Date theEditDate;

/** Время редактирования */
@Comment("Время редактирования")
public Time getEditTime() {return theEditTime;}
public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
/** Время редактирования */
private Time theEditTime;

/** Редактирование пользователя */
@Comment("Редактирование пользователя")
public String getEditUsername() {return theEditUsername;}
public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}
/** Редактирование пользователя */
private String theEditUsername;
}