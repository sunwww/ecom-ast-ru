<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-mis_worker.do" defaultField="personName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete showId="false" vocName="lpu" property="lpu" viewOnlyField="false" size="50" label="ЛПУ" horizontalFill="false" viewAction="entityParentView-mis_lpu.do" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="patient" property="person" viewOnlyField="false"  label="Персона" horizontalFill="true" viewAction="entityView-mis_patient.do" />
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
        
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <tags:mis_patient_new divForButton="personButton" name='Person' title='Создание новой персоны' autoComplitePatient="person"/>
    <msh:ifFormTypeIsView formName="mis_workerForm">
      <msh:section title="Должностные обязанности">
        <ecom:parentEntityListAll formName="work_personalWorkFunctionForm" attribute="functions" />
        <msh:table idField="id" name="functions" action="entityParentView-work_personalWorkFunction.do">
          <msh:tableColumn columnName="Название функции" property="name" />
          <msh:tableColumn columnName="Групповая рабочая функция" property="groupInfo" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Сотрудник">
      <msh:ifFormTypeIsView formName="mis_workerForm">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_worker" name="Изменить" roles="/Policy/Mis/Worker/Worker/Edit" />
        <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-mis_worker" name="Удалить" confirm="Удалить сотрудника?" roles="/Policy/Mis/Worker/Worker/Delete" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <msh:ifFormTypeIsView formName="mis_workerForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-work_personalWorkFunction" name="Должностные обязанности" title="Добавить Должностные обязанности" roles="/Policy/Mis/Worker/WorkFunction/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_workerForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsNotView formName="mis_workerForm">
  	<script type="text/javascript">
  		initPersonPatientDialog()</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

