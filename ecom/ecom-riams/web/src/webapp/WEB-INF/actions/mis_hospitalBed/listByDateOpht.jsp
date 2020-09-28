<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:section title="Список направлений на госпитализацию">
    <ecom:webQuery name="stac_planHospitalOpht" nameFldSql="stac_planHospitalOpht_sql"
    nativeSql="select wct.id as id,pat.lastname||' '||pat.firstname||' '||pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as fio
,wct.phone as phone
,to_char(wct.dateokt,'dd.mm.yyyy') as dateokt
,e.name as eye
,wct.comment as cmnt
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as creator
,to_char(wct.createdate,'dd.mm.yyyy')||' '||to_char(wct.createTime,'HH24:MI') as dt
,list(case when wct.medcase_id is not null then 'background-color:green' when wf.isAdministrator='1' then 'background-color:#add8e6' else '' end) as f9_styleRow
 ,wct.id as f10_changeDtsFlds
 ,wct.dateFrom as f11_dateh
from WorkCalendarHospitalBed wct
left join patient pat on wct.patient_id=pat.id
left join voceye e on e.id=wct.eye_id
left join workfunction wf on wf.id=wct.workfunction_id
left join MedCase mc on mc.id=wct.medcase_id
left join vocworkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id = wf.worker_id
left join patient wp on wp.id=w.person_id
where wct.createDate between to_date('${param.startDate}','dd.mm.yyyy')
	 and to_date('${param.finishDate}','dd.mm.yyyy')
  ${statusSql}
  and e.id is not null
group by wct.id,wct.createDate,pat.id
,wct.createDate,mc.dateStart,mc.dateFinish,wct.phone,e.name
,vwf.name,wp.lastname,wp.firstname,wp.middlename
order by wct.createDate,pat.lastname,pat.firstname,pat.middlename
    "
    />
    <msh:table printToExcelButton="Сохранить в excel" name="stac_planHospitalOpht" action="entityView-stac_planOphtHospital.do"
               idField="1" styleRow="9" >
        <msh:tableColumn columnName="#" property="sn"/>
        <msh:tableColumn columnName="ФИО" property="2" />
        <msh:tableColumn columnName="Телефон" property="3" />
        <msh:tableColumn columnName="Дата ОКТ" property="4" />
        <msh:tableColumn columnName="Глаз" property="5" />
        <msh:tableColumn columnName="Замечания" property="6" />
        <msh:tableColumn columnName="Создал" property="7" />
        <msh:tableColumn columnName="Дата и время создания" property="8" />
        <msh:tableColumn columnName="Дата предв. госп" property="11" />
        <msh:tableButton property="10" buttonShortName="Уст. дату" buttonFunction="setDate" hideIfEmpty="true" />

    </msh:table>
    </msh:section>

  </tiles:put>

</tiles:insert>