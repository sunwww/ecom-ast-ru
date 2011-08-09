<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Журнал обращений по открытым случаям госпитализации (СЛС) за ${param.id}" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_journal currentAction="stac_journalOpenByHospital" />
  </tiles:put>
  <tiles:put name="body" type="string">
<%--    <msh:table name="list" action="entityParentView-stac_ssl.do" idField="id" noDataMessage="Не найдено" guid="03092441-0d8d-421d-95ea-b110dd539b50">
      <msh:tableColumn columnName="Стат.карта" property="statCardNumber" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Наличие СЛО" property="isDepartmentMedCase" guid="bb789d76-55bb-46b1-9438-9b4e408779dd" />
      <msh:tableColumn columnName="Пациент" property="patientInfo" guid="4370bd26-12ec-4ad1-bffe-46159824c0f0" />
      <msh:tableColumn columnName="Кем открыт" property="username" guid="35347247-b552-4154-a82a-ee484a1714ad" />
      <msh:tableColumn columnName="Отделение" property="departmentInfo" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Экстренность" property="emergency" guid="b00c3a27-82cd-4d68-8a12-6f71bc8a7867" />
      <msh:tableColumn columnName="Отказ от госпитализации" property="deniedHospitalizatingInfo" guid="b00c3a27-82cd-4d68-8a12-6f71bc8a7867" />
    </msh:table>--%>
            	<ecom:webQuery name="list" 
            		nativeSql="select m.id as mid,ss.year as ssyear, ss.code as sscode
            			, $$GetBedDays^ZExpCheck('000'|| (case when vht.code='DAYTIMEHOSP' then 'J' else 'A' end) || '00',m.dateStart,isnull(m.dateFinish,CURRENT_DATE))
            			, case when (select count(id) from medcase d where d.parent_id=m.id)>0 then 'Да' else 'Нет' end
            			, p.lastname||' '||p.firstname ||' '|| p.middlename ||' гр.'||to_char(p.birthday,'DD.MM.YYYY')
            			, md.name as mdname,case when m.emergency=1 then 'Да' else 'Нет' end  as memergency
            			, m.dateStart as mdateStart
            			 from medcase m 
            			left join statisticstub ss on m.statisticstub_id=ss.id
            			left join VocHospType vht on vht.id=m.hospType_id
            			left join Patient p on p.id=m.patient_id
            			left join MisLpu md on md.id=m.department_id
            			left join VocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id
            			where m.DTYPE='HospitalMedCase' and m.dateStart=cast($$ini^Zcdat('${param.id}',7) as date) and (m.noActuality =0 or m.noActuality is null) and m.dateFinish is null and m.deniedHospitalizating_id is null and (m.ambulanceTreatment is null or m.ambulanceTreatment=0)"
            	/>
                <msh:table name="list" action="entityParentView-stac_ssl.do" idField="1" disableKeySupport="true">
				      <msh:tableColumn columnName="Год" property="2" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
				      <msh:tableColumn columnName="Стат.карта" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
				      <msh:tableColumn columnName="К/дни" property="4" guid="bb789d76-55bb-46b1-9438-9b4e408779dd" />
				      <msh:tableColumn columnName="Наличие СЛО" property="5" guid="bb789d76-55bb-46b1-9438-9b4e408779dd" />
				      <msh:tableColumn columnName="Пациент" property="6" guid="4370bd26-12ec-4ad1-bffe-46159824c0f0" />
				      <msh:tableColumn columnName="Отделение" property="7" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
				      <msh:tableColumn columnName="Дата поступления" property="9"/>
				      <msh:tableColumn columnName="Экстренность" property="8"/>
                </msh:table>

  </tiles:put>
</tiles:insert>

