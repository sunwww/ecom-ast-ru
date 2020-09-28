<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Учёт случаев госпитализации беременных"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	if (request.getParameter("short")==null||request.getParameter("short").equals("")) {	
  %>
    <msh:form action="/stac_pregnant_hosp_account.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
	    <input type="hidden" name="id" id="id" value=""/>
	    <msh:panel>
	      <msh:row>
	        <msh:separator label="Параметры поиска" colSpan="7" />
	      </msh:row>
	
	      <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
			<msh:textField property="dateEnd" label="по" />
	      </msh:row>
	      <msh:row>
	           <td>
	            <input type="submit" value="Найти" />
	          </td>
	      </msh:row>
	    </msh:panel>
	    </msh:form>
	    
	    <% }%>
	    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
	           <script type='text/javascript'>
	       
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
    	$('dateBegin').value=getCurrentDate() ;
    }

    </script>
    <%
    
    String date = request.getParameter("dateBegin") ;
    if (date!=null) {
    String dateEnd = request.getParameter("dateEnd") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    //String id = (String)request.getParameter("id") ;

    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;

    String sqlAdd = "";

    request.setAttribute("sqlAdd", sqlAdd);
     %>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <ecom:webQuery isReportBase="${isReportBase}" name="PregHospAccount" nameFldSql="PregHospAccount_sql" nativeSql="
select vih.name, count(case when lpu.lpulevel='1' then wchb.id else null end) lvl1,
count(case when lpu.lpulevel='2' then wchb.id else null end) lvl2,
count(case when lpu.lpulevel='3' then wchb.id else null end) lvl3
from vocindicationhospitalization vih 
left join workcalendarhospitalbed wchb on vih.id=coalesce(wchb.indicationtohosp_id,wchb.indicationtohosp)
left join mislpu dep on dep.id=wchb.department_id
left join mislpu lpu on lpu.id=dep.parent_id
where wchb.datefrom is null or wchb.datefrom between 
to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
group by vih.id, vih.name order by vih.id ${sqlAdd}
" />
    <msh:sectionTitle>
    	    <!-- <form action="print-stac_report_32.do" method="post" target="_blank"> 
	    Учёт случаев госпитализации беременных
	     </form> -->     
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="PregHospAccount" 
     action="stac_pregnant_hosp_account.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}"
     idField="16" cellFunction="true"  
     >
      <msh:tableColumn columnName="Показание для госпитализации" property="1" />
      <msh:tableColumn columnName="В ЛПУ I группы" property="2" addParam=""  />
      <msh:tableColumn columnName="В ЛПУ II группы" property="3" addParam=""  />
      <msh:tableColumn columnName="В ЛПУ III группы" property="4" addParam=""  />
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <% 
    }
    	%>
   
  </tiles:put>

</tiles:insert>