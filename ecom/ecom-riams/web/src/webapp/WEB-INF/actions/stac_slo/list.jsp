<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Список всех CЛО" />
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
    <msh:table name="list" action="entityParentView-stac_slo.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="4" class="rightBold">Поступление</th>
                <th colspan="3" class="rightBold">Перевод</th>
                <th colspan="2" class="rightBold">Выписка</th>
                <th colspan="1" />
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="#" property="sn" />
            <msh:tableColumn columnName="Дата" property="2" />
            <msh:tableColumn columnName="Время" property="10" />
            <msh:tableColumn columnName="Отделение" property="3" cssClass="rightBold" />
            <msh:tableColumn columnName="Поток" property="13" cssClass="rightBold" />
            <msh:tableColumn columnName="Дата" property="4" />
            <msh:tableColumn columnName="Время" property="11" />
            <msh:tableColumn columnName="Отделение" property="5" cssClass="rightBold" />
            <msh:tableColumn columnName="Дата" property="6" />
            <msh:tableColumn columnName="Время" property="12" />
            <msh:tableColumn columnName="Койко-дни" property="9" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_slo" name="Новый СЛО" title="Добавить случай стационарного лечения в отделении" 
      roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/Create" key="ALT+2"
      />
    </msh:sideMenu>
    
  </tiles:put>
</tiles:insert>

