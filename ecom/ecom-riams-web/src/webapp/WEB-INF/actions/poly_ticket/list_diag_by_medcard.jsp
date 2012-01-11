<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

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
  			<ecom:webQuery name="medcard" nativeSql="select m.id,m.number 
  			from medcard m 
  			
  			where m.id='${param.id}'"/>
  			<msh:table viewUrl="entityShortView-poly_medcard.do" name="medcard" action="entityParentView-poly_medcard.do" idField="1">
  				<msh:tableColumn property="2" columnName="№карты"/>
  			</msh:table>
  		</msh:sectionContent>
  		</td>
  		<td valign="top">
  		<msh:sectionTitle>Карта обратившегося за психиатрической помощью</msh:sectionTitle>
  		<msh:sectionContent>
  			<ecom:webQuery name="psychcard" nativeSql="select cc.id,cc.cardnumber 
  			from psychiatriccarecard cc 
  			left join medcard m on cc.patient_id=m.person_id 
  			where m.id=${param.id}"/>
  			<msh:table name="psychcard" viewUrl="entityShortView-psych_careCard.do" action="entityParentView-psych_careCard.do" idField="1">
  				<msh:tableColumn property="2" columnName="№карты"/>
  			</msh:table>
  		</msh:sectionContent>
  		</td>
  		</tr>
  		</table>
    	<msh:sectionTitle>Диагнозы по талонам</msh:sectionTitle>
    	<msh:sectionContent>
	    	<ecom:webQuery name="diag" nativeSql="select m.id||':'||t.idc10_id,mkb.code,mkb.name,count(*),max(t.date) 
	    		from Ticket t left join vocidc10 mkb on mkb.id=t.idc10_id 
	    		left join medcard m on m.id=t.medcard_id 
	    		where m.id=${param.id} and t.status=2 and (t.talk is null or t.talk=0) group by idc10_id"/>
	    	<msh:table name="diag" action="js-poly_ticket-listDiag.do" idField="1">
	    		
	    		<msh:tableColumn property="2" columnName="Код МКБ"/>
	    		<msh:tableColumn property="3" columnName="Наим.МКБ"/>
	    		<msh:tableColumn property="4" columnName="Кол-во"/>
	    		<msh:tableColumn property="5" columnName="Дата послед.посещения"/>
	    	</msh:table>
    	</msh:sectionContent>
    	<msh:sectionTitle>Диагнозы по листу уточненных диагнозов</msh:sectionTitle>
    	<msh:sectionContent>
		    <ecom:webQuery name="list" nativeSql="select 
		    d.id, min(d.establishDate),max(d.establishDate) , d.name 
		    , vi.code || ' ' || vi.name   as idc10
		    , vad.name                 as acuity
		    , vk.code || ' ' || vk.name       as ksg   
		    , case when (visit.DTYPE='VISIT' or visit.DTYPE='POLYCLINICMEDCASE') then 'Поликлиника' 
		    	when (visit.DTYPE='HOSPITALMEDCASE' or visit.DTYPE='DEPARTMENTMEDCASE') then 'Стационар' 
		    	when (visit.DTYPE='SERVICEMEDCASE') then 'Услуги' 
		    	else 'неизвестно'
		    	end   
		    , vpd.name as priority, count(*)
		    from diagnosis d 
		    left join Medcard m on m.person_id=d.patient_id 
		    left join MedCase visit            on d.medcase_id  = visit.id 
		    left join MedCase spo              on visit.parent_id       = spo.id
		    left   join VocIdc10 vi          on d.idc10_id    = vi.id 
		    left   join VocAcuityDiagnosis vad on d.acuity_id   = vad.id
		    left   join VocKsg vk on d.clinicStatisticGroup_id = vk.id
		    left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
		    where  m.id=${param.id} 
		    group by d.idc10_id,d.priority_id order by d.establishDate" guid="2d59a9bf-327f-4f4f-8336-531458b6caed" />
		    <msh:table name="list" action="entityView-stac_diagnosis.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
		      <msh:tableColumn columnName="№" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4-1" />
		      <msh:tableColumn columnName="ПО" property="8" />
		      <msh:tableColumn columnName="1 раз регистр." property="2" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Послед. раз регистр." property="3" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
		      <msh:tableColumn columnName="Наименование" property="4" guid="6682eeef-105f-43a0-be61-30a865f27972" />
		      <msh:tableColumn columnName="Код МКБ" property="5" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
		      <msh:tableColumn columnName="Приоритет" property="9" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
		      <msh:tableColumn columnName="Кол-во" property="10" guid="7f7d011d-624c-4003-9c7d-4db6e3dda647" />
		    </msh:table>
    	</msh:sectionContent>

  	</msh:section>
  </tiles:put>
</tiles:insert>

