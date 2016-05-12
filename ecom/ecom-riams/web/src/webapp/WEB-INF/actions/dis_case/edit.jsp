<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай нетрудоспособности
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-dis_case.do" defaultField="dateFrom">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="patient" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="dateFrom" label="Дата начала" viewOnlyField="true" />
          <msh:textField property="dateTo" label="Дата окончания" guid="ae38cf5e-1794-4d32-8918-ad928dae7eb5" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox fieldColSpan="3" horizontalFill="true" property="placementService" label="Состоит на учете в службе занятости"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="earlyPregnancyRegistration" label="Поставлена на учет в ранние сроки беременности (до 12 недель)" horizontalFill="true" fieldColSpan="6"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Сопровождающие лица" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentId="dis_caseForm.patient" vocName="kinsmanByDis" property="nursingPerson1" label="Лицо по уходу 1" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentId="dis_caseForm.patient" vocName="kinsmanByDis" property="nursingPerson2" label="Лицо по уходу 2" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:label property="duration" label="Продолжительность в днях" guid="6e584655-3e8b-496d-9d2f-68706daafb67" />
        </msh:row>
        <msh:row>
        	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        	<msh:textField property="createUsername" label="Пользователь" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="editDate" label="Дата редактирования" viewOnlyField="true"/>
        	<msh:textField property="editUsername" label="Пользователь" viewOnlyField="true"/>
        </msh:row>
        
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="dis_caseForm">
      <msh:section createRoles="/Policy/Mis/Disability/Case/Document/Create" createUrl="entityParentPrepareCreate-dis_document.do?id=${param.id}" guid="sectionChilds" title="Документы нетрудоспособности">
        <ecom:webQuery name="docs" nativeSql="
  	select dd.id
  	,dd.issueDate
  	,vds.name as vdsname
  	, vddt.name as vddtname
  	,vdp.name as vdpname
  	, 'сер. '||dd.series||' №'||dd.number as sernumber
  	, min(dr.datefrom) as mindatefrom
  	, case when 
count(case when dr.dateto is null then '1' else null end)>0 
then '-' else to_char(max(dr.dateto),'dd.mm.yyyy') end maxdateto 
, case when 
count(case when dr.dateto is null then '1' else null end)>0 
then null 
else 
case when max(dr.dateto)=min(dr.datefrom) then '1' else max(dr.dateto)-min(dr.datefrom)+1 end end
  	from disabilitydocument dd 
  	left join disabilityrecord dr on dr.disabilitydocument_id=dd.id 
  	left join VocDisabilityStatus vds on vds.id=dd.status_id
  	left join VocDisabilityDocumentType vddt on vddt.id=dd.documentType_id
  	left join VocDisabilityDocumentPrimarity vdp on vdp.id=dd.primarity_id
  	where dd.disabilitycase_id='${param.id}'
  	group by dd.id,dd.issueDate,vds.name,vddt.name,vdp.name,dd.series,dd.number
  	order by dd.issueDate
  	"/>
    <msh:table viewUrl="entityParentView-dis_document.do?short=Short" name="docs" 
    action="entityParentView-dis_document.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" guid="06d94f6a7-ed40-4ebf-a274-1efd69d01cfe4" />
          <msh:tableColumn columnName="Дата выдачи" property="2" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
          <msh:tableColumn columnName="Статус" property="3" />
          <msh:tableColumn columnName="Тип документа" property="4" guid="71edd77-ddd1-4ed-453fa2687534" />
          <msh:tableColumn columnName="Первичность" property="5" guid="71edd774-ddd1-4e0b-ae7d-453fa2687534" />
          <msh:tableColumn columnName="Информация" property="6" guid="d61b9d49-a94d-4cf2-af1b-05020213901f" />
      <msh:tableColumn columnName="Дата начала" property="7" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Дата окончания" property="8" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Продолжительность" property="9" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
        
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Disability" beginForm="dis_caseForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_caseForm" guid="a2b73e72-2f8a-4b8e-a53e-2925252d9eba">
      <msh:sideMenu guid="sideMenu-123" title="Случай">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-dis_case" name="Изменить" roles="/Policy/Mis/Disability/Case/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_case" name="Удалить" roles="/Policy/Mis/Disability/Case/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="08906109-6c4d-469c-9c76-cd63fd470464">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_document" name="Документы нетрудоспособности" title="Добавить документ нетрудоспособности" guid="19aec748-a626-4b6e-af52-cdcc98b1ac62" roles="/Policy/Mis/Disability/Case/Document/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

