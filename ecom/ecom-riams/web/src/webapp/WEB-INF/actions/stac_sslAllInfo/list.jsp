<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="style" type="string">
    <style type="text/css">tr.deniedHospitalizating td {
            color: red ;
        }</style>
  </tiles:put>
  <tiles:put name="title" type="string">
    <h1>Список всех случаев лечения в стационаре. <msh:sideLink params="id" action="/entityView-mis_patient" name="⇧ Пациент" /></h1>
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
  <%
  String sslid = request.getParameter("sslid") ;
  if (sslid!=null) {
	  
  } else {
	  request.setAttribute("patient", request.getParameter("id")) ;
  }
  %>
  <ecom:webQuery name="list" nativeSql="
  select sls.id as f0slsid
  ,case when sls.dtype='ExtHospitalMedCase' then sls.dtype else null end as f1isexthosp
  ,to_char(sls.dateStart,'dd.mm.yyyy') as f2dateStart
  ,to_char(sls.dateFinish,'dd.mm.yyyy') as f3dateFinish 
  ,vdh.id as f4vhdid,sls.username as f5slsusername
  ,case when sls.emergency='1' then 'да' else null end as f6emergency 
  ,coalesce(ss.code,'')||case when vdh.id is not null then ' '||vdh.name else '' end as f7stacard 
  ,ml.name as f8entdep,mlLast.name as f9mlLastdep 
  ,case when vdh.id is not null then null when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart) end as f10countDays 
  ,list(vpd.name||' '||mkb.code) as f11diagDisch 
  ,list(vpd1.name||' '||mkb1.code) as f12diagClinic
  ,case when sls.deniedHospitalizating_id is null then 'color: red ;' else '' end||
  case when sls.id='${param.sslid}' then 'background-color: #DDDDDD;border-bottom: 2px solid #666666;border-top: 2px solid #666666;' else '' end
   end as f13style 
  from MedCase sls 
  left join VocHospType vht on vht.id=sls.hospType_id 
  left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id 
  left join MedCase sloLast on sloLast.parent_id=sls.id and UPPER(sloLast.dtype)='DEPARTMENTMEDCASE' 
  left join StatisticStub ss on ss.id=sls.statisticStub_id 
  left join MisLpu mlLast on mlLast.id=sloLast.department_id 
  left join MisLpu ml on ml.id=sls.department_id	
  left join Diagnosis diag on sls.id=diag.medCase_id	
  left join VocIdc10 mkb on mkb.id=diag.idc10_id	
  left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id and vdrt.code='3'	
  left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id	
  left join Diagnosis diag1 on sloLast.id=diag1.medCase_id	
  left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id	
  left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'	
  left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id where sls.patient_id=${param.id} 
  and UPPER(sls.DTYPE) IN ('HOSPITALMEDCASE','EXTHOSPITALMEDCASE') 
  and  (sloLast.id is null or sloLast.transferDate is null) 
   group by sls.id,sls.dtype,sls.dateStart,sls.dateFinish,vdh.id ,sls.username ,sls.emergency, ss.code,vdh.id,vdh.name ,ml.name,mlLast.name,vht.code 
   order by sls.dateStart

  "
  />
    <msh:table viewUrl="entityShortView-stac_ssl.do" name="list" action="entityView-stac_ssl.do" styleRow="13">
      <msh:tableColumn columnName="Номер" property="id" />
      <msh:tableColumn columnName="Дата поступления" property="dateStart" />
      <msh:tableColumn columnName="Экстренность" property="emergency" />
      <msh:tableColumn columnName="Кем открыт" property="username" />
      <msh:tableColumn columnName="Дата выбытия" property="dateFinish" />
      <msh:tableColumn columnName="Кем закрыт" property="finishWorkerText" />
      <msh:tableColumn columnName="Стат.карта" property="statCardNumber" />
      <msh:tableColumn columnName="Количество дней" property="daysCount" />
    </msh:table>
    <msh:tableNotEmpty name="list">
      <div class="h3">
        <h3>Легенда</h3>
      </div>
      <table class="tabview">
        <tr class="deniedHospitalizating">
          <td>―</td>
          <td>Отказ в госпитализации</td>
        </tr>
        <tr class="current">
          <td />
          <td>Текущая госпитализация</td>
        </tr>
      </table>
    </msh:tableNotEmpty>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_sslAdmission" name="Новый случай лечения в стационаре" title="Добавить случай стационарного лечения" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink key="ALT+2" params="id" action="/entityView-mis_patient" name="⇧ Пациент" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

