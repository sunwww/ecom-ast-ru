<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
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
    <msh:form action="/smo_journal_closeSpo.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
 
     <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
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
     <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
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
        	<msh:autoComplete property="department" fieldColSpan="5"
        	label="ЛПУ" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" fieldColSpan="5"
        	label="Специалист" horizontalFill="true" vocName="workFunctionByDirect"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="5"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
	        <msh:textField property="finishDate" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
			<td>
	            <input type="submit" onclick="find()" value="Найти" />
	          </td>
        </msh:row>
    </msh:panel>
    </msh:form>
    <%} %>
    <%
    String date = (String)request.getParameter("beginDate") ;
    if (date!=null && !date.equals(""))  {
    	String finishDate = (String)request.getParameter("finishDate") ;
    	if (finishDate==null||finishDate.equals("")) {
    		request.setAttribute("finishDate", date) ;
    		finishDate = date ;
    	} else {
    		request.setAttribute("finishDate", finishDate) ;
    	}
    	request.setAttribute("beginDate", date) ;
    	
    	if (typeView!=null && typeView.equals("1")) {
    		request.setAttribute("additionJoinSql", " left join medcase spo1 on spo1.patient_id=spo.patient_id and spo1.dtype='PolyclinicMedCase'     				left join WorkFunction owf1 on owf1.id=spo1.ownerFunction_id     				left join Worker ow1 on ow1.id=owf1.worker_id ") ;
    		request.setAttribute("additionWhereSql", " and spo1.dateFinish between to_date('"+date+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy') and owf.workFunction_id=owf1.workFunction_id  and ow.lpu_id=ow1.lpu_id and spo1.id!=spo.id ") ;
    	} else if (typeView!=null && typeView.equals("2")) {
    		request.setAttribute("additionJoinSql", " left join medcase spo1 on spo1.patient_id=spo.patient_id and spo1.dtype='PolyclinicMedCase'     				left join WorkFunction owf1 on owf1.id=spo1.ownerFunction_id     				left join Worker ow1 on ow1.id=owf1.worker_id ") ;
    		request.setAttribute("additionWhereSql", " and spo1.dateFinish is null and owf.workFunction_id=owf1.workFunction_id  and ow.lpu_id=ow1.lpu_id and spo1.id!=spo.id ") ;
    		request.setAttribute("additionSpoSql", " and (select count(spo1.id) from medcase spo1 left join WorkFunction owf1 on owf1.id=spo1.ownerFunction_id left join Worker ow1 on ow1.id=owf1.worker_id where spo.patient_id=spo1.patient_id and spo1.dtype='PolyclinicMedCase' and owf.workFunction_id=owf1.workFunction_id and spo1.dateStart between to_date('"+date+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy') and ow.lpu_id=ow1.lpu_id and spo1.dateFinish is null)>1") ;
    	} else {
    		
    	}
    	String dep = (String)request.getParameter("department") ;
    	if (dep!=null && !dep.trim().equals("") && !dep.equals("0")) {
    		request.setAttribute("departmentSql", " and w.lpu_id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
    	}
    	String spec = (String)request.getParameter("spec") ;
    	if (spec!=null && !spec.equals("") && !spec.equals("0")) {
    		request.setAttribute("workFunctionSql", " and vis.workFunctionExecute_id="+spec) ;
    		request.setAttribute("workFunction",spec) ;
    	} else {
    		request.setAttribute("workFunction","0") ;
    	}
    	String servStream = (String)request.getParameter("serviceStream") ;
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("serviceStreamSql", " and vss.id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    	}
    	%>
    	
    	
    	<%
        	String login = LoginInfo.find(request.getSession(true)).getUsername() ;
        	request.setAttribute("login", login) ;
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
	    	WebQueryResult wqr = list.size()>0?(WebQueryResult)list.get(0):null ;
	    	//WebQueryResult wqr = null ;
        	String department = request.getParameter("department") ;
        	String curator = request.getParameter("specialist") ;
        	String workFunc = wqr!=null?""+wqr.get1():"0" ;
        	boolean isBossDepartment=(wqr!=null&&wqr.get3()!=null)?true:false ;
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
        	} else if (department!=null && !department.equals("0")&& !department.equals("") && (isViewAllDepartment || isBossDepartment)) {
        		type=2 ;
       		} else if (isViewAllDepartment) {
       			type=1 ;
       		} else if (wqr!=null) {
       			if (isBossDepartment) {
       				type=2 ;
       				if ((wqr.get2()!=null&&!wqr.get2().equals("0")&&!wqr.get2().equals(""))) {
       					department=""+wqr.get2() ;
       				} else {
       					department="0" ;
       				}
       				
       			} else {
       				type=3 ;
       				curator=workFunc ;
       				department=""+wqr.get2() ;
       			}
       		}
       		request.setAttribute("department", department) ;
       		request.setAttribute("curator", curator) ;
       	%>
        	    
    <%if (type==1) { %>
    <msh:section>
    <msh:sectionTitle>Свод по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select ml.id||'&department='||ml.id,ml.name ,count(distinct spo.patient_id) as cntPat,count(distinct spo.id) as cntSpo  
	from MedCase spo
	left join WorkFunction owf on owf.id=spo.ownerFunction_id
	left join Worker ow on ow.id=owf.worker_id
	left join MisLpu ml on ow.lpu_id=ml.id 
	${additionJoinSql}
	where spo.dtype='PolyclinicMedCase' 
	and spo.dateFinish between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
	${additionWhereSql} ${serviceStreamSql}
	${typeCntVisitSql}
	group by ml.id,ml.name order by ml.name" 
	/>
    <msh:table name="datelist" 
    viewUrl="smo_journal_closeSpo.do?short=Short&typeView=${typeView}&serviceStream=${param.serviceStream}&beginDate=${beginDate}&finishDate=${finishDate}"
    action="smo_journal_closeSpo.do?typeView=${typeView}&serviceStream=${param.serviceStream}&beginDate=${beginDate}&finishDate=${finishDate}" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во пацентов" property="3" isCalcAmount="true" />
      <msh:tableColumn columnName="Кол-во СПО" property="4" isCalcAmount="true" />
    </msh:table>
    </msh:sectionContent>
    </msh:section> 
    <% 
    } 
    if (type==2 )  {	
    %>
    <msh:section>
    <msh:sectionTitle>Реестр по лечащим врачам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select 
owf.id||'&department=${department}&specialist='||owf.id as id
,ovwf.name as ovwfname,owp.lastname||' '||owp.firstname||' '||owp.middlename as lechVr
,count(distinct spo.patient_id) as cntPatient,count(distinct spo.id) as cntSpo 
from MedCase spo
left join Patient pat on spo.patient_id=pat.id 
left join WorkFunction owf on spo.ownerFunction_id=owf.id 
left join VocWorkFunction ovwf on owf.workFunction_id=ovwf.id 
left join Worker ow on owf.worker_id=ow.id 
left join Patient owp on ow.person_id=owp.id 
${additionJoinSql}
where spo.dtype='PolyclinicMedCase' 
and spo.dateFinish  between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
 and ow.lpu_id='${department}'
 ${additionWhereSql}  ${typeCntVisitSql}
group by owf.id,ovwf.name,owp.lastname,owp.middlename,owp.firstname 
order by owp.lastname,owp.middlename,owp.firstname
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="smo_journal_closeSpo.do?short=Short&typeView=${typeView}&serviceStream=${param.serviceStream}&beginDate=${beginDate}&finishDate=${finishDate}"
    action="smo_journal_closeSpo.do?typeView=${typeView}&serviceStream=${param.serviceStream}&beginDate=${beginDate}&finishDate=${finishDate}" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Должность" property="2" />
      <msh:tableColumn columnName="Лечащий врач" property="3" />
      <msh:tableColumn columnName="Кол-во пациентов" property="4" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во СПО" property="5" isCalcAmount="true"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
         <%}%>
         <%if (type==3 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
select spo.id,spo.dateStart,spo.dateFinish
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday  
    ,coalesce(spo.dateFinish,CURRENT_DATE)-spo.dateStart+1 as cnt1
    ,max(vis.dateStart) as maxvisDateStart
    ,case when max(vis.dateStart) is not null then current_date-max(vis.dateStart) else null end as cntdaymax
    ,to_char(max(case when vis.dateStart is null then wcd.calendarDate else null end),'dd.mm.yyyy') as maxPlanDate
    ,list(distinct vvr.name) as vvrname,list(distinct mkb.code||' '||vpd.name) as diag
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
	${additionJoinSql}
    where spo.ownerFunction_id='${curator}' 
 and spo.dateFinish  between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
     and spo.DTYPE='PolyclinicMedCase' 
     and   ow.lpu_id='${department}' and (vis.DTYPE='Visit' or vis.DTYPE='ShortMedCase')
 ${additionWhereSql} ${typeCntVisitSql}
    group by  spo.id,spo.dateStart,spo.dateFinish,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday
    order by pat.lastname,pat.firstname,pat.middlename,spo.dateStart
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" 
    viewUrl="entityParentView-smo_spo.do?short=Short"
    action="entityParentView-smo_spo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата начала СПО" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Дата окончания СПО" property="3" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Кол-во дней" property="6"/>
      <msh:tableColumn columnName="Диагнозы" property="11"/>
      <msh:tableColumn columnName="Результаты визитов" property="10"/>
      <msh:tableColumn columnName="Дата последнего посещения" property="7"/>
      <msh:tableColumn columnName="Кол-во дней после посл. посещения" property="8"/>
      <msh:tableColumn columnName="Дата посл. планир. посещения" property="9"/>
      
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } %>
    	
    	
    	
    	
    <% } else { %>
    	<i>Выберите параметры и нажмите найти </i>
    <% }   %>
    
    <script type='text/javascript'>
    //var typePatient = document.forms[0].typePatient ;
    //var typeDate = document.forms[0].typeDate ;
    //checkFieldUpdate('typeSwod','${typeSwod}',2,1) ;
    //checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeView','${typeView}',3) ;
    checkFieldUpdate('typeCntVisit','${typeCntVisit}',3) ;
    //checkFieldUpdate('typePatient','${typePatient}',3,3) ;
    //checkFieldUpdate('typeStatus','${typeStatus}',2,2) ;
    
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
    	//frm.action='stac_groupByBedFundList.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>