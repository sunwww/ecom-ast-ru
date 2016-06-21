<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_newBornForm" guid="c8ee4cc0-2ebd-4772-8d66-f2891f3c6f04">
      <msh:sideMenu title="Новорожденный" guid="bef784df-2afa-4cbb-8052-3b0ea730b81c">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-preg_newBorn" name="Изменить" roles="/Policy/Mis/NewBorn/Edit" guid="920412b7-c092-4ef5-bec9-d0e235b8f8c4" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_newBorn" name="Удалить" roles="/Policy/Mis/NewBorn/Delete" guid="dc3b73ce-bf8d-4651-baf0-718b8779e8da" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="d942ffee-6e9f-4317-9fa7-8dc776d47e90">
        <msh:sideLink params="id" action="/print-newbornhistory.do?s=PrintNewBornHistoryService&amp;m=printNewBornHistory" name="Историю развития новорожденного" guid="439e8484-527f-4f10-aa0f-4073e6431478" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Новорожденный
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_newBorn.do" defaultField="lastname" guid="88a846fc-510d-4ffd-b68a-f4157d2ccdba">
      <msh:hidden property="id" guid="227e3745-e945-40cb-891e-ba073b77e5c6" />
      <msh:hidden property="medCase" guid="e078491f-f05c-48ab-b515-69cca8aff105" />
      <msh:hidden property="childBirth" guid="54270d91-95d2-4ef9-b466-2a7f5c640871" />
      <msh:hidden property="saveType" guid="fe3095ba-8a17-4e20-a90a-e725d5d8c7c1" />
      <msh:hidden property="patient" guid="d1dbbb8c-6fd4-4303-87e3-eefed35dae3f" />
      <msh:panel guid="6698b1a2-f6ce-4e70-85a3-0c3689e690da" colsWidth="7%,7%,4%">
          <msh:row guid="9625d712-b37d-4426-8072-de2ccf878aa4">
          	<msh:autoComplete property="child" vocName="vocNewBorn" label="Ребенок" horizontalFill="true" fieldColSpan="3"/>
          </msh:row>
        <msh:ifFormTypeIsView formName="preg_newBornForm" guid="2211c6d9-2251-4059-b91c-780e6bf32bad">
          <msh:row guid="90569cad-0d3e-4dbc-93d2-79a86a59eefe">
            <msh:autoComplete property="patient" vocName="patient" label="Персона ребенка" guid="c7d24f51-85cd-4714-83bf-84c071df9efd" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" viewAction="entityView-mis_patient.do" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row guid="712f1fdd-f61f-4f8f-8f82-3a16fa870225">
          <msh:autoComplete property="sex" vocName="vocSex" label="Пол" guid="171b9903-3e96-4d7b-9c5e-f374cf8ea696" horizontalFill="true" />
        </msh:row>
        <msh:row guid="7ed1ae1c-b636-481a-b020-abf75a0d68d5">
          <msh:textField property="birthDate" label="Дата рождения" guid="d72aef25-6193-4b14-8311-e6841db6162b" />
          <msh:textField property="birthTime" label="Время" guid="8cfac7a8-4610-46ee-a906-3f057a87a426" />
        </msh:row>
        <msh:row guid="ffcd5ba0-5927-43df-8de8-78486cb9da6f">
          <msh:textField property="birthWeight" label="Вес при рождении (гр.)" guid="d9c42906-074e-4514-a87e-103a475c55f4" />
          <msh:textField property="birthHeight" label="Рост при рождении (см)" guid="8dd9c5cc-7b1d-4f60-91b2-717d1f2c1e6f" />
        </msh:row>
        <msh:row guid="b8af5514-e54a-4e01-9ca9-c2d245657a21">
          <msh:textField property="headCircle" label="Окружность головы" guid="0314588d-60e4-4800-b094-ed7dc3f6268c" />
          <msh:textField property="shouldersCircle" label="Окружность плеч" guid="3cd23d3e-00a4-4053-a814-5a34c81d5231" />
        </msh:row>
        <msh:row guid="43669dbf-aba5-48b7-8979-20b12d5e196b">
          <msh:autoComplete property="liveBorn" label="Родился" vocName="vocLiveBorn" fieldColSpan="1" />
        </msh:row>
        <msh:checkBox property = "deadBeforeLabors" label="Умер до начала родовой деятельности (только для родившихся мертвыми)"/>
        
        <msh:row guid="43669dbf-aba5-48b7-8979-20b12d5e196b">
          <msh:autoComplete property="partBodyBorn" label="Родился" vocName="vocPartBodyBorn" fieldColSpan="1" />
        </msh:row>
        <msh:row guid="59f403df-942b-49f4-b67c-46baf688b3a6">
          <msh:autoComplete property="maturity" vocName="vocNewBornMaturity" label="Зрелость" horizontalFill="true" fieldColSpan="3" guid="f6d7e46c-adc3-4225-810a-8fccc2c1a137" />
        </msh:row>
        <msh:row guid="cca33c1f-0258-4790-8982-c0f578fec4e6">
          <msh:textField property="birthState" label="Состояние при рождении" horizontalFill="true" fieldColSpan="3" guid="50f9ebae-0c85-496b-97c5-376576f7f4b4" />
        </msh:row>
        <msh:row guid="c7ee4578-8d70-4735-a152-76b44c70644b">
          <msh:textField property="setPart" label="Вставленная часть тела" guid="af5727c6-6da3-486e-8dd0-892c32d938f7" />
          <msh:textField property="setView" label="Вид вставки" guid="21a1677c-35b9-49b6-8727-0b69c861b5a4" />
        </msh:row>
        
        <msh:row guid="5f38a220-3e41-408b-bb66-231156b77f68">
          <msh:separator label="Пуповина" colSpan="4" guid="921f6c76-69ed-4032-9adc-d780405df2d8" />
        </msh:row>
        <msh:row guid="bb717265-975a-4084-8cb2-a8785c800583">
          <msh:textField property="cordLength" label="Длина пуповины (см)" guid="f6171ecf-bc9a-42c5-b458-ac7833911ed2" />
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
        <msh:row guid="e7fd00f3-b4c5-4bf6-ada2-5603a53a1186">
          <msh:autoComplete property="cordAttaching" horizontalFill="true" vocName="vocNewBornCordAttaching" label="Прикрепление пуповины" guid="9e8a6644-c476-4308-b5cf-fef98328c1bb" />
          <msh:textField property="hemorrhageVolume" label="Кровопотеря (мл)" guid="ef03770a-3e38-4866-99f7-db2c2762f4f3" />
        </msh:row>
        
        <msh:row guid="c3c7839e-e37d-4979-b2ea-23937b555c8d">
          <msh:separator label="Дополнительно" colSpan="4" guid="5507691a-5fdf-4588-be0f-83c6605419c5" />
        </msh:row>
        <msh:row guid="62deaeba-2fda-43fc-97b7-89c0ae2a5f42">
          <msh:textField property="oesophagusPermeability" label="Проходимость пищевода" guid="3cdd3867-1684-4184-9050-41ee7b2a7219" horizontalFill="true" />
          <msh:checkBox property="anus" label="Наличие ануса" guid="efeb3f63-f958-4f8f-919b-f68df6ab20df" />
        </msh:row>
        <msh:row guid="8392365c-d4d2-4df5-9b69-f8b273165275">
          <msh:textField property="malformations" label="Пороки развития" fieldColSpan="3" horizontalFill="true" guid="495d5ee7-30fa-478f-8e04-53e3f8d2e9d1" />
        </msh:row>
        <msh:row guid="0ba5c980-775f-4a75-8f09-7bfe5fc46f68">
          <msh:autoComplete property="childDeliverer" label="Кто принимал ребенка" vocName="workFunction" fieldColSpan="3" horizontalFill="true" guid="c2a66ae9-2bf4-4e42-a879-f641e5ae95d0" />
        </msh:row>
        <msh:row guid="2be7215c-2830-421d-9500-1bc3c7a5f9d5">
          <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" guid="309593a6-8300-4ea9-be02-be2d0ef8cd1b" />
          <msh:textField property="createTime" label="Время" viewOnlyField="true" guid="fac5b2da-e4ff-4370-a1fd-9f5fabf69ac2" />
        </msh:row>
        <msh:row guid="ca06aabe-2a85-4477-81b5-9590545b3b87">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="e6946c8a-08e3-4c5e-bc45-500c5fa53b01" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="15e2342c-9373-4dec-b306-0bc21c1b603c" />
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
      <msh:tableColumn columnName="#" property="sn" guid="ce16c32c-9459-4673-9ce8-d6e646f969ff" />
      <msh:tableColumn columnName="Стат.карта" property="7" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Кол-во дней" property="10" guid="8b496fc7-80e9-4beb-878b-5bfb20e98f31" />
      <msh:tableColumn columnName="Экстр.?" property="6" guid="e98f6bbc96" />
      <msh:tableColumn columnName="Отделение пост." property="8" guid="8b496fc7-80e9-4beb-878b-5bfb20e98f31" />
      <msh:tableColumn columnName="Отделение выписки (текущее)" property="9" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Диагноз клин. посл. отд." property="12" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Диагноз выписной" property="11" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Кем открыт" property="5" guid="35347247-b552-4154-a82a-ee484a1714ad" />
    </msh:table>
    </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_newBornForm" guid="59d4465e-092d-4353-98ff-af27d0a2c482" />
  </tiles:put>
  <tiles:put name="javascript" type="string">

  </tiles:put>
</tiles:insert>

