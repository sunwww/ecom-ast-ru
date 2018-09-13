package ru.ecom.mis.ejb.domain.scheduleTask;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Time;

/** Created by rkurbanov on 04.09.2018. */

@Comment("Планировщик задач")
@Entity
@Table(schema="SQLUser")
public class ScheduleTask extends BaseEntity{

    private String name;
    private Time time;
    private String link;

    @Comment("Наименование задачи")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Comment("Время исполнения задачи")
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }

    @Comment("Ссылка задачи")
    public String getLink() {
        return link;
    }
    public void setLink(String linkl) {
        this.link = linkl;
    }
}
