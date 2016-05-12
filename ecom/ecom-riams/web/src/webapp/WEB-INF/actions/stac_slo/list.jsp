<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Список всех CЛО" guid="c951c449-0ed2-489d-9163-da1263effaa3" />
  </tiles:put>
  <tiles:put name="side" type="string" >
	<msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" />
	</msh:sideMenu>  
  </tiles:put>
  <tiles:put name="body" type="string">
          <ecom:webQuery name="list" nativeSql="select MedCase.id as mid, MedCase.dateStart as mdateStart       
           , MedCase.department_id||' '||postDep.Name     as department    
           , MedCase.transferDate as transferDate 
           , MedCase.transferDepartment_id||' '||tranDep.Name as departmentTransfer
           , MedCase.dateFinish as mdateFinish
           , Patient.lastname || ' ' ||  Patient.firstname || ' ' || Patient.middlename as startWorker
           , op.lastname || ' ' ||  op.firstname || ' ' || op.middlename as ownerWorker
           ,
           case when (coalesce(MedCase.dateFinish,MedCase.transferDate,CURRENT_DATE)-MedCase.dateStart)=0 then 1
               when cast(BedFund.addCaseDuration as integer)=1 then (coalesce(MedCase.dateFinish,MedCase.transferDate,CURRENT_DATE)-MedCase.dateStart)+1
               else (coalesce(MedCase.dateFinish,MedCase.transferDate,CURRENT_DATE)-MedCase.dateStart)
           end
           ||
           case 
            when MedCase.dateFinish is not null then ' (выписан)'
          	when MedCase.transferDate is not null then ' (перевод)'
          	else ' (на текущий момент)'
           end as countBed
          , MedCase.entranceTime as entranceTime,MedCase.transferTime as transferTime
          ,MedCase.dischargeTime  as dischargeTime
           ,VocServiceStream.name as vssname
           from MedCase
           left outer join Worker     on MedCase.startWorker_id = Worker.id  
           left outer join Patient    on Worker.person_id       = Patient.id  
           left outer join Worker ow  on MedCase.owner_id       = ow.id  
           left outer join Patient op on ow.person_id           = op.id  
           left outer join MisLpu tranDep on MedCase.transferDepartment_id           = tranDep.id 
           left outer join MisLpu postDep on MedCase.department_id           = postDep.id
           left join BedFund on BedFund.id=MedCase.bedFund_id 
           left join VocBedSubType on VocBedSubType.id = BedFund.bedSubType_id 
           left join VocServiceStream on VocServiceStream.id=MedCase.serviceStream_id
  
           where MedCase.parent_id=${param.id} 
           and MedCase.DTYPE='DepartmentMedCase'
           order by MedCase.dateStart,MedCase.entranceTime
           " />
    <msh:table name="list" action="entityParentView-stac_slo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th colspan="1" />
                <th colspan="4" class="rightBold">Поступление</th>
                <th colspan="3" class="rightBold">Перевод</th>
                <th colspan="2" class="rightBold">Выписка</th>
                <th colspan="1" />
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
            <msh:tableColumn columnName="Дата" property="2" guid="024d14d9-7e0b-484a-ad93-7db35046b351" />
            <msh:tableColumn columnName="Время" property="10" />
            <msh:tableColumn columnName="Отделение" property="3" cssClass="rightBold" guid="e52974a2-76d9-404c-bd22-78f28d3456ed" />
            <msh:tableColumn columnName="Поток" property="13" cssClass="rightBold" />
            <msh:tableColumn columnName="Дата" property="4" guid="26ab551e-5512-4c99-91bd-ea0e083e98cb" />
            <msh:tableColumn columnName="Время" property="11" />
            <msh:tableColumn columnName="Отделение" property="5" cssClass="rightBold" guid="cedd2007-2476-48d1-b24c-92a4606d2eca" />
            <msh:tableColumn columnName="Дата" property="6" guid="5b3627ee-362b-4923-a1d9-f0213cd89480" />
            <msh:tableColumn columnName="Время" property="12" />
            <msh:tableColumn columnName="Койко-дни" property="9" guid="8110d88e-6422-47c8-b13d-9e7720250627" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b33faf64-b72e-4845-bf32-5fda8e274fc3">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_slo" name="Новый СЛО" title="Добавить случай стационарного лечения в отделении" 
      roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Create" key="ALT+2"
      guid="dc488234-9da8-4290-9e71-3b4558d27ec7" />
    </msh:sideMenu>
    
  </tiles:put>
</tiles:insert>

