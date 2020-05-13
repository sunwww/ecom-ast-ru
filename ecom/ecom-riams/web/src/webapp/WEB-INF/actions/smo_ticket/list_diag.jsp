<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Medcard">Список талонов</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String dtype = request.getParameter("dtype") ;
  if (dtype==null || dtype.equals("")) {
	  request.setAttribute("dtypeSql", "and t.id is null") ;
	  request.setAttribute("asPatient", "diag" );
  } else {
	  request.setAttribute("dtypeSql", new StringBuilder().append(" and t.dtype='").append(dtype).append("'"));
	  request.setAttribute("asPatient", "t" );
  }
  %>
  	<msh:section>
  	<table>
  		<tr>
  			<td valign="top">
  		<msh:sectionTitle>Мед.карта</msh:sectionTitle>
  		<msh:sectionContent>
  			<ecom:webQuery name="medcard" nativeSql="select m.id,m.number from medcard m left join psychiatriccarecard cc on cc.patient_id=m.person_id where cc.id='${param.card}'"/>
  			<msh:table name="medcard" action="entityParentView-poly_medcard.do" idField="1">
  				<msh:tableColumn property="2" columnName="№карты"/>
  			</msh:table>
  		</msh:sectionContent>
  		</td>
  		<td valign="top">
  		<msh:sectionTitle>Карта обратившегося за психиатрической помощью</msh:sectionTitle>
  		<msh:sectionContent>
  			<ecom:webQuery name="psychcard" 
  			nativeSql="select cc.id,cc.cardnumber from psychiatriccarecard cc 
  			where cc.id='${param.card}'"/>
  			<msh:table name="psychcard" action="entityParentView-psych_careCard.do" idField="1">
  				<msh:tableColumn property="2" columnName="№карты"/>
  			</msh:table>
  		</msh:sectionContent>
  		</td>
  		</tr>
  		</table>
  		<msh:sectionTitle>Список талонов по данному МКБ</msh:sectionTitle>
  		<msh:sectionContent>
		    <ecom:webQuery name="list" 
		    nativeSql=" select t.id as tid
		    ,t.dateStart as tdate
		    ,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo
		    ,list(vpd.name||' '||mkb.code) as mkbcode 
		    ,vr.name as vrname  
		    from diagnosis diag 
		    left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
		    left join Medcase t on diag.medcase_id=t.id
		    left join workfunction wf on wf.id=t.workFunctionExecute_id    
		    left join vocworkfunction vwf on vwf.id=wf.workFunction_id    
		    left join worker  w on w.id=wf.worker_id    
		    left join patient wp on wp.id=w.person_id
		        
		    left join vocIdc10 mkb on mkb.id=diag.idc10_id    
		    left join vocreason vr on vr.id=t.visitReason_id   
		    left join PsychiatricCareCard cc on cc.patient_id=${asPatient}.patient_id 
		      
		    where cc.id='${param.card}' ${dtype} and diag.idc10_id='${param.mkb}'
		    and vpd.id='${param.priority}'
		    group by  t.id ,t.dateStart
		    ,vwf.name,wp.lastname, wp.firstname,wp.middlename
		    ,vr.name 
		    order by t.dateStart" />
		    <msh:table name="list" action="entityParentEdit-poly_ticket.do" idField="1" noDataMessage="Не найдено">
		      <msh:tableColumn columnName="#" property="sn" />
		      <msh:tableColumn columnName="№талона" property="1" />
		      <msh:tableColumn columnName="Дата приема" property="2" />
		      <msh:tableColumn columnName="Специалист" property="3" />
		      <msh:tableColumn columnName="Диагноз" property="4" />
		      <msh:tableColumn columnName="Цель посещения" property="5" />
		    </msh:table>
  		</msh:sectionContent>
  	</msh:section>
  </tiles:put>
</tiles:insert>

