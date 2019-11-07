<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех планирований введения ангиогенеза" guid="40efbd1b-4177-47a8-9aad-1971732f3f98" />
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu guid="helloSideMenu-123" title="Добавить">
            <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_planOphtHospital" name="Добавить планирование введения ангиогенеза" title="Добавить планирование введения ангиогенеза" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Create" key="ALT+N" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <ecom:webQuery name="list"  nativeSql="
      select wct.id as id,pat.lastname||' '||pat.firstname||' '||pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as fio
,pat.phone as phone
,to_char(wct.dateokt,'dd.mm.yyyy') as dateokt
,e.name as eye
,to_char(wct.datefrom,'dd.mm.yyyy') as dateFrom
,wct.comment as cmnt
,wct.createusername as creator
,to_char(wct.createdate,'dd.mm.yyyy')||' '||to_char(wct.createTime,'HH24:MI') as dt
from WorkCalendarHospitalBed wct
left join patient pat on wct.patient_id=pat.id
left join voceye e on e.id=wct.eye_id
where pat.id='${param.id}' and e.id is not null"/>
        <msh:table name="list" action="entityParentView-stac_planOphtHospital.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
            <msh:tableColumn columnName="ФИО" property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
            <msh:tableColumn columnName="Телефон" property="3" guid="781559cd-fd34-40f5-a214-cec404fe19e3" />
            <msh:tableColumn columnName="Дата ОКТ" property="4" guid="5905cf65-048f-4ce1-8301-5aef1e9ac80e" />
            <msh:tableColumn columnName="Глаз" property="5" guid="2bab495e-eadb-4cd9-b2e9-140bf7a5f43f" />
            <msh:tableColumn columnName="Дата планируемой госпитализации" property="6" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Замечания" property="7" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
            <msh:tableColumn columnName="Создал" property="8" guid="f31b12-3392-4978-b31f-5e54ff2e45bd" />
            <msh:tableColumn columnName="Дата и время создания" property="9" guid="f31b12-3392-4978-b31f-5e54ff2e45bd" />
        </msh:table>
    </tiles:put>
</tiles:insert>