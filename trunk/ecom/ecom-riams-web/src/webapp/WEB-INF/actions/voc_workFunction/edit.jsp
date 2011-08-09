<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник рабочих функций
    	  -->
    <msh:form action="/entitySaveGoView-voc_workFunction.do" defaultField="code" guid="c8f2f6d6-5482-43a0-a137-1b22828b43f5">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
        <msh:row guid="232f0010-a709-4078-bb63-090b4e2aee95">
          <msh:textField label="Код" property="code" guid="40f2cd0a-d466-4811-82fc-ed560c6ca164" />
          <msh:textField label="Наименование" property="name" size="80" guid="7d51235b-c16b-4b60-8743-46e38c0c1ca0" />
        </msh:row>
        <msh:row guid="10e684ef-5a8d-45dd-ab3d-c5bae516a73e">
          <msh:autoComplete horizontalFill="true" property="vocPost" vocName="vocPost" label="Должность" fieldColSpan="3" viewAction="entityView-voc_post.do" guid="351c87de-9b9a-46ee-80c9-b65d6256fd0c" />
        </msh:row>
          <msh:row guid="609cc05e-2317-4c15-873c-23738b8c21ed">
            <ecom:oneToManyOneAutocomplete viewAction="entityView-mis_medService.do" label="Прикр. мед. услуги" property="workFunctionServices" vocName="medServiceDop" colSpan="3" guid="8bab83af-57b9-4e4e-960b-922841a1435d" />
          </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="voc_workFunctionForm" guid="f2c60a50-ba01-48ae-8cc9-fda607304293">
      <msh:section guid="aa36e0dc-88f0-49d4-a6b9-581c2e3028f2">
        <msh:sectionTitle guid="482fa4a1-314b-4189-995d-7acd22f59c05">Рабочие функции специалистов</msh:sectionTitle>
        <msh:sectionContent guid="217bfa11-c4f6-4ec7-be9e-421a55030561">
          <ecom:webQuery name="workFuncs" nativeSql="select wf.id, wp.lastname||' '||wp.firstname||' '||wp.middlename,ml.name,gr.groupname from WorkFunction wf left join Worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id left join MisLpu ml on ml.id=w.lpu_id        left join WorkFunction gr on gr.id=wf.group_id       where wf.DTYPE='PersonalWorkFunction' and wf.workFunction_id='${param.id}'" guid="6cd78a21-a53c-4a77-a9fd-4990947e3c73" />
          <msh:table name="workFuncs" action="entitySubclassView-work_workFunction.do" idField="1" guid="a57b9c5c-e9bb-47f2-a40b-77dedc83527e">
            <msh:tableColumn property="sn" columnName="#" guid="0bc4a95d-8353-4cea-a132-f24420c79cb0" />
            <msh:tableColumn columnName="Сотрудник" property="2" guid="f4fce95e-6357-45c4-ae8a-63855de3de28" />
            <msh:tableColumn columnName="Подразделение" property="3" guid="172f9ffa-d96d-4709-9dfc-016896fb36a4" />
            <msh:tableColumn columnName="Рабочая группа" property="4" guid="b24ab9cd-fbf9-4226-9348-6c9abdd32d42" />
          </msh:table>
        </msh:sectionContent>
        <msh:sectionTitle guid="cde5a335-378b-4d80-a654-f830679fc864">Групповые рабочие функции</msh:sectionTitle>
        <msh:sectionContent guid="36354c0e-b6b7-4e1e-9c82-22fa507ec0d9">
          <ecom:webQuery name="workFuncGrs" nativeSql="select wf.id,wf.groupname,ml.name as name from WorkFunction wf left join MisLpu ml on ml.id=wf.lpu_id        where wf.DTYPE='GroupWorkFunction' and wf.workFunction_id='${param.id}'" guid="747837c0-1242-4c04-8471-9d71aa89c244" />
          <msh:table name="workFuncGrs" action="entitySubclassView-work_workFunction.do" idField="1" guid="bafa90dc-6ddd-4485-8200-14b342b20d5d">
            <msh:tableColumn property="sn" columnName="#" guid="7912e52f-cb94-444a-a7d0-13c5ba2d424d" />
            <msh:tableColumn columnName="Название" property="2" guid="2eea190a-f841-48bf-980a-c4993ecf9623" />
            <msh:tableColumn columnName="Подразделение" property="3" guid="d055aaa6-0ab0-44de-b7a1-43c6a73adbc2" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Voc" beginForm="voc_workFunctionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Справочник рабочих функций" guid="ded8e3d3-a6a8-4c76-b484-92dfe52cc1b7">
      <msh:sideLink roles="/Policy/Voc/VocWorkFunction/Edit" key="ALT+2" params="id" action="/entityEdit-voc_workFunction" name="Изменить" title="Изменить данные по рабочей функции" guid="75bdfecf-3673-4e79-85d1-caa9ec490608" />
      <msh:sideLink roles="/Policy/Voc/VocWorkFunction/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-voc_workFunction" name="Удалить" title="Удалить данные рабочей функции" guid="90ae4764-8e8c-4c53-8648-2fffd15f4097" />
    </msh:sideMenu>
        <tags:voc_menu currentAction="workFunction"/>
  </tiles:put>
</tiles:insert>

