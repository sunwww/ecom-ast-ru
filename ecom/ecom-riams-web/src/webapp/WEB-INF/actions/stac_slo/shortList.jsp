<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
<ecom:webQuery name="list" nativeSql="select MedCase.id, MedCase.dateStart&#xA;       
           , MedCase.department_id||' '||postDep.Name&#xA;        , MedCase.transferDate &#xA;      
             , MedCase.transferDepartment_id||' '||tranDep.Name&#xA;       
              , MedCase.dateFinish&#xA;&#x9;, Patient.lastname || ' ' ||  Patient.firstname || ' ' || Patient.middlename as startWorker&#xA;&#x9;, op.lastname || ' ' ||  op.firstname || ' ' || op.middlename as ownerWorker&#xA;  ,ifnull(MedCase.dateFinish, ifnull(MedCase.transferDate,($$GetBedDays^ZExpCheck('000' || (case when BedFund.addCaseDuration=1 then 'J' else 'A' end),MedCase.dateStart,CURRENT_DATE)) || '(на текущий момент)' ,($$GetBedDays^ZExpCheck('000' || (case when BedFund.addCaseDuration=1 then 'J' else 'A' end),MedCase.dateStart,MedCase.transferDate)) ||' (перевод)') ,($$GetBedDays^ZExpCheck('000' || (case when BedFund.addCaseDuration=1 then 'J' else 'A' end),MedCase.dateStart,MedCase.dateFinish)) ||' (выписан)')           , MedCase.entranceTime,MedCase.transferTime,MedCase.dischargeTime  
              ,VocServiceStream.name
              from MedCase&#xA;  left outer join Worker     on MedCase.startWorker_id = Worker.id&#xA;  
          left outer join Patient    on Worker.person_id       = Patient.id&#xA;  
          left outer join Worker ow  on MedCase.owner_id       = ow.id &#xA; 
left outer join Patient op on ow.person_id           = op.id&#xA;  
left outer join MisLpu tranDep on MedCase.transferDepartment_id           = tranDep.id&#xA; 
 left outer join MisLpu postDep on MedCase.department_id           = postDep.id&#xA; 
  left join BedFund on BedFund.id=MedCase.bedFund_id 
  left join VocBedSubType on VocBedSubType.id = BedFund.bedSubType_id 
          left join VocServiceStream on VocServiceStream.id=MedCase.serviceStream_id
  
  where MedCase.parent_id=${param.id} &#xA;   and MedCase.DTYPE='DepartmentMedCase'" guid="624771b1-fdf1-449e-b49e-5fcc34e03fb5" />
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

</tiles:insert>