
package ru.ecom.mis.ejb.domain.medcase.kili;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliDefect;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
public class ProtocolKiliDefect extends BaseEntity{
/** Протокол КИЛИ */
@Comment("Протокол КИЛИ")
@ManyToOne
public ProtocolKili getProtocol() {return theProtocol;}
public void setProtocol(ProtocolKili aProtocol) {theProtocol = aProtocol;}
/** Протокол КИЛИ */
private ProtocolKili theProtocol;

/** Дефект */
@Comment("Дефект")
@OneToOne
public VocKiliDefect getDefect() {return theDefect;}
public void setDefect(VocKiliDefect aDefect) {theDefect = aDefect;}
/** Дефект */
private VocKiliDefect theDefect;

/** Дефект обнаружен */
@Comment("Дефект обнаружен")
public Boolean getIsDefectFound() {return theIsDefectFound;}
public void setIsDefectFound(Boolean aIsDefectFound) {theIsDefectFound = aIsDefectFound;}
/** Дефект обнаружен */
private Boolean theIsDefectFound;

/** Описание дефекта */
@Comment("Описание дефекта")
public String getDefectText() {return theDefectText;}
public void setDefectText(String aDefectText) {theDefectText = aDefectText;}
/** Описание дефекта */
private String theDefectText;
}
