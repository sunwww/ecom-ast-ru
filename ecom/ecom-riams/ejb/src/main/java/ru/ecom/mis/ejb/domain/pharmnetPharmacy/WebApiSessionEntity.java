package ru.ecom.mis.ejb.domain.pharmnetPharmacy;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by rkurbanov on 13.02.2018.
 */
@Entity
@Table(name = "webApiSession", schema="pharmnet")
public class WebApiSessionEntity {

    private Integer id;
    private String webApiSession;
    private Date dateStart;
    private Time timeStart;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false,nullable = false)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "webApiSession")
    public String getWebApiSession() {
        return webApiSession;
    }
    public void setWebApiSession(String webApiSession) {
        this.webApiSession = webApiSession;
    }

    @Basic
    @Column(name = "dateStart")
    public Date getDateStart() {
        return dateStart;
    }
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "timeStart")
    public Time getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }
}
