<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form guid="123" action="entityParentSaveGoView-mis_worker.do" defaultField="personName">
      <msh:hidden property="id" guid="67476bdd-7eb2-4eac-8512-9476a6e2d5ad" />
      <msh:hidden property="saveType" guid="0a37e6e5-4875-4dae-a226-50fba9990881" />
      <msh:hidden property="lpu" guid="e38246ea-4a88-40fb-8f4d-3986e8856332" />
      <msh:panel guid="04fd7a8f-37bc-4492-996b-5778911d56cc">
        <msh:row guid="1e6e2aa4-4025-b877-58993d9b320d">
          <msh:autoComplete showId="false" vocName="lpu" property="lpu" viewOnlyField="false" size="50" label="ЛПУ" guid="e4aa19-4405-adcf-8b7ef8fad03f" horizontalFill="false" viewAction="entityParentView-mis_lpu.do" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="1e6e2aa0-b434-4025-b877-58993d9b320d">
          <msh:autoComplete showId="false" vocName="patient" property="person" viewOnlyField="false"  label="Персона" guid="e4a238b3-0a19-4405-adcf-8b7ef8fad03f" horizontalFill="true" viewAction="entityView-mis_patient.do" />
<td align="right" width="1px"><div id="personButton"></div></td>
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                
        
        <msh:submitCancelButtonsRow colSpan="2" guid="9279e49d-2ee0-426b-9024-b06947a1f0a8" />
      </msh:panel>
    </msh:form>
    <tags:mis_patient_new divForButton="personButton" name='Person' title='Создание новой персоны' autoComplitePatient="person"/>
    <msh:ifFormTypeIsView formName="mis_workerForm" guid="e4c62deb-7fd7-43f2-a2a1-ba3e365bde45">
      <!--      <msh:section title="Должности" guid="0c0dd1a6-d052-4b8a-afe0-0c273a5f4ca3">-->
      <!--        <ecom:parentEntityListAll formName="mis_workBookForm" attribute="text" guid="2d1c932f-25b3-4b7a-8f84-3eab13c839ed" />-->
      <!--        <msh:table name="text" action="entityParentView-mis_workBook.do" idField="id" guid="c19feeb3-5e2e-4176-b711-c9be98b7c2a5">-->
      <!--          <msh:tableColumn columnName="Должность" property="post" guid="517ca77d-72ce-4d15-8022-805569350a0b" />-->
      <!--          <msh:tableColumn columnName="Совместительство" property="nameCombo" guid="49580cee-7dee-4fd1-a336-d039e5bf2991" />-->
      <!--          <msh:tableColumn columnName="Дата приёма" property="dateOn" guid="613aaf33-83d4-4331-bab5-3cf526b0bbb1" />-->
      <!--          <msh:tableColumn columnName="Дата увольнения" property="dateOff" guid="2fe67e33-3f03-403e-9b9a-643b6884ea1d" />-->
      <!--        </msh:table>-->
      <!--      </msh:section>-->
      <msh:section title="Должностные обязанности" guid="fefb4ff0-0639-4962-9a9f-1e2f0b2d16df">
        <ecom:parentEntityListAll formName="work_personalWorkFunctionForm" attribute="functions" guid="10a1c98a-229b-42a0-a38f-aa07be61335f" />
        <msh:table idField="id" name="functions" action="entityParentView-work_personalWorkFunction.do" guid="f5718e90-b707-46d5-8ff6-b2d41f611134">
          <msh:tableColumn columnName="Название функции" property="name" guid="c0d5d07-b789-9211d6de17de" />
          <msh:tableColumn columnName="Групповая рабочая функция" property="groupInfo" guid="c0d51e15-4d07-b789-9211d6de17de" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Сотрудник" guid="8709feb9-c102-4a0f-8bb5-3404cf624927">
      <msh:ifFormTypeIsView formName="mis_workerForm" guid="e4a7c0d3-c936-4947-a202-2c14949a8567">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_worker" name="Изменить" guid="175cf029-eaae-47a2-8ad7-0486fcbea707" roles="/Policy/Mis/Worker/Worker/Edit" />
        <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-mis_worker" name="Удалить" confirm="Удалить сотрудника?" guid="bed2b91a-84fc-4a8b-9cb1-0a83d94fd2de" roles="/Policy/Mis/Worker/Worker/Delete" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <msh:ifFormTypeIsView formName="mis_workerForm" guid="b0b5c1a9-5459-43b9-9030-ba0177a24cbd">
      <msh:sideMenu title="Добавить" guid="652cf5dc-1acf-4fb5-b064-7eb3912e6531">
        <msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-work_personalWorkFunction" name="Должностные обязанности" title="Добавить Должностные обязанности" guid="6bebca67-8d42-4281-baaf-a203fb08f426" roles="/Policy/Mis/Worker/WorkFunction/Create" />
        <!--        <msh:sideLink params="id" action="/entityParentPrepareCreate-mis_workBook" name="Трудовая книжка" title="Добавить трудовую книжку" guid="6b2a67-8d42-4281-baaf-a288f426" roles="/Policy/Mis/Worker/WorkBook/Create" />-->
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_workerForm" guid="049dd53d-f3b2-495a-bfec-be41a32d7a27" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsNotView formName="mis_workerForm">
  	<script type="text/javascript">
  		initPersonPatientDialog()</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

