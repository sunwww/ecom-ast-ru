<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract" >Контрольный отчет по оформленным договорам</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
	<tags:contractMenu currentAction="controlReport"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","1", request) ;
%>
		<msh:form action="contract_reports_accrual.do" defaultField="dateFrom">
			<msh:panel>
				<msh:row>
				<msh:textField property="dateFrom" label="c"/>
				<msh:textField property="dateTo" label="по"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="operator" fieldColSpan="4" label="Оператор" vocName="workFunction" horizontalFill="true"/>
				</msh:row>
        <msh:row>
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1"> расхождения по к/дням (выписанные)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> приближ. превыш. или расхождение
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> еще лежат пациенты
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4"> реестр полный
	        </td>
        </msh:row>				
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
			</msh:panel>
		</msh:form>
<%
		String dateFrom = request.getParameter("dateFrom") ;
		String dFrom = "" ;
		if (dateFrom==null ||dateFrom.equals("") ) {
			dFrom=" is null " ;
		} else {
			dFrom = ">=to_date('"+dateFrom+"', 'dd.mm.yyyy')" ;
		}
		request.setAttribute("dFrom",dFrom) ;
		
		String dateTo = request.getParameter("dateTo") ;
		String dTo = "" ;
		if (dateTo==null ||dateTo.equals("") ) {
			dTo=" is null " ;
		} else {
			dTo = "<=to_date('"+dateTo+"', 'dd.mm.yyyy')" ;
		}
		request.setAttribute("dTo",dTo) ;
		
		String FromTo = "";
		if  (dateTo==null ||dateTo.equals("") ) {}
		else if (dateFrom==null ||dateFrom.equals("") ) {}
		else FromTo="C "+dFrom+" По "+dTo;
		
		if (typeGroup.equals("1")) {
			// Группировка по дате
			request.setAttribute("groupSql", "to_char(CAo.operationdate,'dd.mm.yyyy')") ;
       		request.setAttribute("groupSqlId", "'&dateFrom='||to_char(CAo.operationdate,'dd.mm.yyyy')||'&dateTo='||to_char(CAo.operationdate,'dd.mm.yyyy')") ;
       		request.setAttribute("groupName", "Дате операции") ;
       		request.setAttribute("groupGroup", "CAo.operationdate") ;
       		request.setAttribute("groupGroupNext", "2") ;
       		request.setAttribute("groupOrder", "CAo.operationdate") ;
		} else if (typeGroup.equals("2")) {
			// Группировка по операторам
   			request.setAttribute("groupSql", "wp.lastname||' '||wp.firstname||' '||wp.middlename") ;
   			request.setAttribute("groupSqlId", "'&operator='||wf.id") ;
   			request.setAttribute("groupName", "Оператор") ;
   			request.setAttribute("groupGroup", "wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
       		request.setAttribute("groupGroupNext", "4") ;
   			request.setAttribute("groupOrder", "wp.lastname") ;
		} else if (typeGroup.equals("3")) {
			// Группировка по услугам 
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSqlId", "'&medService='||pms.id") ;
   			request.setAttribute("groupName", "Услуга") ;
       		request.setAttribute("groupGroupNext", "4") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name") ;
   			request.setAttribute("groupOrder", "pp.code") ;
		} else {
			//Реестр
   			request.setAttribute("groupSql", "pms.name") ;
   			request.setAttribute("groupSqlId", "'&medService='||pms.id") ;
   			request.setAttribute("groupName", "Сотрудник") ;
   			request.setAttribute("groupGroup", "pms.id,pms.code,pms.name") ;
   			request.setAttribute("groupOrder", "pms.code") ;
		}
		ActionUtil.setParameterFilterSql("operator","cao.workFunction_id", request) ;
		ActionUtil.setParameterFilterSql("medService","pms.id", request) ;
		%>
		<% if (typeGroup!=null && typeGroup.equals("1") ) {%>
			<msh:section title="Контрольный отчет по договорам за период ${param.dateFrom}-${param.dateTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
select sls.id as slsid,ss.code as sscode,pat.lastname||' '||pat.firstname||' '||pat.middlename as patinfo
,ml.name as mlname
,vrt.name  ||' №'||list(DISTINCT wp.name)as vrtname
,case when sls.emergency='1' then 'Э' else 'П' end as emergency
,to_char(sls.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.dateFinish,'dd.mm.yyyy'),'по наст.время') as periodsls
,list(to_char(slo.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(slo.transferDate,'dd.mm.yyyy'),to_char(slo.dateFinish,'dd.mm.yyyy'),'по наст.время')) as periodslo
,sum(coalesce(slo.dateFinish,slo.transferdate,CURRENT_DATE)-slo.dateStart)
||case when sls.dateFinish is null then '-' else '' end as factd
,(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
    and wfs.roomType_id=wp.roomType_id
where cp.patient_id=pat.id
) as acDays
,(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
where cp.patient_id=pat.id
) as oplBed
from medcase slo
left join medcase sls on sls.id=slo.parent_id
left join statisticstub ss on ss.id=sls.statisticStub_id
left join bedfund bf on bf.id=slo.bedfund_id
left join workPlace wp on wp.id=slo.roomNumber_id
left join Patient pat on pat.id=slo.patient_id
left join VocRoomType vrt on vrt.id=wp.roomType_id
left join mislpu ml on ml.id=slo.department_id
where slo.dtype='DepartmentMedCase'
and slo.serviceStream_id=6
and sls.dateStart between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy')
and sls.dateFinish is not null
group by pat.id,pat.lastname,pat.firstname,pat.middlename,sls.id,
sls.dateStart,sls.dateFinish,slo.department_id,bf.bedtype_id,bf.bedsubtype_id,wp.roomType_id,vrt.name,ml.name
,sls.emergency,ss.code

having

(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
    and wfs.roomType_id=wp.roomType_id
where cp.patient_id=pat.id
)!=sum(coalesce(slo.dateFinish,slo.transferdate,CURRENT_DATE)-slo.dateStart)
order by pat.lastname,pat.firstname,pat.middlename
			"/>

				<msh:table name="finansReport" 
				action="entitySubclassView-mis_medCase.do"
				viewUrl="entitySubclassShortView-mis_medCase.do" 
				idField="1">
					<msh:tableColumn columnName="Стат.карта" property="2" />
					<msh:tableColumn columnName="ФИО пациента" property="3" />
					<msh:tableColumn columnName="Отделение" property="4" />
					<msh:tableColumn columnName="Палата" property="5" />
					<msh:tableColumn columnName="Показания" property="6" />
					<msh:tableColumn columnName="Факт. к.дней" property="9" />
					<msh:tableColumn columnName="Опл. к.дней" property="10" />
					<msh:tableColumn columnName="Опл. к.дней (без учета ур. палаты)" property="11" />
					<msh:tableColumn columnName="Период госпитализации" property="7" />
					<msh:tableColumn columnName="Периоды по отделению" property="8" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("2")) {%>
			<msh:section title="Расхождения, за период ${param.dateFrom}-${param.dateTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
select sls.id as slsid,ss.code as sscode,pat.lastname||' '||pat.firstname||' '||pat.middlename as patinfo
,ml.name as mlname
,vrt.name  ||' №'||list(DISTINCT wp.name)as vrtname
,case when sls.emergency='1' then 'Э' else 'П' end as emergency
,to_char(sls.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.dateFinish,'dd.mm.yyyy'),'по наст.время') as periodsls
,list(to_char(slo.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(slo.transferDate,'dd.mm.yyyy'),to_char(slo.dateFinish,'dd.mm.yyyy'),'по наст.время')) as periodslo
,sum(coalesce(slo.dateFinish,slo.transferdate,CURRENT_DATE)-slo.dateStart)
||case when sls.dateFinish is null then '-' else '' end as factd
,(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
    and wfs.roomType_id=wp.roomType_id
where cp.patient_id=pat.id
) as acDays
,(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
where cp.patient_id=pat.id
) as oplBed
from medcase slo
left join medcase sls on sls.id=slo.parent_id
left join statisticstub ss on ss.id=sls.statisticStub_id
left join bedfund bf on bf.id=slo.bedfund_id
left join workPlace wp on wp.id=slo.roomNumber_id
left join Patient pat on pat.id=slo.patient_id
left join VocRoomType vrt on vrt.id=wp.roomType_id
left join mislpu ml on ml.id=slo.department_id
where slo.dtype='DepartmentMedCase'
and slo.serviceStream_id=6
and sls.dateStart between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy')
and sls.dateFinish is null

