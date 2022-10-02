<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Contract">Учёт направлений платных пациентов</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
        <tags:contractMenu currentAction="serviciesPolicReport"/>
    </tiles:put>
    <tiles:put name='body' type='string' >
        <msh:form action="/visit_report_service_charged.do" defaultField="dateFrom">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateFrom" label="Период с" />
                    <msh:textField property="dateTo" label="по" />
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>

        <%
            String dateFrom = request.getParameter("dateFrom") ;
            if (dateFrom!=null && !dateFrom.equals("")) {
                request.setAttribute("dateFrom",dateFrom);
            }
            String dateTo = request.getParameter("dateTo") ;
            if (dateTo==null || dateTo.equals("")) dateTo=dateFrom;
            if (dateTo!=null && !dateTo.equals("")) {
                request.setAttribute("dateTo",dateTo);
            }
            if (request.getParameter("dateFrom")!=null &&  !request.getParameter("dateFrom").equals("") || request.getParameter("short")!=null) {
                if (request.getParameter("short")==null) {
        %>
        <msh:section>
            <ecom:webQuery name="finansReport" nameFldSql="finansReport_sql" nativeSql="
select pp.id as ppid,pp.code as ppcode,pp.name as ppname
,wp.lastname||' '||wp.firstname||' '||wp.middlename as fio,count(distinct caos.id) as cnt
,sum(round(1*(cams.cost*(100-coalesce(cao.discount,0))/100),2)) as sumRender
,list(caos.id||'') as f8_caoses
from ContractAccountOperationByService caos
left join ContractAccountOperation cao on caos.accountOperation_id=cao.id and cao.dtype='OperationAccrual' and cao.repealOperation_id is null
left join medcase mc on mc.id=caos.medcase_id
left join WorkFunction wf on wf.id=mc.orderworkfunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join ContractAccountMedService cams on cams.id=caos.accountMedService_id
left join ServedPerson sp on cao.account_id=sp.account_id
left join ContractPerson cp on sp.person_id=cp.id
left join Patient p on cp.patient_id=p.id
left join pricemedservice pms on cams.medService_id=pms.id
left join priceposition pp on pms.priceposition_id=pp.id
left join priceposition ppG on ppG.id=pp.parent_id
left join mislpu ml on ml.id=w.lpu_id
left join medservice ms on pms.medservice_id=ms.id
left join vocservicestream sstr on sstr.id=mc.servicestream_id
left join workfunction wfex on wfex.id=mc.workfunctionexecute_id
left join worker wex on wex.id=wfex.worker_id
left join mislpu mlex on mlex.id=wex.lpu_id
where cao.operationdate
between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy')
and sstr.code='CHARGED'
and vwf.name like 'Врач%'
and ml.id in (180,409,246)
and mlex.isforreportcharged=true
group by ppG.lpu_id,pp.id,pp.code,pp.name,wp.lastname,wp.firstname,wp.middlename,vwf.name
order by pp.name
			"/>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="finansReport"
                           action="visit_report_service_charged.do?short=Short" idField="7">
                    <msh:tableColumn columnName="Код услуги" property="2" />
                    <msh:tableColumn columnName="Наименование" property="3" />
                    <msh:tableColumn columnName="ФИО направителя" property="4" />
                    <msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="5" />
                    <msh:tableColumn columnName="Сумма" isCalcAmount="true" property="6" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
        } else  {%>
        <msh:section title="Реестр по пациентам ">
            <ecom:webQuery name="finansReportReestr" nameFldSql="finansReportReestr_sql" nativeSql="
select caos.id as caosid,p.patientSync,p.lastname||' '||p.firstname||' '||p.middlename as fiopat
,to_char(cao.operationdate,'dd.mm.yyyy') as operdate
,pp.code||' '||pp.name as rendername
,sum(round(cams.cost*((100-coalesce(cao.discount,0))/100),2)) as sumRender
,p.id as f7_pid
from ContractAccountOperationByService caos
left join ContractAccountOperation cao on caos.accountOperation_id=cao.id and cao.dtype='OperationAccrual' and cao.repealOperation_id is null
left join ContractAccountMedService cams on cams.id=caos.accountMedService_id
left join ServedPerson sp on cao.account_id=sp.account_id
left join ContractPerson cp on sp.person_id=cp.id
left join Patient p on cp.patient_id=p.id
left join pricemedservice pms on cams.medService_id=pms.id
left join priceposition pp on pms.priceposition_id=pp.id
where caos.id in (${param.id})
group by caos.id,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,pp.code,pp.name
,cao.operationdate
order by p.lastname,p.firstname,p.middlename,pp.name
			"/>

            <msh:table name="finansReportReestr" printToExcelButton="Сохранить в excel"  action="entityView-mis_patient.do" idField="7" openNewWindow="true">
                <msh:tableColumn columnName="Код пациента" property="2"/>
                <msh:tableColumn columnName="ФИО" property="3" />
                <msh:tableColumn columnName="Дата услуги"  property="4" />
                <msh:tableColumn columnName="Код услуги" property="5" />
                <msh:tableColumn columnName="Сумма" isCalcAmount="true" property="6" />
            </msh:table>
        </msh:section>
        <%} }%>
    </tiles:put>
</tiles:insert>