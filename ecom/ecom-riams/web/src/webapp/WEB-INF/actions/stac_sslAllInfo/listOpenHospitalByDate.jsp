<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.s}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Журнал обращений по открытым случаям госпитализации (СЛС) за ${param.id}" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_journal currentAction="stac_journalOpenByHospital" />
  </tiles:put>
  <tiles:put name="body" type="string">
<%--    <msh:table name="list" action="entityParentView-stac_ssl.do" idField="id" noDataMessage="Не найдено">
      <msh:tableColumn columnName="Стат.карта" property="statCardNumber" />
      <msh:tableColumn columnName="Наличие СЛО" property="isDepartmentMedCase" />
      <msh:tableColumn columnName="Пациент" property="patientInfo" />
      <msh:tableColumn columnName="Кем открыт" property="username" />
      <msh:tableColumn columnName="Отделение" property="departmentInfo" />
      <msh:tableColumn columnName="Экстренность" property="emergency" />
      <msh:tableColumn columnName="Отказ от госпитализации" property="deniedHospitalizatingInfo" />
    </msh:table>--%>
            	<ecom:webQuery name="list" 
            		nativeSql="select m.id as mid,ss.year as ssyear, ss.code as sscode
            			, case 
            				when (coalesce(m.dateFinish,CURRENT_DATE)-m.dateStart)=0 then 1 
            				when vht.code='DAYTIMEHOSP' then ((coalesce(m.dateFinish,CURRENT_DATE)-m.dateStart)+1) 
            				else (coalesce(m.dateFinish,CURRENT_DATE)-m.dateStart)
            				end as cntDays
            			, case when (select count(d.id) from medcase d where d.parent_id=m.id and d.dtype='DepartmentMedCase')>0 then 'Да' else 'Нет' end as sloIs
            			, p.lastname||' '||p.firstname ||' '|| p.middlename ||' гр.'||to_char(p.birthday,'DD.MM.YYYY') as patInfo
            			, md.name as mdname,case when cast(m.emergency as integer)=1 then 'Да' else 'Нет' end  as memergency
            			, m.dateStart as mdateStart
            			 from medcase m 
            			left join statisticstub ss on m.statisticstub_id=ss.id
            			left join VocHospType vht on vht.id=m.hospType_id
            			left join Patient p on p.id=m.patient_id
            			left join MisLpu md on md.id=m.department_id
            			left join VocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id
            			where m.DTYPE='HospitalMedCase' and m.dateStart=to_date('${param.id}','DD.MM.YYYY') 
            			and (cast(m.noActuality as integer) =0 or m.noActuality is null) 
            			and m.dateFinish is null 
            			and m.deniedHospitalizating_id is null 
            			and (m.ambulanceTreatment is null or cast(m.ambulanceTreatment as integer)=0)"
            	/>
                <msh:table viewUrl="entityShortView-stac_ssl.do" name="list" action="entityParentView-stac_ssl.do" idField="1" disableKeySupport="true">
				      <msh:tableColumn columnName="Год" property="2" />
				      <msh:tableColumn columnName="Стат.карта" property="3" />
				      <msh:tableColumn columnName="К/дни" property="4" />
				      <msh:tableColumn columnName="Наличие СЛО" property="5" />
				      <msh:tableColumn columnName="Пациент" property="6" />
				      <msh:tableColumn columnName="Отделение" property="7" />
				      <msh:tableColumn columnName="Дата поступления" property="9"/>
				      <msh:tableColumn columnName="Экстренность" property="8"/>
                </msh:table>

  </tiles:put>
</tiles:insert>

