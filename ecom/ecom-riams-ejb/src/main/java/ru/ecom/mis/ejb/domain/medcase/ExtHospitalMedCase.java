package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Госпитализация в другом ЛПУ")
@Entity
public class ExtHospitalMedCase extends HospitalMedCase {

}
