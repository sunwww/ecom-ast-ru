package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Периоды отчета")
@Entity
@Table(schema="SQLUser")
public class VocReportPeriod extends VocBaseEntity{

}
