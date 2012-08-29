<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Poly" title="Реестр" property="worker" />
    
  </tiles:put>
    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="reportNationality"/>
    </tiles:put>
  <tiles:put name="body" type="string">
  	
  	<%
  	String id = request.getParameter("id") ;
  	String[] params = id.split(":") ;
  	request.setAttribute("dateStart", params[1]) ;
  	request.setAttribute("dateEnd", params[2]) ;
  	request.setAttribute("nationality", params[0].equals("0")?" is null ":"='"+params[0]+"'") ;
  	%>  	
  	<msh:section title="Поликлиника">

  	
	    <ecom:webQuery name="list_yes" nativeSql="select v.id
	    
	    ,to_char(v.dateStart,'DD.MM.YYYY')||' '||cast(v.timeExecute as varchar(5)) as dateStart

	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,vwfe.name||' '||pe.lastname as pefio

from medcase v 
left join patient p on p.id=v.patient_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
where  v.dateStart between to_date('${dateStart}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and v.DTYPE='Visit' and v.dateStart is not null
and (v.noActuality is null or v.noActuality='0')
and p.nationality_id ${nationality}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_yes" action="entityView-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Пациент" property="3" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Дата" identificator="false" property="2" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="4" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
	    </msh:table>
  	</msh:section>
  	<msh:section title="Стационар">

  	
	    <ecom:webQuery name="list_stac" nativeSql="select v.id
	    
	    ,to_char(v.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(v.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,ss.code as sscode 

from medcase v 
left join patient p on p.id=v.patient_id
left join statisticstub ss on ss.id=v.statisticStub_id
where  v.dateStart between to_date('${dateStart}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and v.DTYPE='HospitalMedCase' and v.dateStart is not null
and (v.noActuality is null or v.noActuality='0')
and v.deniedHospitalizating_id is null
and p.nationality_id ${nationality}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-stac_ssl.do" 
 name="list_stac"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Номер стат.карты" property="5" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn columnName="Дата поступления" property="2" />
	      <msh:tableColumn columnName="Дата выписки" identificator="false" property="3" />
	    </msh:table>
  	</msh:section>
  	<msh:section title="Талоны">

  	
	    <ecom:webQuery name="list_ticket" nativeSql="select v.id
	    
	    ,to_char(v.date,'DD.MM.YYYY') as dateStart

	   
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,vwfe.name||' '||pe.lastname as pefio
	

from ticket v 
left join medcard m on m.id=v.medcard_id
left join patient p on p.id=m.person_id
left join WorkFunction wfe on wfe.id=v.workFunction_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
where  v.date between to_date('${dateStart}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
and v.status='2'
and p.nationality_id ${nationality}

order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-smo_visit.do" editUrl="entityParentEdit-smo_visit.do" name="list_yes" action="entityView-smo_visit.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
	      <msh:tableColumn columnName="Пациент" property="3" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
	      <msh:tableColumn columnName="Дата" identificator="false" property="2" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="4" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
	    </msh:table>
  	</msh:section>
  </tiles:put>
  
</tiles:insert>

