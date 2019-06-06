<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
<%
    long department  = request.getParameter("department")!=null ? Long.valueOf(request.getParameter("department")):0L;
    if (department>0L) {
        request.setAttribute("departmentPlanSql"," and wchb.department_id="+department);

    }
%>
    <msh:section title="Список направлений на госпитализацию">
    <ecom:webQuery name="stac_planHospital" nameFldSql="stac_planHospital_sql"
    nativeSql="select wchb.id,ml.name as mlname,p.id,p.lastname||' '||p.firstname||' '||p.middlename as fio,p.birthday
as birthday,mkb.code,wchb.diagnosis
 ,wchb.dateFrom,mc.dateStart,mc.dateFinish,list(mkbF.code),wchb.phone
 ,wchb.createDate as wchbcreatedate
 ,list(vwf.name ||' '||wPat.lastname) as f14_creator
 ,list(case when wchb.medcase_id is not null then 'background-color:green' when wf.isAdministrator='1' then 'background-color:#add8e6' else '' end) as f15_styleRow
 ,case when wchb.medcase_id is null then wchb.id||'#'||p.id else null end as f16_createHospIds
from WorkCalendarHospitalBed wchb
left join Patient p on p.id=wchb.patient_id
left join MedCase mc on mc.id=wchb.medcase_id
left join VocIdc10 mkb on mkb.id=wchb.idc10_id
left join MisLpu ml on ml.id=wchb.department_id
left join Diagnosis diag on diag.medcase_id=mc.id
left join VocIdc10 mkbF on mkbF.id=diag.idc10_id
left join workfunction wf on wf.id=wchb.workfunction_id
left join worker w on w.id=wf.worker_id
left join patient wpat on wpat.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
where wchb.dateFrom between to_date('${param.startDate}','dd.mm.yyyy')
	 and to_date('${param.finishDate}','dd.mm.yyyy')
 ${departmentPlanSql} ${statusSql}
group by wchb.id,wchb.createDate,ml.name,p.id,p.lastname,p.firstname,p.middlename,p.birthday
,mkb.code,wchb.diagnosis,wchb.dateFrom,mc.dateStart,mc.dateFinish,wchb.phone
order by wchb.dateFrom,p.lastname,p.firstname,p.middlename
    "
    />
    <msh:table printToExcelButton="Сохранить в excel" name="stac_planHospital" action="entityParentView-stac_planHospital.do"
    idField="1" styleRow="15" >
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата пред.госпитализации" property="8"/>
            <msh:tableColumn columnName="Направлен в отделение" property="2"/>
            <msh:tableColumn columnName="Телефон пациента" property="12"/>
            <msh:tableColumn columnName="ФИО пациента" property="4"/>
            <msh:tableColumn columnName="Дата рождения" property="5"/>
            <msh:tableColumn columnName="Код МКБ" property="6"/>
            <msh:tableColumn columnName="Диагноз" property="7"/>
            <msh:tableColumn columnName="Кто создал" property="14"/>
            <msh:tableColumn columnName="Дата создания" property="13"/>
        <msh:tableButton property="16" buttonShortName="ГОСП" buttonFunction="createHosp" hideIfEmpty="true" />

    </msh:table>
    </msh:section>

  </tiles:put>

</tiles:insert>