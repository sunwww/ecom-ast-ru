<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Поиск текущих и ближайших пациентов</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/arrivedAO.do" defaultField="" disableFormDataConfirm="true" method="GET">
        </msh:form>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="true" name="total" nameFldSql="total_sql" nativeSql="
                 select distinct pat.id,pat.patientinfo
                ,case when hmc.id is not null and hmc.datefinish is null and hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null
                then dep.name||' '||to_char(hmc.dateStart,'dd.mm.yyyy')
                else case when wct.id is not null and wcd.calendardate between current_date and current_date+3
                and (wct.isdeleted is null or wct.isdeleted='0') then
                to_char(wcd.calendardate,'dd.mm.yyyy')||' '||to_char(wct.timefrom,'HH24:MM') ||' '||
                case when wf.dtype='GroupWorkFunction' then wf.groupname else vwf.name||' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename
                end end end
                from patient pat
                left join patientlistrecord plr on plr.patient=pat.id
                left join medcase hmc on hmc.patient_id=pat.id and hmc.patient_id=plr.patient
                left join workcalendartime wct on wct.prepatient_id=pat.id and wct.prepatient_id=plr.patient
                left join workcalendarday wcd on wcd.id=wct.workcalendarday_id
                left join workcalendar wc on wc.id=wcd.workcalendar_id
                left join workfunction wf on wf.id=wc.workfunction_id
                left join vocworkfunction vwf on vwf.id=wf.workfunction_id
                left join worker w on w.id=wf.worker_id
                left join patient wpat on wpat.id=w.person_id
                left join mislpu dep on dep.id=hmc.department_id
                where plr.id is not null and plr.patientlist =3
                and (hmc.id is not null and hmc.datefinish is null and hmc.dtype='HospitalMedCase' and hmc.deniedhospitalizating_id is null) or
                (wct.id is not null and wcd.calendardate between current_date and current_date+3
                and (wct.isdeleted is null or wct.isdeleted='0'))
                group by pat.id,hmc.id,dep.name,wct.id,wcd.calendardate,vwf.name,wpat.id,wf.dtype, wf.groupname"/>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="total"
                           action="entityView-mis_patient.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="ФИО, ДР пациента" property="2"/>
                    <msh:tableColumn columnName="СЛС/направление" property="3"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
    </tiles:put>
</tiles:insert>