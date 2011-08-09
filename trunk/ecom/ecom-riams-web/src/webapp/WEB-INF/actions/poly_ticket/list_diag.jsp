<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Medcard" guid="4b11dc98-30fc-413e-8bc6-976f292e704f">Список талонов</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  	<msh:section>
  	<table>
  		<tr>
  			<td valign="top">
  		<msh:sectionTitle>Мед.карта</msh:sectionTitle>
  		<msh:sectionContent>
  			<ecom:webQuery name="medcard" nativeSql="select m.id,m.number from medcard m left join psychiatriccarecard cc on cc.patient_id=m.person_id where cc.id=$piece('${param.id}',':',1)"/>
  			<msh:table name="medcard" action="entityParentView-poly_medcard.do" idField="1">
  				<msh:tableColumn property="2" columnName="№карты"/>
  			</msh:table>
  		</msh:sectionContent>
  		</td>
  		<td valign="top">
  		<msh:sectionTitle>Карта обратившегося за психиатрической помощью</msh:sectionTitle>
  		<msh:sectionContent>
  			<ecom:webQuery name="psychcard" nativeSql="select cc.id,cc.cardnumber from psychiatriccarecard cc where cc.id=$piece('${param.id}',':',1)"/>
  			<msh:table name="psychcard" action="entityParentView-psych_careCard.do" idField="1">
  				<msh:tableColumn property="2" columnName="№карты"/>
  			</msh:table>
  		</msh:sectionContent>
  		</td>
  		</tr>
  		</table>
  		<msh:sectionTitle>Список талонов по данному МКБ</msh:sectionTitle>
  		<msh:sectionContent>
		    <ecom:webQuery name="list" nativeSql=" select t.id as tid,m.number as mnumber, p.lastname||' '|| p.firstname||' '||p.middlename ||' г.р.'||p.birthday,t.dateCreate,t.date as tdate,vwf.name||' '||wp.lastname||' '|| wp.firstname||' '||wp.middlename as wfinfo,mkb.code as mkbcode ,vr.name as vrname  from Ticket t left join medcard m on m.id=t.medcard_id     left join patient p on p.id=m.person_id     left join workfunction wf on wf.id=t.workFunction_id    left join vocworkfunction vwf on vwf.id=wf.workFunction_id    left join worker  w on w.id=wf.worker_id    left join patient wp on wp.id=w.person_id    left join vocIdc10 mkb on mkb.id=t.idc10_id    left join vocreason vr on vr.id=t.vocreason_id   left join PsychiatricCareCard cc on m.person_id=cc.patient_id   where cc.id=$piece('${param.id}',':',1) and t.idc10_id=$piece('${param.id}',':',2) order by p.lastname,p.firstname,p.middlename" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
		    <msh:table name="list" action="entityParentEdit-poly_ticket.do" idField="1" noDataMessage="Не найдено" guid="6600cebc-4548-4f57-a048-5a3a2e67a673">
		      <msh:tableColumn columnName="#" property="sn" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
		      <msh:tableColumn columnName="№талона" property="1" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
		      <msh:tableColumn columnName="№мед.карты" property="2" guid="612d85fd-ca3a-46a4-9598-a611b83a01ab" />
		      <msh:tableColumn columnName="Пациент" property="3" guid="d7955208-4c68-42ce-85d6-684a4b9076a9" />
		      <msh:tableColumn columnName="Дата приема" property="5" guid="ee9ce01d-4924-4e76-bc93-3ecb73d8b18f" />
		      <msh:tableColumn columnName="Специалист" property="6" guid="9465992e-5fe3-42ee-b125-63929fda5158" />
		      <msh:tableColumn columnName="Диагноз" property="7" guid="9465992e-5fe3-42ee-b125-63929fda5158" />
		      <msh:tableColumn columnName="Цель посещения" property="8" guid="9465992e-5fe3-42ee-b125-63929fda5158" />
		    </msh:table>
  		</msh:sectionContent>
  	</msh:section>
  </tiles:put>
</tiles:insert>

