<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Medcard">Список талонов</msh:title>
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

    	<msh:sectionTitle>Диагнозы по листу уточненных диагнозов</msh:sectionTitle>
    	<msh:sectionContent>
		    <ecom:webQuery name="list" nativeSql="select 
		    max(d.id), min(d.establishDate),max(d.establishDate) 
		    , vi.code || ' ' || vi.name   as idc10
		    , vad.name                 as acuity
		    , vk.code || ' ' || vk.name       as ksg   
		    , case when (visit.DTYPE='Visit' or visit.DTYPE='PolyclinicMedCase') then 'Поликлиника' 
		    	when (visit.DTYPE='HospitalMedCase' or visit.DTYPE='DepartmentMedCase') then 'Стационар' 
		    	when (visit.DTYPE='ServiceMedCase') then 'Услуги'
		    	when (visit.DTYPE='ShortMedCase') then 'Талоны'  
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
		    group by d.idc10_id,d.priority_id,vi.code,vi.name,vad.name,vk.code,vk.name
			,visit.DTYPE,vpd.name 
			order by max(d.establishDate)" />
		    <msh:table name="list" action="entityView-stac_diagnosis.do" idField="1">
		      <msh:tableColumn columnName="№" property="sn" />
		      <msh:tableColumn columnName="ПО" property="7" />
		      <msh:tableColumn columnName="1 раз регистр." property="2" />
		      <msh:tableColumn columnName="Послед. раз регистр." property="3" />
		      <msh:tableColumn columnName="Код МКБ" property="4" />
		      <msh:tableColumn columnName="Приоритет" property="8" />
		      <msh:tableColumn columnName="Кол-во" property="9" />
		    </msh:table>
    	</msh:sectionContent>

  	</msh:section>
  </tiles:put>
</tiles:insert>

