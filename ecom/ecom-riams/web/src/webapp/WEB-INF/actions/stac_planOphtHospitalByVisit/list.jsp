<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail beginForm="smo_visitForm" mainMenu="Patient" title="Список всех планирований введения ангиогенеза в визите" />
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu title="Добавить">
            <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_planOphtHospitalByVisit" name="Добавить планирование введения ангиогенеза" title="Добавить планирование введения ангиогенеза" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Create" key="ALT+N" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <ecom:webQuery name="list"  nativeSql="
      select wct.id as id,pat.lastname||' '||pat.firstname||' '||pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as fio
,pat.phone as phone
,to_char(wct.dateokt,'dd.mm.yyyy') as dateokt
,e.name as eye
,wct.comment as cmnt
,wct.createusername as creator
,to_char(wct.createdate,'dd.mm.yyyy')||' '||to_char(wct.createTime,'HH24:MI') as dt
from WorkCalendarHospitalBed wct
left join patient pat on wct.patient_id=pat.id
left join voceye e on e.id=wct.eye_id
where wct.dtype='PlanOphtHospital' and wct.visit_id='${param.id}'"/>
        <msh:table name="list" action="entityParentView-stac_planOphtHospitalByVisit.do" idField="1">
            <msh:tableColumn columnName="ФИО" property="2" />
            <msh:tableColumn columnName="Телефон" property="3" />
            <msh:tableColumn columnName="Дата ОКТ" property="4" />
            <msh:tableColumn columnName="Глаз" property="5" />
            <msh:tableColumn columnName="Замечания" property="6" />
            <msh:tableColumn columnName="Создал" property="7" />
            <msh:tableColumn columnName="Дата и время создания" property="8" />
        </msh:table>
    </tiles:put>
</tiles:insert>