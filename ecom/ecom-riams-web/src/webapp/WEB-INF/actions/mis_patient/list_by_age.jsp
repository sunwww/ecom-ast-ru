<%@page import="ru.nuzmsh.util.query.ReportParamUtil"%>
<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Разбивка пациентов по возрастам</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_everyday"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <%
    String typeReestr = request.getParameter("typeReestr") ;
    String typeHour =ActionUtil.updateParameter("mis_patient_by_age","typeHour","2", request) ;
    if (typeReestr==null) {
	  	String noViewForm = request.getParameter("noViewForm") ;
		String typeDate =ActionUtil.updateParameter("mis_patient_by_age","typeDate","1", request) ;
		String typeEmergency =ActionUtil.updateParameter("mis_patient_by_age","typeEmergency","3", request) ;
		
		String typeView =ActionUtil.updateParameter("mis_patient_by_age","typeView","2", request) ;
    
  	%>
    <msh:form action="/mis_patient_by_age.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
      <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBeginYear" label="Год" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
      </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
    </msh:panel>
    </msh:form>
       <script type='text/javascript'>
    
    //checkFieldUpdate('typeDate','${typeDate}',1) ;
    //checkFieldUpdate('typePatient','${typePatient}',4) ;
    //checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    //checkFieldUpdate('typeView','${typeView}',1) ;
    //checkFieldUpdate('typeHour','${typeHour}',3) ;
    //checkFieldUpdate('typeDepartment','${typeDepartment}',1) ;
    
  
   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }

    if ($('dateBegin').value=="") {
    	var dt = new Date() ;
    	dt.setDate(dt.getDate()-1);
    	$('dateBegin').value=format2day(dt.getDate())+"."+format2day(dt.getMonth()+1)+"."+dt.getFullYear() ;
    }

			 
    </script>
    <%
    }
    String date = request.getParameter("dateBeginYear") ;
    
    if (date!=null && !date.equals(""))  {
    	String view = (String)request.getAttribute("typeView") ;
    	
    	String pigeonHole="" ;
    	String pigeonHole1="" ;
    	StringBuilder paramHref = new StringBuilder();
    	paramHref.append("typeReestr=1");
    	paramHref.append("&typeView=").append(view);
    	
    	ActionUtil.setParameterFilterSql("serviceStream", "m.serviceStream_id", request);
    	ActionUtil.setParameterFilterSql("department", "ml.id", request);
    	ActionUtil.setParameterFilterSql("pigeonHole", "ml.pigeonHole_id", request);
    	String serviceStream=request.getParameter("serviceStream");
    	paramHref.append("&serviceStream=").append(serviceStream!=null?serviceStream:"");
    	paramHref.append("&dateBegin=").append(date);
    	request.setAttribute("paramHref", paramHref.toString()) ;
    	
    	
			
		    if (typeReestr!=null && typeReestr.equals("1")) {
    			String year=request.getParameter("year") ; String month=request.getParameter("month" );
    			StringBuilder where = new StringBuilder() ;
    			String dtype="Patient" ;
    			if (year!=null) {
    				where.append(" and to_char(pat.birthday,'yyyy')='").append(year).append("'") ;
    			}
    			if (month!=null) {
    				where.append(" and to_char(pat.birthday,'mm')='").append(month).append("'") ;
    			}
    			
    				request.setAttribute("whereSql", where.toString()) ;
    				//request.setAttribute("groupBy", groupBy) ;
    				if (dtype.equals("Patient")) {
    			%>
    			
    <ecom:webQuery name="reestr" nameFldSql="reestr_sql" nativeSql="
    select pat.id
    ,pat.lastname,pat.firstname,pat.middlename,to_char(pat.birthday,'dd.mm.yyyy') as birthday,coalesce(a.fullname)||' ' || case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end 
	 ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end 
	||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end as address
	,(select list(ved.name||' '||to_char(edc.startDate,'dd.mm.yyyy')||' '||to_char(edc.finishDate,'dd.mm.yyyy')) from ExtDispCard edc left join VocExtDisp ved on ved.id=edc.dispType_id where edc.patient_id=pat.id and to_char(edc.FinishDate,'yyyy')='${param.dateBeginYear}') as edclist
     from  Patient pat
     left join Address2 a on a.addressid=pat.address_addressid
	where pat.deathDate is null ${whereSql} 
	order by pat.lastname,pat.firstname,pat.middlename
    "/>
   <msh:section>
       <msh:sectionTitle>
    
    	    <form action="print-mis_patient_by_age.do" method="post" target="_blank">
	    Реестр пациентов
	    <input type='hidden' name="sqlText" id="sqlText" value="${reestr_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Список пациентов, рожденных ${param.month}.${param.year}  на ${param.dateBeginYear} год">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table action="entityView-mis_patient.do"
    viewUrl="entityShortView-mis_patient.do"
     name="reestr" idField="1">
    	<msh:tableColumn property="sn" columnName="#"/>
    	<msh:tableColumn property="2" columnName="Фамилия"/>
    	<msh:tableColumn property="3" columnName="Имя"/>
    	<msh:tableColumn property="4" columnName="Отчество"/>
    	<msh:tableColumn property="5" columnName="Дата рождения"/>
    	<msh:tableColumn property="6" columnName="Адрес"/>
    	<msh:tableColumn property="7" columnName="Доп. диспансеризации за ${param.dateBeginYear}"/>
    </msh:table>
    </msh:sectionContent>
   </msh:section> 			
    				    			<%    					
    				}
    				} else {
%>
<ecom:webQuery name="swod" nativeSql="
select '&dateBeginYear=${param.dateBeginYear}&year='||to_char(pat.birthday,'yyyy') as id
,${param.dateBeginYear}-cast(to_char(pat.birthday,'yyyy') as int) as year
,count(case when to_char(pat.birthday,'mm')='01' then pat.id else null end) cnt01
,count(case when to_char(pat.birthday,'mm')='02' then pat.id else null end) cnt02
,count(case when to_char(pat.birthday,'mm')='03' then pat.id else null end) cnt03
,count(case when to_char(pat.birthday,'mm')='04' then pat.id else null end) cnt04
,count(case when to_char(pat.birthday,'mm')='05' then pat.id else null end) cnt05
,count(case when to_char(pat.birthday,'mm')='06' then pat.id else null end) cnt06
,count(case when to_char(pat.birthday,'mm')='07' then pat.id else null end) cnt07
,count(case when to_char(pat.birthday,'mm')='08' then pat.id else null end) cnt08
,count(case when to_char(pat.birthday,'mm')='09' then pat.id else null end) cnt09
,count(case when to_char(pat.birthday,'mm')='10' then pat.id else null end) cnt10
,count(case when to_char(pat.birthday,'mm')='11' then pat.id else null end) cnt11
,count(case when to_char(pat.birthday,'mm')='12' then pat.id else null end) cnt12
,count(*) as cntAll
from patient pat
where deathdate is null
group by to_char(pat.birthday,'yyyy')
order by to_char(pat.birthday,'yyyy') desc
"/> 
<msh:table name="swod" action="mis_patient_by_age.do?typeReestr=1" idField="1" cellFunction="true">
	<msh:tableColumn property="2" columnName="Возраст" addParam=""/>
	<msh:tableColumn property="3" columnName="Январь" addParam="&month=01"/>
	<msh:tableColumn property="4" columnName="Февраль" addParam="&month=02"/>
	<msh:tableColumn property="5" columnName="Март" addParam="&month=03"/>
	<msh:tableColumn property="6" columnName="Апрель" addParam="&month=04"/>
	<msh:tableColumn property="7" columnName="Май" addParam="&month=05"/>
	<msh:tableColumn property="8" columnName="Июнь" addParam="&month=06"/>
	<msh:tableColumn property="9" columnName="Июль" addParam="&month=07"/>
	<msh:tableColumn property="10" columnName="Август" addParam="&month=08"/>
	<msh:tableColumn property="11" columnName="Сентябрь" addParam="&month=09"/>
	<msh:tableColumn property="12" columnName="Октябрь" addParam="&month=10"/>
	<msh:tableColumn property="13" columnName="Ноябрь" addParam="&month=11"/>
	<msh:tableColumn property="14" columnName="Декабрь" addParam="&month=12"/>
	<msh:tableColumn property="15" columnName="Общее кол-во" addParam=""/>
</msh:table>
<%
    				}
    	} else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
		<script type="text/javascript">
	    
	    
		</script>
  </tiles:put>
</tiles:insert>

