package ru.ecom.mis.ejb.domain.psychiatry;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocCriminalCodeArticle;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocLawCourt;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychCompulsoryTreatment;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychCourtTreatmentDecision;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Принудительное лечение
 */
@Comment("Принудительное лечение")
@Entity
@AIndexes({
        @AIndex(properties = {"careCard"})
})
@Table(schema = "SQLUser")
@Getter
@Setter
public class CompulsoryTreatment extends BaseEntity {

    /**
     * Дата регистрации
     */
    private Date registrationDate;
    /**
     * Порядковый номер лечения
     */
    private int orderNumber;
    /**
     * Дата решения суда
     */
    private Date decisionDate;
    /**
     * Описание решения
     */
    private String decisionNotes;

    /**
     * Карта обратившегося за психиатрической помощью
     */
    @Comment("Карта обратившегося за психиатрической помощью")
    @ManyToOne
    public PsychiatricCareCard getCareCard() {
        return careCard;
    }

    /**
     * Карта обратившегося за психиатрической помощью
     */
    private PsychiatricCareCard careCard;

    /**
     * Вид принудительного лечения
     */
    @Comment("Вид принудительного лечения")
    @OneToOne
    public VocPsychCompulsoryTreatment getKind() {
        return kind;
    }

    /**
     * Вид принудительного лечения
     */
    private VocPsychCompulsoryTreatment kind;

    /**
     * Суд, принявший решение
     */
    @Comment("Суд, принявший решение")
    @OneToOne
    public VocLawCourt getLawCourt() {
        return lawCourt;
    }

    /**
     * Суд, принявший решение
     */
    private VocLawCourt lawCourt;

    /**
     * Статья уголовного кодекса
     */
    @Comment("Статья уголовного кодекса")
    @OneToOne
    public VocCriminalCodeArticle getCrimainalCodeArticle() {
        return crimainalCodeArticle;
    }

    /**
     * Статья уголовного кодекса
     */
    private VocCriminalCodeArticle crimainalCodeArticle;

    /**
     * Психиатрическая экспертиза
     */
    @Comment("Психиатрическая экспертиза")
    @OneToOne
    public PsychiatricExamination getPsychatricExamination() {
        return psychatricExamination;
    }

    /**
     * Психиатрическая экспертиза
     */
    private PsychiatricExamination psychatricExamination;


    @Comment("Статья уголовного кодекса инфо")
    @Transient
    public String getCrimainalCodeArticleInfo() {
        return crimainalCodeArticle != null ? crimainalCodeArticle.getName() : "";
    }

    @Comment("Суд, принявший решение инфо")
    @Transient
    public String getLawCourtInfo() {
        return lawCourt != null ? lawCourt.getName() : "";
    }

    @Comment("Вид принудительного решения инфо")
    @Transient
    public String getKindInfo() {
        return kind != null ? kind.getCode() : "";
    }

    @Comment("Психиатрическая экспертиза инфо")
    @Transient
    public String getPsychatricExaminationInfo() {
        return psychatricExamination != null ? psychatricExamination.getExpertDecision() : "";
    }


    /**
     * Суд, заменивший тип принудительного лечения
     */
    @Comment("Суд, заменивший тип принудительного лечения")
    @OneToOne
    public VocLawCourt getLawCourtReplace() {
        return lawCourtReplace;
    }

    /**
     * Решение заменено на
     */
    @Comment("Решение заменено на")
    @OneToOne
    public VocPsychCourtTreatmentDecision getCourtDecisionReplace() {
        return courtDecisionReplace;
    }

    /**
     * Решение заменено на
     */
    private VocPsychCourtTreatmentDecision courtDecisionReplace;
    /**
     * Суд, заменивший тип принудительного лечения
     */
    private VocLawCourt lawCourtReplace;
    /**
     * Дата замены
     */
    private Date dateReplace;

    /**
     * Дата регистрации замены
     */
    private Date registrationReplaceDate;

    /**
     * Время редактрования
     */
    private Time editTime;
    /**
     * Время создания
     */
    private Time createTime;

    /**
     * Пользователь, последний редактировавший запись
     */
    private String editUsername;
    /**
     * Пользователь, создавший запись
     */
    private String createUsername;

    /**
     * Дата редактирования
     */
    private Date editDate;
    /**
     * Дата создания
     */
    private Date createDate;
}
