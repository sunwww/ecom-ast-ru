<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
  <tiles:put name="title" type="string">
    <msh:title mainMenu="LaboratoryJournal" title="Журнал аннулированных лабораторных исследований" />
  </tiles:put>

  <tiles:put name="side" type="string">
	  <tags:laboratory_menu currentAction="pres_annul_journal"/>
  </tiles:put>

  <tiles:put name="body" type="string">
  	  <msh:form action="/pres_annul_journal.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <msh:textField property="beginDate" label="Дата с" />
        <msh:textField property="endDate" label="Дата по" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
        if ($('beginDate').value=="") {
            $('beginDate').value=getCurrentDate() ;
        }
    </script>
    <%
    String beginDate = request.getParameter("beginDate") ;

  		if (beginDate==null || beginDate.equals("")) {
  			beginDate=DateFormat.formatToDate(new Date()) ;
  		}
  		String endDate = request.getParameter("endDate") ;
  	  	if (endDate==null|| endDate.equals("")) {endDate=beginDate;}
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("endDate", endDate) ;
    %>
    <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    select j.prescription, j.createdate , j.createtime , pat.patientinfo, j.createusername, j.annulreason
    ,ms.code||' '||ms.name as f7_service
	from adminchangejournal j
	left join prescription p on p.id=j.prescription
	left join medservice ms on ms.id=p.medservice_id
	left join medcase slo on slo.id=p.medcase_id
	left join medcase sls on sls.id=slo.parent_id
	left join patient pat on pat.id=coalesce (slo.patient_id ,sls.patient_id )
	where j.ctype ='UN_PRESCRIPT'
    and j.createDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${endDate}','dd.mm.yyyy')
    order by j.createdate desc , j.createtime desc, pat.patientinfo
    
    "/>
    <msh:sectionContent>
		<msh:table name="list" action="entityView-pres_servicePrescription.do" idField="1" >
			<msh:tableColumn columnName="#" property="sn"  />
	      	<msh:tableColumn columnName="Дата аннулирования" property="2"/>
	      	<msh:tableColumn columnName="Время аннулирования" property="3"  />
	      	<msh:tableColumn columnName="ФИО пациента" property="4"  />
	      	<msh:tableColumn columnName="Анализ" property="7"  />
			<msh:tableColumn columnName="Кто аннулировал" property="5"/>
			<msh:tableColumn columnName="Причина" property="6"/>
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>