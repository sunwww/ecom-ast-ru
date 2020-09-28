<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_newBornForm">
      <msh:sideMenu title="Новорожденный">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-preg_newBorn" name="Изменить" roles="/Policy/Mis/NewBorn/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_newBorn" name="Удалить" roles="/Policy/Mis/NewBorn/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
        <msh:sideLink params="id" action="/print-newbornhistory.do?s=PrintNewBornHistoryService&amp;m=printNewBornHistory" name="Историю развития новорожденного" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Новорожденный
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_newBorn.do" defaultField="lastname">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="childBirth" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:panel colsWidth="7%,7%,4%">
          <msh:row>
          	<msh:autoComplete property="child" vocName="vocNewBorn" label="Ребенок" horizontalFill="true" fieldColSpan="3"/>
          </msh:row>
        <msh:ifFormTypeIsView formName="preg_newBornForm">
          <msh:row>
            <msh:autoComplete property="patient" vocName="patient" label="Персона ребенка" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" viewAction="entityView-mis_patient.do" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete property="sex" vocName="vocSex" label="Пол" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthDate" label="Дата рождения" />
          <msh:textField property="birthTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthWeight" label="Вес при рождении (гр.)" />
          <msh:textField property="birthHeight" label="Рост при рождении (см)" />
        </msh:row>
        <msh:row>
          <msh:textField property="headCircle" label="Окружность головы" />
          <msh:textField property="shouldersCircle" label="Окружность плеч" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="liveBorn" label="Родился" vocName="vocLiveBorn" fieldColSpan="1" />
        </msh:row>
        <msh:checkBox property = "deadBeforeLabors" label="Умер до начала родовой деятельности (только для родившихся мертвыми)"/>
        
        <msh:row>
          <msh:autoComplete property="partBodyBorn" label="Родился" vocName="vocPartBodyBorn" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="maturity" vocName="vocNewBornMaturity" label="Зрелость" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthState" label="Состояние при рождении" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="setPart" label="Вставленная часть тела" />
          <msh:textField property="setView" label="Вид вставки" />
        </msh:row>
        
        <msh:row>
          <msh:separator label="Пуповина" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="cordLength" label="Длина пуповины (см)" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="entanglement" vocName="vocBirthEntanglement" label="Обвитие" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="entanglementMultiplicity" vocName="vocBirthEntanglementMultiplicity" label="Кол-во обвитий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="whereEntanglement" vocName="vocBirthWhereEntanglement" label="Где" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="cordAttaching" horizontalFill="true" vocName="vocNewBornCordAttaching" label="Прикрепление пуповины" />
          <msh:textField property="hemorrhageVolume" label="Кровопотеря (мл)" />
        </msh:row>
        
        <msh:row>
          <msh:separator label="Дополнительно" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="oesophagusPermeability" label="Проходимость пищевода" horizontalFill="true" />
          <msh:checkBox property="anus" label="Наличие ануса" />
        </msh:row>
        <msh:row>
          <msh:textField property="malformations" label="Пороки развития" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="childDeliverer" label="Кто принимал ребенка" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" />
          <msh:textField property="createTime" label="Время" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="preg_newBornForm">
    <msh:section title="Госпитализация">
    <ecom:webQuery name="list" nativeSql="select sls.id as f1slsid
  ,to_char(sls.dateStart,'dd.mm.yyyy') as f2dateStart
  ,to_char(sls.dateFinish,'dd.mm.yyyy') as f3dateFinish 
  ,vdh.id as f4vhdid,sls.username as f5slsusername
  ,case when sls.emergency='1' then 'да' else null end as f6emergency 
  ,coalesce(ss.code,'')||case when vdh.id is not null then ' '||vdh.name else '' end as f7stacard 
  ,ml.name as f8entdep,mlLast.name as f9mlLastdep 
  ,case when vdh.id is not null then null when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart) end as f10countDays 
  ,list(distinct vpd.name||' '||mkb.code) as f11diagDisch 
  ,list(distinct vpd1.name||' '||mkb1.code) as f12diagClinic
  ,case when vdh.id is not null then 'color: red ;' 
  when UPPER(sls.DTYPE) ='EXTHOSPITALMEDCASE' then 'color: blue ;'
  else '' end
    as f13style 
  from MedCase sls 
  left join newborn nb on nb.patient_id=sls.patient_id
  left join VocHospType vht on vht.id=sls.hospType_id 
  left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id 
  left join MedCase sloLast on sloLast.parent_id=sls.id and sloLast.dtype='DepartmentMedCase' 
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
  left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id where nb.id=${param.id} 
  and UPPER(sls.DTYPE) IN ('HOSPITALMEDCASE','EXTHOSPITALMEDCASE') 
  and  (sloLast.id is null or sloLast.transferDate is null) 
   group by sls.id,sls.dtype,sls.dateStart,sls.dateFinish,vdh.id ,sls.username ,sls.emergency, ss.code,vdh.id,vdh.name ,ml.name,mlLast.name,vht.code 
   order by sls.dateStart
  "/>
    <msh:table name="list" action="entitySubclassView-mis_medCase.do" 
    viewUrl="entitySubclassView-mis_medCase.do?short=Short"
    idField="1" styleRow="13">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="7" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Дата выписки" property="3" />
      <msh:tableColumn columnName="Кол-во дней" property="10" />
      <msh:tableColumn columnName="Экстр.?" property="6" />
      <msh:tableColumn columnName="Отделение пост." property="8" />
      <msh:tableColumn columnName="Отделение выписки (текущее)" property="9" />
      <msh:tableColumn columnName="Диагноз клин. посл. отд." property="12" />
      <msh:tableColumn columnName="Диагноз выписной" property="11" />
      <msh:tableColumn columnName="Кем открыт" property="5" />
    </msh:table>
    </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_newBornForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">

  </tiles:put>
</tiles:insert>

