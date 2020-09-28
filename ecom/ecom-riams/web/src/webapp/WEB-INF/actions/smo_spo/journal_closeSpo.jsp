<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по закрытым СПО</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
         <tags:visit_finds currentAction="smo_journal_closeSpo"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typeView =ActionUtil.updateParameter("Report_CloseSpo","typeView","3", request) ;
	String typeCntVisit =ActionUtil.updateParameter("Report_CloseSpo","typeCntVisit","3", request) ;
  String shortParam = request.getParameter("short") ;
  if (shortParam==null || !shortParam.equals("Short")) {
  %>
    <msh:form action="/smo_journal_closeSpo.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
 
     <msh:row>
        <td class="label" title="Просмотр (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отчет:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  2 и более случаев
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2">  есть открытый СПО
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3">  реестр СПО
        </td>
        </msh:row>
     <msh:row>
        <td class="label" title="Просмотр СПО (typeCntVisit)" colspan="1"><label for="typeCntVisitName" id="typeCntVisitLabel">Кол-во посещ. в СПО:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeCntVisit" value="1">  2 и более посещ.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeCntVisit" value="2">  1 посещ.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeCntVisit" value="3">  все СПО
        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="5" label="ЛПУ" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" fieldColSpan="5" label="Специалист" horizontalFill="true" vocName="workFunctionByDirect"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="5" label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="beginDate" label="Период с" />
	        <msh:textField property="finishDate" label="по" />
			<td>
	            <input type="submit" onclick="find()" value="Найти" />
	          </td>
	          <td><msh:link action="js-smo_spo-deleteEmptySpo.do" roles="/Policy/Mis/MedCase/Visit/DeleteEmptySpo">Удалить пустые СПО</msh:link></td>
        </msh:row>
    </msh:panel>
    </msh:form>
    <%} %>
    <%
    String date = request.getParameter("beginDate") ;
    if (date!=null && !date.equals(""))  {
    	String finishDate = request.getParameter("finishDate") ;
    	if (finishDate==null||finishDate.equals("")) {
    		finishDate = date ;
    	}
        request.setAttribute("finishDate", finishDate) ;
    	request.setAttribute("beginDate", date) ;
        request.setAttribute("isReportBase", ActionUtil.isReportBase(date,finishDate,request));
    	if ("1".equals(typeView)) {
    		request.setAttribute("additionJoinSql", " left join medcase spo1 on spo1.patient_id=spo.patient_id and spo1.dtype='PolyclinicMedCase'" +
                    " left join WorkFunction owf1 on owf1.id=spo1.ownerFunction_id     				" +
                    " left join Worker ow1 on ow1.id=owf1.worker_id ") ;
    		request.setAttribute("additionWhereSql", " and spo1.dateFinish between to_date('"+date+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy') and owf.workFunction_id=owf1.workFunction_id  and ow.lpu_id=ow1.lpu_id and spo1.id!=spo.id ") ;
    	} else if ("2".equals(typeView)) {
    		request.setAttribute("additionJoinSql", " left join medcase spo1 on spo1.patient_id=spo.patient_id and spo1.dtype='PolyclinicMedCase'" +
                    " left join WorkFunction owf1 on owf1.id=spo1.ownerFunction_id" +
                    " left join Worker ow1 on ow1.id=owf1.worker_id ") ;
    		request.setAttribute("additionWhereSql", " and spo1.dateFinish is null and owf.workFunction_id=owf1.workFunction_id  and ow.lpu_id=ow1.lpu_id and spo1.id!=spo.id ") ;
    		request.setAttribute("additionSpoSql", " and (select count(spo1.id) from medcase spo1 left join WorkFunction owf1 on owf1.id=spo1.ownerFunction_id left join Worker ow1 on ow1.id=owf1.worker_id where spo.patient_id=spo1.patient_id and spo1.dtype='PolyclinicMedCase' and owf.workFunction_id=owf1.workFunction_id and spo1.dateStart between to_date('"+date+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy') and ow.lpu_id=ow1.lpu_id and spo1.dateFinish is null)>1") ;
    	}
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.trim().equals("") && !dep.equals("0")) {
    		request.setAttribute("departmentSql", " and ow.lpu_id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
            request.setAttribute("departmentSql", "");
    	}
    	String spec = request.getParameter("spec") ;
    	if (spec!=null && !spec.equals("") && !spec.equals("0")) {
    		request.setAttribute("workFunctionSql", " and vis.workFunctionExecute_id="+spec) ;
    		request.setAttribute("workFunction",spec) ;
    	} else {
    		request.setAttribute("workFunction","0") ;
    	}
    	String servStream = request.getParameter("serviceStream") ;
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("serviceStreamSql", " and vss.id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    	}
        request.setAttribute("login", LoginInfo.find(request.getSession(true)).getUsername()) ;
        %>
        <ecom:webQuery name="infoByLogin"
        maxResult="1" nativeSql="
        select wf.id,w.lpu_id,case when wf.isAdministrator='1' then '1' else null end as isAdmin
        from SecUser su
        left join WorkFunction wf on su.id=wf.secUSer_id
        left join Worker w on wf.worker_id=w.id
        where su.login='${login}'
        "
        />
        <%
        	boolean isViewAllDepartment=RolesHelper.checkRoles("/Policy/Mis/MedCase/Visit/ViewAll",request) ;
        	List list= (List)request.getAttribute("infoByLogin");
	    	WebQueryResult wqr = list.isEmpty() ? null : (WebQueryResult)list.get(0) ;
	    	//WebQueryResult wqr = null ;
        	String curator = request.getParameter("specialist") ;
        	String workFunc = wqr!=null ? ""+wqr.get1() : "0" ;
        	boolean isBossDepartment= wqr!=null && wqr.get3()!=null;
        	if (typeCntVisit.equals("1")) {
        		request.setAttribute("typeCntVisitSql", " and (select count(visD.id) from medcase visD where visD.parent_id=spo.id and (visD.dtype='Visit' or visD.dtype='ShortMedCase') and (visD.noActuality='0' or visD.noActuality is null))>1") ;
        	} else if (typeCntVisit.equals("2")){
        		request.setAttribute("typeCntVisitSql", " and (select count(visD.id) from medcase visD where visD.parent_id=spo.id and (visD.dtype='Visit' or visD.dtype='ShortMedCase') and (visD.noActuality='0' or visD.noActuality is null))=1") ;
        	} else {
        		request.setAttribute("typeCntVisitSql", "") ;
        	}
        	int type=0 ;
        	if (curator!=null && !curator.equals("0")&& !curator.equals("")) {
        		type=3 ;
        		if (!isViewAllDepartment&&!isBossDepartment&&!curator.equals(workFunc)) {
        			curator=workFunc ;
        		}
        	} else if (dep!=null && !dep.equals("0")&& !dep.equals("") && (isViewAllDepartment || isBossDepartment)) {
        		type=2 ;
       		} else if (isViewAllDepartment) {
       			type=1 ;
       		} else if (wqr!=null) {
       			if (isBossDepartment) {
       				type=2 ;
       				if ((wqr.get2()!=null && !wqr.get2().equals("0") && !wqr.get2().equals(""))) {
       					dep=""+wqr.get2() ;
       				} else {
       					dep="0" ;
       				}
       			} else {
       				type=3 ;
       				curator=workFunc ;
       				dep=""+wqr.get2() ;
       			}
       		}
       		request.setAttribute("department", dep) ;
       		request.setAttribute("curator", curator) ;
       	%>
        	    
    <%if (type==1) { %>
    <msh:section>
    <msh:sectionTitle>Свод по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="datelist" nativeSql="
    select ml.id||'&department='||ml.id,ml.name ,count(distinct spo.patient_id) as cntPat,count(distinct spo.id) as cntSpo

,count(distinct case when spo.dateStart!=spo.dateFinish then spo.id end) as f5_cntLongSpo
,count(distinct case when spo.dateStart!=spo.dateFinish then smo.id end) as f6_cntVisitInLongSpo
,count(distinct case when spo.dateStart=spo.dateFinish then spo.id end) as f7_cntShortSpo
	from MedCase spo
	left join MedCase smo on smo.parent_id=spo.id
	left join VocServiceStream vss on vss.id=spo.serviceStream_id
	left join WorkFunction owf on owf.id=spo.ownerFunction_id
	left join Worker ow on ow.id=owf.worker_id
	left join MisLpu ml on ow.lpu_id=ml.id 
	${additionJoinSql}
	where spo.dtype='PolyclinicMedCase' 
	and spo.dateFinish between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
	${additionWhereSql} ${serviceStreamSql}
	${typeCntVisitSql}
	group by ml.id,ml.name order by ml.name"  nameFldSql="datelist_sql"
	/>
    <msh:table name="datelist" 
    viewUrl="smo_journal_closeSpo.do?short=Short&typeView=${typeView}&serviceStream=${param.serviceStream}&beginDate=${beginDate}&finishDate=${finishDate}"
    action="smo_journal_closeSpo.do?typeView=${typeView}&serviceStream=${param.serviceStream}&beginDate=${beginDate}&finishDate=${finishDate}" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во пацентов" property="3" isCalcAmount="true" />
      <msh:tableColumn columnName="Кол-во СПО" property="4" isCalcAmount="true" />
      <msh:tableColumn columnName="Кол-во обращений" property="5" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во визитов в обращениях" property="6" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во посещений" property="7" isCalcAmount="true"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% 
    } else if (type==2 )  {
    %>
    <msh:section>
    <msh:sectionTitle>Реестр по лечащим врачам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="datelist" nativeSql="
    select 
owf.id||'&department=${department}&specialist='||owf.id as id
,ovwf.name as ovwfname,owp.lastname||' '||owp.firstname||' '||owp.middlename as lechVr
,count(distinct spo.patient_id) as cntPatient,count(distinct spo.id) as cntSpo
,count(distinct case when spo.dateStart!=spo.dateFinish then spo.id end) as f6_cntLongSpo
,count(distinct case when spo.dateStart!=spo.dateFinish then smo.id end) as f7_cntVisitInLongSpo
,count(distinct case when spo.dateStart=spo.dateFinish then spo.id end) as f8_cntShortSpo
from MedCase spo
left join MedCase smo on smo.parent_id=spo.id
left join Patient pat on spo.patient_id=pat.id 
left join WorkFunction owf on spo.ownerFunction_id=owf.id 
left join VocWorkFunction ovwf on owf.workFunction_id=ovwf.id 
left join Worker ow on owf.worker_id=ow.id 
left join Patient owp on ow.person_id=owp.id 
${additionJoinSql}
where spo.dtype='PolyclinicMedCase' 
and spo.dateFinish  between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
 ${departmentSql}
 ${additionWhereSql}  ${typeCntVisitSql}
group by owf.id,ovwf.name,owp.lastname,owp.middlename,owp.firstname 
order by owp.lastname,owp.middlename,owp.firstname
    " nameFldSql="datelist_sql" />
    <msh:table name="datelist" 
    viewUrl="smo_journal_closeSpo.do?short=Short&typeView=${typeView}&serviceStream=${param.serviceStream}&beginDate=${beginDate}&finishDate=${finishDate}"
    action="smo_journal_closeSpo.do?typeView=${typeView}&serviceStream=${param.serviceStream}&beginDate=${beginDate}&finishDate=${finishDate}" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Должность" property="2" />
      <msh:tableColumn columnName="Лечащий врач" property="3" />
      <msh:tableColumn columnName="Кол-во пациентов" property="4" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во СПО" property="5" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во обращений" property="6" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во визитов в обращениях" property="7" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во посещений" property="8" isCalcAmount="true"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
         <% } else if (type==3 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="datelist" nativeSql="
select spo.id,spo.dateStart,spo.dateFinish
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday  
    ,coalesce(spo.dateFinish,CURRENT_DATE)-spo.dateStart+1 as cnt1
    ,max(vis.dateStart) as maxvisDateStart
    ,case when max(vis.dateStart) is not null then current_date-max(vis.dateStart) else null end as cntdaymax
    ,to_char(max(case when vis.dateStart is null then wcd.calendarDate else null end),'dd.mm.yyyy') as maxPlanDate
    ,list(distinct vvr.name) as vvrname,list(distinct mkb.code||' '||vpd.name) as diag
    ,list(distinct vr.name) as vrname
    from medCase spo 
    left join MedCase vis on vis.parent_id=spo.id 
    left join Diagnosis diag on diag.medcase_id=vis.id
    left join VocIdc10 mkb on mkb.id=diag.idc10_id
    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
    left join VocVisitResult vvr on vvr.id=vis.visitResult_id
    left join WorkCalendarDay wcd on wcd.id=vis.datePlan_id
    left join Patient pat on spo.patient_id = pat.id 
    left join WorkFunction owf on spo.ownerFunction_id=owf.id 
	left join Worker ow on owf.worker_id=ow.id 
	left join Patient owp on ow.person_id=owp.id 
	LEFT JOIN VocReason vr on vr.id=vis.visitReason_id 
	${additionJoinSql}
    where spo.ownerFunction_id='${curator}' 
 and spo.dateFinish  between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
     and spo.DTYPE='PolyclinicMedCase' 
     ${departmentSql} and (vis.DTYPE='Visit' or vis.DTYPE='ShortMedCase')
 ${additionWhereSql} ${typeCntVisitSql}
    group by  spo.id,spo.dateStart,spo.dateFinish,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday
    order by pat.lastname,pat.firstname,pat.middlename,spo.dateStart
    " />
    <msh:table name="datelist" 
    viewUrl="entityParentView-smo_spo.do?short=Short"
    action="entityParentView-smo_spo.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="4" />
      <msh:tableColumn columnName="Год рождения" property="5" />
      <msh:tableColumn columnName="Дата начала СПО" property="2" />
      <msh:tableColumn columnName="Дата окончания СПО" property="3" />
      <msh:tableColumn columnName="Кол-во дней" property="6"/>
      <msh:tableColumn columnName="Диагнозы" property="11"/>
      <msh:tableColumn columnName="Цель визита" property="12"/>
      <msh:tableColumn columnName="Результаты визитов" property="10"/>
      <msh:tableColumn columnName="Дата последнего посещения" property="7"/>
      <msh:tableColumn columnName="Кол-во дней после посл. посещения" property="8"/>
      <msh:tableColumn columnName="Дата посл. планир. посещения" property="9"/>
      
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }
    } else { %>
    	<i>Выберите параметры и нажмите найти </i>
    <% }   %>
    
    <script type='text/javascript'>
    checkFieldUpdate('typeView','${typeView}',3) ;
    checkFieldUpdate('typeCntVisit','${typeCntVisit}',3) ;

    function checkFieldUpdate(aField,aValue,aDefaultValue) {
       	eval('var chk =  document.forms[0].'+aField) ;
       	var max = chk.length ;
       	aValue=+aValue ;
       	if (aValue==0 || (aValue>max)) {aValue=+aDefaultValue}
       	if (aValue==0 || (aValue>max)) {
       		chk[max-1].checked='checked' ;
       	} else {
       		chk[aValue-1].checked='checked' ;
       	}
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='smo_journal_closeSpo.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    }
    </script>
  </tiles:put>
</tiles:insert>