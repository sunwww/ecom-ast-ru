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
    <h1>Список всех случаев лечения в стационаре. <msh:sideLink params="id" action="/entityView-mis_patient" name="⇧ Пациент" guid="f07e71b2-bfbe-4137-8bba-b347b8056561" /></h1>
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
    <msh:table viewUrl="entityShortView-stac_ssl.do" name="list" action="entityView-stac_ssl.do" guid="d579127c-69a0-4eca-b3e3-950381d1585c" styleRow="13">
      <msh:tableColumn columnName="Номер" property="id" guid="ce16c32c-9459-4673-9ce8-d6e646f969ff" />
      <msh:tableColumn columnName="Дата поступления" property="dateStart" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Экстренность" property="emergency" guid="e98f6bbc96" />
      <msh:tableColumn columnName="Кем открыт" property="username" guid="35347247-b552-4154-a82a-ee484a1714ad" />
      <msh:tableColumn columnName="Дата выбытия" property="dateFinish" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Кем закрыт" property="finishWorkerText" guid="6b562107-5017-4559-9b94-ab525b579202" />
      <msh:tableColumn columnName="Стат.карта" property="statCardNumber" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Количество дней" property="daysCount" guid="8b496fc7-80e9-4beb-878b-5bfb20e98f31" />
    </msh:table>
    <msh:tableNotEmpty name="list" guid="189caa95-f200-4b88-ae0f-5669effa19ce">
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
    <msh:sideMenu title="Добавить" guid="6372e109-9e1b-49dc-840b-9b38f524efeb">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_sslAdmission" name="Новый случай лечения в стационаре" title="Добавить случай стационарного лечения" guid="436bbb7b-497c-4b10-ba0e-c5601675a713" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" guid="b43f7427-60be-4539-8b79-38a6882a8512">
      <msh:sideLink key="ALT+2" params="id" action="/entityView-mis_patient" name="⇧ Пациент" guid="f07e71b2-bfbe-4137-8bba-b347b8056561" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