group by pat.id,pat.lastname,pat.firstname,pat.middlename,sls.id,
sls.dateStart,sls.dateFinish,slo.department_id,bf.bedtype_id,bf.bedsubtype_id,wp.roomType_id,vrt.name,ml.name
,sls.emergency,ss.code

having

(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
    and wfs.roomType_id=wp.roomType_id
where cp.patient_id=pat.id
)<=sum(coalesce(slo.dateFinish,slo.transferdate,CURRENT_DATE)-slo.dateStart)
order by pat.lastname,pat.firstname,pat.middlename
			"/>

				<msh:table name="finansReport" 
				action="entitySubclassView-mis_medCase.do"
				viewUrl="entitySubclassShortView-mis_medCase.do" 
				idField="1">
					<msh:tableColumn columnName="Стат.карта" property="2" />
					<msh:tableColumn columnName="ФИО пациента" property="3" />
					<msh:tableColumn columnName="Отделение" property="4" />
					<msh:tableColumn columnName="Палата" property="5" />
					<msh:tableColumn columnName="Показания" property="6" />
					<msh:tableColumn columnName="Факт. к.дней" property="9" />
					<msh:tableColumn columnName="Опл. к.дней" property="10" />
					<msh:tableColumn columnName="Опл. к.дней (без учета ур. палаты)" property="11" />
					<msh:tableColumn columnName="Период госпитализации" property="7" />
					<msh:tableColumn columnName="Периоды по отделению" property="8" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("3")) {%>
			<msh:section title="Еще находятся на лечение, за период ${param.dateFrom}-${param.dateTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
select sls.id as slsid,ss.code as sscode,pat.lastname||' '||pat.firstname||' '||pat.middlename as patinfo
,ml.name as mlname
,vrt.name  ||' №'||list(DISTINCT wp.name)as vrtname
,case when sls.emergency='1' then 'Э' else 'П' end as emergency
,to_char(sls.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.dateFinish,'dd.mm.yyyy'),'по наст.время') as periodsls
,list(to_char(slo.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(slo.transferDate,'dd.mm.yyyy'),to_char(slo.dateFinish,'dd.mm.yyyy'),'по наст.время')) as periodslo
,sum(coalesce(slo.dateFinish,slo.transferdate,CURRENT_DATE)-slo.dateStart)
||case when sls.dateFinish is null then '-' else '' end as factd
,(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
    and wfs.roomType_id=wp.roomType_id
where cp.patient_id=pat.id
) as acDays
,(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
where cp.patient_id=pat.id
) as oplBed
from medcase slo
left join medcase sls on sls.id=slo.parent_id
left join statisticstub ss on ss.id=sls.statisticStub_id
left join bedfund bf on bf.id=slo.bedfund_id
left join workPlace wp on wp.id=slo.roomNumber_id
left join Patient pat on pat.id=slo.patient_id
left join VocRoomType vrt on vrt.id=wp.roomType_id
left join mislpu ml on ml.id=slo.department_id
where slo.dtype='DepartmentMedCase'
and slo.serviceStream_id=6
and sls.dateStart between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy')
and sls.dateFinish is null
group by pat.id,pat.lastname,pat.firstname,pat.middlename,sls.id,
sls.dateStart,sls.dateFinish,slo.department_id,bf.bedtype_id,bf.bedsubtype_id,wp.roomType_id,vrt.name,ml.name
,sls.emergency,ss.code
order by pat.lastname,pat.firstname,pat.middlename
			"/>

				<msh:table name="finansReport" 
				action="entitySubclassView-mis_medCase.do"
				viewUrl="entitySubclassShortView-mis_medCase.do" 
				idField="1">
					<msh:tableColumn columnName="Стат.карта" property="2" />
					<msh:tableColumn columnName="ФИО пациента" property="3" />
					<msh:tableColumn columnName="Отделение" property="4" />
					<msh:tableColumn columnName="Палата" property="5" />
					<msh:tableColumn columnName="Показания" property="6" />
					<msh:tableColumn columnName="Факт. к.дней" property="9" />
					<msh:tableColumn columnName="Опл. к.дней" property="10" />
					<msh:tableColumn columnName="Опл. к.дней (без учета ур. палаты)" property="11" />
					<msh:tableColumn columnName="Период госпитализации" property="7" />
					<msh:tableColumn columnName="Периоды по отделению" property="8" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("4")) {%>
			<msh:section title="Реестр ${FromTo} ">
			<ecom:webQuery name="finansReport" nativeSql="
select sls.id as slsid,ss.code as sscode,pat.lastname||' '||pat.firstname||' '||pat.middlename as patinfo
,ml.name as mlname
,vrt.name  ||' №'||list(DISTINCT wp.name)as vrtname
,case when sls.emergency='1' then 'Э' else 'П' end as emergency
,to_char(sls.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.dateFinish,'dd.mm.yyyy'),'по наст.время') as periodsls
,list(to_char(slo.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(slo.transferDate,'dd.mm.yyyy'),to_char(slo.dateFinish,'dd.mm.yyyy'),'по наст.время')) as periodslo
,sum(coalesce(slo.dateFinish,slo.transferdate,CURRENT_DATE)-slo.dateStart)
||case when sls.dateFinish is null then '-' else '' end as factd
,(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
    and wfs.roomType_id=wp.roomType_id
where cp.patient_id=pat.id
) as acDays
,(select
sum(case when wfs.medservice_id = pms.medservice_id then cas.countmedservice else 0 end)
from ContractPerson cp
left join ServedPerson sp on sp.Person_id=cp.id
left join ContractAccount ca on ca.id=sp.account_id
left join ContractAccountOperation cao on cao.account_id=ca.id and cao.dtype='OperationAccrual'
and cao.operationdate  between sls.dateStart-3 and coalesce(sls.dateFinish,current_date)
left join contractaccountoperationbyservice caos on caos.accountoperation_id = cao.id
left join contractaccountmedservice cas on caos.accountmedservice_id = cas.id and cas.servedperson_id = sp.id
left join pricemedservice pms on pms.id=cas.medservice_id
left join priceposition pp on pp.id=pms.priceposition_id and pp.expunit='койко-день'
left join medservice ms on ms.id=pms.medservice_id
left join workfunctionservice wfs on wfs.lpu_id=slo.department_id
    and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id
where cp.patient_id=pat.id
) as oplBed
from medcase slo
left join medcase sls on sls.id=slo.parent_id
left join statisticstub ss on ss.id=sls.statisticStub_id
left join bedfund bf on bf.id=slo.bedfund_id
left join workPlace wp on wp.id=slo.roomNumber_id
left join Patient pat on pat.id=slo.patient_id
left join VocRoomType vrt on vrt.id=wp.roomType_id
left join mislpu ml on ml.id=slo.department_id
where slo.dtype='DepartmentMedCase'
and slo.serviceStream_id=6
and sls.dateStart between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy')
group by pat.id,pat.lastname,pat.firstname,pat.middlename,sls.id,
sls.dateStart,sls.dateFinish,slo.department_id,bf.bedtype_id,bf.bedsubtype_id,wp.roomType_id,vrt.name,ml.name
,sls.emergency,ss.code
order by pat.lastname,pat.firstname,pat.middlename
			"/>

				<msh:table name="finansReport" 
				action="entitySubclassView-mis_medCase.do"
				viewUrl="entitySubclassShortView-mis_medCase.do" 
				idField="1">
					<msh:tableColumn columnName="Стат.карта" property="2" />
					<msh:tableColumn columnName="ФИО пациента" property="3" />
					<msh:tableColumn columnName="Отделение" property="4" />
					<msh:tableColumn columnName="Палата" property="5" />
					<msh:tableColumn columnName="Показания" property="6" />
					<msh:tableColumn columnName="Факт. к.дней" property="9" />
					<msh:tableColumn columnName="Опл. к.дней" property="10" />
					<msh:tableColumn columnName="Опл. к.дней (без учета ур. палаты)" property="11" />
					<msh:tableColumn columnName="Период госпитализации" property="7" />
					<msh:tableColumn columnName="Периоды по отделению" property="8" />
				</msh:table>

			</msh:section>

	<%} %>
	</tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    </script>
    </tiles:put>
</tiles:insert>