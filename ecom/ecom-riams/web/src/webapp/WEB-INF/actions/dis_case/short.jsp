<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай нетрудоспособности
    	  -->
    <msh:form action="/entityParentSaveGoView-dis_case.do" defaultField="dateFrom">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:panel>
        <msh:row>
          <msh:textField property="dateFrom" label="Дата начала" viewOnlyField="true" />
          <msh:textField property="dateTo" label="Дата окончания" viewOnlyField="true" />
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
        <msh:row>
          <msh:label property="duration" label="Продолжительность в днях" />
        </msh:row>
        <msh:row>
        	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        	<msh:textField property="createUsername" label="Пользователь" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="editDate" label="Дата редактирования" viewOnlyField="true"/>
        	<msh:textField property="editUsername" label="Пользователь" viewOnlyField="true"/>
        </msh:row>
        
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="dis_caseForm">
      <msh:section createRoles="/Policy/Mis/Disability/Case/Document/Create" createUrl="entityParentPrepareCreate-dis_document.do?id=${param.id}" title="Документы нетрудоспособности">
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
      <msh:tableColumn columnName="#" property="sn" />
          <msh:tableColumn columnName="Дата выдачи" property="2" />
          <msh:tableColumn columnName="Статус" property="3" />
          <msh:tableColumn columnName="Тип документа" property="4" />
          <msh:tableColumn columnName="Первичность" property="5" />
          <msh:tableColumn columnName="Информация" property="6" />
      <msh:tableColumn columnName="Дата начала" property="7" />
      <msh:tableColumn columnName="Дата окончания" property="8" />
      <msh:tableColumn columnName="Продолжительность" property="9" />
    </msh:table>
        
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_caseForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_caseForm">
      <msh:sideMenu title="Случай">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-dis_case" name="Изменить" roles="/Policy/Mis/Disability/Case/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_case" name="Удалить" roles="/Policy/Mis/Disability/Case/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-dis_document" name="Документы нетрудоспособности" title="Добавить документ нетрудоспособности" roles="/Policy/Mis/Disability/Case/Document/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

